package com.ziroom.minsu.services.customer.test.dao;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.customer.LandlordBehaviorEntity;
import com.ziroom.minsu.services.customer.dao.LandlordBehaviorDao;
import com.ziroom.minsu.services.customer.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by homelink on 2016/10/27.
 */
public class LandlordBehaviorDaoTest extends BaseTest {

    @Resource(name="customer.landlordBehaviorDao")
    private LandlordBehaviorDao landlordBehaviorDao;


    @Test
    public void testSaveLandlordBehavior(){
        LandlordBehaviorEntity landLordBehaviorEntity = new LandlordBehaviorEntity();
        landLordBehaviorEntity.setFid(UUIDGenerator.hexUUID());
        landLordBehaviorEntity.setLandlordUid(UUIDGenerator.hexUUID());
        landlordBehaviorDao.insertLandlordBehavior(landLordBehaviorEntity);
    }

    @Test
    public void testfindLandlordBehavior(){
        LandlordBehaviorEntity landlordBehavior = landlordBehaviorDao.findLandlordBehavior("8a9e9891582d13b601582d13b6660001");


    }

    @Test
    public void updateLandlordBehavior(){
        LandlordBehaviorEntity landLordBehaviorEntity = new LandlordBehaviorEntity();
        landLordBehaviorEntity.setLandlordUid("8a9e9891582d13b601582d13b6660001");
        landLordBehaviorEntity.setAdvisoryNum(100);
        landlordBehaviorDao.updateLandlordBehavior(landLordBehaviorEntity);
    }
}
