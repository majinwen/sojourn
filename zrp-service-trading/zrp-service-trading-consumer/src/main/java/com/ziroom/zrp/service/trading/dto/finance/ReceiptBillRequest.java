package com.ziroom.zrp.service.trading.dto.finance;

/**
 * <p>应收账单 请求参数</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月25日 11:23
 * @since 1.0
 */
public class ReceiptBillRequest {
    /**
     * 账单编号
     */
    private String billNum;
    /**
     * 出房合同号
     */
    private String outContractCode;
    /**
     * 账单类型
     */
    private String documentType;
    /**
     * 期数
     */
    private Integer periods;

    public String getBillNum() {
        return billNum;
    }

    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    public String getOutContractCode() {
        return outContractCode;
    }

    public void setOutContractCode(String outContractCode) {
        this.outContractCode = outContractCode;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Integer getPeriods() {
        return periods;
    }

    public void setPeriods(Integer periods) {
        this.periods = periods;
    }
}
