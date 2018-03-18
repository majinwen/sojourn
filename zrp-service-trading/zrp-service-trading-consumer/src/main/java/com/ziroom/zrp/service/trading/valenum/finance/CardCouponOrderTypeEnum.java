package com.ziroom.zrp.service.trading.valenum.finance;

/**
 * <p>卡券类型枚举</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年12月25日 14:50
 * @since 1.0
 */

public enum CardCouponOrderTypeEnum {

    ZJK("租金卡",95),
    YGK("员工卡",92);

    CardCouponOrderTypeEnum(String name, Integer orderType) {
        this.name = name;
        this.orderType = orderType;
    }

    private String name;

    private Integer orderType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }
}
