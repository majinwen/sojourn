/**
 * @FileName: NationCodeDao.java
 * @Package com.ziroom.minsu.services.basedata.dao
 * 
 * @author bushujie
 * @created 2017年4月11日 下午2:26:05
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.base.NationCodeEntity;

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
@Repository("basedata.nationCodeDao")
public class NationCodeDao {
	
	private String SQLID = "basedata.nationCodeDao.";

	@Autowired
	@Qualifier("basedata.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	
	/**
	 * 
	 * 查询国家码列表
	 *
	 * @author bushujie
	 * @created 2017年4月11日 下午2:59:04
	 *
	 * @return
	 */
	public List<NationCodeEntity> findNationCodeList(){
		return mybatisDaoContext.findAll(SQLID+"findNationCodeList");
	}

}
