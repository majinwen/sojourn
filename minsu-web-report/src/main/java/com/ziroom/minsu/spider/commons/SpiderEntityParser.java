/**
 * @FileName: SpiderEntityParser.java
 * @Package com.ziroom.minsu.spider.commons
 * 
 * @author zl
 * @created 2016年10月25日 下午10:09:50
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.commons;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseDescEntity;
import com.ziroom.minsu.entity.house.HousePhyMsgEntity;
import com.ziroom.minsu.spider.airbnb.entity.AirbnbHouseInfoEntityWithBLOBs;
import com.ziroom.minsu.spider.airbnb.entity.AirbnbHousePriceEntity;
import com.ziroom.minsu.spider.commons.dto.CalendarResponseVo;
import com.ziroom.minsu.spider.commons.dto.HouseMsgVo;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

/**
 * <p>TODO</p>
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
public class SpiderEntityParser {
	
	private static Properties property = PropertiesUtils.getProperties("classpath:application.properties"); 
	
	public static HouseMsgVo airbnbHouseToMinsuHouse(AirbnbHouseInfoEntityWithBLOBs airbnbHouse){
		if (airbnbHouse==null) {
			return null;
		}
		
		HouseMsgVo houseMsgVo = null;
		try {
			houseMsgVo = new HouseMsgVo();

			String houseAddr ="";
			if (airbnbHouse.getCountry()!=null) {
				houseAddr+=airbnbHouse.getCountry();
			}
			if (airbnbHouse.getHouseregion()!=null) {
				houseAddr+=airbnbHouse.getHouseregion();
			}
			if (airbnbHouse.getCity()!=null) {
				houseAddr+=airbnbHouse.getCity();
			}
			if (airbnbHouse.getStreet()!=null) {
				houseAddr+=airbnbHouse.getStreet();
			}
			
			houseMsgVo.setHouseAddr(houseAddr);			
			houseMsgVo.setCreateDate(new Date());
			houseMsgVo.setHouseCleaningFees(airbnbHouse.getCleaningFee());			
			houseMsgVo.setHouseName(airbnbHouse.getHouseName());
			houseMsgVo.setIsDel(YesOrNoEnum.NO.getCode());
			houseMsgVo.setLastModifyDate(new Date());
			houseMsgVo.setLeasePrice(airbnbHouse.getHousePrice());
			houseMsgVo.setRefreshDate(new Date());
			
//			houseMsgVo.setHouseArea(houseArea);
//			houseMsgVo.setHouseChannel();
//			houseMsgVo.setBalconyNum(balconyNum);
//			houseMsgVo.setCameramanMobile(cameramanMobile);
//			houseMsgVo.setCameramanName(cameramanName);
//			houseMsgVo.setFid(fid);
//			houseMsgVo.setHallNum(hallNum);
			
//			houseMsgVo.setHouseSn(houseSn);
//			houseMsgVo.setHouseSource(houseSource);
//			houseMsgVo.setHouseStatus(houseStatus);
//			houseMsgVo.setHouseType(houseType);
//			houseMsgVo.setHouseWeight(houseWeight);
//			houseMsgVo.setId(id);
//			houseMsgVo.setIntactRate(intactRate);
//			houseMsgVo.setIsLock(isLock);
//			houseMsgVo.setIsPic(isPic);
//			houseMsgVo.setKitchenNum(kitchenNum);
//			houseMsgVo.setLandlordUid(landlordUid);
			//FIXME 国际房源价格需要转化  get  http://apis.baidu.com/apistore/currencyservice/type 
//			houseMsgVo.setOldStatus(oldStatus);
//			houseMsgVo.setOperateSeq(operateSeq);			
//			houseMsgVo.setRentWay(rentWay);			
//			houseMsgVo.setRoomNum(roomNum);	
//			houseMsgVo.setTillDate(tillDate);
			
			if (airbnbHouse.getToiletCount()!=null) {
				houseMsgVo.setToiletNum(Math.round(airbnbHouse.getToiletCount()));
			}
			
			
			HousePhyMsgEntity housePhyMsg = new HousePhyMsgEntity();
			
			if (airbnbHouse.getLongitude()!=null &&airbnbHouse.getLatitude()!=null) {
				
				try {
					String phyurl="http://restapi.amap.com/v3/geocode/regeo?key=246e9cd4440a1a89a30f24a67c8e8a7b&radius=200&extensions=all&batch=false&roadlevel=1&location="+airbnbHouse.getLongitude()+","+airbnbHouse.getLatitude();
					String phyrsp = HttpTookit.doGet(phyurl, null, CharEncoding.UTF8,null);
					if (phyrsp!=null&& phyrsp.length()>0) {
						JSONObject phyObj=JSON.parseObject(phyrsp);
						if (phyObj!=null &&phyObj.containsKey("regeocode") ) {
							JSONObject regeocode=phyObj.getJSONObject("regeocode");
							if (regeocode!=null && regeocode.containsKey("addressComponent")) {
								JSONObject addressComponent=regeocode.getJSONObject("addressComponent");
								if (addressComponent!=null) {
									String country = addressComponent.getString("country");
									String province = addressComponent.getString("province");
									String city = addressComponent.getString("city");
									String citycode = addressComponent.getString("citycode");
									String district = addressComponent.getString("district");
									String adcode = addressComponent.getString("adcode");
									String township = addressComponent.getString("township");
									String towncode = addressComponent.getString("towncode");
									
									housePhyMsg.setAreaCode(adcode);
									housePhyMsg.setCityCode(citycode);
									housePhyMsg.setCommunityName(township);
									housePhyMsg.setCreateDate(new Date());
									housePhyMsg.setIsDel(YesOrNoEnum.NO.getCode());
									housePhyMsg.setLastModifyDate(new Date());
									housePhyMsg.setLatitude(airbnbHouse.getLatitude());
									housePhyMsg.setLongitude(airbnbHouse.getLongitude());
//									housePhyMsg.setBuildingCode(buildingCode);
//									housePhyMsg.setCreateUid(createUid);
//									housePhyMsg.setFid(fid);
//									housePhyMsg.setId(id);
//									housePhyMsg.setNationCode(nationCode);
//									housePhyMsg.setProvinceCode(provinceCode);
//									housePhyMsg.setZoJobNum(zoJobNum);
//									housePhyMsg.setZoMobile(zoMobile);
//									housePhyMsg.setZoName(zoName);
									
									
								}
							}
						}
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			

			HouseBaseExtEntity houseBaseExt =new HouseBaseExtEntity();
			houseBaseExt.setCheckInLimit(airbnbHouse.getPersonCapacity());
			houseBaseExt.setMinDay(airbnbHouse.getMinNights());			
//			houseBaseExt.setBuildingNum(buildingNum);
//			houseBaseExt.setCheckInTime(checkInTime);
//			houseBaseExt.setCheckOutRulesCode(checkOutRulesCode);
//			houseBaseExt.setCheckOutRulesCode(checkOutRulesCode);
//			houseBaseExt.setCheckOutTime(checkOutTime);
//			houseBaseExt.setDefaultPicFid(defaultPicFid);
//			houseBaseExt.setDepositRulesCode(depositRulesCode);
//			houseBaseExt.setDetailAddress(detailAddress);
//			houseBaseExt.setDiscountRulesCode(discountRulesCode);
//			houseBaseExt.setFacilityCode(facilityCode);
//			houseBaseExt.setFid(fid);
//			houseBaseExt.setFloorNum(floorNum);
//			houseBaseExt.setFullDiscount(fullDiscount);
//			houseBaseExt.setHomestayType(homestayType);
//			houseBaseExt.setHouseBaseFid(houseBaseFid);
//			houseBaseExt.setHouseNum(houseNum);
//			houseBaseExt.setHouseStreet(houseStreet);
//			houseBaseExt.setId(id);
//			houseBaseExt.setIsLandlordPic(isLandlordPic);
//			houseBaseExt.setIsTogetherLandlord(isTogetherLandlord);
//			houseBaseExt.setOldDefaultPicFid(oldDefaultPicFid);
//			houseBaseExt.setOrderType(orderType);
//			houseBaseExt.setServiceCode(serviceCode);
//			houseBaseExt.setSheetsReplaceRules(sheetsReplaceRules);
//			houseBaseExt.setUnitNum(unitNum);
			
			
			HouseDescEntity houseDesc = new HouseDescEntity();
			houseDesc.setAddtionalInfo(airbnbHouse.getCancellationPolicy());
			houseDesc.setCreateDate(new Date());
			houseDesc.setHouseAroundDesc(airbnbHouse.getDescription());
			houseDesc.setHouseDesc(airbnbHouse.getSummary());
			houseDesc.setHouseRules(airbnbHouse.getHouseRules());
			houseDesc.setIsDel(YesOrNoEnum.NO.getCode());
			houseDesc.setLastModifyDate(new Date());
//			houseDesc.setHouseBaseFid(houseBaseFid);
//			houseDesc.setId(id);
//			houseDesc.setCreateUid(createUid);
//			houseDesc.setFid(fid);
			
			

			houseMsgVo.setHousePhyMsg(housePhyMsg);
			houseMsgVo.setHouseBaseExt(houseBaseExt);
			houseMsgVo.setHouseDesc(houseDesc);
			
//			List<HouseConfMsgEntity> houseConfList = new ArrayList<>();			
//			List<HousePicMsgEntity> housePicList = new ArrayList<>();			
//			List<RoomMsgVo> roomList = new ArrayList<>();
//			HouseGuardRelEntity houseGuardRel = new HouseGuardRelEntity();
//			houseMsgVo.setRoomList(roomList);
//			houseMsgVo.setPhyFid(phyFid);
//			houseMsgVo.setHouseConfList(houseConfList);
//			houseMsgVo.setHouseGuardRel(houseGuardRel);
//			houseMsgVo.setHousePicList(housePicList);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return houseMsgVo;
	}
	
	public static List<CalendarResponseVo> airbnbPriceToMinsuPrice(List<AirbnbHousePriceEntity> priceEntities){
		if (priceEntities==null||priceEntities.size()==0) {
			return null;
		}
		
		List<CalendarResponseVo> list = new ArrayList<CalendarResponseVo>();
		for (AirbnbHousePriceEntity airbnbHousePriceEntity : priceEntities) {
			CalendarResponseVo minsuPice = new CalendarResponseVo();
			minsuPice.setDate(airbnbHousePriceEntity.getDate());
			//FIXME 国际货币需转化
			minsuPice.setPrice(airbnbHousePriceEntity.getLocalPrice());
			if (airbnbHousePriceEntity.getAvailable()!=null) {
				if (airbnbHousePriceEntity.getAvailable()==1) {
					minsuPice.setStatus(0);
				}else if (airbnbHousePriceEntity.getAvailable()==0) {
					minsuPice.setStatus(2);
				}
			}
			list.add(minsuPice);
			
		}
		
		return list;
	}
	
	
	public static void main(String[] args) {
		
		String urlString="http://apis.baidu.com/apistore/currencyservice/type";
		
		Header[] headers =new Header[1];
		Header apiHeader = new BasicHeader("apikey", "378251d134a39b8192acaad47865c858");
		headers[0]=apiHeader;
		String string = HttpTookit.doGet(urlString, null, CharEncoding.UTF8,headers);
		
		System.err.println(string);
		
		//String urlString2="http://restapi.amap.com/v3/geocode/regeo?key=246e9cd4440a1a89a30f24a67c8e8a7b&location=116.481488,39.990464&poitype=商务写字楼&radius=1000&extensions=all&batch=false&roadlevel=1";
//		String urlString2="http://restapi.amap.com/v3/geocode/regeo?key=246e9cd4440a1a89a30f24a67c8e8a7b&location=114.52208184420766,38.048958314615454&radius=1000&extensions=all&batch=false&roadlevel=1";
//
//		String string2 = HttpTookit.doGet(urlString2, null, CharEncoding.UTF8);
//		
//		System.err.println(string2);
		
	}
	

}
