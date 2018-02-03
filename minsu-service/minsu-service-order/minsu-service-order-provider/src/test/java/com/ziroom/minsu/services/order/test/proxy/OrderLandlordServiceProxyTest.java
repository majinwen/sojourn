package com.ziroom.minsu.services.order.test.proxy;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.order.dto.LoadlordRequest;
import com.ziroom.minsu.services.order.proxy.OrderLandlordServiceProxy;
import com.ziroom.minsu.services.order.proxy.OrderUserServiceProxy;
import com.ziroom.minsu.services.order.test.base.BaseTest;
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
 * @author afi on on 2016/12/19.
 * @version 1.0
 * @since 1.0
 */
public class OrderLandlordServiceProxyTest extends BaseTest {

    @Resource(name = "order.orderLandlordServiceProxy")
    private OrderLandlordServiceProxy orderLandlordServiceProxy;


    @Test
    public void saveOtherMoney() {

        LoadlordRequest request = new LoadlordRequest();
        request.setLandlordUid("78aa924b-5c7c-42aa-ad36-378156ab43af");
        request.setOrderSn("1612211OZVBT9E161215");
        request.setOtherMoney(0);

        String orderHouseSnapshotEntity = orderLandlordServiceProxy.saveOtherMoney(JsonEntityTransform.Object2Json(request));

        System.out.println(orderHouseSnapshotEntity);

    }

}
