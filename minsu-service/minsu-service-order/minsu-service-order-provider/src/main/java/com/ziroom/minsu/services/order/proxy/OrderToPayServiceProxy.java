package com.ziroom.minsu.services.order.proxy;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderToPayEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.order.api.inner.OrderToPayService;
import com.ziroom.minsu.services.order.service.OrderToPayServiceImpl;


@Component("order.orderToPayServiceProxy")
public class OrderToPayServiceProxy implements OrderToPayService {
	
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderToPayServiceProxy.class);

	@Resource(name = "order.messageSource")
    private MessageSource messageSource;
	
	@Resource(name = "order.orderToPayServiceImpl")
	private OrderToPayServiceImpl orderToPayServiceImpl;


	@Override
	public String saveOrderToPay(String params) {
		LogUtil.info(LOGGER, "params:{}", params);
		DataTransferObject dto = new DataTransferObject();
		try{
			OrderToPayEntity orderToPay = JsonEntityTransform.json2Object(params, OrderToPayEntity.class);
			if(Check.NuNObj(orderToPay)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("参数不能为空");
				return dto.toJsonString();
			}
			
			orderToPayServiceImpl.saveOrderToPay(orderToPay);
			
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

}
