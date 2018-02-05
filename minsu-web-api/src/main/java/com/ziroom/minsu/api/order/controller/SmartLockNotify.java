/**
 * @FileName: SmartLockNotify.java
 * @Package com.ziroom.minsu.api.order.controller
 * 
 * @author yd
 * @created 2016年6月24日 下午7:58:41
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.order.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderSmartLockEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.common.utils.StringUtils;
import com.ziroom.minsu.services.house.smartlock.enumvalue.SmartErrNoEnum;
import com.ziroom.minsu.services.house.smartlock.enumvalue.SmartResultEnum;
import com.ziroom.minsu.services.house.smartlock.param.SmartResult;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.api.inner.OrderSmartLockService;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import com.ziroom.minsu.valenum.ordersmart.TempPswdStatusEnum;

/**
 * <p>门锁异步回调通知</p>
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
@Controller
@RequestMapping("/lock")
public class SmartLockNotify {



	@Resource(name = "order.orderSmartLockService")
	private OrderSmartLockService  orderSmartLockService;

	@Resource(name = "basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;


	@Resource(name = "order.orderCommonService")
	private OrderCommonService orderCommonService;

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(SmartLockNotify.class);


	/**
	 * 
	 * 门锁异步回调
	 *
	 * @author yd
	 * @created 2016年6月24日 下午8:02:16
	 *
	 * @param request
	 * @throws IOException 
	 */
	@RequestMapping("/notify")
	public void  notify(HttpServletRequest request){


		BufferedReader br = null;
		ByteArrayOutputStream out =  new ByteArrayOutputStream();;
		try {
			br = new BufferedReader(new InputStreamReader(
					(ServletInputStream) request.getInputStream()));
			LogUtil.info(logger, "智能锁设置密码回调开始");
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			SmartResult smartResult =  new SmartResult();
			smartResult.setErrNo(Integer.valueOf(SmartErrNoEnum.SUCCESS.getCode()));
			smartResult.setErrMsg("民宿更新失败");

			String pwd = request.getParameter("pwd");
			String orderSn = request.getParameter("orderSn");
			if(!Check.NuNObj(sb)){
				String appMsg=sb.toString();
				LogUtil.info(logger, "用户设置智能锁异步返回结果smartReturn={}", appMsg);
				Map<String, Object>  returnMap = (Map<String, Object>) JsonEntityTransform.json2Map(appMsg);

				Object resultObj = returnMap.get("result");
				Object serviceIdObj = returnMap.get("service_id");
				if(!Check.NuNObj(resultObj)&&!Check.NuNObj(serviceIdObj)){
					int result = (int)resultObj;
					String serviceId = (String) serviceIdObj;
					OrderSmartLockEntity orderSmartLock = new OrderSmartLockEntity();
					orderSmartLock.setServiceId(serviceId);
					if(result==SmartResultEnum.SUCCESS.getCode()){

						String landlordTel  = "";
						String userTel  = "";
						smartResult.setErrMsg("民宿更新成功"); 
						//查询当前订单
						DataTransferObject dto  = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.getOrderInfoByOrderSn(orderSn));

						if(dto.getCode() == 0){
							OrderInfoVo  order = SOAResParseUtil.getValueFromDataByKey(dto.toJsonString(), "orderInfoVo", OrderInfoVo.class);
							if(!Check.NuNObj(order)){
								LogUtil.info(logger, "设置密码成功后,开始发短信,订单对象order={}", order.toJsonStr());
								if(!Check.NuNObj(orderSn)){
									landlordTel = order.getLandlordTel();
									userTel = order.getUserTel();
								}
								orderSmartLock.setOrderSn(orderSn);
								orderSmartLock.setTempPswdStatus(TempPswdStatusEnum.SUCCESS.getCode());
								Map<String, String> paramsMap = new HashMap<String, String>();
								SmsRequest smsRequest = new SmsRequest();
								if(!Check.NuNStr(userTel)&&!Check.NuNStr(pwd)){
									paramsMap.put("{1}", order.getHouseName());
									paramsMap.put("{2}", StringUtils.decode(pwd));
									smsRequest.setMobile(userTel);
									smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.SMARTLOCK_TENENT_TEMPPWD_NOTIFY_ENENT.getCode()));
									smsRequest.setParamsMap(paramsMap);
									this.smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
								}
								if(!Check.NuNStr(landlordTel)&&!Check.NuNStr(pwd)&&!Check.NuNStr(userTel)){
									paramsMap.clear();
									paramsMap.put("{1}", order.getHouseName());
									paramsMap.put("{2}", order.getUserName());
									smsRequest.setParamsMap(paramsMap);
									smsRequest.setMobile(landlordTel);
									smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.SMARTLOCK_TENENT_TEMPPWD_NOTIFY_LANDLORD.getCode()));
									this.smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
								}
							}

						}
					}else{
						orderSmartLock.setTempPswdStatus(TempPswdStatusEnum.FAILED.getCode());
					}
					LogUtil.info(logger, "智能锁设置密码成功后，待修改对象orderSmartLock={}", orderSmartLock.toJsonStr());
					orderSmartLockService.updateOrderSmartLockByOrderSn(JsonEntityTransform.Object2Json(orderSmartLock));
				}
				LogUtil.info(logger, "智能锁设置密码回调结束");
			}
			out.write(JsonEntityTransform.Object2Json(smartResult).getBytes());


		} catch (IOException e) {
			LogUtil.error(logger, "智能锁回调IO异常e={}", e);
		} catch (SOAParseException e) {
			LogUtil.error(logger, "智能锁回调SOAP异常e={}", e);
		}finally{
			try {
				if(!Check.NuNObj(br)){
					br.close();
				}
				if(!Check.NuNObj(out)){
					out.close();
				}
			} catch (IOException e) {
				LogUtil.error(logger, "智能锁回调流关闭异常e={}", e);
			}
		}

	}

}
