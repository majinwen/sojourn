/**
 * @FileName: MsgLableDao.java
 * @Package com.ziroom.minsu.services.message.dao
 * 
 * @author yd
 * @created 2016年4月16日 下午7:08:21
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

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.MsgLableEntity;
import com.ziroom.minsu.services.message.dto.MsgLableRequest;
import com.ziroom.minsu.services.message.entity.MsgKeyVo;

/**
 * <p>消息标签持久化</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
@Repository("message.msgLableDao")
public class MsgLableDao {
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(MsgLableDao.class);

	private String SQLID = "message.msgLableDao.";

	@Autowired
	@Qualifier("message.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 
	 * 条件分页查询
	 *
	 * @author yd
	 * @created 2016年4月16日 下午7:17:12
	 *
	 * @return
	 */
	public PagingResult<MsgLableEntity> queryByPage(MsgLableRequest msgLableRequest){
		
		LogUtil.info(logger, "当前查询条件msgLableRequest={}", msgLableRequest);
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(msgLableRequest.getLimit());
		pageBounds.setPage(msgLableRequest.getPage());
		
		PagingResult<MsgLableEntity> lisPagingResult = null;
		try {
			lisPagingResult = mybatisDaoContext.findForPage(SQLID + "queryByPage", MsgLableEntity.class, msgLableRequest, pageBounds);
		} catch (Exception e) {
		LogUtil.error(logger, "查询错误，错误异常e={}",e);
		}
		return lisPagingResult;
	}
	
	/**
	 * 
	 * 条件查询
	 *
	 * @author yd
	 * @created 2016年4月16日 下午7:25:45
	 *
	 * @param msgLableRequest
	 * @return
	 */
	public List<MsgLableEntity> queryByCondition(MsgLableRequest msgLableRequest){
		LogUtil.info(logger, "当前查询条件msgLableRequest={}", msgLableRequest);
		return mybatisDaoContext.findAll(SQLID+"queryByPage", msgLableRequest);
	}
	
	/**
	 * 
	 * 条件查询标签关键词
	 *
	 * @author yd
	 * @created 2016年4月18日 上午11:20:39
	 *
	 * @param msgLableRequest
	 * @return
	 */
	public List<MsgKeyVo> queryMsgKeyByCondition(MsgLableRequest msgLableRequest){
		
		if(Check.NuNObj(msgLableRequest)){
			LogUtil.info(logger,"msgLableRequest is null");
		}
		LogUtil.info(logger, "查询条件msgLableRequest={}",msgLableRequest);
		return this.mybatisDaoContext.findAll(SQLID+"queryMsgKeyByCondition",MsgKeyVo.class, msgLableRequest);
	}
	
	/**
	 * 
	 * 添加实体
	 *
	 * @author yd
	 * @created 2016年4月16日 下午7:37:15
	 *
	 * @param msgLableEntity
	 * @return
	 */
	public int save(MsgLableEntity msgLableEntity){
		if(Check.NuNObj(msgLableEntity)){
			LogUtil.info(logger,"msgLableEntity is null");
			return 0;
		}
		if(Check.NuNStr(msgLableEntity.getFid() )) msgLableEntity.setFid(UUIDGenerator.hexUUID());
		return this.mybatisDaoContext.save(SQLID+"insertSelective", msgLableEntity);
	}
	
	/**
	 * 
	 * 条件修改 一般是房东修改
	 *
	 * @author yd
	 * @created 2016年4月16日 下午7:39:30
	 *
	 * @param msgLableRequest
	 * @return
	 */
	public int updateByFid(MsgLableEntity msgLableRequest){
		
		if(Check.NuNObj(msgLableRequest)||Check.NuNStr(msgLableRequest.getFid())){
			LogUtil.info(logger,"msgLableRequest or fid is null");
			return 0;
		}
		return this.mybatisDaoContext.update(SQLID+"updateByFid", msgLableRequest);
	}
}
