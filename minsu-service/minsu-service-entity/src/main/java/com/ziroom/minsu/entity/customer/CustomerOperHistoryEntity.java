package com.ziroom.minsu.entity.customer;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
/**
 * 审核客户操作记录
 * @author jixd on 2016-04-24
 *
 */
public class CustomerOperHistoryEntity extends BaseEntity{
	
    /**
	 * 序列Id
	 */
	private static final long serialVersionUID = 9223121079566043203L;
	/**
	 * 递增id
	 */
	private Integer id;
	/**
	 * 逻辑ID
	 */
    private String fid;
    /**
     * 客户UID
     */
    private String uid;
    /**
     * 操作人ID
     */
    private String operUid;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 审核前状态
     */
    private Integer auditBeforeStatus;
    /**
     * 审核后状态
     */
    private Integer auditAfterStatus;
    /**
     * 审核备注
     */
    private String operRemark;

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

    public String getOperUid() {
        return operUid;
    }

    public void setOperUid(String operUid) {
        this.operUid = operUid == null ? null : operUid.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getAuditBeforeStatus() {
        return auditBeforeStatus;
    }

    public void setAuditBeforeStatus(Integer auditBeforeStatus) {
        this.auditBeforeStatus = auditBeforeStatus;
    }

    public Integer getAuditAfterStatus() {
        return auditAfterStatus;
    }

    public void setAuditAfterStatus(Integer auditAfterStatus) {
        this.auditAfterStatus = auditAfterStatus;
    }

    public String getOperRemark() {
        return operRemark;
    }

    public void setOperRemark(String operRemark) {
        this.operRemark = operRemark == null ? null : operRemark.trim();
    }
}