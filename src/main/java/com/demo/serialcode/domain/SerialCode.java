package com.demo.serialcode.domain;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Date;

/**
 * 各模块序列号公共存储类
 * @author zhangshiyang 2017年7月14日 上午10:27:34
 */
public class SerialCode implements Serializable {

    private static final long serialVersionUID = 8954227315215071692L;
    
    /**
     * 唯一标识
     */
    private String id;
    
    /**
     * 当前序列号
     */
    private String currentCode;
    
    /**
     * 当前序列号进制数
     */
    private int curCodeRadix;
    
    /**
     * 序列号所属类型
     * 注：三位大写英文，一般为序列号前缀，用于区分序列号从属和拼装完整序列号，若序列号不需要前缀，则仅用于区分序列号从属
     * 使用参见ToolModuleSerialCodeType
     */
    private String codeType;
    
    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 获取属性  id 的值
     * @return 属性 id 的值
     */
    public String getId() {
        return id;
    }

    /**
     * 给属性 id 赋值
     * @param id 将要给属性 id 赋予的值
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取属性  currentCode 的值
     * @return 属性 currentCode 的值
     */
    public String getCurrentCode() {
        return currentCode;
    }

    /**
     * 给属性 currentCode 赋值
     * @param currentCode 将要给属性 currentCode 赋予的值
     */
    public void setCurrentCode(String currentCode) {
        this.currentCode = currentCode;
    }
    
    /**
     * 获取属性  curCodeRadix 的值
     * @return 属性 curCodeRadix 的值
     */
    public int getCurCodeRadix() {
        return curCodeRadix;
    }
    
    /**
     * 给属性 curCodeRadix 赋值
     * @param curCodeRadix 将要给属性 curCodeRadix 赋予的值
     */
    public void setCurCodeRadix(int curCodeRadix) {
        this.curCodeRadix = curCodeRadix;
    }

    /**
     * 获取属性  codeType 的值
     * @return 属性 codeType 的值
     */
    public String getCodeType() {
        return codeType;
    }

    /**
     * 给属性 codeType 赋值
     * @param codeType 将要给属性 codeType 赋予的值
     */
    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    /**
     * 获取属性  modifyDate 的值
     * @return 属性 modifyDate 的值
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * 给属性 modifyDate 赋值
     * @param modifyDate 将要给属性 modifyDate 赋予的值
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
