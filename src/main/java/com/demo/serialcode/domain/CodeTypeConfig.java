package com.demo.serialcode.domain;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Date;

/**
 * 序列号所属类型配置类
 *
 * @author hongzhaomin 2018年3月28日 下午14:12:13
 */
public class CodeTypeConfig implements Serializable {

    /**
     * 唯一标识
     */
    private String id;

    /**
     * 序列号所属类型 注：三位大写英文，一般为序列号前缀，用于区分序列号从属和拼装完整序列号，若序列号不需要前缀，则仅用于区分序列号从属 使用参见ToolModuleSerialCodeType
     * 前缀格式，例如：
     * 用户 - USR
     * 订单 - ORD
     * 组织 - ORG
     */
    private String codeType;

    /**
     * 序列号生成规则
     */
    private String genRule;

    /**
     * 序列号类别
     */
    private Scope scope;

    /**
     * 序列号位长
     * 最后几位生成的长度
     */
    private Integer numLength;

    /**
     * 中缀
     * 1.日期生成（Calendar），格式为：YYYYMMddHHmmss
     * 2.一般生成（normal）,自定义形式，格式为：xxxxxxxx
     *
     */
    private String infix;

    /**
     * 指定进制
     */
    private Integer radix;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 获取属性 id 的值
     *
     * @return 属性 id 的值
     */
    public String getId() {
        return id;
    }

    /**
     * 给属性 id 赋值
     *
     * @param id
     *            将要给属性 id 赋予的值
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取属性 codeType 的值
     *
     * @return 属性 codeType 的值
     */
    public String getCodeType() {
        return codeType;
    }

    /**
     * 给属性 codeType 赋值
     *
     * @param codeType
     *            将要给属性 codeType 赋予的值
     */
    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    /**
     * 获取属性 genRule 的值
     *
     * @return 属性 genRule 的值
     */
    public String getGenRule() {
        return genRule;
    }

    /**
     * 给属性 genRule 赋值
     *
     * @param genRule
     *            将要给属性 genRule 赋予的值
     */
    public void setGenRule(String genRule) {
        this.genRule = genRule;
    }

    /**
     * 获取属性 scope 的值
     *
     * @return 属性 scope 的值
     */
    public Scope getScope() {
        return scope;
    }

    /**
     * 给属性 scope 赋值
     *
     * @param scope
     * @return 将要给属性 scope 赋予的值
     */
    public void setScope(Scope scope) {
        this.scope = scope;
    }

    /**
     * 获取属性 numLength 的值
     *
     * @return 属性 numLength 的值
     */
    public Integer getNumLength() {
        return numLength;
    }

    /**
     * 给属性 numLength 赋值
     *
     * @param numLength
     *            将要给属性 numLength 赋予的值
     */
    public void setNumLength(Integer numLength) {
        this.numLength = numLength;
    }

    /**
     * 获取属性 infix 的值
     *
     * @return 属性 infix 的值
     */
    public String getInfix() {
        return infix;
    }

    /**
     * 给属性 infix 赋值
     *
     * @param infix
     *            将要给属性 infix 赋予的值
     */
    public void setInfix(String infix) {
        this.infix = infix;
    }

    /**
     * 获取属性 radix 的值
     *
     * @return 属性 radix 的值
     */
    public Integer getRadix() {
        return radix;
    }

    /**
     * 给属性 radix 赋值
     *
     * @param radix
     *            将要给属性 radix 赋予的值
     */
    public void setRadix(Integer radix) {
        this.radix = radix;
    }

    /**
     * 获取属性 modifyDate 的值
     *
     * @return 属性 modifyDate 的值
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * 给属性 modifyDate 赋值
     *
     * @param modifyDate
     *            将要给属性 modifyDate 赋予的值
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * 参看父类中的注释 @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
