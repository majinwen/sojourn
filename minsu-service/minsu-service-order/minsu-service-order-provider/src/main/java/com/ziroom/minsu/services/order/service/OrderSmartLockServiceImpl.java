package com.ziroom.minsu.services.order.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderSmartLockEntity;
import com.ziroom.minsu.services.order.dao.OrderSmartLockDao;

/**
 * 
 * <p>订单智能所service</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
@Service("order.orderSmartLockServiceImpl")
public class OrderSmartLockServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderSmartLockServiceImpl.class);

    @Resource(name = "order.orderSmartLockDao")
    private OrderSmartLockDao orderSmartLockDao;

    /**
     * 
     * 新增订单智能锁信息
     *
     * @author liujun
     * @created 2016年6月23日
     *
     * @param orderSmartLock
     */
    public int insertOrderSmartLock(OrderSmartLockEntity orderSmartLock) {
    	if(Check.NuNObj(orderSmartLock)){
    		LogUtil.info(LOGGER, "orderSmartLock is null or blank");
    		return 0;
    	}
    	
    	if(Check.NuNStr(orderSmartLock.getFid())){
    		orderSmartLock.setFid(UUIDGenerator.hexUUID());
    	}
    	
		return orderSmartLockDao.insertOrderSmartLock(orderSmartLock);
	}
    
    /**
     * 
     * 更新订单智能锁信息
     *
     * @author liujun
     * @created 2016年4月9日 上午11:51:38
     *
     * @param orderSmartLock
     * @return
     */
    public int updateOrderSmartLock(OrderSmartLockEntity orderSmartLock) {
    	if(Check.NuNObj(orderSmartLock)){
    		LogUtil.info(LOGGER, "orderSmartLock is null or blank");
    		return 0;
    	}
    	
    	if(Check.NuNStr(orderSmartLock.getFid())){
    		LogUtil.info(LOGGER, "orderSmartLock#fid is null or blank");
    		return 0;
    	}
    	
    	return orderSmartLockDao.updateOrderSmartLock(orderSmartLock);
    } 
    
    /**
     * 
     * 更新订单智能锁信息
     *
     * @author liujun
     * @created 2016年6月23日
     *
     * @param orderSmartLock
     * @return
     */
    public int updateOrderSmartLockByOrderSn(OrderSmartLockEntity orderSmartLock) {
    	if(Check.NuNObj(orderSmartLock)){
    		LogUtil.info(LOGGER, "orderSmartLock is null or blank");
    		return 0;
    	}
    	
    	if(Check.NuNStr(orderSmartLock.getOrderSn())){
    		LogUtil.info(LOGGER, "orderSmartLock#orderSn is null or blank");
    		return 0;
    	}
    	
    	return orderSmartLockDao.updateOrderSmartLockByOrderSn(orderSmartLock);
    }
    
    /**
     * 
     * 更新订单智能锁信息
     *
     * @author liujun
     * @created 2016年6月23日
     *
     * @param orderSmartLock
     * @return
     */
    public int updateOrderSmartLockByServiceId(OrderSmartLockEntity orderSmartLock) {
    	
    	return orderSmartLockDao.updateOrderSmartLockByServiceId(orderSmartLock);
    }
    /**
     * 
     * 查询订单智能锁信息
     *
     * @author liujun
     * @created 2016年6月23日
     *
     * @param fid 智能锁逻辑id
     * @return
     */
    public OrderSmartLockEntity findOrderSmartLockByFid(String fid) {
    	if(Check.NuNStr(fid)){
    		LogUtil.info(LOGGER, "orderSmartLock#fid is null or blank");
    		return null;
    	}
    	
		return orderSmartLockDao.findOrderSmartLockByFid(fid);
    }
    
    /**
     * 
     * 查询订单智能锁信息
     *
     * @author liujun
     * @created 2016年6月23日
     *
     * @param orderSn 订单编号
     * @return
     */
	public OrderSmartLockEntity findOrderSmartLockByOrderSn(String orderSn) {
		if(Check.NuNStr(orderSn)){
			LogUtil.info(LOGGER, "orderSmartLock#orderSn is null or blank");
			return null;
		}
		
		return orderSmartLockDao.findOrderSmartLockByOrderSn(orderSn);
	}
}
