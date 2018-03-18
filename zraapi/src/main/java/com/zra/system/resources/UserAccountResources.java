package com.zra.system.resources;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.system.logic.UserAccountLogic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 用户资源类
 * @author tianxf9
 *
 */
@Component
@Path("/userAccount")
@Api(value="/userAccount")
public class UserAccountResources {
	
	@Autowired
	private UserAccountLogic logic;
	
	/**
	 * 根据用户获取该用户的角色
	 * @author tianxf9
	 * @param userId
	 * @return
	 */
	@GET
	@Path("/getRoles/v1")
	@Produces("application/json")
	@ApiOperation(value="根据用户查询用户的角色",httpMethod = "GET")
	public Map<String,Boolean> getUserRoles(@ApiParam(name = "userId",value = "用户id")@QueryParam(value = "userId")String userId) {
		return this.logic.getUserRoles(userId);
	}

}
