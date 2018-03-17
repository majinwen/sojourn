package com.ziroom.minsu.portal.fd.center.common.constant;

/**
 * 
 * <p>常量工具类</p>
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
public final class Constant {

    /**
     * 私有构造器
     */
    private Constant() {

    }

    /**
     * 当前session中存储的token
     */
    public static final String CURRENT_TOKEN = "__CURRENT_TOKEN__";

    /**
     * cookie中的名称
     */
    public static final String COOKIE_TOKEN = "passport_token";
    
    /**
     * 7天的时间（秒）
     */
    public static final Integer SEVEN_TIME = 604800;
}
