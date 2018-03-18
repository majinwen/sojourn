package com.zra.house.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zra.cms.entity.CmsHousetype;
import com.zra.cms.entity.dto.CmsAppProjectDto;
import com.zra.cms.entity.dto.CmsAppProjectLabelDto;
import com.zra.cms.entity.dto.CmsAppProjectLabelImgDto;
import com.zra.cms.entity.dto.CmsAppProjectTrafficDto;
import com.zra.cms.entity.dto.CmsAppProjectZspaceImgDto;
import com.zra.cms.logic.CmsAppLogic;
import com.zra.common.constant.ClientRoomStateEnum;
import com.zra.common.constant.ConditionSortEnum;
import com.zra.common.constant.DirectionEnum;
import com.zra.common.constant.FloorEnum;
import com.zra.common.dto.appbase.AppBaseDto;
import com.zra.common.dto.house.BusinessListReturnDto;
import com.zra.common.dto.house.ProjectDto;
import com.zra.common.dto.marketing.MkProjectDto;
import com.zra.common.dto.pricerange.PriceRangeDto;
import com.zra.common.enums.HousetypeLabelTypeEnum;
import com.zra.common.utils.CommonUtil;
import com.zra.common.utils.DateUtil;
import com.zra.common.utils.HtmlTagUtil;
import com.zra.common.utils.PropUtils;
import com.zra.common.utils.ZraApiConst;
import com.zra.house.entity.HouseImgEntity;
import com.zra.house.entity.HouseTypeEntity;
import com.zra.house.entity.ProAndHtInfoEntity;
import com.zra.house.entity.RoomInfoEntity;
import com.zra.house.entity.SearchProjectEntity;
import com.zra.house.entity.dto.AreaDto;
import com.zra.house.entity.dto.CityDto;
import com.zra.house.entity.dto.HouseConfigDto;
import com.zra.house.entity.dto.HouseTypeConditionDto;
import com.zra.house.entity.dto.HouseTypeDetailDto;
import com.zra.house.entity.dto.HouseTypeDto;
import com.zra.house.entity.dto.HouseTypeImgDto;
import com.zra.house.entity.dto.HouseTypeLabDto;
import com.zra.house.entity.dto.HouseTypeReqDto;
import com.zra.house.entity.dto.NewProjectDetailDto;
import com.zra.house.entity.dto.NewProjectListDto;
import com.zra.house.entity.dto.PriceDto;
import com.zra.house.entity.dto.ProDetailTopPicDto;
import com.zra.house.entity.dto.ProDetailZSpaceDto;
import com.zra.house.entity.dto.ProjectAndHouseDto;
import com.zra.house.entity.dto.ProjectDetailDto;
import com.zra.house.entity.dto.ProjectListDto;
import com.zra.house.entity.dto.ProjectListReturnDto;
import com.zra.house.entity.dto.ProjectTelDto;
import com.zra.house.entity.dto.RoomConditionDto;
import com.zra.house.entity.dto.RoomDetailDto;
import com.zra.house.entity.dto.RoomPriceDetailDto;
import com.zra.house.entity.dto.SearchConditionDto;
import com.zra.house.entity.dto.SearchOfHTReturnDto;
import com.zra.house.entity.dto.SearchOfProjectParamDto;
import com.zra.house.entity.dto.SearchOfProjectReturnDto;
import com.zra.house.entity.dto.SearchProjectCondition;
import com.zra.house.entity.dto.SearchReqDto;
import com.zra.house.entity.dto.SearchRoomReqDto;
import com.zra.house.entity.dto.ZSpacePicDto;
import com.zra.house.service.HouseImgService;
import com.zra.house.service.HouseItemsConfigService;
import com.zra.house.service.HouseTypeService;
import com.zra.house.service.ProjectService;
import com.zra.house.service.RoomInfoService;
import com.zra.m.entity.dto.MZOListDto;
import com.zra.m.entity.dto.MZOProDto;
import com.zra.m.entity.dto.ZOLabelDto;
import com.zra.system.entity.CityEntity;
import com.zra.system.entity.EmployeeEntity;
import com.zra.system.entity.UserAccountEntity;
import com.zra.system.logic.CityLogic;
import com.zra.system.logic.EmployeeLogic;
import com.zra.system.logic.UserAccountLogic;
import com.zra.system.service.CityService;
import com.zra.zmconfig.ConfigClient;

/**
 * Author: wangxm113
 * CreateDate: 2016/7/29.
 */
@Component
public class ProjectLogic {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(ProjectLogic.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserAccountLogic userAccountLogic;

    @Autowired
    private EmployeeLogic employeeLogic;

    @Autowired
    private CityService cityService;
    
    @Autowired
    private CityLogic cityLogic;

    @Autowired
    private RoomInfoService roomInfoService;
    
    @Autowired
    private HouseItemsConfigService houseItemsConfigService;

    @Autowired
    private HouseImgService houseImgService;

    @Autowired
    private HouseTypeService houseTypeService;

    @Autowired
    private ConfigClient configClient;

    @Autowired
    private CmsAppLogic cmsAppLogic;
    
    @Autowired
    private PriceRangeLogic priceRangeLogic;
    
    @Autowired
    private RoomInfoLogic roomInfoLogic;

    /**
     * 项目筛选
     *
     * @Author: wangxm113
     * @CreateDate: 2016-07-29
     */
    public ProjectListDto getSearchProject(SearchOfProjectReturnDto dto) {
        String picPrefixUrl = PropUtils.getString(ZraApiConst.PIC_PREFIX_URL);
        ProjectListDto result = new ProjectListDto();
        result.setIfSearchSuc("1");//假设根据条件能查到结果
        List<ProjectAndHouseDto> projectList = new ArrayList<>();
        List<SearchProjectEntity> searchProjectList;
        if (dto.getCity() == null && (dto.getPrice() == null || dto.getPrice().getMinPrice() == null && dto.getPrice().getMaxPrice() == null) && dto.getCheckInTime() == null) {
            //根据选定的筛选条件查询,结果的粒度到房型
            searchProjectList = projectService.getSearchProjectOther();
        } else {
            //根据选定的筛选条件查询,结果的粒度到房型
            searchProjectList = projectService.getSearchProject(dto.getCity(),
                    dto.getPrice() == null ? null : dto.getPrice().getMinPrice(), dto.getPrice() == null ? null : dto.getPrice().getMaxPrice(),
                    dto.getCheckInTime());
            if (searchProjectList.isEmpty()) {//当查不到时，查询所有的
                result.setIfSearchSuc("0");
                result.setSearchFailMsg(PropUtils.getString(ZraApiConst.SEARCH_FAIL_MSG));
                searchProjectList = projectService.getSearchProject(dto.getCity(), null, null, null);
            }
        }
        result.setHtAcc(searchProjectList.size());//符合条件的户型总数
        Map<String, ProjectAndHouseDto> projectMap = new HashMap<>();//项目id、项目信息
        Map<String, Double> proMinPriceMap = new HashMap<>();//项目id、项目最低价格
        ProjectAndHouseDto projectDto = null;
        HouseTypeDto houseTypeDto = null;
        for (SearchProjectEntity entity : searchProjectList) {
            String projectId = entity.getProjectId();
            //房型信息赋值
            houseTypeDto = new HouseTypeDto();
            houseTypeDto.setHtName(entity.getHtName());
            houseTypeDto.setHtArea(entity.getHtArea());
            int avaRoomAcc = entity.getHtAvaRoomAcc();
            houseTypeDto.setHtAvaRoomAcc(avaRoomAcc);
            if (avaRoomAcc > 0) {//即有不同的标签
                List<String> roomTags = new ArrayList<>();
                String[] states = entity.getHtAvaRoomState().split(",");
                for (String state : states) {
                    if ("0".equals(state)) {
                        roomTags.add("可签约");
                    } else if ("8".equals(state)) {
                        roomTags.add("可预订");
                    }
                }

                houseTypeDto.setIsRoomful(1);//是否满房(0:满房; 1:未满)
                houseTypeDto.setRoomTags(roomTags);
            } else {
                houseTypeDto.setRoomFulTag("已满房");
                houseTypeDto.setRoomTags(new ArrayList<String>());
            }
            houseTypeDto.setHtId(entity.getHtId());
            houseTypeDto.setHtImgUrl(entity.getHtImgUrl() == null ? null : picPrefixUrl + entity.getHtImgUrl());
            houseTypeDto.setHtMinPrice(entity.getHtMinPrice());
            houseTypeDto.setHtMaxPrice(entity.getHtMaxPrice());
            //项目信息赋值
            ProjectAndHouseDto p = projectMap.get(projectId);
            if (p == null) {
                projectDto = new ProjectAndHouseDto();
                projectDto.setProjectId(projectId);
                projectDto.setProjectAddr(entity.getProjectAddr());
                projectDto.setProjectName(entity.getProjectName());
                projectDto.setProjectImgUrl(entity.getProjectImgUrl() == null ? null : picPrefixUrl + entity.getProjectImgUrl());
                List<HouseTypeDto> htList = new ArrayList<>();
                htList.add(houseTypeDto);
                projectDto.setHouseTypeList(htList);
                projectMap.put(projectId, projectDto);
            } else {
                p.getHouseTypeList().add(houseTypeDto);
            }
            //项目最低价格
            Double projectMinPrice = proMinPriceMap.get(projectId);
            if (projectMinPrice == null) {
                proMinPriceMap.put(projectId, entity.getHtMinPrice());
            } else {
                projectMinPrice = projectMinPrice > entity.getHtMinPrice() ? entity.getHtMinPrice() : projectMinPrice;
                proMinPriceMap.put(projectId, projectMinPrice);
            }
        }

        //将项目放进list中，同时赋值其项目最低价格
        for (Map.Entry<String, ProjectAndHouseDto> entry : projectMap.entrySet()) {
            String proId = entry.getKey();
            ProjectAndHouseDto d = entry.getValue();
            d.setProjectMinPrice(proMinPriceMap.get(proId));
//            projectList.add(d);
        }

        projectList = sortProject(projectMap);

        result.setProjectAcc(projectMap.size());
        result.setProjectList(projectList);
        return result;
    }

    /**
     * 项目排序
     *
     * @param map
     * @param <T>
     * @return
     */
    private <T> List<T> sortProject(Map<String, T> map){
        List<T> list = new ArrayList<>();
        //按设置的排序
        String projectSort = configClient.get(ZraApiConst.CONS_PROJECT_SORT_KEY, ZraApiConst.CONS_SYSTEMID_ZRA);
        if (projectSort != null && projectSort.length() > 0) {
            String[] projIdStr = projectSort.split(",");
            for (String s : projIdStr) {
                Iterator<Map.Entry<String, T>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, T> entry = iterator.next();
                    if (s.equals(entry.getKey())) {
                        list.add(entry.getValue());
                        iterator.remove();
                    }
                }
            }
        }

        //将没有排序的随便排序
        for (Map.Entry<String, T> entry : map.entrySet()) {
            list.add(entry.getValue());
        }

        return list;
    }

	/**
	 * 根据用户id 获取用户有权限的项目
	 * @param userId 用户id
	 * @param cityId 城市id
	 * @return List<ProjectDto>
	 */
	public List<ProjectDto> getProjectListByUser(String userId, String cityId) {
		List<ProjectDto> projectList  = new ArrayList<ProjectDto>();
		//是否是管理员
		boolean isAdmin = userAccountLogic.isAdmin(userId);
		//管理员有所有项目的权限
		if(isAdmin){
			projectList = projectService.getAllProjectList(cityId);
		}else{ //非管理员具有分配的项目权限
			UserAccountEntity userAccount = userAccountLogic.getUserAccountByUserId(userId);
			if(userAccount!=null && userAccount.getLinkEmployeeId()!=null){
				EmployeeEntity employee = employeeLogic.getEmployeeById(userAccount.getLinkEmployeeId());
				//add by tianxf9 对于重复数据（自如寓中用户数据有重复，并且重复的用户中有的有权限，有的没有权限）
				String empIds = "";
				String empCode = employee.getCode();
				if(empCode!=null&&!"".equals(empCode)) {
					List<EmployeeEntity> employeeEntitys = this.employeeLogic.getEmployeeByCode(empCode);
					if(employeeEntitys!=null&&!employeeEntitys.isEmpty()) {
						for(int i=0,j=employeeEntitys.size();i<j;i++) {
							empIds +="'"+employeeEntitys.get(i).getId()+"'";
							if(i!=j-1) {
								empIds +=",";
							}
						}
					}
					projectList = projectService.getProjectListByEmployeeId(empIds,cityId);
				}
				//end by tianxf9
			}
		}
		return projectList;
	}
	
	public List<ProjectDto> getAllProjectList(String cityId) {
	    return projectService.getAllProjectList(cityId);
	}

    /**
     * 获取查询条件
     * @return
     */
    public SearchConditionDto getSearchCondition(){

        SearchConditionDto searchConditionDto = new SearchConditionDto();

        //获取城市列表(0608首页优化-获取所有城市包括全国) modify by tianxf9
        List<CityEntity> cityEntityList = this.cityLogic.findAllCity2();
        List<CityDto> cityDtoList = cityService.convert(cityEntityList);

        //获取价格最大最小值
        PriceDto priceDto = roomInfoService.findMaxAndMinRoomPrice(null, null,null,null,null,null,null,null,null,null);

        //TODO 获取房屋状态
        List<Map> stateList = new ArrayList<>();
        Map<String, Object> stateMap;
        for(ClientRoomStateEnum clientRoomStateEnum: ClientRoomStateEnum.values()){
            stateMap = new HashMap<>();
            stateMap.put("name",clientRoomStateEnum.getName());
            stateMap.put("state",clientRoomStateEnum.getCode());
            stateList.add(stateMap);
        }

        searchConditionDto.setCityList(cityDtoList);
        searchConditionDto.setPrice(priceDto);
        searchConditionDto.setCheckInTime(stateList);
        //排序
        searchConditionDto.setSortList(ConditionSortEnum.getSortMapList(1));

        return searchConditionDto;
    }
    
    /**
     * 0608自如寓首页优化
     * 获取项目筛选条件接口
     * @author tianxf9
     * @return
     */
    public SearchProjectCondition getSearchProCondition() {
    	
    	SearchProjectCondition searchProjectCondition = new SearchProjectCondition();
        //获取城市列表
        List<CityEntity> cityEntityList = cityLogic.findAllCity2();
        List<CityDto> cityDtoList = cityService.convert(cityEntityList);
        searchProjectCondition.setCityList(cityDtoList);
        
        //获取价格列表
        List<PriceRangeDto> priceRangeDtos = priceRangeLogic.getAllEntitys();
        searchProjectCondition.setPriceRangeList(priceRangeDtos);
        
        //获取所有房间金额的最大最
        Double maxPrice = this.roomInfoLogic.getRoomMaxPrice();
        searchProjectCondition.setUnlimitPrice(maxPrice);
        searchProjectCondition.setUnlimitPriceStr(maxPrice == null ? "0" : CommonUtil.doubleToString(maxPrice));
    	return searchProjectCondition;
    }

    /**
     * 获取房型查询条件
     * @param searchReqDto
     * @return
     */
    public HouseTypeConditionDto getHouseTypeSearchConditon(SearchReqDto searchReqDto){

        HouseTypeConditionDto searchConditionDto = new HouseTypeConditionDto();

        //获取价格最大最小值
        PriceDto priceDto = roomInfoService.findMaxAndMinRoomPrice(searchReqDto.getProjectId(), searchReqDto.getHouseTypeId(), null, null, null, null, null, null, null, null);
        //查询最高和最低面积
        AreaDto roomAreaDto = roomInfoService.findMaxAndMinRoomArea(searchReqDto.getProjectId(), searchReqDto.getHouseTypeId(),null,null,null,null,null,null,null,null);

        //TODO 获取房屋状态
        List<Map> stateList = new ArrayList<>();
        Map<String, Object> stateMap;
        for(ClientRoomStateEnum clientRoomStateEnum: ClientRoomStateEnum.values()){
            stateMap = new HashMap<>();
            stateMap.put("name",clientRoomStateEnum.getName());
            stateMap.put("state",clientRoomStateEnum.getCode());
            stateList.add(stateMap);
        }
        //TODO 获取楼层
        List<Map> floorList = new ArrayList<>();
        Map<String, Object> floorMap;
        for(FloorEnum floorEnum: FloorEnum.values()){
            floorMap = new HashMap<>();
            floorMap.put("name",floorEnum.getName());
            floorMap.put("state",floorEnum.getCode());
            floorList.add(floorMap);
        }
        //TODO 获取朝向
        List<Map> directionList = new ArrayList<>();
        Map<String, Object> directionMap;
        for(DirectionEnum directionEnum: DirectionEnum.values()){
            directionMap = new HashMap<>();
            directionMap.put("name",directionEnum.getName());
            directionMap.put("state",directionEnum.getCode());
            directionList.add(directionMap);
        }

        searchConditionDto.setPrice(priceDto);
        searchConditionDto.setArea(roomAreaDto);
        searchConditionDto.setCheckInTime(stateList);
        searchConditionDto.setDirectionList(directionList);
        searchConditionDto.setFloorNumberList(floorList);
        //排序
        searchConditionDto.setSortList(ConditionSortEnum.getSortMapList(2));

        return searchConditionDto;

    }

    /**
     * 获取房间查询条件
     * @param searchReqDto
     * @return
     */
    public RoomConditionDto getRoomSearchConditon(SearchRoomReqDto searchReqDto){

        RoomConditionDto searchConditionDto = new RoomConditionDto();

        //获取价格最大最小值
        PriceDto priceDto = roomInfoService.findMaxAndMinRoomPrice(null, searchReqDto.getHouseTypeId(),null,null,null,null,null,null,null,null);
        if(priceDto == null){
            priceDto = new PriceDto();
        }
        //查询最高和最低面积
        AreaDto roomAreaDto = roomInfoService.findMaxAndMinRoomArea(null, searchReqDto.getHouseTypeId(),null,null,null,null,null,null,null,null);

        //TODO 获取房屋状态
        List<Map> stateList = new ArrayList<>();
        Map<String, Object> stateMap;
        for(ClientRoomStateEnum clientRoomStateEnum: ClientRoomStateEnum.values()){
            stateMap = new HashMap<>();
            stateMap.put("name",clientRoomStateEnum.getName());
            stateMap.put("state",clientRoomStateEnum.getCode());
            stateList.add(stateMap);
        }
        //TODO 获取楼层
        List<Map> floorList = new ArrayList<>();
        Map<String, Object> floorMap;
        for(FloorEnum floorEnum: FloorEnum.values()){
            floorMap = new HashMap<>();
            floorMap.put("name",floorEnum.getName());
            floorMap.put("state",floorEnum.getCode());
            floorList.add(floorMap);
        }
        //TODO 获取朝向
        List<Map> directionList = new ArrayList<>();
        Map<String, Object> directionMap;
        for(DirectionEnum directionEnum: DirectionEnum.values()){
            directionMap = new HashMap<>();
            directionMap.put("name",directionEnum.getName());
            directionMap.put("state",directionEnum.getCode());
            directionList.add(directionMap);
        }
        Map roomNum = new HashMap();
        roomNum.put("state",true);
        searchConditionDto.setRoomNumber(roomNum);
        searchConditionDto.setPrice(priceDto);
        searchConditionDto.setArea(roomAreaDto);
        searchConditionDto.setCheckInTime(stateList);
        searchConditionDto.setDirectionList(directionList);
        searchConditionDto.setFloorNumberList(floorList);

        //排序
        searchConditionDto.setSortList(ConditionSortEnum.getSortMapList(3));

        return searchConditionDto;

    }

    /**
     * 获取房间信息
     * @param searchRoomReqDto
     * @return
     */
    public RoomPriceDetailDto getRoomDetailByCondition(SearchRoomReqDto searchRoomReqDto){
        String picPrefixUrl = PropUtils.getString(ZraApiConst.PIC_PREFIX_URL);
        RoomPriceDetailDto roomPriceDetailDto = new RoomPriceDetailDto();
        //房间详情
        List<RoomInfoEntity> roomInfoEntityList;
        PageHelper.startPage(searchRoomReqDto.getPageNum(), searchRoomReqDto.getPageSize());
        roomInfoEntityList = roomInfoService.findRoomInfoByCondition(searchRoomReqDto);
        boolean isFind = false;
        if (roomInfoEntityList.isEmpty()) {
            SearchRoomReqDto searchDto = new SearchRoomReqDto();
            searchDto.setHouseTypeId(searchRoomReqDto.getHouseTypeId());
            PageHelper.startPage(searchRoomReqDto.getPageNum(), searchRoomReqDto.getPageSize());
            roomInfoEntityList = roomInfoService.findRoomInfoByCondition(searchDto);
            isFind = true;
        }

        PageInfo<RoomInfoEntity> pageInfo = new PageInfo<>(roomInfoEntityList);
        List<RoomDetailDto> roomDetailDtoList = roomInfoService.convert(pageInfo.getList());

        roomPriceDetailDto.setTotal(pageInfo.getTotal());
        roomPriceDetailDto.setPageNum(pageInfo.getPageNum());
        roomPriceDetailDto.setPageSize(pageInfo.getPageSize());
        //查询结果价格区间
        if(!CollectionUtils.isEmpty(roomDetailDtoList)){
            PriceDto priceDto;
            if (isFind) {
                priceDto = roomInfoService.findMaxAndMinRoomPriceOther(searchRoomReqDto.getHouseTypeId());
            } else {
                priceDto = roomInfoService.findMaxAndMinRoomPrice(null, searchRoomReqDto.getHouseTypeId(), searchRoomReqDto.getRoomNumber(),
                        searchRoomReqDto.getPrice() == null ? null : searchRoomReqDto.getPrice().getMinPrice(),
                        searchRoomReqDto.getPrice() == null ? null : searchRoomReqDto.getPrice().getMaxPrice(),
                        searchRoomReqDto.getArea() == null ? null : searchRoomReqDto.getArea().getMinArea(),
                        searchRoomReqDto.getArea() == null ? null : searchRoomReqDto.getArea().getMaxArea(),
                        searchRoomReqDto.getCheckInTime(), searchRoomReqDto.getFloor(), searchRoomReqDto.getDirection());
            }
//            List<RoomDetailDto> roomDetailDtoList = roomInfoService.convert(roomInfoEntityList);
//            List<Double> priceList = new LinkedList<>();
//            for (RoomDetailDto roomDetailDto: roomDetailDtoList){
//                priceList.add(roomDetailDto.getLongPrice());
//            }
//            //价格排序
//            Collections.sort(priceList);
//            PriceDto priceDto = new PriceDto();
//            priceDto.setMinPrice(priceList.get(0));
//            priceDto.setMaxPrice(priceList.get(priceList.size() - 1));

            roomPriceDetailDto.setPriceDto(priceDto);
            roomPriceDetailDto.setRoomDetailDtoList(roomDetailDtoList);
        } else {
            PriceDto priceDto = roomInfoService.findMaxAndMinRoomPriceOther(searchRoomReqDto.getHouseTypeId());
            roomPriceDetailDto.setPriceDto(priceDto);
            roomPriceDetailDto.setRoomDetailDtoList(new ArrayList<RoomDetailDto>());
        }
        //for 预约看房
        ProjectListReturnDto proInfo = projectService.getProjectInfoByHtFid(searchRoomReqDto.getHouseTypeId());
        roomPriceDetailDto.setProName(proInfo.getProjName());
        roomPriceDetailDto.setProAddr(proInfo.getProjAddr());
        roomPriceDetailDto.setProHeadPic(picPrefixUrl + proInfo.getProjImgUrl());

        return roomPriceDetailDto;

    }

    /**
     * 条件查询房型信息（包括房型配置信息）
     * @param houseTypeReqDto
     * @return
     */
    public HouseTypeDetailDto findHouseTypeDetail(HouseTypeReqDto houseTypeReqDto) {

    	
        String picPrefixUrl = PropUtils.getString(ZraApiConst.PIC_PREFIX_URL);
        HouseTypeEntity houseTypeEntity = houseTypeService.findHouseTypeById
                (houseTypeReqDto.getHouseTypeId());

        if (houseTypeEntity == null) {
            return null;
        }

        HouseTypeDetailDto houseTypeDetailDto = houseTypeService.convert
                (houseTypeEntity);
        List<HouseImgEntity> houseImgEntityList =
                houseImgService.findImgByHouseTypeId(houseTypeReqDto.getHouseTypeId());

        String[] imgOrder = PropUtils.getString
                (ZraApiConst.HOUSE_DETAIL_IMG_ORDER).split(",");

        //房型图片对象列表
        List<HouseTypeImgDto> houseTypeImgDtoList = new LinkedList<>();
        //类型-图片list-》map
        Map<String, List<String>> houseTypeImgMap = new LinkedHashMap<>();
        for (String imgType : imgOrder) {
            houseTypeImgMap.put(imgType, new ArrayList<String>());
        }

        if (!CollectionUtils.isEmpty(houseImgEntityList)) {
            for (HouseImgEntity houseImgEntity : houseImgEntityList) {
                List<String> list;
                //类型
                String imgTypeTemp = houseImgEntity.getDescription();
                //图片地址
                String imgUrlTemp = houseImgEntity.getHouseImage();
                if (imgTypeTemp != null && imgUrlTemp != null) {
                    String imgType = imgTypeTemp.trim();
                    String imgUrl = imgUrlTemp.trim();
                    if (StringUtils.isNotBlank(imgType) &&
                            StringUtils.isNotBlank(imgUrl)) {
                        imgUrl = picPrefixUrl + imgUrl;
                        if (!houseTypeImgMap.keySet().contains(imgType)) {
                            list = new ArrayList<>();
                            list.add(imgUrl);
                            houseTypeImgMap.put(imgType, list);
                        } else {
                            list = houseTypeImgMap.get(imgType);
                            list.add(imgUrl);
                        }
                    }
                }
            }
            //转换为对象
            HouseTypeImgDto houseTypeImgDto;
            for (String str : houseTypeImgMap.keySet()) {
                List<String> list = houseTypeImgMap.get(str);
                if (list.size() > 0) {
                    houseTypeImgDto = new HouseTypeImgDto();
                    houseTypeImgDto.setImgTypeName(str);
                    houseTypeImgDto.setImgPathList(houseTypeImgMap.get(str));
                    houseTypeImgDtoList.add(houseTypeImgDto);
                }
            }
        }

        //设置房型图片列表
        houseTypeDetailDto.setHouseTypeImgDtoList(houseTypeImgDtoList);

        List<HouseConfigDto> houseConfigDtoList =
                houseItemsConfigService.findItemsConfigByHouseTypeId
                        (houseTypeReqDto.getHouseTypeId());
        //设置物品配置iconURL
        setItemConfigIconUrl(houseConfigDtoList, picPrefixUrl);
        //设置房型配置
        houseTypeDetailDto.setHouseConfigDtoList(houseConfigDtoList);
        setHouseTypeOtherMsg(houseTypeDetailDto, houseTypeEntity);
        return houseTypeDetailDto;
    }
    
    
    /**
     * 设置物品配置url
     * @author tianxf9
     * @param houseConfigDtoList
     * @param picPrefixUrl
     * @return
     */
    public List<HouseConfigDto> setItemConfigIconUrl(List<HouseConfigDto> houseConfigDtoList,String picPrefixUrl) {
    	
    	if(CollectionUtils.isNotEmpty(houseConfigDtoList)) {
    		for(HouseConfigDto configDto:houseConfigDtoList) {
    			if(StringUtils.isNotBlank(configDto.getImgUrl())) {
    				String imgUrl = configDto.getImgUrl().trim();
    				configDto.setImgUrl(picPrefixUrl+imgUrl);
    			}
    		}
    	}
    	
    	return houseConfigDtoList;
    }
    
    /**
     * 0608APP优化户型详情页增加显示项
     * @author tianxf9
     * @param houseTypeDetailDto
     * @param houseTypeEntity
     */
    public void setHouseTypeOtherMsg(HouseTypeDetailDto houseTypeDetailDto, HouseTypeEntity houseTypeEntity) {
    	
        String projectId = houseTypeEntity.getProjectId();
        ProjectDto projectDto = this.projectService.getProjectDtoByProId(projectId);
        houseTypeDetailDto.setProAddrDesc(projectDto.getAddressDesc());
        houseTypeDetailDto.setLat(projectDto.getLat());
        houseTypeDetailDto.setLng(projectDto.getLon());
        CmsHousetype cmsHousetype = this.cmsAppLogic.getCmsHousetypeByHousetypeId(houseTypeEntity.getId());
        //户型介绍，如果营销管理配置了取得就是营销管理配置里的，如果没有配置取基础数据里的
        if(cmsHousetype!=null&&!StringUtils.isBlank(cmsHousetype.getIntroduction())) {
        	houseTypeDetailDto.setRoomIntroduction(cmsHousetype.getIntroduction());
        }
        List<HouseTypeLabDto> coreLab = this.cmsAppLogic.getCmsHousetypeLab(houseTypeEntity.getId(), HousetypeLabelTypeEnum.CORE_LAB.getIndex());
        if(!CollectionUtils.isEmpty(coreLab)) {
        	houseTypeDetailDto.setCoreLab(coreLab.get(0).getContent());
        }
        
        List<HouseTypeLabDto> basicLab = this.cmsAppLogic.getCmsHousetypeLab(houseTypeEntity.getId(), HousetypeLabelTypeEnum.BASIC_LAB.getIndex());
        List<String> baseLabStrs = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(basicLab)) {
        	for(HouseTypeLabDto dto:basicLab) {
        		baseLabStrs.add(dto.getContent());
        	}
        }
        houseTypeDetailDto.setBasicLab(baseLabStrs);
        List<HouseTypeLabDto> activityLab = this.cmsAppLogic.getCmsHousetypeLab(houseTypeEntity.getId(), HousetypeLabelTypeEnum.ACTIVITY_LAB.getIndex());
        houseTypeDetailDto.setActivityLab(activityLab);
        //设置电话
        houseTypeDetailDto.setPhone(projectDto.getMarketTel());
    }

    /**
     * 房型筛选
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-01
     */
    public ProjectDetailDto getSearchHouseType(SearchOfHTReturnDto dto) {
        String picPrefixUrl = PropUtils.getString(ZraApiConst.PIC_PREFIX_URL);
        ProjectDetailDto result = new ProjectDetailDto();
        result.setIfSearchSuc("1");//默认能根据筛选条件查询出来结果
        List<String> proImgList = new ArrayList<>();
        List<HouseTypeDto> htList = new ArrayList<>();
        Double minPrice = null;
        //根据项目id获取除了最低价格、房型信息之外的所有信息（其实可以一次都查询出来，但是感觉不太好，所以分了两次）
        List<ProjectDto> projectInfoList = projectService.getProjectDetailInfo(dto.getProjectId());
        boolean isFirst = true;
        for (ProjectDto d : projectInfoList) {
            if (isFirst) {
                result.setfPanoramicUrl(d.getPanoramicUrl());
                result.setProjectAddr(d.getAddress());
                result.setProjectAroundUrl(d.getPeripheralUrl());
                result.setProjectId(d.getId());
                result.setProjectLat(d.getLat());
                result.setProjectLong(d.getLon());
                result.setProjectShareUrl(d.getShareUrl());
                result.setProjectName(d.getName());
                result.setProjectPhone(d.getMarketTel());
                String peripheralName = d.getPeripheralName();
                if (peripheralName == null || peripheralName.length() <= 0) {
                    result.setProjectAroundName("周边");
                } else {
                    result.setProjectAroundName(peripheralName);
                }
                isFirst = false;
            }
            proImgList.add(d.getHeadFigureUrl() == null ? null : picPrefixUrl + d.getHeadFigureUrl());
        }
        result.setProjectImgUrl(proImgList);

        //根据项目id查询项目下的房型信息，同时处理最低价格
        List<SearchProjectEntity> searchProjectList = null;
        if ((dto.getPrice() == null || dto.getPrice().getMinPrice() == null && dto.getPrice().getMaxPrice() == null) &&
                (dto.getArea() == null || dto.getArea().getMinArea() == null && dto.getArea().getMaxArea() == null) && dto.getCheckInTime() == null
                && dto.getFloor() == null && dto.getDirection() == null) {
            searchProjectList = projectService.getHTInfoByProIdOther(dto.getProjectId());
        } else {
            searchProjectList = projectService.getHTInfoByProId(dto.getProjectId(),
                    dto.getPrice() == null ? null : dto.getPrice().getMinPrice(),
                    dto.getPrice() == null ? null : dto.getPrice().getMaxPrice(),
                    dto.getArea() == null ? null : dto.getArea().getMinArea(),
                    dto.getArea() == null ? null : dto.getArea().getMaxArea(),
                    dto.getCheckInTime(), dto.getFloor(), dto.getDirection());
            if (searchProjectList.isEmpty()) {
                result.setIfSearchSuc("0");
                result.setSearchFailMsg(PropUtils.getString(ZraApiConst.SEARCH_FAIL_MSG));
                searchProjectList = projectService.getHTInfoByProIdOther(dto.getProjectId());
            }
        }
        HouseTypeDto houseTypeDto = null;
        for (SearchProjectEntity entity : searchProjectList) {
            //房型信息赋值
            houseTypeDto = new HouseTypeDto();
            houseTypeDto.setHtName(entity.getHtName());
            houseTypeDto.setHtArea(entity.getHtArea());
            int avaRoomAcc = entity.getHtAvaRoomAcc();
            houseTypeDto.setHtAvaRoomAcc(avaRoomAcc);
            if (avaRoomAcc > 0) {//即有不同的标签
                List<String> roomTags = new ArrayList<>();
                String[] states = entity.getHtAvaRoomState().split(",");
                for (String state : states) {
                    if ("0".equals(state)) {
                        roomTags.add("可签约");
                    } else if ("8".equals(state)) {
                        roomTags.add("可预订");
                    }
                }

                houseTypeDto.setIsRoomful(1);//是否满房(0:满房; 1:未满)
                houseTypeDto.setRoomTags(roomTags);
            } else {
                houseTypeDto.setRoomFulTag("已满房");
                houseTypeDto.setRoomTags(new ArrayList<String>());
            }
            houseTypeDto.setHtId(entity.getHtId());
            houseTypeDto.setHtImgUrl(entity.getHtImgUrl() == null ? null : picPrefixUrl + entity.getHtImgUrl());
            houseTypeDto.setHtMinPrice(entity.getHtMinPrice());
            houseTypeDto.setHtMaxPrice(entity.getHtMaxPrice());
            htList.add(houseTypeDto);
            //项目最低价格
            if (minPrice == null) {
                minPrice = entity.getHtMinPrice();
            } else {
                minPrice = minPrice > entity.getHtMinPrice() ? entity.getHtMinPrice() : minPrice;
            }
        }

        result.setHouseTypeList(htList);
        result.setProjectMinPrice(minPrice);
        return result;
    }
    
    
    /**
     * wangws21 2016-8-3
     * 获取项目下的房型
     * @param projectId 项目id
     * @return 房型列表
     */
    public List<com.zra.common.dto.house.HouseTypeDto> getHouseTypeByProjectId(String projectId) {
		return projectService.getHouseTypeByProjectId(projectId);
	}

    /**
     * 根据项目id获取城市id
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-04
     */
    public String getCityIdByProjectId(String projectId) {
        return projectService.getCityIdByProjectId(projectId);
    }

    /**
     * 查询网站显示户型配置物品
     * @param houseTypeId
     * @return
     */
    public List<HouseConfigDto> findItemsConfigByHouseTypeId(String houseTypeId){
        return houseItemsConfigService.findItemsConfigByHouseTypeId(houseTypeId);
    }

    /**
     * 获取所有项目的项目分机号
     * @author tianxf9
     * @return
     */
    public List<ProjectTelDto> getAllProjectTelMsg() {
    	return projectService.getAllProjectTelMsg();
    }

    /**
	 * wangws21   获取项目信息 2016-8-5
	 * @param projectId 项目id
	 * @return ProjectDto
	 */
	public ProjectDto getProjectById(String projectId) {
		List<ProjectDto> projectList = projectService.getProjectDetailInfo(projectId);
		if(projectList!=null && !projectList.isEmpty())
			return projectList.get(0);
		return null;
	}
	
	/**
	 * 根据项目id获取项目
	 * @author tianxf9
	 * @param projectId
	 * @return
	 */
	public ProjectDto getProjectDtoById(String projectId) {
		return this.projectService.getProjectDtoByProId(projectId);
	}

    /**
     * 获取项目列表
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-08
     */
    public List<ProjectListReturnDto> getProjectList() {
        String picPrefixUrl = PropUtils.getString(ZraApiConst.PIC_PREFIX_URL);
        List<ProjectListReturnDto> list = projectService.getProjectList();
        Map<String, ProjectListReturnDto> map = new HashMap<>();
        for (ProjectListReturnDto dto : list) {
            dto.setProjImgUrl(dto.getProjImgUrl() == null ? null : picPrefixUrl + dto.getProjImgUrl());
            map.put(dto.getProjId(), dto);
        }
        list = sortProject(map);
        return list;
    }
    

	/**
     * for商机列表-查询项目信息
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-24
     */
    public List<BusinessListReturnDto> getProInfoForBusinessList(String bids){
        return projectService.getProInfoForBusinessList(bids);
    }
    
    /**
     * wangws21 2016-8-24
     * 获取房型
     * @param houseTypeId 放心id
     * @return 房型
     */
    public HouseTypeEntity getHouseTypeById(String houseTypeId){
    	return this.houseTypeService.findHouseTypeById(houseTypeId);
    }
   
    public List<ProjectDto> getAllProjectList() {
    	return this.projectService.getAllProjects();
    }

    /**
     * 新项目列表
     *
     * @Author: wangxm113
     * @CreateDate: 2017-02-16
     */
    public List<NewProjectListDto> getNewProjectList(AppBaseDto dto) {
        List<NewProjectListDto> result = new ArrayList<>();
        String picPrefixUrl = PropUtils.getString(ZraApiConst.PIC_PREFIX_URL);
//        List<SearchProjectEntity> searchProjectList = projectService.getSearchProject(dto.getCityCode(), null, null, null);//获取信息
        List<SearchProjectEntity> searchProjectList = projectService.getSearchProjectAnother(dto.getCityCode());//获取信息

        List<ProjectDto> projectDtoList = projectService.getAllProjectList(dto.getCityCode());
        Map<String, Integer> map = new HashMap<>();
        for (SearchProjectEntity entity : searchProjectList) {
            map.put(entity.getProjectId(), 1);
        }
        StringBuilder sb = new StringBuilder();
        for (ProjectDto projectDto : projectDtoList) {
            if (map.get(projectDto.getId()) == null) {
                sb.append("'").append(projectDto.getId()).append("',");
            }
        }
        if (sb.length() > 0) {
            sb = sb.deleteCharAt(sb.length() - 1);
            LOGGER.info("[新-项目列表]----重新查询已满房的项目id:" + sb.toString());
            List<SearchProjectEntity> searchProjectList1 = projectService.getSearchProjectAnotherAgain(dto.getCityCode(), sb.toString());
            LOGGER.info("[新-项目列表]----重新查询已满房的项目个数:" + searchProjectList1.size());
            searchProjectList.addAll(searchProjectList1);
        }
        Map<String, NewProjectListDto> projectMap = new LinkedHashMap<>();//项目id、项目信息
        Map<String, Set<String>> tagsMap = new HashMap<>();//项目id、项目的标签
        Map<String, Double> proMinPriceMap = new HashMap<>();//项目id、项目最低价格
        Map<String, Double> proMaxPriceMap = new HashMap<>();//项目id、项目最高价格
        for (SearchProjectEntity entity : searchProjectList) {
            String projectId = entity.getProjectId();
            NewProjectListDto proDto = projectMap.get(projectId);
            if (proDto == null) {
                NewProjectListDto re = new NewProjectListDto();
                re.setProFid(projectId);
                re.setProCode(entity.getProCode());
                re.setProName(entity.getProjectName());
                re.setProAddr(entity.getProjectAddr());
                re.setLng(entity.getLng());
                re.setLat(entity.getLat());
                re.setHeadPic(picPrefixUrl + entity.getProjectImgUrl());//头图处理
                re.setShowPic(picPrefixUrl + entity.getShowImg());//风采展示
                projectMap.put(projectId, re);
            }
            //标签处理
            Set<String> roomTags = tagsMap.get(projectId);
            if (roomTags == null) {
                roomTags = new HashSet<>();
            }
            if (entity.getHtAvaRoomAcc() > 0) {//即有不同的标签
                String[] states = entity.getHtAvaRoomState().split(",");
                for (String state : states) {
                    if ("0".equals(state)) {
                        roomTags.add("可签约");
                    } else if ("8".equals(state)) {
                        roomTags.add("可预订");
                    }
                }
            } else {
                roomTags.add("已满房");
            }
            tagsMap.put(projectId, roomTags);
            //项目最低价格
            Double projectMinPrice = proMinPriceMap.get(projectId);
            Double min = entity.getHtMinPrice() == null ? 0D : entity.getHtMinPrice();
            if (projectMinPrice == null) {
                proMinPriceMap.put(projectId, min);
            } else {
                projectMinPrice = projectMinPrice > min ? min : projectMinPrice;
                proMinPriceMap.put(projectId, projectMinPrice);
            }
            //项目最高价格
            Double projectMaxPrice = proMaxPriceMap.get(projectId);
            Double max = entity.getHtMaxPrice() == null ? 0D : entity.getHtMaxPrice();
            if (projectMaxPrice == null) {
                proMaxPriceMap.put(projectId, entity.getHtMaxPrice());
            } else {
                projectMaxPrice = projectMaxPrice < max ? max : projectMaxPrice;
                proMaxPriceMap.put(projectId, projectMaxPrice);
            }
        }

        //标签、最小价格、最高价格
        for (Map.Entry<String, NewProjectListDto> entry : projectMap.entrySet()) {
            String projectId = entry.getKey();
            NewProjectListDto value = entry.getValue();
            Set<String> strings = tagsMap.get(projectId);
            value.setTagList(strings);
            if (strings.contains("已满房")) {
                value.setTagFlag("1");
            }
            Double min = proMinPriceMap.get(projectId);
            Double max = proMaxPriceMap.get(projectId);
            value.setMinPrice(min == null ? "0" : CommonUtil.doubleToString(min));
            value.setMaxPrice(max == null ? "0" : CommonUtil.doubleToString(max));
            result.add(value);
        }
        return result;
    }
    
    
    /**
     * 
     * 根据条件查询项目列表
     * @Author: tianxf9
     * @CreateDate: 2017-02-16
     */
    public List<NewProjectListDto> queryProjectByCondition(SearchOfProjectParamDto dto) {
        List<NewProjectListDto> result = new ArrayList<>();
        String picPrefixUrl = PropUtils.getString(ZraApiConst.PIC_PREFIX_URL);
        String checkInTime = null;
        if(!StringUtils.isBlank(dto.getCheckInTime())) {
        	checkInTime = DateUtil.getDayBeginTime(dto.getCheckInTime());
        }
        List<SearchProjectEntity> searchProjectList = projectService.getSearchProject2(dto.getCity(),
                dto.getMinPrice(), dto.getMaxPrice(),checkInTime);
        List<ProjectDto> projectDtoList = projectService.getAllProjectList(dto.getCity());
        Map<String, Integer> map = new HashMap<>();
        for (SearchProjectEntity entity : searchProjectList) {
            map.put(entity.getProjectId(), 1);
        }
        StringBuilder sb = new StringBuilder();
        for (ProjectDto projectDto : projectDtoList) {
            if (map.get(projectDto.getId()) == null) {
                sb.append("'").append(projectDto.getId()).append("',");
            }
        }
        if (sb.length() > 0) {
            sb = sb.deleteCharAt(sb.length() - 1);
            LOGGER.info("[新-项目列表]----重新查询已满房的项目id:" + sb.toString());
            List<SearchProjectEntity> searchProjectList1 = projectService.getSearchProjectAnotherAgain(dto.getCity(), sb.toString());
            LOGGER.info("[新-项目列表]----重新查询已满房的项目个数:" + searchProjectList1.size());
            searchProjectList.addAll(searchProjectList1);
        }
        Map<String, NewProjectListDto> projectMap = new LinkedHashMap<>();//项目id、项目信息
        Map<String, Set<String>> tagsMap = new HashMap<>();//项目id、项目的标签
        Map<String, Double> proMinPriceMap = new HashMap<>();//项目id、项目最低价格
        Map<String, Double> proMaxPriceMap = new HashMap<>();//项目id、项目最高价格
        for (SearchProjectEntity entity : searchProjectList) {
            String projectId = entity.getProjectId();
            NewProjectListDto proDto = projectMap.get(projectId);
            if (proDto == null) {
                NewProjectListDto re = new NewProjectListDto();
                re.setProFid(projectId);
                re.setProCode(entity.getProCode());
                re.setProName(entity.getProjectName());
                re.setProAddr(entity.getProjectAddr());
                re.setLng(entity.getLng());
                re.setLat(entity.getLat());
                re.setHeadPic(picPrefixUrl + entity.getProjectImgUrl());//头图处理
                re.setShowPic(picPrefixUrl + entity.getShowImg());//风采展示
                projectMap.put(projectId, re);
            }
            //标签处理
            Set<String> roomTags = tagsMap.get(projectId);
            if (roomTags == null) {
                roomTags = new HashSet<>();
            }
            if (entity.getHtAvaRoomAcc() > 0) {//即有不同的标签
                String[] states = entity.getHtAvaRoomState().split(",");
                for (String state : states) {
                    if ("0".equals(state)) {
                        roomTags.add("可签约");
                    } else if ("8".equals(state)) {
                        roomTags.add("可预订");
                    }
                }
            } else {
                roomTags.add("已满房");
            }
            tagsMap.put(projectId, roomTags);
            //项目最低价格
            Double projectMinPrice = proMinPriceMap.get(projectId);
            Double min = entity.getHtMinPrice() == null ? 0D : entity.getHtMinPrice();
            if (projectMinPrice == null) {
                proMinPriceMap.put(projectId, min);
            } else {
                projectMinPrice = projectMinPrice > min ? min : projectMinPrice;
                proMinPriceMap.put(projectId, projectMinPrice);
            }
            //项目最高价格
            Double projectMaxPrice = proMaxPriceMap.get(projectId);
            Double max = entity.getHtMaxPrice() == null ? 0D : entity.getHtMaxPrice();
            if (projectMaxPrice == null) {
                proMaxPriceMap.put(projectId, entity.getHtMaxPrice());
            } else {
                projectMaxPrice = projectMaxPrice < max ? max : projectMaxPrice;
                proMaxPriceMap.put(projectId, projectMaxPrice);
            }
        }

        //标签、最小价格、最高价格
        for (Map.Entry<String, NewProjectListDto> entry : projectMap.entrySet()) {
            String projectId = entry.getKey();
            NewProjectListDto value = entry.getValue();
            Set<String> strings = tagsMap.get(projectId);
            value.setTagList(strings);
            if (strings.contains("已满房")) {
                value.setTagFlag("1");
            }
            Double min = proMinPriceMap.get(projectId);
            Double max = proMaxPriceMap.get(projectId);
            value.setMinPrice(min == null ? "0" : CommonUtil.doubleToString(min));
            value.setMaxPrice(max == null ? "0" : CommonUtil.doubleToString(max));
            result.add(value);
        }
        return result;
    }
    
    /**
     * 新项目详情页
     *
     * @Author: wangxm113
     * @CreateDate: 2017-02-16
     */
    public NewProjectDetailDto getNewProjectDetail(SearchReqDto dto) {
        NewProjectDetailDto result = new NewProjectDetailDto();
        String picPrefixUrl = PropUtils.getString(ZraApiConst.PIC_PREFIX_URL);
        CmsAppProjectDto cmsAppProjectDto = cmsAppLogic.queryCmsAppProjectDtoByPid(dto.getProjectId());
        ProjectDto projectDto = projectService.getProjectDtoByProId(dto.getProjectId());
        result.setProId(cmsAppProjectDto.getProjectId());
        result.setProName(projectDto.getName());//项目名称
        result.setSlogan(cmsAppProjectDto.getProjectTitle());//项目slogan
        result.setTelePhone(projectDto.getMarketTel());//项目联系电话
        result.setPanoramicUrl(cmsAppProjectDto.getPanoramicUrl());//全景看房URL
        String removeHtmlTags = HtmlTagUtil.removeHtmlTags(cmsAppProjectDto.getProjectDescribe());
        String replaceTags = HtmlTagUtil.replaceTags(removeHtmlTags);
        result.setBriefInfo(replaceTags);//项目简介
        result.setLng(projectDto.getLon());//项目经度
        result.setLat(projectDto.getLat());//项目纬度
        result.setPeripheralUrl(cmsAppProjectDto.getPeripheralUrl());
        result.setProAddr(projectDto.getAddress());//项目地址
        result.setShareUrl(cmsAppProjectDto.getShareUrl());//分享链接
        result.setZODesc(cmsAppProjectDto.getZoDesc());//专属ZO介绍
        result.setZOUrl(cmsAppProjectDto.getZoImgUrl());//专属ZO图片URL
        result.setZoServiceDesc(cmsAppProjectDto.getZoServiceDesc());//专属服务
        result.setProHeadPic(cmsAppProjectDto.getHeadImg());//项目头图-for预约看房

        //轮播图区
        List<ProDetailTopPicDto> topPicList = new ArrayList<>();
        List<CmsAppProjectLabelDto> labelDtoList = cmsAppProjectDto.getAppProjectLabelDtoList();
        if(labelDtoList != null && labelDtoList.size() > 0){
            for (CmsAppProjectLabelDto labelDto : labelDtoList) {
                ProDetailTopPicDto topPicDto = new ProDetailTopPicDto();
                topPicDto.setLabel(labelDto.getModuleName());

                List<String> list = new ArrayList<>();
                List<CmsAppProjectLabelImgDto> labelImgList = labelDto.getProjectLabelImgList();
                if(labelImgList != null && labelImgList.size() > 0){
                    for (CmsAppProjectLabelImgDto imgDto : labelImgList) {
                        list.add(imgDto.getImgUrl());
                    }
                }

                topPicDto.setPicURL(list);
                topPicList.add(topPicDto);
            }
        }
        result.setTopPicList(topPicList);
        //交通
        List<String> trafficList = new ArrayList<>();
        List<CmsAppProjectTrafficDto> trafficDtoList = cmsAppProjectDto.getAppProjectTrafficDtoList();
        if(trafficDtoList != null && trafficDtoList.size() > 0){
            for (CmsAppProjectTrafficDto trafficDto : trafficDtoList) {
                trafficList.add(trafficDto.getTrafficDes());
            }
        }
        result.setTrafficList(trafficList);
        //z-space区域
        ProDetailZSpaceDto ZSpaceDto = new ProDetailZSpaceDto();
        ZSpaceDto.setDescription(cmsAppProjectDto.getZspaceDesc());
        List<CmsAppProjectZspaceImgDto> zspaceImgDtoList = cmsAppProjectDto.getAppProjectZspaceImgDtoList();
        if(zspaceImgDtoList != null && zspaceImgDtoList.size() > 0){
            List<ZSpacePicDto> picList = new ArrayList<>();
            for (CmsAppProjectZspaceImgDto zspaceImgDto : zspaceImgDtoList) {
                ZSpacePicDto picDto = new ZSpacePicDto();
                picDto.setPicUrl(zspaceImgDto.getImgUrl());
                picDto.setPicDes(zspaceImgDto.getImgDesc());
                picList.add(picDto);
            }
            ZSpaceDto.setPicList(picList);
        }
        result.setZSpaceDto(ZSpaceDto);
        //户型简介区域
        List<HouseTypeDto> houseTypeList = new ArrayList<>();
        Double minPrice = null;
        Double maxPrice = null;
        List<SearchProjectEntity> searchProjectList = projectService.getHTInfoByProId(dto.getProjectId(), null, null, null, null, null, null, null);
        Set<String> set = new HashSet<>();
        for (SearchProjectEntity entity : searchProjectList) {
            set.add(entity.getHtId());
        }
        List<SearchProjectEntity> searchProjectList1 = projectService.getHTInfoByProIdOther(dto.getProjectId());
        for (SearchProjectEntity entity : searchProjectList1) {
            if (!set.contains(entity.getHtId())) {
                searchProjectList.add(entity);
            }
        }
        for (SearchProjectEntity entity : searchProjectList) {
            HouseTypeDto houseTypeDto = new HouseTypeDto();
            houseTypeDto.setHtName(entity.getHtName());
            houseTypeDto.setHtArea(entity.getHtArea());
            int avaRoomAcc = entity.getHtAvaRoomAcc();
            houseTypeDto.setHtAvaRoomAcc(avaRoomAcc);
            if (avaRoomAcc > 0) {//即有不同的标签
                List<String> roomTags = new ArrayList<>();
                String[] states = entity.getHtAvaRoomState().split(",");
                for (String state : states) {
                    if ("0".equals(state)) {
                        roomTags.add("可签约");
                    } else if ("8".equals(state)) {
                        roomTags.add("可预订");
                    }
                }

                houseTypeDto.setIsRoomful(1);//是否满房(0:满房; 1:未满)
                houseTypeDto.setRoomTags(roomTags);
            } else {
                houseTypeDto.setRoomFulTag("已满房");
                houseTypeDto.setRoomTags(new ArrayList<String>());
            }
            houseTypeDto.setHtId(entity.getHtId());
            houseTypeDto.setHtImgUrl(entity.getHtImgUrl() == null ? null : picPrefixUrl + entity.getHtImgUrl());
            houseTypeDto.setHtMinPrice(entity.getHtMinPrice());
            houseTypeDto.setHtMaxPrice(entity.getHtMaxPrice());
            houseTypeList.add(houseTypeDto);
            //项目最低价格
            if (minPrice == null) {
                minPrice = entity.getHtMinPrice();
            } else {
                minPrice = minPrice > entity.getHtMinPrice() ? entity.getHtMinPrice() : minPrice;
            }
            //项目最高价格
            if (maxPrice == null) {
                maxPrice = entity.getHtMaxPrice();
            } else {
                maxPrice = maxPrice < entity.getHtMaxPrice() ? entity.getHtMaxPrice() : maxPrice;
            }
        }
        result.setHouseTypeList(houseTypeList);
        result.setMinPrice((minPrice == null) ? "0" : CommonUtil.doubleToString(minPrice));//项目最低价格-for预约看房
        result.setMaxPrice((maxPrice == null) ? "0" : CommonUtil.doubleToString(maxPrice));//项目最高价格-for预约看房

        return result;
    }
    
    /**
     * 根据城市获取所有项目
     * @author tianxf9
     * @param cityId
     * @return
     */
    public List<MkProjectDto> getProjectsByCityId(String cityId) {
    	
    	List<ProjectDto> projectDtos = this.projectService.getAllProjectList(cityId);
    	List<MkProjectDto> mkProjectDtos = new ArrayList<MkProjectDto>();
    	for(ProjectDto dto:projectDtos) {
    		MkProjectDto temp = new MkProjectDto();
    		temp.setProjectId(dto.getId());
    		temp.setProjectName(dto.getName());
    		mkProjectDtos.add(temp);
    	}
    	
    	return mkProjectDtos;
    }

    public List<ProAndHtInfoEntity> getProAndHtInfo() {
        return projectService.getProAndHtInfo();
    }

    public List<MZOProDto> getProAndZOInfo() {
        return projectService.getProAndZOInfo();
    }

    public List<MZOListDto> getZOInfoList(String projectId) {
        return projectService.getZOInfoList(projectId);
    }

    public List<ZOLabelDto> getLabelsByType(String i) {
        return projectService.getLabelsByType(i);
    }

    public List<ZOLabelDto> getHasLabelsByZOIdAndType(String zoId, String i, int hasLabelsNum) {
        return projectService.getHasLabelsByZOIdAndType(zoId, i, hasLabelsNum);
    }

    public MZOListDto getZOInfoByZOId(String zoId) {
        return projectService.getZOInfoByZOId(zoId);
    }

}
