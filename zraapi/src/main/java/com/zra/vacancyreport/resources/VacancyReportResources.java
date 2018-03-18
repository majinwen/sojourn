package com.zra.vacancyreport.resources;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.vacancyreport.logic.VacancyReportLogic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 空置报表
 * @author tianxf9
 *
 */
@Component
@Path("/vacancyreport")
@Api(value="/vacancyreport")
public class VacancyReportResources {
	
	private static final Logger LOGGER = Logger.getLogger(VacancyReportResources.class);
	
	@Autowired
	public VacancyReportLogic reportLogic;
	
	/**
	 * 获取空置报表明细数据
	 * @author tianxf9
	 * @param date
	 * @param isSave
	 * @return
	 */
	
	@GET
	@Path("/get/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "获取报表明细数据", notes = "获取空置报表明细数据", response = Response.class)
	public Map<String,Object> getVacancyReport(@QueryParam("date")@ApiParam(name="date",value="日期(yyyy-MM-dd)")String date,@QueryParam("projectId")@ApiParam(name="projectId",value="项目Id(所有：ALL)")String projectId,@QueryParam("isSave")@ApiParam(name="isSave",value="是否保存数据库（Y/N）")String isSave) {
		
		if(isSave.equals("Y")) {
			LOGGER.info("重新保存数据之前删除原来数据");
			this.reportLogic.delReportEmptyByRecordDate(date, projectId);
			LOGGER.info("重新保存数据");
			return this.reportLogic.getVacancyReportEntitys(date, projectId, true);
		}else {
			return this.reportLogic.getVacancyReportEntitys(date, projectId, false);
		}
	}

	@GET
	@Path("/get/history/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "获取历史报表明细数据", notes = "获取历史空置报表明细数据", response = Response.class)
	public Map<String, Object> getHistoryVacancyReport (@QueryParam("date")@ApiParam(name="date",value="日期(yyyy-MM-dd)")String date,@QueryParam("projectId")@ApiParam(name="projectId",value="项目Id(所有：ALL)")String projectId) {

		return this.reportLogic.getHistoryVacancyReport(projectId, date);
	}

}
