package com.ziroom.minsu.services.order.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.List;

/**
 * <p>订单的价格展示</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/5.
 * @version 1.0
 * @since 1.0
 */
public class PriceVo extends BaseEntity {

    /** 基础价格 */
    private Integer basePrice;

    /** 价格展示 */
    private List<String> specialDayPrices ;


}
