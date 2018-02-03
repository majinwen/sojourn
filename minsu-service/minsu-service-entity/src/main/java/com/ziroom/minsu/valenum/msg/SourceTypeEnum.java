/**
 * @FileName: SourceTypeEnum.java
 * @Package com.ziroom.minsu.valenum.msg
 * 
 * @author yd
 * @created 2017年8月1日 下午2:36:54
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.msg;

/**
 * <p>来源</p>
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
public enum SourceTypeEnum {
	
	//1=自如驿后台添加群组 2=自如驿下单 3=自如驿退房 4=自如驿申请退款成功  0=不入库到操作失败表
	ZRY_DEFAULT(0,"不入库"),
	ZRY_ADD_GROUP_FAILED(1,"自如驿后台添加群组"),
	ZRY_ORDER_ADD_GROUP_MEMBER_FAILED(2,"自如驿办理入住"),
	ZRY_CHECKOUT_DELETE_GROUP_MEMBER_FAILED(3,"自如驿退租"),
	ZRY_REFUND_DELETE_GROUP_MEMBER_FAILED(4,"自如驿申请退款成功"),
	ZRY_DELETE_GROUP_FAILED(5,"自如驿后台删除群组"),
	ZRY_TENANT_EXIT_GROUP(6,"房客主动退群"),
	ZRY_DELETE_GROUP_MEMBER_FAILED(7,"自如驿后台移除群成员"),
	ZRY_ZO_ADD_GROUP_MEMBER(8,"自如驿后台添加群成员");
	
	SourceTypeEnum(int code,String val){
		this.code = code;
		this.val = val;
	}
	
	private int code;
	
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
	
	
	
	public static SourceTypeEnum   getSourceTypeEnumByCode(int code){
		
		for (SourceTypeEnum sourceTypeEnum : SourceTypeEnum.values()) {
			
			if(code == sourceTypeEnum.getCode()){
				return sourceTypeEnum;
			}
		}
		
		return null;
	}
	
}
