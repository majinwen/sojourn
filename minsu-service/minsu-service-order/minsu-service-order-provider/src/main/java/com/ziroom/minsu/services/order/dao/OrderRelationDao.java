
package com.ziroom.minsu.services.order.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderRelationEntity;
import com.ziroom.minsu.services.order.dto.OrderRelationRequest;
import com.ziroom.minsu.services.order.entity.OrderRelationVo;
import com.ziroom.minsu.valenum.order.CheckedStatusEnum;

/**
 * <p>新旧订单关联持久化处理</p>
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
@Repository("order.orderRelationDao")
public class OrderRelationDao {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(OrderRelationDao.class);
	
	private String SQLID = "order.orderRelationDao.";

	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	
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
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(orderRelationRequest.getLimit());
		pageBounds.setPage(orderRelationRequest.getPage());
    	return mybatisDaoContext.findForPage(SQLID + "selectByPage", OrderRelationVo.class, orderRelationRequest, pageBounds);
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
		return this.mybatisDaoContext.findAll(SQLID+"selectByOldOrderSn", orderRelationRequest);
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
		
		if(Check.NuNObj(orderRelationEntity)){
			LogUtil.info(logger,"orderRelationEntity is null");
			return 0;
		} 
		if(Check.NuNStr(orderRelationEntity.getFid() )) orderRelationEntity.setFid(UUIDGenerator.hexUUID());
		
		LogUtil.info(logger, "待保存实体orderRelationEntity={}", orderRelationEntity.toJsonStr());
		return this.mybatisDaoContext.save(SQLID+"insertSelective", orderRelationEntity);
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
		
		if(Check.NuNObj(orderRelationEntity)||(Check.NuNStr(orderRelationEntity.getFid())&&(Check.NuNStr(orderRelationEntity.getOldOrderSn())))){
			
			LogUtil.info(logger,"orderRelationEntity is null ,or fid,  oldOrderSn all is null");
			return 0;
		}
		
		LogUtil.info(logger, "待更新的实体对象orderRelationEntity={}",orderRelationEntity);
		return this.mybatisDaoContext.update(SQLID+"updateByCondition", orderRelationEntity);
	}
	
	/**
	 * 
	 * 去关联订单时候 校验 
	 * 未关联的订单只能是唯一的，处在未校验状态
	 *
	 * @author yd
	 * @created 2016年4月26日 下午2:34:49
	 *
	 * @param orderRelationRequest
	 * @return map map 中 放查询状态  如有实体 则包含实体
	 */
	public Map<String, Object> checkOrderIsRelation(OrderRelationRequest orderRelationRequest){
		
		
		Map<String, Object> resultMap = new HashMap<String, Object>(); 
		resultMap.put("flag", true);
		//无旧订单号，直接返回true 相当于 房东已经 申请了  此时校验无法做关联操作
		if(Check.NuNObj(orderRelationRequest)&&Check.NuNStr(orderRelationRequest.getOldOrderSn())){
			resultMap.put("flag", false);
			resultMap.put("message","查询参数错误，旧订单号没有");
			LogUtil.info(logger,"查询参数错误，旧订单号没有");
			return resultMap;
		}
		
		orderRelationRequest.setCheckedStatus(CheckedStatusEnum.INIT_CHECKED.getCode());
		List<OrderRelationEntity> lsitEntities = this.queryByOldOrderSn(orderRelationRequest);
		
		if(Check.NuNCollection(lsitEntities)||(!Check.NuNCollection(lsitEntities)&&lsitEntities.size() == 1)){
			resultMap.put("orderRelationEntity", null);
			if(!Check.NuNCollection(lsitEntities)){
				resultMap.put("orderRelationEntity", lsitEntities.get(0));
			}
		}else{
			resultMap.put("flag", false);
			resultMap.put("message","此旧订单已经被多此强制取消，是问题订单，一个订单在回复之前只能被强制取消一次");
		}
		
		return resultMap;
			
	}
}
