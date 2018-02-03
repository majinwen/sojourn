package com.ziroom.minsu.services.cms.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>
 * 对外的优惠券信息
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author afi
 * @since 1.0
 * @version 1.0
 */
public class ZiRoomCouponInfoVo extends BaseEntity {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -641213213121711236L;

    /** 业务线名称 */
    private String businessLineName = "民宿";

    /** 业务线名称 */
    private String businessLineType = "ms";

    /** 卡卷id */
    private String cardId;

    /** 金额 */
    private String price;

    /** 单位 可为空 */
    private String unit;

    /** 卡卷名称 */
    private String cardName;

    /** 描述 */
    private String description = "";

    /** 描述 */
    private String subDescription = "";

    /** 状态(1:可使用 2:过期，3全部) */
    private String status;

    /** 开始时间 */
    private String startDate;

    /** 开始时间 */
    private String endDate;

    public String getBusinessLineName() {
        return businessLineName;
    }

    public void setBusinessLineName(String businessLineName) {
        this.businessLineName = businessLineName;
    }

    public String getBusinessLineType() {
        return businessLineType;
    }

    public void setBusinessLineType(String businessLineType) {
        this.businessLineType = businessLineType;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }




    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public String getSubDescription() {
        return subDescription;
    }

    public void setSubDescription(String subDescription) {
        this.subDescription = subDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}