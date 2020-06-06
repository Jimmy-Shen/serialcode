package com.demo.serialcode.repository.redis;

import com.demo.serialcode.moduleinfo.SerailCodeModuleBeanNames;
import com.demo.serialcode.util.CommonUtils;
import com.demo.serialcode.util.Constants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * SerialCode的所有数据存放在redis缓存服务
 *
 * @author shenhongjun
 * @since 2020/5/29
 */
@Service(value = SerailCodeModuleBeanNames.SerialCodeCollectionRedisService)
public class SerialCodesCollectionService {
    /**
     * 序列号子key格式
     */
    public static final String HASH_KEY = Constants.SERIAL_CODE_DATA_COLLECTION;
    /**
     * 序列号数量key格式
     */
    public static final String COUNT_KEY = Constants.SERIAL_CODE_DATA_COUNT;

    /**
     * redis
     */
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 获取序列号
     * @param key 缓存KEY
     * @return 返回序列号
     */
    public String get(String key) {
        String serialCode = redisTemplate.opsForList().rightPop(key);
        return serialCode;
    }

    /**
     * 存储序列号
     * @param key 缓存KEY
     * @param serialCodes 序列号集合
     */
    public void put(String key, String infix, List<String> serialCodes) {
        // 事务
        redisTemplate.multi();
        redisTemplate.opsForList().leftPushAll(key, serialCodes);
        Date expire = CommonUtils.convertExpireDate(infix);
        redisTemplate.expireAt(key, expire);
        redisTemplate.opsForValue().increment(key + "-count", serialCodes.size());
        redisTemplate.expireAt(key + "-count", expire);
        System.out.println(redisTemplate.exec());

        // 查重
//        serialCodes.forEach(item -> {
//            redisTemplate.opsForSet().add(key + "-set", item);
//        });
    }

    public long decr(String key) {
        return redisTemplate.opsForValue().decrement(key + "-count");
    }

    /**
     * 清除存储序列号
     * @param key 缓存KEY
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
