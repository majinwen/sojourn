
package com.ziroom.minsu.valenum.house;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 房源渠道枚举
 * 
 * @author zl
 * @created 2016年9月12日
 *
 */
public enum HouseChannelEnum {

	CH_ZHIYING(1,"直营",false),
	CH_FANGDONG(2,"房东",true),
	CH_DITUI(3,"地推",true);
	
	HouseChannelEnum(int code,String name,boolean isPushToMap) {
		this.code = code;
		this.name = name;
		this.isPushToMap = isPushToMap;
	}

	/** code */
	private int code;

	/** 名称 */
	private String name;
	
	/** 是否初始化进入Map标志 */
    private boolean isPushToMap;
	
    
    private static final Map<Integer,String> enumMap = new LinkedHashMap<Integer,String>();
    private static final Map<Integer,String> enumMapAll = new LinkedHashMap<Integer,String>();
	
	static {
		for (HouseChannelEnum valEnum : HouseChannelEnum.values()) {
			enumMapAll.put(valEnum.getCode(), valEnum.getName()); 
			if(valEnum.getIsPushToMap()){
				enumMap.put(valEnum.getCode(), valEnum.getName());  
			}
        }  
	}
    
    /**
     * 获取
     * @param code
     * @return
     */
    public static HouseChannelEnum getHouseChannelByCode(int code) {
        for (final HouseChannelEnum valEnum : HouseChannelEnum.values()) {
            if (valEnum.getCode() == code) {
                return valEnum;
            }
        }
        return null;
    }
	

 
	public int getCode() {
		return code;
	} 
	 
	public String getName() {
		return name;
	} 
	
    public boolean getIsPushToMap() {
    	return isPushToMap;
    }

	public static Map<Integer,String> getEnumMap() {
    	return enumMap;
    }
	
	public static Map<Integer,String> getEnumMapAll() {
    	return enumMapAll;
    }
}
