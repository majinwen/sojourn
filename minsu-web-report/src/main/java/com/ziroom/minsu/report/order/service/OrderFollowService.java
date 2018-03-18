package com.ziroom.minsu.report.order.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.common.service.ReportService;
import com.ziroom.minsu.report.order.dao.OrderFollowDao;
import com.ziroom.minsu.report.order.dto.OrderDataStatisticsRequest;
import com.ziroom.minsu.report.order.dto.OrderFollowQueryRequest;
import com.ziroom.minsu.report.order.vo.OrderFollowNumVo;
import com.ziroom.minsu.report.order.vo.OrderFollowQueryVo;
import com.ziroom.minsu.report.order.vo.OrderFollowVo;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
 * @author lishaochuan on 2016/12/15 10:58
 * @version 1.0
 * @since 1.0
 */
@Service("report.orderfollowservice")
public class OrderFollowService implements ReportService <OrderFollowVo,OrderDataStatisticsRequest>{

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderFollowService.class);

    @Resource(name = "report.orderFollowDao")
    private OrderFollowDao orderFollowDao;

    /**
     * 订单客服数据统计报表
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2016/12/16 14:44
     */
    public PagingResult<OrderFollowVo> orderDataStatistics(OrderDataStatisticsRequest orderDataStatisticsRequest) {
        PagingResult<OrderFollowVo> orderFollowVos = orderFollowDao.orderDataStatistics(orderDataStatisticsRequest);
        OrderFollowVo orderFollowVo = orderFollowDao.sumOrderDataStatistics(orderDataStatisticsRequest);
        orderFollowVos.getRows().add(orderFollowVo);
        return orderFollowVos;
    }


    /**
     * 跟进订单状态数据统计表
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2016/12/16 14:44
     */
    public PagingResult<OrderFollowNumVo> followOrderStatusNum(OrderDataStatisticsRequest orderDataStatisticsRequest) {

        List<OrderFollowNumVo> orderFollowNumVos = orderFollowDao.followOrderStatusNum(orderDataStatisticsRequest);
        for (OrderFollowNumVo orderFollowNumVo : orderFollowNumVos) {
            try {
                orderFollowNumVo.setOrderStatus(OrderStatusEnum.getOrderStatusByCode(ValueUtil.getintValue(orderFollowNumVo.getOrderStatus())).getStatusName());
            }catch (Exception e){
                LogUtil.error(LOGGER, "e:{}", e);
            }
        }
        PagingResult<OrderFollowNumVo> orderFollowNumVoPagingResult = new PagingResult<>();
        orderFollowNumVoPagingResult.setRows(orderFollowNumVos);
        orderFollowNumVoPagingResult.setTotal(orderFollowNumVos.size());
        return orderFollowNumVoPagingResult;
    }


    /**
     * 客服跟进数据报表查询功能
     * 
     * @author lishaochuan
     * @create 2016/12/19 17:57
     * @param 
     * @return 
     */
    public PagingResult<OrderFollowQueryVo> getOrderFollowQuery(OrderFollowQueryRequest orderFollowQueryRequest){
        return orderFollowDao.getOrderFollowQuery(orderFollowQueryRequest);
    }


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#getPageInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public PagingResult<OrderFollowVo> getPageInfo(OrderDataStatisticsRequest par) {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#countDataInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public Long countDataInfo(OrderDataStatisticsRequest par) {
		// TODO Auto-generated method stub
		return null;
	}
}
