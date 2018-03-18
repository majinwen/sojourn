package com.ziroom.minsu.report.test.order.dao;

import base.BaseTest;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.basedata.entity.CityNumEntity;
import com.ziroom.minsu.report.house.entity.HouseNumEntity;
import com.ziroom.minsu.report.order.entity.OrderEvaluateEntity;
import com.ziroom.minsu.report.order.dto.OrderDataStatisticsRequest;
import com.ziroom.minsu.report.order.dto.OrderEvaluateRequest;
import com.ziroom.minsu.report.order.dto.OrderFollowQueryRequest;
import com.ziroom.minsu.report.order.dto.OrderHouseRequest;
import com.ziroom.minsu.report.order.dao.OrderEvaluateDao;
import com.ziroom.minsu.report.order.dao.OrderFollowDao;
import com.ziroom.minsu.report.order.dao.OrderHouseDao;
import com.ziroom.minsu.report.order.dao.OrderNumDao;
import com.ziroom.minsu.report.order.dto.OrderNumRequest;
import com.ziroom.minsu.report.order.entity.OrderNumEntity;
import com.ziroom.minsu.report.order.vo.OrderFollowNumVo;
import com.ziroom.minsu.report.order.vo.OrderFollowQueryVo;
import com.ziroom.minsu.report.order.vo.OrderFollowVo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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
 * @author afi on on 2017/1/19.
 * @version 1.0
 * @since 1.0
 */
public class OrderNumDaoTest extends BaseTest {


    private static final Logger LOGGER = LoggerFactory.getLogger(OrderNumDaoTest.class);

    @Resource(name="report.orderNumDao")
    private OrderNumDao orderNumDao;

    @Test
    public void getOrderNumByCity(){
        try {
            OrderNumRequest orderNumRequest = new OrderNumRequest();
            List<OrderNumEntity> list = orderNumDao.getOrderNumByCity(orderNumRequest);
            System.out.println(JsonEntityTransform.Object2Json(list));
        }catch (Exception e){
            LogUtil.error(LOGGER, "e:{}", e);
        }
    }



    @Test
    public void getUserNumByCity(){
        try {
            OrderNumRequest orderNumRequest = new OrderNumRequest();
            List<CityNumEntity> list = orderNumDao.getUserNumByCity(orderNumRequest);
            System.out.println(JsonEntityTransform.Object2Json(list));
        }catch (Exception e){
            LogUtil.error(LOGGER, "e:{}", e);
        }
    }





    @Test
    public void getConsultNumByCity(){
        try {
            OrderNumRequest orderNumRequest = new OrderNumRequest();
            List<CityNumEntity> list = orderNumDao.getConsultNumByCity(orderNumRequest);
            System.out.println(JsonEntityTransform.Object2Json(list));
        }catch (Exception e){
            LogUtil.error(LOGGER, "1e:{}", e);
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
    public static class OrderEvaluateDaoTest extends BaseTest {


        private static final Logger LOGGER = LoggerFactory.getLogger(OrderEvaluateDaoTest.class);

        @Resource(name="report.orderEvaluateDao")
        private OrderEvaluateDao orderEvaluateDao;

        @Test
        public void getOrderEvaluateListByCityCode(){
            try {
                OrderEvaluateRequest orderEvaluateRequest = new OrderEvaluateRequest();
                List<OrderEvaluateEntity> list = orderEvaluateDao.getOrderEvaluateListByCityCode(orderEvaluateRequest);

                System.out.println(JsonEntityTransform.Object2Json(list));
            }catch (Exception e){
                LogUtil.error(LOGGER, "e:{}", e);
            }
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
    public static class OrderHouseDaoTest extends BaseTest {


        private static final Logger LOGGER = LoggerFactory.getLogger(OrderHouseDaoTest.class);

        @Resource(name="report.orderHouseDao")
        private OrderHouseDao orderHouseDao;

        @Test
        public void getOrderEvaluateListByCityCode(){
            try {
                OrderHouseRequest orderHouseRequest = new OrderHouseRequest();

                List<String>  list = new ArrayList<>();
                list.add("110100");
                orderHouseRequest.setCityList(list);
                orderHouseRequest.setStarTime(new Date());
                List<HouseNumEntity> rst = orderHouseDao.getHouseNumByCityCode(orderHouseRequest);
                System.out.println(JsonEntityTransform.Object2Json(rst));
            }catch (Exception e){
                LogUtil.error(LOGGER, "e:{}", e);
            }
        }



        @Test
        public void getDayNumListByCityCode(){
            try {
                OrderHouseRequest orderHouseRequest = new OrderHouseRequest();
                List<String>  list = new ArrayList<>();
                list.add("110100");
                orderHouseRequest.setCityList(list);
                orderHouseRequest.setStarTime(new Date());
                List<CityNumEntity> rst = orderHouseDao.getDayNumListByCityCode(orderHouseRequest);
                System.out.println(JsonEntityTransform.Object2Json(rst));
            }catch (Exception e){
                LogUtil.error(LOGGER, "e:{}", e);
            }
        }


        @Test
        public void getOrderNumListByCityCode(){
            try {
                OrderHouseRequest orderHouseRequest = new OrderHouseRequest();
                List<String>  list = new ArrayList<>();
                list.add("110100");
                orderHouseRequest.setCityList(list);
                orderHouseRequest.setStarTime(new Date());
                List<CityNumEntity> rst = orderHouseDao.getOrderNumListByCityCode(orderHouseRequest);
                System.out.println(JsonEntityTransform.Object2Json(rst));
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
     * @author lishaochuan on 2016/12/15 11:41
     * @version 1.0
     * @since 1.0
     */
    public static class OrderFollowDaoTest extends BaseTest {

        @Resource(name="report.orderFollowDao")
        private OrderFollowDao orderFollowDao;

        @Test
        public void sumOrderDataStatistics(){
            OrderDataStatisticsRequest orderDataStatisticsRequest = new OrderDataStatisticsRequest();
            // orderDataStatisticsRequest.setCity("110100");
            OrderFollowVo orderFollowVo = orderFollowDao.sumOrderDataStatistics(orderDataStatisticsRequest);
            System.err.println(JsonEntityTransform.Object2Json(orderFollowVo));
        }

        @Test
        public void orderStaticsDao() throws Exception {
            OrderDataStatisticsRequest orderDataStatisticsRequest = new OrderDataStatisticsRequest();
            // orderDataStatisticsRequest.setCity("110100");
            PagingResult<OrderFollowVo> orderFollowVos = orderFollowDao.orderDataStatistics(orderDataStatisticsRequest);
            System.err.println(JsonEntityTransform.Object2Json(orderFollowVos));
        }

        @Test
        public void folowOrderStatusNum(){
            OrderDataStatisticsRequest orderDataStatisticsRequest = new OrderDataStatisticsRequest();
            List<OrderFollowNumVo> orderFollowNumVos = orderFollowDao.followOrderStatusNum(orderDataStatisticsRequest);
            System.err.println(JsonEntityTransform.Object2Json(orderFollowNumVos));
        }

        @Test
        public void getOrderFollowQuery(){
            OrderFollowQueryRequest orderFollowQueryRequest = new OrderFollowQueryRequest();
            orderFollowQueryRequest.setPayStatus(0);
            PagingResult<OrderFollowQueryVo> orderFollowQuery = orderFollowDao.getOrderFollowQuery(orderFollowQueryRequest);
            System.err.println(JsonEntityTransform.Object2Json(orderFollowQuery));
        }
    }
}
