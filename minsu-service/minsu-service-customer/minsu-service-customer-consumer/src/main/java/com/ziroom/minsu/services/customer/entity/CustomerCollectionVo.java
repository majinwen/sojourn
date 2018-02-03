package com.ziroom.minsu.services.customer.entity;

import com.ziroom.minsu.entity.customer.CustomerCollectionEntity;

/**
 * 
 * <p>客户房源收藏信息</p>
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
public class CustomerCollectionVo extends CustomerCollectionEntity{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 3080631759794549950L;
	
	/**
	 * 房东图像基础访问地址
	 */
	private String landPicBaseUrl;
	
	/**
	 * 房东图像后缀
	 */
	private String landPicSuffix;
	
	public String getLandPicBaseUrl() {
		return landPicBaseUrl;
	}

	public void setLandPicBaseUrl(String landPicBaseUrl) {
		this.landPicBaseUrl = landPicBaseUrl;
	}

	public String getLandPicSuffix() {
		return landPicSuffix;
	}

	public void setLandPicSuffix(String landPicSuffix) {
		this.landPicSuffix = landPicSuffix;
	}
	
}
