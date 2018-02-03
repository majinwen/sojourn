package com.ziroom.minsu.entity.house;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>
 * 房源床位信息实体
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class HouseBedMsgEntity extends BaseEntity {
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -4490032497990014968L;

	// 自增id
	private Integer id;

	// 逻辑id
	private String fid;

	// 房源房间信息表逻辑id
	private String roomFid;

	// 房源基本信息表逻辑id
	private String houseBaseFid;

	// 床位状态(10:待发布,11:已发布,20:信息审核通过,21:信息审核未通过,30:照片审核未通过,40:上架,41:下架,50:强制下架)
	private Integer bedStatus;
	
	// 床位类型
	private Integer bedType;
	
	// 床位尺寸
	private Integer bedSize;

	// 床位编号
	private Integer bedSn;

	// 床位日价格(分)
	private Integer bedPrice;

	// 创建日期
	private Date createDate;

	// 最后修改日期
	private Date lastModifyDate;

	// 创建人uid
	private String createUid;

	// 是否删除(0:否,1:是)
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

	public String getRoomFid() {
		return roomFid;
	}

	public void setRoomFid(String roomFid) {
		this.roomFid = roomFid == null ? null : roomFid.trim();
	}

	public String getHouseBaseFid() {
		return houseBaseFid;
	}

	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid == null ? null : houseBaseFid.trim();
	}

	public Integer getBedStatus() {
		return bedStatus;
	}

	public void setBedStatus(Integer bedStatus) {
		this.bedStatus = bedStatus;
	}
	
	public Integer getBedType() {
		return bedType;
	}
	
	public void setBedType(Integer bedType) {
		this.bedType = bedType;
	}
	
	public Integer getBedSize() {
		return bedSize;
	}
	
	public void setBedSize(Integer bedSize) {
		this.bedSize = bedSize;
	}

	public Integer getBedSn() {
		return bedSn;
	}

	public void setBedSn(Integer bedSn) {
		this.bedSn = bedSn;
	}

	public Integer getBedPrice() {
		return bedPrice;
	}

	public void setBedPrice(Integer bedPrice) {
		this.bedPrice = bedPrice;
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

	public String getCreateUid() {
		return createUid;
	}

	public void setCreateUid(String createUid) {
		this.createUid = createUid == null ? null : createUid.trim();
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
}