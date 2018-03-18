package com.ziroom.zrp.service.trading.dto.surrender;

import java.io.Serializable;

/**
 * <p>费用结算页，zrams调用此服务计算时的入参</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author wangxm113
 * @Date 2017年10月11日 15时11分
 * @Version 1.0
 * @Since 1.0
 */
public class CostItemAccountDto implements Serializable {
    private String surParentId;//父解约协议id
    private String contractId;//合同id
    private Integer resNo;//违约责任方（0：公司；1：租客）
    private Integer anew;//是否刷新重新计算（0：不需要；1：需要）

    public String getSurParentId() {
        return surParentId;
    }

    public void setSurParentId(String surParentId) {
        this.surParentId = surParentId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Integer getResNo() {
        return resNo;
    }

    public void setResNo(Integer resNo) {
        this.resNo = resNo;
    }

    public Integer getAnew() {
        return anew;
    }

    public void setAnew(Integer anew) {
        this.anew = anew;
    }

    @Override
    public String toString() {
        return "CostItemAccountDto{" +
                "surParentId='" + surParentId + '\'' +
                ", contractId='" + contractId + '\'' +
                ", resNo=" + resNo +
                ", anew=" + anew +
                '}';
    }
}
