package com.ziroom.minsu.services.customer.test.proxy;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.customer.CustomerLocationEntity;
import com.ziroom.minsu.services.customer.proxy.CustomerLocationServiceProxy;
import com.ziroom.minsu.services.customer.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>用户定位的代理</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/11/30.
 * @version 1.0
 * @since 1.0
 */
public class CustomerLocationServiceProxyTest extends BaseTest {

    @Resource(name = "customer.customerLocationServiceProxy")
    private CustomerLocationServiceProxy customerLocationServiceProxy;

    @Test
    public void saveUserLocationTest(){

        CustomerLocationEntity customerLocationEntity = new CustomerLocationEntity();
        customerLocationEntity.setFid(UUIDGenerator.hexUUID());
        customerLocationEntity.setUid("12321");
        customerLocationEntity.setAppName("ziroom");
		customerLocationEntity.setChannelName("pugongying");
		customerLocationEntity.setOsVersion("9.9");
		customerLocationEntity.setImei("D17AB88B-B996-40A5-A424-CD62ACBB2E6E");
        customerLocationEntity.setImsi("imsi");
//        customerLocationEntity.setDeviceIp(131312312);
        customerLocationEntity.setDeviceNo("deviNp");
        customerLocationEntity.setLatitude(39.938576);
        customerLocationEntity.setLongitude(116.421883);
        customerLocationEntity.setPhoneModel("mpdel");
        customerLocationEntity.setVersionCode("12312312");
        customerLocationEntity.setCityCode("cityCode");
        customerLocationEntity.setCityName("cityName");

        String result = customerLocationServiceProxy.saveUserLocation(JsonEntityTransform.Object2Json(customerLocationEntity));
        System.err.println("result:"+result);
    }

}
