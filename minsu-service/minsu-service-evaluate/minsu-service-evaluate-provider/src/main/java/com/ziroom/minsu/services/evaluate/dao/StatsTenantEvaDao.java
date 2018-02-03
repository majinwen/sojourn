/**
 * @FileName: StatsTenantEvaDao.java
 * @Package com.ziroom.minsu.services.evaluate.dao
 * 
 * @author yd
 * @created 2016年4月8日 下午6:08:42
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.LandlordEvaluateEntity;
import com.ziroom.minsu.entity.evaluate.StatsTenantEvaEntity;
import com.ziroom.minsu.services.evaluate.constant.EvaluateConsant;
import com.ziroom.minsu.services.evaluate.dto.StatsTenantEvaRequest;
import com.ziroom.minsu.services.evaluate.utils.EvaluateUtils;
import com.ziroom.minsu.valenum.evaluate.EvaluateStatuEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <p>统计房东对房客的满意程度持久层
 *   说明：此处涉及房东对房客的满意程度进行统计的结果，结果来源房东对房客的满意程度评价表
 *   操作：没有记录时候添加，以后是按房客的uid进行修改
 * </p>
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
@Repository("evaluate.statsTenantEvaDao")
public class StatsTenantEvaDao {
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(StatsHouseEvaDao.class);
	
	private String SQLID = "evaluate.statsTenantEvaDao.";

	@Autowired
	@Qualifier("evaluate.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 
	 * 添加统计信息
	 *
	 * @author yd
	 * @created 2016年4月8日 下午2:53:14
	 *
	 * @param statsHouseEvaEntity
	 * @return
	 */
	public int save(StatsTenantEvaEntity statsTenantEvaEntity){
		int index = -1;
		if(!Check.NuNObj(statsTenantEvaEntity)){
			if(statsTenantEvaEntity.getFid() == null) statsTenantEvaEntity.setFid(UUIDGenerator.hexUUID());
			LogUtil.debug(logger, "待添加对象statsTenantEvaEntity={}", statsTenantEvaEntity);
			index = this.mybatisDaoContext.save(SQLID+"insertSelective", statsTenantEvaEntity);
		}
		return index;
	}
	

	/**
	 * 
	 * 条件更新统计信息 或者添加
	 * 
	 * 此处只能更新统计信息（比如各种星级 ） 最后更新时间  其他数据不让更新
	 *
	 * @author yd
	 * @created 2016年4月8日 下午2:58:36
	 *
	 * @param statsHouseEvaEntity
	 * @return
	 */
	public int updateBySelective(StatsTenantEvaEntity statsTenantEvaEntity){
		
		int index = -1;
		if(!Check.NuNObj(statsTenantEvaEntity)){
			if(!Check.NuNStr(statsTenantEvaEntity.getFid())){
				LogUtil.debug(logger, "待添加对象statsTenantEvaEntity={}", statsTenantEvaEntity);
				index = this.mybatisDaoContext.save(SQLID+"updateBySelective", statsTenantEvaEntity);
			}else{
				index = this.save(statsTenantEvaEntity);
			}
		}
		return index;
	}
	
	/**
	 * 
	 * 条件查询：fid   tenant_uid       
	 * 
	 * @author yd
	 * @created 2016年4月8日 下午3:10:52
	 *
	 * @param fid
	 * @return
	 */
	public List<StatsTenantEvaEntity> queryByCondition(StatsTenantEvaRequest statsTenantEvaRequest){
		return this.mybatisDaoContext.findAll(SQLID+"queryByCondition", statsTenantEvaRequest);
	}
	
	/**
	 * 
	 * 保存或者更新 统计房东对房客评价的统计信息
	 *
	 * @author yd
	 * @created 2016年4月9日 上午11:27:09
	 *
	 * @param landlordEvaluateEntity
	 * @param evaluateOrderEntity
	 */
	public void saveOrUpdateStatsTenantEva(LandlordEvaluateEntity landlordEvaluateEntity,EvaluateOrderEntity evaluateOrderEntity){
		
		if(!Check.NuNObj(landlordEvaluateEntity)&&!Check.NuNObj(evaluateOrderEntity)&&!Check.NuNStr(evaluateOrderEntity.getRatedUserUid())&&!Check.NuNObj(evaluateOrderEntity.getEvaStatu())&&EvaluateStatuEnum.ONLINE.getEvaStatuCode() == evaluateOrderEntity.getEvaStatu().intValue()){
			
			StatsTenantEvaRequest statsTenantEvaRequest = new StatsTenantEvaRequest();
			statsTenantEvaRequest.setTenantUid(evaluateOrderEntity.getRatedUserUid());
		    List<StatsTenantEvaEntity>	lsitTenantEvaEntities = this.queryByCondition(statsTenantEvaRequest);
		    StatsTenantEvaEntity statsTenantEvaEntity  = null;
		    if(!Check.NuNCollection(lsitTenantEvaEntities)){
		    	statsTenantEvaEntity = lsitTenantEvaEntities.get(0);
		    	statsTenantEvaEntity.setEvaTotal(statsTenantEvaEntity.getEvaTotal()+EvaluateConsant.INIT_EVA_NUM);
		    	statsTenantEvaEntity.setLandSatisfTal(statsTenantEvaEntity.getLandSatisfTal()+landlordEvaluateEntity.getLandlordSatisfied());
		    }else{
		    	statsTenantEvaEntity = new StatsTenantEvaEntity();
		    	statsTenantEvaEntity.setCreateTime(new Date());
		    	statsTenantEvaEntity.setEvaTotal(EvaluateConsant.INIT_EVA_NUM);
		    	statsTenantEvaEntity.setLandSatisfTal(landlordEvaluateEntity.getLandlordSatisfied());
		    	statsTenantEvaEntity.setTenantUid(evaluateOrderEntity.getRatedUserUid());
		    }
		    statsTenantEvaEntity.setLastModifyDate(new Date());
		    statsTenantEvaEntity.setLandSatisfAva(EvaluateUtils.getFloatValue(statsTenantEvaEntity.getEvaTotal(), statsTenantEvaEntity.getLandSatisfTal(), null));
		    this.updateBySelective(statsTenantEvaEntity);
		    	
		}
		
	}

	/**
	 * 删除统计
	 * @author jixd
	 * @created 2017年03月15日 11:47:29
	 * @param
	 * @return
	 */
	public int delTenantStatByFid(StatsTenantEvaEntity statsTenantEvaEntity){
		return mybatisDaoContext.delete(SQLID + "delTenantStatByFid",statsTenantEvaEntity);
	}
}
