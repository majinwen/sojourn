
package com.ziroom.minsu.services.order.api.inner;


/**
 * <p>房东点击强制取消业务处理
 *  业务说明： 发送场景 —— 订单已支付  且在 '待入住 '和 '已入住'状态下，房东才可操作申请强制取消
 *  1.对于房东：点击强制取消——》校验订单状态成功————》修改订单状态为强制取消申请中，并且记下订单日志，以及保存参数表此时订单的修改前的状态（方便后期客服恢复此订单，强制取消可以
 *  多次，但记录最后一次的修改前状态即可恢复，因为恢复永远是恢复上一次操作修改状态的状态值）
 *  2.对于客服：两种操作，1.恢复订单（即：订单恢复到申请强制取消订单中前一刻的状态，即参数表记录时刻的状态） 2.同意房东的强制取消申请
 *     针对第二种情况分析：
 *     房东：
 *       A.房东这边不发生扣钱情况
 *       B.房东这边发生扣钱情况
 *     房客：
 *       A.房客决定退钱，走结算
 *       B.房客同意换房，走结算
 *       
 * </p>
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
public interface CommissionerOrderService {

	/**
	 * 
	 * 房东强制点击强制取消
	 *
	 * @author yd
	 * @created 2016年4月23日 下午5:27:21
	 *
	 * @param orderSn
	 * @return
	 */
	public String compulsoryCancellOrderByLandlord(String orderSn);
	
	/**
	 * 
	 * 客服恢复强制取消的订单
	 *
	 * @author yd
	 * @created 2016年4月23日 下午6:19:21
	 *
	 * @param orderSn
     * @param createId
	 * @return
	 */
	public String recoveryCancelOrde(String orderSn,String createId);

    /**
     * 客服同意强制取消订单
     * @param orderSn
     * @param createId
     * @param cancelType
     * @return
     */
	String agreeCancelOrde(String orderSn,String createId,Integer cancelType);
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
	public String  queryOrderRelanionByPage(String orderRelationRequest);
	
	/**
	 * 
	 * 新订单支付成功后 插入新旧订单关联关系， 存在即更新
	 * 说明： 在待审批的情况下 校验如下，此时状态应该是 未关联  所以没有一下校验
	 *     1.校验新订单是否支付成功，支付失败的不让插入
	 *     2.校验旧订单是否是强制取消状态  否的话 不让插入 打款单
	 *
	 * @author yd
	 * @created 2016年4月25日 下午1:41:10
	 *
	 * @param orderRelationEntity
	 * @return
	 */
	public String saveOrderRelation(String orderRelationSaveRequest);
	
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
	public String updateOrderRelationByCondition(String orderRelationSaveRequest);
	
	/**
	 * 
	 * 获取两个订单的差额
	 *
	 * @author yd
	 * @created 2016年4月27日 下午1:23:59
	 *
	 * @param newOrderSn
	 * @param oldOrderSn
	 * @return
	 */
	public String getMoneyLast(String newOrderSn,String oldOrderSn);
	
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
	public String queryByCondition(String orderRelationRequest);
	
	/**
	 * 
	 * 查询客服取消订单信息
	 *
	 * @author yd
	 * @created 2017年1月5日 下午12:02:02
	 *
	 * @param orderRelationRequest
	 * @return
	 */
	public String findCancleOrderVo(String orderSn);
	
}
