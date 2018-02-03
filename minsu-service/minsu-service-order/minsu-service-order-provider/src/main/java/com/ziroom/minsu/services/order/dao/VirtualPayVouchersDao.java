package com.ziroom.minsu.services.order.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.order.entity.OrderConfigVo;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.order.utils.FinanceMoneyUtil;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.order.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.FinancePayVouchersDetailEntity;
import com.ziroom.minsu.entity.order.FinancePayVouchersEntity;
import com.ziroom.minsu.services.order.entity.FinancePayAndDetailVo;
import com.ziroom.minsu.services.order.entity.FinancePayVouchersDetailVo;
import com.ziroom.minsu.services.order.utils.OrderSnUtil;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

/**
 * <p>付款单相关操作</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年5月3日
 * @since 1.0
 * @version 1.0
 */
@Repository("order.virtualPayVouchersDao")
public class VirtualPayVouchersDao {
	
	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(VirtualPayVouchersDao.class);
	
	
	@Resource(name = "order.financePayVouchersDao")
    private FinancePayVouchersDao financePayVouchersDao;
    
    @Resource(name = "order.financePayVouchersDetailDao")
    private FinancePayVouchersDetailDao financePayVouchersDetailDao;


    /**
     * 处理用户最后的结算信息
     * 并且为下面的保存订单做准备，这个非常重要千万别忘了
     * @author afi
     * @param orderEntity
     * @param dto
     */
    public int dealFinanceUser(OrderInfoVo orderEntity,DataTransferObject dto){
        int rst =0;
        if(dto.getCode() == DataTransferObject.SUCCESS){
            FinancePayAndDetailVo financeVo = new FinancePayAndDetailVo();
            /**
             * 给用户的
             * money_1 = 支付的 + 优惠券 + 活动金额（满减） - (房租 + 违约金 + 佣金 + 清洁费 - 折扣的)
             * 剩余现金金额 = money_1 & 实际支付金额   去最小值
             * lastMoney = 剩余现金金额 - 额外消费
             *
             * 最终lastMoney才是给用户的钱，因为之前优惠券已经虚拟消费掉了，额外消费只能是现金金额消费
             */
            int normalCost = orderEntity.getPenaltyMoney() + orderEntity.getRentalMoney() + orderEntity.getRealUserMoney() + orderEntity.getCleanMoney() - orderEntity.getDiscountMoney();
            int cost = orderEntity.getOtherMoney() + normalCost;

            //实际支付金额
            Integer payMoney = orderEntity.getPayMoney();
            //int last = payMoney  + orderEntity.getCouponMoney() - cost;
            //在取消订单的时候可能已经重新计算了 优惠券和活动的实际使用金额
            int last = payMoney + orderEntity.getCouponMoney() + orderEntity.getActMoney() - normalCost;

            LogUtil.info(LOGGER,"【用户结算】 支付金额{}，优惠券{}，活动金额{}，额外消费:{},违约金:{}房租:{}佣金:{}清洁费:{}折扣:{}",
                    payMoney,orderEntity.getCouponMoneyAll(),orderEntity.getActMoneyAll(),orderEntity.getOtherMoney() ,orderEntity.getPenaltyMoney(),orderEntity.getRentalMoney(),orderEntity.getRealUserMoney(),orderEntity.getCleanMoney(),orderEntity.getDiscountMoney());
            if(last< 0){
                LogUtil.error(LOGGER, "【用户结算】orderSN：{} last is null", orderEntity.getOrderSn());
                LogUtil.error(LOGGER, "【用户结算】【金额异常】 orderSn：{} 支付金额{}，优惠券{}，额外消费:{},违约金:{}房租:{}佣金:{} 清洁费:{}折扣:{}",
                        orderEntity.getOrderSn(),
                        payMoney, orderEntity.getCouponMoney(), orderEntity.getOtherMoney(), orderEntity.getPenaltyMoney(), orderEntity.getRentalMoney(), orderEntity.getRealUserMoney(),orderEntity.getCleanMoney(),orderEntity.getDiscountMoney());
                throw new BusinessException("异常的订单金额");
            }
            LogUtil.info(LOGGER,"【用户结算】 应该退款用户金额{}，支付金额{}",last,payMoney);
            /*if(last > payMoney){
                //如果退回的金额大约已经支付金额，只返回用户的支付金额
                last = payMoney;
            }*/
            // 如果退回的金额大约已经支付金额，只返回用户的支付金额
            last = ValueUtil.getMin(last,payMoney);
            if (orderEntity.getOtherMoney() > 0){
                last = last - orderEntity.getOtherMoney();
            }

            if (last < 0){
                LogUtil.error(LOGGER,"剩余金额有问题，额外消费的钱不够扣了,orderInfo={}", JsonEntityTransform.Object2Json(orderEntity));
                last = 0;
            }
            LogUtil.info(LOGGER,"【用户结算】 最后给用户退款金额{}",last);

           //设置订单的退款金额[这个非常重要用于外面的订单的相关的结算信息] 对，就是这里
            orderEntity.setRefundMoney(last);
            orderEntity.setRealMoney(cost);
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
                financeVo.setPaySourceType(PaySourceTypeEnum.USER_SETTLEMENT.getCode());
                financeVo.setReceiveUid(orderEntity.getUserUid());
                financeVo.setReceiveType(UserTypeEnum.TENANT.getUserType());
                financeVo.setPayUid(orderEntity.getLandlordUid());
                financeVo.setPayType(UserTypeEnum.LANDLORD.getUserType());
                financeVo.setTotalFee(last);
                financeVo.setGenerateFeeTime(new Date());
                financeVo.setRunTime(new Date());
                financeVo.setAuditStatus(AuditStatusEnum.COMPLETE.getCode());
                financeVo.setPaymentType(OrderPaymentTypeEnum.YLFH.getCode());
                this.saveFinancePayVouchers(financeVo);
                rst = last;
            }
        }
        return rst;
    }


    /**
     * 处理房东最后的结算信息
     * @author afi
     * @param orderEntity
     * @param hasRentalMoney 已经打款的金额
     * @param penaltyMoneyComm 违约金的佣金
     * @param dto
     */
    public int dealFinanceLand(OrderConfigVo config,OrderInfoVo orderEntity,int hasRentalMoney,int penaltyMoneyComm,DataTransferObject dto){
        if(dto.getCode() == DataTransferObject.SUCCESS){
            FinancePayAndDetailVo financeVo = new FinancePayAndDetailVo();

            LogUtil.info(LOGGER,"【房东结算】开始给房东结算。。。。。");
            /**
             * 给房东的
             * 违约金 + 房租+ 赔付款
             * [千万不要把佣金混进来，佣金已经前置计算完成]
             */
            List<FinancePayVouchersDetailVo> detailVoList = new ArrayList<>();
            //累计金额
            int total = 0;

            //获取剩余房租的佣金
            int rentAllComm = FinanceMoneyUtil.transMoney2Comm(config, orderEntity.getRentalMoney()-orderEntity.getDiscountMoney(), UserTypeEnum.LANDLORD);
            LogUtil.info(LOGGER,"【房东结算】 真实的房租佣金：{}",rentAllComm);
            //1.房租
            int rentlast = orderEntity.getRentalMoney() - orderEntity.getDiscountMoney() - rentAllComm - hasRentalMoney;
            LogUtil.info(LOGGER,"【房东结算】真实的房租：{},真实房租的佣金{}，已经打的房租{}",orderEntity.getRentalMoney()-orderEntity.getDiscountMoney(),rentAllComm,hasRentalMoney);
            LogUtil.info(LOGGER,"【房东结算】需要再次给房东打的{}",rentlast);
            if (rentlast == -1 || rentlast == 1){
            	rentlast = 0;
                LogUtil.info(LOGGER,"【房东结算】当前金额正好差一分钱，直接兼容 ：{}",rentlast);
            }
            if(rentlast > 0){
                FinancePayVouchersDetailVo detailRent = new FinancePayVouchersDetailVo();
                detailRent.setFeeItemCode(FeeItemCodeEnum.RENT.getCode());
                detailRent.setItemMoney(rentlast);
                total += rentlast;
                detailVoList.add(detailRent);
            }

            //2.违约金
            int penaltyMoneyPlan = orderEntity.getPenaltyMoney();
            int penaltyMoney = penaltyMoneyPlan -penaltyMoneyComm;

            if(penaltyMoney > 0){
                FinancePayVouchersDetailVo detailPenalty = new FinancePayVouchersDetailVo();
                detailPenalty.setFeeItemCode(FeeItemCodeEnum.CONTRARY.getCode());
                detailPenalty.setItemMoney(penaltyMoney);
                total += penaltyMoney;
                detailVoList.add(detailPenalty);
            }

            LogUtil.info(LOGGER,"【房东结算】违约金：{}，违约金的佣金：{}，实际打给房东的违约金{}",penaltyMoneyPlan,penaltyMoneyComm,penaltyMoney);

            //3.赔付款
            int otherMoney = orderEntity.getOtherMoney();
            if(otherMoney > 0){
                FinancePayVouchersDetailVo detailOther = new FinancePayVouchersDetailVo();
                detailOther.setFeeItemCode(FeeItemCodeEnum.USER_ORTHER_MONEY.getCode());
                detailOther.setItemMoney(otherMoney);
                total += otherMoney;
                detailVoList.add(detailOther);
            }
            LogUtil.info(LOGGER,"【房东结算】赔付款：{}，赔付款是不需要收取佣金的",otherMoney);

            LogUtil.info(LOGGER,"【房东结算】：累计再给房东打钱：{}",total);

            //如果累计金额大于0
            if(total > 0){
                financeVo.setFinancePayVouchersDetailList(detailVoList);
                financeVo.setOrderSn(orderEntity.getOrderSn());
                financeVo.setCityCode(orderEntity.getCityCode());
                financeVo.setPaySourceType(PaySourceTypeEnum.USER_SETTLEMENT.getCode());
                financeVo.setReceiveUid(orderEntity.getLandlordUid());
                financeVo.setReceiveType(UserTypeEnum.LANDLORD.getUserType());
                financeVo.setPayUid(orderEntity.getLandlordUid());
                financeVo.setPayType(UserTypeEnum.LANDLORD.getUserType());
                financeVo.setTotalFee(total);
                financeVo.setGenerateFeeTime(new Date());
                financeVo.setRunTime(new Date());
                financeVo.setAuditStatus(AuditStatusEnum.COMPLETE.getCode());
                financeVo.setPaymentType(null);
                this.saveFinancePayVouchers(financeVo);
            }
            return total;
        }else {
            return 0;
        }
    }
    
    /**
     * 生成付款单
     * @author lishaochuan
     * @create 2016年5月3日
     * @param financePayAndDetailVo
     */
	public void saveFinancePayVouchers(FinancePayAndDetailVo financePayAndDetailVo) {
		if (Check.NuNObj(financePayAndDetailVo)) {
			LogUtil.error(LOGGER, "生成付款单参数错误，financePayAndDetailVo:{}", financePayAndDetailVo);
			throw new BusinessException("financePayAndDetailVo is null on saveFinancePayVouchers");
		}
		FinancePayVouchersEntity payVouchersEntity = financePayAndDetailVo.getFinancePayVouchersEntity();
		List<FinancePayVouchersDetailVo> payVouchersDetailList = financePayAndDetailVo.getFinancePayVouchersDetailList();
		
		String pvSn = payVouchersEntity.getPvSn();
		if(Check.NuNStr(payVouchersEntity.getPvSn())){
			pvSn = OrderSnUtil.getPvSn();;
			payVouchersEntity.setPvSn(pvSn);
		}
		if(Check.NuNObj(payVouchersEntity.getPaymentStatus())){
			payVouchersEntity.setPaymentStatus(PaymentStatusEnum.UN_PAY.getCode());
		}
		//默认为空
		payVouchersEntity.setPaymentType(null);
		payVouchersEntity.setIsSend(YesOrNoEnum.NO.getCode());
		
		financePayVouchersDao.insertPayVouchers(payVouchersEntity);
		for (FinancePayVouchersDetailVo financePayVouchersDetailVo : payVouchersDetailList) {
			FinancePayVouchersDetailEntity payVouchersDetailEntity = financePayVouchersDetailVo.getFinancePayVouchersDetailEntity();
			payVouchersDetailEntity.setPvSn(pvSn);
			financePayVouchersDetailDao.insertPayVouchersDetail(payVouchersDetailEntity);
		}
	}

}
