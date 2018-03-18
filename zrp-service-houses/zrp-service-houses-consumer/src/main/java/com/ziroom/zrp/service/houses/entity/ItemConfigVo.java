package com.ziroom.zrp.service.houses.entity;

/**
 * <p>配置项</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月30日 14:41
 * @since 1.0
 */
public class ItemConfigVo {
    /**
     * 设置id
     */
    private String itemId;
    /**
     * 物品名称
     */
    private String itemName;
    /**
     * 物品类型
     */
    private Integer itemType;
    /**
     * 物品类型名称
     */
    private String itemTypeName;
    /**
     * 物品数量
     */
    private Integer num;
    /**
     * 单价
     */
    private Double price;


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

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
