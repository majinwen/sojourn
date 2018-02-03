package com.ziroom.minsu.services.cms.entity;

import java.math.BigDecimal;

/**
 * <p>自如寓活动金额项</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月16日 16:38
 * @since 1.0
 */
public class ZrpActFeeVo {
    /**
     * 活动号
     */
    private String actSn;
    /**
     * 活动名称
     */
    private String actName;
    /**
     * 折扣金额
     */
    private double discountAmount;
    /**
     * 优惠费用项
     */
    private String expendCostCode;

    public String getActSn() {
        return actSn;
    }

    public void setActSn(String actSn) {
        this.actSn = actSn;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getExpendCostCode() {
        return expendCostCode;
    }

    public void setExpendCostCode(String expendCostCode) {
        this.expendCostCode = expendCostCode;
    }
}
