package com.ziroom.minsu.entity.order;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>订单标记表</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年8月1日
 * @since 1.0
 * @version 1.0
 */
public class OrderFlagEntity extends BaseEntity{
	
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -9134999131056302301L;

	/**
	 * 主键id
	 */
	private Integer id;

	/**
	 * 逻辑id
	 */
	private String fid;

	/**
	 * 订单编号
	 */
	private String orderSn;

	/**
	 * 标记code
	 */
	private String flagCode;

	/**
	 * 标记值
	 */
	private String flagValue;

	/**
	 * 创建人fid
	 */
	private String createFid;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 最后修改时间
	 */
	private Date lastModifyDate;

	/**
	 * 是否删除 0：否，1：是
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

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn == null ? null : orderSn.trim();
	}

	public String getFlagCode() {
		return flagCode;
	}

	public void setFlagCode(String flagCode) {
		this.flagCode = flagCode == null ? null : flagCode.trim();
	}

	public String getFlagValue() {
		return flagValue;
	}

	public void setFlagValue(String flagValue) {
		this.flagValue = flagValue == null ? null : flagValue.trim();
	}

	public String getCreateFid() {
		return createFid;
	}

	public void setCreateFid(String createFid) {
		this.createFid = createFid == null ? null : createFid.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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