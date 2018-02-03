package com.ziroom.minsu.valenum.house;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * <p>房源实勘状态类型枚举</p>
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
public enum SurveyRecordStatusEnum {
	DRAFT(0,"草稿"),
	NORMAL(1,"正常"),
	DELETE(2,"删除");
	
	private int code;

	private String value;

	private static final Map<Integer, String> enumMap = new LinkedHashMap<Integer, String>();

	static {
		for (SurveyRecordStatusEnum valEnum : SurveyRecordStatusEnum.values()) {
			enumMap.put(valEnum.getCode(), valEnum.getValue());
		}
	}

	SurveyRecordStatusEnum(int code, String value) {
		this.code = code;
		this.value = value;
	}

	public int getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	public static Map<Integer, String> getEnumMap() {
		return enumMap;
	}

}
