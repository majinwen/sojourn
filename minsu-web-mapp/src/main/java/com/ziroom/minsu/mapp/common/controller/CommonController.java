
package com.ziroom.minsu.mapp.common.controller;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.valenum.common.JumpOpenAppEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;


/**
 * <p>公用控制层</p>
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
@RequestMapping("common")
@Controller
public class CommonController {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	
	/**
	 * 
	 * 到全局错误页
	 *
	 * @author yd
	 * @created 2016年5月31日 下午11:04:18
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/globalEror")
	public String globalEror(HttpServletRequest request){
		return "/error/error";
	}



	@RequestMapping("${NO_LOGIN_AUTH}/goToApp")
	public String goToApp(HttpServletRequest request){
		String paramJson = request.getParameter("param");
		LogUtil.info(logger,"参数param={}",paramJson);
		JSONObject object = JSONObject.parseObject(paramJson);
		Integer jumpType = object.getInteger("jumpType");
		String toApp = null;//不需要区分的
		String toIosApp = null;
		String toAndroidApp = null;
		if (jumpType.intValue() == JumpOpenAppEnum.EVALUATE_INFO.getCode()){
			toApp =  "appminsu://ziroom.app/openeva?orderSn=%s&userType=%s";
			String orderSn = object.getString("orderSn");
			String type = object.getString("type");
			toApp = String.format(toApp,orderSn,type);
		}else if(jumpType.intValue() == JumpOpenAppEnum.MINSU_HOME_PAGE.getCode()){
			toApp =  "ziroom://ziroom.app/openApp?p=cHJvZHVjdD1taW5zdSZmdW5jdGlvbj10b190ZW5hbnRfaG9tZQ%3d%3d";
		}else if(jumpType.intValue() == JumpOpenAppEnum.TEN_ORDER_INFO.getCode()){
			toIosApp =  "appminsu://ziroom.app/openmsorder?orderSn=%s&userType=%s";
			String orderSn = object.getString("orderSn");
			String type = object.getString("type");
			toIosApp = String.format(toIosApp,orderSn,type);
			toAndroidApp =  "appminsu://ziroom.app/openmsordertenant?orderSn=%s&userType=%s";
			toAndroidApp = String.format(toAndroidApp,orderSn,type);
		}else if(jumpType.intValue() == JumpOpenAppEnum.LAND_ORDER_INFO.getCode()){
			toIosApp =  "appminsu://ziroom.app/openmsorder?orderSn=%s&userType=%s";
			String orderSn = object.getString("orderSn");
			String type = object.getString("type");
			toIosApp = String.format(toIosApp,orderSn,type);
			toAndroidApp =  "appminsu://ziroom.app/openmsorderlandlord?orderSn=%s&userType=%s";
			toAndroidApp = String.format(toAndroidApp,orderSn,type);
		}else if(jumpType.intValue() == JumpOpenAppEnum.LAND_HOUSE_LIST.getCode()){
			toApp =  "ziroom://ziroom.app/openApp?p=cHJvZHVjdD1taW5zdSZmdW5jdGlvbj10b19sbF9ob3VzZV9saXN0";
		}else if(jumpType.intValue() == JumpOpenAppEnum.PERSONAL_CENTER.getCode()){
			toApp =  "ziroom://ziroom.app/openApp?p=cHJvZHVjdD1jb21tb20mZnVuY3Rpb249dG9fbXl6aXJvb20%3d";
		}
		request.setAttribute("toApp",toApp);
		request.setAttribute("jumpType",jumpType);
		request.setAttribute("toIosApp",toIosApp);
		request.setAttribute("toAndroidApp",toAndroidApp);
		LogUtil.info(logger,"toApp={}",toApp);
		LogUtil.info(logger,"toIosApp={}",toIosApp);
		LogUtil.info(logger,"toAndroidApp={}",toAndroidApp);
		return "/common/goToApp";
	}
	
	public static void main(String[] args) {
		/*
		 * http://wiki.ziroom.com/pages/viewpage.action?pageId=167477251 这是app端提供的，短链调起app的文档，一定看一下
		 */
		    String unEncodeUrl = "product=minsu&function=to_ll_house_list";
		    String toAppUrl= "ziroom://ziroom.app/openApp?p=";
		    try {
		    	//String encodeBase64 = Base64.getEncoder().encodeToString(unEncodeUrl.getBytes("utf-8"));
		    	String encodeBase64 = Base64.getEncoder().encodeToString(unEncodeUrl.getBytes());
				String param = URLEncoder.encode(encodeBase64,"utf-8");
				toAppUrl = toAppUrl + param;
				System.out.println(toAppUrl);
				//
				String decode = URLDecoder.decode(param, "utf-8");
				byte[] decode2 = Base64.getDecoder().decode(decode);
		    	System.out.println(new String(decode2));
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
}
