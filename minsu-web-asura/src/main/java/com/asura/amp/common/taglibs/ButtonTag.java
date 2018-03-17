/**
 * @FileName: PermissionTag.java
 * @Package com.asura.amp.common.taglibs
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
 * 按钮功能许可证, 主要用于按钮权限验证
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
public class ButtonTag extends RequestContextAwareTag {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6642054653374636921L;

	/*
	 * button id
	 */
	private String id;

	/*
	 * 菜单id
	 */
	private String resId;

	/*
	 * 显示信息
	 */
	private String title;

	/*
	 * url， 添加菜单功能的时候必须写url, 这里用这个判断是不是有这个功能
	 */
	private String url;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public ButtonTag() {
	}

	@Override
	protected int doStartTagInternal() throws Exception {
		StringBuffer sb = new StringBuffer();
		HttpSession session = pageContext.getSession();
		// 所有的功能
		@SuppressWarnings("unchecked")
		List<Resource> funs = (List<Resource>) session.getAttribute(LogonConstant.SESSION_FUNS_RESOURCE);
		for (Resource res : funs) {
//			int parentId = res.getParentId();
//			if (parentId == Integer.parseInt(resId) && url.equals(res.getResUrl())) {
			if (url.equals(res.getResUrl())) {
				sb.append("<div class='buttonActive'>");
				sb.append("<div class='buttonContent'>");
				sb.append("<button type=\"submit\" ");
				if(id!=null && !id.isEmpty()) sb.append(" id=\"").append(id).append("\"");
				sb.append(">").append(title).append("</button>");
				sb.append("</div>");
				sb.append("</div>");
				break;
			}
		}
		JspWriter out = pageContext.getOut();
		out.write(sb.toString());
		return 0;
	}

}
