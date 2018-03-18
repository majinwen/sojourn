package com.zra.evaluate.entity.dto;

import java.util.List;

/**
 * Author: wangxm113
 * CreateDate: 2016/8/5.
 */
public class EvaluateHistoryReturnDto {
    private String status;
    private String tokenId;
    private String message;
    private String zoIntroduce;
    private String zoPhotoUrl;
    private String zoName;
    private List<QuestionOfHistoryDto> questions;

    public String getZoIntroduce() {
        return zoIntroduce;
    }

    public void setZoIntroduce(String zoIntroduce) {
        this.zoIntroduce = zoIntroduce;
    }

    public String getZoPhotoUrl() {
        return zoPhotoUrl;
    }

    public void setZoPhotoUrl(String zoPhotoUrl) {
        this.zoPhotoUrl = zoPhotoUrl;
    }

    public String getZoName() {
        return zoName;
    }

    public void setZoName(String zoName) {
        this.zoName = zoName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<QuestionOfHistoryDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionOfHistoryDto> questions) {
        this.questions = questions;
    }
}
