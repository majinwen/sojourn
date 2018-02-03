package com.ziroom.minsu.services.basedata.test.proxy;

import javax.annotation.Resource;

import org.junit.Test;

import com.ziroom.minsu.services.basedata.proxy.EmployeeServiceProxy;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;

/**
 * 
 * <p>员工代理测试</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class EmployeeServiceProxyTest extends BaseTest {
	
	@Resource(name="basedata.employeeServiceProxy")
	private EmployeeServiceProxy employeeServiceProxy;
	
	@Test
	public void findEmployeeByEmpCodeTest(){
		String empCode = "20223709";
		String resultJson = employeeServiceProxy.findEmployeeByEmpCode(empCode);
		System.err.println(resultJson);
	}
	
	@Test
	public void findEmployeByEmpFid(){
		String empFid = "00300CB2213DDACBE05010AC69062479";
		String resultJson = employeeServiceProxy.findEmployeByEmpFid(empFid);
		System.err.println(resultJson);
	}
}
