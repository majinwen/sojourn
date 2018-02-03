package com.ziroom.minsu.services.house.photog.dto;

import java.io.Serializable;

import com.ziroom.minsu.entity.photographer.PhotographerBaseMsgEntity;
import com.ziroom.minsu.entity.photographer.PhotographerBaseMsgExtEntity;

/**
 * <p>摄影师dto</p>
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
public class PhotogDto implements Serializable{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -2909463427970089363L;
	
	private PhotographerBaseMsgEntity base;
	
	private PhotographerBaseMsgExtEntity ext;

	public PhotographerBaseMsgEntity getBase() {
		return base;
	}

	public void setBase(PhotographerBaseMsgEntity base) {
		this.base = base;
	}

	public PhotographerBaseMsgExtEntity getExt() {
		return ext;
	}

	public void setExt(PhotographerBaseMsgExtEntity ext) {
		this.ext = ext;
	}
}
