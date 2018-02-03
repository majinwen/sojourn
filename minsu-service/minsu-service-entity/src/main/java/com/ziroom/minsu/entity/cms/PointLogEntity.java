package com.ziroom.minsu.entity.cms;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * @author yanb
 */
public class PointLogEntity extends BaseEntity {

    private static final long serialVersionUID = -4613663979970369321L;
    /**
     * 主键
     */
    private Integer id;

    /**
     * 逻辑fid
     */
    private String fid;

    /**
     * 用户uid
     */
    private String uid;

    /**
     * 初始积分
     */
    private Integer fromPoints;

    /**
     * 改变后的积分
     */
    private Integer toPoints;

    /**
     * 积分变化值
     */
    private Integer changePoints;

    /**
     * 变化种类 1:邀请好友获得积分  2:兑换优惠券消耗积分
     */
    private Integer changeType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人id
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public Integer getFromPoints() {
        return fromPoints;
    }

    public void setFromPoints(Integer fromPoints) {
        this.fromPoints = fromPoints;
    }

    public Integer getToPoints() {
        return toPoints;
    }

    public void setToPoints(Integer toPoints) {
        this.toPoints = toPoints;
    }

    public Integer getChangePoints() {
        return changePoints;
    }

    public void setChangePoints(Integer changePoints) {
        this.changePoints = changePoints;
    }

    public Integer getChangeType() {
        return changeType;
    }

    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
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
}