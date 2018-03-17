/**
 * @FileName: TaskServiceImpl.java
 * @Package com.asura.amp.quartz.service.impl
 * 
 * @author zhangshaobin
 * @created 2012-12-11 下午5:22:22
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.quartz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asura.amp.quartz.dao.TriggerDao;
import com.asura.amp.quartz.entity.Trigger;
import com.asura.amp.quartz.service.TriggerService;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchModel;

/**
 * <p>
 * 定时任务操作Service
 * </p>
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
@Service("triggerService")
public class TriggerServiceImpl implements TriggerService {

	@Autowired
	private TriggerDao triggerDao;

	@Override
	public PagingResult<Trigger> findForPage(SearchModel searchModel) {
		return this.triggerDao.findForPage(searchModel);
	}
}
