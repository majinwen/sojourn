package com.ziroom.minsu.mapp.common.util;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.sys.CurrentuserEntity;
import com.ziroom.minsu.mapp.common.constant.MappMessageConst;
import com.ziroom.minsu.services.customer.entity.CustomerVo;


/**
 * 
 * <p>用户工具类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class UserUtil {

	// 登陆人信息session标记
	private static final String LOGIN_USER_SESSION_ID = MappMessageConst.SESSION_USER_KEY;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserUtil.class);
	
	private UserUtil(){
		
	}

	/**
	 * 
	 * 获取当前登录用户
	 *
	 * @author liujun
	 * @created 2016-3-18 下午10:01:43
	 *
	 * @return
	 */
	public static CustomerVo getCurrentUser() {
		CustomerVo user = null ;
		try {
			HttpSession session = HttpUtil.getSession();
			Object object = session.getAttribute(LOGIN_USER_SESSION_ID);
			if(!Check.NuNObj(object) && object instanceof CustomerVo){
				user = (CustomerVo)object;
			}
		} catch (Exception e) {
            LogUtil.error(LOGGER, "Failed to get current user from the session ", e) ;
		}
		return user ;
	}
	
	/**
	 * 
	 * 获取当前登录用户逻辑FID
	 *
	 * @author liujun
	 * @created 2016-3-18 下午10:02:50
	 *
	 * @return
	 */
	public static String getCurrentUserFid() {
		String userFid = null;
		try {
			CustomerVo user = getCurrentUser();
			userFid = (user == null ? userFid : user.getUid());
		} catch (Exception e) {
            LogUtil.error(LOGGER,"Failed to get current user fid from the session " , e) ;
		}
		return userFid ;
	}
	
	/**
	 * 
	 * 往sesson中注入用户
	 *
	 * @author liujun
	 * @created 2016-3-19 下午1:50:28
	 *
	 * @param user
	 */
	public static void setUser2Session(CurrentuserEntity user) {
		setUser2Session(user, HttpUtil.getSession());
    }

	/**
	 * 
	 * 往sesson中注入用户
	 *
	 * @author liujun
	 * @created 2016-3-19 下午1:50:28
	 *
	 * @param user
	 * @param session
	 */
	public static void setUser2Session(CurrentuserEntity user, HttpSession session) {
		session.setAttribute(LOGIN_USER_SESSION_ID, user);
    }
	
	/**
	 * 
	 * 从session中移除用户
	 *
	 * @author liujun
	 * @created 2016-3-19 下午1:50:28
	 *
	 * @param user
	 * @param session
	 */
    public static void removeUserFromSession(HttpSession session) {
        session.removeAttribute(LOGIN_USER_SESSION_ID);
    }
}
