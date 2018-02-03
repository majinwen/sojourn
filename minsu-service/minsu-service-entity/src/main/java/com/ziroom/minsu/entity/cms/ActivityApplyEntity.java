package com.ziroom.minsu.entity.cms;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>活动申请表</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年6月29日
 * @since 1.0
 * @version 1.0
 */
public class ActivityApplyEntity extends BaseEntity {
	
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -9001687758472118667L;

	private Integer id;

	private String fid;

	private String uid;

	private String customerMoblie;

	private String customerName;

	private Integer isZlan;

    private Integer roleCode;

	private Integer applyStatus;

	private String cityCode;

	private String areaCode;

	private String houseScore;

	private Integer houseNum;

	private String remark;

	private Date createTime;

	private Date lastModifyDate;

	private Integer isDel;
	
	/**
	 * 礼包收货地址
	 */
	private String giftAddress;
	
	/**
	 * 房东介绍
	 */
	private String customerIntroduce;
	
	/**
	 * 房源故事
	 */
	private String houseStory;
	
	public String getGiftAddress() {
		return giftAddress;
	}

	public void setGiftAddress(String giftAddress) {
		this.giftAddress = giftAddress;
	}

	public String getCustomerIntroduce() {
		return customerIntroduce;
	}

	public String getHouseStory() {
		return houseStory;
	}

	public void setCustomerIntroduce(String customerIntroduce) {
		this.customerIntroduce = customerIntroduce;
	}

	public void setHouseStory(String houseStory) {
		this.houseStory = houseStory;
	}
	 
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid == null ? null : fid.trim();
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid == null ? null : uid.trim();
	}

	public String getCustomerMoblie() {
		return customerMoblie;
	}

	public void setCustomerMoblie(String customerMoblie) {
		this.customerMoblie = customerMoblie == null ? null : customerMoblie.trim();
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName == null ? null : customerName.trim();
	}

	public Integer getIsZlan() {
		return isZlan;
	}

	public void setIsZlan(Integer isZlan) {
		this.isZlan = isZlan;
	}

    public Integer getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(Integer roleCode) {
        this.roleCode = roleCode;
    }

	public Integer getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(Integer applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode == null ? null : cityCode.trim();
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode == null ? null : areaCode.trim();
	}

	public String getHouseScore() {
		return houseScore;
	}

	public void setHouseScore(String houseScore) {
		this.houseScore = houseScore == null ? null : houseScore.trim();
	}

	public Integer getHouseNum() {
		return houseNum;
	}

	public void setHouseNum(Integer houseNum) {
		this.houseNum = houseNum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
}