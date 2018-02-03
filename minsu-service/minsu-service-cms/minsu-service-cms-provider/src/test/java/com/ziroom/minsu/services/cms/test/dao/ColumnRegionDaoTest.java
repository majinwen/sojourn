/**
 * @FileName: ColumnRegionDaoTest.java
 * @Package com.ziroom.minsu.services.cms.test.dao
 * 
 * @author bushujie
 * @created 2016年11月10日 下午6:17:18
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.test.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.cms.ColumnRegionEntity;
import com.ziroom.minsu.services.cms.dao.ColumnRegionDao;
import com.ziroom.minsu.services.cms.dto.ColumnRegionRequest;
import com.ziroom.minsu.services.cms.entity.ColumnRegionVo;
import com.ziroom.minsu.services.cms.test.base.BaseTest;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class ColumnRegionDaoTest extends BaseTest{
	
	@Resource(name="cms.columnRegionDao")
	private ColumnRegionDao columnRegionDao;
	
	@Test
	public void findColumnRegionListTest(){
		ColumnRegionRequest columnRegionRequest=new ColumnRegionRequest();
		columnRegionRequest.setColumnCityFid("8a9e98985847fdfc015847fdfcbf0000");
		PagingResult<ColumnRegionVo> list=columnRegionDao.findColumnRegionList(columnRegionRequest);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void insertColumnRegionTest(){
		ColumnRegionEntity columnRegionEntity=new ColumnRegionEntity();
		columnRegionEntity.setFid(UUIDGenerator.hexUUID());
		columnRegionEntity.setColumnCityFid("8a9e98985847fdfc015847fdfcbf0000");
		columnRegionEntity.setRegionFid("18a9e9aa856126a5b0156126a5bd30000");
		columnRegionDao.insertColumnRegion(columnRegionEntity);
	}
	
	@Test
	public void findNextOrderSortByColumnCityFidTest(){
		int maxSort=columnRegionDao.findNextOrderSortByColumnCityFid("8a9e98985847fdfc015847fdfcbf0000");
		System.err.println(maxSort);
	}
}
