package com.ziroom.zrp.service.houses.entity;

import com.ziroom.zrp.houses.entity.HouseItemsConfigEntity;

/**
 * <p>房型物品扩展表</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年11月28日 20:25
 * @since 1.0
 */
public class ExtHouseItemsConfigVo extends HouseItemsConfigEntity{
    private static final long serialVersionUID = 3582826016831663950L;
    /**
     * 物品价格
     */
    private Double itemPrice;

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }
}
