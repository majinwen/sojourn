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
import com.zra.common.dto.kanban.SummaryShowDto;
import com.zra.common.utils.DateUtil;
import com.zra.kanban.logic.KanbanSummaryLogic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 目标看板核心数据
 * @author tianxf9
 *
 */
@Component
@Path("/kanbanSummary")
@Api(value="kanbanSummary")
public class KanbanSummaryResource {
	
	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(KanbanSummaryResource.class);
	
	@Autowired
	private KanbanSummaryLogic summaryLogic;
	
	/**
	 * 统计今天的目标看板的核心数据
	 * @author tianxf9
	 */
	@GET
	@Path("/reckon/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "统计今天目标看板的核心数据", notes = "统计今天目标看板的核心数据", response = Response.class)
	public int reckonKanSummaryData() {
		Date yDate = DateUtil.getYesterDay(new Date());
		return this.summaryLogic.taskKanbanSummary(yDate);
	}
	
	/**
	 * 查询目标看板核心数据
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param cycleTyep
	 * @return
	 */
	@POST
	@Path("/getList/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "查询目标看板的核心数据", notes = "查询目标看板的核心数据", response = Response.class)
	public List<SummaryShowDto> getSummaryShowDtos(KanbanQueryDto queryDto) {
		LOGGER.info("调用api查询目标看板核心数据接口......params="+JSON.toJSONString(queryDto));
		return this.summaryLogic.getSummaryShowDtos(queryDto);
	}

}
