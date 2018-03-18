package com.ziroom.minsu.report.basedata.valenum;

import java.util.LinkedHashMap;
import java.util.Map;

/**  
 * @Title: HouseQualityGradeEnum.java  
 * @Package com.ziroom.minsu.report.basedata.valenum  
 * @Description: 房源级别枚举
 * @author loushuai  
 * @date 2017年4月20日  
 * @version V1.0  
 */
public enum HouseQualityGradeEnum {

	HQG_TOP("TOP","TOP",true),
	HQG_APLUS("A","塔尖",true),
	HQG_A("A","A类",true),
	HQG_B("B","B类",true),
	HQG_C("C","C类",true);
	
	/** 房源登记代号 */
	private String code;
	
	/** 房源状等级名字*/
	private String name;
	
	/** 是否初始化到map*/
	private boolean isPushToMap;
	
    private HouseQualityGradeEnum(String code, String name, boolean isPushToMap) {
		this.code = code;
		this.name = name;
		this.isPushToMap = isPushToMap;
	}

	public static  HouseQualityGradeEnum getQualityGradeByCode(String code){
    	if(null != code && !"".equals(code)){
    		for(HouseQualityGradeEnum temp : HouseQualityGradeEnum.values()){
        		if(code.equals(temp.getCode())){
        			return temp;
        		}
        	}
    	}
    	return null;
    }
    
    /** 全部集合 */
	private static final Map<String, String> enumMap = new LinkedHashMap<String, String>();
    
    /** 有效集合 */
	private static final Map<String, String> validEnumMap = new LinkedHashMap<String, String>();
    
    static{
    	for(HouseQualityGradeEnum temp : HouseQualityGradeEnum.values()){
    		enumMap.put(temp.getCode(), temp.getName());
    		if(temp.getIsPushToMap()){
    			validEnumMap.put(temp.getCode(), temp.getName());
    		}
    	}
    	
    }
    
    /** 获取所有状态集合 */
	public static Map<String, String> getEnumMap() {
		return enumMap;
	}

	/** 获取所有有效状态集合 */
	public static Map<String, String> getValidEnumMap() {
		return validEnumMap;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
	public boolean getIsPushToMap() {
		return isPushToMap;
	}

	public void setPushToMap(boolean isPushToMap) {
		this.isPushToMap = isPushToMap;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
