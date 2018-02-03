package com.ziroom.minsu.valenum.pay;

public enum PayCustomerTypeEnum {
	order(1, "KH"),
	punish(2, "YZ");

	PayCustomerTypeEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public static PayCustomerTypeEnum getByCode(int code){
    	for(final PayCustomerTypeEnum ose : PayCustomerTypeEnum.values()){
    		if(ose.getCode() == code){
    			return ose;
    		}
    	}
    	return null;
    }

	/** code */
	private int code;

	/** 名称 */
	private String name;

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
}
