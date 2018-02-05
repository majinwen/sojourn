package com.ziroom.minsu.api.common.constant;

/**
 * <p>评价常量</p>
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
public class EvaluateConst {


    public static final String EVA_INFO_TITLE_TIME= "还有%d天来完成评价。";
    /**
     * 评价时间超时，不能评价
     */
    public static final String EVA_INFO_TIME_OUT = "已超过评价有效期，无法进行评价。";

    public static final String EVA_INFO_TITLE_TIPS= "%s已经给您写了评价，您完成评价后就可以阅读Ta写给您的评价。";
    /**
     * 房客已评价 但是房东还不能评价
     */
    public static final String LAN_EVA_INFO_GAP_TIPS= "%s已经给您写了评价，Ta离开后就可以对Ta的此次入住进行评价，并阅读Ta写给您的评价。";

    public static final String LAN_INFO_TITME_REPL = "还有%d天来完成对Ta评价的公开回复。";

    /**
     * 房东评价详情标题
     */
    public static final String LAN_EVA_INFO_TITLE_NAME= "评价%s的住宿";

    /**
     * 房客的评价
     */
    public static final String TEN_EVA_INFO_TITLE_NAME = "评价在%s的%d晚住宿";
    /**
     * 评价分享主标题
     */
    public static final String EVA_SHARE_TITLE_MAIN_NAME = "我在%s旅行，分享我住过的超赞自如民宿给你！";

    /**
     * 评价分享副标题
     */
    public static final String EVA_SHARE_TITLE_SUB_NAME = "淳美民居，焕心之旅";


    public final static String TENANT_DEFAULT_NAME = "房客";



}
