/**
 * @FileName: GuardAreaDaoTest.java
 * @Package com.ziroom.minsu.services.basedata.test.dao
 * 
 * @author yd
 * @created 2016年7月5日 下午3:44:05
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.test.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.conf.GuardAreaEntity;
import com.ziroom.minsu.services.basedata.dao.GuardAreaDao;
import com.ziroom.minsu.services.basedata.dto.GuardAreaRequest;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import com.ziroom.minsu.valenum.msg.IsDelEnum;

/**
 * <p>区域管家测试</p>
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
public class GuardAreaDaoTest extends BaseTest{

	

    @Resource(name="basedata.guardAreaDao")
    private GuardAreaDao guardAreaDao;
    
    
	@Test
	public void saveGuardAreaTest() {
		
		GuardAreaEntity guardAreaEntity = new GuardAreaEntity();
		guardAreaEntity.setFid(UUIDGenerator.hexUUID());
		guardAreaEntity.setNationCode("100000");
		guardAreaEntity.setProvinceCode("110000");
		guardAreaEntity.setCreateDate(new Date());
		guardAreaEntity.setCreateFid("fd1s56f4ds56f4ds56f4d5s6f456");
		guardAreaEntity.setEmpCode("20223709");
		guardAreaEntity.setEmpName("杨东");
		guardAreaEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
		guardAreaEntity.setLastModifyDate(new Date());
		int i = this.guardAreaDao.saveGuardArea(guardAreaEntity);
		
		System.out.println(i);
	}
	
	/**
	 * 
	 * 修改测试
	 *
	 * @author yd
	 * @created 2016年7月5日 下午4:10:47
	 *
	 */
	@Test
	public void updateGuardAreaByFidTest(){
		GuardAreaEntity guardAreaEntity = new GuardAreaEntity();
		guardAreaEntity.setFid("8a9e9cd955ba1b2e0155ba1b2ee30000");
		guardAreaEntity.setCreateFid("123465456456456456456456");
		guardAreaEntity.setEmpCode("20223708");
		guardAreaEntity.setEmpName("杨");
		guardAreaEntity.setIsDel(IsDelEnum.DEL.getCode());
		guardAreaEntity.setEmpPhone("18701482472");
		guardAreaEntity.setLastModifyDate(new Date());
		
		int i = this.guardAreaDao.updateGuardAreaByFid(guardAreaEntity);
		System.out.println(i);
	}
	
	/**
	 * 
	 * 分页查询
	 *
	 * @author yd
	 * @created 2016年7月5日 下午4:13:23
	 *
	 */
	@Test
	public void findGaurdAreaByPageTest(){
		
		GuardAreaRequest guardAreaR = new GuardAreaRequest();
		guardAreaR.setIsDel(IsDelEnum.NOT_DEL.getCode());
		PagingResult<GuardAreaEntity> page = this.guardAreaDao.findGaurdAreaByPage(guardAreaR);
		
		System.out.println(page);
	}

	
	@Test
	public void findGaurdAreaByConditionTest(){
		GuardAreaRequest guardAreaR = new GuardAreaRequest();
		guardAreaR.setIsDel(IsDelEnum.DEL.getCode());
		List<GuardAreaEntity> page = this.guardAreaDao.findGaurdAreaByCondition(guardAreaR);
		
		System.out.println(page);
	}
	
	@Test
	public void findGuardAreaByFidTest(){
		
		GuardAreaEntity guardAreaEntity= this.guardAreaDao.findGuardAreaByFid("8a9e9cd955c5947c0155c5c3c1aa0005");
		
		System.out.println(guardAreaEntity);
	}
	
}
