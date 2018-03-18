package com.ziroom.zrp.service.trading.dto.finance;

import lombok.Data;

import java.util.List;

/**
 * <p>创建收款单返回结果</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年11月22日 11:16
 * @since 1.0
 */
@Data
public class ReceiptCreateResponse {
    private Integer code;

    private String message;

    private List<ReceiptResponse> data;

    /**
     * 创建收款单成功返回结果
     * @author jixd
     * @created 2017年11月22日 11:20:26
     * @param
     * @return
     */
    @Data
    public static class ReceiptResponse{
        /**
         * 应收账单编号
         */
        private String billNum;
        /**
         * 费用项
         */
        private String costCode;
        /**
         * 应收金额
         */
        private Integer billAmount;
        /**
         * 实收金额
         */
        private Integer receivedAmount;


    }

}
