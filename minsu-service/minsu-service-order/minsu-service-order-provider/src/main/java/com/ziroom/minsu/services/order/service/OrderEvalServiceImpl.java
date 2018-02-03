package com.ziroom.minsu.services.order.service;

import com.ziroom.minsu.services.order.dao.OrderEvalDao;
import com.ziroom.minsu.services.order.dto.EvalSynRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>订单评价</p>
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
@Service("order.orderEvalServiceImpl")
public class OrderEvalServiceImpl {

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEvalServiceImpl.class);


    @Resource(name = "order.orderEvalDao")
    private OrderEvalDao orderEvalDao;


    /**
     * 同步当前的初评状态
     * @author afi
     * @param evalSynRequest
     * @return
     */
    public int updatePjStatuByOrderSn(EvalSynRequest evalSynRequest){
        return orderEvalDao.updatePjStatuByOrderSn(evalSynRequest);
    }


    /**
     * 获取当前用户待评价的订单数量
     * @author afi
     * @param userUid
     * @return
     */
    public Long countUserWaitEvaNumAll(String userUid,int limitDay){
        return orderEvalDao.countUserWaitEvaNumAll(userUid,limitDay);
    }


    /**
     * 获取当前房东待评价的订单数量
     * @author afi
     * @param landUid
     * @return
     */
    public Long countLandWaitEvaNumAll(String landUid,int limitDay){
        return orderEvalDao.countLandWaitEvaNumAll(landUid,limitDay);
    }
}
