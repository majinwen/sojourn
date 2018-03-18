package com.ziroom.zrp.service.trading.valenum.finance;

public enum BillPeriodsEnum {
	FIRST(1,"第一期"),
	SECOND(2,"第二期"),
	THREE(3,"第三期"),
	FOUR(4,"第四期"),
	FIVE(5,"第五期"),
	SIX(6,"第六期"),
	SEVEN(7,"第七期"),
	EIGHT(8,"第八期"),
	NINE(9,"第九期"),
	TEN(10,"第十期"),
	ELEVEN(11,"第十一期"),
	TWELVE(12,"第十二期");
	/**
     * 状态code
     */
    private int code;
    /**
     * 状态名称
     */
    private String name;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	BillPeriodsEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}
    
}
