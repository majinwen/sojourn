package com.ziroom.minsu.services.order.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ziroom.minsu.entity.cms.InviteEntity;
import com.ziroom.minsu.entity.order.OrderMoneyEntity;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.order.dao.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.entity.order.OrderLogEntity;
import com.ziroom.minsu.services.order.dto.OrderTaskRequest;
import com.ziroom.minsu.services.order.entity.OrderHouseVo;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;

/**
 * <p>用户的service</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/1.
 * @version 1.0
 * @since 1.0
 */
@Service("order.orderTaskServiceImpl")
public class OrderTaskServiceImpl {


    private static final Logger LOGGER = LoggerFactory.getLogger(OrderTaskServiceImpl.class);


    @Resource(name = "order.virtualOrderBaseDao")
    private VirtualOrderBaseDao virtualOrderBaseDao;


    @Resource(name = "order.orderDao")
    private OrderDao orderDao;

    @Resource(name = "order.orderLogDao")
    private OrderLogDao orderLogDao;

    @Resource(name = "order.orderBaseDao")
    private OrderBaseDao orderBaseDao;

    @Resource(name = "order.orderMoneyDao")
    private OrderMoneyDao orderMoneyDao;


    @Resource(name = "order.activityDao")
    private OrderActivityDao orderActivityDao;

    @Resource(name = "order.houseLockDao")
    private HouseLockDao houseLockDao;


    /**
     * 获取超时取消的订单编号的列表
     * @param taskRequest
     * @return
     */
    public List<String> taskGetToCanceOrderSnList(OrderTaskRequest taskRequest){
        return orderBaseDao.taskGetToCanceOrderSnList(taskRequest);
    }


    /**
     * 获取已退房的列表
     * @param taskRequest
     * @return
     */
    public List<String> taskGetToCheckOutOrderSnList(OrderTaskRequest taskRequest){
        return orderBaseDao.taskGetToCheckOutOrderSnList(taskRequest);
    }

    /**
     * 查询超过入住时间还未入住的订单
     * @param taskRequest
     * @return
     */
    public List<String> taskGetOverCheckInTimeOrderSnList(OrderTaskRequest taskRequest){
        return orderBaseDao.taskGetOverCheckInTimeOrderSnList(taskRequest);
    }


    /**
     * 检查被邀请人是否有已经入住订单
     *
     * @param inviteEntity
     * @return
     * @author lishaochuan
     */
    public Long checkIfInviteCheckInOrder(InviteEntity inviteEntity) {
        return orderBaseDao.checkIfInviteCheckInOrder(inviteEntity);
    }


    /**
     * 根据订单编号 更新订单状态  释放房源
     * @author liyingjie
     * @param orderSn
     * @return
     */
    public int updateOrderAndUnLockHouse(String orderSn){
    	if(Check.NuNStr(orderSn)){
    		return 0;
    	}
        OrderMoneyEntity moneyEntity = orderMoneyDao.getOrderMoneyByOrderSn(orderSn);
        if (Check.NuNObj(moneyEntity)){
            throw new BusinessException("获取订单金额信息失败。orderSn："+orderSn);
        }

        OrderLogEntity log = new OrderLogEntity();
        log.setOrderSn(orderSn);
        log.setToStatus(OrderStatusEnum.CANCLE_TIME.getOrderStatus());
        log.setFromStatus(OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus());
        log.setRemark("超时未支付订单自动取消");
        log.setCreateId("001");
        orderLogDao.insertOrderLog(log);

		int orderRes = orderBaseDao.taskUpdateOrderToCancelStatus(orderSn);
        if (orderRes ==1){
            //当前的订单使用了优惠券 需要取消优惠券信息
            if (moneyEntity.getCouponMoney() > 0){
                orderActivityDao.updateGetStatusByCoupon(orderSn);
            }
        }

		if( orderRes ==1){
            //更新订单成功之后 释放房源信息
            houseLockDao.delLockHouseByOrderSn(orderSn);
		}else{
            LogUtil.error(LOGGER,"【取消订单Job】更新订单失败 orderSn:{}",orderSn);
			throw new BusinessException("【取消订单Job】更新订单失败");
		}
        return orderRes;
    }

    /**
     * 根据订单编号 更新订单状态  为已入住
     * @author liyingjie
     * @param orderList
     * @return
     */
    public void taskBatchUpdateOrder(List<String> orderList){
        if(Check.NuNCollection(orderList)){
            return;
        }
    	for(String orderSn : orderList){
            orderBaseDao.taskUpdateOrderToCheckInStatus(orderSn);
    	}
    }


    /**
     * 超过30min 定时任务批量取消订单
     * @author liyingjie
     * @param orderList
     * @return
     */
    @Deprecated
    public void taskBatchUpdateToCancelOrder(List<String> orderList){
        if(Check.NuNCollection(orderList)){
            return;
        }
    	for(String orderSn : orderList){
            try {
                this.updateOrderAndUnLockHouse(orderSn);
            }catch (Exception e){
                LogUtil.error(LOGGER,"orderSN:{}error:{}",orderSn,e);
            }
    	}
    }

    /**
     * 根据订单编号 更新订单状态  为已入住
     * @author liyingjie
     * @param orderSn
     * @return
     */
    public int taskUpdateOrderToCheckInStatus(String orderSn){
    	if(Check.NuNStr(orderSn)){
    		return 0;
    	}
    	OrderLogEntity log = new OrderLogEntity();
		log.setOrderSn(orderSn);
		log.setToStatus(OrderStatusEnum.CHECKED_IN.getOrderStatus());
		log.setFromStatus(OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus());
		log.setRemark("定时任务到时间自动已入住");
		log.setCreateId("001");
		orderLogDao.insertOrderLog(log);
    	return orderBaseDao.taskUpdateOrderToCheckInStatus(orderSn);
    }





    /**
     * 获取当前超时未支付的订单的数量
     * @author afi
     * @param taskRequest
     * @return
     */
    public Long taskGetOverTimeNum(OrderTaskRequest taskRequest){
        return orderBaseDao.taskGetOverTimeNum(taskRequest);
    }


    /**
     * 获取当前已入住的数量
     * @author afi
     * @param taskRequest
     * @return
     */
    public Long taskGetOverCheckInNum(OrderTaskRequest taskRequest){
        return orderBaseDao.taskGetOverCheckInNum(taskRequest);
    }


    /**
     * 获取当前已入住的数量
     * @author afi
     * @param taskRequest
     * @return
     */
    public Long taskGetOverCheckOutNum(OrderTaskRequest taskRequest){
        return orderBaseDao.taskGetOverCheckOutNum(taskRequest);
    }

    /**
     * 获取相对于前一天已支付待入住订单数量
     * @author jixd
     * @return
     */
    public Long taskGetTomorrowOrderNum(){
    	return orderBaseDao.taskGetTomorrowOrderNum();
    }
    /**
     * 获取相对于前一天已支付待入住订单列表
     * @author jixd on 2016年4月20日
     */
    public List<OrderEntity> taskGetTomorrowOrderList(OrderTaskRequest taskRequest){
    	return orderBaseDao.taskGetTomorrowOrderList(taskRequest);
    }


    /**
     * 定时任务退房退房
     * @author afi
     * @param orderSn
     * @return
     */
    public void taskCheckOutOrder(String orderSn,int oldStatus){
        OrderInfoVo orderInfo = orderDao.getOrderInfoByOrderSn(orderSn);
        if(Check.NuNObj(orderInfo)){
            LogUtil.error(LOGGER,"order notExit orderSn is:{}",orderSn);
            return ;
        }
        //将订单状态变更为已退房
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderSn(orderSn);
        orderEntity.setRealEndTime(new Date());
        orderEntity.setOrderStatus(OrderStatusEnum.CHECKING_OUT.getOrderStatus());
        virtualOrderBaseDao.updateOrderBaseAndStatus("task",oldStatus,orderEntity,null,null);

    }


    /**
     * 查询处于待确认状态一定时间内未操作的订单count
     * @author lishaochuan
     * @create 2016年5月12日下午1:50:09
     * @param limitTime
     * @return
     */
    public Long taskWaitConfirmOrderCount(Date limitTime){
    	List<Integer> orderStatusList = new ArrayList<Integer>();
    	orderStatusList.add(OrderStatusEnum.WAITING_CONFIRM.getOrderStatus());
    	return orderBaseDao.getWaitConformOrderCount(orderStatusList, limitTime);
    }

    /**
     * 查询处于待确认状态一定时间内未操作的订单list
     * @author lishaochuan
     * @create 2016年5月12日下午1:51:40
     * @param limitTime
     * @param limit
     * @return
     */
    public List<OrderEntity> taskWaitConfirmOrderList(Date limitTime, int limit){
    	List<Integer> orderStatusList = new ArrayList<Integer>();
    	orderStatusList.add(OrderStatusEnum.WAITING_CONFIRM.getOrderStatus());
    	return orderBaseDao.getWaitConformOrderList(orderStatusList, limitTime, limit);
    }



    /**
     * 房东查询退房中并且一定时间内未确认操作的订单count
     * @author lishaochuan
     * @create 2016年5月12日上午12:41:02
     * @param limitTime
     * @return
     */
    public Long taskConfirmOtherFeeLandlordCount(Date limitTime){
    	List<Integer> orderStatusList = new ArrayList<Integer>();
    	orderStatusList.add(OrderStatusEnum.CHECKING_OUT.getOrderStatus());
    	orderStatusList.add(OrderStatusEnum.CHECKING_OUT_PRE.getOrderStatus());
    	return orderBaseDao.getNoOperateOrderCount(orderStatusList, limitTime);
    }

    /**
     * 房东查询退房中并且一定时间内未确认操作的订单List
     * @author lishaochuan
     * @create 2016年5月12日上午12:45:28
     * @param limitTime
     * @param limit
     */
    public List<OrderEntity> taskConfirmOtherFeeLandlordList(Date limitTime, int limit){
    	List<Integer> orderStatusList = new ArrayList<Integer>();
    	orderStatusList.add(OrderStatusEnum.CHECKING_OUT.getOrderStatus());
    	orderStatusList.add(OrderStatusEnum.CHECKING_OUT_PRE.getOrderStatus());
    	return orderBaseDao.getNoOperateOrderList(orderStatusList, limitTime, limit);
    }


    /**
     * 房客查询待用户确认额外消费并且一定时间内未确认操作的订单count
     * @author lishaochuan
     * @create 2016年5月12日上午1:05:20
     * @param limitTime
     * @return
     */
    public Long taskConfirmOtherFeeUserCount(Date limitTime){
    	List<Integer> orderStatusList = new ArrayList<Integer>();
    	orderStatusList.add(OrderStatusEnum.WAITING_EXT.getOrderStatus());
    	orderStatusList.add(OrderStatusEnum.WAITING_EXT_PRE.getOrderStatus());
    	return orderBaseDao.getNoOperateOrderCount(orderStatusList, limitTime);
    }

    /**
     * 房客查询待用户确认额外消费并且一定时间内未确认操作的订单List
     * @author lishaochuan
     * @create 2016年5月12日上午1:06:39
     * @param limitTime
     * @param limit
     * @return
     */
    public List<OrderEntity> taskConfirmOtherFeeUserList(Date limitTime, int limit){
    	List<Integer> orderStatusList = new ArrayList<Integer>();
    	orderStatusList.add(OrderStatusEnum.WAITING_EXT.getOrderStatus());
    	orderStatusList.add(OrderStatusEnum.WAITING_EXT_PRE.getOrderStatus());
    	return orderBaseDao.getNoOperateOrderList(orderStatusList, limitTime, limit);
    }



    /**
     * 查询下单后未提醒房东确认的订单count
     * @author lishaochuan
     * @create 2016年7月29日下午6:49:54
     * @param limitDate
     * @return
     */
    public Long getWaitConfimOrderCount(Date limitDate) {
    	return orderBaseDao.getWaitConfimOrderCount(limitDate);
    }

    /**
     * 查询下单后未提醒房东确认的订单list
     * @author lishaochuan
     * @create 2016年7月29日下午6:49:16
     * @param limit
     * @param limitDate
     * @return
     */
    public List<OrderEntity> getWaitConfimOrderList(int limit, Date limitDate) {
    	return orderBaseDao.getWaitConfimOrderList(limit, limitDate);
    }


    /**
     * 查询当天退房的订单
     * @author lisc30
     * @return
     */
    public List<OrderEntity> getCheckOutTodayList(){
        return orderBaseDao.getCheckOutTodayList();
    }


	/**
	 * 获取每天将要入住的订单，给房东发短信
	 *
	 * @author loushuai
	 * @created 2017年7月31日 上午10:05:30
	 *
	 * @param paramMap
	 * @return
	 */
	public PagingResult<OrderHouseVo> getWaitCheckinList(PageRequest pageRequest) {
		return orderBaseDao.getWaitCheckinList(pageRequest);
	}

}

