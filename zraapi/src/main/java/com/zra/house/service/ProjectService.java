package com.zra.house.service;

import com.zra.common.dto.house.BusinessListReturnDto;
import com.zra.common.dto.house.ProjectDto;
import com.zra.house.dao.ProjectMapper;
import com.zra.house.entity.ProAndHtInfoEntity;
import com.zra.house.entity.SearchProjectEntity;
import com.zra.house.entity.dto.ProjectListReturnDto;
import com.zra.house.entity.dto.ProjectTelDto;

import com.zra.m.entity.dto.MZOListDto;
import com.zra.m.entity.dto.MZOProDto;
import com.zra.m.entity.dto.ZOLabelDto;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: wangxm113
 * CreateDate: 2016/7/29.
 */
@Service
public class ProjectService {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(ProjectService.class);

    @Autowired
    private ProjectMapper projectMapper;

    /**
     * 项目筛选
     *
     * @Author: wangxm113
     * @CreateDate: 2016-07-29
     */
    public List<SearchProjectEntity> getSearchProject(String cityCode, Double minPrice, Double maxPrice, String checkInTime) {
        return projectMapper.getSearchProject(cityCode, minPrice, maxPrice, checkInTime);
    }
    
    /**
     * 0608APP首页项目筛选列表
     * @author tianxf9
     * @param cityCode
     * @param minPrice
     * @param maxPrice
     * @param checkInTime
     * @return
     */
    public List<SearchProjectEntity> getSearchProject2(String cityCode, Double minPrice, Double maxPrice, String checkInTime) {
    	return projectMapper.getSearchProject2(cityCode, minPrice, maxPrice, checkInTime);
    }

    /**
     * 项目筛选
     *
     * @Author: wangxm113
     * @CreateDate: 2016-07-29
     */
    public List<SearchProjectEntity> getSearchProjectAnother(String cityCode) {
        return projectMapper.getSearchProjectAnother(cityCode);
    }

    /**
     * 项目筛选-当没有筛选条件时
     *
     * @Author: wangxm113
     * @CreateDate: 2016-07-29
     */
    public List<SearchProjectEntity> getSearchProjectOther() {
        return projectMapper.getSearchProjectOther();
    }

    /**
     * 获取所有项目
     * @author wangws21 2016-8-1
     * @param cityId 城市id
     * @return list
     */
    public List<ProjectDto> getAllProjectList(String cityId){
    	return projectMapper.getAllProjectList(cityId);
    }


	/**
	 * 根据员工（ZO） 获取其有权限的项目列表
	 * @author wangws21 2016-8-1
	 * @param eid 员工id
	 * @param cityId 城市id
	 * @return List<ProjectDto>
	 */
	public List<ProjectDto> getProjectListByEmployeeId(String eid, String cityId) {
		return projectMapper.getProjectListByEmployeeId(eid,cityId);
	}

    /**
     * 房型筛选-根据项目id获取项目的一些信息
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-01
     */
    public List<ProjectDto> getProjectDetailInfo(String projectId) {
        return projectMapper.getProjectDetailInfo(projectId);
    }
    
    /**
     * 根据项目id获取项目
     * @author tianxf9
     * @param projectId
     * @return
     */
    public ProjectDto getProjectDtoByProId(String projectId) {
    	return projectMapper.getProjectDtoById(projectId);
    }

    /**
     * 房型筛选-根据项目id获取房型信息
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-02
     */
    public List<SearchProjectEntity> getHTInfoByProId(String projectId, Double minPrice, Double maxPrice, Double minArea, Double maxArea,
                                                      String checkInTime, String floor, String direction) {
        return projectMapper.getHTInfoByProId(projectId, minPrice, maxPrice, minArea, maxArea, checkInTime, floor, direction);
    }

    /**
     * 房型筛选-根据项目id获取房型信息-当没有筛选条件时
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-02
     */
    public List<SearchProjectEntity> getHTInfoByProIdOther(String projectId) {
        return projectMapper.getHTInfoByProIdOther(projectId);
    }

    /**
     * wangws21 2016-8-3
     * 获取项目下的房型
     * @param projectId 项目id
     * @return 房型列表
     */
	public List<com.zra.common.dto.house.HouseTypeDto> getHouseTypeByProjectId(String projectId) {
		return projectMapper.getHouseTypeByProjectId(projectId);
	}

    /**
     *  根据项目id获取城市id
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-04
     */
    public String getCityIdByProjectId(String projectId) {
        return projectMapper.getCityIdByProjectId(projectId);
    }
    
   
    /**
     * 查询所有项目的分机号
     * @author tianxf9
     * @return
     */
    public List<ProjectTelDto> getAllProjectTelMsg() {
    	return this.projectMapper.getProjectMarketTel();
    }

    /**
     * 获取项目列表
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-08
     */
    public List<ProjectListReturnDto> getProjectList() {
        return projectMapper.getProjectList();
    }

    /**
     * for商机列表-查询项目信息
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-24
     */
    public List<BusinessListReturnDto> getProInfoForBusinessList(String bids) {
        return projectMapper.getProInfoForBusinessList(bids);
    }


    public String getProjectCodeByBillFid(String billFid) {
        return projectMapper.getProjectCodeByBillFid(billFid);

    }
    public List<ProjectDto> getAllProjects() {
    	return projectMapper.getAllProjects();

    }

    public ProjectListReturnDto getProjectInfoByHtFid(String houseTypeId) {
        return projectMapper.getProjectInfoByHtFid(houseTypeId);
    }

    public List<SearchProjectEntity> getSearchProjectAnotherAgain(String cityCode, String s) {
        return projectMapper.getSearchProjectAnotherAgain(cityCode, s);
    }


    public List<ProAndHtInfoEntity> getProAndHtInfo() {
        return projectMapper.getProAndHtInfo();
    }

    public List<MZOProDto> getProAndZOInfo() {
        return projectMapper.getProAndZOInfo();
    }

    public List<MZOListDto> getZOInfoList(String projectId) {
        return projectMapper.getZOInfoList(projectId);
    }

    public List<ZOLabelDto> getLabelsByType(String i) {
        return projectMapper.getLabelsByType(i);
    }

    public List<ZOLabelDto> getHasLabelsByZOIdAndType(String zoId, String i, int hasLabelsNum) {
        return projectMapper.getHasLabelsByZOIdAndType(zoId, i, hasLabelsNum);
    }

    public MZOListDto getZOInfoByZOId(String zoId) {
        return projectMapper.getZOInfoByZOId(zoId);
    }

}
