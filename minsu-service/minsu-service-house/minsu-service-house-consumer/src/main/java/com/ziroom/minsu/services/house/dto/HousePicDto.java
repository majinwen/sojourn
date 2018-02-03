/**
 * @FileName: HousePicDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author bushujie
 * @created 2016年4月9日 下午5:36:24
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import com.ziroom.minsu.entity.house.HousePicMsgEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>房源图片dto</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class HousePicDto {
	
	//房源fid
	private String houseBaseFid;
	 //房间fid
	private String houseRoomFid;
	//图片类型
	private Integer picType;
	//图片fid
	private String housePicFid;
	
	private Integer isDefault;

	// 摄影师姓名
	private String cameramanName;

	// 摄影师手机号
	private String cameramanMobile;
	
	private List<String> picFidS = new ArrayList<String>();
	//上传图片列表
	private List<HousePicMsgEntity> picList=new ArrayList<HousePicMsgEntity>();
	
	//多余字段 做兼容
	private boolean successNoLogin;

	private Integer picSource;

	public Integer getOperateSource() {
		return operateSource;
	}

	public void setOperateSource(Integer operateSource) {
		this.operateSource = operateSource;
	}

	/**
	 * 新增加字段,操作来源
	 * 用于标识是否来自于运营人员的直接修改
	 * 2.业务人员 枚举类CreaterTypeEnum.GUARD
	 * @author yanb
	 * @return
	 */
	private Integer operateSource;

	//照片来源  0：app   1:troy   2:m站
	public Integer getPicSource() {
		return picSource;
	}

	public void setPicSource(Integer picSource) {
		this.picSource = picSource;
	}

	/**
	 * @return the successNoLogin
	 */
	public boolean isSuccessNoLogin() {
		return successNoLogin;
	}

	/**
	 * @param successNoLogin the successNoLogin to set
	 */
	public void setSuccessNoLogin(boolean successNoLogin) {
		this.successNoLogin = successNoLogin;
	}

	/**
	 * @return the picList
	 */
	public List<HousePicMsgEntity> getPicList() {
		return picList;
	}

	/**
	 * @param picList the picList to set
	 */
	public void setPicList(List<HousePicMsgEntity> picList) {
		this.picList = picList;
	}
	
	public List<String> getPicFidS() {
		return picFidS;
	}

	public void setPicFidS(List<String> picFidS) {
		this.picFidS = picFidS;
	}

	/**
	 * @return the housePicFid
	 */
	public String getHousePicFid() {
		return housePicFid;
	}

	/**
	 * @param housePicFid the housePicFid to set
	 */
	public void setHousePicFid(String housePicFid) {
		this.housePicFid = housePicFid;
	}

	/**
	 * @return the houseBaseFid
	 */
	public String getHouseBaseFid() {
		return houseBaseFid;
	}

	/**
	 * @param houseBaseFid the houseBaseFid to set
	 */
	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid;
	}

	/**
	 * @return the houseRoomFid
	 */
	public String getHouseRoomFid() {
		return houseRoomFid;
	}

	/**
	 * @param houseRoomFid the houseRoomFid to set
	 */
	public void setHouseRoomFid(String houseRoomFid) {
		this.houseRoomFid = houseRoomFid;
	}

	/**
	 * @return the picType
	 */
	public Integer getPicType() {
		return picType;
	}

	/**
	 * @param picType the picType to set
	 */
	public void setPicType(Integer picType) {
		this.picType = picType;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public String getCameramanName() {
		return cameramanName;
	}

	public void setCameramanName(String cameramanName) {
		this.cameramanName = cameramanName;
	}

	public String getCameramanMobile() {
		return cameramanMobile;
	}

	public void setCameramanMobile(String cameramanMobile) {
		this.cameramanMobile = cameramanMobile;
	}
}
