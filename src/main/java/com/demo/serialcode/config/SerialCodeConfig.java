package com.demo.serialcode.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 序列号数量配置类
 *
 * @author shenhongjun
 * @since 2020/6/1
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis.serialcode")
public class SerialCodeConfig {
    /**
     * 数量
     */
    int count = 1000;

    /**
     * 比例
     */
    double rate = 0.3;

    /**
     * 锁的时间(单位：毫秒)
     */
    int lockTime = 1000;

    /**
     * 链接符
     */
    String joiner = "-";

    /**
     * 线程等待时间(单位：毫秒)
     */
    int waitTime = 300;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getLockTime() {
        return lockTime;
    }

    public void setLockTime(int lockTime) {
        this.lockTime = lockTime;
    }

    public String getJoiner() {
        return joiner;
    }

    public void setJoiner(String joiner) {
        this.joiner = joiner;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }
}
