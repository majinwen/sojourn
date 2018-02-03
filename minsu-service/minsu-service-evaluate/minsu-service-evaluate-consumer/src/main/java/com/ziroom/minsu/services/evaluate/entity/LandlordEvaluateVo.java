/**
 * @FileName: LandlordEvaluateVo.java
 * @Package com.ziroom.minsu.services.evaluate.entity
 * 
 * @author yd
 * @created 2016年4月7日 下午2:33:15
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.entity;

import java.util.Date;

import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.TenantEvaluateEntity;

/**
 * <p>评价返回vo</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class LandlordEvaluateVo extends EvaluateOrderEntity{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -7695980105505527943L;
	
	/**
	 * 房东评价内容
	 */
	private String content;
	
	/**
	 * 房东对房客的满意度
	 */
	private Integer landlordSatisfied;

    /**
     * 是否删除 0：否，1：是
     */
    private Integer isDel;
    
    /**
     * 最后修改时间
     */
    private Date lastModifyDate;
    /**
     * 房源名称
     */
	private String houseName;
	
	/**
	 * 房源照片地址
	 */
	private String picUrl;
	/**
	 * 房东头像
	 */
	private String landlordPicUrl;
	
	/**
	 * 房东昵称
	 */
	private String landlordNickName;
	
	/**
	 * 入住起始时间
	 */
	private String startTimeStr;
	
	/**
	 * 入住结束时间
	 */
	private String endTimeStr;
    
    /**
     *房客对房源的评价
     */
    private TenantEvaluateEntity tenantEvaluateEntity;
    
    /**
     * 房客 图片
     */
    private String userPicUrl;

    
	public String getLandlordNickName() {
		return landlordNickName;
	}

	public void setLandlordNickName(String landlordNickName) {
		this.landlordNickName = landlordNickName;
	}

	public String getUserPicUrl() {
		return userPicUrl;
	}

	public void setUserPicUrl(String userPicUrl) {
		this.userPicUrl = userPicUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getLandlordSatisfied() {
		return landlordSatisfied;
	}

	public void setLandlordSatisfied(Integer landlordSatisfied) {
		this.landlordSatisfied = landlordSatisfied;
	}


	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public TenantEvaluateEntity getTenantEvaluateEntity() {
		return tenantEvaluateEntity;
	}

	public void setTenantEvaluateEntity(TenantEvaluateEntity tenantEvaluateEntity) {
		this.tenantEvaluateEntity = tenantEvaluateEntity;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public String getLandlordPicUrl() {
		return landlordPicUrl;
	}

	public void setLandlordPicUrl(String landlordPicUrl) {
		this.landlordPicUrl = landlordPicUrl;
	}

}
