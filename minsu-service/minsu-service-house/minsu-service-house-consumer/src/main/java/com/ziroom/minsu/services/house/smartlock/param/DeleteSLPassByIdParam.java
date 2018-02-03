/**
 * @FileName: DeleteSmartLockPassByIdParam.java
 * @Package com.ziroom.minsu.services.common.smartlock
 * 
 * @author jixd
 * @created 2016年6月23日 上午9:45:15
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.smartlock.param;

/**
 * <p>删除密码</p>
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
public class DeleteSLPassByIdParam extends BaseSLParam {

	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = 428411439538688004L;
	/**
	 * 回调URL
	 */
	private String callback_url;
	/**
	 * 房屋编号
	 */
	private String house_id;
	/**
	 * 房间编号
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
	 * 需要删除密码
	 */
	private String password_id;
	
	/**
	 * 密码类型：1：租客密码 2：内部员工 3：第三方
	 */
	private Integer password_type;
	
	/**
	 * 如果password_type为1，此参数为合同号；如果如果password_type为2；此参数为员工号；如果password_type为3，此参数为订单号。
	 */
	private String user_identify;
	
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

	public String getPassword_id() {
		return password_id;
	}

	public void setPassword_id(String password_id) {
		this.password_id = password_id;
	}
	
}
