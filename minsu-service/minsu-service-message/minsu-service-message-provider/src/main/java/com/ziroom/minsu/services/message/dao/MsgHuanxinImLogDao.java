/**
 * @FileName: MsgHuanxinImLogDao.java
 * @Package com.ziroom.minsu.services.message.dao
 * 
 * @author yd
 * @created 2017年8月3日 上午11:09:54
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.message.MsgHuanxinImLogEntity;

/**
 * <p>消息记录保存实体</p>
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
@Repository("message.msgHuanxinImLogDao")
public class MsgHuanxinImLogDao {

	

	private String SQLID = "message.msgHuanxinImLogDao.";
	
	
	@Autowired
	@Qualifier("message.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	/**
	 * 
	 * 保存
	 *
	 * @author yd
	 * @created 2016年9月9日 下午3:20:18
	 *
	 * @param imRecord
	 * @return
	 * 
	 */
	public int saveMsgHuanxinImLog(MsgHuanxinImLogEntity msgHuanxinImLog){
		return mybatisDaoContext.save(SQLID+"saveMsgHuanxinImLog", msgHuanxinImLog);
	}
}
