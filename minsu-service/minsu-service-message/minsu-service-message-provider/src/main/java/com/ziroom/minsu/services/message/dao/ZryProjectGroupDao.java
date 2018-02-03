/**
 * @FileName: ZryProjectGroupDao.java
 * @Package com.ziroom.minsu.services.message.dao
 * 
 * @author yd
 * @created 2017年7月28日 下午1:58:03
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.message.ZryProjectGroupEntity;

/**
 * <p>自如驿项目和群组关联 实体</p>
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
@Repository("message.zryProjectGroupDao")
public class ZryProjectGroupDao extends BaseEntity{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -3035898945716746182L;
	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(ZryProjectGroupDao.class);

	private String SQLID = "message.zryProjectGroupDao.";
	
	
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
	public int saveZryProjectGroup(ZryProjectGroupEntity zryProjectGroup){
		return mybatisDaoContext.save(SQLID+"insertSelective", zryProjectGroup);
	}
	
	/**
	 * 
	 * 修改
	 *
	 * @author yd
	 * @created 2017年7月28日 上午11:29:01
	 *
	 * @param ZryProjectGroup
	 * @return
	 */
	public int updateZryProjectGroup(ZryProjectGroupEntity zryProjectGroup){
		return mybatisDaoContext.update(SQLID+"updateZryProjectGroup", zryProjectGroup);
	}
	

	/**
	 * 
	 * 修改
	 *
	 * @author yd
	 * @created 2017年7月28日 上午11:29:01
	 *
	 * @param ZryProjectGroup
	 * @return
	 */
	public ZryProjectGroupEntity queryZryProjectGroup(String groupId){
		return mybatisDaoContext.findOne(SQLID+"queryZryProjectGroup",ZryProjectGroupEntity.class, groupId);
	}
}
