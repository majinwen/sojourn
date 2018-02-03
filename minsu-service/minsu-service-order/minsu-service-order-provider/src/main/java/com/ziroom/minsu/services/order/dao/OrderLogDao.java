package com.ziroom.minsu.services.order.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderLogEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p> 订单的操作记录 </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/12.
 * @version 1.0
 * @since 1.0
 */
@Repository("order.orderLogDao")
public class OrderLogDao {

	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(OrderLogDao.class);
	private String SQLID = "order.orderLogDao.";

	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * 获取订单log列表
	 * @author afi
	 * @created 2016年3月31日 下午20:22:38
	 * @return
	 */
	public List<OrderLogEntity> getOrderLogList() {
		return mybatisDaoContext.findAll(SQLID + "getOrderLogList", OrderLogEntity.class);
	}

	/**
	 * 获取订单log列表
	 * @author afi
	 * @created 2016年9月18日 下午20:22:38
	 * @return
	 */
	public List<OrderLogEntity> getOrderLogListByOrderSn(String orderSn) {
		if (Check.NuNStr(orderSn)){
			return null;
		}
		return mybatisDaoContext.findAll(SQLID + "getOrderLogListByOrderSn", OrderLogEntity.class,orderSn);
	}


	/**
	 * 保存订单的操作记录
	 * @author afi
	 * @created 2016年3月31日 下午20:22:38
	 * @param log
	 * @return
	 */
	public int insertOrderLog(OrderLogEntity log) {
		if(Check.NuNObj(log)){
			LogUtil.info(logger,"current log is null on insertOrderLog");
			throw new BusinessException("current log is null on insertOrderLog");
		}
		if(Check.NuNStr(log.getOrderSn())){
			LogUtil.info(logger,"orderSn is null on insertOrderLog");
			throw new BusinessException("orderSn is null on insertOrderLog");
		}
		if(Check.NuNStr(log.getFid())){
			log.setFid(UUIDGenerator.hexUUID());
		}
		return mybatisDaoContext.save(SQLID + "insertOrderLog", log);
	}

}
