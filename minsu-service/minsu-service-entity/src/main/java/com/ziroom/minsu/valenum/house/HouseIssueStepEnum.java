/**
 * @FileName: HousePicTypeEnum.java
 * @Package com.ziroom.minsu.valenum.house
 * 
 * @author bushujie
 * @created 2016年4月14日 上午12:15:54
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.house;

import java.util.LinkedHashMap;
import java.util.Map;
/**
 * 
 * <p>房源发布步骤</p>
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
public enum HouseIssueStepEnum {
	// 房源发布步骤(1:15%,2:30%,3:45%,4:60%,5:75%,6:90%,7:100%)
	ONE(1,"15%",0.15),
	TWO(2,"30%",0.3),
	THREE(3,"45%",0.45),
	FOUR(4,"60%",0.6),
	FIVE(5,"75%",0.75),
	SIX(6,"90%",0.9),
	SEVEN(7,"100%",1.0);
	
	/** code */
	private int code;
	
	/** 百分比 */
	private String name;
	
	/** 值 */
	private Double value;
	
	private static final Map<Integer,String> enumMap = new LinkedHashMap<Integer,String>();
	
	static {
		for (HouseIssueStepEnum valEnum : HouseIssueStepEnum.values()) {  
			enumMap.put(valEnum.getCode(), valEnum.getName());  
        }  
	}
	
	HouseIssueStepEnum(int code,String name,Double value){
		 this.code = code;
	     this.name = name;
	     this.value=value;
	}

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
    
    /**
	 * @return the value
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Double value) {
		this.value = value;
	}


	public static Map<Integer,String> getEnumMap() {
    	return enumMap;
    }
	
    public static String getNameByCode(int code){
    	for(final HouseIssueStepEnum ose : HouseIssueStepEnum.values()){
    		if(ose.getCode()==code){
    			return ose.getName();
    		}
    	}
    	return null;
    }
    
    /**
     * 
     * 查询完整度
     *
     * @author zl
     * @created 2017年7月3日 下午4:33:19
     *
     * @param code
     * @return
     */
    public static Double getValueByCode(int code){
    	for(final HouseIssueStepEnum ose : HouseIssueStepEnum.values()){
    		if(ose.getCode()==code){
    			return ose.getValue();
    		}
    	}
    	return null;
    }
    
}
