package com.ziroom.minsu.services.order.service;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.*;
import com.ziroom.minsu.services.common.vo.HouseStatsVo;
import com.ziroom.minsu.services.finance.entity.OrderActivityInfoVo;
import com.ziroom.minsu.services.order.dao.*;
import com.ziroom.minsu.services.order.dto.PayCallBackRequest;
import com.ziroom.minsu.services.order.entity.FinancePayAndDetailVo;
import com.ziroom.minsu.services.order.entity.FinancePayVouchersDetailVo;
import com.ziroom.minsu.services.order.utils.OrderSnUtil;
import com.ziroom.minsu.valenum.common.RstTypeEnum;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.finance.PaymentTypeEnum;
import com.ziroom.minsu.valenum.finance.SyncStatusEnum;
import com.ziroom.minsu.valenum.order.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("order.orderPayServiceImpl")
public class OrderPayServiceImpl {

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderPayServiceImpl.class);

	@Resource(name = "order.payDao")
	private OrderPayDao orderPayDao;

	@Resource(name = "order.orderBaseDao")
	private OrderBaseDao orderBaseDao;

	@Resource(name = "order.financeIncomeDao")
	private FinanceIncomeDao financeIncomeDao;

	@Resource(name = "order.financePunishDao")
	private FinancePunishDao financePunishDao;


	@Resource(name ="order.financePaymentVouchersDao")
	private  FinancePaymentVouchersDao financePaymentVouchersDao;

	@Resource(name = "order.virtualOrderBaseDao")
	private VirtualOrderBaseDao virtualOrderBaseDao;

	@Resource(name = "order.financePayVouchersDao")
	private FinancePayVouchersDao financePayVouchersDao;

	@Resource(name = "order.virtualPayVouchersDao")
	private VirtualPayVouchersDao virtualPayVouchersDao;

	@Resource(name = "order.orderMoneyDao")
	private OrderMoneyDao orderMoneyDao;

	@Resource(name = "order.houseLockDao")
	private HouseLockDao houseLockDao;


	/**
	 * 更新罚金表状态
	 *
	 * @author liyingjie
	 * @created 2016年4月27日 
	 * @param toPayRequest
	 * @return
	 */
	private int updatePunishOrder(PayCallBackRequest toPayRequest){
		int num =  YesOrNoEnum.NO.getCode(); //更新结果
		if(Check.NuNObj(toPayRequest) || Check.NuNStr(toPayRequest.getBizCode())){
			LogUtil.error(LOGGER, "更新罚款单表参数错误：toPayRequest：{}", toPayRequest);
			return num;
		}
		FinancePunishEntity fpe = new FinancePunishEntity();
		fpe.setPunishSn(toPayRequest.getBizCode());
		fpe.setPunishStatus(PunishStatusEnum.YES.getCode());;
		num = financePunishDao.updateByPunishSn(fpe);
		return num;
	}


	/**
	 * 更新收入表
	 *
	 * @author liyingjie
	 * @created 2016年4月27日 
	 * @param toPayRequest
	 * @return
	 */
	private int updateIncome(PayCallBackRequest toPayRequest){
		int num =  YesOrNoEnum.NO.getCode(); //更新结果
		if(Check.NuNObj(toPayRequest) || Check.NuNStr(toPayRequest.getBizCode())){
			LogUtil.error(LOGGER, "更新收入表参数错误,toPayRequest:{}", toPayRequest);
			return num;
		}
		Map<String, Object> param = new HashMap<String,Object>(3);
		param.put("orderSn", toPayRequest.getOrderSn());
		param.put("isSend", IncomeStatusEnum.YES.getCode());
		param.put("incomeStatus", IncomeStatusEnum.YES.getCode());
		num = financeIncomeDao.updateIncomeLandlordPunish(param);
		return num;
	}

	/**
	 * 支付罚金回调接口
	 *
	 * @author liyingjie
	 * @created 2016年4月27日 
	 * @param toPayRequest
	 * @return
	 */
	public boolean punishPayCallBack(PayCallBackRequest toPayRequest) {
		boolean result = true;
		if(Check.NuNObj(toPayRequest) || Check.NuNStr(toPayRequest.getBizCode()) || Check.NuNStr(toPayRequest.getOut_trade_no())){
			LogUtil.error(LOGGER, "支付罚金回调接口参数错误,toPayRequest:{}", toPayRequest);
			result = false;
			return result;
		}
		//校验 该流水号 是否已经生成收款单
		if(checkIsHasPaymentVouchers(toPayRequest.getOut_trade_no())){
			return result;
		}
		int	orderRes = this.updatePunishOrder(toPayRequest);//更新 账单
		int orderPay = this.createOrderPay(toPayRequest);//新增 支付记录
		int incomeRes = this.updateIncome(toPayRequest);//更新收入表
		int paymentRes = this.createPaymentVouchers(toPayRequest,PaymentTypeEnum.punish.getCode());//创建收款单
		if (orderRes == YesOrNoEnum.NO.getCode()
				|| orderPay == YesOrNoEnum.NO.getCode()
				|| paymentRes == YesOrNoEnum.NO.getCode()
				|| incomeRes == YesOrNoEnum.NO.getCode()) {
			LogUtil.error(LOGGER, "支付罚金回调失败. orderRes:{}, orderPay:{}, incomeRes:{}, paymentRes:{}", orderRes, orderPay,incomeRes ,paymentRes);
			throw new BusinessException("支付罚金回调失败");
		}
		return result;
	}

	/**
	 * 支付正常回调接口
	 *
	 * @author liyingjie
	 * @created 2016年4月8日 
	 * @param payCallBackRequest
	 * @param orderActList
	 * @return
	 */
	public RstTypeEnum payNormalCallBack(PayCallBackRequest payCallBackRequest, List<OrderActivityInfoVo> orderActList) {
		RstTypeEnum result = RstTypeEnum.FAIL;
		if(Check.NuNObj(payCallBackRequest)){
			LogUtil.error(LOGGER, "支付正常回调参数错误,toPayRequest:{}", payCallBackRequest);
			return result;
		}
		//幂等操作 如果当前付款单号存在 直接返回成功
		if(checkIsHasPaymentVouchers(payCallBackRequest.getOut_trade_no())){
			result = RstTypeEnum.REPEAT;
			return result;
		}
		boolean dealFlag = true;
		int orderRes = this.updateOrder(payCallBackRequest);//更新订单
		if (orderRes == YesOrNoEnum.NO.getCode()){
			LogUtil.info(LOGGER, "【支付回调】更新的订单条数为0，需要校验是否真的已经付款 orderSn：{}",payCallBackRequest.getOrderSn());
			OrderEntity ordeBase = orderBaseDao.getOrderBaseByOrderSn(payCallBackRequest.getOrderSn());
			LogUtil.info(LOGGER, "【支付回调】重新从主库去获取订单的支付状态 orderSn：{}",payCallBackRequest.getOrderSn());
			if (ordeBase.getPayStatus() == OrderPayStatusEnum.HAS_PAY.getPayStatus()){
				result = RstTypeEnum.REPEAT;
				return result;
			}else {
				dealFlag = false;
			}
		}
		if (dealFlag){
			int orderPay = this.createOrderPay(payCallBackRequest);//创建支付记录
			if (orderPay == YesOrNoEnum.NO.getCode()){
				LogUtil.error(LOGGER, "【支付回调】创建支付记录失败 orderSn：{}",payCallBackRequest.getOrderSn());
				dealFlag = false;
			}
		}
		if (dealFlag){
			int paymentRes = this.createPaymentVouchers(payCallBackRequest, PaymentTypeEnum.order.getCode());//创建收款单
			if (paymentRes == YesOrNoEnum.NO.getCode()){
				LogUtil.error(LOGGER, "【支付回调】创建收款单失败 orderSn：{}",payCallBackRequest.getOrderSn());
				dealFlag = false;
			}
		}
		//调用成功
		if (dealFlag) {
			return payCallBack4Coupon(payCallBackRequest,orderActList);
		}else {
			LogUtil.error(LOGGER, "支付正常回调失败.payCallBackRequest:{},orderActList:{}", JsonEntityTransform.Object2Json(payCallBackRequest),JsonEntityTransform.Object2Json(orderActList));
			throw new BusinessException("支付正常回调失败");
		}
	}


	/**
	 * 优惠券支付回调
	 * @author afi
	 * @created 2016年4月8日
	 * @param payCallBackRequest
	 * @param orderActList
	 * @return
	 */
	public RstTypeEnum payCallBack4Coupon(PayCallBackRequest payCallBackRequest,List<OrderActivityInfoVo> orderActList) {
		RstTypeEnum result = RstTypeEnum.FAIL;
		if(Check.NuNObj(payCallBackRequest)){
			LogUtil.error(LOGGER, "支付正常回调参数错误,toPayRequest:{}", payCallBackRequest);
			return result;
		}
		boolean dealFlag = true;
		if(!Check.NuNCollection(orderActList)){
			int cuponRes = 0;
			for (OrderActivityInfoVo actVo : orderActList){
				cuponRes += this.createCouponPaymentVouchers(payCallBackRequest, actVo, PaymentTypeEnum.coupon.getCode());//创建优惠券收款单

			}

			if (cuponRes == YesOrNoEnum.NO.getCode()){
				LogUtil.error(LOGGER, "【支付回调】创建优惠券收款单 orderSn：{}",payCallBackRequest.getOrderSn());
				dealFlag = false;
			}


		}

		//调用成功
		if (dealFlag) {
			result = RstTypeEnum.SUCCESS;
		}else {
			LogUtil.error(LOGGER, "支付正常回调失败.payCallBackRequest:{},couponAc:{}", JsonEntityTransform.Object2Json(payCallBackRequest),JsonEntityTransform.Object2Json(orderActList));
			throw new BusinessException("支付正常回调失败");
		}
		return result;
	}


	/**
	 * 收款单判断重复
	 *
	 * @author liyingjie
	 * @created 2016年4月8日 
	 * @param out_trade_no
	 * @return
	 */
	private boolean checkIsHasPaymentVouchers(String out_trade_no) {
		boolean result = false;
		long paymentVouchersNum =  financePaymentVouchersDao.countPaymentNum(out_trade_no);
		if(paymentVouchersNum > 0){
			result = true;
		}
		return result;
	}

	/**
	 * 支付超时回调接口
	 * @author liyingjie
	 * @created 2016年4月8日 
	 * @param toPayRequest flag:判断是否生成付款单
	 * @return
	 */
	public boolean payUnNormalCallBack(PayCallBackRequest toPayRequest,boolean flag) {
		boolean result = true;//返回结果
		if(Check.NuNObj(toPayRequest) || Check.NuNStr(toPayRequest.getOrderSn())){
			result = false;//返回结果
			LogUtil.info(LOGGER, "payUnNormalCallBack 参数,toPayRequest:{}", toPayRequest);
			return result;
		}
		OrderEntity oe = orderBaseDao.getOrderBaseByOrderSn(toPayRequest.getOrderSn());//查询订单
		if(Check.NuNObj(oe)){
			result = false;//返回结果
			LogUtil.info(LOGGER, "payUnNormalCallBack orderSn:{}", toPayRequest.getOrderSn());
			return result;
		}
		if(checkIsHasPaymentVouchers(toPayRequest.getOut_trade_no())){
			return result;
		}

		int orderRes = this.updateUnNormalOrder(toPayRequest);//更新订单
		int orderPay = this.createOrderPay(toPayRequest);//创建支付记录
		int paymentRes = this.createPaymentVouchers(toPayRequest,PaymentTypeEnum.order.getCode());//创建收款单
		boolean consumeRes = true;
		if(flag){//结算逻辑
			consumeRes = this.returnFinanceUser4Cancel(oe,toPayRequest.getCurrent_money());
		}
		if (orderRes == YesOrNoEnum.NO.getCode()
				|| orderPay == YesOrNoEnum.NO.getCode()
				|| paymentRes == YesOrNoEnum.NO.getCode()
				|| !consumeRes ){
			LogUtil.error(LOGGER, "payUnNormalCallBack失败. orderRes:{}, orderPay:{}, paymentRes:{}, cuponRes:{},consumeRes:{}", orderRes, orderPay, paymentRes, consumeRes);
			throw new BusinessException("payNormalCallBack失败.");
		}
		return result;
	}

	/**
	 * 支付回调，订单已经取消，直接生产退款单
	 * @author afi
	 * @param orderEntity
	 * @param refundMoney
	 */
	private boolean returnFinanceUser4Cancel(OrderEntity orderEntity,int refundMoney){
		boolean result = false;
		if(Check.NuNObj(orderEntity) || refundMoney <= 0){
			return result;
		}
		if(this.checkByOrderSn(orderEntity.getOrderSn())){
			result = true;
			return result;
		}
		FinancePayAndDetailVo financeVo = new FinancePayAndDetailVo();
		List<FinancePayVouchersDetailVo> detailVoList = new ArrayList<>();
		//如果累计金额大于0
		if(refundMoney > 0) {
			FinancePayVouchersDetailVo detail = new FinancePayVouchersDetailVo();
			detail.setFeeItemCode(FeeItemCodeEnum.CHECK.getCode());
			detail.setItemMoney(refundMoney);
			detailVoList.add(detail);
			financeVo.setFinancePayVouchersDetailList(detailVoList);
			financeVo.setOrderSn(orderEntity.getOrderSn());
			financeVo.setCityCode(orderEntity.getCityCode());
			financeVo.setPaySourceType(PaySourceTypeEnum.OVERTIME_CANCEL.getCode());
			financeVo.setReceiveUid(orderEntity.getUserUid());
			financeVo.setReceiveType(UserTypeEnum.TENANT.getUserType());
			//ok
			financeVo.setPayType(UserTypeEnum.TENANT.getUserType());
			financeVo.setPayUid(orderEntity.getUserUid());
			financeVo.setTotalFee(refundMoney);
			financeVo.setGenerateFeeTime(new Date());
			financeVo.setRunTime(new Date());
			financeVo.setAuditStatus(AuditStatusEnum.COMPLETE.getCode());
			financeVo.setPaymentType(OrderPaymentTypeEnum.YHFK.getCode());
			virtualPayVouchersDao.saveFinancePayVouchers(financeVo);
			result = true;
		}
		return result;
	}


	/**
	 * 校验当前订单是否已经生成付款单
	 * @param orderSn
	 * @return
	 */
	private boolean checkByOrderSn(String orderSn){
		boolean result = false;
		List<FinancePayVouchersEntity> list = financePayVouchersDao.findByOrderSn(orderSn);
		if(Check.NuNCollection(list)){
			return result;
		}
		if(list.size() > 0){
			result = true;
		}
		return result;
	}

	/**
	 * 生成优惠券 付款单
	 *
	 * @author liyingjie
	 * @created 2016年4月27日 
	 * @param toPayRequest
	 * @return
	 */
	/*private int createCouponPayment(PayCallBackRequest toPayRequest){
		int result = YesOrNoEnum.NO.getCode();
		if(Check.NuNObj(toPayRequest) ){
			LogUtil.info(LOGGER, "生成优惠券参数错误，toPayRequest:{}", toPayRequest);
			return result;
		}
		//获取优惠券列表 方文提供列表   生成优惠券的收款单
		List<CouponEntity>  ceList = couponDao.getCouponListByOrderSn(toPayRequest.getOrderSn());
		if(Check.NuNCollection(ceList)){
			LogUtil.info(LOGGER, "获取优惠券列表错误，ceList：{}", ceList);
			result = YesOrNoEnum.YES.getCode();
			return result;
		}
		for(CouponEntity ce : ceList){
			result++;
			this.createCouponPaymentVouchers(toPayRequest,ce,OrderPaymentTypeEnum.coupon.getCode());
	    }
		return result;

	}*/

	/**
	 * 更新订单接口
	 *
	 * @author liyingjie
	 * @created 2016年4月8日 
	 * @param toPayRequest
	 * @return
	 */
	private int updateOrder(PayCallBackRequest toPayRequest){
		int result = YesOrNoEnum.NO.getCode();
		if(Check.NuNObj(toPayRequest) ){
			LogUtil.error(LOGGER, "更新订单参数错误 ,toPayRequest:{}", toPayRequest);
			return result;
		}
		//更新订单支付状态  支付金额
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setOrderSn(toPayRequest.getOrderSn());
		orderEntity.setPayStatus(OrderPayStatusEnum.HAS_PAY.getPayStatus());
		orderEntity.setPayTime(new Date());
		OrderMoneyEntity moneyEntity = new OrderMoneyEntity();
		moneyEntity.setOrderSn(toPayRequest.getOrderSn());
		moneyEntity.setPayMoney(toPayRequest.getCurrent_money());

		//update order status
		int orderBaseRes = orderBaseDao.updateHasPayStatusOrderSn(orderEntity);
		if(orderBaseRes == YesOrNoEnum.NO.getCode()){
			LogUtil.error(LOGGER, "更新订单错误 ,orderEntity：{},条数:{}", orderEntity, orderBaseRes);
			throw new BusinessException("更新订单错误orderSN:"+moneyEntity.getOrderSn());
		}
		//update money
		int moneyRes = orderMoneyDao.updateOrderMoney(moneyEntity);
		if(moneyRes == YesOrNoEnum.NO.getCode()){
			LogUtil.error(LOGGER, "更新订单金额错误 ,orderEntity：{},条数:{}", orderEntity, moneyRes);
			throw new BusinessException("更新订单金额错误orderSN:"+moneyEntity.getOrderSn());
		}
		//update 订单房源锁
		int lockRes = houseLockDao.payLockHouseByOrderSn(moneyEntity.getOrderSn());
		if(lockRes == YesOrNoEnum.NO.getCode()){
			LogUtil.error(LOGGER, "更新订单锁错误 ,orderEntity：{},条数:{}", orderEntity, lockRes);
			throw new BusinessException("更新订单锁错误 orderSN:"+moneyEntity.getOrderSn());
		}
		result = YesOrNoEnum.YES.getCode();

		return result;


	}

	/**
	 * 
	 * 更新非常 订单
	 *
	 * @author yd
	 * @created 2017年4月19日 下午6:50:19
	 *
	 * @param toPayRequest
	 * @return
	 */
	private int updateUnNormalOrder(PayCallBackRequest toPayRequest){
		int result = YesOrNoEnum.NO.getCode();
		if(Check.NuNObj(toPayRequest) ){
			LogUtil.error(LOGGER, "更新订单参数错误 ,toPayRequest:{}", toPayRequest);
			return result;
		}
		//更新订单支付状态  支付金额
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setOrderSn(toPayRequest.getOrderSn());
		orderEntity.setPayStatus(OrderPayStatusEnum.HAS_PAY.getPayStatus());
		orderEntity.setPayTime(new Date());
		OrderMoneyEntity moneyEntity = new OrderMoneyEntity();
		moneyEntity.setOrderSn(toPayRequest.getOrderSn());
		moneyEntity.setPayMoney(toPayRequest.getCurrent_money());

		//update order status
		int orderBaseRes = orderBaseDao.updateHasPayStatusOrderSn(orderEntity);
		if(orderBaseRes == YesOrNoEnum.NO.getCode()){
			LogUtil.error(LOGGER, "更新订单错误 ,orderEntity：{},条数:{}", orderEntity, orderBaseRes);
			throw new BusinessException("更新订单错误orderSN:"+moneyEntity.getOrderSn());
		}
		//update money
		int moneyRes = orderMoneyDao.updateOrderMoney(moneyEntity);
		if(moneyRes == YesOrNoEnum.NO.getCode()){
			LogUtil.error(LOGGER, "更新订单金额错误 ,orderEntity：{},条数:{}", orderEntity, moneyRes);
			throw new BusinessException("更新订单金额错误orderSN:"+moneyEntity.getOrderSn());
		}
		result = YesOrNoEnum.YES.getCode();

		return result;


	}
	/**
	 * 创建支付记录
	 *
	 * @author liyingjie
	 * @created 2016年4月8日 
	 * @param toPayRequest
	 * @return
	 */
	private int createOrderPay(PayCallBackRequest toPayRequest){
		int result = YesOrNoEnum.NO.getCode();
		if(Check.NuNObj(toPayRequest) ){
			LogUtil.info(LOGGER, "创建支付记录参数错误,toPayRequest:{}", toPayRequest);
			return result;
		}
		//创建支付交易记录
		OrderPayEntity ope = new OrderPayEntity();
		ope.setFid(UUIDGenerator.hexUUID());
		ope.setPayUid(toPayRequest.getPayUid());
		ope.setPaySn(toPayRequest.getBizCode());
		ope.setOrderSn(toPayRequest.getOrderSn());
		ope.setCityCode(toPayRequest.getCityCode());
		ope.setPayType(OrderPayTypeChannelEnum.getPayTypeByPlateFormName(toPayRequest.getPay_type()).getPayType());
		ope.setPayMoney(toPayRequest.getCurrent_money());
		ope.setNeedMoney(toPayRequest.getNeedMoney());
		ope.setPayStatus(OrderPayStatusEnum.HAS_PAY.getPayStatus());
		ope.setTradeNo(toPayRequest.getOut_trade_no());
		ope.setPayCode(toPayRequest.getOrder_code());
		ope.setCreateTime(new Date());
		ope.setLastModifyDate(new Date());
		result = orderPayDao.insertOrderPayRes(ope); //支付回调的时候  保存支付记录
		return result;
	}

	/**
	 * 创建收款单
	 *
	 * @author liyingjie
	 * @created 2016年4月26日 
	 * @param toPayRequest  type:收款单类型  1 订单 2 账单 3 优惠券
	 * @return
	 */
	private int createPaymentVouchers(PayCallBackRequest toPayRequest,int type){
		int result = YesOrNoEnum.NO.getCode();
		if(Check.NuNObj(toPayRequest) ){
			LogUtil.info(LOGGER, "创建收款单参数错误,toPayRequest:{}", toPayRequest);
			return result;
		}
		//创建收款单  
		FinancePaymentVouchersEntity fpve = new FinancePaymentVouchersEntity();
		fpve.setFid(UUIDGenerator.hexUUID());
		fpve.setPaymentSn(OrderSnUtil.getPaymentSn());
		fpve.setOrderSn(toPayRequest.getOrderSn());
		fpve.setCityCode(toPayRequest.getCityCode());
		fpve.setSourceType(toPayRequest.getPaymentSourceType());
		fpve.setPaymentType(type);
		fpve.setPayType(OrderPayTypeChannelEnum.getPayTypeByPlateFormName(toPayRequest.getPay_type()).getPayType());
		fpve.setPaymentUid(toPayRequest.getPayUid());
		fpve.setTotalFee(toPayRequest.getCurrent_money());
		fpve.setNeedMoney(toPayRequest.getNeedMoney());
		fpve.setTradeNo(toPayRequest.getOut_trade_no());
		fpve.setSyncStatus(SyncStatusEnum.unsync.getCode());
		fpve.setIsSend(YesOrNoEnum.NO.getCode());
		//20160617 新增payTime字段
		fpve.setPayTime(new Date());
		result = financePaymentVouchersDao.insertPaymentVouchers(fpve);
		return result;
	}



	/**
	 *  modify by jixd 2017.06.06 兼容 下单减活动
	 * 创建优惠券收款单
	 *
	 * @author liyingjie
	 * @created 2016年4月26日 
	 * @param toPayRequest  type:收款单类型  1 订单 2 账单 3 优惠券
	 * @return
	 */
	private int createCouponPaymentVouchers(PayCallBackRequest toPayRequest,OrderActivityInfoVo orderActivityInfoVo,int type){
		int result = YesOrNoEnum.NO.getCode();
		if(Check.NuNObj(toPayRequest) || Check.NuNObj(orderActivityInfoVo)||Check.NuNStr(orderActivityInfoVo.getAcFid())){
			LogUtil.info(LOGGER, "创建收款单参数错误,toPayRequest:{},cuponEntity:{}", JsonEntityTransform.Object2Json(toPayRequest), JsonEntityTransform.Object2Json(orderActivityInfoVo));
			return result;
		}
		//创建收款单  
		FinancePaymentVouchersEntity fpve = new FinancePaymentVouchersEntity();
		fpve.setFid(UUIDGenerator.hexUUID());
		fpve.setPaymentSn(OrderSnUtil.getPaymentSn());
		fpve.setOrderSn(toPayRequest.getOrderSn());
		fpve.setCityCode(toPayRequest.getCityCode());
		fpve.setSourceType(toPayRequest.getPaymentSourceType());
		//fpve.setPayType(OrderPayTypeChannelEnum.getPayTypeByPlateFormName(SysConst.cash_fill).getPayType());
		fpve.setPayType(OrderAcTypeEnum.getByCode(orderActivityInfoVo.getAcType()).getPayType());
		fpve.setPayTime(new Date());
		fpve.setPaymentType(type);
		fpve.setPaymentUid(toPayRequest.getPayUid());
		fpve.setTotalFee(orderActivityInfoVo.getAcMoney());
		fpve.setNeedMoney(orderActivityInfoVo.getAcMoney());
		fpve.setTradeNo(orderActivityInfoVo.getOrderSn()+"_"+orderActivityInfoVo.getAcFid());
		fpve.setSyncStatus(SyncStatusEnum.unsync.getCode());
		fpve.setIsSend(YesOrNoEnum.NO.getCode());
		//20160617 新增payTime字段
		fpve.setPayTime(new Date());
		// 优惠券收款单执行时间是算出来的
		fpve.setRunTime(orderActivityInfoVo.getPaymentTime());
		result = financePaymentVouchersDao.insertPaymentVouchers(fpve);
		return result;
	}


	/**
	 * 根据订单号获取付信息
	 * @author lishaochuan
	 * @create 2016年8月24日下午1:39:56
	 * @param orderSn
	 * @return
	 */
	public OrderPayEntity getOrderPayByOrderSn(String orderSn){
		return orderPayDao.getOrderPayByOrderSn(orderSn);
	}


	/**
	 * 查询单位时间内房源(房间)的交易量(已支付订单数量)
	 *
	 * @author liujun
	 * @created 2016年12月2日
	 *
	 * @param paramMap
	 * @return
	 */
	public List<HouseStatsVo> queryTradeNumByHouseFid(Map<String, Object> paramMap) {
		return orderPayDao.queryTradeNumByHouseFid(paramMap);
	}
}
