package com.zra.cms.resources;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ziroom.zrp.service.trading.api.RentContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.cms.entity.dto.CmsAppProjectDto;
import com.zra.cms.logic.CmsAppLogic;
import com.zra.common.utils.StrUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * CMS系统接口
 * @author cuiyh9
 */
@Component
@Path("/cms")
@Api(value = "/cms")
public class CmsResource {

    @Autowired
    private CmsAppLogic cmsAppLogic;

    @Resource(name="trading.rentContractService")
    private RentContractService rentContractService;


    @GET
    @Path("/cmsAppProjectDto")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "获取APP端项目配置信息，目前仅内部用于测试", notes = "获取APP端项目配置信息，目前仅内部用于测试")
    public Response getCmsAppProjectDto(
            @Valid @NotNull @QueryParam("projectId") @ApiParam(name = "projectId", value = "项目ID") String projectId) {
        if (StrUtils.isNullOrBlank(projectId)) {
            //TODO --- 这特么是啥
            return Response.ok().entity("projectId is null").build();
        }
        CmsAppProjectDto cmsAppProjectDto = cmsAppLogic.queryCmsAppProjectDtoByPid(projectId);
        return Response.ok().entity(cmsAppProjectDto).build();
    }

    @DELETE
    @Path("/cache/v1")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "删除cms配置的app端展现信息的缓存", notes = "删除cms配置的app端展现信息的缓存")
    public Response deleteCmsAppCache(
            @Valid @NotNull @QueryParam("projectId") @ApiParam(name = "projectId", value = "项目ID") String projectId) {
        boolean flag = cmsAppLogic.deleteCmsAppCache(projectId);
        if (flag) {
            return Response.ok().build();
        } else {
            return Response.serverError().build();
        }
        
    }
    
    
  
    /**
     * 删除cms配置的app端展现的户型信息的缓存
     * @author tianxf9
     * @param housetypeId
     * @return
     */
    @DELETE
    @Path("/delete/cmsHousetype/cache/v1")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "删除cms配置的app端展现的户型信息的缓存", notes = "删除cms配置的app端展现的户型信息的缓存")
    public Response deleteCmsAppHousetypeCache(
            @Valid @NotNull @QueryParam("housetypeId") @ApiParam(name = "housetypeId", value = "户型ID") String housetypeId) {
        boolean flag = cmsAppLogic.deleteCmsHousetypeCache(housetypeId);
        if (flag) {
            return Response.ok().build();
        } else {
            return Response.serverError().build();
        }
        
    }
    
    
    /**
     * @author tianxf9
     * @param housetypeId
     * @param labType
     * @return
     */
    @DELETE
    @Path("/delete/cmsHousetypeLab/cache/v1")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "删除cms配置的app端展现的户型标签信息的缓存", notes = "删除cms配置的app端展现的户型标签信息的缓存")
    public Response deleteCmsAppHousetypeLabCache(
            @Valid @NotNull @QueryParam("housetypeId") @ApiParam(name = "housetypeId", value = "户型ID") String housetypeId,
            @Valid @NotNull @QueryParam("labType") @ApiParam(name = "labType", value = "标签类型ID") Integer labType) {
        boolean flag = cmsAppLogic.deleteCmsHousetypeLabCache(housetypeId, labType);
        if (flag) {
            return Response.ok().build();
        } else {
            return Response.serverError().build();
        }
        
    }
}
