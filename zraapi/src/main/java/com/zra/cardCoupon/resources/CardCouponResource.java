package com.zra.cardCoupon.resources;

import com.alibaba.fastjson.JSON;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.zra.cardCoupon.logic.CardCouponLogic;
import com.zra.common.dto.pay.*;
import com.zra.common.utils.SysConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

/**
 * Created by cuigh6 on 2017/5/23.
 */
@Controller
@Path("/cardCoupon")
@Api("cardCoupon")
public class CardCouponResource {

	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(CardCouponResource.class);
	@Autowired
	private CardCouponLogic cardCouponLogic;

	@Path("/couponRecovery/ms/v1")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "恢复优惠券", notes = "恢复优惠券", response = Response.class)
	public Response couponRecovery(List<String> codes) {
		
		if(CollectionUtils.isEmpty(codes)) {
			return Response.ok(false).build();
		}
		LOGGER.info("/couponRecovery/ms/v1：params="+JSON.toJSONString(codes));
		boolean result = true;
		for(String code:codes) {
			result = result && this.cardCouponLogic.couponRecovery(code);
		}
		return Response.ok(result).build();
	}


	@Path("/cardRecovery/ms/v1")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "恢复租金卡", notes = "恢复租金卡", response = Response.class)
	public Response cardRecovery(List<String> codes) {
		
		if(CollectionUtils.isEmpty(codes)) {
			return Response.ok(false).build();
		}
		LOGGER.info("/cardRecovery/ms/v1：params="+JSON.toJSONString(codes));
		boolean result = true;
		for(String code:codes) {
			result = result && this.cardCouponLogic.cardRecovery(code);
		}
		return Response.ok(result).build();
	}


	@Path("/couponQuery/v1")
	@GET
	@Produces("application/json")
	@ApiOperation(value = "优惠券查询")
	public List<CardCouponDto> couponQuery(@QueryParam("uid") String uid) {
		return this.cardCouponLogic.couponQuery(uid, SysConstant.CARD_COUPON_STATUS);
	}
	
	
	@Path("/couponQuery/ms/v1")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "查询优惠券", notes = "查询优惠券", response = Response.class)
	public List<CardCouponDto> couponQueryForMs(CardCouponParamsDto params) {
		LOGGER.info("/couponQuery/ms/v1：params="+JSON.toJSONString(params));
		return this.cardCouponLogic.couponQueryForMs(params);
	}

	@Path("/couponCheck/ms/v1")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "校验优惠券", notes = "校验优惠券", response = Response.class)
	public Map<String,Object> couponCheck(CardCouponParamsDto params){
		LOGGER.info("校验优惠券接口：params:"+JSON.toJSONString(params));
		return this.cardCouponLogic.couponCheckForMs(params);
	}

	@Path("/couponUse/ms/v1")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "优惠券使用", notes = "优惠券使用", response = Response.class)
	public Map<String,Object> couponUse(CardCouponParamsDto params){
		LOGGER.info("使用优惠券参数：params="+JSON.toJSONString(params));
		return this.cardCouponLogic.couponUseForMs(params);
	}

	@Path("/couponBind/v1")
	@GET
	@Produces("application/json")
	@ApiOperation(value = "兑换优惠券")
	public CardCouponDto couponBind(@QueryParam("couponCode") String couponCode, @QueryParam("uid") String uid) throws Exception {
		return this.cardCouponLogic.couponBind(couponCode, uid);
	}

	@Path("/cardQuery/v1")
	@GET
	@Produces("application/json")
	@ApiOperation(value = "租金卡查询")
	public List<CardCouponDto> cardQuery(@QueryParam("uid") String uid) {
		return this.cardCouponLogic.cardQuery(uid, SysConstant.CARD_COUPON_STATUS);
	}
	
	@Path("/cardQuery/ms/v1")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "查询租金卡", notes = "查询租金卡", response = Response.class)
	public List<CardCouponDto> cardQueryForMs(CardCouponParamsDto params) {
		LOGGER.info("/cardQuery/ms/v1：params="+JSON.toJSONString(params));
		return this.cardCouponLogic.cardQueryForMs(params);
	}

	/**
	  * @description: 查询员工租金卡及卡券新方法
	  *               wiki:http://wiki.ziroom.com/pages/viewpage.action?pageId=325812265
	  * @author: lusp
	  * @date: 2017/12/23 下午 16:18
	  * @params:
	  * @return:
	  */
	@Path("/cardQueryNew/ms/v1")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "查询租金卡及卡券新方法", notes = "查询租金卡及卡券新方法", response = Response.class)
	public CardCouponQueryResponse cardQueryForMsNew(CardCouponQueryRequest params) {
		LOGGER.info("/cardQueryNew/ms/v1：params="+JSON.toJSONString(params));
		return this.cardCouponLogic.cardQueryForMsNew(params);
	}

	/**
	 * @description: 消费员工租金卡及卡券新方法
	 *               wiki:http://wiki.ziroom.com/pages/viewpage.action?pageId=325812267
	 * @author: lusp
	 * @date: 2017/12/24 下午 23:18
	 * @params:
	 * @return:
	 */
	@Path("/cardUseNew/ms/v1")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "消费租金卡及卡券新方法", notes = "消费租金卡及卡券新方法", response = Response.class)
	public CardCouponUseResponse cardUseForMsNew(CardCouponUseRequest params) {
		LOGGER.info("/cardQueryNew/ms/v1：params="+JSON.toJSONString(params));
		return this.cardCouponLogic.cardUseForMsNew(params);
	}

	@Path("/cardCheck/ms/v1")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "校验租金卡", notes = "校验租金卡", response = Response.class)
	public Map<String,Object> cardCheck(CardCouponParamsDto params){
        LOGGER.info("校验租金卡参数：params"+JSON.toJSONString(params));
		return this.cardCouponLogic.cardCheckForMs(params);
	}

	@Path("/cardUse/ms/v1")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "租金卡使用", notes = "租金卡使用", response = Response.class)
	public Map<String,Object> cardUse(CardCouponParamsDto params){
        LOGGER.info("使用租金卡参数：params="+JSON.toJSONString(params));
		return this.cardCouponLogic.cardUseForMs(params);
	}
}
