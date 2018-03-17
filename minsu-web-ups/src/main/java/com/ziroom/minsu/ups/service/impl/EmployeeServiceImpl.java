/**
 * @FileName: EmployeeServiceImpl.java
 * @Package com.ziroom.minsu.ups.service.impl
 * 
 * @author bushujie
 * @created 2017年12月18日 下午2:47:36
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.ups.service.impl;

import javax.annotation.Resource;

import com.ziroom.minsu.entity.sys.CurrentuserEntity;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.services.basedata.dto.EmployeeRequest;
import com.ziroom.minsu.ups.dao.EmployeeDao;
import com.ziroom.minsu.ups.service.EmployeeService;

/**
 * <p>员工业务层实现</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@Service("ups.employeeService")
public class EmployeeServiceImpl implements EmployeeService{
	
	@Resource(name="ups.employeeDao")
	private EmployeeDao employeeDao;

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.ups.service.EmployeeService#findEmployeeByFid(java.lang.String)
	 */
	@Override
	public EmployeeEntity findEmployeeByFid(String fid) {
		return employeeDao.getEmployeeEntityFid(fid);
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.ups.service.EmployeeService#insertEmployee(com.ziroom.minsu.entity.sys.EmployeeEntity)
	 */
	@Override
	public int insertEmployee(EmployeeEntity employee) {
		return employeeDao.insertEmployeeSysc(employee);
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.ups.service.EmployeeService#findEmployeeForPage(com.ziroom.minsu.services.basedata.dto.EmployeeRequest)
	 */
	@Override
	public PagingResult<EmployeeEntity> findEmployeeForPage(
			EmployeeRequest employeeRequest) {
		return employeeDao.findEmployeeForPage(employeeRequest);
	}

    @Override
    public EmployeeEntity getEmployeeEntity(String empCode) {
        return employeeDao.getEmployeeEntity(empCode);
    }

}
