package com.zra.system.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.projectZO.ProjectZODto;
import com.zra.system.logic.EmployeeLogic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * 员工信息资源类
 * @author tianxf9
 *
 */
@Component
@Path("/employee")
@Api(value="/employee")
public class EmployeeResources {
	
	@Autowired
	private EmployeeLogic logic;
	
	
	/**
	 * 根据useid获取员工名称
	 * @author tianxf9
	 * @param userId
	 * @return
	 */
	@GET
	@Path("/v1")
	@Produces("application/json")
	@ApiOperation(value="根据用户id查询",httpMethod = "GET")
	public ProjectZODto getEmployeeByUserId(@ApiParam(name="userId",value="用户id")@QueryParam(value="userId")String userId) {
		return this.logic.getZOMsgByUserId(userId);
	}

}
