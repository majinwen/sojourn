/**
 * @FileName: StatsHouseEvaDao.java
 * @Package com.ziroom.minsu.services.evaluate.dao
 * 
 * @author yd
 * @created 2016年4月8日 下午2:46:58
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.dao;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.StatsHouseEvaEntity;
import com.ziroom.minsu.entity.evaluate.TenantEvaluateEntity;
import com.ziroom.minsu.services.evaluate.constant.EvaluateConsant;
import com.ziroom.minsu.services.evaluate.dto.StatsHouseEvaRequest;
import com.ziroom.minsu.services.evaluate.utils.EvaluateUtils;
import com.ziroom.minsu.valenum.evaluate.EvaluateStatuEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;

/**
 * <p>统计房源评价持久层
 *   说明：此处涉及按房源类型进行统计的结果，结果来源为房客对房源评价表
 *   操作：没有记录时候添加，以后是按房东uid和房源类型  和房源/房间/床位 的fid进行修改
 * </p>
 * 
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
@Repository("evaluate.statsHouseEvaDao")
public class StatsHouseEvaDao {
	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(StatsHouseEvaDao.class);
	
	private String SQLID = "evaluate.statsHouseEvaDao.";

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
	public int save(StatsHouseEvaEntity statsHouseEvaEntity){
		int index = -1;
		if(!Check.NuNObj(statsHouseEvaEntity)){
			if(statsHouseEvaEntity.getFid() == null) statsHouseEvaEntity.setFid(UUIDGenerator.hexUUID());
			LogUtil.debug(logger, "待添加对象statsHouseEvaEntity={}", statsHouseEvaEntity);
			index = this.mybatisDaoContext.save(SQLID+"insertSelective", statsHouseEvaEntity);
		}
		return index;
	}
	
	/**
	 * 
	 * 条件查询：fid   house_fid  room_fid  bed_fid  house_type landlord_uid
	 * 
	 * @author yd
	 * @created 2016年4月8日 下午3:10:52
	 *
	 * @param fid
	 * @return
	 */
	public List<StatsHouseEvaEntity> queryByCondition(StatsHouseEvaRequest statsHouseEvaRequest){
		return this.mybatisDaoContext.findAll(SQLID+"queryByCondition", statsHouseEvaRequest);
	}
	
	/**
	 * 
	 *根据fid 进行更新或添加统计信息
	 * 
	 * 此处只能更新统计信息（比如各种星级 ） 最后更新时间  其他数据不让更新
	 *
	 * @author yd
	 * @created 2016年4月8日 下午2:58:36
	 *
	 * @param statsHouseEvaEntity
	 * @return
	 */
	public int updateBySelective(StatsHouseEvaEntity statsHouseEvaEntity){
		
		int index = -1;
		if(!Check.NuNObj(statsHouseEvaEntity)){
			LogUtil.debug(logger, "待添加或更新对象statsHouseEvaEntity={}", statsHouseEvaEntity);
			if(!Check.NuNStr(statsHouseEvaEntity.getFid())){
				index = this.mybatisDaoContext.save(SQLID+"updateBySelective", statsHouseEvaEntity);
			}else{
				index = this.save(statsHouseEvaEntity);
			}
		}
		return index;
	}

	/**
	 * 
	 * 评论发布时，添加或更新房源统计信息
	 *
	 * @author yd
	 * @created 2016年4月9日 上午10:31:06
	 *
	 * @param tenantEvaluateEntity
	 * @param evaluateOrderEntity
	 */
	public void saveOrUpdateStatsHouseEva(TenantEvaluateEntity tenantEvaluateEntity,EvaluateOrderEntity evaluateOrderEntity){
		int index = -1;
		//把这个更新 提到多此线程中 不让在事物中
		if(!Check.NuNObj(evaluateOrderEntity)&&!Check.NuNObj(tenantEvaluateEntity)&&!Check.NuNObj(evaluateOrderEntity.getEvaStatu())&&EvaluateStatuEnum.ONLINE.getEvaStatuCode() == evaluateOrderEntity.getEvaStatu().intValue()&&!Check.NuNStr(evaluateOrderEntity.getRatedUserUid())){
			StatsHouseEvaRequest statsHouseEvaRequest = new StatsHouseEvaRequest();
			statsHouseEvaRequest.setHouseFid(evaluateOrderEntity.getHouseFid());
			statsHouseEvaRequest.setRoomFid(evaluateOrderEntity.getRoomFid());
			statsHouseEvaRequest.setBedFid(evaluateOrderEntity.getBedFid());
			statsHouseEvaRequest.setLandlordUid(evaluateOrderEntity.getRatedUserUid());
			statsHouseEvaRequest.setRentWay(evaluateOrderEntity.getRentWay());
			List<StatsHouseEvaEntity> listStatsHouseEvaEntities = this.queryByCondition(statsHouseEvaRequest);

			StatsHouseEvaEntity statsHouseEvaEntity = null;
			DecimalFormat df = new DecimalFormat("0.0");
			int total = 0;
			if(!Check.NuNCollection(listStatsHouseEvaEntities)){
				statsHouseEvaEntity = listStatsHouseEvaEntities.get(0);
				total = statsHouseEvaEntity.getEvaTotal()+EvaluateConsant.INIT_EVA_NUM;
				statsHouseEvaEntity.setEvaTotal(total);
				statsHouseEvaEntity.setCostPerforTal(statsHouseEvaEntity.getCostPerforTal()+tenantEvaluateEntity.getCostPerformance());
				statsHouseEvaEntity.setDesMathTal(statsHouseEvaEntity.getDesMathTal()+tenantEvaluateEntity.getDescriptionMatch());
				statsHouseEvaEntity.setEvaFiveTal(statsHouseEvaEntity.getEvaFiveTal()+tenantEvaluateEntity.getEvaFive());
				statsHouseEvaEntity.setEvaFourTal(statsHouseEvaEntity.getEvaFourTal()+tenantEvaluateEntity.getEvaFour());
				statsHouseEvaEntity.setHouseCleanTal(statsHouseEvaEntity.getHouseCleanTal()+tenantEvaluateEntity.getHouseClean());
				statsHouseEvaEntity.setSafeDegreeTal(statsHouseEvaEntity.getSafeDegreeTal()+tenantEvaluateEntity.getSafetyDegree());
				statsHouseEvaEntity.setTrafPosTal(statsHouseEvaEntity.getTrafPosTal()+tenantEvaluateEntity.getTrafficPosition());
			}else{
				statsHouseEvaEntity = new StatsHouseEvaEntity();
				statsHouseEvaEntity.setBedFid(evaluateOrderEntity.getBedFid());
				statsHouseEvaEntity.setHouseFid(evaluateOrderEntity.getHouseFid());
				
				if(!Check.NuNStr(evaluateOrderEntity.getBedFid()))statsHouseEvaEntity.setBedFid(evaluateOrderEntity.getBedFid());
				statsHouseEvaEntity.setRentWay(RentWayEnum.HOUSE.getCode());
				if(!Check.NuNStr(evaluateOrderEntity.getRoomFid())){
					statsHouseEvaEntity.setRoomFid(evaluateOrderEntity.getRoomFid());
					statsHouseEvaEntity.setRentWay(RentWayEnum.ROOM.getCode());
				}
				statsHouseEvaEntity.setLandlordUid(evaluateOrderEntity.getRatedUserUid());
				statsHouseEvaEntity.setEvaTotal(EvaluateConsant.INIT_EVA_NUM);
				statsHouseEvaEntity.setCreateTime(new Date());
				total = statsHouseEvaEntity.getEvaTotal();
				statsHouseEvaEntity.setCostPerforTal(tenantEvaluateEntity.getCostPerformance());
				statsHouseEvaEntity.setDesMathTal(tenantEvaluateEntity.getDescriptionMatch());
				statsHouseEvaEntity.setEvaFiveTal(tenantEvaluateEntity.getEvaFive());
				statsHouseEvaEntity.setEvaFourTal(tenantEvaluateEntity.getEvaFour());
				statsHouseEvaEntity.setHouseCleanTal(tenantEvaluateEntity.getHouseClean());
				statsHouseEvaEntity.setSafeDegreeTal(tenantEvaluateEntity.getSafetyDegree());
				statsHouseEvaEntity.setTrafPosTal(tenantEvaluateEntity.getTrafficPosition());
			}
			statsHouseEvaEntity.setLastModifyDate(new Date());
			statsHouseEvaEntity.setCostPerforAva(EvaluateUtils.getFloatValue(total, statsHouseEvaEntity.getCostPerforTal(),df));
			statsHouseEvaEntity.setDesMatchAva(EvaluateUtils.getFloatValue(total, statsHouseEvaEntity.getDesMathTal(),df));
			statsHouseEvaEntity.setEvaFiveAvage(EvaluateUtils.getFloatValue(total, statsHouseEvaEntity.getEvaFiveTal(),df));
			statsHouseEvaEntity.setEvaFourAvage(EvaluateUtils.getFloatValue(total, statsHouseEvaEntity.getEvaFourTal(),df));
			statsHouseEvaEntity.setHouseCleanAva(EvaluateUtils.getFloatValue(total, statsHouseEvaEntity.getHouseCleanTal(),df));
			statsHouseEvaEntity.setSafeDegreeAva(EvaluateUtils.getFloatValue(total, statsHouseEvaEntity.getSafeDegreeTal(),df));
			statsHouseEvaEntity.setTrafPosAva(EvaluateUtils.getFloatValue(total, statsHouseEvaEntity.getTrafPosTal(),df));
			index = this.updateBySelective(statsHouseEvaEntity);
			LogUtil.info(logger, "添加或者更新统计信息返回index = {}", index);
		}
		
	}
	
	/**
	 * 根据房东uid查询房东的所有评分的平均值
	 *
	 * @author zl
	 * @created 2016年9月18日 
	 * 
	 * @param uid
	 * @return
	 */
	public float selectByAVEScoreByUid(String uid){
		if (Check.NuNStr(uid)) {
			return 0;
		}
		Float value = mybatisDaoContext.findOne(SQLID+"selectByAVEScoreByUid", Float.class, uid);
		if (Check.NuNObj(value)) {
			return 0;
		}
		return value;
	}

	/**
	 * 删除统计记录
	 * @author jixd
	 * @created 2017年03月15日 11:45:51
	 * @param
	 * @return
	 */
	public int delHouseStatByFid(StatsHouseEvaEntity statsHouseEvaEntity){
		return mybatisDaoContext.delete(SQLID + "delHouseStatByFid",statsHouseEvaEntity);
	}
	

}
