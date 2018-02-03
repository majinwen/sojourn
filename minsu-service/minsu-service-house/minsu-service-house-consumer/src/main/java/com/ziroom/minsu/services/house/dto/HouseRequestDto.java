package com.ziroom.minsu.services.house.dto;

import com.ziroom.minsu.entity.sys.CurrentuserCityEntity;
import com.ziroom.minsu.services.common.dto.PageRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>查询参数dto</p>
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
public class HouseRequestDto extends PageRequest{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -6297821695864643736L;
	
	/**整租参数**/
	// 房东姓名
	private String landlordName;
	
	// 房东手机
	private String landlordMobile;
	
	// 是否上传照片
	private Integer isPic;
	
	// 摄影师姓名
	private String cameramanName;
	
	// 摄影师手机
	private String cameramanMobile;
	
	// 房源名称
	private String houseName;
	
	// 房间类型 0：房间   1：客厅
	private Integer roomType;
	
	// 房源状态
	private Integer houseStatus;
	
	// 国家/地区
	private String nationCode;
	
	// 省
	private String provinceCode;
	
	// 城市
	private String cityCode;
	
	// 房源类型
	private Integer houseType;
	
	// 服务管家
	private String zoName;
	
	// 是否有房源权重
	private Integer isWeight;
	/**整租参数**/
	
	/**合租参数**/
	// 合租房东姓名
	private String landlordNameS;
	
	// 合租房东手机
	private String landlordMobileS;
	
	// 合租是否有图片
	private Integer isPicS;
	
	// 合租摄影师名称
	private String cameramanNameS;
	
	// 合租摄影师电话
	private String cameramanMobileS;
	
	// 合租房源地址
	private String houseNameS;
	
	// 合租房源状态
	private Integer houseStatusS;
	
	// 合租省
	private String provinceCodeS;
	
	// 合租国家/地区
	private String nationCodeS;
	
	// 合租城市
	private String cityCodeS;
	
	// 合租房源类型
	private Integer houseTypeS;

	// 合租服务管家
	private String zoNameS;

	// 合租是否有房源权重
	private Integer isWeightS;
	/**合租参数**/
	
	// 房东姓名
	private List<String> landlordUidList;
	
	// 房源状态查询条件集合
	private List<Integer> houseStatusList;
	
	// 房源出租类型
	private Integer rentWay;
	
	//房源创建起始时间
	private String houseCreateTimeStart;
	
	//房源创建结束时间
	private String houseCreateTimeEnd;
	
	//房源上架起始时间
	private String houseOnlineTimeStart;
	
	//房源上架结束时间
	private String houseOnlineTimeEnd;
	
	//房源编号
	private String houseSn;
	
	//房间创建起始时间
	private String roomCreateTimeStart;
	
	//房间创建结束时间
	private String roomCreateTimeEnd;
	
	//房间上架起始时间
	private String roomOnlineTimeStart;
	
	//房间上架结束时间
	private String roomOnlineTimeEnd;
	
	//房间编号
	private String roomSn;
	
	//地推管家姓名
	private String empPushName;
	
	//维护管家姓名
	private String empGuardName;
	
	@Deprecated // 审核说明
	private Integer cause;
	
	// 审核说明
	private String auditCause;
	
	/**
	 * 房源渠道  0：地推，1：直营，2：房东
	 */
	private Integer houseChannel;
	
	/**
	 * 房源品质审核等级
	 */
	private String houseQualityGrade;
	
    /**
     * 权限类型
     */
    private int roleType=0;
    
    /**
     * 员工code
     */
    private String empCode;
    
	/*
	 * 实勘结果 0：未实勘，1：符合品质，2;不符品质
	 */
	private Integer surveyResult;
	
	/**
	 * 管家审核开始时间
	 */
	private String zoDateStart;
	
	/**
	 * 管家审核结束时间
	 */
	private String zoDateEnd;
	/**
	 * 最新发布查询开始时间
	 */
	private String issueLastTimeStart;
	/**
	 * 最新发布查询结束时间
	 */
	private String issueLastTimeEnd;
	
	/**
	 * 排序规则
	 */
	private Integer orderByType;
	
	public Integer getOrderByType() {
		return orderByType;
	}

	public void setOrderByType(Integer orderByType) {
		this.orderByType = orderByType;
	}


	/**
	 * @return the zoDateStart
	 */
	public String getZoDateStart() {
		return zoDateStart;
	}

	/**
	 * @param zoDateStart the zoDateStart to set
	 */
	public void setZoDateStart(String zoDateStart) {
		this.zoDateStart = zoDateStart;
	}

	/**
	 * @return the zoDateEnd
	 */
	public String getZoDateEnd() {
		return zoDateEnd;
	}

	/**
	 * @param zoDateEnd the zoDateEnd to set
	 */
	public void setZoDateEnd(String zoDateEnd) {
		this.zoDateEnd = zoDateEnd;
	}

	public Integer getSurveyResult() {
		return surveyResult;
	}

	public void setSurveyResult(Integer surveyResult) {
		this.surveyResult = surveyResult;
	}
    
    
	/**
     * 服务区域
     */
    private List<CurrentuserCityEntity> userCityList=new ArrayList<CurrentuserCityEntity>();
    
    /**
	 * @return the roleType
	 */
	public int getRoleType() {
		return roleType;
	}

	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

	/**
	 * @return the empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * @param empCode the empCode to set
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * @return the userCityList
	 */
	public List<CurrentuserCityEntity> getUserCityList() {
		return userCityList;
	}

	/**
	 * @param userCityList the userCityList to set
	 */
	public void setUserCityList(List<CurrentuserCityEntity> userCityList) {
		this.userCityList = userCityList;
	}

	public Integer getHouseChannel() {
		return houseChannel;
	}

	public void setHouseChannel(Integer houseChannel) {
		this.houseChannel = houseChannel;
	}

	public Integer getIsPicS() {
		return isPicS;
	}

	public void setIsPicS(Integer isPicS) {
		this.isPicS = isPicS;
	}

	public String getCameramanNameS() {
		return cameramanNameS;
	}

	public void setCameramanNameS(String cameramanNameS) {
		this.cameramanNameS = cameramanNameS;
	}

	public String getCameramanMobileS() {
		return cameramanMobileS;
	}

	public void setCameramanMobileS(String cameramanMobileS) {
		this.cameramanMobileS = cameramanMobileS;
	}
	
	public Integer getHouseStatusS() {
		return houseStatusS;
	}
	
	public void setHouseStatusS(Integer houseStatusS) {
		this.houseStatusS = houseStatusS;
	}

	public String getProvinceCodeS() {
		return provinceCodeS;
	}

	public void setProvinceCodeS(String provinceCodeS) {
		this.provinceCodeS = provinceCodeS;
	}

	public String getNationCodeS() {
		return nationCodeS;
	}

	public void setNationCodeS(String nationCodeS) {
		this.nationCodeS = nationCodeS;
	}

	public String getCityCodeS() {
		return cityCodeS;
	}

	public void setCityCodeS(String cityCodeS) {
		this.cityCodeS = cityCodeS;
	}

	public String getLandlordName() {
		return landlordName;
	}

	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}

	public String getLandlordMobile() {
		return landlordMobile;
	}

	public void setLandlordMobile(String landlordMobile) {
		this.landlordMobile = landlordMobile;
	}

	public Integer getIsPic() {
		return isPic;
	}

	public void setIsPic(Integer isPic) {
		this.isPic = isPic;
	}

	public String getCameramanName() {
		return cameramanName;
	}

	public void setCameramanName(String cameramanName) {
		this.cameramanName = cameramanName;
	}

	public String getCameramanMobile() {
		return cameramanMobile;
	}

	public void setCameramanMobile(String cameramanMobile) {
		this.cameramanMobile = cameramanMobile;
	}
	
	public Integer getHouseStatus() {
		return houseStatus;
	}
	
	public void setHouseStatus(Integer houseStatus) {
		this.houseStatus = houseStatus;
	}

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}
	
	public String getProvinceCode() {
		return provinceCode;
	}
	
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public List<Integer> getHouseStatusList() {
		return houseStatusList;
	}

	public void setHouseStatusList(List<Integer> houseStatusList) {
		this.houseStatusList = houseStatusList;
	}

	public Integer getHouseType() {
		return houseType;
	}

	public void setHouseType(Integer houseType) {
		this.houseType = houseType;
	}

	public Integer getHouseTypeS() {
		return houseTypeS;
	}

	public void setHouseTypeS(Integer houseTypeS) {
		this.houseTypeS = houseTypeS;
	}

	public String getZoName() {
		return zoName;
	}

	public void setZoName(String zoName) {
		this.zoName = zoName;
	}

	public String getZoNameS() {
		return zoNameS;
	}

	public void setZoNameS(String zoNameS) {
		this.zoNameS = zoNameS;
	}

	public Integer getIsWeight() {
		return isWeight;
	}

	public void setIsWeight(Integer isWeight) {
		this.isWeight = isWeight;
	}

	public Integer getIsWeightS() {
		return isWeightS;
	}

	public void setIsWeightS(Integer isWeightS) {
		this.isWeightS = isWeightS;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getHouseNameS() {
		return houseNameS;
	}

	public void setHouseNameS(String houseNameS) {
		this.houseNameS = houseNameS;
	}

	public String getLandlordNameS() {
		return landlordNameS;
	}

	public void setLandlordNameS(String landlordNameS) {
		this.landlordNameS = landlordNameS;
	}

	public String getLandlordMobileS() {
		return landlordMobileS;
	}

	public void setLandlordMobileS(String landlordMobileS) {
		this.landlordMobileS = landlordMobileS;
	}

	public List<String> getLandlordUidList() {
		return landlordUidList;
	}

	public void setLandlordUidList(List<String> landlordUidList) {
		this.landlordUidList = landlordUidList;
	}

	public String getHouseCreateTimeStart() {
		return houseCreateTimeStart;
	}
	
	public void setHouseCreateTimeStart(String houseCreateTimeStart) {
		this.houseCreateTimeStart = houseCreateTimeStart;
	}
	
	public String getHouseCreateTimeEnd() {
		return houseCreateTimeEnd;
	}
	
	public void setHouseCreateTimeEnd(String houseCreateTimeEnd) {
		this.houseCreateTimeEnd = houseCreateTimeEnd;
	}
	
	public String getRoomCreateTimeStart() {
		return roomCreateTimeStart;
	}
	
	public void setRoomCreateTimeStart(String roomCreateTimeStart) {
		this.roomCreateTimeStart = roomCreateTimeStart;
	}
	
	public String getRoomCreateTimeEnd() {
		return roomCreateTimeEnd;
	}
	
	public void setRoomCreateTimeEnd(String roomCreateTimeEnd) {
		this.roomCreateTimeEnd = roomCreateTimeEnd;
	}

	public String getHouseSn() {
		return houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public String getRoomSn() {
		return roomSn;
	}

	public void setRoomSn(String roomSn) {
		this.roomSn = roomSn;
	}

	public String getHouseOnlineTimeStart() {
		return houseOnlineTimeStart;
	}

	public void setHouseOnlineTimeStart(String houseOnlineTimeStart) {
		this.houseOnlineTimeStart = houseOnlineTimeStart;
	}

	public String getHouseOnlineTimeEnd() {
		return houseOnlineTimeEnd;
	}

	public void setHouseOnlineTimeEnd(String houseOnlineTimeEnd) {
		this.houseOnlineTimeEnd = houseOnlineTimeEnd;
	}

	public String getRoomOnlineTimeStart() {
		return roomOnlineTimeStart;
	}

	public void setRoomOnlineTimeStart(String roomOnlineTimeStart) {
		this.roomOnlineTimeStart = roomOnlineTimeStart;
	}

	public String getRoomOnlineTimeEnd() {
		return roomOnlineTimeEnd;
	}

	public void setRoomOnlineTimeEnd(String roomOnlineTimeEnd) {
		this.roomOnlineTimeEnd = roomOnlineTimeEnd;
	}
	
	public String getEmpPushName() {
		return empPushName;
	}
	
	public void setEmpPushName(String empPushName) {
		this.empPushName = empPushName;
	}
	
	public String getEmpGuardName() {
		return empGuardName;
	}
	
	public void setEmpGuardName(String empGuardName) {
		this.empGuardName = empGuardName;
	}

	@Deprecated
	public Integer getCause() {
		return cause;
	}

	@Deprecated
	public void setCause(Integer cause) {
		this.cause = cause;
	}

	public String getHouseQualityGrade() {
		return houseQualityGrade;
	}

	public void setHouseQualityGrade(String houseQualityGrade) {
		this.houseQualityGrade = houseQualityGrade;
	}

	public String getAuditCause() {
		return auditCause;
	}

	public void setAuditCause(String auditCause) {
		this.auditCause = auditCause;
	}

	public String getIssueLastTimeStart() {
		return issueLastTimeStart;
	}

	public void setIssueLastTimeStart(String issueLastTimeStart) {
		this.issueLastTimeStart = issueLastTimeStart;
	}

	public String getIssueLastTimeEnd() {
		return issueLastTimeEnd;
	}

	public void setIssueLastTimeEnd(String issueLastTimeEnd) {
		this.issueLastTimeEnd = issueLastTimeEnd;
	}

	public Integer getRoomType() {
		return roomType;
	}

	public void setRoomType(Integer roomType) {
		this.roomType = roomType;
	}

	/**
	 *下单类型 1：立即预订，2：申请预订

	 */
	private Integer orderType;

	/**
	 *  是否与房东同住 0：否，1：是
	 */
	private Integer isTogetherLandlord;

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getIsTogetherLandlord() {
		return isTogetherLandlord;
	}

	public void setIsTogetherLandlord(Integer isTogetherLandlord) {
		this.isTogetherLandlord = isTogetherLandlord;
	}
}
