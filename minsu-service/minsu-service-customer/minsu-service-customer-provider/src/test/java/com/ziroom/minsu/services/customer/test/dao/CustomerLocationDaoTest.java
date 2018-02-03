package com.ziroom.minsu.services.customer.test.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.customer.CustomerLocationEntity;
import com.ziroom.minsu.services.common.utils.CoordinateTransforUtils;
import com.ziroom.minsu.services.common.utils.Gps;
import com.ziroom.minsu.services.customer.dao.CustomerLocationDao;
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
 * @author afi on on 2016/11/29.
 * @version 1.0
 * @since 1.0
 */
public class CustomerLocationDaoTest extends BaseTest {

    @Resource(name = "customer.customerLocationDao")
    private CustomerLocationDao customerLocationDao;




//    @Test
//    public void updateErrorIpByFid() {
//        int  rst = customerLocationDao.updateErrorIpByFid("0000000058b919740158b9534b620001");
//        System.out.println(JsonEntityTransform.Object2Json(rst));
//    }


    @Test
    public void countIpLocation() {
        Long  rst = customerLocationDao.countIpLocation();
        System.out.println(JsonEntityTransform.Object2Json(rst));
    }

    @Test
    public void getIpLocation() {
        List<CustomerLocationEntity>  rst = customerLocationDao.getIpLocation(40);
        System.out.println(JsonEntityTransform.Object2Json(rst));
    }



    @Test
    public void saveCustomerLocation() {
        CustomerLocationEntity customerLocationEntity = new CustomerLocationEntity();
        customerLocationEntity.setFid(UUIDGenerator.hexUUID());
        customerLocationEntity.setUid("12321");
        customerLocationEntity.setAppName("ziroom");
		customerLocationEntity.setChannelName("pugongying");
		customerLocationEntity.setOsVersion("9.9");
		customerLocationEntity.setImei("D17AB88B-B996-40A5-A424-CD62ACBB2E6E");
        customerLocationEntity.setImsi("imsi");
        Long ip = com.ziroom.minsu.services.common.utils.IpUtil.Ip2Long("255.255.255.254");
        System.out.println(ip);
        customerLocationEntity.setDeviceIp(ip);
        customerLocationEntity.setDeviceNo("deviNp");
        customerLocationEntity.setLatitude(39.938576);
        customerLocationEntity.setLongitude(116.421883);
    	Gps gps = CoordinateTransforUtils.bd09_To_Gps84(39.876911 ,116.349411);
        customerLocationEntity.setGoogleLatitude(Check.NuNObj(gps)?0:gps.getWgLat());
        customerLocationEntity.setGoogleLongitude(Check.NuNObj(gps)?0:gps.getWgLon());
        customerLocationEntity.setPhoneModel("mpdel");
        customerLocationEntity.setVersionCode("12312312");
        customerLocationEntity.setCityCode("cityCode");
        customerLocationEntity.setCityName("cityName");
        customerLocationDao.saveCustomerLocation(customerLocationEntity);
    }


    @Test
    public void getCustomerLocationOneHasUid() {
        long start = System.currentTimeMillis();
        CustomerLocationEntity  locationEntity = customerLocationDao.getCustomerLocationOneHasUid("e0a0f779-9117-6283-84e1-43e0be20ecf4");
        long end = System.currentTimeMillis();
        System.out.println(end-start);
        System.out.println(JsonEntityTransform.Object2Json(locationEntity));
    }


    @Test
    public void getCustomerLocationOneHasDevice() {
    	long start = System.currentTimeMillis();
        CustomerLocationEntity  locationEntity = customerLocationDao.getCustomerLocationOneHasDevice("deviNp");
        long end = System.currentTimeMillis();
        System.out.println(end-start);
        System.out.println(JsonEntityTransform.Object2Json(locationEntity));
    }



}