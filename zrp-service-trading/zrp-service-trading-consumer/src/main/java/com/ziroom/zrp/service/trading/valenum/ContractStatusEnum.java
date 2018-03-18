package com.ziroom.zrp.service.trading.valenum;

import java.util.LinkedHashMap;
import java.util.Map;

public enum ContractStatusEnum {
	
	WQY("wqy","未签约"),
	DZF("dzf","待支付"),
	YQY("yqy","已签约"),
	JYZ("jyz","解约中"),
	YGB("ygb","已关闭"),
	YTZ("ytz","已退租"),
	YZF("yzf","已作废"),
	XYZ("xyz","续约中"),
	YDQ("ydq","已到期"),
	YXY("yxy","已续约");
	
	
	//YQX("yqx","已取消"),
	//YRZ("yrz","已入住");
	
	
	private String status;
	private String name;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private ContractStatusEnum(String status, String name) {
		this.status = status;
		this.name = name;
	}
	
	public static String getNameByStatus(String status){
		for(final ContractStatusEnum contractStatusEnum : ContractStatusEnum.values()){
            if(contractStatusEnum.getStatus().equals(status)){
                return contractStatusEnum.getName();
            }
        }
		return null;
	}

	/**
	 * map
	 * @author jixd
	 * @created 2017年11月27日 10:38:31
	 * @param
	 * @return
	 */
	public static Map<String,String> getSelectMap(){
		Map<String,String> map = new LinkedHashMap<>();
		for (ContractStatusEnum contractStatusEnum : ContractStatusEnum.values()){
			map.put(contractStatusEnum.getStatus(),contractStatusEnum.getName());
		}
		return map;
	}

}
