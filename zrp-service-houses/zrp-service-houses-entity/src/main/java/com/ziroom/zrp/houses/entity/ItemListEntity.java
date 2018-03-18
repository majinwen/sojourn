package com.ziroom.zrp.houses.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;
/**
 * 物品项 表 titemlist
 * @author jixd
 * @created 2017年10月30日 10:40:51
 * @param
 * @return
 */
public class ItemListEntity extends BaseEntity{
    private static final long serialVersionUID = -8433720873549750195L;
    /**
     * 主键
     */
    private String fid;

    /**
     * 物品分类
     */
    private String ftype;

    /**
     * 物品名称
     */
    private String fname;

    /**
     * 物品编码
     */
    private String fcode;

    /**
     * 是否配件 1:是 0:否
     */
    private String fisaccessory;

    /**
     * 损坏赔偿 1:是 0:否
     */
    private String fisdamage;

    /**
     * 物品价格
     */
    private Double fprice;

    /**
     * 是否有库存 1:是 0:否 
     */
    private String fisbackup;

    /**
     * 网站显示名
     */
    private String fwebname;

    /**
     * 显示顺序
     */
    private String fshoworder;

    /**
     * 图标
     */
    private String ficon;

    /**
     * 赔付金额
     */
    private Double fcompensateprice;

    /**
     * 半年内赔率
     */
    private Double fhalfpercent;

    /**
     * 0.5-1年赔率
     */
    private Double fonepercent;

    /**
     * 1-2年赔率
     */
    private Double ftwopercent;

    /**
     * 2-3年赔率
     */
    private Double fthreepercent;

    /**
     * 3-4年赔率
     */
    private Double ffourpercent;

    /**
     * 4-5年赔率
     */
    private Double ffivepercent;

    /**
     * 物品状态 1:在用 0:停用
     */
    private String fstate;

    /**
     * 城市ID
     */
    private String fcityid;

    private Integer fvalid;

    private Date fcreatetime;

    private String createrid;

    private Date fupdatetime;

    private String updaterid;

    /**
     * 是否删除 1:是 0:否
     */
    private Integer fisdel;

    /**
     * 是否列入物业交割单
     */
    private Integer fisregister;

    /**
     * 0:其他费用项目;1:装修费用项目
     */
    private Integer fitemtype;

    /**
     * 关联城市表
     */
    private String cityid;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getFtype() {
        return ftype;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype == null ? null : ftype.trim();
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname == null ? null : fname.trim();
    }

    public String getFcode() {
        return fcode;
    }

    public void setFcode(String fcode) {
        this.fcode = fcode == null ? null : fcode.trim();
    }

    public String getFisaccessory() {
        return fisaccessory;
    }

    public void setFisaccessory(String fisaccessory) {
        this.fisaccessory = fisaccessory == null ? null : fisaccessory.trim();
    }

    public String getFisdamage() {
        return fisdamage;
    }

    public void setFisdamage(String fisdamage) {
        this.fisdamage = fisdamage == null ? null : fisdamage.trim();
    }

    public Double getFprice() {
        return fprice;
    }

    public void setFprice(Double fprice) {
        this.fprice = fprice;
    }

    public String getFisbackup() {
        return fisbackup;
    }

    public void setFisbackup(String fisbackup) {
        this.fisbackup = fisbackup == null ? null : fisbackup.trim();
    }

    public String getFwebname() {
        return fwebname;
    }

    public void setFwebname(String fwebname) {
        this.fwebname = fwebname == null ? null : fwebname.trim();
    }

    public String getFshoworder() {
        return fshoworder;
    }

    public void setFshoworder(String fshoworder) {
        this.fshoworder = fshoworder == null ? null : fshoworder.trim();
    }

    public String getFicon() {
        return ficon;
    }

    public void setFicon(String ficon) {
        this.ficon = ficon == null ? null : ficon.trim();
    }

    public Double getFcompensateprice() {
        return fcompensateprice;
    }

    public void setFcompensateprice(Double fcompensateprice) {
        this.fcompensateprice = fcompensateprice;
    }

    public Double getFhalfpercent() {
        return fhalfpercent;
    }

    public void setFhalfpercent(Double fhalfpercent) {
        this.fhalfpercent = fhalfpercent;
    }

    public Double getFonepercent() {
        return fonepercent;
    }

    public void setFonepercent(Double fonepercent) {
        this.fonepercent = fonepercent;
    }

    public Double getFtwopercent() {
        return ftwopercent;
    }

    public void setFtwopercent(Double ftwopercent) {
        this.ftwopercent = ftwopercent;
    }

    public Double getFthreepercent() {
        return fthreepercent;
    }

    public void setFthreepercent(Double fthreepercent) {
        this.fthreepercent = fthreepercent;
    }

    public Double getFfourpercent() {
        return ffourpercent;
    }

    public void setFfourpercent(Double ffourpercent) {
        this.ffourpercent = ffourpercent;
    }

    public Double getFfivepercent() {
        return ffivepercent;
    }

    public void setFfivepercent(Double ffivepercent) {
        this.ffivepercent = ffivepercent;
    }

    public String getFstate() {
        return fstate;
    }

    public void setFstate(String fstate) {
        this.fstate = fstate == null ? null : fstate.trim();
    }

    public String getFcityid() {
        return fcityid;
    }

    public void setFcityid(String fcityid) {
        this.fcityid = fcityid == null ? null : fcityid.trim();
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

    public Integer getFisregister() {
        return fisregister;
    }

    public void setFisregister(Integer fisregister) {
        this.fisregister = fisregister;
    }

    public Integer getFitemtype() {
        return fitemtype;
    }

    public void setFitemtype(Integer fitemtype) {
        this.fitemtype = fitemtype;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid == null ? null : cityid.trim();
    }
}