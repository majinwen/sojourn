package com.zra.rentcontract.dto;

/**
 * <p>电子验签入参</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年11月05日 11:46
 * @since 1.0
 */

public class VerifySignatureDto {

    //合同id
    private String contractId;

    //密文
    private String signMessage;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getSignMessage() {
        return signMessage;
    }

    public void setSignMessage(String signMessage) {
        this.signMessage = signMessage;
    }
}
