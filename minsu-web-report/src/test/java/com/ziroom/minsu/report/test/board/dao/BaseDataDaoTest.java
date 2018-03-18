/**
 * @FileName: BaseDataDaoTest.java
 * @Package com.ziroom.minsu.report.board.dao
 * 
 * @author bushujie
 * @created 2017年1月12日 下午4:07:27
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.test.board.dao;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.ziroom.minsu.report.board.dto.EmpTargetItemRequest;
import com.ziroom.minsu.report.board.vo.EmpTargetItem;
import com.ziroom.minsu.report.board.dao.BaseDataDao;
import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;

import base.BaseTest;

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
public class BaseDataDaoTest extends BaseTest{
	
	@Resource(name="report.baseDataDao")
	private BaseDataDao baseDataDao;
	
	@Test
	public void findGaurdAreaByPageTest(){
		EmpTargetItemRequest guardAreaR=new EmpTargetItemRequest();
		PagingResult<EmpTargetItem> pagingResult=baseDataDao.findGaurdAreaByPage(guardAreaR);
		System.err.println(JsonEntityTransform.Object2Json(pagingResult.getRows()));
	}
	
	@Test
	public void findTargetByConditionTest(){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("countryCode", "100000");
		paramMap.put("targetMonth", "2017-01");
		HashMap hashMaps=baseDataDao.findTargetByCondition(paramMap);
		System.err.println(JsonEntityTransform.Object2Json(hashMaps));
	}
}
