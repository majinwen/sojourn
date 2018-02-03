/**
 * @FileName: HousePhyListDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author bushujie
 * @created 2016年4月12日 下午6:16:39
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>楼盘列表参数dto</p>
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
public class HousePhyListDto extends PageRequest{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1150760659968435029L;

	// 小区名称
	private String communityName;
	
	// 管家姓名
	private String zoName;
	
	// 管家手机
	private String zoMobile;
	
	// 国家code
	private String nationCode;

	// 省code
	private String provinceCode;

	// 城市code
	private String cityCode;

	// 区code
	private String areaCode;
	
	//新楼盘fid
	private String newPhyFid;
	
	//旧楼盘fid
	private String oldPhyFid;

	/**
	 * @return the newPhyFid
	 */
	public String getNewPhyFid() {
		return newPhyFid;
	}

	/**
	 * @param newPhyFid the newPhyFid to set
	 */
	public void setNewPhyFid(String newPhyFid) {
		this.newPhyFid = newPhyFid;
	}

	/**
	 * @return the oldPhyFid
	 */
	public String getOldPhyFid() {
		return oldPhyFid;
	}

	/**
	 * @param oldPhyFid the oldPhyFid to set
	 */
	public void setOldPhyFid(String oldPhyFid) {
		this.oldPhyFid = oldPhyFid;
	}

	/**
	 * @return the communityName
	 */
	public String getCommunityName() {
		return communityName;
	}

	/**
	 * @param communityName the communityName to set
	 */
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	/**
	 * @return the zoName
	 */
	public String getZoName() {
		return zoName;
	}

	/**
	 * @param zoName the zoName to set
	 */
	public void setZoName(String zoName) {
		this.zoName = zoName;
	}

	/**
	 * @return the zoMobile
	 */
	public String getZoMobile() {
		return zoMobile;
	}

	/**
	 * @param zoMobile the zoMobile to set
	 */
	public void setZoMobile(String zoMobile) {
		this.zoMobile = zoMobile;
	}

	/**
	 * @return the nationCode
	 */
	public String getNationCode() {
		return nationCode;
	}

	/**
	 * @param nationCode the nationCode to set
	 */
	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	/**
	 * @return the provinceCode
	 */
	public String getProvinceCode() {
		return provinceCode;
	}

	/**
	 * @param provinceCode the provinceCode to set
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	/**
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param areaCode the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
}
