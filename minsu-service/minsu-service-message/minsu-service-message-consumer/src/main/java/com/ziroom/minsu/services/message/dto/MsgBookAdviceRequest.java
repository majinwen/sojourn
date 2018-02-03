package com.ziroom.minsu.services.message.dto;

import java.io.Serializable;

/**
 * 联系房东预定咨询参数
 * 
 * @author zl
 * @version 1.0
 * @since 1.0
 */
public class MsgBookAdviceRequest  implements Serializable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -6929965845600542628L;
 
	/**
     * 房源留言关联表fid
     */
    private String msgHouseFid;  
    
    /**
     * 入住时间
     */
    private String startTime;
    
    /**
     * 退房时间
     */
    private String endTime;
    
    /**
     * 入住人数
     */
    private int peopleNum;
    
    /**
     * 出行目的
     */
    private String tripPurpose;

    /**
     * 创建时间
     */
    private String createTime;
    
    /**
     * 房源名称
     */
    private String houseName;
    
    /**
     * 房源url
     */
    private String housePicUrl;
    
	/**
	 * @return the houseName
	 */
	public String getHouseName() {
		return houseName;
	}

	/**
	 * @param houseName the houseName to set
	 */
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	/**
	 * @return the housePicUrl
	 */
	public String getHousePicUrl() {
		return housePicUrl;
	}

	/**
	 * @param housePicUrl the housePicUrl to set
	 */
	public void setHousePicUrl(String housePicUrl) {
		this.housePicUrl = housePicUrl;
	}

	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getMsgHouseFid() {
		return msgHouseFid;
	}

	public void setMsgHouseFid(String msgHouseFid) {
		this.msgHouseFid = msgHouseFid;
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

	public int getPeopleNum() {
		return peopleNum;
	}

	public void setPeopleNum(int peopleNum) {
		this.peopleNum = peopleNum;
	}

	public String getTripPurpose() {
		return tripPurpose;
	}

	public void setTripPurpose(String tripPurpose) {
		this.tripPurpose = tripPurpose;
	}
    
}
