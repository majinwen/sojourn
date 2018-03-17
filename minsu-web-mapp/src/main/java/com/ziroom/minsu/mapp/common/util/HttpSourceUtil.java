package com.ziroom.minsu.mapp.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.utils.LogUtil;
  
/**
 * 
 * <p>判断请求来源工具类</p>
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
public class HttpSourceUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpSourceUtil.class);
      
    // \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),    
    // 字符串在编译时会被转码一次,所以是 "\\b"    
    // \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)    
	private static final String PHONE_REG = new StringBuilder("\\b(ip(hone|od)|android|opera m(ob|in)i")
			.append("|windows (phone|ce)|blackberry").append("|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp")
			.append("|laystation portable)|nokia|fennec|htc[-_]")
			.append("|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b").toString();
	
	private static final String TABLE_REG = new StringBuilder("\\b(ipad|tablet|(Nexus 7)|up.browser")
			.append("|[1-4][0-9]{2}x[1-4][0-9]{2})\\b").toString();
      
    //移动设备正则匹配：手机端、平板  
	private static final Pattern PHONE_PAT = Pattern.compile(PHONE_REG, Pattern.CASE_INSENSITIVE);    
	private static final Pattern TABLE_PAT = Pattern.compile(TABLE_REG, Pattern.CASE_INSENSITIVE);    
        
	/**
	 * 
	 * 检测是否移动设备访问
	 *
	 * @author liujun
	 * @created 2016年6月20日
	 *
	 * @param userAgent 浏览器标识
	 * @return true:移动设备接入，false:pc端接入
	 */
    public static boolean check(String userAgent){    
        if(null == userAgent){    
            userAgent = "";    
        }    
        // 匹配    
        Matcher matcherPhone = PHONE_PAT.matcher(userAgent);    
        Matcher matcherTable = TABLE_PAT.matcher(userAgent);
        return matcherPhone.find() || matcherTable.find();
    }  
    
    /**
     * 
     * 检查访问方式是否为移动端
     *
     * @author liujun
     * @created 2016年6月20日
     *
     * @param request
     * @return true:移动设备接入，false:pc端接入
     */
	public static boolean check(HttpServletRequest request) {
		boolean isFromMobile = false;

		HttpSession session = request.getSession();
		// 检查是否已经记录访问方式（移动端或pc端）
		if (null == session.getAttribute("ua")) {
			// 获取ua，用来判断是否为移动端访问
			String userAgent = request.getHeader("USER-AGENT");
			if (null == userAgent) {
				userAgent = "";
			} else {
				userAgent = userAgent.toLowerCase();
			}
			isFromMobile = check(userAgent);
			// 判断是否为移动端访问
			if (isFromMobile) {
				session.setAttribute("ua", "mobile");
			} else {
				session.setAttribute("ua", "pc");
			}
			try {
			} catch (Exception e) {
				LogUtil.error(LOGGER, "HttpSourceUtil check invoke failed, error:{}", e);
			}
		} else {
			isFromMobile = session.getAttribute("ua").equals("mobile");
		}

		return isFromMobile;
	}
	
}  
