/**
 * @FileName: StatusConfDao.java
 * @Package com.ziroom.minsu.services.message.dao
 * 
 * @author yd
 * @created 2016年9月27日 下午8:24:53
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.StatusConfEntity;
import com.ziroom.minsu.entity.message.MsgBaseEntity;

/**
 * <p>TODO</p>
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
@Repository("message.statusConfDao")
public class StatusConfDao {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(StatusConfDao.class);

	private String SQLID = "message.statusConfDao.";

	@Autowired
	@Qualifier("message.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 
	 * 保存实体
	 *
	 * @author yd
	 * @created 2016年4月16日 下午4:49:05
	 *
	 * @param msgBaseEntity
	 * @return
	 */
	public int save(StatusConfEntity statusConfEntity){
		
		if(Check.NuNObj(statusConfEntity) ){
			LogUtil.info(logger, "statusConfEntity is null");
			return 0;
		}
		if(Check.NuNStr(statusConfEntity.getFid())) statusConfEntity.setFid(UUIDGenerator.hexUUID());
		return this.mybatisDaoContext.save(SQLID+"insertSelective", statusConfEntity);
	}
	
	
	/**
	 * 根据主记录fid 查询当前最近的咨询信息
	 * @param msgHouseFid
	 * @return
	 */
	public StatusConfEntity  queryStatusConfByKey(String key){
		
		if(Check.NuNStr(key)){
			return null;
		}
		return mybatisDaoContext.findOne(SQLID+"queryStatusConfByKey", StatusConfEntity.class, key);
				
	}
	
	/**
	 * 条件更新 记录内容
	 * @param msgBaseRequest
	 * @return
	 */
	public int updateByCondition(StatusConfEntity statusConfEntity){
		
		//如果当前主记录id没有 不让更新
		if(Check.NuNStr(statusConfEntity.getStaKey())){
			return 0;
		}
		return this.mybatisDaoContext.update(SQLID+"updateByCondition", statusConfEntity);
	}
}
