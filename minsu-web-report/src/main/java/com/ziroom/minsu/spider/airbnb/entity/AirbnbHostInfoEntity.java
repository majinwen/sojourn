/**
 * @FileName: AirbnbHostInfo.java
 * @Package com.ziroom.minsu.spider.airbnb.entity
 * 
 * @author zl
 * @created 2016年9月30日 下午2:57:44
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.airbnb.entity;

import java.io.Serializable;
import java.util.Date;

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
public class AirbnbHostInfoEntity implements Serializable  {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3075435337895115151L;

	/**
     * 自增id
     */
    private Integer id;

    /**
     * 房东编号
     */
    private String hostSn;

    /**
     * 城市
     */
    private String city;

    /**
     * 名字
     */
    private String firstName;

    /**
     * 名字
     */
    private String lastName;

    /**
     * 是否超级房东 0：否，1：是
     */
    private Integer superHost;

    /**
     * 是否认证  0：否，1：是
     */
    private Integer auth;

    /**
     * 评价数量
     */
    private Integer reviewsCount;

    /**
     * 房东头像url
     */
    private String hostImg;

    /**
     * 创建日期
     */
    private Date createDate;
    
    
    /**
     * 房东url
     */
    private String detailUrl;
    
	/**
	 * 最后修改日期
	 */
	private Date lastModifyDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHostSn() {
		return hostSn;
	}

	public void setHostSn(String hostSn) {
		this.hostSn = hostSn;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getSuperHost() {
		return superHost;
	}

	public void setSuperHost(Integer superHost) {
		this.superHost = superHost;
	}

	public Integer getAuth() {
		return auth;
	}

	public void setAuth(Integer auth) {
		this.auth = auth;
	}

	public Integer getReviewsCount() {
		return reviewsCount;
	}

	public void setReviewsCount(Integer reviewsCount) {
		this.reviewsCount = reviewsCount;
	}

	public String getHostImg() {
		return hostImg;
	}

	public void setHostImg(String hostImg) {
		this.hostImg = hostImg;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	 
}
