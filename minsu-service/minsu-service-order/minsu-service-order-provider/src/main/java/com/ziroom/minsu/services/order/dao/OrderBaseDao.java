package com.ziroom.minsu.services.order.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.InviteEntity;
import com.ziroom.minsu.entity.message.MsgFirstAdvisoryEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.finance.entity.RefuseOrderVo;
import com.ziroom.minsu.services.finance.entity.RemindOrderVo;
import com.ziroom.minsu.services.order.dto.BeInviterStatusInfoRequest;
import com.ziroom.minsu.services.order.dto.OrderTaskRequest;
import com.ziroom.minsu.services.order.entity.BeInviterStatusInfoVo;
import com.ziroom.minsu.services.order.entity.OrderHouseVo;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.order.OrderFlagEnum;
import com.ziroom.minsu.valenum.order.OrderPayStatusEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p> 订单的主表操作 </p>
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
@Repository("order.orderBaseDao")
public class OrderBaseDao {
	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(OrderBaseDao.class);

	private String SQLID = "order.orderBaseDao.";

	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;



    /**
     * 获取当前房源下的所有未开始的订单
     * @param houseFid
     * @return
     */
    public List<String>  getOrderSnList4LockByHouse(String houseFid){
        if (Check.NuNStr(houseFid)){
            return null;
        }
        Map<String,Object> par = new HashMap<>();
        par.put("houseFid",houseFid);
        return mybatisDaoContext.findAll(SQLID + "getOrderSnList4LockByHouse", String.class, par);
    }


    /**
     * 获取当前房源下的所有未开始的订单
     * @param rooms
     * @return
     */
    public List<String>  getOrderSnList4LockByRooms(List<String> rooms){
        if (Check.NuNCollection(rooms)){
            return null;
        }
        Map<String,Object> par = new HashMap<>();
        par.put("rooms",rooms);
        return mybatisDaoContext.findAll(SQLID + "getOrderSnList4LockByRooms", String.class, par);
    }

    /**
     * 获取当前用户在当前城市下的未支付订单数量
     * @author afi
     * @param cityCode
     * @param uid
     * @return
     */
    public Long countCurrentNoPayOrder(String cityCode , String uid){

        Map<String,Object> countPar = new HashMap<>();
        countPar.put("cityCode",cityCode);
        countPar.put("uid",uid);
        return mybatisDaoContext.count(SQLID + "countCurrentNoPayOrder", countPar);
    }



    /**
     * 获取超过退房时间的数量
     * @author afi
     * @return
     */
    public Long taskGetOverCheckOutNum(OrderTaskRequest taskRequest){
        if(Check.NuNObj(taskRequest)){
            LogUtil.error(logger,"taskRequest is null on taskGetOverCheckOutNum ");
            throw new BusinessException("taskRequest is null on taskGetOverCheckOutNum ");
        }
        if(Check.NuNObj(taskRequest.getPayStatus())
                || Check.NuNObj(taskRequest.getOrderStatus())
                || Check.NuNObj(taskRequest.getLimitDate())){
            LogUtil.error(logger,"par is null taskGetOverCheckOutNum");
            throw new BusinessException("par is null taskGetOverCheckOutNum");
        }
        Map<String,Object> par = new HashMap<>();
        par.put("payStatus",taskRequest.getPayStatus());
        par.put("orderStatus",taskRequest.getOrderStatus());
        par.put("limitDate",taskRequest.getLimitDate());
        return mybatisDaoContext.count(SQLID + "taskGetOverCheckOutNum", par);
    }



    /**
     * 获取超过入住时间的数量
     * @author afi
     * @return
     */
    public Long taskGetOverCheckInNum(OrderTaskRequest taskRequest){
        if(Check.NuNObj(taskRequest)){
        	LogUtil.error(logger,"taskRequest is null on taskGetOverCheckInNum ");
            throw new BusinessException("taskRequest is null on taskGetOverCheckInNum ");
        }
        if(Check.NuNObj(taskRequest.getPayStatus())
                || Check.NuNObj(taskRequest.getOrderStatus())
                || Check.NuNObj(taskRequest.getLimitDate())){
        	LogUtil.error(logger,"par is null taskGetOverCheckInNum");
            throw new BusinessException("par is null taskGetOverCheckInNum");
        }
        Map<String,Object> par = new HashMap<>();
        par.put("payStatus",taskRequest.getPayStatus());
        par.put("orderStatus",taskRequest.getOrderStatus());
        par.put("limitDate",taskRequest.getLimitDate());
        return mybatisDaoContext.count(SQLID + "taskGetOverCheckInNum", par);
    }





    /**
     * 获取超时取消的订单编号的列表
     * @param taskRequest
     * @return
     */
    public List<String> taskGetOverCheckInTimeOrderSnList(OrderTaskRequest taskRequest){
        if(Check.NuNObj(taskRequest)){
        	LogUtil.error(logger,"taskRequest is null on taskGetOverCheckInTimeOrderSnList ");
            throw new BusinessException("taskRequest is null on taskGetOverCheckInTimeOrderSnList ");
        }
        if(Check.NuNObj(taskRequest.getPayStatus())
                || Check.NuNObj(taskRequest.getOrderStatus())
                || Check.NuNObj(taskRequest.getLimitDate())){
        	LogUtil.error(logger,"par is null taskGetOverCheckInTimeOrderSnList");
            throw new BusinessException("par is null taskGetOverCheckInTimeOrderSnList");
        }
        Map<String,Object> par = new HashMap<>();
        par.put("payStatus",taskRequest.getPayStatus());
        par.put("orderStatus",taskRequest.getOrderStatus());
        par.put("limitDate",taskRequest.getLimitDate());
        par.put("limit",taskRequest.getLimit());
        return mybatisDaoContext.findAllByMaster(SQLID + "taskGetOverCheckInTimeOrderSnList",String.class, par);
    }



    /**
     * 已退房的订单列表
     * @param taskRequest
     * @return
     */
    public List<String> taskGetToCheckOutOrderSnList(OrderTaskRequest taskRequest){
        if(Check.NuNObj(taskRequest)){
            LogUtil.error(logger,"taskRequest is null on taskGetToCheckOutOrderSnList ");
            throw new BusinessException("taskRequest is null on taskGetToCheckOutOrderSnList ");
        }
        if(Check.NuNObj(taskRequest.getPayStatus())
                || Check.NuNObj(taskRequest.getOrderStatus())
                || Check.NuNObj(taskRequest.getLimitDate())){
            LogUtil.error(logger,"par is null taskGetToCheckOutOrderSnList");
            throw new BusinessException("par is null taskGetToCheckOutOrderSnList");
        }
        Map<String,Object> par = new HashMap<>();
        par.put("payStatus",taskRequest.getPayStatus());
        par.put("orderStatus",taskRequest.getOrderStatus());
        par.put("limitDate",taskRequest.getLimitDate());
        par.put("limit",taskRequest.getLimit());
        return mybatisDaoContext.findAllByMaster(SQLID + "taskGetToCheckOutOrderSnList",String.class, par);
    }

    /**
     * 获取超时取消的订单编号的列表
     * @param taskRequest
     * @return
     */
    public List<String> taskGetToCanceOrderSnList(OrderTaskRequest taskRequest){
        if(Check.NuNObj(taskRequest)){
        	LogUtil.error(logger,"taskRequest is null on taskGetToCanceOrderSnList ");
            throw new BusinessException("taskRequest is null on taskGetToCanceOrderSnList ");
        }
        if(Check.NuNObj(taskRequest.getPayStatus())
                || Check.NuNObj(taskRequest.getOrderStatus())
                || Check.NuNObj(taskRequest.getLimitDate())){
        	LogUtil.error(logger,"par is null taskGetToCanceOrderSnList");
            throw new BusinessException("par is null taskGetToCanceOrderSnList");
        }
        Map<String,Object> par = new HashMap<>();
        par.put("payStatus",taskRequest.getPayStatus());
        par.put("orderStatus",taskRequest.getOrderStatus());
        par.put("limitDate",taskRequest.getLimitDate());
        par.put("limit",taskRequest.getLimit());
        return mybatisDaoContext.findAllByMaster(SQLID + "taskGetToCanceOrderSnList",String.class, par);
    }



    /**
     * 获取当前用户在当前城市下的未支付订单数量
     * @author afi
     * @return
     */
    public Long taskGetOverTimeNum(OrderTaskRequest taskRequest){
        if(Check.NuNObj(taskRequest)){
        	LogUtil.error(logger,"taskRequest is null on taskGetToCanceNum ");
            throw new BusinessException("taskRequest is null on taskGetToCanceNum ");
        }
        if(Check.NuNObj(taskRequest.getPayStatus())
                || Check.NuNObj(taskRequest.getOrderStatus())
                || Check.NuNObj(taskRequest.getLimitDate())){
        	LogUtil.error(logger,"par is null taskGetToCanceNum");
            throw new BusinessException("par is null taskGetToCanceNum");
        }
        Map<String,Object> par = new HashMap<>();
        par.put("payStatus",taskRequest.getPayStatus());
        par.put("orderStatus",taskRequest.getOrderStatus());
        par.put("limitDate",taskRequest.getLimitDate());
        return mybatisDaoContext.count(SQLID + "taskGetOverTimeNum", par);
    }
    
    /**
     * 第二天符合条件的订单 count
     * @author jixd on 2016年4月20日
     */
    public Long taskGetTomorrowOrderNum(){
        Map<String,Object> par = new HashMap<>();
        //获取前一天的日期
        par.put("limitDate", DateUtil.getDayAfterCurrentDate());
    	return  mybatisDaoContext.countBySlave(SQLID + "taskGetTomorrowOrderNum", par);
    	
    }
    /**
     * 获取第二天待入住订单列表
     * @author jixd on 2016年4月20日
     */
    public List<OrderEntity> taskGetTomorrowOrderList(OrderTaskRequest taskRequest){
    	if(Check.NuNObj(taskRequest)){
        	LogUtil.error(logger,"taskRequest is null on taskGetToCanceNum ");
            throw new BusinessException("taskRequest is null on taskGetToCanceNum ");
        }
        if(Check.NuNObj(taskRequest.getPayStatus())
                || Check.NuNObj(taskRequest.getOrderStatus())
                || Check.NuNObj(taskRequest.getLimitDate())){
        	LogUtil.error(logger,"par is null taskGetToCanceNum");
            throw new BusinessException("par is null taskGetToCanceNum");
        }
        Map<String,Object> par = new HashMap<>();
        par.put("limitDate", DateUtil.getDayAfterCurrentDate());
        par.put("limit",taskRequest.getLimit());
    	return  mybatisDaoContext.findAll(SQLID + "taskGetTomorrowOrderList",OrderEntity.class,par);
    	
    }

    /**
     * 获取当前订单的信息
     * @author afi
     * @param orderSn
     * @return
     */
    public OrderEntity getOrderBaseByOrderSn(String orderSn){
        if(Check.NuNStr(orderSn)){
        	LogUtil.error(logger,"orderSn is null on getOrderINfoByOrderSn");
            throw  new BusinessException("orderSn is null on getOrderINfoByOrderSn");
        }
        return mybatisDaoContext.findOne(SQLID + "getOrderBaseByOrderSn", OrderEntity.class, orderSn);
    }
    
    
    
    /**
     * 根据订单号list获取订单list
     * @author lishaochuan
     * @create 2016年9月11日下午5:37:03
     * @param orderSns
     * @return
     */
    public List<OrderEntity> getOrdersBySns(Set<String> orderSns){
        if(Check.NuNCollection(orderSns)){
        	LogUtil.error(logger,"参数为空，orderSns：{}", orderSns);
            throw new BusinessException("参数为空");
        }
        Map<String,Object> par = new HashMap<>();
        par.put("orderSns", orderSns);
        return mybatisDaoContext.findAllByMaster(SQLID + "getOrdersBySns", OrderEntity.class, par);
    }
    
    


    /**
     * 保存订单信息
     * @author afi
     * @param orderEntity
     * @return
     */
    public int insertOrderBase(OrderEntity orderEntity){
        return mybatisDaoContext.save(SQLID + "insertOrderBase", orderEntity);
    }


    /**
     * 更新订单的信息
     * @author afi
     * @param orderEntity
     * @return
     */
    public int updateOrderBaseByOrderSn(OrderEntity orderEntity){
        if(Check.NuNObj(orderEntity)){
        	LogUtil.error(logger,"orderEntity is null on updateOrderBaseByOrderSn");
            throw new BusinessException("orderEntity is null on updateOrderBaseByOrderSn");
        }
        if(Check.NuNStr(orderEntity.getOrderSn())){
        	LogUtil.error(logger,"orderSn is null on updateOrderBaseByOrderSn");
            throw new BusinessException("orderSn is null on updateOrderBaseByOrderSn");
        }
        return mybatisDaoContext.update(SQLID + "updateOrderBaseByOrderSn", orderEntity);
    }
 
    
    

    /**
     * 根据订单编号查询订单数量
     * @author afi
     * @created 2016年4月8日 下午5:50:21
     * @param orderSn
     * @return
     */
    public boolean  checkOrderByLoadlord(String orderSn,String landlordUid){
        if(Check.NuNStr(orderSn) || Check.NuNStr(landlordUid)){
        	LogUtil.error(logger,"par is null on checkOrderByLoadlord");
            throw new BusinessException("par is null on checkOrderByLoadlord");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("orderSn",orderSn);
        params.put("landlordUid",landlordUid);
        boolean flag  = true;
        try {
			  flag = mybatisDaoContext.countBySlave(SQLID + "countOrderByLoadlord", params)>0?true:false;
		} catch (Exception e) {
            LogUtil.error(logger,"e:{}",e);
		}
        return flag;
    }



    /**
     *  更新 订单 为已取消
     * @author liyingjie
     * @created 2016年4月5日
     *
     * @param orderSn
     * @return
     */
    public int taskUpdateOrderToCancelStatus(String orderSn){
        Map<String,Object> paramMap = new HashMap<String,Object>(1);
        paramMap.put("orderSn", orderSn);
        return mybatisDaoContext.update(SQLID + "taskUpdateOrderToCancelStatus", paramMap);
    }





    /**
     *  更新 订单 为已入住
     * @author liyingjie
     * @created 2016年4月5日
     *
     * @param orderSnStr
     * @return
     */
    public int taskUpdateOrderToCheckInStatus(String orderSn){
        Map<String,Object> paramMap = new HashMap<String,Object>(1);
        paramMap.put("orderSn", orderSn);
        return mybatisDaoContext.update(SQLID+"taskUpdateOrderToCheckInStatus", paramMap);
    }
    
    
    
    
    /**
     * 根据订单状态获取订单数量
     * @author lishaochuan
     * @create 2016年4月19日
     * @param orderStatus
     * @return
     */
    public Long getOrderCountByStatus(int orderStatus){
        if(Check.NuNObj(orderStatus)){
        	LogUtil.error(logger,"orderStatus is null on getOrderCountByStatus ");
            throw new BusinessException("orderStatus is null on getOrderCountByStatus ");
        }
        Map<String,Object> par = new HashMap<>();
        par.put("orderStatus", orderStatus);
        return mybatisDaoContext.count(SQLID + "getOrderCountByStatus", par);
    }
    
    /**
     * 根据订单状态获取订单list
     * @author lishaochuan
     * @create 2016年4月19日
     * @param orderStatus
     * @return
     */
    public List<OrderEntity> getOrderListByStatus(int orderStatus, int limit){
        if(Check.NuNObj(orderStatus)){
        	LogUtil.error(logger,"orderStatus is null on getOrderListByStatus ");
            throw new BusinessException("orderStatus is null on getOrderListByStatus ");
        }
        Map<String,Object> par = new HashMap<>();
        par.put("orderStatus", orderStatus);
        par.put("limit", limit);
        return mybatisDaoContext.findAllByMaster(SQLID + "getOrderListByStatus", OrderEntity.class, par);
    }
    
    
    /**
     * 查询一定时间内未操作的订单count
     * @author lishaochuan
     * @create 2016年5月12日上午12:28:27
     * @param orderStatusList
     * @param limitTime
     * @return
     */
	public Long getNoOperateOrderCount(List<Integer> orderStatusList, Date limitTime) {
		if (Check.NuNObj(orderStatusList) || Check.NuNObj(limitTime)) {
			LogUtil.error(logger, "params is null on getNoOperateOrderCount ");
			throw new BusinessException("params is null on getNoOperateOrderCount ");
		}
		Map<String, Object> par = new HashMap<>();
		par.put("orderStatusList", orderStatusList);
		par.put("limitTime", limitTime);
		return mybatisDaoContext.count(SQLID + "getNoOperateOrderCount", par);
	}
    
	/**
	 * 查询一定时间内未操作的订单订单list
	 * @author lishaochuan
	 * @create 2016年5月12日上午12:30:32
	 * @param orderStatusList
	 * @param limitTime
	 * @param limit
	 * @return
	 */
	public List<OrderEntity> getNoOperateOrderList(List<Integer> orderStatusList, Date limitTime, int limit) {
		if (Check.NuNObj(orderStatusList) || Check.NuNObj(limitTime)) {
			LogUtil.error(logger, "params is null on getNoOperateOrderList ");
			throw new BusinessException("params is null on getNoOperateOrderList ");
		}
		Map<String, Object> par = new HashMap<>();
		par.put("orderStatusList", orderStatusList);
		par.put("limitTime", limitTime);
		par.put("limit", limit);
		return mybatisDaoContext.findAllByMaster(SQLID + "getNoOperateOrderList", OrderEntity.class, par);
	}

    /**
     *  查询房东待确认的清单  只给房东超时未确认的定时任务用
     * @author yd
     * @created
     * @param
     * @return
     */
	public List<OrderEntity> getWaitConformOrderList(List<Integer> orderStatusList, Date limitTime, int limit) {
        if (Check.NuNObj(orderStatusList) || Check.NuNObj(limitTime)) {
            LogUtil.error(logger, "params is null on getNoOperateOrderList ");
            throw new BusinessException("params is null on getNoOperateOrderList ");
        }
        Map<String, Object> par = new HashMap<>();
        par.put("orderStatusList", orderStatusList);
        par.put("limitTime", limitTime);
        par.put("limit", limit);
        return mybatisDaoContext.findAllByMaster(SQLID + "getWaitConformOrderList", OrderEntity.class, par);
    }


    /**
     *  查询房东待确认的清单  只给房东超时未确认的定时任务用
     * @author yd
     * @created
     * @param
     * @return
     */
    public Long getWaitConformOrderCount(List<Integer> orderStatusList, Date limitTime) {
        if (Check.NuNObj(orderStatusList) || Check.NuNObj(limitTime)) {
            LogUtil.error(logger, "params is null on getNoOperateOrderCount ");
            throw new BusinessException("params is null on getNoOperateOrderCount ");
        }
        Map<String, Object> par = new HashMap<>();
        par.put("orderStatusList", orderStatusList);
        par.put("limitTime", limitTime);

        return mybatisDaoContext.count(SQLID + "getWaitConformOrderCount", par);
    }
	
	/**
	 * 查询下单后未提醒房东确认的订单count
	 * @author lishaochuan
	 * @create 2016年5月15日下午10:13:17
	 * @param orderStatus
	 * @param createStartTime
	 * @param createEndTime
	 * @return
	 */
	public Long getWaitConfimOrderCount(Date limitDate) {
		if (Check.NuNObj(limitDate)) {
			LogUtil.error(logger, "参数错误,limitDate:{}", limitDate);
			throw new BusinessException("参数错误");
		}
		Map<String, Object> par = new HashMap<>();
		par.put("orderStatus", OrderStatusEnum.WAITING_CONFIRM.getOrderStatus());
		par.put("limitDate", limitDate);
		par.put("flagCode", OrderFlagEnum.REMIND_ACCEPT_ORDER.getCode());
		par.put("flagValue", YesOrNoEnum.YES.getStr());
		return mybatisDaoContext.countBySlave(SQLID + "getWaitConfimOrderCount", par);
	}
	
	/**
	 * 查询下单后未提醒房东确认的订单list
	 * @author lishaochuan
	 * @create 2016年5月15日下午10:13:17
	 * @param orderStatus
	 * @param createStartTime
	 * @param createEndTime
	 * @return
	 */
	public List<OrderEntity> getWaitConfimOrderList(int limit, Date limitDate) {
		if (Check.NuNObj(limit) || Check.NuNObj(limitDate)) {
			LogUtil.error(logger, "参数错误,limit:{},limitDate:{}",limit,limitDate);
			throw new BusinessException("参数错误");
		}
		Map<String, Object> par = new HashMap<>();
		par.put("orderStatus", OrderStatusEnum.WAITING_CONFIRM.getOrderStatus());
		par.put("limitDate", limitDate);
		par.put("flagCode", OrderFlagEnum.REMIND_ACCEPT_ORDER.getCode());
		par.put("flagValue", YesOrNoEnum.YES.getStr());
		par.put("limit", limit);
		return mybatisDaoContext.findAll(SQLID + "getWaitConfimOrderList", OrderEntity.class, par);
	}
    
    
    
    /**
     * 更新订单支付信息的信息
     * @author liyingjie
     * @param orderEntity
     * @return
     */
    public int updateHasPayStatusOrderSn(OrderEntity orderEntity){
        if(Check.NuNObj(orderEntity)){
        	LogUtil.error(logger,"orderEntity is null on updateOrderPayStatusByOrderSn");
            throw new BusinessException("orderEntity is null on updateOrderPayStatusByOrderSn");
        }
        if(Check.NuNStr(orderEntity.getOrderSn())){
        	LogUtil.error(logger,"orderSn is null on updateOrderPayStatusByOrderSn");
            throw new BusinessException("orderSn is null on updateOrderPayStatusByOrderSn");
        }
        Map<String,Object> par = new HashMap<>();
        par.put("payStatus", orderEntity.getPayStatus());
        par.put("oldPayStatus", OrderPayStatusEnum.UN_PAY.getPayStatus());
        par.put("orderSn", orderEntity.getOrderSn());
        par.put("payTime", orderEntity.getPayTime());
        return mybatisDaoContext.update(SQLID + "updateOrderPayStatusByOrderSn", par);
    }
    
    
    
    
    /**
     * 申请预订且房东未回复的订单
     * @author lishaochuan
     * @create 2016年8月1日下午6:27:45
     * @return
     */
    public PagingResult<RemindOrderVo> getRemindOrderList(PageRequest pageRequest) {
    	PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(pageRequest.getLimit());
		pageBounds.setPage(pageRequest.getPage());
    	
    	
		Map<String, Object> par = new HashMap<>();
		par.put("orderStatus", OrderStatusEnum.WAITING_CONFIRM.getOrderStatus());
		return mybatisDaoContext.findForPage(SQLID + "getRemindOrderList", RemindOrderVo.class, par, pageBounds);
	}
    
    
    /**
     * 查询房东拒绝的申请预定（12小时以内）
     * @author lishaochuan
     * @create 2016年8月3日下午2:28:00
     * @param pageRequest
     * @return
     */
    public PagingResult<RefuseOrderVo> getRefuseOrderList(PageRequest pageRequest) {
    	PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(pageRequest.getLimit());
		pageBounds.setPage(pageRequest.getPage());
    	
    	
		Map<String, Object> par = new HashMap<>();
		par.put("orderStatus", OrderStatusEnum.REFUSED.getOrderStatus());
		par.put("limitDate", DateSplitUtil.jumpHours(new Date(), -12));
		return mybatisDaoContext.findForPage(SQLID + "getRefuseOrderList", RefuseOrderVo.class, par, pageBounds);
	}


    /**
     * 查询当天退房的订单
     * @author lisc
     * @return
     */
    public List<OrderEntity> getCheckOutTodayList(){
        Map<String, Object> par = new HashMap<>();
        par.put("orderStatus", OrderStatusEnum.CHECKED_IN_BILL.getOrderStatus());
        return mybatisDaoContext.findAll(SQLID + "getCheckOutTodayList", OrderEntity.class, par);
    }


    /**
     * 检查被邀请人是否有已经入住订单
     * @author lishaochuan
     * @param inviteEntity
     * @return
     */
    public Long checkIfInviteCheckInOrder(InviteEntity inviteEntity){
        return mybatisDaoContext.count(SQLID + "checkIfInviteCheckInOrder", inviteEntity.toMap());
    }


	/**
	 * 获取每天将要入住的订单，给房东发短信
	 *
	 * @author loushuai
	 * @created 2017年7月31日 上午10:06:59
	 *
	 * @param paramMap
	 * @return
	 */
	public PagingResult<OrderHouseVo> getWaitCheckinList(PageRequest pageRequest) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(pageRequest.getPage());
		pageBounds.setLimit(pageRequest.getLimit());
		return mybatisDaoContext.findForPage(SQLID+"getWaitCheckinList", OrderHouseVo.class, null, pageBounds);
	}

    /**
     * @description: 获取用户咨询时有没有订单信息
     * @author: lusp
     * @date: 2017/8/11 21:41
     * @params: param
     * @return:
     */
    public OrderEntity getAdvisoryOrderInfo(MsgFirstAdvisoryEntity msgFirstAdvisoryEntity) {
        return mybatisDaoContext.findOne(SQLID+"getAdvisoryOrderInfo", OrderEntity.class, msgFirstAdvisoryEntity);
    }


	/**
	 * 根据订单号修改订单状态 
	 *
	 * @author loushuai
	 * @created 2017年10月11日 下午5:48:56
	 *
	 * @param orderEntity
	 * @return
	 */
	public int updateOrderStatusByOrderSn(OrderEntity orderEntity) {
		return mybatisDaoContext.update(SQLID+"updateOrderStatusByOrderSn", orderEntity);
	}


	/**
	 * 批量获取被邀请用户，订单及状态，填充其被邀请状态
	 *
	 * @author loushuai
	 * @created 2017年12月4日 下午2:32:56
	 *
	 * @param request
	 * @return
	 */
	public List<BeInviterStatusInfoVo> getBeInviterStatusInfo(BeInviterStatusInfoRequest request) {
		return mybatisDaoContext.findAll(SQLID+"getBeInviterStatusInfo", BeInviterStatusInfoVo.class, request);
	}


	/**
	 * 查询该用户的创建时间最早的订单的
	 *
	 * @author loushuai
	 * @created 2017年12月19日 下午8:22:43
	 *
	 * @param uid
	 * @return
	 */
	public BeInviterStatusInfoVo getEarliestOrderTime(String uid) {
		return mybatisDaoContext.findOne(SQLID+"getEarliestOrderTime", BeInviterStatusInfoVo.class, uid);
	}

}
