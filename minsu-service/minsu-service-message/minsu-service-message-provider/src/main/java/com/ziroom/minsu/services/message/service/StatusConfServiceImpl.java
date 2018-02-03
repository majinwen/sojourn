/**
 * @FileName: StatusConfServiceImpl.java
 * @Package com.ziroom.minsu.services.message.service
 * 
 * @author yd
 * @created 2016年9月27日 下午8:56:01
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.StatusConfEntity;
import com.ziroom.minsu.services.message.dao.StatusConfDao;

/**
 * <p>业务实现</p>
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
@Service("message.statusConfServiceImpl")
public class StatusConfServiceImpl {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(StatusConfServiceImpl.class);
	
	@Resource(name = "message.statusConfDao")
	private StatusConfDao statusConfDao;
	
	
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
	
		return this.statusConfDao.save(statusConfEntity);
	}
	
	
	/**
	 * 根据主记录fid 查询当前最近的咨询信息
	 * @param msgHouseFid
	 * @return
	 */
	public StatusConfEntity  queryStatusConfByKey(String key){
	
		return statusConfDao.queryStatusConfByKey(key);
				
	}
	
	/**
	 * 条件更新 记录内容
	 * @param msgBaseRequest
	 * @return
	 */
	public int updateByCondition(StatusConfEntity statusConfEntity){
		return this.statusConfDao.updateByCondition(statusConfEntity);
	}
}
