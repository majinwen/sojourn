package com.zra.projectZO.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.projectZO.ProjectZODto;
import com.zra.projectZO.logic.ProjectZOLogic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 管家资源类
 * @author tianxf9 2016-08-01
 *
 */
@Component
@Path("/projectzo")
@Api(value="/projectzo")
public class ProjectZOResources {
 
	@Autowired
	private ProjectZOLogic logic;
	
	/**
	 * 根据项目id获取项目下的所有管家
	 * @author tianxf9
	 * @param projectId
	 * @return
	 */
	@GET
	@Path("/list/v1")
	@Produces("application/json")
	@ApiOperation(value="根据项目id获取该项目下的所有官家",httpMethod = "GET")
	public List<ProjectZODto> getPorjctZosByProId(@ApiParam(name = "projectId", value = "项目Id") @QueryParam(value = "projectId")String projectId) {
		
		return this.logic.getProjectZOsByProId(projectId);
	}
	
	
	/**
	 * 根据用户id和城市id获取所有管家
	 * @author tianxf9
	 * @return
	 */
	@GET
	@Path("/allList/v1")
	@Produces("application/json")
	@ApiOperation(value="根据用户id和城市id获取所有管家信息",httpMethod = "GET")
	public List<ProjectZODto> getAllProjectZOByUser(@ApiParam(name = "userId", value = "用户id") @QueryParam(value = "userId")String userId,
			                                        @ApiParam(name = "cityId",value = "城市id") @QueryParam(value = "cityId") String cityId) {
		
		return this.logic.getAllProjectZOByUser(userId,cityId);
	}
	
}
