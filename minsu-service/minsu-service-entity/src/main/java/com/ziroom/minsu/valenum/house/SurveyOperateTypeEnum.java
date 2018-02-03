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
public enum SurveyOperateTypeEnum {
	UPDATE(1,"修改"),
	AUDIT(2,"审阅");
	
	/** code */
	private int code;
	
	/** 名称 */
	private String name;
	
	private static final Map<Integer,String> enumMap = new LinkedHashMap<Integer,String>();
	
	static {
		for (SurveyOperateTypeEnum valEnum : SurveyOperateTypeEnum.values()) {  
			enumMap.put(valEnum.getCode(), valEnum.getName());  
        }  
	}
	
	SurveyOperateTypeEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
	
	public static Map<Integer,String> getEnumMap() {
    	return enumMap;
    }
    
}
