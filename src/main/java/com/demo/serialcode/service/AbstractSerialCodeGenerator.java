package com.demo.serialcode.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.demo.serialcode.repository.redis.SerialCodeRedisService;
import com.demo.serialcode.domain.CodeTypeConfig;
import com.demo.serialcode.domain.GenRule;
import com.demo.serialcode.domain.SerialCode;
import com.demo.serialcode.config.SerialCodeConfig;
import com.demo.serialcode.moduleinfo.SerailCodeModuleBeanNames;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * 序列号生成器generator 抽象类
 * @author hongzhaomin 2018年3月28日 下午13:53:25
 */
public abstract class AbstractSerialCodeGenerator {

    /**
     * 本生成器支持的生成规则编号。
     */
    protected GenRule genRule;

    /**
     * 缓存规则对应的生成器
     */
    public static final Map<GenRule, AbstractSerialCodeGenerator> generators = new HashMap<GenRule, AbstractSerialCodeGenerator>();

    /**
     * 序列码缓存服务
     */
    @Resource(name = SerailCodeModuleBeanNames.SerialCodeModuleRedisService)
    private SerialCodeRedisService serialCodeRedisService;

    /**
     * 序列号配置
     */
    @Autowired
    private SerialCodeConfig serialCodeConfig;


    /**
     * 将自己注册为指定生成规则的生成器。如果有重复，则抛出异常。
     *
     * 2018年4月3日 下午3:29:56 hongzhaomin添加此方法
     */
    protected void registMe() {
        AbstractSerialCodeGenerator existing = AbstractSerialCodeGenerator.generators.get(this.genRule);
        if (existing == null || !existing.genRule.equals(this.genRule)) {
            AbstractSerialCodeGenerator.generators.put(genRule, this);
        }
    }

    /**
     * 生成新序列号
     *
     * 2018年3月29日 下午2:05:19 hongzhaomin添加此方法
     * @param codeTypeConfig 序列号类型
     * @return 新序列号
     */
    public List<String> generateSerialCodes(CodeTypeConfig codeTypeConfig) {
        String codeType = codeTypeConfig.getCodeType();
        SerialCode curSerialCode = serialCodeRedisService.get(codeType);

        //生成批量新序列号
        List<String> serialCodes = this.genSerialCodes(codeTypeConfig, curSerialCode, serialCodeConfig.getCount());
        //新增或更新最新数据
        this.dummyUpdateCurrentSerialCode(curSerialCode, serialCodes, codeTypeConfig);
        return serialCodes;
    }

    /**
     * 根据规则批量生成新序列号
     *
     * 2018年4月3日 下午6:40:25 hongzhaomin添加此方法
     * @param codeTypeConfig 当前序列号类型对象
     * @param curSerialCode 当前序列号后缀
     * @param amount 生成的序列号数目
     * @return 新序列号集合
     */
    protected abstract List<String> genSerialCodes(CodeTypeConfig codeTypeConfig, SerialCode curSerialCode, int amount);

    /**
     * 将批量生成的最后一条数据更新到数据库中
     *
     * 2018年4月3日 下午6:34:06 hongzhaomin添加此方法
     * @param curSerialCode 当前序列号对象
     * @param serialCodes 需要更新的序列号
     * @param codeTypeConfig 序列号类型
     */
    protected void dummyUpdateCurrentSerialCode(SerialCode curSerialCode, List<String> serialCodes,
                                                CodeTypeConfig codeTypeConfig) {
        //取出批量序列号中最新的序列号
        String newSerialCode = serialCodes.get(serialCodes.size() - 1);
        //将最新的序列号拆开，取出当前值
        String[] newCurCodes = newSerialCode.split(serialCodeConfig.getJoiner());
        String newCurCode = newCurCodes[newCurCodes.length - 1];

        if (curSerialCode == null) {
            this.insert(newCurCode, codeTypeConfig.getRadix(), codeTypeConfig.getCodeType());
        } else {
            this.update(curSerialCode, newCurCode, codeTypeConfig.getRadix());
        }
    }

    /**
     * 新增序列号对象
     *
     * 2018年4月4日 上午10:46:05 hongzhaomin添加此方法
     * @param currentCode 新序列号后缀
     * @param codeType 新序列号类型
     */
    private void insert(String currentCode, int curCodeRadix, String codeType) {
        SerialCode serialCode = new SerialCode();
        serialCode.setId(UUID.randomUUID().toString());
        serialCode.setCurrentCode(currentCode);
        serialCode.setCurCodeRadix(curCodeRadix);
        serialCode.setCodeType(codeType);
        serialCode.setModifyDate(new Date());
        this.serialCodeRedisService.put(serialCode);
    }

    /**
     * 更新当前序列号
     *
     * 2018年4月3日 下午4:18:46 hongzhaomin添加此方法
     * @param serialCode 需要更新的序列号
     *
     */
    private void update(SerialCode serialCode, String newCurCode, int curCodeRadix) {
        SerialCode code = new SerialCode();
        code.setId(serialCode.getId());
        code.setCodeType(serialCode.getCodeType());
        code.setCurrentCode(newCurCode);
        code.setCurCodeRadix(curCodeRadix);
        this.serialCodeRedisService.post(code);
    }

}
