package com.demo.serialcode.service;

import com.demo.serialcode.domain.CodeTypeConfig;
import com.demo.serialcode.domain.GenRule;
import com.demo.serialcode.domain.Scope;

/**
 * 序列号类型配置服务service 接口
 * 
 * @author hongzhaomin
 * 2018年4月4日 下午2:39:40
 */
public interface CodeTypeConfigService {

    /**
     * 新增序列号类型配置
     * 
     * 2018年4月4日 下午2:46:32 hongzhaomin添加此方法
     * @param codeType 序列号类型配置对象
     */
    public void insertCodeTypeConfig(String codeType, Scope scope, GenRule genRule, String cusSuffix, int numLength, int radix);
    
    /**
     * 修改序列号类型配置
     * 
     * 2018年4月4日 下午2:47:49 hongzhaomin添加此方法
     * @param codeType 序列号类型
     * @param genRule 序列号生成规则
     * @param cusSuffix 序列号自定义规则
     * @param numLength 序列号后缀长度
     * @param radix 序列号后缀 进制
     */
    public void updateCodeTypeConfig(String codeType, String genRule, String cusSuffix, int numLength, int radix);
    
    /**
     * 查询序列号类型配置
     * 
     * 2018年4月4日 下午2:49:24 hongzhaomin添加此方法
     * @param codeType 序列号类型
     * @return 序列号类型配置 对象
     */
    public CodeTypeConfig findCodeTypeConfig(String codeType);
    
}
