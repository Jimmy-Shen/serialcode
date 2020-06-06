package com.demo.serialcode.service.generator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import org.springframework.stereotype.Service;

import com.demo.serialcode.domain.CodeTypeConfig;
import com.demo.serialcode.domain.GenRule;
import com.demo.serialcode.domain.SerialCode;
import com.demo.serialcode.moduleinfo.SerailCodeModuleBeanNames;
import org.springframework.util.StringUtils;

/**
 * 在常规序列号生成器规则基础上，指定日期格式作为自定义中缀，以（序列号生成时的毫秒值 * 1000 + 三位自增序号）再转换为36进制数 的序列号生成器;
 * @author wangqiang
 * 2019年8月1日 上午9:49:37
 */
@Service(SerailCodeModuleBeanNames.SerialCodeModuleCalendarInfixTimestampSufixSerialCodeGenerator)
public class CalendarInfixTimestampSufixSerialCodeGenerator extends NormalSerialCodeGenerator {
    
    public CalendarInfixTimestampSufixSerialCodeGenerator() {
        this.genRule = GenRule.CalendarInfixTimestampSufix;
        this.registMe();
    }
    
    @Override
    protected List<String> genInfixAndCurCode(CodeTypeConfig codeTypeConfig, SerialCode curSerialCode) {
        List<String> infixAndCurCode = new ArrayList<String>();
        String infix = codeTypeConfig.getInfix();
        String calenderInfix = DateUtil.format(new Date(), infix);
        String curCode = "";
        if(curSerialCode != null) {
            curCode = this.changeRadix(curSerialCode.getCurrentCode(), curSerialCode.getCurCodeRadix(), 
                    codeTypeConfig.getRadix());
        }
        infixAndCurCode.add(calenderInfix);
        infixAndCurCode.add(curCode);
        return infixAndCurCode;
    }
    
    @Override
    protected String fetchIncreaseCode(String currentVaLue, int radix, int num) {
        long nowMs = System.currentTimeMillis();
        long curCodeLong = 0;
        if (!StringUtils.isEmpty(currentVaLue)) {
            curCodeLong = Long.valueOf(currentVaLue, radix);
            if ((curCodeLong / 1000) < nowMs) {
                curCodeLong = nowMs * 1000;
            }
        } else {
            curCodeLong = nowMs * 1000;
        }
        //拆分出毫秒值和自增值
        long curMs = curCodeLong / 1000;
        long curInc = curCodeLong % 1000;
        long nextCodeLong = curMs * 1000 + curInc + 1;
        // 10进制数转换为radix进制数字符串
        String nextCode = Long.toString(nextCodeLong, radix);
        // 位数不足用“0”填充
        nextCode = String.format("%" + num + "s", nextCode).replace(" ", "0");
        return nextCode;
    }
}
