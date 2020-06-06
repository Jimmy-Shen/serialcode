package com.demo.serialcode.moduleinfo;

/**
 * 工具模块Bean name
 * @author zhangshiyang 2017年7月14日 上午11:21:27
 */
public interface SerailCodeModuleBeanNames {

    /**
     * 序列号 mybatis实现
     */
    public static String SerialCodeModuleSerialCodeMybatisDao = "serialcodeModuleSerialCodeMybatisDao";
    
    /**
     * 序列号类型 mybatis实现
     */
    public static String SerialCodeModuleCodeTypeConfigMybatisDao = "serialcodeModuleCodeTypeConfigMybatisDao";
    
    /**
     * 序列号dataservice 实现
     */
    public static String SerialCodeModuleSerialCodeDataService = "serialcodeModuleSerialCodeDataService";
    
    /**
     * 序列号类型dataservice 实现
     */
    public static String SerialCodeModuleCodeTypeConfigDataService = "serialCodeModuleCodeTypeConfigDataService";
    
    /**
     * 序列号类型配置service 实现
     */
    public static String SerialCodeModuleCodeTypeConfigService = "serialCodeModuleCodeTypeConfigService";
    
    /**
     * 一般序列号生成器generator 实现
     */
    public static String SerialCodeModuleNormalSerialCodeGenerator = "serialCodeModuleNormalSerialCodeGenerator";
    
    /**
     * 日期中缀序列号生成器generator 实现
     */
    public static String SerialCodeModuleCalenderInfixSerialCodeGenerator = "serialCodeModuleCalenderInfixSerialCodeGenerator";
    
    /**
     * 日期中缀序时间戳后缀列号生成器generator 实现
     */
    public static String SerialCodeModuleCalendarInfixTimestampSufixSerialCodeGenerator = "serialCodeModuleCalendarInfixTimestampSufixSerialCodeGenerator";
    
    /**
     * 序列号缓存器Map 实现
     */
    public static String SerialCodeModuleSerialCodeCacheServiceMapImpl = "serialCodeModuleSerialCodeCacheServiceMapImpl";

    /**
     * 序列号缓存器redis 实现
     */
    public static String SerialCodeModuleSerialCodeCacheServiceRedisImpl = "serialCodeModuleSerialCodeCacheServiceRedisImpl";
    
    /**
     * 序列号内部操作 service
     */
    public static String SerialCodeModuleSerialCodeMaintainService = "serialcodeModuleSerialCodeMaintainService";
    
    /**
     * 序列号模块数据删除处理器
     */
    public static final String SerialCodeModuleDataDestroyer = "serialCodeModuleDataDestroyer";


    /**
     * 序列号redis 实现
     */
    public static String SerialCodeModuleRedisService = "serialCodeModuleRedisService";

    /**
     * 序列号类型redis 实现
     */
    public static String CodeTypeConfigModuleRedisService = "codeTypeConfigModuleRedisService";

    /**
     * 序列号数据redis 实现
     */
    public static String SerialCodeCollectionRedisService = "serialCodeCollectionRedisService";
}

