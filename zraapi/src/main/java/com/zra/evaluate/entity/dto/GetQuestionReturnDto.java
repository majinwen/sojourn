package com.zra.evaluate.entity.dto;

import java.util.List;

/**
 * Author: wangxm113
 * CreateDate: 2016/8/5.
 */
public class GetQuestionReturnDto {
    private String status;
    private String message;
    private String tokenId;
    private String zoIntroduce;
    private String zoPhotoUrl;
    private String zoName;
    private List<GetQuestionQuestionDto> questions;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public List<GetQuestionQuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<GetQuestionQuestionDto> questions) {
        this.questions = questions;
    }

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
}
