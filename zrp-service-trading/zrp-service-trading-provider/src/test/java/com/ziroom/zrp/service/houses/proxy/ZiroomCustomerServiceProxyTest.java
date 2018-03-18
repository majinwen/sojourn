package com.ziroom.zrp.service.houses.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dto.PersonalInfoDto;
import com.ziroom.zrp.service.trading.proxy.ZiroomCustomerServiceProxy;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

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
 * @Date Created in 2017年09月27日 10:11
 * @since 1.0
 */
public class ZiroomCustomerServiceProxyTest extends BaseTest{


    @Resource(name="trading.ziroomCustomerServiceProxy")
    private ZiroomCustomerServiceProxy ziroomCustomerServiceProxy;

    @Test
    public void testQueryUidByPhone() {
        String uid = "8fdc471a-536c-6ab6-a334-1f8f16fc690f";
        String queryUid = ziroomCustomerServiceProxy.queryUidByPhone("13521263178");
        System.out.println("queryUid" + queryUid);
    }

    @Test
    public void testFindAuthInfoFromCustomer() {
        String result = ziroomCustomerServiceProxy.findAuthInfoFromCustomer("8fdc471a-536c-6ab6-a334-1f8f16fc690f");
        DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(result);
        PersonalInfoDto personalInfoDto = customerDto.parseData("data", new TypeReference<PersonalInfoDto>() {
        });
        System.out.println("result:" + result);
        System.out.println("personalInfoDto:" + personalInfoDto.getExtend().getWork_name() + "," + personalInfoDto.getCert().getReal_name());
    }

    @Test
    public void testGetUserAuthStatus() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", "08d1dfaa-c3b2-e4f4-8f66-03ced4bd15b9");
        String userAuthInfo = ziroomCustomerServiceProxy.getUserAuthInfo(JsonEntityTransform.Object2Json(map));
        System.err.println(userAuthInfo);
    }
}
