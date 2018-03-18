package com.zrp.finance;

import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.service.trading.api.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>提供给财务的接口</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年10月25日 18:25
 * @since 1.0
 */
@Component
@Path("/finance")
@Api(value = "finance",description = "财务接口")
public class FinanceResource {
	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(FinanceResource.class);

	@Resource(name = "trading.paymentService")
	private PaymentService paymentService;

	/**
	 * 支付验证接口(财务调用)
	 * @param billNums 账单号 多个逗号隔开
	 * @return json
	 * @author cuigh6
	 * @Date 2017年10月
	 */
	@POST
	@Path("/pay/valid/v1")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = "支付验证接口", notes =  "<br/> billNums(String)-账单编号", response = Response.class)
	public Response payValid(@FormParam("billNums") String billNums) {
		LogUtil.info(LOGGER, "【payValid】billNums：{}", billNums);
		Map<String, Object> resultData = new HashMap();
		try{
			Map<String, Object> map = new HashMap<>();
			map.put("billNums", billNums);
			String result = paymentService.validPayForFinance(JsonEntityTransform.Object2Json(map));
			LogUtil.info(LOGGER, "【validPayForFinance】结果：{}", result);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(result);
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				resultData.put("error_message", "成功");
				resultData.put("error_code", DataTransferObject.SUCCESS);
				resultData.put("status", "success");
				resultData.put("data", new ArrayList<>());
			}else {
				resultData.put("error_message", dto.getMsg());
				resultData.put("error_code", 502305);
				resultData.put("status", "failure");
				resultData.put("data", new ArrayList<>());
			}
		}catch(Exception e){
			LogUtil.error(LOGGER, "【payValid】出错!{}", e);
			resultData.put("error_message", "系统错误");
			resultData.put("error_code", 502305);
			resultData.put("status", "failure");
			resultData.put("data", new ArrayList<>());
		}
		return Response.ok().entity(resultData).build();
	}
}
