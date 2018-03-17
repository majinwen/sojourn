/**
 * @FileName: TaskService.java
 * @Package com.asura.amp.quartz.service
 * 
 * @author zhangshaobin
 * @created 2012-12-11 下午5:27:53
 * 
 * Copyright ? 2011-2015 asura
 */
package com.asura.amp.quartz.service;

import com.asura.amp.quartz.entity.Trigger;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchModel;

/**
 * <p>
 * 任务Service
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
public interface TriggerService {

	/**
	 * 
	 * 分页查询
	 *
	 * @author zhangshaobin
	 * @created 2013-2-21 下午2:32:35
	 *
	 * @param searchModel
	 * @return
	 */
	public PagingResult<Trigger> findForPage(SearchModel searchModel);
	
}
