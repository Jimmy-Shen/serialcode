package com.demo.serialcode.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.date.DateUtil;
import com.demo.serialcode.repository.redis.CodeTypeConfigRedisService;
import com.demo.serialcode.repository.redis.SerialCodesCollectionService;
import com.demo.serialcode.config.SerialCodeConfig;
import com.demo.serialcode.moduleinfo.SerailCodeModuleBeanNames;
import com.demo.serialcode.util.CommonUtils;
import com.demo.serialcode.util.Constants;
import com.demo.serialcode.domain.CodeTypeConfig;
import com.demo.serialcode.domain.GenRule;
import com.demo.serialcode.domain.Scope;
import com.demo.serialcode.util.RedisLock;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;


/**
 * 序列号缓存处理器 抽象类
 *
 * @author hongzhaomin 2018年3月28日 下午13:53:10
 */
public abstract class AbstractSerialCodeCacheService {

    /**
     * 序列号配置
     */
    @Autowired
    private SerialCodeConfig serialCodeConfig;

    /**
     * 序列码缓存服务
     */
    @Autowired
    private CodeTypeConfigRedisService codeTypeConfigRedisService;

    /**
     * redis
     */
    @Resource(name = SerailCodeModuleBeanNames.SerialCodeCollectionRedisService)
    private SerialCodesCollectionService serialCodesCollectionService;

    /**
     * redis 配置
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取序列号
     *
     * 2018年4月2日 下午2:37:34 hongzhaomin添加此方法
     * @param codeType 序列号类型
     * @return 序列号serialCode
     */
    public String fetchSerialCode(String codeType) {
        String serialCode = "";
        CodeTypeConfig codeTypeConfig = this.findCodeTypeConfig(codeType);
        if (codeTypeConfig == null) {
            try {
                throw new Exception("序列号类型未配置，请配置此序列号类型：codeType = " + codeType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String key = this.generateKey(codeTypeConfig);
        serialCode = this.get(key, codeTypeConfig.getGenRule(), codeTypeConfig.getInfix());
        while (StringUtils.isEmpty(serialCode)) {
            boolean codeLock = generateSerialCode(codeTypeConfig, key);
            if (!codeLock) {
                try {
                    System.err.println("线程阻塞，等待分布式锁-" + Thread.currentThread().getId());
                    Thread.sleep(serialCodeConfig.getWaitTime());
                } catch (InterruptedException e) {
                    try {
                        throw new Exception("获取序列号，线程等待分布式锁，出现异常:" + e);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            serialCode = this.get(key, codeTypeConfig.getGenRule(), codeTypeConfig.getInfix());
        }

        // 获取缓存中的序列号数量，当小于rate(百分比)的时候，批量生成；否则，直接获取
        Long remain = serialCodesCollectionService.decr(key);
        if (remain <= serialCodeConfig.getCount() * serialCodeConfig.getRate()) {
            System.err.println("序列号数量少于一定比例" + serialCodeConfig.getRate());
            generateSerialCode(codeTypeConfig, key);
        }

        return serialCode;
    }

    /**
     * 生成序列化，使用分布式锁支持多线程获取
     * @param codeTypeConfig 格式
     * @param key reids的key
     * @return 返回是否获取到分布式锁
     */
    private boolean generateSerialCode(CodeTypeConfig codeTypeConfig, String key) {
        // 分布式锁
        String lockKey = CommonUtils.formatter(Constants.SERIAL_CODE_KEY_LOCK, codeTypeConfig.getCodeType());
        RedisLock lock = RedisLock.newLock(stringRedisTemplate, lockKey, serialCodeConfig.getLockTime(), TimeUnit.MILLISECONDS);

        try {
            if (lock.tryLock()) {
                System.out.println("加锁成功");
                List<String> serialCodes = this.batchGenSerialCodes(codeTypeConfig);
                this.cache(key, serialCodes, codeTypeConfig.getInfix());
                return true;
            } else {
                System.out.println("加锁失败");
                return false;
            }
        } finally {
            lock.release();
            System.out.println("释放锁");
        }
    }

    /**
     * 根据序列号类型和企业ID拼装key
     *
     * 2018年3月29日 下午4:30:10 hongzhaomin添加此方法
     * @param codeTypeConfig 序列号类型对象
     * @return key
     */
    protected String generateKey(CodeTypeConfig codeTypeConfig) {
        StringBuffer key = new StringBuffer();
        Scope scope = codeTypeConfig.getScope();
        String newCalenderInfix = codeTypeConfig.getInfix();
        try {
            newCalenderInfix = DateUtil.format(new Date(), codeTypeConfig.getInfix());
        } catch (Exception ex) {
        }
        if (Scope.CLIENTSERIALCODE.equals(scope)) {
            key = key.append("SERIALCODE").append(Constants.SPLITOR).append(codeTypeConfig.getCodeType()).append(Constants.SPLITOR).append(newCalenderInfix);
        } else if (Scope.GLOBALSERIALCODE.equals(scope)) {
            key = key.append("SERIALCODE").append(Constants.SPLITOR).append(codeTypeConfig.getCodeType()).append(Constants.SPLITOR).append("SYSTEMADMINISTRATOR").append(Constants.SPLITOR).append(newCalenderInfix);
        } else {
            try {
                throw new Exception("请明确定义此类型序列号是全局序列号(GLOBALSERIALCODE)还是企业序列号(CLIENTSERIALCODE)！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return key.toString();
    }

    /**
     * 根据规则获取生成器
     *
     * 2018年4月3日 下午3:24:54 hongzhaomin添加此方法
     * @return 生成器对象
     */
    private AbstractSerialCodeGenerator fetchGenerator() {
        return fetchGenerator();
    }

    /**
     * 根据规则获取生成器
     *
     * 2018年4月3日 下午3:24:54 hongzhaomin添加此方法
     * @param genRule 序列号生成规则
     * @return 生成器对象
     */
    private AbstractSerialCodeGenerator fetchGenerator(GenRule genRule) {
        AbstractSerialCodeGenerator generator = AbstractSerialCodeGenerator.generators.get(genRule);
        if (generator == null) {
            try {
                throw new Exception("生成器生成规则错误！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return generator;
    }

    /**
     * 调用序列号生成器来批量生成序列号
     *
     * 2018年3月29日 下午4:30:28 hongzhaomin添加此方法
     * @param codeTypeConfig 序列号类型配置对象
     * @return 新生成的序列号集合
     */
    protected List<String> batchGenSerialCodes(CodeTypeConfig codeTypeConfig) {
        AbstractSerialCodeGenerator generator = this.fetchGenerator(GenRule.valueOf(codeTypeConfig.getGenRule()));
        List<String> serialCodes = generator.generateSerialCodes(codeTypeConfig);
        return serialCodes;
    }

    /**
     * 根据序列号类型查询序列号类型对象
     *
     * 2018年4月3日 上午9:40:48 hongzhaomin添加此方法
     * @param codeType 序列号类型
     * @return 序列号对象 codeTypeConfig
     */
    protected CodeTypeConfig findCodeTypeConfig(String codeType) {
        CodeTypeConfig codeTypeConfig = this.codeTypeConfigRedisService.get(codeType);
        return codeTypeConfig;
    }

    /**
     * 根据key从缓存中获取序列号
     *
     * 2018年3月29日 下午4:30:14 hongzhaomin添加此方法
     * @param key 根据序列号类型和企业ID拼装的key
     * @return 序列号
     */
    protected abstract String get(String key, String genRule, String infix);

    /**
     * 缓存服务增加新的缓存数据
     *
     * 2018年3月29日 下午4:30:49 hongzhaomin添加此方法
     * @param key 根据序列号类型和企业ID拼装的key
     * @param infix 格式
     * @param serialCodes 序列号集合
     */
    protected abstract void cache(String key, List<String> serialCodes, String infix);

}
