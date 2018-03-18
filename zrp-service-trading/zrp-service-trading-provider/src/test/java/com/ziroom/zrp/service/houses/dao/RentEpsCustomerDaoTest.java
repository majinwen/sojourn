package com.ziroom.zrp.service.houses.dao;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dao.RentEpsCustomerDao;
import com.ziroom.zrp.trading.entity.RentEpsCustomerEntity;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年10月17日 19:45
 * @since 1.0
 */
public class RentEpsCustomerDaoTest extends BaseTest {

    @Resource(name="trading.rentEpsCustomerDao")
    private RentEpsCustomerDao rentEpsCustomerDao;

    @Test
    public void testSaveRentEpsCustomer() {
        String customerId = UUIDGenerator.hexUUID();
        RentEpsCustomerEntity rentEpsCustomerEntity = new RentEpsCustomerEntity();
        rentEpsCustomerEntity.setId(customerId);
        rentEpsCustomerEntity.setName("崔玉辉");
        int count = rentEpsCustomerDao.saveRentEpsCustomer(rentEpsCustomerEntity);
        System.out.println("customerId:" +customerId + ",count:" +  count);

    }

    @Test
    public void testUpdateRentEpsCustomer() {
        RentEpsCustomerEntity rentEpsCustomerEntity = new RentEpsCustomerEntity();
        rentEpsCustomerEntity.setId("8a9099cb57e1bf290157e507a95f0046");
        rentEpsCustomerEntity.setName("张学友");
        int count = rentEpsCustomerDao.updateRentEpsCustomer(rentEpsCustomerEntity);
        System.out.println("testUpdateRentEpsCustomer:" + count);
    }

    @Test
    public void findOneRentEpsCustomerByUid(){
        String customerUid = "11";
        RentEpsCustomerEntity rentEpsCustomerEntity = rentEpsCustomerDao.findOneRentEpsCustomerByUid(customerUid);
        System.err.println(JsonEntityTransform.Object2Json(rentEpsCustomerEntity));
    }
}
