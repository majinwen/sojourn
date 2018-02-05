package com.ziroom.minsu.api.common.constant;

import com.ziroom.minsu.services.common.constant.MessageConst;

/**
 * <p>接口服务静态配置常量</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class ApiMessageConst extends MessageConst{
	
	/**
	 * 提示信息:用户uid不能为空
	 */
	public static final String UID_NULL="uid.null";
	
	/**
	 * 提示信息:房东uid不能为空
	 */
	public static final String LANDLORDUID_NULL="landlordUid.null";
	
	/**
	 * 提示信息:房源唯一标示不能为空
	 */
	public static final String HOUSE_BASE_FID_NULL="house.base.fid.null";
	
	/**
	 * 提示信息:房间唯一标示不能为空
	 */
	public static final String HOUSE_ROOM_FID_NULL="house.room.fid.null";

	/**
	 * 提示信息:房源出租方式参数错误
	 */
	public static final String HOUSE_RENTWAY_ERROR ="house.rentway.error";
	
	/**
	 * 提示信息:房源出租方式不能为空
	 */
	public static final String HOUSE_RENTWAY_NULL ="house.rentway.null";
	
	/**
	 * 评价类容最大长度
	 */
	public static final int EVA_CONTENT_MAX_LENGTH=400;
	
	/**
	 * 提示信息:房源唯一标示和房间唯一标示集合不能同时为空
	 */
	public static final String HOUSEFID_AND_ROOMFID_LIST_NULL="houseFid.and.roomFid.list.null";

	/**
	 *智能锁 订单支付状态错误
	 */
	public static final String SMART_ORDER_PAYSTATUS ="smart.order.paystatus";
	
	/**
	 *智能锁 订单不存在
	 */
	public static final String SMART_ORDER_NULL ="smart.order.null";
	
	/**
	 * 智能锁 订单状态错误
	 */
	public static final String SMART_ORDER_ORDERSTATUS ="smart.order.orderstatus";
	
	/**
	 * 智能锁 订单智能锁时间过了订单结束时间
	 */
	public static final String SMART_ORDER_ENDTIME ="smart.order.endtime";
	
	/**
	 * 智能锁 用户无权限
	 */
	public static final String SMART_USER_NOAUTH ="smart.user.noauth";
	
	/**
	 * 智能锁 接口返回错误
	 */
	public static final String SMART_RETURN_ERROR ="smart.return.error";
	
	/**
	 * 智能锁 门锁已离线
	 */
	public static final String SMART_LOCK_OFF ="smart.lock.off";
	
	/**
	 * 智能锁 密码过期
	 */
	public static final String SMART_PWD_EXPIRED ="smart.pwd.expired";
	
	/**
	 * 智能锁 订单号不能为空
	 */
	public static final String SMARTLOCK_ORDERSN_NULL ="smartlock.orderSn.null";
	
	/**
	 * 智能锁 订单已结束
	 */
	public static final String SMARTLOCK_ORDER_CLOSED ="smartlock.order.closed";
	
	/**
	 * 智能锁 动态密码获取次数达到上限
	 */
	public static final String SMARTLOCK_DYNA_PSWD_NUM_OVER_LIMIT ="smartlock.dyna.pswd.num.over.limit";

	/**
	 * 美居介绍
	 */
	public static final String HOUSE_DETAIL_MERCURE_TITLE ="美居介绍";
	/**
	 * 描述
	 */
	public static final String HOUSE_DETAIL_MERCURE_DES_TITLE="描述";
	/**
	 * 周边
	 */
	public static final String HOUSE_DETAIL_AROUND_TITLE="周边";

	/**
	 * 满减
	 */
	public static final String ACT_COUPON_LITTLE_MSG= "满%d减%d";
	/**
	 * 描述信息
	 */
	public static final String ACT_COUPON_DETAIL_MSG= "满%d元可用，有效期%d天";
	/**
	 * 标题
	 */
	public static final String ACT_BIG_TITLE = "自如民宿优惠券";
	/**
	 * 副标题
	 */
	public static final String ACT_SUB_TITLE = "可与[首次下单立减50元]叠加使用";
	/**
	 * 领取成功后提示文案
	 */
	public static final String ACT_SUCCESS_MSG = "领取成功";
	
	/**
	 * 字段审核中提示
	 */
	public static final String FIELD_AUDIT_MSG="审核中";

	/**
	 * 房源详情页首单立减
	 */
	public static final String ACT_FIRST_ORDER_REDUCE= "民宿新人专享，首次下单立减50元";
	
	/**
	 * 私有构造方法
	 */
	private ApiMessageConst(){
		
	}

}
