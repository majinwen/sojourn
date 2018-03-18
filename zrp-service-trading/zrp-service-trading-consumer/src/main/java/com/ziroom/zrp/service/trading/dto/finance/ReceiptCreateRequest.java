package com.ziroom.zrp.service.trading.dto.finance;

import lombok.Data;

import java.util.List;

/**
 * <p>
 *     财务wiki地址  http://wiki.ziroom.com/pages/viewpage.action?pageId=172130396
 *     生成应收账单请求参数
 * </p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年11月22日 10:20
 * @since 1.0
 */

@Data
public class ReceiptCreateRequest {
    /**
     * 系统标示
     */
    private String sysCode;
    /**
     * 0、未关联；1、出房关联； 2、收房关联；3、业务订单号关联；4、预定单号关联
     */
    private Integer isContract;
    /**
     * 是否校验合同（0：不校验；1：校验）
     */
    private Integer isCheckContract;
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
         * 支付方式
         */
        private String payType;
        /**
         * 支付时间  yyyy-MM-dd HH:mm:ss
         */
        private String payTime;
        /**
         * 付款渠道
         */
        private String receiptMothed;
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
         * makerCode
         */
        private String makerCode;
        /**
         * 制单人姓名
         */
        private String makerName;
        /**
         * 制单人部门
         */
        private String makerDept;
        /**
         * 审核状态	 3 未提交 0 未审核，1 审核通过，2 审核未通过，4 审核中
         */
        private Integer confirmStatus;
        /**
         * 收款状态 0已收款，1未收款（默认1），2打回
         */
        private Integer receiptStatus;
        /**
         * 账户操作标识	 0：不调账户 1：充值消费 2：冻结金消费
         */
        private Integer accountFlag;
        /**
         * 1 普通支付  2 批量代扣 2轻松付 3自如分期
         */
        private String callType;
        /**
         * 业务方回调地址
         */
        private String callUrl;
        /**
         * 附件列表
         */
        private List<String> annexList;


    }

}
