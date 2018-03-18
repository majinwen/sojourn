package com.ziroom.minsu.report.basedata.valenum;

import java.util.LinkedHashMap;
import java.util.Map;


/**  
 * @Title: HouseOrderTypeEnum.java  
 * @Package com.ziroom.minsu.report.basedata.valenum  
 * @Description: 房源订单类型枚举
 * @author loushuai  
 * @date 2017年4月20日  
 * @version V1.0  
 */
public enum HouseOrderTypeEnum {

	HOT_SS(1,"实时下单",true),
	HOT_PT(2,"普通下单",true);
	
	/** 房源订单类型代码  */
	private int code;
	
	/** 房源订单类型名字  */
	private String name;
	
	/** 是否初始化进入map标志*/
	private boolean isPushToMap;
	
	HouseOrderTypeEnum(int code, String name, boolean isPushToMap) {
		this.code = code;
		this.name = name;
		this.isPushToMap = isPushToMap;
	}
	
	public static HouseOrderTypeEnum getEnumByOrderTypeCode(int code){
		for(HouseOrderTypeEnum temp : HouseOrderTypeEnum.values()){
			if(temp.getCode() == code){
				return temp;
			}
		}
		return null;
	}
	
	/** 所有状态集合 */
	private static final Map<Integer, String> enumMap  = new LinkedHashMap<Integer, String>();
	
	/**有效状态集合 */
	private static final Map<Integer, String> validEnumMap = new LinkedHashMap<Integer,String>();
    
    static{
    	for(HouseOrderTypeEnum temp : HouseOrderTypeEnum.values()){
    		enumMap.put(temp.getCode(), temp.getName());
    		if(temp.getIsPushToMap()){
    			validEnumMap.put(temp.getCode(), temp.getName());
    		}
    	}
    }
    
    /** 获取所有状态集合 */
    public static Map<Integer, String> getEnumMap(){
    	return enumMap;
    }
    
    /** 获取所有有效状态集合 */
    public static Map<Integer, String> getValidEnumMap(){
    	return validEnumMap;
    }
	

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

	public boolean getIsPushToMap() {
		return isPushToMap;
	}

	public void setPushToMap(boolean isPushToMap) {
		this.isPushToMap = isPushToMap;
	}
	
	
}
