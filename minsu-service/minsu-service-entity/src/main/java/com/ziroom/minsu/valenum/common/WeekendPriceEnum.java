package com.ziroom.minsu.valenum.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.asura.framework.base.util.Check;


/**
 * 
 * <p>周末价格枚举</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public enum WeekendPriceEnum {
	FRI_SAT(1<<5 | 1<<6, "5,6", "周五/周六"),  
	SAT_SUN(1<<6 | 1<<7, "6,7", "周六/周日"),  
	FRI_SAT_SUN(1<<5 | 1<<6 | 1<<7, "5,6,7", "周五/周六/周日"); 
	
	private int code;
	
	private String value;
      
	private String text;
	
	private static final Map<String,String> enumMap = new LinkedHashMap<String,String>();
	
	private static final List<Map<String,Object>> enumData = new ArrayList<Map<String,Object>>();
	
	static {
		for (WeekendPriceEnum enumeration : WeekendPriceEnum.values()) {
			enumMap.put(enumeration.getValue(), enumeration.getText());
			Map<String,Object> data = new LinkedHashMap<String,Object>();
			data.put("value", enumeration.getValue());
			data.put("text", enumeration.getText());
			enumData.add(data);
		}
	}

	private WeekendPriceEnum(int code, String value, String text) {
		this.code = code;
		this.value = value;
		this.text = text;
	}
	
	public int getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public static Map<String,String> getEnumMap() {
		return enumMap;
	}
	
	public static List<Map<String, Object>> getWeekenddata() {
		return enumData;
	}
	
	private static int orOperate(Collection<Integer> coll) {
		int code = 0;
		for (Integer integer : coll) {
			integer = Check.NuNObj(integer) ? 0 : integer;
			code |= 1 << integer;
		}
		return code;
	}
	
	private static WeekendPriceEnum getEnumByCode(int code){
		for (WeekendPriceEnum enumeration : WeekendPriceEnum.values()) {
			if (code == enumeration.getCode()) {
				return enumeration;
			} 
		}
		return null;
	}
	
	public static WeekendPriceEnum getEnumByColl(Collection<Integer> coll){
		return getEnumByCode(orOperate(coll));
	}
    
}
