package com.zra.projectZO.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zra.projectZO.ProjectZODto;

/**
 * @author tianxf9 2016-08-01
 */
@Repository
public interface ProjectZOMapper {
    /**
     * 根据项目id获取该项目下所有官家
     *
     * @param projectId
     * @return
     */
    public List<ProjectZODto> getProjectZOsByProjectId(@Param("projectIdStr")String projectId,@Param("roleName")String roleName);


    /**
     * 获取所有官家
     *
     * @return
     */
    List<ProjectZODto> getProjectZOs(String roleName);

    /**
     * 根据管家fid获取他的系统号
     *
     * @Author: wangxm113
     * @CreateDate: 2016-09-14
     */
    String getSysCodeByFid(String beEvaluatorId);
    
    /**
     * 根据empId获取管家姓名
     * @author tianxf9
     * @param empId
     * @return
     */
    String getNameByEmpIdOrCode(String empId);
    
    /**
     * 根据项目和管家获取管家头像
     * @author tianxf9
     * @param projectId
     * @param empId
     * @return
     */
    String getProjectZoSmallImg(@Param("projectId") String projectId,@Param("empId") String empId);
}
