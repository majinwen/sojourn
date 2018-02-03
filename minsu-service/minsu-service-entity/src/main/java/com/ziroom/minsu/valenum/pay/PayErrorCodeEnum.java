package com.ziroom.minsu.valenum.pay;

public enum PayErrorCodeEnum {
	common100(100, true, "请求入参参数对象为空."),
	common101(101, true, "请求参数订单编号为空."),
	common102(102, true, "参数支付集合为空."),
	common104(104, true, "支付单据类型错误."),
	common105(105, true, "未知错误."),
    common106(106, true, "设备标识错误."),
	order201(201, true, "非法的订单编号."),
	order202(202, true, "用户uid与订单上客户uid不一致."),
	order203(203, true, "订单已经被支付."),
	order204(204 ,true,"支付的金额与订单上的金额不一致."),
	order205(205, true, "订单已超过支付时限."),
	order206(206, true, "订单状态错误,不为待支付状态."),
	punish301(301, true, "非法的罚款单号."),
	punish302(302, true, "用户uid与账单上客户uid不一致."),
	punish303(303, true, "账单已经被支付."),
	punish304(304,  true,"支付的金额与账单上的金额不一致."),
	platform1001(1001, true, "支付平台创建支付订单错误."),
	platform1002(1002, true, "支付平台返回的支付单号为空. "),
	platform1003(1003, true, "支付平台去支付接口返回失败."),

	//==============================================================
	//上面都是老的，新的直接加在下面 并且 old = false; 这个不这么来 打死你
	//==============================================================

	order207(207, false,"房源快照不存在."),
	order208(208,false, "获取房源锁列表错误."),
	order209(209, false,"房源已被其他人占用."),
	;



	
	
	PayErrorCodeEnum(int code, boolean old, String message) {
		this.code = code;
		this.old = old;
		this.message = message;
	}

	/** code */
	private int code;

	/** 是否是老版本的东西 */
	private boolean old;

	/** 名称 */
	private String message;

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public boolean isOld() {
		return old;
	}

	public static PayErrorCodeEnum getPayErrorEnumByCode(int code){
		for(PayErrorCodeEnum eCodeEnum : PayErrorCodeEnum.values()){
			if(eCodeEnum.getCode() == code){
				return eCodeEnum;
			}
		}
		return null;
	}

	/**
	 * 兼容当前的版本信息
	 * @param code
	 * @param old
     * @return
     */
	public static PayErrorCodeEnum getByCode(int code,boolean old){
		for(PayErrorCodeEnum eCodeEnum : PayErrorCodeEnum.values()){
			if(eCodeEnum.getCode() == code){
				if (old){
					//是老版本
					if (eCodeEnum.isOld() == old){
						return eCodeEnum;
					}else {
						return order205;
					}
				}
				return eCodeEnum;
			}
		}
		//未知错误
		return common105;
	}



	public static void main(String []args){
		System.out.println(PayErrorCodeEnum.getPayErrorEnumByCode(106));
	}
}
