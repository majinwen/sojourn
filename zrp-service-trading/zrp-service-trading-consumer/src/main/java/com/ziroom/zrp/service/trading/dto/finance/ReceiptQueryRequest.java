package com.ziroom.zrp.service.trading.dto.finance;

import lombok.Data;

/**
 * <p>
 *     财务wiki地址  http://wiki.ziroom.com/pages/viewpage.action?pageId=381091962
 *     查询收款单入参
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
 * @Date Created in 2018年02月1日 10:20
 * @since 1.0
 */

@Data
public class ReceiptQueryRequest {

    /**
     * 出房合同号
     */
    private String contractCode;

    /**
     * 收款单号
     */
    private String billNum;

    /**
     * 父合同号
     */
    private String parentContractCode;

    /**
     * 审核状态
     */
    private String confirmStatus;

    /**
     * 收款状态
     */
    private String receiptStatus;

}
