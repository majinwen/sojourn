/**
 * @FileName: AppChatRecordsExt.java
 * @Package com.ziroom.minsu.web.im.chat.controller.dto
 * 
 * @author yd
 * @created 2016年9月21日 上午11:42:59
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>app 消息的扩展信息</p>
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
public class AppChatRecordsExt implements Serializable{


	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 9111259971041349225L;

	/**
	 * 房源或房间fid
	 */
	private String fid;

	/**
	 * 出租方式 0=整租  1 = 分租
	 */
	private Integer rentWay;
	
	/**
	 * 房间类型 ：共享客厅 或者 独立房间   
	 */
	private String rentWayName;
	
	/**
	 * 起始日期
	 */
	private String startDate;
	
	/**
	 * 结束日期
	 */
	private String endDate;
	
	/**
	 * 环信 同步民宿消息标识 （民宿：）
	 */
	private String ziroomFlag;
	
	/**
	 * 入住人数
	 */
	private String personNum;
	
	/**
	 * 房源名称
	 */
	private String houseName;
	
	/**
	 * 房源url
	 */
	private String housePicUrl;
	
	/**
	 * 房源卡 代表是：房客点击联系房东 填写完信息 进来的
	 */
	private String houseCard;
	
	/**
	 * 环信 消息id
	 */
	private String huanxinMsgId;

	/**
	 * 分享房源信息
	 */
	private List<ShareHouseMsg> shareHouseMsg = new ArrayList<>();

	/**
	 * 同步环信聊天记录环境标识 （minsu_d  minsu_t  minsu_q minsu_online）
	 */
	private String domainFlag;

	/**
	 * 消息类型 0=普通消息 1=首次咨询 2=分享卡片 3=自动回复
	 */
	private String msgType;
	/**
	 * 角色类型 1=房东 2=房客 3=房东和房客
	 */
	private Integer roleType;
	
	/**
	 * 来源1=安卓 2=IOS
	 */
	private Integer source;
	
    /**
     * 2017.11.27版本，增加木木的表情
     */
    private String em_expr_big_name;

	/**
	 * @return the source
	 */
	public Integer getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(Integer source) {
		this.source = source;
	}

	/**
	 * @return the domainFlag
	 */
	public String getDomainFlag() {
		return domainFlag;
	}

	/**
	 * @param domainFlag the domainFlag to set
	 */
	public void setDomainFlag(String domainFlag) {
		this.domainFlag = domainFlag;
	}

	/**
	 * @return the huanxinMsgId
	 */
	public String getHuanxinMsgId() {
		return huanxinMsgId;
	}

	/**
	 * @param huanxinMsgId the huanxinMsgId to set
	 */
	public void setHuanxinMsgId(String huanxinMsgId) {
		this.huanxinMsgId = huanxinMsgId;
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
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the ziroomFlag
	 */
	public String getZiroomFlag() {
		return ziroomFlag;
	}

	/**
	 * @param ziroomFlag the ziroomFlag to set
	 */
	public void setZiroomFlag(String ziroomFlag) {
		this.ziroomFlag = ziroomFlag;
	}

	/**
	 * @return the personNum
	 */
	public String getPersonNum() {
		return personNum;
	}

	/**
	 * @param personNum the personNum to set
	 */
	public void setPersonNum(String personNum) {
		this.personNum = personNum;
	}

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
	 * @return the houseCard
	 */
	public String getHouseCard() {
		return houseCard;
	}

	/**
	 * @param houseCard the houseCard to set
	 */
	public void setHouseCard(String houseCard) {
		this.houseCard = houseCard;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public List<ShareHouseMsg> getShareHouseMsg() {
		return shareHouseMsg;
	}

	public void setShareHouseMsg(List<ShareHouseMsg> shareHouseMsg) {
		this.shareHouseMsg = shareHouseMsg;
	}

	public String getEm_expr_big_name() {
		return em_expr_big_name;
	}

	public void setEm_expr_big_name(String em_expr_big_name) {
		this.em_expr_big_name = em_expr_big_name;
	}

	public String getRentWayName() {
		return rentWayName;
	}

	public void setRentWayName(String rentWayName) {
		this.rentWayName = rentWayName;
	}

}
