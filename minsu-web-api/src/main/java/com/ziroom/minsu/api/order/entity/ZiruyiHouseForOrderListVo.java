/**
 * @FileName: ZiruyiHouseForOrderListVo.java
 * @Package com.ziroom.minsu.api.order.entity
 * 
 * @author zl
 * @created 2017年5月5日 下午2:32:41
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.order.entity;

import java.io.Serializable;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public class ZiruyiHouseForOrderListVo  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3748130540496793060L;
	/**
	 * 房型的业务bid
	 */
	private String houseTypeBid;
	/**
	 * 房型名称 
	 */
	private String houseName;
	/**
	 * 房型图片地址
	 */
	private String imgUrl;
	/**
	 * 此房型下有几床
	 */
	private Integer bedCount;
	public String getHouseTypeBid() {
		return houseTypeBid;
	}
	public String getHouseName() {
		return houseName;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public Integer getBedCount() {
		return bedCount;
	}
	public void setHouseTypeBid(String houseTypeBid) {
		this.houseTypeBid = houseTypeBid;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public void setBedCount(Integer bedCount) {
		this.bedCount = bedCount;
	}

}
