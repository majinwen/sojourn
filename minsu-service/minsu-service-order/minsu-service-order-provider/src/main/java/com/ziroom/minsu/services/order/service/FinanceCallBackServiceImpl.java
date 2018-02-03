package com.ziroom.minsu.services.order.service;

import javax.annotation.Resource;

import com.asura.framework.base.util.JsonEntityTransform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.FinancePayVouchersEntity;
import com.ziroom.minsu.entity.order.FinancePayVouchersLogEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.finance.dto.PayVouchersCallBackRequest;
import com.ziroom.minsu.services.order.dao.FinancePayVouchersDao;
import com.ziroom.minsu.services.order.dao.FinancePayVouchersLogDao;
import com.ziroom.minsu.services.order.dao.OrderBaseDao;
import com.ziroom.minsu.services.order.dao.OrderDao;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.finance.PayVoucherCallBackEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;
import com.ziroom.minsu.valenum.order.PaymentStatusEnum;

import java.util.List;


/**
 * <p>财务回调</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年5月1日
 * @since 1.0
 * @version 1.0
 */
@Service("order.financeCallBackServiceImpl")
public class FinanceCallBackServiceImpl {
	
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceCallBackServiceImpl.class);
	
	@Resource(name = "order.financePayVouchersDao")
	private FinancePayVouchersDao financePayVouchersDao;
	
	@Resource(name = "order.financePayVouchersLogDao")
	private FinancePayVouchersLogDao financePayVouchersLogDao;
	
	@Resource(name = "order.orderDao")
	private OrderDao orderDao;
	
	@Resource(name = "order.orderBaseDao")
	private OrderBaseDao orderBaseDao;
	
	/**
   	 * 财务系统付款单 接口 回调
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月20日 
   	 *
   	 * @param request
   	 * @return
   	 */
	public int sendPayVouchersCallBack(PayVouchersCallBackRequest request, FinancePayVouchersEntity sourcePv){
		int result = 0;
		FinancePayVouchersEntity updatePv = new FinancePayVouchersEntity();
		updatePv.setOldPaymentStatus(sourcePv.getPaymentStatus());
		updatePv.setPvSn(request.getBusId());
		if(request.getPayFlag() == PayVoucherCallBackEnum.HAS_PAY.getCode()){
			updatePv.setPaymentStatus(PaymentStatusEnum.HAS_PAY.getCode());
		}else if(request.getPayFlag() == PayVoucherCallBackEnum.UN_PAY.getCode()){
			updatePv.setPaymentStatus(PaymentStatusEnum.FAILED_PAY_UNDO.getCode());
		}else if(request.getPayFlag() == PayVoucherCallBackEnum.ERROR_PAY.getCode()){
			updatePv.setPaymentStatus(PaymentStatusEnum.FAILED_PAY_UNDO.getCode());
		}
		if(!Check.NuNObj(request.getPayTime())){
			updatePv.setActualPayTime(request.getPayTime());
		}
		result = financePayVouchersDao.updatePayVouchersByPvSn(updatePv);
		LogUtil.info(LOGGER, "更新条数，result：{}", result);
		this.insertPayVouchersLog(request, sourcePv.getOrderSn(), result);
		return result;
			
	}
	
	/**
   	 * 更新订单结算状态 为已完成
   	 * @author liyingjie
   	 * @created 2016年5月8日 
   	 * @param pvSn
   	 * @return
   	 */
	public void updateOrderAccountStatus(String pvSn, String orderSn) {
		OrderEntity order = orderBaseDao.getOrderBaseByOrderSn(orderSn);
		if(!OrderStatusEnum.canCheckAccount(order.getOrderStatus())){
			LogUtil.info(LOGGER, "订单未完成，暂不需要修改结算状态，orderSn:{},orderStatus:{}", orderSn,order.getOrderStatus());
			return;
		}
		/*List<FinancePayVouchersEntity> financePayVouchersEntities = financePayVouchersDao.listHasNotPayVouchers(orderSn);
        if (!Check.NuNCollection(financePayVouchersEntities)){
            LogUtil.info(LOGGER,"【updateOrderAccountStatus】 未结算完成列表Obj={}", JsonEntityTransform.Object2Json(financePayVouchersEntities.get(0)));
        }*/
		long notPayNum = financePayVouchersDao.countHasNotPayVouchers(orderSn);
		if(notPayNum >= 1){
			LogUtil.info(LOGGER, "当前还有未结算完成的付款单，orderSn:{},notPayNum：{}", orderSn, notPayNum);
			return;
		}
		LogUtil.info(LOGGER, "所有付款单都已付款，更新订单结算状态 为已完成，orderSn:{},notPayNum：{}", orderSn, notPayNum);
		orderDao.updateAccountStatusByOrderSn(orderSn);
		
		/*long allPayNum = 0,hasPayNum = 0;
		allPayNum = financePayVouchersDao.countAllPayVouchers(orderSn);
		hasPayNum = financePayVouchersDao.countHasPayVouchers(orderSn);
		LogUtil.info(LOGGER, "结算生成的付款单条数，allPayNum:{}，已付款条数，hasPayNum:{}，orderSn:{}", allPayNum, hasPayNum, orderSn);
		//付款单已打款完成
		if(allPayNum == hasPayNum){
			LogUtil.info(LOGGER, "所有付款单都已付款，更新订单结算状态 为已完成，orderSn:{}", orderSn);
			orderDao.updateAccountStatusByOrderSn(orderSn);
		}*/
	}
	
	
	
	/**
   	 * 封装日志 log 实体
   	 * @author liyingjie
   	 * @created 2016年4月20日 
   	 * @param request
   	 * @param orderSn
   	 * @param result
   	 * @return
   	 */
	private void insertPayVouchersLog(PayVouchersCallBackRequest request, String orderSn, int result){
		FinancePayVouchersLogEntity  updatePvLog = new FinancePayVouchersLogEntity();
		updatePvLog.setFid(UUIDGenerator.hexUUID());
		
		if(!Check.NuNStr(request.getReason()) && request.getReason().length()>= 500){
			request.setReason(request.getReason().substring(0, 500));
		}
		StringBuilder resultMsg = new StringBuilder();
		if(request.getPayFlag() == PayVoucherCallBackEnum.HAS_PAY.getCode() && result == 1){
			updatePvLog.setCallStatus(YesOrNoEnum.YES.getCode());
			resultMsg.append("财务回调处理成功.PayFlag:");
		}else{
			updatePvLog.setCallStatus(YesOrNoEnum.NO.getCode());
			resultMsg.append("财务回调处理失败.PayFlag:");
		}
		resultMsg.append(PayVoucherCallBackEnum.getNameByCode(request.getPayFlag()));
		resultMsg.append(".更新付款单数据条数：");
		resultMsg.append(result);
		resultMsg.append(".reason:");
		resultMsg.append(request.getReason());
		
		updatePvLog.setResultMsg(resultMsg.toString());
		updatePvLog.setPvSn(request.getBusId());
		updatePvLog.setOrderSn(orderSn);
		
		financePayVouchersLogDao.insertFinancePayVouchersLog(updatePvLog);
		
	}

}
