package com.ziroom.minsu.entity.house;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class HouseBizMsgEntity extends BaseEntity{
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = -4480046159046414291L;

	/**
     * 主键id
     */
    private Integer id;

    /**
     * 逻辑id
     */
    private String fid;

    /**
     * 房源fid
     */
    private String houseBaseFid;

    /**
     * 房间fid
     */
    private String roomFid;

    /**
     * 床位fid
     */
    private String bedFid;

    /**
     * 出租方式 0:整租 1:合租
     */
    private Integer rentWay;

    /**
     * 首次发布时间
     */
    private Date firstDeployDate;

    /**
     * 首次上架时间
     */
    private Date firstUpDate;

    /**
     * 最新发布时间
     */
    private Date lastDeployDate;

    /**
     * 最新发布时间
     */
    private Date lastUpDate;

    /**
     * 最新审核不通过原因
     */
    private String refuseReason;

    /**
     * 最新审核不通过备注
     */
    private String refuseMark;

    /**
     * 最新审核不通过时间
     */
    private Date refuseDate;

    /**
     * 创建人fid
     */
    private String createFid;

    /**
     * 创建时间
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

    public String getHouseBaseFid() {
        return houseBaseFid;
    }

    public void setHouseBaseFid(String houseBaseFid) {
        this.houseBaseFid = houseBaseFid == null ? null : houseBaseFid.trim();
    }

    public String getRoomFid() {
        return roomFid;
    }

    public void setRoomFid(String roomFid) {
        this.roomFid = roomFid == null ? null : roomFid.trim();
    }

    public String getBedFid() {
        return bedFid;
    }

    public void setBedFid(String bedFid) {
        this.bedFid = bedFid == null ? null : bedFid.trim();
    }

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

    public Date getFirstDeployDate() {
        return firstDeployDate;
    }

    public void setFirstDeployDate(Date firstDeployDate) {
        this.firstDeployDate = firstDeployDate;
    }

    public Date getFirstUpDate() {
        return firstUpDate;
    }

    public void setFirstUpDate(Date firstUpDate) {
        this.firstUpDate = firstUpDate;
    }

    public Date getLastDeployDate() {
        return lastDeployDate;
    }

    public void setLastDeployDate(Date lastDeployDate) {
        this.lastDeployDate = lastDeployDate;
    }

    public Date getLastUpDate() {
        return lastUpDate;
    }

    public void setLastUpDate(Date lastUpDate) {
        this.lastUpDate = lastUpDate;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason == null ? null : refuseReason.trim();
    }

    public String getRefuseMark() {
        return refuseMark;
    }

    public void setRefuseMark(String refuseMark) {
        this.refuseMark = refuseMark == null ? null : refuseMark.trim();
    }

    public Date getRefuseDate() {
        return refuseDate;
    }

    public void setRefuseDate(Date refuseDate) {
        this.refuseDate = refuseDate;
    }

    public String getCreateFid() {
        return createFid;
    }

    public void setCreateFid(String createFid) {
        this.createFid = createFid == null ? null : createFid.trim();
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