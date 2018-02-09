package com.ziroom.minsu.web.im.order.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.utils.CloseableHttpsUtil;
import com.ziroom.minsu.web.im.common.dto.ResponseDto;
import com.ziroom.minsu.web.im.common.utils.DtoToResponseDto;

/**
 * 
 * <p>im与订单相关</p>
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
@RequestMapping("imOrder")
public class ImOrderController {

	/**
	 * 日志对象
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(ImOrderController.class);

	@Value("#{'${LAN_BOOK_ORDER_URL}'.trim()}")
	private String LAN_BOOK_ORDER_URL;


	/**
	 * 
	 * 房东端 申请预定页
	 *
	 * @author yd
	 * @created 2017年3月28日 下午5:45:19
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/getNeedPay")
	@ResponseBody
	public void getNeedPay(HttpServletRequest request,HttpServletResponse response){


		ResponseDto responseDto = null;
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String fid =  request.getParameter("fid");
		String rentWay =  request.getParameter("rentWay");
		String uid =  request.getParameter("uid");
		
		
		try {
			Map<String, String> param = new HashMap<String, String>();

			param.put("fid", fid);
			param.put("rentWay",rentWay);
			param.put("uid", uid);
			if(!Check.NuNStr(startTime)){
				param.put("startTime", startTime);
			}
			if(!Check.NuNStr(endTime)){
				param.put("endTime",endTime);
			}


			String result = CloseableHttpsUtil.sendFormPost(LAN_BOOK_ORDER_URL, param);
			if(Check.NuNStr(result)){
				LogUtil.error(LOGGER, "【IM:getNeedPay】 房东端 申请预定页异常:LAN_BOOK_ORDER_URL={},param={}",LAN_BOOK_ORDER_URL, JsonEntityTransform.Object2Json(param));
				responseDto = new ResponseDto();
				responseDto.setStatus(ResponseDto.FAILED);
				responseDto.setMessage("请求异常");
				DtoToResponseDto.getResponseMsg(response, responseDto);
				return ;
			}

			responseDto = JsonEntityTransform.json2Object(result, ResponseDto.class);
			if(!Check.NuNStr(responseDto.getStatus())&&responseDto.getStatus().equals(DataTransferObject.ERROR)
					||Check.NuNObj(responseDto.getData())){
				LogUtil.error(LOGGER, "【IM:getNeedPay】接口请求错误:LAN_BOOK_ORDER_URL={},param={},msg={},data={}",LAN_BOOK_ORDER_URL, JsonEntityTransform.Object2Json(param),responseDto.getMessage(),responseDto.getData());
				DtoToResponseDto.getResponseMsg(response, responseDto);
				return ;
			}

			DtoToResponseDto.getResponseMsg(response, responseDto);
		} catch (BusinessException e) {
			responseDto = new ResponseDto();
			responseDto.setStatus(ResponseDto.FAILED);
			responseDto.setMessage("服务异常");
			LogUtil.error(LOGGER, "服务异常e={}", e);
		} catch (IOException e) {
			responseDto = new ResponseDto();
			responseDto.setStatus(ResponseDto.FAILED);
			responseDto.setMessage("服务异常");
			LogUtil.error(LOGGER, "服务异常e={}", e);
		}

	}
}
