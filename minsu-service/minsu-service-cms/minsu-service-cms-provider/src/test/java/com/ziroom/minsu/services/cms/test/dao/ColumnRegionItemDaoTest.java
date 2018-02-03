/**
 * @FileName: ColumnRegionItemDaoTest.java
 * @Package com.ziroom.minsu.services.cms.test.dao
 * 
 * @author bushujie
 * @created 2017年1月9日 下午12:01:08
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.ziroom.minsu.services.cms.dao.ColumnRegionItemDao;
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
public class ColumnRegionItemDaoTest extends BaseTest{
	
	@Resource(name="cms.columnRegionItemDao")
	private ColumnRegionItemDao columnRegionItemDao;
	
	
	@Test
	public void findNextOrderSortByColumnRegionFidTest(){
		System.err.println(columnRegionItemDao.findNextOrderSortByColumnRegionFid("8a9e98985861b6f8015861b6f8f50000"));
	}
}
