/**
 * @FileName: TenantEvaluateDao.java
 * @Package com.ziroom.minsu.services.evaluate.dao
 * 
 * @author yd
 * @created 2016年4月7日 上午11:44:48
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.evaluate.TenantEvaluateEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>房客评价dao层封装</p>
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
@Repository("evaluate.tenantEvaluateDao")
public class TenantEvaluateDao {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(TenantEvaluateDao.class);

	private String SQLID = "evaluate.tenantEvaluateDao.";

	@Autowired
	@Qualifier("evaluate.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 保存或更新房客评价实体
	 * @author yd
	 * @created 2016年4月7日 下午3:46:46
	 *
	 * @param landlordEvaluateEntity
	 * @return
	 */
	public int saveTentantEvaluate(TenantEvaluateEntity tenantEvaluateEntity){
		
		int index = -1;
		if(tenantEvaluateEntity != null){
			if(tenantEvaluateEntity.getFid() == null) tenantEvaluateEntity.setFid(UUIDGenerator.hexUUID());
			
			LogUtil.info(logger, "当前待更新实体对象landlordEvaluateEntity={}", tenantEvaluateEntity);
			index = this.mybatisDaoContext.save(SQLID+"insertSelective", tenantEvaluateEntity);
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
	public int updateByEvaOrderFid(TenantEvaluateEntity tenantEvaluateEntity){
		
		int index = -1;
		if(tenantEvaluateEntity != null&&!Check.NuNStr(tenantEvaluateEntity.getEvaOrderFid())){
			
			LogUtil.info(logger, "当前待更新实体对象tenantEvaluateEntity={}", tenantEvaluateEntity);
			tenantEvaluateEntity.setLastModifyDate(new Date());
			index = this.mybatisDaoContext.update(SQLID+"updateByEvaOrderFid", tenantEvaluateEntity);
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
	public int updateByFid(TenantEvaluateEntity tenantEvaluateEntity){
		
		int index = -1;
		if(tenantEvaluateEntity != null&&!Check.NuNStr(tenantEvaluateEntity.getFid())){
			
			LogUtil.info(logger, "当前待更新实体对象landlordEvaluateEntity={}", tenantEvaluateEntity);
			tenantEvaluateEntity.setLastModifyDate(new Date());
			index = this.mybatisDaoContext.update(SQLID+"updateByFid", tenantEvaluateEntity);
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
	public TenantEvaluateEntity queryByEvaOrderFid(String evaOrderFid){
		if(!Check.NuNStr(evaOrderFid)){
			return this.mybatisDaoContext.findOne(SQLID+"selectByEvaOrderFid", TenantEvaluateEntity.class, evaOrderFid);
		}
		return null;
	}

	/**
	 * 查询符合条件的房客评价
	 * @author jixd
	 * @created 2017年02月24日 08:57:56
	 * @param
	 * @return
	 */
	public List<TenantEvaluateEntity> listTenEvaByEvaOrderFids(List<String> evaFids){
		Map<String, Object> map = new HashMap<>();
		map.put("evaFids",evaFids);
		return mybatisDaoContext.findAll(SQLID + "listTenEvaByEvaOrderFids",TenantEvaluateEntity.class,map);
	}
}
