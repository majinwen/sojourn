package com.ziroom.zrp.service.trading.dto.finance;

/**
 * <p>修改应收账单请求</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年11月07日 14:22
 * @since 1.0
 */
public class ModifyReceiptBillRequest {

    private String billNum;

    private Integer documentAmount;

    private String endTime;

    private Integer isDel;

    public String getBillNum() {
        return billNum;
    }

    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    public Integer getDocumentAmount() {
        return documentAmount;
    }

    public void setDocumentAmount(Integer documentAmount) {
        this.documentAmount = documentAmount;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
