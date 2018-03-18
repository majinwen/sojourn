package com.ziroom.zrp.service.trading.entity;

import java.io.Serializable;

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
 * @Date 2017年10月12日 14时34分
 * @Version 1.0
 * @Since 1.0
 */
public class SurrenderCostSumHeadVo implements Serializable {
    private String contractId;
    private String roomId;
    private String roomNumber;
    private String mustSum;
    private String haveSum;
    private String refundSum;

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

    public String getMustSum() {
        return mustSum;
    }

    public void setMustSum(String mustSum) {
        this.mustSum = mustSum;
    }

    public String getHaveSum() {
        return haveSum;
    }

    public void setHaveSum(String haveSum) {
        this.haveSum = haveSum;
    }

    public String getRefundSum() {
        return refundSum;
    }

    public void setRefundSum(String refundSum) {
        this.refundSum = refundSum;
    }
}
