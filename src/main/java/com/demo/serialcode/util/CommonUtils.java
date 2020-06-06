package com.demo.serialcode.util;

import cn.hutool.core.date.CalendarUtil;
import cn.hutool.core.date.DateUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * redis帮助类
 *
 * @author shenhongjun
 * @since 2020/5/28
 */
public class CommonUtils {

    /**
     * 格式化redis的key
     * @param key redis的key
     * @param values redis的value
     * @return 返回key
     */
    public static String formatter(String key, String... values) {
        String result = key;

        int length = values.length;
        for (int i = 0; i < length; i++) {
            result = result.replace("{" + i + "}", values[i]);
        }

        return result;
    }

    /**
     * 过期日期
     * @param infix 中缀格式
     * @return 返回过期的日期
     */
    public static Date convertExpireDate(String infix) {
        String expireDate = null;
        Calendar current = CalendarUtil.calendar();
        switch (infix.toLowerCase()) {
            // 下一分钟失效
            case "yyyymmddhhmm":
                current.add(Calendar.MINUTE, 1);
                expireDate = DateUtil.format(current.getTime(), "yyyy-MM-dd HH:mm");
                break;
            // 下一小时失效
            case "yyyymmddhh":
                current.add(Calendar.HOUR, 1);
                expireDate = DateUtil.format(current.getTime(), "yyyy-MM-dd HH");
                break;
            // 下一月失效
            case "yyyymm":
                current.add(Calendar.MONTH, 1);
                expireDate = DateUtil.format(current.getTime(), "yyyy-MM");
                break;
            // 下一年失效
            case "yyyy":
                current.add(Calendar.YEAR, 1);
                expireDate = DateUtil.format(current.getTime(), "yyyy");
                break;
            // 下一天失效
            default:
                current.add(Calendar.DAY_OF_YEAR, 1);
                expireDate = DateUtil.format(current.getTime(), "yyyy-MM-dd");
                break;
        }
        return DateUtil.parseDate(expireDate);
    }
}
