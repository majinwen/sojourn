package com.ziroom.minsu.services.house.test.proxy;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.*;
import com.ziroom.minsu.services.house.dto.*;
import com.ziroom.minsu.services.house.entity.HouseBaseDetailVo;
import com.ziroom.minsu.services.house.entity.HouseBaseExtVo;
import com.ziroom.minsu.services.house.entity.HouseBaseVo;
import com.ziroom.minsu.services.house.proxy.HouseIssueServiceProxy;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>房东端-房源发布</p>
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
public class HouseIssueServiceProxyTest extends BaseTest {

	@Resource(name = "house.houseIssueServiceProxy")
	private HouseIssueServiceProxy houseIssueServiceProxy;

	@Test
	public void saveHouseConfListTest(){
		//8a9e9a8b53d6089f0153d608a1f80002
		List<HouseConfMsgEntity> confList = new ArrayList<HouseConfMsgEntity>();
		HouseConfMsgEntity gapConf1 = new HouseConfMsgEntity();
		gapConf1.setFid("8a9e989458f0b9af0158f0e9ef78000f");
		gapConf1.setHouseBaseFid("8a9084df54b39bd30154b3c6b76e0019");
//		gapConf1.setRoomFid("8a9e989458f0b9af0158f0e8fb12000e");
		gapConf1.setDicCode("ProductRulesEnum0020001");
		gapConf1.setDicVal("0.8");
		gapConf1.setIsDel(2);
		confList.add(gapConf1);
		HouseConfMsgEntity gapConf2 = new HouseConfMsgEntity();
		gapConf2.setFid("8a9e989458f0b9af0158f0e9ef880010");
		gapConf2.setHouseBaseFid("8a9084df54b39bd30154b3c6b76e0019");
//		gapConf2.setRoomFid("8a9e989458f0b9af0158f0e8fb12000e");
		gapConf2.setDicCode("ProductRulesEnum0020002");
		gapConf2.setDicVal("0.9");
		gapConf2.setIsDel(2);
		confList.add(gapConf2);
		HouseConfMsgEntity gapConf3 = new HouseConfMsgEntity();
		gapConf3.setFid("8a9e989458f0b9af0158f0e9ef910011");
		gapConf3.setHouseBaseFid("8a9084df54b39bd30154b3c6b76e0019");
//		gapConf3.setRoomFid("8a9e989458f0b9af0158f0e8fb12000e");
		gapConf3.setDicCode("ProductRulesEnum0020003");
		gapConf3.setDicVal("0.7");
		gapConf3.setIsDel(2);
		confList.add(gapConf3);

		HouseConfMsgEntity saveRate = new HouseConfMsgEntity();
		saveRate.setFid("8a9e989458f0b9af0158f0eb38eb0012");
		saveRate.setHouseBaseFid("8a9084df54b39bd30154b3c6b76e0019");
//		gapConf3.setRoomFid("8a9e989458f0b9af0158f0e8fb12000e");
		saveRate.setDicCode("ProductRulesEnum0019001");
		saveRate.setDicVal("90");
		saveRate.setIsDel(2);
		confList.add(saveRate);

		HouseConfMsgEntity thirtyRate = new HouseConfMsgEntity();
		thirtyRate.setFid("8a9e989458f0b9af0158f0eba6530013");
		thirtyRate.setHouseBaseFid("8a9084df54b39bd30154b3c6b76e0019");
//		gapConf3.setRoomFid("8a9e989458f0b9af0158f0e8fb12000e");
		thirtyRate.setDicCode("ProductRulesEnum0019002");
		thirtyRate.setDicVal("80");
		thirtyRate.setIsDel(2);
		confList.add(thirtyRate);

		String s = houseIssueServiceProxy.saveHouseConfList(JsonEntityTransform.Object2Json(confList));
//		String s = houseIssueServiceProxy.findGapAndFlexPrice(JsonEntityTransform.Object2Json(gapConf));
		System.err.println(s);
	}

	@Test
	public void batchPicTest(){
		HousePicDto housePicDto = new HousePicDto();
		housePicDto.setHouseBaseFid("8a9084df5847373d0158485f8b7d032d");
//		housePicDto.setHouseRoomFid("8a9084df5847373d01584860de120335");
		housePicDto.setPicType(1);
		String resultJson = houseIssueServiceProxy.delAllHousePicMsgByFid(JsonEntityTransform.Object2Json(housePicDto));
		System.err.println(resultJson);

	}

	@Test
	@Deprecated
	public void mergeCheckHouseRoomMsg() {
		HouseRoomMsgDto houseRoomMsg = new HouseRoomMsgDto();
		houseRoomMsg.setHouseBaseFid("8a9e9abc54edbe500154edbe50340001");
		houseRoomMsg.setFid("8a9e9c9954f8946b0154f89bec3f0002");
		houseRoomMsg.setRoomName("201");
		houseRoomMsg.setBedNum(1);
		houseRoomMsg.setRoomArea(10.56);
		houseRoomMsg.setRoomStatus(10);
		//		houseRoomMsg.setRoomPrice(120);
		houseRoomMsg.setIsToilet(0);
		houseRoomMsg.setIsBalcony(0);
		houseRoomMsg.setRoomAspect(1);
		houseRoomMsg.setCreateUid(UUIDGenerator.hexUUID());
		houseRoomMsg.setOperateSeq(6);
		String resultJson = houseIssueServiceProxy.mergeCheckHouseRoomMsg(JsonEntityTransform.Object2Json(houseRoomMsg),null);
		System.err.println(resultJson);
	}

	@Test
	public void updateHouseRoomMsgTest() {
		HouseRoomMsgDto houseRoomMsg = new HouseRoomMsgDto();
		houseRoomMsg.setFid("8a9e9a9453fb3cb00153fb3cb2270001");
		houseRoomMsg.setHouseBaseFid(UUIDGenerator.hexUUID());
		houseRoomMsg.setRoomName("测试房间名称");
		houseRoomMsg.setBedNum(2);
		houseRoomMsg.setRoomArea(10.56);
		houseRoomMsg.setRoomStatus(10);
		houseRoomMsg.setRoomPrice(120);
		houseRoomMsg.setIsToilet(0);
		houseRoomMsg.setIsBalcony(0);
		houseRoomMsg.setRoomAspect(1);
		String resultJson = houseIssueServiceProxy.mergeHouseRoomMsg(JsonEntityTransform.Object2Json(houseRoomMsg));
		System.err.println(resultJson);
	}

	@Test
	@Deprecated
	public void addHouseBedMsgTest() {
		HouseBedMsgEntity houseBedMsg = new HouseBedMsgEntity();
		houseBedMsg.setRoomFid("8a9084df550dc4ae0155150b9ec51e53");
		houseBedMsg.setHouseBaseFid("8a9e9aae5419cc22015419cc250a0002");
		houseBedMsg.setBedPrice(80);
		houseBedMsg.setCreateUid(UUIDGenerator.hexUUID());
		String resultJson = houseIssueServiceProxy.mergeHouseBedMsg(JsonEntityTransform.Object2Json(houseBedMsg));
		System.err.println(resultJson);
	}

	@Test
	@Deprecated
	public void updateHouseBedMsgTest() {
		HouseBedMsgEntity houseBedMsg = new HouseBedMsgEntity();
		houseBedMsg.setFid("8a9e9a8b53d640d00153d640d2800003");
		houseBedMsg.setRoomFid(UUIDGenerator.hexUUID());
		houseBedMsg.setHouseBaseFid(UUIDGenerator.hexUUID());
		houseBedMsg.setBedStatus(10);
		houseBedMsg.setBedSn(102);
		houseBedMsg.setBedPrice(100);
		String resultJson = houseIssueServiceProxy.mergeHouseBedMsg(JsonEntityTransform.Object2Json(houseBedMsg));
		System.err.println(resultJson);
	}

	@Test
	public void searchHouseRoomMsgByFidTest(){
		String houseRoomFid = "8a9e9a8b53d6e07a0153d6e07c390002"; 
		String resultJson = houseIssueServiceProxy.searchHouseRoomMsgByFid(houseRoomFid);
		System.err.println(resultJson);
	}



	@Test
	public void countBedByRoomFid(){
		String houseBedFid = "8a9e9aae5419d73b015419d73ddb0001";
		String resultJson = houseIssueServiceProxy.countBedByRoomFid(houseBedFid);
		System.err.println(resultJson);
	}

	@Test
	public void searchHouseBedMsgByFidTest(){
		String houseBedFid = "8a9e9a8b53d640d00153d640d2800003"; 
		String resultJson = houseIssueServiceProxy.searchHouseBedMsgByFid(houseBedFid);
		System.err.println(resultJson);
	}

	@Test
	public void searchBedListByRoomFidTest(){
		String houseRoomFid = "8a9e9ab2547b337701547b3379420006";
		String resultJson = houseIssueServiceProxy.searchBedListByRoomFid(houseRoomFid);
		System.err.println(resultJson);
	}

	@Test
	public void searchRoomListByHouseBaseFidTest(){
		String houseBaseFid = "8a9e9a8b53d6089f0153d608a1f80002"; 
		String resultJson = houseIssueServiceProxy.searchRoomListByHouseBaseFid(houseBaseFid);
		System.err.println(resultJson);
	}

	@Test
	public void searchHouseBaseMsgByFidTest(){
		String houseBaseFid = "8a9e9a8b53d6089f0153d608a1f80002";
		String resultJson = houseIssueServiceProxy.searchHouseBaseMsgByFid(houseBaseFid);
		System.err.println(resultJson);
	}

	@Test
	public void searchHouseBaseExtAndHouseConfMsgListTest(){
		HouseBaseExtVo houseBaseExtVo = new HouseBaseExtVo();
		houseBaseExtVo.setHouseBaseFid("8a9e9aba54de71940154de7194c50001");

		List<HouseConfMsgEntity> houseConfList = new ArrayList<HouseConfMsgEntity>();
		HouseConfMsgEntity houseConf1 = new HouseConfMsgEntity();
		houseConf1.setDicCode("ProductRulesEnum008001");
		houseConfList.add(houseConf1);

		HouseConfMsgEntity houseConf2 = new HouseConfMsgEntity();
		houseConf2.setDicCode("ProductRulesEnum008001");
		houseConfList.add(houseConf2);

		houseBaseExtVo.setHouseConfList(houseConfList);

		String resultJson = houseIssueServiceProxy.searchHouseBaseExtAndHouseConfList(JsonEntityTransform
				.Object2Json(houseBaseExtVo));
		//String resultJson = houseIssueServiceProxy.searchHouseBaseExtAndHouseConfList("{\"id\":null,\"fid\":null,\"houseBaseFid\":\"8a9e9aba54de71940154de7194c50001\",\"buildingNum\":null,\"unitNum\":null,\"floorNum\":null,\"houseNum\":null,\"houseStreet\":null,\"facilityCode\":null,\"serviceCode\":null,\"orderType\":null,\"homestayType\":null,\"minDay\":null,\"discountRulesCode\":null,\"depositRulesCode\":null,\"checkOutRulesCode\":null,\"checkInLimit\":null,\"checkInTime\":null,\"checkOutTime\":null,\"sheetsReplaceRules\":null,\"fullDiscount\":null,\"isTogetherLandlord\":null,\"operateSeq\":null,\"houseConfList\":[{\"id\":null,\"fid\":null,\"houseBaseFid\":null,\"roomFid\":null,\"bedFid\":null,\"dicCode\":\"ProductRulesEnum008001\",\"dicVal\":null,\"createDate\":null,\"lastModifyDate\":null,\"isDel\":null},{\"id\":null,\"fid\":null,\"houseBaseFid\":null,\"roomFid\":null,\"bedFid\":null,\"dicCode\":\"ProductRulesEnum008002\",\"dicVal\":null,\"createDate\":null,\"lastModifyDate\":null,\"isDel\":null}]}");
		System.err.println(resultJson);
	}

	@Test
	public void addHouseBaseExtAndHouseConfMsgListTest(){
		HouseBaseExtVo houseBaseExtVo = new HouseBaseExtVo();
		houseBaseExtVo.setHouseBaseFid("8a9e9aae5419cc22015419cc250a0002");
		houseBaseExtVo.setOperateSeq(5);
		houseBaseExtVo.setFacilityCode("ProductRulesEnum002");
		houseBaseExtVo.setDiscountRulesCode("ProductRulesEnum0012");
		houseBaseExtVo.setDepositRulesCode("ProductRulesEnum008002");
		houseBaseExtVo.setCheckOutRulesCode("TradeRulesEnum005002");

		List<HouseConfMsgEntity> houseConfList = new ArrayList<HouseConfMsgEntity>();
		HouseConfMsgEntity houseConf1 = new HouseConfMsgEntity();
		houseConf1.setDicCode("TradeRulesEnum005002");
		houseConf1.setDicVal("严格退订政策");
		houseConfList.add(houseConf1);

		HouseConfMsgEntity houseConf2 = new HouseConfMsgEntity();
		houseConf2.setDicCode("ProductRulesEnum008002");
		houseConf2.setDicVal("按房租收取");
		houseConfList.add(houseConf2);

		houseBaseExtVo.setHouseConfList(houseConfList);

		String resultJson = houseIssueServiceProxy.mergeHouseBaseExtAndHouseConfList(JsonEntityTransform
				.Object2Json(houseBaseExtVo));
		System.err.println(resultJson);
	}

	@Test
	public void updateHouseBaseExtAndHouseConfMsgListTest(){
		/*HouseBaseExtVo houseBaseExtVo = new HouseBaseExtVo();
		houseBaseExtVo.setHouseBaseFid("8a9e9a8b53d6089f0153d608a1f80002");
		houseBaseExtVo.setOperateSeq(100);

		List<HouseConfMsgEntity> houseConfList = new ArrayList<HouseConfMsgEntity>();
		HouseConfMsgEntity houseConf1 = new HouseConfMsgEntity();
		houseConf1.setFid("8a9e9a9b5403391d015403391d470000");
		houseConf1.setDicCode("测试code");
		houseConf1.setDicVal("测试value");
		houseConfList.add(houseConf1);

		HouseConfMsgEntity houseConf2 = new HouseConfMsgEntity();
		houseConf2.setFid("8a9e9a9b5403391d015403391d5c0001");
		houseConf2.setDicCode("测试code");
		houseConf2.setDicVal("测试value");
		houseConfList.add(houseConf2);

		houseBaseExtVo.setHouseConfList(houseConfList);

		String resultJson = houseIssueServiceProxy.mergeHouseBaseExtAndHouseConfList(JsonEntityTransform
				.Object2Json(houseBaseExtVo));*/
		String resultJson = houseIssueServiceProxy.mergeHouseBaseExtAndHouseConfList("{\"id\":null,\"fid\":null,\"houseBaseFid\":\"8a9e9aba54de71940154de7194c50001\",\"buildingNum\":null,\"unitNum\":null,\"floorNum\":null,\"houseNum\":null,\"houseStreet\":null,\"facilityCode\":null,\"serviceCode\":null,\"orderType\":null,\"homestayType\":null,\"minDay\":null,\"discountRulesCode\":null,\"depositRulesCode\":null,\"checkOutRulesCode\":\"TradeRulesEnum005001\",\"checkInLimit\":null,\"checkInTime\":null,\"checkOutTime\":null,\"sheetsReplaceRules\":null,\"fullDiscount\":null,\"isTogetherLandlord\":null,\"operateSeq\":null,\"houseConfList\":[]}");
		System.err.println(resultJson);
	}

	@Test
	public void updateHouseBaseMsgAndHouseDescTest(){
		HouseBaseMsgDto houseBaseMsg = new HouseBaseMsgDto();
		houseBaseMsg.setFid("8a9e9a8b53d6089f0153d608a1f80002");
		//房源基础信息
		houseBaseMsg.setLeasePrice(250);
		houseBaseMsg.setHouseName("测试房源名称");

		//房源描述信息
		houseBaseMsg.setHouseDescFid("8a9e9a8b53d6089f0153d608a2000003");
		houseBaseMsg.setHouseDesc("测试房源描述");
		String resultJson = houseIssueServiceProxy.updateHouseBaseMsgAndHouseDesc(JsonEntityTransform
				.Object2Json(houseBaseMsg));
		System.err.println(resultJson);
	}

	@Test
	public void searchHouseBaseMsgAndHouseDescTest(){
		String houseBaseFid = "8a9e9a8b53d6089f0153d608a1f80002";
		String resultJson = houseIssueServiceProxy.searchHouseBaseMsgAndHouseDesc(houseBaseFid);
		System.err.println(resultJson);
	}

	@Test
	public void saveHousePicMsgListTest(){
		List<HousePicMsgEntity> list = new ArrayList<HousePicMsgEntity>();

		HousePicMsgEntity housePicMsg1 = new HousePicMsgEntity();
		housePicMsg1.setFid(UUIDGenerator.hexUUID());
		housePicMsg1.setHouseBaseFid("8a9e9a8b53d6089f0153d608a1f80002");
		housePicMsg1.setOperateType(0);
		housePicMsg1.setPicName("测试图片名称_1");
		housePicMsg1.setPicType(1);
		housePicMsg1.setPicBaseUrl("/test/url/001");
		housePicMsg1.setPicSuffix(".png");
		housePicMsg1.setPicServerUuid(UUIDGenerator.hexUUID());
		housePicMsg1.setAuditStatus(0);
		housePicMsg1.setIsDefault(0);
		housePicMsg1.setCreateFid(UUIDGenerator.hexUUID());
		list.add(housePicMsg1);

		HousePicMsgEntity housePicMsg2 = new HousePicMsgEntity();
		housePicMsg2.setFid(UUIDGenerator.hexUUID());
		housePicMsg2.setHouseBaseFid("8a9e9a8b53d6089f0153d608a1f80002");
		housePicMsg2.setOperateType(0);
		housePicMsg2.setPicName("测试图片名称_2");
		housePicMsg2.setPicType(1);
		housePicMsg2.setPicBaseUrl("/test/url/002");
		housePicMsg2.setPicSuffix(".jpg");
		housePicMsg2.setPicServerUuid(UUIDGenerator.hexUUID());
		housePicMsg2.setAuditStatus(0);
		housePicMsg2.setIsDefault(0);
		housePicMsg2.setCreateFid(UUIDGenerator.hexUUID());
		list.add(housePicMsg2);

		String resultJson = houseIssueServiceProxy.saveHousePicMsgList(JsonEntityTransform.Object2Json(list));
		System.err.println(resultJson);
	}


	@Test
	public void deleteCheckHouseRoomMsgByFid(){
		String houseRoomFid = "8a9084df55025cb901550268552f0006";
		String resultJson = houseIssueServiceProxy.deleteCheckHouseRoomMsgByFid(houseRoomFid);
		System.err.println(resultJson);
	}

	@Test
	public void deleteHouseRoomMsgByFidTest(){
		String houseRoomFid = "9f50fc1a-f8f7-11e5-9cf9-0050568f07f8";
		String resultJson = houseIssueServiceProxy.deleteHouseRoomMsgByFid(houseRoomFid);
		System.err.println(resultJson);
	}

	@Test
	public void deleteHouseBedMsgByFidTest(){
		String houseBedFid = "8a9e9a8b53d640d00153d640d2800003";
		String resultJson = houseIssueServiceProxy.deleteHouseBedMsgByFid(houseBedFid);
		System.err.println(resultJson);
	}

	@Test
	public void deleteHousePicMsgByFidTest(){		
		HousePicDto housePicDto=new HousePicDto();
		housePicDto.setHouseBaseFid("8a90a2d45d30a0c1015d30a92c1a0024");
		housePicDto.setPicType(1);
		housePicDto.setHousePicFid("8a90a2d45d30baf2015d30cbbc970161");
		housePicDto.setHouseRoomFid("8a90a2d45d30b362015d30c3c7ae006b");
		housePicDto.setPicSource(0);
		String resultJson = houseIssueServiceProxy.deleteHousePicMsgByFid(JsonEntityTransform.Object2Json(housePicDto));
		System.err.println(resultJson);
	}

	@Test
	public void searchHousePicMsgByFidTest(){
		String housePicFid = "8a9e9a9053e59dbe0153e59dbe0e0000";
		String resultJson = houseIssueServiceProxy.searchHousePicMsgByFid(housePicFid);
		System.err.println(resultJson);
	}

	@Test
	public void updateHouseBaseAndRoomListTest() {
		List<HouseRoomMsgEntity> list = new ArrayList<HouseRoomMsgEntity>();
		HouseRoomMsgEntity houseRoomMsg1 = new HouseRoomMsgEntity();
		houseRoomMsg1.setFid("8a9e9a8b53d6e07a0153d6e07c390002");
		houseRoomMsg1.setRoomName("111");
		list.add(houseRoomMsg1);

		HouseRoomMsgEntity houseRoomMsg2 = new HouseRoomMsgEntity();
		houseRoomMsg2.setFid("8a9e9a8b53d6089f0153d608a1f80002");
		houseRoomMsg2.setRoomName("独立房间");
		list.add(houseRoomMsg2);

		HouseRoomListDto dto = new HouseRoomListDto();
		dto.setHouseBaseFid("8a9e9aae53e97a9e0153e97a9ecb0000");
		dto.setOperateSeq(0);
		dto.setIntactRate(1.0);
		dto.setRoomList(list);
		String resultJson = houseIssueServiceProxy.updateHouseBaseAndRoomList(JsonEntityTransform.Object2Json(dto));
		System.err.println(resultJson);
	}

	@Test
	public void addHouseBaseMsgTest() {
		HouseBaseMsgEntity houseBaseMsg = new HouseBaseMsgEntity();
		houseBaseMsg.setHouseName("测试小屋1");
		houseBaseMsg.setLandlordUid("8a9e9a8b53d6da8b0153d6da8bae0000");
		String resultJson = houseIssueServiceProxy.saveHouseBaseMsg(JsonEntityTransform.Object2Json(houseBaseMsg));
		System.err.println(resultJson);
	}

	@Test
	public void updateHouseBaseMsgTest() {
		HouseBaseMsgEntity houseBaseMsg = new HouseBaseMsgEntity();
		houseBaseMsg.setFid("8a9e9a8b53d6da8b0153d6da8e4b0002");
		houseBaseMsg.setHouseName("测试小屋1");
		String resultJson = houseIssueServiceProxy.updateHouseBaseMsg(JsonEntityTransform.Object2Json(houseBaseMsg));
		System.err.println(resultJson);
	}

	/**
	 * 
	 * 测试根据房源基础信息逻辑id查询房源基础信息扩展
	 *
	 * @author liujun
	 * @created 2016年4月9日 下午4:49:10
	 *
	 */
	@Test
	public void searchHouseBaseAndExtByFidTest(){
		String houseBaseFid = "8a9e9a8b53d6089f0153d608a1f80002";
		String resultJson = houseIssueServiceProxy.searchHouseBaseAndExtByFid(houseBaseFid);
		System.err.println(resultJson);
	}

	@Test
	public void updateHouseBaseAndExtTest(){
		HouseBaseExtDto houseBase = new HouseBaseExtDto();
		houseBase.setFid("8a9e9a9453f95bf40153f95bf70c0001");
		houseBase.setHouseName("测试小屋4");

		HouseBaseExtEntity houseBaseExt = new HouseBaseExtEntity();
		houseBaseExt.setHouseBaseFid("8a9e9a9453f95bf40153f95bf70c0001");
		houseBaseExt.setCheckInLimit(5);
		houseBaseExt.setIsTogetherLandlord(1);
		houseBase.setHouseBaseExt(houseBaseExt);
		String resultJson = houseIssueServiceProxy.updateHouseBaseAndExt(JsonEntityTransform.Object2Json(houseBase));
		System.err.println(resultJson);
	}


	@Test
	public void testSetDefaultPic(){
		HousePicDto housePicDto=new HousePicDto();
		housePicDto.setHousePicFid("8a90a2d45d16f7e5015d17174e9b02cd");
		housePicDto.setHouseBaseFid("8a90a2d45d16f7e5015d17174e4802ca");
		housePicDto.setPicType(0);
		String updateHouseDefaultPic = houseIssueServiceProxy.updateHouseDefaultPic(JsonEntityTransform.Object2Json(housePicDto));
		System.err.println(updateHouseDefaultPic);
	}

	@Test
	public void isDefaultPicTest(){
		//String resultJson=houseIssueServiceProxy.isDefaultPic("8a9e9a9a54ca57ec0154ca6caec50006");
		//System.out.println(resultJson);
	}


	@Test
	public void mergeHouseBaseAndPhyAndExtTest(){
		//String json = "{\"id\":null,\"fid\":\"\",\"phyFid\":null,\"houseName\":null,\"rentWay\":1,\"houseType\":2,\"houseStatus\":null,\"houseWeight\":null,\"landlordUid\":\"e0a0f779-9117-6283-84e1-43e0be20ecf4\",\"leasePrice\":null,\"houseArea\":null,\"roomNum\":null,\"hallNum\":null,\"toiletNum\":null,\"kitchenNum\":null,\"balconyNum\":null,\"cameramanName\":null,\"cameramanMobile\":null,\"operateSeq\":1,\"intactRate\":null,\"refreshDate\":null,\"tillDate\":null,\"createDate\":null,\"lastModifyDate\":null,\"isDel\":null,\"houseAddr\":null,\"isPic\":null,\"houseSource\":5,\"housePhyMsg\":{\"id\":null,\"fid\":null,\"nationCode\":\"100000\",\"provinceCode\":\"110000\",\"cityCode\":\"110100\",\"areaCode\":\"110102\",\"buildingCode\":null,\"communityName\":\"李少川小区\",\"zoJobNum\":null,\"zoName\":null,\"zoMobile\":null,\"longitude\":null,\"latitude\":null,\"createDate\":null,\"lastModifyDate\":null,\"createUid\":null,\"isDel\":null},\"houseBaseExt\":{\"id\":null,\"fid\":null,\"houseBaseFid\":\"\",\"buildingNum\":null,\"unitNum\":null,\"floorNum\":null,\"houseNum\":null,\"houseStreet\":\"李少川街道\",\"facilityCode\":null,\"serviceCode\":null,\"orderType\":null,\"homestayType\":null,\"minDay\":null,\"discountRulesCode\":null,\"depositRulesCode\":null,\"checkOutRulesCode\":null,\"checkInLimit\":null,\"checkInTime\":null,\"checkOutTime\":null,\"sheetsReplaceRules\":null,\"fullDiscount\":null,\"isTogetherLandlord\":null},\"houseDesc\":null,\"houseConfList\":null,\"housePicList\":null,\"roomList\":null}";
		// String json = "{\"id\":null,\"fid\":\"8a9e9abc54edbe500154edbe50340001\",\"phyFid\":null,\"houseName\":null,\"rentWay\":0,\"houseType\":1,\"houseStatus\":null,\"houseWeight\":null,\"landlordUid\":\"e0a0f779-9117-6283-84e1-43e0be20ecf4\",\"leasePrice\":null,\"houseArea\":null,\"roomNum\":null,\"hallNum\":null,\"toiletNum\":null,\"kitchenNum\":null,\"balconyNum\":null,\"cameramanName\":null,\"cameramanMobile\":null,\"operateSeq\":1,\"intactRate\":null,\"refreshDate\":null,\"tillDate\":null,\"createDate\":null,\"lastModifyDate\":null,\"isDel\":null,\"houseAddr\":null,\"isPic\":null,\"houseSource\":5,\"housePhyMsg\":{\"id\":null,\"fid\":null,\"nationCode\":\"100000\",\"provinceCode\":\"110000\",\"cityCode\":\"110100\",\"areaCode\":\"110102\",\"buildingCode\":null,\"communityName\":\"李少川小区\",\"zoJobNum\":null,\"zoName\":null,\"zoMobile\":null,\"longitude\":null,\"latitude\":null,\"createDate\":null,\"lastModifyDate\":null,\"createUid\":null,\"isDel\":null},\"houseBaseExt\":{\"id\":null,\"fid\":null,\"houseBaseFid\":\"8a9e9abc54edbe500154edbe50340001\",\"buildingNum\":null,\"unitNum\":null,\"floorNum\":null,\"houseNum\":null,\"houseStreet\":\"李少川街道修改一下\",\"facilityCode\":null,\"serviceCode\":null,\"orderType\":null,\"homestayType\":null,\"minDay\":null,\"discountRulesCode\":null,\"depositRulesCode\":null,\"checkOutRulesCode\":null,\"checkInLimit\":null,\"checkInTime\":null,\"checkOutTime\":null,\"sheetsReplaceRules\":null,\"fullDiscount\":null,\"isTogetherLandlord\":null},\"houseDesc\":null,\"houseConfList\":null,\"housePicList\":null,\"roomList\":null}";
		String json = "{\"id\":null,\"fid\":\"8a9e9abc54edbe500154edbe50340001\",\"phyFid\":null,\"houseName\":null,\"rentWay\":1,\"houseType\":1,\"houseStatus\":null,\"oldStatus\":10,\"houseWeight\":null,\"landlordUid\":\"e0a0f779-9117-6283-84e1-43e0be20ecf4\",\"leasePrice\":null,\"houseArea\":null,\"roomNum\":null,\"hallNum\":null,\"toiletNum\":null,\"kitchenNum\":null,\"balconyNum\":null,\"cameramanName\":null,\"cameramanMobile\":null,\"operateSeq\":1,\"intactRate\":null,\"refreshDate\":null,\"tillDate\":null,\"createDate\":null,\"lastModifyDate\":null,\"isDel\":null,\"houseAddr\":null,\"isPic\":null,\"houseSource\":5,\"housePhyMsg\":{\"id\":null,\"fid\":null,\"nationCode\":\"100000\",\"provinceCode\":\"110000\",\"cityCode\":\"110100\",\"areaCode\":\"110102\",\"buildingCode\":null,\"communityName\":\"将府花园\",\"zoJobNum\":null,\"zoName\":null,\"zoMobile\":null,\"longitude\":null,\"latitude\":null,\"createDate\":null,\"lastModifyDate\":null,\"createUid\":null,\"isDel\":null},\"houseBaseExt\":{\"id\":null,\"fid\":null,\"houseBaseFid\":\"8a9e9abc54edbe500154edbe50340001\",\"buildingNum\":null,\"unitNum\":null,\"floorNum\":null,\"houseNum\":null,\"houseStreet\":\"将台东路\",\"facilityCode\":null,\"serviceCode\":null,\"orderType\":null,\"homestayType\":null,\"minDay\":null,\"discountRulesCode\":null,\"depositRulesCode\":null,\"checkOutRulesCode\":null,\"checkInLimit\":null,\"checkInTime\":null,\"checkOutTime\":null,\"sheetsReplaceRules\":null,\"fullDiscount\":null,\"isTogetherLandlord\":null},\"houseDesc\":null,\"houseConfList\":null,\"housePicList\":null,\"roomList\":null}";
		String result = houseIssueServiceProxy.mergeHouseBaseAndPhyAndExt(json);
		System.err.println("result:"+result);
	}

	@Test
	public void updateHouseInputDetailTest(){
		HouseBaseDetailVo houseBaseDetailVo=new HouseBaseDetailVo();
		houseBaseDetailVo.setFid("8a9084df5526714801552a18750e0043");
		houseBaseDetailVo.setRentWay(0);
		houseBaseDetailVo.setLandlordUid("7a8c4184-8e2e-37b4-08e8-f4c20225e350");
		houseBaseDetailVo.setHouseName("测试更新房源98456456");
		houseIssueServiceProxy.updateHouseInputDetail(JsonEntityTransform.Object2Json(houseBaseDetailVo));
	}

	@Test
	public void findHouseFacilityAndServiceTest(){
		String resultJson=houseIssueServiceProxy.findHouseFacilityAndService("8a9e9abc54edbe500154edbe50340001");
		System.err.println(resultJson);
	}

	@Test
	public void updateHouseConfTest(){
		List<HouseConfMsgEntity> list=new ArrayList<HouseConfMsgEntity>();
		HouseConfMsgEntity houseConfMsgEntity=new HouseConfMsgEntity();
		houseConfMsgEntity.setHouseBaseFid("8a9e9abc54edbe500154edbe50340001");
		houseConfMsgEntity.setDicCode("ProductRulesEnum002001");
		houseConfMsgEntity.setDicVal("2");
		list.add(houseConfMsgEntity);
		HouseConfMsgEntity houseConfMsgEntit1=new HouseConfMsgEntity();
		houseConfMsgEntit1.setHouseBaseFid("8a9e9abc54edbe500154edbe50340001");
		houseConfMsgEntit1.setDicCode("ProductRulesEnum0015");
		houseConfMsgEntit1.setDicVal("2");
		list.add(houseConfMsgEntit1);
		String resultJson=houseIssueServiceProxy.updateHouseConf(JsonEntityTransform.Object2Json(list));
		System.err.println(resultJson);
	}

	@Test
	public void testIssueHouse(){
		HouseBaseDto dto = new HouseBaseDto();
		dto.setHouseFid("8a9084df5559e04301555c0c29e4000d");
		dto.setLandlordUid("5f4f193b-07fd-a708-85f8-22907004fd6d");
		dto.setRentWay(0);
		String issueHouseInDetail = houseIssueServiceProxy.issueHouseInDetail(JsonEntityTransform.Object2Json(dto));
		System.out.println(issueHouseInDetail);
	}

	@Test
	public void updateHouseDescAndBaseExtTest(){
		String resultJson=houseIssueServiceProxy.updateHouseDescAndBaseExt("{\"id\":null,\"fid\":null,\"houseBaseFid\":\"8a9e9aba54de71940154de7194c50001\",\"createDate\":null,\"lastModifyDate\":null,\"createUid\":null,\"isDel\":null,\"houseDesc\":null,\"houseAroundDesc\":null,\"houseRules\":\"11111111111111111111\",\"operateSeq\":null,\"intactRate\":null,\"houseExtFid\":null,\"buildingNum\":null,\"unitNum\":null,\"floorNum\":null,\"houseNum\":null,\"minDay\":null,\"checkInTime\":null,\"checkOutTime\":null,\"sheetsReplaceRules\":null}");
		System.err.println(resultJson);
	}
	@Test
	public void testcancleHouseOrRoomByFid(){
		HouseBaseDto dto = new HouseBaseDto();
		dto.setHouseFid("8a9084df5559e04301555c0c29e4000d");
		dto.setRentWay(0);
		dto.setLandlordUid("eaaf194b-067e-4289-bcd7-63a9433d3ef4");
		String result = houseIssueServiceProxy.cancleHouseOrRoomByFid(JsonEntityTransform.Object2Json(dto));
		System.out.println(result);
	}

	@Test
	public void testOpLogList(){
		HouseOpLogSpDto dto = new HouseOpLogSpDto();
		dto.setHouseFid("8a9084df555e743301556151ff0b000a");
		dto.setLimit(1);
		dto.setPage(1);
		dto.setOperateType(1);
		dto.setToStatus(HouseStatusEnum.XXSHWTG.getCode());
		String json = houseIssueServiceProxy.findOperateLogList(JsonEntityTransform.Object2Json(dto));
		System.out.println(json);
	}

	@Test
	public void testdeleteHouseOrRoomByFid(){
		HouseBaseDto dto = new HouseBaseDto();
		dto.setHouseFid("8a9e9ab9555d47f101555d48be700003");
		dto.setRentWay(1);
		String delJson = houseIssueServiceProxy.deleteHouseOrRoomByFid(JsonEntityTransform.Object2Json(dto));
		System.out.println(delJson);
	}

	@Test
	public void searchHouseDescAndBaseExtTest(){
		String resultJson=houseIssueServiceProxy.searchHouseDescAndBaseExt("8a9e9a9a556c00b701556c33eeec0002");
		System.err.println(resultJson);
	}

	@Test
	public void updateHouseAndDelRoomTest(){

		HouseBaseMsgEntity houseBaseMsg  = new HouseBaseMsgEntity();

		houseBaseMsg.setFid("8a90a2d4549341c60154947198f00198");
		houseBaseMsg.setRoomNum(0);
		this.houseIssueServiceProxy.updateHouseAndDelRoom(JsonEntityTransform.Object2Json(houseBaseMsg));
	}

	@Test
	public void houseStatusSituationTest(){

		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.houseIssueServiceProxy.houseStatusSituation("8a9e99a8574b6ac001574b6ac07f0001"));
		System.out.println(dto);
	}
	
	@Test
	public void houseInfoSituationTest(){
		HouseBaseVo vo = new HouseBaseVo();
		vo.setHouseFid("8a9e99a8574b6ac001574b6ac07f0001");
		vo.setRoomFid("8a9e99a8574b6ac001574b6cafcd0007");
		vo.setRentWay(1);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.houseIssueServiceProxy.houseInfoSituation(JsonEntityTransform.Object2Json(vo)));
		System.out.println(dto);
	}

	@Test
	public void saveRoomBedZTest(){

		RoomBedZDto roomBed = new RoomBedZDto();
		roomBed.setCreatedFid("456456456f4d5s6f45d64f");
		roomBed.setHouseBaseFid("8a9e989c56b51cd90156b653de8a0047");
		roomBed.setRoomPrice(60000);
		roomBed.setRoomFid("8a9e989c56b7f4a80156b7f4a8250000");

		List<HouseBedMsgEntity> list = new ArrayList<HouseBedMsgEntity>();

		for (int i = 0; i < 1; i++) {
			HouseBedMsgEntity houseBedMsg = new HouseBedMsgEntity();
			houseBedMsg.setBedSize(1);
			houseBedMsg.setBedType(1);
			houseBedMsg.setFid("8a9e989c56b7f4a80156b7f4e3010001");
			list.add(houseBedMsg);
		} 

		HouseBedMsgEntity houseBedMsg = new HouseBedMsgEntity();
		houseBedMsg.setBedSize(1);
		houseBedMsg.setBedType(1);
		list.add(houseBedMsg);
		roomBed.setListBeds(list);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.houseIssueServiceProxy.saveRoomBedZ(JsonEntityTransform.Object2Json(roomBed)));

		System.out.println(dto);
	}

	@Test
	public void releaseHouseTest(){

		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.houseIssueServiceProxy.releaseHouse("8a9e989c56b51cd90156b653de8a0047"));
		System.out.println(dto);

	}
	
	@Test
	public void countBedNumByHouseFidTest(){

		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.houseIssueServiceProxy.countBedNumByHouseFid("8a9e989c56b51cd90156b653de8a0047","0"));
		System.out.println(dto);

	}
	
	@Test
	public void findHouseDepositTest(){
		
		DataTransferObject dto =  JsonEntityTransform.json2DataTransferObject(this.houseIssueServiceProxy.findHouseDeposit("8a9e9891581974cb01581974cbe70001"));
		
		System.out.println(dto);
	}
	

	@Test
	public void mergeHouseExtAndDescTest(){
		HouseBaseExtDescDto dto = new HouseBaseExtDescDto();
		dto.setHouseBaseFid("8a9e989e5cc590b7015cc596f7ee0004");
		dto.setCheckInTime("14:00:00");
		dto.setCheckOutRulesCode("TradeRulesEnum005002");
		dto.setCheckOutTime("12:00:00");
		dto.setDepositRulesCode("ProductRulesEnum008002");
		dto.setMinDay(1);
		dto.setOrderType(1);
		dto.setRoomFid("8a9e989e5cc590b7015cc5990c16000");
		HouseConfMsgEntity houseConfMsgEntity = new HouseConfMsgEntity();
		houseConfMsgEntity.setDicCode("ProductRulesEnum008002");
		houseConfMsgEntity.setDicVal("40000");
		dto.setHouseConfMsgEntity(houseConfMsgEntity);
		HouseDescEntity houseDescEntity= new HouseDescEntity();
		houseDescEntity.setHouseRules("一次成功");
		dto.setHouseDescEntity(houseDescEntity);
		houseIssueServiceProxy.mergeHouseExtAndDesc(JsonEntityTransform.Object2Json(dto));
		System.out.println(dto);
	}
	
	@Test
	public void findHouseOrRoomDepositTest(){
		HouseBaseVo houseBaseVo = new HouseBaseVo();
		houseBaseVo.setHouseFid("8a9084df5fe98929015fec31a95e0a0e");
		houseBaseVo.setRentWay(1);
//		houseBaseVo.setRoomFid("8a9e989e5caae095015cab077c52004a");
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(houseIssueServiceProxy.findHouseOrRoomDeposit(JsonEntityTransform.Object2Json(houseBaseVo)));
		
		System.out.println(dto);
	}

	@Test
	public void saveDealPolicy(){
		CancellationPolicyDto dto = new CancellationPolicyDto();
		dto.setCancellationPolicy("TradeRulesEnum005002");
		dto.setDepositMoney(500);
		dto.setHouseBaseFid("8a9084df5fe98929015fec31a95e0a0e");
		dto.setHouseRules(null);
		dto.setHouseRulesArray("");
		dto.setOrderType(1);
		dto.setRentWay(1);
//		dto.setRoomFid("");
		dto.setStep(5);

		String resultJson = houseIssueServiceProxy.saveDealPolicy(JsonEntityTransform.Object2Json(dto));
		System.out.println(resultJson);
	}


	@Test
	public void fdjflklfj(){
		JSONObject requestJson = new JSONObject();
		requestJson.put("houseFid","8a9084df5fe3025b015fe3ce10d10345");
		requestJson.put("rentWay",1);
//		requestJson.put("roomFid","null");
		requestJson.put("landlordUid","1233587a-7096-4737-b4f7-e2c142b6e64d");

		String result = houseIssueServiceProxy.issueHouseInDetail(JsonEntityTransform.Object2Json(requestJson));
		System.out.print(result);
	}

	@Test
	public void isSetDefaultPic(){
		HouseBaseParamsDto houseBaseParamsDto = new HouseBaseParamsDto();
		houseBaseParamsDto.setHouseBaseFid("8a9099785d10ddd8015d50f5ed2e3e5f");
//		houseBaseParamsDto.setRoomFid("8a90a2d455b47fe00155b9ae1b4600ec");
		houseBaseParamsDto.setRentWay(0);
		String result = houseIssueServiceProxy.isSetDefaultPic(JsonEntityTransform.Object2Json(houseBaseParamsDto));
		System.out.print(result);
		Boolean flag = SOAResParseUtil.getBooleanFromDataByKey(result,"hasDefault");
		System.out.print(flag);
	}

	@Test
	public void isHallByHouseBaseFid() {
		String houseBaseFid = "8a9e9a9a54963a0a01549956f02e0054";
		//int i=houseIssueServiceProxy.isHallByHouseBaseFid(houseBaseFid);
		//System.err.println(i);
	}

	@Test
	public void getHallRoomFidByHouseBaseFidTest() {
		String houseBaseFid = "8a9084df601561ed01602001103d2564";
		String roomFidJson=houseIssueServiceProxy.getHallRoomFidByHouseBaseFid(houseBaseFid);
		System.out.println(roomFidJson.toString());
		String houseRoomFid=SOAResParseUtil.getStrFromDataByKey(roomFidJson, "roomFid");
		System.out.println(houseRoomFid);
	}
}
