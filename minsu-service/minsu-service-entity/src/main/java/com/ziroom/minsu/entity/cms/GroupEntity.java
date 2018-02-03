package com.ziroom.minsu.entity.cms;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 组
 *
 * @param
 * @author jixd
 * @created 2017年10月10日 15:32:33
 * @return
 */
public class GroupEntity extends BaseEntity {
    private static final long serialVersionUID = -6895105659587492614L;
    /**
     * 编号
     */
    private Integer id;

    /**
     * 逻辑fid
     */
    private String fid;
    /**
     * 业务线类型  1：民宿 2：公寓
     */
    private Integer serviceLine;

    /**
     * 名称
     */
    private String groupName;

    /**
     * 组类型 1=用户组 2=房源组
     */
    private Integer groupType;

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
    /**
     * 人数
     */
    private transient Integer peopleNum;

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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
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

    public Integer getServiceLine() {
        return serviceLine;
    }

    public void setServiceLine(Integer serviceLine) {
        this.serviceLine = serviceLine;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }
}