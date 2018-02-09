package com.ziroom.minsu.troy.cms.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.GroupEntity;
import com.ziroom.minsu.entity.cms.GroupHouseRelEntity;
import com.ziroom.minsu.entity.cms.GroupUserRelEntity;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.services.basedata.entity.entityenum.ServiceLineEnum;
import com.ziroom.minsu.services.cms.api.inner.GroupService;
import com.ziroom.minsu.services.cms.dto.ActivityGroupRequest;
import com.ziroom.minsu.services.cms.dto.GroupHouseRelRequest;
import com.ziroom.minsu.services.cms.dto.GroupRequest;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.common.utils.CloseableHttpsUtil;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.valenum.cms.GroupTypeEnum;
import com.ziroom.zrp.houses.entity.CityEntity;
import com.ziroom.zrp.houses.entity.ProjectEntity;
import com.ziroom.zrp.service.houses.api.CityService;
import com.ziroom.zrp.service.houses.api.HouseTypeService;
import com.ziroom.zrp.service.houses.api.ProjectService;
import com.ziroom.zrp.service.houses.api.RoomService;
import com.ziroom.zrp.service.houses.dto.AddHouseGroupDto;
import com.ziroom.zrp.service.houses.entity.AddHouseGroupVo;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>活动相关组（房源组、用户组）管理</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年10月16日 16:45
 * @since 1.0
 */
@Controller
@RequestMapping("activityGroup")
public class ActivityGroupController {

    @Resource(name = "cms.groupService")
    private GroupService groupService;

    @Resource(name = "houses.projectService")
    private ProjectService projectService;

    @Resource(name = "houses.cityService")
    private CityService cityService;

    @Resource(name = "houses.houseTypeService")
    private HouseTypeService houseTypeService;

    @Resource(name = "houses.roomService")
    private RoomService roomService;

    @Value("#{'${PASSPORT_USER_BASE_URL}'.trim()}")
    private String PASSPORT_USER_BASE_URL;


    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ActivityGroupController.class);

    /**
      * @description: 跳转活动分组页面
      * @author: lusp
      * @date: 2017/10/16 下午 16:48
      * @params: request
      * @return: ModelAndView
      */
    @RequestMapping("toActivityGroupList")
    public ModelAndView toActivityGroupList(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/activity/activityGroupList");
        mv.addObject("serviceLineList",ServiceLineEnum.values());
        return mv;
    }


    /**
      * @description: 分页查询分组列表
      * @author: lusp
      * @date: 2017/10/16 下午 20:29
      * @params: activityGroupRequest,request
      * @return: PageResult
      */
    @RequestMapping("groupList")
    @ResponseBody
    public PageResult groupList(ActivityGroupRequest activityGroupRequest, HttpServletRequest request) {
        PageResult pageResult = new PageResult();
        try {
            if(Check.NuNObj(activityGroupRequest.getGroupType())){
                activityGroupRequest.setGroupType(GroupTypeEnum.HOUSE.getCode());
            }
            String resultJson = groupService.listGroupByType(JsonEntityTransform.Object2Json(activityGroupRequest));
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
            if(dto.getCode()==DataTransferObject.ERROR){
                LogUtil.error(LOGGER,"【groupList】查询分组列表失败,errMsg:{}",dto.getMsg());
                return pageResult;
            }
            List<GroupEntity> groupEntities = SOAResParseUtil.getListValueFromDataByKey(resultJson,"list",GroupEntity.class);
            Long total =SOAResParseUtil.getLongFromDataByKey(resultJson,"total");
            pageResult.setRows(groupEntities);
            pageResult.setTotal(total);
        }catch (Exception e){
            LogUtil.error(LOGGER,"【groupList】分页查询分组失败,e:{}",e);
        }
        return pageResult;
    }


    /**
      * @description: 新增分组
      * @author: lusp
      * @date: 2017/10/17 下午 17:50
      * @params: groupEntity,request
      * @return: DataTransferObject
      */
    @RequestMapping("addGroup")
    @ResponseBody
    public DataTransferObject addGroup(GroupEntity groupEntity,HttpServletRequest request){
        DataTransferObject dto = new DataTransferObject();
        try {
            CurrentuserVo currentuserVo = UserUtil.getFullCurrentUser();
//            groupEntity.setGroupType(GroupTypeEnum.HOUSE.getCode());
            groupEntity.setCreateFid(currentuserVo.getEmployeeFid());
            groupEntity.setCreateName(currentuserVo.getFullName());
            String result = groupService.saveGroup(JsonEntityTransform.Object2Json(groupEntity));
            dto = JsonEntityTransform.json2DataTransferObject(result);
        }catch (Exception e){
            LogUtil.error(LOGGER,"【addGroup】新增分组异常,e:{}",e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常");
        }
        return dto;
    }

    /**
     * @description: 删除组
     * @author: lusp
     * @date: 2017/10/18 下午 10:18
     * @params: groupEntity,request
     * @return: DataTransferObject
     */
    @RequestMapping("deleteGroup")
    @ResponseBody
    public DataTransferObject deleteGroup(GroupEntity groupEntity,HttpServletRequest request){
        DataTransferObject dto = new DataTransferObject();
        try {
            if(Check.NuNObj(groupEntity)||Check.NuNObj(groupEntity.getId())){
                dto.setMsg("参数错误");
                dto.setErrCode(DataTransferObject.ERROR);
                return dto;
            }
            String result = groupService.deleteGroup(JsonEntityTransform.Object2Json(groupEntity));
            dto = JsonEntityTransform.json2DataTransferObject(result);
        }catch (Exception e){
            LogUtil.error(LOGGER,"【deleteGroup】删除组异常,e:{}",e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常");
        }
        return dto;
    }

    /**
     * @description: 删除房源组成员
     * @author: lusp
     * @date: 2017/10/18 下午 10:18
     * @params: groupHouseRelEntity,request
     * @return: DataTransferObject
     */
    @RequestMapping("deleteHouseRel")
    @ResponseBody
    public DataTransferObject deleteHouseRel(GroupHouseRelEntity groupHouseRelEntity,HttpServletRequest request){
        DataTransferObject dto = new DataTransferObject();
        try {
            if(Check.NuNObj(groupHouseRelEntity)||Check.NuNObj(groupHouseRelEntity.getId())){
                dto.setMsg("参数错误");
                dto.setErrCode(DataTransferObject.ERROR);
                return dto;
            }
            String result = groupService.deleteHouseRel(JsonEntityTransform.Object2Json(groupHouseRelEntity));
            dto = JsonEntityTransform.json2DataTransferObject(result);
        }catch (Exception e){
            LogUtil.error(LOGGER,"【deleteHouseRel】删除组成员异常,e:{}",e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常");
        }
        return dto;
    }

    /**
     * @description: 跳转房源组列表页面
     * @author: lusp
     * @date: 2017/10/18 下午 14:46
     * @params: request
     * @return: ModelAndView
     */
    @RequestMapping("toGroupHouseList")
    public ModelAndView toGroupHouseList(GroupRequest groupRequest,HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/activity/activityGroupHouseList");
        mv.addObject("groupFid",groupRequest.getGroupFid());
        return mv;
    }

    /**
     * @description: 分页查询房源组详情列表
     * @author: lusp
     * @date: 2017/10/18 下午 15:18
     * @params: request
     * @return: PageResult
     */
    @RequestMapping("groupHouseList")
    @ResponseBody
    public PageResult groupHouseList(GroupRequest groupRequest, HttpServletRequest request) {
        PageResult pageResult = new PageResult();
        try {
            String resultJson = groupService.listHouseRelByPage(JsonEntityTransform.Object2Json(groupRequest));
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
            if(dto.getCode()==DataTransferObject.ERROR){
                LogUtil.error(LOGGER,"【groupHouseList】分页查询房源组关系失败,errMsg:{}",dto.getMsg());
                return pageResult;
            }
            List<GroupHouseRelEntity> groupHouseRelEntities = SOAResParseUtil.getListValueFromDataByKey(resultJson,"list",GroupHouseRelEntity.class);

            //根据项目id,户型id,房间id组装对应的名字
            String groupHouseRelEntitiesJson = projectService.assembleGroupHouseRelEntitys(JsonEntityTransform.Object2Json(groupHouseRelEntities));
            DataTransferObject groupHouseRelEntitiesDto = JsonEntityTransform.json2DataTransferObject(groupHouseRelEntitiesJson);
            if(groupHouseRelEntitiesDto.getCode()==DataTransferObject.SUCCESS){
                groupHouseRelEntities = SOAResParseUtil.getListValueFromDataByKey(groupHouseRelEntitiesJson,"groupHouseRelEntities",GroupHouseRelEntity.class);
            }else {
                LogUtil.error(LOGGER,"【groupHouseList】调用自如寓基础服务查询对应项目名称、户型、房间编号失败,errMsg:{}",groupHouseRelEntitiesDto.getMsg());
            }
            Long total =SOAResParseUtil.getLongFromDataByKey(resultJson,"total");
            pageResult.setRows(groupHouseRelEntities);
            pageResult.setTotal(total);
        }catch (Exception e){
            LogUtil.error(LOGGER,"【groupHouseList】分页查询房源组关系失败,e:{}",e);
        }
        return pageResult;
    }

    /**
     * @description: 跳转房源组添加项目页面
     * @author: lusp
     * @date: 2017/10/19 下午 14:46
     * @params: request
     * @return: ModelAndView
     */
    @RequestMapping("toAddProject")
    public ModelAndView toAddProject(GroupRequest groupRequest,HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/activity/activityAddProject");
        mv.addObject("groupFid",groupRequest.getGroupFid());
        try {
            //查询自如寓城市集合
            List<CityEntity> cityEntities = null;
            String cityEntitiesJson = cityService.findCityList();
            DataTransferObject cityEntitiesDto = JsonEntityTransform.json2DataTransferObject(cityEntitiesJson);
            if(cityEntitiesDto.getCode()==DataTransferObject.SUCCESS){
                cityEntities = SOAResParseUtil.getListValueFromDataByKey(cityEntitiesJson,"cityEntities",CityEntity.class);
                mv.addObject("cityList",cityEntities);
            }else{
                LogUtil.error(LOGGER,"【toAddProject】查询自如寓城市集合失败,errMsg:{}",cityEntitiesDto.getMsg());
            }
        }catch (Exception e){
            LogUtil.error(LOGGER,"【toAddProject】查询自如寓城市集合失败,e:{}",e);
        }
        return mv;
    }

    /**
     * @description: 跳转房源组添加户型页面
     * @author: lusp
     * @date: 2017/10/19 下午 19:46
     * @params: request
     * @return: ModelAndView
     */
    @RequestMapping("toAddLayout")
    public ModelAndView toAddLayout(GroupRequest groupRequest,HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/activity/activityAddLayout");
        mv.addObject("groupFid",groupRequest.getGroupFid());
        try {
            //查询自如寓城市集合
            List<CityEntity> cityEntities = null;
            String cityEntitiesJson = cityService.findCityList();
            DataTransferObject cityEntitiesDto = JsonEntityTransform.json2DataTransferObject(cityEntitiesJson);
            if(cityEntitiesDto.getCode()==DataTransferObject.SUCCESS){
                cityEntities = SOAResParseUtil.getListValueFromDataByKey(cityEntitiesJson,"cityEntities",CityEntity.class);
                mv.addObject("cityList",cityEntities);
            }else{
                LogUtil.error(LOGGER,"【toAddLayout】查询自如寓城市集合失败,errMsg:{}",cityEntitiesDto.getMsg());
            }
        }catch (Exception e){
            LogUtil.error(LOGGER,"【toAddLayout】查询自如寓城市集合失败,e:{}",e);
        }
        return mv;
    }

    /**
     * @description: 跳转房源组添加房间页面
     * @author: lusp
     * @date: 2017/10/19 下午 20:23
     * @params: request
     * @return: ModelAndView
     */
    @RequestMapping("toAddRoom")
    public ModelAndView toAddRoom(GroupRequest groupRequest,HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/activity/activityAddRoom");
        mv.addObject("groupFid",groupRequest.getGroupFid());
        try {
            //查询自如寓城市集合
            List<CityEntity> cityEntities = null;
            String cityEntitiesJson = cityService.findCityList();
            DataTransferObject cityEntitiesDto = JsonEntityTransform.json2DataTransferObject(cityEntitiesJson);
            if(cityEntitiesDto.getCode()==DataTransferObject.SUCCESS){
                cityEntities = SOAResParseUtil.getListValueFromDataByKey(cityEntitiesJson,"cityEntities",CityEntity.class);
                mv.addObject("cityList",cityEntities);
            }else{
                LogUtil.error(LOGGER,"【toAddRoom】查询自如寓城市集合失败,errMsg:{}",cityEntitiesDto.getMsg());
            }
        }catch (Exception e){
            LogUtil.error(LOGGER,"【toAddRoom】查询自如寓城市集合失败,e:{}",e);
        }
        return mv;
    }

    /**
     * @description: 分页查询项目列表
     * @author: lusp
     * @date: 2017/10/19 下午 15:22
     * @params: addHouseGroupDto,request
     * @return: PageResult
     */
    @RequestMapping("projectList")
    @ResponseBody
    public PageResult projectList(AddHouseGroupDto addHouseGroupDto, HttpServletRequest request) {
        PageResult pageResult = new PageResult();
        try {
            String resultJson = projectService.findProjectListForPage(JsonEntityTransform.Object2Json(addHouseGroupDto));
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
            if(dto.getCode()==DataTransferObject.ERROR){
                LogUtil.error(LOGGER,"【projectList】调用自如寓基础服务分页查询项目信息失败,errMsg:{}",dto.getMsg());
                return pageResult;
            }
            pageResult.setRows(SOAResParseUtil.getListValueFromDataByKey(resultJson,"list", ProjectEntity.class));
            pageResult.setTotal(SOAResParseUtil.getLongFromDataByKey(resultJson,"total"));
        }catch (Exception e){
            LogUtil.error(LOGGER,"【projectList】调用自如寓基础服务分页查询项目信息失败,e:{}",e);
        }
        return pageResult;
    }

    /**
     * @description: 分页查询户型列表
     * @author: lusp
     * @date: 2017/10/19 下午 19:22
     * @params: addHouseGroupDto,request
     * @return: PageResult
     */
    @RequestMapping("layoutList")
    @ResponseBody
    public PageResult layoutList(AddHouseGroupDto addHouseGroupDto, HttpServletRequest request) {
        PageResult pageResult = new PageResult();
        try {
            String resultJson = houseTypeService.findLayoutListForPage(JsonEntityTransform.Object2Json(addHouseGroupDto));
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
            if(dto.getCode()==DataTransferObject.ERROR){
                LogUtil.error(LOGGER,"【layoutList】调用自如寓基础服务分页查询户型信息失败,errMsg:{}",dto.getMsg());
                return pageResult;
            }
            pageResult.setRows(SOAResParseUtil.getListValueFromDataByKey(resultJson,"list", AddHouseGroupVo.class));
            pageResult.setTotal(SOAResParseUtil.getLongFromDataByKey(resultJson,"total"));
        }catch (Exception e){
            LogUtil.error(LOGGER,"【layoutList】调用自如寓基础服务分页查询户型信息失败,e:{}",e);
        }
        return pageResult;
    }

    /**
     * @description: 分页查询楼栋列表
     * @author: lusp
     * @date: 2017/11/1 下午 16:22
     * @params: addHouseGroupDto,request
     * @return: PageResult
     */
    @RequestMapping("buildingList")
    @ResponseBody
    public PageResult buildingList(AddHouseGroupDto addHouseGroupDto, HttpServletRequest request) {
        PageResult pageResult = new PageResult();
        try {
            String resultJson = projectService.findBuildingListForPage(JsonEntityTransform.Object2Json(addHouseGroupDto));
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
            if(dto.getCode()==DataTransferObject.ERROR){
                LogUtil.error(LOGGER,"【buildingList】调用自如寓基础服务分页查询楼栋信息失败,errMsg:{}",dto.getMsg());
                return pageResult;
            }
            pageResult.setRows(SOAResParseUtil.getListValueFromDataByKey(resultJson,"list", AddHouseGroupVo.class));
            pageResult.setTotal(SOAResParseUtil.getLongFromDataByKey(resultJson,"total"));
        }catch (Exception e){
            LogUtil.error(LOGGER,"【buildingList】调用自如寓基础服务分页查询楼栋信息失败,e:{}",e);
        }
        return pageResult;
    }

    /**
     * @description: 分页查询房间列表
     * @author: lusp
     * @date: 2017/10/19 下午 20:30
     * @params: addHouseGroupDto,request
     * @return: PageResult
     */
    @RequestMapping("roomList")
    @ResponseBody
    public PageResult roomList(AddHouseGroupDto addHouseGroupDto, HttpServletRequest request) {
        PageResult pageResult = new PageResult();
        try {
            String resultJson = roomService.findRoomListForPage(JsonEntityTransform.Object2Json(addHouseGroupDto));
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
            if(dto.getCode()==DataTransferObject.ERROR){
                LogUtil.error(LOGGER,"【roomList】调用自如寓基础服务分页查询房间信息失败,errMsg:{}",dto.getMsg());
                return pageResult;
            }
            pageResult.setRows(SOAResParseUtil.getListValueFromDataByKey(resultJson,"list", AddHouseGroupVo.class));
            pageResult.setTotal(SOAResParseUtil.getLongFromDataByKey(resultJson,"total"));
        }catch (Exception e){
            LogUtil.error(LOGGER,"【roomList】调用自如寓基础服务分页查询房间信息失败,e:{}",e);
        }
        return pageResult;
    }

    /**
     * @description: 批量增加房源组
     * @author: lusp
     * @date: 2017/10/20 下午 09:50
     * @params: groupEntity,request
     * @return: DataTransferObject
     */
    @RequestMapping("addGroupHouseRel")
    @ResponseBody
    public DataTransferObject addGroupHouseRel(String param, HttpServletRequest request){
        DataTransferObject dto = new DataTransferObject();
        try {
            GroupHouseRelRequest groupHouseRelRequest = JsonEntityTransform.json2Object(param,GroupHouseRelRequest.class);
            if(Check.NuNObj(groupHouseRelRequest)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数错误");
            }
            List<GroupHouseRelEntity> groupHouseRelEntities = groupHouseRelRequest.getGroupHouseRelEntities();
            if(Check.NuNCollection(groupHouseRelEntities)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数错误");
            }
            CurrentuserVo currentuserVo = UserUtil.getFullCurrentUser();
            for(GroupHouseRelEntity groupHouseRelEntity:groupHouseRelEntities){
                groupHouseRelEntity.setCreateFid(currentuserVo.getEmployeeFid());
                groupHouseRelEntity.setCreateName(currentuserVo.getFullName());
            }
            String result = groupService.addHouseRelBatch(JsonEntityTransform.Object2Json(groupHouseRelRequest));
            dto = JsonEntityTransform.json2DataTransferObject(result);
        }catch (Exception e){
            LogUtil.error(LOGGER,"【addGroupHouseRel】新增房源分组异常,e:{}",e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常");
        }
        return dto;
    }

    /**
     * 跳转组用户 管理页
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年10月17日 16:17:07
     */
    @RequestMapping("/toGroupUserList")
    public ModelAndView toGroupUserList(String groupFid, ModelAndView modelAndView) {
        modelAndView.setViewName("/activity/groupUserList");
        modelAndView.addObject("groupFid", groupFid);
        return modelAndView;
    }


    /**
     * 组用户关系列表
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年10月17日 16:12:13
     */
    @RequestMapping("/groupUserList")
    @ResponseBody
    public PageResult groupUserList(GroupRequest groupRequest) {
        String resultJson = groupService.listUserRelByPage(JsonEntityTransform.Object2Json(groupRequest));
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
        PageResult pageResult = new PageResult();
        if (resultDto.getCode() == DataTransferObject.SUCCESS) {
            List<GroupUserRelEntity> list = resultDto.parseData("list", new TypeReference<List<GroupUserRelEntity>>() {
            });
            pageResult.setRows(list);
            pageResult.setTotal(Long.valueOf(resultDto.getData().get("total").toString()));
        }
        return pageResult;
    }


    /**
     * 删除组内活动
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年10月17日 17:48:07
     */
    @RequestMapping("/delGroupUserRel")
    @ResponseBody
    public String delGroupUserRel(String fids) {
        return groupService.deleteUserRelBatch(fids);
    }

    /**
     * 添加用户组成员
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年10月18日 09:50:43
     */
    @RequestMapping("/addGroupUserRel")
    @ResponseBody
    public String addGroupUserRel(GroupUserRelEntity groupUserRelEntity) {
        CurrentuserVo fullCurrentUser = UserUtil.getFullCurrentUser();
        groupUserRelEntity.setCreateFid(fullCurrentUser.getEmployeeFid());
        groupUserRelEntity.setCreateName(fullCurrentUser.getFullName());
        return groupService.saveUserRel(JsonEntityTransform.Object2Json(groupUserRelEntity));
    }


    /**
     * 查询用户信息
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年10月18日 10:56:47
     */
    @RequestMapping("/searchUser")
    @ResponseBody
    public PageResult searchUser(String userName, String phone) {
        String url = PASSPORT_USER_BASE_URL + "?real_name=" + userName + "&phone=" + phone;
        String result = CloseableHttpsUtil.sendGet(url, null);

        LogUtil.info(LOGGER, "返回结果result={}", result);
        PageResult pageResult = new PageResult();
        JSONObject resultObj = JSONObject.parseObject(result);
        String status = resultObj.getString("status");
        if ("success".equals(status)) {
            JSONArray data = resultObj.getJSONArray("data");
            pageResult.setRows(data);
            pageResult.setTotal((long) data.size());
        }
        return pageResult;
    }



}
