package com.zra.projectZO.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zra.common.utils.SysConstant;
import com.zra.projectZO.ProjectZODto;
import com.zra.projectZO.dao.ProjectZOMapper;

/**
 * @author tianxf9 2016-08-01
 */
@Service
public class ProjectZOService {

    @Autowired
    private ProjectZOMapper dao;

    /**
     * 根据项目获得该项目下的所有管家
     *
     * @param projectId
     * @return
     * @author tianxf9 2015-6-08-01
     */
    public List<ProjectZODto> getProjectZOsByProId(String projectId) {
        return dao.getProjectZOsByProjectId(projectId,SysConstant.ZO_ROLE_NAME);
    }

    /**
     * 获得所有管家
     */
    public List<ProjectZODto> getProjectZOs() {
        return dao.getProjectZOs(SysConstant.ZO_ROLE_NAME);
    }

    /**
     * 根据管家fid获取他的系统号
     *
     * @Author: wangxm113
     * @CreateDate: 2016-09-14
     */
    public String getSysCodeByFid(String beEvaluatorId) {
        return dao.getSysCodeByFid(beEvaluatorId);
    }
    
    /**
     * 根据empId获取管家姓名
     * @author tianxf9
     * @param empId
     * @return
     */
    public String getNameByEmpIdOrCode(String empId) {
    	return dao.getNameByEmpIdOrCode(empId);
    }
    
    public String getProjectZoSmallImg(String projectId,String empId) {
    	return dao.getProjectZoSmallImg(projectId, empId);
    }
}
