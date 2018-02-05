package com.ziroom.minsu.api.order.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.constant.ApiConst;
import com.ziroom.minsu.api.order.dto.PayVouchersCallBackApiRequest;
import com.ziroom.minsu.services.finance.dto.PayVouchersCallBackRequest;
import com.ziroom.minsu.services.order.api.inner.FinanceCallBackServiceService;
import com.ziroom.minsu.valenum.finance.PayVouchersResCodeEnum;

/**
 * <p>财务回调</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author liyingjie on 2016年5月4日
 * @since 1.0
 * @version 1.0
 */
@RequestMapping("/finance")
@Controller
public class FinanceCallBackController {

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(FinanceCallBackController.class);

	@Resource(name = "order.financeCallBackServiceService")
	private FinanceCallBackServiceService financeCallBackServiceImpl;

	/**
	 * 财务回调
	 *
	 * @author liyingjie 
	 * @created 2016年5月4日 
	 * @param request
	 * @return
	 */
	@RequestMapping("${NO_LGIN_AUTH}/financeCallBack")
	@ResponseBody
	public String financeCallBack(HttpServletRequest request) {
		Map<String, Object> resMap  = new HashMap<String,Object>(2);
		try {
			String params = request.getParameter("params");
			LogUtil.info(LOGGER, "financeCallBack params：{}", params);
			if(Check.NuNStr(params)){
                resMap.put("result", ApiConst.OPERATION_FAILURE);
				resMap.put("code", PayVouchersResCodeEnum.common100.getCode());
				resMap.put("meg",PayVouchersResCodeEnum.common100.getName());
				return JsonEntityTransform.Object2Json(resMap);
			}
			//转化为对象
			PayVouchersCallBackApiRequest pca = JsonEntityTransform.json2Object(params, PayVouchersCallBackApiRequest.class);
			//设置为请求对象
			PayVouchersCallBackRequest pc = new PayVouchersCallBackRequest();
			pc.setBusId(pca.getBusId());
			pc.setPayFlag(pca.getPayFlag());
			pc.setReason(pca.getReason());
			//判断日期不为空
			if(!Check.NuNStr(pca.getPayTime())){
				pc.setPayTime(DateUtil.parseDate(pca.getPayTime(), "yyyy-MM-dd HH:mm:ss"));	
			}
			String resultJson = financeCallBackServiceImpl.sendPayVouchersCallBack(JsonEntityTransform.Object2Json(pc));
			LogUtil.info(LOGGER, "financeCallBack resultJson：{}", resultJson);
			
			DataTransferObject strategyDto  = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(strategyDto.getCode() == ApiConst.OPERATION_SUCCESS){
				resMap = strategyDto.getData();
			}else{
				resMap.put("result", strategyDto.getCode());
				resMap.put("code", strategyDto.getMsg());
				resMap.put("meg", PayVouchersResCodeEnum.getByCode(Integer.valueOf(strategyDto.getMsg())).getName());
			}
			return JsonEntityTransform.Object2Json(resMap);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "financeCallBack is error, e={}",e);
			resMap.put("result", ApiConst.OPERATION_SUCCESS);
			resMap.put("meg", "服务器内部异常");
			return JsonEntityTransform.Object2Json(resMap);
		}
		
	}
	
	
}
