/**
 * @FileName: ErrNoEnum.java
 * @Package com.ziroom.minsu.services.house.smartlock.enumvalue
 * 
 * @author yd
 * @created 2016年6月23日 下午7:23:25
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.smartlock.enumvalue;

/**
 * <p>智能锁 错误码列表</p>
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
public enum SmartErrNoEnum {

	SUCCESS(0,"成功"),
	TOKEN_FALSE(101,"Token无效"),
	TOKEN_EXPIRED(102,"Token过期"),
	UNIQUE_CONFLICT(11000,"无法添加重复对象"),
	PARAN_ERROR(14001,"参数有误"),
	DB_ERROR(14003,"数据库操作错误"),
	INNER_ERROR(15000,"内部错误"),
	NO_RECORD(15006,"记录不存在"),
	NOT_IMPLEMENTED_METHOD(15007,"方法未实现"),
	ROOM_REPEAT_LOCK(15008,"该房间已存在绑定的门锁"),
	HOUSE_REPEAT_LOCK(15009,"该公寓已存在绑定的中心"),
	EQUIPMENT_ALREADY_EXITS(15010,"设备记录已存在"),
	EQUIPMENT_ALREADY_BINDING(15011,"设备已被别人绑定"),
	ROOM_NOT_EXITS(15012,"房间信息不存在"),
	NOT_DELETE(15013,"管理员密码不能被删除或冻结"),
	NOT_AUTH(15014,"权限受限"),
	NOT_HAVE_GATWAY(15015,"公寓下面没有安装该网关");
	
	SmartErrNoEnum(int code,String value){
		
		this.code = code;
		this.value = value;
	}
	
	/**
	 * 枚举值
	 */
	private int code;
	
	/**
	 * 
	 */
	private String value;
	

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * 
	 * get SmartErrNoEnum by code
	 *
	 * @author yd
	 * @created 2016年6月23日 下午7:36:01
	 *
	 * @param code
	 * @return
	 */
	public static SmartErrNoEnum getSmartErrNoEnumByCode(int code){
		
		for (SmartErrNoEnum smartErrNoEnum  : SmartErrNoEnum.values()) {
			if(code == smartErrNoEnum.getCode()){
				return smartErrNoEnum;
			}
		}
		return null;
	}
	
}
