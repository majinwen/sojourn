package com.ziroom.minsu.services.order.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderConfigEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> 订单的配置 </p>
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
@Repository("order.orderConfigDao")
public class OrderConfigDao {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(OrderConfigDao.class);
	private String SQLID = "order.orderConfigDao.";

	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * 获取订单配置列表
	 * @author afi
	 * @created 2016年3月31日 下午20:22:38
	 * @return
	 */
	public List<OrderConfigEntity> getOrderConfigList() {
		return mybatisDaoContext.findAll(SQLID + "getOrderConfigList", OrderConfigEntity.class);
	}





    /**
     * 获取当前的订单配置列表
     * @author afi
     * @created 2016年3月31日 下午20:22:38
     * @param orderSn 订单编号
     * @return
     */
    public OrderConfigEntity getOrderConfigByOrderSnAndCode(String orderSn,String code) {
        Map<String,Object> par = new HashMap<>();
        par.put("orderSn",orderSn);
        par.put("code",code);
        return mybatisDaoContext.findOne(SQLID + "getOrderConfigByOrderSnAndCode", OrderConfigEntity.class, par);
    }


	/**
	 * 获取当前的订单配置列表
	 * @author afi
	 * @created 2016年3月31日 下午20:22:38
	 * @param orderSn 订单编号
	 * @return
	 */
	public List<OrderConfigEntity> getOrderConfigListByOrderSn(String orderSn) {
		return mybatisDaoContext.findAll(SQLID + "getOrderConfigListByOrderSn", OrderConfigEntity.class,orderSn);
	}



	/**
	 * 保存订单的配置信息
	 * @author afi
	 * @created 2016年3月31日 下午20:22:38
	 * @param config
	 * @return
	 */
	public int insertOrderConfig(OrderConfigEntity config) {
		if(Check.NuNObj(config)){
			LogUtil.info(logger,"current log is null on insertOrderConfig");
			throw new BusinessException("current log is null on insertOrderConfig");
		}
		if(Check.NuNStr(config.getOrderSn())){
			LogUtil.info(logger,"orderSn is null on insertOrderConfig");
			throw new BusinessException("orderSn is null on insertOrderConfig");
		}
		return mybatisDaoContext.save(SQLID + "insertOrderConfig", config);
	}





	/**
	 * 根据orderSn，confCode 修改t_order_config的config_value
	 *
	 * @author loushuai
	 * @created 2017年5月12日 下午1:46:52
	 *
	 * @param orderSn
	 * @param confCode
	 * @param confValue
	 */
	public int updateOrderConfValue(String orderSn, String confCode,String confValue) {
		Map<String, Object> map = new HashMap<>();
		map.put("orderSn", orderSn);
		map.put("confCode", confCode);
		map.put("confValue", confValue);
		return mybatisDaoContext.update(SQLID + "updateOrderConfValue", map);
		
	}

}
