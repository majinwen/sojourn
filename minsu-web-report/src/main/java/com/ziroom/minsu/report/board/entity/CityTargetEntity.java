package com.ziroom.minsu.report.board.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

public class CityTargetEntity extends BaseEntity{
    private static final long serialVersionUID = -3991706376018352826L;
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 逻辑主键
     */
    private String fid;

    /**
     * 城市code
     */
    private String cityCode;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 目标月份
     */
    private String targetMonth;

    /**
     * 房源上架目标
     */
    private Integer targetHouseNum;

    /**
     * 地推上架目标
     */
    private Integer targetPushHouseNum;

    /**
     * 房源自主上架目标
     */
    private Integer targetSelfHouseNum;

    /**
     * 订单目标
     */
    private Integer targetOrderNum;

    /**
     * 出租间夜目标
     */
    private Integer targetRentNum;

    /**
     * 创建人员工号
     */
    private String createEmpCode;

    /**
     * 创建人名字
     */
    private String createEmpName;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date lastModifyDate;

    /**
     * 是否删除 0=否 1=是
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
        this.fid = fid;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getTargetMonth() {
        return targetMonth;
    }

    public void setTargetMonth(String targetMonth) {
        this.targetMonth = targetMonth;
    }

    public Integer getTargetHouseNum() {
        return targetHouseNum;
    }

    public void setTargetHouseNum(Integer targetHouseNum) {
        this.targetHouseNum = targetHouseNum;
    }

    public Integer getTargetPushHouseNum() {
        return targetPushHouseNum;
    }

    public void setTargetPushHouseNum(Integer targetPushHouseNum) {
        this.targetPushHouseNum = targetPushHouseNum;
    }

    public Integer getTargetSelfHouseNum() {
        return targetSelfHouseNum;
    }

    public void setTargetSelfHouseNum(Integer targetSelfHouseNum) {
        this.targetSelfHouseNum = targetSelfHouseNum;
    }

    public Integer getTargetOrderNum() {
        return targetOrderNum;
    }

    public void setTargetOrderNum(Integer targetOrderNum) {
        this.targetOrderNum = targetOrderNum;
    }

    public Integer getTargetRentNum() {
        return targetRentNum;
    }

    public void setTargetRentNum(Integer targetRentNum) {
        this.targetRentNum = targetRentNum;
    }

    public String getCreateEmpCode() {
        return createEmpCode;
    }

    public void setCreateEmpCode(String createEmpCode) {
        this.createEmpCode = createEmpCode == null ? null : createEmpCode.trim();
    }

    public String getCreateEmpName() {
        return createEmpName;
    }

    public void setCreateEmpName(String createEmpName) {
        this.createEmpName = createEmpName == null ? null : createEmpName.trim();
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