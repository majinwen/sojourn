/**
 * @FileName: OrderLoadlordServiceImpl.java
 * @Package com.ziroom.minsu.services.order.service
 * 
 * @author yd
 * @created 2016年4月3日 下午1:19:48
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.*;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.house.dto.SmartLockDto;
import com.ziroom.minsu.services.order.constant.OrderMessageConst;
import com.ziroom.minsu.services.order.dao.*;
import com.ziroom.minsu.services.order.dto.*;
import com.ziroom.minsu.services.order.entity.OrderConfigVo;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.order.utils.FinanceMoneyUtil;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.evaluate.OrderEvalTypeEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.order.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>房东订单管理实现</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @date 2016-04-03 13:20
 * @version 1.0
 */
@Service("order.orderLoadlordServiceImpl")
public class OrderLoadlordServiceImpl {

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderLoadlordServiceImpl.class);

	@Resource(name = "order.orderDao")
	private OrderDao orderDao;

	@Resource(name = "order.orderStaticsDao")
	private OrderStaticsDao orderStaticsDao;


    @Resource(name = "order.orderBaseDao")
    private OrderBaseDao orderBaseDao;

	@Resource(name = "order.paramDao")
	private OrderParamDao paramDao;
	
	@Resource(name = "order.orderMoneyDao")
	private OrderMoneyDao orderMoneyDao;
	
	@Resource(name = "order.orderLogDao")
	private  OrderLogDao orderLogDao;

    @Resource(name = "order.virtualOrderBaseDao")
    private VirtualOrderBaseDao virtualOrderBaseDao;

    @Resource(name = "order.messageSource")
    private MessageSource messageSource;

    @Resource(name = "order.financeIncomeDao")
    private FinanceIncomeDao financeIncomeDao;

    @Resource(name = "order.financePayVouchersDao")
    private FinancePayVouchersDao financePayVouchersDao;

    @Resource(name = "order.virtualPayVouchersDao")
    private VirtualPayVouchersDao virtualPayVouchersDao;

    @Resource(name = "order.houseLockDao")
    private HouseLockDao houseLockDao;
    
    @Resource(name = "order.activityDao")
    private OrderActivityDao orderActivityDao;

    @Resource(name = "order.houseSnapshotDao")
    private OrderHouseSnapshotDao orderHouseSnapshotDao;

    /**
     * 获取当前房源下的所有未开始的订单
     * @param lockDto
     * @return
     */
    public List<String>  getOrderSnList4Lock(SmartLockDto lockDto){
        List<String> all = new ArrayList<>();
        List<String> listHouse = orderBaseDao.getOrderSnList4LockByHouse(lockDto.getHouseFid());
        List<String> listRooms = orderBaseDao.getOrderSnList4LockByRooms(lockDto.getRoomFidList());
        if (!Check.NuNCollection(listHouse)){
            all.addAll(listHouse);
        }
        if (!Check.NuNCollection(listRooms)){
            all.addAll(listRooms);
        }
        return all;
    }

    /**
     * 安装智能锁
     * @author afi
     * @param orderSn
     * @return
     */
    public  void  installLockByOrderSn(String orderSn){
        orderHouseSnapshotDao.installLockByOrderSn(orderSn);
    }
    /**
     * 房东确认额外消费
     * @author afi
     * @param orderEntity
     * @param otherMoney
     * @param dto
     */
    public void updateOtherMoneyOnly(OrderInfoVo orderEntity,int otherMoney,String paramValue,DataTransferObject dto){
        if(Check.NuNObj(orderEntity)){
            return;
        }
        OrderStatusEnum orderStatus = OrderStatusEnum.getOrderStatusByCode(orderEntity.getOrderStatus());
        //更改订单状态
        OrderEntity order = new OrderEntity();
        order.setOrderSn(orderEntity.getOrderSn());
        order.setOrderStatus(orderStatus.getOtherMoneyStatus(otherMoney).getOrderStatus());
        OrderMoneyEntity moneyEntity = new OrderMoneyEntity();
        moneyEntity.setOrderSn(orderEntity.getOrderSn());
        moneyEntity.setOtherMoney(otherMoney);
        int count = virtualOrderBaseDao.updateOrderInfoAndStatus(orderEntity.getLandlordUid(),orderEntity.getOrderStatus(), order,moneyEntity, null, null);
        if(count ==0){
            //当前状态异常
            LogUtil.info(LOGGER,"【确认额外消费】 修改额外消费异常 orderEntity:{}",JsonEntityTransform.Object2Json(orderEntity));
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_STATUS_ERROR));
            return;
        }
        if(!Check.NuNStr(paramValue)){
			OrderParamEntity orderParamEntity = new OrderParamEntity();
			orderParamEntity.setFid(UUIDGenerator.hexUUID());
			orderParamEntity.setCreateTime(new Date());
			orderParamEntity.setCreateFid(orderEntity.getLandlordUid());
			orderParamEntity.setIsDel(0);
			orderParamEntity.setOrderSn(orderEntity.getOrderSn());
			orderParamEntity.setParCode(OrderParamEnum.OTHER_COST_DES.getCode());
			orderParamEntity.setParValue(paramValue);
			this.paramDao.insertParamRes(orderParamEntity);
		}
    }

    /**
     * 房东确认额外消费 并结算
     * @author afi
     * @param orderEntity
     * @param dto
     */
    public void updateOtherMoneyAndCheck(OrderConfigVo config,OrderInfoVo orderEntity,int otherMoney,DataTransferObject dto){
        if (dto.getCode() == DataTransferObject.SUCCESS){
            OrderStatusEnum orderStatus = OrderStatusEnum.getOrderStatusByCode(orderEntity.getOrderStatus());
            this.updateConfirmOtherMoneyAndCheck(config, orderEntity, orderStatus.getOtherMoneyStatus(otherMoney).getOrderStatus(), dto, null, orderEntity.getLandlordUid());
        }
    }


    /**
     * 确认额外消费 并结算
     * @author afi
     * @param orderEntity
     * @param toOrderStatus
     * @param dto
     */
    public void updateConfirmOtherMoneyAndCheck(OrderConfigVo config,OrderInfoVo orderEntity,int toOrderStatus,DataTransferObject dto, Integer isTakeLandlordComm,String opUuid){
        if (dto.getCode() == DataTransferObject.SUCCESS){
            if(Check.NuNObj(config)){
                LogUtil.info(LOGGER, "【异常数据】 orerSn:{}",orderEntity.getOrderSn());
                throw new BusinessException("config is null");
            }
            LogUtil.info(LOGGER,"1.结算订单 orderSn:{}",orderEntity.getOrderSn());
            LogUtil.info(LOGGER,"1.开始结算。。。。。。。。。");
            //房东的已扣除佣金
            Integer hasLanCommMoney = null;
            //用户的已扣除佣金
            Integer hasUserCommMoney = null;
            //房东的已打房租
            Integer hasRentalMoney = null;
            try {
                List<FinanceIncomeEntity> financeIncomeEntityList = financeIncomeDao.getIncomeListByOrderSn(orderEntity.getOrderSn());
                if(Check.NuNCollection(financeIncomeEntityList)){
                    financeIncomeEntityList = new ArrayList<>();
                }
                List<FinanceIncomeEntity> userList = new ArrayList<>();
                List<FinanceIncomeEntity> landList = new ArrayList<>();
                for(FinanceIncomeEntity income: financeIncomeEntityList){
                    if(income.getIncomeType() == IncomeTypeEnum.USER_RENT_COMMISSION.getCode()){
                        userList.add(income);
                    }else if(income.getIncomeType() == IncomeTypeEnum.LANDLORD_RENT_COMMISSION.getCode()){
                        landList.add(income);
                    }
                }
                //用户已付收入表信息
                hasUserCommMoney = FinanceMoneyUtil.countHasIncome(userList, orderEntity);
                LogUtil.info(LOGGER,"2.【佣金】计算当前有效的用户佣金计划，当前的有效的用户佣金计划金额：{}",ValueUtil.getintValue(hasUserCommMoney));

                //房东已付收入表信息
                hasLanCommMoney = FinanceMoneyUtil.countHasIncome(landList, orderEntity);
                LogUtil.info(LOGGER,"3.【佣金】计算当前有效的房东佣金计划，当前的有效的佣金佣金计划金额：{}",ValueUtil.getintValue(hasLanCommMoney));
            }catch (Exception e){
                throw new BusinessException(e);
            }
            //获取当前有效打款金额
            hasRentalMoney = this.countHasFinace(orderEntity);
            LogUtil.info(LOGGER,"4.【佣金】计算当前的有效的打款计划，当前有效打款计划金额：{}",ValueUtil.getintValue(hasRentalMoney));
            /**
             * 1. 保存房东的佣金
             * 收入：实际的-已打的
             */
            FinanceIncomeEntity incomeLand = FinanceMoneyUtil.getFinanceIncome(config, orderEntity, hasLanCommMoney, IncomeTypeEnum.LANDLORD_RENT_COMMISSION);
            if(!Check.NuNObj(incomeLand) && incomeLand.getTotalFee()> 0){
                financeIncomeDao.insertFinanceIncome(incomeLand);
                LogUtil.info(LOGGER, "5.【佣金】计算房东房租计划之外的佣金：{}", ValueUtil.getintValue(incomeLand.getTotalFee()));
            }else {
                LogUtil.info(LOGGER,"5.【佣金】不需要再给房东打钱");
            }

            /**
             * 2. 保存租客的佣金
             * 收入：实际的-已打的
             */
            FinanceIncomeEntity incomeUser = FinanceMoneyUtil.getFinanceIncome(config, orderEntity, hasUserCommMoney, IncomeTypeEnum.USER_RENT_COMMISSION);
            if(!Check.NuNObj(incomeUser) && incomeUser.getTotalFee()> 0){
                financeIncomeDao.insertFinanceIncome(incomeUser);
                LogUtil.info(LOGGER, "6.【佣金】计算用户房租计划之外的佣金：{}", ValueUtil.getintValue(incomeUser.getTotalFee()));
            }else {
                LogUtil.info(LOGGER,"6.【佣金】不需要再给用户打钱");
            }

            //违约金的佣金
            int penaltyMoneyComm = 0;
			if(Check.NuNObj(isTakeLandlordComm) || (!Check.NuNObj(isTakeLandlordComm) && isTakeLandlordComm == YesOrNoEnum.YES.getCode())){
				/**
				 * 3. 保存租客违约金的佣金
				 * 收入：违约金的佣金
				 */
				FinanceIncomeEntity incomePenalty = FinanceMoneyUtil.getFinanceIncome(config, orderEntity, null, IncomeTypeEnum.USER_PUNISH_COMMISSION);
				if(!Check.NuNObj(incomePenalty) && incomePenalty.getTotalFee()> 0){
					financeIncomeDao.insertFinanceIncome(incomePenalty);
					penaltyMoneyComm = incomePenalty.getTotalFee();
				}
			}

            LogUtil.info(LOGGER,"7.【佣金】 违约金的佣金：{}",penaltyMoneyComm);

            /**
             * 4. 处理房东的打款信息：补款
             */
            int hasLandAgain = virtualPayVouchersDao.dealFinanceLand(config, orderEntity, hasRentalMoney, penaltyMoneyComm, dto);

            LogUtil.info(LOGGER,"8.【房东结算】 结算再次给房东打钱金额：{}",hasLandAgain);
            /**
             * 5. 处理用户的打款信息
             */
            int userLast = virtualPayVouchersDao.dealFinanceUser(orderEntity, dto);
            LogUtil.info(LOGGER, "9.【用户结算】 需要退款佣金金额：{}", userLast);

            OrderEntity order = new OrderEntity();
            order.setOrderSn(orderEntity.getOrderSn());
            order.setOrderStatus(toOrderStatus);
			/*if(OrderStatusEnum.FINISH_NEGOTIATE.getOrderStatus() != toOrderStatus){
            	order.setEvaStatus(OrderEvaStatusEnum.WAITINT_EVA.getCode());
			}*/
            if(hasLandAgain + userLast > 0){
                order.setAccountsStatus(OrderAccountsStatusEnum.ING.getAccountsStatus());
            }else {
                order.setAccountsStatus(OrderAccountsStatusEnum.FINISH.getAccountsStatus());
            }
            OrderMoneyEntity moneyEntity = new OrderMoneyEntity();
            moneyEntity.setOrderSn(orderEntity.getOrderSn());
            moneyEntity.setRefundMoney(orderEntity.getRefundMoney());
            moneyEntity.setRealMoney(orderEntity.getRealMoney());
            int count = virtualOrderBaseDao.updateOrderInfoAndStatus(opUuid,orderEntity.getOrderStatus(), order,moneyEntity, null, null);
            if(count ==0){
                LogUtil.error(LOGGER, "9.【用户结算】 更新订单状态失败orderSn：{},order:{}", order.getOrderSn(), JsonEntityTransform.Object2Json(orderEntity));
                //当前状态异常
                throw new BusinessException("0 row is updated updateOrderInfoAndStatus");
            }
        }
    }


    /**
     * 统计处理打款计划表
     * @author afi
     * @param orderInfoVo
     */
    private int countHasFinace(OrderInfoVo orderInfoVo){
    	List<FinancePayVouchersEntity> financeList = financePayVouchersDao.findEffectiveByOrderSn(orderInfoVo.getOrderSn());
        return  FinanceMoneyUtil.countHasFinace(orderInfoVo,financeList);
    }

	/**
	 * @decription 按条件分页查询房东订单
	 * @date 2016-04-03  13:07
	 * @verion 1.0
	 */
	public PagingResult<OrderInfoVo> queryLoadlordOrderByCondition(
			OrderRequest orderRequest) {
		return orderDao.getOrderInfoListByCondiction(orderRequest);
	}


    /**
     *
     * 房东根据订单编号更新订单状态信息  ：此时的订单状态只能是待确认，否则不让更新
     * 说明：房东操作
     * 1.接受订单   更新到 20：待入住      —— 更新状态  更新时间  保存操作日志
     * 2.拒绝订单 更新到 31：房东已拒绝     —— 更新状态  更新时间  更新参数表 记录拒绝原因  保存操作日志
     * 3.房东输入额外消费金额  订单状态更新到 60：待用户确认额外消费         —— 更新状态  更新时间  更新参数表记录额外消费明细  保存操作日志
     *
     * @author yd
     * @created 2016年4月3日 下午4:54:38
     *
     * @param orderSn
     * @param orderStatus  需要更新到的订单状态
     * @param paramValue 订单操作后相关参数设置
     * @param landlordUid 房东
     * @param otherMoney 其他费用
     * @return
     */
    private boolean updateOrderStatusByLoadlord(String orderSn, int orderStatus,
                                                String paramValue,Integer otherMoney,String landlordUid) {
        return updateOrderStatusByLoadlord(orderSn,  orderStatus, null,
                 paramValue, otherMoney, landlordUid);
    }

	/**
	 * 
	 * 房东根据订单编号更新订单状态信息  ：此时的订单状态只能是待确认，否则不让更新
	 * 说明：房东操作
	 * 1.接受订单   更新到 20：待入住      —— 更新状态  更新时间  保存操作日志
	 * 2.拒绝订单 更新到 31：房东已拒绝     —— 更新状态  更新时间  更新参数表 记录拒绝原因  保存操作日志
	 * 3.房东输入额外消费金额  订单状态更新到 60：待用户确认额外消费         —— 更新状态  更新时间  更新参数表记录额外消费明细  保存操作日志
	 *
	 * @author yd
	 * @created 2016年4月3日 下午4:54:38
	 *
	 * @param orderSn
	 * @param orderStatus  需要更新到的订单状态  
	 * @param paramValue 订单操作后相关参数设置
	 * @param landlordUid 房东
	 * @param otherMoney 其他费用
	 * @return
	 */
	private boolean updateOrderStatusByLoadlord(String orderSn, int orderStatus,Integer payStatus,
			String paramValue,Integer otherMoney,String landlordUid) {

		//1.校验当前订单是否存在
		if(orderBaseDao.checkOrderByLoadlord(orderSn,landlordUid) == false){
			LOGGER.debug("无权操作该订单orderSn：{}，landlordUid：{}",orderSn,landlordUid);
			return false; 
		}

        OrderInfoVo orderInfoVo = this.orderDao.getOrderInfoByOrderSn(orderSn);
		
		OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderSn(orderInfoVo.getOrderSn());
		int oldStatus = orderInfoVo.getOrderStatus();
		orderEntity.setLastModifyDate(new Date());
		orderEntity.setOrderStatus(orderStatus);
        orderEntity.setPayStatus(payStatus);
		orderEntity.setOldStatus(oldStatus);
		if (orderInfoVo.getNeedPay() == 0){
            //如果当前的订单金额为0 直接将原来的订单状态变更为已经支付
            orderEntity.setPayStatus(OrderPayStatusEnum.HAS_PAY.getPayStatus());
        }
		if (orderStatus == OrderStatusEnum.WAITING_EXT.getOrderStatus() || orderStatus == OrderStatusEnum.REFUSED.getOrderStatus()
				|| orderStatus == OrderStatusEnum.REFUSED_OVERTIME.getOrderStatus()){
			if(orderStatus == OrderStatusEnum.WAITING_EXT.getOrderStatus()){
                //这个需要处理
			  OrderMoneyEntity orderMoneyEntity  = 	this.orderMoneyDao.getOrderMoneyByOrderSn(orderSn);
			  LogUtil.info(LOGGER, "根据订单编号orderSn={},查询订单金额实体orderMoneyEntity={}",orderSn, orderMoneyEntity);
			  if(Check.NuNObj(orderMoneyEntity)){
				  throw  new BusinessException("根据订单编号orderSn={"+orderSn+"},查询订单金额实体orderMoneyEntity,金额不存在");
			  }
			  orderMoneyEntity.setOtherMoney(otherMoney);
               orderMoneyEntity.setRefundMoney(orderInfoVo.getRefundMoney() - otherMoney);
			  this.orderMoneyDao.updateOrderMoney(orderMoneyEntity);
            }
			if(paramValue!=null){
				OrderParamEntity orderParamEntity = new OrderParamEntity();
				orderParamEntity.setFid(UUIDGenerator.hexUUID());
				orderParamEntity.setCreateTime(new Date());
				orderParamEntity.setCreateFid(landlordUid);
				orderParamEntity.setIsDel(0);
				orderParamEntity.setOrderSn(orderSn);
				orderParamEntity.setParCode(OrderParamEnum.REFUSE_REASON.getCode());
				if(orderStatus == OrderStatusEnum.WAITING_EXT.getOrderStatus()){
					orderParamEntity.setParCode(OrderParamEnum.OTHER_COST_DES.getCode());
				}
				orderParamEntity.setParValue(paramValue);
                //插入参数表
				this.paramDao.insertParamRes(orderParamEntity);
			}
		}
		//更新订单
		int index =  orderBaseDao.updateOrderBaseByOrderSn(orderEntity);
		//保存操作日志
		OrderLogEntity log = new OrderLogEntity();
		log.setCreateDate(new Date());
		log.setCreateId(landlordUid);
		log.setFid(UUIDGenerator.hexUUID());
		log.setFromStatus(oldStatus);
		log.setIsDel(IsDelEnum.NOT_DEL.getCode());
		log.setOrderSn(orderEntity.getOrderSn());
		log.setToStatus(orderEntity.getOrderStatus());
		log.setRemark(paramValue);
		this.orderLogDao.insertOrderLog(log);


		//当前需要支付的金额为0 并且为非实时订单
		if (orderInfoVo.getNeedPay() == 0 && orderInfoVo.getOrderStatus() == OrderStatusEnum.WAITING_CONFIRM.getOrderStatus() && orderStatus == OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus()){
			List<Date> dateList = DateSplitUtil.dateSplit(orderInfoVo.getStartTime(), orderInfoVo.getEndTime());
			for(int i=0;i<dateList.size();i++){
				HouseLockEntity lockEntity = new HouseLockEntity();
				lockEntity.setOrderSn(orderInfoVo.getOrderSn());
				lockEntity.setCreateTime(new Date());
				lockEntity.setRentWay(orderInfoVo.getRentWay());
				lockEntity.setHouseFid(orderInfoVo.getHouseFid());
				if(orderInfoVo.getRentWay() == RentWayEnum.ROOM.getCode()){
					lockEntity.setRoomFid(orderInfoVo.getRoomFid());
				}
				lockEntity.setLockTime(dateList.get(i));
				lockEntity.setLockType(LockTypeEnum.ORDER.getCode());
				lockEntity.setPayStatus(YesOrNoEnum.YES.getCode());
				houseLockDao.insertHouseLock(lockEntity);
			}
		}

		if(index>0){
			LOGGER.info("房东操作成功");
			return true;
		}
		return false;
	}
	
	
	/**
	 * 拒绝订单
	 * 2.拒绝订单 更新到 31：房东已拒绝     —— 更新状态  更新时间  更新参数表 记录拒绝原因  保存操作日志
	 * @return
	 */
	public boolean refuseOrderByLoadlord(LoadlordRequest loadlordRequest) {
         boolean result = false;
		//1.校验当前订单是否存在
		if(orderBaseDao.checkOrderByLoadlord(loadlordRequest.getOrderSn(),loadlordRequest.getLandlordUid()) == false){
			LogUtil.info(LOGGER,"无权操作该订单orderSn：{}，landlordUid：{}",loadlordRequest.getOrderSn(),loadlordRequest.getLandlordUid());
			return result; 
		}

        OrderInfoVo orderInfoVo = this.orderDao.getOrderInfoByOrderSn(loadlordRequest.getOrderSn());
		
        if(Check.NuNObj(orderInfoVo)){
        	LogUtil.info(LOGGER,"非法的订单编号orderSn：{}",loadlordRequest.getOrderSn());
        	return result; 
        }
        
		OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderSn(orderInfoVo.getOrderSn());
		int oldStatus = orderInfoVo.getOrderStatus();
		orderEntity.setLastModifyDate(new Date());
		orderEntity.setOrderStatus(loadlordRequest.getOrderStatus());
        orderEntity.setOldStatus(oldStatus);
        //更新订单
      	int index =  orderBaseDao.updateOrderBaseByOrderSn(orderEntity);
      	
      	if(index >0 ){
      		result = true;
      		//填充参数表
			OrderParamEntity orderParamEntity = new OrderParamEntity();
			orderParamEntity.setFid(UUIDGenerator.hexUUID());
			orderParamEntity.setCreateTime(new Date());
			orderParamEntity.setCreateFid(loadlordRequest.getLandlordUid());
			orderParamEntity.setIsDel(0);
			orderParamEntity.setOrderSn(loadlordRequest.getOrderSn());
			orderParamEntity.setParCode(OrderParamEnum.REFUSE_REASON.getCode());
			orderParamEntity.setParValueCode(loadlordRequest.getRefuseCode());
			orderParamEntity.setParValue(loadlordRequest.getRefuseReason());
			//插入参数表
			this.paramDao.insertParamRes(orderParamEntity);
		
    		//保存操作日志
    		OrderLogEntity log = new OrderLogEntity();
    		log.setCreateDate(new Date());
    		log.setCreateId(loadlordRequest.getLandlordUid());
    		log.setFid(UUIDGenerator.hexUUID());
    		log.setFromStatus(oldStatus);
    		log.setIsDel(IsDelEnum.NOT_DEL.getCode());
    		log.setOrderSn(orderEntity.getOrderSn());
    		log.setToStatus(orderEntity.getOrderStatus());
    		log.setRemark(loadlordRequest.getRefuseReason());
    		this.orderLogDao.insertOrderLog(log);
    		
    	}
      	return result;
	}
	
	
	
	/**
	 * 
	 * 1.接受订单   更新到 20：待入住      —— 更新状态  更新时间
	 *
	 * @author yd
	 * @created 2016年4月3日 下午8:55:44
	 *
	 * @param orderSn
	 * @param orderStatus
	 * @param landlordUid
	 * @return
	 */
	public boolean acceptOrder(String orderSn, int orderStatus,Integer payStatus,String landlordUid){
		return this.updateOrderStatusByLoadlord(orderSn, orderStatus, payStatus,null, 0, landlordUid);
	}

	/**
	 * 2.拒绝订单 更新到 31：房东已拒绝     —— 更新状态  更新时间  更新参数表 记录拒绝原因
	 * @param loadlordRequest
	 * @param ouponMoney
	 * @return
	 */
	public boolean refusedOrder(LoadlordRequest loadlordRequest,int ouponMoney){
		boolean rst = false;
		if(Check.NuNObj(loadlordRequest)){
			LogUtil.info(LOGGER, "拒绝失败，参数为空，refusedOrder params:{}", JsonEntityTransform.Object2Json(loadlordRequest));
			return rst;
		}
		if(Check.NuNStr(loadlordRequest.getOrderSn())){
			LogUtil.info(LOGGER, "拒绝失败，订单号为空，refusedOrder orderSn:{}",loadlordRequest.getOrderSn());
			return rst;
		}
		
        //更新订单状态
        if(refuseOrderByLoadlord(loadlordRequest)){
            //处理成功 直接释放房源
            houseLockDao.delLockHouseByOrderSn(loadlordRequest.getOrderSn());
            //恢复优惠券
            if(ouponMoney > 0){
            	orderActivityDao.updateGetStatusByCoupon(loadlordRequest.getOrderSn());
            }
            rst = true;
        }
        return rst;
	}
	
	/**
	 * 
	 * 3.房东输入额外消费金额  订单状态更新到 60：待用户确认额外消费         —— 更新状态  更新时间  更新参数表记录额外消费明细
	 *
	 * @author yd
	 * @created 2016年4月3日 下午8:58:22
	 *
	 * @param orderSn
	 * @param orderStatus
	 * @param paramValue     额外消费明细
	 * @param otherMoney
	 * @param landlordUid
	 * @return
	 */
	public boolean saveOtherMoney(String orderSn, int orderStatus,
			String paramValue,Integer otherMoney,String landlordUid){
		return this.updateOrderStatusByLoadlord(orderSn, orderStatus, paramValue, otherMoney, landlordUid);
	}
	
	/**
	 * 
	 * 房东端查询订单列表
	 * 1=待处理  2=进行中  3=已完成
	 *
	 * @author jixd
	 * @created 2016年5月4日 下午8:37:45
	 *
	 * @param orderRequest
	 * @return
	 */
	public PagingResult<OrderInfoVo> queryLoadlordOrderList(OrderRequest orderRequest) {

		PagingResult<OrderInfoVo> pageingResult = new PagingResult<OrderInfoVo>();
		int type =  orderRequest.getLanOrderType();
		if( type == LandlordOrderTypeEnum.PENDING_NEW.getCode()){
			pageingResult = orderDao.getLanlordOrderWaitingList(orderRequest);
		}else if(type == LandlordOrderTypeEnum.UNDERWAY.getCode()){
			pageingResult = orderDao.getLanlordOrderDoingList(orderRequest);
		}else if(type == LandlordOrderTypeEnum.HAS_ENDED.getCode()){
			pageingResult = orderDao.getLanlordOrderDoneList(orderRequest);
		}else if(type ==LandlordOrderTypeEnum.COMING_TEN_ORDERS.getCode() || type ==LandlordOrderTypeEnum.COMING_All_ORDERS.getCode()){
			pageingResult = orderDao.getLanlordOrderComingList(orderRequest);
		}
		return pageingResult;
	}


	/**
	 * 获取房东评价列表
	 * @author afi
	 * @created 2016年11月3日 下午9:54:09
	 *
	 * @param orderEvalRequest
	 * @return
	 */
	public PagingResult<OrderInfoVo> getLandOrderEavlList(OrderEvalRequest orderEvalRequest){
		PagingResult<OrderInfoVo> pageingResult = new PagingResult<OrderInfoVo>();
		
		Integer type =  orderEvalRequest.getOrderEvalType();
		if( Check.NuNObj(type) || type == OrderEvalTypeEnum.ALL.getCode()){
			pageingResult = orderDao.getLandOrderEavlList(orderEvalRequest);
		}else if(type == OrderEvalTypeEnum.WAITING.getCode()){
			pageingResult = orderDao.getLandOrderEavlWaitingList(orderEvalRequest);
		}else if(type == OrderEvalTypeEnum.HAS.getCode()){
			pageingResult = orderDao.getLandOrderEavlHasList(orderEvalRequest);
		}else if(type == OrderEvalTypeEnum.LAND_EVL_SUCCESS_LIST.getCode()){
			pageingResult = orderDao.getLandUnEvalList(orderEvalRequest);
		}
		
		
		return pageingResult;

	}

	/**
     * 
     * 获取 收益订单列表
     * @author liyingjie
     * @created 2016年6月25日 下午6:47:58
     * @param profitRequest
     * @return
     */
    public PagingResult<OrderInfoVo> queryMonthProfitOrderList(OrderProfitRequest profitRequest){
    	if(Check.NuNObj(profitRequest)){
    		LogUtil.info(LOGGER,"queryMonthRealProfitOrderList 参数为空.");
            throw new BusinessException("queryMonthRealProfitOrderList 参数为空.");
    	}
    	
    	if(Check.NuNStr(profitRequest.getHouseFid())){
    		LogUtil.info(LOGGER,"queryMonthRealProfitOrderList houseFid为空.");
            throw new BusinessException("queryMonthRealProfitOrderList houseFid为空.");
    	}
    	
    	if(profitRequest.getMonth() == 0){
    		LogUtil.info(LOGGER,"queryMonthRealProfitOrderList 参数month为0.");
            throw new BusinessException("queryMonthRealProfitOrderList 参数month为0.");
    	}
       
    	if(profitRequest.getRentWay() == RentWayEnum.ROOM.getCode() && Check.NuNStr(profitRequest.getRoomFid())){
    		LogUtil.info(LOGGER,"queryMonthRealProfitOrderList 合租房间，roomFid为空,roomFid:{}.",profitRequest.getRoomFid());
            throw new BusinessException("queryMonthRealProfitOrderList roomFid为空.");
    	}


		Map<Integer, Date> cycleMonth = DateSplitUtil.getCycleMonth();
		Date monthDate = cycleMonth.get(profitRequest.getMonth());
		profitRequest.setBeginTime(DateSplitUtil.getFirstSecondOfMonth(monthDate));
		profitRequest.setEndTime(DateSplitUtil.getLastSecondOfMonth(monthDate));
		PagingResult<OrderInfoVo> pagingResult = new PagingResult<OrderInfoVo>();
		if(profitRequest.getType() == 1){//获取 一个房源 一个月 已经实际收益 所有的订单列表
			pagingResult = orderDao.getMonthRealProfitOrderList(profitRequest);
		}else if(profitRequest.getType() == 2){//获取 一个房源 一个月 预计收益 所有的订单列表
			pagingResult = orderDao.getMonthPredictProfitOrderList(profitRequest);
		}
	   
	   return pagingResult;
	 }
    
    /**
     * 
     * 获取 收益订单列表
     * @author liyingjie
     * @created 2016年6月25日 下午6:47:58
     * @param profitRequest
     * @return
     */
    public Long countOrderList(OrderProfitRequest profitRequest){
    	long result = 0l;
    	if(Check.NuNObj(profitRequest)){
    		LogUtil.info(LOGGER,"countOrderList 参数为空.");
    		return result;
    	}
    	if(Check.NuNStr(profitRequest.getHouseFid())){
    		LogUtil.info(LOGGER,"countOrderList houseFid为空.");
    		return result;
    	}
    	if(profitRequest.getMonth() == 0){
    		LogUtil.info(LOGGER,"countOrderList 参数month为0.");
    		return result;
    	}
    	if(profitRequest.getRentWay() == RentWayEnum.ROOM.getCode() && Check.NuNStr(profitRequest.getRoomFid())){
    		LogUtil.info(LOGGER,"countOrderList 合租房间，roomFid为空,roomFid:{}.",profitRequest.getRoomFid());
    		return result;
    	}

		Map<Integer, Date> cycleMonth = DateSplitUtil.getCycleMonth();
		Date monthDate = cycleMonth.get(profitRequest.getMonth());
		profitRequest.setBeginTime(DateSplitUtil.getFirstSecondOfMonth(monthDate));
		profitRequest.setEndTime(DateSplitUtil.getLastSecondOfMonth(monthDate));
		result = orderDao.countOrderList(profitRequest);
		return result;
	 }
    
    
    /**
     * 计算房东订单总数量
     * @author liyingjie
     * @created 2016年6月25日 下午6:47:58
     * @param request
     * @return
     */
    public Long staticsCountLanOrderNumImp(OrderStaticsRequest request){
    	Long result = orderDao.staticsCountLanOrderNum(request);
	   return result;
	}
    /**
     * 房东X分钟内响应的订单数量
     * @author liyingjie
     * @created 2016年6月25日 下午6:47:58
     * @param request
     * @return
     */
    public Long staticsCountLanReplyOrderNumImp(OrderStaticsRequest request){
    	Long result = orderDao.staticsCountLanReplyOrderNum(request);
	   return result;
	}
    
    /**
     * 房东拒绝的订单数量
     * @author liyingjie
     * @created 2016年6月25日 下午6:47:58
     * @param request
     * @return
     */
    public Long staticsCountLanRefuseOrderNumImp(OrderStaticsRequest request){
    	Long result = orderDao.staticsCountLanRefuseOrderNum(request);
	   return result;
	}
    
    /**
     * 超时系统拒绝的订单数量
     * @author liyingjie
     * @created 2016年6月25日 下午6:47:58
     * @param request
     * @return
     */
    public Long staticsCountSysRefuseOrderNumImp(OrderStaticsRequest request){
    	Long result = orderDao.staticsCountSysRefuseOrderNum(request);
	   return result;
	}
    
    /**
     * 房东响应时间总和
     * @author liyingjie
     * @created 2016年6月25日 下午6:47:58
     * @param request
     * @return
     */
    public Long staticsCountLanReplyOrderTimeImp(OrderStaticsRequest request){
    	Long result = orderDao.staticsCountLanReplyOrderTime(request);
	   return result;
	}
    
    
	/**
	 * 待房东评价订单总数
	 * @author zl
	 * @param landlordUid
	 * @return
	 */
	public Long countWaitLandlordEvaNum(String landlordUid){
		return orderDao.countWaitLandlordEvaNum(landlordUid);
	}
	
	/**
	 * 房东订单总数
	 * @author zl
	 * @param landlordUid
	 * @return
	 */
	public Long countLanOrderNum(String landlordUid){
		return orderDao.countLanOrderNum(landlordUid);
	}
    
    
	/**
	 * 待房客评价订单总数
	 * @author zl
	 * @param landlordUid
	 * @return
	 */
	public Long countWaitTenantEvaNum(String landlordUid){
		return orderDao.countWaitTenantEvaNum(landlordUid);
	}
	
	/**
	 * 当前房东的可评价的订单总数（房东/房东的房客）
	 * @author zl
	 * @param landlordUid
	 * @return
	 */
	public Long countCanEvaNum(String landlordUid){
		return orderDao.countCanEvaNum(landlordUid);
	}
	
	/**
	 * 房东的房客已评价订单数
	 * @author zl
	 * @param landlordUid
	 * @return
	 */
	public Long countTenantEvaedNum(String landlordUid){
		return orderDao.countTenantEvaedNum(landlordUid);
	}
    
	/**
	 * 房东已评价订单数
	 * @author zl
	 * @param landlordUid
	 * @return
	 */
	public Long countLandlordEvaedNum(String landlordUid){
		return orderDao.countLandlordEvaedNum(landlordUid);
	}
	
	/**
	 * 房东已接受订单数
	 * @author zl
	 * @param landlordUid
	 * @return
	 */
	public Long acceptOrderNum(String landlordUid){
		return orderDao.acceptOrderNum(landlordUid);
	}
	
	/**
	 * 房东拒绝订单数
	 * @author zl
	 * @param landlordUid
	 * @return
	 */
	public Long countLanRefuseOrderNum(String landlordUid){
		return orderDao.countLanRefuseOrderNum(landlordUid);
	}
	
	/**
	 * 系统拒绝的订单数
	 * @author zl
	 * @param landlordUid
	 * @return
	 */
	public Long countSysRefuseOrderNum(String landlordUid){
		return orderDao.countSysRefuseOrderNum(landlordUid);
	}

}
