/**
 * @FileName: HuanxinImOfflineDaoTest.java
 * @Package com.ziroom.minsu.services.message.test.dao
 * 
 * @author loushuai
 * @created 2017年8月30日 下午7:44:05
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.message.HuanxinImOfflineEntity;
import com.ziroom.minsu.entity.message.MsgUserRelEntity;
import com.ziroom.minsu.entity.message.MsgUserRelOperaEntity;
import com.ziroom.minsu.services.message.dao.HuanxinImOfflineDao;
import com.ziroom.minsu.services.message.dao.MsgUserRelOperaDao;
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
public class HuanxinImOfflineDaoTest extends BaseTest{


	@Resource(name = "message.huanxinImOfflineDao")
	private HuanxinImOfflineDao huanxinImOfflineDao;
	
	@Test
	public void saveTest() {
		HuanxinImOfflineEntity record = new HuanxinImOfflineEntity();
		record.setMsgId(UUIDGenerator.hexUUID());
		record.setFromUid("app_3a59968c-1eb9-4612-e325-5cc0a856ac34");
		record.setToUid("52a4aea1-5527-7421-1b25-83fbca1c1856");
		record.setZiroomFlag("ZIROOM_CHANGZU_IM");
		int i = huanxinImOfflineDao.insertSelective(record);
		System.out.println(i);
	}
	
	@Test
	public void queryStatusConfByKeyTest(){
		HuanxinImOfflineEntity record =  this.huanxinImOfflineDao.selectByMsgid("8a9e9aa35e32f860015e32f860030000");
		System.out.println(record);
	}
	
	@Test
	public void updateByConditionTest(){
		HuanxinImOfflineEntity record =  new HuanxinImOfflineEntity();
		record.setMsgId("8a9e9aa35e32f860015e32f860030000");
		record.setZiroomFlag("ZIROOM_ZRY_IM");
		int i  = this.huanxinImOfflineDao.updateByMsgid(record);
		System.out.println(i);
	}
	
}
