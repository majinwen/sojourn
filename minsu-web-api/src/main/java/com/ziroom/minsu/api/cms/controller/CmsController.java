package com.ziroom.minsu.api.cms.controller;

import com.alibaba.dubbo.common.utils.Log;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.RegExpUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.services.cms.api.inner.ActCouponService;
import com.ziroom.minsu.services.cms.dto.BindCouponMobileRequest;
import com.ziroom.minsu.services.cms.dto.MobileCouponRequest;
import com.ziroom.minsu.services.cms.dto.OutCouponRequest;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>活动相关</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author afi
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("/cms")
public class CmsController {

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(CmsController.class);

	@Resource(name = "cms.actCouponService")
	private ActCouponService actCouponService;

	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;


	/**
	 * 所有的优惠券列表
	 * @author afi
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_AUTH}/listAll")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> listAll(HttpServletRequest request){
		try {
            String paramJson=(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(logger, "checkCoupon params:{}", paramJson);
			if (Check.NuNStr(paramJson)){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数异常"),HttpStatus.OK);
            }
            Map par = JsonEntityTransform.json2Map(paramJson);
            String uid = ValueUtil.getStrValue(par.get("uid"));
            String status = ValueUtil.getStrValue(par.get("status"));
            if (Check.NuNStr(uid) || Check.NuNStr(status)){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数异常"),HttpStatus.OK);
            }

			// 查询此用户绑定的手机号，是否有未绑定到用户身上的优惠券，如果有则绑定
			String json = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(uid);
			DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(json);
			if(customerDto.getCode() == DataTransferObject.SUCCESS){
				CustomerBaseMsgEntity customer = customerDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {});
				if(!Check.NuNObj(customer) && !Check.NuNStr(customer.getCustomerMobile())){
					BindCouponMobileRequest bindCouponMobileRequest = new BindCouponMobileRequest();
					bindCouponMobileRequest.setUid(uid);
					bindCouponMobileRequest.setMobile(customer.getCustomerMobile());
					actCouponService.bindCouponByMobile(JsonEntityTransform.Object2Json(bindCouponMobileRequest));
				}
			}

            OutCouponRequest outCouponRequest = new OutCouponRequest();
            outCouponRequest.setStatus(status);
            outCouponRequest.setUid(uid);
            outCouponRequest.setLimit(50);
			String couponResultJson = actCouponService.getCouponListByUid(JsonEntityTransform.Object2Json(outCouponRequest));
			//优惠券列表
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(couponResultJson);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(logger, "checkCoupon is error, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}

	/**
	 * 手机号绑定优惠券（服务调用，手机号和订单号绑定一个优惠券）
	 * @author jixd
	 * @created 2016年12月29日 14:04:45
	 * @param
	 * @return
	 */
	@RequestMapping(value="/${NO_LGIN_AUTH}/bindCouponByPhoneAndOrder",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> bindCouponByPhoneAndOrder(HttpServletRequest r,MobileCouponRequest request){

		LogUtil.info(logger,"bindCouponByPhoneAndOrder 参数={}",JsonEntityTransform.Object2Json(request));
		if (Check.NuNStr(request.getMobile())){
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail("手机号码为空"), HttpStatus.OK);
		}
		if(!RegExpUtil.isMobilePhoneNum(request.getMobile())){
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail("手机号码格式错误"), HttpStatus.OK);
		}
		if (Check.NuNStr(request.getOrderSn())){
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail("订单号为空"), HttpStatus.OK);
		}
		if (Check.NuNStr(request.getActSn())){
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail("活动号为空"), HttpStatus.OK);
		}
		if (Check.NuNObj(request.getSourceType())){
			request.setSourceType(0);
		}

		String s = actCouponService.bindCouponByPhoneAndOrder(JsonEntityTransform.Object2Json(request));
		LogUtil.info(logger,"bindCouponByPhoneAndOrder 返回值={}",s);
		DataTransferObject redto = JsonEntityTransform.json2DataTransferObject(s);
		if (redto.getCode() == DataTransferObject.SUCCESS){
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptOK(redto.getData()), HttpStatus.OK);
		}else{
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail(redto.getMsg()), HttpStatus.OK);
		}
	}
}
