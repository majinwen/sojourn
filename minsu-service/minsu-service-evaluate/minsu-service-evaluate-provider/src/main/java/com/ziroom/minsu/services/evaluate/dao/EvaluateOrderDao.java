package com.ziroom.minsu.services.evaluate.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ziroom.minsu.services.evaluate.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.services.evaluate.dto.EvaluatePCRequest;
import com.ziroom.minsu.services.evaluate.dto.EvaluateRequest;
import com.ziroom.minsu.services.evaluate.dto.StatsHouseEvaRequest;

/**
 * 
 * <p>评价订单dao层封装</p>
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
@Repository("evaluate.evaluateOrderDao")
public class EvaluateOrderDao {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(EvaluateOrderDao.class);

	private String SQLID = "evaluate.evaluateOrderDao.";

	@Autowired
	@Qualifier("evaluate.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

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

		int index = -1;
		if(evaluateOrderEntity != null){
			if(evaluateOrderEntity.getFid() == null) evaluateOrderEntity.setFid(UUIDGenerator.hexUUID());
			
			LogUtil.info(logger, "当前待更新实体对象evaluateOrderEntity={}", evaluateOrderEntity);
			index = this.mybatisDaoContext.save(SQLID+"insertSelective", evaluateOrderEntity);
		}
		return index;
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
			return this.mybatisDaoContext.findAll(SQLID+"queryByCondition", EvaluateOrderEntity.class, evaluateOrderEntity);
		}
		return null;
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
		return this.mybatisDaoContext.findAll(SQLID+"queryByEvaluateRe", evaluateRequest);
	}
	/**
	 * 
	 * 根据fid更新实体 此时只能更新状态和最后更新时间
	 *
	 * @author yd
	 * @created 2016年4月7日 下午5:30:01
	 *
	 * @param evaluateOrderEntity
	 */
	public int updateByFid(EvaluateOrderEntity evaluateOrderEntity){

		int index = -1;
		if(evaluateOrderEntity != null&&!Check.NuNStr(evaluateOrderEntity.getFid())&&!Check.NuNObj(evaluateOrderEntity.getEvaStatu())){
			
			LogUtil.info(logger, "当前待更新实体对象evaluateOrderEntity={}", evaluateOrderEntity);
			index = this.mybatisDaoContext.update(SQLID+"updateByFid", evaluateOrderEntity);
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
		
		EvaluateOrderEntity evaluateOrderEntity =  null;
		if(!Check.NuNStr(fid)){
		 evaluateOrderEntity =  this.mybatisDaoContext.findOne(SQLID+"queryByFid", EvaluateOrderEntity.class, fid);
		}
		return evaluateOrderEntity;
	}
	
	/**
	 * 
	 * 按订单号查询评价订单关系实体  查询结果大小要么问 0 或2
	 *
	 * @author yd
	 * @created 2016年4月11日 上午9:29:20
	 *
	 * @param evaluateRequest
	 * @return
	 */
	public List<EvaluateOrderEntity> queryByOrderSn(EvaluateRequest evaluateRequest){
		
		LogUtil.info(logger, "查询条件的订单单号orderSn={}", evaluateRequest);
		
		if(!Check.NuNObj(evaluateRequest)){
			return this.mybatisDaoContext.findAll(SQLID+"queryByOrderSn", evaluateRequest);
		}
		return null;
	}

	/**
	 * 查询带显示状态的订单评价关系实体
	 * @param evaluateRequest
	 * @return
	 */
	public List<EvaluateOrderShowVo> queryEvaluateOrderShowByOrderSn(EvaluateRequest evaluateRequest){
		if(!Check.NuNObj(evaluateRequest)){
			return this.mybatisDaoContext.findAll(SQLID+"queryEvaluateOrderShowByOrderSn", evaluateRequest);
		}
		return null;
	}


	/**
	 * 
	 * 条件分页查询房东的评价内容，故评价人类型固定为：房东=1
	 *
	 * @author yd
	 * @created 2016年4月7日 下午2:41:37
	 *
	 *
	 * @param evaluateRequest
	 * @return
	 */
	public PagingResult<LandlordEvaluateVo>  queryLandlordEvaluateByPage(EvaluateRequest evaluateRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(evaluateRequest.getLimit());
		pageBounds.setPage(evaluateRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "queryLandlordEvaluateByPage", LandlordEvaluateVo.class, evaluateRequest, pageBounds);
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
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(evaluateRequest.getLimit());
		pageBounds.setPage(evaluateRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "queryOtherLanEvaByPage", LandlordEvaluateVo.class, evaluateRequest, pageBounds);
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
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(evaluateRequest.getLimit());
		pageBounds.setPage(evaluateRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "queryTenantEvaluateByPage", TenantEvaluateVo.class, evaluateRequest, pageBounds);
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
		
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(evaluateRequest.getLimit());
		pageBounds.setPage(evaluateRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "queryAllEvaluateByPage", EvaluateVo.class, evaluateRequest.toMap(), pageBounds);
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
		return this.mybatisDaoContext.update(SQLID+"updateBycondition", evaluateOrderEntity);
	}


	/**
	 * 更新已经同步的订单状态
	 * @author afi
	 * @created 2016年11月12日 下午6:46:12
	 *
	 * @param listFid
	 */
	public int updateSynOrderEvaFlagByFid(List<String> listFid){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("listFid", listFid);
		LogUtil.info(logger, "当前修改评价参数params={}", params);
		int i = this.mybatisDaoContext.update(SQLID+"updateSynOrderEvaFlagByFid", params);
		return i;
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
		
		int i = 0;
		LogUtil.info(logger, "当前修改评价参数params={}", params);
		i = this.mybatisDaoContext.update(SQLID+"updateOrderEvaFlag", params);
		return i;
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
		return this.mybatisDaoContext.update(SQLID+"updateByEvaluateRe", evaluateRequest);
	}
	
	/**
	 * 查询房东被评价数量
	 * @author jixd on 2016年8月6日
	 */
	public long countLanEva(EvaluatePCRequest request){
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("landlordUid", request.getLandlordUid());
		return mybatisDaoContext.countBySlave(SQLID+"countLanEva", paramMap);
	}
	
	/**
	 * 查询房源被评价数量
	 * @author jixd on 2016年8月6日
	 */
	public long countHouseEva(EvaluatePCRequest request){
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("houseFid", request.getHouseFid());
		paramMap.put("roomFid", request.getRoomFid());
		paramMap.put("rentWay", request.getRentWay());
		return mybatisDaoContext.countBySlave(SQLID+"countHouseEva", paramMap);
	}
	
	/**
	 * 
	 * 查询房源详情评价 PC
	 *
	 * @author jixd
	 * @created 2016年8月8日 上午9:27:04
	 *
	 * @param evaluateRequest
	 * @return
	 */
	public PagingResult<EvaluateBothItemVo> queryHouseDetailEvaPage(EvaluatePCRequest evaluateRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(evaluateRequest.getLimit());
		pageBounds.setPage(evaluateRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "queryHouseDetailEvaPage", EvaluateBothItemVo.class, evaluateRequest, pageBounds);
	}
	
	/**
	 * 
	 * 查询对房东的评价
	 *
	 * @author jixd
	 * @created 2016年8月8日 上午10:22:06
	 *
	 * @param evaluateRequest
	 * @return
	 */
	public PagingResult<EvaluateBothItemVo> queryLanEvaPage(EvaluatePCRequest evaluateRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(evaluateRequest.getLimit());
		pageBounds.setPage(evaluateRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "queryLanEvaPage", EvaluateBothItemVo.class, evaluateRequest, pageBounds);
	}


	/**
	 * 根据订单号查询评价信息
	 * @author jixd
	 * @created 2016年11月10日 11:10:35
	 * @param
	 * @return
	 */
	public List<EvaluateOrderEntity> findEvaluateByOrderSn(String orderSn){
		return mybatisDaoContext.findAll(SQLID + "findEvaluateByOrderSn",orderSn);
	}

	/**
	 * 分页 查询房源下面评论的订单号
	 * @author jixd
	 * @created 2016年11月10日 15:37:58
	 * @param
	 * @return
	 */
	public PagingResult<EvaluateOrderItemVo> findEvaluateByHouseFid(EvaluateRequest evaluateRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(evaluateRequest.getLimit());
		pageBounds.setPage(evaluateRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "findEvaluateByHouseFid", EvaluateOrderItemVo.class , evaluateRequest, pageBounds);
	}

	/**
	 * 查询房客对房源评价数量
	 * @author jixd
	 * @created 2016年11月15日 10:18:02
	 * @param
	 * @return
	 */
	public long countEvaNum(EvaluateRequest evaluateRequest){
		Map<String,Object> paraMap = new HashMap<>();
		paraMap.put("ratedUserUid",evaluateRequest.getRatedUserUid());
		paraMap.put("evaUserType",evaluateRequest.getEvaUserType());
		return mybatisDaoContext.count(SQLID + "countTenToHouseEvaNum",paraMap);
	}

	/**
	 *
	 * @author jixd
	 * @created 2016年11月15日 10:19:41
	 * @param
	 * @return
	 */
	public long countTenToHouseEvaNum(EvaluateRequest evaluateRequest){
		Map<String,Object> paraMap = new HashMap<>();
		paraMap.put("houseFid",evaluateRequest.getHouseFid());
		paraMap.put("roomFid",evaluateRequest.getRoomFid());
		paraMap.put("rentWay",evaluateRequest.getRentWay());
		return mybatisDaoContext.count(SQLID + "countTenToHouseEvaNum",paraMap);
	}

	/**
	 * 获取未检查是否显示的 评价列表
	 * @param evaluateRequest
	 * @return
	 */
	public PagingResult<EvaluateOrderEntity> listEvaluateUncheckShow(EvaluateRequest evaluateRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(evaluateRequest.getLimit());
		pageBounds.setPage(evaluateRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "listEvaluateUncheckShow",EvaluateOrderEntity.class,evaluateRequest,pageBounds);
	}

	/**
	 *
	 * @author jixd
	 * @created 2017年02月23日 16:20:05
	 * @param
	 * @return
	 */
	public List<EvaluateOrderEntity> listAllEvaOrderByCondition(EvaluateRequest evaluateRequest){
		return mybatisDaoContext.findAllByMaster(SQLID + "listAllEvaOrderByCondition",EvaluateOrderEntity.class,evaluateRequest);
	}

    /**
     * 
     * 查询昨天遗漏的评价行为(房东行为成长体系定时任务)
     * 
     * @author zhangyl2
     * @created 2017年10月12日 11:44
     * @param 
     * @return 
     */
    public List<TenantEvaluateVo> queryTenantEvaluateForCustomerBehaviorJob(EvaluateRequest evaluateRequest){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(evaluateRequest.getLimit());
        pageBounds.setPage(evaluateRequest.getPage());
        return mybatisDaoContext.findAll(SQLID + "queryTenantEvaluateForCustomerBehaviorJob", TenantEvaluateVo.class, evaluateRequest);
    }

}
