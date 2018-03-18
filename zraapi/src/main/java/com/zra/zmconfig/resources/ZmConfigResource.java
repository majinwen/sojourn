package com.zra.zmconfig.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.zmconfig.ZmConfigDto;
import com.zra.zmconfig.entity.CfZmConfig;
import com.zra.zmconfig.logic.ZmConfigLogic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author cuiyh9
 * 配置服务Resource
 */
@Component
@Path("/zmconfig")
@Api(value="/zmconfig")
public class ZmConfigResource {

	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(ZmConfigResource.class);
	
	
	@Autowired
	private  ZmConfigLogic zmConfigLogic;
	
	
	
	@GET
    @Path("/list/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "查询配置列表", httpMethod = "GET", notes = "查询配置列表")
    public List<ZmConfigDto> getList(@Valid @ApiParam(name = "systemId") @QueryParam("systemId") String systemId,
    		@Valid @ApiParam(name = "cfKey") @QueryParam("cfKey") String cfKey) {
		
		List<ZmConfigDto> zmConfigDtoList = new ArrayList<ZmConfigDto>();
        try {
        	
        	CfZmConfig qConfig = new CfZmConfig();
        	qConfig.setCfKey(cfKey);
        	qConfig.setSystemId(systemId);
        	
        	List<CfZmConfig> zmConfigList = zmConfigLogic.queryAll(qConfig);
        	if(zmConfigList !=null && zmConfigList.size() >0){
        		for(CfZmConfig config: zmConfigList){
        			zmConfigDtoList.add( new ZmConfigDto(config.getSystemId(), config.getCfKey(),config.getCfValue(), config.getCfDesc(),config.getCreateTime()));
        		}
        	}
        	LOGGER.info("zmConfigList:"+zmConfigList);
        } catch (Exception e) {
        	LOGGER.error("", e);;
            return null;
        }
        return zmConfigDtoList;
    }
	
	
	@POST
    @Path("/cfzmconfig/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "保存配置列表", httpMethod = "GET", notes = "保存配置列表")
    public Response saveCfzmconfig(ZmConfigDto zmConfigDto) {
		LOGGER.info("MSG:"+zmConfigDto.getSystemId()+","+zmConfigDto.getKey()+","+zmConfigDto.getValue()+","+zmConfigDto.getDesc());
		CfZmConfig config = new CfZmConfig(zmConfigDto.getSystemId(), zmConfigDto.getKey(),zmConfigDto.getValue(), zmConfigDto.getDesc());
		config.setIsDel((byte)0);
		config.setCreateTime(new Date());
		config.setLastModifytime(new Date());
		boolean flag = zmConfigLogic.saveCfZmConfig(config);
		if(flag){
			return Response.ok().build();
		}else{
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
					
		}
		
    }
	
	@PUT
    @Path("/cfzmconfig/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "修改配置列表", httpMethod = "PUT", notes = "保存配置列表")
    public Response updateCfzmconfig(ZmConfigDto zmConfigDto) {
		CfZmConfig config = new CfZmConfig(zmConfigDto.getSystemId(), zmConfigDto.getKey(),zmConfigDto.getValue(), zmConfigDto.getDesc());
		config.setCreateTime(new Date());
		config.setLastModifytime(new Date());
		boolean flag = zmConfigLogic.updateCfZmConfig(config);
		if(flag){
			return Response.ok().build();
		}else{
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
			
    }
	
	
	
	@DELETE
    @Path("/cfzmconfig/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "删除配置信息", httpMethod = "DELETE", notes = "删除配置信息")
    public Response deleteCfzmconfig(@Valid @NotNull @ApiParam(name = "systemId") @QueryParam("systemId") String systemId,
    		@Valid @NotNull @ApiParam(name = "cfKey") @QueryParam("cfKey") String cfKey) {
		LOGGER.info("deleteCfzmconfig systemId:"+systemId+",cfKey:"+cfKey);
		CfZmConfig config = new CfZmConfig();
		config.setCfKey(cfKey);
		config.setSystemId(systemId);
		boolean flag = zmConfigLogic.deleteCfZmConfig(config);
		if(flag){
			return Response.ok().build();
		}else{
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
    }
	
   
}
