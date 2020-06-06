package com.demo.serialcode.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * redis配置
 *
 * @author shenhongjun
 * @since 2020/5/29
 */
@Configuration
public class RedisTemplateConfig {

    /**
     * redis
     */
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 事务开关
     */
    @Value("${spring.redis.transaction.enable:true}")
    private Boolean enable;

    /**
     * 初始化
     */
    @PostConstruct
    public void postConstruct() {
        // 开启事务
        this.redisTemplate.setEnableTransactionSupport(this.enable);

        // 序列器
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        this.redisTemplate.setKeySerializer(redisSerializer);
        this.redisTemplate.setHashKeySerializer(redisSerializer);

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        this.redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        this.redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        this.redisTemplate.afterPropertiesSet();
    }
}
