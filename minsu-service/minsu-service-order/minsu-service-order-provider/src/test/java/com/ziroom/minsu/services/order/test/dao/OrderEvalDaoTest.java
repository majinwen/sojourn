package com.ziroom.minsu.services.order.test.dao;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.order.dao.OrderEvalDao;
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
 * @author afi on on 2016/11/12.
 * @version 1.0
 * @since 1.0
 */
public class OrderEvalDaoTest extends BaseTest {


    @Resource(name = "order.orderEvalDao")
    private OrderEvalDao orderEvalDao;

    @Test
    public void countUserWaitEvaNumAll() {

        Long  count = orderEvalDao.countUserWaitEvaNumAll("123",2);
        System.out.println(JsonEntityTransform.Object2Json(count));
    }

    @Test
    public void countLandWaitEvaNumAll() {

        Long  count = orderEvalDao.countLandWaitEvaNumAll("123",2);
        System.out.println(JsonEntityTransform.Object2Json(count));
    }

}
