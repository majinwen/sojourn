package com.ziroom.minsu.services.customer.test.dao;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.customer.CustomerRoleBaseEntity;
import com.ziroom.minsu.entity.customer.CustomerRoleEntity;
import com.ziroom.minsu.services.customer.dao.CustomerRoleBaseDao;
import com.ziroom.minsu.services.customer.test.BaseTest;
import org.junit.Test;
import javax.annotation.Resource;
import java.util.List;


/**
 * <p>TODO</p>
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
public class CustomerRoleBaseDaoTest extends BaseTest {

    @Resource(name="customer.customerRoleBaseDao")
    private CustomerRoleBaseDao customerRoleBaseDao;

    @Test
    public void insertCustomerRole(){

        CustomerRoleBaseEntity customerRoleEntity = new CustomerRoleEntity();
        customerRoleEntity.setRoleName("name");
        customerRoleEntity.setRoleCode("123");
        customerRoleEntity.setIsLife(1);
        int aa= customerRoleBaseDao.insertCustomerBaseRole(customerRoleEntity);
        System.out.println(JsonEntityTransform.Object2Json(aa));
    }


    @Test
    public void getCustomerRoleBaseByType(){

        CustomerRoleBaseEntity aa= customerRoleBaseDao.getCustomerRoleBaseByType("123");
        System.out.println(JsonEntityTransform.Object2Json(aa));
    }



    @Test
    public void getBaseRoles(){

        List<CustomerRoleBaseEntity> aa= customerRoleBaseDao.getBaseRoles();
        System.out.println(JsonEntityTransform.Object2Json(aa));
    }

}
