package com.zra.pay.entity;


import com.zra.common.utils.PropUtils;

/**
 * @Author: wangxm113
 * @CreateDate: 2016年5月5日
 */
public interface ThirdParam {
    // 支付接口解密密码
    public static final String PAY_AES_KEY = "PAY_AES_KEY";
    public static final String PAY_SYSTEM_ID = "PAY_SYSTEM_ID";
    // 模板解析字符设置
    public final static String ENCODING_UTF = "UTF-8";

    // 正式环境
    public static final String PAY_URL = "PAY_URL";


    // 下单接口地址
    public static final String PAY_GEN_ORDER_URL = PropUtils.getString(PAY_URL) + "/pay/ZRGenerOrder.do";
    // 获取支付页面url接口
    public static final String PAY_PRE_PAY_URL = PropUtils.getString(PAY_URL) + "/pay/{payChannel}/{cityCode}/paySubmit.do";
    // 向支付平台查询URL
    public static final String QUERY_URL = PropUtils.getString(PAY_URL) + "/pay/queryState.do";


    //向客户信息库查询URL
    public static final String CUSTOMER_QUERY_URL = PropUtils.getString("CUSTOMER_STOCK_URL");

}
