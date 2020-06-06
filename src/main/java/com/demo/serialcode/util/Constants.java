package com.demo.serialcode.util;

/**
 * Constant
 *
 * @author shenhongjun
 * @since 2020/4/22
 */
public interface Constants {

    /**
     * 分隔符
     */
    public static final String SPLITOR = ":";

    /**
     * 序列号key
     *
     * 格式：SER
     * 例如：SER
     */
    public static final String SERIAL_CODE_KEY = "SER";

    /**
     * 序列号子key格式
     *
     * 格式：SER:{0}
     * 例如：SER:USR
     */
    public static final String SERIAL_CODE_KEY_COLLECTION = "SER:{0}";

    /**
     * 序列号key格式 锁
     *
     * 格式：SER:{0}:Lock
     * 例如：SER:USR:Lock
     */
    public static final String SERIAL_CODE_KEY_LOCK = "SER:{0}:Lock";

    /**
     * 序列号类型key
     *
     * 格式：TYP
     * 例如：TYP
     */
    public static final String CODE_TYPE_KEY = "TYP";

    /**
     * 序列号类型子key格式
     *
     * 格式：TYP:{0}
     * 例如：TYP:USR
     */
    public static final String CODE_TYPE_KEY_COLLECTION = "TYP:{0}";

    /**
     * 序列号数据key格式
     *
     * 格式：SER:{0}:{1}:{2}:{3}
     * 例如：SER:ZT3000:USR:36:20200529
     */
    public static final String SERIAL_CODE_DATA_COLLECTION = "SER:{0}:{1}:{2}:{3}";

    /**
     * 序列号数量key格式
     *
     * 格式：SER:COUNT:{0}:{1}:{2}:{3}
     * 例如：SER:COUNT:ZT3000:USR:36:20200529
     */
    public static final String SERIAL_CODE_DATA_COUNT = "SER:COUNT:{0}:{1}:{2}:{3}";
}
