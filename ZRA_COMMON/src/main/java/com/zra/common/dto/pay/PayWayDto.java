package com.zra.common.dto.pay;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by cuigh6 on 2016/12/26.
 */
public class PayWayDto {
    @ApiModelProperty(value = "支付订单号")
    private String payOrderNum;

    @ApiModelProperty(value = " 支付方式")
    private List<String> payWayList;

    public String getPayOrderNum() {
        return payOrderNum;
    }

    public void setPayOrderNum(String payOrderNum) {
        this.payOrderNum = payOrderNum;
    }

    public List<String> getPayWayList() {
        return payWayList;
    }

    public void setPayWayList(List<String> payWayList) {
        this.payWayList = payWayList;
    }
}
