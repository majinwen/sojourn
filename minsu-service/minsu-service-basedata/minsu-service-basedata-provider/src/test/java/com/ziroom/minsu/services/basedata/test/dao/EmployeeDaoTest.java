package com.ziroom.minsu.services.basedata.test.dao;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.services.basedata.dao.EmployeeDao;
import com.ziroom.minsu.services.basedata.dto.EmployeeRequest;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>用户测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/25.
 * @version 1.0
 * @since 1.0
 */
public class EmployeeDaoTest extends BaseTest {

    @Resource(name="basedata.employeeDao")
    private EmployeeDao employeeDao;

    @Test
    public void findEmployeeForPageTest(){
        EmployeeRequest employeeRequest=new EmployeeRequest();
        PagingResult<EmployeeEntity> pr=employeeDao.findEmployeeForPage(employeeRequest);
        System.out.println(JsonEntityTransform.Object2Json(pr.getRows()));
    }
    
    @Test
    public void testGetApiEmployee(){
    	Map<String,String> map = new HashMap<String,String>();
    	map.put("page", "1");
    	map.put("size", "5");
    	map.put("startDate", "2016-01-16");
    	map.put("isEnable", "1");
    	System.out.println(JSONObject.toJSONString(map));
    }
    
    @Test
    public void getEmployeeByFid(){
    	String empFid = "00300CB2213DDACBE05010AC69062479";
    	EmployeeEntity employeeEntity = employeeDao.getEmployeeEntityFid(empFid);
    	System.out.println(employeeEntity.getEmpName());
    }
}
