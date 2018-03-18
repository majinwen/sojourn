package com.ziroom.zrp.service.houses.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ziroom.zrp.houses.entity.EmployeeEntity;
import com.ziroom.zrp.service.houses.dao.EmployeeDao;
/**
 * <p>ZO信息查询Service</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年9月18日
 * @since 1.0
 */
@Service("houses.employeeServiceImpl")
public class EmployeeServiceImpl {
	@Resource(name="houses.employeeDao")
	private EmployeeDao employeeDao;
	
	public EmployeeEntity findEmployeeById(String fid){
		return employeeDao.findEmployeeById(fid);
	}

	public EmployeeEntity findEmployeeByCode(String fcode){
		return employeeDao.findEmployeeByCode(fcode);
	}
}
