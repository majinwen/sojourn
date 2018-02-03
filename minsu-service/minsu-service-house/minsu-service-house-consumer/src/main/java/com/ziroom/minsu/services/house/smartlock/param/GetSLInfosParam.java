/**
 * @FileName: GetSmartLockInfos.java
 * @Package com.ziroom.minsu.services.common.smartlock
 * 
 * @author jixd
 * @created 2016年6月23日 上午10:09:04
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.smartlock.param;

/**
 * <p>获取门锁详细信息</p>
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
public class GetSLInfosParam extends BaseSLParam {

	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = 5288135837014136313L;
	/**
	 * 房屋ID
	 */
	private String house_id;
	/**
	 * 房间id数组JSON字符串
	 */
	private String[] rooms;
	
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
