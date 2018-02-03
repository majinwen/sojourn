package com.ziroom.minsu.entity.order;

import com.asura.framework.base.entity.BaseEntity;



/**
 * <p>订单特殊价格实体类</p>
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
public class OrderSpecialPriceEntity extends BaseEntity {

    /** 序列化id  */
    private static final long serialVersionUID = -71442349025171L;


    /** id */
    private Integer id;

    /** 订单编号 */
    private String orderSn;

    /** 特殊价格时间 */
    private String priceDate;

    /** 特殊价格 */
    private Integer priceValue;
    
	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

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