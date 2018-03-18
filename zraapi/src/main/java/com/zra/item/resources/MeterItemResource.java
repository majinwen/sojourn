package com.zra.item.resources;

import com.alibaba.fastjson.JSON;
import com.zra.common.dto.bedInfo.SaveStandardItemParamDto;
import com.zra.common.dto.meteritem.MeterItemParamDto;
import com.zra.item.logic.MeterItemLogic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;


/**
 * Created by xiaona on 2016/9/23.
 */
/**
 * 物品合同配置接口
 * @author xiaona
 *
 */
@Component
@Path("/meterItem")
@Api(value = "/meterItem")
public class MeterItemResource {
	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(MeterItemResource.class);

	@Autowired
	private MeterItemLogic meterItemLogic;
	/**
	 * 保存物品配置
	 * @author tianxf9
	 * @param paramDto
	 * @return
	 */
	@POST
	@Path("/saveByID/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "存储物品配置信息", notes = "存储物品配置信息", response = Map.class)
	public Response saveBedStandardItemsById(MeterItemParamDto paramDto) {
		LOGGER.info(JSON.toJSONString(paramDto));
		boolean isOK = this.meterItemLogic.saveItemConfigById(paramDto);
		return Response.ok(isOK).build();
	}
}
