/**
 * @FileName: HousePhyPcDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author bushujie
 * @created 2016年8月6日 下午2:34:15
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import com.ziroom.minsu.entity.house.HouseGuardRelEntity;
import com.ziroom.minsu.entity.house.HousePhyMsgEntity;

/**
 * <p>保存位置信息dto</p>
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
public class HousePhyPcDto extends HousePhyMsgEntity{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 3338826224100161818L;
	
	/**
	 * 街道信息
	 */
	private String houseStreet;
	
	/**
	 * 房源fid
	 */
	private String houseBaseFid;
	
	/**
	 * 城市名称
	 */
	private String cityName;
	
	/**
	 * 区域名称
	 */
	private String areaName;
	
	/**
	 * 登录用户uid
	 */
	private String uid;
	
	/**
	 * 房源出租类型(0:整租,1:合租,2:床位)
	 */
	private Integer rentWay;

    /**
     * 房屋类型
     */
    private Integer houseType;
    
    /**
     * 楼号门牌号
     */
 	private String detailAddress;
 	
 	/**
 	 * 房源渠道
 	 */
 	private Integer houseChannel;
 	
	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public Integer getHouseType() {
		return houseType;
	}

	public void setHouseType(Integer houseType) {
		this.houseType = houseType;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}



	/**
	 * 房源管家信息
	 */
	private HouseGuardRelEntity houseGuardRel;

	/**
	 * @return the houseGuardRel
	 */
	public HouseGuardRelEntity getHouseGuardRel() {
		return houseGuardRel;
	}

	/**
	 * @param houseGuardRel the houseGuardRel to set
	 */
	public void setHouseGuardRel(HouseGuardRelEntity houseGuardRel) {
		this.houseGuardRel = houseGuardRel;
	}

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * @param areaName the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * @return the houseStreet
	 */
	public String getHouseStreet() {
		return houseStreet;
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

	/**
	 * @param houseStreet the houseStreet to set
	 */
	public void setHouseStreet(String houseStreet) {
		this.houseStreet = houseStreet;
	}

	public Integer getHouseChannel() {
		return houseChannel;
	}

	public void setHouseChannel(Integer houseChannel) {
		this.houseChannel = houseChannel;
	}
	
	
}
