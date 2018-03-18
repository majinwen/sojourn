package com.zra.item.entity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by PC on 2016/9/23.
 */
public class ItemdeliveryEntity {
	/**
	 * 主键
	 * 表字段 : titemdelivery.fid
	 *
	 */
	@ApiModelProperty(value="主键")
	private String fid;

	/**
	 * 合同id
	 * 表字段 : titemdelivery.contractid
	 *
	 */
	@ApiModelProperty(value="合同id")
	private String contractid;

	/**
	 * 房间或者床位id
	 * 表字段 : titemdelivery.room_id
	 *
	 */
	@ApiModelProperty(value="房间或者床位id")
	private String roomId;

	/**
	 * 物品id
	 * 表字段 : titemdelivery.itemid
	 *
	 */
	@ApiModelProperty(value="物品id")
	private String itemid;

	/**
	 * 原有数量
	 * 表字段 : titemdelivery.foriginalnum
	 *
	 */
	@ApiModelProperty(value="原有数量")
	private Integer foriginalnum;

	/**
	 * 实际数量
	 * 表字段 : titemdelivery.factualnum
	 *
	 */
	@ApiModelProperty(value="实际数量")
	private Integer factualnum;

	/**
	 * 交割类型: 0 房间 1 装修
	 * 表字段 : titemdelivery.ftype
	 *
	 */
	@ApiModelProperty(value="交割类型: 0 房间 1 装修")
	private Integer ftype;

	/**
	 * 新旧状态 [0 新，1旧]
	 * 表字段 : titemdelivery.fneworold
	 *
	 */
	@ApiModelProperty(value="新旧状态 [0 新，1旧]")
	private String fneworold;

	/**
	 * 单位
	 * 表字段 : titemdelivery.funitmeter
	 *
	 */
	@ApiModelProperty(value="单位")
	private Double funitmeter;

	/**
	 * 使用状态 [0 正常 ,1 损坏，2 丢失]
	 * 表字段 : titemdelivery.fstate
	 *
	 */
	@ApiModelProperty(value="使用状态 [0 正常 ,1 损坏，2 丢失]")
	private String fstate;

	/**
	 * 赔付费用
	 * 表字段 : titemdelivery.fpayfee
	 *
	 */
	@ApiModelProperty(value="赔付费用")
	private BigDecimal fpayfee;

	/**
	 * 创建日期
	 * 表字段 : titemdelivery.createtime
	 *
	 */
	@ApiModelProperty(value="创建日期")
	private Date createtime;

	/**
	 * 修改日期
	 * 表字段 : titemdelivery.updatetime
	 *
	 */
	@ApiModelProperty(value="修改日期")
	private Date updatetime;

	/**
	 *
	 * 表字段 : titemdelivery.createrid
	 *
	 */
	@ApiModelProperty(value="")
	private String createrid;

	/**
	 *
	 * 表字段 : titemdelivery.updaterid
	 *
	 */
	@ApiModelProperty(value="")
	private String updaterid;

	/**
	 *
	 * 表字段 : titemdelivery.city
	 *
	 */
	@ApiModelProperty(value="")
	private String city;

	/**
	 * 是否删除
	 * 表字段 : titemdelivery.fisdel
	 *
	 */
	@ApiModelProperty(value="是否删除")
	private Integer fisdel;

	/**
	 * 是否有效
	 * 表字段 : titemdelivery.fvalid
	 *
	 */
	@ApiModelProperty(value="是否有效")
	private Integer fvalid;

	/**
	 * 解约协议id
	 * 表字段 : titemdelivery.surrenderid
	 *
	 */
	@ApiModelProperty(value="解约协议id")
	private String surrenderid;

	/**
	 * 物品名称
	 * 表字段 : titemdelivery.itemname
	 *
	 */
	@ApiModelProperty(value="物品名称")
	private String itemname;

	/**
	 * 物品单价
	 * 表字段 : titemdelivery.price
	 *
	 */
	@ApiModelProperty(value="物品单价")
	private Double price;

	/**
	 * 0表示房间1表示是床位
	 * 表字段 : titemdelivery.isbeditem
	 *
	 */
	@ApiModelProperty(value="0表示房间1表示是床位")
	private Byte isbeditem;

	/**
	 *
	 * 表字段 : titemdelivery.item_type
	 *
	 */
	@ApiModelProperty(value="")
	private String itemType;

	@ApiModelProperty(value="自定义还是添加")
	private Integer isDefined;

	public Integer getIsDefined() {
		return isDefined;
	}

	public void setIsDefined(Integer isDefined) {
		this.isDefined = isDefined;
	}

	/**
	 * 关联城市表
	 * 表字段 : titemdelivery.cityid
	 *
	 */
	@ApiModelProperty(value="关联城市表")
	private String cityid;

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

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId == null ? null : roomId.trim();
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

	public BigDecimal getFpayfee() {
		return fpayfee;
	}

	public void setFpayfee(BigDecimal fpayfee) {
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

	public Byte getIsbeditem() {
		return isbeditem;
	}

	public void setIsbeditem(Byte isbeditem) {
		this.isbeditem = isbeditem;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType == null ? null : itemType.trim();
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid == null ? null : cityid.trim();
	}
}
