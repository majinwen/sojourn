/**
 * @FileName: DateTag.java
 * @Package com.asura.management.common.taglibs
 * 
 * @author zhangshaobin
 * @created 2013-1-16 下午5:20:41
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.common.taglibs;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.jsp.JspWriter;

import org.springframework.web.servlet.tags.RequestContextAwareTag;

/**
 * <p>日期处理标签</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public class SfExploreTag extends RequestContextAwareTag {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 数据  第一分组
	 */
	private List<Object> dataFgroup;
	
	/**
	 * 数据  第二分组
	 */
	private List<Object> dataSgroup;
	
	/**
	 * 可用状态显示图片
	 */
	private String enableIcon;
	
	/**
	 * 不可用状态显示图片
	 */
	private String disableIcon;
	
	private String title;
	
	private String status;
	
	private String params;
	
	private String url;
	
	private String fgroupName;
	
	private String sgroupName;
	
	/**
	 * 默认构造函数
	 */
	public SfExploreTag() {
		dataFgroup = null;
		enableIcon = null;
		disableIcon = null;
		title = null;
		status= null;
		params = null;
	}
	
	@Override
	protected int doStartTagInternal() throws Exception {
		try {
			StringBuilder str = new StringBuilder();

			// 输出Writer
			JspWriter out = pageContext.getOut();
			
			String sfexplore = "sfexplore" +  System.currentTimeMillis();
			
			// 添加CSS
			str.append("<style type='text/css'>");
			// str.append("#" + sfexplore + " { border:1px solid #F00;}");
			str.append("#" + sfexplore + " .sfexplore-state { width: 170px; overflow: hidden; padding-bottom: 2px; float: left; margin: 0 3px 12px 0; }");
			str.append("#" + sfexplore + " .sfexplore-state-icon { height: 52px; width: 52px; margin: 1px auto; padding: 2px; position: relative; border-radius: 4px; }");
			str.append("#" + sfexplore + " .sfexplore-state-icon-image { height: 28px; width: 28px; display: block; margin: 0 auto; }");
			str.append("#" + sfexplore + " .sfexplore-state-icon-enable { background: url('" + enableIcon + "') no-repeat scroll padding-box transparent; }");
			str.append("#" + sfexplore + " .sfexplore-state-icon-disable { background: url('" + disableIcon + "') no-repeat scroll padding-box transparent; }");
			str.append("#" + sfexplore + " .sfexplore-state-icon-hover { background: none repeat scroll 0 0 #CCC; }");
			str.append("#" + sfexplore + " .sfexplore-state-name { border-radius: 8px; margin: 3px 1px 0; overflow: hidden; padding: 1px; text-align: center; text-overflow: ellipsis; white-space: pre;}");
			str.append("#" + sfexplore + " .sfexplore-state-name span { display:block; padding:1px; }");
			str.append("#" + sfexplore + " .sfexplore-state-name-hover { background: none repeat scroll 0 0 #3875D7; color: #FFF; }");
			str.append("</style>");
			
			// 添加JS
			str.append("<script type='text/javascript'>");
			str.append("$(function() {");
			str.append("function removeHover() { $('div.sfexplore-state-icon-hover', '#" + sfexplore + "').removeClass('sfexplore-state-icon-hover'); $('div.sfexplore-state-name-hover', '#" + sfexplore + "').removeClass('sfexplore-state-name-hover');}");
			
			str.append("$('.sfexplore-state', '#" + sfexplore + "').click(function() { removeHover(); $('.sfexplore-state-icon', this).addClass('sfexplore-state-icon-hover'); $('.sfexplore-state-name', this).addClass('sfexplore-state-name-hover'); return false;");
			str.append("}).dblclick(function() { var sf_params = $(this).attr('params'); $.pdialog.open('" + url + "?' + sf_params, 'd_" + sfexplore + "', '对话框', {width:430,height:400}); });");
			
			str.append("$('body').click(function() {removeHover();});");
			str.append("});");
			str.append("</script>");
			
			// 添加HTML
			str.append("<div id='" + sfexplore + "' class='pageContent'>");
			str.append("<fieldset>");
			str.append("<legend>"+fgroupName+"</legend>");
			for(Object exp : dataFgroup) {
				str.append("<div class='sfexplore-state' params='" + getExploreParams(exp) + "'>");
				str.append("	<div class='sfexplore-state-icon'>");
				if(getStatusEnable(exp)) {
					str.append("	<div class='sfexplore-state-icon-image sfexplore-state-icon-enable'></div>");
				} else {
					str.append("	<div class='sfexplore-state-icon-image sfexplore-state-icon-disable'></div>");
				}
				str.append("	</div>");
				str.append("	<div class='sfexplore-state-name' title='" + getExploreTitle(exp) + "'>" + getExploreSpanTitle(exp) + "</div>");
				str.append("</div>");
			}
			str.append("</fieldset>");
			str.append("<fieldset>");
			str.append("<legend>"+sgroupName+"</legend>");
			for(Object exp : dataSgroup) {
				str.append("<div class='sfexplore-state' params='" + getExploreParams(exp) + "'>");
				str.append("	<div class='sfexplore-state-icon'>");
				if(getStatusEnable(exp)) {
					str.append("	<div class='sfexplore-state-icon-image sfexplore-state-icon-enable'></div>");
				} else {
					str.append("	<div class='sfexplore-state-icon-image sfexplore-state-icon-disable'></div>");
				}
				str.append("	</div>");
				str.append("	<div class='sfexplore-state-name' title='" + getExploreTitle(exp) + "'>" + getExploreSpanTitle(exp) + "</div>");
				str.append("</div>");
			}
			str.append("</fieldset>");
			str.append("</div>");
			// 输出
			out.write(str.toString());
		} catch (IOException ex) {
		}

		return 0;
	}
	
	private String getValueByInvoke(Object obj, String property) {
		Method getter = null;
		String upperProperty = property.substring(0, 1).toUpperCase() + property.substring(1);
		try {
			getter = obj.getClass().getMethod("get" + upperProperty, new Class<?>[0]);
		} catch (NoSuchMethodException e) {
			try {
				getter = obj.getClass().getMethod("is" + upperProperty, new Class<?>[0]);
			} catch (NoSuchMethodException e2) {
				getter = null;
			}
		}

		if (getter != null) {
			Object value = null;
			try {
				value = getter.invoke(obj, new Object[0]);
			} catch (Exception  e) {
			}
			return String.valueOf(value);
		}
		
		return "";
	}
	
	private String urlEncode(String input) {
		String output = "";

		try {
			output = URLEncoder.encode(input, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}

		return output;
	}
	
	private String getExploreTitle(Object value) {
		StringBuilder sb = new StringBuilder();
		for(String property : title.split(";")) {
			if(sb.length() > 0) {
				sb.append(" ");
			}
			sb.append(getValueByInvoke(value, property));
		}
		
		return sb.toString();
	}
	
	private String getExploreSpanTitle(Object value) {
		StringBuilder sb = new StringBuilder();
		for(String property : title.split(";")) {
			sb.append("<span>" + getValueByInvoke(value, property) + "</span>");
		}
		
		return sb.toString();
	}
	
	private String getExploreParams(Object value) {
		StringBuilder sb = new StringBuilder();
		for(String property : params.split(";")) {
			if(sb.length() > 0) {
				sb.append("&");
			}
			sb.append(property + "=" + urlEncode(getValueByInvoke(value, property)));
		}
		
		return sb.toString();
	}
	
	private boolean getStatusEnable(Object value) {
		return "true".equals(getValueByInvoke(value, status));
	}

	public String getEnableIcon() {
		return enableIcon;
	}

	public void setEnableIcon(String enableIcon) {
		this.enableIcon = enableIcon;
	}

	public String getDisableIcon() {
		return disableIcon;
	}

	public void setDisableIcon(String disableIcon) {
		this.disableIcon = disableIcon;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the dataFgroup
	 */
	public List<Object> getDataFgroup() {
		return dataFgroup;
	}

	/**
	 * @param dataFgroup the dataFgroup to set
	 */
	public void setDataFgroup(List<Object> dataFgroup) {
		this.dataFgroup = dataFgroup;
	}

	/**
	 * @return the dataSgroup
	 */
	public List<Object> getDataSgroup() {
		return dataSgroup;
	}

	/**
	 * @param dataSgroup the dataSgroup to set
	 */
	public void setDataSgroup(List<Object> dataSgroup) {
		this.dataSgroup = dataSgroup;
	}

	/**
	 * @return the fgroupName
	 */
	public String getFgroupName() {
		return fgroupName;
	}

	/**
	 * @param fgroupName the fgroupName to set
	 */
	public void setFgroupName(String fgroupName) {
		this.fgroupName = fgroupName;
	}

	/**
	 * @return the sgroupName
	 */
	public String getSgroupName() {
		return sgroupName;
	}

	/**
	 * @param sgroupName the sgroupName to set
	 */
	public void setSgroupName(String sgroupName) {
		this.sgroupName = sgroupName;
	}
	
	
}