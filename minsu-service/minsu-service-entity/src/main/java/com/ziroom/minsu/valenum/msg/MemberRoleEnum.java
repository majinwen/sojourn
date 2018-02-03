/**
 * @FileName: MemberRoleEnum.java
 * @Package com.ziroom.minsu.valenum.msg
 * 
 * @author yd
 * @created 2017年8月1日 上午10:02:41
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.msg;

/**
 * <p>im角色枚举</p>
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
public enum MemberRoleEnum {

	//0=普通成员 1=管理员 2=群主
	ORDINARY_MEMBER(0,"普通成员"),
	ADMIN_MEMBER(1,"管理员"),
	OWNER_MEMBER(2,"群主");
	
	
	/**
	 * 角色code
	 */
	private int code;
	
	/**
	 * 角色描述
	 */
	private String  val;
	
	MemberRoleEnum(int code,String val){
		this.code = code;
		this.val = val;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the val
	 */
	public String getVal() {
		return val;
	}

	/**
	 * @param val the val to set
	 */
	public void setVal(String val) {
		this.val = val;
	}
	
	
	public static MemberRoleEnum   getMemberRoleEnumByCode(int code){

		for (MemberRoleEnum memberRoleEnum : MemberRoleEnum.values()) {

			if(code == memberRoleEnum.getCode()){
				return memberRoleEnum;
			}
		}
		return null;
	}
	
}
