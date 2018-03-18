package com.zra.zmconfig.resources;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.zmconfig.entity.CfShortUrl;
import com.zra.zmconfig.logic.ShortUrlLogic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author cuiyh9
 * 短链服务URL
 */
@Component
@Path("/shorturl")
@Api(value="/shorturl")
public class ShortUrlResource {

	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(ShortUrlResource.class);
	
	@Autowired
	private ShortUrlLogic shortUrlLogic;
	
	@POST
    @Path("/shorturl/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "生成短链", httpMethod = "POST", notes = "生成短链")
	public Response genShortUrlSuff(@Valid @NotNull @ApiParam(name = "longurl") @FormParam("longurl") String longUrl){
		CfShortUrl cfShortUrl = shortUrlLogic.genShortUrlSuff(longUrl);
		return Response.ok(cfShortUrl).build();
	}
	
	@GET
    @Path("/shorturl/{suid}/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "查询短链", httpMethod = "GET", notes = "查询配置列表")
	public Response getShortUrl(@Valid @NotNull @ApiParam(name = "suid") @PathParam("suid") String suid){
		CfShortUrl cfShortUrl = shortUrlLogic.getLongUrl(suid);
		return Response.ok(cfShortUrl).build();
	}
	
}
