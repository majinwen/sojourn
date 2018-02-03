package com.ziroom.minsu.services.order.proxy;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.ziroom.minsu.entity.order.OrderPayEntity;
import com.ziroom.minsu.services.order.service.FinancePayServiceImpl;
import com.ziroom.minsu.services.order.service.OrderPayServiceImpl;
import com.ziroom.minsu.services.order.utils.FinanceUtil;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBankCardMsgEntity;
import com.ziroom.minsu.entity.order.FinancePayVouchersDetailEntity;
import com.ziroom.minsu.entity.order.FinancePayVouchersEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.finance.api.inner.BillManageService;
import com.ziroom.minsu.services.finance.dto.FinanceIncomeRequest;
import com.ziroom.minsu.services.finance.dto.FinancePayVosRequest;
import com.ziroom.minsu.services.finance.dto.PaymentVouchersRequest;
import com.ziroom.minsu.services.finance.entity.FinanceIncomeVo;
import com.ziroom.minsu.services.finance.entity.FinancePayVouchersVo;
import com.ziroom.minsu.services.finance.entity.FinancePaymentVo;
import com.ziroom.minsu.services.order.constant.OrderMessageConst;
import com.ziroom.minsu.services.order.entity.FinancePayDetailInfoVo;
import com.ziroom.minsu.services.order.service.BillManageServiceImpl;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.finance.PaymentTypeEnum;
import com.ziroom.minsu.valenum.order.OrderPaymentTypeEnum;
import com.ziroom.minsu.valenum.order.PaySourceTypeEnum;
import com.ziroom.minsu.valenum.order.PaymentStatusEnum;


/**
 * <p>账单关联接口代理
 *  说明：收款单  付款单   收入 的后台管理接口
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
@Component("order.billManageServiceProxy")
public class BillManageServiceProxy implements BillManageService {
	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(BillManageServiceProxy.class);
			

	@Resource(name = "order.billManageServiceImpl")
	private BillManageServiceImpl billManageServiceImpl;
	
	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;

	@Resource(name = "customer.customerInfoService")
	private CustomerInfoService customerInfoService;
	
	@Resource(name = "order.messageSource")
	private MessageSource messageSource;

	@Resource(name = "order.orderPayServiceImpl")
	private OrderPayServiceImpl orderPayServiceImpl;

	@Resource(name = "order.financePayServiceImpl")
	private FinancePayServiceImpl financePayServiceImpl;

	/**
	 * 
	 * 按条件分页查询收款单
	 *
	 * @author yd
	 * @created 2016年4月28日 下午8:26:55
	 *
	 * @param paymentVouchersRequest
	 * @return
	 */
	@Override
	public String queryPaymentVoByPage(String paymentVouchersRequest) {
		
		DataTransferObject dto = new DataTransferObject();
		PaymentVouchersRequest  paymentRequest = JsonEntityTransform.json2Object(paymentVouchersRequest, PaymentVouchersRequest.class);
		if(Check.NuNObj(paymentRequest)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数不存在");
			return dto.toJsonString();
		}
		PagingResult<FinancePaymentVo> listPagingResult = null;
		LogUtil.info(logger, "当前查询参数paymentRequest={}", paymentRequest.toJsonStr());
		try {
		     listPagingResult = this.billManageServiceImpl.queryPaymentVoByPage(paymentRequest);
		} catch (Exception e) {
			LogUtil.error(logger, "查询出现异常e={}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("查询出现异常");
			return dto.toJsonString();
		}
		dto.putValue("listFinancePaymentVo", listPagingResult.getRows());
		dto.putValue("total", listPagingResult.getTotal());
		return dto.toJsonString();
	}



    /**
     * 获取收入详情
     * @author afi
     * @param incomeSn
     * @return
     */
    public String getFinanceIncomeDetail(String incomeSn){
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(incomeSn)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("参数不存在");
            return dto.toJsonString();
        }
        FinanceIncomeVo incomeDetail = this.billManageServiceImpl.getFinanceIncomeDetail(incomeSn);
        dto.putValue("incomeDetail",incomeDetail);
        return dto.toJsonString();

    }

	/**
	 * 
	 * 按条件分页查询 公司收入
	 *
	 * @author yd
	 * @created 2016年4月28日 下午8:28:33
	 *
	 * @param financeIncomeReuest
	 * @return
	 */
	@Override
	public String queryFinanceIncomeByPage(String financeIncomeReuest) {
		DataTransferObject dto = new DataTransferObject();
		FinanceIncomeRequest incomeRequest = JsonEntityTransform.json2Object(financeIncomeReuest, FinanceIncomeRequest.class);
		if(Check.NuNObj(incomeRequest)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数不存在");
			return dto.toJsonString();
		}
		LogUtil.info(logger, "当前查询参数incomeRequest={}", incomeRequest.toJsonStr());
		PagingResult<FinanceIncomeVo> listPagingResult = null;
		try {
			 listPagingResult = this.billManageServiceImpl.queryFinanceIncomeByPage(incomeRequest);
		} catch (Exception e) {
			LogUtil.error(logger, "查询出现异常e={}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("查询出现异常");
			return dto.toJsonString();
		}
		
		
		dto.putValue("listFinanceIncomeVo", listPagingResult.getRows());
		dto.putValue("total", listPagingResult.getTotal());
		return dto.toJsonString();
	}

	/**
	 * 
	 * 按条件分页查询付款单表
	 *
	 * @author yd
	 * @created 2016年4月28日 下午8:30:00
	 *
	 * @param FinancePayVosReuest
	 * @return
	 */
	@Override
	public String queryFinancePayVosByPage(String financePayVosReuest) {
		DataTransferObject dto = new DataTransferObject();
		FinancePayVosRequest payVosRequest = JsonEntityTransform.json2Object(financePayVosReuest, FinancePayVosRequest.class);
		if(Check.NuNObj(payVosRequest)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数不存在");
			return dto.toJsonString();
		}
		LogUtil.info(logger, "当前查询参数payVosRequest={}", payVosRequest.toJsonStr());
		PagingResult<FinancePayVouchersVo> listPagingResult = null;
		try {
			listPagingResult = this.billManageServiceImpl.queryFinancePayVosByPage(payVosRequest);
		} catch (Exception e) {
			LogUtil.error(logger, "查询出现异常e={}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("查询出现异常");
			return dto.toJsonString();
		}
		
		dto.putValue("listFinancePayVouchersVo", listPagingResult.getRows());
		dto.putValue("total", listPagingResult.getTotal());
		return dto.toJsonString();
	}
	
	
	/**
	 * 获取付款单详情
	 * @author lishaochuan
	 * @create 2016年5月17日上午11:16:52
	 * @param pvSn
	 * @return
	 */
	@Override
	public String getPayVouchersDetail(String pvSn){
        DataTransferObject dto = new DataTransferObject();
        try {
	        if(Check.NuNStr(pvSn)){
	            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
	            dto.setMsg("参数不存在");
	            return dto.toJsonString();
	        }
	        FinancePayDetailInfoVo pvDetailVo = this.billManageServiceImpl.getFinancePayInfoVo(pvSn);
	        dto.putValue("pvDetailVo",pvDetailVo);
	        
		} catch (Exception e) {
			LogUtil.error(logger, "e:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_OP_ERROR));
            return dto.toJsonString();
		}
        return dto.toJsonString();

    }
	
	/**
	 * 查询重新生成的付款单数量
	 * @author lishaochuan
	 * @create 2016年8月16日下午6:04:16
	 * @param parentPvSn
	 * @return
	 */
	@Override
	public String countReCreatePvs(String parentPvSn) {
		DataTransferObject dto = new DataTransferObject();
        try {
	        if(Check.NuNStr(parentPvSn)){
	            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
	            dto.setMsg("参数不存在");
	            return dto.toJsonString();
	        }
	        long count = this.billManageServiceImpl.countReCreatePvs(parentPvSn);
	        dto.putValue("count",count);
	        
		} catch (Exception e) {
			LogUtil.error(logger, "e:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_OP_ERROR));
            return dto.toJsonString();
		}
        return dto.toJsonString();
	}
	
	/**
	 * 重新生成付款单
	 * @author lishaochuan
	 * @create 2016年8月17日上午11:46:52
	 * @param pvSn
	 * @return
	 */
	@Override
	public String reCreatePvs(String pvSn, String userAccount) {
		DataTransferObject dto = new DataTransferObject();
        try {
	        if(Check.NuNStr(pvSn)){
	            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
	            dto.setMsg("参数不存在");
	            return dto.toJsonString();
	        }
	        
	        //查询当前旧付款单信息
	        FinancePayDetailInfoVo detailVo = this.billManageServiceImpl.getFinancePayInfoVo(pvSn);
	        if(PaymentStatusEnum.FAILED_PAY_UNDO.getCode() != detailVo.getPaymentStatus()){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
	            dto.setMsg("只有"+PaymentStatusEnum.FAILED_PAY_UNDO.getName()+"状态的才能重新生成！");
				return dto.toJsonString();
			}
	        
	        // 新的付款单号
			String parentPvSn = "";
			if("0".equals(detailVo.getParentPvSn()) || Check.NuNStr(detailVo.getParentPvSn())){
				parentPvSn = pvSn;
			}else{
				parentPvSn = detailVo.getParentPvSn();
			}
			long count = this.billManageServiceImpl.countReCreatePvs(parentPvSn);
			String newPvSn = parentPvSn + "_" + (++count);


			// 如果是原路返回，判断是否符合原路返回条件
			String paymentType = OrderPaymentTypeEnum.YHFK.getCode();
			if (OrderPaymentTypeEnum.YLFH.getCode().equals(detailVo.getPaymentType())) {
				OrderPayEntity orderPay = orderPayServiceImpl.getOrderPayByOrderSn(detailVo.getOrderSn());
				boolean checkFlag = FinanceUtil.checkCanYlfh(detailVo, orderPay);
				if (checkFlag && count <= 5) {
					paymentType = OrderPaymentTypeEnum.YLFH.getCode();
				} else {
					paymentType = OrderPaymentTypeEnum.YHFK.getCode();
				}
			}

			//新的银行卡信息
			String bankcardFid = "";
			if(OrderPaymentTypeEnum.YHFK.getCode().equals(paymentType)){
				String bankJson = customerInfoService.getCustomerBankcard(detailVo.getReceiveUid());
				DataTransferObject newBbankDto = JsonEntityTransform.json2DataTransferObject(bankJson);
				CustomerBankCardMsgEntity newBankcard = null;
				if (newBbankDto.getCode() != DataTransferObject.SUCCESS) {
					return newBbankDto.toJsonString();
				}
				newBankcard = newBbankDto.parseData("bankcard", new TypeReference<CustomerBankCardMsgEntity>() {
				});
				bankcardFid = newBankcard.getFid();
			}



	        //新的付款单
	        FinancePayVouchersEntity newPayVouchersEntity = new FinancePayVouchersEntity();
	        BeanUtils.copyProperties(detailVo, newPayVouchersEntity);
	        newPayVouchersEntity.setId(null);
	        newPayVouchersEntity.setPvSn(newPvSn);
	        newPayVouchersEntity.setBankcardFid(bankcardFid);
	        newPayVouchersEntity.setParentPvSn(parentPvSn);
	        newPayVouchersEntity.setPaySourceType(PaySourceTypeEnum.PAY_FAILED_RECREATE.getCode());
	        newPayVouchersEntity.setPaymentStatus(PaymentStatusEnum.UN_PAY.getCode());
	        newPayVouchersEntity.setActualPayTime(null);
	        newPayVouchersEntity.setRunTime(new Date());
	        newPayVouchersEntity.setCreateId(userAccount);
	        newPayVouchersEntity.setCreateTime(new Date());
	        newPayVouchersEntity.setLastModifyDate(null);
	        newPayVouchersEntity.setIsSend(YesOrNoEnum.NO.getCode());
	        newPayVouchersEntity.setIsDel(YesOrNoEnum.NO.getCode());
	        newPayVouchersEntity.setPaymentType(paymentType);
	        
	        //新的付款单详情
	        List<FinancePayVouchersDetailEntity> newDetailList = detailVo.getDetailList();
	        for (FinancePayVouchersDetailEntity financePayVouchersDetailEntity : newDetailList) {
	        	financePayVouchersDetailEntity.setId(null);
	        	financePayVouchersDetailEntity.setPvSn(newPvSn);
			}
	        
	        //保存新付款单、明细，修改旧付款单状态
	        billManageServiceImpl.reCreatePvs(newPayVouchersEntity, newDetailList, pvSn);
	        
	        
		} catch (Exception e) {
			LogUtil.error(logger, "e:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_OP_ERROR));
            return dto.toJsonString();
		}
        return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.finance.api.inner.BillManageService#getPaymentVoById(java.lang.String)
	 */
	@Override
	public String getPaymentVoById(String fid) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(fid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数不存在");
			return dto.toJsonString();
		}
		FinancePaymentVo paymentVo = billManageServiceImpl.getPaymentDetailVo(fid);
		dto.putValue("paymentVo", paymentVo);
		return dto.toJsonString();
	}


	/**
	 * 校验是否可原路返回
	 * @author lishaochuan
	 * @param pvSn
	 * @return
	 */
	@Override
	public String checkCanYlfh(String pvSn) {
		DataTransferObject dto = new DataTransferObject();
		try {
			if(Check.NuNStr(pvSn)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("参数不存在");
				return dto.toJsonString();
			}

			FinancePayVouchersEntity payVouchers = financePayServiceImpl.findPayVouchersByPvSn(pvSn);
			OrderPayEntity orderPay = orderPayServiceImpl.getOrderPayByOrderSn(payVouchers.getOrderSn());

			boolean checkFlag = FinanceUtil.checkCanYlfh(payVouchers, orderPay);

			if(checkFlag){
				//原路返回只能重试5次
				String parentPvSn = "";
				if("0".equals(payVouchers.getParentPvSn()) || Check.NuNStr(payVouchers.getParentPvSn())){
					parentPvSn = pvSn;
				}else{
					parentPvSn = payVouchers.getParentPvSn();
				}
				long count = this.billManageServiceImpl.countReCreatePvs(parentPvSn);
				if(count >= 5){
					checkFlag = false;
				}
			}

			dto.putValue("checkFlag", checkFlag);

		} catch (Exception e) {
			LogUtil.error(logger, "e:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_OP_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}
}
