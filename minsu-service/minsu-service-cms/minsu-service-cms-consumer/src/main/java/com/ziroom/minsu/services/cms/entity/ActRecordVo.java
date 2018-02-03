/**
 * @FileName: ActRecordVo.java
 * @Package com.ziroom.minsu.services.cms.entity
 * 
 * @author yd
 * @created 2016年10月11日 下午2:38:45
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>活动领取记录 列表数据</p>
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
public class ActRecordVo  extends BaseEntity{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -4208770414078290106L;
	
	/**
	 * 用户手机号
	 */
	private  String userMobile;
	
	/**
	 * 当前礼物是否已被领取 0=未领取 默认 1=已领取
	 */
	private Integer isPickUp;
	
	/**
	 * 领取记录创建时间
	 */
	private Date createDate;
	
	/**
	 * 领取人uid
	 */
	private String userUid;
	
	/**
	 * 礼物领取备注
	 */
	private String axRemark;
	
	/**
	 * 用户填写的用户姓名
	 */
	private String userName;
	
	/**
	 * 当前根据用户uid查询的用户姓名
	 */
	private String userCurName;
	
	/**
	 * 用户填写地址
	 */
	private String userAdress;
	
	/**
	 * 活动组编号
	 */
	private String groupSn;
	
	/**
	 * 活动编号
	 */
	private String actSn;
	
	/**
	 * 活动名称
	 */
	private String actName;
	
	/**
	 * 活动来源
	 */
	private String actSource;
	
	/**
	 * 活动来源民称
	 */
	private String actSourceName;
	
	/**
	 * 礼物名称
	 */
	private String giftName;
	
	/**
	 * 礼物备注
	 */
	private String giftRemark;
	
	/**
	 * 活动类型
	 */
	private Integer actType;
	
	/**
	 * 活动类型名称
	 */
	private String actTypeName;
	
	
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
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	 * @return the axRemark
	 */
	public String getAxRemark() {
		return axRemark;
	}

	/**
	 * @param axRemark the axRemark to set
	 */
	public void setAxRemark(String axRemark) {
		this.axRemark = axRemark;
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

	/**
	 * @return the userCurName
	 */
	public String getUserCurName() {
		return userCurName;
	}

	/**
	 * @param userCurName the userCurName to set
	 */
	public void setUserCurName(String userCurName) {
		this.userCurName = userCurName;
	}

	/**
	 * @return the userAdress
	 */
	public String getUserAdress() {
		return userAdress;
	}

	/**
	 * @param userAdress the userAdress to set
	 */
	public void setUserAdress(String userAdress) {
		this.userAdress = userAdress;
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
	public String getActSource() {
		return actSource;
	}

	/**
	 * @param actSource the actSource to set
	 */
	public void setActSource(String actSource) {
		this.actSource = actSource;
	}

	/**
	 * @return the actSourceName
	 */
	public String getActSourceName() {
		return actSourceName;
	}

	/**
	 * @param actSourceName the actSourceName to set
	 */
	public void setActSourceName(String actSourceName) {
		this.actSourceName = actSourceName;
	}

	/**
	 * @return the giftName
	 */
	public String getGiftName() {
		return giftName;
	}

	/**
	 * @param giftName the giftName to set
	 */
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	/**
	 * @return the giftRemark
	 */
	public String getGiftRemark() {
		return giftRemark;
	}

	/**
	 * @param giftRemark the giftRemark to set
	 */
	public void setGiftRemark(String giftRemark) {
		this.giftRemark = giftRemark;
	}

	/**
	 * @return the actType
	 */
	public Integer getActType() {
		return actType;
	}

	/**
	 * @param actType the actType to set
	 */
	public void setActType(Integer actType) {
		this.actType = actType;
	}

	/**
	 * @return the actTypeName
	 */
	public String getActTypeName() {
		return actTypeName;
	}

	/**
	 * @param actTypeName the actTypeName to set
	 */
	public void setActTypeName(String actTypeName) {
		this.actTypeName = actTypeName;
	}
	
	

}
