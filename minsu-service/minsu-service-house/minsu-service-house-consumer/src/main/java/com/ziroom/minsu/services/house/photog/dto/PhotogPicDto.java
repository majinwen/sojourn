package com.ziroom.minsu.services.house.photog.dto;

import java.io.Serializable;

/**
 * <p>摄影师图片dto</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class PhotogPicDto implements Serializable{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 6419284602471463480L;
	
	private String photographerUid;
	
	private Integer picType;

	public String getPhotographerUid() {
		return photographerUid;
	}

	public void setPhotographerUid(String photographerUid) {
		this.photographerUid = photographerUid;
	}

	public Integer getPicType() {
		return picType;
	}

	public void setPicType(Integer picType) {
		this.picType = picType;
	}
}
