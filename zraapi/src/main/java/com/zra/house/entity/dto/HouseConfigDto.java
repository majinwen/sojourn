package com.zra.house.entity.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>配置</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/8/4 14:21
 * @since 1.0
 */
public class HouseConfigDto {
    private String itemId;
    
    @ApiModelProperty(value = "物品icon")
    private String imgUrl;

    @ApiModelProperty(value = "物品名称")
    private String itemName;

    @ApiModelProperty(value = "物品排序")
    private Integer itemOrder;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(Integer itemOrder) {
        this.itemOrder = itemOrder;
    }
}
