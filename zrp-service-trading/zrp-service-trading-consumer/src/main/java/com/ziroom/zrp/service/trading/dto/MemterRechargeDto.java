package com.ziroom.zrp.service.trading.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>智能电表充值信息</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @Date Created in 2018年01月15日 20:22
 * @since 1.0
 */
@ApiModel(value = "智能电表充值信息")
public class MemterRechargeDto implements Serializable{

    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private  String  price;

    /**
     * 充值金额 : 分
     */
    @ApiModelProperty(value = "充值金额 : 分")
    private  Integer  rechargeMoney;

    /**
     * 合同id
     */
    @ApiModelProperty(value = "合同id")
    private  String  contractId;

    /**
     * 应缴示数
     */
    @ApiModelProperty(value = "应缴示数")
    private String  rechargeReadings;


    public String getRechargeReadings() {
        return rechargeReadings;
    }

    public void setRechargeReadings(String rechargeReadings) {
        this.rechargeReadings = rechargeReadings;
    }

    public Integer getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(Integer rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
