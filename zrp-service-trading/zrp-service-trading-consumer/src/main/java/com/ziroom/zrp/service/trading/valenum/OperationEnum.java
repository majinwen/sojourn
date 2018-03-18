package com.ziroom.zrp.service.trading.valenum;

public enum OperationEnum {
	QY(0,"签约"),
	ZF(1,"支付"),//跳转账单详情页
	WYJG(2,"物业交割"),
	XY(3,"续约"),
	TJHT(4,"提交合同"),
	ZFLB_FZ(5,"支付"),//跳转支付列表页房租列表
	ZFLB_SH(6,"支付");//跳转支付列表页生活费用列表
	
	private int code;
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
	OperationEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public static String getNameByCode(Integer code){
		for(final OperationEnum operationEnum : OperationEnum.values()){
            if(operationEnum.getCode() == code){
                return operationEnum.getName();
            }
        }
		return null;
	}
}
