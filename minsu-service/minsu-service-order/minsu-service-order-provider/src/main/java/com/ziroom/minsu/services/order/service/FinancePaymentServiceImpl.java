package com.ziroom.minsu.services.order.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.order.FinancePaymentVouchersEntity;
import com.ziroom.minsu.entity.order.FinanceSyncLogEntity;
import com.ziroom.minsu.services.order.dao.FinancePaymentVouchersDao;
import com.ziroom.minsu.services.order.dao.FinanceSyncLogDao;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.finance.SyncStatusEnum;

/**
 * <p>收款单</p>
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
@Service("order.financePaymentServiceImpl")
public class FinancePaymentServiceImpl {
	
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FinancePaymentServiceImpl.class);

	@Resource(name = "order.financePaymentVouchersDao")
	private FinancePaymentVouchersDao financePaymentVouchersDao;
	
	@Resource(name ="order.financeSyncLogDao")
	private  FinanceSyncLogDao financeSyncLogDao;
	
	/**
	 * 查询订单支付的收款单
	 * @author lishaochuan
	 * @create 2016年8月24日下午9:40:30
	 * @param orderSn
	 * @return
	 */
	public FinancePaymentVouchersEntity getOrderPayment(String orderSn) {
		return financePaymentVouchersDao.getOrderPayment(orderSn);
	}
	
	/**
	 * 查询收款单中未同步收入记录count
	 * @author lishaochuan
	 * @create 2016年5月1日
	 * @return
	 */
	public Long getNotSyncPaymentCount() {
		/** 直接根据 同步sync_status 取未同步和同步失败的信息 */
		return financePaymentVouchersDao.getNotSyncPaymentCount();
	}
	
	/**
	 * 查询收款单中未同步收入记录list
	 * @author lishaochuan
	 * @create 2016年5月1日
	 * @param limit
	 * @return
	 */
	public List<FinancePaymentVouchersEntity> getNotSyncPaymentList(int limit){
		Map<String, Object> map = new HashMap<String, Object>(2);
		/** 直接根据 同步sync_status 取未同步和同步失败的信息 */
		map.put("limit", limit);
		return financePaymentVouchersDao.getNotSyncPaymentList(map);
	}

	
	/**
	 * 收款单同步后，更新状态，记录日志
	 * @author lishaochuan
	 * @create 2016年5月1日
	 * @param fid
	 * @param syncStatus
	 * @return
	 */
	public int updatePaymentHasSync(FinancePaymentVouchersEntity paymentEntity, Map<String,String> resultMap){
		int syncStatus = SyncStatusEnum.fail.getCode();
		int callStatus = YesOrNoEnum.NO.getCode();
		String resultCode = resultMap.get("result");
		String message = resultMap.get("message");
		 
		String resultMsg = "【同步业务帐Job】,result:" + resultCode + ",message:" + message;
		Date actualSyncTime = null;
		if("1".equals(resultCode)){
			syncStatus = SyncStatusEnum.success.getCode();
			callStatus = YesOrNoEnum.YES.getCode();
			actualSyncTime = new Date();
		}
        
		//记录日志
		FinanceSyncLogEntity fsle = new FinanceSyncLogEntity();
		fsle.setFid(UUIDGenerator.hexUUID());
		fsle.setSyncSn(paymentEntity.getPaymentSn());
		fsle.setOrderSn(paymentEntity.getOrderSn());
		fsle.setCallStatus(callStatus);
		fsle.setResultCode(resultCode);
		if(!Check.NuNStr(resultMsg) && resultMsg.length()>= 500){
			resultMsg = resultMsg.substring(0, 500);
		}
		fsle.setResultMsg(resultMsg);
		financeSyncLogDao.insertFinanceSyncLog(fsle);
		
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("fid", paymentEntity.getFid());
		map.put("syncStatus", syncStatus);
		map.put("actualSyncTime", actualSyncTime);
		
		map.put("isSend", YesOrNoEnum.YES.getCode());	
		
		return financePaymentVouchersDao.updatePaymentHasSync(map);
		
		
	}

}
