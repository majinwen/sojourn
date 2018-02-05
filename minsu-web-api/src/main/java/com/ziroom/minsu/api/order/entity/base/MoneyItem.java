package com.ziroom.minsu.api.order.entity.base;

import com.ziroom.minsu.services.order.constant.OrderFeeConst;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年08月01日 11:37
 * @since 1.0
 */
public class MoneyItem extends Item {
    /**
     * 符号 默认值 人民币
     */
    private String feeUnit = OrderFeeConst.FEE_UNIT.getShowName();
    /**
     * 标记
     */
    private String symbol;

    public String getFeeUnit() {
        return feeUnit;
    }

    public void setFeeUnit(String feeUnit) {
        this.feeUnit = feeUnit;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
