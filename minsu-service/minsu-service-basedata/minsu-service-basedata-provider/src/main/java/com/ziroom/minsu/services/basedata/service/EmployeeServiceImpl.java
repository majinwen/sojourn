/**
 * @FileName: UserPermissionServiceImpl.java
 * @Package com.ziroom.minsu.services.basedata.service
 * 
 * @author bushujie
 * @created 2016年3月9日 上午11:14:06
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.sys.CurrentuserEntity;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.services.basedata.dao.CurrentuserDao;
import com.ziroom.minsu.services.basedata.dao.EmployeeDao;
import com.ziroom.minsu.services.basedata.dto.EmployeeRequest;

/**
 * 
 * <p>同步ehr员工账号</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
@Service("basedata.employeeServiceImpl")
public class EmployeeServiceImpl {
	
	@Resource(name="basedata.currentuserDao")
	private CurrentuserDao currentuserDao;

	@Resource(name="basedata.employeeDao")
	private EmployeeDao employeeDao;
	
	/**
	 * 
	 * 同步ehr员工，并增加对应登陆账号
	 *
	 * @author jixd
	 * @created 2016年4月23日 下午2:48:43
	 *
	 * @param employeeEntity
	 */
	public void syncEhrEmployee(EmployeeEntity employeeEntity,CurrentuserEntity currentUser){
		//根据员工账号获取员工信息
		EmployeeEntity hsEntity = employeeDao.getEmployeeEntity(employeeEntity.getEmpCode());
		//如果为空，增加员工和系统账号
		if(Check.NuNObj(hsEntity)){
			//如果插入员工失败则
			if(employeeDao.insertEmployeeSysc(employeeEntity)>0){
				//创建系统用户
				/*CurrentuserEntity currentUser = new CurrentuserEntity();
				currentUser.setFid(UUIDGenerator.hexUUID());
				currentUser.setEmployeeFid(employeeEntity.getFid());
				String email = employeeEntity.getEmpMail();
				currentUser.setUserAccount(email==null ? "":email.substring(0, email.indexOf("@")));
				//0正常 1停用
				currentUser.setAccountStatus(0);
				currentUser.setCreateDate(new Date());
				//是否是管理员 0 否 1是
				currentUser.setIsAdmin(0);
				//是否删除 0 否 1 是
				currentUser.setIsDel(0);
				currentUser.setLastModifyDate(new Date());
				//导入创建
				currentUser.setCreateFid("001");*/
				currentuserDao.insertCurrentuser(currentUser);
			}
		}else{
			//更改fid
			employeeEntity.setFid(hsEntity.getFid());
			//如果存在更新员工信息
			employeeDao.updateEmployeeEntity(employeeEntity);
		}
	}
	
	/**
	 * 
	 * 根据员工系统号查询员工信息
	 *
	 * @author liujun
	 * @created 2016年7月7日
	 *
	 * @param empCode
	 * @return
	 */
	public EmployeeEntity findEmployeeByEmpCode(String empCode){
		return employeeDao.getEmployeeEntity(empCode);
	}

	/**
	 * 根据查询条件查询员工信息
	 *
	 * @author liujun
	 * @created 2016年7月7日
	 *
	 * @param emp
	 * @return
	 */
	public List<EmployeeEntity> findEmployeeByCondition(EmployeeRequest empRequest) {
		return employeeDao.findEmployeeByCondition(empRequest);
	}

	/**
	 * 根据员工表fid查询员工信息
	 *
	 * @author lunan
	 * @created 2016年8月19日 上午9:55:26
	 *
	 * @param empFid
	 * @return
	 */
	public EmployeeEntity findEmployeeByEmpFid(String empFid) {
		return employeeDao.getEmployeeEntityFid(empFid);
	}
}
