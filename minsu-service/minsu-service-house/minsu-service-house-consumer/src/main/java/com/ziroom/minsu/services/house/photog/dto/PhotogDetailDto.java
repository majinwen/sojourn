package com.ziroom.minsu.services.house.photog.dto;

import java.util.List;

import com.ziroom.minsu.entity.photographer.PhotographerBaseMsgEntity;
import com.ziroom.minsu.entity.photographer.PhotographerBaseMsgExtEntity;
import com.ziroom.minsu.entity.photographer.PhotographerBaseMsgPicEntity;


/**
 * <p>摄影师扩展dto</p>
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
public class PhotogDetailDto extends PhotographerBaseMsgEntity{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 7329443031951695410L;
	
	private PhotographerBaseMsgExtEntity ext;
	
	private List<PhotographerBaseMsgPicEntity> picList;

	public PhotographerBaseMsgExtEntity getExt() {
		return ext;
	}

	public void setExt(PhotographerBaseMsgExtEntity ext) {
		this.ext = ext;
	}

	public List<PhotographerBaseMsgPicEntity> getPicList() {
		return picList;
	}

	public void setPicList(List<PhotographerBaseMsgPicEntity> picList) {
		this.picList = picList;
	}
    
}
