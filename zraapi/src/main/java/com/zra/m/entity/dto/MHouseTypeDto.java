package com.zra.m.entity.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Author: wangxm113
 * CreateDate: 2017/3/13.
 */
public class MHouseTypeDto {
    @ApiModelProperty(value = "户型id")
    private String hTBid;
    @ApiModelProperty(value = "户型名称")
    private String hTName;

    public String gethTBid() {
        return hTBid;
    }

    public void sethTBid(String hTBid) {
        this.hTBid = hTBid;
    }

    public String gethTName() {
        return hTName;
    }

    public void sethTName(String hTName) {
        this.hTName = hTName;
    }
}
