/**
 * @FileName: HouseBaseDetailVo.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author bushujie
 * @created 2016年5月26日 上午11:06:11
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;

/**
 * <p>录入房源房源详情</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class HouseBaseDetailVo extends HouseBaseMsgEntity {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 4447596283421295396L;
	
	/**
	 * 入住人数限制 0：不限制
	 */
	private Integer checkInLimit;
	/**
	 * 房源描述
	 */
	private String houseDesc;
	/**
	 * 是否与房东同住 0：否，1：是
	 */
	private Integer isTogetherLandlord;
	
	/**
	 * 是否发布房源流程 0：否，1：是
	 */
	private Integer isIssue=0;
	
	/**
	 * 出租期限字符串
	 */
	private String tillDateStr;
	
	/**
	 * 周边状况
	 */
	private String houseAroundDesc;
	
	/**
	 * 房源fid （统一接口参数名称）
	 */
	private String houseBaseFid;
	
	/**
	 * 分租房间fid
	 */
	private String roomFid;

	
	/**
	 * @return the roomFid
	 */
	public String getRoomFid() {
		return roomFid;
	}
	/**
	 * @param roomFid the roomFid to set
	 */
	public void setRoomFid(String roomFid) {
		this.roomFid = roomFid;
	}
	/**
	 * @return the houseBaseFid
	 */
	public String getHouseBaseFid() {
		return houseBaseFid;
	}
	/**
	 * @param houseBaseFid the houseBaseFid to set
	 */
	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid;
	}
	public String getHouseAroundDesc() {
		return houseAroundDesc;
	}
	public void setHouseAroundDesc(String houseAroundDesc) {
		this.houseAroundDesc = houseAroundDesc;
	}
	public String getTillDateStr() {
		return tillDateStr;
	}
	public void setTillDateStr(String tillDateStr) {
		this.tillDateStr = tillDateStr;
	}
	/**
	 * @return the isIssue
	 */
	public Integer getIsIssue() {
		return isIssue;
	}
	/**
	 * @param isIssue the isIssue to set
	 */
	public void setIsIssue(Integer isIssue) {
		this.isIssue = isIssue;
	}
	/**
	 * @return the checkInLimit
	 */
	public Integer getCheckInLimit() {
		return checkInLimit;
	}
	/**
	 * @param checkInLimit the checkInLimit to set
	 */
	public void setCheckInLimit(Integer checkInLimit) {
		this.checkInLimit = checkInLimit;
	}
	/**
	 * @return the houseDesc
	 */
	public String getHouseDesc() {
		return houseDesc;
	}
	/**
	 * @param houseDesc the houseDesc to set
	 */
	public void setHouseDesc(String houseDesc) {
		this.houseDesc = houseDesc;
	}
	/**
	 * @return the isTogetherLandlord
	 */
	public Integer getIsTogetherLandlord() {
		return isTogetherLandlord;
	}
	/**
	 * @param isTogetherLandlord the isTogetherLandlord to set
	 */
	public void setIsTogetherLandlord(Integer isTogetherLandlord) {
		this.isTogetherLandlord = isTogetherLandlord;
	}
}
