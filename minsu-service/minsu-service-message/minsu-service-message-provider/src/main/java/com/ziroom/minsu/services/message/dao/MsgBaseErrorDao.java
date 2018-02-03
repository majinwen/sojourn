package com.ziroom.minsu.services.message.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.message.MsgBaseErrorEntity;

/**
 * 
 * <p>IM错误消息实体</p>
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
@Component("message.msgBaseErrorDao")
public class MsgBaseErrorDao {
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(MsgBaseErrorDao.class);

	private String SQLID = "message.msgBaseErrorDao.";

	@Autowired
	@Qualifier("message.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	
	/**
	 * 
	 * 保存错误消息实体
	 *
	 * @author yd
	 * @created 2016年11月11日 上午11:49:36
	 *
	 * @param msgBaseError
	 * @return
	 */
	public int saveMsgBaseError(MsgBaseErrorEntity msgBaseError){
		
		if(!Check.NuNObj(msgBaseError)){
			if(Check.NuNStrStrict(msgBaseError.getFid()))
				msgBaseError.setFid(UUIDGenerator.hexUUID());
			
			return this.mybatisDaoContext.save(SQLID+"saveMsgBaseError", msgBaseError);
		}
		
		return 0;
	}
}
