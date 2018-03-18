package com.ziroom.minsu.report.house.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**  
 * @Title: HouseEfficReportVo.java  
 * @Package com.ziroom.minsu.report.house.vo  
 * @Description: TODO
 * @author loushuai  
 * @date 2017年4月25日  
 * @version V1.0  
 */
public class HouseEfficVo extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2611617247638613616L;

	/** 房源表fid */
	private  String houseBaseFid;
	
	/** 房间roomfid */
	private  String roomFid;
	
	/** 国家 */
	private  String nationCode;
	
	/** 国家名称 */
	@FieldMeta(name = "国家", order = 1)
	private  String nationName;
	
	/** 房源大区Code  */
    private  String regionCode ;
    
    /** 房源大区名称  */
    @FieldMeta(name = "大区", order = 2)
    private  String regionName ;
    
    /** 房源省份Code  */
    private  String provinceCode ;
    
    /** 房源省份名称  */
    private  String privinceName ;
    
    /** 城市 */
    private  String cityCode ;
    
    /** 城市 */
    @FieldMeta(name = "城市", order = 3)
    private  String cityName ;
       
    /** 房源编号 */
    @FieldMeta(name = "房源或房间编号", order = 4)
    private  String houseSn ;
    
    /** 房源状态 */
    private  Integer houseStatus ;
    
    /** 房源状态昵称 */
    @FieldMeta(name = "房源或房间状态", order = 5)
    private  String houseStatusName ;
    
    /** 首次上架时间 */
    @FieldMeta(name = "首次上架时间", order = 6)
    private  String firstUpDate ;
    
    /** 首次咨询时间   t_house_stats_day_msg表的createTime*/
    @FieldMeta(name = "首次咨询时间", order = 7)
    private  String firstAdviceDate ;
    
    /** 首次申请时间 t_order_house_snapshot 表关联t_order表，createTime最早时间*/
    @FieldMeta(name = "首次申请时间", order = 8)
    private  String firstApplyDate ;
    
    /** 首次支付时间 t_order_house_snapshot 表关联t_order表，payStatus=1中pay_time时间最早的 */
    @FieldMeta(name = "首次支付时间", order = 9)
    private  String firstPayDate ;
    
    /** 首次入住时间  t_order_house_snapshot 表关联t_order表，start_time最早的 */
    @FieldMeta(name = "首次入住时间", order = 10)
    private  String firstCheckinDate ;
    
    /** 首次评价时间 t_evaluate_order表中housefid的所有评价中，时间最早的 */
    @FieldMeta(name = "首次评价时间", order = 11)
    private  String firstEvalDate ;
    
	public HouseEfficVo() {
		super();
	}
	


	public HouseEfficVo(String houseBaseFid, String roomFid, String nationCode,
			String nationName, String regionCode, String regionName,
			String provinceCode, String privinceName, String cityCode,
			String cityName, String houseSn, Integer houseStatus,
			String houseStatusName, String firstUpDate, String firstAdviceDate,
			String firstApplyDate, String firstPayDate,
			String firstCheckinDate, String firstEvalDate) {
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
		this.firstAdviceDate = firstAdviceDate;
		this.firstApplyDate = firstApplyDate;
		this.firstPayDate = firstPayDate;
		this.firstCheckinDate = firstCheckinDate;
		this.firstEvalDate = firstEvalDate;
	}



	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
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

	public String getFirstAdviceDate() {
		return firstAdviceDate;
	}

	public void setFirstAdviceDate(String firstAdviceDate) {
		this.firstAdviceDate = firstAdviceDate;
	}

	public String getFirstApplyDate() {
		return firstApplyDate;
	}

	public void setFirstApplyDate(String firstApplyDate) {
		this.firstApplyDate = firstApplyDate;
	}

	public String getFirstPayDate() {
		return firstPayDate;
	}

	public void setFirstPayDate(String firstPayDate) {
		this.firstPayDate = firstPayDate;
	}

	public String getFirstCheckinDate() {
		return firstCheckinDate;
	}

	public void setFirstCheckinDate(String firstCheckinDate) {
		this.firstCheckinDate = firstCheckinDate;
	}

	public String getFirstEvalDate() {
		return firstEvalDate;
	}

	public void setFirstEvalDate(String firstEvalDate) {
		this.firstEvalDate = firstEvalDate;
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
	
}
