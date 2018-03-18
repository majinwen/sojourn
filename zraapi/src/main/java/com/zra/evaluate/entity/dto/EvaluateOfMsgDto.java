package com.zra.evaluate.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Author: wangxm113
 * CreateDate: 2016/8/5.
 */
public class EvaluateOfMsgDto {
    @ApiModelProperty(value = "请评对象id")
    private String requesterId;
    @ApiModelProperty(value = "请评对象类型")
    private String requesterType;
    @ApiModelProperty(value = "评价的订单号或合同号")
    private String orderCode;
    @ApiModelProperty(value = "拓展字段")
    private String ext;
    List<QuestionDto> questions;

    private String requesterPhone; //请评人手机号
    private String requesterName;  //请评人姓名
    
    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public String getRequesterType() {
        return requesterType;
    }

    public void setRequesterType(String requesterType) {
        this.requesterType = requesterType;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "requesterId:" + requesterId + "; requesterType:" + requesterType + "; orderCode:" + orderCode + "; ext:" + ext + "; questions:" + questions;
    }
    

    public String getRequesterPhone() {
        return requesterPhone;
    }

    public void setRequesterPhone(String requesterPhone) {
        this.requesterPhone = requesterPhone;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }
}
