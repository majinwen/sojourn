package com.zra.evaluate.entity.dto;

import com.zra.common.dto.appbase.AppBaseDto;

/**
 * Author: wangxm113
 * CreateDate: 2016/8/5.
 */
public class EvaluateHistoryDto extends AppBaseDto {
    private String requesterId;
    private String tokenId;

    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
