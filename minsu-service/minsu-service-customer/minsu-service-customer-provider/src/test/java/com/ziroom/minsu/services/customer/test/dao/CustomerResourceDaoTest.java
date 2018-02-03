package com.ziroom.minsu.services.customer.test.dao;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.customer.CustomerResourceEntity;
import com.ziroom.minsu.services.customer.dao.CustomerResourceDao;
import com.ziroom.minsu.services.customer.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

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
public class CustomerResourceDaoTest extends BaseTest {

    @Resource(name="customer.customerResourceDao")
    private CustomerResourceDao customerResourceDao;




//
//
//    @Test
//    public void getCustomerRoleByType(){
//
//        CustomerRoleEntity customerRoleEntity = customerRoleDao.getCustomerRoleByType("664524c5-6e75-ee98-4e0d-667d38b9eee1","seed");
//        System.out.println(JsonEntityTransform.Object2Json(customerRoleEntity));
//    }


//
//    @Test
//    public void getCustomerRoles(){
//
//        List<CustomerRoleEntity>  list = customerRoleDao.getCustomerRoles("123");
//        System.out.println(JsonEntityTransform.Object2Json(list));
//    }


    @Test
    public void insertCustomerResource(){

        CustomerResourceEntity customerRoleEntity = new CustomerResourceEntity();
        customerRoleEntity.setRoleCode("123");
        customerRoleEntity.setUid("123");
        int aa= customerResourceDao.insertCustomerResource(customerRoleEntity);
        System.out.println(JsonEntityTransform.Object2Json(aa));
    }


    @Test
    public void frozenCustomerResource(){

        customerResourceDao.frozenCustomerResource("8a9e9aa855de00010155de0002000000");
    }


}
