package com.zra.common.dto.house;

import java.util.Date;

/**
 * @author wangws21 2016-7-29 
 * 项目dto
 */
public class ProjectDto {
	
	//主键
	private String id; 
	//项目编码
	private String code;
	/*公司id*/
	private String companyId;
	/*项目名称*/
	private String name;
	/*副标题*/
	private String title;
	/*项目介绍*/
	private String projectDescribe;
	/*项目地址*/
	private String address;
	/*经度*/
	private Double lon;
	/*维度*/
	private Double lat;
	/*项目所属商圈*/
	private Integer businessDistrict;
	/*项目所属区域*/
	private String region;
	/*项目面积*/
	private Double area;
	/*整栋数*/	
	private Integer buildingNum;
	/*车位数量*/
	private Integer carPortNum;
	/*楼体结构*/
	private String buildingStruture;
	/*房间总数*/
	private Integer roomNum;
	/*户型数*/
	private Integer houseTypeNum;
	/*公区面积*/
	private Integer publicArea;
	// 用电类别
	private String electricityType; 
	// 用水类别
	private String waterType; 
	/*项目合作模式*/
	private String cooperationModel;
	/*业主类型*/
	private String ownerType;
	/*业主*/
	private String ownerName;
	/*产权类型*/
	private String propertyType;
	/*建成年代*/
	private Integer finishYear;
	/*项目收楼日期*/
	private Date contractBegin;	
	/*项目租赁年限*/
	private Integer contractCycle;
	/*项目到期日期*/
	private Date contractEnd;
	/*项目开业日期*/
	private Date openingTime;
	/*项目空置总天数*/
	private Integer vacancyDay;
	/*项目负责人*/
	private String projectManager;
	/*销售电话*/
	private String marketTel;
	/*门锁类型*/
	private String lockType;
	/*是否删除*/
	private Integer isDel = 0;
	// AFA项目编码
	private String afaProNum;
	/*关联城市表*/
	private String cityId;
	// 风采展示项目图片
	private String projectShowImage;
	/*全景看房*/
	private String panoramicUrl;
	/*周边链接*/
	private String peripheralUrl;
	/*分享链接*/
	private String shareUrl;
	/*项目头图*/
	private String headFigureUrl;
	/*周边模块名称*/
	private String peripheralName;
	
	//add by tianxf9 0608App优化
	/*简介地址*/ 
	private String addressDesc;
	
	//add by tianxf9 1212自如寓-预售项目
	private Integer projectState;

	public String getPeripheralName() {
		return peripheralName;
	}

	public void setPeripheralName(String peripheralName) {
		this.peripheralName = peripheralName;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getProjectDescribe() {
		return projectDescribe;
	}
	public void setProjectDescribe(String projectDescribe) {
		this.projectDescribe = projectDescribe;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Integer getBusinessDistrict() {
		return businessDistrict;
	}
	public void setBusinessDistrict(Integer businessDistrict) {
		this.businessDistrict = businessDistrict;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public Double getArea() {
		return area;
	}
	public void setArea(Double area) {
		this.area = area;
	}
	public Integer getBuildingNum() {
		return buildingNum;
	}
	public void setBuildingNum(Integer buildingNum) {
		this.buildingNum = buildingNum;
	}
	public Integer getCarPortNum() {
		return carPortNum;
	}
	public void setCarPortNum(Integer carPortNum) {
		this.carPortNum = carPortNum;
	}
	public String getBuildingStruture() {
		return buildingStruture;
	}
	public void setBuildingStruture(String buildingStruture) {
		this.buildingStruture = buildingStruture;
	}
	public Integer getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}
	public Integer getHouseTypeNum() {
		return houseTypeNum;
	}
	public void setHouseTypeNum(Integer houseTypeNum) {
		this.houseTypeNum = houseTypeNum;
	}
	public Integer getPublicArea() {
		return publicArea;
	}
	public void setPublicArea(Integer publicArea) {
		this.publicArea = publicArea;
	}
	public String getElectricityType() {
		return electricityType;
	}
	public void setElectricityType(String electricityType) {
		this.electricityType = electricityType;
	}
	public String getWaterType() {
		return waterType;
	}
	public void setWaterType(String waterType) {
		this.waterType = waterType;
	}
	public String getCooperationModel() {
		return cooperationModel;
	}
	public void setCooperationModel(String cooperationModel) {
		this.cooperationModel = cooperationModel;
	}
	public String getOwnerType() {
		return ownerType;
	}
	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	public Integer getFinishYear() {
		return finishYear;
	}
	public void setFinishYear(Integer finishYear) {
		this.finishYear = finishYear;
	}
	public Date getContractBegin() {
		return contractBegin;
	}
	public void setContractBegin(Date contractBegin) {
		this.contractBegin = contractBegin;
	}
	public Integer getContractCycle() {
		return contractCycle;
	}
	public void setContractCycle(Integer contractCycle) {
		this.contractCycle = contractCycle;
	}
	public Date getContractEnd() {
		return contractEnd;
	}
	public void setContractEnd(Date contractEnd) {
		this.contractEnd = contractEnd;
	}
	public Date getOpeningTime() {
		return openingTime;
	}
	public void setOpeningTime(Date openingTime) {
		this.openingTime = openingTime;
	}
	public Integer getVacancyDay() {
		return vacancyDay;
	}
	public void setVacancyDay(Integer vacancyDay) {
		this.vacancyDay = vacancyDay;
	}
	public String getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}
	public String getMarketTel() {
		return marketTel;
	}
	public void setMarketTel(String marketTel) {
		this.marketTel = marketTel;
	}
	public String getLockType() {
		return lockType;
	}
	public void setLockType(String lockType) {
		this.lockType = lockType;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public String getAfaProNum() {
		return afaProNum;
	}
	public void setAfaProNum(String afaProNum) {
		this.afaProNum = afaProNum;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getProjectShowImage() {
		return projectShowImage;
	}
	public void setProjectShowImage(String projectShowImage) {
		this.projectShowImage = projectShowImage;
	}
	public String getPanoramicUrl() {
		return panoramicUrl;
	}
	public void setPanoramicUrl(String panoramicUrl) {
		this.panoramicUrl = panoramicUrl;
	}
	public String getPeripheralUrl() {
		return peripheralUrl;
	}
	public void setPeripheralUrl(String peripheralUrl) {
		this.peripheralUrl = peripheralUrl;
	}
	public String getShareUrl() {
		return shareUrl;
	}
	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}
	public String getHeadFigureUrl() {
		return headFigureUrl;
	}
	public void setHeadFigureUrl(String headFigureUrl) {
		this.headFigureUrl = headFigureUrl;
	}

	public String getAddressDesc() {
		return addressDesc;
	}

	public void setAddressDesc(String addressDesc) {
		this.addressDesc = addressDesc;
	}

	public Integer getProjectState() {
		return projectState;
	}

	public void setProjectState(Integer projectState) {
		this.projectState = projectState;
	}
	
	
}
