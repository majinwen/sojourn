package com.ziroom.zrp.service.houses.entity;

import com.ziroom.zrp.houses.entity.RoomItemsConfigEntity;

/**
 * <p>扩展类</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月30日 16:11
 * @since 1.0
 */
public class ExtRoomItemsConfigVo extends RoomItemsConfigEntity{
    private static final long serialVersionUID = 2548045476494441650L;

    /**
     * 物品名称
     */
    private String itemName;

    /**
     * 物品类型
     */
    private String itemType;

    private Integer itemFrom;
    /**
     * 物品价格
     */
    private Double itemPrice;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Integer getItemFrom() {
        return itemFrom;
    }

    public void setItemFrom(Integer itemFrom) {
        this.itemFrom = itemFrom;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }
}
