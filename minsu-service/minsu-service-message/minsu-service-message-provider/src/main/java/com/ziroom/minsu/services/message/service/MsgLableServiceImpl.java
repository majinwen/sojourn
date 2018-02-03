/**
 * @FileName: MsgLableServiceImpl.java
 * @Package com.ziroom.minsu.services.message.service
 * 
 * @author yd
 * @created 2016年4月18日 下午3:11:57
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.MsgLableEntity;
import com.ziroom.minsu.services.message.dao.MsgLableDao;
import com.ziroom.minsu.services.message.dto.MsgLableRequest;
import com.ziroom.minsu.services.message.entity.MsgKeyVo;

/**
 * <p>回复标签的业务层service</p>
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
@Service("message.msgLableServiceImpl")
public class MsgLableServiceImpl {


	/**
	 * 日志对象
	 */
	private static Logger logger =  LoggerFactory.getLogger(MsgLableServiceImpl.class);
	
	@Resource(name = "message.msgLableDao")
	private MsgLableDao msgLableDao;
	
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
		return msgLableDao.queryByPage(msgLableRequest);
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
		return msgLableDao.queryByCondition(msgLableRequest);
	}
	
	/**
	 * 
	 * 条件查询标签关键词 (代理层需要严格校验 区分 后台 和前台)
	 *
	 * @author yd
	 * @created 2016年4月18日 上午11:20:39
	 *
	 * @param msgLableRequest
	 * @return
	 */
	public List<MsgKeyVo> queryMsgKeyByCondition(MsgLableRequest msgLableRequest){
		return msgLableDao.queryMsgKeyByCondition(msgLableRequest);
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
		return this.msgLableDao.save(msgLableEntity);
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
		return this.msgLableDao.updateByFid(msgLableRequest);
	}
}
