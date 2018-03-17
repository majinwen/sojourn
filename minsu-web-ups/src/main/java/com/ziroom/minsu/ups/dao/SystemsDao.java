/**
 * @FileName: SystemsDao.java
 * @Package com.ziroom.minsu.ups.dao
 * 
 * @author bushujie
 * @created 2016年12月1日 下午2:37:48
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.ups.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.sys.SystemsEntity;
import com.ziroom.minsu.ups.dto.SystemsRequest;

/**
 * <p>系统信息Dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@Repository("ups.systemsDao")
public class SystemsDao {
	
	private String SQLID="ups.systemsDao.";
	
	@Autowired
	@Qualifier("ups.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 
	 * 插入系统信息
	 *
	 * @author bushujie
	 * @created 2016年12月1日 下午2:41:06
	 *
	 * @param systemsEntity
	 */
	public void insertSystems(SystemsEntity systemsEntity){
		mybatisDaoContext.save(SQLID+"insertSystems", systemsEntity);
	}
	
	/**
	 * 
	 * fid查询系统信息
	 *
	 * @author bushujie
	 * @created 2016年12月1日 下午2:46:38
	 *
	 * @param fid
	 * @return
	 */
	public SystemsEntity getSystemsEntityByFid(String fid){
		return mybatisDaoContext.findOneSlave(SQLID+"getSystemsEntityByFid", SystemsEntity.class, fid);
	}
	
	/**
	 * 
	 * 分页查询系统信息
	 *
	 * @author bushujie
	 * @created 2016年12月1日 下午4:03:53
	 *
	 * @param systemsRequest
	 * @return
	 */
	public PagingResult<SystemsEntity> findSystemsByPage(SystemsRequest systemsRequest){
		PageBounds pageBounds=new PageBounds();
		pageBounds.setLimit(systemsRequest.getLimit());
		pageBounds.setPage(systemsRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "findSystemsByPage", SystemsEntity.class, systemsRequest, pageBounds);
	}
	
	/**
	 * 
	 * 更新系统信息
	 *
	 * @author bushujie
	 * @created 2016年12月1日 下午4:09:47
	 *
	 * @param systemsEntity
	 * @return
	 */
	public int updateSystems(SystemsEntity systemsEntity){
		return mybatisDaoContext.update(SQLID+"updateSystems", systemsEntity);
	}
	
	/**
	 * 
	 * 系统code查询系统信息
	 *
	 * @author bushujie
	 * @created 2016年12月7日 下午5:39:35
	 *
	 * @param sysCode
	 * @return
	 */
	public SystemsEntity getSystemsEntityByCode(String sysCode){
		return mybatisDaoContext.findOneSlave(SQLID+"getSystemsEntityByCode", SystemsEntity.class, sysCode);
	}
	
	/**
	 * 
	 * 查询所有系统
	 *
	 * @author bushujie
	 * @created 2016年12月8日 下午2:11:10
	 *
	 * @return
	 */
	public List<SystemsEntity> findAllSystem(){
		return mybatisDaoContext.findAll(SQLID+"findAllSystem", SystemsEntity.class);
	}

	/**
	 * 查找用拥有系统列表
	 * @author jixd
	 * @created 2016年12月09日 10:50:45
	 * @param
	 * @return
	 */
	public List<SystemsEntity> findHasSysByUid(String fid){
		return mybatisDaoContext.findAll(SQLID + "findHasSysByUid",SystemsEntity.class,fid);
	}
}
