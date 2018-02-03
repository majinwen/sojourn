/**
 * @FileName: HousePicListVo.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author bushujie
 * @created 2016年5月28日 下午4:13:04
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

/**
 * <p>图片列表vo</p>
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
public class HousePicListVo {
	
	/**
	 * 图片fid
	 */
	private String fid;
	/**
	 * 图片访问地址
	 */
	private String picUrl;

	/**
	 * 图片缩略图访问地址
	 */
	private String minPicUrl;

	/**
	 * 图片类型
	 */
	private Integer picType;
	/**
	 * 图片是否默认 0：否，1：是
	 */
	private Integer isDefault;
	
    // 宽度像素：属性值为正整数，单位是pixel
    private Integer widthPixel;
    
	// 高度像素：属性值为正整数，单位是pixel
    private Integer heightPixel;

    /**
	 * @return the widthPixel
	 */
	public Integer getWidthPixel() {
		return widthPixel;
	}
	/**
	 * @param widthPixel the widthPixel to set
	 */
	public void setWidthPixel(Integer widthPixel) {
		this.widthPixel = widthPixel;
	}
	/**
	 * @return the heightPixel
	 */
	public Integer getHeightPixel() {
		return heightPixel;
	}
	/**
	 * @param heightPixel the heightPixel to set
	 */
	public void setHeightPixel(Integer heightPixel) {
		this.heightPixel = heightPixel;
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
	 * @return the picUrl
	 */
	public String getPicUrl() {
		return picUrl;
	}
	/**
	 * @param picUrl the picUrl to set
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
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
	/**
	 * @return the isDefault
	 */
	public Integer getIsDefault() {
		return isDefault;
	}
	/**
	 * @param isDefault the isDefault to set
	 */
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public String getMinPicUrl() {
		return minPicUrl;
	}

	public void setMinPicUrl(String minPicUrl) {
		this.minPicUrl = minPicUrl;
	}
}
