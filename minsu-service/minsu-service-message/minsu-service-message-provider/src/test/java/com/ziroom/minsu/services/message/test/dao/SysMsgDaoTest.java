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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.message.SysMsgEntity;
import com.ziroom.minsu.services.message.dao.SysMsgDao;
import com.ziroom.minsu.services.message.dto.SysMsgRequest;
import com.ziroom.minsu.services.message.test.base.BaseTest;
import com.ziroom.minsu.valenum.msg.IsDelEnum;

/**
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class SysMsgDaoTest extends BaseTest {

	@Resource(name = "message.sysMsgDao")
	private SysMsgDao sysMsgDao;

	@Test
	public void testSave() {
		SysMsgEntity sysMsgEntity = new SysMsgEntity();
		sysMsgEntity.setCreateTime(new Date());
		sysMsgEntity.setFid(UUIDGenerator.hexUUID());
		sysMsgEntity.setIsDel(0);
		sysMsgEntity.setIsRead(0);
		sysMsgEntity.setMsgContent("您有一个下单new");
		sysMsgEntity.setMsgTitle("下单提醒new");
		sysMsgEntity.setMsgTargetType(1);
		sysMsgEntity.setMsgTargetUid(UUIDGenerator.hexUUID());
		//tinyint最大127
		sysMsgEntity.setMsgTmpType(200);
		sysMsgDao.saveSysMsg(sysMsgEntity);
	}
	@Test
	public void testSaveSysMsgBatch(){
		List<SysMsgEntity> list = new ArrayList<>();
		for(int i = 0;i<100;i++){
			SysMsgEntity sysMsgEntity = new SysMsgEntity();
			sysMsgEntity.setCreateTime(new Date());
			sysMsgEntity.setFid(UUIDGenerator.hexUUID());
			sysMsgEntity.setIsDel(0);
			sysMsgEntity.setIsRead(0);
			sysMsgEntity.setMsgContent("您有一个下单--->"+i);
			sysMsgEntity.setMsgTitle("下单提醒---->"+i);
			sysMsgEntity.setMsgTargetType(1);
			sysMsgEntity.setMsgTargetUid("8a9e9a96542771e401542771e4890063");
			//tinyint最大127
			sysMsgEntity.setMsgTmpType(126);
			list.add(sysMsgEntity);
			System.out.println(sysMsgEntity.getFid());
		}
		
		System.out.println(list.size()+"--------");
		System.out.println(sysMsgDao.saveSysMsgBatch(list)+"++++++++++++");
	}
	
	@Test
	public void testdeleteSysMsg(){
		SysMsgEntity sysMsgEntity = new SysMsgEntity();
		sysMsgEntity.setFid("8a9e9a96542771e401542771e4890062");
		sysMsgDao.deleteSysMsg(sysMsgEntity);
	}
	
	@Test
	public void testQueryList(){
		SysMsgRequest sysMsgRequest = new SysMsgRequest();
		sysMsgRequest.setMsgTargetUid("12255885852");
		sysMsgRequest.setPage(2);
		sysMsgRequest.setLimit(10);
		sysMsgRequest.setIsDel(0);
		PagingResult<SysMsgEntity> result = sysMsgDao.queryByTargetUid(sysMsgRequest);
		List<SysMsgEntity> rows = result.getRows();
		System.out.println(rows.size());
		System.out.println(result.getTotal());
		
	}
	
}
