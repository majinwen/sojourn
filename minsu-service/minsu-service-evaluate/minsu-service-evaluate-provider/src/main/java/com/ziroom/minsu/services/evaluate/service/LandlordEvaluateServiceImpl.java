/**
 * @FileName: LandlordEvaluateServiceImpl.java
 * @Package com.ziroom.minsu.services.evaluate.service
 * 
 * @author yd
 * @created 2016年4月7日 下午6:16:01
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.service;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import com.ziroom.minsu.entity.evaluate.LandlordReplyEntity;
import com.ziroom.minsu.services.evaluate.dao.LandlordReplyDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.LandlordEvaluateEntity;
import com.ziroom.minsu.services.evaluate.dao.EvaluateOrderDao;
import com.ziroom.minsu.services.evaluate.dao.LandlordEvaluateDao;
import com.ziroom.minsu.services.evaluate.dao.StatsTenantEvaDao;
import com.ziroom.minsu.services.evaluate.dto.EvaluateRequest;
import com.ziroom.minsu.services.evaluate.entity.LandlordEvaluateVo;
import com.ziroom.minsu.services.evaluate.utils.StatsEvaluateManage;
import com.ziroom.minsu.services.evaluate.utils.StatsTenantTvaTask;

/**
 * <p>房东评价业务逻辑</p>
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
@Service("evaluate.landlordEvaluateServiceImpl")
public class LandlordEvaluateServiceImpl {


	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(LandlordEvaluateServiceImpl.class);

	@Resource(name="evaluate.landlordEvaluateDao")
	private LandlordEvaluateDao landlordEvaluateDao;

	@Resource(name = "evaluate.evaluateOrderDao")
	private EvaluateOrderDao evaluateOrderDao;

	@Resource(name = "evaluate.landlordReplyDao")
	private LandlordReplyDao landlordReplyDao;

	@Resource(name = "evaluate.statsTenantEvaDao")
	private StatsTenantEvaDao statsTenantEvaDao;

	/**
	 * 
	 * 房东查询给定用户被其他房东评价的信息
	 *
	 * @author yd
	 * @created 2016年4月7日 下午6:18:57
	 *
	 * @param evaluateRequest
	 * @return
	 */
	public PagingResult<LandlordEvaluateVo> queryLandlordEvaluateByPage(EvaluateRequest evaluateRequest){

		//被评人为给定用户
		if(evaluateRequest!=null&&!Check.NuNStr(evaluateRequest.getRatedUserUid())){
			LogUtil.info(logger, "当前请求信息为evaluateRequest={}", evaluateRequest);
			return this.evaluateOrderDao.queryLandlordEvaluateByPage(evaluateRequest);
		}
		return  null;
	}

	/**
	 * 1.保存评价订单关系实体 t_evaluate_order
	 * 2.保存房东评价  t_landlord_evaluate
	 * 3.开启线程保存 房东对房客的满意度的统计信息 t_stats_tenant_eva  线程执行为了 把该更新从事物中分离开
	 *
	 * @author yd
	 * @created 2016年4月7日 下午7:34:08
	 *
	 * @param landlordEvaluateEntity
	 * @return
	 */
	public int saveLandlordEvaluate(LandlordEvaluateEntity landlordEvaluateEntity,EvaluateOrderEntity evaluateOrderEntity){

		int evaOrderIndex = this.evaluateOrderDao.saveEvaluateOrder(evaluateOrderEntity);
		if(evaOrderIndex>0){
			landlordEvaluateEntity.setEvaOrderFid(evaluateOrderEntity.getFid());
			landlordEvaluateEntity.setCreateTime(new Date());
			landlordEvaluateEntity.setLastModifyDate(new Date());
			evaOrderIndex = this.landlordEvaluateDao.saveLandlordEvaluate(landlordEvaluateEntity);
		}else{
			LogUtil.info(logger, "保存EvaluateOrderEntity失败");
		}
		//保存房东对房客评价的统计信息
		//statsEvaluateManage(landlordEvaluateEntity, evaluateOrderEntity);
		return evaOrderIndex;
	}

	/**
	 * 保存房东回复
	 * @author jixd
	 * @created 2017年02月09日 14:15:05
	 * @param
	 * @return
	 */
	public int saveLandlordReply(LandlordReplyEntity landlordReplyEntity,EvaluateOrderEntity evaluateOrderEntity){
		int evaOrderIndex = this.evaluateOrderDao.saveEvaluateOrder(evaluateOrderEntity);
		if (evaOrderIndex>0){
			landlordReplyEntity.setEvaOrderFid(evaluateOrderEntity.getFid());
			evaOrderIndex += this.landlordReplyDao.saveLandlordReply(landlordReplyEntity);
		}
		return evaOrderIndex;
	}



	/**
	 * 
	 * 统计评价
	 *
	 * @author yd
	 * @created 2016年5月3日 下午9:55:32
	 *
	 * @param landlordEvaluateEntity
	 * @param evaluateOrderEntity
	 */
	public void statsEvaluateManage(LandlordEvaluateEntity landlordEvaluateEntity,EvaluateOrderEntity evaluateOrderEntity,Map<String, String> lanParamsMap){
		//保存房东对房客评价的统计信息
		StatsEvaluateManage<Integer> statsEvaluateManage  = new StatsEvaluateManage<Integer>(new StatsTenantTvaTask(statsTenantEvaDao, landlordEvaluateEntity, evaluateOrderEntity,lanParamsMap));
		statsEvaluateManage.saveOrUpdateStatsTenantEva();
	}

	/**
	 * 
	 * 根据evaOrderFid修改
	 *
	 * @author yd
	 * @created 2016年4月7日 下午7:36:13
	 *
	 * @param landlordEvaluateEntity
	 * @return
	 */
	public int updateByEvaOrderFid(LandlordEvaluateEntity landlordEvaluateEntity){
		return this.landlordEvaluateDao.updateByEvaOrderFid(landlordEvaluateEntity);
	}

	/**
	 * 
	 * 根据Fid修改
	 *
	 * @author yd
	 * @created 2016年4月7日 下午7:36:13
	 *
	 * @param landlordEvaluateEntity
	 * @return
	 */
	public int updateByFid(LandlordEvaluateEntity landlordEvaluateEntity){
		return this.landlordEvaluateDao.updateByFid(landlordEvaluateEntity);
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
		return landlordEvaluateDao.queryByEvaOrderFid(evaOrderFid);
	}
}
