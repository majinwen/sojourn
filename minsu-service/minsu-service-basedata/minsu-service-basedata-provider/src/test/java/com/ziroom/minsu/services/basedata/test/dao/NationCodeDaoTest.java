/**
 * @FileName: NationCodeDaoTest.java
 * @Package com.ziroom.minsu.services.basedata.test.dao
 * 
 * @author bushujie
 * @created 2017年4月11日 下午3:04:42
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.test.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.base.NationCodeEntity;
import com.ziroom.minsu.services.basedata.dao.NationCodeDao;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;

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
public class NationCodeDaoTest extends BaseTest{
	
	@Resource(name="basedata.nationCodeDao")
	private NationCodeDao nationCodeDao;
	
	@Test
	public void findNationCodeList(){
		List<NationCodeEntity> list=nationCodeDao.findNationCodeList();
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
}
