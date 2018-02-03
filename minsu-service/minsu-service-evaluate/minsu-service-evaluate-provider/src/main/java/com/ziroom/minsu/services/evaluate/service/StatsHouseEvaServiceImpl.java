/**
 * @FileName: StatsHouseEvaServiceImpl.java
 * @Package com.ziroom.minsu.services.evaluate.service
 * 
 * @author yd
 * @created 2016年4月8日 下午6:55:50
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.service;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.EvaluateShowEntity;
import com.ziroom.minsu.entity.evaluate.StatsHouseEvaEntity;
import com.ziroom.minsu.entity.evaluate.TenantEvaluateEntity;
import com.ziroom.minsu.services.evaluate.dao.EvaluateShowDao;
import com.ziroom.minsu.services.evaluate.dao.StatsHouseEvaDao;
import com.ziroom.minsu.services.evaluate.dao.TenantEvaluateDao;
import com.ziroom.minsu.services.evaluate.dto.StatsHouseEvaRequest;
import com.ziroom.minsu.services.evaluate.utils.EvaluateUtils;
import com.ziroom.minsu.valenum.evaluate.EvaluateStatuEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>房源评价统计业务实现层</p>
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
@Service("evaluate.statsHouseEvaServiceImpl")
public class StatsHouseEvaServiceImpl {
	
	@Resource(name = "evaluate.statsHouseEvaDao")
	private StatsHouseEvaDao statsHouseEvaDao;

	@Resource(name = "evaluate.evaluateShowDao")
	private EvaluateShowDao evaluateShowDao;

	@Resource(name = "evaluate.tenantEvaluateDao")
	private TenantEvaluateDao tenantEvaluateDao;

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(StatsHouseEvaServiceImpl.class);
	
	/**
	 * 
	 * 保存实体
	 *
	 * @author yd
	 * @created 2016年4月8日 下午7:07:17
	 *
	 * @param statsHouseEvaEntity
	 * @return
	 */
	public int save(StatsHouseEvaEntity statsHouseEvaEntity){
		
		LogUtil.debug(logger, "保存实体statsHouseEvaEntity={}", statsHouseEvaEntity);
		return this.statsHouseEvaDao.save(statsHouseEvaEntity);
	}
	/**
	 * 
	 * 条件更新统计信息
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
		if(!Check.NuNObj(statsHouseEvaEntity)&&!Check.NuNStr(statsHouseEvaEntity.getHouseFid())&&!Check.NuNObj(statsHouseEvaEntity.getRentWay())){
			index = this.statsHouseEvaDao.updateBySelective(statsHouseEvaEntity);
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
	 * @param statsHouseEvaRequest
	 * @return
	 */
	public List<StatsHouseEvaEntity> queryByCondition(StatsHouseEvaRequest statsHouseEvaRequest){
		return this.statsHouseEvaDao.queryByCondition(statsHouseEvaRequest);
	}
	
	
	
	/**
	 * 根据房东uid查询房东的所有评分的平均值
	 *
	 * @author zl
	 * @created 2016年9月18日 
	 * 
	 * @param landlordUid
	 * @return
	 */
	public float selectByAVEScoreByUid(String landlordUid){
		 
		return statsHouseEvaDao.selectByAVEScoreByUid(landlordUid);
	}

	/**
	 *
	 * @author jixd
	 * @created 2017年02月24日 09:59:52
	 * @param
	 * @return
	 */
	public int updateShowAndHouseStatEva(List<EvaluateOrderEntity> statList, EvaluateOrderEntity showEntity){
		int count = 0;
		//处理人工下线重新统计逻辑
		if (showEntity.getEvaStatu() == EvaluateStatuEnum.PERSON_OFFLINE.getEvaStatuCode()){
			count = 1;
		}
		//上架状态插入记录 插入记录
		if (showEntity.getEvaStatu() == EvaluateStatuEnum.ONLINE.getEvaStatuCode()){
			EvaluateShowEntity evaluateShowEntity = new EvaluateShowEntity();
			evaluateShowEntity.setEvaOrderFid(showEntity.getFid());
			count = evaluateShowDao.saveEntity(evaluateShowEntity);
		}
		if (count > 0) {

			if (Check.NuNCollection(statList)){
				return count;
			}
			count = this.houseEvaReCalculate(statList, showEntity, count);
		}
		return count;
	}

	/**
	 * 重新计算房源评分
	 * @author wangwt
	 * @created 2017年08月04日 18:00:14
	 * @param
	 * @return
	 */
	public int houseEvaReCalculate(List<EvaluateOrderEntity> statList, EvaluateOrderEntity showEntity, int count){
		StatsHouseEvaRequest statsHouseEvaRequest = new StatsHouseEvaRequest();
		statsHouseEvaRequest.setHouseFid(showEntity.getHouseFid());
		statsHouseEvaRequest.setRoomFid(showEntity.getRoomFid());
		statsHouseEvaRequest.setRentWay(showEntity.getRentWay());
		List<StatsHouseEvaEntity> statsHouseEvaEntities = statsHouseEvaDao.queryByCondition(statsHouseEvaRequest);
		StatsHouseEvaEntity statsHouseEvaEntity = null;
		if (Check.NuNCollection(statsHouseEvaEntities)){
			statsHouseEvaEntity = new StatsHouseEvaEntity();
			statsHouseEvaEntity.setRentWay(showEntity.getRentWay());
			statsHouseEvaEntity.setRoomFid(showEntity.getRoomFid());
			statsHouseEvaEntity.setHouseFid(showEntity.getHouseFid());
			statsHouseEvaEntity.setBedFid(showEntity.getBedFid());
			statsHouseEvaEntity.setLandlordUid(showEntity.getRatedUserUid());
		}else{
			statsHouseEvaEntity = statsHouseEvaEntities.get(0);
		}
		statsHouseEvaEntity.setLastModifyDate(new Date());



		List<String> fids = new ArrayList<>();
		for (int i = 0; i < statList.size(); i++) {
			fids.add(statList.get(i).getFid());
		}
		List<TenantEvaluateEntity> tenantEvaluateEntities = tenantEvaluateDao.listTenEvaByEvaOrderFids(fids);

		Integer houseCleanTal = 0;
		Integer desMathTal = 0;
		Integer safeDegreeTal = 0;
		Integer trafPosTal = 0;
		Integer costPerforTal = 0;
		Integer total = tenantEvaluateEntities.size();
		DecimalFormat df = new DecimalFormat("0.0");
		for (TenantEvaluateEntity tenantEvaluateEntity : tenantEvaluateEntities){
			houseCleanTal += tenantEvaluateEntity.getHouseClean();
			desMathTal += tenantEvaluateEntity.getDescriptionMatch();
			safeDegreeTal += tenantEvaluateEntity.getSafetyDegree();
			trafPosTal += tenantEvaluateEntity.getTrafficPosition();
			costPerforTal += tenantEvaluateEntity.getCostPerformance();
		}
		statsHouseEvaEntity.setHouseCleanTal(houseCleanTal);
		statsHouseEvaEntity.setSafeDegreeTal(safeDegreeTal);
		statsHouseEvaEntity.setDesMathTal(desMathTal);
		statsHouseEvaEntity.setTrafPosTal(trafPosTal);
		statsHouseEvaEntity.setCostPerforTal(costPerforTal);
		statsHouseEvaEntity.setEvaTotal(total);
		statsHouseEvaEntity.setHouseCleanAva(EvaluateUtils.getFloatValue(total, houseCleanTal,df));
		statsHouseEvaEntity.setDesMatchAva(EvaluateUtils.getFloatValue(total, desMathTal,df));
		statsHouseEvaEntity.setSafeDegreeAva(EvaluateUtils.getFloatValue(total, safeDegreeTal,df));
		statsHouseEvaEntity.setCostPerforAva(EvaluateUtils.getFloatValue(total, costPerforTal,df));
		statsHouseEvaEntity.setTrafPosAva(EvaluateUtils.getFloatValue(total, trafPosTal,df));
		LogUtil.info(logger,"房源评价统计重新计算数值----------data={}", JsonEntityTransform.Object2Json(statsHouseEvaEntity));
		//插入或者更新
		count += statsHouseEvaDao.updateBySelective(statsHouseEvaEntity);
		return count;
	}
	/**
	 * 删除房源统计
	 * @author jixd
	 * @created 2017年03月15日 13:55:02
	 * @param
	 * @return
	 */
	public int delHouseStatByFid(String fid){
		StatsHouseEvaEntity statsHouseEvaEntity = new StatsHouseEvaEntity();
		statsHouseEvaEntity.setFid(fid);
		return statsHouseEvaDao.delHouseStatByFid(statsHouseEvaEntity);
	}

}
