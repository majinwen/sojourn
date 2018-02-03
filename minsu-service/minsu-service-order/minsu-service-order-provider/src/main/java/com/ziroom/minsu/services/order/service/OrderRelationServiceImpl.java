package com.ziroom.minsu.services.order.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderRelationEntity;
import com.ziroom.minsu.services.order.dao.OrderRelationDao;
import com.ziroom.minsu.services.order.dto.OrderRelationRequest;
import com.ziroom.minsu.services.order.entity.OrderRelationVo;


/**
 * <p>新旧订单关联实现业务处理层</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
@Service("order.orderRelationServiceImpl")
public class OrderRelationServiceImpl {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(OrderRelationServiceImpl.class);	
	
	@Resource(name = "order.orderRelationDao")
	private OrderRelationDao orderRelationDao;
	
	/**
	 * 
	 * 分页按条件查询 新旧订单关联实体
	 *
	 * @author yd
	 * @created 2016年4月25日 下午3:30:39
	 *
	 * @param orderRelationRequest
	 * @return
	 */
	public PagingResult<OrderRelationVo> queryOrderRelanionByPage(OrderRelationRequest orderRelationRequest){
    	return orderRelationDao.queryOrderRelanionByPage(orderRelationRequest);
	}
	
	/**
	 * 
	 * 保存实体
	 *
	 * @author yd
	 * @created 2016年4月25日 下午1:41:10
	 *
	 * @param orderRelationEntity
	 * @return
	 */
	public int insert(OrderRelationEntity orderRelationEntity){
		LogUtil.info(logger, "待保存实体orderRelationEntity={}", orderRelationEntity.toJsonStr());
		return this.orderRelationDao.insert(orderRelationEntity);
	}
	/**
	 * 
	 * 按fid或 新旧订单号 更新实体状态  
	 *
	 * @author yd
	 * @created 2016年4月25日 下午1:44:43
	 *
	 * @param orderRelationEntity
	 * @return
	 */
	public int updateByCondition(OrderRelationEntity orderRelationEntity){
		
		LogUtil.info(logger, "待更新的实体对象orderRelationEntity={}",orderRelationEntity);
		return this.orderRelationDao.updateByCondition(orderRelationEntity);
	}
	/**
	 * 
	 * get OrderRelationEntity by oldOrderSn
	 *
	 * @author yd
	 * @created 2016年4月26日 下午2:26:49
	 *
	 * @param oldOrderSn
	 * @return
	 */
	public List<OrderRelationEntity> queryByOldOrderSn(OrderRelationRequest orderRelationRequest){
		LogUtil.info(logger, "根据条件orderRelationRequest={}查询", orderRelationRequest);
		return this.orderRelationDao.queryByOldOrderSn(orderRelationRequest);
	}
	
}
