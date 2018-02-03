package com.ziroom.minsu.entity.cms;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 房源组
 *
 * @param
 * @author jixd
 * @created 2017年10月10日 15:32:47
 * @return
 */
public class GroupHouseRelEntity extends BaseEntity {
    /**
     * 编号
     */
    private Integer id;
    /**
     * 逻辑id
     */
    private String fid;

    /**
     * 房源组编号
     */
    private String groupFid;

    /**
     * 类型 1=项目 2=户型 3=房间
     */
    private Integer houseType;

    /**
     * 项目ID
     */
    private String projectId;
    /**
     * 户型ID
     */
    private String layoutId;
    /**
     * 房间ID
     */
    private String roomId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 户型名称
     */
    private String layoutName;

    /**
     * 房间名称
     */
    private String roomName;

    /**
     * 房间编号
     */
    private String roomNumber;

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

    public Integer getHouseType() {
        return houseType;
    }

    public void setHouseType(Integer houseType) {
        this.houseType = houseType == null ? null : houseType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getLayoutName() {
        return layoutName;
    }

    public void setLayoutName(String layoutName) {
        this.layoutName = layoutName == null ? null : layoutName.trim();
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName == null ? null : roomName.trim();
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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(String layoutId) {
        this.layoutId = layoutId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}