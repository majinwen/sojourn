package com.ziroom.minsu.entity.cms;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

public class PointTiersEntity extends BaseEntity {

    private static final long serialVersionUID = 1895585492596503270L;
    /**
     * 主键
     */
    private Integer id;

    /**
     * 逻辑fid
     */
    private String fid;

    /**
     * 阶梯规则的活动类型 1. 邀请好友活动
     */
    private Integer tiersType;

    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 该档次对应积分
     */
    private Integer points;

    /**
     * 最低数量
     */
    private Integer minNum;

    /**
     * 最高数量
     */
    private Integer maxNum;

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

    public Integer getTiersType() {
        return tiersType;
    }

    public void setTiersType(Integer tiersType) {
        this.tiersType = tiersType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getMinNum() {
        return minNum;
    }

    public void setMinNum(Integer minNum) {
        this.minNum = minNum;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
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

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}
    
}