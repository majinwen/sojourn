package com.ziroom.minsu.services.order.proxy;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.FinancePayVouchersEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.utils.DataFormat;
import com.ziroom.minsu.services.finance.dto.PayVouchersCallBackRequest;
import com.ziroom.minsu.services.order.api.inner.FinanceCallBackServiceService;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.order.service.FinanceCallBackServiceImpl;
import com.ziroom.minsu.services.order.service.FinancePayServiceImpl;
import com.ziroom.minsu.services.order.service.OrderCommonServiceImpl;
import com.ziroom.minsu.valenum.common.ErrorCodeEnum;
import com.ziroom.minsu.valenum.finance.PayVoucherCallBackEnum;
import com.ziroom.minsu.valenum.finance.PayVouchersResCodeEnum;
import com.ziroom.minsu.valenum.order.PaymentStatusEnum;
import com.ziroom.minsu.valenum.order.ReceiveTypeEnum;

/**
 * <p>
 * 财务回调
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie 
 * @since 1.0
 * @version 1.0
 */
@Component("order.financeCallBackServiceProxy")
public class FinanceCallBackServiceProxy implements FinanceCallBackServiceService{

	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceCallBackServiceProxy.class);

	@Resource(name = "order.messageSource")
	private MessageSource messageSource;
	
	@Resource(name = "order.financeCallBackServiceImpl")
	private FinanceCallBackServiceImpl financeCallBackServiceImpl;
	
	@Resource(name = "order.financePayServiceImpl")
	private FinancePayServiceImpl financePayServiceImpl;
	
	@Resource(name ="order.orderCommonServiceImpl")
	private OrderCommonServiceImpl orderCommonServiceImpl;
	
	@Resource(name ="order.orderMsgProxy")
	private OrderMsgProxy orderMsgProxy;
	
	@Override
	public String sendPayVouchersCallBack(String param) {
		LogUtil.info(LOGGER, "sendPayVouchersCallBack par:{}", param);
		PayVouchersCallBackRequest request = JsonEntityTransform.json2Object(param, PayVouchersCallBackRequest.class);
		DataTransferObject dto = new DataTransferObject();
		
		//校验参数
		this.checkParams(dto, request);
		if(dto.getCode() != DataTransferObject.SUCCESS){
			return dto.toJsonString();
		}
		
		//获取付款单
		FinancePayVouchersEntity sourcePv = financePayServiceImpl.findPayVouchersByPvSn(request.getBusId());
		
		//校验付款单状态
		this.checkPayFlag(dto, request, sourcePv);
		if(dto.getCode() != DataTransferObject.SUCCESS){
			return dto.toJsonString();
		}

		//处理回调
		int result = financeCallBackServiceImpl.sendPayVouchersCallBack(request, sourcePv);
		
		//处理返回结果
		this.checkResult(dto, request, sourcePv, result);
		
		//发送短信
		this.sendPayMsg(request, sourcePv);
		
		return dto.toJsonString();
	}
	
	
	/**
	 * 校验参数
	 * @author lishaochuan
	 * @create 2016年8月31日下午5:42:04
	 * @param dto
	 * @param request
	 */
	private void checkParams(DataTransferObject dto, PayVouchersCallBackRequest request){
		// 校验 请求参数存在
		if (Check.NuNObj(request)) {
			LogUtil.error(LOGGER, "sendPayVouchersCallBack方法请求参数错误:request{}",request);
			dto.setErrCode(ErrorCodeEnum.fail.getCode());
			dto.setMsg(String.valueOf(PayVouchersResCodeEnum.common100.getCode()));
			return;
		}
		// 校验 busId
		if (Check.NuNStr(request.getBusId())) {
			LogUtil.error(LOGGER, "sendPayVouchersCallBack方法参数busId为空:{}", request.getBusId());
			dto.setErrCode(ErrorCodeEnum.fail.getCode());
			dto.setMsg(String.valueOf(PayVouchersResCodeEnum.common100.getCode()));
			return;
		}
	}
	
	/**
	 * 校验付款单状态
	 * @author lishaochuan
	 * @create 2016年8月31日下午5:44:10
	 * @param dto
	 * @param request
	 * @param sourcePv
	 */
	private void checkPayFlag(DataTransferObject dto, PayVouchersCallBackRequest request, FinancePayVouchersEntity sourcePv){
		// 校验 busId合法性校验
		if (Check.NuNObj(sourcePv)) {
			LogUtil.error(LOGGER, "sendPayVouchersCallBack 非法的pvSn:{}", request.getBusId());
			dto.setErrCode(ErrorCodeEnum.fail.getCode());
			dto.setMsg(String.valueOf(PayVouchersResCodeEnum.common102.getCode()));
			return;
		}
		// 校验当前付款单状态
		int paymentStatus = sourcePv.getPaymentStatus();
		if (request.getPayFlag() == PayVoucherCallBackEnum.HAS_PAY.getCode() && paymentStatus == PaymentStatusEnum.HAS_PAY.getCode()){
			LogUtil.info(LOGGER, "当前付款单已回调置为打款成功，幂等返回，paymentStatus:{},sourcePv:{}", paymentStatus, JsonEntityTransform.Object2Json(sourcePv));
			dto.putValue("code", "");
			dto.putValue("meg", ErrorCodeEnum.success.getCodeEn());
			return;
		}
		if ((request.getPayFlag() == PayVoucherCallBackEnum.UN_PAY.getCode() || request.getPayFlag() == PayVoucherCallBackEnum.ERROR_PAY.getCode())
				&& paymentStatus == PaymentStatusEnum.FAILED_PAY_UNDO.getCode()) {
			LogUtil.info(LOGGER, "当前付款单已回调置为打款失败待处理，幂等返回，paymentStatus:{},sourcePv:{}", paymentStatus, JsonEntityTransform.Object2Json(sourcePv));
			dto.putValue("code", "");
			dto.putValue("meg", ErrorCodeEnum.success.getCodeEn());
			return;
		}
		if (paymentStatus != PaymentStatusEnum.HAS_REQUEST_PAY.getCode()){
			LogUtil.error(LOGGER, "当前付款单状态不为已申请打款，paymentStatus:{},sourcePv:{}", paymentStatus, JsonEntityTransform.Object2Json(sourcePv));
			dto.setErrCode(ErrorCodeEnum.fail.getCode());
			dto.setMsg(String.valueOf(PayVouchersResCodeEnum.common100.getCode()));
			return;
		}
	}
	
	
	/**
	 * 处理返回结果,更新结算状态
	 * @author lishaochuan
	 * @create 2016年8月31日下午5:57:44
	 * @param dto
	 * @param request
	 * @param sourcePv
	 * @param result
	 */
	private void checkResult(DataTransferObject dto, PayVouchersCallBackRequest request, FinancePayVouchersEntity sourcePv, int result){
		if (result != 1) {
			LogUtil.info(LOGGER, "更新付款单失败", sourcePv);
			dto.setErrCode(ErrorCodeEnum.fail.getCode());
			dto.setMsg(String.valueOf(PayVouchersResCodeEnum.common104.getCode()));
		} else {
			LogUtil.info(LOGGER, "更新付款单成功");
			dto.putValue("result", result);
			dto.putValue("code", "");
			dto.putValue("meg", ErrorCodeEnum.success.getCodeEn());
			// 更新结算状态
			financeCallBackServiceImpl.updateOrderAccountStatus(request.getBusId(), sourcePv.getOrderSn());
		}
	}
	
	/**
	 * 发送短信
	 * @author lishaochuan
	 * @create 2016年8月31日下午5:34:06
	 * @param request
	 * @param sourcePv
	 */
	private void sendPayMsg(PayVouchersCallBackRequest request, FinancePayVouchersEntity sourcePv) {
		if (request.getPayFlag() == PayVoucherCallBackEnum.HAS_PAY.getCode()) {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("orderSn", sourcePv.getOrderSn());
			paramMap.put("type", String.valueOf(sourcePv.getReceiveType()));
			paramMap.put("uid", sourcePv.getReceiveUid());
			paramMap.put("totalFee", DataFormat.formatHundredPrice(sourcePv.getTotalFee()));

			OrderInfoVo order = orderCommonServiceImpl.getOrderInfoByOrderSn(sourcePv.getOrderSn());
			if (sourcePv.getReceiveType() == ReceiveTypeEnum.TENANT.getCode()) {
				paramMap.put("phone", order.getUserTel());
				paramMap.put("userTelCode", order.getUserTelCode());
			}else if (sourcePv.getReceiveType() == ReceiveTypeEnum.LANDLORD.getCode()) {
				paramMap.put("phone", order.getLandlordTel());
				paramMap.put("landlordTelCode", order.getLandlordTelCode());
				orderMsgProxy.sendMsgToLanForPayVouchers(sourcePv.getOrderSn(), sourcePv.getPaySourceType(), sourcePv.getTotalFee(), "绑定的银行卡",sourcePv.getPvSn());
			    return;
			}
			paramMap.put("houseSn", order.getHouseSn());
			orderMsgProxy.sendMsg4RefundSuccess(paramMap);
			
			
			
		} else {// 打款失败消息
			
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("type", String.valueOf(sourcePv.getReceiveType()));
			paramMap.put("uid", sourcePv.getReceiveUid());

			// 转账失败给客户发送短信通知
			String orderSn = sourcePv.getOrderSn();
			OrderEntity orderEntity = orderCommonServiceImpl.getOrderBaseByOrderSn(orderSn);
			if (sourcePv.getReceiveType() == ReceiveTypeEnum.TENANT.getCode()) {
				paramMap.put("phone", orderEntity.getUserTel());
				paramMap.put("userTelCode", orderEntity.getUserTelCode());
			} else if (sourcePv.getReceiveType() == ReceiveTypeEnum.LANDLORD.getCode()) {
				paramMap.put("phone", orderEntity.getLandlordTel());
				paramMap.put("landlordTelCode", orderEntity.getLandlordTelCode());
			}
			orderMsgProxy.sendMsg4RefundFail(paramMap);
		}
	}

}
