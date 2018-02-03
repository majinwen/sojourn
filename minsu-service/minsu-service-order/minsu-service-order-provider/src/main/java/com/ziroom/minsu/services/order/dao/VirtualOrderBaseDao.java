package com.ziroom.minsu.services.order.dao;

import javax.annotation.Resource;

import com.asura.framework.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.entity.order.OrderLogEntity;
import com.ziroom.minsu.entity.order.OrderMoneyEntity;
import com.ziroom.minsu.entity.order.OrderParamEntity;
import com.ziroom.minsu.valenum.order.OrderParamEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;

/**
 * <p>订单相关操作</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年5月3日
 * @since 1.0
 * @version 1.0
 */
@Repository("order.virtualOrderBaseDao")
public class VirtualOrderBaseDao {

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(VirtualOrderBaseDao.class);
	
	@Resource(name = "order.orderBaseDao")
	private OrderBaseDao orderBaseDao;
	
	@Resource(name = "order.orderMoneyDao")
	private OrderMoneyDao orderMoneyDao;
	
	@Resource(name = "order.orderLogDao")
	private OrderLogDao orderLogDao;
	
	@Resource(name = "order.paramDao")
	private OrderParamDao orderParamDao;
	
	
	/**
	 * 更改订单状态等信息
	 * @author afi
	 * @param oldOrderStatus 订单的原始状态
	 * @param orderEntity 订单的主信息
	 * @param orderMoney 订单的金额信息
	 * @param remark 备注
	 * @see com.ziroom.minsu.valenum.order.OrderParamEnum 备注类型code
	 * @param remarkType
	 * @param opUuid
	 */
	public int updateOrderInfoAndStatus(String opUuid,int oldOrderStatus,OrderEntity orderEntity,OrderMoneyEntity orderMoney,String remark,String remarkType){
		int rst = 0;
		if(Check.NuNObj(orderEntity)){
			return rst;
		}
		if(Check.NuNStr(orderEntity.getOrderSn())){
			throw  new BusinessException("orderSn is null on updateOrderInfo");
		}
		if(OrderStatusEnum.getOrderStatusByCode(oldOrderStatus) == null){
            LogUtil.error(LOGGER, "更改订单状态等信息参数  {} ,{} ,{},{}", oldOrderStatus, JsonEntityTransform.Object2Json(orderEntity), remark, remarkType);
			throw new BusinessException("oldOrderStatus is error on updateOrderInfo");
		}else {
			orderEntity.setOldStatus(oldOrderStatus);
		}
		//直接保存订单信息
		rst = orderBaseDao.updateOrderBaseByOrderSn(orderEntity);
		if(rst != 1){
			LogUtil.error(LOGGER, "更改订单状态等信息失败，参数  {} ,{} ,{},{}", oldOrderStatus, JsonEntityTransform.Object2Json(orderEntity), remark, remarkType);
			throw new BusinessException("更新0条记录");
		}
		//保存订单的金额
		if(!Check.NuNObj(orderMoney)){
			//校验是否有金额的变动
			orderMoneyDao.updateOrderMoney(orderMoney);
		}
		//记录订单的修改记录，并保存参数信息
		this.saveOrderLog(opUuid, oldOrderStatus, orderEntity, remark, remarkType, opUuid);

        //获取参数类型
        OrderParamEnum orderParamEnum = OrderParamEnum.geOrderParamByCode(remarkType);
        if(orderParamEnum != null && orderParamEnum !=OrderParamEnum.LOG ){
            saveOrderpar(orderEntity.getOrderSn(),orderParamEnum.getCode(),remark,orderEntity.getUserUid());
        }
		return rst;
	}
	
	
	/**
	 * 保存订单的修改记录
	 * @author afi
	 * @param oldOrderStatus
	 * @param orderEntity
	 * @param remark
	 * @param remarkType
	 * @see com.ziroom.minsu.valenum.order.OrderParamEnum
	 */
	private void saveOrderLog(String opuuid,int oldOrderStatus,OrderEntity orderEntity,String remark,String remarkType,String parValue){

		if(Check.NuNObj(orderEntity)){
			return;
		}
		if(Check.NuNStr(orderEntity.getOrderSn())){
			throw  new BusinessException("orderSn is null on saveOrderLog");
		}
		if(OrderStatusEnum.getOrderStatusByCode(oldOrderStatus) == null){
            LogUtil.error(LOGGER,"request par is {}",orderEntity.getOrderSn());
			throw  new BusinessException("oldOrderStatus is error on saveOrderLog");
		}
		if(oldOrderStatus == orderEntity.getOrderStatus()){
			LogUtil.info(LOGGER, "新老状态一样，不记录更新日志", oldOrderStatus);
			return;
		}
		if(Check.NuNStr(parValue)){
			parValue = remark;
		}
		OrderLogEntity log = new OrderLogEntity();
		log.setOrderSn(orderEntity.getOrderSn());
		log.setToStatus(orderEntity.getOrderStatus() == null ? oldOrderStatus : orderEntity.getOrderStatus());
		log.setFromStatus(oldOrderStatus);
		log.setRemark(remark);
		log.setCreateId(opuuid);
		orderLogDao.insertOrderLog(log);
	}
	
	
	/**
	 * 保存订单的参数信息
     * 修改或者增加
	 * @author afi
	 * @param orderSn
	 * @param parCode
	 * @param parValue
	 * @param userFid
	 */
	private void saveOrderpar(String orderSn,String parCode,String parValue,String userFid){
        OrderParamEntity par= orderParamDao.findParamByCode(orderSn,parCode);
        if(Check.NuNObj(par)){
            OrderParamEntity paramEntity = new OrderParamEntity();
            paramEntity.setOrderSn(orderSn);
            paramEntity.setParCode(parCode);
            paramEntity.setParValue(parValue);
            paramEntity.setCreateFid(userFid);
            orderParamDao.insertParamRes(paramEntity);
        }else {
            orderParamDao.updateParamByFid(par.getFid(),parValue);
        }


	}
	
	
	/**
	 * 更改订单基础信息和状态等信息 此处 日志备注和 参数配置的value值相同
	 * @author afi
	 * @param oldOrderStatus 订单的原始状态
	 * @param orderEntity 订单的主信息
	 * @param remark 备注
	 * @see com.ziroom.minsu.valenum.order.OrderParamEnum 备注类型code
	 * @param remarkType 参数配置的code
	 *
	 */
	public int updateOrderBaseAndStatus(String opUuid,int oldOrderStatus,OrderEntity orderEntity,String remark,String remarkType){

		return updateOrderBaseAndStatus(opUuid,oldOrderStatus, orderEntity, remark, remarkType,null);
	}
	
	/**
	 * 更改订单基础信息和状态等信息（ 此处 日志备注和 参数配置的value值不相同 自己设定）
	 * @author afi
	 * @param oldOrderStatus 订单的原始状态
	 * @param orderEntity 订单的主信息
	 * @param remark 备注
	 * @see com.ziroom.minsu.valenum.order.OrderParamEnum 备注类型code
	 * @param remarkType  参数配置的code
	 * @param parValue 参数配置的value
	 *
	 */
	public int updateOrderBaseAndStatus(String opUuid,int oldOrderStatus,OrderEntity orderEntity,String remark,String remarkType,String parValue){
		int rst = 0;
		if(Check.NuNObj(orderEntity)){
			return rst;
		}
		if(Check.NuNStr(orderEntity.getOrderSn())){
			throw  new BusinessException("orderSn is null on updateOrderInfo");
		}
		if(OrderStatusEnum.getOrderStatusByCode(oldOrderStatus) == null){
            LogUtil.error(LOGGER,"request par is {} ,{} ,{},{}",oldOrderStatus,JsonEntityTransform.Object2Json(orderEntity),remark,remarkType);
			throw  new BusinessException("oldOrderStatus is error on updateOrderInfo");
		}else {
			orderEntity.setOldStatus(oldOrderStatus);
		}
		//直接保存订单信息
		rst = orderBaseDao.updateOrderBaseByOrderSn(orderEntity);
		//记录订单的修改记录，并保存参数信息

		this.saveOrderLog(opUuid,oldOrderStatus,orderEntity,remark,remarkType,parValue);

        //获取参数类型
        OrderParamEnum orderParamEnum = OrderParamEnum.geOrderParamByCode(remarkType);
        if(orderParamEnum != null && orderParamEnum !=OrderParamEnum.LOG ){
            saveOrderpar(orderEntity.getOrderSn(),orderParamEnum.getCode(),parValue,orderEntity.getUserUid());
        }

		return rst;
	}

}
