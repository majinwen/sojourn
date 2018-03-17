
package com.ziroom.minsu.mapp.app.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.mapp.common.util.CustomerVoUtils;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.dto.OrderRequest;

/**
 * <p>app视图控制层</p>
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
@RequestMapping("/app")
public class AppController {
	
	@Resource(name = "order.orderCommonService")
	private OrderCommonService orderCommonService; 
	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(AppController.class);
	
	/**
	 * 
	 * 客户端请求失败
	 *
	 * @author yd
	 * @created 2016年5月25日 下午9:07:55
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/appError")
	public String appError(HttpServletRequest request){
		LogUtil.info(LOGGER, "客户端请求失败");
		return "/error/appError";
	}

	/**
	 * 跳转错误页
	 * @param request
	 * @return
	 */
	@RequestMapping("/error")
	public String error(HttpServletRequest request){
		LogUtil.info(LOGGER, "客户端请求失败");
		return "/error/error";
	}
	
	/**
	 * 
	 * app直接切换房东角色
	 *
	 * @author jixd
	 * @created 2016年5月29日 下午6:49:14
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/toLandlordRole")
	public String changeToLandlordRole(HttpServletRequest request){
		CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
		String uid = customerVo.getUid();
		
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setLandlordUid(uid);
		orderRequest.setRequestType(2);
		orderRequest.setPage(1);
		orderRequest.setLimit(1);
		
		String orderList = orderCommonService.getOrderListByCondiction(JsonEntityTransform.Object2Json(orderRequest));
		DataTransferObject orderDto = JsonEntityTransform.json2DataTransferObject(orderList);
		if(orderDto.getCode() == DataTransferObject.SUCCESS){
			int size = (int) orderDto.getData().get("size");
			//如果存在订单则跳转到订单页，否则调转到房源页
			if(size > 0){
				return "redirect:/orderland/43e881/showlist";
			}else{
				return "redirect:/houseMgt/43e881/myHouses";
			}
		}
		return "redirect:/houseMgt/43e881/myHouses";
	}
	
}
