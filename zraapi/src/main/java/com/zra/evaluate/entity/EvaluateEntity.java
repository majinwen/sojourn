package com.zra.evaluate.entity;

import java.util.Date;

/**
 * Author: wangxm113
 * CreateDate: 2016/8/10.
 */
public class EvaluateEntity {
    private String id;
    private String createrId;
    private Date createTime;
    private String updaterId;
    private Date updateTime;
    private Byte isDel;
    private String requesterId;
    private Integer businessId;
    private String tokenId;
    private String questionType;
    private String beEvaluateId;

    public String getBeEvaluateId() {
        return beEvaluateId;
    }

    public void setBeEvaluateId(String beEvaluateId) {
        this.beEvaluateId = beEvaluateId;
    }

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }
}
