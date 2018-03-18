package com.ziroom.zrp.service.trading.dto.finance;

import lombok.Data;

import java.util.List;

/**
 * <p>
 *     财务wiki地址  http://wiki.ziroom.com/pages/viewpage.action?pageId=381091962
 *     查询收款单出参
 * </p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2018年02月1日 10:22
 * @since 1.0
 */

@Data
public class ReceiptQueryResponse {

    private Integer code;

    private String message;

    private List<ReceiptList> data;

    @Data
    public static class ReceiptList{

        private String outContractCode;

        private String parentContractCode;

        private List<Receipt> receiptList;

        @Data
        public static class Receipt{

            private String receiptNum;

            private String companyCode;

            private String payer;

            private String uid;

            private String payTime;

            private String paymentTypeCode;

            private String receiptMothed;

            private Integer totalAmount;

            private Integer confirmStatus;

            private Integer receiptStatus;

            private String beatBackReason;

            private String remark;

            private List<ReceiptDetail> receiptDetailList;

            private List<String> annexList;

            @Data
            public static class ReceiptDetail{

                private String billNum;

                private String costCode;

                private Integer amount;

            }
        }
    }
}
