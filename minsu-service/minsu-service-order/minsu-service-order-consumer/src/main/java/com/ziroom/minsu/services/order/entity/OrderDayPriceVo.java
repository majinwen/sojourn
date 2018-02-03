package com.ziroom.minsu.services.order.entity;

import com.asura.framework.base.entity.BaseEntity;


/**
 * <p>订单每天的价格</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/31.
 * @version 1.0
 * @since 1.0
 */
public class OrderDayPriceVo extends BaseEntity {

    /** 序列化id  */
    private static final long serialVersionUID = -14564123156025171L;


    /** 特殊价格时间 */
    private String priceDate;

    /** 特殊价格 */
    private Integer priceValue;


    public String getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(String priceDate) {
        this.priceDate = priceDate;
    }

    public Integer getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(Integer priceValue) {
        this.priceValue = priceValue;
    }
}