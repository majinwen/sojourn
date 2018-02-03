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

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.evaluate.EvaluateShowEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * <p>评价是否展示</p>
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
@Repository("evaluate.evaluateShowDao")
public class EvaluateShowDao {

	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(EvaluateShowDao.class);

	private String SQLID = "evaluate.evaluateShowDao.";

	@Autowired
	@Qualifier("evaluate.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	
	/**
	 * 
	 * 插入或者部分更新
	 *
	 * @author jixd
	 * @created 2016年4月7日 上午11:59:57
	 *
	 * @param evaluateShowEntity
	 * @return
	 */
	public int saveEntity(EvaluateShowEntity evaluateShowEntity){
		return mybatisDaoContext.save(SQLID + "insertEvaluateShow",evaluateShowEntity);
	}

	/**
	 * 查找实体
	 * @author jixd
	 * @created 2017年02月14日 14:06:02
	 * @param
	 * @return
	 */
	public EvaluateShowEntity findEvaShow(String evaFid){
		return mybatisDaoContext.findOne(SQLID + "selectOrderEvaFid",EvaluateShowEntity.class,evaFid);
	}


}
