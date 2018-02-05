package com.ziroom.minsu.api.order.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>用户中心显示订单列表</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/9.
 * @version 1.0
 * @since 1.0
 */
public class OrderUCVo extends BaseEntity{

    //订单编号 订单编号，订单金额、订单实际成交金额、订单日期、入住日期、退房日期，订单状态。

    /**
     * 序列ID
     */
    private static final long serialVersionUID = -410509735122413093L;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 订单金额
     */
    private Integer sumMoney;

    /**
     * 订单实际成交金额
     */
    private Integer realMoney;

    /**
     * 订单状态
     */
    private Integer orderStatus;
    /**
     * 订单状态名称
     */
    private String orderStatusName;

    /**
     * 创建时间 yyyy-MM-dd
     */
    private String createTimeStr;

    /**
     * 开始时间 yyyy-MM-dd
     */
    private String startTimeStr;
    /**
     * 结束时间 yyyy-MM-dd
     */
    private String endTimeStr;



    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(Integer sumMoney) {
        this.sumMoney = sumMoney;
    }

    public Integer getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(Integer realMoney) {
        this.realMoney = realMoney;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

}
