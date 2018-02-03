package com.ziroom.minsu.services.order.test.dao;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.order.OrderFollowLogEntity;
import com.ziroom.minsu.services.order.dao.OrderFollowLogDao;
import com.ziroom.minsu.services.order.test.base.BaseTest;
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
 * @author afi on on 2016/12/14.
 * @version 1.0
 * @since 1.0
 */
public class OrderFollowLogDaoTest extends BaseTest {

    @Resource(name = "order.orderFollowLogDao")
    private OrderFollowLogDao orderFollowLogDao;



    @Test
    public void getOrderFollowLogListByOrderSn(){

        List<OrderFollowLogEntity> list = orderFollowLogDao.getOrderFollowLogListByOrderSn("123");
        System.err.println(JsonEntityTransform.Object2Json(list));
    }

    @Test
    public void saveOrderFollowLog(){
        OrderFollowLogEntity log = new OrderFollowLogEntity();
        log.setOrderSn("123");
        log.setContent("test");
        log.setCreateFid("fid");
        log.setCreateName("name");
        log.setOrderStatus(1);
        log.setFollowFid("fddddd");
        int list = orderFollowLogDao.saveOrderFollowLog(log);
        System.err.println(JsonEntityTransform.Object2Json(list));
    }


}
