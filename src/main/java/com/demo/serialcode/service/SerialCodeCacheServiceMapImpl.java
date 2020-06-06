package com.demo.serialcode.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.date.DateUtil;
import com.demo.serialcode.domain.GenRule;
import com.demo.serialcode.config.SerialCodeConfig;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 序列号Map缓存器
 *
 * @author hongzhaomin 2018年3月28日 下午14:02:30
 */
//@Service(SerailCodeModuleBeanNames.SerialCodeModuleSerialCodeCacheServiceMapImpl)
public class SerialCodeCacheServiceMapImpl extends AbstractSerialCodeCacheService {

    private Map<String, List<String>> serialCodeMap = new HashMap<String, List<String>>();

    /**
     * 序列号配置
     */
    @Autowired
    private SerialCodeConfig serialCodeConfig;

    @Override
    protected String get(String key, String genRule, String infix) {
        String serialCode = null;
        List<String> serialCodes = serialCodeMap.get(key);
        //如果是日期中缀类型的序列号，需要判断缓存中的序列号是否已经过期
        GenRule genRuleObj = GenRule.valueOf(genRule);
        if (GenRule.CalendarInfix == genRuleObj || GenRule.CalendarInfixTimestampSufix == genRuleObj) {
            String newCalenderInfix = DateUtil.format(new Date(), infix);
            String curCalenderInfix = "";
            if (serialCodes != null && serialCodes.size() > 0) {
                serialCode = serialCodes.remove(0);
                //从取出的序列号中获取中缀日期字符串
                String[] codes = serialCode.split(serialCodeConfig.getJoiner());
                curCalenderInfix = codes[1];
                if (!newCalenderInfix.equals(curCalenderInfix)) {
                    serialCodes.clear();
                    serialCode = null;
                }
            }
        } else {
            if (serialCodes != null && serialCodes.size() > 0) {
                serialCode = serialCodes.remove(0);
            }
        }

        return serialCode;
    }

    @Override
    protected void cache(String key, List<String> serialCodes, String infix) {
        List<String> cachedValues = serialCodeMap.get(key);
        if (cachedValues == null) {
            cachedValues = new ArrayList<String>();
        }
        cachedValues.addAll(serialCodes);
        serialCodeMap.put(key, cachedValues);
    }

}
