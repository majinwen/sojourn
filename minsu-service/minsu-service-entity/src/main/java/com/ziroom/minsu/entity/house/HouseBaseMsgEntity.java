package com.ziroom.minsu.entity.house;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>
 * 房源基本信息实体
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
public class HouseBaseMsgEntity extends BaseEntity {

	/**
	 *  序列化id
	 */
	private static final long serialVersionUID = 1525468350550797018L;

	// 自增id
	private Integer id;

	// 逻辑id
	private String fid;

	// 房源物理信息表逻辑id
	private String phyFid;

	// 房源名称
	private String houseName;

	// 房源出租类型(0:整租,1:合租,2:床位)
	private Integer rentWay;

    //房屋类型
    private Integer houseType;

	// 房源状态(10:待发布,11:已发布,20:管家审核通过,21:管家审核未通过,30:品质审核未通过,40:上架,41:下架,50:强制下架)
	private Integer houseStatus;
	
	// 房源旧状态，更新时使用
	transient private Integer oldStatus;
	
	// 房源权重
	private Integer houseWeight;

	// 房东uid
	private String landlordUid;

	// 房源日价格(分)
	private Integer leasePrice; 

	// 房源面积
	private Double houseArea;

	// 卧室数量
	private Integer roomNum;

	// 客厅数量
	private Integer hallNum;

	// 卫生间数量
	private Integer toiletNum;

	// 厨房数量
	private Integer kitchenNum;

	// 阳台数量
	private Integer balconyNum;

	// 摄影师姓名
	private String cameramanName;

	// 摄影师手机号
	private String cameramanMobile;

	// 操作步骤
	private Integer operateSeq;
	
	//信息完整率
	private Double intactRate;

	// 房源刷新日期
	private Date refreshDate;

	// 截止日期
	private Date tillDate;

	// 创建日期
	private Date createDate;

	// 最后修改日期
	private Date lastModifyDate;

	// 是否修改(0:否,1:是)
	private Integer isDel;
	
	// 房源项详细地址
	private String houseAddr;
	
	// 是否有图片(0:否，1：是)
	private Integer isPic;
	
	// 是否有智能锁(0:否，1：是)
	private Integer isLock;
	
	//房源的录入来源
	private Integer houseSource;
	
	//房源编号
	private String houseSn;
	
	// 房源保洁费(分)
	private Integer houseCleaningFees;
	
	/**
	 * 房源渠道  0:地推, 1:直营, 2:房东
	 */
	private Integer houseChannel;

	public Integer getHouseChannel() {
		return houseChannel;
	}


	public void setHouseChannel(Integer houseChannel) {
		this.houseChannel = houseChannel;
	}


	public Integer getHouseCleaningFees() {
		return houseCleaningFees;
	}


	public void setHouseCleaningFees(Integer houseCleaningFees) {
		this.houseCleaningFees = houseCleaningFees;
	}


	public String getHouseSn() {
		return houseSn;
	}


	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}


	public Integer getHouseSource() {
		return houseSource;
	}


	public void setHouseSource(Integer houseSource) {
		this.houseSource = houseSource;
	}


	public String getHouseAddr() {
		return houseAddr;
	}

	
	public void setHouseAddr(String houseAddr) {
		this.houseAddr = houseAddr;
	}

	
	public Integer getIsPic() {
		return isPic;
	}

	
	public void setIsPic(Integer isPic) {
		this.isPic = isPic;
	}

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

	public String getPhyFid() {
		return phyFid;
	}

	public void setPhyFid(String phyFid) {
		this.phyFid = phyFid == null ? null : phyFid.trim();
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName == null ? null : houseName.trim();
	}
	
	public Integer getRentWay() {
		return rentWay;
	}
	
	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

    public Integer getHouseType() {
        return houseType;
    }

    public void setHouseType(Integer houseType) {
        this.houseType = houseType;
    }

	public Integer getHouseStatus() {
		return houseStatus;
	}

	public void setHouseStatus(Integer houseStatus) {
		this.houseStatus = houseStatus;
	}
	
	public Integer getHouseWeight() {
		return houseWeight;
	}
	
	public void setHouseWeight(Integer houseWeight) {
		this.houseWeight = houseWeight;
	}

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid == null ? null : landlordUid.trim();
	}

	public Integer getLeasePrice() {
		return leasePrice;
	}

	public void setLeasePrice(Integer leasePrice) {
		this.leasePrice = leasePrice;
	}

	public Double getHouseArea() {
		return houseArea;
	}

	public void setHouseArea(Double houseArea) {
		this.houseArea = houseArea;
	}

	public Integer getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}

	public Integer getHallNum() {
		return hallNum;
	}

	public void setHallNum(Integer hallNum) {
		this.hallNum = hallNum;
	}

	public Integer getToiletNum() {
		return toiletNum;
	}

	public void setToiletNum(Integer toiletNum) {
		this.toiletNum = toiletNum;
	}

	public Integer getKitchenNum() {
		return kitchenNum;
	}

	public void setKitchenNum(Integer kitchenNum) {
		this.kitchenNum = kitchenNum;
	}

	public Integer getBalconyNum() {
		return balconyNum;
	}

	public void setBalconyNum(Integer balconyNum) {
		this.balconyNum = balconyNum;
	}

	public String getCameramanName() {
		return cameramanName;
	}

	public void setCameramanName(String cameramanName) {
		this.cameramanName = cameramanName == null ? null : cameramanName
				.trim();
	}

	public String getCameramanMobile() {
		return cameramanMobile;
	}

	public void setCameramanMobile(String cameramanMobile) {
		this.cameramanMobile = cameramanMobile == null ? null : cameramanMobile
				.trim();
	}

	public Integer getOperateSeq() {
		return operateSeq;
	}

	public void setOperateSeq(Integer operateSeq) {
		this.operateSeq = operateSeq;
	}

	public Date getRefreshDate() {
		return refreshDate;
	}

	public void setRefreshDate(Date refreshDate) {
		this.refreshDate = refreshDate;
	}

	public Date getTillDate() {
		return tillDate;
	}

	public void setTillDate(Date tillDate) {
		this.tillDate = tillDate;
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

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Double getIntactRate() {
		return intactRate;
	}

	public void setIntactRate(Double intactRate) {
		this.intactRate = intactRate;
	}

	public Integer getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(Integer oldStatus) {
		this.oldStatus = oldStatus;
	}

	public Integer getIsLock() {
		return isLock;
	}

	public void setIsLock(Integer isLock) {
		this.isLock = isLock;
	}
 
}
