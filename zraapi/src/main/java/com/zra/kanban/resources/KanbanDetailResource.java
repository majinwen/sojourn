package com.zra.kanban.resources;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zra.common.dto.kanban.KanbanQueryDto;
import com.zra.common.dto.kanban.SecondDataDetailDto;
import com.zra.common.dto.kanban.SecondDataShowDto;
import com.zra.common.utils.DateUtil;
import com.zra.kanban.logic.KanbanDetailLogic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Component
@Path("/kanbanDetail")
@Api(value="kanbanDetail")
public class KanbanDetailResource {
	
	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(KanbanDetailResource.class);
	
	@Autowired
	private KanbanDetailLogic detailLogic;
	
	
	/**
	 * 统计今天的目标看板二级数据
	 * @author tianxf9
	 */
	@GET
	@Path("/runTask/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "统计今天目标看板的核心数据", notes = "统计今天目标看板的核心数据", response = Response.class)
	public int runTaskCreateDetail() {
		Date yDate = DateUtil.getYesterDay(new Date());
		return this.detailLogic.taskCreateDetail(yDate);
	}
	
	@POST
	@Path("/getList/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "查询目标看板的二级数据", notes = "查询目标看板的二级数据", response = Response.class)
	public List<SecondDataShowDto> getSecondDataShowDto(KanbanQueryDto queryDto) {
		LOGGER.info("调用api查询目标看板二级数据.......params="+JSON.toJSONString(queryDto));
		return this.detailLogic.getSecondData(queryDto);
	}
	
	
	@POST
	@Path("/getDetailList/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "查询目标看板的二级数据详情", notes = "查询目标看板的二级数据详情", response = Response.class)
	public List<SecondDataDetailDto> getSeconDataShowDetailDto(KanbanQueryDto queryDto) {
		LOGGER.info("调用api查询目标看板二级数据详情.......params="+JSON.toJSONString(queryDto));
		return this.detailLogic.getSeconDataDetail(queryDto);
	}
	

}
