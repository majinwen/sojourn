package com.ziroom.minsu.services.common.constant;


/**
 * 
 * <p>极光推送常量</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年5月28日
 * @since 1.0
 * @version 1.0
 */
public class JpushConst {

    /**
     * 私有化其构造
     */
    protected JpushConst() {
    }

    /**
	 * 民宿自定义消息通知key
	 */
	public static final String MSG_BODY_TYPE_KEY = "msg_body_type";
	
	/**
	 * 民宿自定义消息通知value
	 */
	public static final String MSG_BODY_TYPE_VALUE = "minsu_notify";
	/**
	 * 极光推送时间戳
	 */
	public static final String MSG_PUSH_TIME = "push_time";

	/**
	 * 推送类型 1=房客 2=房东 3=全部
	 */
	public static final String MSG_TAG_TYPE = "msg_tag_type";

	/**
	 * 消息是否可点击  0=不可点击  1=可点击
	 */
	public static final String MSG_HAS_RESPONSE = "msg_has_response";
	/**
	 * 民宿业务key
	 */
	public static final String  MSG_SUB_TYPE_KEY = "msg_sub_type";

	/**
	 * 民宿业务：跳转页面url
	 */
	public static final String MSG_SUB_TYPE_VALULE_1 = "1";
	
	/**
	 * 民宿业务：orderSn 订单详情
	 */
	public static final String MSG_SUB_TYPE_VALULE_2 = "2";
	
	/**
	 * 民宿业务：orderSn、rentWay、fid 评价
	 */
	public static final String MSG_SUB_TYPE_VALULE_3 = "3";

	/**
	 * 民宿业务：不需要参数
	 */
	public static final String MSG_SUB_TYPE_VALULE_4 = "4";

	/**
	 * 自如寓业务：wiki：http://wiki.ziroom.com/pages/viewpage.action?pageId=275382353
	 */
	public static final String MSG_SUB_TYPE_VALULE_5 = "5";

	/**
	 * 原生房源列表页
	 */
	public static final String MSG_SUB_TYPE_VALULE_6 = "6";

	/**
	 * 民宿业务：评价详情 orderSn userType (1=房东 2=房客)
	 */
	public static final String MSG_SUB_TYPE_VALUE_7 = "7";

	/**
	 * 原生优惠券列表页
	 */
	public static final String MSG_SUB_TYPE_VALULE_8 = "8";

	/**
	 * 原生房源修改页
	 */
	public static final String MSG_SUB_TYPE_VALULE_9 = "9";

	/**
	 * 推送给房客
	 */
	public static final String MSG_TARGET_TENANT = "1";
	/**
	 * 推送给房东
	 */
	public static final String MSG_TARGET_LAN = "2";
	/**
	 * 推送给全部人
	 */
	public static final String MSG_TARGET_ALL = "3";

	/**
	 * 推送title
	 */
	public static final String MSG_TITLE = "title";
	
	/**
	 * 推送title值
	 */
	public static final String MSG_TITLE_VALUE_1 = "您有新的待房客支付订单";
	
	/**
	 * 推送title值
	 */
	public static final String MSG_TITLE_VALUE_2 = "您有新的订单待房东确认";
	
    /**
     * 房东的我的房源 的url
     */
    public static final String HOUSE_DETAIL_URL = "houseMgt/43e881/myHouses";
	/**
	 * 房间或者房源详情
	 */
	public static final String HOUSE_ROOM_DETAIL_URL = "houseInput/43e881/goToHouseUpdate?requestType=2&houseBaseFid=%s&houseRoomFid=%s&rentWay=%s";

    /**
     * 房东的订单详情的url
     */
    public static final String ORDER_DETAIL_URL = "orderland/43e881/showDetail?requestType=2&orderSn=%s&landlordUid=%s";
    
    /**
     * 房客的订单详情的url
     */
    public static final String USER_ORDER_DETAIL_URL = "orderTen/43e881/showDetail?requestType=2&orderSn=%s&landlordUid=%s";


    //---------------------------------------华丽的分割线------------------------------------
    //公寓极光推送参数
    public static final String ZRP_MSG_BODY_TYPE_VALUE = "apartment_notify";



}
