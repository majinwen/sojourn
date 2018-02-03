package com.ziroom.minsu.entity.house;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class HouseStatsDayMsgEntity extends BaseEntity{
	
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = 8111547876696336596L;

	/**
     * 主键id
     */
    private Integer id;

    /**
     * 逻辑id
     */
    private String fid;

    /**
     * 房源(房间)fid
     */
    private String houseFid;

    /**
     * 出租方式 0：整租，1：合租
     */
    private Integer rentWay;

    /**
     * 房源(房间)编号
     */
    private String houseSn;

    /**
     * 省code
     */
    private String provinceCode;

    /**
     * 市code
     */
    private String cityCode;

    /**
     * 交易量
     */
    private Integer tradeNum;

    /**
     * 咨询量
     */
    private Integer consultNum;

    /**
     * 统计日期
     */
    private Date statsDate;

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

    /**
     * 是否删除 0:否，1：是
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

    public String getHouseFid() {
        return houseFid;
    }

    public void setHouseFid(String houseFid) {
        this.houseFid = houseFid == null ? null : houseFid.trim();
    }

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

    public String getHouseSn() {
        return houseSn;
    }

    public void setHouseSn(String houseSn) {
        this.houseSn = houseSn == null ? null : houseSn.trim();
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public Integer getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(Integer tradeNum) {
        this.tradeNum = tradeNum;
    }

    public Integer getConsultNum() {
        return consultNum;
    }

    public void setConsultNum(Integer consultNum) {
        this.consultNum = consultNum;
    }

    public Date getStatsDate() {
        return statsDate;
    }

    public void setStatsDate(Date statsDate) {
        this.statsDate = statsDate;
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

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}