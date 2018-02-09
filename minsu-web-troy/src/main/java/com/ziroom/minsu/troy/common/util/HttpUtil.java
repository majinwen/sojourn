package com.ziroom.minsu.troy.common.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;

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
public class HttpUtil {
	
	private HttpUtil(){
		
	}

	/**
	 * 
	 * 获取HttpServletRequest
	 *
	 * @author liujun
	 * @created 2016-3-18 下午10:01:43
	 *
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	/**
	 * 
	 * 获取HttpSession
	 *
	 * @author liujun
	 * @created 2016-3-19 下午1:24:12
	 *
	 * @return
	 */
	public static HttpSession getSession(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
	}
	
	public static void main(String[] args) throws IOException {
		//服务查询登录用户信息
		Map<String, String> param=new HashMap<String, String>();
		param.put("userAccount", "busj");
		param.put("sysCode", "troy");
        Map<String,String> map = new HashMap<>();
        map.put("Accept","application/json");
		String resultJson=CloseableHttpUtil.sendFormPost("http://ups.t.ziroom.com/upsApi/upsUserMsg", param,map);
		System.err.println(resultJson);
	}
}
