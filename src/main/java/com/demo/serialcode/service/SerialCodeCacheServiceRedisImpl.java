package com.demo.serialcode.service;

import cn.hutool.core.date.DateUtil;
import com.demo.serialcode.domain.GenRule;
import com.demo.serialcode.moduleinfo.SerailCodeModuleBeanNames;
import com.demo.serialcode.repository.redis.SerialCodesCollectionService;
import com.demo.serialcode.config.SerialCodeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * 序列号Redis缓存器
 *
 * @author shenhongjun
 * @since 2020/5/29
 */
@Service(SerailCodeModuleBeanNames.SerialCodeModuleSerialCodeCacheServiceRedisImpl)
public class SerialCodeCacheServiceRedisImpl extends AbstractSerialCodeCacheService {

    @Resource(name = SerailCodeModuleBeanNames.SerialCodeCollectionRedisService)
    private SerialCodesCollectionService serialCodesCollectionService;

    /**
     * 序列号配置
     */
    @Autowired
    private SerialCodeConfig serialCodeConfig;

    @Override
    protected String get(String key, String genRule, String infix) {
        String serialCode = serialCodesCollectionService.get(key);
        if (StringUtils.isEmpty(serialCode)) {
            return null;
        }

        //如果是日期中缀类型的序列号，需要判断缓存中的序列号是否已经过期
        GenRule genRuleObj = GenRule.valueOf(genRule);
        if (GenRule.CalendarInfix == genRuleObj || GenRule.CalendarInfixTimestampSufix == genRuleObj) {
            String newCalenderInfix = DateUtil.format(new Date(), infix);
            String curCalenderInfix = "";
            //从取出的序列号中获取中缀日期字符串
            String[] codes = serialCode.split(serialCodeConfig.getJoiner());
            curCalenderInfix = codes[1];
            if (!newCalenderInfix.equals(curCalenderInfix)) {
                serialCodesCollectionService.delete(key);
            }
        }

        return serialCode;
    }

    @Override
    protected void cache(String key, List<String> serialCodes, String infix) {
        serialCodesCollectionService.put(key, infix, serialCodes);
    }
}
