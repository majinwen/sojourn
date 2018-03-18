package com.zra.common.enums;

/**
 * <p>短信推送模板code</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月19日 14:41
 * @since 1.0
 */
public enum SmsTemplateCodeEnum {

    SIGN_INVITE_SMS_MSG(200001, "新签邀请短信提醒"),
    SIGN_INVITE_PUSH_MSG(210001, "新签邀请推送提醒"),
    RENEWAL_SIGN_INVITE_SMS_MSG(200002, "续签邀请短信提醒"),
    RENEWAL_SIGN_INVITE_PUSH_MSG(210002, "续签邀请推送提醒"),

    DELIVERY_USER_MSG(220001,"提示用户物业交割"),
    DELIVERY_ZO_MSG(220002,"提示管家物业交割"),
    DELIVERY_USER_FEE_MSG(220003,"提示用户物业交割-带生活费用"),
    DELIVERY_ZO_FEE_MSG(220004,"提示管家物业交割-带生活费用"),
    DELIVERY_USER_DONE_FEE_MSG(220005,"定时任务确认物业交割提示用户"),
    HAS_PAY_NEED_DELIVERY_ZO_MSG(220007,"用户首期支付后提示管家录入物业交割"),
    HAS_APPLY_SURRENDER(220008,"解约申请后发送短信提示用户"),

    PAY_OVERTIME_MSG(220100,"支付有效期已失效提醒"),
    UNSIGNED_CLOSE_MSG(220101,"签约有效期超时关闭合同提醒"),
    PAY_BEFORE_OVERTIME_REMIND_SMS_MSG(220102,"支付有效期前n小时短信提醒"),
    PAY_BEFORE_OVERTIME_REMIND_PUSH_MSG(220103,"支付有效期前n小时推送提醒"),
    UNSIGNED_BEFORE_OVERTIME_REMIND_SMS_MSG(220104,"签约有效期超时前n小时短信提醒"),
    UNSIGNED_BEFORE_OVERTIME_REMIND_PUSH_MSG(220105,"签约有效期超时前n小时推送提醒"),
    LOWLEVEL_CALLBACK_REMIND_PUSH_MSG(220011,"自如寓低电量提醒推送消息"),
    LOWLEVEL_CALLBACK_REMIND_SMS_MSG(220010,"自如寓低电量提醒短信");


    SmsTemplateCodeEnum(int code, String name){
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


    public static SmsTemplateCodeEnum getMsgTemplateCodeByCode(int msgCode) {
        for (final SmsTemplateCodeEnum code : SmsTemplateCodeEnum.values()) {
            if (code.getCode() == msgCode) {
                return code;
            }
        }
        return null;
    }
}
