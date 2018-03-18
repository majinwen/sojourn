package com.ziroom.minsu.report.order.dao;


import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.report.order.dto.OrderDataStatisticsRequest;
import com.ziroom.minsu.report.order.dto.OrderFollowQueryRequest;
import com.ziroom.minsu.report.order.vo.OrderFollowNumVo;
import com.ziroom.minsu.report.order.vo.OrderFollowQueryVo;
import com.ziroom.minsu.report.order.vo.OrderFollowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/12/15 10:49
 * @version 1.0
 * @since 1.0
 */
@Repository("report.orderFollowDao")
public class OrderFollowDao {

    private String SQLID = "report.orderFollowDao.";

    @Autowired
    @Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 订单客服数据统计总计
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2016/12/16 14:25
     */
    public OrderFollowVo sumOrderDataStatistics(OrderDataStatisticsRequest orderDataStatisticsRequest) {
        return mybatisDaoContext.findOneSlave(SQLID + "sumOrderDataStatisticsNew", OrderFollowVo.class, orderDataStatisticsRequest);
    }

    /**
     * 订单客服数据统计报表
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2016/12/16 14:25
     */
    public PagingResult<OrderFollowVo> orderDataStatistics(OrderDataStatisticsRequest orderDataStatisticsRequest) {
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(orderDataStatisticsRequest.getLimit());
        pageBounds.setPage(orderDataStatisticsRequest.getPage());
        return mybatisDaoContext.findForPage(SQLID + "orderDataStatisticsNew", OrderFollowVo.class, orderDataStatisticsRequest.toMap(), pageBounds);
    }


    /**
     * 跟进订单状态数据统计表
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2016/12/16 14:28
     */
    public List<OrderFollowNumVo> followOrderStatusNum(OrderDataStatisticsRequest orderDataStatisticsRequest) {
        return mybatisDaoContext.findAll(SQLID + "followOrderStatusNum", OrderFollowNumVo.class, orderDataStatisticsRequest.toMap());
    }



    /**
     * 客服跟进数据报表查询功能
     *
     * @author lishaochuan
     * @create 2016/12/19 17:53
     * @param 
     * @return 
     */
    public PagingResult<OrderFollowQueryVo> getOrderFollowQuery(OrderFollowQueryRequest orderFollowQueryRequest){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(orderFollowQueryRequest.getLimit());
        pageBounds.setPage(orderFollowQueryRequest.getPage());
        return mybatisDaoContext.findForPage(SQLID + "getOrderFollowQuery", OrderFollowQueryVo.class, orderFollowQueryRequest, pageBounds);
    }

}
