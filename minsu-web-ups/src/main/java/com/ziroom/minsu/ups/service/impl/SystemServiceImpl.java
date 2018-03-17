/**
 * @FileName: SystemService.java
 * @Package com.ziroom.minsu.ups.service
 * 
 * @author bushujie
 * @created 2016年12月1日 下午4:14:56
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.ups.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.sys.ResourceEntity;
import com.ziroom.minsu.entity.sys.SystemsEntity;
import com.ziroom.minsu.ups.dao.ResourceDao;
import com.ziroom.minsu.ups.dao.SystemsDao;
import com.ziroom.minsu.ups.dto.SystemsRequest;
import com.ziroom.minsu.ups.service.ISystemService;

/**
 * <p>系统信息业务层</p>
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
@Service("ups.systemService")
public class SystemServiceImpl implements ISystemService{
	
	@Resource(name="ups.systemsDao")
	private SystemsDao systemsDao;
	
	@Resource(name="ups.resourceDao")
	private ResourceDao resourceDao;
	
	/**
	 * 
	 * 插入系统信息
	 *
	 * @author bushujie
	 * @created 2016年12月1日 下午4:26:49
	 *
	 * @param systemsEntity
	 */
	public void insertSystem(SystemsEntity systemsEntity){
		//插入系统信息
		systemsDao.insertSystems(systemsEntity);
		//插入系统顶级菜单
		ResourceEntity resourceEntity=new ResourceEntity();
		resourceEntity.setFid(UUIDGenerator.hexUUID());
		resourceEntity.setSystemsFid(systemsEntity.getFid());
		resourceEntity.setResName(systemsEntity.getSysName()+"菜单树");
		resourceEntity.setOrderCode(1);
		resourceEntity.setResType(2);
		resourceDao.insertMenuResource(resourceEntity);
	}
	
	/**
	 * 
	 * 分页查询系统信息
	 *
	 * @author bushujie
	 * @created 2016年12月2日 上午10:57:04
	 *
	 * @param systemsRequest
	 * @return
	 */
	public PagingResult<SystemsEntity> findSystemsByPage(SystemsRequest systemsRequest){
		return systemsDao.findSystemsByPage(systemsRequest);
	}
	
	/**
	 * 
	 * fid查询系统信息
	 *
	 * @author bushujie
	 * @created 2016年12月2日 下午4:18:20
	 *
	 * @param fid
	 * @return
	 */
	public SystemsEntity getSystemsEntityByFid(String fid){
		return systemsDao.getSystemsEntityByFid(fid);
	}
	
	/**
	 * 
	 * 更新系统信息
	 *
	 * @author bushujie
	 * @created 2016年12月2日 下午5:06:07
	 *
	 * @param systemsEntity
	 * @return
	 */
	public int updateSystem(SystemsEntity systemsEntity){
		if(systemsEntity.getIsDel()!=null&&systemsEntity.getIsDel()==1){
			resourceDao.delResBySystemFid(systemsEntity.getFid());
		}
		return systemsDao.updateSystems(systemsEntity);
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.ups.service.ISystemService#getSystemsEntityByCode(java.lang.String)
	 */
	@Override
	public SystemsEntity getSystemsEntityByCode(String sysCode) {
		return systemsDao.getSystemsEntityByCode(sysCode);
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.ups.service.ISystemService#findAllSystem()
	 */
	@Override
	public List<SystemsEntity> findAllSystem() {
		return systemsDao.findAllSystem();
	}

	@Override
	public List<SystemsEntity> findHasSysByUid(String fid) {
		return systemsDao.findHasSysByUid(fid);
	}
}
