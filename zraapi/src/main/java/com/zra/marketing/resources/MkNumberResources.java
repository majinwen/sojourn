package com.zra.marketing.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.house.entity.dto.ProjectTelDto;
import com.zra.marketing.logic.MkNumberLogic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Component
@Path("/mkNumber")
@Api("/mkNumber")
public class MkNumberResources {
	
	@Autowired
	private MkNumberLogic numLogic;
	
	
	/**
	 * 根据项目id获取项目渠道分机号
	 * @author tianxf9
	 * @param cityId
	 * @return
	 */
	@GET
	@Path("/getProjectNums/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "根据项目id获取项目渠道分机号", notes = "根据项目id获取项目渠道分机号", response = Response.class)
	public List<String> getNumbersByProjectId(@ApiParam(name = "projectId", value = "项目Id") @QueryParam(value = "projectId")String projectId) {
		return this.numLogic.getNumberByProId(projectId);
	}
	
	@GET
	@Path("/getAllNums/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "获取所有渠道分机号", notes = "获取所有渠道分机号", response = Response.class)
	public List<ProjectTelDto> getAllNumbers() {
		return this.numLogic.getAllNumber();
	}

}
