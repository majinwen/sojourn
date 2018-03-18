package com.ziroom.zrp.service.trading.valenum;

public enum ConstatusShowEnum {
	DQY(0,"待签约"),
	DZF(1,"待支付"),
	DWYJG(2,"待物业交割"),
	SHZ(3,"审核中"),
	LXZ(4,"履行中"),
	YWC(5,"已完成"),
	YGB(6,"已关闭"),
	YTZ(7,"已退租"),
	JYZ(8,"解约中"),
	YDQ(9,"已到期"),
	DRZ(10,"待入住");
	
	private Integer code;//合同码
	private String name;//合同显示状态
	
	
	ConstatusShowEnum(Integer code,String name){
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static String getNameByCode(Integer code){
		for(final ConstatusShowEnum constatusShowEnum : ConstatusShowEnum.values()){
            if(constatusShowEnum.getCode() == code){
                return constatusShowEnum.getName();
            }
        }
		return null;
	}
	
}
