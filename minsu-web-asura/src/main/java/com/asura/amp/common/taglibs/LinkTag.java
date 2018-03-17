/**
 * @FileName: PermissionTag.java
 * @Package com.asura.management.common.taglibs
 * 
 * @author zhangshaobin
 * @created 2013-1-31 上午11:47:56
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.common.taglibs;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.asura.amp.authority.entity.Resource;
import com.asura.amp.authority.logon.constant.LogonConstant;

/**
 * <p>
 * 超链接功能许可证， 主要用于超链接权限验证
 * </p>
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
public class LinkTag extends RequestContextAwareTag {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6642054653374636921L;

	/*
	 * 菜单id
	 */
	private String resId;

	/*
	 * 目标组件
	 */
	private String target;

	/*
	 * url后面参数
	 */
	private String param;

	/*
	 * 宽
	 */
	private String width;

	/*
	 * 高
	 */
	private String height;

	/*
	 * 样式, add 增加, delete 删除, edit修改
	 */
	private String typeClass;

	/*
	 * 引用
	 */
	private String rel;

	/*
	 * 显示信息
	 */
	private String title;

	/*
	 * 提示信息
	 */
	private String promptMessage;

	/*
	 * 警告信息
	 */
	private String warn;

	/*
	 * url， 添加菜单功能的时候必须写url, 这里用这个判断是不是有这个功能
	 */
	private String url;
	
	/**
	 * 
	 */
	private String callback;

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the warn
	 */
	public String getWarn() {
		return warn;
	}

	/**
	 * @param warn
	 *            the warn to set
	 */
	public void setWarn(String warn) {
		this.warn = warn;
	}

	/**
	 * @return the promptMessage
	 */
	public String getPromptMessage() {
		return promptMessage;
	}

	/**
	 * @param promptMessage
	 *            the promptMessage to set
	 */
	public void setPromptMessage(String promptMessage) {
		this.promptMessage = promptMessage;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the rel
	 */
	public String getRel() {
		return rel;
	}

	/**
	 * @param rel
	 *            the rel to set
	 */
	public void setRel(String rel) {
		this.rel = rel;
	}

	/**
	 * @return the resId
	 */
	public String getResId() {
		return resId;
	}

	/**
	 * @param resId
	 *            the resId to set
	 */
	public void setResId(String resId) {
		this.resId = resId;
	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target
	 *            the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return the param
	 */
	public String getParam() {
		return param;
	}

	/**
	 * @param param
	 *            the param to set
	 */
	public void setParam(String param) {
		this.param = param;
	}

	/**
	 * @return the width
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * @return the typeClass
	 */
	public String getTypeClass() {
		return typeClass;
	}

	/**
	 * @param typeClass
	 *            the typeClass to set
	 */
	public void setTypeClass(String typeClass) {
		this.typeClass = typeClass;
	}

	public LinkTag() {
		this.param = "";
	}

	@Override
	protected int doStartTagInternal() throws Exception {
		StringBuffer sb = new StringBuffer();
		HttpSession session = pageContext.getSession();
		// 所有的功能
		@SuppressWarnings("unchecked")
		List<Resource> funs = (List<Resource>) session.getAttribute(LogonConstant.SESSION_FUNS_RESOURCE);
		for (Resource res : funs) {
			//int parentId = res.getParentId();
//			if (parentId == Integer.parseInt(resId) && url.equals(res.getResUrl())) {
			if (url.equals(res.getResUrl())) {
				if (param.isEmpty()) {
					param = "?resId=" + resId;
					String href = res.getResUrl() + param;
					if (width == null || height == null) {
						if(callback == null){
							sb.append("<a title=\"" + title + "\" target=\"" + target + "\" rel=\"" + rel + "\" href=\"" + href + "\" class=\"" + typeClass + "\">");
						}else{
							sb.append("<a title=\"" + title + "\" target=\"" + target + "\" rel=\"" + rel + "\" callback=\"" + callback + "\" href=\"" + href + "\" class=\"" + typeClass + "\">");
						}
						
					} else {
						if(callback == null){
							sb.append("<a title=\"" + title + "\" target=\"" + target + "\" rel=\"" + rel + "\" href=\"" + href + "\" width=\"" + width + "\" height=\"" + height + "\" class=\""
									+ typeClass + "\">");
						}else{
							sb.append("<a title=\"" + title + "\" target=\"" + target + "\" rel=\"" + rel + "\" callback=\"" + callback + "\" href=\"" + href + "\" width=\"" + width + "\" height=\"" + height + "\" class=\""
									+ typeClass + "\">");
						}
						
					}
				} else {
					param = "?" + param + "&resId=" + resId;
					String href = res.getResUrl() + param;
					if (width == null || height == null) {
						if(callback == null){
							sb.append("<a title=\"" + title + "\" target=\"" + target + "\" rel=\"" + rel + "\" href=\"" + href + "\" class=\"" + typeClass + "\">");
						}else{
							sb.append("<a title=\"" + title + "\" target=\"" + target + "\" rel=\"" + rel + "\" callback=\"" + callback + "\" href=\"" + href + "\" class=\"" + typeClass + "\">");
						}
						
					} else {
						if(callback == null){
							sb.append("<a title=\"" + title + "\" target=\"" + target + "\" rel=\"" + rel + "\" href=\"" + href + "\" width=\"" + width + "\" height=\"" + height + "\" class=\""
									+ typeClass + "\">");
						}else{
							sb.append("<a title=\"" + title + "\" target=\"" + target + "\" rel=\"" + rel + "\" callback=\"" + callback + "\" href=\"" + href + "\" width=\"" + width + "\" height=\"" + height + "\" class=\""
									+ typeClass + "\">");
						}
						
					}
				}
				sb.append("<span>" + title + "</span>");
				sb.append("</a>");
				break;
			}
		}
		JspWriter out = pageContext.getOut();
		out.write(sb.toString());
		return 0;
	}

}
