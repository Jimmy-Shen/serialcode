package com.demo.serialcode.service;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import cn.hutool.core.util.ReflectUtil;
import com.demo.serialcode.repository.redis.CodeTypeConfigRedisService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.demo.serialcode.domain.CodeTypeConfig;
import com.demo.serialcode.domain.GenRule;
import com.demo.serialcode.domain.Scope;
import com.demo.serialcode.moduleinfo.SerailCodeModuleBeanNames;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 序列号类型Dao 单元测试
 *
 * @author hongzhaomin 2018年3月28日 下午4:25:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CodeTypeConfigMybatisDaoTest {

    /**
     * redis服务
     */
    @Resource(name = SerailCodeModuleBeanNames.CodeTypeConfigModuleRedisService)
    private CodeTypeConfigRedisService codeTypeConfigRedisService;

    /**
     * 前置条件
     */
    @Before
    public void before() {
    }

    /**
     * Normal（client） 常规序列号的生成器，序列号类别为client
     * 生成normal规则
     */
    //    @Ignore
    @Test
    public void insertNormalTest() {
        CodeTypeConfig codeType = new CodeTypeConfig();
        codeType.setId(UUID.randomUUID().toString());
        codeType.setCodeType("DRV");
        codeType.setScope(Scope.CLIENTSERIALCODE);
        codeType.setInfix("SHJ");
        codeType.setGenRule(GenRule.Normal.toString());
        codeType.setNumLength(10);
        codeType.setRadix(36);
        codeType.setModifyDate(new Date(System.currentTimeMillis()));

//        this.codeTypeConfigMapper.insert(codeType);

        this.codeTypeConfigRedisService.put(codeType);
    }

    /**
     * CalendarInfix（client）指定日期格式作为自定义中缀的序列号生成器，序列号类别为client
     * 生成calendarInfix规则
     */
    //    @Ignore
    @Test
    public void insertCalendarInfixTest() {
        CodeTypeConfig codeType = new CodeTypeConfig();
        codeType.setId(UUID.randomUUID().toString());
        codeType.setCodeType("ORD");
        codeType.setScope(Scope.CLIENTSERIALCODE);
        codeType.setInfix("yyyyMMdd");
        codeType.setGenRule(GenRule.CalendarInfix.toString());
        codeType.setNumLength(10);
        codeType.setRadix(36);
        codeType.setModifyDate(new Date(System.currentTimeMillis()));

//        this.codeTypeConfigMapper.insert(codeType);

        this.codeTypeConfigRedisService.put(codeType);
    }

    /**
     * CalendarInfixTimestampSufix（client）日期格式作为自定义中缀，使用（生成序列号时的毫秒时间值 ×1000 + 自增3位数字），序列号类别为client
     * 生成calendarInfixTimestampSufix规则
     */
    //    @Ignore
    @Test
    public void insertCalendarInfixTimestampSufixTest() {
        CodeTypeConfig codeType = new CodeTypeConfig();
        codeType.setId(UUID.randomUUID().toString());
        codeType.setCodeType("PRO");
        codeType.setScope(Scope.CLIENTSERIALCODE);
        codeType.setInfix("yyyyMMdd");
        codeType.setGenRule(GenRule.CalendarInfixTimestampSufix.toString());
        codeType.setNumLength(10);
        codeType.setRadix(36);
        codeType.setModifyDate(new Date(System.currentTimeMillis()));

//        this.codeTypeConfigMapper.insert(codeType);

        this.codeTypeConfigRedisService.put(codeType);
    }


    /**
     * Normal（global）常规序列号的生成器，序列号类别为global
     * 生成normal规则
     */
    @Ignore
    @Test
    public void insertNormalGlobalTest() {
        CodeTypeConfig codeType = new CodeTypeConfig();
        codeType.setId(UUID.randomUUID().toString());
        codeType.setCodeType("DRV");
        codeType.setScope(Scope.GLOBALSERIALCODE);
        codeType.setInfix("SHJ");
        codeType.setGenRule(GenRule.Normal.toString());
        codeType.setNumLength(10);
        codeType.setRadix(36);
        codeType.setModifyDate(new Date(System.currentTimeMillis()));

//        this.codeTypeConfigMapper.insert(codeType);

        this.codeTypeConfigRedisService.put(codeType);
    }

    /**
     * CalendarInfix（global）指定日期格式作为自定义中缀的序列号生成器，序列号类别为global
     * 生成calendarInfix规则
     */
    @Ignore
    @Test
    public void insertCalendarInfixGlobalTest() {
        CodeTypeConfig codeType = new CodeTypeConfig();
        codeType.setId(UUID.randomUUID().toString());
        codeType.setCodeType("ORD");
        codeType.setScope(Scope.GLOBALSERIALCODE);
        codeType.setInfix("yyyyMMdd");
        codeType.setGenRule(GenRule.CalendarInfix.toString());
        codeType.setNumLength(10);
        codeType.setRadix(36);
        codeType.setModifyDate(new Date(System.currentTimeMillis()));

//        this.codeTypeConfigMapper.insert(codeType);

        this.codeTypeConfigRedisService.put(codeType);
    }

    /**
     * CalendarInfixTimestampSufix（global）日期格式作为自定义中缀，使用（生成序列号时的毫秒时间值 ×1000 + 自增3位数字），序列号类别为global
     * 生成calendarInfixTimestampSufix规则
     */
    @Ignore
    @Test
    public void insertCalendarInfixTimestampSufixGlobalTest() {
        CodeTypeConfig codeType = new CodeTypeConfig();
        codeType.setId(UUID.randomUUID().toString());
        codeType.setCodeType("PRO");
        codeType.setScope(Scope.GLOBALSERIALCODE);
        codeType.setInfix("yyyyMMdd");
        codeType.setGenRule(GenRule.CalendarInfixTimestampSufix.toString());
        codeType.setNumLength(10);
        codeType.setRadix(36);
        codeType.setModifyDate(new Date(System.currentTimeMillis()));

//        this.codeTypeConfigMapper.insert(codeType);

        this.codeTypeConfigRedisService.put(codeType);
    }

    @Ignore
    @Test
    public void updateTest() {
//        this.codeTypeConfigMapper.updateCodeTypeConfig("XVZ", "YYYY", "HZM", 10, 36);

        CodeTypeConfig codeType = new CodeTypeConfig();
        codeType.setCodeType("XVZ");
        codeType.setInfix("HZM");
        codeType.setGenRule(GenRule.Normal.toString());
        codeType.setNumLength(10);
        codeType.setRadix(36);
        codeType.setModifyDate(new Date(System.currentTimeMillis()));
        this.codeTypeConfigRedisService.post(codeType);
    }

    @Ignore
    @Test
    public void findTest() {
//        CodeTypeConfig codeType = this.codeTypeConfigMapper.findCodeTypeConfig("USR");
//        System.err.println(codeType.toString());
        CodeTypeConfig codeTypeConfig = this.codeTypeConfigRedisService.get("USR");
        System.err.println(codeTypeConfig.toString());
    }


    /**
     * redis
     */
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Ignore
    @Test
    public void redisTest() {

        CodeTypeConfig codeType = new CodeTypeConfig();
        codeType.setId(UUID.randomUUID().toString());
        codeType.setCodeType("XVZ");
        codeType.setScope(Scope.CLIENTSERIALCODE);
        codeType.setInfix("YYYY");
        codeType.setGenRule(GenRule.CalendarInfix.toString());
        codeType.setNumLength(10);
        codeType.setRadix(36);
        codeType.setModifyDate(new Date(System.currentTimeMillis()));

        Map codeTypeMap = new HashMap();
        Map<String, Field> fieldMap = ReflectUtil.getFieldMap(CodeTypeConfig.class);
        fieldMap.forEach((key, filed) -> {
            Object value = ReflectUtil.getFieldValue(codeType, filed);
            codeTypeMap.put(key, value);
        });
        redisTemplate.opsForHash().putAll("test", codeTypeMap);

        //获取值
        Map<Object, Object> entries = redisTemplate.opsForHash().entries("test");
        CodeTypeConfig codeType1 = new CodeTypeConfig();
        entries.entrySet().forEach(item -> {
            ReflectUtil.setFieldValue(codeType1, (String) item.getKey(), item.getValue());
        });

        System.out.println("===============获取值===============");
        System.out.println(codeType1);

        //修改值
        redisTemplate.opsForHash().put("test", "infix", "yydd");
        redisTemplate.opsForHash().put("test", "codeType", "SHJ");
        System.out.println("===============修改值===============");
        Map<Object, Object> entries1 = redisTemplate.opsForHash().entries("test");
        CodeTypeConfig codeType2 = new CodeTypeConfig();
        entries1.entrySet().forEach(item -> {
            ReflectUtil.setFieldValue(codeType2, (String) item.getKey(), item.getValue());
        });
        System.out.println(entries1);
    }
}
