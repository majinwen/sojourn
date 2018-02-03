/**
 * @FileName: HuanxinImRecordDaoTest.java
 * @Package com.ziroom.minsu.services.message.dao
 * 
 * @author yd
 * @created 2016年9月9日 下午3:30:22
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.dao;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.message.HuanxinImRecordEntity;
import com.ziroom.minsu.services.message.dao.HuanxinImRecordDao;
import com.ziroom.minsu.services.message.dto.MsgSyncRequest;
import com.ziroom.minsu.services.message.test.base.BaseTest;

/**
 * <p>环信同步记录测试dao层类</p>
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
public class HuanxinImRecordDaoTest extends BaseTest {
	
	
	@Resource(name = "message.huanxinImRecordDao")
	private HuanxinImRecordDao huanxinImRecordDao;
	
	
	
	@Test
	public void atest() {
		
		
		try {
			Thread.sleep(99999999);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	public void saveHuanxinImRecordTest() {
		
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
		imRecord.setType("txt");
		imRecord.setLength(1000);
		imRecord.setUrl("http:localhost:8080");
		imRecord.setFilename("吃点饭打算");
		imRecord.setFileLength(81818181);
		imRecord.setSize("房东发送放大");
		imRecord.setSecret("123456789");
		imRecord.setLat((float) 9.1);
		imRecord.setLng((float) 9.1);
		imRecord.setAddr("fdsafdsf");
		this.huanxinImRecordDao.saveHuanxinImRecord(imRecord);
	}
	
	

	@Test
	public void queryHuanxinImRecordByPageTest(){
		
		MsgSyncRequest msgSyncRequest = new MsgSyncRequest();
		String dateStr = DateUtil.dateFormat(new Date(), "yyyy-MM-dd");
		Date date;
		try {
			date = DateUtil.parseDate(dateStr, "yyyy-MM-dd");
			Date time = DateUtil.getTime(date, -360);
			msgSyncRequest.setTillDate(time);
			msgSyncRequest.setLimit(500);
			msgSyncRequest.setUid("app_78211cfa-b7e0-447c-b83c-71eb1df6fb51");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		PagingResult<HuanxinImRecordEntity> page = 	huanxinImRecordDao.queryHuanxinImRecordByPage(msgSyncRequest);
		
		System.out.println(page);
	}

}
