package com.ziroom.zrp.service.trading.dto.finance;

import java.io.Serializable;
import java.util.List;

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
 * @Date 2017年10月15日 15时40分
 * @Version 1.0
 * @Since 1.0
 */
public class ReceiptInfoResponse implements Serializable {


    /**
     * billNum : 10012016081200001
     * outContractCode : BJ101160629066
     * uid : 100219238483
     * costCode : khfz
     * receiptList : [{"receiptNum":"xxxxx","paymentTypeCode":"wx_ios_pay","receiptMothed":"ye","paySerialNum":"WXPC147800898167910149013801000","payTime":"2017-03-12 12:30:00","receiptStatus":1,"amount":"1000","payer":"小李","remark":"XXX","annexList":["http://10.16.5.168:9090/upload/2016-12-14/1481691296861_BJW16903331.jpg"]}]
     */

    private String billNum;
    private String outContractCode;
    private String uid;
    private String costCode;
    private List<ReceiptListBean> receiptList;

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCostCode() {
        return costCode;
    }

    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }

    public List<ReceiptListBean> getReceiptList() {
        return receiptList;
    }

    public void setReceiptList(List<ReceiptListBean> receiptList) {
        this.receiptList = receiptList;
    }

    public static class ReceiptListBean {
        /**
         * receiptNum : xxxxx
         * paymentTypeCode : wx_ios_pay
         * receiptMothed : ye
         * paySerialNum : WXPC147800898167910149013801000
         * payTime : 2017-03-12 12:30:00
         * receiptStatus : 1
         * amount : 1000
         * payer : 小李
         * remark : XXX
         * annexList : ["http://10.16.5.168:9090/upload/2016-12-14/1481691296861_BJW16903331.jpg"]
         */

        private String receiptNum;
        private String paymentTypeCode;
        private String receiptMothed;
        private String paySerialNum;
        private String payTime;
        private int receiptStatus;
        private String amount;
        private String payer;
        private String remark;
        private List<String> annexList;

        public String getReceiptNum() {
            return receiptNum;
        }

        public void setReceiptNum(String receiptNum) {
            this.receiptNum = receiptNum;
        }

        public String getPaymentTypeCode() {
            return paymentTypeCode;
        }

        public void setPaymentTypeCode(String paymentTypeCode) {
            this.paymentTypeCode = paymentTypeCode;
        }

        public String getReceiptMothed() {
            return receiptMothed;
        }

        public void setReceiptMothed(String receiptMothed) {
            this.receiptMothed = receiptMothed;
        }

        public String getPaySerialNum() {
            return paySerialNum;
        }

        public void setPaySerialNum(String paySerialNum) {
            this.paySerialNum = paySerialNum;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public int getReceiptStatus() {
            return receiptStatus;
        }

        public void setReceiptStatus(int receiptStatus) {
            this.receiptStatus = receiptStatus;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPayer() {
            return payer;
        }

        public void setPayer(String payer) {
            this.payer = payer;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public List<String> getAnnexList() {
            return annexList;
        }

        public void setAnnexList(List<String> annexList) {
            this.annexList = annexList;
        }
    }
}
