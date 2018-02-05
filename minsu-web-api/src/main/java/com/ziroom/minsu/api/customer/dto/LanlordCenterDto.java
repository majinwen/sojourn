/**
 * @FileName: LanlordCenterDto.java
 * @Package com.ziroom.minsu.api.customer.dto
 * 
 * @author yd
 * @created 2016年9月7日 上午11:17:47
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.customer.dto;

import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>房东个人中心首页 返回数据</p>
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
public class LanlordCenterDto{

	
	/**
	 * 用户实体
	 */
	private CustomerBaseMsgEntity customer;
	
	/**
	 * 是否认证
	 */
	private String isAuth;
	
	/**
	 * 400电话
	 */
	private String phone400;
	
	/**
	 * 审核状态
	 */
	private Integer authCode;
	
	/**
	 * 图片服务器地址
	 */
	private String picBaseAddrMona;
	
	/**
	 * 默认大小
	 */
	private String defaultHeadSize;
	
	/**
	 * 菜单类型
	 */
	private String menuType;
	
	/**
	 * 房东uid
	 */
	private String landlordUid;
	
	/**
	 * 用户头像
	 */
	private String userPicUrl;

	/**
	 * 是否天使房东
	 */
	private Integer isAngel = YesOrNoEnum.NO.getCode();

	/**
	 * 是否五周年
	 */
	private Integer isFive = YesOrNoEnum.NO.getCode();
	
	
	/**
	 * 待评价的订单的数量
	 */
	private Integer waitEvaNum = 0;

	/**
	 * 上传照片规则
	 */
	private List<String> photoRules = new ArrayList<>();
	
	/**
	 * 用户头像审核信息
	 */
	private String userPicAuditMsg;
	

	/**
	 * @return the userPicAuditMsg
	 */
	public String getUserPicAuditMsg() {
		return userPicAuditMsg;
	}

	/**
	 * @param userPicAuditMsg the userPicAuditMsg to set
	 */
	public void setUserPicAuditMsg(String userPicAuditMsg) {
		this.userPicAuditMsg = userPicAuditMsg;
	}

	public Integer getWaitEvaNum() {
		return waitEvaNum;
	}

	public void setWaitEvaNum(Integer waitEvaNum) {
		this.waitEvaNum = waitEvaNum;
	}

	public Integer getIsAngel() {
		return isAngel;
	}

	public void setIsAngel(Integer isAngel) {
		this.isAngel = isAngel;
	}

	public Integer getIsFive() {
		return isFive;
	}

	public void setIsFive(Integer isFive) {
		this.isFive = isFive;
	}

	/**
	 * @return the userPicUrl
	 */
	public String getUserPicUrl() {
		return userPicUrl;
	}

	/**
	 * @param userPicUrl the userPicUrl to set
	 */
	public void setUserPicUrl(String userPicUrl) {
		this.userPicUrl = userPicUrl;
	}

	/**
	 * @return the customer
	 */
	public CustomerBaseMsgEntity getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(CustomerBaseMsgEntity customer) {
		this.customer = customer;
	}

	
	/**
	 * @return the isAuth
	 */
	public String getIsAuth() {
		return isAuth;
	}

	/**
	 * @param isAuth the isAuth to set
	 */
	public void setIsAuth(String isAuth) {
		this.isAuth = isAuth;
	}

	/**
	 * @return the phone400
	 */
	public String getPhone400() {
		return phone400;
	}

	/**
	 * @param phone400 the phone400 to set
	 */
	public void setPhone400(String phone400) {
		this.phone400 = phone400;
	}

	/**
	 * @return the authCode
	 */
	public Integer getAuthCode() {
		return authCode;
	}

	/**
	 * @param authCode the authCode to set
	 */
	public void setAuthCode(Integer authCode) {
		this.authCode = authCode;
	}

	/**
	 * @return the picBaseAddrMona
	 */
	public String getPicBaseAddrMona() {
		return picBaseAddrMona;
	}

	/**
	 * @param picBaseAddrMona the picBaseAddrMona to set
	 */
	public void setPicBaseAddrMona(String picBaseAddrMona) {
		this.picBaseAddrMona = picBaseAddrMona;
	}

	/**
	 * @return the defaultHeadSize
	 */
	public String getDefaultHeadSize() {
		return defaultHeadSize;
	}

	/**
	 * @param defaultHeadSize the defaultHeadSize to set
	 */
	public void setDefaultHeadSize(String defaultHeadSize) {
		this.defaultHeadSize = defaultHeadSize;
	}

	/**
	 * @return the menuType
	 */
	public String getMenuType() {
		return menuType;
	}

	/**
	 * @param menuType the menuType to set
	 */
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	/**
	 * @return the landlordUid
	 */
	public String getLandlordUid() {
		return landlordUid;
	}

	/**
	 * @param landlordUid the landlordUid to set
	 */
	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public List<String> getPhotoRules() {
		return photoRules;
	}

	public void setPhotoRules(List<String> photoRules) {
		this.photoRules = photoRules;
	}
}
