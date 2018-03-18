package com.ziroom.minsu.report.test.order.service;

import base.BaseTest;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.order.dto.OrderNumRequest;
import com.ziroom.minsu.report.order.service.OrderNumService;
import com.ziroom.minsu.report.order.vo.OrderNumVo;
import com.ziroom.minsu.report.test.order.dao.OrderNumDaoTest;
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
 * @author afi on on 2017/2/15.
 * @version 1.0
 * @since 1.0
 */
public class OrderNumServiceTest extends BaseTest{


    private static final Logger LOGGER = LoggerFactory.getLogger(OrderNumDaoTest.class);

    @Resource(name="report.orderNumService")
    private OrderNumService orderNumService;

    @Test
    public void getOrderNumBypage(){
        try {
            OrderNumRequest orderNumRequest = new OrderNumRequest();
            orderNumRequest.setNationCode("100000");
            PagingResult<OrderNumVo> pagingResult = orderNumService.getOrderNumBypage(orderNumRequest);
            System.out.println(JsonEntityTransform.Object2Json(pagingResult));
        }catch (Exception e){
            LogUtil.error(LOGGER, "e:{}", e);
        }
    }

}
