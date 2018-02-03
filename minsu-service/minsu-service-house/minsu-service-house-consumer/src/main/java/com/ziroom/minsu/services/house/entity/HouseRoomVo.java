/**
 * @FileName: HouseRoomListVo.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author bushujie
 * @created 2016年4月2日 下午3:31:32
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

import org.apache.xbean.propertyeditor.IntegerEditor;

/**
 * 
 * <p>房东端-我的房源</p>
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
public class HouseRoomVo extends BaseEntity{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -8515156293713502049L;
	
	// 房源逻辑id
	private String houseBaseFid;

	// 房间逻辑id
	private String houseRoomFid;

	// 出租方式
	private Integer rentWay;

	// 房源状态
	private Integer houseStatus;

	// 房间状态
	private Integer roomStatus;
	
	// 房源名称
	private String houseName;
	
	// 房间名称
	private String roomName;
	
	// 默认图片地址
	private String defaultPic;
	
	// 房源未来30天预订率
	private Double houseBookRate;
	
	// 信息完整率
	private Double intactRate;
	
	// 评价星级
	private Double starRating;
	
	// 房源地址
	private String houseAddr;
	
	// 维护管家工号
	private String zoCode;
	
	// 维护管家姓名
	private String zoName;

	// 维护管家手机
	private String zoMobile;
	
	// 实际收益
	private Integer actualMoney;
	
	// 预计收益
	private Integer predictMoney; 
	
	//修改跳转标示 0：按步骤跳转，1：跳转到修改页
	private Integer isIssue;
	
	//步骤
	private Integer operateSeq;
	
	//是否可删除 0：否，1：是
	private Integer isNoDel;

	//房间类型 0:房间  1:客厅
	private Integer roomType;
	
	//预定类型
	private Integer orderType;
	
	//预定类型展示
	private String orderTypeStr;

	public Integer getRoomType() {
		return roomType;
	}

	public void setRoomType(Integer roomType) {
		this.roomType = roomType;
	}

	/**
	 * @return the isNoDel
	 */
	public Integer getIsNoDel() {
		return isNoDel;
	}

	/**
	 * @param isNoDel the isNoDel to set
	 */
	public void setIsNoDel(Integer isNoDel) {
		this.isNoDel = isNoDel;
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
	 * @return the operateSeq
	 */
	public Integer getOperateSeq() {
		return operateSeq;
	}

	/**
	 * @param operateSeq the operateSeq to set
	 */
	public void setOperateSeq(Integer operateSeq) {
		this.operateSeq = operateSeq;
	}

	/**
	 * 综合状态
	 */
	private Integer status;
	
	/**
	 * 综合状态名称
	 */
	private String statusName;
	
	/**
	 * 综合名称
	 */
	private String name;
	
	/**
	 * 浏览量
	 */
	private Integer housePv;
	
	/**
	 * 房源的评价得分  无得分显示无得分
	 */
	private String houseEvaScore; 
	
	/**
	 * 订单量
	 */
	private Integer orderNum;

	/**
	 * 房源编号
	 */
	private String houseSn;
	
	//add by jixd 08/02
	/**
	 * 房间编号
	 */
	private String roomSn;
	/**
	 * 数据最后更新时间
	 */
	private Date lastModifyDate;
	
	/**
	 * 品质审核不通过原因
	 */
	private String refuseReason;

	//是否可以预约摄影  0:不可以  1:可以
	private Integer whetherBookPhoto;
	
	private Integer photoStatus;
	
	private String photoStatName;
	
	private String bookStartTime;
	
	/**
	 *是否开启今夜特价 0：没有按钮，1：未开启，2：已开启
	 */
	private Integer isTodayDiscount;
	
	/**
	 * 今夜特价折扣描述
	 */
	private String todayDiscount;
	
	/**
	 * @return the todayDiscount
	 */
	public String getTodayDiscount() {
		return todayDiscount;
	}

	/**
	 * @param todayDiscount the todayDiscount to set
	 */
	public void setTodayDiscount(String todayDiscount) {
		this.todayDiscount = todayDiscount;
	}

	/**
	 * @return the isTodayDiscount
	 */
	public Integer getIsTodayDiscount() {
		return isTodayDiscount;
	}

	/**
	 * @param isTodayDiscount the isTodayDiscount to set
	 */
	public void setIsTodayDiscount(Integer isTodayDiscount) {
		this.isTodayDiscount = isTodayDiscount;
	}

	public String getBookStartTime() {
		return bookStartTime;
	}

	public void setBookStartTime(String bookStartTime) {
		this.bookStartTime = bookStartTime;
	}

	public Integer getPhotoStatus() {
		return photoStatus;
	}

	public void setPhotoStatus(Integer photoStatus) {
		this.photoStatus = photoStatus;
	}

	public String getPhotoStatName() {
		return photoStatName;
	}

	public void setPhotoStatName(String photoStatName) {
		this.photoStatName = photoStatName;
	}

	public Integer getWhetherBookPhoto() {
		return whetherBookPhoto;
	}

	public void setWhetherBookPhoto(Integer whetherBookPhoto) {
		this.whetherBookPhoto = whetherBookPhoto;
	}

	public String getHouseSn() {
		return houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getHouseEvaScore() {
		return houseEvaScore;
	}

	public void setHouseEvaScore(String houseEvaScore) {
		this.houseEvaScore = houseEvaScore;
	}

	/**
	 * @return the housePv
	 */
	public Integer getHousePv() {
		return housePv;
	}

	/**
	 * @param housePv the housePv to set
	 */
	public void setHousePv(Integer housePv) {
		this.housePv = housePv;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getHouseBaseFid() {
		return houseBaseFid;
	}

	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid;
	}

	public String getHouseRoomFid() {
		return houseRoomFid;
	}

	public void setHouseRoomFid(String houseRoomFid) {
		this.houseRoomFid = houseRoomFid;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public Integer getHouseStatus() {
		return houseStatus;
	}

	public void setHouseStatus(Integer houseStatus) {
		this.houseStatus = houseStatus;
	}

	public Integer getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(Integer roomStatus) {
		this.roomStatus = roomStatus;
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

	public String getDefaultPic() {
		return defaultPic;
	}

	public void setDefaultPic(String defaultPic) {
		this.defaultPic = defaultPic;
	}

	public Double getHouseBookRate() {
		return houseBookRate;
	}

	public void setHouseBookRate(Double houseBookRate) {
		this.houseBookRate = houseBookRate;
	}

	public Double getIntactRate() {
		return intactRate;
	}

	public void setIntactRate(Double intactRate) {
		this.intactRate = intactRate;
	}

	public Double getStarRating() {
		return starRating;
	}

	public void setStarRating(Double starRating) {
		this.starRating = starRating;
	}

	public String getHouseAddr() {
		return houseAddr;
	}

	public void setHouseAddr(String houseAddr) {
		this.houseAddr = houseAddr;
	}

	public String getZoName() {
		return zoName;
	}

	public void setZoName(String zoName) {
		this.zoName = zoName;
	}

	public String getZoMobile() {
		return zoMobile;
	}

	public void setZoMobile(String zoMobile) {
		this.zoMobile = zoMobile;
	}

	public Integer getActualMoney() {
		return actualMoney;
	}

	public void setActualMoney(Integer actualMoney) {
		this.actualMoney = actualMoney;
	}

	public Integer getPredictMoney() {
		return predictMoney;
	}

	public void setPredictMoney(Integer predictMoney) {
		this.predictMoney = predictMoney;
	}

	public String getZoCode() {
		return zoCode;
	}

	public void setZoCode(String zoCode) {
		this.zoCode = zoCode;
	}

	public String getRoomSn() {
		return roomSn;
	}

	public void setRoomSn(String roomSn) {
		this.roomSn = roomSn;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getOrderTypeStr() {
		return orderTypeStr;
	}

	public void setOrderTypeStr(String orderTypeStr) {
		this.orderTypeStr = orderTypeStr;
	}
	
}
