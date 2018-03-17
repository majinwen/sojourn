/**
 * @FileName: LogService.java
 * @Package com.asura.amp.authority.log.service
 * 
 * @author zhangshaobin
 * @created 2013-2-2 下午4:06:26
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.authority.log.service;

import com.asura.amp.authority.entity.Log;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchModel;

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
public interface LogService {
	
	/**
	 * 
	 * 保存操作日志
	 *
	 * @author zhangshaobin
	 * @created 2013-2-2 下午4:01:07
	 *
	 * @param log
	 */
	public void save(Log log);
	
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
	public PagingResult<Log> findForPage(SearchModel searchModel);

}
