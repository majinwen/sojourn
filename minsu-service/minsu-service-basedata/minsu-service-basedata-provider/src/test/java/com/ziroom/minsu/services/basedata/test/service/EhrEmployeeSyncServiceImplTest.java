/**
 * @FileName: EhrEmployeeSyncServiceImplTest.java
 * @Package com.ziroom.minsu.services.basedata.service
 * 
 * @author jixd
 * @created 2016年4月23日 下午4:24:47
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.test.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.services.basedata.service.EmployeeServiceImpl;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>员工测试</p>
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
public class EhrEmployeeSyncServiceImplTest extends BaseTest {
	
	@Test
	public void testSyncEmployee(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("page", "1");
		map.put("size", "1");
		map.put("startDate", "2016-06-29");
		map.put("endDate", "2016-06-30");
		map.put("isEnable", "1");
		
		String result = CloseableHttpUtil.sendFormPost("http://ehr.ziroom.com/ehr/getAllUser.action", map);
		
		System.out.println(result);
	/*	JSONObject jsonObj = JSONObject.parseObject(result);
		
		String message = jsonObj.getString("message");
		String status = jsonObj.getString("status");
		System.out.println("message"+message);
		System.out.println("status:"+status);
		JSONArray jsonArr = jsonObj.getJSONArray("data");
		for(int  i =0;i<jsonArr.size();i++){
			JSONObject temp = jsonArr.getJSONObject(i);
			EmployeeEntity entity = new EmployeeEntity();
			entity.setCreateDate(new Date());
			entity.setEmpName(temp.getString("name"));
			entity.setEmpCode(temp.getString("emplid"));
			entity.setEmpMail(temp.getString("email"));
			entity.setEmpMobile(temp.getString("phone"));
			entity.setIsDel(0);
			entity.setEhrCityCode(String.valueOf(temp.getIntValue("cityCode")));
			entity.setLastModifyDate(new Date());
			entity.setDepartCode(temp.getString("deptCode"));
			entity.setDepartName(temp.getString("dept"));
			
			entity.setDepartCode(temp.getString("deptCode"));
			entity.setDepartName(temp.getString("dept"));
			entity.setPostName(temp.getString("descr"));
			entity.setPostCode(temp.getString("jobCode"));
			
			entity.setEmpValid(1);
			entity.setCenterCode(temp.getString("centerCode"));
			entity.setCenter(temp.getString("center"));
			entity.setGroupCode(temp.getString("groupCode"));
			entity.setGroupName(temp.getString("group"));
			entity.setBranceCompanyCode(temp.getString("setId"));
			
			ehrEmployeeSyncServiceImpl.syncEhrEmployee(entity);
		}*/
	}
}
