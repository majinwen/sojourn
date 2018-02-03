package com.ziroom.minsu.services.order.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.ziroom.minsu.entity.order.OrderToPayEntity;
import com.ziroom.minsu.services.order.dao.OrderToPayDao;


@Service("order.orderToPayServiceImpl")
public class OrderToPayServiceImpl {
	
	
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderToPayServiceImpl.class);
	
	
	@Resource(name = "order.toPayDao")
	private OrderToPayDao orderToPayDao;
	
	
	/**
	 * 根据orderSn获取OrderToPay
	 * @author lishaochuan
	 * @create 2016年9月22日下午10:59:00
	 * @param payCode
	 */
	public OrderToPayEntity selectByOrderSn(String orderSn){
        if(Check.NuNStr(orderSn)){
            throw new BusinessException("orderSn为空");
        }
        return orderToPayDao.selectByOrderSn(orderSn);
    }
	
	/**
	 * 根据payCode获取OrderToPay
	 * @author lishaochuan
	 * @create 2016年9月22日下午10:59:00
	 * @param payCode
	 */
	public OrderToPayEntity selectByPayCode(String payCode){
        if(Check.NuNStr(payCode)){
            throw new BusinessException("payCode为空");
        }
        return orderToPayDao.selectByPayCode(payCode);
    }
	
	
	/**
	 * 保存OrderToPay
	 * @author lishaochuan
	 * @create 2016年9月22日下午11:01:10
	 * @param orderToPayEntity
	 */
	public void saveOrderToPay(OrderToPayEntity orderToPayEntity){
		if(Check.NuNObj(orderToPayEntity)){
            throw new BusinessException("orderToPayEntity为空");
        }
		orderToPayDao.insertToPay(orderToPayEntity);
	}

}
