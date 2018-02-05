 package com.ziroom.minsu.api.evaluate.dto;

import com.ziroom.minsu.api.common.dto.BaseParamDto;

/**
 * <p>评价管理请求参数</p>
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
public class EvaOrderApiRequest extends BaseParamDto{

	
	/**
     * 订单编号
     */
    private String orderSn;

    /**
     * 评价状态(1=待审核 2=审核失败 3=审核成功)
     */
    private Integer evaStatu;

    /**
     * 评价人uid(即创建人uid)
     */
    private String evaUserUid;

    /**
     * 被评人uid
     */
    private String ratedUserUid;

    /**
     * 评价人类型（房东=1 房客=2）
     */
    private Integer evaUserType;

    /**
     * 房源fid
     */
    private String houseFid;

    /**
     * 房间fid
     */
    private String roomFid;

    /**
     * 床fid
     */
    private String bedFid;
	/**
	 * 是否删除 0：否，1：是
	 */
	private Integer isDel;
	
	/**
	 * 房东对房客的评价内容
	 */
	private String tenContent;
	
	/**
	 * 房源类型
	 */
	private Integer rentWay;
	
	/**
	 * 房客对房源的评价
	 */
	private String laeContent;
	
	/**
	 * 订单是否已评价标识 0=订单未修改成已评价，1=代表订单已修改成已评价
	 */
	private Integer orderEvaFlag;
	
	/**
	 * 起始时间
	 */
	private String startTime;
	
	/**
	 * 结束时间
	 */
	private String endTime;
	
	/**
	 * 评价内容
	 */
	private String content;
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public Integer getEvaStatu() {
		return evaStatu;
	}
	public void setEvaStatu(Integer evaStatu) {
		this.evaStatu = evaStatu;
	}
	public String getEvaUserUid() {
		return evaUserUid;
	}
	public void setEvaUserUid(String evaUserUid) {
		this.evaUserUid = evaUserUid;
	}
	public String getRatedUserUid() {
		return ratedUserUid;
	}
	public void setRatedUserUid(String ratedUserUid) {
		this.ratedUserUid = ratedUserUid;
	}
	public Integer getEvaUserType() {
		return evaUserType;
	}
	public void setEvaUserType(Integer evaUserType) {
		this.evaUserType = evaUserType;
	}
	public String getHouseFid() {
		return houseFid;
	}
	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}
	public String getRoomFid() {
		return roomFid;
	}
	public void setRoomFid(String roomFid) {
		this.roomFid = roomFid;
	}
	public String getBedFid() {
		return bedFid;
	}
	public void setBedFid(String bedFid) {
		this.bedFid = bedFid;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public String getTenContent() {
		return tenContent;
	}
	public void setTenContent(String tenContent) {
		this.tenContent = tenContent;
	}
	public String getLaeContent() {
		return laeContent;
	}
	public void setLaeContent(String laeContent) {
		this.laeContent = laeContent;
	}
	public Integer getRentWay() {
		return rentWay;
	}
	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}
	public Integer getOrderEvaFlag() {
		return orderEvaFlag;
	}
	public void setOrderEvaFlag(Integer orderEvaFlag) {
		this.orderEvaFlag = orderEvaFlag;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	

}
