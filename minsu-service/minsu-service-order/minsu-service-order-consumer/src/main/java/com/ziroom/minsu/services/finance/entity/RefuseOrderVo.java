package com.ziroom.minsu.services.finance.entity;

import com.ziroom.minsu.entity.order.OrderEntity;

/**
 * <p>
 * troy房东拒绝的申请预定vo
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年8月1日
 * @since 1.0
 * @version 1.0
 */
public class RefuseOrderVo extends OrderEntity {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 4415712387461499538L;

	/**
	 * 房源fid
	 */
	private String houseFid;

	/**
	 * 房源名称
	 */
	private String houseName;

	/**
	 * 城市名称
	 */
	private String cityName;

	public String getHouseFid() {
		return houseFid;
	}

	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

}
