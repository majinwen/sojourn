/**
 * @FileName: OrderConst.java
 * @Package com.ziroom.minsu.api.common.constant
 * 
 * @author jixd
 * @created 2016年5月3日 上午9:11:20
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.common.constant;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class OrderConst {
	/**
	 * 已支付产生违约金
	 */
	public static final String MSG_HASPAY_HASPENALTY = "取消此次订单，您将支付违约金%.2f元，剩余金额%.2f元将退款至您的支付空间，请注意查收！";


	/**
	 * 已支付产生违约金
	 */
	public static final String MSG_HASPAY_HASPENALTY_COMM = "取消此次订单，您将支付违约金%.2f元，平台服务费%.2f元，剩余金额%.2f元将退款至您的支付空间，请注意查收！";

	/**
	 * 已支付未产生违约金
	 */
	public static final String MSG_HASPAY_NOPENALTY = "取消此次订单,将返还给您%.2f元。";
	/**
	 * 没有支付
	 */
	public static final String MSG_NOPAY = "您确定要取消订单吗？";
	
	
	/**
	 * 提前退房
	 */
	public static final String MSG_EARLY_CHECKOUT = "当前属于提前退房，您将支付违约金%.2f元，如确认提前退房，请等待房东确认是否存在其他消费";


	/**
	 * 提前退房
	 */
	public static final String MSG_EARLY_CHECKOUT_COMM = "当前属于提前退房，您将支付违约金%.2f元，平台服务费%.2f元，如确认提前退房，请等待房东确认是否存在其他消费";

    /**
     * 提前退房 有智能锁
     */
    public static final String MSG_EARLY_CHECKOUT_LOCK = "当前属于提前退房，您将支付违约金%.2f元，如确认提前退房，请等待房东确认是否存在其他消费，您入住的房源装有智能门锁，退房后，密码将失效，请知悉。";
	
	/**
	 * 正常退房
	 */
	public static final String MSG_NORMAL_CHECKOUT = "您确定要退房吗？";

	/**
	 * 申请预定页，预订人信息提示
	 */
	public static final String MSG_BOOKER_ORDER = "为了您的资金安全，请确认预订人信息无误，若涉及银行卡退款，仅支持退还至预订人的银行卡账户";

	/**
	 * 房东订单详情页标题
	 */
	public static final String LANDLORD_ORDER_DETAIL_TITLE = "%s的%d晚住宿";
    /**
     * 房东平台服务费提示信息
     */
    public static final String LANDLORD_ORDER_COMM_MONEY_TIPS = "为更好的服务用户，平台将收取房费的%s作为信息服务费，房东服务费根据订单房费、清洁费、违约金计算得出，不包括押金、其他消费费用。";
    /**
     * 长期入住优惠提示文案
     */
    public static final String LANDLORD_ORDER_COMM_MONEY_CHEEP_TIPS = "已享受长期住宿服务费优惠%s元";
    /**
     * 房东拒绝订单二次确认提示信息
     */
    public static final String LANDLORD_REFUSE_CALENDAR_CONFIRM_MSG = "拒绝订单后房客无法预订您的房源，将减少您的收益，请确认是否继续拒绝订单？";
    /**
     * 房东拒绝订单二次确认提示信息
     */
    public static final String LANDLORD_REFUSE_CONFIRM_MSG = "拒绝订单后房客无法预订您的房源，将减少您的收益，请确认是否继续拒绝订单？";
    /**
     * 房东就接受订单二次确认提示信息
     */
    public static final String LANDLORD_ACCEPT_CONFIRM_MSG = "您将接受%s的%s至%s的%d晚预订申请，请确认是否继续？";
    /**
     * 房东确认额外消费 有金额
     */
    public static final String LANDLORD_OTHER_HAS_MONEY_CONFIRM_MSG = "您将对房客发起Money元的其他消费付款要求，请确认是否继续？";
    /**
     * 房东确认额外消费 没有金额
     */
    public static final String LANDLORD_OTHER_NO_MONEY_CONFIRM_MSG = "您没有填写其他消费金额，确认订单后将无法对房客发起其他消费付款要求，请确认是否继续？";

	/**
	 * 房东房源详情 字段名称
	 */
	public static final String FIELD_CHECK_IN_PEOPLE = "入住人信息";

	public static final String FIELD_CHECK_IN_TIME = "入住时间";

	public static final String FIELD_CHECK_OUT_TIME = "退房时间";

	public static final String FIELD_INCOME_DESC = "包含房费，清洁费等";

	public static final String FIELD_PENALTY_MONEY = "本单罚款";

	public static final String FIELD_OTHER_MONEY = "其他消费";

	public static final String FIELD_OTHER_MONEY_VALUE = "其他消费金额";

    public static final String FIELD_OTHER_MONEY_VALUE_DESC = "请填写用户其他消费金额、赔偿金额等";

	public static final String FIELD_OTHER_MONEY_DETAIL = "其他消费明细";

	public static final String FIELD_OTHER_MONEY_DETAIL_DESC = "请填写消费明细或赔偿原因";

    public static final String FIELD_OTHER_MONEY_DESC = "房客退房后%s小时确认";

    public static final String FIELD_OTHER_MONEY_DESC_1 = "未填写其他消费";

	public static final String FIELD_CHECK_OUT_RULE = "退订政策";

	public static final String FIELD_TRIP_PURPOSE = "Ta的出行目地";

	public static final String FIELD_PENALTY_NAME = "罚款单号";

	public static final String FIELD_REMARK = "备注";

    public static final String FIELD_REMARK_DESC = "请填写您的订单备注信息，例如，是否需要接机、是否需要准备其他物品等，50字以内。";
    //拒绝原因
    public static final String FIELD_REFUSE_REASON_DESC = "请输入其他原因，字数100字以内。";




}
