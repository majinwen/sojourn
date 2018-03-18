package com.zra.house.dao;

import java.util.List;

import com.zra.common.dto.house.BusinessListReturnDto;
import com.zra.house.entity.ProAndHtInfoEntity;
import com.zra.house.entity.dto.ProjectListReturnDto;
import com.zra.m.entity.dto.MZOListDto;
import com.zra.m.entity.dto.MZOProDto;
import com.zra.m.entity.dto.ZOLabelDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zra.common.dto.house.ProjectDto;
import com.zra.house.entity.SearchProjectEntity;
import com.zra.house.entity.dto.ProjectTelDto;

/**
 * Author: wangxm113
 * CreateDate: 2016/7/29.
 */
@Repository
public interface ProjectMapper {
    /**
     * 项目筛选
     *
     * @Author: wangxm113
     * @CreateDate: 2016-07-29
     */
    List<SearchProjectEntity> getSearchProject(@Param("cityCode") String cityCode, @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice, @Param("checkInTime") String checkInTime);
    
    /**
     * 0608项目首页优化项目列表查询
     * @author tianxf9
     * @param cityCode
     * @param minPrice
     * @param maxPrice
     * @param checkInTime
     * @return
     */
    List<SearchProjectEntity> getSearchProject2(@Param("cityCode") String cityCode, @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice, @Param("checkInTime") String checkInTime);

	/**
	 * 项目筛选
	 *
	 * @Author: wangxm113
	 * @CreateDate: 2016-07-29
	 */
	List<SearchProjectEntity> getSearchProjectAnother(@Param("cityCode") String cityCode);

	/**
	 * 项目筛选-当没有筛选条件时
	 *
	 * @Author: wangxm113
	 * @CreateDate: 2016-07-29
	 */
	List<SearchProjectEntity> getSearchProjectOther();

    /**
     * 获取所有项目
     *
     * @param cityId 城市id
     * @return list
     * @author wangws21 2016-8-1
     */
    List<ProjectDto> getAllProjectList(@Param(value="cityId") String cityId);

    /**
     * 根据员工（ZO） 获取其有权限的项目列表
     *
     * @param eid    员工id
     * @param cityId 城市id
     * @return List<ProjectDto>
     */
    List<ProjectDto> getProjectListByEmployeeId(@Param("eid") String eid, @Param("cityId") String cityId);

    /**
     * 房型筛选-根据项目id获取项目的一些信息
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-01
     */
    List<ProjectDto> getProjectDetailInfo(String projectId);

    /**
     * 房型筛选-根据项目id获取房型信息
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-02
     */
    List<SearchProjectEntity> getHTInfoByProId(@Param("projectId") String projectId, @Param("minPrice") Double minPrice,
                                               @Param("maxPrice") Double maxPrice, @Param("minArea") Double minArea,
                                               @Param("maxArea") Double maxArea, @Param("checkInTime") String checkInTime,
                                               @Param("floor") String floor, @Param("direction") String direction);


    /**
     * 房型筛选-根据项目id获取房型信息-当没有筛选条件时
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-02
     */
    List<SearchProjectEntity> getHTInfoByProIdOther(@Param("projectId") String projectId);


    /**
     * wangws21 2016-8-3
     * 获取项目下的房型
     *
     * @param projectId 项目id
     * @return 房型列表
     */
    List<com.zra.common.dto.house.HouseTypeDto> getHouseTypeByProjectId(String projectId);

    /**
     * 根据项目id获取城市id
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-04
     */
    String getCityIdByProjectId(String projectId);

    /**
     * 获取所有项目的分机号
     *
     * @return
     * @author tianxf9
     */
    List<ProjectTelDto> getProjectMarketTel();

    /**
     * 获取项目列表
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-08
     */
    List<ProjectListReturnDto> getProjectList();

	/**
	 * for商机列表-查询项目信息
	 *
	 * @Author: wangxm113
	 * @CreateDate: 2016-08-24
	 */
	List<BusinessListReturnDto > getProInfoForBusinessList(@Param("bids") String bids);
	String getProjectCodeByBillFid(String billFid);
	List<ProjectDto> getAllProjects();
	
	/**
	 * 根据项目id获取项目
	 * @author tianxf9
	 * @return
	 */
	ProjectDto getProjectDtoById(String projectId);

    ProjectListReturnDto getProjectInfoByHtFid(String houseTypeId);

	List<SearchProjectEntity> getSearchProjectAnotherAgain(@Param("cityCode") String cityCode, @Param("proBids") String proBids);

	List<ProAndHtInfoEntity> getProAndHtInfo();

    List<MZOProDto> getProAndZOInfo();

    List<MZOListDto> getZOInfoList(String projectId);

    List<ZOLabelDto> getLabelsByType(@Param("type") String type);

    List<ZOLabelDto> getHasLabelsByZOIdAndType(@Param("zoId") String zoId, @Param("typeI") String typeI, @Param("hasLabelsNum") int hasLabelsNum);

    MZOListDto getZOInfoByZOId(String zoId);
}
