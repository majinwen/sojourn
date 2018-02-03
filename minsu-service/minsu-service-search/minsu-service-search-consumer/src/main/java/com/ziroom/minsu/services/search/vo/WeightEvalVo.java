package com.ziroom.minsu.services.search.vo;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>权重的评价</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/18.
 * @version 1.0
 * @since 1.0
 */
public class WeightEvalVo  extends BaseEntity{

    private static final long serialVersionUID = 332428979809946703L;

    /** 已经评价数量  */
    private Integer evlNum;

    /** 结束订单数量  */
    private Integer orderNum;

    public Integer getEvlNum() {
        return evlNum;
    }

    public void setEvlNum(Integer evlNum) {
        this.evlNum = evlNum;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
