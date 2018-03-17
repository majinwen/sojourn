/*
 * Copyright (c) 2016. Copyright (c) 2016. ziroom.com.
 */
package com.ziroom.minsu.ups.common.constant;

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
     * 权限信息缓存时间
     */
    public static final Integer  RES_CACHE_TIME=60*60;
    
    /**
     * 权限树后缀
     */
    public static final String RES_LIST="res_list";
    
    /**
     * 权限集合后缀
     */
    public static final String RES_SET="res_set";
    
    /**
     * 登录用户名缓存时间
     */
    public static final int USER_NAME_TIME=30*60;
    
    /**
     * 权限系统统一登录状态缓存key
     */
    public static final String USER_NAME_CACHE_KEY="_const_cas_assertion_cache_key";
}
