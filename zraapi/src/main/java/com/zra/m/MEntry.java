package com.zra.m;

import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.ziroom.tech.scaffold.boot.protocol.http.HttpRequest;
import com.zra.business.entity.dto.BusinessApplyReturnDto;
import com.zra.business.entity.dto.BusinessOrderDto;
import com.zra.common.dto.appbase.AppBaseDto;
import com.zra.common.utils.ZraApiConst;
import com.zra.house.entity.dto.*;
import com.zra.m.entity.dto.*;
import com.zra.m.resources.MResources;
import com.zra.m.tools.CookieDealUtil;
import com.zra.m.tools.MResultReturn;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import java.util.List;

/**
 * Author: wangxm113
 * CreateDate: 2017/3/9.
 */
@Component
@Path("/m")
@Api(value = "/m")
public class MEntry {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(MEntry.class);

    @Autowired
    private MResources mResources;

    @GET
    @Path("/project/list/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "项目列表", notes = "", response = NewProjectListDto.class)
    public MResultReturn getProjectList() {
        try {
            AppBaseDto dto = new AppBaseDto();
            List<NewProjectListDto> result = mResources.getNewProjectList(dto);
            return MResultReturn.toSuccess(result);
        } catch (Exception e) {
            LOGGER.info("[M站项目列表]出错！", e);
            return MResultReturn.toFail(e);
        }
    }

    @GET
    @Path("/project/detail/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "项目详情", notes = "projectId(String)-项目fid;", response = NewProjectDetailDto.class)
    public MResultReturn getNewProjectDetail(@QueryParam("projectId") String projectId) {
        try {
            SearchReqDto dto = new SearchReqDto();
            dto.setProjectId(projectId);
            NewProjectDetailDto result = mResources.getNewProjectDetail(dto);
            return MResultReturn.toSuccess(result);
        } catch (Exception e) {
            LOGGER.info("[M站项目详情]出错！", e);
            return MResultReturn.toFail(e);
        }
    }

    @GET
    @Path("/room/info/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "房间信息", notes = "houseTypeId(String)-房型id", response = MRoomDto.class)
    public MResultReturn getRoomSearchCondition(@QueryParam("houseTypeId") String houseTypeId) {
        try {
            HouseTypeReqDto dto = new HouseTypeReqDto();
            dto.setHouseTypeId(houseTypeId);
            MRoomDto result = mResources.findHouseTypeDetail(dto);
            return MResultReturn.toSuccess(result);
        } catch (Exception e) {
            LOGGER.info("[M站房间信息]出错！", e);
            return MResultReturn.toFail(e);
        }
    }

    @GET
    @Path("/business/entry/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "进入约看", notes = "", response = MBusinessEntryDto.class)
    public MResultReturn getProAndHtInfo() {
        try {
            MBusinessEntryDto result = mResources.getProAndHtInfo();
            return MResultReturn.toSuccess(result);
        } catch (Exception e) {
            LOGGER.info("[M站进入约看]出错！", e);
            return MResultReturn.toFail(e);
        }
    }

    @GET
    @Path("/business/save/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "提交约看", notes = "name(String)-姓名"
            + "<br/>nationality(String)-国籍"
            + "<br/>phone(String)-手机号"
            + "<br/>company(String)-工作单位"
            + "<br/>expectTime(String)-约看时间(yyyy-MM-dd HH:mm)"
            + "<br/>projectId(String)-约看项目id"
            + "<br/>comeSource(String)-提交约看的前一个页面的URL"
            + "<br/>htId(String)-约看房型id"
            + "<br/>message(String)-留言"
            + "<br/>passport_token", response = MResultReturn.class)
    public MResultReturn insertBusiness(@BeanParam BusinessOrderDto dto) {
        try {
//            String token = CookieDealUtil.getCookieByRequest(request, "passport_token");
//            dto.setToken(token);
            mResources.insertBusiness(dto);
            return MResultReturn.toSuccess();
        } catch (Exception e) {
            LOGGER.info("[M站提交约看]出错！", e);
            return MResultReturn.toFail(e);
        }
    }

    @GET
    @Path("/zo/entry/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "进入围观ZO", notes = "", response = MZOProDto.class)
    public MResultReturn getProAndZOInfo() {
        try {
            List<MZOProDto> result = mResources.getProAndZOInfo();
            return MResultReturn.toSuccess(result);
        } catch (Exception e) {
            LOGGER.info("[M站进入围观ZO]出错！", e);
            return MResultReturn.toFail(e);
        }
    }

    @GET
    @Path("/zo/list/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "ZO列表", notes = "projectId(String)-项目id", response = MZOListDto.class)
    public MResultReturn getZOInfoList(@QueryParam("projectId") String projectId) {
        try {
            List<MZOListDto> result = mResources.getZOInfoList(projectId);
            return MResultReturn.toSuccess(result);
        } catch (Exception e) {
            LOGGER.info("[M站ZO列表]出错！", e);
            return MResultReturn.toFail(e);
        }
    }

    @GET
    @Path("/zo/evaentry/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "进入评价ZO", notes = "zoId(String)-zoId"
                + "<br/>passport_token", response = MZOListDto.class)
    public MResultReturn getZOLabelToEva(@QueryParam("zoId") String zoId, @QueryParam("passport_token") String token) {
        try {
//            String token = CookieDealUtil.getCookieByRequest(request, "passport_token");
            MZOListDto result = mResources.getZOLabelToEva(token, zoId);
            return MResultReturn.toSuccess(result);
        } catch (Exception e) {
            LOGGER.info("[M站进入评价ZO]出错！", e);
            return MResultReturn.toFail(e);
        }
    }

    @GET
    @Path("/zo/ifzryer/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "是否是自如寓客户", notes = "phone(String)-用户手机", response = MResultReturn.class)
    public MResultReturn getIfEvaluate(@QueryParam("phone") String phone) {
        try {
            mResources.getIfEvaluate(phone);
            return MResultReturn.toSuccess();
        } catch (Exception e) {
            LOGGER.info("[M站是否是自如寓客户]出错！", e);
            return MResultReturn.toFail(e);
        }
    }

    @GET
    @Path("/zo/evasave/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "提交评价", notes = "proId(String)-项目id"
            + "<br/>zoId(String)-受评人zoId"
            + "<br/>positiveList(List<String>)-正面标签"
            + "<br/>negativeList(List<String>)-负面标签"
            + "<br/>content(String)-评价内容"
            + "<br/>passport_token", response = MResultReturn.class)
    public MResultReturn saveEvaluate(@BeanParam MEvaluateSaveDto dto) {
        try {
//            String token = CookieDealUtil.getCookieByRequest(request, "passport_token");
//            dto.setToken(token);
            mResources.saveEvaluate(dto);
            return MResultReturn.toSuccess();
        } catch (Exception e) {
            LOGGER.info("[M站提交评价]出错！", e);
            return MResultReturn.toFail(e);
        }
    }

    @GET
    @Path("/token/check/v1")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "验证token", notes = "passport_token", response = MResultReturn.class)
    public MResultReturn checkToken(@QueryParam("passport_token") String token) {
        try {
//            String token = CookieDealUtil.getCookieByRequest(request, "passport_token");
            mResources.checkToken(token);
            return MResultReturn.toSuccess();
        } catch (Exception e) {
            LOGGER.info("[M站验证token]出错！", e);
            return MResultReturn.toFail(e);
        }
    }
}
