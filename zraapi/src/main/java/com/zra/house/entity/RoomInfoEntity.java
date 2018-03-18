package com.zra.house.entity;

import java.util.Date;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/8/1 16:13
 * @since 1.0
 */
public class RoomInfoEntity {

    /**
     * 主键
     */
    private String id;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 楼栋ID
     */
    private String buildingId;

    /**
     * 层数
     */
    private Integer floorNumber;

    /**
     * 房间号
     */
    private String roomNumber;

    /**
     * 房间面积
     */
    private Double roomArea;

    /**
     * 房间实际居住表id
     */
    private String roomLiveId;

    /**
     * 户型ID
     */
    private String houseTypeId;

    /**
     * 基础价格
     */
    private Double basePrice;

    /**
     * 当前长租房价
     */
    private Double longPrice;

    /**
     * 当前短租房价(1-3个月，不包括3个月)
     */
    private Double shortPrice;

    /**
     * 当前短租房价(3-6个月，包括3个月)
     */
    private Double shortPrice2;

    /**
     * 床类型
     */
    private String bedType;

    /**
     * 装修风格
     */
    private String decorationStyle;

    /**
     *   朝向
     */
    private String direction;

    /**
     * 是否可短租
     */
    private String shortRent;

    /**
     * 当前状态
     */
    private String currentState;

    /**
     * 网站是否显示
     */
    private Integer isNetShow;

    /**
     * 公司ID
     */
    private String companyId;

    /**
     * 是否有效
     */
    private Integer valid;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createrId;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private String updaterId;

    /**
     * 是否删除
     */
    private Integer isDel;

    /**
     * 锁定时间
     */
    private Date lockTime;

    /**
     * 禁止时间
     */
    private Date disableTime;

    /**
     * 房间配置完成日期
     */
    private Date configCompleteDate;

    /**
     * 停用启用房间标识;0:停用;1启用; 只有非已出租房有此功能
     */
    private Integer isUsed;

    /**
     * 关联城市表
     */
    private String cityId;

    /**
     * 锁定房间来源:空或1为自如寓管理后台锁定;2为M站锁定
     */
    private String lockRoomSource;

    /**
     * 房间描述
     */
    private String roomDesc;

    /**
     * 可签约日期(只有状态为可预订时，此字段才会有意义）
     */
    private Date avaSignDate;
    
    private Integer rentType;
    
    

    public Integer getRentType() {
		return rentType;
	}

	public void setRentType(Integer rentType) {
		this.rentType = rentType;
	}

	public Date getAvaSignDate() {
        return avaSignDate;
    }

    public void setAvaSignDate(Date avaSignDate) {
        this.avaSignDate = avaSignDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Double getRoomArea() {
        return roomArea;
    }

    public void setRoomArea(Double roomArea) {
        this.roomArea = roomArea;
    }

    public String getHouseTypeId() {
        return houseTypeId;
    }

    public void setHouseTypeId(String houseTypeId) {
        this.houseTypeId = houseTypeId;
    }

    public String getRoomLiveId() {
        return roomLiveId;
    }

    public void setRoomLiveId(String roomLiveId) {
        this.roomLiveId = roomLiveId;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Double getLongPrice() {
        return longPrice;
    }

    public void setLongPrice(Double longPrice) {
        this.longPrice = longPrice;
    }

    public Double getShortPrice() {
        return shortPrice;
    }

    public void setShortPrice(Double shortPrice) {
        this.shortPrice = shortPrice;
    }

    public Double getShortPrice2() {
        return shortPrice2;
    }

    public void setShortPrice2(Double shortPrice2) {
        this.shortPrice2 = shortPrice2;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public String getDecorationStyle() {
        return decorationStyle;
    }

    public void setDecorationStyle(String decorationStyle) {
        this.decorationStyle = decorationStyle;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getShortRent() {
        return shortRent;
    }

    public void setShortRent(String shortRent) {
        this.shortRent = shortRent;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public Integer getIsNetShow() {
        return isNetShow;
    }

    public void setIsNetShow(Integer isNetShow) {
        this.isNetShow = isNetShow;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    public Date getDisableTime() {
        return disableTime;
    }

    public void setDisableTime(Date disableTime) {
        this.disableTime = disableTime;
    }

    public Date getConfigCompleteDate() {
        return configCompleteDate;
    }

    public void setConfigCompleteDate(Date configCompleteDate) {
        this.configCompleteDate = configCompleteDate;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getLockRoomSource() {
        return lockRoomSource;
    }

    public void setLockRoomSource(String lockRoomSource) {
        this.lockRoomSource = lockRoomSource;
    }

    public String getRoomDesc() {
        return roomDesc;
    }

    public void setRoomDesc(String roomDesc) {
        this.roomDesc = roomDesc;
    }
}
