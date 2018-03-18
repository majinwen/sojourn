package com.zra.common.utils;

public interface ZraApiConst {

    /**
     * swagger 包扫描
     */
    String CON_SWAGGER_RES_PACKAG_KEY = "swagger.resource.package";
    String CON_SWAGGER_HOST_KEY = "swagger.host";
    /**
     * swagger接口参数
     */
    String CON_NEED_PARAM = "接口参数列表<br> imei(String)-设备唯一标识;<br>timestamp(Timestamp)-时间戳;"
            + "<br>phoneModel(String)-手机型号;<br><font color=\"#FF0000\">l(String)-语言版本,示例：en/zh;</font> <br>appVersionStr(String)-应用版本号;"
            + "<br>sysVersionStr(String)-系统版本号;<br>source(String)-来源;<br><font color=\"#FF0000\">cityCode(String)-城市编码;</font><br>";

    /**
     * APP预约看房成功后返回的文字
     */
    String APP_BUSINESS_APPLY_RETURN = "APP_BUSINESS_APPLY_RETURN";

    /**
     * 获取评价问题信息接口地址
     */
    String EVALUATE_GETQUESTION_URL = "EVALUATE_GETQUESTION_URL";

    /**
     * 评价的地址
     */
    String EVALUATE_URL = "EVALUATE_URL";

    /**
     * 获取评价历史详情
     */
    String EVALUATE_HISTORY_URL = "EVALUATE_HISTORY_URL";

    /**
     * 调用第三方接口时的编码
     */
    String ENCODING_UTF = "UTF-8";

    /**
     * 图片服务器地址前缀
     */
    String PIC_PREFIX_URL = "PIC_PREFIX_URL";

    /**
     * App端项目排序
     */
    String  CONS_PROJECT_SORT_KEY = "projectsortkey";
    String CONS_SYSTEMID_ZRA = "1001";

    /**
     * 待约看处理时间
     */
    String DYK_MINUTES = "DYK_MINUTES";
    
    /**
     * 短信接口token 
     */
    String SMS_TOKEN = "sms.token";
    
    /**
     * 短信签名
     */
    String SMS_SIGNATURE_KEY = "sms.signature";

    /**
     * 短信是否发送标识 
     */
    String SMS_IS_SEND = "sms.is.send";
    
    /**
     * 短信模板
     */
    /**服务管家*/
    String SMS_ZO_NAME_MSG="SMS_ZO_NAME_MSG";
    String SMS_ZO_PHONE_MSG="SMS_ZO_PHONE_MSG";
    /**约看确认短信*/
    String SMS_YKQR_MSG="SMS_YKQR_MSG";
    /**约看时间变更：*/
    String SMS_YKSJBG_MSG="SMS_YKSJBG_MSG";
    /**预约通知：*/
    String SMS_YYTZ_MSG="SMS_YYTZ_MSG";
    /**约看提醒：*/
    String SMS_YKTX_MSG="SMS_YKTX_MSG";
    /**带看评价：*/
    String SMS_DKPJ_MSG="SMS_DKPJ_MSG";

    /**
     * 项目筛选和房型筛选，根据筛选条件没有查到内容时显示的文案
     */
    String SEARCH_FAIL_MSG = "SEARCH_FAIL_MSG";
    
    
    /**
     * 带看评价长连接地址
     */
    String ZOEVA_LONG_URL="ZOEVA_LONG_URL";
    
    /**
     * 带看评价短连接地址
     */
    String ZOEVA_SHORT_URL="ZOEVA_SHORT_URL";
    
    /**
     * 带看评价短信开关
     */
    String ZOEVA_SMS_OPEN="ZOEVA_SMS_OPEN";
    
    /**
     * 带看评价push开关
     */
    String ZOEVA_PUSH_OPEN="ZOEVA_PUSH_OPEN";
    
    /**
     * 带看评价push开关
     */
    String YYTZ_SMS_OPEN="YYTZ_SMS_OPEN";
    
    /**
     * 评价推送标题
     */
    String YYTZ_PUSH_TITLE = "YYTZ_PUSH_TITLE";
    
    /**
     * 评价推送内容
     */
    String YYTZ_PUSH_MSG = "YYTZ_PUSH_MSG";

    /**
     * 约看确认推送
     */
    String YKQR_PUSH_MSG = "YKQR_PUSH_MSG";

    /**
     * 约看确认推送长链接
     */
    String YKQR_LONG_URL = "YKQR_LONG_URL";

    /**
     * 管家带看完成后推动内容
     */
    String DKWC_PUSH_MSG = "DKWC_PUSH_MSG";

    /**
     * 自如寓热线
     */
    String ZRA_TEL_PREFIX = "4001001111,";

    /**
     * 联系不上短信  管家手机号
     */
    String SMS_LXBS_MSG_PHONE="SMS_LXBS_MSG_PHONE";
    /**
	 * 联系不上短信  短信内容
     */
    String SMS_LXBS_MSG_CONTENT="SMS_LXBS_MSG_CONTENT";

    /**
     * 自如寓户型详情页图片展示顺序
     */
    String HOUSE_DETAIL_IMG_ORDER = "HOUSE_DETAIL_IMG_ORDER";
    /**
     * 支付平台回调的接口URL
     */
    String PAY_CALLBACK_URL = "PAY_CALLBACK_URL";

    /**
     * 下单时，若使用了优惠券之后支付金额为0元，返回给app的值
     */
    String RETURN_APP_WITHOUT_PAYMONEY = "RETURN_APP_WITHOUT_PAYMONEY";

    /**
     * 向支付平台下单时的参数,支付种类，当前为1（外部支付，单纯充值账户余额也请填1）
     */
    Integer ORDER_TYPE = 1;

    /**
     * 提交的订单不支付，多长时间失效（单位：秒），最长为一年，即31536000秒，默认为30分钟，即1800秒
     */
    String ORDER_EXPIRED = "ORDER_EXPIRED";

    /**
     * 调用支付平台时，传的cityCode
     */
    String CITY_CODE_FOR_PAYMENT_PLATFORM_BJ = "CITY_CODE_FOR_PAYMENT_PLATFORM_BJ";
    String CITY_CODE_FOR_PAYMENT_PLATFORM_SH = "CITY_CODE_FOR_PAYMENT_PLATFORM_SH";
    /**
     * 调用支付平台时，是否传账户passAccount
     */
    String PASS_ACCOUNT_FOR_PAYMENT_PLATFORM = "PASS_ACCOUNT_FOR_PAYMENT_PLATFORM";
    
    /**
     * 春节商机 延期处理短信提示内容
     */
    String SMS_CHUNJIE_MSG_CONTENT="SMS_CHUNJIE_MSG_CONTENT";
    
    /**
     * 缓存开关标识
     */
    String REDIS_SWITCH_KEY = "redis.switch";
    
    String ELASTICSEARCH_CREATEINDEX_URL = "ELASTICSEARCH_CREATEINDEX_URL";
    
    /**
     * 业务总览统计研发短信模板
     */
    String SMS_REPORTOVERVIEW_MSG = "SMS_REPORTOVERVIEW_MSG";
    
    /**
     * 业务总览统计研发短信接收手机号
     */
    String SMS_REPORTOVERVIEW_PHONES = "SMS_REPORTOVERVIEW_PHONES";
    //add by tianxf9 回复卡券失败短信提醒
    String CARD_COUPON_FAIL_MSG = "CARD_COUPON_FAIL_MSG";
    String RECEIVE_CARD_COUPON_PHONE = "RECEIVE_CARD_COUPON_PHONE";
    String FINSETTLE_COUPONS_NEW_ID = "FINSETTLE_COUPONS_NEW_ID";
}
