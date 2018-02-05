package com.ziroom.minsu.api.order.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.*;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.constant.ApiConst;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.order.dto.PayVouchersCallBackApiRequest;
import com.ziroom.minsu.api.order.entity.OrderDetailCaiwuVo;
import com.ziroom.minsu.entity.order.OrderLogEntity;
import com.ziroom.minsu.entity.order.OrderPayEntity;
import com.ziroom.minsu.entity.order.UsualContactEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.finance.dto.PayVouchersCallBackRequest;
import com.ziroom.minsu.services.order.api.inner.FinanceCallBackServiceService;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.entity.OrderDetailVo;
import com.ziroom.minsu.valenum.finance.PayVouchersResCodeEnum;
import com.ziroom.minsu.valenum.house.OrderTypeEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;
import com.ziroom.minsu.valenum.order.PaySourceTypeEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>财务回调</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author afi on 2016年11月4日
 * @since 1.0
 * @version 1.0
 */
@RequestMapping("/orderCom")
@Controller
public class CommonOrderController {

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(CommonOrderController.class);

	@Resource(name = "order.orderCommonService")
	private OrderCommonService orderCommonService;

	@Resource(name="basedata.confCityService")
	private ConfCityService confCityService;

	/**
	 * 订单详情 为财务
	 * @author afi
	 * @created 2016年11月22日
	 * @param request
	 * @return
	 */
	@RequestMapping("${UNLOGIN_AUTH}/detail")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> detail(HttpServletRequest request,String orderSn) {
		try {
			if (Check.NuNStr(orderSn)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数异常"),HttpStatus.OK);
			}
			DataTransferObject dto  = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.getOrderAllBySn(orderSn));
			if(dto.getCode() == DataTransferObject.SUCCESS){
				OrderDetailVo order = dto.parseData("orderDetailVo", new TypeReference<OrderDetailVo>() {});
				OrderPayEntity pay = dto.parseData("pay", new TypeReference<OrderPayEntity>() {});
				OrderDetailCaiwuVo detailCaiwuVo = trans2CaiwuVo(order,pay);
				if (Check.NuNObj(detailCaiwuVo)){
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("当前订单不存在"),HttpStatus.OK);
				}
				LogUtil.info(LOGGER,JsonEntityTransform.Object2Json(detailCaiwuVo));
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(detailCaiwuVo),HttpStatus.OK);
			}else {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()),HttpStatus.OK);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "detail is error, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}

	/**
	 * 将当前的订单信息转成财务的信息
	 * @param detail
	 * @return
	 */
	private OrderDetailCaiwuVo trans2CaiwuVo(OrderDetailVo detail,OrderPayEntity pay){
		OrderDetailCaiwuVo vo = new OrderDetailCaiwuVo();
		if (Check.NuNObj(detail)){
			return null;
		}
		String nationJson=confCityService.getCityNameByCode(detail.getCityCode());
		String name = SOAResParseUtil.getStrFromDataByKey(nationJson, "cityName");
		vo.setCityCode(detail.getCityCode());
		vo.setCityName(name);
		vo.setOrderSn(detail.getOrderSn());
		vo.setUserUid(detail.getUserUid());
		vo.setUserTel(detail.getUserTel());
		OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusByCode(detail.getOrderStatus());
		vo.setOrderStatus(orderStatusEnum.getOrderStatus());
		vo.setOrderStatusName(orderStatusEnum.getStatusName());
		vo.setPayStatus(detail.getPayStatus());

		OrderTypeEnum  orderTypeEnum = OrderTypeEnum.getOrderTypeByCode(detail.getOrderType());
		if (!Check.NuNObj(orderTypeEnum)){
			vo.setOrderType(orderTypeEnum.getCode());
			vo.setOrderTypeName(orderTypeEnum.getName());
		}

		if (!Check.NuNObj(pay)){
			vo.setPayType(pay.getPayType());
		}
		vo.setAllMoney(BigDecimalUtil.div(detail.getNeedPay()+detail.getCouponMoney(),100));
		vo.setPayMoney(BigDecimalUtil.div(detail.getPayMoney(),100));
		vo.setCouponMoney(BigDecimalUtil.div(detail.getCouponMoney(),100));
		return vo;
	}
}
