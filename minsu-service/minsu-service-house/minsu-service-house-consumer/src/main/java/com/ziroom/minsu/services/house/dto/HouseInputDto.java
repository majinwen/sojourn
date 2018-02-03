/**
 * @FileName: HouseInputDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author bushujie
 * @created 2016年5月4日 下午3:27:45
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import java.util.ArrayList;
import java.util.List;

import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseConfMsgEntity;
import com.ziroom.minsu.entity.house.HouseDescEntity;
import com.ziroom.minsu.entity.house.HouseGuardRelEntity;
import com.ziroom.minsu.entity.house.HousePhyMsgEntity;

/**
 * <p>房源录入参数</p>
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
public class HouseInputDto {

	/**
	 * 房源基础信息
	 */
	private HouseBaseMsgEntity houseBase=new HouseBaseMsgEntity();
	
	/**
	 * 房源扩展信息
	 */
	private HouseBaseExtEntity houseExt=new HouseBaseExtEntity();
	
	/**
	 * 房源描述信息
	 */
	private HouseDescEntity houseDesc= new HouseDescEntity();
	
	/**
	 * 房源物理房源
	 */
	private HousePhyMsgEntity housePhy=new HousePhyMsgEntity();
	
	/**
	 * 配套设施
	 */
	private List<String> facilityList=new ArrayList<String>();
	
	/**
	 * 优惠规则
	 */
	private List<String> discountList=new ArrayList<String>();
	
	/**
	 * 押金规则
	 */
	private List<String> depositList=new ArrayList<String>();
	
	/**
	 * 房间名称
	 */
	private List<String> roomName=new ArrayList<String>();
	
	/**
	 * 房间名称
	 */
	private String roomNameNick;
	
	/**
	 * 房间面积
	 */
	private List<String> roomArea=new ArrayList<String>();
	
	/**
	 * 房间价格
	 */
	private List<String> roomPrice=new ArrayList<String>();
	
	/**
	 * 页面房间标示
	 */
	private List<String> roomFid=new ArrayList<String>();

	/**
	 * 房间人数限制
	 */
	private List<Integer> roomLimit=new ArrayList<Integer>();
	
	/**
	 * 房间是否独立厕所
	 */
	private List<Integer> isToilet=new ArrayList<Integer>();
	
	/**
	 * 床fid集合
	 */
	private List<String> bedFidList=new ArrayList<String>();

	/**
	 * 床类型
	 */
	private List<String> bedType=new ArrayList<String>();
	
	/**
	 * 床规格
	 */
	private List<String> bedSize=new ArrayList<String>();
	
	/**
	 * 截止日期
	 */
	private String tillDate;
	
	/**
	 * 退订政策
	 */
	private List<String> unregPolicy=new ArrayList<String>();
	
	/**
	 * 服务
	 */
	private List<String> serviceList=new ArrayList<String>();
	
	//房源维护管家
	private HouseGuardRelEntity houseGuardRel;
	
	/**
	 * 价格设置-整租
	 */
	private SpecialPriceDto sp = new SpecialPriceDto();
	
	/**
	 * 价格设置-分租
	 */
	private List<SpecialPriceDto> spList = new ArrayList<>();
	
	/**
	 * 灵活定价配置
	 */
	private List<HouseConfMsgEntity> gapPriceList = new ArrayList<>();
	
	/**
	 * 地图类型
	 */
	private String mapType;
	
	/**
	 * 操作人 员工号
	 */
	private String empCode;
	
	/**
	 * 百度坐标
	 */
	private String coordinate;
	
	
	/**
	 * @return the empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * @return the coordinate
	 */
	public String getCoordinate() {
		return coordinate;
	}

	/**
	 * @param coordinate the coordinate to set
	 */
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	/**
	 * @param empCode the empCode to set
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * @return the mapType
	 */
	public String getMapType() {
		return mapType;
	}

	/**
	 * @param mapType the mapType to set
	 */
	public void setMapType(String mapType) {
		this.mapType = mapType;
	}

	public HouseGuardRelEntity getHouseGuardRel() {
		return houseGuardRel;
	}

	public void setHouseGuardRel(HouseGuardRelEntity houseGuardRel) {
		this.houseGuardRel = houseGuardRel;
	}

	/**
	 * @return the unregPolicy
	 */
	public List<String> getUnregPolicy() {
		return unregPolicy;
	}

	/**
	 * @param unregPolicy the unregPolicy to set
	 */
	public void setUnregPolicy(List<String> unregPolicy) {
		this.unregPolicy = unregPolicy;
	}

	public List<String> getRoomName() {
		return roomName;
	}

	public void setRoomName(List<String> roomName) {
		this.roomName = roomName;
	}

	public String getRoomNameNick() {
		return roomNameNick;
	}

	public void setRoomNameNick(String roomNameNick) {
		this.roomNameNick = roomNameNick;
	}

	/**
	 * @return the roomArea
	 */
	public List<String> getRoomArea() {
		return roomArea;
	}

	/**
	 * @param roomArea the roomArea to set
	 */
	public void setRoomArea(List<String> roomArea) {
		this.roomArea = roomArea;
	}

	/**
	 * @return the roomPrice
	 */
	public List<String> getRoomPrice() {
		return roomPrice;
	}

	/**
	 * @param roomPrice the roomPrice to set
	 */
	public void setRoomPrice(List<String> roomPrice) {
		this.roomPrice = roomPrice;
	}

	/**
	 * @return the roomLimit
	 */
	public List<Integer> getRoomLimit() {
		return roomLimit;
	}

	/**
	 * @param roomLimit the roomLimit to set
	 */
	public void setRoomLimit(List<Integer> roomLimit) {
		this.roomLimit = roomLimit;
	}

	/**
	 * @return the isToilet
	 */
	public List<Integer> getIsToilet() {
		return isToilet;
	}

	/**
	 * @param isToilet the isToilet to set
	 */
	public void setIsToilet(List<Integer> isToilet) {
		this.isToilet = isToilet;
	}
	
	/**
	 * @return the bedType
	 */
	public List<String> getBedType() {
		return bedType;
	}

	/**
	 * @param bedType the bedType to set
	 */
	public void setBedType(List<String> bedType) {
		this.bedType = bedType;
	}

	/**
	 * @return the bedSize
	 */
	public List<String> getBedSize() {
		return bedSize;
	}

	/**
	 * @param bedSize the bedSize to set
	 */
	public void setBedSize(List<String> bedSize) {
		this.bedSize = bedSize;
	}

	/**
	 * @return the depositList
	 */
	public List<String> getDepositList() {
		return depositList;
	}

	/**
	 * @param depositList the depositList to set
	 */
	public void setDepositList(List<String> depositList) {
		this.depositList = depositList;
	}

	/**
	 * @return the discountList
	 */
	public List<String> getDiscountList() {
		return discountList;
	}

	/**
	 * @param discountList the discountList to set
	 */
	public void setDiscountList(List<String> discountList) {
		this.discountList = discountList;
	}

	/**
	 * @return the facilityList
	 */
	public List<String> getFacilityList() {
		return facilityList;
	}

	/**
	 * @param facilityList the facilityList to set
	 */
	public void setFacilityList(List<String> facilityList) {
		this.facilityList = facilityList;
	}

	/**
	 * @return the houseExt
	 */
	public HouseBaseExtEntity getHouseExt() {
		return houseExt;
	}

	/**
	 * @param houseExt the houseExt to set
	 */
	public void setHouseExt(HouseBaseExtEntity houseExt) {
		this.houseExt = houseExt;
	}

	/**
	 * @return the houseDesc
	 */
	public HouseDescEntity getHouseDesc() {
		return houseDesc;
	}

	/**
	 * @param houseDesc the houseDesc to set
	 */
	public void setHouseDesc(HouseDescEntity houseDesc) {
		this.houseDesc = houseDesc;
	}

	/**
	 * @return the housePhy
	 */
	public HousePhyMsgEntity getHousePhy() {
		return housePhy;
	}

	/**
	 * @param housePhy the housePhy to set
	 */
	public void setHousePhy(HousePhyMsgEntity housePhy) {
		this.housePhy = housePhy;
	}

	/**
	 * @return the houseBase
	 */
	public HouseBaseMsgEntity getHouseBase() {
		return houseBase;
	}

	/**
	 * @param houseBase the houseBase to set
	 */
	public void setHouseBase(HouseBaseMsgEntity houseBase) {
		this.houseBase = houseBase;
	}

	/**
	 * @return the tillDate
	 */
	public String getTillDate() {
		return tillDate;
	}

	/**
	 * @param tillDate the tillDate to set
	 */
	public void setTillDate(String tillDate) {
		this.tillDate = tillDate;
	}

	/**
	 * @return the serviceList
	 */
	public List<String> getServiceList() {
		return serviceList;
	}

	/**
	 * @param serviceList the serviceList to set
	 */
	public void setServiceList(List<String> serviceList) {
		this.serviceList = serviceList;
	}
	
	/**
	 * @return the roomFid
	 */
	public List<String> getRoomFid() {
		return roomFid;
	}

	/**
	 * @param roomFid the roomFid to set
	 */
	public void setRoomFid(List<String> roomFid) {
		this.roomFid = roomFid;
	}

	/**
	 * @return the sp
	 */
	public SpecialPriceDto getSp() {
		return sp;
	}

	/**
	 * @param sp the sp to set
	 */
	public void setSp(SpecialPriceDto sp) {
		this.sp = sp;
	}

	/**
	 * @return the spList
	 */
	public List<SpecialPriceDto> getSpList() {
		return spList;
	}

	/**
	 * @param spList the spList to set
	 */
	public void setSpList(List<SpecialPriceDto> spList) {
		this.spList = spList;
	}

	/**
	 * @return the gapPriceList
	 */
	public List<HouseConfMsgEntity> getGapPriceList() {
		return gapPriceList;
	}

	/**
	 * @param gapPriceList the gapPriceList to set
	 */
	public void setGapPriceList(List<HouseConfMsgEntity> gapPriceList) {
		this.gapPriceList = gapPriceList;
	}
	
	/**
	 * @return the bedFidList
	 */
	public List<String> getBedFidList() {
		return bedFidList;
	}

	/**
	 * @param bedFidList the bedFidList to set
	 */
	public void setBedFidList(List<String> bedFidList) {
		this.bedFidList = bedFidList;
	}
}
