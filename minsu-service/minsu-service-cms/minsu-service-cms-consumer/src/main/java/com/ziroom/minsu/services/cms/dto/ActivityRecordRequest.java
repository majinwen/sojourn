/**
 * @FileName: ActivityRecordRequest.java
 * @Package com.ziroom.minsu.services.cms.dto
 * 
 * @author yd
 * @created 2016年10月9日 下午2:04:52
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.dto;

import java.util.Date;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>活动记录请求参数</p>
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
public class ActivityRecordRequest extends PageRequest{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -6882519028119526915L;
	

    /**
     * 活动组sn
     */
    private String groupSn;

    /**
     * 活动编码
     */
    private String actSn;

    /**
     * 用户uid
     */
    private String userUid;

    /**
     * 用户手机号
     */
    private String userMobile;

    /**
     * 礼品fid
     */
    private String giftFid;

    /**
     * 当前礼物是否已被领取  0=未领取 默认  1=已领取
     */
    private Integer isPickUp;

    /**
     * 免佣金开始时间
     */
    private Date startTime;

    /**
     * 免佣金结束时间
     */
    private Date endTime;

    /**
     * 用户姓名
     */
    private String userName;
    
    /**
     * 活动名称
     */
    private String actName;
    
    /**
	 * 活动来源
	 */
	private Integer actSource;
	
	/**
	 * 活动类型
	 */
	private Integer actKind;
	
	
	

	/**
	 * @return the actKind
	 */
	public Integer getActKind() {
		return actKind;
	}

	/**
	 * @param actKind the actKind to set
	 */
	public void setActKind(Integer actKind) {
		this.actKind = actKind;
	}

	/**
	 * @return the actName
	 */
	public String getActName() {
		return actName;
	}

	/**
	 * @param actName the actName to set
	 */
	public void setActName(String actName) {
		this.actName = actName;
	}


	/**
	 * @return the actSource
	 */
	public Integer getActSource() {
		return actSource;
	}

	/**
	 * @param actSource the actSource to set
	 */
	public void setActSource(Integer actSource) {
		this.actSource = actSource;
	}

	/**
	 * @return the groupSn
	 */
	public String getGroupSn() {
		return groupSn;
	}

	/**
	 * @param groupSn the groupSn to set
	 */
	public void setGroupSn(String groupSn) {
		this.groupSn = groupSn;
	}

	/**
	 * @return the actSn
	 */
	public String getActSn() {
		return actSn;
	}

	/**
	 * @param actSn the actSn to set
	 */
	public void setActSn(String actSn) {
		this.actSn = actSn;
	}

	/**
	 * @return the userUid
	 */
	public String getUserUid() {
		return userUid;
	}

	/**
	 * @param userUid the userUid to set
	 */
	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	/**
	 * @return the userMobile
	 */
	public String getUserMobile() {
		return userMobile;
	}

	/**
	 * @param userMobile the userMobile to set
	 */
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	/**
	 * @return the giftFid
	 */
	public String getGiftFid() {
		return giftFid;
	}

	/**
	 * @param giftFid the giftFid to set
	 */
	public void setGiftFid(String giftFid) {
		this.giftFid = giftFid;
	}

	/**
	 * @return the isPickUp
	 */
	public Integer getIsPickUp() {
		return isPickUp;
	}

	/**
	 * @param isPickUp the isPickUp to set
	 */
	public void setIsPickUp(Integer isPickUp) {
		this.isPickUp = isPickUp;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

   
    
    

}
