package com.ziroom.zrp.service.houses.proxy;

import com.alibaba.fastjson.JSON;
import com.ziroom.zrp.service.houses.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月07日 11:39
 * @since 1.0
 */
public class RentPriceStrategyServiceProxyTest extends BaseTest{

    @Resource(name = "houses.rentPriceStrategyServiceProxy")
    private RentPriceStrategyServiceProxy rentPriceStrategyServiceProxy;

    @Test
    public void testGetRentPriceStrategy() {
        Map map = new HashMap();
        map.put("projectId", "16");
        map.put("rentType", 1);
        String s = rentPriceStrategyServiceProxy.getRentPriceStrategy(JSON.toJSONString(map));
        System.err.println(s);
    }


}
