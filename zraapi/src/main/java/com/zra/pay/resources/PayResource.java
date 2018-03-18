package com.zra.pay.resources;

import com.zra.common.utils.PropUtils;
import com.zra.pay.entity.CallbackOfPaymentPlatformDto;
import com.zra.pay.entity.ThirdParam;
import com.zra.pay.logic.PayLogic;
import com.zra.pay.utils.CryptAES;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuigh6 on 2016/12/30.
 */
@Component
@Path("/pay")
@Api("/pay")
public class PayResource {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(PayResource.class);

    @Autowired
    private PayLogic payLogic;

    @POST
    @Path("/callback/v1")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    @ApiOperation(value = "支付平台回调", notes = "支付平台回调接口")
    public Response callbackOfPaymentPlatform(@FormParam("encryption") String encryption) {
        Map<String, String> map = new HashMap();
        try {
            LOGGER.info("[支付平台回调]原始参数:" + encryption);
            String returnContent = CryptAES.AES_Decrypt(PropUtils.getString(ThirdParam.PAY_AES_KEY), encryption);
            LOGGER.info("[支付平台回调]原始参数解密结果:" + returnContent);
            CallbackOfPaymentPlatformDto callbackOfPaymentPlatformDto = new ObjectMapper()
                    .configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false)// 解析未知参数
                    .readValue(returnContent, CallbackOfPaymentPlatformDto.class);
            this.payLogic.callbackOfPayPlatform(callbackOfPaymentPlatformDto);
        } catch (Exception e) {
            LOGGER.error("#支付平台回调#-#异常#", e);
        }
        map.put("status", "success");
        return Response.ok().entity(map).build();
    }
}
