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
import com.ziroom.minsu.entity.message.MsgBaseLogEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * <p>基本留言持久化层</p>
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
@Repository("message.msgBaseLogDao")
public class MsgBaseLogDao {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(MsgBaseLogDao.class);

	private String SQLID = "message.msgBaseLogDao.";

	@Autowired
	@Qualifier("message.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * 保存
	 * @author jixd
	 * @created 2017年04月07日 09:48:14
	 * @param
	 * @return
	 */
	public int save(MsgBaseLogEntity msgBaseLogEntity){
		if(Check.NuNObj(msgBaseLogEntity) ){
			LogUtil.info(logger, "msgBaseEntity is null");
			return 0;
		}
		if(Check.NuNStr(msgBaseLogEntity.getFid())) {
			msgBaseLogEntity.setFid(UUIDGenerator.hexUUID());
		}
		return this.mybatisDaoContext.save(SQLID+"insertSelective", msgBaseLogEntity);
	}


}
