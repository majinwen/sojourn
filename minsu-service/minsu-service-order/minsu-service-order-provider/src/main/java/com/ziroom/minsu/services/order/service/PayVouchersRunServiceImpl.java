package com.ziroom.minsu.services.order.service;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.*;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.order.dao.*;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.order.IncomeSourceTypeEnum;
import com.ziroom.minsu.valenum.order.IncomeStatusEnum;
import com.ziroom.minsu.valenum.order.IncomeTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("order.payVouchersRunServiceImpl")
public class PayVouchersRunServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(PayVouchersRunServiceImpl.class);

	@Resource(name = "order.financePayVouchersDao")
	private FinancePayVouchersDao financePayVouchersDao;
	
	@Resource(name = "order.financePayVouchersLogDao")
	private FinancePayVouchersLogDao financePayVouchersLogDao;
	
	@Resource(name = "order.financePayVouchersDetailDao")
    private FinancePayVouchersDetailDao financePayVouchersDetailDao;

	@Resource(name = "order.financeIncomeDao")
	private FinanceIncomeDao financeIncomeDao;
	
	@Resource(name = "order.financeIncomeLogDao")
	private FinanceIncomeLogDao financeIncomeLogDao;


	/**
	 * 将当前的付款变成黑名单
	 * 并且将当前的金额变成收入
	 * @author afi
	 * @param payVouchers
	 */
	public void blackPayVoucherAndTrans2Income(FinancePayVouchersEntity payVouchers){
		if (Check.NuNObj(payVouchers)){
			LogUtil.error(LOGGER, "更新当前付款单为黑名单，当前的付款单为空");
			throw new BusinessException("更新当前付款单为黑名单当前的付款单为空");
		}
		//将当前的付款变成黑名单
		int num = financePayVouchersDao.updatePvBlack(payVouchers.getPvSn());
		if(num != 1){
			LogUtil.error(LOGGER, "更新当前付款单为黑名单，更新的信息条数为：pvsn：{},num:{}",payVouchers.getPvSn(),num);
			throw new BusinessException("异常的付款单信息");
		}
		//将当前的付款单转化成收入
		FinanceIncomeEntity incomeEntity = new FinanceIncomeEntity();
		incomeEntity.setIncomeSourceType(IncomeSourceTypeEnum.USER_SETTLEMENT.getCode());
		incomeEntity.setIncomeType(IncomeTypeEnum.BLACK.getCode());
		incomeEntity.setOrderSn(payVouchers.getOrderSn());
		incomeEntity.setCityCode(payVouchers.getCityCode());
		incomeEntity.setPayUid(payVouchers.getPayUid());
		incomeEntity.setPayType(UserTypeEnum.LANDLORD.getUserType());
		incomeEntity.setTotalFee(payVouchers.getTotalFee());
		financeIncomeDao.insertFinanceIncome(incomeEntity);

		//处理付款单的操作记录
		FinancePayVouchersLogEntity payVouchersLogEntity = new FinancePayVouchersLogEntity();
		payVouchersLogEntity.setFid(UUIDGenerator.hexUUID());
		payVouchersLogEntity.setPvSn(payVouchers.getPvSn());
		payVouchersLogEntity.setOrderSn(payVouchers.getOrderSn());
		payVouchersLogEntity.setCallStatus(YesOrNoEnum.YES.getCode());
		payVouchersLogEntity.setResultCode("未请求");
		payVouchersLogEntity.setResultMsg("黑名单，民宿系统直接把付款单执行成收入，请知晓具体联系运营人员");
		// payVouchersLogEntity.setRemark();
		financePayVouchersLogDao.insertFinancePayVouchersLog(payVouchersLogEntity);
	}

	
	/**
	 * 根据付款单号，获取付款详情
	 * @author lishaochuan
	 * @create 2016年4月23日
	 * @param pvSn
	 * @return
	 */
	public List<FinancePayVouchersDetailEntity> findPayVouchersDetailByPvSn(String pvSn){
		return financePayVouchersDetailDao.findPayVouchersDetailByPvSn(pvSn);
	}
	

	/**
	 * 更新付款单发送状态
	 * @author lishaochuan
	 * @create 2016年9月22日上午11:37:52
	 * @param pvSn
	 */
	public void updateIsSend(String pvSn){
		int num = financePayVouchersDao.updateIsSend(pvSn);
		if(num != 1){
			LogUtil.error(LOGGER, "更新付款单发送状态isSend失败，pvSn:{}，num:{}", pvSn, num);
			throw new BusinessException("更新付款单发送状态isSend失败");
		}
	}


	/**
	 * 更新付款单为未发送状态
	 * @param pvSn
	 * @return
	 */
	public void updateNoSend(String pvSn){
		int num = financePayVouchersDao.updateNoSend(pvSn);
		if(num != 1){
			LogUtil.error(LOGGER, "更新付款单为未发送状态isSend失败，pvSn:{}，num:{}", pvSn, num);
			throw new BusinessException("更新付款单为未发送状态isSend失败");
		}
	}

	/**
	 * 记录付款方式为:ylfh
	 * @author lishaochuan
	 * @create 2016年9月23日下午4:17:58
	 * @param payVouchers
	 */
	public void updateYlfh(FinancePayVouchersEntity payVouchers){
		FinancePayVouchersEntity payVouchersEntity = new FinancePayVouchersEntity();
		payVouchersEntity.setPvSn(payVouchers.getPvSn());
		payVouchersEntity.setPaymentType(payVouchers.getPaymentType());
		financePayVouchersDao.updatePayVouchersByPvSn(payVouchersEntity);
	}
	
	/**
	 * 记录付款方式为：yhfk
	 * 并在付款单上落地银行卡信息
	 * @author lishaochuan
	 * @create 2016年9月20日下午5:28:14
	 * @param payVouchers
	 */
	public void updateYhfk(FinancePayVouchersEntity payVouchers){
		FinancePayVouchersEntity payVouchersEntity = new FinancePayVouchersEntity();
		payVouchersEntity.setPvSn(payVouchers.getPvSn());
		payVouchersEntity.setPaymentType(payVouchers.getPaymentType());
		payVouchersEntity.setBankcardFid(payVouchers.getBankcardFid());
		financePayVouchersDao.updatePayVouchersByPvSn(payVouchersEntity);
	}
	
	/**
	 * 记录付款方式为:account
	 * @author lishaochuan
	 * @create 2016年9月23日下午4:17:58
	 * @param payVouchers
	 */
	public void updateAccount(FinancePayVouchersEntity payVouchers){
		FinancePayVouchersEntity payVouchersEntity = new FinancePayVouchersEntity();
		payVouchersEntity.setPvSn(payVouchers.getPvSn());
		payVouchersEntity.setPaymentType(payVouchers.getPaymentType());
		financePayVouchersDao.updatePayVouchersByPvSn(payVouchersEntity);
	}
	
	
	/**
	 * 更新付款单状态，并记录日志
	 * @author lishaochuan
	 * @create 2016年9月20日上午10:24:26
	 * @param payVouchers
	 * @param callStatus
	 * @param resultCode
	 * @param resultMsg
	 */
	public int updatePayVouchersStatus(FinancePayVouchersEntity payVouchers, Integer callStatus, String resultCode, String resultMsg) {
		FinancePayVouchersEntity payVouchersEntity = new FinancePayVouchersEntity();
		payVouchersEntity.setPvSn(payVouchers.getPvSn());
		payVouchersEntity.setPaymentStatus(payVouchers.getPaymentStatus());
		payVouchersEntity.setPreviousPaymentStatus(payVouchers.getPreviousPaymentStatus());
		payVouchersEntity.setPaymentType(payVouchers.getPaymentType());
		int updateRunPaymentStatus = financePayVouchersDao.updateRunPaymentStatus(payVouchersEntity);
		
		FinancePayVouchersLogEntity payVouchersLogEntity = new FinancePayVouchersLogEntity();
		payVouchersLogEntity.setFid(UUIDGenerator.hexUUID());
		payVouchersLogEntity.setPvSn(payVouchers.getPvSn());
		payVouchersLogEntity.setOrderSn(payVouchers.getOrderSn());
		payVouchersLogEntity.setCallStatus(callStatus);
		payVouchersLogEntity.setResultCode(resultCode);
		if(!Check.NuNStr(resultMsg) && resultMsg.length()>= 500){
			resultMsg = resultMsg.substring(0, 500);
		}
		payVouchersLogEntity.setResultMsg(resultMsg);
		// payVouchersLogEntity.setRemark();
		financePayVouchersLogDao.insertFinancePayVouchersLog(payVouchersLogEntity);
		return updateRunPaymentStatus;
	}


	/**
	 * 更新付款单执行时间,延迟3个小时
	 *
	 * @author lishaochuan
	 * @create 2016/12/13 14:44
	 * @param 
	 * @return 
	 */
	public void updateDelayRunTime(FinancePayVouchersEntity payVouchers){
		Date runTimeNew = DateSplitUtil.jumpHours(payVouchers.getRunTime(), 3);
		if(runTimeNew.before(new Date())){
			runTimeNew = DateSplitUtil.jumpHours(new Date(), 3);
		}

		Map<String, Object> param = new HashMap<>();
		param.put("pvSn", payVouchers.getPvSn());
		param.put("runTime", runTimeNew);
		int num = financePayVouchersDao.updateDelayRunTime(param);
		if(num != 1){
			LogUtil.error(LOGGER, "更新付款单执行时间失败，pvSn:{}，runTimeNew:{}", payVouchers.getPvSn(), runTimeNew);
			throw new BusinessException("更新付款单执行时间失败");
		}
	}
	
	
	
	
	/**
	 * 更新收入表状态为成功，并记录日志
	 * @author lishaochuan
	 * @create 2016年9月18日下午5:02:57
	 * @param incomeSn
	 * @param orderSn
	 * @param resultMsg
	 */
	public void updateIncomeStatusSuccess(String incomeSn, String orderSn, String resultMsg){
		FinanceIncomeEntity incomeEntity = new FinanceIncomeEntity();
		incomeEntity.setIncomeSn(incomeSn);
		incomeEntity.setIncomeStatus(IncomeStatusEnum.YES.getCode());
		incomeEntity.setActualIncomeTime(new Date());
		financeIncomeDao.consumeIncome(incomeEntity);

		FinanceIncomeLogEntity incomeLogEntity = new FinanceIncomeLogEntity();
		incomeLogEntity.setFid(UUIDGenerator.hexUUID());
		incomeLogEntity.setIncomeSn(incomeSn);
		incomeLogEntity.setOrderSn(orderSn);
		incomeLogEntity.setCallStatus(YesOrNoEnum.YES.getCode());
		if(!Check.NuNStr(resultMsg) && resultMsg.length()>= 500){
			resultMsg = resultMsg.substring(0, 500);
		}
		incomeLogEntity.setResultMsg(resultMsg);
		financeIncomeLogDao.insertFinanceIncomeLog(incomeLogEntity);
	}
	
	
	/**
	 * 更新收入表状态为失败，并记录日志
	 * @author lishaochuan
	 * @create 2016年9月18日下午5:03:15
	 * @param incomeSn
	 * @param orderSn
	 * @param resultMsg
	 */
	public void updateIncomeStatusFail(String incomeSn, String orderSn, String resultMsg){
		FinanceIncomeEntity incomeEntity = new FinanceIncomeEntity();
		incomeEntity.setIncomeSn(incomeSn);
		financeIncomeDao.consumeIncome(incomeEntity);

		FinanceIncomeLogEntity incomeLogEntity = new FinanceIncomeLogEntity();
		incomeLogEntity.setFid(UUIDGenerator.hexUUID());
		incomeLogEntity.setIncomeSn(incomeSn);
		incomeLogEntity.setOrderSn(orderSn);
		incomeLogEntity.setCallStatus(YesOrNoEnum.NO.getCode());
		if(!Check.NuNStr(resultMsg) && resultMsg.length()>= 500){
			resultMsg = resultMsg.substring(0, 500);
		}
		incomeLogEntity.setResultMsg(resultMsg);
		financeIncomeLogDao.insertFinanceIncomeLog(incomeLogEntity);
	}

}
