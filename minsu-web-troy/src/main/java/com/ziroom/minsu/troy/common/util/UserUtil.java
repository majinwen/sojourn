package com.ziroom.minsu.troy.common.util;

import javax.servlet.http.HttpSession;

import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.ziroom.minsu.entity.sys.CurrentuserEntity;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.services.basedata.entity.UpsUserVo;
import com.ziroom.minsu.troy.constant.Constant;


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
	private static final String LOGIN_USER_SESSION_ID = Constant.CURRENT_LOGIN_USER_NAME;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserUtil.class);
	
	private UserUtil(){
		
	}

	/**
	 * 
	 * 获取当前登录用户ups信息
	 *
	 * @author busj
	 * @created 2016-3-18 下午10:01:43
	 *
	 * @return
	 */
	public static UpsUserVo getUpsUserMsg() {
		UpsUserVo user = null ;
		try {
			HttpSession session = HttpUtil.getSession();
			
			Object object = session.getAttribute(LOGIN_USER_SESSION_ID);
			
			if(!Check.NuNObj(object)){
				user = (UpsUserVo)object;
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
			UpsUserVo user = getUpsUserMsg();
			userFid = (user == null ? userFid : user.getCurrentuserEntity().getFid());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "Failed to get current user fid from the session ", e);
		}
		return userFid;
	}
	
	/**
	 * 
	 * 获取当前登录员工逻辑FID
	 *
	 * @author liujun
	 * @created 2016-3-18 下午10:02:50
	 *
	 * @return
	 */
	public static String getEmployeeFid() {
		String employeeFid = null;
		try {
			UpsUserVo user = getUpsUserMsg();
			employeeFid = (user == null ? employeeFid : user.getEmployeeEntity().getFid());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "Failed to get current user fid from the session ", e);
		}
		return employeeFid;
	}
	
	/**
	 * 
	 * 获得登录用户信息
	 *
	 * @author bushujie
	 * @created 2017年11月6日 下午3:27:46
	 *
	 */
	public static CurrentuserEntity getCurrentUser(){
		CurrentuserEntity currentuserEntity = null;
		try {
			UpsUserVo user = getUpsUserMsg();
			currentuserEntity = (user == null ? currentuserEntity : user.getCurrentuserEntity());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "Failed to get current user fid from the session ", e);
		}
		return currentuserEntity;
	}
	
	/**
	 * 
	 * 获取登录用户扩展信息
	 *
	 * @author bushujie
	 * @created 2017年11月6日 下午3:31:30
	 *
	 * @return
	 */
	public static CurrentuserVo getFullCurrentUser(){
		CurrentuserVo currentuserVo= new CurrentuserVo();
		try {
			UpsUserVo user = getUpsUserMsg();
			if(!Check.NuNObj(user)){
				BeanUtils.copyProperties(user.getCurrentuserEntity(), currentuserVo);
				currentuserVo.setEmployeeFid(user.getEmployeeEntity().getFid());
				currentuserVo.setEmpCode(user.getEmployeeEntity().getEmpCode());
				currentuserVo.setFullName(user.getEmployeeEntity().getEmpName());
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "Failed to get current user fid from the session ", e);
		}
		return currentuserVo;
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
	public static void setUser2Session(UpsUserVo user) {
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
	public static void setUser2Session(UpsUserVo user, HttpSession session) {
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
