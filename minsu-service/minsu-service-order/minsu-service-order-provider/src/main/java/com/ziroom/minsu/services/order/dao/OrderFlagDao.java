package com.ziroom.minsu.services.order.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderFlagEntity;

/**
 * <p>订单标记表dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年7月29日
 * @since 1.0
 * @version 1.0
 */
@Repository("order.orderFlagDao")
public class OrderFlagDao {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(OrderFlagDao.class);
	private String SQLID = "order.orderFlagDao.";

	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	
	/**
	 * 保存
	 * @author lishaochuan
	 * @create 2016年8月1日上午9:40:48
	 * @param orderFlag
	 * @return
	 */
	public int saveOrderFlag(OrderFlagEntity orderFlag) {
		if(Check.NuNObj(orderFlag)){
			LogUtil.error(logger, "orderFlag为空");
			throw new BusinessException("orderFlag为空");
		}
		if(Check.NuNStr(orderFlag.getFid())){
			orderFlag.setFid(UUIDGenerator.hexUUID());
		}
		return mybatisDaoContext.save(SQLID + "saveOrderFlag", orderFlag);
	}
}
