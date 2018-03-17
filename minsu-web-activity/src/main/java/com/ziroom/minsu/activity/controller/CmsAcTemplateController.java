/**
 * @FileName: CmsAcTemplateController.java
 * @Package com.ziroom.minsu.activity.controller
 * 
 * @author yd
 * @created 2016年12月26日 下午5:39:25
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.activity.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.MD5Util;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.activity.common.utils.WxShareVo;
import com.ziroom.minsu.activity.constant.ActivityConstant;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;

/**
 * <p>cms活动模板生成</p>
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
@RequestMapping("/cmsAc")
@Controller
public class CmsAcTemplateController extends WxConmmonController{

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(CityFileController.class);

	@Value("#{'${AC_INTERTFACES_URL}'.trim()}")
	private String AC_INTERTFACES_URL;

	@Value("#{'${CREAT_HTML_URL}'.trim()}")
	private String creatHtmlUrl;

	/**
	 * 
	 * 房东故事
	 *
	 * @author yd
	 * @created 2016年12月27日 下午4:07:53
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("createAcStroy")
	public String createAcStroy(HttpServletRequest request,HttpServletResponse response){
		return gotoAcPage(request, response, "/cmsac/story");
	}

	/**
	 * 
	 * 焕心之旅
	 *
	 * @author yd
	 * @created 2016年12月27日 下午4:07:31
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("createAcHuanXin")
	public String createAcHuanXin(HttpServletRequest request,HttpServletResponse response){
		return gotoAcPage(request, response, "/cmsac/huanxin");

	}

	/**
	 * 
	 * 公用参数赋值
	 *
	 * @author yd
	 * @created 2016年12月27日 下午8:22:56
	 *
	 * @param request
	 */
	private  void  setCommonRequest(HttpServletRequest request){

		String id = request.getParameter("id");
		String shareFlag = request.getParameter("shareFlag");
		String from = request.getParameter("from");
		String isappinstalled = request.getParameter("isappinstalled");
		request.setAttribute("id", id);
		request.setAttribute("shareFlag", shareFlag);
		request.setAttribute("from", from);
		request.setAttribute("isappinstalled", isappinstalled);
	}

	/**
	 * 
	 * 去 相应活动页面
	 *
	 * @author yd
	 * @created 2016年12月27日 下午8:37:06
	 *
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 */
	private String  gotoAcPage(HttpServletRequest request,HttpServletResponse response,String page){

		String id = request.getParameter("id");
		setCommonRequest(request);
		try {
			StringBuilder url = new StringBuilder(this.AC_INTERTFACES_URL);

			Map<String, Object> params = new HashMap<String, Object>();
			Long timestamp= new Date().getTime();
			String sign = MD5Util.MD5Encode("1"+timestamp+"7srzT88FcNiRQA3n","UTF-8");

			url.append("&timestamp="+timestamp).append("&sign="+sign).append("&uid=1").append("&id="+id);
			this.wxShare(request);
			
			String cmsJson = CloseableHttpUtil.sendGet(url.toString(), null);

			if(!Check.NuNStrStrict(cmsJson)){
				cmsJson = cmsJson.replace("(", "").replace(")", "");
				
				if(!(cmsJson.indexOf("status")>0)){
					JSONArray jsonArray = new JSONArray(cmsJson);
					JSONObject obj= null;
					if(!Check.NuNObj(jsonArray)){
						obj=jsonArray.getJSONObject(0);
						params.clear();
						params =  obj.toMap();
						Object status = params.get("status");
						if(!Check.NuNObj(status)&&ActivityConstant.CMS_STATUS.equals(status.toString())){
							LogUtil.error(LOGGER, "cms获取模板失败,id={},cmsJson={}",id,cmsJson);
							return page;
						}

						String bodyHtml = String.valueOf(params.get("main_body"));
						String newsTitle = String.valueOf(params.get("news_title"));
						String newThe = String.valueOf(params.get("new_the"));
						request.setAttribute("bodyHtml", bodyHtml);
						request.setAttribute("newThe", newThe);
						request.setAttribute("newsTitle", newsTitle);
					}
				}else{
					LogUtil.error(LOGGER, "获取cms任务文章失败,cmsJson={}", cmsJson);
				}
				

			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "活动模板渲染失败,id={},e={}", id,e);
		}

		return page;
	}

}
