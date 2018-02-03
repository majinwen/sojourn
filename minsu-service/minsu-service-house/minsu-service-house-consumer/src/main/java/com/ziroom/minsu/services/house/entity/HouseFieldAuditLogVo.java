/**
 * @FileName: HouseFieldAuditLogVo.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author lusp
 * @created 2017年7月31日 上午11:06:11
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>房源字段审核记录vo</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lusp
 * @since 1.0
 * @version 1.0
 */
public class HouseFieldAuditLogVo extends BaseEntity {


	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 2993498741947291449L;

	/**
	 * 审核记录id   主键
	 */
	private Integer id;
	
	/**
	 * 审核记录fid   房源基本信息修改记录表fid    fid=MD5(house_fid+room_fid+rent_way+field_path)
	 */
	private String fid;

	/**
	 * 房源fid
	 */
	private String houseFid;

	/**
	 * 出租方式  0：整租   1：分租
	 */
	private Integer rentWay;

	/**
	 * 房间fid
	 */
	private String roomFid;

	/**
	 *  审核状态 默认0=未审核；1=审核通过；2=审核拒绝
	 */
	private Integer auditStatus;

	/**
	 * 审核字段的路径 (例如： com.ziroom.minsu.entity.house.AbHouseRelateEntity.XXX)
	 */
	private String fieldPath;

	/**
	 * MD5(house_fid+room_fid+rent_way+field_path)
	 */
	private String fieldPathKey;
	
	/**
	 * 审核字段描述
	 */
	private String fieldDesc;
	
	/**
	 * 创建人fid
	 */
	private String createFid;
	
	/**
	 * 创建人类型 0=其他 1=房东 2=业务人员
	 */
	private Integer createType;
	
	/**
	 * 修改字段来源 0=troy 1=pc 2=IOS 3=android 5=APP 6=其他
	 */
	private Integer sourceType;

	/**
	 * 修改字段的旧值
	 */
	private String oldValue;

	/**
	 * 修改字段新值
	 */
	private String newValue;

	/**
	 * 是否是大字段 0=否 1=是 (超过1024 即是大字段)
	 */
	private Integer isText;

	/**
	 * 修改备注
	 */
	private String remark;

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

	public String getHouseFid() {
		return houseFid;
	}

	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public String getRoomFid() {
		return roomFid;
	}

	public void setRoomFid(String roomFid) {
		this.roomFid = roomFid;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getFieldPath() {
		return fieldPath;
	}

	public void setFieldPath(String fieldPath) {
		this.fieldPath = fieldPath;
	}

	public String getFieldPathKey() {
		return fieldPathKey;
	}

	public void setFieldPathKey(String fieldPathKey) {
		this.fieldPathKey = fieldPathKey;
	}

	public String getFieldDesc() {
		return fieldDesc;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	public String getCreateFid() {
		return createFid;
	}

	public void setCreateFid(String createFid) {
		this.createFid = createFid;
	}

	public Integer getCreateType() {
		return createType;
	}

	public void setCreateType(Integer createType) {
		this.createType = createType;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public Integer getIsText() {
		return isText;
	}

	public void setIsText(Integer isText) {
		this.isText = isText;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
