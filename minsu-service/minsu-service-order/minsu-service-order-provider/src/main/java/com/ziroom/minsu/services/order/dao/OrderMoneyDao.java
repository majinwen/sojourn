package com.ziroom.minsu.services.order.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderMoneyEntity;
import com.ziroom.minsu.services.order.dto.OrderProfitRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>订单的金额的操作 </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/8.
 * @version 1.0
 * @since 1.0
 */
@Repository("order.orderMoneyDao")
public class OrderMoneyDao {
	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(OrderMoneyDao.class);

	private String SQLID = "order.orderMoneyDao.";

	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * 获取订单价格
	 * @return
	 */
	public List<OrderMoneyEntity> getOrderMoneyList() {
		return mybatisDaoContext.findAll(SQLID + "orderMoneyDao", OrderMoneyEntity.class);
	}


    /**
     * 获取订单的金额
     * @author afi
     * @param orderSn
     * @return
     */
    public OrderMoneyEntity getOrderMoneyByOrderSn(String orderSn) {
        return mybatisDaoContext.findOne(SQLID + "getOrderMoneyByOrderSn", OrderMoneyEntity.class, orderSn);
    }


    /**
     * 保存订单金额信息
     * @author afi
     * @param orderMoneyEntity
     * @return
     */
    public int insertOrderMoney(OrderMoneyEntity orderMoneyEntity){
        if(Check.NuNObj(orderMoneyEntity)){
        	LogUtil.info(logger,"orderMoneyEntity is null on insertOrderMoney");
            throw new BusinessException("orderMoneyEntity is null on insertOrderMoney");
        }

        if(Check.NuNStr(orderMoneyEntity.getFid())){
            orderMoneyEntity.setFid(UUIDGenerator.hexUUID());
        }
        return mybatisDaoContext.save(SQLID + "insertOrderMoney", orderMoneyEntity);
    }

    /**
     * 保存订单金额信息
     * @author afi
     * @param orderMoneyEntity
     * @return
     */
    public int updateOrderMoney(OrderMoneyEntity orderMoneyEntity){
        if(Check.NuNObj(orderMoneyEntity)){
        	LogUtil.info(logger,"orderMoneyEntity is null on insertOrderMoney");
            throw new BusinessException("orderMoneyEntity is null on insertOrderMoney");
        }
        if(Check.NuNStr(orderMoneyEntity.getOrderSn())){
        	LogUtil.info(logger,"orderSn is null on insertOrderMoney");
            throw new BusinessException("orderSn is null on insertOrderMoney");
        }
        
        return mybatisDaoContext.update(SQLID + "updateOrderMoney", orderMoneyEntity);
    }
    

    /**
     * 计算 一个房源 一个月 实际收益
     * @author liyingjie
     * @param profitRequest
     * @return
     */
    public long countMonthRealProfit(OrderProfitRequest profitRequest) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("houseFid", profitRequest.getHouseFid());
        paramMap.put("beginTime", profitRequest.getBeginTime());
        paramMap.put("endTime", profitRequest.getEndTime());
        paramMap.put("rentWay", profitRequest.getRentWay());
        paramMap.put("roomFid", profitRequest.getRoomFid());
        return mybatisDaoContext.count(SQLID + "monthRealProfit", paramMap);
    }
    
    /**
     * 计算 一个房源 一个月 预计收益
     * @author liyingjie
     * @param profitRequest
     * @return
     */
    public long countMonthPredictProfit(OrderProfitRequest profitRequest) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("houseFid", profitRequest.getHouseFid());
        paramMap.put("beginTime", profitRequest.getBeginTime());
        paramMap.put("endTime", profitRequest.getEndTime());
        paramMap.put("rentWay", profitRequest.getRentWay());
        paramMap.put("roomFid", profitRequest.getRoomFid());
        return mybatisDaoContext.count(SQLID + "monthPredictProfit", paramMap);
    }
    
}
