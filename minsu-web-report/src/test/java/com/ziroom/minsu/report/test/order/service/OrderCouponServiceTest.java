package com.ziroom.minsu.report.test.order.service;

import base.BaseTest;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.order.entity.OrderEvaluateEntity;
import com.ziroom.minsu.report.order.dto.OrderDataStatisticsRequest;
import com.ziroom.minsu.report.order.dto.OrderEvaluateRequest;
import com.ziroom.minsu.report.order.dto.OrderCouponRequest;
import com.ziroom.minsu.report.order.service.OrderCouponService;
import com.ziroom.minsu.report.order.service.OrderEvaluateService;
import com.ziroom.minsu.report.order.service.OrderFollowService;
import com.ziroom.minsu.report.order.vo.OrderCouponVo;
import com.ziroom.minsu.report.test.order.dao.OrderNumDaoTest;
import com.ziroom.minsu.report.order.vo.OrderFollowVo;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;

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
public class OrderCouponServiceTest extends BaseTest{


    private static final Logger LOGGER = LoggerFactory.getLogger(OrderNumDaoTest.class);

    @Resource(name="report.orderCouponService")
    private OrderCouponService orderCouponService;

    @Test
    public void getOrderCouponBypage(){
        try {
            OrderCouponRequest orderCouponRequest = new OrderCouponRequest();
            orderCouponRequest.setNationCode("100000");
            PagingResult<OrderCouponVo> pagingResult = orderCouponService.getOrderCouponBypage(orderCouponRequest);
            System.out.println(JsonEntityTransform.Object2Json(pagingResult));
        }catch (Exception e){
            LogUtil.error(LOGGER, "e:{}", e);
        }
    }

    /**
     * <p>TODO</p>
     * <p/>
     * <PRE>
     * <BR>	修改记录
     * <BR>-----------------------------------------------
     * <BR>	修改日期			修改人			修改内容
     * </PRE>
     *
     * @author afi on on 2017/1/19.
     * @version 1.0
     * @since 1.0
     */
    public static class OrderEvaluateServiceTest extends BaseTest {


        private static final Logger LOGGER = LoggerFactory.getLogger(OrderEvaluateServiceTest.class);

        @Resource(name="report.orderEvaluateService")
        private OrderEvaluateService orderEvaluateService;

        @Test
        public void getOrderEvaluateListByCityCode(){
            try {
                OrderEvaluateRequest orderEvaluateRequest = new OrderEvaluateRequest();
                orderEvaluateRequest.setNationCode("100000");
                orderEvaluateRequest.setStarTime(DateSplitUtil.jumpDate(new Date(),-40));
                orderEvaluateRequest.setEndTime(DateSplitUtil.jumpDate(new Date(),140));
                PagingResult<OrderEvaluateEntity> pagingResult = orderEvaluateService.getOrderEvaluateBypage(orderEvaluateRequest);
                System.out.println(JsonEntityTransform.Object2Json(pagingResult));
            }catch (Exception e){
                LogUtil.error(LOGGER, "e:{}", e);
            }
        }
    }

    /**
     * <p></p>
     * <p>
     * <PRE>
     * <BR>	修改记录
     * <BR>-----------------------------------------------
     * <BR>	修改日期			修改人			修改内容
     * </PRE>
     *
     * @author lishaochuan on 2016/12/15 15:18
     * @version 1.0
     * @since 1.0
     */
    public static class OrderFollowServiceTest extends BaseTest {

        @Resource(name="report.orderfollowservice")
        private OrderFollowService orderFollowService;

        @Test
        public void orderDataStatistics() throws Exception {
            OrderDataStatisticsRequest orderDataStatisticsRequest = new OrderDataStatisticsRequest();
            // orderDataStatisticsRequest.setCity("110100");
            PagingResult<OrderFollowVo> orderFollowVos = orderFollowService.orderDataStatistics(orderDataStatisticsRequest);
            System.err.println(JsonEntityTransform.Object2Json(orderFollowVos));
        }
    }
}
