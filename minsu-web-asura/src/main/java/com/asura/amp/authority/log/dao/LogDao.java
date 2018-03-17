/**
 * @FileName: LogDao.java
 * @Package com.asura.amp.authority.log.dao
 * 
 * @author zhangshaobin
 * @created 2013-2-2 下午3:43:46
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.authority.log.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.asura.amp.authority.entity.Log;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchModel;
import com.asura.framework.dao.old.BaseIbatisDAO;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
@Repository("logDao")
public class LogDao extends BaseIbatisDAO{

	@Autowired
	public void setSystemSqlMapClient(SqlMapClient ampSqlMapClient) {
		super.setCurrentSqlMapClient(ampSqlMapClient);
	}
	
	/**
	 * 
	 * 保存操作日志
	 *
	 * @author zhangshaobin
	 * @created 2013-2-2 下午4:01:07
	 *
	 * @param log
	 */
	public void save(Log log){
		this.save("com.asura.management.authority.log.dao.save", log);
	}
	
	/**
	 * 
	 * 分页查询
	 *
	 * @author zhangshaobin
	 * @created 2013-2-2 下午4:03:30
	 *
	 * @param searchModel
	 * @return
	 */
	public PagingResult<Log> findForPage(SearchModel searchModel){
		return this.findForPage(
				"com.asura.management.authority.log.dao.findCountBySearchCondition",// 查询记录数的sqlid
				"com.asura.management.authority.log.dao.findBySearchCondition",// 查询数据的sqlid
				searchModel, Log.class);
	}
}
