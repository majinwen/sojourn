/**
 * @FileName: MsgUserLivenessDaoTest.java
 * @Package com.ziroom.minsu.services.message.test.dao
 * 
 * @author loushuai
 * @created 2017年9月1日 上午10:56:53
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.message.MsgUserLivenessEntity;
import com.ziroom.minsu.entity.message.MsgUserRelEntity;
import com.ziroom.minsu.services.message.dao.MsgUserLivenessDao;
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
public class MsgUserLivenessDaoTest extends BaseTest{

	@Resource(name = "message.msgUserLivenessDao")
	private MsgUserLivenessDao msgUserLivenessDao;
	
	@Test
	public void saveTest() {
		MsgUserLivenessEntity record = new MsgUserLivenessEntity();
		record.setUid("8a9e9a9f544b372101544b3721de0000");
		record.setLastLiveTime(new Date());
		int i = msgUserLivenessDao.insertSelective(record);
		System.out.println(i);
	}
	
	@Test
	public void queryStatusConfByKeyTest(){
		
		MsgUserLivenessEntity record =  this.msgUserLivenessDao.selectByUid("8a9e9a9f544b372101544b3721de0000");
		
		System.out.println(record);
	}
	
	@Test
	public void updateByConditionTest(){
		
		MsgUserLivenessEntity record =  new MsgUserLivenessEntity();
		record.setUid("8a9e9a9f544b372101544b3721de0000");
		record.setLastLiveTime(new Date());
		int i  = this.msgUserLivenessDao.updateByUid(record);
		System.out.println(i);
	}
	
	@Test
	public void testgetAllUidLiveness(){
		List<String> toUidList = new ArrayList<String>();
		toUidList.add("8a9e9a9f544b372101544b3721de0000");
		toUidList.add("8a9e9aa35e3c0381015e3c0381ab0000");
		toUidList.add("8a9e9aa35e3c0381015e3c0381ab0001");
		toUidList.add("8a9e9aa35e3c0381015e3c0381ab0001");
		List<MsgUserLivenessEntity> allUidLiveness = this.msgUserLivenessDao.getAllUidLiveness(toUidList);
		System.out.println(allUidLiveness);
	}
	
}
