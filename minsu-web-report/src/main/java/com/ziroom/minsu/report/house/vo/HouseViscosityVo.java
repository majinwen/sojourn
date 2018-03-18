package com.ziroom.minsu.report.house.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * @author andyl
 *
 */
public class HouseViscosityVo extends BaseEntity{

	/**
	 * 
	 */
	@FieldMeta(skip = true)
	private static final long serialVersionUID = -6392941901402561778L;

	/** 房源表fid */
	private  String houseBaseFid;
	
	/** roofid */
	private  String roomFid;
	
	/** 国家 */
	private  String nationCode;
	
	/** 国家名称 */
	@FieldMeta(name = "国家",order=1)
	private  String nationName;
	
	/** 房源大区Code  */
    private  String regionCode ;
    
    /** 房源大区名称  */
    @FieldMeta(name = "大区",order=2)
    private  String regionName ;
    
    /** 房源省份Code  */
    private  String provinceCode ;
    
    /** 房源省份名称  */
    private  String privinceName ;
    
    /** 城市 */
    private  String cityCode ;
    
    /** 城市 */
    @FieldMeta(name = "城市",order=3)
    private  String cityName ;
       
    /** 房源编号 */
    @FieldMeta(name = "房源编号",order=4)
    private  String houseSn ;
    
    /** 房源状态 */
    private  Integer houseStatus ;
    
    /** 房源状态昵称 */
    @FieldMeta(name = "房源状态",order=5)
    private  String houseStatusName ;
    
    /** 首次上架时间 */
    @FieldMeta(name = "首次上架时间",order=6)
    private  String firstUpDate ;
    
    /** 累计浏览量 */
    @FieldMeta(name = "累计浏览量",order=7)
    private Integer cumulViewNum;
    
    /** 累计咨询量 */
    @FieldMeta(name = "累计咨询量",order=8)
    private Integer cumulAdviceNum;
    
    /** 累计申请量 */
    @FieldMeta(name = "累计申请量",order=9)
    private Integer cumulApplyNum;
    
    /** 累计接单量 */
    @FieldMeta(name = "累计接单量",order=10)
    private Integer cumulGetOrderNum;
    
    /** 累计定单量 */
    @FieldMeta(name = "累计定单量",order=11)
    private Integer cumulOrderNum;
    
    /** 累计预定间夜量 */
    @FieldMeta(name = " 累计预定间夜量 ",order=12)
    private Integer cumulReserveJYNum;
    
    /** 累计入住订单量 */
    @FieldMeta(name = "累计入住订单量",order=13)
    private Integer cumulCheckInOrderNum;
    
    /** 累计入住间夜量 */
    @FieldMeta(name = "累计入住间夜量",order=14)
    private Integer cumulCheckInJYNum;
    
    /** 累计屏蔽间夜量 */
    @FieldMeta(name = "累计屏蔽间夜量",order=15)
    private Integer cumulShieldJYNum;
    
    /** 出租率 */
    @FieldMeta(name = "出租率",order=16)
    private String rentRate;
    
    /** 累计收到评价量 */
    @FieldMeta(name = "累计收到评价量",order=17)
    private Integer cumulGetEvalNum;
    
    /** 累计房租收益 */
    @FieldMeta(name = "累计房租收益 ",order=18)
    private Integer cumulProfit;
    
    /** 订单开始时间 */
    private  String orderStartTime ;
    
    /** 订单结束时间时间 */
    private  String orderEndTime ;

    /** 订单实际结束时间时间 */
    private  String orderRelEndTime ;
    
	public HouseViscosityVo() {
		super();
	}

	

	public HouseViscosityVo(String houseBaseFid, String roomFid,
			String nationCode, String nationName, String regionCode,
			String regionName, String provinceCode, String privinceName,
			String cityCode, String cityName, String houseSn,
			Integer houseStatus, String houseStatusName, String firstUpDate,
			Integer cumulViewNum, Integer cumulAdviceNum,
			Integer cumulApplyNum, Integer cumulGetOrderNum,
			Integer cumulOrderNum, Integer cumulReserveJYNum,
			Integer cumulCheckInOrderNum, Integer cumulCheckInJYNum,
			Integer cumulShieldJYNum, String rentRate, Integer cumulGetEvalNum,
			Integer cumulProfit, String orderStartTime, String orderEndTime,
			String orderRelEndTime) {
		super();
		this.houseBaseFid = houseBaseFid;
		this.roomFid = roomFid;
		this.nationCode = nationCode;
		this.nationName = nationName;
		this.regionCode = regionCode;
		this.regionName = regionName;
		this.provinceCode = provinceCode;
		this.privinceName = privinceName;
		this.cityCode = cityCode;
		this.cityName = cityName;
		this.houseSn = houseSn;
		this.houseStatus = houseStatus;
		this.houseStatusName = houseStatusName;
		this.firstUpDate = firstUpDate;
		this.cumulViewNum = cumulViewNum;
		this.cumulAdviceNum = cumulAdviceNum;
		this.cumulApplyNum = cumulApplyNum;
		this.cumulGetOrderNum = cumulGetOrderNum;
		this.cumulOrderNum = cumulOrderNum;
		this.cumulReserveJYNum = cumulReserveJYNum;
		this.cumulCheckInOrderNum = cumulCheckInOrderNum;
		this.cumulCheckInJYNum = cumulCheckInJYNum;
		this.cumulShieldJYNum = cumulShieldJYNum;
		this.rentRate = rentRate;
		this.cumulGetEvalNum = cumulGetEvalNum;
		this.cumulProfit = cumulProfit;
		this.orderStartTime = orderStartTime;
		this.orderEndTime = orderEndTime;
		this.orderRelEndTime = orderRelEndTime;
	}



	public String getHouseBaseFid() {
		return houseBaseFid;
	}

	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid;
	}

	public String getRoomFid() {
		return roomFid;
	}

	public void setRoomFid(String roomFid) {
		this.roomFid = roomFid;
	}

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getPrivinceName() {
		return privinceName;
	}

	public void setPrivinceName(String privinceName) {
		this.privinceName = privinceName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getHouseSn() {
		return houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public Integer getHouseStatus() {
		return houseStatus;
	}

	public void setHouseStatus(Integer houseStatus) {
		this.houseStatus = houseStatus;
	}

	public String getHouseStatusName() {
		return houseStatusName;
	}

	public void setHouseStatusName(String houseStatusName) {
		this.houseStatusName = houseStatusName;
	}

	public String getFirstUpDate() {
		return firstUpDate;
	}

	public void setFirstUpDate(String firstUpDate) {
		this.firstUpDate = firstUpDate;
	}

	public Integer getCumulViewNum() {
		return cumulViewNum;
	}

	public void setCumulViewNum(Integer cumulViewNum) {
		this.cumulViewNum = cumulViewNum;
	}

	public Integer getCumulAdviceNum() {
		return cumulAdviceNum;
	}

	public void setCumulAdviceNum(Integer cumulAdviceNum) {
		this.cumulAdviceNum = cumulAdviceNum;
	}

	public Integer getCumulApplyNum() {
		return cumulApplyNum;
	}

	public void setCumulApplyNum(Integer cumulApplyNum) {
		this.cumulApplyNum = cumulApplyNum;
	}

	public Integer getCumulGetOrderNum() {
		return cumulGetOrderNum;
	}

	public void setCumulGetOrderNum(Integer cumulGetOrderNum) {
		this.cumulGetOrderNum = cumulGetOrderNum;
	}

	public Integer getCumulOrderNum() {
		return cumulOrderNum;
	}

	public void setCumulOrderNum(Integer cumulOrderNum) {
		this.cumulOrderNum = cumulOrderNum;
	}

	public Integer getCumulReserveJYNum() {
		return cumulReserveJYNum;
	}

	public void setCumulReserveJYNum(Integer cumulReserveJYNum) {
		this.cumulReserveJYNum = cumulReserveJYNum;
	}

	public Integer getCumulCheckInOrderNum() {
		return cumulCheckInOrderNum;
	}

	public void setCumulCheckInOrderNum(Integer cumulCheckInOrderNum) {
		this.cumulCheckInOrderNum = cumulCheckInOrderNum;
	}

	public Integer getCumulCheckInJYNum() {
		return cumulCheckInJYNum;
	}

	public void setCumulCheckInJYNum(Integer cumulCheckInJYNum) {
		this.cumulCheckInJYNum = cumulCheckInJYNum;
	}

	public Integer getCumulShieldJYNum() {
		return cumulShieldJYNum;
	}

	public void setCumulShieldJYNum(Integer cumulShieldJYNum) {
		this.cumulShieldJYNum = cumulShieldJYNum;
	}

	public String getRentRate() {
		return rentRate;
	}

	public void setRentRate(String rentRate) {
		this.rentRate = rentRate;
	}

	public Integer getCumulGetEvalNum() {
		return cumulGetEvalNum;
	}

	public void setCumulGetEvalNum(Integer cumulGetEvalNum) {
		this.cumulGetEvalNum = cumulGetEvalNum;
	}

	public Integer getCumulProfit() {
		return cumulProfit;
	}

	public void setCumulProfit(Integer cumulProfit) {
		this.cumulProfit = cumulProfit;
	}

	public String getOrderStartTime() {
		return orderStartTime;
	}

	public void setOrderStartTime(String orderStartTime) {
		this.orderStartTime = orderStartTime;
	}

	public String getOrderRelEndTime() {
		return orderRelEndTime;
	}

	public void setOrderRelEndTime(String orderRelEndTime) {
		this.orderRelEndTime = orderRelEndTime;
	}

	public String getOrderEndTime() {
		return orderEndTime;
	}

	public void setOrderEndTime(String orderEndTime) {
		this.orderEndTime = orderEndTime;
	}
	
}
