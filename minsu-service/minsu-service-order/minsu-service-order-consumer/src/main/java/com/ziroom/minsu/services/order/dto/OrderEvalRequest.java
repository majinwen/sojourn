package com.ziroom.minsu.services.order.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/11/14.
 * @version 1.0
 * @since 1.0
 */
public class OrderEvalRequest extends PageRequest {

    private static final long serialVersionUID = 5645123564545403L;
    /**
     * 用户uid
     */
    private String uid;

    /**
     * @see com.ziroom.minsu.valenum.common.RequestTypeEnum
     */
    private Integer requestType;


    /**
     * @see com.ziroom.minsu.valenum.evaluate.OrderEvalTypeEnum
     */
    private Integer orderEvalType = 0;

    /**
     * 评价有效天数
     */
    private Integer  limitDay;


    public Integer getLimitDay() {
        return limitDay;
    }

    public void setLimitDay(Integer limitDay) {
        this.limitDay = limitDay;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getRequestType() {
        return requestType;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
    }

    public Integer getOrderEvalType() {
        return orderEvalType;
    }

    public void setOrderEvalType(Integer orderEvalType) {
        this.orderEvalType = orderEvalType;
    }
}
