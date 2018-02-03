/**
 * @FileName: HouseTypeLocationVo.java
 * @Package com.ziroom.minsu.services.house.issue.vo
 * 
 * @author bushujie
 * @created 2017年6月15日 下午12:05:18
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.issue.vo;

import com.ziroom.minsu.services.common.entity.FieldSelectListVo;
import com.ziroom.minsu.services.common.entity.FieldTextValueVo;
import com.ziroom.minsu.services.common.entity.FieldTextVo;

/**
 * <p>发布房源1-1和1-2Vo</p>
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
public class HouseTypeLocationVo {
	
	/**
	 * 出租方式
	 */
	private FieldSelectListVo houseRentWay =new FieldSelectListVo();
	
	/**
	 * 房源类型
	 */
	private FieldSelectListVo houseType =new FieldSelectListVo();
	
	/**
	 * 地区信息
	 */
	private FieldTextValueVo<String> regionMsg;
	/**
	 * 街道
	 */
	private FieldTextVo<String> houseStreet;
	/**
	 * 小区名称
	 */
	private FieldTextVo<String> communityName;
	/**
	 * 门牌号楼层
	 */
	private FieldTextVo<String> houseNumber;
	
	/**
	 * 百度经纬度
	 */
	private FieldTextVo<String> baiduLocation;
	/**
	 * 谷歌经纬度
	 */
	private FieldTextVo<String> googleLocation;

	/**
	 * 共享客厅文案
	 */
	private String hallContent;

	public String getHallContent() {
		return hallContent;
	}

	public void setHallContent(String hallContent) {
		this.hallContent = hallContent;
	}

	/**
	 * @return the baiduLocation
	 */
	public FieldTextVo<String> getBaiduLocation() {
		return baiduLocation;
	}
	/**
	 * @param baiduLocation the baiduLocation to set
	 */
	public void setBaiduLocation(FieldTextVo<String> baiduLocation) {
		this.baiduLocation = baiduLocation;
	}
	/**
	 * @return the googleLocation
	 */
	public FieldTextVo<String> getGoogleLocation() {
		return googleLocation;
	}
	/**
	 * @param googleLocation the googleLocation to set
	 */
	public void setGoogleLocation(FieldTextVo<String> googleLocation) {
		this.googleLocation = googleLocation;
	}
	/**
	 * @return the houseRentWay
	 */
	public FieldSelectListVo getHouseRentWay() {
		return houseRentWay;
	}
	/**
	 * @param houseRentWay the houseRentWay to set
	 */
	public void setHouseRentWay(FieldSelectListVo houseRentWay) {
		this.houseRentWay = houseRentWay;
	}
	/**
	 * @return the houseType
	 */
	public FieldSelectListVo getHouseType() {
		return houseType;
	}
	/**
	 * @param houseType the houseType to set
	 */
	public void setHouseType(FieldSelectListVo houseType) {
		this.houseType = houseType;
	}
	/**
	 * @return the regionMsg
	 */
	public FieldTextValueVo<String> getRegionMsg() {
		return regionMsg;
	}
	/**
	 * @param regionMsg the regionMsg to set
	 */
	public void setRegionMsg(FieldTextValueVo<String> regionMsg) {
		this.regionMsg = regionMsg;
	}
	/**
	 * @return the houseStreet
	 */
	public FieldTextVo<String> getHouseStreet() {
		return houseStreet;
	}
	/**
	 * @param houseStreet the houseStreet to set
	 */
	public void setHouseStreet(FieldTextVo<String> houseStreet) {
		this.houseStreet = houseStreet;
	}
	/**
	 * @return the communityName
	 */
	public FieldTextVo<String> getCommunityName() {
		return communityName;
	}
	/**
	 * @param communityName the communityName to set
	 */
	public void setCommunityName(FieldTextVo<String> communityName) {
		this.communityName = communityName;
	}
	/**
	 * @return the houseNumber
	 */
	public FieldTextVo<String> getHouseNumber() {
		return houseNumber;
	}
	/**
	 * @param houseNumber the houseNumber to set
	 */
	public void setHouseNumber(FieldTextVo<String> houseNumber) {
		this.houseNumber = houseNumber;
	}
}
