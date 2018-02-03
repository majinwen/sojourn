/**
 * @FileName: MsgBaseErrorDaoTest.java
 * @Package com.ziroom.minsu.services.message.test.dao
 * 
 * @author yd
 * @created 2016年11月11日 上午11:52:30
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.dao;

import static org.junit.Assert.*;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.message.MsgBaseErrorEntity;
import com.ziroom.minsu.services.message.dao.MsgBaseErrorDao;
import com.ziroom.minsu.services.message.test.base.BaseTest;
import com.ziroom.minsu.valenum.common.UserTypeEnum;

/**
 * <p>TODO</p>
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
public class MsgBaseErrorDaoTest extends BaseTest{

	
	@Resource(name = "message.msgBaseErrorDao")
	private MsgBaseErrorDao msgBaseErrorDao;
	
	
	@Test
	public void testSaveMsgBaseError() {
		
		MsgBaseErrorEntity msgBaseError = new MsgBaseErrorEntity();
		
		msgBaseError.setEndDate(new Date());
		msgBaseError.setEndDate(new Date());
		msgBaseError.setFid(UUIDGenerator.hexUUID());
		msgBaseError.setHouseFid("8a9e9a8b53d6089f0153d608a1f80002");
		msgBaseError.setHouseName("fadsfsdf");
		msgBaseError.setLandlordUid("8a9e9a9e543d23f901543d23f9e90000");
		msgBaseError.setMsgContent("1521654");
		msgBaseError.setMsgSentType(UserTypeEnum.TENANT_HUANXIN.getUserCode());
		msgBaseError.setMsgSource(1);
		msgBaseError.setPersonNum(2);
		msgBaseError.setTenantUid("50cc2cc3-49a1-79cc-fa23-fd0c4d881448");
	
		int i = this.msgBaseErrorDao.saveMsgBaseError(msgBaseError);
	}

}
