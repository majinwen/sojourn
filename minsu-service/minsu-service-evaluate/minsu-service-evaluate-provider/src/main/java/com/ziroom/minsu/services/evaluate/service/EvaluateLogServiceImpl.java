/**
 * @FileName: EvaluateLogServiceImpl.java
 * @Package com.ziroom.minsu.services.evaluate.service
 * 
 * @author yd
 * @created 2016年4月7日 下午8:30:46
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.evaluate.EvaluateLogEntity;
import com.ziroom.minsu.services.evaluate.dao.EvaluateLogDao;

/**
 * <p>评价日志业务层</p>
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
@Service("evaluate.evaluateLogServiceImpl")
public class EvaluateLogServiceImpl {

	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(EvaluateLogServiceImpl.class);
	
	@Resource(name = "evaluate.evaluateLogDao")
	private EvaluateLogDao evaluateLogDao;
	
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
			LogUtil.info(logger, "当前待更新实体对象evaluateLogEntity={}", evaluateLogEntity);
			index = this.evaluateLogDao.saveEvaluateOrder(evaluateLogEntity);
		}
		return index;
	}
}
