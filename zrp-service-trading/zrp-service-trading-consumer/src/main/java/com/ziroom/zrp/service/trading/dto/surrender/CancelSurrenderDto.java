package com.ziroom.zrp.service.trading.dto.surrender;

/**
 * <p>作废解约协议request dto</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author wangxm113
 * @Date 2017年10月24日 10时38分
 * @Version 1.0
 * @Since 1.0
 */
public class CancelSurrenderDto {
    private String surrenderId;
    private String contractId;
    private String surParentId;
    private String zoId;
    private String zoName;

    public String getZoId() {
        return zoId;
    }

    public void setZoId(String zoId) {
        this.zoId = zoId;
    }

    public String getZoName() {
        return zoName;
    }

    public void setZoName(String zoName) {
        this.zoName = zoName;
    }

    public String getSurrenderId() {
        return surrenderId;
    }

    public void setSurrenderId(String surrenderId) {
        this.surrenderId = surrenderId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getSurParentId() {
        return surParentId;
    }

    public void setSurParentId(String surParentId) {
        this.surParentId = surParentId;
    }
}
