package com.ziroom.minsu.entity.customer;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class CustomerUpdateHistoryExtLogEntity extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1323643900576065774L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 用户基本信息修改记录表fid
     */
    private String fid;

    /**
     * 修改字段的旧值
     */
    private String oldValue;

    /**
     * 修改字段的新值
     */
    private String newValue;

    /**
     * 创建时间
     */
    private Date createDate;

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

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue == null ? null : oldValue.trim();
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue == null ? null : newValue.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}