package com.zra.evaluate.entity.dto;

import com.zra.common.dto.appbase.AppBaseDto;

/**
 * Author: wangxm113
 * CreateDate: 2016/8/5.
 */
public class EvaluateDto extends AppBaseDto {
    private String beEvaluateId;
    private Integer businessBid;
    private String tokenId;
    private EvaluateOfMsgDto evaluateMsg;
    private String callback;

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getBeEvaluateId() {
        return beEvaluateId;
    }

    public void setBeEvaluateId(String beEvaluateId) {
        this.beEvaluateId = beEvaluateId;
    }

    public Integer getBusinessBid() {
        return businessBid;
    }

    public void setBusinessBid(Integer businessBid) {
        this.businessBid = businessBid;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public EvaluateOfMsgDto getEvaluateMsg() {
        return evaluateMsg;
    }

    public void setEvaluateMsg(EvaluateOfMsgDto evaluateMsg) {
        this.evaluateMsg = evaluateMsg;
    }

    @Override
    public String toString() {
        return "beEvaluateId:" + beEvaluateId + "; businessBid:" + businessBid + "; tokenId:" + tokenId + "; callback:" + callback + "; evaluateMsg:" + evaluateMsg.toString();
    }
    
}
