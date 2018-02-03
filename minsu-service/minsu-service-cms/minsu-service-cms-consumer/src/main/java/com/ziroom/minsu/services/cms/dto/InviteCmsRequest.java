/**
 * @FileName: InviterDetailRequest.java
 * @Package com.ziroom.minsu.services.cms.dto
 * 
 * @author loushuai
 * @created 2017年12月2日 下午2:35:31
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>邀请人详情页请求对象</p>
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
public class InviteCmsRequest extends PageRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7024352494350421236L;
	
	/**
	 * 活动码
	 */
	private String actSn;
	
	/**
	 * 活动组号
	 */
	private String groupSn;
	
	/**
	 * 用户uid
	 */
	private String uid;
	
	/**
     * 邀请码来源 0，老版邀请下单活动 1，新版邀请好友下单  默认0
     */
    private Integer inviteSource;
    
    /**
	 * 兑换积分
	 */
	private String points;
	
	/**
	 * 电话
	*/
	private String phone;

	public String getActSn() {
		return actSn;
	}

	public void setActSn(String actSn) {
		this.actSn = actSn;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Integer getInviteSource() {
		return inviteSource;
	}

	public void setInviteSource(Integer inviteSource) {
		this.inviteSource = inviteSource;
	}

	public String getGroupSn() {
		return groupSn;
	}

	public void setGroupSn(String groupSn) {
		this.groupSn = groupSn;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
