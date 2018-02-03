/**
 * @FileName: GuardAreaLogDao.java
 * @Package com.ziroom.minsu.services.basedata.dao
 * 
 * @author yd
 * @created 2016年7月5日 下午4:28:14
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.conf.GuardAreaLogEntity;
import com.ziroom.minsu.services.basedata.dto.GuardAreaLogRequest;

/**
 * <p>区域管家修改日志 数据库持久层</p>
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
@Repository("basedata.guardAreaLogDao")
public class GuardAreaLogDao {

	private String SQLID="basedata.guardAreaLogDao.";

	@Autowired
	@Qualifier("basedata.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	

	/**
	 * 
	 * 添加实体
	 *
	 * @author yd
	 * @created 2016年7月5日 下午3:05:17
	 *
	 * @param guardAreaLog
	 * @return
	 */
	public int saveGuardAreaLog(GuardAreaLogEntity guardAreaLog){

		if(Check.NuNObj(guardAreaLog)){
			return 0;
		}
		if(Check.NuNObj(guardAreaLog.getFid())){
			guardAreaLog.setFid(UUIDGenerator.hexUUID());
		}
		return this.mybatisDaoContext.save(SQLID+"insertSelective", guardAreaLog);
	}
	
	/**
	 * 
	 * 条件查询
	 * 空条件 不让查询
	 *
	 * @author yd
	 * @created 2016年7月5日 下午4:37:52
	 *
	 * @return
	 */
	public List<GuardAreaLogEntity> queryGuardAreaLogByCondition(GuardAreaLogRequest guardAreaLogRe){
		if(Check.NuNObj(guardAreaLogRe)) return null;
		return this.mybatisDaoContext.findAllByMaster(SQLID+"queryGuardAreaLogByCondition", GuardAreaLogEntity.class, guardAreaLogRe);
	}
}
