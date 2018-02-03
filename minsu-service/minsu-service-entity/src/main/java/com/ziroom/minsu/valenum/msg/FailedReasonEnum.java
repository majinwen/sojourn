/**
 * @FileName: FailedReasonEnum.java
 * @Package com.ziroom.minsu.valenum.msg
 * 
 * @author yd
 * @created 2017年8月1日 下午2:27:01
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.msg;

/**
 * <p>群主操作失败原因</p>
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
public enum FailedReasonEnum {

	//1=添加群组失败 （uid为群主的uid）2=添加群成员失败 3=删除群成员失败
	ADD_GROUP_FAILED(1,"添加群组失败"),
	ADD_GROUP_MEMBER_FAILED(2,"加群成员失败"),
	DELETE_GROUP_MEMBER_FAILED(3,"删除群成员失败"),
	DELETE_GROUP_FAILED(4,"删除群失败");
	
	FailedReasonEnum(int code,String val){
		
		this.code = code;
		this.val = val;
	}
	
	/**
	 * 编号
	 */
	private int code;
	
	/**
	 * 描述
	 */
	private String val;

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
	
	
	public static FailedReasonEnum   getFailedReasonEnumByCode(int code){

		for (FailedReasonEnum failedReasonEnum : FailedReasonEnum.values()) {

			if(code == failedReasonEnum.getCode()){
				return failedReasonEnum;
			}
		}
		return null;
	}
	
}
