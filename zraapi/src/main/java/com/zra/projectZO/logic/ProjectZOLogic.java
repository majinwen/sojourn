package com.zra.projectZO.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.common.dto.house.ProjectDto;
import com.zra.house.logic.ProjectLogic;
import com.zra.projectZO.ProjectZODto;
import com.zra.projectZO.service.FourooTelphoneService;
import com.zra.projectZO.service.ProjectZOService;
import com.zra.system.logic.EmployeeLogic;
import com.zra.system.logic.UserAccountLogic;

/**
 * @author tianxf9 2016-08-01
 */
@Component
public class ProjectZOLogic {
	
	@Autowired
	private ProjectZOService service;
	
	@Autowired
	private UserAccountLogic userLogic;

	@Autowired
	private ProjectLogic projectLogic;

	@Autowired
	private EmployeeLogic employeeLogic;

	@Autowired
	private FourooTelphoneService telService;

	/**
	 * 根据项目获取该项目下的所有官家
	 * @author tianxf9
	 * @param projectId
	 * @return
	 */
	public List<ProjectZODto> getProjectZOsByProId(String projectId) {
		return this.service.getProjectZOsByProId(projectId);
	}

	/**获取所有管家*/
	public List<ProjectZODto> getProjectZOs() {
		return this.service.getProjectZOs();
	}

	/**
	 * 根据用户信息获取所有项目ZO
	 * @author tianxf9
	 * @param userId
	 * @param cityId
	 * @return
	 */
	public List<ProjectZODto> getAllProjectZOByUser(String userId,String cityId) {
		Map<String,Boolean> userRoles = this.userLogic.getUserRoles(userId);
		List<ProjectDto > allProject =  this.projectLogic.getProjectListByUser(userId, cityId);
		String projectIdStr = "";
		if(allProject!=null&&allProject.size()>0) {
			projectIdStr = allProject.get(0).getId();
		}else {
			return null;
		}
		if(userRoles.get("isAdmin")||userRoles.get("isPM")) {
			return this.service.getProjectZOsByProId(projectIdStr);
		}else {
			List<ProjectZODto> projectZo = new ArrayList<ProjectZODto>();
			projectZo.add(this.employeeLogic.getZOMsgByUserId(userId));
			return projectZo;
		}
	}

    /**
     * 绑定分机号
     */
    public boolean bindPhone(ProjectZODto zo) {
        return this.telService.bindPhone(zo);
    }

	/**
	 * 根据管家fid获取他的系统号
	 *
	 * @Author: wangxm113
	 * @CreateDate: 2016-09-14
	 */
	public String getSysCodeByFid(String beEvaluatorId) {
		return service.getSysCodeByFid(beEvaluatorId);
	}
	
	/**
	 * @author tianxf9
	 * @param empId
	 * @return
	 */
	public String getEmpName(String empId) {
		return service.getNameByEmpIdOrCode(empId);
	}
	
	/**
	 * 根据项目和管家获取管家头像
	 * @author tianxf9
	 * @param projectId
	 * @param empId
	 * @return
	 */
	public String getProjectZoSmallImg(String projectId,String empId) {
		return service.getProjectZoSmallImg(projectId, empId);
	}
}
