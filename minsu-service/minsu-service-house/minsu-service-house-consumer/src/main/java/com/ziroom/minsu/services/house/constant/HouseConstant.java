package com.ziroom.minsu.services.house.constant;

import com.ziroom.minsu.valenum.house.RentWayEnum;


/**
 * <p>房源常量</p>
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
public class HouseConstant{
	
	/**
	 * 私有构造方法
	 */
	private HouseConstant(){
		
	}
	
	//房源出租类型-整租
	public static final int HOUSE_RENTWAY_HOUSE = RentWayEnum.HOUSE.getCode();
	
	//房源出租类型-独立房间
	public static final int HOUSE_RENTWAY_ROOM = RentWayEnum.ROOM.getCode(); 
	
	//房源出租类型-独立床位
	public static final int HOUSE_RENTWAY_BED = RentWayEnum.BED.getCode(); 
	
	@Deprecated
	//一个类型下允许传的照片最大个数
	public static final int HOUSE_PIC_TYPE_LIMIT=8;//新需求根据照片类型进行限制 卧室10张，客厅12张，厨房、卫生间、室外6张  @HousePicTypeEnum
	
	// 房源日志操作类型-房东
	public static final int HOUSE_LOG_OPERATE_TYPE_LANDLORD = 0; 
	
	// 房源日志操作类型-业务人员
	public static final int HOUSE_LOG_OPERATE_TYPE_BUSINESS = 1;
	
	// 是否删除(0:否,1:是)
	public static final int IS_NOT_DEL = 0; 
	
	// 是否删除(0:否,1:是)
	public static final int IS_DEL = 1; 
	
	// 是与否(0:否,1:是)
	public static final int IS_NOT_TRUE = 0; 
	
	// 是与否(0:否,1:是)
	public static final int IS_TRUE = 1; 
	
	// 房源月收益类型-全部整租
	public static final int HOUSE_REVENUE_TYPE_HOUSE = 0; 
	
	// 房源月收益类型-全部合租
	public static final int HOUSE_REVENUE_TYPE_ROOM = 1; 
	
	// 房源月收益类型-既有整租也有合租
	public static final int HOUSE_REVENUE_TYPE_MIX = 2; 
	
	/** 房源信息完整率 **/
	// 房源必选项数量
	public static final int HOUSE_REQUIRED_ITEMS = 17; 
	
	// 房源可选项数量
	public static final int HOUSE_OPTIONAL_ITEMS = 16; 
	
	// 房间必选项数量
	public static final int ROOM_REQUIRED_ITEMS = 18; 
	
	// 房间可选项数量
	public static final int ROOM_OPTIONAL_ITEMS = 16; 
	/** 房源信息完整率 **/

	/** 房源默认配置信息 **/
	/**
	 * 房源默认下单类型-申请预订
	 */
	public static final int DEFAULT_ORDER_TYPE = 2; 
	/**
	 * 房源默认民宿类型-通用
	 */
	public static final int DEFAULT_HOMESTAY_TYPE = 0; 
	
	/**
	 * 房源默认最小入住天数
	 */
	public static final int DEFAULT_MIN_DAY = 1; 
	
	/**
	 * 房源默认入住时间
	 */
	public static final String DEFAULT_CHECKIN_TIME = "14:00:00"; 
	
	/**
	 * 房源默认退房时间
	 */
	public static final String DEFAULT_CHECKOUT_TIME = "12:00:00"; 
	
	/**
	 * 房源默认3天优惠
	 */
	public static final String DEFAULT_THREE_DAYS_DISCOUNT = "0.0"; 
	
	/**
	 * 房源默认7天优惠
	 */
	public static final String DEFAULT_SEVEN_DAYS_DISCOUNT = "0.0"; 
	
	/**
	 * 房源默认30天优惠
	 */
	public static final String DEFAULT_THIRTY_DAYS_DISCOUNT = "0.0"; 
	
	/**
	 * 房源默认押金规则-按房租收取
	 */
	public static final String DEFAULT_DEPOSIT_BY_RENT = "1"; 
	
	/**
	 * 整租方式标识
	 */
	public static final String HOUSE_RENTWAY_ID="Z";
	
	/**
	 * 分租方式标识
	 */
	public static final String ROOM_RENTWAY_ID="F";
	
	/**
	 * 床铺默认bedSn
	 */
	public static final int DEFAULT_BEDSN = 101;
	
	/**
	 * 默认摄影师名称
	 */
	public static final String HOUSE_CAMERAMAN_NAME = "房东";
	
	/**
	 * 默认摄影师手机号
	 */
	public static final String HOUSE_CAMERAMAN_MOBILE = "13333333333";
	
	/**
	 * 灵活定价&折扣设置开关(true:开 false:关)
	 */
	public static final boolean HOUSE_PRICE_SWITCH = true;
	
	/**
	 * 房源未审核通过跟进开始时间
	 */
	public static final String HOUSE_FOLLOW_START_TIME="2017-03-06 00:00:00";
	
	/**
	 * 默认客服多少小时后开始跟进int
	 */
	public static final int SERVICE_FOLLOW_WAIT_INT=12;
	
	/**
	 * 默认专员多少小时后开始跟进int
	 */
	public static final int ATTACHE_FOLLOW_WAIT_INT=24;
	
	/**
	 * 房源锁业务等待时间
	 */
	public static final int HOUSE_LOCK_WAIT_MINUTE=20;
}
