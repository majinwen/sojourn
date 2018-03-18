package com.zra.item.entity;

/**
 * Created by PC on 2016/9/23.
 */

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 房间或者床位与物品的配置
 */
public class RoomitemsconfigEntity {
	/**
	 *
	 * 表字段 : troomitemsconfig.fid
	 *
	 */
	@ApiModelProperty(value="")
	private String fid;

	/**
	 * 房间ID
	 * 表字段 : troomitemsconfig.roomid
	 *
	 */
	@ApiModelProperty(value="房间ID")
	private String roomid;

	/**
	 * 项目ID
	 * 表字段 : troomitemsconfig.projectid
	 *
	 */
	@ApiModelProperty(value="项目ID")
	private String projectid;

	/**
	 * 单品编码,条码
	 * 表字段 : troomitemsconfig.fitemcode
	 *
	 */
	@ApiModelProperty(value="单品编码,条码")
	private String fitemcode;

	/**
	 * 物品ID
	 * 表字段 : troomitemsconfig.itemid
	 *
	 */
	@ApiModelProperty(value="物品ID")
	private String itemid;

	/**
	 * 品牌
	 * 表字段 : troomitemsconfig.fbrand
	 *
	 */
	@ApiModelProperty(value="品牌")
	private String fbrand;

	/**
	 * 数量
	 * 表字段 : troomitemsconfig.fnumber
	 *
	 */
	@ApiModelProperty(value="数量")
	private Integer fnumber;

	/**
	 * 供应商ID
	 * 表字段 : troomitemsconfig.supplierid
	 *
	 */
	@ApiModelProperty(value="供应商ID")
	private String supplierid;

	/**
	 * 质保起始日期
	 * 表字段 : troomitemsconfig.fstartdate
	 *
	 */
	@ApiModelProperty(value="质保起始日期")
	private String fstartdate;

	/**
	 * 质保期
	 * 表字段 : troomitemsconfig.fqualityperiod
	 *
	 */
	@ApiModelProperty(value="质保期")
	private Integer fqualityperiod;

	/**
	 * 客服电话
	 * 表字段 : troomitemsconfig.fcustomertel
	 *
	 */
	@ApiModelProperty(value="客服电话")
	private String fcustomertel;

	/**
	 * 城市ID
	 * 表字段 : troomitemsconfig.cityid
	 *
	 */
	@ApiModelProperty(value="城市ID")
	private String cityid;

	/**
	 *
	 * 表字段 : troomitemsconfig.fvalid
	 *
	 */
	@ApiModelProperty(value="")
	private Integer fvalid;

	/**
	 *
	 * 表字段 : troomitemsconfig.fcreatetime
	 *
	 */
	@ApiModelProperty(value="")
	private Date fcreatetime;

	/**
	 *
	 * 表字段 : troomitemsconfig.createrid
	 *
	 */
	@ApiModelProperty(value="")
	private String createrid;

	/**
	 *
	 * 表字段 : troomitemsconfig.fupdatetime
	 *
	 */
	@ApiModelProperty(value="")
	private Date fupdatetime;

	/**
	 *
	 * 表字段 : troomitemsconfig.updaterid
	 *
	 */
	@ApiModelProperty(value="")
	private String updaterid;

	/**
	 *
	 * 表字段 : troomitemsconfig.fisdel
	 *
	 */
	@ApiModelProperty(value="")
	private Integer fisdel;

	/**
	 * 数量
	 * 表字段 : troomitemsconfig.fnum
	 *
	 */
	@ApiModelProperty(value="数量")
	private Integer fnum;

	/**
	 * 价格
	 * 表字段 : troomitemsconfig.fprice
	 *
	 */
	@ApiModelProperty(value="价格")
	private Double fprice;

	/**
	 * 装配功能区
	 * 表字段 : troomitemsconfig.fregionmaintainid
	 *
	 */
	@ApiModelProperty(value="装配功能区")
	private String fregionmaintainid;

	/**
	 * 配置的类别：0表示房间，1表示床位
	 * 表字段 : troomitemsconfig.ftype
	 *
	 */
	@ApiModelProperty(value="配置的类别：0表示房间，1表示床位")
	private Byte ftype;

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

	public Byte getFtype() {
		return ftype;
	}

	public void setFtype(Byte ftype) {
		this.ftype = ftype;
	}
}
