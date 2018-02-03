package com.ziroom.minsu.valenum.house;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * <p>房源实勘操作类型枚举</p>
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
public enum SurveyResultEnum {
	UNSURVEYED(0,"未实勘",false),
	CONSILIENT(1,"符合品质",true),
	UNMATCHED(2,"不符合品质",true);
	
	private int code;

	private String value;
	 
    /** 是否初始化进入Map标志 */
    private boolean isPushToMap;

	private static final Map<Integer, String> enumMap = new LinkedHashMap<Integer, String>();
	
	private static final Map<Integer, String> totalMap = new LinkedHashMap<Integer, String>();

	static {
		for (SurveyResultEnum valEnum : SurveyResultEnum.values()) {
			if (valEnum.isPushToMap()) {
				enumMap.put(valEnum.getCode(), valEnum.getValue());
			}
			totalMap.put(valEnum.getCode(), valEnum.getValue());
		}
	}

	SurveyResultEnum(int code, String value, boolean isPushToMap) {
		this.code = code;
		this.value = value;
		this.isPushToMap = isPushToMap;
	}

	public int getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	public boolean isPushToMap() {
		return isPushToMap;
	}

	public static Map<Integer, String> getEnumMap() {
		return enumMap;
	}

	public static Map<Integer, String> getTotalmap() {
		return totalMap;
	}

}
