/**
 * @FileName: UserTypeEnum.java
 * @Package com.ziroom.minsu.valenum.common
 * 
 * @author yd
 * @created 2016年4月7日 下午10:04:01
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.common;

import com.asura.framework.base.util.Check;

/**
 * <p>请求类型</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author afi
 * @since 1.0
 * @version 1.0
 */
public enum RequestTypeEnum {

	LANDLORD(2,"房东"){
        @Override
        public boolean checkLandlord() {
           return true;
        }
    },
	TENANT(1,"房客"){
        @Override
        public boolean checkTeant() {
            return true;
        }
    };


    /**
     * 校验是否是房客
     * @return
     */
    public boolean checkTeant(){
        return false;
    }

    /**
     * 校验是否是房东
     * @return
     */
    public boolean checkLandlord(){
        return false;
    }


	RequestTypeEnum(int requestType, String requestTypeName){
		this.requestType = requestType;
		this.requestTypeName = requestTypeName;
	}
	
	/**  code值  */
	private int requestType;
	
	/** 类型名称 */
	private String requestTypeName;

    public int getRequestType() {
        return requestType;
    }

    public String getRequestTypeName() {
        return requestTypeName;
    }

    /**
     * 获取
     * @param requestType
     * @return
     */
    public static RequestTypeEnum getRequestTypeByCode(Integer requestType) {
        if(Check.NuNObj(requestType)){
            return null;
        }
        for (final RequestTypeEnum requestTypeEnum : RequestTypeEnum.values()) {
            if (requestTypeEnum.getRequestType() == requestType.intValue()) {
                return requestTypeEnum;
            }
        }
        return null;
    }
}
