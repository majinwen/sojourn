package com.ziroom.minsu.services.order.constant;

import com.ziroom.minsu.services.common.constant.MessageConst;

/**
 * <p>订单常亮</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/1.
 * @version 1.0
 * @since 1.0
 */
public class OrderMessageConst extends MessageConst {


    /**
     * 当前超出数量
     *
     */
    public static final String ORDER_COUNT_MORE = "order.count.more";

    /**
     * 房源被占有
     */
    public static final String ORDER_HOUSE_OCCUPY = "order.house.occupy";

    /** 房源信息不匹配  */
    public static final String ORDER_HOUSE_INFO_ERROR = "order.house.error";

    /** 房源状态未上架  */
    public static final String ORDER_HOUSE_STATUS_ERROR = "order.house.status";

    /** 房源已过期  */
    public static final String ORDER_HOUSE_ERROR_TIME = "order.error.TIME";

    /** 房源最小天数  */
    public static final String ORDER_HOUSE_ERROR_TIME_MIN = "order.error.min";

    /** 房源最大天数  */
    public static final String ORDER_HOUSE_ERROR_TIME_MAX = "order.error.max";

    /** 房源最大天数  */
    public static final String ORDER_HOUSE_ERROR_TENANT = "order.error.tenant";

    /** 当前订单不存在  */
    public static final String ORDER_EXIT_NULL = "order.exit.no";

    /** 当前订单没有权限 */
    public static final String ORDER_AUTH_NULL = "order.auth.no";

    /** 异常的订单状态  */
    public static final String ORDER_STATUS_ERROR = "order.status.error";

    /** 当前状态不能取消  */
    public static final String ORDER_STATUS_CANCLE = "order.status.cancle";

    /** 房源已经被锁定  */
    public static final String ORDER_HOUSE_LOCK = "order.house.locked";
    
    /** 房源未锁定  */
    public static final String ORDER_HOUSE_UNLOCK = "order.house.unlocked";

    /** 操作失败  */
    public static final String ORDER_OP_ERROR = "order.op.error";

    /** 当前优惠券不存在  */
    public static final String ORDER_CONPON_NULL = "order.coupon.null";

    /** 当前优惠券未开始  */
    public static final String ORDER_CONPON_START = "order.coupon.start";

    /** 当前优惠券过期 */
    public static final String ORDER_CONPON_END = "order.coupon.end";

    /** 优惠券入住时间未开始  */
    public static final String ORDER_CONPON_CHECKIN_START = "order.coupon.check.start";

    /** 入住时间为满足优惠券的使用时间  */
    public static final String ORDER_CONPON_TIME = "act.coupon.time";

    /** 优惠券入住时间已结束 */
    public static final String ORDER_CONPON_CHECKIN_END = "order.coupon.check.end";

    /** 优惠券不满足条件 */
    public static final String ORDER_CONPON_ERROR = "order.coupon.erro";

    /** 当前版本不支持清洁费，请升级 */
    public static final String ORDER_CLEAN_VERSION = "order.clean.version";


    /** 非法额外消费 */
    public static final String ORDER_OTHER_MONRY_ERROR = "order.otherMoney.error";

    public static final String ORDER_PUNISH_ERROR = "order.punish.error";

    public static final String ACT_COUPON_NO_USER = "act.coupon.no.user";
    /** 异常的优惠券状态 */
    public static final String ACT_COUPON_STATUS_ERROR = "act.coupon.status.error";
    /** 当前优惠券状态不可用 */
    public static final String ACT_COUPON_STATUS_CANOT = "act.coupon.status.canot";
    /** 当前优惠券在当前城市不可用 */
    public static final String ACT_COUPON_CITY_CANOT = "act.coupon.city.canot";

    /** 当前优惠券在当前城市不可用 */
    public static final String ACT_COUPON_HOUSE_CANOT = "act.coupon.house.canot";

    /** 未满足当前优惠券的使用金额 */
    public static final String ACT_COUPON_MONEY_LIMIT = "act.coupon.money.limit";


    /** 未支持的优惠券类型 */
    public static final String ACT_COUPON_MONEY_TYPES = "act.coupon.money.type";

    /** 不满足使用条件请核实券的使用条件 */
    public static final String ACT_COUPON_COMMON = "act.coupon.common";

    /** 当前优惠券已使用 */
    public static final String ACT_COUPON_USED = "act.coupon.used";



    
    /*********************************智能锁*********************************/
    /** 智能锁唯一标示不能为空 */
    public static final String SMARTLOCK_FID_NULL = "smartlock.fid.null";
    
    /** 智能锁订单编号不能为空 */
    public static final String SMARTLOCK_ORDERSN_NULL = "smartlock.orderSn.null";
    
    /** 智能锁信息不能为空 */
    public static final String SMARTLOCK_NULL = "smartlock.null";
    
    /** 智能锁永久密码与动态密码不能同时为空 */
    public static final String SMARTLOCK_PERM_AND_DYNA_PSWD_NULL = "smartlock.perm.and.dyna.pswd.null";
}
