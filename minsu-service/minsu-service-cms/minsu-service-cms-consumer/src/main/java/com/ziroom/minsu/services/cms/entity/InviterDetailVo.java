/**
 * @FileName: InviterDetailVo.java
 * @Package com.ziroom.minsu.services.cms.entity
 * 
 * @author loushuai
 * @created 2017年12月2日 下午4:09:32
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.entity;

import java.util.List;

/**
 * <p>邀请人详情页vo</p>
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
public class InviterDetailVo {

	/**
	 * 可兑换积分
	 */
	//private String activityContent;
	
	/**
	 * 可兑换积分
	 */
	//private String activityDetailRule;
	
	/**
	 * 可兑换现金券（可兑换积分*积分兑换现金券比例）
	 */
	private String canExchangeCash;
	
	/**
     *  总计积分（对应页面“总计多少元”，积分已经兑换多少优惠券）
     */
    private String sumCash;
	
	
	/**
	 * 已经成功邀请的好友（成功邀请好友数指已入住、可加分的好友数）
	 */
	private String successInviteFriends;
	
	/**
	 * 邀请的总人数
	 */
	private Long count;
	
	/**
	 * 所有被邀请的好友的uid集合
	 */
	private List<String> beInviteUidList;
	
	/**
	 *尚未给邀请人增加积分的uid集合
	 */
	private List<String> hasnotGivePointsUidList;
	
	/**
	 * 所有被邀请的好友的信息集合
	 */
	private List<BeInviterInfo> beInviterInfoList;
	
	/**
	 * 尚未给邀请人增加积分的信息集合
	 */
	private List<BeInviterInfo> hasnotGivePointsList;
	
	/**
	 * 已经给邀请人给邀请人增加积分的信息集合
	 */
	private List<BeInviterInfo> hasGivePointsList;

	public String getSumCash() {
		return sumCash;
	}

	public void setSumCash(String sumCash) {
		this.sumCash = sumCash;
	}

	public String getCanExchangeCash() {
		return canExchangeCash;
	}

	public void setCanExchangeCash(String canExchangeCash) {
		this.canExchangeCash = canExchangeCash;
	}

	public String getSuccessInviteFriends() {
		return successInviteFriends;
	}

	public void setSuccessInviteFriends(String successInviteFriends) {
		this.successInviteFriends = successInviteFriends;
	}

	public List<String> getBeInviteUidList() {
		return beInviteUidList;
	}

	public void setBeInviteUidList(List<String> beInviteUidList) {
		this.beInviteUidList = beInviteUidList;
	}

	public List<BeInviterInfo> getBeInviterInfoList() {
		return beInviterInfoList;
	}

	public void setBeInviterInfoList(List<BeInviterInfo> beInviterInfoList) {
		this.beInviterInfoList = beInviterInfoList;
	}

	public List<BeInviterInfo> getHasnotGivePointsList() {
		return hasnotGivePointsList;
	}

	public void setHasnotGivePointsList(List<BeInviterInfo> hasnotGivePointsList) {
		this.hasnotGivePointsList = hasnotGivePointsList;
	}

	public List<BeInviterInfo> getHasGivePointsList() {
		return hasGivePointsList;
	}

	public void setHasGivePointsList(List<BeInviterInfo> hasGivePointsList) {
		this.hasGivePointsList = hasGivePointsList;
	}

	public List<String> getHasnotGivePointsUidList() {
		return hasnotGivePointsUidList;
	}

	public void setHasnotGivePointsUidList(List<String> hasnotGivePointsUidList) {
		this.hasnotGivePointsUidList = hasnotGivePointsUidList;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}
