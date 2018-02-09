/*
 * Copyright (c) 2016. Copyright (c) 2016. ziroom.com.
 */
package com.ziroom.minsu.troy.constant;

/**
 * 
 * <p>常量类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class Constant {

    /**
     * 私有构造器
     */
    private Constant() {

    }

    /**
     * 当前登录的用户名
     */
    public static final String CURRENT_LOGIN_USER_NAME = "__CURRENT_LOGIN_USER_NAME__";

    /**
     * 当前登录的用户名 信息
     */
    public static final String CURRENT_LOGIN_USER_INFO = "__CURRENT_LOGIN_USER_INFO__";

    /**
     * _const_cas_assertion_是CAS中存放登录用户名的session标志
     */
    public static final String CONST_CAS_ASSERTION = "_const_cas_assertion_";

    /**
     * 请求处理前的时间戳
     */
    public static final String PRE_REQUEST_MILLSECOND = "_pre_millsecond";

    /**
     * 日志
     */
    public static final String LOG_FORMAT = "CLASS:[{}],METHOD:[{}],PARAM:[{}],ERROR:[{}]";

    /**
     * session_user_key
     */
    public static final String SESSION_USER_KEY = "session_user_key";

    /**
     * session_return_url_key
     */
    public static final String SESSION_RETURN_URL_KEY = "session_return_url_key";

    /**
     * 请求日志token
     */
    public static final String LOG_TOKEN_KEY = "logToken";

    /**
     * session_token_key
     */
    public static final String SESSION_TOKEN_KEY = "token";

    /**
     * 请求日志记录格式
     */
    public static final String LOG_REQUEST_FORMAT = "URL:[{}],PARAM:[{}],TOKEN:[{}],UID:[{}]";


    /**
     * 请求日志的最大长度
     */
    public static final Integer OP_LOG_LENG_MAX = 500;
    
    /**
     * t_conf_city表顶层节点编码
     */
    public static final String T_CONF_CITY_ROOT_CODE = "100";
    
    /**
     * 开始数字
     */
    public static final Integer COUNT_BEGIN_NUM = 10000000;
    
    /**
     * 违约金收取方式-按天收取
     */
    public static final int LIQUIDATED_DAMAGES_DAY = 0;
    
    /**
     * 违约金收取方式-按固定收取
     */
    public static final int LIQUIDATED_DAMAGES_FIX = 1;
    
}
