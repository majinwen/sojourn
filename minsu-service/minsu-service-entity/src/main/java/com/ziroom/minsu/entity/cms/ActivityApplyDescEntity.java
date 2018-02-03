package com.ziroom.minsu.entity.cms;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class ActivityApplyDescEntity extends BaseEntity {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -1165904799538206906L;

	/**
     * 主键id
     */
    private Integer id;

    /**
     * 逻辑fid
     */
    private String fid;

    /**
     * 活动基础信息fid
     */
    private String activityApplyFid;

    /**
     * 房东自我介绍
     */
    private String customerIntroduce;

    /**
     * 房源故事
     */
    private String houseStory;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

    /**
     * 是否删除 0：否，1：是
     */
    private Integer isDel;

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

    public String getActivityApplyFid() {
        return activityApplyFid;
    }

    public void setActivityApplyFid(String activityApplyFid) {
        this.activityApplyFid = activityApplyFid == null ? null : activityApplyFid.trim();
    }

    public String getCustomerIntroduce() {
        return customerIntroduce;
    }

    public void setCustomerIntroduce(String customerIntroduce) {
        this.customerIntroduce = customerIntroduce == null ? null : customerIntroduce.trim();
    }

    public String getHouseStory() {
        return houseStory;
    }

    public void setHouseStory(String houseStory) {
        this.houseStory = houseStory == null ? null : houseStory.trim();
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