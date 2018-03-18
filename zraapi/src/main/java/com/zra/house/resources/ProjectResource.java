package com.zra.house.resources;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.common.dto.house.HouseTypeDto;
import com.zra.common.dto.house.ProjectDto;
import com.zra.common.dto.marketing.MkProjectDto;
import com.zra.house.logic.ProjectLogic;
import com.zra.house.logic.RoomInfoLogic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Author: wangxm113
 * CreateDate: 2016/7/29.
 */
@Component
@Path("/project")
@Api(value="/project")
public class ProjectResource {
	
	@Autowired
	private ProjectLogic projectLogic;
	@Autowired
	private RoomInfoLogic roomInfoLogic;
	/**
	 * wangws21 根据用户获取项目列表
	 * @param userId 用户id
	 * @param cityId 城市id
	 * @return 返回List<ProjectDto>
	 */
	@POST
	@Path("/{userId}/users/{cityId}/cities/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "获取当前用户有权限的项目", notes = "获取当前用户有权限的项目", response = Response.class)
	public List<ProjectDto> getProjectListByUser(@Valid @NotNull @PathParam("userId") @ApiParam(name = "userId", value = "用户ID")String userId,
			@Valid @NotNull @PathParam("cityId") @ApiParam(name = "cityId", value = "城市ID") String cityId ){
		
		List<ProjectDto> projectList = projectLogic.getProjectListByUser(userId, cityId);
		return projectList;
	}
	
	/**
	 * wangws21   获取项目下的房型
	 * @param projectId 项目id
	 * @return 返回List<ProjectDto>
	 */
	@GET
	@Path("/{projectId}/housetypes/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "获取项目下的房型", notes = "获取项目下的房型", response = Response.class)
	public List<HouseTypeDto> getHouseTypeByProjectId(@Valid @NotNull @PathParam("projectId") @ApiParam(name = "projectId", value = "项目ID")String projectId){
		
		List<HouseTypeDto> houseTypeList = projectLogic.getHouseTypeByProjectId(projectId);
		return houseTypeList;
	}
	
	/**
	 * wangws21   获取项目信息 2016-8-5
	 * @param projectId 项目id
	 * @return ProjectDto
	 */
	@GET
	@Path("/{projectId}/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "获取项目信息", notes = "获取项目信息", response = ProjectDto.class)
	public ProjectDto getProjectById(@Valid @NotNull @PathParam("projectId") @ApiParam(name = "projectId", value = "项目ID")String projectId){
		
		return this.projectLogic.getProjectById(projectId);
	}
	/**
	* @Author lixn49
	* @Date 2016/11/17 15:18
	* @Description 定时任务启动
	*/
	@GET
	@Path("/check/v1")
	@ApiOperation(value = "定时任务启动", notes = "定时任务启动")
	public String bootTast(){
		roomInfoLogic.modifyPreStatus();
        return "success";
    }
	
	/**
	 * 根据城市获id获取项目
	 * @author tianxf9
	 * @param cityId
	 * @return
	 */
	@GET
	@Path("/getProjectsByCityId/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "获取项目信息根据城市id", notes = "获取项目信息根据城市id", response = ProjectDto.class)
	public List<MkProjectDto> getProjectsByCityId(@ApiParam(name = "cityId", value = "城市id")@QueryParam(value = "cityId")String cityId) {
		return this.projectLogic.getProjectsByCityId(cityId);
	}
}
