/**
 * @FileName: HouseUpdateHistoryLogServiceProxyTest.java
 * @Package com.ziroom.minsu.services.house.test.proxy
 * 
 * @author yd
 * @created 2017年7月5日 下午2:56:16
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseDescEntity;
import com.ziroom.minsu.entity.house.HousePhyMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.services.house.dto.HouseUpdateHistoryLogDto;
import com.ziroom.minsu.services.house.dto.WaitUpdateHouseInfoDto;
import com.ziroom.minsu.services.house.proxy.HouseUpdateHistoryLogServiceProxy;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.house.CreaterTypeEnum;
import com.ziroom.minsu.valenum.house.HouseSourceEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>房源修记录保存测试</p>
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
public class HouseUpdateHistoryLogServiceProxyTest extends BaseTest{

	@Resource(name = "house.houseUpdateHistoryLogServiceProxy")
	private HouseUpdateHistoryLogServiceProxy houseUpdateHistoryLogServiceProxy;
	
	@Test
	public void saveHistoryLogFromHouseBaseMsg() {
		
		WaitUpdateHouseInfoDto waitUpdateHouseInfo = new WaitUpdateHouseInfoDto();
		waitUpdateHouseInfo.setHouseFid("8a9e9a9a548a061301548a67e6300029");
		waitUpdateHouseInfo.setRentWay(RentWayEnum.HOUSE.getCode());
		
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(houseUpdateHistoryLogServiceProxy.findWaitUpdateHouseInfo(JsonEntityTransform.Object2Json(waitUpdateHouseInfo)));
		
		if(dto.getCode() == DataTransferObject.SUCCESS){
			HouseUpdateHistoryLogDto	houseUpdateHistoryLogDto = dto.parseData("houseUpdateHistoryLogDto", new TypeReference<HouseUpdateHistoryLogDto>() {
			});
			
			if(!Check.NuNObj(houseUpdateHistoryLogDto)){
				houseUpdateHistoryLogDto.setCreaterFid("20223709");
				houseUpdateHistoryLogDto.setCreateType(CreaterTypeEnum.GUARD.getCode());
				houseUpdateHistoryLogDto.setSourceType(HouseSourceEnum.PC.getCode());
				HouseBaseMsgEntity houseBaseMsg = new HouseBaseMsgEntity();
				
				houseBaseMsg.setFid("8a9e9a9a548a061301548a67e6300029");
				houseBaseMsg.setHouseName("杨东的测试房源修改保存记录99999999");
				houseBaseMsg.setRentWay(RentWayEnum.HOUSE.getCode());

				
				
				HouseRoomMsgEntity houseRoomMsg = new HouseRoomMsgEntity(); 
				
				houseRoomMsg.setFid("8a9e9a9a548a061301548a67ebe30038");
				houseRoomMsg.setRoomArea(Double.valueOf(50));
				
				HouseBaseExtEntity houseBaseExt = new HouseBaseExtEntity();
				houseBaseExt.setCheckOutRulesCode("TradeRulesEnum005002");
				
				HouseDescEntity houseDesc = new HouseDescEntity();
				houseDesc.setHouseAroundDesc("fdsafdasfsdoifdsfdsjijfdsoifjdsojjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjdsafdasfsdoifdsfdsjijfdsoifjdsojjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjdsafdasfsdoifdsfdsjijfdsoifjdsojjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjdsafdasfsdoifdsfdsjijfdsoifjdsojjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjdsafdasfsdoifdsfdsjijfdsoifjdsojjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjdsafdasfsdoifdsfdsjijfdsoifjdsojjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjdsafdasfsdoifdsfdsjijfdsoifjdsojjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjdsafdasfsdoifdsfdsjijfdsoifjdsojjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjdsafdasfsdoifdsfdsjijfdsoifjdsojjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjdsafdasfsdoifdsfdsjijfdsoifjdsojjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
				
				
				HousePhyMsgEntity housePhyMsg = new HousePhyMsgEntity();
				housePhyMsg.setAreaCode("100012");
				
				houseUpdateHistoryLogDto.setHouseBaseMsg(houseBaseMsg);
				houseUpdateHistoryLogDto.setHouseRoomMsg(houseRoomMsg);
				houseUpdateHistoryLogDto.setHouseBaseExt(houseBaseExt);
				houseUpdateHistoryLogDto.setHouseDesc(houseDesc);
				houseUpdateHistoryLogDto.setHousePhyMsg(housePhyMsg);
				
				houseUpdateHistoryLogDto.setHouseFid(waitUpdateHouseInfo.getHouseFid());
				houseUpdateHistoryLogDto.setRentWay(waitUpdateHouseInfo.getRentWay());
				 /*List<HouseConfMsgEntity>  ListHouseConfMsg = new ArrayList<HouseConfMsgEntity>();
				 
				 HouseConfMsgEntity houseConfMsgEntity = new HouseConfMsgEntity();
				 houseConfMsgEntity.setHouseBaseFid("8a9e9a9a548a061301548a67e6300029");
				 houseConfMsgEntity.setDicCode("");*/
				houseUpdateHistoryLogServiceProxy.saveHistoryLog(JsonEntityTransform.Object2Json(houseUpdateHistoryLogDto));
			}
		}
		
		
	}

	@Test
	public void saveHistoryLogTest(){

		String paramJson = "{\"houseBaseMsg\":null,\"oldHouseBaseMsg\":null,\"houseRoomMsg\":null,\"oldHouseRoomMsg\":null,\"houseBaseExt\":{\"id\":null,\"fid\":null,\"houseBaseFid\":null,\"buildingNum\":null,\"unitNum\":null,\"floorNum\":null,\"houseNum\":null,\"houseStreet\":null,\"facilityCode\":null,\"serviceCode\":null,\"orderType\":null,\"homestayType\":null,\"minDay\":null,\"discountRulesCode\":null,\"depositRulesCode\":null,\"checkOutRulesCode\":null,\"checkInLimit\":null,\"checkInTime\":null,\"checkOutTime\":null,\"sheetsReplaceRules\":null,\"fullDiscount\":null,\"isTogetherLandlord\":null,\"defaultPicFid\":\"000000005d872c35015d87ecb4c00025\",\"oldDefaultPicFid\":null,\"detailAddress\":null,\"isLandlordPic\":null,\"houseQualityGrade\":null,\"isPhotography\":null,\"surveyResult\":null,\"rentRoomNum\":null},\"oldHouseBaseExt\":{\"id\":2159,\"fid\":\"8a9084df5d122f79015d125a1e07014a\",\"houseBaseFid\":\"8a9084df5d122f79015d125a1e050149\",\"buildingNum\":null,\"unitNum\":null,\"floorNum\":null,\"houseNum\":null,\"houseStreet\":\"jammy\",\"facilityCode\":\"ProductRulesEnum002\",\"serviceCode\":\"ProductRulesEnum0015\",\"orderType\":1,\"homestayType\":0,\"minDay\":1,\"discountRulesCode\":\"ProductRulesEnum0012\",\"depositRulesCode\":\"ProductRulesEnum008002\",\"checkOutRulesCode\":\"TradeRulesEnum005002\",\"checkInLimit\":0,\"checkInTime\":\"14:00:00\",\"checkOutTime\":\"12:00:00\",\"sheetsReplaceRules\":0,\"fullDiscount\":0.0,\"isTogetherLandlord\":0,\"defaultPicFid\":\"000000005d872c35015d87eceb280031\",\"oldDefaultPicFid\":null,\"detailAddress\":null,\"isLandlordPic\":0,\"houseQualityGrade\":\"A\",\"isPhotography\":0,\"surveyResult\":0,\"rentRoomNum\":0},\"houseDesc\":null,\"oldHouseDesc\":null,\"housePhyMsg\":null,\"oldHousePhyMsg\":null,\"houseRoomExt\":null,\"oldHouseRoomExt\":null,\"houseFid\":\"8a9084df5d122f79015d125a1e050149\",\"roomFid\":null,\"rentWay\":0,\"createrFid\":\"60003009\",\"createType\":2,\"sourceType\":0,\"operateSource\":2,\"listHouseConfMsg\":null}\n";
		houseUpdateHistoryLogServiceProxy.saveHistoryLog(paramJson);


	}
}
