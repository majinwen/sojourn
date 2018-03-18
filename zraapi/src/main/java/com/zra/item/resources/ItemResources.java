package com.zra.item.resources;


import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.common.dto.bedInfo.ItemDto;
import com.zra.common.dto.bedInfo.QueryItemDto;
import com.zra.item.logic.ItemLogic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 物品接口
 * @author tianxf9
 *
 */
@Component
@Path("/item")
@Api(value = "/item")
public class ItemResources {
	
	@Autowired
	private ItemLogic itemLogic;
	
	/**
	 * @author tianxf9
	 * @return
	 */
	@POST
	@Path("/list/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "查询物品列表", notes = "查询物品列表", response = Response.class)
	public List<ItemDto> getAllItems(QueryItemDto queryDto) {
		return this.itemLogic.getAllItems(queryDto);
	}

}
