/**
 * @FileName: SmartResource.java
 * @Package com.zrp.smart
 *
 * @author bushujie
 * @created 2018年1月23日 下午5:54:51
 *
 * Copyright 2011-2015 asura
 */
package com.zrp.smart;

import java.util.List;

import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.DateUtil;
import com.ziroom.zrp.houses.entity.ProjectEntity;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.service.houses.api.ProjectService;
import com.ziroom.zrp.service.houses.api.RoomService;
import com.ziroom.zrp.service.houses.api.SmartPlatformService;
import com.ziroom.zrp.service.houses.dto.smartplatform.SmartPlatformSaveHireContractDto;
import com.ziroom.zrp.service.houses.valenum.CompanyEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.service.trading.api.RentCheckinService;
import com.ziroom.zrp.trading.entity.RentContractEntity;

/**
 * <p>智能设备相关接口</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@Component
@Path("/smart")
@Api(value = "smart",description = "智能设备相关接口")
public class SmartResource {

    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(SmartResource.class);

    @Resource(name="trading.rentCheckinService")
    private RentCheckinService rentCheckinService;

    @Resource(name = "houses.projectService")
    private ProjectService projectService;

    @Resource(name = "houses.roomService")
    private RoomService roomService;

    @Resource(name = "houses.smartPlatformService")
    private SmartPlatformService smartPlatformService;

    /**
     *
     * 判断用户是否自如寓用户
     *
     * @author bushujie
     * @created 2018年1月23日 下午6:17:45
     *
     * @param uid
     * @return
     */
    @POST
    @Path("/customer/isZyu/v1")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "校验用户是否自如寓用户", notes =  "<br/> uid(String)-用户uid", response = Response.class)
    public DataTransferObject customerIsZyu(@FormParam("uid") String uid) {
        LogUtil.info(LOGGER, "【customerIsZyu】uid：{}", uid);
        DataTransferObject resultDto=new DataTransferObject();
        try{
            String resutJson=rentCheckinService.getValidContractList(uid);
            LogUtil.info(LOGGER, "【getValidContractList】结果：{}", resutJson);
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resutJson);
            if (dto.getCode() == DataTransferObject.SUCCESS) {
                List<RentContractEntity> list=SOAResParseUtil.getListValueFromDataByKey(resutJson, "list", RentContractEntity.class);
                if(!Check.NuNCollection(list)){
                    resultDto.putValue("isZyu", 1);
                }else{
                    resultDto.putValue("isZyu", 0);
                }
            }else {
                resultDto.putValue("isZyu", 0);
            }
        }catch(Exception e){
            LogUtil.error(LOGGER, "【getValidContractList】出错!{}", e);
            resultDto.putValue("isZyu", 0);
        }
        return resultDto;
    }

    /**
     *
     * 初始化智能平台维度数据(非后台研发人员勿用)
     *
     * @author zhangyl2
     * @created 2018年02月07日 15:43
     * @param
     * @return
     */
    @GET
    @Path("/initSmartHireContract")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "初始化智能平台维度数据(非后台研发人员勿用)", notes =  "初始化智能平台维度数据(非后台研发人员勿用)", response = Response.class)
    public DataTransferObject initSmartHireContract(@QueryParam("projectId") String projectId) {

        LogUtil.info(LOGGER, "【initSmartHireContract】projectId={}", projectId);
        DataTransferObject dto = new DataTransferObject();

        try {
            ProjectEntity projectEntity = SOAResParseUtil.getValueFromDataByKey(
                    projectService.findProjectById(projectId),
                    "projectEntity",
                    ProjectEntity.class);

            SmartPlatformSaveHireContractDto p = new SmartPlatformSaveHireContractDto();
            p.setHireContractCode(projectEntity.getFid());
            p.setHouseCode(projectEntity.getFid());
            p.setHouseSourceCode(projectEntity.getFname());

            CompanyEnum companyEnum = CompanyEnum.getCompanyEnumByCode(projectEntity.getFcompanyid());
            p.setCityCode(Check.NuNObj(companyEnum) ? "" : companyEnum.getCode());
            p.setCityName(Check.NuNObj(companyEnum) ? "" : companyEnum.getName());

            p.setAddress(projectEntity.getFaddress());
            p.setHireContractStartTime(DateUtil.timestampFormat(projectEntity.getFopeningtime()));
            p.setHireContractEndTime(DateUtil.timestampFormat(projectEntity.getFcontractend()));

            List<RoomInfoEntity> list = SOAResParseUtil.getListValueFromDataByKey(
                    roomService.findAllRoom(projectEntity.getFid()),
                    "list",
                    RoomInfoEntity.class);

            for(RoomInfoEntity roomInfoEntity : list){
                SmartPlatformSaveHireContractDto.Device device = p.new Device();
                device.setPositionRank1(projectEntity.getFid());
                // 外门锁
                device.setDeviceType("ZR0011");
                device.setPositionRank2(roomInfoEntity.getFid());

                device.setPositionRankName(roomInfoEntity.getFroomnumber());

                p.addDevice(device);
            }

            String result = smartPlatformService.initSmartHireContract(p.toJsonStr());

            dto.setMsg(result);

        } catch (SOAParseException e) {
            e.printStackTrace();
        }

        return dto;

    }

}