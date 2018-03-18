package com.ziroom.minsu.report.basedata.valenum;

import java.util.LinkedHashMap;
import java.util.Map;

/**  
 * @Title: RegionEnum.java  
 * @Package com.ziroom.minsu.report.basedata.valenum  
 * @Description: 大区枚举
 * @author loushuai  
 * @date 2017年4月21日  
 * @version V1.0  
 */
/**
 * @author Administrator
 *
 */
public enum RegionEnum {
	Region_HuaBei("8a9e988b59810f230159810f240b0000","华北区",true),
	Region_HuaDong("8a9e99b459b136da0159b153afa20001","华东区",true),
	Region_HuaNan("8a9e98985a3b42da015a4134a2810002","华南大区",true);
	
	/** 大区fid  */
	private String regionFid;
	
	/** 大区名字  */
	private String regionName;
	
	/** 是否初始化map  */
	private boolean isPushToMap;
	

	RegionEnum(String regionFid, String regionName, boolean isPushToMap) {
		this.regionFid = regionFid;
		this.regionName = regionName;
		this.isPushToMap = isPushToMap;
	}



	public static RegionEnum getRegionEnumByFid(String regionFid){
		if(null != regionFid && !"".equals(regionFid)){
			for(RegionEnum temp : RegionEnum.values()){
				if(regionFid.equals(temp.getRegionFid())){
					return temp;
				}
			}
		}
		return null;
	}

	/** 所有大区集合  */
	private static final Map<String, String> enumMap = new LinkedHashMap<String, String>();
	
	/** 有效大区集合  */
	private static final Map<String, String> validEnumMap = new LinkedHashMap<String, String>();
	
	static{
		for(RegionEnum temp : RegionEnum.values()){
			enumMap.put(temp.getRegionFid(), temp.getRegionName());
			if(temp.getIsPushToMap()){
				validEnumMap.put(temp.getRegionFid(), temp.getRegionName());
			}
		}
	}
	
	/** 所有状态集合 */
	public static Map<String, String> getEnumMap(){
		return enumMap;
	}
	
	/**有效状态集合 */
	public static Map<String, String> getValidEnumMap(){
		return validEnumMap;
	}

	public String getRegionFid() {
		return regionFid;
	}

	public void setRegionFid(String regionFid) {
		this.regionFid = regionFid;
	}

	
	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public boolean getIsPushToMap() {
		return isPushToMap;
	}

	public void setPushToMap(boolean isPushToMap) {
		this.isPushToMap = isPushToMap;
	}
	
	
}
