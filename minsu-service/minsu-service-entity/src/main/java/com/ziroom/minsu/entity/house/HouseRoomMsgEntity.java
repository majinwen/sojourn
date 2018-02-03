package com.ziroom.minsu.entity.house;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 
 * <p>
 * 房源房间信息实体
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
public class HouseRoomMsgEntity extends BaseEntity {
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -1871282830182672552L;

	// 自增id
	private Integer id;

	// 逻辑id
	private String fid;

	// 房源基本信息表逻辑id
	private String houseBaseFid;

	// 房间名称
	private String roomName;

	// 床位数量
	private Integer bedNum;

	// 房间面积
	private Double roomArea;

	// 房间状态(10:待发布,11:已发布,20:信息审核通过,21:信息审核未通过,30:照片审核未通过,40:上架,41:下架,50:强制下架)
	private Integer roomStatus;
	
	// 房间权重
	private Integer roomWeight;

	// 房间价格(分)
	private Integer roomPrice;

	// 是否有卫生间(0:否,1:是)
	private Integer isToilet;

	// 是否有阳台(0:否,1:是)
	private Integer isBalcony;

	// 房间朝向(1:东,2:南,3:西,4:北)
	private Integer roomAspect;

	// 创建日期
	private Date createDate;

	// 最后修改日期
	private Date lastModifyDate;

	// 创建人uid
	private String createUid;

	// 是否删除(0:否,1:是)
	private Integer isDel;
	
	//是否有图片(0:否，1：是)
	private Integer isPic;
	
	//入住人数限制
	private Integer checkInLimit;
	
	//是否默认（0：否，1：是）
	private Integer isDefault;
	
	//房间编号
	private String roomSn;
	
	//独立房间默认图片fid
	private String defaultPicFid;
	
	//旧房源默认图片fid
    private String oldDefaultPicFid;
    
    // 房间保洁费(分)
 	private Integer roomCleaningFees;

	/**
	 * @author yanb
	 * @created 2017年11月16日 22:43:24
	 * 房间类型 0：房间   1：客厅
	 */
	private Integer roomType;

	public Integer getRoomType() {
		return roomType;
	}

	public void setRoomType(Integer roomType) {
		this.roomType = roomType;
	}

	public Integer getRoomCleaningFees() {
		return roomCleaningFees;
	}

	public void setRoomCleaningFees(Integer roomCleaningFees) {
		this.roomCleaningFees = roomCleaningFees;
	}

	/**
	 * @return the oldDefaultPicFid
	 */
	public String getOldDefaultPicFid() {
		return oldDefaultPicFid;
	}

	/**
	 * @param oldDefaultPicFid the oldDefaultPicFid to set
	 */
	public void setOldDefaultPicFid(String oldDefaultPicFid) {
		this.oldDefaultPicFid = oldDefaultPicFid;
	}

	/**
	 * @return the defaultPicFid
	 */
	public String getDefaultPicFid() {
		return defaultPicFid;
	}

	/**
	 * @param defaultPicFid the defaultPicFid to set
	 */
	public void setDefaultPicFid(String defaultPicFid) {
		this.defaultPicFid = defaultPicFid;
	}

	public String getRoomSn() {
		return roomSn;
	}

	public void setRoomSn(String roomSn) {
		this.roomSn = roomSn;
	}

	/**
	 * @return the isDefault
	 */
	public Integer getIsDefault() {
		return isDefault;
	}

	/**
	 * @param isDefault the isDefault to set
	 */
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public Integer getCheckInLimit() {
		return checkInLimit;
	}

	public void setCheckInLimit(Integer checkInLimit) {
		this.checkInLimit = checkInLimit;
	}

	public Integer getId() {
		return id;
	}

	public Integer getIsPic() {
		return isPic;
	}

	public void setIsPic(Integer isPic) {
		this.isPic = isPic;
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

	public String getHouseBaseFid() {
		return houseBaseFid;
	}

	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid == null ? null : houseBaseFid.trim();
	}
	
	public String getRoomName() {
		return roomName;
	}
	
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Integer getBedNum() {
		return bedNum;
	}

	public void setBedNum(Integer bedNum) {
		this.bedNum = bedNum;
	}

	public Double getRoomArea() {
		return roomArea;
	}

	public void setRoomArea(Double roomArea) {
		this.roomArea = roomArea;
	}

	public Integer getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(Integer roomStatus) {
		this.roomStatus = roomStatus;
	}
	
	public Integer getRoomWeight() {
		return roomWeight;
	}
	
	public void setRoomWeight(Integer roomWeight) {
		this.roomWeight = roomWeight;
	}

	public Integer getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(Integer roomPrice) {
		this.roomPrice = roomPrice;
	}

	public Integer getIsToilet() {
		return isToilet;
	}

	public void setIsToilet(Integer isToilet) {
		this.isToilet = isToilet;
	}

	public Integer getIsBalcony() {
		return isBalcony;
	}

	public void setIsBalcony(Integer isBalcony) {
		this.isBalcony = isBalcony;
	}

	public Integer getRoomAspect() {
		return roomAspect;
	}

	public void setRoomAspect(Integer roomAspect) {
		this.roomAspect = roomAspect;
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