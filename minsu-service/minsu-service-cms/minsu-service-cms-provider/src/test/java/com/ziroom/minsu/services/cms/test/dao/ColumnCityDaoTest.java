/**
 * @FileName: ColumnCityDaoTest.java
 * @Package com.ziroom.minsu.services.cms.test.dao
 * 
 * @author bushujie
 * @created 2016年11月9日 下午2:52:09
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.test.dao;

import javax.annotation.Resource;

import com.ziroom.minsu.services.cms.entity.FileRegionsVo;
import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.cms.ColumnCityEntity;
import com.ziroom.minsu.services.cms.dao.ColumnCityDao;
import com.ziroom.minsu.services.cms.dto.ColumnCityRequest;
import com.ziroom.minsu.services.cms.entity.ColumnCityVo;
import com.ziroom.minsu.services.cms.test.base.BaseTest;

/**
 * <p>城市专栏dao测试</p>
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
public class ColumnCityDaoTest  extends BaseTest{
	
	@Resource(name="cms.columnCityDao")
	private ColumnCityDao columnCityDao;
	
	
	@Test
	public void findColumnCityList(){
		ColumnCityRequest columnCityRequest=new ColumnCityRequest();
		PagingResult<ColumnCityVo> result=columnCityDao.findColumnCityList(columnCityRequest);
		System.err.println(result.getTotal());
	}
	
	@Test
	public void insertColumnCityTest(){
		ColumnCityEntity cityEntity=new ColumnCityEntity();
		cityEntity.setFid(UUIDGenerator.hexUUID());
		cityEntity.setCityCode("110100");
		cityEntity.setTempFid("8a9e98985841d0b4015841d0b4d90000");
		cityEntity.setColTitle("测试专栏");
		columnCityDao.insertColumnCity(cityEntity);
	}
	
	@Test
	public void getColumnCityByFidTest(){
		ColumnCityEntity cityEntity=columnCityDao.getColumnCityByFid("8a9e98985847fdfc015847fdfcbf0000");
		System.err.println(JsonEntityTransform.Object2Json(cityEntity));
	}
	
	@Test
	public void updateColumnCityTest(){
		ColumnCityEntity cityEntity=new ColumnCityEntity();
		cityEntity.setFid("8a9e98985847fdfc015847fdfcbf0000");
		cityEntity.setCreateFid("8a9e9aaf537e3f7501537e3f75af0000");
		int upNum=columnCityDao.updateColumnCity(cityEntity);
		System.err.println(upNum);
	}

	@Test
	public void findFileRegionsByCityCodeTest(){
		FileRegionsVo fileRegionsVo = columnCityDao.findFileRegionsByCityCode("510100");
		String resultJson = JsonEntityTransform.Object2Json(fileRegionsVo);
		System.err.println(resultJson);
	}
}
