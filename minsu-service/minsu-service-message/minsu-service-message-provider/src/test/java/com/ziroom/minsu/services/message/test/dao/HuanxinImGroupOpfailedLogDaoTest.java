/**
 * @FileName: HuanxinImGroupOpfailedLogDaoTest.java
 * @Package com.ziroom.minsu.services.message.test.dao
 * 
 * @author yd
 * @created 2017年8月18日 下午2:14:45
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.dao;

import static org.junit.Assert.fail;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.message.HuanxinImGroupOpfailedLogEntity;
import com.ziroom.minsu.services.message.dao.HuanxinImGroupOpfailedLogDao;
import com.ziroom.minsu.services.message.dto.GroupOpfailedLogDto;
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
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class HuanxinImGroupOpfailedLogDaoTest extends BaseTest{

	@Resource(name="message.huanxinImGroupOpfailedLogDao")
	private HuanxinImGroupOpfailedLogDao huanxinImGroupOpfailedLogDao;


	/**
	 * Test method for {@link com.ziroom.minsu.services.message.dao.HuanxinImGroupOpfailedLogDao#updateHuanxinImGroupOpfailedLog(com.ziroom.minsu.entity.message.HuanxinImGroupOpfailedLogEntity)}.
	 */
	@Test
	public void testUpdateHuanxinImGroupOpfailedLog() {
		
		HuanxinImGroupOpfailedLogEntity huanxinImGroupOpfailedLog = new  HuanxinImGroupOpfailedLogEntity();
		huanxinImGroupOpfailedLog.setFid("8a9e989c5def881a015def881a350000");
		huanxinImGroupOpfailedLog.setUid("461eefee-a9a8-e9db-263b-196cd6b6df6f");
		huanxinImGroupOpfailedLog.setGroupId("23953027432449");
		huanxinImGroupOpfailedLog.setFailedReason(2);
		huanxinImGroupOpfailedLog.setSourceType(2);
		huanxinImGroupOpfailedLog.setSysStatu(1);
		int i = huanxinImGroupOpfailedLogDao.updateHuanxinImGroupOpfailedLog(huanxinImGroupOpfailedLog);
		
		System.out.println(i);
	}

	/**
	 * Test method for {@link com.ziroom.minsu.services.message.dao.HuanxinImGroupOpfailedLogDao#queryGroupOpfailedByPage(com.ziroom.minsu.services.message.dto.GroupOpfailedLogDto)}.
	 */
	@Test
	public void testQueryGroupOpfailedByPage() {
		
		
		GroupOpfailedLogDto groupOpfailedLogDto = new GroupOpfailedLogDto();

		PagingResult<HuanxinImGroupOpfailedLogEntity>  page = huanxinImGroupOpfailedLogDao.queryGroupOpfailedByPage(groupOpfailedLogDto);
		
		System.out.println(page);
	}

}
