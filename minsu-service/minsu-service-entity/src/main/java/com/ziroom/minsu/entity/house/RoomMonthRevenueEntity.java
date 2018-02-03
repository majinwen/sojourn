package com.ziroom.minsu.entity.house;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class RoomMonthRevenueEntity extends BaseEntity{
	
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = 5498294938789765862L;

	/**
	 * 主键
	 */
    private Integer id;

    /**
     * 逻辑id
     */
    private String fid;

    /**
     * 房间逻辑id
     */
    private String roomFid;

    /**
     * 房间名称
     */
    private String roomName;

    /**
     * 房源月收益逻辑id
     */
    private String houseMonthRevenueFid;

    /**
     * 房间月收益
     */
    private Integer roomMonthRevenue = 0;

    /**
     * 房源分摊收益
     */
    private Integer houseShareRevenue = 0;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

    /**
     * 是否删除
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

    public String getRoomFid() {
        return roomFid;
    }

    public void setRoomFid(String roomFid) {
        this.roomFid = roomFid == null ? null : roomFid.trim();
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName == null ? null : roomName.trim();
    }

    public String getHouseMonthRevenueFid() {
        return houseMonthRevenueFid;
    }

    public void setHouseMonthRevenueFid(String houseMonthRevenueFid) {
        this.houseMonthRevenueFid = houseMonthRevenueFid == null ? null : houseMonthRevenueFid.trim();
    }

    public Integer getRoomMonthRevenue() {
        return roomMonthRevenue;
    }

    public void setRoomMonthRevenue(Integer roomMonthRevenue) {
        this.roomMonthRevenue = roomMonthRevenue;
    }

    public Integer getHouseShareRevenue() {
        return houseShareRevenue;
    }

    public void setHouseShareRevenue(Integer houseShareRevenue) {
        this.houseShareRevenue = houseShareRevenue;
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
}