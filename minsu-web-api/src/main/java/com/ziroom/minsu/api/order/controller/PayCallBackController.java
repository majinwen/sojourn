package com.ziroom.minsu.api.order.controller;

import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ziroom.minsu.valenum.pay.PlatFormPayEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.ziroom.minsu.api.common.thread.SendOrderEmailThread;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.common.constant.CatConstant;
import com.ziroom.minsu.services.common.thread.pool.SendEmailThreadPool;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.order.api.inner.OrderPayService;
import com.ziroom.minsu.services.order.dto.SendOrderEmailRequest;

/**
 * <p>支付回调</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年4月30日
 * @since 1.0
 * @version 1.0
 */
@RequestMapping("/pay")
@Controller
public class PayCallBackController {

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(PayCallBackController.class);

	 @Resource(name = "order.orderPayService")
	private OrderPayService orderPayService;
	 
	@Resource(name="basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;
	
	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;

	/**
	 * 正常支付回调
	 * @author lishaochuan
	 * @create 2016年4月27日
	 * @param request
	 * @return
	 */
	@RequestMapping("${NO_LGIN_AUTH}/payCallBackNormal")
	@ResponseBody
	public String payCallBackNormal(HttpServletRequest request) {
		DataTransferObject dto = new DataTransferObject();
		try {
			String encryption = request.getParameter("encryption");
			if(Check.NuNStr(encryption)){
				dto.setErrCode(1);
				dto.setMsg("参数为空！");
				return dto.toJsonString();
			}
			
			LogUtil.info(LOGGER, "payCallBackNormal encryption：{}", encryption);
			String eString = URLEncoder.encode(encryption,"UTF-8");
			LogUtil.info(LOGGER, "payCallBackNormal params：{}", eString);
			String resultJson = orderPayService.payCallBack(eString, PlatFormPayEnum.order.getCode());
			LogUtil.info(LOGGER, "resultJson：{}", resultJson);
			//支付完成发送邮件开始
			SendOrderEmailRequest orderEmailRequest=SOAResParseUtil.getValueFromDataByKey(resultJson, "orderEmailRequest", SendOrderEmailRequest.class);
			if(!Check.NuNObj(orderEmailRequest)){
				SendEmailThreadPool.execute(new SendOrderEmailThread(orderEmailRequest,customerMsgManagerService,smsTemplateService));
			}
			//支付完成发送邮件结束
			//统计支付回调数量
			Transaction payTran = Cat.newTransaction("PayCallBackController", CatConstant.PAY_CALL_BACK);
			try {
				Cat.logMetricForCount("支付回调数量");
				payTran.setStatus(Message.SUCCESS);
			} catch(Exception ex) {
				Cat.logError("支付回调数量 打点异常", ex);
				payTran.setStatus(ex);
			} finally {
				payTran.complete();
			}
			return resultJson;
		} catch (Exception e) {
			
			LogUtil.error(LOGGER, "payCallBackNormal error, e={}",e);
			dto.setErrCode(1);
			dto.setMsg("服务器内部异常");
			return dto.toJsonString();
		}
		
	}
	
	/**
	 * 房东支付罚款回调
	 * @author lishaochuan
	 * @create 2016年4月27日
	 * @param request
	 * @return
	 */
	@RequestMapping("${NO_LGIN_AUTH}/payCallBackPunish")
	@ResponseBody
	public String payCallBackPunish(HttpServletRequest request) {
		
		try {
			String encryption = request.getParameter("encryption");
			String eString = URLEncoder.encode(encryption,"UTF-8");
			LogUtil.info(LOGGER, "payCallBackPunish params：{}", eString);
			String resultJson = orderPayService.payCallBack(eString, PlatFormPayEnum.punish.getCode());
			LogUtil.info(LOGGER, "payCallBackPunish resultJson：{}", resultJson);
			return resultJson;
		} catch (Exception e) {
			DataTransferObject dto = new DataTransferObject();
			LogUtil.error(LOGGER, "payCallBackPunish error, e={}",e);
			dto.setErrCode(1);
			dto.setMsg("服务器内部异常");
			return dto.toJsonString();
		}
		
	}
}
