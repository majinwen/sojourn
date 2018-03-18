package com.zra.business.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
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

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zra.business.entity.BusinessEntity;
import com.zra.business.entity.dto.BusinessOrderDto;
import com.zra.business.logic.BusinessLogic;
import com.zra.common.dto.business.BOQueryParamDto;
import com.zra.common.dto.business.BoCloseParamDto;
import com.zra.common.dto.business.BoCloseSMSContent;
import com.zra.common.dto.business.BoDistParamDto;
import com.zra.common.dto.business.BusinessDto;
import com.zra.common.dto.business.BusinessFullDto;
import com.zra.common.dto.business.BusinessResultDto;
import com.zra.common.error.ResultException;
import com.zra.common.utils.ZraApiConst;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author wangws21 2016年8月1日
 * 商机接口
 */
@Component
@Path("/business")
@Api(value="/business")
public class BusinessResource {
	
	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(BusinessResource.class);
	
	@Autowired
	private BusinessLogic businessLogic;
	
	/**
	 * 保存商机 wangws21 2016-8-3
	 * @param business 商机实体
	 * @return Response
	 */
	@POST
	@Path("/save/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "保存商机", notes = "保存商机", response = Response.class)
	public Response saveBusinessFull(BusinessFullDto businessFullDto){
		boolean isOK = businessLogic.save(businessFullDto);
		return Response.ok(isOK).build();
	}
	
	/**
	 * 查询商机列表
	 * @author tianxf9
	 * @param queryParams
	 * @return
	 */
	@POST
	@Path("/query/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "查询商机列表", notes = "查询商机列表", response = BusinessDto.class)
	public Map<String,Object> getBusinessDtoList(BOQueryParamDto queryParams) {
		return this.businessLogic.getBusinessList(queryParams);
	}
	
	/**
	 * 获取商机  用于商机处理
	 * @author wangws21 2016-8-4
	 * @param businessBid 商机bid
	 * @return BusinessSaveDto
	 */
	@GET
	@Path("/{businessBid}/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "获取商机详细信息", notes = "获取商机详细信息", response = BusinessFullDto.class)
	public BusinessFullDto getBusunessDetail(@Valid @NotNull @PathParam("businessBid") @ApiParam(name = "businessBid", value = "商机ID") String businessBid) {

		return this.businessLogic.getBusinessDetail(businessBid);
	}
	
	/**
	 * wangws21 2016-8-6
	 * 处理商机
	 * @param business 商机实体
	 * @return Response
	 */
	@POST
	@Path("/handle/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "处理商机", notes = "处理商机", response = Response.class)
	public Response updateBusinessFull(BusinessFullDto businessFullDto){
		boolean isOK = businessLogic.updateBusinessFull(businessFullDto);
		return Response.ok(isOK).build();
	}
	
	/**
	 * wangws21 2016-8-8
	 * 获取商机处理历史结果
	 * @param businessBid 商机bid
	 * @return List<BusinessResultDto>
	 */
	@GET
	@Path("/{businessBid}/businessResults/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "获取商机处理历史结果", notes = "获取商机处理历史结果", response = Response.class)
	public List<BusinessResultDto> getBusinessResults(@Valid @NotNull @PathParam("businessBid") @ApiParam(name = "businessBid", value = "商机ID") String businessBid){
		List<BusinessResultDto> businessResultList = this.businessLogic.getBusinessResultList(businessBid);
		return businessResultList;
	}
	
	/**
	 * 
	 * M站保存约看信息
	 *
	 * @author liujun
	 * @created 2016年8月10日
	 *
	 * @param paramJson
	 * @return Response
	 */
	@POST
	@Path("/save/apply/v1")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "预约看房", notes = ZraApiConst.CON_NEED_PARAM
	+ "<p>uuid(String)-用户的uid"
	+ "<br/>name(String)-姓名"
	+ "<br/>phone(String)-手机号"
	+ "<br/>company(String)-工作单位"
	+ "<br/>expectTime(String)-约看时间(yyyy-MM-dd HH:mm)"
	+ "<br/>projectId(String)-约看项目id"
	+ "<br/>nationality(String)-国籍"
	+ "<br/>sourceZra(Byte)-来源渠道"
	+ "<br/>htId(String)-约看房型id", response = Response.class)
	public Map<String, Object> insertBusinessApply(BusinessOrderDto dto) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
		    LOGGER.info("insertBusinessApply dto.getProjectId():"+dto.getProjectId()+",dto.getHtId():"+dto.getHtId()+",dto.getPhone():"+dto.getPhone());
		    boolean hasBus = businessLogic.hasBuss(dto.getProjectId(), dto.getHtId(), dto.getPhone());
		    if(hasBus) {
		        /*暂时这个样子，判断是否存在使用1018状态码表示*/
		        map.put("error_code", 1018);
		        map.put("status", "fail");
		    } else {
		        businessLogic.insertBusinessApplyFromMsite(dto);
                map.put("error_code", Status.OK.getStatusCode());
                map.put("status", "success");
		    }
			
			map.put("error_message", Status.OK.getReasonPhrase());
		} catch (ResultException e) {
			LOGGER.info("[预约看房]出错！", e);
			map.put("error_code", e.getCode());
			map.put("error_message", e.getMessage());
		} catch (Exception e) {
			LOGGER.info("[预约看房]出错！", e);
			map.put("status", "fail");
			map.put("error_code", Status.INTERNAL_SERVER_ERROR.getStatusCode());
			map.put("error_message", Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
		return map;
	}
	
	/**
	 * 
	 * 商机分配
	 *
	 * @author liujun
	 * @created 2016年8月10日
	 *
	 * @param dto
	 * @return Response
	 */
	@PUT
	@Path("/distribute/v1")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "商机分配", notes = ZraApiConst.CON_NEED_PARAM
	+ "<p>businessBid(String)-商机bid"
	+ "<br/>zoId(String)-管家姓名"
	+ "<br/>zoName(String)-管家姓名", response = Response.class)
	public Map<String, Object> updateBusiness(BoDistParamDto dto) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int upNum = businessLogic.updateBusinessEntityByBid(dto);
			resultMap.put("upNum", upNum);
			resultMap.put("status", "success");
			resultMap.put("error_code", Status.OK.getStatusCode());
			resultMap.put("error_message", Status.OK.getReasonPhrase());
		} catch (Exception e) {
			LOGGER.info("[商机分配]出错！", e);
			resultMap.put("status", "fail");
			resultMap.put("error_code", Status.INTERNAL_SERVER_ERROR.getStatusCode());
			resultMap.put("error_message", Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
		return resultMap;
	}
	
	/**
	 * 
	 * 批量商机分配
	 *
	 * @author liujun
	 * @created 2016年8月11日
	 *
	 * @param paramJson
	 * @return Response
	 */
	@PUT
	@Path("/batch/distribute/v1")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "批量商机分配", notes = ZraApiConst.CON_NEED_PARAM
	+ "<p>listBusinessBid(List<String>)-商机bid集合"
	+ "<br/>zoId(String)-管家姓名"
	+ "<br/>zoName(String)-管家姓名", response = Response.class)
	public Map<String, Object> batchUpdateBusiness(BoDistParamDto dto) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int upNum = businessLogic.batchUpdateBusinessEntity(dto);
			resultMap.put("upNum", upNum);
			resultMap.put("status", "success");
			resultMap.put("error_code", Status.OK.getStatusCode());
			resultMap.put("error_message", Status.OK.getReasonPhrase());
		} catch (Exception e) {
			LOGGER.info("[批量商机分配]出错！", e);
			resultMap.put("status", "fail");
			resultMap.put("error_code", Status.INTERNAL_SERVER_ERROR.getStatusCode());
			resultMap.put("error_message", Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
		return resultMap;
	}
	
	/**
	 * 定时任务更新处理进度
	 * @author tianxf9
	 * @return
	 */
	@POST
	@Path("/updateHandState/v1")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "自动任务更新处理进度")
	public int updateHandState() {
		return this.businessLogic.updateBusinessHandState();
	}

	/**
	 * 
	 * 关闭商机
	 *
	 * @author liujun
	 * @created 2016年8月10日
	 *
	 * @param dto
	 * @return Response
	 */
	@PUT
	@Path("/close/v1")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "关闭商机", notes = ZraApiConst.CON_NEED_PARAM
	+ "<p>businessBid(String)-商机bid"
	+ "<br/>updaterId(String)-操作人id"
	+ "<br/>step(Byte)-商机阶段"
	+ "<br/>handResultContent(String)-未成交原因", response = Response.class)
	public Map<String, Object> closeBusiness(BoCloseParamDto dto) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int upNum = businessLogic.closeBusinessEntity(dto);
			resultMap.put("upNum", upNum);
			resultMap.put("status", "success");
			resultMap.put("error_code", Status.OK.getStatusCode());
			resultMap.put("error_message", Status.OK.getReasonPhrase());
		} catch (Exception e) {
			LOGGER.info("[关闭商机]出错！", e);
			resultMap.put("status", "fail");
			resultMap.put("error_code", Status.INTERNAL_SERVER_ERROR.getStatusCode());
			resultMap.put("error_message", Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
		return resultMap;
	}
	
	/**
	 * 
	 * 查询商机信息
	 *
	 * @author liujun
	 * @created 2016年8月12日
	 *
	 * @param businessBid
	 * @return BusinessDto
	 */
	@GET
	@Path("query/{businessBid}/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "获取商机信息", notes = "获取商机信息", response = BusinessEntity.class)
	public BusinessEntity getBusuness(@Valid @NotNull @PathParam("businessBid") @ApiParam(name = "businessBid", value = "商机ID") String businessBid) {
		return this.businessLogic.getBusinessEntity(businessBid);
	}
	
	/**
	 * 
	 * 查询商机信息列表
	 *
	 * @author liujun
	 * @created 2016年8月12日
	 *
	 * @param businessBid
	 * @return BusinessDto
	 */
	@GET
	@Path("query/list/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "获取商机信息列表", notes = "获取商机信息列表", response = List.class)
	public List<BusinessEntity> getBusunessList(@QueryParam("listBusinessBid") String paramJson) {
		LOGGER.info("params:" + paramJson);
		List<String> listBusinessBid = JSON.parseArray(paramJson, String.class);
		return this.businessLogic.getBusinessListByBidList(listBusinessBid);
	}
	
	/**
	 * 
	 * 批量关闭商机
	 *
	 * @author liujun
	 * @created 2016年8月14日
	 *
	 * @param dto
	 * @return Response
	 */
	@PUT
	@Path("/batch/close/v1")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "关闭商机", notes = ZraApiConst.CON_NEED_PARAM
	+ "<p>listBusinessBid(List<String>-商机bid集合"
	+ "<br/>updaterId(String)-操作人id"
	+ "<br/>step(Byte)-商机阶段"
	+ "<br/>handResultContent(String)-未成交原因", response = Response.class)
	public Map<String, Object> batchCloseBusiness(BoCloseParamDto dto) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			System.err.println(JSON.toJSONString(dto));
			int upNum = businessLogic.batchCloseBusinessEntity(dto);
			resultMap.put("upNum", upNum);
			resultMap.put("status", "success");
			resultMap.put("error_code", Status.OK.getStatusCode());
			resultMap.put("error_message", Status.OK.getReasonPhrase());
		} catch (Exception e) {
			LOGGER.info("[关闭商机]出错！", e);
			resultMap.put("status", "fail");
			resultMap.put("error_code", Status.INTERNAL_SERVER_ERROR.getStatusCode());
			resultMap.put("error_message", Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
		return resultMap;
	}

	/**
	 * 
	 * 发送约看提醒短信定时任务
	 *
	 * @author liujun
	 * @created 2016年8月15日
	 *
	 */
	@POST
	@Path("/sendYkRemindSms/v1")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "约看提醒短信定时任务")
	public void sendYkRemindSms() {
		this.businessLogic.sendYkRemindSms();
	}
	
	/**
	 * 获取关闭商机发送短信模板列表
	 * @author tianxf9
	 * @return
	 */
	@GET
	@Path("getCloseSMSList/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "获取关闭商机发送短信模板列表", notes = "获取关闭商机发送短信模板列表", response = List.class)
	public List<BoCloseSMSContent> getCloseSMSList() {
		return this.businessLogic.getSmsList();
	}
	
	/**
	 * 根据id获取关闭商机短信模板
	 * @param id
	 * @return
	 */
	@GET
	@Path("getSmsContentById/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "根据id获取关闭商机短信模板", notes = "根据id获取关闭商机短信模板", response = List.class)
	public BoCloseSMSContent getSmsContentById(@QueryParam("id")Integer id) {
		return this.businessLogic.getSmsContentById(id);
	}
}
