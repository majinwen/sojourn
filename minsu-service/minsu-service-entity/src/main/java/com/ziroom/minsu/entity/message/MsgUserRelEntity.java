package com.ziroom.minsu.entity.message;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class MsgUserRelEntity extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 931457829095081414L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 业务编号
     */
    private String fid;

    /**
     * 发送人uid
     */
    private String fromUid;

    /**
     * 接收人uid
     */
    private String toUid;

    /**
     * 客户间关系状态（0=可聊 1=不可聊）
     */
    private Integer relStatus;

    /**
     * 屏蔽来源（0=屏蔽，1=投诉）
     */
    private Integer sourceType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 自如网标识 ZIROOM_MINSU_IM= 代表民宿 ZIROOM_ZRY_IM= 自如驿  ZIROOM_CHANGZU_IM= 自如长租
     */
    private String ziroomFlag;

    /**
     * 创建人fid （自如客是uid，业务人员是系统号)
     */
    private String createFid;

    /**
     * 创建人类型 0=自如客 1=业务人员
     */
    private Integer createrType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

    /**
     * 是否删除 默认0(0：不删除 1：删除)
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

    public String getFromUid() {
        return fromUid;
    }

    public void setFromUid(String fromUid) {
        this.fromUid = fromUid == null ? null : fromUid.trim();
    }

    public String getToUid() {
        return toUid;
    }

    public void setToUid(String toUid) {
        this.toUid = toUid == null ? null : toUid.trim();
    }

    public Integer getRelStatus() {
        return relStatus;
    }

    public void setRelStatus(Integer relStatus) {
        this.relStatus = relStatus;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getZiroomFlag() {
        return ziroomFlag;
    }

    public void setZiroomFlag(String ziroomFlag) {
        this.ziroomFlag = ziroomFlag == null ? null : ziroomFlag.trim();
    }

    public String getCreateFid() {
        return createFid;
    }

    public void setCreateFid(String createFid) {
        this.createFid = createFid == null ? null : createFid.trim();
    }

    public Integer getCreaterType() {
        return createrType;
    }

    public void setCreaterType(Integer createrType) {
        this.createrType = createrType;
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