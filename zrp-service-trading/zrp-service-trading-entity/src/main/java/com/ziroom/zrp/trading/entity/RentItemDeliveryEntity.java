package com.ziroom.zrp.trading.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;
/**
 * <p>合同物品项</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年9月15日
 * @since 1.0
 */
public class RentItemDeliveryEntity extends BaseEntity{
    private static final long serialVersionUID = 8179811612026239314L;
    /**
     * 主键
     */
    private String fid;

    /**
     * 合同id
     */
    private String contractid;

    /**
     * 物品id
     */
    private String itemid;

    /**
     * 原有数量
     */
    private Integer foriginalnum;

    /**
     * 实际数量
     */
    private Integer factualnum;

    /**
     * 交割类型: 0 房间 1 装修
     */
    private Integer ftype;

    /**
     * 新旧状态 [0 新，1旧]
     */
    private String fneworold;

    /**
     * 单位
     */
    private Double funitmeter;

    /**
     * 使用状态 [0 正常 ,1 损坏，2 丢失]
     */
    private String fstate;

    /**
     * 赔付费用
     */
    private Double fpayfee;

    /**
     * 创建日期
     */
    private Date createtime;

    /**
     * 修改日期
     */
    private Date updatetime;

    private String createrid;

    private String updaterid;

    private String city;

    /**
     * 是否删除
     */
    private Integer fisdel;

    /**
     * 是否有效
     */
    private Integer fvalid;

    /**
     * 解约协议id
     */
    private String surrenderid;

    /**
     * 物品名称
     */
    private String itemname;

    /**
     * 物品单价
     */
    private Double price;

    /**
     * 关联城市表
     */
    private String cityid;

    /**
     * 房间或者床位id
     */
    private String roomId;

    /**
     * 0:非自定义，1：表示自定义，2是添加已有的
     */
    private Integer isDefined;

    /**
     * 0表示房间1表示是床位
     */
    private Integer isbeditem;

    /**
     * 物品类型
     */
    private String itemType;

    /**
     * 是否页面新增物品
     */
    private Integer isNew;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getContractid() {
        return contractid;
    }

    public void setContractid(String contractid) {
        this.contractid = contractid == null ? null : contractid.trim();
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid == null ? null : itemid.trim();
    }

    public Integer getForiginalnum() {
        return foriginalnum;
    }

    public void setForiginalnum(Integer foriginalnum) {
        this.foriginalnum = foriginalnum;
    }

    public Integer getFactualnum() {
        return factualnum;
    }

    public void setFactualnum(Integer factualnum) {
        this.factualnum = factualnum;
    }

    public Integer getFtype() {
        return ftype;
    }

    public void setFtype(Integer ftype) {
        this.ftype = ftype;
    }

    public String getFneworold() {
        return fneworold;
    }

    public void setFneworold(String fneworold) {
        this.fneworold = fneworold == null ? null : fneworold.trim();
    }

    public Double getFunitmeter() {
        return funitmeter;
    }

    public void setFunitmeter(Double funitmeter) {
        this.funitmeter = funitmeter;
    }

    public String getFstate() {
        return fstate;
    }

    public void setFstate(String fstate) {
        this.fstate = fstate == null ? null : fstate.trim();
    }

    public Double getFpayfee() {
        return fpayfee;
    }

    public void setFpayfee(Double fpayfee) {
        this.fpayfee = fpayfee;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getCreaterid() {
        return createrid;
    }

    public void setCreaterid(String createrid) {
        this.createrid = createrid == null ? null : createrid.trim();
    }

    public String getUpdaterid() {
        return updaterid;
    }

    public void setUpdaterid(String updaterid) {
        this.updaterid = updaterid == null ? null : updaterid.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public Integer getFisdel() {
        return fisdel;
    }

    public void setFisdel(Integer fisdel) {
        this.fisdel = fisdel;
    }

    public Integer getFvalid() {
        return fvalid;
    }

    public void setFvalid(Integer fvalid) {
        this.fvalid = fvalid;
    }

    public String getSurrenderid() {
        return surrenderid;
    }

    public void setSurrenderid(String surrenderid) {
        this.surrenderid = surrenderid == null ? null : surrenderid.trim();
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname == null ? null : itemname.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid == null ? null : cityid.trim();
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId == null ? null : roomId.trim();
    }

    public Integer getIsDefined() {
        return isDefined;
    }

    public void setIsDefined(Integer isDefined) {
        this.isDefined = isDefined;
    }

    public Integer getIsbeditem() {
        return isbeditem;
    }

    public void setIsbeditem(Integer isbeditem) {
        this.isbeditem = isbeditem;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType == null ? null : itemType.trim();
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }
}