/**
 * @FileName: MsgUserRelOperaDaoTest.java
 * @Package com.ziroom.minsu.services.message.dao
 * 
 * @author loushuai
 * @created 2017年8月30日 下午7:18:19
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.message.MsgUserRelOperaEntity;
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
public class MsgUserRelOperaDaoTest extends BaseTest{

	@Resource(name = "message.msgUserRelOperaDao")
	private MsgUserRelOperaDao msgUserRelOperaDao;
	
	@Test
	public void saveTest() {
		MsgUserRelOperaEntity record = new MsgUserRelOperaEntity();
		record.setFid(UUIDGenerator.hexUUID());
		record.setMsgUserRelFid("8a9e9aa35e32c982015e32c982510000");
		record.setFromStatus(1);
		record.setToStatus(0);
		record.setRemark("第一次测试");
		record.setCreateFid("52a4aea1-5527-7421-1b25-83fbca1c1856");
		record.setCreaterType(0);
		int i = msgUserRelOperaDao.insertSelective(record);
		System.out.println(i);
	}
	
	@Test
	public void queryStatusConfByKeyTest(){
		MsgUserRelOperaEntity record =  this.msgUserRelOperaDao.selectByFid("8a9e9aa35e32e3c2015e32e3c2ec0000");
		
		System.out.println(record);
	}
	
	@Test
	public void updateByConditionTest(){
		
		MsgUserRelOperaEntity record =  new MsgUserRelOperaEntity();
		record.setFid("8a9e9aa35e32e3c2015e32e3c2ec0000");
		record.setRemark("ces测试修改");
		int i  = this.msgUserRelOperaDao.updateByFid(record);
		System.out.println(i);
	}
	
}
