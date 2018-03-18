package com.zra.evaluate.logic;

import com.zra.business.entity.CustomerEntity;
import com.zra.business.logic.BusinessLogic;
import com.zra.business.service.CustomerService;
import com.zra.evaluate.entity.EvaluateEntity;
import com.zra.evaluate.entity.ZoEvaluationEntity;
import com.zra.evaluate.entity.dto.*;
import com.zra.evaluate.entity.mapper.ZoEvaluationItemEntity;
import com.zra.evaluate.service.EvaluateService;
import com.zra.evaluate.thirdinterface.EvaluatePlatform;
import com.zra.projectZO.logic.ProjectZOLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * Author: wangxm113
 * CreateDate: 2016/8/5.
 */
@Component
public class EvaluateLogic {

    @Autowired
    private BusinessLogic businessLogic;

    @Autowired
    private EvaluateService evaluateService;

    @Autowired
    private ProjectZOLogic projectZOLogic;

    @Autowired
    private CustomerService customerService;

    /**
     * 获取评价问题信息
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-05
     */
    public GetQuestionReturnDto getQuestion(GetQuestionDto dto) throws IOException {
        //根据管家fid获取他的系统号
        String sysCode = projectZOLogic.getSysCodeByFid(dto.getBeEvaluatorId());
        dto.setBeEvaluatorId(sysCode);
        return EvaluatePlatform.getQuestion(dto);
    }

    /**
     * 评价
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-05
     */
    public String evaluatePlatform(EvaluateDto dto) throws IOException {
        //added by wangxm113 2017-02-06，显示的给评价系统请评人手机号码和名字 start........
        CustomerEntity entity = customerService.getCuatomerByBuId(dto.getBusinessBid());
        dto.getEvaluateMsg().setRequesterName(entity.getName());
        dto.getEvaluateMsg().setRequesterPhone(entity.getPhone());
        //added by wangxm113 2017-02-06，显示的给评价系统请评人手机号码和名字 end........
        String result = EvaluatePlatform.evaluatePlatform(dto);
        EvaluateEntity e = new EvaluateEntity();
        e.setBusinessId(dto.getBusinessBid());
        e.setQuestionType("ZADKPJ");
        e.setTokenId(dto.getTokenId());
        e.setRequesterId(dto.getEvaluateMsg().getRequesterId());
        e.setBeEvaluateId(dto.getBeEvaluateId());
        evaluateService.insert(e);
        return result;
    }

    /**
     * 获取评价历史详情
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-05
     */
    public EvaluateHistoryReturnDto getEvaluateHistory(EvaluateHistoryDto dto) throws IOException {
        return EvaluatePlatform.getEvaluateHistory(dto);
    }

    /**
     * 判断一个约看商机是否评价过
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-24
     */
    public List<EvaluateDto> ifEvaluate(String businessIds) {
        return evaluateService.ifEvaluate(businessIds);
    }

    public Integer saveEvaluate(ZoEvaluationEntity zoEvaluationEntity) {
        return evaluateService.saveEvaluate(zoEvaluationEntity);
    }

    public Integer saveEvaluateItem(List<ZoEvaluationItemEntity> zoEvaluationItemEntity) {
        return evaluateService.saveEvaluateItem(zoEvaluationItemEntity);
    }

}
