/**
 * @FileName: EvaluateLogDao.java
 * @Package com.ziroom.minsu.services.evaluate.dao
 * 
 * @author yd
 * @created 2016年4月7日 下午8:19:29
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.evaluate.EvaluateLogEntity;

/**
 * <p>评价dao</p>
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
@Repository("evaluate.evaluateLogDao")
public class EvaluateLogDao {

	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(EvaluateLogDao.class);

	private String SQLID = "evaluate.evaluateLogDao.";

	@Autowired
	@Qualifier("evaluate.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	
	/**
	 * 
	 * 插入或者部分更新
	 *
	 * @author yd
	 * @created 2016年4月7日 上午11:59:57
	 *
	 * @param evaluateOrderEntity
	 * @return
	 */
	public int saveEvaluateOrder(EvaluateLogEntity evaluateLogEntity){

		int index = -1;
		if(evaluateLogEntity != null){
			if(evaluateLogEntity.getFid() == null) evaluateLogEntity.setFid(UUIDGenerator.hexUUID());
			
			LogUtil.info(logger, "当前待更新实体对象evaluateLogEntity={}", evaluateLogEntity.toJsonStr());
			index = this.mybatisDaoContext.save(SQLID+"insertSelective", evaluateLogEntity);
		}
		return index;
	}

}
