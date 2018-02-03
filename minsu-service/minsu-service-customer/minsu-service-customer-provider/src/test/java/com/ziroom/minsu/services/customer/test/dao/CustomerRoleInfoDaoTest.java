package com.ziroom.minsu.services.customer.test.dao;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.customer.CustomerRoleEntity;
import com.ziroom.minsu.services.customer.dao.CustomerRoleInfoDao;
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
public class CustomerRoleInfoDaoTest extends BaseTest {

    @Resource(name="customer.customerRoleInfoDao")
    private CustomerRoleInfoDao customerRoleInfoDao;

    @Test
    public void frozenCustomerRole(){

        List<CustomerRoleEntity> rst = customerRoleInfoDao.getCustomerRolesInfoByUid("123");
        System.out.println(JsonEntityTransform.Object2Json(rst));
    }

}
