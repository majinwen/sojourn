package com.ziroom.minsu.services.house.entity;

import com.ziroom.minsu.entity.house.HouseGuardRelEntity;

/**
 * 
 * <p>
 * 房源维护管家参数dto
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class HouseGuardVo extends HouseGuardRelEntity {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 4478762835153593998L;

	/**
	 * 房源名称
	 */
	private String houseName;

	/**
	 * 房源编号
	 */
	private String houseSn;
	
	/**
	 * 房源地址
	 */
	private String houseAddr;

	/**
	 * 房源状态
	 */
	private Integer houseStatus;
	
	/**
	 * 地推管家手机号
	 */
	@Deprecated // modified by liujun 2017-02-24
	private String empPushMobile;
	
	/**
	 * 运营专员手机号
	 */
	private String empGuardMobile; // 维护管家岗位变更为运营专员 2017-02-24
	
	/**
	 * 房东uid
	 */
	private String landlordUid;
	
	/**
	 * 房东姓名
	 */
	private String landlordName;
	
	/**
	 * 房东昵称
	 */
	private String landlordNickname;
	
	/**
	 * 房东手机号
	 */
	private String landlordMobile;

	/**
	 * 国家code
	 */
	private String nationCode;

	/**
	 * 省code
	 */
	private String provinceCode;

	
	/**
	 * 城市code
	 */
	private String cityCode;

	/**
	 * 区code
	 */
	private String areaCode;
	
	/**
	 * 房间编号
	 */
	private String roomSn;

	/**
	 * 房源渠道  1:直营, 2:房东, 3:地推'
	 */
	private Integer houseChannel;

	public Integer getHouseChannel() {
		return houseChannel;
	}

	public void setHouseChannel(Integer houseChannel) {
		this.houseChannel = houseChannel;
	}

	public String getRoomSn() {
		return roomSn;
	}

	public void setRoomSn(String roomSn) {
		this.roomSn = roomSn;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getHouseSn() {
		return houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public String getLandlordName() {
		return landlordName;
	}

	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}

	public String getLandlordNickname() {
		return landlordNickname;
	}

	public void setLandlordNickname(String landlordNickname) {
		this.landlordNickname = landlordNickname;
	}

	public String getLandlordMobile() {
		return landlordMobile;
	}

	public void setLandlordMobile(String landlordMobile) {
		this.landlordMobile = landlordMobile;
	}

	public String getHouseAddr() {
		return houseAddr;
	}

	public void setHouseAddr(String houseAddr) {
		this.houseAddr = houseAddr;
	}

	public String getEmpGuardMobile() {
		return empGuardMobile;
	}

	public void setEmpGuardMobile(String empGuardMobile) {
		this.empGuardMobile = empGuardMobile;
	}

	public String getEmpPushMobile() {
		return empPushMobile;
	}

	public void setEmpPushMobile(String empPushMobile) {
		this.empPushMobile = empPushMobile;
	}

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Integer getHouseStatus() {
		return houseStatus;
	}

	public void setHouseStatus(Integer houseStatus) {
		this.houseStatus = houseStatus;
	}
	
}
