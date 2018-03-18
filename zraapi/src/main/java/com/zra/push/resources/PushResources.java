package com.zra.push.resources;

import java.util.Date;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.common.dto.push.PushDto;
import com.zra.push.logic.PushLogic;

import io.swagger.annotations.Api;

/**
 * 给app推送消息模块.
 * @author cuiyuhui
 */
@Component
@Path("/push")
@Api(value = "/push")
public class PushResources {

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(PushResources.class);

    /**
     * 推送业务逻辑类.
     * */
    @Autowired
    private PushLogic pushLogic;

    /**
     * @return Response
     */
    @POST
    @Path("/v1")
    public final Response pushMessage() {
        LOGGER.info("");
        String pushTitle = "自如客";
        String pushContent = "自如客您下午好";
        String systemId = "1001";
        String funcFlag = "test";
        String uid = "8fdc471a-536c-6ab6-a334-1f8f16fc690f";
        String openUrl = "http://m.ziroomapartment.com/zra_mst/BJ/index";
        PushDto pushDto = new PushDto(systemId, funcFlag, pushTitle, pushContent, openUrl, uid);
        pushLogic.push(pushDto);
        return Response.ok().build();
    }

}
