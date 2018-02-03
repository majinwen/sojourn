package com.ziroom.minsu.services.order.entity;

/**
 * <p>房东我的订单列表</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/9/6.
 * @version 1.0
 * @since 1.0
 */
public class LandlordOrderVo  extends  LandlordOrderItemVo {

    /** 订单详情url */
    private String detailUrl = "";

    /** 添加备注url */
    private String remarkUrl = "";


    /** 评价url */
    private String evaluateUrl = "";

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getRemarkUrl() {
        return remarkUrl;
    }

    public void setRemarkUrl(String remarkUrl) {
        this.remarkUrl = remarkUrl;
    }

    public String getEvaluateUrl() {
        return evaluateUrl;
    }

    public void setEvaluateUrl(String evaluateUrl) {
        this.evaluateUrl = evaluateUrl;
    }

}
