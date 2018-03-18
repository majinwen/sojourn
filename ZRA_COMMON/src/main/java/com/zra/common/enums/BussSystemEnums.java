package com.zra.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cuiyh9
 * 系统枚举
 */
public enum BussSystemEnums {

	ZRA("1001","自如寓","zra_business"),
	ZRY("1002","自如驿站","zry");
	
	private String key;
	
	private String value;
	
	private String aliasName;
	
	/*枚举Map*/
	protected static final Map<String, String> bussSystemMap = new HashMap();
    
    static {
        for (BussSystemEnums bussEnums : EnumSet.allOf(BussSystemEnums.class)) {
            bussSystemMap.put(bussEnums.getKey(), bussEnums.getValue());
        }
    }
	
	private BussSystemEnums(String key,String value,String aliasName){
		this.key = key;
		this.value = value;
		this.aliasName = aliasName;
		
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
	
	public String getAliasName() {
		return aliasName;
	}

	/**
	 * 根据系统号获取
	 * @param systemId
	 * @return
	 */
	public static BussSystemEnums getByKey(String systemId){
		for (BussSystemEnums bussEnums : EnumSet.allOf(BussSystemEnums.class)) {
			if(bussEnums.getKey().equals(systemId)){
				return bussEnums;
			}
		}
		return null;
	}

	public static Map<String, String> getBusssystemmap() {
		return bussSystemMap;
	}
}
