/**
 * @FileName: PhotographerBookLogDao.java
 * @Package com.ziroom.minsu.services.house.dao
 * 
 * @author yd
 * @created 2016年11月4日 下午3:20:32
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.photographer.PhotographerBookLogEntity;

import java.util.List;

/**
 * <p>摄影订单操作日志</p>
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
@Component("photographer.photographerBookLogDao")
public class PhotographerBookLogDao {

	private String SQLID="photographer.photographerBookLogDao.";

	@Autowired
	@Qualifier("house.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * 
	 * 保存实体
	 *
	 * @author yd
	 * @created 2016年11月4日 下午3:43:34
	 *
	 * @param photographerBookLog
	 * @return
	 */
	public int savePhotographerBookLog(PhotographerBookLogEntity photographerBookLog){

		if(!Check.NuNObj(photographerBookLog)&&!Check.NuNStrStrict(photographerBookLog.getBookOrderSn())){

			if(Check.NuNStrStrict(photographerBookLog.getFid())) photographerBookLog.setFid(UUIDGenerator.hexUUID());

			return this.mybatisDaoContext.save(SQLID+"savePhotographerBookLog", photographerBookLog);
		}

		return 0;
	}

	/**
	 * 查询预约摄影单的日志记录
	 * @param bookOrderSn
	 * @return
	 */
	public List<PhotographerBookLogEntity> findLogs(String bookOrderSn){
		if(!Check.NuNStrStrict(bookOrderSn)){
			return mybatisDaoContext.findAll(SQLID+"findLogs",PhotographerBookLogEntity.class,bookOrderSn);
		}
		return null;
	}
}
