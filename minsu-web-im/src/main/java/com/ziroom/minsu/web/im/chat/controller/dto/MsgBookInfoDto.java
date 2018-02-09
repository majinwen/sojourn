package com.ziroom.minsu.web.im.chat.controller.dto;



/**
 * <p>IM预定信息实体</p>
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
public class MsgBookInfoDto{

	/**
	 * 序列id
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = -1535732251373808266L;
	
	/**
	 * 房源fid或者roomId
	 */
	private String fid;
	
	/**
	 * 出租方式
	 */
	private Integer rentWay;
	
	/**
	 * 开始时间
	 */
	private String startTime;
	
	/**
	 * 结束时间
	 */
	private String endTime;
	
	/**
	 * 房东姓名
	 */
	private String lName;
	
	/**
	 * 旅游目的
	 */
	private String tripPurpose;
	
	/**
	 * 预定人数
	 */
	private int peopleNum;
	
	

	/**
	 * @return the peopleNum
	 */
	public int getPeopleNum() {
		return peopleNum;
	}

	/**
	 * @param peopleNum the peopleNum to set
	 */
	public void setPeopleNum(int peopleNum) {
		this.peopleNum = peopleNum;
	}

	/**
	 * @return the fid
	 */
	public String getFid() {
		return fid;
	}

	/**
	 * @param fid the fid to set
	 */
	public void setFid(String fid) {
		this.fid = fid;
	}

	/**
	 * @return the rentWay
	 */
	public Integer getRentWay() {
		return rentWay;
	}

	/**
	 * @param rentWay the rentWay to set
	 */
	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the lName
	 */
	public String getlName() {
		return lName;
	}

	/**
	 * @param lName the lName to set
	 */
	public void setlName(String lName) {
		this.lName = lName;
	}

	/**
	 * @return the tripPurpose
	 */
	public String getTripPurpose() {
		return tripPurpose;
	}

	/**
	 * @param tripPurpose the tripPurpose to set
	 */
	public void setTripPurpose(String tripPurpose) {
		this.tripPurpose = tripPurpose;
	}


}
