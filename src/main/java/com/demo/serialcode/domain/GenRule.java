package com.demo.serialcode.domain;

/**
 * 和生成器对应的生成规则枚举
 * @author hongzhaomin
 * 2018年4月9日 上午11:24:53
 */
public enum GenRule {
    
    /**
     * 常规序列号的生成器，遵循格式为：CodeType-自定义中缀-指定进制和长度的递增字符串
     */
    Normal,
    
    /**
     * 在常规序列号生成器规则基础上，指定日期格式作为自定义中缀的序列号生成器;
     */
    CalendarInfix,
    
    /**
     * 在常规序列号生成器规则基础上，指定日期格式作为自定义中缀，使用（生成序列号时的毫秒时间值 ×1000 + 自增3位数字）
     * 的36进制数，作为序列号生成器;
     */
    CalendarInfixTimestampSufix;
}
