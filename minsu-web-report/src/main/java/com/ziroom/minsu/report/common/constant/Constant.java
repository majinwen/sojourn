/*
 * Copyright (c) 2016. Copyright (c) 2016. ziroom.com.
 */
package com.ziroom.minsu.report.common.constant;

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
     * resource_user_tree
     */
    public static final String RESOURCE_USER_TREE="resource_user_tree";

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
    public static final Integer OP_LOG_LENG_MAX = 512;
    
    /**
     * t_conf_city表顶层节点编码
     */
    public static final String T_CONF_CITY_ROOT_CODE = "100";
    
    /**
     * 开始数字
     */
    public static final Integer COUNT_BEGIN_NUM = 10000000;
    
    /**
     * 每个excel限制记录数目
     */
    public static final Integer PAGE_LIMIT = 800;
    
    /**
     * 目录
     */
    public static final String DIR_PATH = "";
    
    /**
     * 编码格式
     */
    public static final String ENCODE = "GBK";
    
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
    
    /**
     * 每次读取数据条数
     */
    public static final Integer AREA_READ_LIMIT = 1000;
    
    /**
     * excel最大数量限制
     */
    public static final Integer EXCEL_NUM_LIMIT=60000;
}
