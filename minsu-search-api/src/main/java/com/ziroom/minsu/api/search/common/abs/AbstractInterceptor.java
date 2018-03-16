package com.ziroom.minsu.api.search.common.abs;

import com.ziroom.minsu.entity.sys.CurrentuserEntity;
import com.ziroom.minsu.api.search.constant.Constant;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpSession;

/**
 * <p>抽象拦截器</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/12.
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractInterceptor extends HandlerInterceptorAdapter {


    /**
     * 获取当前登录的用户
     *
     * @param session
     *
     * @return
     */
    public static String getCurrentUserName(HttpSession session) {
        Object sessionUser = session.getAttribute(Constant.CURRENT_LOGIN_USER_NAME);
        if (sessionUser == null) {
            return null;
        }
        String useraccountVO = (String) sessionUser;
        return useraccountVO;
    }



    /**
     * 获取当前登录的用户信息
     *
     * @param session
     *
     * @return
     */
    public static CurrentuserEntity getCurrentUserInfo(HttpSession session) {
        Object sessionUser = session.getAttribute(Constant.CURRENT_LOGIN_USER_INFO);
        if (sessionUser == null) {
            return null;
        }
        CurrentuserEntity user = (CurrentuserEntity) sessionUser;
        return user;
    }
}
