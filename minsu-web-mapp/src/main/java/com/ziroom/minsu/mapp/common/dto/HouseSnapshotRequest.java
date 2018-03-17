package com.ziroom.minsu.mapp.common.dto;

import java.util.ArrayList;
import java.util.List;

import com.ziroom.minsu.valenum.order.OrderEvaStatusEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;

/**
 * <p>查询房源快照 api 请求参数
 *   主要作用：查询房客端的评价列表的查询
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
public class HouseSnapshotRequest extends BaseParamDto{

	
	/**
	 * 评价类型  1=待评价  2=已评价
	 */
	private  Integer evaType;

	public Integer getEvaType() {
		return evaType;
	}

	public void setEvaType(Integer evaType) {
		this.evaType = evaType;
	}
	
	/**
	 * 
	 * 订单状态为  正常退房完成 或 提前退房完成
	 *
	 * @author yd
	 * @created 2016年5月3日 上午9:14:18
	 *
	 * @return
	 */
	public List<Integer> returnOrderStatus(){
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(OrderStatusEnum.FINISH_COMMON.getOrderStatus());
		list.add(OrderStatusEnum.FINISH_PRE.getOrderStatus());
		return list;
	}
	
	/**
	 * 
	 * 获取评价状态
	 *
	 * @author yd
	 * @created 2016年5月3日 上午9:21:07
	 *
	 * @return
	 */
	public List<Integer> returnEvastatus(){
		List<Integer> list = new ArrayList<Integer>();
		//待评价(100 待评价  101 用户已评价  )
		if(this.evaType.intValue() == 1){
			list.add(OrderEvaStatusEnum.UESR_HVA_EVA.getCode());
			list.add(OrderEvaStatusEnum.WAITINT_EVA.getCode());
			
		}
		//已评价( 110 房东已评价  111 都已经评价)
		if(this.evaType.intValue() == 2){
			list.add(OrderEvaStatusEnum.LANLORD_EVA.getCode());
			list.add(OrderEvaStatusEnum.ALL_EVA.getCode());
			
		}
			
		return list;
		
	}
	
}
