/**
 * @FileName: BeInviterInfo.java
 * @Package com.ziroom.minsu.services.cms.entity
 * 
 * @author loushuai
 * @created 2017年12月2日 下午4:16:30
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>被邀请人信息</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class BeInviterInfo extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1569638204947077623L;
	
	/**
	 * 被邀请人uid
	 */
    private String beInviterUid;
	
	/**
	 * 被邀请人头像
	 */
    private String headUrl;
    
    /**
	 * 被邀请人昵称
	 */
    private String nickName;
    
    /**
	 * 被邀请人状态
	 */
    private Integer inviteStatusCode;
    
    /**
  	 * 被邀请人状态
  	 */
    private Integer isGiveInviterPoints;
    
    
    /**
	 * 被邀请人状态展示
	 */
    private String inviteStatusName;
    
    /**
	 * 被邀请人状态展示文案
	 */
    private String inviteStatusShow;
    
    /**
	 * 预期奖金
	 */
    private String expectBonus;
    
    /**
   	 * 预期奖金展示文案
   	 */
    private String expectBonusShow;
    
    /**
   	 * 过期时间
   	 */
    private Date expiryTime;
    
    /**
   	 * 接受邀请时间
   	 */
    private Date inviteTime;
    

	public String getBeInviterUid() {
		return beInviterUid;
	}

	public void setBeInviterUid(String beInviterUid) {
		this.beInviterUid = beInviterUid;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public Integer getInviteStatusCode() {
		return inviteStatusCode;
	}

	public void setInviteStatusCode(Integer inviteStatusCode) {
		this.inviteStatusCode = inviteStatusCode;
	}

	public String getInviteStatusName() {
		return inviteStatusName;
	}

	public void setInviteStatusName(String inviteStatusName) {
		this.inviteStatusName = inviteStatusName;
	}

	public String getInviteStatusShow() {
		return inviteStatusShow;
	}

	public void setInviteStatusShow(String inviteStatusShow) {
		this.inviteStatusShow = inviteStatusShow;
	}

	public String getExpectBonus() {
		return expectBonus;
	}

	public void setExpectBonus(String expectBonus) {
		this.expectBonus = expectBonus;
	}

	public String getExpectBonusShow() {
		return expectBonusShow;
	}

	public void setExpectBonusShow(String expectBonusShow) {
		this.expectBonusShow = expectBonusShow;
	}

	public Date getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}

	public Integer getIsGiveInviterPoints() {
		return isGiveInviterPoints;
	}

	public void setIsGiveInviterPoints(Integer isGiveInviterPoints) {
		this.isGiveInviterPoints = isGiveInviterPoints;
	}

	public Date getInviteTime() {
		return inviteTime;
	}

	public void setInviteTime(Date inviteTime) {
		this.inviteTime = inviteTime;
	}
	
}
