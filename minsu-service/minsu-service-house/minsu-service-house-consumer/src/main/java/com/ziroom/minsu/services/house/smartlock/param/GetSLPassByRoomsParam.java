/**
 * @FileName: GetSmartLockPassParam.java
 * @Package com.ziroom.minsu.services.common.smartlock
 * 
 * @author jixd
 * @created 2016年6月23日 上午9:53:27
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.smartlock.param;

/**
 * <p>获取多个房间的门锁密码列表</p>
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
public class GetSLPassByRoomsParam extends BaseSLParam {

	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = 8910151819480715396L;
	/**
	 * 密码类型：1：租客密码 2：内部员工 3：第三方
	 */
	private Integer password_type;
	/**
	 * 为空的话使用op_userid来获取
	 */
	private String user_identify;
	/**
	 * 房屋id
	 */
	private String house_id;
	/**
	 * 房间id数组JSON字符串（需要外门锁的话也需要加入house_id）
	 */
	private String[] rooms;
	
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
	public String getHouse_id() {
		return house_id;
	}
	public void setHouse_id(String house_id) {
		this.house_id = house_id;
	}
	public String[] getRooms() {
		return rooms;
	}
	public void setRooms(String[] rooms) {
		this.rooms = rooms;
	}
	
	
}
