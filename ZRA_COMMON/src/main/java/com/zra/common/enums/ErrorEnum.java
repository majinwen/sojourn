package com.zra.common.enums;

/**
 * Created by cuigh6 on 2016/4/21.
 */
public enum ErrorEnum {

    MSG_SUCCESS(200, "操作成功！"),
    MSG_FAIL(0, "系统异常,请稍后重试！！！"),
    MSG_PARAM_NULL(1001, "参数不能为空！"),
    MSG_SIGN_FAIL(400, "Signature illegal!"),
    MSG_BUSINESS_EXPECTTIME_FAIL(1000, "约看时间格式不正确"),
    MSG_BUSINESS_EXPECTINTIME_FAIL(1001, "期望入住时间格式不正确"),
    MSG_BUSINESS_CALENDTIME_FAIL(1002, "计算约看截止日期出错"),
    MSG_BUSINESS_CALHANDSTATE_FAIL(1003, "计算约看截止日期出错"),
    MSG_BUSINESS_HANDSTATE_FAIL(1004, "新建商机时处理状态错误"),
    MSG_BUSINESS_NOSHEDUALZO_FAIL(1004, "没有排班的管家"),
    MSG_BUSINESS_SAVE_FAIL(1005, "保存商机出错"),
    MSG_BUSINESS_SAVECUSTOMER_FAIL(1006, "保存客户出错"),
    MSG_BUSINESS_GET_FAIL(1007, "获取商机信息错误"),
    MSG_EVALUATE_RETURN_NULL(1008, "评价系统返回信息为空"),
    MSG_EVALUATE_RETURN_FAIL(1009, "评价系统返回错误"),
    MSG_EVALUATE_RUN_FAIL(1010, "评价系统失败"),
    MSG_EVALUATE_GETQUESTION_NULL(1011, "获取评价问题信息返回为空"),
    MSG_EVALUATE_GETQUERETURN_FAIL(1012, "获取评价问题信息返回错误"),
    MSG_EVALUATE_GETQUERUN_FAIL(1013, "获取评价问题信息失败"),
    MSG_EVALUATE_HISRUN_FAIL(1014, "获取评价历史详情失败"),
    MSG_EVALUATE_HISRETURN_FAIL(1015, "获取评价历史详情返回错误"),
    MSG_EVALUATE_HISTORY_NULL(1015, "获取评价历史详情返回错误"),
    MSG_BUSINESS_ALREADY_HANDED(1016, "商机已经处理，请刷新查看"),
    MSG_BUSINESS_CLOSE_FAIL(1017, "关闭商机出错"),
    MSG_BUSINESS_REPEAT_SUBMIT(1018, "商机重复提交"),
    MSG_BUSINESS_REPORT_PROJECT_NOT_NULL(1019, "商机报表，项目id不能为空"),
    MSG_BUSINESS_CANCEL_STEP_FAIL(1020, "管家已经处理，无法取消，请联系管家"),
    MSG_BED_IN_RENTAL(1021, "出租中的床位不可以修改状态"),
    MSG_BED_HAS_ITEM(1022, "床位有物品配置不能删除"),
    MSG_BED_ITEM_CONFIGED(1023, "已配置床位不能删除"),
    MSG_ROOM_COPY_PROJECT_ROOM_FULL(1024, "当前项目房间已满，不能复制房间"),
    MSG_ROOM_COPY_ROOM_NUMBER_ISEXIST(1025, "当前项目已存在该房号房间"),
    MSG_SUBMIT_PAYMENT_PLATFORM_FAIL(1026, "向支付平台下单失败，请稍后重试"),
    MSG_QUERY_PAYMENT_PLATFORM_FAIL(1027, "向支付平台查询失败，请稍后重试"),
    MSG_PAYMENT_AMOUNT_ERR(1028, "支付金额大于待缴金额，请重新选择"),
    MSG_PAY_ORDER_STATUS_FAIL(1029, "订单已提交支付信息，请勿重新支付"),
    MSG_PAY_PAYMENT_PLATFORM_FAIL(1030, "向支付平台支付失败，请稍后重试"),
    RENT_CARD_CODE_NO_PASS(1031, "租金卡校验不通过，请稍后重试"),
    COUPON_CODE_NO_PASS(1031, "优惠券校验不通过，请稍后重试"),
    COUPON_CODE_INVALID(1032, "优惠券编码无效，请检查后重试"),
    CARD_USE_FAIL(1033, "租金卡使用失败，请稍后重试"),
    COUPON_USE_FAIL(1034, "优惠券使用失败，请稍后重试"),
    MSG_CARDCOUPON_CONSUME_ERR(1035, "使用卡券失败，请稍后重试"),
    CONTRACT_DETAIL_NULL(1036, "未查询到合同信息"),
    CONTRSCT_HAS_CHANGED(1038,"支付回调失败，合同状态已变更"),
    SIGN_CHECK_FAIL(1036,"验签失败,请联系管家"),
    FORCE_UPGRADE_TXT(1039, "非常抱歉,请您升级到新版本APP进行查看");


    private final String message;
    private final int code;

    ErrorEnum(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
