/**
 * @FileName: MsgHuanxinImLogDaoTest.java
 * @Package com.ziroom.minsu.services.message.test.dao
 * 
 * @author yd
 * @created 2017年8月3日 上午11:13:50
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.dao;

import static org.junit.Assert.fail;

import javax.annotation.Resource;

import org.junit.Test;

import com.ziroom.minsu.entity.message.MsgHuanxinImLogEntity;
import com.ziroom.minsu.services.message.dao.MsgHuanxinImLogDao;
import com.ziroom.minsu.services.message.test.base.BaseTest;

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
public class MsgHuanxinImLogDaoTest extends BaseTest{
	
	@Resource(name = "message.msgHuanxinImLogDao")
	private MsgHuanxinImLogDao  msgHuanxinImLogDao;

	@Test
	public void testSaveMsgHuanxinImLog() {
		
		MsgHuanxinImLogEntity msgHuanxinImLog = new MsgHuanxinImLogEntity();
		
		msgHuanxinImLog.setChatStatu(0);
		msgHuanxinImLog.setChatType("chat");
		msgHuanxinImLog.setContent("哈哈哈哈");
		msgHuanxinImLog.setExt("fdsafojsdfjdsfj");
		msgHuanxinImLog.setFromUid("1f56sd4f56s4f5dsf4ds56f4ds4fds564f");
		msgHuanxinImLog.setMsgId("GFGFDSDAS ");
		msgHuanxinImLog.setToUid("4564564564564564564564564564564564");
		msgHuanxinImLog.setType("txt");
		msgHuanxinImLog.setZiroomFlag("MINSU_ZRY_IM");
		msgHuanxinImLog.setExt("测试");
		msgHuanxinImLog.setType("txt");
		msgHuanxinImLog.setLength(1000);
		msgHuanxinImLog.setUrl("http:localhost:8080");
		msgHuanxinImLog.setFileLength(81818181);
		msgHuanxinImLog.setSize("房东发送放大");
		msgHuanxinImLog.setSecret("123456789");
		msgHuanxinImLog.setLat((float) 9.1);
		msgHuanxinImLog.setLng((float) 9.1);
		msgHuanxinImLog.setAddr("fdsafdsf");
		int i = msgHuanxinImLogDao.saveMsgHuanxinImLog(msgHuanxinImLog);
		
		System.out.println(i);
	}

}
