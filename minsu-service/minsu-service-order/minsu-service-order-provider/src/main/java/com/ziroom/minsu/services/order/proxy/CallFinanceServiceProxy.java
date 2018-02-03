package com.ziroom.minsu.services.order.proxy;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.FinancePayVouchersDetailEntity;
import com.ziroom.minsu.entity.order.FinancePaymentVouchersEntity;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.CryptAES;
import com.ziroom.minsu.services.finance.dto.PayVouchersRequest;
import com.ziroom.minsu.services.finance.entity.FinanceIncomeVo;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.finance.InvoiceIncomeEnum;
import com.ziroom.minsu.valenum.finance.PaymentTypeEnum;
import com.ziroom.minsu.valenum.finance.ReceiptStatusEnum;
import com.ziroom.minsu.valenum.order.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>调财务接口</p>
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
@Service("order.callFinanceServiceProxy")
public class CallFinanceServiceProxy {

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CallFinanceServiceProxy.class);
	
    @Value(value = "${FINANCE.FINANCE_PAY_KEY}")
    private String FINANCE_PAY_KEY;
	
	@Value(value = "${FINANCE.FINANCE_VOUCHERS_PAY_URL}")
    private String FINANCE_VOUCHERS_PAY_URL;
	
	@Value(value = "${FINANCE.SYNC_ACCOUNT_DATA}")
    private String SYNC_ACCOUNT_DATA;

	@Value(value = "${PAY.ENCODING}")
    private String ENCODING;

	@Value(value = "${FINANCE.COMMON_URI}")
    private String COMMON_URI;
	
	@Value(value = "${SYS.LOCAL_URL}")
    private String LOCAL_URL;

	@Value(value = "${FINANCE.SYNCINCOME_URL}")
	private String SYNCINCOME_URL;
	
	
	
	/**
   	 * 同步收入到财务
   	 * @author liyingjie
   	 * @created 2016年4月20日 
   	 *
   	 * @param financeIncomeVo
   	 * @return
	 * @throws Exception 
   	 */
	public Map<String,String> syncIncomeData(FinanceIncomeVo financeIncomeVo) throws Exception {
		JSONObject jsonParam = makeIncomeDataJson(financeIncomeVo);
		LogUtil.info(LOGGER, "同步收入到财务参数,param:{}", financeIncomeVo.toJsonStr());
		String resultContent  = excuteAndGetResult(SYNCINCOME_URL , jsonParam.toString());
		LogUtil.info(LOGGER, "同步收入到财务结果,resultContent:{}", resultContent);
		return this.parseSyncIncomeReponse(resultContent);
	}

	/**
	 * 处理同步收入接口出参
	 * @param resultContent
	 * @return
	 * @throws Exception
     */
	private Map<String,String> parseSyncIncomeReponse(String resultContent) throws Exception {
		// 返回结果: 成功:{"code":200,"message":"success"} 参数错误:其他
		Map<String,String> resMap = new HashMap<>();
		if (!Check.NuNStr(resultContent)) {
			JSONObject resJson = JSONObject.parseObject(resultContent);
			resMap.put("code", resJson.getString("code"));
			resMap.put("message", resJson.getString("message"));
		} else {
			resMap.put("code", "FAIL");
			resMap.put("message", "调用同步接口 返回结果为空");
			LogUtil.error(LOGGER, "parseSyncIncomeReponse接口没有返回信息{}", resultContent);
		}
		return resMap;
	}



	/**
   	 * 封装同步收入参数
   	 * @author liyingjie
   	 * @created 2016年4月20日 
   	 *
   	 * @param 
   	 * @return
   	 */
	private JSONObject makeIncomeDataJson(FinanceIncomeVo fivo) {
		JSONObject incomeJson = new JSONObject();
		incomeJson.put("orderNo", fivo.getOrderSn());

		IncomeTypeEnum incomeTypeEnum = IncomeTypeEnum.getByCode(fivo.getIncomeType());
		if (Check.NuNObj(incomeTypeEnum)){
			LogUtil.error(LOGGER,"异常的收入类型fivo：{}", JsonEntityTransform.Object2Json(fivo));
			throw new BusinessException("异常的收入类型");
		}
		if (incomeTypeEnum.getUserType().getUserType() == UserTypeEnum.TENANT.getUserType()) {
			incomeJson.put("customerName", fivo.getUserName());
		} else if (incomeTypeEnum.getUserType().getUserType() == UserTypeEnum.LANDLORD.getUserType()) {
			incomeJson.put("landlordName", fivo.getLandlordName());
		}
		incomeJson.put("orderStatus", SysConst.Income.order_status);
		incomeJson.put("companyCode", SysConst.Common.company_code);
		incomeJson.put("companyName", SysConst.Income.company_minsu_name);
		incomeJson.put("businessUnitCode",SysConst.Common.BU_CODE);
		incomeJson.put("businessUnitName",SysConst.Common.BU_NAME);
		incomeJson.put("orderNum",fivo.getTotalFee());
		incomeJson.put("incomeNum", fivo.getTotalFee());
		incomeJson.put("invoiceContent", InvoiceIncomeEnum.getByCode(fivo.getIncomeType()).getName()); //业主-服务费  自如客-服务费
		incomeJson.put("invoiceContentCode", InvoiceIncomeEnum.getByCode(fivo.getIncomeType()).getFinanceCode()); //业主-服务费  自如客-服务费
		incomeJson.put("incomeType", InvoiceIncomeEnum.getByCode(fivo.getIncomeType()).getCode()); //房东服务费、房客服务费、违约金服务费（房客违约金，房东违约金）
		incomeJson.put("startTime", DateUtil.dateFormat(fivo.getGenerateFeeTime(), "yyyy-MM-dd HH:mm:ss"));
		incomeJson.put("endTime", DateUtil.dateFormat(fivo.getGenerateFeeTime(),"yyyy-MM-dd HH:mm:ss"));
		incomeJson.put("uniqueSequence", SysConst.sys_source_max + fivo.getIncomeSn());
        return incomeJson;
	}
	
	
	
	
    /**
   	 * 支付成功同步业务账接口
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月20日 
   	 *
   	 * @param payment
   	 * @return
     * @throws Exception 
   	 */
	public Map<String,String> syncPaymentData(FinancePaymentVouchersEntity payment) throws Exception {
		JSONObject jsonParam = makePaymentDataJson(payment);
		LogUtil.info(LOGGER, "同步业务账到财务参数,param:{}", jsonParam.toJSONString());
        String resultContent = "";//结果// 
		resultContent = excuteAndGetResult(COMMON_URI+SYNC_ACCOUNT_DATA , jsonParam.toString());
		LogUtil.info(LOGGER, "支付成功同步业务账结果,resultContent:{}", resultContent);
		return this.parseSyncReponse(resultContent);
	}

	
	/**
   	 * 封装同步业务账参数
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月20日 
   	 *
   	 * @param payment
   	 * @return
   	 */
	private JSONObject makePaymentDataJson(FinancePaymentVouchersEntity payment) {
		JSONObject payVoucher = new JSONObject();
		payVoucher.put("uniqueSequence", SysConst.sys_source_max + payment.getPaymentSn());
		payVoucher.put("receivableAmount", payment.getNeedMoney()); //应付金额
		payVoucher.put("realAmount", payment.getNeedMoney()); //金额
		payVoucher.put("receiptStatus", ReceiptStatusEnum.has_sure.getCode()); //收款状态
		
		/*//现金收款单
		if(PaymentTypeEnum.order.getCode() == payment.getPaymentType()){
			payVoucher.put("receiptPath", PaymentTypeEnum.order.getFinanceCode()); // receiptPath 房租传1 
			payVoucher.put("receiptType", PaymentTypeEnum.order.getFinanceName()); //
		}
		//优惠券收款单
		if(PaymentTypeEnum.coupon.getCode() == payment.getPaymentType()){
			payVoucher.put("receiptPath", PaymentTypeEnum.coupon.getFinanceCode()); //receiptPath 优惠券传传5 
			payVoucher.put("receiptType", PaymentTypeEnum.coupon.getFinanceName()); //
		}
		//账单收款单
		if(PaymentTypeEnum.punish.getCode() == payment.getPaymentType()){
			payVoucher.put("receiptPath", PaymentTypeEnum.punish.getFinanceCode()); //receiptPath 罚金 暂未确认
			payVoucher.put("receiptType", PaymentTypeEnum.punish.getFinanceName()); //
		}*/
		PaymentTypeEnum paymentTypeEnum = PaymentTypeEnum.getByCode(payment.getPaymentType());
		if(Check.NuNObj(paymentTypeEnum)){
			LogUtil.error(LOGGER, "同步业务账到财务参数,paymentTypeEnum为空，paymentType:{},payment:{}", payment.getPaymentType(), JsonEntityTransform.Object2Json(payment));
			throw new BusinessException("同步业务账paymentTypeEnum为空");
		}
		payVoucher.put("receiptPath", paymentTypeEnum.getFinanceCode());
		payVoucher.put("receiptType", paymentTypeEnum.getFinanceName());
		
		
		payVoucher.put("payNum",payment.getTradeNo()); //支付单号/优惠码
		payVoucher.put("receiptMethod", OrderPayTypeChannelEnum.getPayStatusByName(payment.getPayType()).getReceiptMethod()); //收款方式
		payVoucher.put("drawee", payment.getPaymentUid()); //付款人
		payVoucher.put("isContract", YesOrNoEnum.YES.getCode()); //是否关联合同
		payVoucher.put("contract", payment.getOrderSn()); //合同号
		payVoucher.put("businessNum", payment.getOrderSn()); //业务系统关联号
		payVoucher.put("dataSources", SysConst.Common.BU_CODE); //BU代码
		payVoucher.put("remark", SysConst.Payment.remark); //备注
		payVoucher.put("cityCode",SysConst.Common.company_code);
		
		Date payTimeDate = payment.getPayTime();
		if(!Check.NuNObj(payTimeDate)){
			payVoucher.put("payTime", DateUtil.dateFormat(payTimeDate, "yyyy-MM-dd HH:mm:ss")); //BU代码
		}
		return payVoucher;

	}
	
	
	/**
	 * 处理同步收入、业务帐接口返回值
	 * @author lishaochuan
	 * @create 2016年5月1日
	 * @param resultContent
	 * @return
	 * @throws Exception
	 */
	private Map<String,String> parseSyncReponse(String resultContent) throws Exception {
		// 返回结果: 成功:{"result":1,"message":"OK"} 参数错误:{"result":2,"message":"PARAM ERROR"} 失败:{"result":0,"message":"ERROR"}
		Map<String,String> resMap = new HashMap<String,String>(2);
		if (!Check.NuNStr(resultContent)) {
			JSONObject resJson = JSONObject.parseObject(resultContent);
			resMap.put("result", resJson.getString("result"));
			resMap.put("message", resJson.getString("message"));
		} else {
			resMap.put("result", "FAIL");
			resMap.put("message", "调用同步接口 返回结果为空");
			LogUtil.error(LOGGER, "parseSyncReponse接口没有返回信息{}", resultContent);
		}
		return resMap;
	}
	

	
	
	
	/**
   	 * 给财务系统 调用 付款单
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月20日 
   	 *
   	 * @param payVouchersRequest
   	 * @return
   	 */
	public Map<String,String> sendPayVouchers(PayVouchersRequest payVouchersRequest) throws Exception{
		JSONObject payVoucher = makePayVouchersJson(payVouchersRequest); //封装下单参数
		LogUtil.info(LOGGER, "sendPayVouchers params:{}",payVoucher);
		String resultContent = "";//结果
		//FINANCE_VOUCHERS_PAY_URL
		String url = FINANCE_VOUCHERS_PAY_URL
				+ "?request_type="+ SysConst.request_type
				+ "&time_stamp=" + String.valueOf(new Date().getTime())
				+ "&encryption=" + URLEncoder.encode(CryptAES.AES_Encrypt(FINANCE_PAY_KEY,payVoucher.toString()), ENCODING);
	    resultContent = excuteAndGetResult(url);
		
		LogUtil.info(LOGGER, "sendPayVouchers result:{}",resultContent);
		return parsePayVouchersReponse(resultContent);
	}

	
	
	/**
   	 * 封装付款单接口参数
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月20日 
   	 *
   	 * @param 
   	 * @return
   	 */
	private JSONObject makePayVouchersJson(PayVouchersRequest payVouchersRequest) {
		JSONArray array = new JSONArray();
		JSONObject detail = null;
		for (FinancePayVouchersDetailEntity fp : payVouchersRequest.getDetailList()) {
			detail = new JSONObject();
			detail.put("cost_code", FeeItemCodeEnum.getByCode(fp.getFeeItemCode()).getFinanceName());
			detail.put("total_fee", - Math.abs(BigDecimalUtil.div(fp.getItemMoney(), 100,2)));
			array.add(detail);
		}
        JSONObject payVoucher = new JSONObject();
		payVoucher.put("company_code", SysConst.Common.company_code);//公司
		payVoucher.put("panyment_type_code", payVouchersRequest.getPaymentTypeCode());//付款类型：银行付款、原路返回
		payVoucher.put("mark_collect_code", ReceiveTypeEnum.getByCode(payVouchersRequest.getReceiveType()).getFinanceCode());//收款对象类型
		payVoucher.put("pay_vouchers_detail", array); //付款单明细
		payVoucher.put("bill_type", SysConst.bill_type);//单据类型
		payVoucher.put("bus_id", payVouchersRequest.getPvSn()); //业务单号
		payVoucher.put("data_sources", SysConst.sys_source);//数据来源  来自哪个系统
		payVoucher.put("audit_flag", payVouchersRequest.getAuditStatus());//审核状态
		payVoucher.put("pay_flag", payVouchersRequest.getPaymentStatus()); //付款状态
		payVoucher.put("creator", payVouchersRequest.getCreator()); // 创建人
		payVoucher.put("order_code", payVouchersRequest.getOrderSn());//订单号
		payVoucher.put("customer_bank_name", payVouchersRequest.getCustomerBankName()); // 开户银行名称
		payVoucher.put("customer_account_name", payVouchersRequest.getCustomerAccountName()); //开户人名称
		payVoucher.put("customer_bank_account", payVouchersRequest.getCustomerAccountNo()); //开户银行卡号
		payVoucher.put("recieved_account", SysConst.recieved_account);
		payVoucher.put("pay_time", payVouchersRequest.getPayTime());//实际的付款时间
		payVoucher.put("uid", payVouchersRequest.getReceiveUid()); //用户uid
		payVoucher.put("customer_name", payVouchersRequest.getCustomerName());
		payVoucher.put("customer_phone", payVouchersRequest.getCustomerPhone());//LOCAL_URL
		payVoucher.put("call_url", LOCAL_URL+SysConst.pay_vouchers_callback_url);
		
		//原路返回参数
		if(OrderPaymentTypeEnum.YLFH.getCode().equals(payVouchersRequest.getPaymentTypeCode())){
			payVoucher.put("Chanal", payVouchersRequest.getChannal());
			payVoucher.put("BizCode", payVouchersRequest.getBizCode());
			payVoucher.put("out_trade_no", payVouchersRequest.getOutTradeNo());
			payVoucher.put("transaction_id", payVouchersRequest.getTransactionId());
			payVoucher.put("payorderCode_origin", payVouchersRequest.getPayorderCodeOrigin());
			payVoucher.put("pay_total_fee", payVouchersRequest.getPayTotalFee());
		}
		
		return payVoucher;
    }
	
	
	/**
   	 * 处理 付款单 接口返回值
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月20日 
   	 *
   	 * @param resultContent
   	 * @return
   	 */
	private Map<String,String> parsePayVouchersReponse(String resultContent) throws Exception {
		Map<String,String> resMap = new HashMap<String,String>(2);
		if (!Check.NuNStr(resultContent)) {
			JSONObject resJson = JSONObject.parseObject(resultContent);
			resMap.put("resultCode", resJson.getString("resultCode"));
			resMap.put("status", resJson.getString("status"));
			resMap.put("mesName", resJson.getString("mesName"));
		} else {
			resMap.put("status", "FAIL");
			resMap.put("mesName", "调用付款单接口 返回结果为空");
			LogUtil.error(LOGGER, "parsePayVouchersReponse接口没有返回信息{}", resultContent);
		}
		return resMap;
	}
	
	
	
	/**
   	 * 发送请求
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月8日 
   	 *
   	 * @param url http请求路径 param请求参数
   	 * @return
   	 */
	private String excuteAndGetResult(String url,String param){
		
		String resJson = null;
		try {
			resJson = CloseableHttpUtil.sendPost(url, param);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "excuteAndGetResults接口没有返回信息{}", e);
		}
		return resJson;
		
	}
	
	
	/**
   	 * 发送请求
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月8日 
   	 *
   	 * @param url http请求路径 及参数
   	 * @return
   	 */
	private String excuteAndGetResult(String url){
		String resJson = "";
		try {
			resJson = CloseableHttpUtil.sendPost(url);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "excuteAndGetResults接口没有返回信息{}", resJson);
		}
		return resJson;
	}
	
	
	
}
