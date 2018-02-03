package com.ziroom.minsu.services.order.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * 罚款单请求
 * @author jixd
 * @created 2017年05月15日 10:21:30
 * @param
 * @return
 */
public class PenaltyRequest extends PageRequest {
    private static final long serialVersionUID = -5516014134335258688L;
    /**
     * 罚款单
     */
    private String penaltySn;
    /**
     * 订单号
     */
    private String orderSn;
    /**
     * 罚款单状态
     */
    private Integer penaltyStatus;
    /**
     * 房东姓名
     */
    private String landlordName;
    /**
     * 房东电话
     */
    private String landlordTel;
    /**
     * 创建开始
     */
    private String createDateStart;
    /**
     * 创建结束
     */
    private String createDateEnd;
    /**
     * 修改开始
     */
    private String modifyDateStart;
    /**
     * 修改结束
     */
    private String modifyDateEnd;

    public String getPenaltySn() {
        return penaltySn;
    }

    public void setPenaltySn(String penaltySn) {
        this.penaltySn = penaltySn;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getPenaltyStatus() {
        return penaltyStatus;
    }

    public void setPenaltyStatus(Integer penaltyStatus) {
        this.penaltyStatus = penaltyStatus;
    }

    public String getLandlordName() {
        return landlordName;
    }

    public void setLandlordName(String landlordName) {
        this.landlordName = landlordName;
    }

    public String getCreateDateStart() {
        return createDateStart;
    }

    public void setCreateDateStart(String createDateStart) {
        this.createDateStart = createDateStart;
    }

    public String getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(String createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

    public String getModifyDateStart() {
        return modifyDateStart;
    }

    public void setModifyDateStart(String modifyDateStart) {
        this.modifyDateStart = modifyDateStart;
    }

    public String getModifyDateEnd() {
        return modifyDateEnd;
    }

    public void setModifyDateEnd(String modifyDateEnd) {
        this.modifyDateEnd = modifyDateEnd;
    }

    public String getLandlordTel() {
        return landlordTel;
    }

    public void setLandlordTel(String landlordTel) {
        this.landlordTel = landlordTel;
    }
}
