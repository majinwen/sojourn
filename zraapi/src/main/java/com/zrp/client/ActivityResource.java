package com.zrp.client;

import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.cms.api.inner.GroupService;
import com.ziroom.minsu.services.cms.dto.ZrpAttendActRequest;
import com.zra.common.enums.ErrorEnum;
import com.zra.common.result.ResponseDto;
import com.zra.common.utils.Check;
import com.zra.common.utils.RedisUtil;
import com.zra.common.utils.ZraApiConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * <p>活动接口</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2018年01月25日 16:53
 * @since 1.0
 */
@Component
@Path("/activity")
@Api(value = "/活动", description = "活动相关接口")
public class ActivityResource {

    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(ActivityResource.class);

    @Autowired
    @Qualifier("cms.groupService")
    private GroupService groupService;
    @POST
    @Path("/userAttendActivity/v1")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "获取合同详情", notes = ZraApiConst.CON_NEED_PARAM
            + "<br/> contractId(String)-合同标识", response = ResponseDto.class)
    public ResponseDto userAttendActivity(@FormParam("groupFid") String groupFid, @FormParam("uid") String uid) {
        LOGGER.info("【userAttendActivity】入参：groupFid={},uid={}",groupFid,uid);
        if (Check.NuNStr(groupFid) || Check.NuNStr(uid)){
            return ResponseDto.responseDtoFail("参数为空");
        }
        String redisKey = "ZrpAct:"+groupFid+":"+uid;
        try{
            String data = RedisUtil.getData(redisKey);
            if (!Check.NuNStr(data)){
                return ResponseDto.responseDtoFail("请求太频繁了");
            }else{
                RedisUtil.setData(redisKey,uid);
            }
            ZrpAttendActRequest zrpAttendActRequest = new ZrpAttendActRequest();
            zrpAttendActRequest.setGroupUserFid(groupFid);
            zrpAttendActRequest.setUid(uid);
            zrpAttendActRequest.setCreateName("用户参加活动");
            zrpAttendActRequest.setCreateFid("001");
            String resultJson = groupService.userAddGroupAct(JsonEntityTransform.Object2Json(zrpAttendActRequest));
            LOGGER.info("返回结果result={}",resultJson);
            DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
            RedisUtil.deleteData(redisKey);
            if (resultDto.getCode() == DataTransferObject.ERROR){
                return ResponseDto.responseDtoFail(resultDto.getMsg());
            }
            return ResponseDto.responseOK(resultDto.getData().get("actList"));
        }catch(Exception e){
            LOGGER.error("【userAttendActivity】出错!{}", e);
            return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
        }
    }

}
