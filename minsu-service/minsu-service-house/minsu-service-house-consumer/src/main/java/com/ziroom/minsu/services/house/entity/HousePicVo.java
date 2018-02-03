/**
 * @FileName: HousePicVo.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author bushujie
 * @created 2016年4月13日 下午10:23:00
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import com.ziroom.minsu.entity.house.HousePicMsgEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>房源图片Vo</p>
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
public class HousePicVo {
	
	/**
	 * 房间fid
	 */
	private String roomFid;

	/**
	 * 房间名称
	 */
	private String roomName;

	/**
	 * 图片类型名称
	 */
	private String picTypeName;
	
	/**
	 * 图片类型
	 */
	private Integer picType;

	/**
	 * 图片最小上传张数
	 */
	private Integer picMinNum;
	
	/**
	 * 图片最大上传张数
	 */
	private Integer picMaxNum;

	/**
	 * 主键id
	 */
	private Integer id;

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

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	/**
	  * 图片对象集合
	  */
	private List<HousePicMsgEntity> picList=new ArrayList<HousePicMsgEntity>();
	
	 /**
	 * @return the picTypeName
	 */
	public String getPicTypeName() {
		return picTypeName;
	}

	/**
	 * @param picTypeName the picTypeName to set
	 */
	public void setPicTypeName(String picTypeName) {
		this.picTypeName = picTypeName;
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
	
	/**
	 * @return the roomFid
	 */
	public String getRoomFid() {
		return roomFid;
	}
	
	/**
	 * @param roomFid the roomFid to set
	 */
	public void setRoomFid(String roomFid) {
		this.roomFid = roomFid;
	}

	/**
	 * @return the picMaxNum
	 */
	public Integer getPicMaxNum() {
		return picMaxNum;
	}

	/**
	 * @param picMaxNum the picMaxNum to set
	 */
	public void setPicMaxNum(Integer picMaxNum) {
		this.picMaxNum = picMaxNum;
	}

	public Integer getPicMinNum() {
		return picMinNum;
	}

	public void setPicMinNum(Integer picMinNum) {
		this.picMinNum = picMinNum;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
