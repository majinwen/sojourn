package com.ziroom.zrp.service.trading.dto.finance;

import lombok.Data;

import java.util.List;

/**
 * <p>付款单列表</p>
 * <p>
 *     http://zfpayment.t.ziroom.com/api/zryj/getPayVouchersByContract
 * </p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2018年01月08日 10:52
 * @since 1.0
 */
@Data
public class PayVoucherResponse {
    //String类型 付款单号
    private String payOrderCode;
    //String类型 公司
    private String cityCode;
    //String类型 支付流水号
    private String paySerialNum;
    //String类型 收房合同
    private String collectContract;
    //String类型 出房合同
    private String outContract;
    //String类型 订单号
    private String orderCode;
    //String类型 付款类型（附录1）
    private String paymentTypeCode;
    //String类型 付款对象
    private String markCollectCode;
    //String类型 客户名称
    private String customerName;
    //String类型 uid
    private String uid;
    //String类型 对方银行
    private String customerBankName;
    //String类型 对方开户名
    private String customerAccountName;
    //String类型 对方卡号
    private String customerBankAccount;
    //Integer类型 审核状态 1:待提交，2：待审核，3：审核驳回，4：审核通过
    private String auditFlag;
    // Integer类型 付款状态 1：未付款，2：已付款，3：付款异常，4：付款中
    private String payFlag;
    // double类型 付款单总金额 付款单合计（单位：元）
    private Double total;

    private List<PayDetail> detailList;


    @Data
    public class PayDetail{

        private String costCode;

        private Double amount;
    }

}
