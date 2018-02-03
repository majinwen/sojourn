/**
 * @FileName: ImgVo.java
 * @Package com.ziroom.minsu.api.common.dto
 * 
 * @author jixd
 * @created 2016年5月27日 上午12:00:50
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.dto;

import java.io.Serializable;

/**
 * <p>返回图片的对象</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class ImgDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2622700297250891914L;
	/**
	 * 图片类型
	 */
	private Integer picType;
	/**
	 * 图片uuid
	 */
	private String uuid;
	/**
	 * 图片url
	 */
	private String url;
	/**
	 * 图片基本路径
	 */
	private String urlBase;
	/**
	 * 图片后缀
	 */
	private String urlExt;
	/**
	 * 图片原名称
	 */
	private String originalFilename;
	public Integer getPicType() {
		return picType;
	}
	public void setPicType(Integer picType) {
		this.picType = picType;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrlBase() {
		return urlBase;
	}
	public void setUrlBase(String urlBase) {
		this.urlBase = urlBase;
	}
	public String getUrlExt() {
		return urlExt;
	}
	public void setUrlExt(String urlExt) {
		this.urlExt = urlExt;
	}
	public String getOriginalFilename() {
		return originalFilename;
	}
	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}
	
}
