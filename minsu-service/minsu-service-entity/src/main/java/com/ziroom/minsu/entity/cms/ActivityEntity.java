package com.ziroom.minsu.entity.cms;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.util.Check;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

/**
 * <p>活动主表</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年7月12日
 * @since 1.0
 * @version 1.0
 */
public class ActivityEntity extends BaseEntity {
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -5818325655058326029L;

	private Integer id;

	private String actSn;

	private String actName;
	/**
	 * 业务线标示 1：民宿 2：自如寓
	 */
	private Integer serviceLine;
	/**
	 * 客户类型 0：不限制 1：个人 2：企业
	 */
	private Integer customerType;
	/**
	 * 签约类型 0：不限制 1：新签 2：续约
	 */
	private Integer signType;
	/**
	 * 是否可以叠加 0:不能叠加 1：可以叠加
	 */
	private Integer isStack;
	/**
	 * 活动优惠费用项 1：服务费
	 */
	private Integer actCostType;

    private Integer actSource;

	private Integer actStatus;

    private String roleCode;

    private Integer actKind;

	private Integer actType;

	private Integer actLimit;

	private Integer limitTime;

	private Integer times;

	private Integer actMax;

	private Integer actCut;

	private Date actStartTime;

	private Date actEndTime;
    /**
     * 是否生成优惠券
     * @see com.ziroom.minsu.valenum.cms.IsCouponEnum
     */
    private Integer isCoupon;

	private Integer isCheckTime;

	private Date checkInTime;

	private Date checkOutTime;
	/**
	 * 是否限制房源 0=不限制 1=限制
	 */
	private Integer isLimitHouse;

	private String createId;

	private Date createTime;

	private Date lastModifyDate;

    private Integer isDel;
    
    /**
     * 活动组sn
     */
    private String groupSn;
	/**
	 * 用户组fid
	 */
	private String groupUserFid;
	/**
	 * 房源组fid
	 */
	private String groupHouseFid;

    /**
	 * @return the groupSn
	 */
	public String getGroupSn() {
		return groupSn;
	}

	/**
	 * @param groupSn the groupSn to set
	 */
	public void setGroupSn(String groupSn) {
		this.groupSn = groupSn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getActSn() {
		return actSn;
	}

	public void setActSn(String actSn) {
		this.actSn = actSn == null ? null : actSn.trim();
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName == null ? null : actName.trim();
	}

	public Integer getActSource() {
		return actSource;
	}

    public void setActSource(Integer actSource) {
        this.actSource = actSource;
    }

	public Integer getActStatus() {
		return actStatus;
	}

	public void setActStatus(Integer actStatus) {
		this.actStatus = actStatus;
	}

	public String getRoleCode() {
		return roleCode;
	}

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    public Integer getActKind() {
        return actKind;
    }

    public void setActKind(Integer actKind) {
        this.actKind = actKind;
    }

	public Integer getActType() {
		return actType;
	}

	public void setActType(Integer actType) {
		this.actType = actType;
	}

	public Integer getActLimit() {
		return actLimit;
	}

	public void setActLimit(Integer actLimit) {
		this.actLimit = actLimit;
	}

	public Integer getActMax() {
		return actMax;
	}

	public void setActMax(Integer actMax) {
		this.actMax = actMax;
	}

	public Integer getActCut() {
		return actCut;
	}

	public void setActCut(Integer actCut) {
		this.actCut = actCut;
	}

	public Date getActStartTime() {
		return actStartTime;
	}

	public void setActStartTime(Date actStartTime) {
		this.actStartTime = actStartTime;
	}

	public Date getActEndTime() {
		return actEndTime;
	}

	public void setActEndTime(Date actEndTime) {
		this.actEndTime = actEndTime;
	}

    public Integer getIsCoupon() {
        return isCoupon;
    }

    public void setIsCoupon(Integer isCoupon) {
        this.isCoupon = isCoupon;
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


	public Integer getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(Integer limitTime) {
		this.limitTime = limitTime;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Integer getIsLimitHouse() {
		return isLimitHouse;
	}

	public void setIsLimitHouse(Integer isLimitHouse) {
		this.isLimitHouse = isLimitHouse;
	}

	public Integer getServiceLine() {
		return serviceLine;
	}

	public void setServiceLine(Integer serviceLine) {
		this.serviceLine = serviceLine;
	}

	public Integer getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}

	public Integer getSignType() {
		return signType;
	}

	public void setSignType(Integer signType) {
		this.signType = signType;
	}

	public Integer getIsStack() {
		return isStack;
	}

	public void setIsStack(Integer isStack) {
		this.isStack = isStack;
	}

	public Integer getActCostType() {
		return actCostType;
	}

	public void setActCostType(Integer actCostType) {
		this.actCostType = actCostType;
	}

	public String getGroupUserFid() {
		return groupUserFid;
	}

	public void setGroupUserFid(String groupUserFid) {
		this.groupUserFid = groupUserFid;
	}

	public String getGroupHouseFid() {
		return groupHouseFid;
	}

	public void setGroupHouseFid(String groupHouseFid) {
		this.groupHouseFid = groupHouseFid;
	}
}