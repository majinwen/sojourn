package com.ziroom.minsu.valenum.msg;


/**
 * 消息模板code枚举，此code对应消息模板 sms_code
 * @author jixd on 2016年4月18日
 * @version 1.0
 * @since 1.0
 */
public enum MessageTemplateCodeEnum {
	EMPTY(99,"没有任何内容的消息"),
	CUSTOM(100,"自定义消息"),
	PLACE_ORDER(101,"下单提示"),
	MOBILE_CODE(102,"获取短信验证码"),
	REFUND_CODE(103,"您的退款已到账"), //用户
	
	TENANT_EVALUATE(104,"房客评价"),
	CHECKIN_NOTICE_TENANT_EVAL(1041,"房客入住当天通知房客评价"),
	ORDER_FINISH_2DAY_NOTICE_TENANT_EVAL(1042,"订单完成两天通知房客评价"),
	LANLORD_EVALUATE(105,"房东评价"),
	ORDER_FINISH_2DAY_NOTICE_LANLORD_EVAL(1052,"订单完成两天通知房东评价"),
	
	WAIT_COMFIRM(106,"您有待审核订单即将失效"),
	
	WARN_COUPON(107,"报警"),
	
	ORDER_USER_TO_PAY(301,"待支付订单"),
	ORDER_USER_HAS_PAY(302,"订单支付成功-客户"),
	SHARE_HALLL_ORDER_USER_HAS_PAY(342,"共享客厅-订单支付成功-客户"),
	
	ORDER_LANLORD_CANCEL(303,"订单取消通知"),
	ORDER_LANLORD_CHECK(304,"订单审核通知"),
	//ORDER_LANLORD_HAS_PAY(305,"订单支付成功-业主"),
	ORDER_LANLORD_HAS_PAY_V2(100305,"订单支付成功-业主"), //新模板，兼容发版过程中305
	ORDER_LANLORD_HAS_PAY_TONIGHT_V2(100306,"订单支付成功-业主,包含今夜特价"),
	ORDER_LANLORD_CHECK_OUT(306,"订单退房通知"),
	ORDER_TENANT_CHECK_OUT(333,"订单退房通知房客"),
    ORDER_USER_CREATE(307,"下单给用户发"),
    CURRENT_ORDER_SMS_TO_LAND(347,"立即预定，用户下单，给房东发送短信"),
    ORDER_LANLORD_CREATE(308,"下单给房东发"),
    APPLY_ORDER_SMS_TO_TEN(348,"申请预定，用户下单，给房客发送短信"),
    ORDER_LANLORD_OTHER_AGREE(309,"同意额外消费"),
    ORDER_LANLORD_OTHER_YES(310,"产生额外消费"),
    ORDER_USER_CAN_MARK(311,"用户可评价"),
    ORDER_USER_REFUND_SUSSESS(312,"用户退款到账"),
    //ORDER_LANLORD_REFUND_SUSSESS(313,"房租已到账"),
    ORDER_LANLORD_REFUND_SUSSESS_V2(100313,"房租已到账"),//新模板，兼容发版过程中313

    ORDER_USER_REFUND_FAIL(314,"您的退款已失败"), //用户
    ORDER_LANLORD_REFUND_FAIL(315,"房租转账失败"),
    ORDER_USER_PAY_FAIL(316,"您的订单支付失败"),
    ORDER_ZERO(317,"出现0元订单 直接给开发人发送短信"),
	ORDER_Malice(1317,"出现恶意订单 直接给开发人发送短信"),
	ORDER_USER_CLEAN(318,"清洁费"),
	
	ORDER_CASHBACK_HIGH_FEE(319,"返现单金额大于订单金额，给开发人员发短信"),
	ORDER_CASHBACK_HIGH_TIMES(320,"返现单生成多次，给开发人员发短信"),
	ORDER_PAY_BACK_CANCELED(321,"支付回调超时，订单已经被取消，给开发人员发短信"),

	ORDER_REFUSED(322,"拒绝订单给房客发送短信"),

	ORDER_PAY_BACK_REPEATE(323,"重复支付回调，给开发人员发短信"),
	
	ORDER_CHECK_IN_PRE_NOTICE_TENANT(335,"房客入住前通知"),
	
	ORDER_UN_PAY_CANCEL_TO_LAN_MSG(100322,"未支付的订单取消通知－房东"), //您有未支付订单被取消。您的{房源名称}房源{订单编号}订单已被房客取消。
	
	//ORDER_LANLORD_CANCEL_MSG(324,"订单取消通知－房东"),
	ORDER_LANLORD_NO_CONFIRM_CANCEL_MSG(100323,"未确认的订单取消通知－房东"), //您有未确认订单被取消。您的XX房源xx订单已被房客xx取消。
	ORDER_LANLORD_CANCEL_MSG_V2(100324,"订单取消通知－房东"), //您有已确认订单被取消。您的XX房源xx订单已被房客xx取消，{未产生违约金}/{产生违约金xx元，请注意查看您的收款账户}。

	ORDER_LANLORD_NEGOTIATE(100325, "协商取消订单给房东发短信"),
	ORDER_USER_NEGOTIATE(100326, "协商取消订单给房客发短信"),

	ORDER_LANLORD_APPLY_CANCEL_TO_LAN(100327, "房东申请取消给房东发短信"),
	ORDER_LANLORD_APPLY_CANCEL_TO_TEN(100328, "房东申请取消给房客发短信"),

	ORDER_WILL_CHECKIN_SEND_MESSAGE_TO_LAN(100400, "给次日将要有订单入住的房东发短信"),
	ORDER_PAYFEE_TO_LAN_SEND_MESSAGE_TO_LAN(100401, "打款成功，给房东发短信"),
	ORDER_PAYCASHBACK_TO_LAN_SEND_MESSAGE_TO_LAN(100402, "返现成功，给房东发短信"),
	
	ORDER_CHECK_OUT_MSG(325,"房客退房日当天"), //房客退房日当天上午给房客发短信

	
	PHOTOGRAPHER_BOOK_ASSIGN(326,"指派摄影师发送房东"),
	PHOTOGRAPHER_CANCEL_REBOOK(327,"作废可再次预约房东"),
	PHOTOGRAPHER_CANCEL_REBOOKG(332,"作废可再次预约摄影师"),
	PHOTOGRAPHER_CANCEL_NOTBOOK(331,"作废不可再次预约"),
	PHOTOGRAPHER_BOOK_GRAPHER(330,"指派发送给摄影师"),

	ORDER_CREATE_MSG(328,"下单完成提示"),

	HOUSE_ONLINE(501,"房源上架短信"),
	HOUSE_ONLINE_MSG(541,"房源上架通知"),
	HOUSE_ONLINE_AB(521,"房源上架通知"),
	HOUSE_ONLINE_AB_ROOMTYPE(531,"房源上架通知"),
	HOUSE_BUTLER_AUDIT_FAIL(502,"管家审核未通过(房东)"),
	HOUSE_ZO_AUDIT_FAIL(503,"管家审核未通过(管家)"),
	HOUSE_ZO_AUDIT_SUCCESS(504,"管家审核通过(管家)"),
	HOUSE_QA_AUDIT_FAIL(505,"品质审核未通过(管家)"),
	HOUSE_QA_AUDIT_SUCCESS(506,"品质审核通过(管家)"),
	HOUSE_RELEASE(507,"房东发布房源(管家)"),
	HOUSE_DOWN(508,"房东申请房源下架(管家)"),
	HOUSE_FORCE_OFFLINE(509,"房源强制下架短信"),//(房东短信)
	HOUSE_FORCE_OFFLINE_MSG(529,"房源强制下架通知"),//(房东消息)
	HOUSE_LANDLORD_RELEASE(510,"房源发布"),
	HOUSE_PIC_MODIFY_AUDIT_PASS(511,"房源照片修改审核通过"),
	HOUSE_DFB_NOTICE_MESSAGE(512,"待发布状态房源消息通知"),
	HOUSE_QA_AUDIT_FAIL_LANDLORD_SMS(513,"房源审核未通过短信"),//(房东短信)
	HOUSE_QA_AUDIT_FAIL_LANDLORD_MSG(514,"房源审核未通过通知"),//(房东消息)
	HOUSE_QA_AUDIT_FAIL_OVERTIME_LANDLORD_SMS(515,"品质审核未通过超时短信"),//(房东短信)
	HOUSE_QA_AUDIT_FAIL_OVERTIME_LANDLORD_MSG(516,"品质审核未通过超时通知"),//(房东消息)
	HOUSE_QA_AUDIT_FAIL_NOT_CONTACT_LANDLORD_SMS(517,"无法联系到房东短信"),

	HOUSE_SJ_MODIFY_AUDIT_PASS(518,"上架房源信息修改审核通过"),
	HOUSE_SJ_MODIFY_AUDIT_REJECT(519,"上架房源信息修改审核驳回"),
	HOUSE_SJ_MODIFY_AUDIT_BF_REJECT(520,"上架房源信息修改部分审核驳回"),

	SMARTLOCK_LANDLORD_TEMPPWD_SUCCESS(611,"房东申请临时密码成功"),
	SMARTLOCK_LANDLORD_TEMPPWD_FAIL(612,"房东申请临时密码失败"),
	
	/*SMARTLOCK_LANDLORD_DYNAPWD_SUCCESS(613,"房东申请动态密码成功"),
	SMARTLOCK_LANDLORD_DYNAPWD_FAIL(614,"房东申请动态密码失败"),*/
	
	SMARTLOCK_LANDLORD_MANAGERPWD_SUCCESS(615,"房东修改管理密码成功"),
	SMARTLOCK_LANDLORD_MANAGERPWD_FAIL(616,"房东修改管理密码失败"),
	
	SMARTLOCK_OTHER_TEMPPWD_SUCCESS(617,"房东发给其他人临时密码成功"),
	SMARTLOCK_OTHER_DYNAPWD_SUCCESS(618,"房东发送其他人动态密码成功"),
	
	HOUSE_TODAYDISCOUNT_LANDLORD_REMIND_MSG(619,"提醒房东设置今夜特价通知"),
	HOUSE_TODAYDISCOUNT_LANDLORD_REMIND_SMS(620,"提醒房东设置今夜特价短信"),
	
	SMARTLOCK_TENENT_DYNAPSWD_NOTIFY_TENENT(603,"房客获取动态密码成功通知(房客)"),
	SMARTLOCK_TENENT_DYNAPSWD_NOTIFY_LANDLORD(604,"房客获取动态密码成功通知(房东)"),
	
	SMARTLOCK_TENENT_TEMPPWD_NOTIFY_ENENT(605,"房客获取临时密码成功通知(房客)"),
	SMARTLOCK_TENENT_TEMPPWD_NOTIFY_LANDLORD(606,"房客获取临时密码成功通知(房东)"),
	
	
	IM_TENENT_UNREAD_MSG(701,"IM房客未读消息提醒"),
	IM_LANDLORD_UNREAD_MSG(702,"IM房东未读消息提醒"),
	MSG_LANDLORD_UNREAD_MSG(703,"短信房东未读消息提醒"),
	
	IM_ZRY_CHECK_IN_ADD_GROUP(705,"办理入住后自动进群"),
	IM_ZRY_CHECK_OUT_EXIT_GROUP(706,"退房后系统将退房人自动移除群聊"),
	IM_ZRY_USER_EXIT_GROUP(707,"用户主动退出群聊"),
	IM_ZRY_ZO_ADD_GAG(708,"群聊中被管理员（ZO）禁言"),
	IM_ZRY_ZO_DELETE_GROUP(709,"被管理员被（ZO）踢出群聊"),
	IM_ZRY_ZO_ADD_GROUP_MANAGER(710,"成为管理员"),
	IM_ZRY_ZO_DELETE_GROUP_MANAGER(711,"取消管理员"),
	IM_ZRY_ZO_DELETE_GAG(712,"解除禁言"),

	JOB_NOTICE(801,"定时任务短信提醒"),
	JOB_NOTICE_F(802,"定时任务执行失败"),

	ACT_COUPON(901,"优惠券码"),
	
	WEATHER_MSG(99999999,"天气消息模板"),
	
	ACTIVITY_GIFT(10181,"自如五周年礼品"),
	ACTIVITY_YHQ(10184,"自如五周年优惠券"),
	ACTIVITY_YONGJ(10182,"自如五周年佣金"),

	ACTIVITY_SYRSQ(10185, "给受邀人送券"),
	ACTIVITY_YQRSQ(10186, "给邀请人送券"),
	
	INVITEE_ACCEPT_SMS_TO_INVITER(10285, "被邀请人接收邀请，给邀请人发送短信"),
	INVITEE_ACCEPT_SMS_TO_HESELF(10286, "被邀请人接收邀请，获得优惠券，给被邀发送短信"),
	INVITEE_CREATE_ORDER_SMS_TO_INVITER(10287, "被邀请人下单并已入住成功，给邀请人发送短信"),
	
	CMS_FIRST_SINGLE_REDUCTION_NOTICE_MSG(10187,"首单立减系统消息"),
	CMS_FIRST_SINGLE_REDUCTION_REMIND_SMS(10188,"首单立减30天唤醒提示短信"),

	CMS_HOUSE_DETAIL_COUPON_GET_SUCCESS(10189,"房源详情领券获取优惠券提示信息"),
	CMS_HOUSE_DETAIL_COUPON_EXPIRE_MSG(10190,"领取优惠券过期提醒"),

	PHOTOGRAPHER_FINISH_NOTIFY_LANDLORD(11001,"摄影拍摄完成"),
	
	CASH_ORDER_TO_LANLORD_FIRST_SECONT_THIRD(12000,"房东进击活动 第一 第二 第三等级"),
	CASH_ORDER_TO_LANLORD_FOUR(12001,"房东进击活动 第四等级"),
	
	
	LANLORD_AUDITED_INTRDUCE(13001,"房东个人介绍审核通过"),
	LANLORD_AUDITED_HEAD_PIC(13002,"房东头像审核通过"),
	LANLORD_AUDITED_NICKNAME(13003,"房东昵称审核通过"),
	
	EMAIL_CODE_ORDER(900001,"自如民宿订单提醒");

	
	MessageTemplateCodeEnum(int code, String name){
		this.code = code;
		this.name = name;
	}
	 /** code */
    private int code;
    
    /** 名称 */
    private String name;
    
    public int getCode() {
    	return code;
    }
    
    public void setCode(int code) {
    	this.code = code;
    }
    
    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public static MessageTemplateCodeEnum getMsgTemplateCodeByCode(int msgCode) {
        for (final MessageTemplateCodeEnum code : MessageTemplateCodeEnum.values()) {
            if (code.getCode() == msgCode) {
                return code;
            }
        }
        return null;
    }

}
