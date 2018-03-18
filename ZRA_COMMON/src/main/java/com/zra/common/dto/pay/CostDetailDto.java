package com.zra.common.dto.pay;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

/**
 * 费用明细
 * Created by cuigh6 on 2016/12/21.
 */
public class CostDetailDto{
    @ApiModelProperty(value = "费用项名称")
    private String key;
    @ApiModelProperty(value = "费用项金额")
    private String value;
    private BigDecimal amount;
    private BigDecimal actualAmount;

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
