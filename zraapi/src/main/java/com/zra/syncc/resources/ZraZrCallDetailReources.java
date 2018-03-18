package com.zra.syncc.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.syncc.logic.ZraZrCallDetailLogic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author tianxf9
 *
 */
@Component
@Path("/callDetail")
@Api(value="/callDetail")
public class ZraZrCallDetailReources {
	
	@Autowired
	private ZraZrCallDetailLogic logic;
	
	
	/**
	 * 从400获取自如寓来电详情
	 * @author tianxf9
	 * @return
	 */
	@GET
	@Path("/getentity")
	@Produces("application/json")
	@ApiOperation(value="从400获取自如寓来电详情",httpMethod = "GET")
	public int synBusinessFromCC(@ApiParam("yyyy-MM-dd HH:mm:ss")@QueryParam("startDate")String startDate,@ApiParam("yyyy-MM-dd HH:mm:ss")@QueryParam("endDate")String endDate) {
	       return this.logic.synBusinessFromCC(startDate,endDate);	
	}
	
	/**
	 * 根据日期同步通话详情的拨号结果
	 * @author tianxf9
	 * @param dateStr
	 * @return
	 */
	@GET
	@Path("/updateDialResult")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "根据日期更新通话详情的通话拨号结果", notes = "根据日期更新通话详情的通话拨号结果", response = Response.class)
	public Response sycDialResult(@ApiParam("yyyy-MM-dd HH:mm:ss")@QueryParam("startDate")String startDate,@ApiParam("yyyy-MM-dd HH:mm:ss")@QueryParam("endDate")String endDate) {
		this.logic.sycDialResult(startDate,endDate);
		return Response.ok().build();
	}

}
