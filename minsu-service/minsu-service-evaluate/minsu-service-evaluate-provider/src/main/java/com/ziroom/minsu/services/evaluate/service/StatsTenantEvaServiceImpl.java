/**
 * @FileName: StatsTenantEvaServiceImpl.java
 * @Package com.ziroom.minsu.services.evaluate.service
 * 
 * @author yd
 * @created 2016年4月8日 下午7:15:59
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.service;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.EvaluateShowEntity;
import com.ziroom.minsu.entity.evaluate.LandlordEvaluateEntity;
import com.ziroom.minsu.entity.evaluate.StatsTenantEvaEntity;
import com.ziroom.minsu.services.evaluate.dao.EvaluateShowDao;
import com.ziroom.minsu.services.evaluate.dao.LandlordEvaluateDao;
import com.ziroom.minsu.services.evaluate.dao.StatsTenantEvaDao;
import com.ziroom.minsu.services.evaluate.dto.StatsTenantEvaRequest;
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
 * <p>房东评价房客统计业务</p>
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
@Service("evaluate.statsTenantEvaServiceImpl")
public class StatsTenantEvaServiceImpl {
	
	@Resource(name = "evaluate.statsTenantEvaDao")
	private StatsTenantEvaDao statsTenantEvaDao;

	@Resource(name = "evaluate.evaluateShowDao")
	private EvaluateShowDao evaluateShowDao;

	@Resource(name = "evaluate.landlordEvaluateDao")
	private LandlordEvaluateDao landlordEvaluateDao;
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(StatsTenantEvaServiceImpl.class);


	/**
	 * 
	 * 保存实体
	 *
	 * @author yd
	 * @created 2016年4月8日 下午7:07:17
	 *
	 * @param statsTenantEvaEntity
	 * @return
	 */
	public int save(StatsTenantEvaEntity statsTenantEvaEntity){
		LogUtil.debug(logger, "保存实体statsTenantEvaEntity={}", statsTenantEvaEntity);
		return this.statsTenantEvaDao.save(statsTenantEvaEntity);
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
	 * @param statsTenantEvaEntity
	 * @return
	 */
	public int updateBySelective(StatsTenantEvaEntity statsTenantEvaEntity){
		
		int index = -1;
		if(!Check.NuNObj(statsTenantEvaEntity)&&!Check.NuNStr(statsTenantEvaEntity.getTenantUid())){
			index = this.statsTenantEvaDao.updateBySelective(statsTenantEvaEntity);
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
	 * @param statsTenantEvaRequest
	 * @return
	 */
	public List<StatsTenantEvaEntity> queryByCondition(StatsTenantEvaRequest statsTenantEvaRequest){
		return this.statsTenantEvaDao.queryByCondition(statsTenantEvaRequest);
	}



	/**
	 *
	 * @author jixd
	 * @created 2017年02月24日 09:59:52
	 * @param
	 * @return
	 */
	public int updateShowAndTenantStatEva(List<EvaluateOrderEntity> statList, EvaluateOrderEntity showEntity){
		int count = 0;
		//处理人工下线重新统计逻辑
		if (showEntity.getEvaStatu() == EvaluateStatuEnum.PERSON_OFFLINE.getEvaStatuCode()){
			count = 1;
		}
		if (showEntity.getEvaStatu() == EvaluateStatuEnum.ONLINE.getEvaStatuCode()){
			EvaluateShowEntity evaluateShowEntity = new EvaluateShowEntity();
			evaluateShowEntity.setEvaOrderFid(showEntity.getFid());
			count = evaluateShowDao.saveEntity(evaluateShowEntity);
		}

		if (count > 0) {
			count = this.statusTenantReCalculate(statList, showEntity, count);
		}
		return count;
	}

	/**
	 * 重新计算房客评分
	 * @author wangwt
	 * @created 2017年08月04日 18:01:17
	 * @param
	 * @return
	 */
	public int statusTenantReCalculate(List<EvaluateOrderEntity> statList, EvaluateOrderEntity showEntity, int count){
		if (Check.NuNCollection(statList)){
			return count;
		}
		List<String> fids = new ArrayList<>();
		for (int i = 0; i < statList.size(); i++) {
			fids.add(statList.get(i).getFid());
		}

		StatsTenantEvaRequest statsTenantEvaRequest = new StatsTenantEvaRequest();
		statsTenantEvaRequest.setTenantUid(showEntity.getRatedUserUid());

		List<StatsTenantEvaEntity> statsTenantEvaEntities = statsTenantEvaDao.queryByCondition(statsTenantEvaRequest);
		StatsTenantEvaEntity statsTenantEvaEntity = null;
		if (Check.NuNCollection(statsTenantEvaEntities)){
			statsTenantEvaEntity = new StatsTenantEvaEntity();
			statsTenantEvaEntity.setTenantUid(showEntity.getRatedUserUid());
		}else{
			statsTenantEvaEntity = statsTenantEvaEntities.get(0);
		}
		statsTenantEvaEntity.setLastModifyDate(new Date());
		List<LandlordEvaluateEntity> landlordEvaluateEntities = landlordEvaluateDao.listLanEvaByEvaOrderFids(fids);

		Integer landSatisfTal = 0;
		int total = landlordEvaluateEntities.size();
		for (LandlordEvaluateEntity landlordEvaluateEntity : landlordEvaluateEntities){
			landSatisfTal += landlordEvaluateEntity.getLandlordSatisfied();
		}
		statsTenantEvaEntity.setEvaTotal(total);
		statsTenantEvaEntity.setLandSatisfTal(landSatisfTal);
		statsTenantEvaEntity.setLandSatisfAva(EvaluateUtils.getFloatValue(total, landSatisfTal,new DecimalFormat("0.0")));

		LogUtil.info(logger,"房客评分统计实体计算数据-----data={}", JsonEntityTransform.Object2Json(statsTenantEvaEntity));
		count += statsTenantEvaDao.updateBySelective(statsTenantEvaEntity);
		return count;
	}


	/**
	 * 删除房源统计
	 * @author jixd
	 * @created 2017年03月15日 13:55:02
	 * @param
	 * @return
	 */
	public int delTenantStatByFid(String fid){
		StatsTenantEvaEntity statsTenantEvaEntity = new StatsTenantEvaEntity();
		statsTenantEvaEntity.setFid(fid);
		return statsTenantEvaDao.delTenantStatByFid(statsTenantEvaEntity);
	}
}
