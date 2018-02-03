/**
 * @FileName: MsgUserRelDaoTest.java
 * @Package com.ziroom.minsu.services.message.test.dao
 * 
 * @author loushuai
 * @created 2017年8月30日 下午6:30:06
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.message.MsgUserRelEntity;
import com.ziroom.minsu.services.message.dao.MsgUserRelDao;
import com.ziroom.minsu.services.message.test.base.BaseTest;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class MsgUserRelDaoTest extends BaseTest{

	@Resource(name = "message.msgUserRelDao")
	private MsgUserRelDao msgUserRelDao;
	
	@Test
	public void saveTest() {
		MsgUserRelEntity record = new MsgUserRelEntity();
		record.setFid(UUIDGenerator.hexUUID());
		record.setFromUid("app_3a59968c-1eb9-4612-e325-5cc0a856ac34");
		record.setToUid("52a4aea1-5527-7421-1b25-83fbca1c1856");
		record.setRelStatus(1);
		record.setSourceType(0);
		record.setRemark("第一次测试");
		record.setZiroomFlag("ZIROOM_CHANGZU_IM");
		record.setCreateFid(UUIDGenerator.hexUUID());
		record.setCreaterType(2);
		int i = msgUserRelDao.insertSelective(record);
		System.out.println(i);
	}
	
	@Test
	public void queryStatusConfByKeyTest(){
		
		MsgUserRelEntity record =  this.msgUserRelDao.selectByFid("8a9e9aa35e32c982015e32c982510000");
		
		System.out.println(record);
	}
	
	@Test
	public void updateByConditionTest(){
		
		MsgUserRelEntity record =  new MsgUserRelEntity();
		record.setFid("8a9e9aa35e32c982015e32c982510000");
		record.setRemark("ces测试修改");
		int i  = this.msgUserRelDao.updateByFid(record);
		System.out.println(i);
	}
}
