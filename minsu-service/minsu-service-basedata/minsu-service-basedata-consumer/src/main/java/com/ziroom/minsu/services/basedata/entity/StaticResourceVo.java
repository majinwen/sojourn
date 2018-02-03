package com.ziroom.minsu.services.basedata.entity;

import com.ziroom.minsu.entity.base.StaticResourceEntity;

/**
 * 
 * <p>静态资源vo</p>
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
public class StaticResourceVo extends StaticResourceEntity {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -376932043788242030L;
	
	private String creatorName;
	
	private String mainPicParam;
	
	/**
	 * 图片房源基础地址
	 */
	private String picBaseUrl;

	/**
	 * 图片后缀
	 */
	private String picSuffix;
	
	/**
	 * 基础地址加后缀
	 */
	private String picUrl;
	
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
	

	public String getMainPicParam() {
		return mainPicParam;
	}

	public void setMainPicParam(String mainPicParam) {
		this.mainPicParam = mainPicParam;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	/**
	 * @return the picBaseUrl
	 */
	public String getPicBaseUrl() {
		return picBaseUrl;
	}

	/**
	 * @param picBaseUrl the picBaseUrl to set
	 */
	public void setPicBaseUrl(String picBaseUrl) {
		this.picBaseUrl = picBaseUrl;
	}

	/**
	 * @return the picSuffix
	 */
	public String getPicSuffix() {
		return picSuffix;
	}

	/**
	 * @param picSuffix the picSuffix to set
	 */
	public void setPicSuffix(String picSuffix) {
		this.picSuffix = picSuffix;
	}

}
