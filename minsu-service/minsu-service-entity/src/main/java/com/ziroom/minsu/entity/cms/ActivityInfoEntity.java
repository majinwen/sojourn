package com.ziroom.minsu.entity.cms;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>活动信息表</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年6月23日
 * @since 1.0
 * @version 1.0
 */
public class ActivityInfoEntity extends BaseEntity {
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -2755808152351739758L;

	private Integer id;

    private String activitySn;

    private String activityName;

	private String cityCode;

    private Integer activityStatus;

	private String userRole;

	private Integer activityType;

    private Integer activityLimit;

    private Integer activityCut;

	private Date startTime;

	private Date endTime;

	private Integer isCheckTime;

	private Date checkInTime;

	private Date checkOutTime;

	private String createId;

	private Date createTime;

	private Date lastModifyDate;

	private Integer isDel;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public String getActivitySn() {
        return activitySn;
    }

    public void setActivitySn(String activitySn) {
        this.activitySn = activitySn == null ? null : activitySn.trim();
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName == null ? null : activityName.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public Integer getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(Integer activityStatus) {
        this.activityStatus = activityStatus;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole == null ? null : userRole.trim();
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public Integer getActivityLimit() {
        return activityLimit;
    }

    public void setActivityLimit(Integer activityLimit) {
        this.activityLimit = activityLimit;
    }

    public Integer getActivityCut() {
        return activityCut;
    }

    public void setActivityCut(Integer activityCut) {
        this.activityCut = activityCut;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getIsCheckTime() {
        return isCheckTime;
    }

    public void setIsCheckTime(Integer isCheckTime) {
        this.isCheckTime = isCheckTime;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Date getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Date checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId == null ? null : createId.trim();
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