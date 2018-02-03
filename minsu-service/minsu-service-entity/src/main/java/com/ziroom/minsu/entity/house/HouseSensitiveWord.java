package com.ziroom.minsu.entity.house;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class HouseSensitiveWord extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 492565077328280199L;

	private Integer id;

    private String fid;

    private String houseBaseFid;

    private String roomFid;

    private Integer rentWay;

    private String houseDescSensitiveWord;

    private String aroundDescSensitiveWord;

    private Date createDate;

    private Date lastModifyDate;

    private String createUid;

    private Integer isDel;

    private String houseRulesSensitiveWord;

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

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

    public String getHouseDescSensitiveWord() {
        return houseDescSensitiveWord;
    }

    public void setHouseDescSensitiveWord(String houseDescSensitiveWord) {
        this.houseDescSensitiveWord = houseDescSensitiveWord == null ? null : houseDescSensitiveWord.trim();
    }

    public String getAroundDescSensitiveWord() {
        return aroundDescSensitiveWord;
    }

    public void setAroundDescSensitiveWord(String aroundDescSensitiveWord) {
        this.aroundDescSensitiveWord = aroundDescSensitiveWord == null ? null : aroundDescSensitiveWord.trim();
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

    public String getCreateUid() {
        return createUid;
    }

    public void setCreateUid(String createUid) {
        this.createUid = createUid == null ? null : createUid.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getHouseRulesSensitiveWord() {
        return houseRulesSensitiveWord;
    }

    public void setHouseRulesSensitiveWord(String houseRulesSensitiveWord) {
        this.houseRulesSensitiveWord = houseRulesSensitiveWord == null ? null : houseRulesSensitiveWord.trim();
    }
}