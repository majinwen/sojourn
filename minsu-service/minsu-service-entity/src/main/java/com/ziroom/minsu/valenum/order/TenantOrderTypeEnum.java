package com.ziroom.minsu.valenum.order;

import com.asura.framework.base.util.Check;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>房东订单类型：1.待处理  2.进行中  3.已结束</p>
 * 待处理：10=待确认  50=正常退房中 51 = 提前退房中
 * 进行中： 20=待入住   40.已入住未生成单据  41.已入住已生成单据   60=待用户确认额外消费  
 * 已结束： 30=强制取消  31=房东已拒绝  32=房客取消  33=未支付超时取消 71=提前退房完成  72=正常退房完成
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
public enum TenantOrderTypeEnum {

	LING(1,"进行中"),
	ENDED(2,"已完成"),
    LOCKED(3,"lock"),
	APPLY(4,"预约中"),
	WAIT_PAY(5,"待支付"),
	WAIT_CHECK_IN(6,"待入住"),
	WAIT_EVA(7,"待评价"),
	WAIT_CHECK_OUT(8,"待入住+已入住"), //for自如app，我的旅行
	ACTIVE(9,"待入住+已入住+已经退房且未评价"); //房客端 待入住+已入住+已经退房且未评价 订单(for自如app，我的旅行)
	TenantOrderTypeEnum(int code,String chineseName){
		this.chineseName = chineseName;
		this.code = code;
	}

	/**
	 * 订单状态值得map
	 */
	public final static Map<String, List<Integer>> TenantOrderTypeMap = new HashMap<String, List<Integer>>();
	
	
	/**
	 * 枚举编码
	 */
	private int code;

	/**
	 * 中文名称
	 */
	private String chineseName;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	/**
	 * 
	 * get LandlordOrderTypeEnum by code
	 *
	 * @author yd
	 * @created 2016年4月21日 上午11:39:41
	 *
	 * @param code
	 * @return
	 */
	public static TenantOrderTypeEnum getTenantOrderTypeEnumByCode(Integer code){
        if (Check.NuNObj(code)){
            return null;
        }
		for (TenantOrderTypeEnum landlordOrderTypeEnum : TenantOrderTypeEnum.values()) {

			if(landlordOrderTypeEnum.getCode() == code){
				return landlordOrderTypeEnum;
			}
		}
		return null;
	}

	//初始化房东订单状态的map
	static{

		List<Integer> pendingNew = new ArrayList<Integer>();//待处理
		List<Integer> underWay = new ArrayList<Integer>();//待处理
		List<Integer> hasEnded = new ArrayList<Integer>();//待处理

		pendingNew.add(OrderStatusEnum.WAITING_CONFIRM.getOrderStatus());
		pendingNew.add(OrderStatusEnum.CHECKING_OUT.getOrderStatus());
		
		underWay.add(OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus());
		underWay.add(OrderStatusEnum.CHECKED_IN.getOrderStatus());
		underWay.add(OrderStatusEnum.CHECKED_IN_BILL.getOrderStatus());
		underWay.add(OrderStatusEnum.WAITING_EXT.getOrderStatus());
		

		hasEnded.add(OrderStatusEnum.CANCLE_FORCE.getOrderStatus());
		hasEnded.add(OrderStatusEnum.REFUSED.getOrderStatus());
		hasEnded.add(OrderStatusEnum.CANCLE_TENANT.getOrderStatus());
		hasEnded.add(OrderStatusEnum.CANCLE_TIME.getOrderStatus());
		hasEnded.add(OrderStatusEnum.CHECKING_OUT_PRE.getOrderStatus());
		hasEnded.add(OrderStatusEnum.FINISH_COMMON.getOrderStatus());
		hasEnded.add(OrderStatusEnum.FINISH_PRE.getOrderStatus());
		
		TenantOrderTypeMap.put("1", pendingNew);
		TenantOrderTypeMap.put("2", underWay);
		TenantOrderTypeMap.put("3", hasEnded);
		

	}
}
