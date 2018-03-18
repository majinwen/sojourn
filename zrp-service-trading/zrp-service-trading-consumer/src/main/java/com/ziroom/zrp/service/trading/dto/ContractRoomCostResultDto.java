package com.ziroom.zrp.service.trading.dto;

import java.math.BigDecimal;

/**
 * <p>企业合同房间明细</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年10月21日 14:50
 * @since 1.0
 */
public class ContractRoomCostResultDto {

    //房间id
    private String roomId;

    //房间/床位号
    private String roomNumber;

    //户型
    private String houseTypeName;


    //面积
    private String roomArea;

    //标准出房价
    private BigDecimal basePrice;

    //实际出房价
    private BigDecimal actualPrice;

    //标准服务费
    private BigDecimal mustCommission;

    //优惠后服务费
    private BigDecimal discountCommission;

    //押金
    private BigDecimal mustDeposit;

    public ContractRoomCostResultDto() {

    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getHouseTypeName() {
        return houseTypeName;
    }

    public void setHouseTypeName(String houseTypeName) {
        this.houseTypeName = houseTypeName;
    }

    public String getRoomArea() {
        return roomArea;
    }

    public void setRoomArea(String roomArea) {
        this.roomArea = roomArea;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public BigDecimal getMustCommission() {
        return mustCommission;
    }

    public void setMustCommission(BigDecimal mustCommission) {
        this.mustCommission = mustCommission;
    }

    public BigDecimal getDiscountCommission() {
        return discountCommission;
    }

    public void setDiscountCommission(BigDecimal discountCommission) {
        this.discountCommission = discountCommission;
    }

    public BigDecimal getMustDeposit() {
        return mustDeposit;
    }

    public void setMustDeposit(BigDecimal mustDeposit) {
        this.mustDeposit = mustDeposit;
    }
}
