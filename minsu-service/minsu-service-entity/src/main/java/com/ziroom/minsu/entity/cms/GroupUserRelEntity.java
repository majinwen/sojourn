package com.ziroom.minsu.entity.cms;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 用户组
 *
 * @param
 * @author jixd
 * @created 2017年10月10日 15:47:20
 * @return
 */
public class GroupUserRelEntity extends BaseEntity {
    private static final long serialVersionUID = -5627365220725713431L;
    /**
     * 编号
     */
    private Integer id;
    /**
     * 逻辑id
     */
    private String fid;

    /**
     * 关联编号
     */
    private String groupFid;

    /**
     * uid
     */
    private String uid;

    /**
     * 姓名
     */
    private String customerName;

    /**
     * 用户手机号
     */
    private String customerPhone;

    /**
     * 创建人fid
     */
    private String createFid;

    /**
     * 创建人姓名
     */
    private String createName;

    /**
     * 创建时间
     */
    private Date createDate;

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

    public String getGroupFid() {
        return groupFid;
    }

    public void setGroupFid(String groupFid) {
        this.groupFid = groupFid == null ? null : groupFid.trim();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone == null ? null : customerPhone.trim();
    }

    public String getCreateFid() {
        return createFid;
    }

    public void setCreateFid(String createFid) {
        this.createFid = createFid == null ? null : createFid.trim();
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
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

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }
}