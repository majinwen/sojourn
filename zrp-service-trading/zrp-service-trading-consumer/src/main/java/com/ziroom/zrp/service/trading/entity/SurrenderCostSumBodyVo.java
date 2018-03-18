package com.ziroom.zrp.service.trading.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author wangxm113
 * @Date 2017年10月12日 14时10分
 * @Version 1.0
 * @Since 1.0
 */
public class SurrenderCostSumBodyVo extends BaseEntity implements Serializable {
    private String contractId;
    private String surrenderId;
    private String conRentCode;
    private String surCostId;
    private String surCostItemId;
    private String roomId;
    private String roomNumber;
    private String expenseItemId; //费用id
    private String expenseItemName; //费用名称
    private BigDecimal originalNum; //应缴金额
    private BigDecimal actualNum; //已缴金额
    private BigDecimal refundNum; //应退金额
    private int order;//顺序

    public SurrenderCostSumBodyVo() {
    }

    public SurrenderCostSumBodyVo(String surrenderId, String surCostId, String contractId, String conRentCode, String roomId, String roomNumber,
                                  String surCostItemId, String expenseItemId, String expenseItemName, BigDecimal originalNum,
                                  BigDecimal actualNum, BigDecimal refundNum, int order) {
        this.surrenderId = surrenderId;
        this.surCostId = surCostId;
        this.contractId = contractId;
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.surCostItemId = surCostItemId;
        this.expenseItemId = expenseItemId;
        this.expenseItemName = expenseItemName;
        this.originalNum = originalNum;
        this.actualNum = actualNum;
        this.refundNum = refundNum;
        this.order = order;
        this.conRentCode = conRentCode;
    }

    public String getSurrenderId() {
        return surrenderId;
    }

    public void setSurrenderId(String surrenderId) {
        this.surrenderId = surrenderId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getSurCostId() {
        return surCostId;
    }

    public void setSurCostId(String surCostId) {
        this.surCostId = surCostId;
    }

    public String getSurCostItemId() {
        return surCostItemId;
    }

    public void setSurCostItemId(String surCostItemId) {
        this.surCostItemId = surCostItemId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
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

    public String getExpenseItemId() {
        return expenseItemId;
    }

    public void setExpenseItemId(String expenseItemId) {
        this.expenseItemId = expenseItemId;
    }

    public String getExpenseItemName() {
        return expenseItemName;
    }

    public void setExpenseItemName(String expenseItemName) {
        this.expenseItemName = expenseItemName;
    }

    public BigDecimal getOriginalNum() {
        return originalNum;
    }

    public void setOriginalNum(BigDecimal originalNum) {
        this.originalNum = originalNum;
    }

    public BigDecimal getActualNum() {
        return actualNum;
    }

    public void setActualNum(BigDecimal actualNum) {
        this.actualNum = actualNum;
    }

    public BigDecimal getRefundNum() {
        return refundNum;
    }

    public void setRefundNum(BigDecimal refundNum) {
        this.refundNum = refundNum;
    }

    public String getConRentCode() {
        return conRentCode;
    }

    public void setConRentCode(String conRentCode) {
        this.conRentCode = conRentCode;
    }
}
