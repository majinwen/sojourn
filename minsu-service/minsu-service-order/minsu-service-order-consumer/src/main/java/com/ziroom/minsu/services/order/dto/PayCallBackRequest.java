package com.ziroom.minsu.services.order.dto;

import com.asura.framework.base.entity.BaseEntity;


public class PayCallBackRequest extends BaseEntity{
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 6810044124338131812L;
	

	/** 业务单号，对应我们平台的orderSn*/
	private String bizCode;

	/** 支付订单号   支付平台生成的唯一标识 */
	private String order_code;

	/** 支付平台流水号   支付平台与第三方的交易流水（内部支付回调不传） */
	private String out_trade_no;
	
	/** 业务类型  首笔:pay_first,自如网:ziroom;测试:test 可空 */
	private String type;
	
	/** 下单中传递的  异步通知url */
	private String callback_url;
	
	/** 订单金额 单位：分 */
	private Integer total_price;
	
	/** 支付金额  支付平台传对应支付金额 单位：分 */
	private Integer current_money;
	
	/** 传对应支付类型 */
	private String pay_type;

	/** 付款单为  客户订单 还是 房东账单 类型 */
	private int paymentSourceType;
	
	/** 订单、账单 应付金额  */
	private int needMoney;
	
	/** 充值客户uid */
	private String fillUid;
	
	/** 支付客户uid */
	private String payUid;
	
	/** 关联订单号 */
	private String orderSn;
	
	/** 城市code */
	private String cityCode;

	public int getNeedMoney() {
		return needMoney;
	}

	public void setNeedMoney(int needMoney) {
		this.needMoney = needMoney;
	}



	public String getFillUid() {
		return fillUid;
	}

	public void setFillUid(String fillUid) {
		this.fillUid = fillUid;
	}

	public int getPaymentSourceType() {
		return paymentSourceType;
	}

	public void setPaymentSourceType(int paymentSourceType) {
		this.paymentSourceType = paymentSourceType;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getOrder_code() {
		return order_code;
	}

	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCallback_url() {
		return callback_url;
	}

	public void setCallback_url(String callback_url) {
		this.callback_url = callback_url;
	}

	public Integer getTotal_price() {
		return total_price;
	}

	public void setTotal_price(Integer total_price) {
		this.total_price = total_price;
	}

	public Integer getCurrent_money() {
		return current_money;
	}

	public void setCurrent_money(Integer current_money) {
		this.current_money = current_money;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getPayUid() {
		return payUid;
	}

	public void setPayUid(String payUid) {
		this.payUid = payUid;
	}


}
