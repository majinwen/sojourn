/**
 * @FileName: MsgBaseDao.java
 * @Package com.ziroom.minsu.services.message.dao.map
 * 
 * @author yd
 * @created 2016年4月16日 下午3:18:34
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.SysMsgManagerEntity;
import com.ziroom.minsu.services.message.dto.SysMsgManagerRequest;

/**
 * @author jixd on 2016年4月16日
 * @since 1.0
 * @version 1.0
 */
@Repository("message.sysMsgManagerDao")
public class SysMsgManagerDao {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(SysMsgManagerDao.class);

	private String SQLID = "message.sysMsgManagerDao.";

	@Autowired
	@Qualifier("message.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 插入消息
	 * @author jixd on 2016年4月16日
	 */
	public int saveSysMsgManagerEntity(SysMsgManagerEntity sysMsgManagerEntity){
		int index = -1;
		if(sysMsgManagerEntity != null){
			if(Check.NuNStr(sysMsgManagerEntity.getFid())){
				sysMsgManagerEntity.setFid(UUIDGenerator.hexUUID());
			}
			index = mybatisDaoContext.save(SQLID+"insertSelective", sysMsgManagerEntity);
		}
		return index;
	}
	/**
	 * 发布消息
	 * @author jixd on 2016年4月16日
	 */
	public int releaseSysMsgByFid(SysMsgManagerEntity sysMsgManagerEntity){
		if(sysMsgManagerEntity == null || Check.NuNStr(sysMsgManagerEntity.getFid())){
			return -1;
		}
		return mybatisDaoContext.update(SQLID+"releaseMsgManagerByfid", sysMsgManagerEntity);
	}
	/**
	 * 删除待发布消息
	 * @author jixd on 2016年4月16日
	 */
	public int deleteSysMsgManagerByFid(SysMsgManagerEntity sysMsgManagerEntity){
		if(sysMsgManagerEntity == null || Check.NuNStr(sysMsgManagerEntity.getFid())){
			return -1;
		}
		return mybatisDaoContext.update(SQLID+"deleteMsgManagerByfid", sysMsgManagerEntity);
	}
	
	/**
	 * 修改消息
	 * @author jixd on 2016年4月16日
	 */
	public int updateSysMsgManager(SysMsgManagerEntity sysMsgManagerEntity){
		if(sysMsgManagerEntity != null && !Check.NuNStr( sysMsgManagerEntity.getFid())){
			return mybatisDaoContext.update(SQLID+"updateByFid", sysMsgManagerEntity);
		}
		return -1;
	}
	/**
	 * 查询消息
	 * @author jixd on 2016年4月16日
	 */
	public PagingResult<SysMsgManagerEntity> querySysMsgManager(SysMsgManagerRequest sysMsgManagerRequest){
		if(Check.NuNObj(sysMsgManagerRequest)) {
			LogUtil.error(logger, "sysMsgManagerRequest is null");
			throw new BusinessException("sysMsgManagerRequest is null");
		}
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(sysMsgManagerRequest.getLimit());
		pageBounds.setPage(sysMsgManagerRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID+"queryByPage", SysMsgManagerEntity.class, sysMsgManagerRequest,pageBounds);
	}
	/**
	 * 更加fid查询
	 * @author jixd on 2016年4月19日
	 */
	public SysMsgManagerEntity findSysMsgManagerByFid(String fid){
		if(Check.NuNStr(fid)){
			LogUtil.error(logger, "fid is null");
			throw new BusinessException("fid is null");
		}
		return mybatisDaoContext.findOne(SQLID+"selectByFid", SysMsgManagerEntity.class, fid);
	}
}
