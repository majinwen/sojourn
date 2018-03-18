package com.zra.evaluate.entity.dto;

import com.zra.common.dto.appbase.AppBaseDto;

/**
 * Author: wangxm113
 * CreateDate: 2016/8/5.
 */
public class GetQuestionDto extends AppBaseDto {
    private String channelCode;
    private String beEvaluatorId;
    private String beEvaluatorType;
    private String questionType;

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getBeEvaluatorId() {
        return beEvaluatorId;
    }

    public void setBeEvaluatorId(String beEvaluatorId) {
        this.beEvaluatorId = beEvaluatorId;
    }

    public String getBeEvaluatorType() {
        return beEvaluatorType;
    }

    public void setBeEvaluatorType(String beEvaluatorType) {
        this.beEvaluatorType = beEvaluatorType;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    @Override
    public String toString() {
        return "channelCode" + channelCode + "beEvaluatorId" + beEvaluatorId + "beEvaluatorType" + beEvaluatorType + "questionType" + questionType;
    }
}
