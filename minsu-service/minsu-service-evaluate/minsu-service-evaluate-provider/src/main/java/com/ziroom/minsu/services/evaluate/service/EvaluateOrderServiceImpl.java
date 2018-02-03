/**
 * @FileName: EvaluateOrderServiceImpl.java
 * @Package com.ziroom.minsu.services.evaluate.service
 * 
 * @author yd
 * @created 2016年4月7日 下午7:50:21
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.evaluate.EvaluateLogEntity;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.EvaluateShowEntity;
import com.ziroom.minsu.entity.evaluate.LandlordEvaluateEntity;
import com.ziroom.minsu.entity.evaluate.LandlordReplyEntity;
import com.ziroom.minsu.entity.evaluate.TenantEvaluateEntity;
import com.ziroom.minsu.services.evaluate.dao.EvaluateLogDao;
import com.ziroom.minsu.services.evaluate.dao.EvaluateOrderDao;
import com.ziroom.minsu.services.evaluate.dao.EvaluateShowDao;
import com.ziroom.minsu.services.evaluate.dao.LandlordEvaluateDao;
import com.ziroom.minsu.services.evaluate.dao.LandlordReplyDao;
import com.ziroom.minsu.services.evaluate.dao.TenantEvaluateDao;
import com.ziroom.minsu.services.evaluate.dto.EvaluateOrderRequest;
import com.ziroom.minsu.services.evaluate.dto.EvaluatePCRequest;
import com.ziroom.minsu.services.evaluate.dto.EvaluateRequest;
import com.ziroom.minsu.services.evaluate.entity.EvaluateBothItemVo;
import com.ziroom.minsu.services.evaluate.entity.EvaluateVo;
import com.ziroom.minsu.services.evaluate.entity.LandlordEvaluateVo;
import com.ziroom.minsu.services.evaluate.entity.TenantEvaluateVo;
import com.ziroom.minsu.valenum.evaluate.EvaluateUserEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>评价订单关联实体</p>
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
@Service("evaluate.evaluateOrderServiceImpl")
public class EvaluateOrderServiceImpl {

	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(EvaluateOrderServiceImpl.class);
	
	@Resource(name= "evaluate.evaluateOrderDao")
	private EvaluateOrderDao evaluateOrderDao;

	@Resource(name = "evaluate.evaluateLogDao")
	private EvaluateLogDao evaluateLogDao;

	@Resource(name = "evaluate.landlordEvaluateDao")
	private LandlordEvaluateDao landlordEvaluateDao;	

	@Resource(name = "evaluate.tenantEvaluateDao")
	private TenantEvaluateDao tenantEvaluateDao;

	@Resource(name = "evaluate.landlordReplyDao")
	private LandlordReplyDao landlordReplyDao;

    @Resource(name = "evaluate.evaluateShowDao")
    private EvaluateShowDao evaluateShowDao;


	private static final String DATE_FORMATTER = "yyyy年MM月";

	/**
	 * 
	 * 插入
	 *
	 * @author yd
	 * @created 2016年4月7日 上午11:59:57
	 *
	 * @param evaluateOrderEntity
	 * @return
	 */
	public int saveEvaluateOrder(EvaluateOrderEntity evaluateOrderEntity){
		return evaluateOrderDao.saveEvaluateOrder(evaluateOrderEntity);
	}

	/**
	 * 
	 * 按fid修改EvaluateOrder  主要后台审核 以及 内容过滤成功后 直接更新
	 * 
	 * 1.修改EvaluateOrder
	 * 2.保存修改日志
	 * 3.状态如果改成已上线 则需要更新统计信息
	 *
	 * @author yd
	 * @created 2016年4月7日 下午8:04:53
	 *
	 * @param evaluateOrde
	 * @return
	 */
	public int updateEvaluateOrderByFid(EvaluateOrderRequest evaluateOrde,EvaluateOrderEntity evaluateOrderEntity){

		int index = -1;
		if(!Check.NuNObj(evaluateOrde)&&!Check.NuNStr(evaluateOrde.getFid())&&!Check.NuNObj(evaluateOrde.getEvaStatu())){
			if(Check.NuNObj(evaluateOrderEntity)||Check.NuNStr(evaluateOrderEntity.getFid())){
				LogUtil.info(logger, "根据{}查询EvaluateOrderEntity，不存在", evaluateOrde.getFid());
				return index;
			}
			LogUtil.info(logger, "当前修改对象evaluateOrderEntity={}", evaluateOrderEntity);

			if(Check.NuNStr(evaluateOrde.getCreateUid())){
				LogUtil.info(logger, "当前参数对象valuateOrde={},创建人不存在", evaluateOrde.toJsonStr());
			}
			if(!Check.NuNObj(evaluateOrderEntity)){
				evaluateOrderEntity.setLastModifyDate(new Date());
				EvaluateLogEntity evaluateLogEntity = new EvaluateLogEntity();
				evaluateLogEntity.setCreateTime(new Date());
				evaluateLogEntity.setCreateUid(evaluateOrde.getCreateUid());
				evaluateLogEntity.setEvaOrderFid(evaluateOrderEntity.getFid());
				evaluateLogEntity.setFid(UUIDGenerator.hexUUID());
				evaluateLogEntity.setFromStatus(evaluateOrderEntity.getEvaStatu());
				evaluateLogEntity.setIsDel(0);
				evaluateLogEntity.setRemark(evaluateOrde.getRemark());
				evaluateLogEntity.setToStatus(evaluateOrde.getEvaStatu());
				evaluateOrderEntity.setEvaStatu(evaluateOrde.getEvaStatu());
				index = this.evaluateOrderDao.updateByFid(evaluateOrderEntity);
				index = this.evaluateLogDao.saveEvaluateOrder(evaluateLogEntity);
			/*	if(evaluateOrderEntity.getEvaStatu().intValue() == EvaluateStatuEnum.ONLINE.getEvaStatuCode()){
					StatsEvaluateManage<Integer> statsEvaluateManage = null;
					if(evaluateOrderEntity.getEvaUserType().intValue() == UserTypeEnum.LANDLORD.getUserType()){
						LandlordEvaluateEntity landlordEvaluateEntity = this.landlordEvaluateDao.queryByEvaOrderFid(evaluateOrderEntity.getFid());
						if(!Check.NuNObj(landlordEvaluateEntity)){
							statsEvaluateManage = new StatsEvaluateManage<Integer>(new StatsTenantTvaTask(statsTenantEvaDao, landlordEvaluateEntity, evaluateOrderEntity,lanParamMap));
							statsEvaluateManage.saveOrUpdateStatsTenantEva();
						}
					}
					if(evaluateOrderEntity.getEvaUserType().intValue() == UserTypeEnum.TENANT.getUserType()){
						TenantEvaluateEntity tenantEvaluateEntity = this.tenantEvaluateDao.queryByEvaOrderFid(evaluateOrderEntity.getFid());
						if(!Check.NuNObj(tenantEvaluateEntity)){
							statsEvaluateManage = new StatsEvaluateManage<Integer>(new StatsHouseEvaTask(statsHouseEvaDao, tenantEvaluateEntity, evaluateOrderEntity,tenParamMap));
							statsEvaluateManage.saveOrUpdateStatsHouseEva();
						}
					
					}
				}*/

			}else{
				LogUtil.info(logger, "fid为{}的EvaluateOrderEntity不存在", evaluateOrde.getFid());
			}

		}else{
			LogUtil.info(logger, "fid为evaluateOrde{}参数不存在", evaluateOrde);
		}
		return index;
	}
	

	/**
	 * 
	 * 根据fid查询实体 此时只能更新状态和最后更新时间
	 *
	 * @author yd
	 * @created 2016年4月7日 下午5:30:01
	 *
	 * @param fid
	 */
	public EvaluateOrderEntity queryByFid(String fid){
		
		return this.evaluateOrderDao.queryByFid(fid);
	}

	/**
	 * 
	 * 条件分页查询其他房东的评价内容，故评价人类型固定为：房东=1
	 *
	 * @author yd
	 * @created 2016年4月7日 下午2:41:37
	 *
	 *
	 * @param evaluateRequest
	 * @return
	 */
	public PagingResult<LandlordEvaluateVo>  queryLandlordEvaluateByPage(EvaluateRequest evaluateRequest){
		return this.evaluateOrderDao.queryLandlordEvaluateByPage(evaluateRequest);
	}
	/**
	 * 
	 * 查看其他房东 对 特定用户评价   互评记录才能展示sql - 单独给房东端 M站使用 
	 *
	 * @author yd
	 * @created 2016年4月7日 下午2:41:37
	 *
	 *
	 * @param evaluateRequest
	 * @return
	 */
	public PagingResult<LandlordEvaluateVo>  queryOtherLanEvaByPage(EvaluateRequest evaluateRequest){
		return this.evaluateOrderDao.queryOtherLanEvaByPage(evaluateRequest);
	}

	/**
	 * 
	 * 条件分页查询房客的评价内容，故评价人类型固定为：房客=2
	 *
	 * @author yd
	 * @created 2016年4月7日 下午2:41:37
	 *
	 * @param evaluateRequest
	 * @return
	 */
	public PagingResult<TenantEvaluateVo>  queryTenantEvaluateByPage(EvaluateRequest evaluateRequest){
		return this.evaluateOrderDao.queryTenantEvaluateByPage(evaluateRequest);
	}

	/**
	 * 
	 * 根据订单编号查询
	 *
	 * @author yd
	 * @created 2016年4月9日 下午11:57:54
	 *
	 * @param evaluateRequest
	 * @return
	 */
	public Map<String, Object> queryEvaluateByOrderSn(EvaluateRequest evaluateRequest){
		
		Map<String, Object> evaluateMap = new HashMap<String, Object>();
		if(!Check.NuNObj(evaluateRequest)){
			List<EvaluateOrderEntity>  listOrderEvaluateOrderEntities  = this.evaluateOrderDao.queryByOrderSn(evaluateRequest);
			if(!Check.NuNCollection(listOrderEvaluateOrderEntities)){
				evaluateMap.put("listOrderEvaluateOrder", listOrderEvaluateOrderEntities);
				LandlordEvaluateEntity landlordEvaluateEntity = null;
				TenantEvaluateEntity tenantEvaluateEntity = null;
				for (EvaluateOrderEntity evaluateOrderEntity : listOrderEvaluateOrderEntities) {
				    int userType = evaluateOrderEntity.getEvaUserType();
					String evaFid = evaluateOrderEntity.getFid();
					if(userType == EvaluateUserEnum.LAN.getCode()){
						//房东评价
						 landlordEvaluateEntity = landlordEvaluateDao.queryByEvaOrderFid(evaFid);
						 evaluateMap.put("landlordEvaluate",landlordEvaluateEntity);
						 evaluateMap.put("evaOrderIdLandlord",evaFid);
					}
					if(userType == EvaluateUserEnum.TEN.getCode()){
						//房客评价
						tenantEvaluateEntity = tenantEvaluateDao.queryByEvaOrderFid(evaFid);
						evaluateMap.put("tenantEvaluate", tenantEvaluateEntity);
						evaluateMap.put("evaluateOrderEntity", evaluateOrderEntity);
					}
					if (userType == EvaluateUserEnum.LAN_REPLY.getCode()){
						//房东回复
                        LandlordReplyEntity landlordReplyEntity = landlordReplyDao.findEvaReplyByEvaFid(evaFid);
						evaluateMap.put("landlordReplyEntity",landlordReplyEntity);
					}

				}
			}
		}
		return evaluateMap;
	}


	/**
	 * 
	 * 查询评价公共信息(后台使用)
	 *
	 * @author yd
	 * @created 2016年4月11日 下午2:35:48
	 *
	 * @param evaluateRequest
	 * @return
	 */
	public PagingResult<EvaluateVo> queryAllEvaluateByPage(EvaluateRequest evaluateRequest){
		return this.evaluateOrderDao.queryAllEvaluateByPage(evaluateRequest);
	}
	
	/**
	 * 
	 * 条件修改评价订单关系表
	 *
	 * @author yd
	 * @created 2016年4月12日 上午11:44:51
	 *
	 * @param evaluateOrderEntity
	 * @return
	 */
	public int updateBycondition(EvaluateOrderEntity evaluateOrderEntity){
		return this.evaluateOrderDao.updateBycondition(evaluateOrderEntity);
	}
	
	/**
	 * 
	 * 条件查询
	 *
	 * @author yd
	 * @created 2016年4月12日 下午2:11:40
	 *
	 * @param evaluateOrderEntity
	 * @return
	 */
	public List<EvaluateOrderEntity> queryByCondition(EvaluateOrderEntity evaluateOrderEntity){
		
		if(!Check.NuNObj(evaluateOrderEntity)){
			return this.evaluateOrderDao.queryByCondition(evaluateOrderEntity);
		}
		return null;
	}


	/**
	 * 更新已经同步的订单状态
	 * @author afi
	 * @created 2016年11月12日 下午6:46:12
	 *
	 * @param listFid
	 */
	public void updateSynOrderEvaFlagByFid(List<String> listFid){
		if (Check.NuNCollection(listFid)){
			return;
		}
		evaluateOrderDao.updateSynOrderEvaFlagByFid(listFid);
	}
	
	/**
	 * 
	 * 根据订单号修改评价的订单是否评价标识
	 *
	 * @author yd
	 * @created 2016年4月12日 下午6:48:58
	 *
	 * @param params
	 * @return
	 */
	public int updateOrderEvaFlag(Map<String, Object> params){
		
		if(params == null||Check.NuNObj(params.get("listOrderSn"))) return -1;
		LogUtil.info(logger, "当前修改评价参数dddddddddddddddddddddddddddddparams={}", params);
		return this.evaluateOrderDao.updateOrderEvaFlag(params);
	}
	/**
	 * 
	 * 按时间更新评价状态 为已发布 4
	 *
	 * @author yd
	 * @created 2016年5月3日 下午9:18:51
	 *
	 * @param evaluateRequest
	 * @return
	 */
	public int updateByEvaluateRe(EvaluateRequest evaluateRequest){
		return this.evaluateOrderDao.updateByEvaluateRe(evaluateRequest);
	}
	/**
	 * 
	 * 条件查询
	 *
	 * @author yd
	 * @created 2016年4月12日 下午2:11:40
	 *
	 * @param evaluateRequest
	 * @return
	 */
	public List<EvaluateOrderEntity> queryByEvaluateRe(EvaluateRequest evaluateRequest){
		return this.evaluateOrderDao.queryByEvaluateRe(evaluateRequest);
	}
	
	/**
	 * 
	 * 查询房东被评价数量
	 *
	 * @author jixd
	 * @created 2016年8月6日 下午2:17:49
	 *
	 * @param request
	 * @return
	 */
	public long countLanEva(EvaluatePCRequest request){
		return this.evaluateOrderDao.countLanEva(request);
	}
	
	/**
	 * 
	 * 查询房源被评价数量
	 *
	 * @author jixd
	 * @created 2016年8月6日 下午2:18:33
	 *
	 * @param request
	 * @return
	 */
	public long countHouseEva(EvaluatePCRequest request){
		return this.evaluateOrderDao.countHouseEva(request);
	}
	
	/**
	 * 
	 * 查询房源详情评价列表
	 *
	 * @author jixd
	 * @created 2016年8月8日 上午10:26:02
	 *
	 * @param evaluateRequest
	 * @return
	 */
	public PagingResult<EvaluateBothItemVo> queryHouseDetailEvaPage(EvaluatePCRequest evaluateRequest){
		return evaluateOrderDao.queryHouseDetailEvaPage(evaluateRequest);
	}
	
	/**
	 * 
	 * 查询房客对房东评价列表
	 *
	 * @author jixd
	 * @created 2016年8月8日 上午10:26:17
	 *
	 * @param evaluateRequest
	 * @return
	 */
	public PagingResult<EvaluateBothItemVo> queryLanEvaPage(EvaluatePCRequest evaluateRequest){
		return evaluateOrderDao.queryLanEvaPage(evaluateRequest);
	}

	/**
	 * 查询未显示 的评价
	 * @author jixd
	 * @created 2017年02月14日 14:47:05
	 * @param
	 * @return
	 */
    public PagingResult<EvaluateOrderEntity> listEvaluateUncheckShow(EvaluateRequest evaluateRequest){
        return evaluateOrderDao.listEvaluateUncheckShow(evaluateRequest);
    }

	/**
	 * 查询 房东对房客 或者房客对房东评价数量
	 * @author jixd
	 * @created 2016年11月15日 11:19:41
	 * @param
	 * @return
	 */
	public long countEvaNum(EvaluateRequest evaluateRequest){
		return evaluateOrderDao.countEvaNum(evaluateRequest);
	}

	/**
	 * 查询房客对房源的评价条数 （初评 和 评价）
	 * @author jixd
	 * @created 2016年11月15日 11:20:18
	 * @param
	 * @return
	 */
	public long countTenToHouseEvaNum(EvaluateRequest evaluateRequest){
		return evaluateOrderDao.countTenToHouseEvaNum(evaluateRequest);
	}

	/**
	 * 保存评价显示
	 * @author jixd
	 * @created 2017年02月14日 16:06:02
	 * @param
	 * @return
	 */
	public long saveEvaluateShow(EvaluateShowEntity evaluateShowEntity){
        return evaluateShowDao.saveEntity(evaluateShowEntity);
    }

    /**
     * 查询符合条件的订单评价
     * @author jixd
     * @created 2017年02月23日 16:23:41
     * @param
     * @return
     */
    public List<EvaluateOrderEntity> listAllEvaOrderByCondition(EvaluateRequest request){
        return evaluateOrderDao.listAllEvaOrderByCondition(request);
    }

	/**
	 * 查询房东回复
	 * @author jixd
	 * @created 2017年03月14日 14:28:41
	 * @param
	 * @return
	 */
	public LandlordReplyEntity findEvaReplyByOrderSn(String orderSn){
		return landlordReplyDao.findEvaReplyByOrderSn(orderSn);
	}

	/**
	 * 
	 * 查询昨天遗漏的评价行为(房东行为成长体系定时任务)
	 * 
	 * @author zhangyl2
	 * @created 2017年10月12日 11:45
	 * @param 
	 * @return 
	 */
    public List<TenantEvaluateVo> queryTenantEvaluateForCustomerBehaviorJob(EvaluateRequest evaluateRequest){
        return evaluateOrderDao.queryTenantEvaluateForCustomerBehaviorJob(evaluateRequest);
    }

}
