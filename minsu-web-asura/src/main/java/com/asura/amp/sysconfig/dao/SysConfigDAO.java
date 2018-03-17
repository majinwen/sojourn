/**
 * @FileName: SysConfigDAO.java
 * @Package com.asura.amp.sysconfig.dao
 * 
 * @author zhangshaobin
 * @created 2013-1-16 下午5:20:41
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.sysconfig.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.asura.amp.sysconfig.entity.SysConfig;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchModel;
import com.asura.framework.dao.BaseIbatisDaoContext;

/**
 * <p>系统配置DAO</p>
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
@Repository("sysConfigDao")
public class SysConfigDAO {

	public static final String SQLMAP_NAMESPACE = "com.asura.management.sysconfig.dao";

	@Resource(name = "ampBaseIbatisDaoContext")
	private BaseIbatisDaoContext daoContext;

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
	public PagingResult<SysConfig> findSysConfigByPager(SearchModel searchModel) {
		final PagingResult<SysConfig> result = daoContext.findForPage(SQLMAP_NAMESPACE + ".countSysConfigByCondition",//显示的总的条数
				SQLMAP_NAMESPACE + ".findSysConfigByCondition", //分页信息情况
				searchModel, SysConfig.class);
		return result;
	}

	/**
	 * 
	 * 查询所有的系统参数信息
	 *
	 * @author zhangshaobin
	 * @created 2014年12月1日 下午12:21:35
	 *
	 * @return
	 */
	public List<SysConfig> findAllSysConfig() {
		return daoContext.findAll(SQLMAP_NAMESPACE + ".findAllSysConfig");
	}

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
	public int saveSysConfig(SysConfig entity) {
		return Integer.class.cast(daoContext.save(SQLMAP_NAMESPACE + ".saveSysConfig", entity));
	}

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
	public int updateSysConfig(SysConfig entity) {
		return this.daoContext.update(SQLMAP_NAMESPACE + ".updateSysConfig", entity);
	}

	/**
	 * 
	 * 根据type-code逻辑删除数据
	 *
	 * @author zhangshaobin
	 * @created 2014年12月1日 下午12:31:46
	 *
	 * @param ids
	 * @return
	 */
	public int deleteSysConfig(String type, String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("code", code);
		return this.daoContext.update(SQLMAP_NAMESPACE + ".deleteSysConfig", map);
	}
}
