package com.zra.marketing.resources;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zra.common.dto.marketing.MkChannelDataDto;
import com.zra.common.dto.marketing.MkChannelDto;
import com.zra.common.dto.marketing.MkChannelLineDto;
import com.zra.common.dto.marketing.MkChannelQueryDto;
import com.zra.common.dto.marketing.MkLineChannelShowDto;
import com.zra.marketing.logic.MkChannelLogic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 统计渠道
 * 
 * @author tianxf9
 *
 */
@Component
@Path("/mkChannel")
@Api("/mkChannel")
public class MkChannelResources {

	private static final Logger LOGGER = LoggerFactory.getLogger(MkChannelLogic.class);

	@Autowired
	private MkChannelLogic channelLogic;

	/**
	 * 渠道统计报表数据
	 * @author tianxf9
	 * @param queryDto
	 * @return
	 */
	@POST
	@Path("/queryData/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "查询渠道统计数据", notes = "查询渠道统计数据", response = Response.class)
	public List<MkChannelDataDto> queryData(MkChannelQueryDto queryDto) {
		LOGGER.info("======调用api查询渠道统计数据接口：params=" + JSON.toJSONString(queryDto));
		return this.channelLogic.getMkChannelData(queryDto);
	}

	/**
	 * 保存线上渠道
	 * @author tianxf9
	 * @param channelDtos
	 * @return
	 */
	@POST
	@Path("/saveOnLine/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "线上渠道统计提交", notes = "线上渠道统计提交", response = Response.class)
	public Response saveOnLine(List<MkChannelDto> channelDtos) {
		boolean isOK = this.channelLogic.saveOnLineChannels(channelDtos);
		return Response.ok(isOK).build();
	}
	
	
	/**
	 * 保存线下渠道
	 * @author tianxf9
	 * @param channelDtos
	 * @return
	 */
	@POST
	@Path("/saveLine/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "线下渠道统计提交", notes = "线下渠道统计提交", response = Response.class)
	public Map saveLine(MkChannelLineDto channelDtos) {
		LOGGER.info("mkChannel/saveLine/v1:params="+JSON.toJSONString(channelDtos));
		return this.channelLogic.saveLineChannel(channelDtos);
	}
	
	/**
	 * 删除渠道
	 * @author tianxf9
	 * @param channelDto
	 * @return
	 */
	@POST
	@Path("/deleteChannel/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "删除渠道", notes = "删除渠道", response = Response.class)
	public Response deleteChannel(MkChannelDto channelDto) {
		boolean isOk = this.channelLogic.deleteChannelByBid(channelDto);
		return Response.ok(isOk).build();
	}
	
	
	/**
	 * 获取线上渠道列表
	 * @author tianxf9
	 * @param cityId
	 * @return
	 */
	@GET
	@Path("/getOnLineChannelList/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "获取线上渠道", notes = "获取线上渠道", response = Response.class)
	public Map getOnLineChannelList(@ApiParam(name = "cityId", value = "城市Id") @QueryParam(value = "cityId")String cityId
			,@ApiParam(name = "pageNum",value="第几页")@QueryParam(value="pageNum")int pageNum
			,@ApiParam(name="rows",value="总共的条数")@QueryParam(value="rows") int rows) {
		return this.channelLogic.getOnLineList(cityId,pageNum,rows);
	}
	
	
	/**
	 * 获取线下渠道列表
	 * @author tianxf9
	 * @param cityId
	 * @return
	 */
	@GET
	@Path("/lineChannelList/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "获取线下渠道", notes = "获取线下渠道", response = Response.class)
	public List<MkLineChannelShowDto> lineChannelList(@ApiParam(name = "cityId", value = "城市Id") @QueryParam(value = "cityId")String cityId) {
		return this.channelLogic.getLineList(cityId);
	}
	
	/**
	 * 获取线下渠道的项目，分机号列表
	 * @author tianxf9
	 * @param channelBid
	 * @param pageNum
	 * @param rows
	 * @return
	 */
	@GET
	@Path("/getNumsByChannelBid/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "获取线下渠道", notes = "获取线下渠道", response = Response.class)
	public Map getNumsByChannelBid(@ApiParam(name = "channelBid", value = "渠道bid") @QueryParam(value = "channelBid")String channelBid) {
				
		return this.channelLogic.getNumsByChannelBid(channelBid);
		
	}
	
	/**
	 * 根据渠道bid获取渠道name
	 * @author tianxf9
	 * @param channelBid
	 * @return
	 */
	@GET
	@Path("/getChannelNameByBid/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value="根据渠道bid获取渠道name",notes="根据渠道bid获取渠道name",response = Response.class)
	public String getChannelNameByBid(@ApiParam(name="channelBid",value="渠道bid")@QueryParam(value="channelBid")String channelBid) {
		return this.channelLogic.getChannelNameByBid(channelBid);
	}
	
	

	

}
