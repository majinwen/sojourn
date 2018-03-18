package com.zra.report.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.house.entity.dto.PageDto;
import com.zra.report.entity.dto.BaseResponse;
import com.zra.report.entity.dto.ReportDto;
import com.zra.report.logic.ReportLogic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author huangy168@ziroom.com
 * @Date 2016年10月29日
 * @Time 下午4:26:49
 */
@Component
@Path("/report")
@Api(value="/report")
public class ReportResource {
	
	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(ReportResource.class);
	
	@Autowired
	private ReportLogic reportLogic;
	
	/**
	 * 获取报表详情
	 * @param projectId
	 * @param beginTime
	 * @param endTime
	 * @param timeType
	 * @param type
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@GET
	@Path("/{projectId}/details")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "获取运营报表详情", notes = "获取运营报表详情", response = Response.class)
	public BaseResponse<List<ReportDto>> getReportDetailList(@PathParam("projectId") @ApiParam(name = "projectId", value = "项目id") String projectId, 
			@QueryParam("beginTime") @ApiParam(name = "beginTime", value = "开始时间") String beginTime, 
			@QueryParam("endTime") @ApiParam(name = "endTime", value = "结束时间") String endTime, 
			@QueryParam("dateType") @ApiParam(name = "dateType", value = "日期类型") Integer dateType, 
			@QueryParam("type") @ApiParam(name = "type", value = "数据类型") String type, 
			@QueryParam("pageNum") @ApiParam(name = "pageNum", value = "当前页码") Integer pageNum, 
			@QueryParam("pageSize") @ApiParam(name =  "pageSize", value = "每页大小") Integer pageSize) {
		LOGGER.info("Begin to get report details.");
		PageDto pageDto = new PageDto();
		pageDto.setPageNum(pageNum);
		pageDto.setPageSize(pageSize);
		
		return reportLogic.getReportDetailList(projectId, beginTime, endTime, dateType, type, pageDto);
	}
	
	/**
	 * 获取报表总计
	 * @param projectId
	 * @param beginTime
	 * @param endTime
	 * @param timeType
	 * @param type
	 * @return
	 */
	@GET
	@Path("/{projectId}/total")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "获取运营报表总计", notes = "获取运营报表总计", response = Response.class)
	public BaseResponse<ReportDto> getReportTotalList(@PathParam("projectId") @ApiParam(name = "projectId", value = "项目id") String projectId, 
			@QueryParam("beginTime") @ApiParam(name = "beginTime", value = "开始时间") String beginTime, 
			@QueryParam("endTime") @ApiParam(name = "endTime", value = "结束时间") String endTime, 
			@QueryParam("dateType") @ApiParam(name = "dateType", value = "日期类型") Integer dateType, 
			@QueryParam("type") @ApiParam(name = "type", value = "数据类型") String type) {
		LOGGER.info("Begin to get report details.");
		
		return reportLogic.getReportTotalList(projectId, beginTime, endTime, dateType, type);
	}
}
