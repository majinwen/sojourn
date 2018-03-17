/**
 * @FileName: SysConfigService.java
 * @Package com.sfbest.management.sysconfig.service
 * 
 * @author zhangshaobin
 * @created 2013-1-16 下午5:20:41
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.sysconfig.service;

import java.util.List;

import com.asura.amp.sysconfig.entity.SysConfig;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchModel;

/**
 * <p>系统配置项Service接口类</p>
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
public interface SysConfigService {

	/**
	 * 
	 * 分页查询系统参数
	 *
	 * @author zhangshaobin
	 * @created 2014年12月1日 下午12:20:16
	 *
	 * @param searchModel
	 * @return
	 */
	public PagingResult<SysConfig> findSysConfigByPager(SearchModel searchModel);

	/**
	 * 
	 * 查询所有的系统参数信息
	 *
	 * @author zhangshaobin
	 * @created 2014年12月1日 下午12:21:35
	 *
	 * @return
	 */
	public List<SysConfig> findAllSysConfig();

	/**
	 * 
	 * 保存系统参数
	 *
	 * @author zhangshaobin
	 * @created 2014年12月1日 下午12:23:32
	 *
	 * @param entity
	 * @return
	 */
	public int saveSysConfig(SysConfig entity);

	/**
	 * 
	 * 保存系统参数
	 *
	 * @author zhangshaobin
	 * @created 2014年12月1日 下午12:24:33
	 *
	 * @param entity
	 * @return
	 */
	public int updateSysConfig(SysConfig entity);

	/**
	 * 
	 * 批量删除
	 *
	 * @author zhangshaobin
	 * @created 2014年12月1日 下午12:31:46
	 *
	 * @param ids
	 * @return
	 */
	public int deleteSysConfig(String type, String code);
}
