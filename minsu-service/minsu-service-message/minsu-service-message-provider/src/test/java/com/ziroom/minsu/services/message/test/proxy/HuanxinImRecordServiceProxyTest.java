/**
 * @FileName: HuanxinImRecordServiceProxyTest.java
 * @Package com.ziroom.minsu.services.message.test.proxy
 * 
 * @author yd
 * @created 2016年9月10日 下午5:32:09
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.message.HuanxinImRecordEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.JsonTransform;
import com.ziroom.minsu.services.message.dto.HuanxinImRecordDto;
import com.ziroom.minsu.services.message.dto.MsgFirstDdvisoryRequest;
import com.ziroom.minsu.services.message.entity.SysMsgVo;
import com.ziroom.minsu.services.message.proxy.HuanxinImRecordServiceProxy;
import com.ziroom.minsu.services.message.test.base.BaseTest;
import com.ziroom.minsu.services.message.utils.base.SendImMsgRequest;
import com.ziroom.minsu.valenum.msg.RunStatusEnum;

import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>环信同步聊天记录</p>
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
public class HuanxinImRecordServiceProxyTest  extends BaseTest{


	@Resource(name = "message.huanxinImRecordServiceProxy")
	private HuanxinImRecordServiceProxy huanxinImRecordServiceProxy;
	@Test
	public void sysHuanxinImMesTest() {

		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.huanxinImRecordServiceProxy.sysHuanxinImMes("25","10"));
		System.out.println(dto);

		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void sendImMesToHuanxin(){
		List<SendImMsgRequest> list = new ArrayList<>();
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.huanxinImRecordServiceProxy.sendImMesToHuanxin(JsonTransform.Object2Json(list)));
		System.out.println(dto);

		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testQueryByPage(){
		MsgFirstDdvisoryRequest msgFirstDdvisoryRequest = new MsgFirstDdvisoryRequest();
		msgFirstDdvisoryRequest.setRunTime(DateUtil.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
		List<Integer> listStatus = new LinkedList<Integer>();
		listStatus.add(RunStatusEnum.NOT_RUN.getValue());
		listStatus.add(RunStatusEnum.RUN_FAILED.getValue());
		msgFirstDdvisoryRequest.setListStatus(listStatus);
		msgFirstDdvisoryRequest.setLimit(MessageConst.limit);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.huanxinImRecordServiceProxy.queryByPage(JsonEntityTransform.Object2Json(msgFirstDdvisoryRequest)));
		PagingResult<SysMsgVo> pageRsult = dto.parseData("pagingResult", new TypeReference<PagingResult<SysMsgVo>>() {});
		System.out.println("******************:pageRsult:" + pageRsult.getRows());
		System.out.println("******************:pageRsult:" + pageRsult.getTotal());
	}
	
	@Test
	public void	deactivateImUser(){
		this.huanxinImRecordServiceProxy.activateImUser("7a8c4184-8e2e-37b4-08e8-f4c20225e350");
	}
	
	@Test
	public void	getCountMsgEachTest(){
		HuanxinImRecordEntity huanxinIm = new HuanxinImRecordEntity();
		huanxinIm.setFromUid("74462e51-4e50-9e1a-8018-efa49050caff");
		huanxinIm.setToUid("29deba01-772d-43ec-901c-cacc7a11475c");
		huanxinIm.setZiroomFlag("ZIROOM_CHANGZU_IM");
		this.huanxinImRecordServiceProxy.getCountMsgEach(JsonEntityTransform.Object2Json(huanxinIm));
	}
	
	@Test
	public void testsaveHuanxinImRecord(){
		HuanxinImRecordEntity imRecord = new HuanxinImRecordEntity();
		imRecord.setFid(UUIDGenerator.hexUUID());
		imRecord.setUuid(UUIDGenerator.hexUUID());
		imRecord.setInterfaceType("chatmessage");
		imRecord.setCreated(new Date());
		imRecord.setModified(new Date());
		imRecord.setTimestampSend(new Date());
		imRecord.setFromUid(UUIDGenerator.hexUUID());
		imRecord.setToUid(UUIDGenerator.hexUUID());
		imRecord.setMsgId(UUIDGenerator.hexUUID());
		imRecord.setChatType("chat");
		imRecord.setZiroomFlag("ZIROOM_CHANGZU_IM");
		imRecord.setExt("测试");
		imRecord.setMsg("测试");
		imRecord.setType("img");
		imRecord.setLength(1000);
		imRecord.setUrl("http://pic.sogou.com/pics/recompic/detail.jsp?category=%E5%A3%81%E7%BA%B8&tag=%E9%A3%8E%E6%99%AF#0%26799954%260");
		imRecord.setFilename("吃点饭打算");
		imRecord.setFileLength(81818181);
		imRecord.setSize("房东发送放大");
		imRecord.setSecret("123456789");
		imRecord.setLat((float) 9.1);
		imRecord.setLng((float) 9.1);
		imRecord.setAddr("fdsafdsf");
		HuanxinImRecordDto dto = new HuanxinImRecordDto();
		dto.setHuanxinImRecord(imRecord);
		String saveHuanxinImRecord = this.huanxinImRecordServiceProxy.saveHuanxinImRecord(JsonEntityTransform.Object2Json(dto));
		System.out.println(saveHuanxinImRecord);
	}
}
