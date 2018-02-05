
package com.ziroom.minsu.api.common.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.MD5Util;
import com.asura.framework.utils.LogUtil;

/**
 * <p>配置登录或加密的全局常量
 *   说明：登录拦截器拦截  1.登录并且解密  和 登录并且加密 （lonAuth 和 lonUnauth）
 *       加密拦截器拦截  2.登录并且加密  和 不登录并且加密（lonAuth 和 unlonAuth）
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class LoginAuthConst {
	
	private static Logger logger =LoggerFactory.getLogger(LoginAuthConst.class);

	/**
	 * 登录并且加密lonAuth
	 */
	public final static String LOGIN_AUTH= "ea61d2";
	
	/**
	 * 登录并且不加密lonUnauth
	 */
	public final static String LOGIN_UNAUTH="43e881";
	/**
	 * 不登录并且加密unlonAuth
	 */
	public final static String UNLOGIN_AUTH="8541d0";
	/**
	 * 不登录并且不加密noLonAuth
	 */
	public final static String NO_LGIN_AUTH="ee5f86";

	/**
	 * token的参数名称
	 */
	public final static String TOKEN = "token";

	/**
	 * 用户uid的参数名称
	 */
	public final static String UID = "uid";
	
	/**
	 * 成功状态success
	 */
	public final static String SUCCESS = "20000";
	
	/**
	 * 封装用户token和uid的参数
	 */
	public final static String USER_INFO ="userInfo";
	
	/**
	 * version的参数
	 */
	public final static String VERSION ="client-version";
	
	
	/**
	 * 
	 * 字符串MD5后取前6位
	 *
	 * @author yd
	 * @created 2016年5月5日 上午10:30:31
	 *
	 * @param sourceStr
	 * @return
	 */
	private static String getMd5Pre(String sourceStr){
		
		if(!Check.NuNStr(sourceStr)){
			return MD5Util.MD5Encode(sourceStr).substring(0, 6);
		}
		return null;
	}
	
	/**
	 * 
	 * 获取字符串测试
	 *
	 * @author yd
	 * @created 2016年5月5日 上午10:34:07
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		
		LogUtil.info(logger,"lonAuth="+LoginAuthConst.getMd5Pre("lonAuth"));
		LogUtil.info(logger,"lonUnauth="+LoginAuthConst.getMd5Pre("lonUnauth"));
		LogUtil.info(logger,"unlonAuth="+LoginAuthConst.getMd5Pre("unlonAuth"));
		LogUtil.info(logger,"noLonAuth="+LoginAuthConst.getMd5Pre("noLonAuth"));
	}

}
