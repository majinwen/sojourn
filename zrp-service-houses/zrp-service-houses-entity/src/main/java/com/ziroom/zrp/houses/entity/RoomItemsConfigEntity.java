package com.ziroom.zrp.houses.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 房间物品信息 表troomitemsconfig
 * @author jixd
 * @created 2017年10月30日 10:39:24
 * @param
 * @return
 */
public class RoomItemsConfigEntity extends BaseEntity{
    private static final long serialVersionUID = -3427519374676133523L;
    private String fid;

    /**
     * 房间ID
     */
    private String roomid;

    /**
     * 项目ID
     */
    private String projectid;

    /**
     * 单品编码,条码
     */
    private String fitemcode;

    /**
     * 物品ID
     */
    private String itemid;

    /**
     * 品牌
     */
    private String fbrand;

    /**
     * 数量
     */
    private Integer fnumber;

    /**
     * 供应商ID
     */
    private String supplierid;

    /**
     * 质保起始日期
     */
    private String fstartdate;

    /**
     * 质保期
     */
    private Integer fqualityperiod;

    /**
     * 客服电话
     */
    private String fcustomertel;

    /**
     * 城市ID
     */
    private String cityid;

    private Integer fvalid;

    private Date fcreatetime;

    private String createrid;

    private Date fupdatetime;

    private String updaterid;

    private Integer fisdel;

    /**
     * 数量
     */
    private Integer fnum;

    /**
     * 价格
     */
    private Double fprice;

    /**
     * 装配功能区
     */
    private String fregionmaintainid;

    /**
     * 配置的类别：0表示房间，1表示床位
     */
    private Integer ftype;

	public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid == null ? null : roomid.trim();
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid == null ? null : projectid.trim();
    }

    public String getFitemcode() {
        return fitemcode;
    }

    public void setFitemcode(String fitemcode) {
        this.fitemcode = fitemcode == null ? null : fitemcode.trim();
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid == null ? null : itemid.trim();
    }

    public String getFbrand() {
        return fbrand;
    }

    public void setFbrand(String fbrand) {
        this.fbrand = fbrand == null ? null : fbrand.trim();
    }

    public Integer getFnumber() {
        return fnumber;
    }

    public void setFnumber(Integer fnumber) {
        this.fnumber = fnumber;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
    }

    public String getFstartdate() {
        return fstartdate;
    }

    public void setFstartdate(String fstartdate) {
        this.fstartdate = fstartdate == null ? null : fstartdate.trim();
    }

    public Integer getFqualityperiod() {
        return fqualityperiod;
    }

    public void setFqualityperiod(Integer fqualityperiod) {
        this.fqualityperiod = fqualityperiod;
    }

    public String getFcustomertel() {
        return fcustomertel;
    }

    public void setFcustomertel(String fcustomertel) {
        this.fcustomertel = fcustomertel == null ? null : fcustomertel.trim();
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid == null ? null : cityid.trim();
    }

    public Integer getFvalid() {
        return fvalid;
    }

    public void setFvalid(Integer fvalid) {
        this.fvalid = fvalid;
    }

    public Date getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(Date fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    public String getCreaterid() {
        return createrid;
    }

    public void setCreaterid(String createrid) {
        this.createrid = createrid == null ? null : createrid.trim();
    }

    public Date getFupdatetime() {
        return fupdatetime;
    }

    public void setFupdatetime(Date fupdatetime) {
        this.fupdatetime = fupdatetime;
    }

    public String getUpdaterid() {
        return updaterid;
    }

    public void setUpdaterid(String updaterid) {
        this.updaterid = updaterid == null ? null : updaterid.trim();
    }

    public Integer getFisdel() {
        return fisdel;
    }

    public void setFisdel(Integer fisdel) {
        this.fisdel = fisdel;
    }

    public Integer getFnum() {
        return fnum;
    }

    public void setFnum(Integer fnum) {
        this.fnum = fnum;
    }

    public Double getFprice() {
        return fprice;
    }

    public void setFprice(Double fprice) {
        this.fprice = fprice;
    }

    public String getFregionmaintainid() {
        return fregionmaintainid;
    }

    public void setFregionmaintainid(String fregionmaintainid) {
        this.fregionmaintainid = fregionmaintainid == null ? null : fregionmaintainid.trim();
    }

    public Integer getFtype() {
        return ftype;
    }

    public void setFtype(Integer ftype) {
        this.ftype = ftype;
    }

}