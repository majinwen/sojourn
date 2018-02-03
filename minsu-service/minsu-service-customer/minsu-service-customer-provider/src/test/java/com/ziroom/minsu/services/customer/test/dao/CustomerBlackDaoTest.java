package com.ziroom.minsu.services.customer.test.dao;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.customer.CustomerBlackEntity;
import com.ziroom.minsu.services.customer.dao.CustomerBlackDao;
import com.ziroom.minsu.services.customer.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by homelink on 2016/10/27.
 */
public class CustomerBlackDaoTest extends BaseTest {

    @Resource(name="customer.customerBlackDao")
    private CustomerBlackDao customerBlackDao;


    @Test
    public void testSaveCustomerBlack(){
        CustomerBlackEntity entity = new CustomerBlackEntity();
        entity.setFid(UUIDGenerator.hexUUID());
        entity.setUid("5feafcff-af19-4a8d-b2d6-ca08761c2b94");
        entity.setRemark("测试");
        customerBlackDao.insertSelective(entity);
    }

    @Test
    public void testSelectByUid(){
        CustomerBlackEntity entity = customerBlackDao.selectByUid("ec4c20dc-56b6-4b17-b040-90a7b98fca8e");
        System.err.println(entity.getBlackType());
    }
}
