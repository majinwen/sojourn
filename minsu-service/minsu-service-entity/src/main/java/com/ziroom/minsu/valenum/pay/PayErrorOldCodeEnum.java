package com.ziroom.minsu.valenum.pay;

public enum PayErrorOldCodeEnum {
	common100(100, "请求入参参数对象为空."),
	common101(101, "请求参数订单编号为空."),
	common102(102, "参数支付集合为空."),
	common104(104, "支付单据类型错误."),
	common105(105, "未知错误."),
    common106(106, "设备标识错误."),
	
	order201(201, "非法的订单编号."),
	order202(202, "用户uid与订单上客户uid不一致."),
	order203(203, "订单已经被支付."),
	order204(204, "支付的金额与订单上的金额不一致."),
	order205(205, "订单已超过支付时限."),
	order206(206, "订单状态错误,不为待支付状态."),
	
	punish301(301, "非法的罚款单号."),
	punish302(302, "用户uid与账单上客户uid不一致."),
	punish303(303, "账单已经被支付."),
	punish304(304, "支付的金额与账单上的金额不一致."),
	
	platform1001(1001, "支付平台创建支付订单错误."),
	platform1002(1002, "支付平台返回的支付单号为空. "),
	platform1003(1003, "支付平台去支付接口返回失败.");

	
	
	PayErrorOldCodeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	/** code */
	private int code;

	/** 名称 */
	private String message;

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
	public static PayErrorOldCodeEnum getPayErroeEnumByCode(int code){
		for(PayErrorOldCodeEnum eCodeEnum : PayErrorOldCodeEnum.values()){
			if(eCodeEnum.getCode() == code){
				return eCodeEnum;
			}else{
				return null;
			}
		}
		return null;
	}
}
