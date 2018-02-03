/**
 * @FileName: GiftTypeEnum.java
 * @Package com.ziroom.minsu.valenum.cms
 * 
 * @author yd
 * @created 2016年10月9日 上午11:08:46
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.cms;

import java.util.*;

/**
 * <p>活动礼物类型</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public enum GiftTypeEnum {

	 //1：免佣金 2：礼品实物 
	NO_LOAD(1,"免佣金"),
    GIFT_THING(2,"礼品实物");
	
	  /** code */
    private int code;

    /** 名称 */
    private String name;
    
	private static final Map<Integer, String> enumMap = new LinkedHashMap<Integer, String>();

	static {
		for (GiftTypeEnum valEnum : GiftTypeEnum.values()) {
			enumMap.put(valEnum.getCode(), valEnum.getName());
		}
	}

	/**
	 * 将当前的枚举转化成list
	 * @author afi
	 * @return
	 */
	public static List<Map<String, Object> >  getList(){
		List<Map<String, Object>> rst = new ArrayList<>();
		for (GiftTypeEnum valEnum : GiftTypeEnum.values()) {
			Map<String,Object> ele = new HashMap<>();
			ele.put("code",valEnum.getCode());
			ele.put("name",valEnum.getName());
			rst.add(ele);
		}
		return rst;
	}

	
	GiftTypeEnum(int code, String name){
		
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
