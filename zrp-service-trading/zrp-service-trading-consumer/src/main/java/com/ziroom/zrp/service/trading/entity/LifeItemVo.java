package com.ziroom.zrp.service.trading.entity;

/**
 * <p>生活费用项</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月20日 18:28
 * @since 1.0
 */
public class LifeItemVo {
    /**
     * 合同id
     */
    private String contractId;
    /**
     * 房间id
     */
    private String roomFid;
    /**
     * 费用金额
     */
    private Double paymentAmount;
    /**
     * 费用名称
     */
    private String itemName;
    /**
     * 费用code
     */
    private String itemCode;
    /**
     * 费用id
     */
    private String expenseItemId;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getRoomFid() {
        return roomFid;
    }

    public void setRoomFid(String roomFid) {
        this.roomFid = roomFid;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getExpenseItemId() {
        return expenseItemId;
    }

    public void setExpenseItemId(String expenseItemId) {
        this.expenseItemId = expenseItemId;
    }
}
