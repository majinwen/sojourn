/**
 * @FileName: LockInfosSlVo.java
 * @Package com.ziroom.minsu.services.house.smartlock.vo
 * 
 * @author jixd
 * @created 2016年6月23日 下午12:36:10
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.smartlock.vo;

/**
 * <p>智能锁详细信息</p>
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
public class LockInfosSlVo extends BaseSLVo {

	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = 3718791016977240219L;
	/**
	 * 房间id
	 */
	private String room_id;
	/**
	 * 房间code
	 */
	private String room_code;
	/**
	 * 门锁在线状态1：在线2：离线
	 */
	private Integer onoff_line; 
	/**
	 * 最近一次在线状态更新时间戳，单位ms
	 */
	private Integer onoff_time;
	/**
	 * 门锁的电量信息，-1表示未知，0-100
	 */
	private Integer power;
	/**
	 * 最近一次电量更新时间戳，单位ms
	 */
	private Integer power_refreshtime;
	/**
	 * 设备注册时间时间戳，单位ms
	 */
	private Integer bind_time;
	/**
	 * 门锁uuid
	 */
	private String uuid;
	/**
	 * 门锁绑定的网关
	 */
	private String gateway;
	/**
	 * 供应商ID
	 */
	private String manufactory_id;
	/**
	 * “home_lock”: 外门锁 ”room_lock”：内门锁
	 */
	private String device_type;
	/**
	 * 供应商设备自定义字段，界面转化成String显示
	 */
	private String extra;
	public String getRoom_id() {
		return room_id;
	}
	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}
	public String getRoom_code() {
		return room_code;
	}
	public void setRoom_code(String room_code) {
		this.room_code = room_code;
	}
	public Integer getOnoff_line() {
		return onoff_line;
	}
	public void setOnoff_line(Integer onoff_line) {
		this.onoff_line = onoff_line;
	}
	public Integer getOnoff_time() {
		return onoff_time;
	}
	public void setOnoff_time(Integer onoff_time) {
		this.onoff_time = onoff_time;
	}
	public Integer getPower() {
		return power;
	}
	public void setPower(Integer power) {
		this.power = power;
	}
	public Integer getPower_refreshtime() {
		return power_refreshtime;
	}
	public void setPower_refreshtime(Integer power_refreshtime) {
		this.power_refreshtime = power_refreshtime;
	}
	public Integer getBind_time() {
		return bind_time;
	}
	public void setBind_time(Integer bind_time) {
		this.bind_time = bind_time;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public String getManufactory_id() {
		return manufactory_id;
	}
	public void setManufactory_id(String manufactory_id) {
		this.manufactory_id = manufactory_id;
	}
	public String getDevice_type() {
		return device_type;
	}
	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	
}
