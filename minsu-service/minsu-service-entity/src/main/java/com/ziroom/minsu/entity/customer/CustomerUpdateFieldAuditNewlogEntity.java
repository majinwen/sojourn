package com.ziroom.minsu.entity.customer;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class CustomerUpdateFieldAuditNewlogEntity extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7678832353209313168L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 与t_customer_update_history_log 对应, fid=MD5(house_fid+room_fid+rent_way+field_path_key)
     */
    private String fid;

    /**
     * 用户uid
     */
    private String uid;

    /**
     * 审核状态  默认0=未审核；1=审核通过；2=审核拒绝  只有需要审核字段才有此状态 
     */
    private Integer fieldAuditStatu;

    /**
     * 审核字段的路径  (例如：com.ziroom.minsu.entity.customer.CustomerBaseMsgExtEntity.customerIntroduce)
     */
    private String fieldPath;

    /**
     * 审核字段的描述
     */
    private String fieldDesc;

    /**
     * 创建人fid （房东是uid，业务人员是系统号)
     */
    private String createrFid;

    /**
     * 创建人类型 0=其他 1=房东 2=业务人员 
     */
    private Integer createrType;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 最后更新时间
     */
    private Date lastModifyDate;

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

    public Integer getFieldAuditStatu() {
        return fieldAuditStatu;
    }

    public void setFieldAuditStatu(Integer fieldAuditStatu) {
        this.fieldAuditStatu = fieldAuditStatu;
    }

    public String getFieldPath() {
        return fieldPath;
    }

    public void setFieldPath(String fieldPath) {
        this.fieldPath = fieldPath == null ? null : fieldPath.trim();
    }

    public String getFieldDesc() {
        return fieldDesc;
    }

    public void setFieldDesc(String fieldDesc) {
        this.fieldDesc = fieldDesc == null ? null : fieldDesc.trim();
    }

    public String getCreaterFid() {
        return createrFid;
    }

    public void setCreaterFid(String createrFid) {
        this.createrFid = createrFid == null ? null : createrFid.trim();
    }

    public Integer getCreaterType() {
        return createrType;
    }

    public void setCreaterType(Integer createrType) {
        this.createrType = createrType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }
}