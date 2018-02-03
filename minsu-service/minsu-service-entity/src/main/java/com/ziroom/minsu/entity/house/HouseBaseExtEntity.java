package com.ziroom.minsu.entity.house;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>房源基础信息扩展entity</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class HouseBaseExtEntity extends BaseEntity{
   
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -793858537825646823L;

	//主键id
    private Integer id;

    //逻辑id
    private String fid;

    //房源基础信息fid
    private String houseBaseFid;

    //楼号
    private String buildingNum;

    //单元号
    private String unitNum;

    //楼层
    private String floorNum;

    //门牌号
    private String houseNum;

    //街道
    private String houseStreet;

    //配套设施code
    private String facilityCode;
    
    //服务code
    private String serviceCode;

    //下单类型
    private Integer orderType;

    //民宿类型
    private Integer homestayType;

    //最少入住天数
    private Integer minDay;

    //优惠规则配置code
    private String discountRulesCode;

    //押金规则配置coede
    private String depositRulesCode;

    //退订政策规则配置code
    private String checkOutRulesCode;

    //入住限制人数
    private Integer checkInLimit;

    //入住时间配置
    private String checkInTime;

    //退订时间配置
    private String checkOutTime;

    //床单更换规则
    private Integer sheetsReplaceRules;
    
    //整租折扣
    private Float fullDiscount;
    
    //是否和房东合租 0：否，1：是
    private Integer isTogetherLandlord;
    
    //房源默认图片fid
    private String defaultPicFid;
    
    //旧房源默认图片fid
    private String oldDefaultPicFid;
    
    //详细地址
    private String detailAddress;
    
    //是否是房东提供的图片 0：房东提供 1：摄影师提供
    private Integer isLandlordPic;
    
	//房源品质登记 A B C
	private String houseQualityGrade;
	
	//是否已预约摄影师 0=否 1=是
	private Integer isPhotography;
	
	//实勘结果 100:未实勘 101:符合品质 102:不符品质
	private Integer surveyResult;
	
	//要出租房间的数量（合租）
	private Integer rentRoomNum;
	
	public Integer getIsPhotography() {
		return isPhotography;
	}

	public void setIsPhotography(Integer isPhotography) {
		this.isPhotography = isPhotography;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public String getOldDefaultPicFid() {
		return oldDefaultPicFid;
	}

	public void setOldDefaultPicFid(String oldDefaultPicFid) {
		this.oldDefaultPicFid = oldDefaultPicFid;
	}
	
	public String getDefaultPicFid() {
		return defaultPicFid;
	}

	public void setDefaultPicFid(String defaultPicFid) {
		this.defaultPicFid = defaultPicFid;
	}

	public Integer getIsTogetherLandlord() {
		return isTogetherLandlord;
	}

	public void setIsTogetherLandlord(Integer isTogetherLandlord) {
		this.isTogetherLandlord = isTogetherLandlord;
	}

	public Float getFullDiscount() {
		return fullDiscount;
	}

	public void setFullDiscount(Float fullDiscount) {
		this.fullDiscount = fullDiscount;
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

	public String getHouseBaseFid() {
		return houseBaseFid;
	}

	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid == null ? null : houseBaseFid.trim();
	}

	public String getBuildingNum() {
		return buildingNum;
	}

	public void setBuildingNum(String buildingNum) {
		this.buildingNum = buildingNum == null ? null : buildingNum.trim();
	}

	public String getUnitNum() {
		return unitNum;
	}

	public void setUnitNum(String unitNum) {
		this.unitNum = unitNum == null ? null : unitNum.trim();
	}

	public String getFloorNum() {
		return floorNum;
	}

	public void setFloorNum(String floorNum) {
		this.floorNum = floorNum == null ? null : floorNum.trim();
	}

	public String getHouseNum() {
		return houseNum;
	}

	public void setHouseNum(String houseNum) {
		this.houseNum = houseNum == null ? null : houseNum.trim();
	}

	public String getHouseStreet() {
		return houseStreet;
	}

	public void setHouseStreet(String houseStreet) {
		this.houseStreet = houseStreet == null ? null : houseStreet.trim();
	}

	public String getFacilityCode() {
		return facilityCode;
	}

	public void setFacilityCode(String facilityCode) {
		this.facilityCode = facilityCode == null ? null : facilityCode.trim();
	}
	
	public String getServiceCode() {
		return serviceCode;
	}
	
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getHomestayType() {
		return homestayType;
	}

	public void setHomestayType(Integer homestayType) {
		this.homestayType = homestayType;
	}

	public Integer getMinDay() {
		return minDay;
	}

	public void setMinDay(Integer minDay) {
		this.minDay = minDay;
	}

	public String getDiscountRulesCode() {
		return discountRulesCode;
	}

	public void setDiscountRulesCode(String discountRulesCode) {
		this.discountRulesCode = discountRulesCode == null ? null : discountRulesCode.trim();
	}

	public String getDepositRulesCode() {
		return depositRulesCode;
	}

	public void setDepositRulesCode(String depositRulesCode) {
		this.depositRulesCode = depositRulesCode == null ? null : depositRulesCode.trim();
	}
	
    public Integer getCheckInLimit() {
        return checkInLimit;
    }
   
    public void setCheckInLimit(Integer checkInLimit) {
        this.checkInLimit = checkInLimit;
    }

	public String getCheckOutRulesCode() {
		return checkOutRulesCode;
	}

	public void setCheckOutRulesCode(String checkOutRulesCode) {
		this.checkOutRulesCode = checkOutRulesCode == null ? null : checkOutRulesCode.trim();
	}

	public String getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime == null ? null : checkInTime.trim();
	}

	public String getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime == null ? null : checkOutTime.trim();
	}

	public Integer getSheetsReplaceRules() {
		return sheetsReplaceRules;
	}

	public void setSheetsReplaceRules(Integer sheetsReplaceRules) {
		this.sheetsReplaceRules = sheetsReplaceRules;
	}

	public Integer getIsLandlordPic() {
		return isLandlordPic;
	}

	public void setIsLandlordPic(Integer isLandlordPic) {
		this.isLandlordPic = isLandlordPic;
	}

	public String getHouseQualityGrade() {
		return houseQualityGrade;
	}

	public void setHouseQualityGrade(String houseQualityGrade) {
		this.houseQualityGrade = houseQualityGrade;
	}

	public Integer getSurveyResult() {
		return surveyResult;
	}

	public void setSurveyResult(Integer surveyResult) {
		this.surveyResult = surveyResult;
	}

	public Integer getRentRoomNum() {
		return rentRoomNum;
	}

	public void setRentRoomNum(Integer rentRoomNum) {
		this.rentRoomNum = rentRoomNum;
	}
}