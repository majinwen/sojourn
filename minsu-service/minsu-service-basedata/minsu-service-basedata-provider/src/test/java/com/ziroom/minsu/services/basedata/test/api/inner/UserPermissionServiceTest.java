package com.ziroom.minsu.services.basedata.test.api.inner;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.basedata.dto.CurrentuserRequest;
import com.ziroom.minsu.services.basedata.dto.EmployeeRequest;
import com.ziroom.minsu.services.basedata.proxy.UserPermissionServiceProxy;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>用户权限测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/24.
 * @version 1.0
 * @since 1.0
 */
public class UserPermissionServiceTest extends BaseTest {

    @Resource(name = "basedata.userPermissionServiceProxy")
    private UserPermissionServiceProxy userPermissionServiceProxy;


    @Test
    public void TestInitSaveUserInfo() {
        String aa = userPermissionServiceProxy.initSaveUserInfo("8a9e9aaf535557ef01535557ef6e0000");
        System.out.println(aa);
    }

    @Test
    public void findCurrentuserPageProxyTest() {
        CurrentuserRequest currentuserRequest = new CurrentuserRequest();
        currentuserRequest.setUserName("busj");
        currentuserRequest.setLimit(5);
        currentuserRequest.setPage(1);
        String resultJson = userPermissionServiceProxy
                .searchCurrentuserList(JsonEntityTransform.Object2Json(currentuserRequest));
        System.out.println(resultJson);
    }

    @Test
    public void findEmployeeForPageProxyTest(){
        EmployeeRequest employeeRequest=new EmployeeRequest();
        String resultJson=userPermissionServiceProxy.employeePageList(JsonEntityTransform.Object2Json(employeeRequest));
        System.out.println(resultJson);
    }
    


}
