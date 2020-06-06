package com.demo.serialcode.service;

import java.util.Date;
import java.util.UUID;

import com.demo.serialcode.domain.CodeTypeConfig;
import com.demo.serialcode.domain.GenRule;
import com.demo.serialcode.moduleinfo.SerailCodeModuleBeanNames;
import org.springframework.stereotype.Service;

import com.demo.serialcode.domain.Scope;

/**
 * 序列号类型配置 内部服务service 实现
 *
 * @author hongzhaomin
 * 2018年4月4日 下午2:51:31
 */
@Service(SerailCodeModuleBeanNames.SerialCodeModuleCodeTypeConfigService)
public abstract class CodeTypeConfigServiceImpl implements CodeTypeConfigService {


    @Override
    public void insertCodeTypeConfig(String codeType, Scope scope, GenRule genRule, String infix, int numLength, int radix) {
        CodeTypeConfig codeTypeConfig = new CodeTypeConfig();
        codeTypeConfig.setId(UUID.randomUUID().toString());
        codeTypeConfig.setCodeType(codeType);
        codeTypeConfig.setScope(scope);
        codeTypeConfig.setGenRule(genRule.toString());
        codeTypeConfig.setInfix(infix);
        codeTypeConfig.setNumLength(numLength);
        codeTypeConfig.setRadix(radix);
        codeTypeConfig.setModifyDate(new Date());
//        codeTypeConfigMapper.insert(codeTypeConfig);
    }

    @Override
    public void updateCodeTypeConfig(String codeType, String genRule, String infix, int numLength, int radix) {
//        codeTypeConfigMapper.updateCodeTypeConfig(codeType, genRule, infix, numLength, radix);
    }

    @Override
    public CodeTypeConfig findCodeTypeConfig(String codeType) {
//        return codeTypeConfigMapper.findCodeTypeConfig(codeType);
        return null;
    }

}
