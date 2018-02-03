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
 * 房东首页-当月即将到来的订单: 20=待入住  40.已入住未生成单据  41.已入住已生成单据 
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修
 * 改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public enum LandlordOrderTypeEnum {

	PENDING_NEW(1,"待处理"),
	UNDERWAY(2,"进行中"),
	HAS_ENDED(3,"已结束"),
	COMING_TEN_ORDERS(11,"房东首页-即将到来的订单（未来订单，取前10个）"),
	COMING_All_ORDERS(12,"房东首页-所有订单（日历就开放6个月，所以最多也就是6个月的订单）");

	LandlordOrderTypeEnum(int code,String chineseName){
		this.chineseName = chineseName;
		this.code = code;
	}

	/**
	 * 订单状态值得map
	 */
	public final static Map<String, List<Integer>> LandlordOrderTypeMap = new HashMap<String, List<Integer>>();
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
	public static LandlordOrderTypeEnum getLandlordOrderTypeEnumByCode(Integer code){
		if (Check.NuNObj(code)){
			return null;
		}
		for (LandlordOrderTypeEnum landlordOrderTypeEnum : LandlordOrderTypeEnum.values()) {

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
		
		LandlordOrderTypeMap.put("1", pendingNew);
		LandlordOrderTypeMap.put("2", underWay);
		LandlordOrderTypeMap.put("3", hasEnded);
		

	}
}
