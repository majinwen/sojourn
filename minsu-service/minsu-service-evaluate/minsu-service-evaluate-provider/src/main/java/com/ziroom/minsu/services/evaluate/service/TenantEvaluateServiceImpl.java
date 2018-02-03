/**
 * @FileName: TenantEvaluateServiceImpl.java
 * @Package com.ziroom.minsu.services.evaluate.service
 * 
 * @author yd
 * @created 2016年4月7日 下午7:37:42
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.service;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.TenantEvaluateEntity;
import com.ziroom.minsu.services.evaluate.dao.EvaluateOrderDao;
import com.ziroom.minsu.services.evaluate.dao.StatsHouseEvaDao;
import com.ziroom.minsu.services.evaluate.dao.TenantEvaluateDao;
import com.ziroom.minsu.services.evaluate.utils.StatsEvaluateManage;
import com.ziroom.minsu.services.evaluate.utils.StatsHouseEvaTask;

/**
 * <p>房客业务实现</p>
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
@Service("evaluate.tenantEvaluateServiceImpl")
public class TenantEvaluateServiceImpl {
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(TenantEvaluateServiceImpl.class);

	@Resource(name="evaluate.tenantEvaluateDao")
	private TenantEvaluateDao tenantEvaluateDao;

	@Resource(name = "evaluate.evaluateOrderDao")
	private EvaluateOrderDao evaluateOrderDao;

	@Resource(name = "evaluate.statsHouseEvaDao")
	private StatsHouseEvaDao statsHouseEvaDao;

	/**
	 *1. 保存房客评价内容
	 *2. 成功保存后必须计算房客的对房源评价的统计信息
	 * 
	 * @author yd
	 * @created 2016年4月7日 下午7:41:24
	 *
	 * @param tenantEvaluateEntity
	 * @return
	 */
	public int saveTenantEvaluate(TenantEvaluateEntity tenantEvaluateEntity,EvaluateOrderEntity evaluateOrderEntity){

		LogUtil.info(logger, "当前需要保存的实体tenantEvaluateEntity={},evaluateOrderEntity={}", tenantEvaluateEntity,evaluateOrderEntity);
		int evaOrderIndex = this.evaluateOrderDao.saveEvaluateOrder(evaluateOrderEntity);
		if(evaOrderIndex<=0){
			LogUtil.info(logger, "保存EvaluateOrderEntity失败");
			return -1;
		}
		tenantEvaluateEntity.setEvaOrderFid(evaluateOrderEntity.getFid());
		tenantEvaluateEntity.setCreateTime(new Date());
		tenantEvaluateEntity.setLastModifyDate(new Date());
		evaOrderIndex = this.tenantEvaluateDao.saveTentantEvaluate(tenantEvaluateEntity);
		if(evaOrderIndex<=0){
			LogUtil.info(logger, "保存tenantEvaluateEntity失败");
			return -1;
		}
		//处理评价统计信息  只有已发布状态才会被统计信息 1. 状态为已发布  2.被评人必须有 即房东uid  3.house_type 必须有 这个上面已经有保证,把这个更新 提到多此线程中 不让在事物中
		//statsEvaluateManage(tenantEvaluateEntity, evaluateOrderEntity);
		return evaOrderIndex;
	}

	/**
	 * 
	 * 统计评价
	 *
	 * @author yd
	 * @created 2016年5月3日 下午9:56:43
	 *
	 * @param tenantEvaluateEntity
	 * @param evaluateOrderEntity
	 */
	public void statsEvaluateManage(TenantEvaluateEntity tenantEvaluateEntity,EvaluateOrderEntity evaluateOrderEntity,Map<String, String> tenParamsMap){
		//处理评价统计信息  只有已发布状态才会被统计信息 1. 状态为已发布  2.被评人必须有 即房东uid  3.house_type 必须有 这个上面已经有保证,把这个更新 提到多此线程中 不让在事物中
		StatsEvaluateManage<Integer> statsEvaluateManage  = new StatsEvaluateManage<Integer>(new StatsHouseEvaTask(statsHouseEvaDao, tenantEvaluateEntity, evaluateOrderEntity,tenParamsMap));
		statsEvaluateManage.saveOrUpdateStatsHouseEva();
	}


	/**
	 * 
	 * 根据evaOrderFid 更新房客配评价信息
	 *
	 * @author yd
	 * @created 2016年4月7日 下午7:44:10
	 *
	 * @param tenantEvaluateEntity
	 * @return
	 */
	public int updateByEvaOrderFid(TenantEvaluateEntity tenantEvaluateEntity){
		return this.tenantEvaluateDao.updateByEvaOrderFid(tenantEvaluateEntity);
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
		return tenantEvaluateDao.queryByEvaOrderFid(evaOrderFid);
	}
}
