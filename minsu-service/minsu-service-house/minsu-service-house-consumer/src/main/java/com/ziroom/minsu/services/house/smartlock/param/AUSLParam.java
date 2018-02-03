/**
 * @FileName: AUSmartLockParam.java
 * @Package com.ziroom.minsu.services.common.smartlock
 * 
 * @author jixd
 * @created 2016年6月22日 下午7:07:51
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.smartlock.param;

/**
 * <p>增加修改智能锁参数</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class AUSLParam extends BaseSLParam {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 5795532684813047680L;
	/**
	 * 异步操作结果回调地址
	 */
	private String callback_url;
	/**
	 * 房屋编号
	 */
	private String house_id;
	/**
	 * 房间编号数组
	 */
	private String room_id;
	/**
	 * 供应商id，智能锁前端调用此接口，此参数为必须
	 */
	private String manufactory_id;
	/**
	 * 设备uuid，智能锁前端调用此接口，此参数为必须
	 */
	private String uuid;
	/**
	 * 开锁密码（6位），数字字符串。如果不带此参数，则后台随机生成密码。
	 */
	private String password;
	/**
	 * 密码使用者的名字
	 */
	private String user_name;
	/**
	 * 密码使用者电话号码
	 */
	private String user_phone;
	/**
	 * 密码类型：1：租客密码 2：内部员工 3：第三方
	 */
	private Integer password_type;
	/**
	 * 如果password_type为1，此参数为合同号；如果如果password_type为2；此参数为员工号；如果password_type为3，此参数为订单号。
	 */
	private String user_identify;
	/**
	 * 密码生成成功后，是否下发短信给用户手机，手机号为user_phone1：不发送2：发送
	 */
	private Integer is_send_sms;
	/**
	 * 密码有效期的开始时间，UTC时间，精确到S
	 */
	private PermissionTimeParam permission;
	public String getCallback_url() {
		return callback_url;
	}
	public void setCallback_url(String callback_url) {
		this.callback_url = callback_url;
	}
	public String getHouse_id() {
		return house_id;
	}
	public void setHouse_id(String house_id) {
		this.house_id = house_id;
	}
	public String getRoom_id() {
		return room_id;
	}
	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}
	public String getManufactory_id() {
		return manufactory_id;
	}
	public void setManufactory_id(String manufactory_id) {
		this.manufactory_id = manufactory_id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	public Integer getPassword_type() {
		return password_type;
	}
	public void setPassword_type(Integer password_type) {
		this.password_type = password_type;
	}
	public String getUser_identify() {
		return user_identify;
	}
	public void setUser_identify(String user_identify) {
		this.user_identify = user_identify;
	}
	public Integer getIs_send_sms() {
		return is_send_sms;
	}
	public void setIs_send_sms(Integer is_send_sms) {
		this.is_send_sms = is_send_sms;
	}
	public PermissionTimeParam getPermission() {
		return permission;
	}
	public void setPermission(PermissionTimeParam permission) {
		this.permission = permission;
	}
	
}
