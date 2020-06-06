package com.demo.serialcode.repository.redis;

import cn.hutool.core.util.ReflectUtil;
import com.demo.serialcode.domain.SerialCode;
import com.demo.serialcode.moduleinfo.SerailCodeModuleBeanNames;
import com.demo.serialcode.util.Constants;
import com.demo.serialcode.util.CommonUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * SerialCode的redis缓存服务
 *
 * @author shenhongjun
 * @since 2020/5/28
 */
@Service(value = SerailCodeModuleBeanNames.SerialCodeModuleRedisService)
public class SerialCodeRedisService {

    /**
     * 序列号key
     */
    public static final String REDIS_KEY = Constants.SERIAL_CODE_KEY;

    /**
     * 序列号子key格式
     */
    public static final String HASH_KEY = Constants.SERIAL_CODE_KEY_COLLECTION;

    /**
     * 排除的key
     */
    public static final String EXCLUSION_KEY = "serialVersionUID";

    /**
     * redis
     */
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 获取序列号，根据序列号所属类型和企业ID
     * @param codeType 序列号所属类型
     * @return 返回序列号公共存储类
     */
    public SerialCode get(String codeType) {
        String hashKey = CommonUtils.formatter(HASH_KEY, codeType);
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(hashKey);
        if (CollectionUtils.isEmpty(entries)) {
            return null;
        }

        SerialCode serialCode = new SerialCode();
        entries.entrySet().forEach(item -> {
            if (!EXCLUSION_KEY.equals(item.getKey())) {
                ReflectUtil.setFieldValue(serialCode, (String) item.getKey(), item.getValue());
            }
        });

        return serialCode;
    }

    /**
     * 存储序列号
     * @param serialCode 序列号数据
     */
    public void put(SerialCode serialCode) {
        String hashKey = CommonUtils.formatter(HASH_KEY, serialCode.getCodeType());
        Map serialCodeMap = new HashMap();
        Map<String, Field> map = ReflectUtil.getFieldMap(SerialCode.class);
        map.forEach((key, filed) -> {
            Object value = ReflectUtil.getFieldValue(serialCode, filed);
            serialCodeMap.put(key, String.valueOf(value));
        });
        redisTemplate.opsForHash().putAll(hashKey, serialCodeMap);
    }

    /**
     * 更新序列号
     * @param serialCode 序列号数据
     */
    public synchronized void post(SerialCode serialCode) {
        // 事务
        redisTemplate.multi();
        String hashKey = CommonUtils.formatter(HASH_KEY, serialCode.getCodeType());
        redisTemplate.opsForHash().put(hashKey, "currentCode", serialCode.getCurrentCode());
        redisTemplate.opsForHash().put(hashKey, "curCodeRadix", String.valueOf(serialCode.getCurCodeRadix()));
        redisTemplate.opsForHash().put(hashKey, "modifyDate", String.valueOf(new Date()));
        System.out.println(redisTemplate.exec());
    }
}
