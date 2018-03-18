package com.zra.evaluate.dao;

import com.zra.evaluate.entity.EvaluateEntity;
import com.zra.evaluate.entity.ZoEvaluationEntity;
import com.zra.evaluate.entity.dto.EvaluateDto;
import com.zra.evaluate.entity.mapper.ZoEvaluationItemEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author: wangxm113
 * CreateDate: 2016/8/10.
 */
@Repository
public interface EvaluateMapper {
    int insert(EvaluateEntity entity);

    /**
     * 判断一个约看商机是否评价过
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-24
     */
    List<EvaluateDto> ifEvaluate(@Param("businessIds") String businessIds);

    Integer saveEvaluate(ZoEvaluationEntity zoEvaluationEntity);

    Integer saveEvaluateItem(List<ZoEvaluationItemEntity> zoEvaluationItemEntity);
}
