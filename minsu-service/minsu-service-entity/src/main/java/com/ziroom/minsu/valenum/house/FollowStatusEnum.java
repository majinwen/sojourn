/**
 * @FileName: HouseStatusEnum.java
 * @Package com.ziroom.minsu.valenum.house
 * 
 * @author bushujie
 * @created 2016年4月2日 下午8:56:30
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.house;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * <p>跟进类型</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public enum FollowStatusEnum {
	KFDGJ(101,"客服待跟进"){
		public boolean isServiceFollowEnd(){
			return true;
		}
	},
	KFGJZ(102,"客服跟进中"){
		public boolean isServiceFollowEnd(){
			return true;
		}
	},
	KFWLXSFD(103,"客服未联系上房东"),
	KFGJJS(104,"客服跟进结束"),
	ZYDGJ(201,"专员待跟进"),
	ZYGJZ(202,"专员跟进中"),
	ZYGJJS(203,"专员跟进结束"),
	XTJS(301,"系统结束"),
	CSJS(302,"超时终结");

	/** code */
	private int code;
	
	/** 名称 */
	private String name;

	
	/**
	 * 
	 *客服是否可以跟进结束
	 *
	 * @author yd
	 * @created 2017年4月20日 上午10:21:58
	 *
	 * @return
	 */
	public boolean isServiceFollowEnd(){
		return false;
	}

	private static final Map<Integer,String> enumMap = new LinkedHashMap<Integer,String>();
	
	static {
		for (FollowStatusEnum houseStatusEnum : FollowStatusEnum.values()) {  
			enumMap.put(houseStatusEnum.getCode(), houseStatusEnum.getName());  
        }  
	}
	
	FollowStatusEnum(int code,String name){
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
	
	public static FollowStatusEnum getFollowStatusEnumByCode(int code){
		   for (final FollowStatusEnum valEnum : FollowStatusEnum.values()) {
	            if (valEnum.getCode() == code) {
	                return valEnum;
	            }
	        }
	        return null;
	}
}
