package com.ziroom.minsu.services.house.test.proxy;

import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.ziroom.minsu.entity.house.HousePhyMsgEntity;
import com.ziroom.minsu.entity.house.HouseUpdateFieldAuditNewlogEntity;
import com.ziroom.minsu.services.house.dto.*;
import com.ziroom.minsu.services.house.entity.HouseResultNewVo;
import com.ziroom.minsu.services.house.proxy.TroyHouseMgtServiceProxy;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台房源管理测试类
 * </p>
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
public class TroyHouseMgtServiceProxyTest extends BaseTest {

	@Resource(name = "house.troyHouseMgtServiceProxy")
	private TroyHouseMgtServiceProxy troyHouseMgtServiceProxy;

	@Test
	public void findHouseBaseByHouseSn(){

		String houseBaseByHouseSn = troyHouseMgtServiceProxy.findHouseBaseByHouseSn("110100812604Z");
		System.err.println(houseBaseByHouseSn);
	}

	@Test
	public void searchHouseMsgListByAllTest() {
		HouseRequestDto houseRequest = new HouseRequestDto();
//		houseRequest.setRentWay(0);
//		houseRequest.setHouseAddr("将台");
//		houseRequest.setCameramanMobile("");
//		houseRequest.setCameramanName("");
//		houseRequest.setCityCode("");
//		houseRequest.setIsPic(0);
//		houseRequest.setLandlordMobile("");
//		houseRequest.setLandlordName("");
//		houseRequest.setNationCode("");
//		houseRequest.setProvinceCode("");
//		
//		List<Integer> list = new ArrayList<Integer>();
//		list.add(10);
//		list.add(20);
//		houseRequest.setHouseStatusList(list);
		List<Integer> list = new ArrayList<Integer>();
		//list.add(HouseStatusEnum.XXSHTG.getCode());
		//list.add(HouseStatusEnum.ZPSHWTG.getCode());
		//list.add(HouseStatusEnum.SJ.getCode());
		houseRequest.setHouseStatusList(list);
		houseRequest.setRentWay(1);
		houseRequest.setHouseQualityGrade("A");
		//String resultJson = troyHouseMgtServiceProxy.searchHouseMsgList(JsonEntityTransform.Object2Json(houseRequest));
//		String resultJson = troyHouseMgtServiceProxy.searchHouseMsgList("{\"page\":1,\"limit\":10,\"landlordName\":\"磊哥\",\"landlordMobile\":\"15810006510\",\"isPic\":0,\"cameramanName\":\"\",\"cameramanMobile\":\"\",\"houseName\":\"\",\"houseStatus\":40,\"nationCode\":\"\",\"provinceCode\":\"\",\"cityCode\":\"\",\"houseType\":null,\"zoName\":null,\"isWeight\":null,\"landlordNameS\":null,\"landlordMobileS\":null,\"isPicS\":null,\"cameramanNameS\":null,\"cameramanMobileS\":null,\"houseNameS\":null,\"houseStatusS\":null,\"provinceCodeS\":null,\"nationCodeS\":null,\"cityCodeS\":null,\"houseTypeS\":null,\"zoNameS\":null,\"isWeightS\":null,\"landlordUidList\":[\"d3e52c72-af55-4a3f-896f-33bf26a44886\"],\"houseStatusList\":null,\"rentWay\":0,\"houseCreateTimeStart\":\"\",\"houseCreateTimeEnd\":\"\",\"houseOnlineTimeStart\":\"2016-06-29 00:00:00\",\"houseOnlineTimeEnd\":\"2016-06-29 23:59:59\",\"houseSn\":\"110100653995Z\",\"roomCreateTimeStart\":null,\"roomCreateTimeEnd\":null,\"roomOnlineTimeStart\":null,\"roomOnlineTimeEnd\":null,\"roomSn\":null}");
//		String resultJson = troyHouseMgtServiceProxy.searchHouseMsgList("{\"page\":1,\"limit\":10,\"landlordName\":\"\",\"landlordMobile\":\"\",\"isPic\":null,\"cameramanName\":\"\",\"cameramanMobile\":\"\",\"houseName\":\"\",\"houseStatus\":null,\"nationCode\":\"\",\"provinceCode\":\"\",\"cityCode\":\"\",\"houseType\":null,\"zoName\":null,\"isWeight\":null,\"landlordNameS\":null,\"landlordMobileS\":null,\"isPicS\":null,\"cameramanNameS\":null,\"cameramanMobileS\":null,\"houseNameS\":null,\"houseStatusS\":null,\"provinceCodeS\":null,\"nationCodeS\":null,\"cityCodeS\":null,\"houseTypeS\":null,\"zoNameS\":null,\"isWeightS\":null,\"landlordUidList\":null,\"houseStatusList\":null,\"rentWay\":0,\"houseCreateTimeStart\":\"\",\"houseCreateTimeEnd\":\"\",\"houseOnlineTimeStart\":\"\",\"houseOnlineTimeEnd\":\"\",\"houseSn\":\"\",\"roomCreateTimeStart\":null,\"roomCreateTimeEnd\":null,\"roomOnlineTimeStart\":null,\"roomOnlineTimeEnd\":null,\"roomSn\":null,\"empPushName\":\"\",\"empGuardName\":\"\",\"cause\":null,\"houseChannel\":1}");
		//String paramString="{\"page\":1,\"limit\":10,\"landlordName\":\"\",\"landlordMobile\":\"\",\"isPic\":null,\"cameramanName\":\"\",\"cameramanMobile\":\"\",\"houseName\":\"\",\"houseStatus\":null,\"nationCode\":null,\"provinceCode\":null,\"cityCode\":null,\"houseType\":null,\"zoName\":null,\"isWeight\":null,\"landlordNameS\":null,\"landlordMobileS\":null,\"isPicS\":null,\"cameramanNameS\":null,\"cameramanMobileS\":null,\"houseNameS\":null,\"houseStatusS\":null,\"provinceCodeS\":null,\"nationCodeS\":null,\"cityCodeS\":null,\"houseTypeS\":null,\"zoNameS\":null,\"isWeightS\":null,\"landlordUidList\":null,\"houseStatusList\":[20,30,41,40,11,21],\"rentWay\":0,\"houseCreateTimeStart\":\"\",\"houseCreateTimeEnd\":\"\",\"houseOnlineTimeStart\":\"\",\"houseOnlineTimeEnd\":\"\",\"houseSn\":\"\",\"roomCreateTimeStart\":null,\"roomCreateTimeEnd\":null,\"roomOnlineTimeStart\":null,\"roomOnlineTimeEnd\":null,\"roomSn\":null,\"empPushName\":null,\"empGuardName\":null,\"cause\":null,\"houseChannel\":null}";
		String resultJson = troyHouseMgtServiceProxy.searchHouseMsgListNew(JsonEntityTransform.Object2Json(houseRequest));
		System.err.println(resultJson);
	}
	
	@Test
	public void searchHouseMsgListByRoomTest() {
		HouseRequestDto houseRequest = new HouseRequestDto();
		houseRequest.setRentWay(0);
		houseRequest.setCameramanMobile("");
		houseRequest.setCameramanName("");
		houseRequest.setCityCode("");
		houseRequest.setIsPic(0);
		houseRequest.setLandlordMobile("");
		houseRequest.setLandlordName("");
		houseRequest.setNationCode("");
		houseRequest.setProvinceCode("");
		
	/*	List<Integer> list = new ArrayList<Integer>();
		list.add(10);
		houseRequest.setHouseStatusList(list);*/
		String resultJson = troyHouseMgtServiceProxy.searchHouseMsgList(JsonEntityTransform.Object2Json(houseRequest));
		//String resultJson = troyHouseMgtServiceProxy.searchHouseMsgList("{\"page\":1,\"limit\":10,\"landlordName\":\"\",\"landlordMobile\":\"\",\"isPic\":null,\"cameramanName\":\"\",\"cameramanMobile\":\"\",\"houseName\":\"\",\"houseStatus\":null,\"nationCode\":\"\",\"provinceCode\":\"\",\"cityCode\":\"\",\"houseType\":null,\"zoName\":null,\"isWeight\":null,\"landlordNameS\":\"\",\"landlordMobileS\":\"\",\"isPicS\":null,\"cameramanNameS\":\"\",\"cameramanMobileS\":\"\",\"houseNameS\":\"\",\"houseStatusS\":null,\"provinceCodeS\":\"\",\"nationCodeS\":\"\",\"cityCodeS\":\"\",\"houseTypeS\":null,\"zoNameS\":null,\"isWeightS\":null,\"landlordUidList\":null,\"houseStatusList\":null,\"rentWay\":1,\"houseCreateTimeStart\":null,\"houseCreateTimeEnd\":null,\"houseOnlineTimeStart\":null,\"houseOnlineTimeEnd\":null,\"houseSn\":null,\"roomCreateTimeStart\":\"\",\"roomCreateTimeEnd\":\"\",\"roomOnlineTimeStart\":\"2016-06-28 23:59:59\",\"roomOnlineTimeEnd\":\"2016-06-30 23:59:59\",\"roomSn\":\"\"}");
		System.err.println(resultJson);
	}
	
	
	@Test
	public void searchUpateHouseMsgListTest(){
		HouseRequestDto houseRequest = new HouseRequestDto();
		houseRequest.setRentWay(0);
		houseRequest.setCameramanMobile("");
		houseRequest.setCameramanName("");
		houseRequest.setCityCode("");
		houseRequest.setIsPic(0);
		houseRequest.setLandlordMobile("");
		houseRequest.setLandlordName("");
		houseRequest.setNationCode("");
		houseRequest.setProvinceCode("");
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(10);
		houseRequest.setHouseStatusList(list);
		String resultJson = troyHouseMgtServiceProxy.searchUpateHouseMsgList(JsonEntityTransform.Object2Json(houseRequest));
		System.err.println(resultJson);
	}
	@Test
	public void searchHouseMsgListNewTest() throws SOAParseException {
		HouseRequestDto houseRequestDto=new HouseRequestDto();
		houseRequestDto.setPage(1);
		houseRequestDto.setLimit(10);
		houseRequestDto.setRentWay(1);
		houseRequestDto.setOrderType(1);
		String resultJson = troyHouseMgtServiceProxy.searchHouseMsgListNew(JsonEntityTransform.Object2Json(houseRequestDto));
		List<HouseResultNewVo> houseMsgList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HouseResultNewVo.class);
		System.err.println(resultJson);
	}
	
	@Test
	public void forceDownHouseTest() {
		String houseBaseFid = "8a9e9aae5419cc22015419cc250a0002";
		String operaterFid = "8a9e9aaf5383821f01538391fcbd0001";
		String remark = "备注";
		String resultJson = troyHouseMgtServiceProxy.forceDownHouse(houseBaseFid, operaterFid, remark);
		System.err.println(resultJson);
	}
	
	@Test
	public void forceDownRoomTest() {
		String houseRoomFid = "8a9e9aae53e97a9e0153e97a9ecb0000";
		String operaterFid = "8a9e9aaf5383821f01538391fcbd0001";
		String remark = "备注";
		String resultJson = troyHouseMgtServiceProxy.forceDownRoom(houseRoomFid, operaterFid, remark);
		System.err.println(resultJson);
	}
	
	@Test
	public void reIssueHouseTest() {
		String houseBaseFid = "8a9084df5506f4ce01550703deea004e";
		String operaterFid = "8a9e9aaf5383821f01538391fcbd0001";
		String remark = "备注";
		String resultJson = troyHouseMgtServiceProxy.reIssueHouse(houseBaseFid, operaterFid, remark);
		System.err.println(resultJson);
	}
	
	@Test
	public void reIssueRoomTest() {
		String houseRoomFid = "8a9e9aae53e97a9e0153e97a9ecb0000";
		String operaterFid = "8a9e9aaf5383821f01538391fcbd0001";
		String remark = "备注";
		String resultJson = troyHouseMgtServiceProxy.reIssueRoom(houseRoomFid, operaterFid, remark);
		System.err.println(resultJson);
	}
	
	@Test
	public void approveHouseInfoTest() {
		String houseBaseFid = "8a9e9a8b53d6089f0153d608a1f80002";
		String operaterFid = "8a9e9aaf5383821f01538391fcbd0001";
		String remark = "测试房源信息审核通过";
		String addtionalInfo = "";
		String resultJson = troyHouseMgtServiceProxy.approveHouseInfo(houseBaseFid, operaterFid, remark, addtionalInfo);
		System.err.println(resultJson);
	}
	
	@Test
	public void unApproveHouseInfoTest() {
		String houseBaseFid = "8a9e9a8b53d6089f0153d608a1f80002";
		String operaterFid = "8a9e9aaf5383821f01538391fcbd0001";
		String remark = "测试房源信息审核未通过";
		String addtionalInfo = "";
		String resultJson = troyHouseMgtServiceProxy.unApproveHouseInfo(houseBaseFid, operaterFid, remark, addtionalInfo);
		System.err.println(resultJson);
	}
	
	@Test
	public void approveRoomInfoTest() {
		String houseRoomFid = "8a9e9a9053e6f0070153e6f0091b0001";
		String operaterFid = "8a9e9aaf5383821f01538391fcbd0001";
		String remark = "测试房间信息审核通过";
		String addtionalInfo = "";
		String resultJson = troyHouseMgtServiceProxy.approveRoomInfo(houseRoomFid, operaterFid, remark, addtionalInfo);
		System.err.println(resultJson);
	}
	
	@Test
	public void unApproveRoomInfoTest() {
		String houseRoomFid = "8a9e9a9053e6f0070153e6f0091b0001";
		String operaterFid = "8a9e9aaf5383821f01538391fcbd0001";
		String remark = "测试房间信息审核未通过";
		String addtionalInfo = "";
		String resultJson = troyHouseMgtServiceProxy.unApproveRoomInfo(houseRoomFid, operaterFid, remark, addtionalInfo);
		System.err.println(resultJson);
	}
	
	@Test
	public void approveHousePicTest() {
		HouseQualityAuditDto auditDto = new HouseQualityAuditDto();
		auditDto.setHouseFid("8a9e9a9a54c2bf950154c322d4df0032");
		auditDto.setOperaterFid("8a9e9aaf5383821f01538391fcbd0001");
		auditDto.setRemark("测试房源照片审核通过");
		String resultJson = troyHouseMgtServiceProxy.approveHousePic(JsonEntityTransform.Object2Json(auditDto));
		System.err.println(resultJson);
	}
	
	@Test
	public void unApproveHousePicTest() {
		HouseQualityAuditDto auditDto = new HouseQualityAuditDto();
		auditDto.setHouseFid("8a9e9a8b53d6089f0153d608a1f80002");
		auditDto.setOperaterFid("8a9e9aaf5383821f01538391fcbd0001");
		auditDto.setRemark("测试房源照片审核未通过");
		String resultJson = troyHouseMgtServiceProxy.unApproveHousePic(JsonEntityTransform.Object2Json(auditDto));
		System.err.println(resultJson);
	}
	
	@Test
	public void approveRoomPicTest() {
		HouseQualityAuditDto auditDto = new HouseQualityAuditDto();
		auditDto.setHouseFid("8a9084df5dc60fa5015dc616c9460022");
		auditDto.setOperaterFid("8a9e9aaf537e3f7501537e3f75af0000");
		auditDto.setRemark("审核通过");
		String resultJson = troyHouseMgtServiceProxy.approveRoomPic(JsonEntityTransform.Object2Json(auditDto));
		System.err.println(resultJson);
	}
	
	@Test
	public void unApproveRoomPicTest() {
		HouseQualityAuditDto auditDto = new HouseQualityAuditDto();
		auditDto.setHouseFid("8a9e9a9053e6f0070153e6f0091b0001");
		auditDto.setOperaterFid("8a9e9aaf5383821f01538391fcbd0001");
		auditDto.setRemark("测试房间照片审核未通过");
		String resultJson = troyHouseMgtServiceProxy.unApproveRoomPic(JsonEntityTransform.Object2Json(auditDto));
		System.err.println(resultJson);
	}
	
	@Test
	public void searchHouseDetailByFidTest(){
		String houseBaseFid = "8a9084df55574216015557c8c9140005";
		String resultJson = troyHouseMgtServiceProxy.searchHouseDetailByFid(houseBaseFid);
		System.err.println(resultJson);
	}
	
	@Test
	public void searchRoomDetailByFidTest(){
		String houseRoomFid = "8a9084df54c541c40154c549c1f0005e";
		String resultJson = troyHouseMgtServiceProxy.searchRoomDetailByFid(houseRoomFid);
		System.err.println(resultJson);
	}
	
	@Test
	public void searchHouseOperateLogListTest(){
		List<Integer> fromList = new ArrayList<Integer>();
		fromList.add(HouseStatusEnum.YFB.getCode());
		fromList.add(HouseStatusEnum.XXSHWTG.getCode());
		fromList.add(HouseStatusEnum.XXSHTG.getCode());
		fromList.add(HouseStatusEnum.ZPSHWTG.getCode());
		fromList.add(HouseStatusEnum.XJ.getCode());
		fromList.add(HouseStatusEnum.SJ.getCode());
		
		HouseOpLogDto houseOpLogDto = new HouseOpLogDto();
		houseOpLogDto.setFromList(fromList);
		houseOpLogDto.setHouseFid("8a9e9a8b53d6089f0153d608a1f80002");
		houseOpLogDto.setRentWay(0);
		
		String resultJson = troyHouseMgtServiceProxy.searchHouseOperateLogList(JsonEntityTransform
				.Object2Json(houseOpLogDto));
		System.err.println(resultJson);
	}
	
	@Test
	public void searchRoomOperateLogListTest(){
		List<Integer> fromList = new ArrayList<Integer>();
		fromList.add(HouseStatusEnum.YFB.getCode());
		fromList.add(HouseStatusEnum.XXSHWTG.getCode());
		fromList.add(HouseStatusEnum.XXSHTG.getCode());
		fromList.add(HouseStatusEnum.ZPSHWTG.getCode());
		fromList.add(HouseStatusEnum.XJ.getCode());
		fromList.add(HouseStatusEnum.SJ.getCode());
		
		HouseOpLogDto houseOpLogDto = new HouseOpLogDto();
		houseOpLogDto.setFromList(fromList);
		houseOpLogDto.setHouseFid("8a9e9a8b53d62d740153d62d76730002");
		houseOpLogDto.setRentWay(1);
		
		String resultJson = troyHouseMgtServiceProxy.searchHouseOperateLogList(JsonEntityTransform
				.Object2Json(houseOpLogDto));
		System.err.println(resultJson);
	}
	
	@Test
	public void searchHousePhyMsgListTest(){
		HousePhyMsgEntity housePhyMsgEntity=new HousePhyMsgEntity();
		housePhyMsgEntity.setCommunityName("测试小区");
		String resultJson=troyHouseMgtServiceProxy.searchHousePhyMsgList(JsonEntityTransform.Object2Json(housePhyMsgEntity));
		System.err.println(resultJson);
	}
	
	@Test
	public void updateHouseBasePhyFidTest(){
		String resultJson=troyHouseMgtServiceProxy.updateHouseBasePhyFid("8a9e9a8b53d6089f0153d608a1540001", "8a9e9a8b53d6da8b0153d6da8da70001");
		System.err.println(resultJson);
	}
	
	@Test
	public void updateHousePhyMsgTest(){
		HousePhyMsgEntity housePhyMsgEntity=new HousePhyMsgEntity();
		housePhyMsgEntity.setFid("8a9e9a8b53d6089f0153d608a1540001");
		housePhyMsgEntity.setZoName("卜书杰");
		String resultJson=troyHouseMgtServiceProxy.updateHousePhyMsg(JsonEntityTransform.Object2Json(housePhyMsgEntity));
		System.err.println(resultJson);
	}
	
	@Test
	public void findHouseConfListByFidAndCodeTest(){
		String houseBaseFid = "8a9e9a8b53d6089f0153d608a1f80002";
		String enumCode = "测试code";
		String resultJson = troyHouseMgtServiceProxy.searchHouseConfListByFidAndCode(houseBaseFid, enumCode);
		System.err.println(resultJson);
	}
	
	@Test
	public void housePicAuditListTest() throws SOAParseException {
		HousePicDto housePicDto=new HousePicDto();
		housePicDto.setHouseBaseFid("8a9084df5fe3025b015fe3ce10d10345");
		//housePicDto.setHouseRoomFid("8a9e9aae53e97a9e0153e97a9ecb0000");
		String housePicAuditVoJson=troyHouseMgtServiceProxy.housePicAuditList(JsonEntityTransform.Object2Json(housePicDto));
		System.err.println(housePicAuditVoJson);


		//HousePicAuditVo housePicAuditVo =SOAResParseUtil.getValueFromDataByKey(housePicAuditVoJson,"housePicAuditVo",HousePicAuditVo.class);


	}
	
	@Test
	public void searchPicUnapproveedHouseListTest(){
		/*HouseRequestDto houseRequest = new HouseRequestDto();
		houseRequest.setRentWay(0);
		String resultJson = troyHouseMgtServiceProxy.searchPicUnapproveedHouseList(JsonEntityTransform
				.Object2Json(houseRequest));*/
		String resultJson = troyHouseMgtServiceProxy.searchPicUnapproveedHouseList("{\"page\":1,\"limit\":10,\"landlordUidList\":null,\"landlordName\":\"\",\"landlordMobile\":\"\",\"isPic\":null,\"cameramanName\":\"\",\"cameramanMobile\":\"\",\"houseName\":\"\",\"houseStatus\":null,\"houseStatusList\":[40],\"nationCode\":null,\"provinceCode\":null,\"cityCode\":null,\"rentWay\":1,\"houseType\":null,\"zoName\":null,\"isWeight\":null,\"landlordNameS\":null,\"landlordMobileS\":null,\"isPicS\":null,\"cameramanNameS\":null,\"cameramanMobileS\":null,\"houseNameS\":null,\"houseStatusS\":null,\"provinceCodeS\":null,\"nationCodeS\":null,\"cityCodeS\":null,\"houseTypeS\":null,\"zoNameS\":null,\"isWeightS\":null}");
		System.err.println(resultJson);
	}
	
	@Test
	public void searchPicUnapproveedRoomListTest(){
		/*HouseRequestDto houseRequest = new HouseRequestDto();
		houseRequest.setRentWay(1);
		String resultJson = troyHouseMgtServiceProxy.searchPicUnapproveedHouseList(JsonEntityTransform
				.Object2Json(houseRequest));*/
		String resultJson = troyHouseMgtServiceProxy.searchPicUnapproveedHouseList("{\"page\":1,\"limit\":10,\"landlordUidList\":null,\"landlordName\":\"\",\"landlordMobile\":\"\",\"isPic\":null,\"cameramanName\":\"\",\"cameramanMobile\":\"\",\"houseName\":\"\",\"houseStatus\":null,\"houseStatusList\":[40],\"nationCode\":null,\"provinceCode\":null,\"cityCode\":null,\"rentWay\":1,\"houseType\":null,\"zoName\":null,\"isWeight\":null,\"landlordNameS\":\"\",\"landlordMobileS\":\"\",\"isPicS\":null,\"cameramanNameS\":\"\",\"cameramanMobileS\":\"\",\"houseNameS\":\"\",\"houseStatusS\":null,\"provinceCodeS\":null,\"nationCodeS\":null,\"cityCodeS\":null,\"houseTypeS\":null,\"zoNameS\":null,\"isWeightS\":null,\"houseCreateTimeStart\":null,\"houseCreateTimeEnd\":null,\"roomCreateTimeStart\":\"\",\"roomCreateTimeEnd\":\"\"}");
		System.err.println(resultJson);
	}
	
	@Test
	public void findHousePicCountByTypeTest(){
		HousePicDto housePicDto=new HousePicDto();
		housePicDto.setHouseBaseFid("8a9e9a8b53d6da8b0153d6da8e4b0002");
		housePicDto.setHouseRoomFid("8a9e9a9453fb3cb00153fb3cb2270001");
		housePicDto.setPicType(0);
		String resultJson=troyHouseMgtServiceProxy.findHousePicCountByType(JsonEntityTransform.Object2Json(housePicDto));
		System.err.println(resultJson);
	}
	
	@Test
	public void batchEditHouseWeightTest(){
		HouseWeightDto dto = new HouseWeightDto();
		dto.setRentWay(0);
		dto.setWeight(11);
		
		List<String> list = new ArrayList<String>();
		list.add("8a9e9a9453f95bf40153f95bf70c0001");
		list.add("8a9e9a8b53d6da8b0153d6da8e4b0002");
		dto.setHouseFidList(list);


		String resultJson=troyHouseMgtServiceProxy.batchEditHouseWeight(JsonEntityTransform.Object2Json(dto));
		System.err.println(resultJson);
	}
	
	@Test
	public void houseInputTest(){
		String resultJson=troyHouseMgtServiceProxy.houseInput("{\"houseBase\":{\"id\":null,\"fid\":null,\"phyFid\":null,\"houseName\":\"测试服务项\",\"rentWay\":0,\"houseType\":1,\"houseStatus\":null,\"houseWeight\":null,\"landlordUid\":\"\",\"leasePrice\":1,\"houseArea\":1.0,\"roomNum\":1,\"hallNum\":1,\"toiletNum\":1,\"kitchenNum\":1,\"balconyNum\":1,\"cameramanName\":null,\"cameramanMobile\":null,\"operateSeq\":null,\"intactRate\":null,\"refreshDate\":null,\"tillDate\":null,\"createDate\":null,\"lastModifyDate\":null,\"isDel\":null,\"houseAddr\":\"中国北京北京市东城区11 1号楼1单元1层1号\",\"isPic\":null,\"houseSource\":null},\"houseExt\":{\"id\":null,\"fid\":null,\"houseBaseFid\":null,\"buildingNum\":\"1\",\"unitNum\":\"1\",\"floorNum\":\"1\",\"houseNum\":\"1\",\"houseStreet\":\"1\",\"facilityCode\":null,\"serviceCode\":null,\"orderType\":1,\"homestayType\":1,\"minDay\":1,\"discountRulesCode\":null,\"depositRulesCode\":\"ProductRulesEnum008002\",\"checkOutRulesCode\":\"TradeRulesEnum005001\",\"checkInLimit\":0,\"checkInTime\":\"14:00:00\",\"checkOutTime\":\"12:00:00\",\"sheetsReplaceRules\":0,\"fullDiscount\":null,\"isTogetherLandlord\":0},\"houseDesc\":{\"id\":null,\"fid\":null,\"houseBaseFid\":null,\"createDate\":null,\"lastModifyDate\":null,\"createUid\":null,\"isDel\":null,\"houseDesc\":\"1\",\"houseAroundDesc\":\"1\"},\"housePhy\":{\"id\":null,\"fid\":null,\"nationCode\":\"100000\",\"provinceCode\":\"110000\",\"cityCode\":\"110100\",\"areaCode\":\"110101\",\"buildingCode\":null,\"communityName\":\"1\",\"zoJobNum\":null,\"zoName\":null,\"zoMobile\":null,\"longitude\":1.0,\"latitude\":1.0,\"createDate\":null,\"lastModifyDate\":null,\"createUid\":null,\"isDel\":null},\"facilityList\":[\"ProductRulesEnum002001-1\",\"ProductRulesEnum002002-1\",\"ProductRulesEnum002003-1\"],\"discountList\":[\"ProductRulesEnum0012001-0.01\",\"ProductRulesEnum0012002-0.05\",\"ProductRulesEnum0012003-0.08\"],\"depositList\":[\"ProductRulesEnum008001-1\",\"ProductRulesEnum008002-0\"],\"roomName\":[],\"roomArea\":[],\"roomPrice\":[],\"roomLimit\":[],\"isToilet\":[],\"bedType\":[],\"bedSize\":[],\"tillDate\":\"2016-06-07\",\"unregPolicy\":[],\"serviceList\":[\"1\",\"2\"]}");
		System.err.println(resultJson);
	}
	
	@Test
	public void findNoAuditHousePicListTest(){
		HouseBaseDto houseBaseDto=new HouseBaseDto();
		houseBaseDto.setHouseFid("8a9084df5501fffe0155021ab52d0035");
		houseBaseDto.setRentWay(0);
		String resultJson=troyHouseMgtServiceProxy.findNoAuditHousePicList(JsonEntityTransform.Object2Json(houseBaseDto));
		System.err.println(resultJson);
	}
	
	@Test
	public void upHouseMsgTest(){
		String string="{\"houseBase\":{\"id\":null,\"fid\":\"8a9084df574ad58701574aefd91c007a\",\"phyFid\":null,\"houseName\":\"阿萨德发射的\",\"rentWay\":null,\"houseType\":null,\"houseStatus\":null,\"houseWeight\":null,\"landlordUid\":null,\"leasePrice\":null,\"houseArea\":null,\"roomNum\":null,\"hallNum\":null,\"toiletNum\":null,\"kitchenNum\":null,\"balconyNum\":null,\"cameramanName\":null,\"cameramanMobile\":null,\"operateSeq\":null,\"intactRate\":null,\"refreshDate\":null,\"tillDate\":null,\"createDate\":null,\"lastModifyDate\":null,\"isDel\":null,\"houseAddr\":\"北京市西城区搜搜搜的发生的发生的斯蒂芬斯蒂芬爱施德士大夫撒旦法斯蒂芬 1号楼1单元1层1号\",\"isPic\":null,\"isLock\":null,\"houseSource\":null,\"houseSn\":null,\"houseCleaningFees\":null,\"houseChannel\":0,\"oldStatus\":null},\"houseExt\":{\"id\":null,\"fid\":\"8a9084df574ad58701574aefd925007c\",\"houseBaseFid\":null,\"buildingNum\":\"1\",\"unitNum\":\"1\",\"floorNum\":\"1\",\"houseNum\":\"1\",\"houseStreet\":null,\"facilityCode\":null,\"serviceCode\":null,\"orderType\":null,\"homestayType\":null,\"minDay\":null,\"discountRulesCode\":null,\"depositRulesCode\":null,\"checkOutRulesCode\":null,\"checkInLimit\":null,\"checkInTime\":null,\"checkOutTime\":null,\"sheetsReplaceRules\":null,\"fullDiscount\":null,\"isTogetherLandlord\":null,\"defaultPicFid\":null,\"oldDefaultPicFid\":null,\"detailAddress\":null,\"isLandlordPic\":null},\"houseDesc\":{\"id\":null,\"fid\":\"8a9084df574ad58701574aefd923007b\",\"houseBaseFid\":null,\"createDate\":null,\"lastModifyDate\":null,\"createUid\":null,\"isDel\":null,\"houseDesc\":\"岁到发射点发生的艾丝凡是岁到发射点发生的艾丝凡是岁到发射点发生的艾丝凡是岁到发射点发生的艾丝凡是岁到发射点发生的艾丝凡是岁到发射点发生的艾丝凡是岁到发射点发生的艾丝凡是岁到发射点发生的艾丝凡是岁到发射点发生的艾丝凡是岁到发射点发生的艾丝凡是\",\"houseAroundDesc\":\"岁到发射点发生的艾丝凡是岁到发射点发生的艾丝凡是岁到发射点发生的艾丝凡是岁到发射点发生的艾丝凡是岁到发射点发生的艾丝凡是岁到发射点发生的艾丝凡是岁到发射点发生的艾丝凡是岁到发射点发生的艾丝凡是岁到发射点发生的艾丝凡是岁到发射点发生的艾丝凡是岁到发射点发生的艾丝凡是岁到发射点发生的艾丝凡是岁到发射点发生的艾丝凡是岁到发射点发生的艾丝凡是岁到发射点发生的艾丝凡是\",\"houseRules\":null,\"addtionalInfo\":null},\"housePhy\":{\"id\":null,\"fid\":\"8a9084df574ad58701574aefd9180079\",\"nationCode\":null,\"provinceCode\":null,\"cityCode\":null,\"areaCode\":null,\"buildingCode\":null,\"communityName\":\"士大夫撒旦法斯蒂芬\",\"zoJobNum\":null,\"zoName\":null,\"zoMobile\":null,\"longitude\":null,\"latitude\":null,\"createDate\":null,\"lastModifyDate\":null,\"createUid\":null,\"isDel\":null},\"facilityList\":[],\"discountList\":[],\"depositList\":[],\"roomName\":[\"sss\"],\"roomNameNick\":null,\"roomArea\":[],\"roomPrice\":[],\"roomFid\":[\"8a9084df574ad58701574aefd92e0081\"],\"roomLimit\":[],\"isToilet\":[],\"bedType\":[],\"bedSize\":[],\"tillDate\":null,\"unregPolicy\":[],\"serviceList\":[],\"houseGuardRel\":null}";
		String resultJson=troyHouseMgtServiceProxy.upHouseMsg(string);
		System.err.println(resultJson);
	}
	

	@Test
	public void testgetHoseFidListForIMFollow(){
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("houseName", "虾米");
		String houseListForIMFollow = troyHouseMgtServiceProxy.getHoseFidListForIMFollow(JsonEntityTransform.Object2Json(map));
		System.out.println(houseListForIMFollow);
	}
		
	@Test
	public void testgetRoomFidListForIMFollow(){
		Map<String, Object> map = new HashMap<String, Object>(); 
	/*	map.put("rentWay", 1);
		map.put("nationCode", "100000");
		map.put("cityCode", "110100");*/
		map.put("roomName", "合租普通");
		String houseListForIMFollow = troyHouseMgtServiceProxy.getRoomFidListForIMFollow(JsonEntityTransform.Object2Json(map));
		System.out.println(houseListForIMFollow);
	}
	
	
	@Test
	public void testgetHouseInfoForImFollow(){
		Map<String, Object> map = new HashMap<String, Object>(); 
		//map.put("houseFid", "8a9e9aae5419d73b015419d73ddb0001");
		map.put("roomFid", "8a9e9a94547fadb601547fadb76e0010");
		String houseListForIMFollow = troyHouseMgtServiceProxy.getRoomInfoForImFollow(JsonEntityTransform.Object2Json(map));
		System.out.println(houseListForIMFollow);
	}
	
	@Test
	public void testapproveGroundingHouseInfo(){
		HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlogEntity = new HouseUpdateFieldAuditNewlogEntity();
		houseUpdateFieldAuditNewlogEntity.setHouseFid("8a9098fb5d55a40c015d5890eea3015f");
		houseUpdateFieldAuditNewlogEntity.setRentWay(0);
		HouseUpdateFieldAuditDto houseUpdateFieldAuditDto = new HouseUpdateFieldAuditDto();
		houseUpdateFieldAuditDto.setHouseUpdateFieldAuditNewlog(houseUpdateFieldAuditNewlogEntity);
		houseUpdateFieldAuditDto.setPicFids(null);
		String houseListForIMFollow = troyHouseMgtServiceProxy.approveGroundingHouseInfo(JsonEntityTransform.Object2Json(houseUpdateFieldAuditDto));
		System.out.println(houseListForIMFollow);
	}
	
//	@Test
//	public void testHouseIssue(){
//
//		ResponseDto dto=new ResponseDto();
//		dto.setStatus("0");
//		//发布房源第二步2-1
//		HouseDescVo houseDescVo=new HouseDescVo();
//		houseDescVo.setHouseName(new FieldTextVo<String>(true, "后海胡同独栋复式观景露台雅致美屋"));
//		houseDescVo.setHouseDesc(new FieldTextVo<String>(true, "我们家是独栋复式3层的胡同，一层是主卧室，房间较大，二层有两间客房，厨房在一楼，卫生间一楼和二楼各有1个"));
//		houseDescVo.setHouseAreaDesc(new FieldTextVo<String>(true, "房子后面就是后海，走5分钟就到恭王府，鼓楼，步行10分钟就到酒吧一条街，开车10分钟到故宫，15分钟到天安门，20分钟到簋"));
//		
//		dto.setData(houseDescVo);
//		System.err.println(JsonEntityTransform.Object2Json(dto));
//		
//		//发布房源第二步2-2
//		HousePriceVo housePriceVo=new HousePriceVo();
//		housePriceVo.setHouseArea(new FieldTextVo<Double>(true, 123.4));
//		housePriceVo.setHouseFacility(new FieldTextValueVo<String>("ProductRulesEnum002001:2,ProductRulesEnum002001:4,ProductRulesEnum002001:5,ProductRulesEnum002001:7", "电视、空调、无线网络、电吹", true));
//		housePriceVo.setHousePriceDetail(new FieldTextValueVo<String>("370,0:600,0.9,0.8,12,", "基础价格：370/晚,空调、周五、周六价格：600/晚,满7天折扣：9折,满30天折扣：8折,清洁费：12元", true));
//		housePriceVo.getCheckInLimit().getList().add(new FieldSelectVo<Integer>(0, "不限制", "", false));
//		housePriceVo.getCheckInLimit().getList().add(new FieldSelectVo<Integer>(1, "1人", "", false));
//		housePriceVo.getCheckInLimit().getList().add(new FieldSelectVo<Integer>(2, "2人", "", false));
//		housePriceVo.getCheckInLimit().getList().add(new FieldSelectVo<Integer>(3, "3人", "", true));
//		housePriceVo.getCheckInLimit().getList().add(new FieldSelectVo<Integer>(4, "4人", "", false));
//		housePriceVo.getCheckInLimit().getList().add(new FieldSelectVo<Integer>(5, "5人", "", false));
//		housePriceVo.getCheckInLimit().setIsEdit(true);
//		housePriceVo.setHouseModel(new FieldTextValueVo<String>("2,1,1,1", "2室 1厅 1卫 1厨 1阳台", true));
//		
//		housePriceVo.getHouseRoomList().getList().add(new FieldSelectVo<String>("8a90a3575c20d950015c340f34420212", "卧室1", "2:1,1:2", false));
//		housePriceVo.getHouseRoomList().getList().add(new FieldSelectVo<String>("8a90a3575c87e6b5015c95f575e100ed", "卧室2", "2:1,1:2", false));
//		housePriceVo.getBedList().getList().add(new FieldSelectVo<Integer>(1, "双人大床", "宽1.8米以上", false));
//		housePriceVo.getBedList().getList().add(new FieldSelectVo<Integer>(1, "双人床", "宽1.8米", false));
//		housePriceVo.getBedList().getList().add(new FieldSelectVo<Integer>(1, "单人床", "宽1.5米以下", false));
//		housePriceVo.getBedList().setIsEdit(true);
//		housePriceVo.setMaxRoom(30);
//		housePriceVo.setMaxParlor(20);
//		housePriceVo.setMaxToilet(20);
//		housePriceVo.setMaxKitchen(20);
//		housePriceVo.setMaxBalcony(20);
//		
//		dto.setData(housePriceVo);
//		System.err.println(JsonEntityTransform.Object2Json(dto));
//		
//		//发布房源第一步1-1和1-2
//		HouseTypeLocationVo houseTypeLocationVo=new HouseTypeLocationVo();
//		houseTypeLocationVo.getHouseRentWay().setIsEdit(true);
//		houseTypeLocationVo.getHouseRentWay().getList().add(new FieldSelectVo<Integer>(0, "整套房子", "用户可以预订整个空间，包括客厅、厨房等公用空间。", true));
//		houseTypeLocationVo.getHouseRentWay().getList().add(new FieldSelectVo<Integer>(1, "单个房间", "用户可以预订一个独立房间，同时和其他客人分享客厅、厨房等公用空间。", false));
//		
//		houseTypeLocationVo.getHouseType().setIsEdit(true);
//		houseTypeLocationVo.getHouseType().getList().add(new FieldSelectVo<Integer>(1, "老洋房", "", false));
//		houseTypeLocationVo.getHouseType().getList().add(new FieldSelectVo<Integer>(2, "四合院", "", false));
//		houseTypeLocationVo.getHouseType().getList().add(new FieldSelectVo<Integer>(3, "公寓", "", false));
//		houseTypeLocationVo.getHouseType().getList().add(new FieldSelectVo<Integer>(4, "别墅", "", false));
//		houseTypeLocationVo.getHouseType().getList().add(new FieldSelectVo<Integer>(5, "民宅", "", false));
//		houseTypeLocationVo.getHouseType().getList().add(new FieldSelectVo<Integer>(6, "复式", "", false));
//		houseTypeLocationVo.setRegionMsg(new FieldTextValueVo<String>("100000,110000,110100,110105", "中国,北京市,北京,朝阳区", true));
//		houseTypeLocationVo.setHouseStreet(new FieldTextVo<String>(true, "朝阳路"));
//		houseTypeLocationVo.setCommunityName(new FieldTextVo<String>(true, "左家庄东里"));
//		houseTypeLocationVo.setHouseNumber(new FieldTextVo<String>(true, "67号楼独栋单元4层402号"));
//		//houseTypeLocationVo.setLatitude(new FieldTextVo<Double>(true, 31.243143));
//		//houseTypeLocationVo.setLongitude(new FieldTextVo<Double>(true, 121.456390));
//		dto.setData(houseTypeLocationVo);
//		System.err.println(JsonEntityTransform.Object2Json(dto));
//		
//		List<HouseRoomVo1> roomList=new ArrayList<HouseRoomVo1>();
//		HouseRoomVo1 vo=new HouseRoomVo1();
//		vo.setBedMsg("2:1,1:2");
//		roomList.add(vo);
//		HouseRoomVo1 vo1=new HouseRoomVo1();
//		vo1.setBedMsg("2:1,1:2");
//		roomList.add(vo1);
//		System.err.println(JsonEntityTransform.Object2Json(roomList));
//	}
	
}

