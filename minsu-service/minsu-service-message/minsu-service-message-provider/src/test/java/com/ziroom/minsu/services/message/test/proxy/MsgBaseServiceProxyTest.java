/**
 * @FileName: MsgBaseServiceProxyTest.java
 * @Package com.ziroom.minsu.services.message.test.proxy
 * 
 * @author yd
 * @created 2016年4月18日 下午9:00:04
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.proxy;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.DateUtil.IntervalUnit;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.message.MsgBaseEntity;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.message.dto.MsgBaseRequest;
import com.ziroom.minsu.services.message.dto.MsgBookAdviceRequest;
import com.ziroom.minsu.services.message.dto.MsgHouseRequest;
import com.ziroom.minsu.services.message.dto.MsgStaticsRequest;
import com.ziroom.minsu.services.message.dto.MsgSyncRequest;
import com.ziroom.minsu.services.message.dto.PeriodHuanxinRecordRequest;
import com.ziroom.minsu.services.message.proxy.MsgBaseServiceProxy;
import com.ziroom.minsu.services.message.test.base.BaseTest;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.msg.IsReadEnum;

import org.codehaus.jackson.type.TypeReference;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>基本消息代理层测试</p>
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
public class MsgBaseServiceProxyTest extends BaseTest{
    @Resource(name = "message.msgBaseServiceProxy")
	private MsgBaseServiceProxy msgBaseServiceProxy;


	@Test
	public void queryAllMsgByConditionTest() {
		MsgBaseRequest msgBaseRequest = new MsgBaseRequest();
		msgBaseRequest.setMsgHouseFid("8a90a5d65557cc17015557cc17f10000");
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(msgBaseServiceProxy.queryAllMsgByCondition(JsonEntityTransform.Object2Json(msgBaseRequest)));
		List<MsgBaseEntity> listMsgBase =  dto.parseData("listMsgBase",new TypeReference<List<MsgBaseEntity>>() {
		});
		System.out.println(listMsgBase);
	}
	@Test
	public void saveTest() {

		MsgBaseEntity msgBaseEntity = new MsgBaseEntity();

		msgBaseEntity.setCreateTime(new Date());
		msgBaseEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
		msgBaseEntity.setIsRead(IsReadEnum.READ.getCode());
		msgBaseEntity.setMsgContent("fd4s65f46");
		msgBaseEntity.setMsgHouseFid("f4d5s6fdsfdfdfsfdsff4d56");
		msgBaseEntity.setMsgSenderType(UserTypeEnum.TENANT.getUserType());
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(msgBaseServiceProxy.save(JsonEntityTransform.Object2Json(msgBaseEntity)));

		System.out.println(dto.getData().get("result"));
	}

	@Test
	public void updateByMsgHouseFidTest(){
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(msgBaseServiceProxy.updateByMsgHouseFid(JsonEntityTransform.Object2Json("8a9e9c8b541ebdd101541ebdd1fa0000")));

		System.out.println(dto.getData().get("result"));

	}

	@Test
	public void staticsCountLanImReplyNumTest(){
		MsgStaticsRequest request = new MsgStaticsRequest();
		request.setLimitTime(DateUtil.intervalDate(-50, IntervalUnit.DAY));
		request.setSumTime(30*60*100000);
		request.setLandlordUid("690af53e-5938-4db5-a023-7d1ac905b526");
		System.out.println(msgBaseServiceProxy.staticsCountLanImReplyNum(JsonEntityTransform.Object2Json(request)));
    }


	@Test
	public void staticsCountLanImReplySumTimeTest(){
		MsgStaticsRequest request = new MsgStaticsRequest();
		request.setLimitTime(DateUtil.intervalDate(-50, IntervalUnit.DAY));
		request.setSumTime(30*60*1000);
		request.setLandlordUid("8a9e9a9f544b372101544b3721de0000");
		System.out.println(msgBaseServiceProxy.staticsCountLanImReplySumTime(JsonEntityTransform.Object2Json(request)));
    }
	@Test
	public void getNotReplyListTest(){

		String notReplyList = msgBaseServiceProxy.getNotReplyList(JsonEntityTransform.Object2Json(new PageRequest()));
		System.err.println(notReplyList);

	}


	@Test
	public void updateByConditionTest(){

		MsgBookAdviceRequest msgBookRequest = new MsgBookAdviceRequest();

		msgBookRequest.setEndTime("2018-05-02");
		msgBookRequest.setMsgHouseFid("8a9e9cd9556816d201556816d24f0000");
		msgBookRequest.setStartTime("2016-09-02");
		msgBookRequest.setPeopleNum(3);
		msgBookRequest.setTripPurpose("出去玩玩");

		MsgBaseEntity msgBaseEntity = new MsgBaseEntity();
		msgBaseEntity.setMsgContent(JsonEntityTransform.Object2Json(msgBookRequest));
		msgBaseEntity.setMsgHouseFid(msgBookRequest.getMsgHouseFid());
		msgBaseEntity.setMsgSenderType(UserTypeEnum.All.getUserType());
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(msgBaseServiceProxy.updateByCondition(JsonEntityTransform.Object2Json(msgBaseEntity)));
		System.out.println(dto);
	}

	@Test
	public void queryTwoChatRecordTest(){

		MsgHouseRequest msgHouseRe  = new MsgHouseRequest();

		msgHouseRe.setLandlordUid("7a8c4184-8e2e-37b4-08e8-f4c20225e350");
		msgHouseRe.setTenantUid("7a8c4184-8e2e-37b4-08e8-f4c20225e350");

		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(
				this.msgBaseServiceProxy.queryTwoChatRecord(JsonEntityTransform.Object2Json(msgHouseRe)));

		System.out.println(dto);
	}

	@Test
	public void queryOneChatRecordTest(){

		MsgHouseRequest msgHouseRe  = new MsgHouseRequest();

		msgHouseRe.setLandlordUid("9dd9f42a-d23b-4a2e-81ee-1e20a4362052");
		msgHouseRe.setTenantUid("2e62e9ce-51a8-4ae5-9a51-1b3d4c6949df");

		/*msgHouseRe.setLandlordUid("723e8dad-caa8-458a-aff9-d9b513934470");
		msgHouseRe.setTenantUid("5b5b53a0-ccf2-19f4-e5b1-20abc508c963");*/
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(
				this.msgBaseServiceProxy.queryOneChatRecord(JsonEntityTransform.Object2Json(msgHouseRe)));

		System.out.println(dto);
	}
	

	@Test
	public void findIMChatRecordTest(){

		MsgHouseRequest msgHouseRe  = new MsgHouseRequest();

		msgHouseRe.setLandlordUid("9dd9f42a-d23b-4a2e-81ee-1e20a4362052");
		msgHouseRe.setTenantUid("2e62e9ce-51a8-4ae5-9a51-1b3d4c6949df");

		/*msgHouseRe.setLandlordUid("a06f82a2-423a-e4e3-4ea8-e98317540190");
		msgHouseRe.setTenantUid("d249c630-20d1-513b-a2ab-ed06feae595b");*/
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(
				this.msgBaseServiceProxy.findIMChatRecord(JsonEntityTransform.Object2Json(msgHouseRe)));

		System.out.println(dto);
	}

	@Test
	public void listImMsgSyncListTest() throws ParseException{
	long start = System.currentTimeMillis();
		MsgSyncRequest msgSyncRequest = new MsgSyncRequest();
		msgSyncRequest.setPage(1);
		msgSyncRequest.setLimit(500);
		msgSyncRequest.setUid("5f4f193b-07fd-a708-85f8-22907004fd6d");
		String dateStr = DateUtil.dateFormat(new Date(), "yyyy-MM-dd");
		Date date = DateUtil.parseDate(dateStr, "yyyy-MM-dd");
		Date time = DateUtil.getTime(date, -360);
		msgSyncRequest.setTillDate(time);
		String s = msgBaseServiceProxy.listImMsgSyncList(JsonEntityTransform.Object2Json(msgSyncRequest));
		long end = System.currentTimeMillis();
		System.out.println(end-start);
		System.err.println(s);
	}

	@Test
	public void listChatOnAdvisory() throws Exception {
		String msgBaseFid = "8a90a366609c55b30160a03148f207d9";
		String json = msgBaseServiceProxy.listChatOnAdvisory(msgBaseFid);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(json);
		Assert.assertTrue(dto.getCode() == DataTransferObject.SUCCESS);
		System.out.println(dto);
	}
	
	@Test
	public void queryUserChatInTwentyFourTest() throws ParseException{
	/*	long formatDateToLong = DateUtil.formatDateToLong("2017-09-06 15:09:56");
		System.out.println(formatDateToLong);
		Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-09-06 15:09:56");
		long time1 = parse.getTime();
		long lTime = parse.getTime() / 1000;
		List<String> uidList = new ArrayList<String>();
		uidList.add("a06f82a2-423a-e4e3-4ea8-e98317540190");
		uidList.add("8a9e9a9f544b372101544b3721de0000");
		uidList.add("8a9e9a9e544b614001544b6140a70000");
		uidList.add("8a9e9a8b53d6da8b0153d6da8bae0000");
		uidList.add("7bbbf57f-6228-5e92-91dc-c9688d4398ce");
		uidList.add("f8a4375c-7437-b078-6f4b-5e2b7d6dfcb6");
		JSONObject aJsonObject = new JSONObject();
		aJsonObject.put("uidList", uidList);*/
		PeriodHuanxinRecordRequest msgSyncRequest = new PeriodHuanxinRecordRequest();
		msgSyncRequest.setPage(1);
		msgSyncRequest.setLimit(500);
		msgSyncRequest.setUid("e1751628-5a0f-4756-a9fc-c000343a5976");
		msgSyncRequest.setFromUid("7bd60b2b-99af-49ed-997a-d28f67fea9bf");
		msgSyncRequest.setToUid("adee1bf9-c3d8-4a95-84a1-5c922f8f155a");
		msgSyncRequest.setZiroomFlag("ZIROOM_CHANGZU_IM");
		msgSyncRequest.setTillDate(new Date());
		Date time = DateUtil.getTime(new Date(), -1);
		msgSyncRequest.setBeginDate(time);
		String s = msgBaseServiceProxy.queryUserChatInTwentyFour(JsonEntityTransform.Object2Json(msgSyncRequest));
		System.err.println(s);
	}
}
