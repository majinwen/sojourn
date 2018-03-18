package com.zra.pay.entity;

/**
 * @Author: wangxm113
 * @CreateDate: 2016年5月7日
 */
public class CallbackOfPaymentPlatformDto {
	private String order_code;// 支付订单号-支付平台生成的唯一标识
	private String type;// 业务类型-3.1中接口的bizType
	private String callback_url;// 回调url-3.1中接口的notifyUrl
	private String total_price;// 订单金额-单位：分
	private String out_trade_no;// 支付平台流水号-支付平台与第三方的交易流水（内部支付回调不传）
	private String current_money;// 支付金额-单位：分
	private String pay_type;// 支付类型-取值见字典（传对应支付类型，请注意字典中的内部支付）
	private String bizCode;// 业务单号-3.1中接口的bizCode

	public String getOrder_code() {
		return order_code;
	}

	public void setOrder_code(String order_code) {
		this.order_code = order_code;
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

	public String getTotal_price() {
		return total_price;
	}

	public void setTotal_price(String total_price) {
		this.total_price = total_price;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getCurrent_money() {
		return current_money;
	}

	public void setCurrent_money(String current_money) {
		this.current_money = current_money;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "order_code:" + order_code + "; type:" + type + "; callback_url:" + callback_url + "; total_price:"
				+ total_price + "; out_trade_no:" + out_trade_no + "; current_money:" + current_money + "; pay_type:"
				+ pay_type + "; bizCode" + bizCode;
	}

}
