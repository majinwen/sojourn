/**
 * @FileName: HousePicBaseParamsDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * @author lusp
 * @created 2017年6月23日 下午4:55:41
 * <p>
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>房源照片基础信息</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @since 1.0
 * @version 1.0
 */
public class HousePicBaseParamsDto implements Serializable{

	private static final long serialVersionUID = 7064917277530047381L;


	/**
	 * 房源fid
	 */
	@NotNull(message="{house.base.fid.null}")
	private String houseBaseFid;

	/**
	 * 是否为默认照片：0:否,1:是
	 */
	private Integer isDefault;

	//图片fid
	@NotNull(message="房源照片fid不能为null")
	private String housePicFid;

	//图片类型
	private Integer picType;

	/**
	 *  房间fid
	 */
	private String houseRoomFid;

	//照片来源  0：app   1:troy   2：m站
	private Integer picSource;

	public Integer getPicSource() {
		return picSource;
	}

	public void setPicSource(Integer picSource) {
		this.picSource = picSource;
	}

	public String getHousePicFid() {
		return housePicFid;
	}

	public void setHousePicFid(String housePicFid) {
		this.housePicFid = housePicFid;
	}

	public Integer getPicType() {
		return picType;
	}

	public void setPicType(Integer picType) {
		this.picType = picType;
	}

	public String getHouseBaseFid() {
		return houseBaseFid;
	}

	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid;
	}

	public String getHouseRoomFid() {
		return houseRoomFid;
	}

	public void setHouseRoomFid(String houseRoomFid) {
		this.houseRoomFid = houseRoomFid;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
}
