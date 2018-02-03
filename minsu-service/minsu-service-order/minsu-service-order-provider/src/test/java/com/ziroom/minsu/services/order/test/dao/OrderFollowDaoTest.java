package com.ziroom.minsu.services.order.test.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.order.OrderFollowEntity;
import com.ziroom.minsu.entity.order.OrderFollowLogEntity;
import com.ziroom.minsu.services.order.dao.OrderFollowDao;
import com.ziroom.minsu.services.order.dao.OrderFollowLogDao;
import com.ziroom.minsu.services.order.dto.OrderFollowRequest;
import com.ziroom.minsu.services.order.entity.OrderFollowVo;
import com.ziroom.minsu.services.order.entity.OrderHouseVo;
import com.ziroom.minsu.services.order.entity.UidVo;
import com.ziroom.minsu.services.order.test.base.BaseTest;
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
public class OrderFollowDaoTest extends BaseTest {

    @Resource(name = "order.orderFollowDao")
    private OrderFollowDao orderFollowDao;


    @Test
    public void saveOrderFollowLog(){
        OrderFollowEntity log = new OrderFollowEntity();
        log.setOrderSn("1231111");
        log.setCreateFid("fid");
        log.setCreateName("name");
        log.setOrderStatus(1);
        int list = orderFollowDao.saveOrderFollow(log);
        System.err.println(JsonEntityTransform.Object2Json(list));
    }


    @Test
    public void getOrderFollowByOrderSn(){
        OrderFollowEntity list = orderFollowDao.getOrderFollowByOrderSn("123");
        System.err.println(JsonEntityTransform.Object2Json(list));
    }


    @Test
    public void getOrderFollowUidByPage(){
        OrderFollowRequest request = new OrderFollowRequest();
        request.setLimit(10);
        PagingResult<UidVo> orderFollowUidByPage = orderFollowDao.getOrderFollowUidByPage(request);
        System.err.println(JsonEntityTransform.Object2Json(orderFollowUidByPage));
    }



    @Test
    public void getOrderFollow(){
        OrderFollowRequest request  = new OrderFollowRequest();
        List<String> userUidList = new ArrayList<>();
        userUidList.add("03a9ec5a-d921-432b-b7ad-d9c429d0925d");
        userUidList.add("0f163457-ad6a-09ce-d5de-de452a251cf6");
        userUidList.add("219f8b9e-5a45-49eb-991e-c27c5b92d458");
        request.setUserUidList(userUidList);
        List<OrderFollowVo> orderFollow = orderFollowDao.getOrderFollow(request);
        System.err.println(JsonEntityTransform.Object2Json(orderFollow));
    }

}
