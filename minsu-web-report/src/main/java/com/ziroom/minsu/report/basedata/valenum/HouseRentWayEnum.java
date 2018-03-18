package com.ziroom.minsu.report.basedata.valenum;

import java.util.LinkedHashMap;
import java.util.Map;


/**  
 * @Title: HouseRentWayEnum.java  
 * @Package com.ziroom.minsu.report.house.entity  
 * @Description: 房源出租类型枚举
 * @author loushuai  
 * @date 2017年4月20日  	
 * @version V1.0  
 */
public enum HouseRentWayEnum {

	RT_ZZ(0,"整租",true),
	RT_FZ(1,"分租",true),
	RT_CW(2,"床位",false);
	
	/** 房源类型代码  */
	private int code;
	
	/** 房源类型名字 */
	private String name;
	
	/** 是否初始化进入Map标志 */
	private boolean isPushToMap;
	
	HouseRentWayEnum(int code, String name, boolean isPushToMap) {
		this.code = code;
		this.name = name;
		this.isPushToMap = isPushToMap;
	}

	/** 根据code获取枚举对象 */
	public static HouseRentWayEnum getEnumByRentWayCode(int code){
		for(HouseRentWayEnum temp : HouseRentWayEnum.values()){
			if(temp.getCode() == code){
				return temp;
			}
		}
		return null;
	}
	
	/** 所有状态集合 */
	private static final Map<Integer, String> enumMap = new LinkedHashMap<Integer, String>();
	
	/** 有效状态集合 */
	private static final Map<Integer, String> validEnumMap = new LinkedHashMap<Integer, String>();
	
	
	static{
		for(HouseRentWayEnum temp : HouseRentWayEnum.values()){
			enumMap.put(temp.getCode(), temp.getName());
			if(temp.getIsPushToMap()){
				validEnumMap.put(temp.getCode(), temp.getName());
			}
		}
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

	public void setPushToMap(boolean isPushToMap) {
		this.isPushToMap = isPushToMap;
	}

	public boolean getIsPushToMap() {
    	return isPushToMap;
    }

	/** 获取所有状态集合 */
	public static Map<Integer, String> getEnumMap() {
		return enumMap;
	}

	/** 获取有效状态集合 */
	public static Map<Integer, String> getValidEnumMap() {
		return validEnumMap;
	}
	
}
