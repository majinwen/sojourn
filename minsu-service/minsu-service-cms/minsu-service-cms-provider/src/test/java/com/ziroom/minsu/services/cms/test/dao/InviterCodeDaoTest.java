package com.ziroom.minsu.services.cms.test.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.cms.InviteEntity;
import com.ziroom.minsu.entity.cms.InviterCodeEntity;
import com.ziroom.minsu.entity.cms.PointDetailEntity;
import com.ziroom.minsu.services.cms.dao.InviteDao;
import com.ziroom.minsu.services.cms.dao.InviterCodeDao;
import com.ziroom.minsu.services.cms.dto.InviteListRequest;
import com.ziroom.minsu.services.cms.test.base.BaseTest;

public class InviterCodeDaoTest  extends BaseTest {

	@Resource(name = "cms.inviterCodeDao")
    private InviterCodeDao inviterCodeDao;


	@Test
	public void inviterCodeDaoTest(){
		InviterCodeEntity record = new InviterCodeEntity();
		record.setInviteUid("loushuai");
		record.setInviteCode("123456");
		int insertInviterCod = inviterCodeDao.insertInviterCod(record);
		System.out.println(insertInviterCod);
	}
	
	@Test
	public void selectByInviteUidTest(){
		InviterCodeEntity result = inviterCodeDao.selectByInviteUid("loushuai");
		System.out.println(result);
	}
	
	@Test
	public void updateByParamTest(){
		InviterCodeEntity record = new InviterCodeEntity();
		record.setInviteUid("loushuai");
		record.setInviteCode("12345689");
		int updateByInviteUid = inviterCodeDao.updateByInviteUid(record);
		System.out.println(updateByInviteUid);
	}
	


}
