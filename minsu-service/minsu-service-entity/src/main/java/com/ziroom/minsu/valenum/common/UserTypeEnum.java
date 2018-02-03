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
 * <p>用户类型</p>
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
public enum UserTypeEnum {

	LANDLORD(1,"房东",1),
	TENANT(2,"房客",2),
	LANDLORD_HUAXIN(10,"房东",1),
	TENANT_HUANXIN(20,"房客",2),
	All(3,"房东和房客",3);
	
	UserTypeEnum(int userType, String userTypeName,int userCode){
		
		this.userType = userType;
		this.userTypeName = userTypeName;
		this.userCode = userCode;
	}
	
	/**
	 * code值
	 */
	private int userType;
	
	/**
	 * 类型名称
	 */
	private String userTypeName;
	
	private int userCode;

    public int getUserType() {
        return userType;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    /**
	 * @return the userCode
	 */
	public int getUserCode() {
		return userCode;
	}

	/**
     * 获取
     * @param userType
     * @return
     */
    public static UserTypeEnum getUserTypeByCode(Integer userType) {
        if(Check.NuNObj(userType)){
            return null;
        }
        for (final UserTypeEnum evaUserTypeEnum : UserTypeEnum.values()) {
            if (evaUserTypeEnum.getUserType() == userType.intValue()) {
                return evaUserTypeEnum;
            }
        }
        return null;
    }
}
