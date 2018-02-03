/**
 * @FileName: MsgReplySetDao.java
 * @Package com.ziroom.minsu.services.message.dao
 * 
 * @author yd
 * @created 2016年4月18日 上午9:54:37
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

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.MsgReplySetEntity;
import com.ziroom.minsu.services.message.dto.MsgReplySetRequest;

/**
 * <p>测试</p>
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
@Repository("message.msgReplySetDao")
public class MsgReplySetDao {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(MsgReplySetDao.class);
	
	@Autowired
	@Qualifier("message.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	private final static String SQLID = "message.msgReplySetDao.";
	
	/**
	 * 条件查询 自动回复设置实体
	 *
	 * @author yd
	 * @created 2016年4月18日 上午9:57:38
	 *
	 * @param msgReplySetRequest
	 * @return
	 */
	public List<MsgReplySetEntity> queryByCodition(MsgReplySetRequest msgReplySetRequest){
		
		LogUtil.info(logger, "查询条件msgReplySetRequest={}", msgReplySetRequest);
		return this.mybatisDaoContext.findAll(SQLID+"queryByCodition",MsgReplySetEntity.class, msgReplySetRequest);
	}
	
	/**
	 * 
	 * 保存实体
	 *
	 * @author yd
	 * @created 2016年4月18日 上午10:02:38
	 *
	 * @param msgReplySetEntity
	 * @return
	 */
	public int save(MsgReplySetEntity msgReplySetEntity){
		
		if(Check.NuNObj(msgReplySetEntity)){
			LogUtil.info(logger,"msgReplySetEntity is null");
			return 0;
		}
		if(Check.NuNObj(msgReplySetEntity.getFid())) msgReplySetEntity.setFid(UUIDGenerator.hexUUID());
		return this.mybatisDaoContext.save(SQLID+"insertSelective", msgReplySetEntity);
	}
	
	/**
	 * 
	 * 条件修改
	 *
	 * @author yd
	 * @created 2016年4月18日 上午10:04:29
	 *
	 * @param msgReplySetRequest
	 * @return
	 */
	public int updateByCondition(MsgReplySetEntity msgReplySetEntity){
		
		if( Check.NuNObj(msgReplySetEntity)){
			LogUtil.info(logger, "msgReplySetRequest  is null");
			return 0;
		}
		return this.mybatisDaoContext.update(SQLID+"updateByCondition", msgReplySetEntity);
	}
}
