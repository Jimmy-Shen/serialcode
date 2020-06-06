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

/**
 * 在常规序列号生成器规则基础上，指定日期格式作为自定义中缀的序列号生成器;
 * 
 * @author hongzhaomin
 * 2018年4月3日 下午2:14:33
 */
@Service(SerailCodeModuleBeanNames.SerialCodeModuleCalenderInfixSerialCodeGenerator)
public class CalendarInfixSerialCodeGenerator extends NormalSerialCodeGenerator {
    
    public CalendarInfixSerialCodeGenerator() {
        this.genRule = GenRule.CalendarInfix;
        this.registMe();
    }

    /**
     * 根据本规则的定义，生成中缀字符串与当前序列号后缀字符串的集合
     * @param codeTypeConfig 序列号类型配置对象
     * @param curSerialCode 当前序列号对象
     * @return
     */
    @Override
    protected List<String> genInfixAndCurCode(CodeTypeConfig codeTypeConfig, SerialCode curSerialCode) {
        List<String> infixAndCurCode = new ArrayList<String>();
        String infix = codeTypeConfig.getInfix();
        String calenderInfix = DateUtil.format(new Date(), infix);
        String curCode = "";
        //判断当前序列号对象是否存在
        if(curSerialCode != null) {
            String oldCalenderInfix = DateUtil.format(curSerialCode.getModifyDate(), infix);
            //判断中缀是否过期，若过期，序列号则从头计数
            if(calenderInfix.equals(oldCalenderInfix)) {
                curCode = this.changeRadix(curSerialCode.getCurrentCode(), curSerialCode.getCurCodeRadix(), 
                        codeTypeConfig.getRadix());
            } else {
                curCode = "0";
            }
        }
        infixAndCurCode.add(calenderInfix);
        infixAndCurCode.add(curCode);
        return infixAndCurCode;
    }
    
}
