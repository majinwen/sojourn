package com.ziroom.minsu.services.cms.entity;

/**
 * <p>优惠券领取展示</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年06月15日 14:40
 * @since 1.0
 */
public class CouponItemVo {
    /**
     * 优惠券金额
     */
    private String money;
    /**
     * 描述
     */
    private String desc;
    /**
     * 活动号
     */
    private String actSn;
    /**
     * 金额符号
     */
    private String symbol;
    /**
     * 是否可以领取 1=是 0=否
     */
    private Integer isCan = 1;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getIsCan() {
        return isCan;
    }

    public void setIsCan(Integer isCan) {
        this.isCan = isCan;
    }

    public String getActSn() {
        return actSn;
    }

    public void setActSn(String actSn) {
        this.actSn = actSn;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
