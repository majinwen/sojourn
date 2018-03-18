package com.ziroom.zrp.houses.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;
/**
 * 房型物品信息 表 thouseitemsconfig
 * @author jixd
 * @created 2017年10月30日 10:38:44
 * @param
 * @return
 */
public class HouseItemsConfigEntity extends BaseEntity{
    private static final long serialVersionUID = 2479618244551796721L;
    private String fid;

    /**
     * 户型ID
     */
    private String fhousetypeid;

    /**
     * 物品ID
     */
    private String fitemid;

    /**
     * 物品分类
     */
    private String fitemstype;

    /**
     * 物品名称
     */
    private String fitemsname;

    /**
     * 物品编码
     */
    private String fitemscode;

    /**
     * 物品数量
     */
    private Integer fitemsnum;

    /**
     * 必配 1:是 0:否
     */
    private String finputoptions;

    /**
     * 是否有库存 1:是 0:否
     */
    private String finventorymanage;

    /**
     * 数量可修改 1:是 0:否
     */
    private String fnummodify;

    /**
     * 项目ID
     */
    private String projectid;

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
     * 物品所属实体类型：0:-房间；1-床位
     */
    private Integer ftype;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getFhousetypeid() {
        return fhousetypeid;
    }

    public void setFhousetypeid(String fhousetypeid) {
        this.fhousetypeid = fhousetypeid == null ? null : fhousetypeid.trim();
    }

    public String getFitemid() {
        return fitemid;
    }

    public void setFitemid(String fitemid) {
        this.fitemid = fitemid == null ? null : fitemid.trim();
    }

    public String getFitemstype() {
        return fitemstype;
    }

    public void setFitemstype(String fitemstype) {
        this.fitemstype = fitemstype == null ? null : fitemstype.trim();
    }

    public String getFitemsname() {
        return fitemsname;
    }

    public void setFitemsname(String fitemsname) {
        this.fitemsname = fitemsname == null ? null : fitemsname.trim();
    }

    public String getFitemscode() {
        return fitemscode;
    }

    public void setFitemscode(String fitemscode) {
        this.fitemscode = fitemscode == null ? null : fitemscode.trim();
    }

    public Integer getFitemsnum() {
        return fitemsnum;
    }

    public void setFitemsnum(Integer fitemsnum) {
        this.fitemsnum = fitemsnum;
    }

    public String getFinputoptions() {
        return finputoptions;
    }

    public void setFinputoptions(String finputoptions) {
        this.finputoptions = finputoptions == null ? null : finputoptions.trim();
    }

    public String getFinventorymanage() {
        return finventorymanage;
    }

    public void setFinventorymanage(String finventorymanage) {
        this.finventorymanage = finventorymanage == null ? null : finventorymanage.trim();
    }

    public String getFnummodify() {
        return fnummodify;
    }

    public void setFnummodify(String fnummodify) {
        this.fnummodify = fnummodify == null ? null : fnummodify.trim();
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid == null ? null : projectid.trim();
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

    public Integer getFtype() {
        return ftype;
    }

    public void setFtype(Integer ftype) {
        this.ftype = ftype;
    }
}