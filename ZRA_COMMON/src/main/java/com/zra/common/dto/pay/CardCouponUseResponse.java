package com.zra.common.dto.pay;

/**
 * <p>卡券消费返回值</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年12月24日 16:42
 * @since 1.0
 */

public class CardCouponUseResponse {

    /**
     * 	消费序列号  成功时返回
     */
    private String serialNumber;

    /**
     * 消费失败的卡券号  失败时返回
     */
    private String card_code;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCard_code() {
        return card_code;
    }

    public void setCard_code(String card_code) {
        this.card_code = card_code;
    }
}
