package com.zra.log.resources;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.apollo.message.entity.BaseRequestEntity;
import com.apollo.message.util.SendMessageClient;
import com.zra.common.utils.PropUtils;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.common.dto.log.LogRecordDto;
import com.zra.common.dto.log.LogRecordParam;
import com.zra.common.dto.log.ResultLogRecordDto;
import com.zra.common.utils.StrUtils;
import com.zra.log.logic.LogLogic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author cuiyh9
 * 日志服务
 */
@Component
@Path("/log")
@Api(value="/log")
public class LogResource {
	
	
	public static final Logger LOGGER = LoggerFactoryProxy.getLogger(LogResource.class);
	
	@Autowired
	public LogLogic logLogic;
	
	
	@GET
    @Path("/list/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "查询日志", httpMethod = "GET", notes = "查询日志")
    public Response getLogs(@Valid @ApiParam(name = "systemId", value = "系统号") @QueryParam("systemId") String systemId,
    		@Valid @ApiParam(name = "operModId", value = "操作类型") @QueryParam("operModId") String operModId,
    		@Valid @ApiParam(name = "operObjId", value = "操作对象ID") @QueryParam("operObjId") String operObjId,
    		@Valid @ApiParam(name = "page", value = "页数，从1开始") @QueryParam("page") String page,
    		@Valid @ApiParam(name = "size", value = "每页显示多少条") @QueryParam("size") String size) {
		if(StrUtils.isNullOrBlank(page) ){
			page = "1";
		}
		if(StrUtils.isNullOrBlank(size) ){
			size = "10";
		}
		LogRecordParam logRecordParam = new LogRecordParam(systemId,operModId,operObjId,Integer.valueOf(page),Integer.valueOf(size));
		ResultLogRecordDto resultLogRecordDto = logLogic.getLogs(logRecordParam);
		if(resultLogRecordDto == null){
			return Response.status(Status.BAD_REQUEST).build();
		}else{
			return Response.ok(resultLogRecordDto).build();
		}
		
    }
	
	@POST
    @Path("/log/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "保存日志,所有的值不许为空，不许有空格", httpMethod = "POST", notes = "保存日志")
    public Response saveLog(LogRecordDto logRecordDto) {
		boolean flag = logLogic.saveLog(logRecordDto);
		if(flag){
			return Response.ok().build();
		}else{
			return Response.status(Status.BAD_REQUEST).build();
		}
		
    }



	@GET
	@Path("/test/log")
	public String testException(){
        String tst= null;
        try {
            LOGGER.error("nihao","异常日志--格式【error(String var1, Object var2)】");
        } catch (Exception e) {
            LOGGER.error("异常日志--格式【error(String var1, Throwable var2)】",e);
        }
        LOGGER.error("just测试log-Exception 异常日志--格式【void error(String var1)】:"+tst);

		BaseRequestEntity ss=new BaseRequestEntity("9tF_mdo_QjW2WSA1WeMSjA","20275658","just test object send message");
		String addr= PropUtils.getString("CON_DINGDING_PATH_KEY");
		SendMessageClient.callSendMessageAPI(ss,addr);
        return "success";
    }
}
