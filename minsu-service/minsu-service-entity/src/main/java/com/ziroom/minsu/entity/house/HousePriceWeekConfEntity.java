package com.ziroom.minsu.entity.house;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>
 * 房源星期价格配置实体
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public class HousePriceWeekConfEntity extends BaseEntity {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -3544573528168451969L;

	// 自增id
	private Integer id;

	// 逻辑id
	private String fid;

	// 房源基本信息表逻辑id
	private String houseBaseFid;

	// 房源房间信息表逻辑id
	private String roomFid;

	// 房源床位信息表逻辑id
	private String bedFid;

	// 配置星期
	private Integer setWeek;

	// 设置价格
	private Integer priceVal;

	// 创建日期
	private Date createDate;

	// 最后修改日期
	private Date lastModifyDate;

	// 创建人uid
	private String createUid;

	// 是否删除(0:否,1:是)
	private Integer isDel; 
	
	// 是否有效(0:否,1:是)
	private Integer isValid; 
	
	// 出租方式 @RentWayEnum
	transient private Integer rentWay; 

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

	public String getHouseBaseFid() {
		return houseBaseFid;
	}

	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid == null ? null : houseBaseFid.trim();
	}

	public String getRoomFid() {
		return roomFid;
	}

	public void setRoomFid(String roomFid) {
		this.roomFid = roomFid == null ? null : roomFid.trim();
	}

	public String getBedFid() {
		return bedFid;
	}

	public void setBedFid(String bedFid) {
		this.bedFid = bedFid == null ? null : bedFid.trim();
	}

	public Integer getSetWeek() {
		return setWeek;
	}

	public void setSetWeek(Integer setWeek) {
		this.setWeek = setWeek;
	}

	public Integer getPriceVal() {
		return priceVal;
	}

	public void setPriceVal(Integer priceVal) {
		this.priceVal = priceVal;
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

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}
	
}