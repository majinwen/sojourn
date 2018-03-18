package com.zra.evaluate.service;

import com.zra.evaluate.dao.EvaluateMapper;
import com.zra.evaluate.entity.EvaluateEntity;
import com.zra.evaluate.entity.ZoEvaluationEntity;
import com.zra.evaluate.entity.dto.EvaluateDto;
import com.zra.evaluate.entity.mapper.ZoEvaluationItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: wangxm113
 * CreateDate: 2016/8/10.
 */
@Service
public class EvaluateService {
    @Autowired
    private EvaluateMapper evaluateMapper;

    public int insert(EvaluateEntity e) {
        return evaluateMapper.insert(e);
    }

    /**
     * 判断一个约看商机是否评价过
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-24
     */
    public List<EvaluateDto> ifEvaluate(String businessIds) {
        return evaluateMapper.ifEvaluate(businessIds);
    }

    public Integer saveEvaluate(ZoEvaluationEntity zoEvaluationEntity) {
        return evaluateMapper.saveEvaluate(zoEvaluationEntity);
    }

    public Integer saveEvaluateItem(List<ZoEvaluationItemEntity> zoEvaluationItemEntity) {
        return evaluateMapper.saveEvaluateItem(zoEvaluationItemEntity);
    }

}
