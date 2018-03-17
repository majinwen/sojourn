/**
 * @FileName: QuartzDao.java
 * @Package com.asura.management.quartz.dao
 * 
 * @author zhangshaobin
 * @created 2013-2-5 下午5:15:22
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.quartz.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.amp.quartz.entity.Trigger;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchModel;
import com.asura.framework.dao.old.BaseIbatisDAO;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * <p>操作触发器dao层</p>
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
@Repository("triggerDao")
public class TriggerDao extends BaseIbatisDAO{
	
	@Autowired
	public void setSystemSqlMapClient(@Qualifier("quartzSqlMapClient")SqlMapClient sqlMapClient) {
		super.setCurrentSqlMapClient(sqlMapClient);
	}
	
	/**
	 * 
	 * 分页查询
	 *
	 * @author zhangshaobin
	 * @created 2013-2-21 下午2:28:39
	 *
	 * @param searchModel
	 * @return
	 */
	public PagingResult<Trigger> findForPage(SearchModel searchModel) {
		return this.findForPage(
						"com.asura.management.quartz.dao.map.findCountBySearchCondition",// 查询记录数的sqlid
						"com.asura.management.quartz.dao.map.findBySearchCondition",// 查询数据的sqlid
						searchModel, Trigger.class);
	}
	

}
