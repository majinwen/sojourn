/**
 * @FileName: HuanxinImGroupDaoTest.java
 * @Package com.ziroom.minsu.services.message.test.dao
 * 
 * @author yd
 * @created 2017年8月1日 下午4:40:22
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.message.HuanxinImGroupEntity;
import com.ziroom.minsu.services.message.dao.HuanxinImGroupDao;
import com.ziroom.minsu.services.message.dto.GroupDto;
import com.ziroom.minsu.services.message.entity.GroupVo;
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
public class HuanxinImGroupDaoTest extends BaseTest{
	
	@Resource(name = "message.huanxinImGroupDao")
	private  HuanxinImGroupDao huanxinImGroupDao;

	/**
	 * Test method for {@link com.ziroom.minsu.services.message.dao.HuanxinImGroupDao#queryGroupByPage(com.ziroom.minsu.services.message.dto.GroupDto)}.
	 */
	@Test
	public void testQueryGroupByPage() {
		
		GroupDto groupDto = new GroupDto();
		groupDto.setProjectBid("63615afa5a344153a047aca1ea32cc51");
		 PagingResult<GroupVo>  list = huanxinImGroupDao.queryGroupByPage(groupDto);
		 
		 System.out.println(list);
	}

	@Test
	public void testQueryDefaultGroupByProBid(){
		HuanxinImGroupEntity huanxinImGroupEntity = huanxinImGroupDao.queryDefaultGroupByProBid("63615afa5a344153a047aca1ea32cc51");
		System.out.println(huanxinImGroupEntity.toJsonStr());
		
	}
}
