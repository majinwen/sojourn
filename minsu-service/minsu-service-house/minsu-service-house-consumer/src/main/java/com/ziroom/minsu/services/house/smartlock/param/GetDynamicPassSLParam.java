/**
 * @FileName: GetDynamicPassSLParam.java
 * @Package com.ziroom.minsu.services.house.smartlock.param
 * 
 * @author jixd
 * @created 2016年6月23日 下午12:48:00
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.smartlock.param;

/**
 * <p>TODO</p>
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
public class GetDynamicPassSLParam extends BaseSLParam {

	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = -3379725122584388069L;
	/**
	 * 房屋编号
	 */
	private String house_id;
	/**
	 * 房间编号
	 */
	private String room_id;
	
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
	
	
}
