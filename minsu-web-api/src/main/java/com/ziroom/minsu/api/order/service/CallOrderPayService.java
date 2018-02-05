package com.ziroom.minsu.api.order.service;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderToPayEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.order.api.inner.OrderPayService;
import com.ziroom.minsu.services.order.api.inner.OrderToPayService;
import com.ziroom.minsu.services.order.dto.ToPayRequest;
import com.ziroom.minsu.services.order.entity.OrderPayResultStatusVo;
import com.ziroom.minsu.services.order.utils.PayUtil;
import com.ziroom.minsu.valenum.order.OrderSourceEnum;
import com.ziroom.minsu.valenum.pay.PayErrorCodeEnum;

/**
 * <p>订单的支付的相关操作</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/1.
 * @version 1.0
 * @since 1.0
 */
@Service("pay.callOrderPayService")
public class CallOrderPayService {


    @Resource(name = "pay.callPayService")
    private CallPayService callPayService;
    
    @Resource(name = "order.orderPayService")
	private OrderPayService orderPayService;
    
    @Resource(name = "order.orderToPayService")
	private OrderToPayService orderToPayService;

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CallOrderPayService.class);


    /**
     * 去支付接口
     * @param toPayRequest
     * @return
     */
    public DataTransferObject toPay(ToPayRequest toPayRequest) {
        DataTransferObject dto = new DataTransferObject();
        try{
	        LogUtil.info(LOGGER, "【去支付】订单来源转换,sourceType:{}", toPayRequest.getSourceType());
	        OrderSourceEnum orderSourceEnum = null;
	        if(!Check.NuNObj(toPayRequest) && !Check.NuNObj(toPayRequest.getSourceType())){
	            orderSourceEnum = OrderSourceEnum.getByClientName(ValueUtil.getStrValue(toPayRequest.getSourceType()));
	            if(Check.NuNObj(orderSourceEnum)){
	                orderSourceEnum = OrderSourceEnum.getByCode(ValueUtil.getintValue(toPayRequest.getSourceType()));
	            }
	            if(Check.NuNObj(orderSourceEnum)){
	                PayUtil.parseFailDto(dto, PayErrorCodeEnum.common106.getCode());
	                return dto;
	            }else {
	                toPayRequest.setSourceType(orderSourceEnum.getClientName());
	            }
	        }
	        LogUtil.info(LOGGER, "【去支付】订单来源转换完毕,sourceType:{}", toPayRequest.getSourceType());
        
	        LogUtil.info(LOGGER, "【去支付】获取支付的信息以及校验,checkToPay");
            String jsonResult = orderPayService.checkToPay(JsonEntityTransform.Object2Json(toPayRequest));
            LogUtil.info(LOGGER, "【去支付】校验结果:{}", jsonResult);
            dto = JsonEntityTransform.json2DataTransferObject(jsonResult);
			if (dto.getCode() != DataTransferObject.SUCCESS ) {
				dto.getData().remove("toPayRequest");
				return dto;
			}
			toPayRequest = SOAResParseUtil.getValueFromDataByKey(jsonResult, "toPayRequest", ToPayRequest.class);
//			String payCode = SOAResParseUtil.getStrFromDataByKey(jsonResult, "payCode");
			LogUtil.info(LOGGER, "【去支付】调用支付平台去支付接口");
    		return this.payCommonLogic(toPayRequest);
    		
        }catch(Exception ex){
            PayUtil.parseFailDto(dto, PayErrorCodeEnum.common105.getCode());
            LogUtil.error(LOGGER, "toPays error:{}", ex);
            return dto;
        }

    }



    /**
     * 订单、账单  去公共逻辑
     * @author liyingjie
     * @created 2016年4月27日
     * @param
     * @return
     */
    private DataTransferObject payCommonLogic(ToPayRequest toPayRequest)throws Exception{
    	DataTransferObject dto = new DataTransferObject();
		String payCode = null;
		// 获取支付单号
		toPayRequest.setBizeCode(toPayRequest.getOrderSn()+"_"+System.currentTimeMillis());
		Map<String,String> payOrderRes = callPayService.createPayOrder(toPayRequest);

		// 调用支付系统下单接口，获取支付单号
		if(Check.NuNMap(payOrderRes) || payOrderRes.get("status").toLowerCase().equals(MessageConst.FAIL_CODE)){
			LogUtil.error(LOGGER, "【去支付】调用支付系统下单接口失败，payOrderRes：{}", payOrderRes);
			PayUtil.parseFailDto(dto,PayErrorCodeEnum.platform1001.getCode());
			return dto;
		}
		payCode = payOrderRes.get("data");
		//校验支付单号
		LogUtil.info(LOGGER, "【去支付】bizeCode:{}, 支付单号payCode:{}",toPayRequest.getBizeCode(),payCode);
		if(Check.NuNStr(payCode)){
			LogUtil.error(LOGGER, "【去支付】bizeCode:{},支付平台返回支付单号为空:{}",toPayRequest.getBizeCode());
			PayUtil.parseFailDto(dto,PayErrorCodeEnum.platform1002.getCode());
			return dto;
		}
		if (!this.saveOrderToPay(toPayRequest, payCode)){
			LogUtil.error(LOGGER, "【去支付】落地 payCode 失败:{}",toPayRequest.getBizeCode());
			PayUtil.parseFailDto(dto,PayErrorCodeEnum.common105.getCode());
			return dto;
		}
        // 调支付系统支付去支付接口
        Map<String,Object> payResMap = callPayService.paySubmit(payCode, toPayRequest);
        //校验去支付结果
        if(Check.NuNMap(payResMap) ||  payResMap.get("status").equals(MessageConst.FAIL_CODE)){
            LogUtil.error(LOGGER, "【去支付】bizeCode:{},payCode:{} 提交支付接口返回失败:{}",toPayRequest.getBizeCode(),payCode,JsonEntityTransform.Object2Json(payResMap));
            PayUtil.parseFailDto(dto,PayErrorCodeEnum.platform1003.getCode());
            return dto;
        }
       
        dto.putValue("data", payResMap.get("data"));
        return dto;
    }


    /**
	 * 落地支付code
	 * @param toPayRequest
	 * @param payCode
	 * @return
     */
    private boolean saveOrderToPay(ToPayRequest toPayRequest, String payCode){
		boolean dealGlag = false;
    	OrderToPayEntity orderToPayEntity = new OrderToPayEntity();
    	orderToPayEntity.setPayCode(payCode);
    	orderToPayEntity.setOrderSn(toPayRequest.getOrderSn());
    	orderToPayEntity.setBizCode(toPayRequest.getBizeCode());
		String payJson = orderToPayService.saveOrderToPay(JsonEntityTransform.Object2Json(orderToPayEntity));
		DataTransferObject toPayDto = JsonEntityTransform.json2DataTransferObject(payJson);
		if (toPayDto.getCode() == DataTransferObject.SUCCESS){
			dealGlag = true;
		}else {
			LogUtil.error(LOGGER, "【去支付】落地 payCode 失败:{}",JsonEntityTransform.Object2Json(orderToPayEntity));
		}
		return dealGlag;
    }


    /**
     * 查询支付结果
     * @author liyingjie
     * @created 2016年4月8日
     * @param
     *
     * @return
     */
    public String findPayResultByPayCode(String payCode){
        //返回结果
        DataTransferObject dto = new DataTransferObject();

        if(Check.NuNStr(payCode)){
            LogUtil.info(LOGGER, "payCode:{}", payCode);
            return dto.toJsonString();
        }
        //调用查询 支付接口
        OrderPayResultStatusVo oprs = callPayService.findPayResult(payCode);
        //设置返回结果值
        dto.putValue("payResData", oprs);

        return dto.toJsonString();
    }


}
