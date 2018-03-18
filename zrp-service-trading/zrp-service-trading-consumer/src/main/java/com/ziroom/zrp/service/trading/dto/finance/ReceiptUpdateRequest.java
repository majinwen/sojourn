package com.ziroom.zrp.service.trading.dto.finance;

import lombok.Data;

import java.util.List;

/**
 * <p>
 *     财务wiki地址  http://wiki.ziroom.com/pages/viewpage.action?pageId=208175270
 *     修改应收账单请求参数
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
 * @Date Created in 2017年12月18日 10:20
 * @since 1.0
 */

@Data
public class ReceiptUpdateRequest {

    /**
     * 应收账单编号
     */
    private List<String> preBillNumList;
    /**
     * 合并支付列表
     */
    private List<Receipt> receiptList;

    /**
     *  收款单
     * @author jixd
     * @created 2017年11月22日 11:06:03
     * @param
     * @return
     */
    @Data
    public static class Receipt{
        /**
         * 收款单编号
         */
        private String billNum;
        /**
         * 支付总金额 分
         */
        private Integer amount;
        /**
         * 流水号 线上支付该字段为支付平台的流水号；线下支付该字段为对方账户
         */
        private String paySerialNum;

        /**
         * 支付时间  yyyy-MM-dd HH:mm:ss
         */
        private String payTime;

        /**
         * 付款人姓名
         */
        private String payer;

        /**
         * POS终端号
         */
        private String posId;

        /**
         * 参考号
         */
        private String referenceNum;

        /**
         * 支票号
         */
        private String checkNumber;

        /**
         * 审核状态	 3 未提交 0 未审核，1 审核通过，2 审核未通过，4 审核中
         */
        private Integer confirmStatus;

        /**
         * 收款状态 0已收款，1未收款（默认1），2打回
         */
        private Integer receiptStatus;

        /**
         * 附件列表
         */
        private List<String> annexList;

        /**
         * 备注
         */
        private String remark;


    }

}
