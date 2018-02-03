package com.ziroom.minsu.services.order.entity;

import java.util.List;

import com.ziroom.minsu.entity.customer.CustomerBankCardMsgEntity;
import com.ziroom.minsu.entity.order.FinancePayVouchersDetailEntity;
import com.ziroom.minsu.entity.order.FinancePayVouchersEntity;
import com.ziroom.minsu.entity.order.FinancePayVouchersLogEntity;
import com.ziroom.minsu.entity.order.OrderMoneyEntity;

/**
 * <p>
 * 付款单详情VO
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年5月17日
 * @since 1.0
 * @version 1.0
 */
public class FinancePayDetailInfoVo extends FinancePayVouchersEntity {

	private static final long serialVersionUID = 7042886399169899833L;

	/** 订单金额 */
	OrderMoneyEntity orderMoney;

	/** 付款单明细 */
	List<FinancePayVouchersDetailEntity> detailList;
	
	/** 付款单日志*/
	List<FinancePayVouchersLogEntity> logList;

	/** 付款人名称*/
	private String payName;
	
	/** 收款人名称*/
	private String receiveName;
	
	/** 付款单来源名称*/
	private String paySourceName;
	
	/** 收款人类型*/
	private String receiveTypeName;
	
	
	public String getPaySourceName() {
		return paySourceName;
	}

	public void setPaySourceName(String paySourceName) {
		this.paySourceName = paySourceName;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public OrderMoneyEntity getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(OrderMoneyEntity orderMoney) {
		this.orderMoney = orderMoney;
	}

	public List<FinancePayVouchersDetailEntity> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<FinancePayVouchersDetailEntity> detailList) {
		this.detailList = detailList;
	}

	public List<FinancePayVouchersLogEntity> getLogList() {
		return logList;
	}

	public void setLogList(List<FinancePayVouchersLogEntity> logList) {
		this.logList = logList;
	}

	public String getReceiveTypeName() {
		return receiveTypeName;
	}

	public void setReceiveTypeName(String receiveTypeName) {
		this.receiveTypeName = receiveTypeName;
	}

}
