/**
 * @FileName: LandlordEvaluateDao.java
 * @Package com.ziroom.minsu.services.evaluate.dao
 * 
 * @author yd
 * @created 2016年4月7日 上午11:43:08
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.evaluate.LandlordEvaluateEntity;
import com.ziroom.minsu.services.evaluate.service.EvaluateOrderServiceImpl;

/**
 * <p>房东评价dao层封装</p>
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
@Repository("evaluate.landlordEvaluateDao")
public class LandlordEvaluateDao {

	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(EvaluateOrderServiceImpl.class);

	private String SQLID = "evaluate.landlordEvaluateDao.";

	@Autowired
	@Qualifier("evaluate.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 保存或更新房东评价实体
	 * @author yd
	 * @created 2016年4月7日 下午3:46:46
	 *
	 * @param landlordEvaluateEntity
	 * @return
	 */
	public int saveLandlordEvaluate(LandlordEvaluateEntity landlordEvaluateEntity){
		
		int index = -1;
		if(landlordEvaluateEntity != null){
			if(landlordEvaluateEntity.getFid() == null) landlordEvaluateEntity.setFid(UUIDGenerator.hexUUID());
			LogUtil.info(logger, "当前待更新实体对象landlordEvaluateEntity={}", landlordEvaluateEntity.toJsonStr());
			index = this.mybatisDaoContext.save(SQLID+"insertSelective", landlordEvaluateEntity);
		}
		return index;
	}
	
	/**
	 * 按evaOrderFid更新房东评价实体
	 * @author yd
	 * @created 2016年4月7日 下午3:46:46
	 *
	 * @param landlordEvaluateEntity
	 * @return
	 */
	public int updateByEvaOrderFid(LandlordEvaluateEntity landlordEvaluateEntity){
		
		int index = -1;
		if(landlordEvaluateEntity != null&&!Check.NuNStr(landlordEvaluateEntity.getEvaOrderFid())){
			LogUtil.info(logger, "当前待更新实体对象landlordEvaluateEntity={}", landlordEvaluateEntity.toJsonStr());
			landlordEvaluateEntity.setLastModifyDate(new Date());
			index = this.mybatisDaoContext.update(SQLID+"updateByEvaOrderFid", landlordEvaluateEntity);
		}
		return index;
	}
	/**
	 * 按fid 更新房东评价实体
	 * @author yd
	 * @created 2016年4月7日 下午3:46:46
	 *
	 * @param landlordEvaluateEntity
	 * @return
	 */
	public int updateByFid(LandlordEvaluateEntity landlordEvaluateEntity){
		
		int index = -1;
		if(landlordEvaluateEntity != null&&!Check.NuNStr(landlordEvaluateEntity.getFid())){
			landlordEvaluateEntity.setLastModifyDate(new Date());
			LogUtil.info(logger, "当前待更新实体对象landlordEvaluateEntity={}]", landlordEvaluateEntity.toJsonStr());
			index = this.mybatisDaoContext.update(SQLID+"updateByFid", landlordEvaluateEntity);
		}
		return index;
	}
	
	/**
	 * 
	 * 根据eva_order_fid 查询实体
	 *
	 * @author yd
	 * @created 2016年4月9日 下午12:55:47
	 *
	 * @param evaOrderFid
	 * @return
	 */
	public LandlordEvaluateEntity queryByEvaOrderFid(String evaOrderFid){
		if(!Check.NuNStr(evaOrderFid)){
			return this.mybatisDaoContext.findOne(SQLID+"selectByEvaOrderFid", LandlordEvaluateEntity.class, evaOrderFid);
		}
		return null;
	}


	public List<LandlordEvaluateEntity> listLanEvaByEvaOrderFids(List<String> evaFids){
		Map<String, Object> map = new HashMap<>();
		map.put("evaFids",evaFids);
		return mybatisDaoContext.findAll(SQLID + "listLanEvaByEvaOrderFids",LandlordEvaluateEntity.class,map);
	}
}
