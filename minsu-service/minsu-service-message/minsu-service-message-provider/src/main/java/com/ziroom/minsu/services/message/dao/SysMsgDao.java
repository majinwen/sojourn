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

import java.util.List;

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
import com.ziroom.minsu.entity.message.SysMsgEntity;
import com.ziroom.minsu.services.message.dto.SysMsgRequest;

/**
 * 系统消息dao
 * @author jixd on 2016年4月16日
 * @since 1.0
 * @version 1.0
 */
@Repository("message.sysMsgDao")
public class SysMsgDao {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(SysMsgDao.class);

	private String SQLID = "message.sysMsgDao.";

	@Autowired
	@Qualifier("message.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 增加系统消息
	 * @author jixd on 2016年4月16日
	 */
	public int saveSysMsg(SysMsgEntity sysMsgEntity){
		int index = -1;
		if(sysMsgEntity != null){
			if(Check.NuNStr(sysMsgEntity.getFid())){
				sysMsgEntity.setFid(UUIDGenerator.hexUUID());
			}
			index = mybatisDaoContext.save(SQLID+"insertSelective", sysMsgEntity);
		}
		return index;
	}
	
	/**
	 * 查询消息列表
	 * @author jixd on 2016年4月16日
	 */
	public PagingResult<SysMsgEntity> queryByTargetUid(SysMsgRequest sysMsgRequest){
		if(Check.NuNObj(sysMsgRequest)) {
			LogUtil.error(logger, "sysMsgRequest is null");
			throw new BusinessException("sysMsgRequest is null");
		}
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(sysMsgRequest.getLimit());
		pageBounds.setPage(sysMsgRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID+"queryByPage", SysMsgEntity.class, sysMsgRequest,pageBounds);
	}
	
	/**
	 * 批量插入系統消息
	 * @author jixd on 2016年4月16日
	 */
	public int saveSysMsgBatch(List<SysMsgEntity> list){
		if(Check.NuNCollection(list)){
			LogUtil.error(logger, "sysMsglist is null");
			throw new BusinessException("sysMsglist is null");
		}
		for(SysMsgEntity sysMsgEntity: list){
			if(Check.NuNStr(sysMsgEntity.getFid())){
				sysMsgEntity.setFid(UUIDGenerator.hexUUID());
			}
			mybatisDaoContext.save(SQLID+"insertSelective", sysMsgEntity);
		}
		return list.size();
	}
	/**
	 * 刪除系統消息
	 * @author jixd on 2016年4月16日
	 */
	public int deleteSysMsg(SysMsgEntity sysMsgEntity){
		if(Check.NuNObj(sysMsgEntity)){
			return -1;
		}
		return mybatisDaoContext.update(SQLID+"updateIsDelByFid", sysMsgEntity);
	}
	/**
	 * 
	 * 更新为已读状态
	 *
	 * @author jixd
	 * @created 2016年4月21日 下午4:18:40
	 *
	 * @param sysMsgEntity
	 * @return
	 */
	public int updateSysMsgRead(SysMsgEntity sysMsgEntity){
		if(Check.NuNObj(sysMsgEntity)){
			return -1;
		}
		return mybatisDaoContext.update(SQLID+"updateReadByFid", sysMsgEntity);
	}
	
}
