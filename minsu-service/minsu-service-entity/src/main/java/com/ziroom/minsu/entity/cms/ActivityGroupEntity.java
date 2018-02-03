package com.ziroom.minsu.entity.cms;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>活动组实体</p>
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
public class ActivityGroupEntity extends BaseEntity{
    /**
	 * 序列id
	 */
	private static final long serialVersionUID = 6474876331499856474L;

	/**
     * 主键id
     */
    private Integer id;

    /**
     * 逻辑id
     */
    private String fid;
    
    /**
     * 活动组类型 0：普通活动，1：条件活动
     */
    private Integer groupType;

    /**
     * 组编号
     */
    private String groupSn;

    /**
     * 组名称
     */
    private String groupName;

    /**
     * 是否可以单独活动中重复领取
     */
    private Integer isRepeat;

    /**
     * 备注
     */
    private String remark;
    
    /**
     * 活动组限制领取次数 0：不限制
     */
    private Integer groupLimitNum;
    
    /**
     * 是否有效 0：否，1：是
     */
    private Integer isValid;
    

	/**
     * 是否条件限制 0：否，1：是 已过期和已使用是否可再次领取
     */
    private Integer isValidLimit;

    /**
     * 创建人
     */
    private String createId;

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

    public String getGroupSn() {
        return groupSn;
    }

    public void setGroupSn(String groupSn) {
        this.groupSn = groupSn == null ? null : groupSn.trim();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public Integer getIsRepeat() {
        return isRepeat;
    }

    public void setIsRepeat(Integer isRepeat) {
        this.isRepeat = isRepeat;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
    
    /**
	 * @return the groupType
	 */
	public Integer getGroupType() {
		return groupType;
	}

	/**
	 * @param groupType the groupType to set
	 */
	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

	/**
	 * @return the groupLimitNum
	 */
	public Integer getGroupLimitNum() {
		return groupLimitNum;
	}

	/**
	 * @param groupLimitNum the groupLimitNum to set
	 */
	public void setGroupLimitNum(Integer groupLimitNum) {
		this.groupLimitNum = groupLimitNum;
	}

	/**
	 * @return the isValid
	 */
	public Integer getIsValid() {
		return isValid;
	}

	/**
	 * @param isValid the isValid to set
	 */
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	/**
	 * @return the isValidLimit
	 */
	public Integer getIsValidLimit() {
		return isValidLimit;
	}

	/**
	 * @param isValidLimit the isValidLimit to set
	 */
	public void setIsValidLimit(Integer isValidLimit) {
		this.isValidLimit = isValidLimit;
	}
}