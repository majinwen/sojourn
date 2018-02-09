/**
 * @FileName: MsgCheckErrorLogDto.java
 * @Package com.ziroom.minsu.services.message.dto
 * 
 * @author loushuai
 * @created 2018年1月23日 下午4:25:18
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.web.im.chat.controller.dto;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>为了验证环信登录问题，配合app，记录他们传过来的日志</p>
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
public class MsgCheckErrorLogDto  extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5919184132683278077L;
	
	/**
	 * uid
	 */
	private String uid;
	
	/**
	 * 环信id
	 */
	private String huanxinId;
	
	/**
	 * 设备标识
	 */
	private String equipmentIden;
	
	/**
	 * 系统标识（ios还是安卓）
	 */
	private String systemIden;

	/**
	 * 手机型号
	 */
	private String phoneModel;
	
	/**
	 * 手机品牌
	 */
	private String phoneBrand;
	
	/**
	 * 手机系统
	 */
	private String osVersion;
	
	/**
	 * 网络环境
	 */
	private String etWork;
	
	/**
	 *app版本
	 */
	private String versionCode;
	
	/**
	 * 登录用时
	 */
	private String loginConsumeTime;
	
	/**
	 * 失败类型（登录失败，注销失败）
	 */
	private String failType;
	
	/**
	 * 错误描述
	 */
	private String errorDescribe;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getHuanxinId() {
		return huanxinId;
	}

	public void setHuanxinId(String huanxinId) {
		this.huanxinId = huanxinId;
	}

	public String getEquipmentIden() {
		return equipmentIden;
	}

	public void setEquipmentIden(String equipmentIden) {
		this.equipmentIden = equipmentIden;
	}

	public String getSystemIden() {
		return systemIden;
	}

	public void setSystemIden(String systemIden) {
		this.systemIden = systemIden;
	}

	public String getPhoneModel() {
		return phoneModel;
	}

	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}

	public String getPhoneBrand() {
		return phoneBrand;
	}

	public void setPhoneBrand(String phoneBrand) {
		this.phoneBrand = phoneBrand;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getEtWork() {
		return etWork;
	}

	public void setEtWork(String etWork) {
		this.etWork = etWork;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getLoginConsumeTime() {
		return loginConsumeTime;
	}

	public void setLoginConsumeTime(String loginConsumeTime) {
		this.loginConsumeTime = loginConsumeTime;
	}

	public String getFailType() {
		return failType;
	}

	public void setFailType(String failType) {
		this.failType = failType;
	}

	public String getErrorDescribe() {
		return errorDescribe;
	}

	public void setErrorDescribe(String errorDescribe) {
		this.errorDescribe = errorDescribe;
	}

}
