package com.ziroom.minsu.services.order.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderSmartLockEntity;

/**
 * 
 * <p>订单智能所操作dao</p>
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
@Repository("order.orderSmartLockDao")
public class OrderSmartLockDao {
	
	/**
	 * 日志对象
	 */
	private static Logger logger =LoggerFactory.getLogger(OrderSmartLockDao.class);

	private String SQLID = "order.orderSmartLockDao.";

	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;


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
		return mybatisDaoContext.save(SQLID+"insertOrderSmartLock", orderSmartLock);
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
    	return mybatisDaoContext.update(SQLID + "updateOrderSmartLockByFid", orderSmartLock);
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
    	return mybatisDaoContext.update(SQLID + "updateOrderSmartLockByOrderSn", orderSmartLock);
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
    	if(Check.NuNObj(orderSmartLock)
    			||Check.NuNStr(orderSmartLock.getServiceId())){
    		LogUtil.info(logger, "待修改对象为null或者serviceId不存在");
    		return -1;
    	}
    	return mybatisDaoContext.update(SQLID + "updateOrderSmartLockByServiceId", orderSmartLock);
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
		return mybatisDaoContext.findOne(SQLID + "findOrderSmartLockByFid", OrderSmartLockEntity.class, fid);
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
		return mybatisDaoContext
				.findOne(SQLID + "findOrderSmartLockByOrderSn", OrderSmartLockEntity.class, orderSn);
	}
    
}
