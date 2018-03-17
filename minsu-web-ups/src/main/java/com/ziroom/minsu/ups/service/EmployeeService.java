/**
 * @FileName: EmployeeService.java
 * @Package com.ziroom.minsu.ups.service
 * 
 * @author bushujie
 * @created 2017年12月18日 下午2:41:27
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.ups.service;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.services.basedata.dto.EmployeeRequest;

/**
 * <p>员工信息业务层</p>
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
public interface EmployeeService {
	
	/**
	 * 
	 * fid查询员工信息
	 *
	 * @author bushujie
	 * @created 2017年12月18日 下午2:44:11
	 *
	 * @param fid
	 * @return
	 */
	EmployeeEntity findEmployeeByFid(String fid);
	
	/**
	 * 
	 * 同步插入员工信息
	 *
	 * @author bushujie
	 * @created 2017年12月18日 下午4:29:07
	 *
	 * @param employee
	 */
	int insertEmployee(EmployeeEntity employee);
	
	/**
	 *
	 *  分页查询员工列表
	 *
	 * @author bushujie
	 * @created 2017年12月20日 下午4:28:54
	 *
	 * @param employeeRequest
	 * @return
	 */
	PagingResult<EmployeeEntity> findEmployeeForPage(EmployeeRequest employeeRequest);

	/**
	 *
	 * 根据员工系统号查询员工信息
	 *
	 * @author zhangyl2
	 * @created 2018年02月05日 15:13
	 * @param
	 * @return
	 */
    EmployeeEntity getEmployeeEntity(String empCode);
}
