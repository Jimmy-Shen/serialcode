package com.demo.serialcode.service.generator;

import java.util.ArrayList;
import java.util.List;

import com.demo.serialcode.service.AbstractSerialCodeGenerator;
import com.demo.serialcode.config.SerialCodeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.serialcode.domain.CodeTypeConfig;
import com.demo.serialcode.domain.GenRule;
import com.demo.serialcode.domain.SerialCode;
import com.demo.serialcode.moduleinfo.SerailCodeModuleBeanNames;
import org.springframework.util.StringUtils;

/**
 * 一般序列号生成器 （包括无中缀和自定义中缀）
 *
 * @author hongzhaomin
 * 2018年4月3日 下午2:14:33
 */
@Service(SerailCodeModuleBeanNames.SerialCodeModuleNormalSerialCodeGenerator)
public class NormalSerialCodeGenerator extends AbstractSerialCodeGenerator {

    @Autowired
    private SerialCodeConfig serialCodeConfig;

    public NormalSerialCodeGenerator() {
        this.genRule = GenRule.Normal;
        this.registMe();
    }

    @Override
    protected List<String> genSerialCodes(CodeTypeConfig codeTypeConfig, SerialCode curSerialCode, int amount) {
        List<String> infixAndCurCode = this.genInfixAndCurCode(codeTypeConfig, curSerialCode);
        String infix = infixAndCurCode.get(0);
        String curCode = infixAndCurCode.get(1);
        List<String> serialCodes = new ArrayList<String>();
        for (int i = 0; i < amount; i++) {
            String singleSerialCode = this.genSingleSerialCode(codeTypeConfig, curCode, infix);
            serialCodes.add(singleSerialCode);
            //将所生成的序列号拆开，取出当前值，再赋值给curCode
            String[] newCurCodes = singleSerialCode.split(serialCodeConfig.getJoiner());
            curCode = newCurCodes[newCurCodes.length - 1];
        }
        return serialCodes;
    }

    /**
     * 根据本规则的定义，生成中缀字符串与当前序列号后缀字符串的集合
     *
     * 2018年4月12日 下午1:27:24 hongzhaomin添加此方法
     * @param codeTypeConfig 序列号类型配置对象
     * @param curSerialCode 当前序列号对象
     * @return 中缀和当前序列号后缀的集合
     */
    protected List<String> genInfixAndCurCode(CodeTypeConfig codeTypeConfig, SerialCode curSerialCode) {
        List<String> infixAndCurCode = new ArrayList<String>();
        String normalInfix = codeTypeConfig.getInfix();
        String curCode = "";
        //判断当前序列号对象是否存在
        if (curSerialCode != null) {
            curCode = this.changeRadix(curSerialCode.getCurrentCode(), curSerialCode.getCurCodeRadix(),
                    codeTypeConfig.getRadix());
        }
        infixAndCurCode.add(normalInfix);
        infixAndCurCode.add(curCode);
        return infixAndCurCode;
    }

    /**
     * 根据当前值 (若当前值为空则初始化,若序列号过期则初始化) 计算递增1后的新值，并根据位长在前面补充0.
     *
     * 2018年4月12日 上午11:27:30 hongzhaomin添加此方法
     * @param currentVaLue 当前值
     * @param radix 序列号类型配置对象中 指定的进制数
     * @param num 序列号类型配置对象中 指定的位数
     * @return 下一个序列号后缀
     */
    protected String fetchIncreaseCode(String currentVaLue, int radix, int num) {
        if (StringUtils.isEmpty(currentVaLue)) {
            currentVaLue = "0";
        }
        // 将radix进制数字符串转换为10进制数并自增1
        Long nextCodeLong = Long.valueOf(currentVaLue, radix) + 1;
        // 10进制数转换为radix进制数字符串
        String nextCode = Long.toString(nextCodeLong, radix);
        // 位数不足用“0”填充
        nextCode = String.format("%" + num + "s", nextCode).replace(" ", "0");
        return nextCode;
    }

    /**
     * 生成单条序列号
     *
     * 2018年4月12日 上午9:25:55 hongzhaomin添加此方法
     * @param codeTypeConfig 序列号类型配置对象
     * @param curSuffixCode 当前序列号后缀
     * @param infix 序列号对象更新时间
     * @return 单条序列号
     */
    private String genSingleSerialCode(CodeTypeConfig codeTypeConfig, String curSuffixCode, String infix) {
        curSuffixCode = this.fetchIncreaseCode(curSuffixCode, codeTypeConfig.getRadix(), codeTypeConfig.getNumLength());
        StringBuffer serialCode = new StringBuffer();
        serialCode.append(codeTypeConfig.getCodeType()).append(serialCodeConfig.getJoiner());
        if (!StringUtils.isEmpty(infix)) {
            serialCode.append(infix).append(serialCodeConfig.getJoiner());
        }
        serialCode.append(curSuffixCode);

        return serialCode.toString();
    }

    /**
     * 进制转换（将当前进制字符串 转换为新进制字符串）,若其进制数与配置中进制数不一致，则需转换
     *
     * 2018年4月8日 下午2:38:40 hongzhaomin添加此方法
     * @param curCode 当前序列号后缀 当前进制 字符串
     * @param curRadix 当前后缀进制数
     * @param newRadix 需转换的新进制数
     * @return 当前序列号后缀新进制的字符串
     */
    protected String changeRadix(String curCode, int curRadix, int newRadix) {
        if (curRadix != newRadix) {
            curCode = Long.toString(Long.valueOf(curCode, curRadix), newRadix);
        }
        return curCode;
    }

}
