package com.ziroom.minsu.services.order.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderPayEntity;
import com.ziroom.minsu.services.common.vo.HouseStatsVo;


/**
 * <p>
 * 城市信息
 * </p>
 * <p/>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liyingjie on 2016/4/1.
 * @version 1.0
 * @since 1.0
 */
@Repository("order.payDao")
public class OrderPayDao {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(OrderPayDao.class);
	
	private String SQLID = "order.payDao.";

	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	
	
	/**
	 *
	 * 插入资源记录
	 *
	 * @author liyingjie
	 * @created 2016年4月1日 
	 *
	 * @param paramEntity
	 */
	public int insertOrderPayRes(OrderPayEntity orderPayEntity) {
		if(Check.NuNObj(orderPayEntity)){
			LogUtil.info(logger,"current orderPayEntity is null on insertPay"+orderPayEntity);
		}
		if(Check.NuNStr(orderPayEntity.getFid())){
			orderPayEntity.setFid(UUIDGenerator.hexUUID());
		}
		return mybatisDaoContext.save(SQLID + "insertPay", orderPayEntity);
	}
	
	
	/**
	 * 根据订单号获取付信息
	 * @author lishaochuan
	 * @create 2016年8月24日上午11:49:03
	 * @param orderSn
	 * @return
	 */
	public OrderPayEntity getOrderPayByOrderSn(String orderSn){
		return mybatisDaoContext.findOne(SQLID + "selectByOrderSn", OrderPayEntity.class, orderSn);
	}
	
	
	/**
	 *
	 * 查询 资源记录
	 *
	 * @author liyingjie
	 * @created 2016年4月1日 
	 *
	 * @param 
	 */
	public List<OrderPayEntity> findOrderPayByCondiction(Map<String,Object> paramMap){
		return mybatisDaoContext.findAll(SQLID + "selectByCondiction", OrderPayEntity.class, paramMap);
	}
	
	
	/**
	 *
	 * 更新资源记录
	 *
	 * @author liyingjie
	 * @created 2016年4月1日 
	 *
	 * @param 
	 */
	public int updateOrderPayByCondiction(OrderPayEntity orderPayEntity){
		return mybatisDaoContext.update(SQLID + "updateByOrderSnAndPayType", orderPayEntity);
	}
	

	/**
	 * 
	 * 查询单位时间内房源(房间)的交易量(已支付订单数量)
	 *
	 * @author liujun
	 * @created 2016年12月1日
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public List<HouseStatsVo> queryTradeNumByHouseFid(Map<String, Object> paramMap){
		if (Check.NuNObjs(paramMap, paramMap.get("startTime"), paramMap.get("endTime"))) {
			paramMap.put("startTime", DateUtil.getDayBeforeCurrentDate());
			paramMap.put("endTime", DateUtil.dateFormat(new Date()));
		}
		return this.mybatisDaoContext.findAll(SQLID+"queryTradeNumByHouseFid", HouseStatsVo.class, paramMap);
	}
	
}
