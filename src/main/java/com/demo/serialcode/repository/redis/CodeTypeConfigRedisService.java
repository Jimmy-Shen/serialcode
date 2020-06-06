package com.demo.serialcode.repository.redis;

import cn.hutool.core.util.ReflectUtil;
import com.demo.serialcode.domain.CodeTypeConfig;
import com.demo.serialcode.moduleinfo.SerailCodeModuleBeanNames;
import com.demo.serialcode.util.CommonUtils;
import com.demo.serialcode.util.Constants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * CodeTypeConfig的redis缓存服务
 *
 * @author shenhongjun
 * @since 2020/5/28
 */
@Service(value = SerailCodeModuleBeanNames.CodeTypeConfigModuleRedisService)
public class CodeTypeConfigRedisService {
    /**
     * 序列号类型key
     */
    public static final String REDIS_KEY = Constants.CODE_TYPE_KEY;

    /**
     * 序列号类型子key格式
     */
    public static final String HASH_KEY = Constants.CODE_TYPE_KEY_COLLECTION;

    /**
     * redis
     */
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取序列号类型，根据序列号所属类型
     * @param codeType 序列号所属类型
     * @return 返回序列号类型
     */
    public CodeTypeConfig get(String codeType) {
        String hashKey = CommonUtils.formatter(HASH_KEY, codeType);
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(hashKey);
        if (null == entries) {
            return null;
        }

        CodeTypeConfig codeTypeConfig = new CodeTypeConfig();
        entries.entrySet().forEach(item -> {
            ReflectUtil.setFieldValue(codeTypeConfig, (String) item.getKey(), item.getValue());
        });

        return codeTypeConfig;
    }

    /**
     * 存储序列号类型
     * @param codeTypeConfig 序列号类型数据
     */
    public void put(CodeTypeConfig codeTypeConfig) {
        String hashKey = CommonUtils.formatter(HASH_KEY, codeTypeConfig.getCodeType());
        Map codeTypeConfigMap = new HashMap();
        Map<String, Field> map = ReflectUtil.getFieldMap(CodeTypeConfig.class);
        map.forEach((key, filed) -> {
            Object value = ReflectUtil.getFieldValue(codeTypeConfig, filed);
            codeTypeConfigMap.put(key, String.valueOf(value));
        });
        redisTemplate.opsForHash().putAll(hashKey, codeTypeConfigMap);
    }

    /**
     * 更新序列号类型
     * @param codeTypeConfig 序列号类型数据
     */
    public synchronized void post(CodeTypeConfig codeTypeConfig) {
        String hashKey = CommonUtils.formatter(HASH_KEY, codeTypeConfig.getCodeType());
        Map<String, Field> map = ReflectUtil.getFieldMap(CodeTypeConfig.class);
        // 事务
        redisTemplate.multi();
        map.forEach((key, filed) -> {
            Object value = ReflectUtil.getFieldValue(codeTypeConfig, filed);
            if (StringUtils.isEmpty(value)) {
                redisTemplate.opsForHash().put(hashKey, key, value);
            }
        });
        System.out.println(redisTemplate.exec());
    }
}
