package com.ziroom.minsu.report.basedata.valenum;

import java.util.LinkedHashMap;
import java.util.Map;

/**  
 * @Title: NationEnum.java  
 * @Package com.ziroom.minsu.report.basedata.valenum  
 * @Description: 国家枚举
 * @author loushuai  
 * @date 2017年4月21日  
 * @version V1.0  
 */
public enum NationEnum {

	NATION_CHI("100000","中国","86",true),
	NATION_JAP("386","日本","81",true);
	
	/** 国家code  */
	private String nationCode;
	
	/** 国家名字  */
	private String nationName;
	
	/** 国家代表码  */
	private String countryCode;
	
	/** 是否有效  */
	private boolean isPushToMap;

	NationEnum(String nationCode, String nationName,
			String countryCode, boolean isPushToMap) {
		this.nationCode = nationCode;
		this.nationName = nationName;
		this.countryCode = countryCode;
		this.isPushToMap = isPushToMap;
	}
	
	/** 根据nationCode获取国家  */
	public static NationEnum getNationEnumByNationCode(String nationCode){
		if(null != nationCode && !"".equals(nationCode)){
			for(NationEnum temp : NationEnum.values()){
				if(temp.getNationCode().equals(nationCode)){
					return temp;
				}
			}
		}
		return null;
	}
	
	/** 根据countryCode获取国家  */
	public static NationEnum getNationEnumByCountryCode(String countryCode){
		if(null != countryCode && !"".equals(countryCode)){
			for(NationEnum temp : NationEnum.values()){
				if(countryCode.equals(temp.getCountryCode())){
					return temp;
				}
			}
		}
		return null;
	}
	
	/** 全部国家集合-key为nationCode  value为nationName  */
	private static final Map<String, String> ncEnumMap = new LinkedHashMap<String, String>();
	
	/** 有效状态国家集合-key为nationCode  value为nationName  */
	private static final Map<String, String> ncValidEnumMap = new LinkedHashMap<String, String>();
	
	/** 全部国家集合-key为countryCode  value为nationName  */
	private static final Map<String, String> ccEnumMap = new LinkedHashMap<String, String>();
	
	/** 有效状态国家集合-key为countryCode  value为nationName  */
	private static final Map<String, String> ccValidEnumMap = new LinkedHashMap<String, String>();
	
	static{
		for(NationEnum temp : NationEnum.values()){
			ncEnumMap.put(temp.getNationCode(), temp.getNationName());
			ccEnumMap.put(temp.getCountryCode(), temp.getNationName());
			if(temp.getIsPushToMap()){
				ncValidEnumMap.put(temp.getNationCode(), temp.getNationName());
				ccValidEnumMap.put(temp.getCountryCode(), temp.getNationName());
			}
		}
	}

	
	/** 获取全部国家集合-key为nationCode  value为nationName  */
	public static Map<String, String> getNcEnumMap() {
		return ncEnumMap;
	}

	/** 获取有效状态国家集合-key为nationCode  value为nationName */
	public static Map<String, String> getNcValidEnumMap() {
		return ncValidEnumMap;
	}
	
	/** 获取全部国家集合-key为countryCode  value为nationName */
	public static Map<String, String> getCcEnumMap() {
		return ccEnumMap;
	}

	/** 获取有效状态国家集合-key为countryCode  value为nationName */
	public static Map<String, String> getCcValidEnumMap() {
		return ccValidEnumMap;
	}
	
	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public boolean getIsPushToMap() {
		return isPushToMap;
	}

	public void setPushToMap(boolean isPushToMap) {
		this.isPushToMap = isPushToMap;
	}
	
	
	
}
