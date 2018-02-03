package com.ziroom.minsu.entity.cms;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 活动组关联
 *
 * @param
 * @author jixd
 * @created 2017年10月10日 15:32:05
 * @return
 */
public class GroupActRelEntity extends BaseEntity {
    private static final long serialVersionUID = 3255107183126451735L;
    /**
     * 编号
     */
    private Integer id;
    /**
     * 逻辑id
     */
    private String fid;

    /**
     * 活动编号
     */
    private String actSn;

    /**
     * 关联编号
     */
    private String groupFid;

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

    public String getActSn() {
        return actSn;
    }

    public void setActSn(String actSn) {
        this.actSn = actSn == null ? null : actSn.trim();
    }

    public String getGroupFid() {
        return groupFid;
    }

    public void setGroupFid(String groupFid) {
        this.groupFid = groupFid == null ? null : groupFid.trim();
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