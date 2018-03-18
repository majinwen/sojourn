package com.ziroom.zrp.service.houses.entity;

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
public class MemterRechargeVo implements Serializable{


    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private  String  address;

    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private  String  price;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
