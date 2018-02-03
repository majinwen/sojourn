package com.ziroom.minsu.services.order.dto;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.entity.order.OrderLogEntity;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;

import java.util.List;
import java.util.Map;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2017/1/10 19:38
 * @version 1.0
 * @since 1.0
 */
public class ShowCancelOrderResponse extends BaseEntity {

    OrderInfoVo orderInfo;

    String cancelReason;

    Map<String, String> lanPay;

    Map<String, String> userPay;

    Map<String, String> income;

    List<OrderLogEntity> orderLogs;
    
    CancelOrderServiceRequest cancelOrderServiceRequest;

    public CancelOrderServiceRequest getCancelOrderServiceRequest() {
		return cancelOrderServiceRequest;
	}

	public void setCancelOrderServiceRequest(
			CancelOrderServiceRequest cancelOrderServiceRequest) {
		this.cancelOrderServiceRequest = cancelOrderServiceRequest;
	}

	public OrderInfoVo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfoVo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Map<String, String> getLanPay() {
        return lanPay;
    }

    public void setLanPay(Map<String, String> lanPay) {
        this.lanPay = lanPay;
    }

    public Map<String, String> getUserPay() {
        return userPay;
    }

    public void setUserPay(Map<String, String> userPay) {
        this.userPay = userPay;
    }

    public Map<String, String> getIncome() {
        return income;
    }

    public void setIncome(Map<String, String> income) {
        this.income = income;
    }

    public List<OrderLogEntity> getOrderLogs() {
        return orderLogs;
    }

    public void setOrderLogs(List<OrderLogEntity> orderLogs) {
        this.orderLogs = orderLogs;
    }
}
