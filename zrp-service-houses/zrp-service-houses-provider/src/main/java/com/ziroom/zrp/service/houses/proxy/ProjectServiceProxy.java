package com.ziroom.zrp.service.houses.proxy;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.houses.entity.*;
import com.ziroom.zrp.service.houses.api.ProjectService;
import com.ziroom.zrp.service.houses.dto.AddHouseGroupDto;
import com.ziroom.zrp.service.houses.entity.AddHouseGroupVo;
import com.ziroom.zrp.service.houses.entity.HouseTypeVo;
import com.ziroom.zrp.service.houses.entity.ProjectHouseTypeVo;
import com.ziroom.zrp.service.houses.service.BuildingInfoServiceImpl;
import com.ziroom.zrp.service.houses.service.HouseTypeServiceImpl;
import com.ziroom.zrp.service.houses.service.ProjectServiceImpl;
import com.ziroom.zrp.service.houses.service.RoomInfoServiceImpl;
import com.ziroom.zrp.service.houses.valenum.MeterTypeEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>代理层</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月07日 09:22
 * @since 1.0
 */
@Component("houses.projectServiceProxy")
public class ProjectServiceProxy implements ProjectService{

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectServiceProxy.class);

    @Resource(name = "houses.projectServiceImpl")
    private ProjectServiceImpl projectServiceImpl;

    @Resource(name = "houses.houseTypeServiceImpl")
    private HouseTypeServiceImpl houseTypeServiceImpl;

    @Resource(name = "houses.roomInfoServiceImpl")
    private RoomInfoServiceImpl roomInfoServiceImpl;

    @Resource(name = "houses.buildingInfoServiceImpl")
    private BuildingInfoServiceImpl buildingInfoServiceImpl;

    @Override
    public String findProjectByCode(String code) {
        LogUtil.info(LOGGER,"【findProjectByCode】参数={}",code);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(code)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        ProjectEntity projectEntity = projectServiceImpl.findProjectByCode(code);
        dto.putValue("project",projectEntity);
        return dto.toJsonString();
    }
    /**
	 * 根据项目标识查询项目信息
	 * @author xiangb
	 * @created 2017年9月13日
	 * @param projectId 项目ID
	 * @return
     */
	@Override
	public String findProjectById(String projectId) {
		LogUtil.info(LOGGER, "【findProjectById】项目查询入参：{}", projectId);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(projectId)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
		ProjectEntity projectEntity = projectServiceImpl.findProjectById(projectId);
		LogUtil.info(LOGGER, "【findProjectById】项目查询返回信息：{}", JSONObject.toJSONString(projectEntity));
		dto.putValue("projectEntity", projectEntity);
		return dto.toJsonString();
	}

    @Override
    public String findCostStandardByProjectId(String projectId) {
	    LogUtil.info(LOGGER,"【findCostStandardByProjectId】project={}",projectId);
	    DataTransferObject dto = new DataTransferObject();
	    if (Check.NuNStr(projectId)){
	        dto.setErrCode(DataTransferObject.ERROR);
	        dto.setMsg("参数为空");
	        return dto.toJsonString();
        }
        List<CostStandardEntity> list = projectServiceImpl.findCostStandardByProjectId(projectId);

	    double waterPrice = 0.0;
        double electricityPrice = 0.0;
        if (!Check.NuNCollection(list)){
            for (CostStandardEntity costStandardEntity : list){
                if (costStandardEntity.getFmetertype().equals(String.valueOf(MeterTypeEnum.WATER.getCode()))){
                    waterPrice = costStandardEntity.getFprice();
                }
                if (costStandardEntity.getFmetertype().equals(String.valueOf(MeterTypeEnum.ELECTRICITY.getCode()))){
                    electricityPrice = costStandardEntity.getFprice();
                }
            }
        }
	    dto.putValue("waterPrice",waterPrice);
        dto.putValue("electricityPrice",electricityPrice);
	    dto.putValue("list",list);
	    LogUtil.info(LOGGER,"【findCostStandardByProjectId】結果={}",dto.toJsonString());
        return dto.toJsonString();
    }

    /**
     *
     * 根据项目id和水电类型 更新水电费标准
     *
     * @author zhangyl2
     * @created 2018年02月09日 16:20
     * @param
     * @return
     */
    @Override
    public String updateCostStandard(String paramJson) {
        LogUtil.info(LOGGER, "【updateCostStandard】paramJson={}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        CostStandardEntity costStandardEntity = JsonEntityTransform.json2Entity(paramJson, CostStandardEntity.class);
        if (Check.NuNObj(costStandardEntity) ||
                Check.NuNStr(costStandardEntity.getProjectid()) ||
                Check.NuNStr(costStandardEntity.getFmetertype())) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        int result = projectServiceImpl.updateCostStandard(costStandardEntity);
        dto.putValue("result", result);
        LogUtil.info(LOGGER, "【updateCostStandard】結果={}", dto.toJsonString());
        return dto.toJsonString();
    }

    /**
     *
     * 更新项目表
     *
     * @author zhangyl2
     * @created 2018年02月09日 17:30
     * @param
     * @return
     */
    @Override
    public String updateProjectByFid(String paramJson){
        LogUtil.info(LOGGER, "【updateProjectByFid】paramJson={}", paramJson);
        DataTransferObject dto = new DataTransferObject();

        ProjectEntity projectEntity = JsonEntityTransform.json2Entity(paramJson, ProjectEntity.class);
        if(Check.NuNObj(projectEntity) ||
                Check.NuNStr(projectEntity.getFid())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }

        int result = projectServiceImpl.updateProjectByFid(projectEntity);
        dto.putValue("result", result);
        LogUtil.info(LOGGER, "【updateProjectByFid】結果={}", dto.toJsonString());
        return dto.toJsonString();
    }

    @Override
    public String assembleGroupHouseRelEntitys(String paramJson) {
        LogUtil.info(LOGGER,"【assembleGroupHouseRelEntitys】paramJson={}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            List<GroupHouseRelEntity> groupHouseRelEntities = JsonEntityTransform.json2List(paramJson,GroupHouseRelEntity.class);
            if(Check.NuNCollection(groupHouseRelEntities)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数不能为空");
                return dto.toJsonString();
            }
            ProjectEntity projectEntity = null;
            HouseTypeEntity houseTypeEntity = null;
            RoomInfoEntity RoomInfoEntity = null;
            for(GroupHouseRelEntity groupHouseRelEntity:groupHouseRelEntities){
                 projectEntity = projectServiceImpl.findProjectById(groupHouseRelEntity.getProjectId());
                 groupHouseRelEntity.setProjectName((Check.NuNObj(projectEntity)||Check.NuNStrStrict(projectEntity.getFname()))?null:projectEntity.getFname());
                 if(!Check.NuNStrStrict(groupHouseRelEntity.getLayoutId())){
                     houseTypeEntity = houseTypeServiceImpl.findHouseTypeById(groupHouseRelEntity.getLayoutId());
                     groupHouseRelEntity.setLayoutName((Check.NuNObj(houseTypeEntity)||Check.NuNStrStrict(houseTypeEntity.getFhousetypename()))?null:houseTypeEntity.getFhousetypename());
                 }
                 if(!Check.NuNStrStrict(groupHouseRelEntity.getRoomId())){
                     RoomInfoEntity = roomInfoServiceImpl.getRoomInfoByFid(groupHouseRelEntity.getRoomId());
                     groupHouseRelEntity.setRoomNumber((Check.NuNObj(RoomInfoEntity)||Check.NuNStrStrict(RoomInfoEntity.getFroomnumber()))?null:RoomInfoEntity.getFroomnumber());
                 }
            }
            dto.putValue("groupHouseRelEntities",groupHouseRelEntities);
        }catch (Exception e){
            LogUtil.error(LOGGER, "【assembleGroupHouseRelEntitys】 error:{},param={}", e, paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }

    @Override
    public String findProjectListForPage(String paramJson) {
        LogUtil.info(LOGGER,"【findProjectListForPage】paramJson={}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            AddHouseGroupDto addHouseGroupDto = JsonEntityTransform.json2Object(paramJson,AddHouseGroupDto.class);
            PagingResult<ProjectEntity> pagingResult = projectServiceImpl.findProjectListForPage(addHouseGroupDto);
            dto.putValue("list",pagingResult.getRows());
            dto.putValue("total",pagingResult.getTotal());
        }catch (Exception e){
            LogUtil.error(LOGGER, "【findProjectListForPage】 error:{},param={}", e, paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }

    @Override
    public String findBuildingListForPage(String paramJson) {
        LogUtil.info(LOGGER,"【findBuildingListForPage】paramJson={}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            AddHouseGroupDto addHouseGroupDto = JsonEntityTransform.json2Object(paramJson,AddHouseGroupDto.class);
            PagingResult<AddHouseGroupVo> pagingResult = buildingInfoServiceImpl.findBuildingListForPage(addHouseGroupDto);
            dto.putValue("list",pagingResult.getRows());
            dto.putValue("total",pagingResult.getTotal());
        }catch (Exception e){
            LogUtil.error(LOGGER, "【findBuildingListForPage】 error:{},param={}", e, paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }

    /**
     *  查询对应项目的对应户型
     *  说明 ：此处 稍后可以加上缓存
     * @author yd
     * @created
     * @param 
     * @return 
     */
    @Override
    public String queryAllPro(String paramJson){

        DataTransferObject dto = new DataTransferObject();

        try {

            List<ProjectHouseTypeVo>  listProjectHouseType = new ArrayList<ProjectHouseTypeVo>();
            AddHouseGroupDto addHouseGroupDto = JsonEntityTransform.json2Object(paramJson,AddHouseGroupDto.class);
            List<AddHouseGroupVo> listHouse  = this.houseTypeServiceImpl.findHouseTypeByCondition(addHouseGroupDto);

            if(!Check.NuNCollection(listHouse)){

                Map<String,List<HouseTypeVo>>  baseMap = new HashMap<String,List<HouseTypeVo>>();

                Map<String,String> projectMap = new HashMap<String,String>();
                for (AddHouseGroupVo addHouseGroupVo:listHouse ) {
                    String projectId = addHouseGroupVo.getProjectid();
                    if(!Check.NuNStr(projectId)){
                        List<HouseTypeVo> list = baseMap.get(projectId);
                        if(Check.NuNCollection(list)){
                            list = new ArrayList<HouseTypeVo>();
                        }

                        HouseTypeVo houseTypeVo = new HouseTypeVo();
                        houseTypeVo.setHouseTypeName(addHouseGroupVo.getHousetypename());
                        houseTypeVo.setHouseTypeId(addHouseGroupVo.getHousetypeid());
                        list.add(houseTypeVo);

                        baseMap.put(projectId,list);
                        String name = projectMap.get(projectId);
                        if(Check.NuNStr(name)){
                            projectMap.put(projectId,addHouseGroupVo.getProjectname());
                        }

                    }
                }

                if(!Check.NuNMap(baseMap)){
                    for (Map.Entry<String,List<HouseTypeVo>> entry:baseMap.entrySet()) {
                        String key = entry.getKey();
                        List<HouseTypeVo> list = entry.getValue();
                        ProjectHouseTypeVo projectHouseTypeVo = new ProjectHouseTypeVo();
                        projectHouseTypeVo.setProjectId(key);
                        projectHouseTypeVo.setProjectName(projectMap.get(key));
                        projectHouseTypeVo.setListHouseTypeVo(list);
                        listProjectHouseType.add(projectHouseTypeVo);
                    }
                }
            }

            dto.putValue("listProjectHouseType",listProjectHouseType);
        }catch (Exception e){
            LogUtil.error(LOGGER,"查询项目错误:paramJson={},e={}",paramJson,e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("查询项目错误");
        }
        return  dto.toJsonString();
    }
	/* (non-Javadoc)
	 * @see com.ziroom.zrp.service.houses.api.ProjectService#userProjectList(java.lang.String)
	 */
	@Override
	public String userProjectList(String empCode) {
		LogUtil.info(LOGGER,"【userProjectList】empCode={}",empCode);
		DataTransferObject dto=new DataTransferObject();
		try {
			dto.putValue("userProjectList", projectServiceImpl.userProjectList(empCode));
		}catch (Exception e){
            LogUtil.error(LOGGER,"查询用户项目权限列表:paramJson={},e={}",empCode,e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("查询用户项目权限列表");
		}
		return dto.toJsonString();
	}

    @Override
    public String getProjectInfoForApp(String projectId) {

        return null;
    }
}
