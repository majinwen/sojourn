/**
 * @FileName: LogServiceImpl.java
 * @Package com.asura.amp.authority.log.service.impl
 * 
 * @author zhangshaobin
 * @created 2013-2-2 下午4:06:48
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.authority.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asura.amp.authority.entity.Log;
import com.asura.amp.authority.log.dao.LogDao;
import com.asura.amp.authority.log.service.LogService;
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
@Service("logService")
public class LogServiceImpl implements LogService {
	
	@Autowired
	private LogDao logDao;

	@Override
	public void save(Log log) {
		this.logDao.save(log);
	}

	@Override
	public PagingResult<Log> findForPage(SearchModel searchModel) {
		return this.logDao.findForPage(searchModel);
	}

}
