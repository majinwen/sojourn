package com.ziroom.minsu.services.order.service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.*;
import com.ziroom.minsu.services.basedata.entity.ConfigForceVo;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.order.dao.*;
import com.ziroom.minsu.services.order.dto.OrderRelationRequest;
import com.ziroom.minsu.services.order.entity.FinancePayAndDetailVo;
import com.ziroom.minsu.services.order.entity.FinancePayVouchersDetailVo;
import com.ziroom.minsu.services.order.entity.OrderConfigVo;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.order.utils.FinanceMoneyUtil;
import com.ziroom.minsu.services.order.utils.OrderSnUtil;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.order.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>房东点击强制取消业务处理
 *  业务说明： 发送场景 —— 订单已支付  且在 '待入住 '和 '已入住'状态下，房东才可操作申请强制取消
 *  1.对于房东：点击强制取消——》校验订单状态成功————》修改订单状态为强制取消申请中，并且记下订单日志，以及保存参数表此时订单的修改前的状态（方便后期客服恢复此订单，强制取消可以
 *  多次，但记录最后一次的修改前状态即可恢复，因为恢复永远是恢复上一次操作修改状态的状态值）
 *  2.对于客服：两种操作，1.恢复订单（即：订单恢复到申请强制取消订单中前一刻的状态，即参数表记录时刻的状态） 2.同意房东的强制取消申请
 *     针对第二种情况分析：
 *     房东：
 *       A.房东这边不发生扣钱情况
 *       B.房东这边发生扣钱情况
 *     房客：
 *       A.房客决定退钱，走结算
 *       B.房客同意换房，走结算
 *       
 * </p>
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
@Service("order.commissionerOrderServiceImpl")
public class CommissionerOrderServiceImpl {

	/**
	 * 
	 */
	private static Logger logger = LoggerFactory.getLogger(CommissionerOrderServiceImpl.class);


	@Resource(name = "order.orderRelationDao")
	private OrderRelationDao orderRelationDao;

    @Resource(name = "order.orderDao")
    private OrderDao orderDao;

    @Resource(name = "order.virtualPayVouchersDao")
    private VirtualPayVouchersDao virtualPayVouchersDao;


    @Resource(name = "order.financePunishDao")
    private FinancePunishDao financePunishDao;

    @Resource(name = "order.financeIncomeDao")
    private FinanceIncomeDao financeIncomeDao;


    @Resource(name = "order.virtualOrderBaseDao")
    private VirtualOrderBaseDao virtualOrderBaseDao;

    @Resource(name = "order.financePayVouchersDao")
    private FinancePayVouchersDao financePayVouchersDao;
    private int hasLand;

    /**
	 * 强制取消当前的订单信息 并退款
	 * @author afi
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public void agreeCancelOrdeAndRefund(ConfigForceVo configForceVo,OrderConfigVo config,OrderInfoVo order,DataTransferObject dto,String createId,Map<String,Integer> priceMap){
		if(Check.NuNObj(order)){
			return;
		}
		if(dto.getCode() == DataTransferObject.SUCCESS){
			//直接更新操作
//			this.updateCancelOrderAndRefund(configForceVo,config,createId,order,dto,priceMap);
		}
		//更新新旧订单关联表
		updateOrderRe(order);
	}

//    /**
//     * 客服强制取消订单 并执行全额退款操作
//     * @author afi
//     * @param orderInfoVo
//     * @param dto
//     */
//    @Deprecated
//    private void updateCancelOrderAndRefund(ConfigForceVo configForceVo,OrderConfigVo config,String createId,OrderInfoVo orderInfoVo,DataTransferObject dto,Map<String,Integer> priceMap){
//        if (dto.getCode() == DataTransferObject.SUCCESS){
//
//            int punishMoney = this.getFillPunishMoney(configForceVo,config,OrderCancelTypeEnum.REFUND,createId, orderInfoVo, dto);
//            //强赋值罚款信息
//            orderInfoVo.setPunishMoney(punishMoney);
//            //校验当前的处罚金额
//            if(dto.getCode() != DataTransferObject.SUCCESS){
//                return;
//            }
//            Date now = new Date();
//            //实际房租
//            int realRentalMoney = FinanceMoneyUtil.getRealRentalMoney(config, orderInfoVo, now,priceMap);
//            //强赋值房租信息
//            orderInfoVo.setRentalMoney(realRentalMoney);
//            //获取真实的房东的佣金
//            int lanCommMoney = FinanceMoneyUtil.getRealCommMoneyExt(config,orderInfoVo,null,UserTypeEnum.LANDLORD);
//            //获取真实的用户的佣金 包含房东罚金的扩展金额
//            int userCommMoney = FinanceMoneyUtil.getRealCommMoneyExt(config,orderInfoVo, punishMoney, UserTypeEnum.TENANT);
//            //强赋值房用户的佣金
//            orderInfoVo.setRealUserMoney(userCommMoney);
//            //强赋值房房东的佣金
//            orderInfoVo.setRealLanMoney(lanCommMoney);
//            //房东的已扣除佣金
//            Integer hasLanCommMoney = null;
//            //用户的已扣除佣金
//            Integer hasUserCommMoney = null;
//            //房东的已打房租
//            Integer hasRentalMoney = null;
//            try {
//                List<FinanceIncomeEntity> financeIncomeEntityList = financeIncomeDao.getIncomeListByOrderSn(orderInfoVo.getOrderSn());
//                if(Check.NuNObj(financeIncomeEntityList)){
//                    financeIncomeEntityList = new ArrayList<>();
//                }
//                List<FinanceIncomeEntity> userList = new ArrayList<>();
//                List<FinanceIncomeEntity> landList = new ArrayList<>();
//                for(FinanceIncomeEntity income: financeIncomeEntityList){
//                    if(income.getIncomeType() == IncomeTypeEnum.USER_RENT_COMMISSION.getCode()){
//                        userList.add(income);
//                    }else if(income.getIncomeType() == IncomeTypeEnum.LANDLORD_RENT_COMMISSION.getCode()){
//                        landList.add(income);
//                    }
//                }
//                //用户已付收入表信息
//                hasUserCommMoney = FinanceMoneyUtil.countHasIncome(userList, orderInfoVo);
//                //房东已付收入表信息
//                hasLanCommMoney = FinanceMoneyUtil.countHasIncome(landList, orderInfoVo);
//            }catch (Exception e){
//                throw new BusinessException(e);
//            }
//
//            List<FinancePayVouchersEntity> financeList = financePayVouchersDao.findEffectiveByOrderSn(orderInfoVo.getOrderSn());
//            //获取已发房租
//            hasRentalMoney = FinanceMoneyUtil.countHasFinace(orderInfoVo,financeList);
//
//            /**
//             * 1. 保存房东的佣金
//             * 收入：实际的-已打的
//             */
//            FinanceIncomeEntity incomeLand = FinanceMoneyUtil.getFinanceIncome(config, orderInfoVo, hasLanCommMoney, IncomeTypeEnum.LANDLORD_RENT_COMMISSION);
//            if(!Check.NuNObj(incomeLand) && incomeLand.getTotalFee()>0){
//                financeIncomeDao.insertFinanceIncome(incomeLand);
//            }
//            /**
//             * 2. 保存租客的佣金
//             * 收入：实际的-已打的
//             */
//            FinanceIncomeEntity incomeUser = FinanceMoneyUtil.getFinanceIncome(config,orderInfoVo, hasUserCommMoney, IncomeTypeEnum.USER_RENT_COMMISSION);
//            if(!Check.NuNObj(incomeUser) && incomeUser.getTotalFee()>0){
//                financeIncomeDao.insertFinanceIncome(incomeUser);
//            }
//            //不涉及到违约金的佣金，所以不需要这个
//            int penaltyMoneyComm = 0;
//            //3. 处理房东的打款信息
//            hasLand = virtualPayVouchersDao.dealFinanceLand(config,orderInfoVo,hasRentalMoney,penaltyMoneyComm,dto);
//            //4. 处理用户的打款信息
//            virtualPayVouchersDao.dealFinanceUser(orderInfoVo, dto);
//            //4. 处理罚款单 并打给用户
//            this.dealFinancePunish2User(config,orderInfoVo,dto);
//            OrderEntity order = new OrderEntity();
//            order.setOrderSn(orderInfoVo.getOrderSn());
//            order.setOrderStatus(OrderStatusEnum.CANCLE_FORCE.getOrderStatus());
//            order.setAccountsStatus(OrderAccountsStatusEnum.ING.getAccountsStatus());
//            order.setRealEndTime(now);
//            OrderMoneyEntity moneyEntity = new OrderMoneyEntity();
//            moneyEntity.setOrderSn(orderInfoVo.getOrderSn());
//            moneyEntity.setRefundMoney(orderInfoVo.getRefundMoney());
//            moneyEntity.setRealMoney(orderInfoVo.getRealMoney());
//            moneyEntity.setRealLanMoney(lanCommMoney);
//            moneyEntity.setRealUserMoney(userCommMoney);
//            moneyEntity.setRentalMoney(realRentalMoney);
//            moneyEntity.setPunishMoney(punishMoney);
//            int count = virtualOrderBaseDao.updateOrderInfoAndStatus(createId,orderInfoVo.getOrderStatus(), order,moneyEntity, null, null);
//            if(count ==0){
//                throw new BusinessException("0 row is updatedon updateOrderInfoAndStatus");
//            }
//        }
//    }


    /**
     * 将罚款单给用户
     * @author afi
     * @param orderEntity
     * @param dto
     */
    private void dealFinancePunish2User(OrderConfigVo config,OrderInfoVo orderEntity,DataTransferObject dto){
        if(Check.NuNObj(orderEntity)){
            return;
        }
        if(dto.getCode() == DataTransferObject.SUCCESS){
            int punishMoney = ValueUtil.getintValue(orderEntity.getPunishMoney());
            if(punishMoney <= 0){
                return;
            }
            //获取罚款金额减去违约金的金额
            int last = punishMoney - FinanceMoneyUtil.transMoney2Comm(config, punishMoney, UserTypeEnum.TENANT);
            FinancePayAndDetailVo financeVo = new FinancePayAndDetailVo();
            List<FinancePayVouchersDetailVo> detailVoList = new ArrayList<>();
            //如果累计金额大于0
            if(last > 0){
                FinancePayVouchersDetailVo detail = new FinancePayVouchersDetailVo();
                detail.setFeeItemCode(FeeItemCodeEnum.CHECK.getCode());
                detail.setItemMoney(last);
                detailVoList.add(detail);

                financeVo.setFinancePayVouchersDetailList(detailVoList);
                financeVo.setOrderSn(orderEntity.getOrderSn());
                financeVo.setCityCode(orderEntity.getCityCode());
                financeVo.setPaySourceType(PaySourceTypeEnum.OVER_DRAFT.getCode());
                financeVo.setReceiveUid(orderEntity.getUserUid());
                financeVo.setReceiveType(UserTypeEnum.TENANT.getUserType());
                //ok
                financeVo.setPayType(UserTypeEnum.LANDLORD.getUserType());
                //TODO 需要产品提供这一期先不上
                financeVo.setPayUid("需要郑哥哥提供");
                financeVo.setTotalFee(last);
                financeVo.setGenerateFeeTime(new Date());
                financeVo.setRunTime(new Date());
                financeVo.setAuditStatus(AuditStatusEnum.COMPLETE.getCode());
                financeVo.setPaymentType(OrderPaymentTypeEnum.YHFK.getCode());
                virtualPayVouchersDao.saveFinancePayVouchers(financeVo);
            }
        }
    }

    /**
     * 强制取消订单获取当前订单的房东需要支付的违约金
     * @author afi
     * @param order
     * @return
     * @throws Exception
     */
    public int getFillPunishMoney(ConfigForceVo configForceVo,OrderConfigVo config,OrderCancelTypeEnum orderCancelType,String creatId,OrderInfoVo order,DataTransferObject dto){
        if(Check.NuNObj(configForceVo)){
            throw new BusinessException("ConfigForceVo为空");
        }
        int rst = 0 ;
        if(dto.getCode() == DataTransferObject.SUCCESS){


            int limitDay = ValueUtil.getintValue(configForceVo.getLimitDay());
            int punishDay = ValueUtil.getintValue(configForceVo.getPunishDay());
            int tillDay = ValueUtil.getintValue(configForceVo.getTillDay());
            Date limitTime = DateSplitUtil.jumpDate(order.getStartTime(), -limitDay);
            Date tillTime = DateSplitUtil.jumpDate(new Date(), tillDay);
            if(new Date().before(limitTime)){
                rst = 0;
            }else {
                rst =  ValueUtil.getintValue(configForceVo.getDayPrice()) * punishDay;
            }

            if(rst > 0){
                //保存罚款单
                FinancePunishEntity financePunishEntity =new FinancePunishEntity();
                financePunishEntity.setCreateId(creatId);
                financePunishEntity.setOrderSn(order.getOrderSn());
                financePunishEntity.setCityCode(order.getCityCode());
                financePunishEntity.setLastPayTime(tillTime);
                financePunishEntity.setPunishFee(rst);
                financePunishEntity.setPunishType(UserTypeEnum.LANDLORD.getUserType());
                financePunishEntity.setPunishUid(order.getLandlordUid());
                //TODO 描述需要找产品却定
                financePunishEntity.setPunishDescribe("强制取消扣款");
                financePunishEntity.setPunishSn(OrderSnUtil.getPunishSn());
                financePunishEntity.setPunishStatus(PunishStatusEnum.NO.getCode());
                financePunishDao.saveFinancePunish(financePunishEntity);
            }

            FinanceIncomeEntity incomeEntity = null;
            //生成收入表
            if(orderCancelType.getCode() == OrderCancelTypeEnum.REFUND.getCode()){
                incomeEntity = FinanceMoneyUtil.getFinanceIncome(config,order, null, IncomeTypeEnum.LANDLORD_PUNISH_COMMISSION);
            }if(orderCancelType.getCode() == OrderCancelTypeEnum.CONTINUE.getCode()){
                incomeEntity = FinanceMoneyUtil.getFinanceIncome(config,order,null,IncomeTypeEnum.LANDLORD_PUNISH);
            }else {
                throw new BusinessException("orderCancelType错误");
            }
            if(!Check.NuNObj(incomeEntity) && incomeEntity.getTotalFee() > 0){
                financeIncomeDao.insertFinanceIncome(incomeEntity);
            }

        }
        return rst;
    }

    /**
	 * 
	 * 客服同意 更新新旧订单关联表
	 *
	 * @author yd
	 * @created 2016年4月26日 下午11:28:37
	 *
	 * @param order
	 */
	private void updateOrderRe(OrderInfoVo order){
		OrderRelationRequest orderRelationRequest  =new OrderRelationRequest();
		orderRelationRequest.setOldOrderSn(order.getOrderSn());
		Map<String, Object> reMap = this.orderRelationDao.checkOrderIsRelation(orderRelationRequest);

		boolean flag = (boolean) reMap.get("flag");
		//更新新旧订单关联表
		if(flag == true){
			OrderRelationEntity orderRelationEntity = (OrderRelationEntity) reMap.get("orderRelationEntity");
			if(!Check.NuNObj(orderRelationEntity)){
				orderRelationEntity.setLastModifyDate(new Date());
				orderRelationEntity.setAgreeTime(new Date());
				this.orderRelationDao.updateByCondition(orderRelationEntity);
			}
		}
	}
	/**
	 * 强制取消当前的订单信息 并继续租房子
	 * @author afi
	 * @param order
	 * @return
	 * @throws Exception
	 */
//	public void updateCancelOrderAndContinue(ConfigForceVo configForceVo,OrderConfigVo config,OrderInfoVo order,DataTransferObject dto,String createId){
//		if(Check.NuNObj(order)){
//			return;
//		}
//		if(dto.getCode() == DataTransferObject.SUCCESS){
//			//直接更新操作
////			this.updateCancelOrderAndContinue(configForceVo,config,createId, order,dto);
//		}
//
//	}

//    /**
//     * 客服强制取消订单 并执行继续租房逻辑
//     * @author afi
//     * @param orderInfoVo
//     * @param dto
//     */
//    public void updateCancelOrderAndContinue(ConfigForceVo configForceVo,OrderConfigVo config,OrderInfoVo orderInfoVo,DataTransferObject dto,String createId,Map<String,Integer> priceMap){
//        if (dto.getCode() == DataTransferObject.SUCCESS){
//
//            this.getFillPunishMoney(configForceVo,config,OrderCancelTypeEnum.CONTINUE, createId, orderInfoVo, dto);
//            //校验当前的处罚金额
//            if(dto.getCode() != DataTransferObject.SUCCESS){
//                return;
//            }
//            //实际房租
//            int realRentalMoney = FinanceMoneyUtil.getRealRentalMoney(config,orderInfoVo, new Date(),priceMap);
//            //强赋值房租信息
//            orderInfoVo.setRentalMoney(realRentalMoney);
//            //获取真实的房东的佣金
//            int lanCommMoney = FinanceMoneyUtil.getRealCommMoneyExt(config,orderInfoVo,null,UserTypeEnum.LANDLORD);
//            //获取真实的用户的佣金
//            int userCommMoney = FinanceMoneyUtil.getRealCommMoneyExt(config, orderInfoVo,null, UserTypeEnum.TENANT);
//            //强赋值房用户的佣金
//            orderInfoVo.setRealUserMoney(userCommMoney);
//            //强赋值房房东的佣金
//            orderInfoVo.setRealLanMoney(lanCommMoney);
//            //房东的已扣除佣金
//            Integer hasLanCommMoney = null;
//            //用户的已扣除佣金
//            Integer hasUserCommMoney = null;
//            //房东的已打房租
//            Integer hasRentalMoney = null;
//            try {
//                List<FinanceIncomeEntity> financeIncomeEntityList = financeIncomeDao.getIncomeListByOrderSn(orderInfoVo.getOrderSn());
//                if(Check.NuNCollection(financeIncomeEntityList)){
//                    throw new BusinessException("financeIncomeEntityList为空");
//                }
//                List<FinanceIncomeEntity> userList = new ArrayList<>();
//                List<FinanceIncomeEntity> landList = new ArrayList<>();
//                for(FinanceIncomeEntity income: financeIncomeEntityList){
//                    if(income.getIncomeType() == IncomeTypeEnum.USER_RENT_COMMISSION.getCode()){
//                        userList.add(income);
//                    }else if(income.getIncomeType() == IncomeTypeEnum.LANDLORD_RENT_COMMISSION.getCode()){
//                        landList.add(income);
//                    }
//                }
//                //用户已付收入表信息
//                hasUserCommMoney = FinanceMoneyUtil.countHasIncome(userList, orderInfoVo);
//                //房东已付收入表信息
//                hasLanCommMoney = FinanceMoneyUtil.countHasIncome(landList, orderInfoVo);
//            }catch (Exception e){
//                throw new BusinessException(e);
//            }
//
//            List<FinancePayVouchersEntity> financeList = financePayVouchersDao.findEffectiveByOrderSn(orderInfoVo.getOrderSn());
//            //获取已发房租
//            hasRentalMoney = FinanceMoneyUtil.countHasFinace(orderInfoVo,financeList);
//
//            /**
//             * 1. 保存房东的佣金
//             * 收入：实际的-已打的
//             */
//            FinanceIncomeEntity incomeLand = FinanceMoneyUtil.getFinanceIncome(config, orderInfoVo, hasLanCommMoney, IncomeTypeEnum.LANDLORD_RENT_COMMISSION);
//            if(!Check.NuNObj(incomeLand) && incomeLand.getTotalFee()>0){
//                financeIncomeDao.insertFinanceIncome(incomeLand);
//            }
//            /**
//             * 2. 保存租客的佣金
//             * 收入：实际的-已打的
//             */
//            FinanceIncomeEntity incomeUser = FinanceMoneyUtil.getFinanceIncome(config,orderInfoVo, hasUserCommMoney, IncomeTypeEnum.USER_RENT_COMMISSION);
//            if(!Check.NuNObj(incomeUser) && incomeUser.getTotalFee()>0){
//                financeIncomeDao.insertFinanceIncome(incomeUser);
//            }
//            //不涉及到违约金的佣金，所以不需要这个
//            int penaltyMoneyComm = 0;
//            //3. 处理房东的打款信息
//            virtualPayVouchersDao.dealFinanceLand(config,orderInfoVo, hasRentalMoney,penaltyMoneyComm,dto);
//            //4. 用户的不考虑
//
//            OrderEntity order = new OrderEntity();
//            order.setOrderSn(orderInfoVo.getOrderSn());
//            order.setOrderStatus(OrderStatusEnum.CANCLE_FORCE.getOrderStatus());
//            order.setAccountsStatus(OrderAccountsStatusEnum.ING.getAccountsStatus());
//
//            OrderMoneyEntity moneyEntity = new OrderMoneyEntity();
//            moneyEntity.setOrderSn(orderInfoVo.getOrderSn());
////            moneyEntity.setRefundMoney(refund);
//            //用户的实际消费
//            moneyEntity.setRealMoney(realRentalMoney + userCommMoney);
//            moneyEntity.setRealLanMoney(lanCommMoney);
//            moneyEntity.setRealUserMoney(userCommMoney);
//            moneyEntity.setRentalMoney(realRentalMoney);
//            int count = virtualOrderBaseDao.updateOrderInfoAndStatus(createId,orderInfoVo.getOrderStatus(), order,moneyEntity, null, null);
//            if(count ==0){
//                //当前状态异常
//                throw new BusinessException("操作失败");
//            }
//            //5.更新新旧订单关联表
//            updateOrderRe(orderInfoVo);
//        }
//    }



	/**
	 * 新订单支付成功后 客服关联旧订单，并生成打款单
	 * 说明：1.校验新订单是否支付成功，支付失败的不让插入
	 *     2.校验旧订单是否是强制取消状态  否的话 不让插入 打款单
	 *     
	 *     此校验已经在 proxy层完成
	 *
	 * @author yd
	 * @created 2016年4月25日 下午6:47:27
	 *
	 * @param orderRelationEntity
	 * @return
	 */
	public int saveOrUpdateOrderRelation(OrderRelationEntity orderRelationEntity,String payUid) {

		LogUtil.info(logger, "新订单关联旧订单实体orderRelationEntity={}", orderRelationEntity);

		OrderRelationRequest orderRelationRequest =new  OrderRelationRequest();
		orderRelationRequest.setOldOrderSn(orderRelationEntity.getOldOrderSn());
		Map<String, Object> reMap = checkOrderIsRelation(orderRelationRequest);

		boolean flag = (boolean) reMap.get("flag");
		int index = 0;
		if(flag){
			if(reMap.get("orderRelationEntity") == null){
				index = this.orderRelationDao.insert(orderRelationEntity); 
			}else{
				index = updateOrderRelation(orderRelationEntity, payUid);
			}
		}
		//审核通过 走付款单
		if(index>0){
			savePayVouchersEntity(orderRelationEntity, payUid);
		}

		return index;
	}

	/**
	 * 给房东和房客结算
	 * @author yd
	 * @created 2016年4月23日 下午11:08:33
	 * @param orderRelationEntity
	 * @param payUid
	 */
	private void savePayVouchersEntity(OrderRelationEntity orderRelationEntity,String payUid){

		if(!Check.NuNObj(orderRelationEntity)){
			//审核通过 走付款单
			int checkedStatus = orderRelationEntity.getCheckedStatus().intValue() ;
			if(!Check.NuNObj(orderRelationEntity)&&(checkedStatus == CheckedStatusEnum.PERSONER_CHECKED_OK.getCode() ||checkedStatus == CheckedStatusEnum.SYS_CHECKED_OK.getCode())){
				//付款单
                OrderInfoVo orderOld = orderDao.getOrderInfoByOrderSn(orderRelationEntity.getOldOrderSn());
                OrderInfoVo orderNew = orderDao.getOrderInfoByOrderSn(orderRelationEntity.getNewOrderSn());
                if(Check.NuNObjs(orderOld,orderNew) ){
                    throw new   BusinessException("旧订单号错误，oldOrderSn:"+orderRelationEntity.getOldOrderSn());
                }
                if(orderOld.getOrderStatus() != OrderStatusEnum.CANCLE_FORCE.getOrderStatus()){
                    throw new BusinessException("旧订单号错误");
                }
                //直接处理强制取消并和新老订单结算
                this.dealUserLast4CancelBind(orderOld, orderNew);
			}
		}

	}


    /**
     * 取消订单之后给退换用户押金和多与的佣金 并把新订单的差额打到收入账号
     * 1. 将用户的押金和多余的佣金返回给用户，
     * 2. 把剩余的房租发给用户
     * 3. 把抵扣的款给账号
     * @author afi
     * @param oldOrder
     * @param orderNew
     */
    private void dealUserLast4CancelBind(OrderInfoVo oldOrder,OrderInfoVo orderNew){

        if(Check.NuNObj(oldOrder)){
            throw new BusinessException("旧订单号为空");
        }
        if(oldOrder.getPayStatus() == OrderPayStatusEnum.UN_PAY.getPayStatus()){
            throw new BusinessException("payStatus error");
        }
        if(oldOrder.getPayMoney() + oldOrder.getCouponMoney() <= 0){
            throw new BusinessException("payMoney error");
        }
        //1. 将用户的押金和多余的佣金返回给用户，
        int last = oldOrder.getDepositMoney();
        FinancePayAndDetailVo financeVo = new FinancePayAndDetailVo();
        //设置订单的退款金额
        List<FinancePayVouchersDetailVo> detailVoList = new ArrayList<>();
        //并退回用户的多的佣金
        if(!Check.NuNObjs(oldOrder.getUserCommMoney(),oldOrder.getRealUserMoney())){
            last = last+ oldOrder.getUserCommMoney()-oldOrder.getRealUserMoney();
        }
        //如果累计金额大于0
        if(last > 0){
            FinancePayVouchersDetailVo detail = new FinancePayVouchersDetailVo();
            detail.setFeeItemCode(FeeItemCodeEnum.CHECK.getCode());
            detail.setItemMoney(last);
            detailVoList.add(detail);

        }

        //2. 把剩余的房租发给用户
        //公式：支付的+优惠券-佣金-押金-实际房租
        int lastRental = oldOrder.getPayMoney() + oldOrder.getCouponMoney() -oldOrder.getUserCommMoney() - oldOrder.getDepositMoney() - oldOrder.getRentalMoney();

        //新订单的支付金额
        int userLast = lastRental - orderNew.getRentalMoney();
        if(userLast > 0 ){
            FinancePayVouchersDetailVo detail = new FinancePayVouchersDetailVo();
            detail.setFeeItemCode(FeeItemCodeEnum.CHECK.getCode());
            detail.setItemMoney(userLast);
            detailVoList.add(detail);
            //金额累加
            last += userLast;
        }
        financeVo.setFinancePayVouchersDetailList(detailVoList);
        financeVo.setOrderSn(oldOrder.getOrderSn());
        financeVo.setCityCode(oldOrder.getCityCode());
        financeVo.setPaySourceType(PaySourceTypeEnum.FORCE_SETTLEMENT.getCode());
        financeVo.setReceiveUid(oldOrder.getUserUid());
        financeVo.setReceiveType(UserTypeEnum.TENANT.getUserType());
        //ok
        financeVo.setPayType(UserTypeEnum.LANDLORD.getUserType());
        financeVo.setPayUid(oldOrder.getLandlordUid());
        financeVo.setTotalFee(last);
        financeVo.setGenerateFeeTime(new Date());
        financeVo.setRunTime(new Date());
        financeVo.setAuditStatus(AuditStatusEnum.COMPLETE.getCode());
        financeVo.setPaymentType(OrderPaymentTypeEnum.YHFK.getCode());
        virtualPayVouchersDao.saveFinancePayVouchers(financeVo);

        // 3. 把抵扣的款给账号
        int ourMoney = ValueUtil.getMin(lastRental, orderNew.getRentalMoney());
        if(ourMoney > 0){
            FinancePayAndDetailVo financeVoOur = new FinancePayAndDetailVo();
            //设置订单的退款金额
            List<FinancePayVouchersDetailVo> detailVoListOur = new ArrayList<>();
            FinancePayVouchersDetailVo detail = new FinancePayVouchersDetailVo();
            detail.setFeeItemCode(FeeItemCodeEnum.CHECK.getCode());
            detail.setItemMoney(ourMoney);
            detailVoListOur.add(detail);
            financeVoOur.setFinancePayVouchersDetailList(detailVoListOur);
            financeVoOur.setOrderSn(oldOrder.getOrderSn());
            financeVoOur.setCityCode(oldOrder.getCityCode());
            financeVoOur.setPaySourceType(PaySourceTypeEnum.OLD_NEW_TRANS.getCode());
            financeVoOur.setReceiveUid(oldOrder.getUserUid());
            financeVoOur.setReceiveType(UserTypeEnum.TENANT.getUserType());
            //ok
            financeVo.setPayType(UserTypeEnum.LANDLORD.getUserType());
            financeVoOur.setPayUid(oldOrder.getLandlordUid());
            financeVoOur.setTotalFee(ourMoney);
            financeVoOur.setGenerateFeeTime(new Date());
            financeVoOur.setRunTime(new Date());
            financeVoOur.setAuditStatus(AuditStatusEnum.COMPLETE.getCode());
            financeVo.setPaymentType(OrderPaymentTypeEnum.YHFK.getCode());
            virtualPayVouchersDao.saveFinancePayVouchers(financeVoOur);
        }
    }

	/**
	 * 
	 * 获取来个订单的差额
	 *
	 * @author yd
	 * @created 2016年4月27日 下午1:27:45
	 *
	 * @param newOrderSn
	 * @param orderOld
	 * @return
	 */
	public int getTwoOrderBalance(String newOrderSn, OrderInfoVo orderOld){
		
		OrderInfoVo orderNew = orderDao.getOrderInfoByOrderSn(newOrderSn);
		if(Check.NuNObj(orderOld)){
			throw new BusinessException("旧订单号错误，oldOrderSn:"+orderOld.getOrderSn());
		}
		if(Check.NuNObj(orderNew)){
			throw new BusinessException("新订单号错误，newOrderSn:"+newOrderSn);
		}
		if(orderOld.getOrderStatus() != OrderStatusEnum.CANCLE_FORCE.getOrderStatus()){
			throw new BusinessException("旧订单状态错误");
		}
		
		int twoOrderBalance = this.getMoneyLast(orderOld,orderNew);
		
		if(twoOrderBalance < 0){
			twoOrderBalance = 0;
		}
		
		return twoOrderBalance;
	}
	/**
	 * 获取两个订单的价格差
	 * @param orderOld
	 * @param orderNew
	 * @return
	 */
	private int getMoneyLast(OrderInfoVo orderOld,OrderInfoVo orderNew){
		int last = 0;
		if(Check.NuNObjs(orderOld,orderNew)){
			return last;
		}
		int lodLast = orderOld.getPayMoney() + orderOld.getCouponMoney() - orderOld.getRentalMoney() - orderOld.getPenaltyMoney();
		int newCost = orderNew.getRentalMoney();
		last = lodLast - newCost;
		return last;
	}
	/**
	 * 审核新旧订单关联状态
	 * 说明：1.校验新订单是否支付成功，支付失败的不让插入
	 *     2.校验旧订单是否是强制取消状态  否的话 不让插入 打款单
	 *     
	 *     此校验已经在 proxy层完成
	 *
	 * @author yd
	 * @created 2016年4月25日 下午6:47:27
	 *
	 * @param orderRelationEntity
	 * @return
	 */
	public int updateOrderRelation(OrderRelationEntity orderRelationEntity,String payUid) {

		LogUtil.info(logger, "新订单关联旧订单实体orderRelationEntity={}", orderRelationEntity);
		int index = this.orderRelationDao.updateByCondition(orderRelationEntity);
		//审核通过 走付款单
		if(index>0){
			savePayVouchersEntity(orderRelationEntity, payUid);
		}

		return index;
	}
	/**
	 * 
	 * 去关联订单时候 校验 
	 * 未关联的订单只能是唯一的，处在未校验状态
	 *
	 * @author yd
	 * @created 2016年4月26日 下午2:34:49
	 *
	 * @param orderRelationRequest
	 * @return map map 中 放查询状态  如有实体 则包含实体
	 */
	public Map<String, Object> checkOrderIsRelation(OrderRelationRequest orderRelationRequest){
		Map<String, Object> resultMap = this.orderRelationDao.checkOrderIsRelation(orderRelationRequest);
		return resultMap;

	}
}
