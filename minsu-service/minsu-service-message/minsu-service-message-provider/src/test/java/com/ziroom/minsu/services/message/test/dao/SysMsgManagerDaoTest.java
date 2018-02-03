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

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.message.SysMsgEntity;
import com.ziroom.minsu.entity.message.SysMsgManagerEntity;
import com.ziroom.minsu.services.message.dao.SysMsgManagerDao;
import com.ziroom.minsu.services.message.dto.SysMsgManagerRequest;
import com.ziroom.minsu.services.message.dto.SysMsgRequest;
import com.ziroom.minsu.services.message.test.base.BaseTest;

/**
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class SysMsgManagerDaoTest extends BaseTest {

	@Resource(name = "message.sysMsgManagerDao")
	private SysMsgManagerDao sysMsgManagerDao;

	@Test
	public void testSave() {
		SysMsgManagerEntity entity = new SysMsgManagerEntity();
		entity.setCreateTime(new Date());
		entity.setCreateUid(UUIDGenerator.hexUUID());
		entity.setFid(UUIDGenerator.hexUUID());
		entity.setIsDel(0);
		entity.setMsgContent("您的手机已欠费");
		entity.setMsgTitle("手机欠费通知");
		entity.setMsgTargetType(1);
		entity.setMsgTargetUid(UUIDGenerator.hexUUID());
		entity.setIsRelease(0);
		entity.setLastModifyDate(new Date());
		entity.setModifyUid(UUIDGenerator.hexUUID());
		
		sysMsgManagerDao.saveSysMsgManagerEntity(entity);
	}

	@Test
	public void testReleaseSysMsgByFid(){
		SysMsgManagerEntity entity = new SysMsgManagerEntity();
		entity.setFid("8a9e9a965427cf83015427cf83030001");
		sysMsgManagerDao.releaseSysMsgByFid(entity);
		
	}
	@Test
	public void testDeleteSysMsgByFid(){
		SysMsgManagerEntity entity = new SysMsgManagerEntity();
		entity.setFid("8a9e9a965427cf83015427cf83030001");
		sysMsgManagerDao.deleteSysMsgManagerByFid(entity);
	}
	@Test
	public void testUpdateSysMsg(){
		SysMsgManagerEntity entity = new SysMsgManagerEntity();
		entity.setFid("8a9e9a965427cf83015427cf83030001");
		entity.setMsgContent("修改内容");
		entity.setLastModifyDate(new Date());
		sysMsgManagerDao.updateSysMsgManager(entity);
	}
	@Test
	public void testQueryList(){
		SysMsgManagerRequest request = new SysMsgManagerRequest();
		//request.setFid("8a9e9a965427cf83015427cf83030001");
		request.setCreateUid("8a9e9a965427cf83015427cf83030000");
		request.setPage(1);
		request.setLimit(10);
		PagingResult<SysMsgManagerEntity> result = sysMsgManagerDao.querySysMsgManager(request);
		List<SysMsgManagerEntity> rows = result.getRows();
		SysMsgManagerEntity sysMsgManagerEntity = rows.get(0);
		
		System.out.println(sysMsgManagerEntity.getCreateUid());
	}
}
