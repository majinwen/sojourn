package com.ziroom.minsu.valenum.house;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * <p>被单更换枚举</p>
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
public enum SheetReplaceEnum {

	CUSTOMER(0,"每客一换",true),
	DAY(1,"每日一换",true);

    SheetReplaceEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    /** code */
    private int code;
    
    /** 名称 */
    private String name;
    
    /** 是否初始化进入Map标志 */
    private boolean isPushToMap;

	private static final Map<Integer,String> enumMap = new LinkedHashMap<Integer,String>();
	
	static {
		for (SheetReplaceEnum valEnum : SheetReplaceEnum.values()) {
			if(valEnum.getIsPushToMap()){
				enumMap.put(valEnum.getCode(), valEnum.getName());  
			}
        }  
	}

	SheetReplaceEnum(int code, String name, boolean isPushToMap) {
        this.code = code;
        this.name = name;
        this.isPushToMap = isPushToMap;
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
	
}
