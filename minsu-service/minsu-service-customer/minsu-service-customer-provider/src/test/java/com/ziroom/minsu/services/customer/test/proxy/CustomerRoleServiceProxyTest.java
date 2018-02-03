package com.ziroom.minsu.services.customer.test.proxy;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.customer.proxy.CustomerRoleServiceProxy;
import com.ziroom.minsu.services.customer.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>用户角色测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/6/23.
 * @version 1.0
 * @since 1.0
 */
public class CustomerRoleServiceProxyTest extends BaseTest {

    @Resource(name = "customer.customerRoleServiceProxy")
    private CustomerRoleServiceProxy customerRoleServiceProxy;


    @Test
    public void getBaseRolesByPage(){
        PageRequest request = new PageRequest();
        String aa = customerRoleServiceProxy.getBaseRolesByPage(JsonEntityTransform.Object2Json(request));
        System.out.println(aa);
    }




    @Test
    public void getBaseRolesMap(){
        String aa = customerRoleServiceProxy.getBaseRolesMap();
        System.out.println(aa);
    }

    @Test
    public void getBaseRoles(){
        String aa = customerRoleServiceProxy.getBaseRoles();
        System.out.println(aa);
    }




    @Test
    public void getCustomerRoles(){
        String aa = customerRoleServiceProxy.getCustomerRoles("123");
        System.out.println(aa);
    }


    @Test
    public void insertCustomerRole(){
        String aa = customerRoleServiceProxy.saveCustomerRole("123", "123");
        System.out.println(aa);
    }




    @Test
    public void unfrozenCustomerRole(){
        String aa = customerRoleServiceProxy.unfrozenCustomerRoleByType("123", "123");
        System.out.println(aa);
    }


    @Test
    public void frozenCustomerRoleByType(){
        String aa = customerRoleServiceProxy.frozenCustomerRoleByType("8a9e9a9e543d23f901543d23f9e90000", "123");
        System.out.println(aa);
    }
    
    @Test
    public void cancelAngelLandlord(){
        String aa = customerRoleServiceProxy.cancelAngelLandlord("8a9e9a9e543d23f901543d23f9e912345", "1");
        System.out.println(aa);
    }

    @Test
    public void checkIsBan(){
        String aa = customerRoleServiceProxy.checkIsBan("b23c0f96-d2fe-4297-8a04-e0c327173af5", "1");
        System.out.println(aa);
    }
}
