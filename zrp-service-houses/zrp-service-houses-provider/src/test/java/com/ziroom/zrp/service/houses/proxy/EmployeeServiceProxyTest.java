package com.ziroom.zrp.service.houses.proxy;

import javax.annotation.Resource;

import org.junit.Test;

import com.ziroom.zrp.service.houses.base.BaseTest;
/**
 * <p>测试ZOProxy</p>
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
public class EmployeeServiceProxyTest extends BaseTest{
	
	@Resource(name="houses.employeeServiceProxy")
	private EmployeeServiceProxy employeeProxy;
	@Test
	public void testfindEmployeeById(){
		String fid = "900009565";
		String json = employeeProxy.findEmployeeById(fid);
		System.err.println(json);
		
	}
	
	@Test
	public void testfindEmployeeByCode(){
		String fcode = "20095992";
		String json = employeeProxy.findEmployeeByCode(fcode);
		System.err.println(json);
		
	}

}
