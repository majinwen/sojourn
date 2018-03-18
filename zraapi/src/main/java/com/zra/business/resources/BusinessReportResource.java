package com.zra.business.resources;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.business.logic.BusinessReportLogic;
import com.zra.common.dto.business.BoProjectReportShowDto;
import com.zra.common.dto.business.BoReportQueryParamDto;
import com.zra.common.dto.business.BoZoReportShowDto;
import com.zra.common.enums.ErrorEnum;
import com.zra.common.error.ResultException;
import com.zra.common.utils.StrUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author tianxf9
 * 商机报表接口
 */
@Component
@Path("/businessReport")
@Api(value="/businessReport")
public class BusinessReportResource {
	
	@Autowired 
	private BusinessReportLogic businessReportLogic;
	
	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(BusinessReportResource.class);
	
	/**
	 * 管家商机统计报表
	 * @author wangws21 2016-8-18
	 * @param reportQueryParamDto 查询参数实体
	 * @return List<BoZoReportShowDto>
	 */
	@POST
	@Path("zoBusinessReport/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "获取管家商机统计报表", notes = "获取管家商机统计报表", response = Map.class)
	public List<BoZoReportShowDto> getZoBusinessReportList(BoReportQueryParamDto reportQueryParamDto) {
		
		if(reportQueryParamDto==null || StrUtils.isNullOrBlank(reportQueryParamDto.getProjectIdStr())){
			throw new ResultException(ErrorEnum.MSG_BUSINESS_REPORT_PROJECT_NOT_NULL); 
		}
		
		return this.businessReportLogic.getZoBusinessReportList(reportQueryParamDto);
	}
	
	@POST
	@Path("list/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "获取项目商机信息列表", notes = "获取项目商机信息列表", response = List.class)
	public List<BoProjectReportShowDto> getProjectBusinessReportList(BoReportQueryParamDto reportQueryParamDto) {
		
		return this.businessReportLogic.getProjectReportShowDtos(reportQueryParamDto);
	}
}
