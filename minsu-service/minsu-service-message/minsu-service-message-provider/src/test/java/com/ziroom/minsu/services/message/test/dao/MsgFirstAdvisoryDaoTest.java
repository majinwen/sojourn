/**
 * @FileName: MsgFirstAdvisoryDaoTest.java
 * @Package com.ziroom.minsu.services.message.test.dao
 * 
 * @author yd
 * @created 2017年4月8日 下午1:07:45
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.message.MsgFirstAdvisoryEntity;
import com.ziroom.minsu.services.message.dao.MsgFirstAdvisoryDao;
import com.ziroom.minsu.services.message.dto.MsgAdvisoryFollowRequest;
import com.ziroom.minsu.services.message.dto.MsgFirstDdvisoryRequest;
import com.ziroom.minsu.services.message.entity.MsgAdvisoryFollowVo;
import com.ziroom.minsu.services.message.entity.SysMsgVo;
import com.ziroom.minsu.services.message.test.base.BaseTest;
import com.ziroom.minsu.valenum.msg.RunStatusEnum;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>测试</p>
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
public class MsgFirstAdvisoryDaoTest extends BaseTest{
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Resource(name = "message.msgFirstAdvisoryDao")
	private  MsgFirstAdvisoryDao msgFirstAdvisoryDao;

	@Test
	public void saveMsgFirstAdvisory() {

		try {
			MsgFirstAdvisoryEntity msgFirstAdvisory = new MsgFirstAdvisoryEntity();
			msgFirstAdvisory.setCreateTime(new Date());
			msgFirstAdvisory.setFid(UUIDGenerator.hexUUID());
			msgFirstAdvisory.setFromUid("5f4f193b-07fd-a708-85f8-22907004fd6d");
			msgFirstAdvisory.setHouseFid("8a90a2d459055bad0159064009f501a2");
			msgFirstAdvisory.setMsgBaseFid("8a90a2d45b3c37a5015b3d155e420089");
			msgFirstAdvisory.setMsgContent("hhh ");
			msgFirstAdvisory.setMsgContentExt("{\"msgContent\":\"hhh \",\"appChatRecordsExt\":{\"fid\":\"8a90a2d459055bad0159064009f501a2\",\"rentWay\":0,\"startDate\":\"2017-04-05\",\"endDate\":\"2017-04-07\",\"ziroomFlag\":\"ZIROOM_MINSU_IM\",\"personNum\":\"1\",\"houseName\":\"北京特色房源333333333\",\"housePicUrl\":\"http://10.16.34.44:8000/minsu/group1/M00/01/F2/ChAiMFhTjuKAbuJYAA0l0xkAxmU890.JPG_Z_240_240.jpg\",\"houseCard\":\"\",\"huanxinMsgId\":\"317027263746934852\",\"domainFlag\":NULL,\"source\":1}}");
			msgFirstAdvisory.setMsgHouseFid("8a90a2d45b3c37a5015b3d155e420089");
			msgFirstAdvisory.setMsgSendTime(DateUtil.parseDate("2017-04-05 15:47:42", "yyyy-MM-dd HH:mm:ss"));
			msgFirstAdvisory.setFollowStatus(20);
			msgFirstAdvisory.setRemark("");
			msgFirstAdvisory.setRentWay(0);
			msgFirstAdvisory.setRuntime(DateUtil.parseDate(DateUtil.timestampFormat(msgFirstAdvisory.getMsgSendTime().getTime()+5*60*1000),"yyyy-MM-dd HH:mm:ss"));
			msgFirstAdvisory.setStatus(RunStatusEnum.NOT_RUN.getValue());
			msgFirstAdvisory.setToUid("dda8c8dd-a698-4e18-8e9c-d8d802970082");
			int i = this.msgFirstAdvisoryDao.saveMsgFirstAdvisory(msgFirstAdvisory);

			System.out.println(i);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void updateByMsgHouseFid(){
		MsgFirstAdvisoryEntity msgFirstAdvisory = new MsgFirstAdvisoryEntity();
		msgFirstAdvisory.setMsgBaseFid("8a90a2d45b3c37a5015b3d155e420089");
		msgFirstAdvisory.setMsgHouseFid("8a90a2d45b3c37a5015b3d155e420089");
		msgFirstAdvisory.setStatus(RunStatusEnum.RUN_SUCCESS.getValue());

		this.msgFirstAdvisoryDao.updateByUid(msgFirstAdvisory);
	}

	@Test
	public void  findAllByCondition(){

		MsgFirstDdvisoryRequest msgFirstDdvisoryRequest = new MsgFirstDdvisoryRequest();

		msgFirstDdvisoryRequest.setRunTime(DateUtil.dateFormat(new Date(),"yyyy-MM-dd HH:mm:ss"));

		List<Integer> listStatus = new LinkedList<Integer>();
		listStatus.add(RunStatusEnum.NOT_RUN.getValue());
		listStatus.add(RunStatusEnum.RUN_FAILED.getValue());
		listStatus.add(RunStatusEnum.RUN_SUCCESS.getValue());
		msgFirstDdvisoryRequest.setListStatus(listStatus);
		PagingResult<SysMsgVo>  list = this.msgFirstAdvisoryDao.queryByPage(msgFirstDdvisoryRequest);
		System.out.println(list);
	}

	@Test
	public void queryByMsgBaseFid() throws Exception {
		String msgBaseFid = "8a90a2d45b3c37a5015b3d155e420089";
		MsgFirstAdvisoryEntity result = this.msgFirstAdvisoryDao.queryByMsgBaseFid(msgBaseFid);
		Assert.assertNotNull(result);
	}

	@Test
	public void updateByMsgBaseFid() throws Exception {
		MsgFirstAdvisoryEntity msgFirstAdvisoryEntity = new MsgFirstAdvisoryEntity();
		msgFirstAdvisoryEntity.setFid("8a9e988e5b4c1dc6015b4c1dc6370000");
		msgFirstAdvisoryEntity.setFollowStatus(20);
		msgFirstAdvisoryEntity.setLastModifyDate(new Date());
		int result = this.msgFirstAdvisoryDao.updateFollowStatusByFid(msgFirstAdvisoryEntity);
		Assert.assertEquals(0, result);
	}

	@Test
	public void queryAllNeedFollowPageNoUnion(){
		MsgAdvisoryFollowRequest msgAdvisoryFollowRequest = new MsgAdvisoryFollowRequest();
		msgAdvisoryFollowRequest.setLimit(5);
		msgAdvisoryFollowRequest.setPage(1);
		msgAdvisoryFollowRequest.setMsgSendStartTime("2017-06-01");
		PagingResult<MsgAdvisoryFollowVo> msgAdvisoryFollowVoPagingResult = msgFirstAdvisoryDao.queryAllNeedFollowPageNoUnion(msgAdvisoryFollowRequest);
		System.err.print(msgAdvisoryFollowVoPagingResult);
	}


}
