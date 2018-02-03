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

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.MsgBaseOfflineEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * <p>环信离线消息处理dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
@Repository("message.msgBaseOfflineDao")
public class MsgBaseOfflineDao {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(MsgBaseOfflineDao.class);

	private String SQLID = "message.msgBaseOfflineDao.";

	@Autowired
	@Qualifier("message.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * 
	 * 保存实体
	 *
	 * @author jixd
	 * @created 2016年4月16日 下午4:49:05
	 *
	 * @param msgBaseOfflineEntity
	 * @return
	 */
	public int save(MsgBaseOfflineEntity msgBaseOfflineEntity){
		if(Check.NuNStr(msgBaseOfflineEntity.getFid())) {
			msgBaseOfflineEntity.setFid(UUIDGenerator.hexUUID());
		}
		return this.mybatisDaoContext.save(SQLID+"insertSelective", msgBaseOfflineEntity);
	}

}
