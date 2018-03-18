package com.zra.common.constant;

/**
 * <p>合同相关消息常量</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月14日 16:36
 * @since 1.0
 */
public class ContractMsgConstant {

    //提示信息
    public static final String DELIVERY_TIME_MSG = "请于%s前确认物业交割";
    //¥3000/件
    public static final String DELIVERY_ITEM_MONEY_MSG = "¥%s/件";
    //表底示数 2.00
    public static final String DELIVERY_LIFEFEE_NUMBER_MSG = "表底示数 %s";
    //单价 5.00元/吨
    public static final String DELIVERY_LIFEFEE_UNIT_MSG = "单价 %s/%s";
    //联系管家
    public static final String CONTACT_ZO = "联系管家";
    //查看明细
    public static final String LOOK_DETAIL = "查看明细";
    //账单详情页提示信息
    public static final String RENT_DETAIL_PROMPT_INFO = "包括房租，生活费用";
    //账单详情页提示信息
    public static final String RENT_DETAIL_CARE_INFO = "您授权我们以您的本人信息（包括但不局限于姓名和身份证号）对正在签署的合同进行数字签名";
    
    public static final String RENT_DZF_CARE_INFO = "您有%s个待支付账单";
    
    public static final String SIGN_CONTRACT_TIME_MSG = "请于 %s 23:59:59 前签约";
    
    public static final String SIGN_CONTRACT_TIME_INFO = "请于{1}前签约";
    
    public static final String SIGN_CONTRACT_TIME = " 23:59:59";
    //合同详情页页面顶部提示信息
    public static final String RENT_HAS_CLOSED_CARE_INFO = "合同已关闭，无法继续签约";
    //查看合同
    public static final String LOOK_PRE_CONTRACT = "查看合同";
    //验证合同状态不为已签约的提示信息
    public static final String CONTRACT_STATUS_CHANGE = "您的合同已超时关闭，如果继续签约，请联系管家";
    
    public static final String CONTRACT_STATUS_ERROR_MSG = "您的合同状态异常，无法继续提交合同，请联系管家";
    
    public static final String CONTRACT_TIMEOUT = "您已超过签约有效期，如果继续签约，请联系管家";
    
    public static final String CONTRACT_TIME_BEFORE = "请于签约有效期当天进行签约，如需帮助请联系管家";
    
    public static final String RENEW_CONTRACT_TIMEOUT = "您已超过签约有效期，如果继续签约，请联系管家";
    
    public static final String RENEW_PAY_REMIND = "您的合同存在待支付账单，请完成账单支付后继续续约";
    
    public static final String CONTRACT_ROOM_STATUS_CHANGE = "您的待签约房间状态已变更为不可签约状态，如果继续签约，请联系管家";
    //签约有效期
    public static final String SIGN_TIME_EFFECTIVE = "签约有效期：{1}";
    
    public static final String WARN_SIGN_CONTRACT = "请在%s前完成签约，否则签约信息将失效";
    
    public static final String PAY_TIME_MSG = "请在{1}前完成支付";
    
    public static final String PAY_TIME_WARN_MSG = "如果未能及时完成首期账单支付，合同将自动关闭，已支付的金额将退回至您的账户空间";
    
    public static final String DELIVERY_TIME_MSG_CARD = "请在{1}前完成物业交割";
    
    public static final String DELIVERY_DEFAULT_COMPLETE_CARD = "如果未能及时完成物业交割，系统将默认完成物业交割";
    
    public static final String DELIVERY_NEED_PAY_MSG_CARD = "请及时支付生活费用账单，并完成物业交割";
    
    public static final String PAY_LIFE_COST_FIRST_MSG = "请在%s前完成支付，未支付生活费用前无法支付房租";
    
    public static final String PAY_LIFE_COST_CAN_RENEW = "请及时支付生活费用，支付完成后即可联系管家进行续约了";
    
    public static final String OVER_TIME_WARN_MSG = "请在%s前完成支付，否则将产生逾期违约金";
    
    public static final String CONTRACT_OVERTIME_DATE_MSG = "距离合同到期还有%s天";
    
    public static final String CONTRACT_TODAY_OVERTIME_MSG = "合同已到期，今天是您的续约截止日期";
    
    public static final String ADVANCE_RENEW_DISCOUNT_MSG = "您现在可联系管家续约了，越早续约优惠越大哦";
    
    public static final String ADVANCE_CAN_RENEW_MSG = "管家已发续约通知，您可以开始续约了";
    //在后台续约的提示信息
    public static final String ADVANCE_HAVE_RENEW_INBACK_MSG = "您已通过管家进行续约，如需帮助请联系管家";
    
    //联系管家录入物业交割单
    public static final String DELIVERY_CUSTOMER_TIP_CONTACT="管家未录入物业交割单，请尽快联系管家";

    public static final String DELIVERY_CUSTOMER_PUSH_TITLE="您有待物业交割的自如寓合同";

    public static final String DELIVERY_CUSTOMER_DONE_PUSH_TITLE = "自如寓物业交割已自动确认";
    
    public static final String SIGN_PERSON_AGE_LIMIT = "您已超过40岁，无法签约自如寓";
    
    public static final String FLOOR_MSG = "层";
    
    public static final String ROOM_NUM_MSG = "号房间";
    
    public static final String FROM_TO_MSG = "至";
    
    public static final String DIRECTION_MSG = "向";
    
    public static final String VERTICAL_MSG = " | ";

    public static final String CONTRACT_STOP_PAY_MSG = "系统维护中，暂时无法支付";
    
    
    
}
