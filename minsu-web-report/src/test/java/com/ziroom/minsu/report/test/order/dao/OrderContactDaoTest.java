package com.ziroom.minsu.report.test.order.dao;

import base.BaseTest;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.order.dao.OrderContactDao;
import com.ziroom.minsu.report.order.dto.OrderContactRequest;
import com.ziroom.minsu.report.order.vo.OrderContactVo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * @author afi on on 2017/3/10.
 * @version 1.0
 * @since 1.0
 */
public class OrderContactDaoTest extends BaseTest {


    private static final Logger LOGGER = LoggerFactory.getLogger(OrderNumDaoTest.class);

    @Resource(name="report.orderContactDao")
    private OrderContactDao orderContactDao;

    @Test
    public void getOrderContactByPage(){
        try {
            OrderContactRequest orderContactRequest = new OrderContactRequest();
            PagingResult<OrderContactVo> pagingResult = orderContactDao.getOrderContactByPage(orderContactRequest);
            System.out.println(JsonEntityTransform.Object2Json(pagingResult));
        }catch (Exception e){
            LogUtil.error(LOGGER, "e:{}", e);
        }
    }
}
