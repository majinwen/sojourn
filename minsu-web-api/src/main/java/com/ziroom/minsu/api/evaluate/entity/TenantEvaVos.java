package com.ziroom.minsu.api.evaluate.entity;

import java.io.Serializable;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.valenum.house.RentWayEnum;

/**
 * <p>房客端评价返回实体</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class TenantEvaVos implements Serializable{
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -6861976171885852945L;

	/** 订单编号*/
	private String orderSn;

	/** 房源名称 */
	private String houseName;

	/** 房间名称  */
	private String roomName;

	/** 床编号  */
	private String bedSn;

	/** 房源地址  */
	private String houseAddr;

	/** 房源图片路径 */
	private String picUrl;

	/**
	 * 出租类型
	 * @see RentWayEnum
	 */
	private Integer rentWay;

	/** 出租价格 */
	private Integer price;
	/**住房实际时长（单位：晚）*/
	private Integer housingDay;
	
	/**预订人姓名*/
	private String userName;
	   /** 订单开始时间 */
    private String startTimeStr;

    /** 订单结束时间 */
    private String endTimeStr;
    
    /**
     * 评价状态200：不可评价 100 待评价 101 用户已评价 110 房东已评价 111 都已经评价
     */
    private Integer evaStatus;
    /**
	 * 入住人数
	 */
	private Integer peopleNum;

	/**
	 * 用户图片地址
	 */
	private String userPicUrl;
	
	/**
	 * 根据当前的出租类型 判断fid  
	 */
	private String fid;
	
	/**
	 *房东 审核状态 (0=待评价 给客户端默认的  1=待审核 2=系统下线 3=人工下线 4=已发布 5=已举报)
	 */
	private Integer lanEvaStatu;
	/**
	 *房客 审核状态(0=待评价 给客户端默认的  1=待审核 2=系统下线 3=人工下线 4=已发布 5=已举报)
	 */
	private Integer tenEvaStatu;
	
	/**
	 * 状态提示
	 */
	private String evaTips;
	
	/**
	 * 房东uid
	 */
	private String landlordUid;
	
	/**
	 * 房客uid
	 */
	private String userUid;
	
	public String getEvaTips() {
		return evaTips;
	}

	public void setEvaTips(String evaTips) {
		this.evaTips = evaTips;
	}

	public Integer getLanEvaStatu() {
		return lanEvaStatu;
	}

	public void setLanEvaStatu(Integer lanEvaStatu) {
		this.lanEvaStatu = lanEvaStatu;
	}

	public Integer getTenEvaStatu() {
		return tenEvaStatu;
	}

	public void setTenEvaStatu(Integer tenEvaStatu) {
		this.tenEvaStatu = tenEvaStatu;
	}

	/**
	 * 
	 * 获取fid
	 *
	 * @author yd
	 * @created 2016年5月16日 上午12:47:10
	 *
	 * @return
	 */
	public String returnFid(String houseFid,String roomFid,String bedFid){
		
		if(Check.NuNObj(this.rentWay)){
			return null;
		}
		if(this.rentWay.intValue() == RentWayEnum.HOUSE.getCode()){
			return houseFid;
		}
		if(this.rentWay.intValue() == RentWayEnum.ROOM.getCode()){
			this.setHouseName(this.getRoomName());
			return roomFid;
		}
		if(this.rentWay.intValue() == RentWayEnum.BED.getCode()){
			return bedFid;
		}
		return null;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public Integer getPeopleNum() {
		return peopleNum;
	}

	public void setPeopleNum(Integer peopleNum) {
		this.peopleNum = peopleNum;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getBedSn() {
		return bedSn;
	}

	public void setBedSn(String bedSn) {
		this.bedSn = bedSn;
	}

	public String getHouseAddr() {
		return houseAddr;
	}

	public void setHouseAddr(String houseAddr) {
		this.houseAddr = houseAddr;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getHousingDay() {
		return housingDay;
	}

	public void setHousingDay(Integer housingDay) {
		this.housingDay = housingDay;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	

	public Integer getEvaStatus() {
		return evaStatus;
	}

	public void setEvaStatus(Integer evaStatus) {
		this.evaStatus = evaStatus;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}


	public String getUserPicUrl() {
		return userPicUrl;
	}

	public void setUserPicUrl(String userPicUrl) {
		this.userPicUrl = userPicUrl;
	}

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}
	
}
