package com.ziroom.minsu.valenum.house;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * <p>实勘图片类型枚举</p>
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
public enum SurveyPicTypeEnum {
	DEFAULT(0,"默认实勘",1,10);
	
	/** code */
	private int code;
	
	/** 名称 */
	private String name;
	
	/** 照片最小数量 */
	private int min;
	
	/** 照片最大数量 */
	private int max;
	
	private static final Map<Integer,String> enumMap = new LinkedHashMap<Integer,String>();
	
	static {
		for (SurveyPicTypeEnum valEnum : SurveyPicTypeEnum.values()) {  
			enumMap.put(valEnum.getCode(), valEnum.getName());  
        }  
	}
	
	SurveyPicTypeEnum(int code,String name,int min,int max){
		 this.code = code;
	     this.name = name;
	     this.min = min;
	     this.max = max;
	}
	
	public static SurveyPicTypeEnum getEnumByCode(int code) {
        for (final SurveyPicTypeEnum enumeration : SurveyPicTypeEnum.values()) {
            if (enumeration.getCode() == code) {
                return enumeration;
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
    
    public int getMin() {
    	return min;
    }
    
    public int getMax() {
    	return max;
    }

	public static Map<Integer,String> getEnumMap() {
    	return enumMap;
    }
}
