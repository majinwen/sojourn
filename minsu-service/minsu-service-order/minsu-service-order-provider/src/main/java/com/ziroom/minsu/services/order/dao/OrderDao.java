package com.ziroom.minsu.services.order.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.order.dto.*;
import com.ziroom.minsu.services.order.entity.CashbackOrderVo;
import com.ziroom.minsu.services.order.entity.OrderCashbackVo;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.order.entity.OrderInviteVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> 订单的操作 </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/12.
 * @version 1.0
 * @since 1.0
 */
@Repository("order.orderDao")
public class OrderDao {


	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(OrderDao.class);
	private String SQLID = "order.orderDao.";

	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;


	/**
	 *
	 * 分页  订单  列表 获取（房东、客户、管理后台）  参数添加请求类型（房东：必须有fid,客户：必须有客户fid,后台系统：不限）
	 *
	 * @author liyingjie
	 * @created 2016年4月2日 
	 *
	 * @return
	 */
	public PagingResult<OrderInfoVo> getOrderInfoListByCondiction(OrderRequest orderRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(orderRequest.getLimit());
		pageBounds.setPage(orderRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "getOrderInfoListByCondiction", OrderInfoVo.class, orderRequest.toMap(), pageBounds);
	}


	/**
	 * 获取最近的一条待入住的订单
	 * @param userUid
	 * @return
	 */
	public  OrderInfoVo getOrderLastByUid(String userUid){
		if (Check.NuNStr(userUid)){
			return null;
		}
		return mybatisDaoContext.findOne(SQLID + "getOrderLastByUid", OrderInfoVo.class,userUid);
	}


	/**
	 * 
	 * 房客端进行中订单列表
	 *
	 * @author jixd
	 * @created 2016年5月3日 下午9:43:00
	 *
	 * @param orderRequest
	 * @return
	 */
	public PagingResult<OrderInfoVo> getTenantOrderDoingList(OrderRequest orderRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(orderRequest.getLimit());
		pageBounds.setPage(orderRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "getTenantOrderDoingList", OrderInfoVo.class, orderRequest, pageBounds);
	}




	/**
	 * 房客待评价列表
	 * @author afi
	 * @created 2016年11月13日 下午9:43:00
	 * @param orderEvalRequest
	 * @return
	 */
	public PagingResult<OrderInfoVo> getTenantOrderEavlWaitingList(OrderEvalRequest orderEvalRequest){

		if(	Check.NuNObj(orderEvalRequest.getLimitDay())){
			return null;
		}

		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(orderEvalRequest.getLimit());
		pageBounds.setPage(orderEvalRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "getTenantOrderEavlWaitingList", OrderInfoVo.class, orderEvalRequest, pageBounds);
	}


	/**
	 * 房客已评价列表
	 * @author afi
	 * @created 2016年11月13日 下午9:43:00
	 * @param orderEvalRequest
	 * @return
	 */
	public PagingResult<OrderInfoVo> getTenantOrderEavlHasList(OrderEvalRequest orderEvalRequest){

		if(	Check.NuNObj(orderEvalRequest.getLimitDay())){
			return null;
		}
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(orderEvalRequest.getLimit());
		pageBounds.setPage(orderEvalRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "getTenantOrderEavlHasList", OrderInfoVo.class, orderEvalRequest, pageBounds);
	}


	/**
	 * 房东评价列表(全部)
	 * @author afi
	 * @created 2016年11月13日 下午9:43:00
	 * @param orderEvalRequest
	 * @return
	 */
	public PagingResult<OrderInfoVo> getLandOrderEavlList(OrderEvalRequest orderEvalRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(orderEvalRequest.getLimit());
		pageBounds.setPage(orderEvalRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "getLandOrderEavlList", OrderInfoVo.class, orderEvalRequest, pageBounds);
	}

	/** 
	 * 房东待评价列表
	 * @param orderEvalRequest
	 * @author zl
	 * @created 2017年02月09日 
	 * @return
	 */
	public PagingResult<OrderInfoVo> getLandOrderEavlWaitingList(OrderEvalRequest orderEvalRequest){
		PageBounds pageBounds = new PageBounds();
		if(	Check.NuNObj(orderEvalRequest.getLimitDay())){
			return null;
		}
		pageBounds.setLimit(orderEvalRequest.getLimit());
		pageBounds.setPage(orderEvalRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "getLandOrderEavlWaitingList", OrderInfoVo.class, orderEvalRequest, pageBounds);
	}

	/** 
	 * 房东已经评价列表
	 * @param orderEvalRequest
	 * @author zl
	 * @created 2017年02月09日 
	 * @return
	 */
	public PagingResult<OrderInfoVo> getLandOrderEavlHasList(OrderEvalRequest orderEvalRequest){
		PageBounds pageBounds = new PageBounds();
		if(	Check.NuNObj(orderEvalRequest.getLimitDay())){
			return null;
		}
		pageBounds.setLimit(orderEvalRequest.getLimit());
		pageBounds.setPage(orderEvalRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "getLandOrderEavlHasList", OrderInfoVo.class, orderEvalRequest, pageBounds);
	}




	/**
	 * 获取当前用户申请中的订单数量
	 * @author afi
	 * @param userUid
	 * @return
	 */
	public Long countUserApplyNum(String userUid){
		if (Check.NuNStr(userUid)){
			return 0L;
		}
		Map<String,Object> par = new HashMap<>();
		par.put("userUid",userUid);
		return mybatisDaoContext.count(SQLID + "countUserApplyNum", par);
	}

	/**
	 * 获取当前用户待支付的订单数量
	 * @author afi
	 * @param userUid
	 * @return
	 */
	public Long countUserWaitPayNum(String userUid){
		if (Check.NuNStr(userUid)){
			return 0L;
		}
		Map<String,Object> par = new HashMap<>();
		par.put("userUid",userUid);
		return mybatisDaoContext.count(SQLID + "countUserWaitPayNum", par);
	}

	/**
	 * 获取当前用户待入住的订单数量
	 * @author afi
	 * @param userUid
	 * @return
	 */
	public Long countUserWaitCheckInNum(String userUid){
		if (Check.NuNStr(userUid)){
			return 0L;
		}
		Map<String,Object> par = new HashMap<>();
		par.put("userUid",userUid);
		return mybatisDaoContext.count(SQLID + "countUserWaitCheckInNum", par);
	}

	/**
	 * 获取当前用户待评价的订单数量
	 * @author afi
	 * @param userUid
	 * @return
	 */
	public Long countUserWaitEvaNum(String userUid){
		if (Check.NuNStr(userUid)){
			return 0L;
		}
		Map<String,Object> par = new HashMap<>();
		par.put("userUid",userUid);
		return mybatisDaoContext.count(SQLID + "countUserWaitEvaNum", par);
	}

	/**
	 * 待房东评价订单总数
	 * @author zl
	 * @param landlordUid
	 * @return
	 */
	public Long countWaitLandlordEvaNum(String landlordUid){
		if (Check.NuNStr(landlordUid)){
			return 0L;
		}
		Map<String,Object> par = new HashMap<>();
		par.put("landlordUid",landlordUid);
		return mybatisDaoContext.count(SQLID + "countWaitLandlordEvaNum", par);
	}

	/**
	 * 房东订单总数
	 * @author zl
	 * @param landlordUid
	 * @return
	 */
	public Long countLanOrderNum(String landlordUid){
		if (Check.NuNStr(landlordUid)){
			return 0L;
		}
		Map<String,Object> par = new HashMap<>();
		par.put("landlordUid",landlordUid);
		return mybatisDaoContext.count(SQLID + "countLanOrderNum", par);
	}


	/**
	 * 待房客评价订单总数
	 * @author zl
	 * @param landlordUid
	 * @return
	 */
	public Long countWaitTenantEvaNum(String landlordUid){
		if (Check.NuNStr(landlordUid)){
			return 0L;
		}
		Map<String,Object> par = new HashMap<>();
		par.put("landlordUid",landlordUid);
		return mybatisDaoContext.count(SQLID + "countWaitTenantEvaNum", par);
	}

	/**
	 * 当前房东的可评价的订单总数（房东/房东的房客）
	 * @author zl
	 * @param landlordUid
	 * @return
	 */
	public Long countCanEvaNum(String landlordUid){
		if (Check.NuNStr(landlordUid)){
			return 0L;
		}
		Map<String,Object> par = new HashMap<>();
		par.put("landlordUid",landlordUid);
		return mybatisDaoContext.count(SQLID + "countCanEvaNum", par);
	}

	/**
	 * 房东的房客已评价订单数
	 * @author zl
	 * @param landlordUid
	 * @return
	 */
	public Long countTenantEvaedNum(String landlordUid){
		if (Check.NuNStr(landlordUid)){
			return 0L;
		}
		Map<String,Object> par = new HashMap<>();
		par.put("landlordUid",landlordUid);
		return mybatisDaoContext.count(SQLID + "countTenantEvaedNum", par);
	}

	/**
	 * 房东已评价订单数
	 * @author zl
	 * @param landlordUid
	 * @return
	 */
	public Long countLandlordEvaedNum(String landlordUid){
		if (Check.NuNStr(landlordUid)){
			return 0L;
		}
		Map<String,Object> par = new HashMap<>();
		par.put("landlordUid",landlordUid);
		return mybatisDaoContext.count(SQLID + "countLandlordEvaedNum", par);
	}


	/**
	 * 房东已接受订单数
	 * @author zl
	 * @param landlordUid
	 * @return
	 */
	public Long acceptOrderNum(String landlordUid){
		if (Check.NuNStr(landlordUid)){
			return 0L;
		}
		Map<String,Object> par = new HashMap<>();
		par.put("landlordUid",landlordUid);
		return mybatisDaoContext.count(SQLID + "acceptOrderNum", par);
	}

	/**
	 * 房东拒绝的订单数
	 * @author zl
	 * @param landlordUid
	 * @return
	 */
	public Long countLanRefuseOrderNum(String landlordUid){
		if (Check.NuNStr(landlordUid)){
			return 0L;
		}
		Map<String,Object> par = new HashMap<>();
		par.put("landlordUid",landlordUid);
		return mybatisDaoContext.count(SQLID + "countLanRefuseOrderNum", par);
	}

	/**
	 * 系统拒绝的订单数
	 * @author zl
	 * @param landlordUid
	 * @return
	 */
	public Long countSysRefuseOrderNum(String landlordUid){
		if (Check.NuNStr(landlordUid)){
			return 0L;
		}
		Map<String,Object> par = new HashMap<>();
		par.put("landlordUid",landlordUid);
		return mybatisDaoContext.count(SQLID + "countSysRefuseOrderNum", par);
	}


	/**
	 * 房客获取智能锁订单数量
	 * @author afi
	 * @param userUid
	 * @return
	 */
	public Long countLock(String userUid){
		if (Check.NuNStr(userUid)){
			return 0L;
		}
		Map<String,Object> par = new HashMap<>();
		par.put("userUid",userUid);
		return mybatisDaoContext.count(SQLID + "countOrderLock", par);
	}


	/**
	 * 房客获取智能锁列表
	 * @author afi
	 * @param orderRequest
	 * @return
	 */
	public PagingResult<OrderInfoVo> getTenantOrderLockList(OrderRequest orderRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(orderRequest.getLimit());
		pageBounds.setPage(orderRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "getTenantOrderLockList", OrderInfoVo.class, orderRequest, pageBounds);
	}

	/**
	 * 
	 * 房客端已完成订单列表
	 *
	 * @author jixd
	 * @created 2016年5月3日 下午9:43:57
	 *
	 * @param orderRequest
	 * @return
	 */
	public PagingResult<OrderInfoVo> getTenantOrderDoneList(OrderRequest orderRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(orderRequest.getLimit());
		pageBounds.setPage(orderRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "getTenantOrderDoneList", OrderInfoVo.class, orderRequest, pageBounds);
	}

	/**
	 * 申请中的订单
	 * @author afi
	 * @param orderRequest
	 * @return
	 */
	public PagingResult<OrderInfoVo> getTenantOrderApplyList(OrderRequest orderRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(orderRequest.getLimit());
		pageBounds.setPage(orderRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "getTenantOrderApplyList", OrderInfoVo.class, orderRequest, pageBounds);
	}

	/**
	 * 待支付的订单
	 * @author afi
	 * @param orderRequest
	 * @return
	 */
	public PagingResult<OrderInfoVo> getTenantOrderWaitPayList(OrderRequest orderRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(orderRequest.getLimit());
		pageBounds.setPage(orderRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "getTenantOrderWaitPayList", OrderInfoVo.class, orderRequest, pageBounds);
	}



	/**
	 * 待入住的订单
	 * @author afi
	 * @param orderRequest
	 * @return
	 */
	public PagingResult<OrderInfoVo> getTenantOrderWaitCheckInList(OrderRequest orderRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(orderRequest.getLimit());
		pageBounds.setPage(orderRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "getTenantOrderWaitCheckInList", OrderInfoVo.class, orderRequest, pageBounds);
	}


	/**
	 * 待评价的订单
	 * @author afi
	 * @param orderRequest
	 * @return
	 */
	public PagingResult<OrderInfoVo> getTenantOrderWaitEvaList(OrderRequest orderRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(orderRequest.getLimit());
		pageBounds.setPage(orderRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "getTenantOrderWaitEvaList", OrderInfoVo.class, orderRequest, pageBounds);
	}

	/**
	 * 待评价和初见的订单
	 * @author afi
	 * @param orderRequest
	 * @return
	 */
	public PagingResult<OrderInfoVo> getTenantOrderWaitEvaAllList(OrderRequest orderRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(orderRequest.getLimit());
		pageBounds.setPage(orderRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "getTenantOrderWaitEvaAllList", OrderInfoVo.class, orderRequest, pageBounds);
	}



	/**
	 * 待入住+已入住的订单，(for自如app，我的旅行)
	 * @author lishaochuan
	 * @param orderRequest
	 * @return
	 */
	public PagingResult<OrderInfoVo> getTenantOrderWaitCheckOutList(OrderRequest orderRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(orderRequest.getLimit());
		pageBounds.setPage(orderRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "getTenantOrderWaitCheckOutList", OrderInfoVo.class, orderRequest, pageBounds);
	}

	/**
	 * 
	 * 房客端 待入住+已入住+已经退房且未评价 订单(for自如app，我的旅行)
	 *
	 * @author zl
	 * @created 2017年5月12日 下午3:07:54
	 *
	 * @param orderRequest
	 * @return
	 */
	public PagingResult<OrderInfoVo> getTenantActiveList(OrderRequest orderRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(orderRequest.getLimit());
		pageBounds.setPage(orderRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "getTenantActiveList", OrderInfoVo.class, orderRequest, pageBounds);
	}


	/**
	 * 
	 * 房东端订单 待确认
	 *
	 * @author jixd
	 * @created 2016年5月4日 下午6:47:58
	 *
	 * @param orderRequest
	 * @return
	 */
	public PagingResult<OrderInfoVo> getLanlordOrderWaitingList(OrderRequest orderRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(orderRequest.getLimit());
		pageBounds.setPage(orderRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "getLanlordOrderWaitingList", OrderInfoVo.class, orderRequest, pageBounds);
	}
	/**
	 * 
	 * 房东端订单 进行中
	 *
	 * @author jixd
	 * @created 2016年5月4日 下午6:48:39
	 *
	 * @param orderRequest
	 * @return
	 */
	public PagingResult<OrderInfoVo> getLanlordOrderDoingList(OrderRequest orderRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(orderRequest.getLimit());
		pageBounds.setPage(orderRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "getLanlordOrderDoingList", OrderInfoVo.class, orderRequest, pageBounds);
	}
	/**
	 * 
	 * 房东端已完成
	 *
	 * @author jixd
	 * @created 2016年5月4日 下午6:49:13
	 *
	 * @param orderRequest
	 * @return
	 */
	public PagingResult<OrderInfoVo> getLanlordOrderDoneList(OrderRequest orderRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(orderRequest.getLimit());
		pageBounds.setPage(orderRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "getLanlordOrderDoneList", OrderInfoVo.class, orderRequest, pageBounds);
	}


	/**
	 * 获取订单的信息
	 * @author afi
	 * @param orderSn
	 * @return
	 */
	public OrderInfoVo getOrderInfoByOrderSn(String orderSn){
		if(Check.NuNStr(orderSn)){
			LogUtil.info(logger,"orderSn is null on getOrderInfoByOrderSn");
			throw  new BusinessException("orderSn is null on getOrderInfoByOrderSn");
		}
		return mybatisDaoContext.findOne(SQLID + "getOrderInfoByOrderSn", OrderInfoVo.class, orderSn);

	}

	/**
	 * 
	 * 条件查询订单
	 *
	 * @author yd
	 * @created 2016年4月12日 下午3:07:26
	 *
	 * @param orderRequest
	 * @return
	 */
	public List<OrderEntity> queryOrderByCondition(OrderRequest orderRequest){
		if(Check.NuNObj(orderRequest)) return null;
		return this.mybatisDaoContext.findAll(SQLID+"queryOrderByCondition", OrderEntity.class, orderRequest.toMap());
	}


	/**
	 * 
	 * 根据订单编号修改订单的评价状态
	 *
	 * @author yd
	 * @created 2016年4月12日 下午5:43:04
	 *
	 * @param orderRequest
	 * @return
	 */
	public int updateEvaStatuByOrderSn(OrderRequest orderRequest){
		if(Check.NuNObj(orderRequest)||Check.NuNCollection(orderRequest.getListOrderSn())||Check.NuNObj(orderRequest.getEvaStatus())) return -1;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("evaStatus", orderRequest.getEvaStatus());
		map.put("listOrderSn", orderRequest.getListOrderSn());
		return this.mybatisDaoContext.update(SQLID+"updateEvaStatuByOrderSn", map);
	}






	/**
	 * 修改订单状态
	 * @author lishaochuan
	 * @create 2016年4月19日
	 * @param orderSn
	 * @param orderStatusNew
	 * @param orderStatusOld
	 * @return
	 */
	public int updateOrderStatuByOrderSn(String orderSn, int orderStatusNew, int orderStatusOld){
		if (Check.NuNObj(orderSn) || Check.NuNObj(orderStatusNew) || Check.NuNObj(orderStatusOld)) {
			LogUtil.info(logger, "orderSn|orderStatusNew|orderStatusOld is null on updateOrderStatuByOrderSn");
			throw new BusinessException("orderSn|orderStatusNew|orderStatusOld is null on updateOrderStatuByOrderSn");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderSn", orderSn);
		map.put("orderStatusNew", orderStatusNew);
		map.put("orderStatusOld", orderStatusOld);
		return this.mybatisDaoContext.update(SQLID+"updateOrderStatuByOrderSn", map);
	}

	/**
	 * 
	 * 修改结算状态
	 *
	 * @author liyingjie
	 * @created 2016年5月8日 
	 *
	 * @param orderSn
	 * @return
	 */
	public int updateAccountStatusByOrderSn(String orderSn){
		if(Check.NuNStr(orderSn)){
			return -1;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderSn", orderSn);
		return this.mybatisDaoContext.update(SQLID+"updateAccountStatusByOrderSn", map);
	}


	/**
	 * 
	 * 获取 一个房源 一个月 已经实际收益 所有的订单列表
	 * @author liyingjie
	 * @created 2016年6月25日 下午6:47:58
	 * @param request
	 * @return
	 */
	public PagingResult<OrderInfoVo> getMonthRealProfitOrderList(OrderProfitRequest request){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(request.getLimit());
		pageBounds.setPage(request.getPage());
		return mybatisDaoContext.findForPage(SQLID + "monthRealProfitOrderList", OrderInfoVo.class, request, pageBounds);
	}

	/**
	 * 
	 * 获取 一个房源 一个月 预计收益 所有的订单列表
	 * @author liyingjie
	 * @created 2016年6月25日 下午6:47:58
	 * @param request
	 * @return
	 */
	public PagingResult<OrderInfoVo> getMonthPredictProfitOrderList(OrderProfitRequest request){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(request.getLimit());
		pageBounds.setPage(request.getPage());
		return mybatisDaoContext.findForPage(SQLID + "monthPredictProfitOrderList", OrderInfoVo.class, request, pageBounds);
	}


	/**
	 * 
	 * 计算房源订单数量
	 * @author liyingjie
	 * @created 2016年6月25日 下午6:47:58
	 * @param request
	 * @return
	 */
	public Long countOrderList(OrderProfitRequest request){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("houseFid", request.getHouseFid());
		params.put("roomFid", request.getRoomFid());
		params.put("rentWay", request.getRentWay());
		params.put("beginTime", request.getBeginTime());
		params.put("endTime", request.getEndTime());
		return mybatisDaoContext.count(SQLID +"countOrderNum", params);
	}

	/**
	 * 
	 * 计算房东订单总数量
	 * @author liyingjie
	 * @created 2016年6月25日 下午6:47:58
	 * @param request
	 * @return
	 */
	public Long staticsCountLanOrderNum(OrderStaticsRequest request){

		if(Check.NuNObj(request)){
			LogUtil.info(logger, "staticsCountLanOrderNum 参数对象为空");
			throw new BusinessException("staticsCountLanOrderNum 参数对象为空");
		}
		if(Check.NuNStr(request.getLandlordUid())){
			LogUtil.info(logger, "staticsCountLanOrderNum landlordUid为空");
			throw new BusinessException("staticsCountLanOrderNum landlordUid为空");
		}

		if(Check.NuNObj(request.getLimitTime())){
			LogUtil.info(logger, "staticsCountLanOrderNum limitTime:{}",request.getLimitTime());
			throw new BusinessException("staticsCountLanOrderNum limitTime为空");
		}

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("landlordUid", request.getLandlordUid());
		params.put("limitTime", request.getLimitTime());

		return mybatisDaoContext.count(SQLID +"staticsCountLanOrderNum", params);
	}

	/**
	 * 
	 * 房东X分钟内响应的订单数量
	 * @author liyingjie
	 * @created 2016年6月25日 下午6:47:58
	 * @param request
	 * @return
	 */
	public Long staticsCountLanReplyOrderNum(OrderStaticsRequest request){

		if(Check.NuNObj(request)){
			LogUtil.info(logger, "staticsCountLanReplyOrderNum 参数对象为空");
			throw new BusinessException("staticsCountLanReplyOrderNum 参数对象为空");
		}
		if(Check.NuNStr(request.getLandlordUid())){
			LogUtil.info(logger, "staticsCountLanReplyOrderNum landlordUid为空");
			throw new BusinessException("staticsCountLanReplyOrderNum landlordUid为空");
		}

		if(Check.NuNObj(request.getLimitTime())){
			LogUtil.info(logger, "staticsCountLanReplyOrderNum limitTime:{}",request.getLimitTime());
			throw new BusinessException("staticsCountLanReplyOrderNum limitTime为空");
		}

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("landlordUid", request.getLandlordUid());
		params.put("limitTime", request.getLimitTime());
		params.put("sumTime", request.getSumTime());

		return mybatisDaoContext.count(SQLID +"staticsCountLanReplyOrderNum", params);
	}

	/**
	 * 
	 * 房东拒绝的订单数量
	 * @author liyingjie
	 * @created 2016年6月25日 下午6:47:58
	 * @param request
	 * @return
	 */
	public Long staticsCountLanRefuseOrderNum(OrderStaticsRequest request){

		if(Check.NuNObj(request)){
			LogUtil.info(logger, "staticsCountLanRefuseOrderNum 参数对象为空");
			throw new BusinessException("staticsCountLanRefuseOrderNum 参数对象为空");
		}
		if(Check.NuNStr(request.getLandlordUid())){
			LogUtil.info(logger, "staticsCountLanRefuseOrderNum landlordUid为空");
			throw new BusinessException("staticsCountLanRefuseOrderNum landlordUid为空");
		}

		if(Check.NuNObj(request.getLimitTime())){
			LogUtil.info(logger, "staticsCountLanRefuseOrderNum limitTime:{}",request.getLimitTime());
			throw new BusinessException("staticsCountLanRefuseOrderNum limitTime为空");
		}

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("landlordUid", request.getLandlordUid());
		params.put("limitTime", request.getLimitTime());
		params.put("sumTime", request.getSumTime());

		return mybatisDaoContext.count(SQLID +"staticsCountLanRefuseOrderNum", params);
	}

	/**
	 * 
	 * 超时系统拒绝的订单数量
	 * @author liyingjie
	 * @created 2016年6月25日 下午6:47:58
	 * @param request
	 * @return
	 */
	public Long staticsCountSysRefuseOrderNum(OrderStaticsRequest request){

		if(Check.NuNObj(request)){
			LogUtil.info(logger, "staticsCountSysRefuseOrderNum 参数对象为空");
			throw new BusinessException("staticsCountSysRefuseOrderNum 参数对象为空");
		}
		if(Check.NuNStr(request.getLandlordUid())){
			LogUtil.info(logger, "staticsCountSysRefuseOrderNum landlordUid为空");
			throw new BusinessException("staticsCountSysRefuseOrderNum landlordUid为空");
		}

		if(Check.NuNObj(request.getLimitTime())){
			LogUtil.info(logger, "staticsCountSysRefuseOrderNum limitTime:{}",request.getLimitTime());
			throw new BusinessException("staticsCountSysRefuseOrderNum limitTime为空");
		}

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("landlordUid", request.getLandlordUid());
		params.put("limitTime", request.getLimitTime());

		return mybatisDaoContext.count(SQLID +"staticsCountSysRefuseOrderNum", params);
	}

	/**
	 * 
	 * 房东响应时间总和
	 * @author liyingjie
	 * @created 2016年6月25日 下午6:47:58
	 * @param request
	 * @return
	 */
	public Long staticsCountLanReplyOrderTime(OrderStaticsRequest request){

		if(Check.NuNObj(request)){
			LogUtil.info(logger, "staticsCountLanReplyOrderTime 参数对象为空");
			throw new BusinessException("staticsCountLanReplyOrderTime 参数对象为空");
		}
		if(Check.NuNStr(request.getLandlordUid())){
			LogUtil.info(logger, "staticsCountLanReplyOrderTime landlordUid为空");
			throw new BusinessException("staticsCountLanReplyOrderTime landlordUid为空");
		}

		if(Check.NuNObj(request.getLimitTime())){
			LogUtil.info(logger, "staticsCountLanReplyOrderTime limitTime:{}",request.getLimitTime());
			throw new BusinessException("staticsCountLanReplyOrderTime limitTime为空");
		}

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("landlordUid", request.getLandlordUid());
		params.put("limitTime", request.getLimitTime());
		params.put("sumTime", request.getSumTime());

		return mybatisDaoContext.count(SQLID +"staticsCountLanReplyOrderTime", params);
	}


	/**
	 * troy查询返现申请列表
	 * @author lishaochuan
	 * @create 2016年9月8日下午12:04:25
	 * @param request
	 * @return
	 */
	public PagingResult<OrderCashbackVo> getOrderCashbackList(OrderCashbackRequest request){
		if(Check.NuNObj(request)){
			LogUtil.info(logger, "getOrderCashbackList 参数对象为空");
			throw new BusinessException("getOrderCashbackList 参数对象为空");
		}
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(request.getLimit());
		pageBounds.setPage(request.getPage());
		return mybatisDaoContext.findForPage(SQLID + "getOrderCashbackList", OrderCashbackVo.class, request.toMap(), pageBounds);
	}


	/**
	 * 
	 * 根据　用户uid 判断用户是否符合首单立减用户  供下单使用
	 * 1. 用户存在 有效订单且发生过入住  
	 * 2. 用户存在有效订单且未发生过入住且违约金大于0
	 * 3. 取 满足1且满足2  如果>0 不是新用户  ==0 是新用户
	 *
	 * @author yd
	 * @created 2017年6月5日 下午4:13:31
	 *
	 * @param uid
	 * @return
	 */
	public boolean isNewUserByOrder(String uid){

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("uid", uid);
		Long num = mybatisDaoContext.count(SQLID+"queryNewUserByOrder", paramMap);
		if(Check.NuNObj(num)||num.intValue() == 0){
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 根据　用户uid 判断用户是否是新人  供首页使用
	 * 
	 * 说明： 只判断当前用户是否有订单
	 *
	 * @author yd
	 * @created 2017年6月16日 下午12:05:05
	 *
	 * @param uid
	 * @return
	 */
	public boolean isNewUserByOrderForFirstPage(String uid){

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("uid", uid);
		Long num = mybatisDaoContext.count(SQLID+"queryOrderCountByUid", paramMap);
		if(Check.NuNObj(num)||num.intValue() == 0){
			return true;
		}
		return false;
	}

	/**
	 * 
	 *  房东进击计划：查询 房东 下单返现活动 订单
	 *
	 * @author yd
	 * @created 2017年8月31日 上午11:58:03
	 *
	 * @return
	 */
	public PagingResult<CashbackOrderVo>  queryCashbackOrderVo(OrderCashbackRequest request){
		if(Check.NuNObj(request)){
			LogUtil.info(logger, "queryCashbackOrderVo 参数对象为空");
			throw new BusinessException("queryCashbackOrderVo 参数对象为空");
		}
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(request.getLimit());
		pageBounds.setPage(request.getPage());
		return mybatisDaoContext.findForPage(SQLID + "queryCashbackOrderVo", CashbackOrderVo.class, request.toMap(), pageBounds);
	}
	
	/**
	 * 
	 * 条件分页查询订单
	 *
	 * @author yd
	 * @created 2016年4月12日 下午3:07:26
	 *
	 * @param orderRequest
	 * @return
	 */
	public PagingResult<OrderEntity> queryOrderByPage(OrderRequest orderRequest){
		
		if(Check.NuNObj(orderRequest)){
			LogUtil.info(logger, "queryOrderByPage 参数对象为空");
			throw new BusinessException("queryOrderByPage 参数对象为空");
		}
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(orderRequest.getLimit());
		pageBounds.setPage(orderRequest.getPage());
		return this.mybatisDaoContext.findForPage(SQLID+"queryOrderByPage", OrderEntity.class, orderRequest.toMap(), pageBounds);
	}

    /**
     *
     * 查询昨天遗漏的订单行为(房东行为成长体系定时任务)
     *
     * @author zhangyl2
     * @created 2017年10月13日 12:58
     * @param
     * @return
     */
    public List<OrderEntity> queryOrderForCustomerBehaviorJob(OrderRequest orderRequest){
        return this.mybatisDaoContext.findAll(SQLID+"queryOrderForCustomerBehaviorJob", OrderEntity.class, orderRequest.toMap());
    }


	/**
	 * 询房东近60天内的，通过“申请类型”的订单数量 
	 *
	 * @author loushuai
	 * @created 2017年10月26日 上午9:27:06
	 *
	 * @param uid
	 * @return
	 */
	public long countAcceptApplyOrder(String uid) {
		if(Check.NuNStr(uid)){
			return 0;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("uid", uid);
		long count = this.mybatisDaoContext.count(SQLID+"countAcceptApplyOrder", param);
		return count;
	}


	/**
	 * 查询房东近60天内的，所有“申请类型”的订单数量
	 *
	 * @author loushuai
	 * @created 2017年10月26日 上午9:27:12
	 *
	 * @param uid
	 * @return
	 */
	public long countAllApplyOrder(String uid) {
		if(Check.NuNStr(uid)){
			return 0;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("uid", uid);
		return this.mybatisDaoContext.count(SQLID+"countAllApplyOrder", param);
	}


	/**
	 * 房东首页-当月即将到来的订单
	 *
	 * @author loushuai
	 * @created 2017年11月16日 上午10:45:00
	 *
	 * @param orderRequest
	 * @return
	 */
	public PagingResult<OrderInfoVo> getLanlordOrderComingList(OrderRequest orderRequest) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(orderRequest.getLimit());
		pageBounds.setPage(orderRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "getLanlordOrderComingList", OrderInfoVo.class, orderRequest, pageBounds);
	}

	/**
	 * 查询当前时间4个小时内的已结算的订单的uid和orderSn
	 * @author loushuai
	 * @return
	 */
	public List<OrderInviteVo> queryOrder4Hour() {
		return mybatisDaoContext.findAll(SQLID + "queryOrder4Hour", OrderInviteVo.class);
	}


	/**
	 * 房东评价成功,展示房东未评价列表
	 *
	 * @author 娄帅
	 * @created 2018年1月9日 下午5:14:36
	 *
	 * @param orderEvalRequest
	 * @return
	 */
	public PagingResult<OrderInfoVo> getLandUnEvalList(OrderEvalRequest orderEvalRequest) {
		PageBounds pageBounds = new PageBounds();
		if(	Check.NuNObj(orderEvalRequest.getLimitDay())){
			return null;
		}
		pageBounds.setLimit(orderEvalRequest.getLimit());
		pageBounds.setPage(orderEvalRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "getLandUnEvalList", OrderInfoVo.class, orderEvalRequest, pageBounds);
	}
}
