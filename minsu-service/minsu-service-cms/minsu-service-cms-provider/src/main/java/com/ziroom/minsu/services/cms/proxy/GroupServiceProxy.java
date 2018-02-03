package com.ziroom.minsu.services.cms.proxy;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.AsuraHttpClient;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.*;
import com.ziroom.minsu.services.cms.api.inner.GroupService;
import com.ziroom.minsu.services.cms.dto.ActivityGroupRequest;
import com.ziroom.minsu.services.cms.dto.GroupHouseRelRequest;
import com.ziroom.minsu.services.cms.dto.GroupRequest;
import com.ziroom.minsu.services.cms.dto.ZrpAttendActRequest;
import com.ziroom.minsu.services.cms.service.ActivityServiceImpl;
import com.ziroom.minsu.services.cms.service.GroupServiceImpl;
import com.ziroom.minsu.services.common.utils.CloseableHttpsUtil;
import com.ziroom.minsu.valenum.cms.GroupTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>群组列表</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月12日 16:39
 * @since 1.0
 */
@Component("cms.groupServiceProxy")
public class GroupServiceProxy implements GroupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupServiceProxy.class);

    @Resource(name = "cms.groupServiceImpl")
    private GroupServiceImpl groupServiceImpl;
    @Resource(name = "cms.activityServiceImpl")
    private ActivityServiceImpl activityServiceImpl;
    @Value("#{'${CUSTOMER_USER_BASE_URL}'.trim()}")
    private String CUSTOMER_USER_BASE_URL;


    @Override
    public String listGroupByType(String paramJson) {
        LogUtil.info(LOGGER, "【listGroupByType】参数paramJson:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            ActivityGroupRequest activityGroupRequest = JsonEntityTransform.json2Object(paramJson, ActivityGroupRequest.class);
            PagingResult<GroupEntity> pagingResult = groupServiceImpl.listGroupByType(activityGroupRequest);
            List<GroupEntity> rows = pagingResult.getRows();
            for (GroupEntity groupEntity : rows) {
                if (activityGroupRequest.getGroupType() != null && groupEntity.getGroupType() == GroupTypeEnum.USER.getCode()) {
                    groupEntity.setPeopleNum((int) groupServiceImpl.countUserByGroupId(groupEntity.getFid()));
                }
            }
            dto.putValue("total", pagingResult.getTotal());
            dto.putValue("list", pagingResult.getRows());
        }catch (Exception e){
            LogUtil.error(LOGGER, "【listGroupByType】参数paramJson:{},e:{}", paramJson,e);
            dto.setMsg("系统异常");
            dto.setErrCode(DataTransferObject.ERROR);
        }
        return dto.toJsonString();
    }

    @Override
    public String listGroupActRelByActSn(String actSn) {
        LogUtil.info(LOGGER, "【listGroupActRelByActSn】参数actSn:{}", actSn);
        DataTransferObject dto = new DataTransferObject();
        try {
            if (Check.NuNStr(actSn)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数为空");
                return dto.toJsonString();
            }
            List<GroupActRelEntity> groupActRelEntities = groupServiceImpl.listGroupActRelByActSn(actSn);
            dto.putValue("list", groupActRelEntities);
        }catch (Exception e){
            LogUtil.error(LOGGER, "【listGroupActRelByActSn】参数actSn:{},e:{}", actSn,e);
            dto.setMsg("系统异常");
            dto.setErrCode(DataTransferObject.ERROR);
        }
        return dto.toJsonString();
    }

    @Override
    public String saveGroup(String paramJson) {
        LogUtil.info(LOGGER, "【saveGroup】参数paramJson:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            GroupEntity groupEntity = JsonEntityTransform.json2Entity(paramJson,GroupEntity.class);
            if(Check.NuNStrStrict(groupEntity.getGroupName())){
                dto.setMsg("组名称不能为空");
                dto.setErrCode(DataTransferObject.ERROR);
                return dto.toJsonString();
            }
            if(groupServiceImpl.selectCountByGroupName(groupEntity).intValue()!=0){
                dto.setMsg("该组名已存在，请修改");
                dto.setErrCode(DataTransferObject.ERROR);
                return dto.toJsonString();
            }
            int num = groupServiceImpl.saveGroup(groupEntity);
            dto.putValue("num",num);
        }catch (Exception e){
            LogUtil.error(LOGGER, "【saveGroup】参数paramJson:{},e:{}", paramJson,e);
            dto.setMsg("系统异常");
            dto.setErrCode(DataTransferObject.ERROR);
        }
        return dto.toJsonString();
    }

    @Override
    public String deleteGroup(String paramJson) {
        return null;
    }

    @Override
    public String deleteHouseRel(String paramJson) {
        LogUtil.info(LOGGER, "【deleteHouseRel】参数paramJson:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            GroupHouseRelEntity groupHouseRelEntity = JsonEntityTransform.json2Object(paramJson, GroupHouseRelEntity.class);
            int num = groupServiceImpl.deleteHouseRel(groupHouseRelEntity);
            dto.putValue("num", num);
        }catch (Exception e){
            LogUtil.error(LOGGER, "【deleteHouseRel】参数paramJson:{},e:{}", paramJson,e);
            dto.setMsg("系统异常");
            dto.setErrCode(DataTransferObject.ERROR);
        }
        return dto.toJsonString();
    }

    @Override
    public String listHouseRelByPage(String paramJson) {
        LogUtil.info(LOGGER, "【listHouseRelByPage】参数paramJson:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            GroupRequest groupRequest = JsonEntityTransform.json2Object(paramJson, GroupRequest.class);
            PagingResult<GroupHouseRelEntity> pagingResult = groupServiceImpl.listHouseRelByFidForPage(groupRequest);
            dto.putValue("total", pagingResult.getTotal());
            dto.putValue("list", pagingResult.getRows());
        }catch (Exception e){
            LogUtil.error(LOGGER, "【listHouseRelByPage】参数paramJson:{},e:{}", paramJson,e);
            dto.setMsg("系统异常");
            dto.setErrCode(DataTransferObject.ERROR);
        }
        return dto.toJsonString();
    }

    @Override
    public String listUserRelByPage(String paramJson) {
        LogUtil.info(LOGGER, "【listUserRelByPage】参数param={}", paramJson);
        GroupRequest groupRequest = JsonEntityTransform.json2Object(paramJson, GroupRequest.class);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNObj(groupRequest)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        PagingResult<GroupUserRelEntity> pagingResult = groupServiceImpl.listUserRelByPage(groupRequest);
        long total = pagingResult.getTotal();
        List<GroupUserRelEntity> rows = pagingResult.getRows();
        dto.putValue("total", total);
        dto.putValue("list", rows);
        return dto.toJsonString();
    }

    @Override
    public String deleteUserRelBatch(String paramJson) {
        LogUtil.info(LOGGER, "【deleteUserRelBatch】参数param={}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(paramJson)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        String[] split = paramJson.split(",");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            if (!Check.NuNStr(split[i])) {
                list.add(split[i]);
            }
        }
        int i = groupServiceImpl.deleteUserRelBatch(list);
        dto.putValue("count", i);
        return dto.toJsonString();
    }

    @Override
    public String saveUserRel(String paramJson) {
        LogUtil.info(LOGGER, "【saveUserRel】param={}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(paramJson)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        GroupUserRelEntity groupUserRelEntity = JsonEntityTransform.json2Object(paramJson, GroupUserRelEntity.class);
        if (Check.NuNStr(groupUserRelEntity.getGroupFid())) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("活动组ID为空");
            return dto.toJsonString();
        }
        if (Check.NuNStr(groupUserRelEntity.getUid())) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("uid为空");
            return dto.toJsonString();
        }

        List<GroupUserRelEntity> groupUserRelEntities = groupServiceImpl.listUserRelByUid(groupUserRelEntity.getGroupFid(), groupUserRelEntity.getUid());
        if (!Check.NuNCollection(groupUserRelEntities)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("用户已存在");
            return dto.toJsonString();
        }
        int count = groupServiceImpl.saveUserRel(groupUserRelEntity);
        dto.putValue("count", count);
        return dto.toJsonString();
    }

    @Override
    public String addHouseRelBatch(String paramJson) {
        LogUtil.info(LOGGER, "【addHouseRelBatch】paramJson={}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            GroupHouseRelRequest groupHouseRelRequest = JsonEntityTransform.json2Object(paramJson,GroupHouseRelRequest.class);
            if(Check.NuNObj(groupHouseRelRequest)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数错误");
                return dto.toJsonString();
            }
            List<GroupHouseRelEntity> groupHouseRelEntities = groupHouseRelRequest.getGroupHouseRelEntities();
            if(Check.NuNCollection(groupHouseRelEntities)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数错误");
                dto.toJsonString();
            }
            Long num = 0L;
            //校验有没有重复添加房源组
            for(GroupHouseRelEntity groupHouseRelEntity:groupHouseRelEntities){
                num += groupServiceImpl.selectExistCount(groupHouseRelEntity);
            }
            if(num.intValue() != 0){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("您批量添加的房源组中和已添加的房源组有冲突，请核对后重新添加");
                return dto.toJsonString();
            }
            groupServiceImpl.addHouseRelBatch(groupHouseRelEntities);
        }catch (Exception e){
            LogUtil.error(LOGGER,"【addHouseRelBatch】批量添加房源组异常,e:{}",e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常");
        }
        return dto.toJsonString();
    }


    @Override
    public String userAddGroupAct(String paramJson) {
        DataTransferObject dto = new DataTransferObject();
        LogUtil.info(LOGGER, "【userAddGroupAct】参数param={}", paramJson);
        ZrpAttendActRequest zrpAttendActRequest = JsonEntityTransform.json2Object(paramJson, ZrpAttendActRequest.class);
        try {
            if (Check.NuNObj(zrpAttendActRequest)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数为空");
                return dto.toJsonString();
            }
            String groupUserFid = zrpAttendActRequest.getGroupUserFid();
            if (Check.NuNStr(groupUserFid) || Check.NuNStr(zrpAttendActRequest.getUid())) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数为空");
                return dto.toJsonString();
            }
            GroupEntity groupEntity = groupServiceImpl.findGroupByFid(groupUserFid);
            if (Check.NuNObj(groupEntity)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("活动不存在");
                return dto.toJsonString();
            }
            List<GroupUserRelEntity> groupvalidList = groupServiceImpl.listUserRelByUid(groupUserFid, zrpAttendActRequest.getUid());
            if (Check.NuNCollection(groupvalidList)) {
                String userName = "";
                String phone = "";
                try {
                    String url = CUSTOMER_USER_BASE_URL + zrpAttendActRequest.getUid();
                    String resultJson = CloseableHttpsUtil.sendGet(url, null);
                    JSONObject rootObj = JSONObject.parseObject(resultJson);
                    if (rootObj.getString("status").equals("success")) {
                        JSONObject data = rootObj.getJSONObject("data");
                        JSONObject profileObj = data.getJSONObject("profile");
                        userName = profileObj.getString("user_name");
                        phone = profileObj.getString("phone");
                    }
                } catch (Exception e) {
                    LogUtil.error(LOGGER, "查询用户信息异常,e={}", e);
                }

                GroupUserRelEntity groupUserRelEntity = new GroupUserRelEntity();
                groupUserRelEntity.setGroupFid(groupUserFid);
                groupUserRelEntity.setUid(zrpAttendActRequest.getUid());
                groupUserRelEntity.setCreateFid(zrpAttendActRequest.getCreateFid());
                groupUserRelEntity.setCreateName(zrpAttendActRequest.getCreateName());
                groupUserRelEntity.setCustomerName(userName);
                groupUserRelEntity.setCustomerPhone(phone);
                int count = groupServiceImpl.saveUserRel(groupUserRelEntity);
                LogUtil.info(LOGGER, "更新count={}", count);
            }
            List<ActivityEntity> activityEntities = activityServiceImpl.listUserGroupActForZrp(groupUserFid);
            List<Map<String, Object>> list = new ArrayList<>();
            if (Check.NuNCollection(activityEntities)) {
                dto.putValue("actList", list);
                return dto.toJsonString();
            }
            //参加活动
            for (ActivityEntity activityEntity : activityEntities) {
                Map<String, Object> itemMap = new HashMap<>();
                itemMap.put("actName", activityEntity.getActName());
                //itemMap.put("actType",activityEntity.getActType());
                itemMap.put("actStartTime", DateUtil.dateFormat(activityEntity.getActStartTime(), "yyyy-MM-dd HH:mm:ss"));
                itemMap.put("actEndTime", DateUtil.dateFormat(activityEntity.getActEndTime(), "yyyy-MM-dd HH:mm:ss"));
                list.add(itemMap);
            }
            dto.putValue("actList", list);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【addHouseRelBatch】批量添加房源组异常,e:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常");
        }
        return dto.toJsonString();
    }
}
