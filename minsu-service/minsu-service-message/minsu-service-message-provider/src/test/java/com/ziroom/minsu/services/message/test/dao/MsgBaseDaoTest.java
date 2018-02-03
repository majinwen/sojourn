/**
 * @FileName: MsgBaseDaoTest.java
 * @Package com.ziroom.minsu.services.message.test.dao
 * 
 * @author yd
 * @created 2016年4月16日 下午5:14:27
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.ziroom.minsu.services.message.dto.*;
import com.ziroom.minsu.services.message.entity.ImMsgSyncVo;
import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.message.MsgBaseEntity;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.message.dao.MsgBaseDao;
import com.ziroom.minsu.services.message.entity.ImMsgListVo;
import com.ziroom.minsu.services.message.entity.MsgNotReplyVo;
import com.ziroom.minsu.services.message.test.base.BaseTest;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.msg.IsReadEnum;

/**
 * <p>消息基本体测试dao</p>
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
public class MsgBaseDaoTest extends BaseTest{



	@Resource(name = "message.msgBaseDao")
	private  MsgBaseDao msgBaseDao;	
	@Test
	public void saveTest() {

		int index =  0;
		int j = 1;
		for (int i = 0; i < 1; i++) {
			MsgBaseEntity msgBaseEntity = new MsgBaseEntity();

			msgBaseEntity.setCreateTime(new Date());
			msgBaseEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
			msgBaseEntity.setIsRead(IsReadEnum.READ.getCode());
			if(i<200){
				msgBaseEntity.setMsgSenderType(3);
				msgBaseEntity.setMsgContent("{\"msgHouseFid\":\"8a9e9c8b568c392f01568c392f580000\",\"startTime\":\"2016-08-15 00:00:00\",\"endTime\":\"2016-08-19 23:59:59\",\"peopleNum\":4,\"tripPurpose\":\"哈哈"+i+"\",\"createTime\":\"2016-08-15 11:45:48\",\"houseName\":\"鱼儿的普通房2\",\"housePicUrl\":\"http://10.16.34.44:8000/minsu/group1/M00/00/2C/ChAiMFc0NSmABx7TAABPJSJKFpc038.jpg\"}");
			}else{
				msgBaseEntity.setMsgSenderType(j);
				j = j==1?2:1;
				msgBaseEntity.setMsgContent("嘿嘿"+i);
			}
			msgBaseEntity.setMsgRealContent("嘿嘿");
			msgBaseEntity.setMsgHouseFid("8a90a2d457651ac8015765470da00135");
			msgBaseEntity.setHouseFid("8a90a2d45765950c015765df846202c5");
			msgBaseEntity.setRentWay(0);
			msgBaseEntity.setFid(UUIDGenerator.hexUUID());
			index += this.msgBaseDao.save(msgBaseEntity);
		}


		System.out.println(index);
	}

	@Test
	public void updateByFidTest(){
		MsgBaseEntity msgBaseEntity = new MsgBaseEntity();

		msgBaseEntity.setCreateTime(new Date());
		msgBaseEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
		msgBaseEntity.setIsRead(IsReadEnum.READ.getCode());
		msgBaseEntity.setMsgContent("{\"appChatRecordsExt\":{\"fid\":\"8a90a2d45765950c015765df846202c5\",\"rentWay\":0,\"startDate\":\"\",\"endDate\":\"\",\"ziroomFlag\":\"ZIROOM_MINSU_IM\",\"personNum\":\"\",\"houseName\":\"zhangshaobin\",\"housePicUrl\":\"http://10.16.34.44:8000/minsu/group1/M00/01/19/ChAiMFfo7e2AJhCmABupP2Zron8424.jpg_Z_240_240.jpg\",\"houseCard\":\"\",\"huanxinMsgId\":\"TXVjp-18-399c0\"},\"msgContent\":\"哈哈哈哈\"}");
		msgBaseEntity.setMsgHouseFid("8a90a2d457651ac8015765470da00134");
		msgBaseEntity.setMsgSenderType(UserTypeEnum.TENANT.getUserType());
		msgBaseEntity.setFid("8a9e9c8b541ebdd101541ebdd1fa0000");
		int index = this.msgBaseDao.updateByFid(msgBaseEntity);

		System.out.println(index);
	}


	@Test
	public void queryByPageTest(){

		MsgBaseRequest msgBaseRequest = new MsgBaseRequest();
		msgBaseRequest.setMsgHouseFid("8a9e9c8b541e32c001541e32c0150000");
		PagingResult<MsgBaseEntity> listPagingResult = this.msgBaseDao.queryByPage(msgBaseRequest);

		System.out.println(listPagingResult);
	}

	@Test
	public void testqueryMsgCountByItem(){
		MsgBaseRequest msgBaseRequest = new MsgBaseRequest();
		msgBaseRequest.setMsgHouseFid("8a9e9cd9556816d201556816d24f0000");
		msgBaseRequest.setMsgSenderType(1);
		long queryMsgCountByItem = msgBaseDao.queryMsgCountByItem(msgBaseRequest);
		System.out.println(queryMsgCountByItem);
	}

	@Test
	public void testqueryMsgCountByUid(){
		MsgCountRequest request = new MsgCountRequest();
		request.setLandlordUid("eaaf194b-067e-4289-bcd7-63a9433d3ef4");
		request.setMsgSenderType(1);
		long count = msgBaseDao.queryMsgCountByUid(request);
		System.out.println(count);
	}

	@Test
	public void getNotReplyListTest(){
		PageRequest pageRequest = new PageRequest();

		PagingResult<MsgNotReplyVo> notReplyList = msgBaseDao.getNotReplyList(pageRequest);
		System.err.println(JsonEntityTransform.Object2Json(notReplyList));
	}

	@Test
	public void queryCurrMsgBookTest(){

		MsgBaseEntity msgBaseEntity = this.msgBaseDao.queryCurrMsgBook("8a90a2d556798c7f01567997addd0002");
		System.out.println(msgBaseEntity);
	}

	@Test
	public void queryTwoChatRecordTest(){

		MsgHouseRequest msgHouseRequest = new MsgHouseRequest();
		msgHouseRequest.setLandlordUid("7a8c4184-8e2e-37b4-08e8-f4c20225e350");
		msgHouseRequest.setTenantUid("7a8c4184-8e2e-37b4-08e8-f4c20225e350");
		PagingResult<MsgBaseEntity> list = this.msgBaseDao.queryTwoChatRecord(msgHouseRequest);

		System.out.println(list);

	}

	@Test
	public void queryLanReplyTime(){
		MsgReplyStaticsRequest request = new MsgReplyStaticsRequest();
		request.setLandlordUid("acc89c6f-9fd8-488b-beac-4d818601a140");
		//		request.setHouseFid("8a9084df556cd72c01556d0eaf1b000d");
		//		request.setRentWay(RentWayEnum.ROOM.getCode());

		MsgReplyStaticsData data = msgBaseDao.queryLanReplyTime(request);
		System.out.println(JsonEntityTransform.Object2Json(data));
	}

	@Test
	public void findPreLanRecordTest(){

		MsgBaseEntity curMsgBase = new MsgBaseEntity();
		curMsgBase.setFid("8a90a36757c09de10157c3457a1a0138");
		curMsgBase.setMsgSenderType(UserTypeEnum.LANDLORD_HUAXIN.getUserType());
		curMsgBase.setMsgHouseFid("8a9085df56f89b240156fe3b727d0027");
		curMsgBase.setHouseFid("41564564");
		MsgBaseEntity msgBaseEntity  = this.msgBaseDao.findPreLanRecord(curMsgBase);

		System.out.println(msgBaseEntity);

	}

	@Test
	public void findMsgBaseByFidTest(){

		MsgBaseEntity curMsgBase = this.msgBaseDao.findMsgBaseByFid("8a90a36757c09de10157c3457a1a0138");

		System.out.println(curMsgBase);
	}

	@Test
	public void queryImMsgListTest(){

		ImMsgListDto imMsgListDto  = new ImMsgListDto();
		imMsgListDto.setLandlordUid("a06f82a2-423a-e4e3-4ea8-e98317540190");
		imMsgListDto.setImLastmodifyTime("2017-04-01 23:59:59");
		List<ImMsgListVo> list =  this.msgBaseDao.queryImMsgList(imMsgListDto);
		System.err.println(list);
	}

	@Test
	public void testlistImMsgSyncList(){
		MsgSyncRequest msgSyncRequest = new MsgSyncRequest();
		Date date = new Date(1494518400000L);

		msgSyncRequest.setPage(1);
		msgSyncRequest.setLimit(100);
		msgSyncRequest.setTillDate(date);
		String s = DateUtil.dateFormat(date);
		System.err.println(s);
		msgSyncRequest.setUid("e1751628-5a0f-4756-a9fc-c000343a5976");
		PagingResult<ImMsgSyncVo> imMsgSyncVoPagingResult = msgBaseDao.listImMsgSyncList(msgSyncRequest);

	}


}
