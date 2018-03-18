package com.ziroom.zrp.service.trading.valenum;

import java.util.LinkedHashMap;
import java.util.Map;

public enum ContractAuditStatusEnum {

	DSH("0","待审核"),
	YHBH("1","审核驳回"),
	YTG("2", "审核通过");


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
	private ContractAuditStatusEnum(String status, String name) {
		this.status = status;
		this.name = name;
	}
	
	public static String getNameByStatus(String status){
		for(final ContractAuditStatusEnum contractStatusEnum : ContractAuditStatusEnum.values()){
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
		for (ContractAuditStatusEnum contractAuditStatusEnum : ContractAuditStatusEnum.values()){
			map.put(contractAuditStatusEnum.getStatus(),contractAuditStatusEnum.getName());
		}
		return map;
	}

}
