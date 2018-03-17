/**
 * @FileName: ISystemService.java
 * @Package com.ziroom.minsu.ups.service
 * 
 * @author bushujie
 * @created 2016年12月5日 上午10:07:33
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.ups.service;

import java.util.List;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.sys.SystemsEntity;
import com.ziroom.minsu.ups.dto.SystemsRequest;

/**
 * <p>系统信息业务层接口</p>
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
public interface ISystemService {
	
	/**
	 * 
	 * 插入系统信息
	 *
	 * @author bushujie
	 * @created 2016年12月5日 上午10:09:43
	 *
	 * @param systemsEntity
	 */
	public void insertSystem(SystemsEntity systemsEntity);
	
	/**
	 * 
	 * 分页查询系统信息
	 *
	 * @author bushujie
	 * @created 2016年12月5日 上午10:10:09
	 *
	 * @param systemsRequest
	 * @return
	 */
	public PagingResult<SystemsEntity> findSystemsByPage(SystemsRequest systemsRequest);
	
	/**
	 * 
	 * fid查询系统信息
	 *
	 * @author bushujie
	 * @created 2016年12月5日 上午10:10:14
	 *
	 * @param fid
	 * @return
	 */
	public SystemsEntity getSystemsEntityByFid(String fid);
	
	/**
	 * 
	 * 更新系统信息
	 *
	 * @author bushujie
	 * @created 2016年12月5日 上午10:10:22
	 *
	 * @param systemsEntity
	 * @return
	 */
	public int updateSystem(SystemsEntity systemsEntity);
	
	/**
	 * 
	 * 根据code查询系统信息
	 *
	 * @author bushujie
	 * @created 2016年12月7日 下午5:46:11
	 *
	 * @param sysCode
	 * @return
	 */
	public SystemsEntity getSystemsEntityByCode(String sysCode);
	
	/**
	 * 
	 * 查询所有系统列表
	 *
	 * @author bushujie
	 * @created 2016年12月8日 下午2:15:59
	 *
	 * @return
	 */
	public List<SystemsEntity> findAllSystem();

	/**
	 * 查询登陆用户有权限的系统列表
	 * @return
	 */
	List<SystemsEntity> findHasSysByUid(String fid);
}
