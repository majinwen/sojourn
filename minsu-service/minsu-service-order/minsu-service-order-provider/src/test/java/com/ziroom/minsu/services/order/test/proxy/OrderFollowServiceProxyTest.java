package com.ziroom.minsu.services.order.test.proxy;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.order.OrderFollowLogEntity;
import com.ziroom.minsu.services.order.dto.OrderFollowRequest;
import com.ziroom.minsu.services.order.proxy.OrderFollowServiceProxy;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.valenum.order.FollowStatusEnum;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
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
 * @author afi on on 2016/12/14.
 * @version 1.0
 * @since 1.0
 */
public class OrderFollowServiceProxyTest extends BaseTest {


    @Resource(name = "order.orderFollowServiceProxy")
    private OrderFollowServiceProxy orderFollowServiceProxy;


    @Test
    public void Testvalue() {
        OrderFollowLogEntity log = new OrderFollowLogEntity();
        log.setOrderSn("1111");
        log.setContent("test");
        log.setCreateFid("fid");
        log.setCreateName("name");
        log.setOrderStatus(1);
        log.setFollowStatus(FollowStatusEnum.OVER.getCode());
        String json = orderFollowServiceProxy.saveOrderFollow(JsonEntityTransform.Object2Json(log));
        System.out.println(json);
    }



    @Test
    public void getOrderFollowByPage(){
        OrderFollowRequest request = new OrderFollowRequest();
       /* List<String> userUidList = new ArrayList<>();
        userUidList.add("03a9ec5a-d921-432b-b7ad-d9c429d0925d");
        userUidList.add("0f163457-ad6a-09ce-d5de-de452a251cf6");
        userUidList.add("219f8b9e-5a45-49eb-991e-c27c5b92d458");
        request.setUserUidList(userUidList);*/
        request.setLimit(10);
        String orderFollowByPage = orderFollowServiceProxy.getOrderFollowByPage(JsonEntityTransform.Object2Json(request));
        System.err.println(orderFollowByPage);
    }
}
