/**
 * @FileName: SysConfigServiceImpl.java
 * @Package com.asura.management.sysconfig.service.impl
 * 
 * @author zhangshaobin
 * @created 2013-1-16 下午5:20:41
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.sysconfig.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asura.amp.sysconfig.dao.SysConfigDAO;
import com.asura.amp.sysconfig.entity.SysConfig;
import com.asura.amp.sysconfig.service.SysConfigService;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchModel;

/**
 * <p>系统配置项Service实现类</p>
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
@Service("sysConfigService")
public class SysConfigServiceImpl implements SysConfigService {

	@Autowired
	private SysConfigDAO sysConfigDao;

	/* (non-Javadoc)
	 * @see com.asura.amp.sysconfig.service.SysConfigService#findSysConfigByPager(com.asura.framework.base.paging.SearchModel)
	 */
	@Override
	public PagingResult<SysConfig> findSysConfigByPager(SearchModel searchModel) {
		// TODO Auto-generated method stub
		return sysConfigDao.findSysConfigByPager(searchModel);
	}

	/* (non-Javadoc)
	 * @see com.asura.amp.sysconfig.service.SysConfigService#findAllSysConfig()
	 */
	@Override
	public List<SysConfig> findAllSysConfig() {
		// TODO Auto-generated method stub
		return sysConfigDao.findAllSysConfig();
	}

	/* (non-Javadoc)
	 * @see com.asura.amp.sysconfig.service.SysConfigService#saveSysConfig(com.asura.amp.sysconfig.entity.SysConfig)
	 */
	@Override
	public int saveSysConfig(SysConfig entity) {
		// TODO Auto-generated method stub
		return sysConfigDao.saveSysConfig(entity);
	}

	/* (non-Javadoc)
	 * @see com.asura.amp.sysconfig.service.SysConfigService#updateSysConfig(com.asura.amp.sysconfig.entity.SysConfig)
	 */
	@Override
	public int updateSysConfig(SysConfig entity) {
		// TODO Auto-generated method stub
		return sysConfigDao.updateSysConfig(entity);
	}

	/* (non-Javadoc)
	 * @see com.asura.amp.sysconfig.service.SysConfigService#batchDeleteSysConfig(java.lang.String)
	 */
	@Override
	public int deleteSysConfig(String type, String code) {
		// TODO Auto-generated method stub
		return sysConfigDao.deleteSysConfig(type, code);
	}

}
