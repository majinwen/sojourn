/**
 * @FileName: ColumnTemplateDaoTest.java
 * @Package com.ziroom.minsu.services.cms.test.dao
 * 
 * @author bushujie
 * @created 2016年11月7日 下午6:19:31
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.test.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.cms.ColumnTemplateEntity;
import com.ziroom.minsu.services.cms.dao.ColumnTemplateDao;
import com.ziroom.minsu.services.cms.dto.ColumnTemplateRequest;
import com.ziroom.minsu.services.cms.entity.ColumnTemplateVo;
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
public class ColumnTemplateDaoTest  extends BaseTest{
	
	@Resource(name="cms.columnTemplateDao")
	private ColumnTemplateDao columnTemplateDao;
	
	
	@Test
	public void findColumnTemplateListTest(){
		ColumnTemplateRequest templateRequest=new ColumnTemplateRequest();
		PagingResult<ColumnTemplateVo> result=columnTemplateDao.findColumnTemplateList(templateRequest);
		System.err.println(result.getTotal());
	}
	
	@Test
	public void getColumnTemplateEntityByFidTest(){
		ColumnTemplateEntity columnTemplateEntity=columnTemplateDao.getColumnTemplateEntityByFid("8a9e98985843591a015843591a6e0000");
		System.err.println(JsonEntityTransform.Object2Json(columnTemplateEntity));
	}
	
	@Test
	public void findAllRegTemplateTest(){
		List<ColumnTemplateEntity> list=columnTemplateDao.findAllRegTemplate();
		System.err.println(list.size());
	}
}
