/**
 * @FileName: HouseDetailNewVo.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author yd
 * @created 2016年12月7日 下午4:58:04
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import com.ziroom.minsu.entity.base.MinsuEleEntity;
import com.ziroom.minsu.entity.search.LabelTipsEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>房源详情 V1版本的vo</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class HouseDetailNewVo implements Serializable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 7378310960133592504L;

	/**
	 * 房源或房间fid
	 */
	private String fid;
	
	/**
	 * 房源或房间名称
	 */
	private String houseName;

	/**
	 * @see com.ziroom.minsu.valenum.house.HouseStatusEnum
	 */
	private Integer houseStatus;
	/**
	 * 原价
	 */
	private Integer originalPrice;
	/**
	 * 房源或房间价格
	 */
	private Integer housePrice;
	
	/**
	 * 出租方式 0：整租，1：合租，2：床位
	 */
	private Integer rentWay;
	
	/**
	 * 出租方式名称
	 */
	private String rentWayName;
	
	/**
	 * 评论星级
	 */
	private Float gradeStar=0f;



    /**
     * 带描述的图片信息
     */
    private List<MinsuEleEntity> picDisList = new ArrayList<>();

	/**
	 * 房东fid
	 */
	private String landlordUid;
	
	/**
	 * 默认图片
	 */
	private String defaultPic;
	
	/**
	 * 评论总数
	 */
	private Integer evaluateCount=0;
	 
	/**
	 * 房东头像
	 */
	private String landlordIcon;
	
	/**
	 * 房东姓名
	 */
	private String landlordName;
	
	/**
	 * 床单更换规则
	 */
	private Integer sheetsReplaceRulesValue;
	
	/**
	 * 床单更换规则值
	 */
	private String sheetsReplaceRulesName;
	
	
	/**
	 * 一条评论信息
	 */
	private TenantEvalVo tenantEvalVo;

	
	/**
	 * 小区名称
	 */
	private String communityName;
	
	/**
	 * 房东电话
	 */
	private String landlordMobile;
	
	/**
	 * 房源详细地址
	 */
	private String houseAddr;
	
	/**
	 * 房东是否认证
	 */
	private Integer isAuth=1;
	
	/**
	 * 分享房源url
	 */
	private String houseShareUrl;
	
	/**
	 * 是否有智能锁(0:否,1:是)
	 */
	private Integer isLock;
	
	/**
	 * 截止时间
	 */
    private Date tillDate;
    
    /**
     * 预定订单页面 押金提示展示
     */
    private String msgInfo;

    /**
     * 房源名称下-房源信息展示
     */
    private LinkedList<String> houseNameInfoList;
    
    /**
     * 房源美居介绍信息
     */
    private MercureInfoVo mercureInfoVo;
    
    /**
     * 房源 设施 默认展示5个
     */
    private LinkedList<EnumVo> listHouseFacilities;
    
    /**
     * 房源的服务  设施  电器 卫浴 列表
     */
    private LinkedList<EnumVo> listHouseFacServ;
    
    /**
     * 入住规则
     */
    private LinkedList<CheckRuleVo> listCheckRuleVo;
    
    /**
	 * 经度
	 */
	private Double longitude;

	/**
	 * 纬度
	 */
	private Double latitude;
	
	/**
	 * 下单类型
	 */
	private Integer orderType;
	
	/**
	 * 下单类型名称
	 */
	private String orderTypeName; 
	
	/**
	 * 入住人数限制 0：不限制
	 */
	private Integer checkInLimit;
	
	/**
	 * 长租类型tyoe:43
	 */
	private String longTermType;
	
	/**
	 * 出租名称： 长租
	 */
	private String longTermName;
	
	/**
	 * 退订政策名称： 退订政策
	 */
	private String checkOutRulesTitle;
	
	/**
	 * 当前退订政策type
	 */
	private String curRulesType;
	
	/**
	 * 长租天数
	 */
	private String longTermDays;
	

	/**
	 * 最少入住天数
	 */
	private Integer minDay;
    /**
     * 周租折扣，月租折扣 今日特惠，闪惠
     */
    private List<LabelTipsEntity> labelTipsList = new ArrayList<>();

	/**
	 * 是否设置今夜特价 0:否,1:是
	 */
	private Integer isToNightDiscount;

	/**
	 * 今夜特价信息
	 */
	private ToNightDiscount toNightDiscount;

	public Integer getIsToNightDiscount() {
		return isToNightDiscount;
	}

	public void setIsToNightDiscount(Integer isToNightDiscount) {
		this.isToNightDiscount = isToNightDiscount;
	}

	public ToNightDiscount getToNightDiscount() {
		return toNightDiscount;
	}

	public void setToNightDiscount(ToNightDiscount toNightDiscount) {
		this.toNightDiscount = toNightDiscount;
	}

	/**
	 * @return the minDay
	 */
	public Integer getMinDay() {
		return minDay;
	}

	/**
	 * @param minDay the minDay to set
	 */
	public void setMinDay(Integer minDay) {
		this.minDay = minDay;
	}

	/**
	 * @return the longTermDays
	 */
	public String getLongTermDays() {
		return longTermDays;
	}

	/**
	 * @param longTermDays the longTermDays to set
	 */
	public void setLongTermDays(String longTermDays) {
		this.longTermDays = longTermDays;
	}

	/**
	 * @return the curRulesType
	 */
	public String getCurRulesType() {
		return curRulesType;
	}

	public Integer getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Integer originalPrice) {
		this.originalPrice = originalPrice;
	}

	/**
	 * @param curRulesType the curRulesType to set
	 */
	public void setCurRulesType(String curRulesType) {
		this.curRulesType = curRulesType;
	}

	/**
	 * @return the longTermType
	 */
	public String getLongTermType() {
		return longTermType;
	}

	/**
	 * @param longTermType the longTermType to set
	 */
	public void setLongTermType(String longTermType) {
		this.longTermType = longTermType;
	}

	/**
	 * @return the longTermName
	 */
	public String getLongTermName() {
		return longTermName;
	}

	/**
	 * @param longTermName the longTermName to set
	 */
	public void setLongTermName(String longTermName) {
		this.longTermName = longTermName;
	}

	/**
	 * @return the checkOutRulesTitle
	 */
	public String getCheckOutRulesTitle() {
		return checkOutRulesTitle;
	}

	/**
	 * @param checkOutRulesTitle the checkOutRulesTitle to set
	 */
	public void setCheckOutRulesTitle(String checkOutRulesTitle) {
		this.checkOutRulesTitle = checkOutRulesTitle;
	}

	/**
	 * @return the checkInLimit
	 */
	public Integer getCheckInLimit() {
		return checkInLimit;
	}

	/**
	 * @param checkInLimit the checkInLimit to set
	 */
	public void setCheckInLimit(Integer checkInLimit) {
		this.checkInLimit = checkInLimit;
	}

	/**
	 * @return the orderType
	 */
	public Integer getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	/**
	 * @return the orderTypeName
	 */
	public String getOrderTypeName() {
		return orderTypeName;
	}

	/**
	 * @param orderTypeName the orderTypeName to set
	 */
	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the fid
	 */
	public String getFid() {
		return fid;
	}

	/**
	 * @param fid the fid to set
	 */
	public void setFid(String fid) {
		this.fid = fid;
	}

	/**
	 * @return the houseName
	 */
	public String getHouseName() {
		return houseName;
	}

	/**
	 * @param houseName the houseName to set
	 */
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	/**
	 * @return the houseStatus
	 */
	public Integer getHouseStatus() {
		return houseStatus;
	}

	/**
	 * @param houseStatus the houseStatus to set
	 */
	public void setHouseStatus(Integer houseStatus) {
		this.houseStatus = houseStatus;
	}

	/**
	 * @return the housePrice
	 */
	public Integer getHousePrice() {
		return housePrice;
	}

	/**
	 * @param housePrice the housePrice to set
	 */
	public void setHousePrice(Integer housePrice) {
		this.housePrice = housePrice;
	}

	/**
	 * @return the rentWay
	 */
	public Integer getRentWay() {
		return rentWay;
	}

	/**
	 * @param rentWay the rentWay to set
	 */
	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	/**
	 * @return the rentWayName
	 */
	public String getRentWayName() {
		return rentWayName;
	}

	/**
	 * @param rentWayName the rentWayName to set
	 */
	public void setRentWayName(String rentWayName) {
		this.rentWayName = rentWayName;
	}

	/**
	 * @return the gradeStar
	 */
	public Float getGradeStar() {
		return gradeStar;
	}

	/**
	 * @param gradeStar the gradeStar to set
	 */
	public void setGradeStar(Float gradeStar) {
		this.gradeStar = gradeStar;
	}

	/**
	 * @return the picDisList
	 */
	public List<MinsuEleEntity> getPicDisList() {
		return picDisList;
	}

	/**
	 * @param picDisList the picDisList to set
	 */
	public void setPicDisList(List<MinsuEleEntity> picDisList) {
		this.picDisList = picDisList;
	}

	/**
	 * @return the landlordUid
	 */
	public String getLandlordUid() {
		return landlordUid;
	}

	/**
	 * @param landlordUid the landlordUid to set
	 */
	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	/**
	 * @return the defaultPic
	 */
	public String getDefaultPic() {
		return defaultPic;
	}

	/**
	 * @param defaultPic the defaultPic to set
	 */
	public void setDefaultPic(String defaultPic) {
		this.defaultPic = defaultPic;
	}

	/**
	 * @return the evaluateCount
	 */
	public Integer getEvaluateCount() {
		return evaluateCount;
	}

	/**
	 * @param evaluateCount the evaluateCount to set
	 */
	public void setEvaluateCount(Integer evaluateCount) {
		this.evaluateCount = evaluateCount;
	}

	/**
	 * @return the landlordIcon
	 */
	public String getLandlordIcon() {
		return landlordIcon;
	}

	/**
	 * @param landlordIcon the landlordIcon to set
	 */
	public void setLandlordIcon(String landlordIcon) {
		this.landlordIcon = landlordIcon;
	}

	/**
	 * @return the landlordName
	 */
	public String getLandlordName() {
		return landlordName;
	}

	/**
	 * @param landlordName the landlordName to set
	 */
	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}

	/**
	 * @return the sheetsReplaceRulesValue
	 */
	public Integer getSheetsReplaceRulesValue() {
		return sheetsReplaceRulesValue;
	}

	/**
	 * @param sheetsReplaceRulesValue the sheetsReplaceRulesValue to set
	 */
	public void setSheetsReplaceRulesValue(Integer sheetsReplaceRulesValue) {
		this.sheetsReplaceRulesValue = sheetsReplaceRulesValue;
	}

	/**
	 * @return the sheetsReplaceRulesName
	 */
	public String getSheetsReplaceRulesName() {
		return sheetsReplaceRulesName;
	}

	/**
	 * @param sheetsReplaceRulesName the sheetsReplaceRulesName to set
	 */
	public void setSheetsReplaceRulesName(String sheetsReplaceRulesName) {
		this.sheetsReplaceRulesName = sheetsReplaceRulesName;
	}

	/**
	 * @return the tenantEvalVo
	 */
	public TenantEvalVo getTenantEvalVo() {
		return tenantEvalVo;
	}

	/**
	 * @param tenantEvalVo the tenantEvalVo to set
	 */
	public void setTenantEvalVo(TenantEvalVo tenantEvalVo) {
		this.tenantEvalVo = tenantEvalVo;
	}

	/**
	 * @return the communityName
	 */
	public String getCommunityName() {
		return communityName;
	}

	/**
	 * @param communityName the communityName to set
	 */
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	/**
	 * @return the landlordMobile
	 */
	public String getLandlordMobile() {
		return landlordMobile;
	}

	/**
	 * @param landlordMobile the landlordMobile to set
	 */
	public void setLandlordMobile(String landlordMobile) {
		this.landlordMobile = landlordMobile;
	}

	/**
	 * @return the houseAddr
	 */
	public String getHouseAddr() {
		return houseAddr;
	}

	/**
	 * @param houseAddr the houseAddr to set
	 */
	public void setHouseAddr(String houseAddr) {
		this.houseAddr = houseAddr;
	}

	/**
	 * @return the isAuth
	 */
	public Integer getIsAuth() {
		return isAuth;
	}

	/**
	 * @param isAuth the isAuth to set
	 */
	public void setIsAuth(Integer isAuth) {
		this.isAuth = isAuth;
	}

	/**
	 * @return the houseShareUrl
	 */
	public String getHouseShareUrl() {
		return houseShareUrl;
	}

	/**
	 * @param houseShareUrl the houseShareUrl to set
	 */
	public void setHouseShareUrl(String houseShareUrl) {
		this.houseShareUrl = houseShareUrl;
	}

	/**
	 * @return the isLock
	 */
	public Integer getIsLock() {
		return isLock;
	}

	/**
	 * @param isLock the isLock to set
	 */
	public void setIsLock(Integer isLock) {
		this.isLock = isLock;
	}

	/**
	 * @return the tillDate
	 */
	public Date getTillDate() {
		return tillDate;
	}

	/**
	 * @param tillDate the tillDate to set
	 */
	public void setTillDate(Date tillDate) {
		this.tillDate = tillDate;
	}

	/**
	 * @return the msgInfo
	 */
	public String getMsgInfo() {
		return msgInfo;
	}

	/**
	 * @param msgInfo the msgInfo to set
	 */
	public void setMsgInfo(String msgInfo) {
		this.msgInfo = msgInfo;
	}

	/**
	 * @return the houseNameInfoList
	 */
	public LinkedList<String> getHouseNameInfoList() {
		return houseNameInfoList;
	}

	/**
	 * @param houseNameInfoList the houseNameInfoList to set
	 */
	public void setHouseNameInfoList(LinkedList<String> houseNameInfoList) {
		this.houseNameInfoList = houseNameInfoList;
	}

	/**
	 * @return the mercureInfoVo
	 */
	public MercureInfoVo getMercureInfoVo() {
		return mercureInfoVo;
	}

	/**
	 * @param mercureInfoVo the mercureInfoVo to set
	 */
	public void setMercureInfoVo(MercureInfoVo mercureInfoVo) {
		this.mercureInfoVo = mercureInfoVo;
	}

	/**
	 * @return the listHouseFacilities
	 */
	public LinkedList<EnumVo> getListHouseFacilities() {
		return listHouseFacilities;
	}

	/**
	 * @param listHouseFacilities the listHouseFacilities to set
	 */
	public void setListHouseFacilities(LinkedList<EnumVo> listHouseFacilities) {
		this.listHouseFacilities = listHouseFacilities;
	}

	/**
	 * @return the listHouseFacServ
	 */
	public LinkedList<EnumVo> getListHouseFacServ() {
		return listHouseFacServ;
	}

	/**
	 * @param listHouseFacServ the listHouseFacServ to set
	 */
	public void setListHouseFacServ(LinkedList<EnumVo> listHouseFacServ) {
		this.listHouseFacServ = listHouseFacServ;
	}

	/**
	 * @return the listCheckRuleVo
	 */
	public LinkedList<CheckRuleVo> getListCheckRuleVo() {
		return listCheckRuleVo;
	}

	/**
	 * @param listCheckRuleVo the listCheckRuleVo to set
	 */
	public void setListCheckRuleVo(LinkedList<CheckRuleVo> listCheckRuleVo) {
		this.listCheckRuleVo = listCheckRuleVo;
	}

    public List<LabelTipsEntity> getLabelTipsList() {
        return labelTipsList;
    }

    public void setLabelTipsList(List<LabelTipsEntity> labelTipsList) {
        this.labelTipsList = labelTipsList;
    }

}
