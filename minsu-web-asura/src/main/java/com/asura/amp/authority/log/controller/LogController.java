/**
 * @FileName: LogController.java
 * @Package com.asura.amp.authority.log.controller
 * 
 * @author zhangshaobin
 * @created 2013-3-14 下午9:59:01
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.authority.log.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asura.amp.authority.entity.Log;
import com.asura.amp.authority.log.service.LogService;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchModel;

/**
 * <p>
 * TODO
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
@Controller
@RequestMapping("authority/log")
public class LogController {

	@Autowired
	private LogService logService;

	/**
	 * 
	 * 查询日志数据
	 *
	 * @author zhangshaobin
	 * @created 2013-3-14 下午10:03:38
	 *
	 * @param searchModel
	 * @param model
	 */
	@RequestMapping("logList")
	public void logList(SearchModel searchModel, Model model) {
		if (searchModel.getPage() == 0) {
			searchModel.setPage(1);
		}
		PagingResult<Log> pr = this.logService.findForPage(searchModel);
		model.addAttribute("PAGING_RESULT", pr);
	}

}
