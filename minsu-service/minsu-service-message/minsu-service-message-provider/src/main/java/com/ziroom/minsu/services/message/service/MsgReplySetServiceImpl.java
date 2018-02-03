/**
 * @FileName: MsgReplySetServiceImpl.java
 * @Package com.ziroom.minsu.services.message.service
 * 
 * @author yd
 * @created 2016年4月18日 下午3:20:59
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.MsgReplySetEntity;
import com.ziroom.minsu.services.message.dao.MsgReplySetDao;
import com.ziroom.minsu.services.message.dto.MsgReplySetRequest;

/**
 * <p>房东可以设置标签管理service</p>
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
@Service("message.msgReplySetServiceImpl")
public class MsgReplySetServiceImpl {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(MsgReplySetServiceImpl.class);
	
	@Resource(name = "message.msgReplySetDao")
	private MsgReplySetDao msgReplySetDao;
	
	/**
	 * 查询当前房东设置实体
	 *
	 * @author yd
	 * @created 2016年4月18日 上午9:57:38
	 *
	 * @param msgReplySetRequest
	 * @return
	 */
	public MsgReplySetEntity queryByLanglordFid(MsgReplySetRequest msgReplySetRequest){
		
		if(Check.NuNObj(msgReplySetRequest)||Check.NuNStr(msgReplySetRequest.getLandlordUid())){
			LogUtil.info(logger, "查询条件langlordFid={}", msgReplySetRequest);
			return null;
		}
		
		List<MsgReplySetEntity> listMsgReplySetEntities = msgReplySetDao.queryByCodition(msgReplySetRequest);
		
		if(Check.NuNCollection(listMsgReplySetEntities)){
			return null;
		}
		return listMsgReplySetEntities.get(0);
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
		
		return msgReplySetDao.save(msgReplySetEntity);
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
		return this.msgReplySetDao.updateByCondition(msgReplySetEntity);
	}
}
