/**
 * @FileName: TreeTag.java
 * @Package com.asura.management.common.taglibs
 * 
 * @author zhangshaobin
 * @created 2013-1-16 下午5:20:41
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.common.taglibs;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspWriter;

import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.asura.amp.common.login.entity.LeftMenuTree;

/**
 * <p>
 * 		树形菜单处理标签
 * 		1. 一级菜单为导航栏工程菜单，parentId为0作为标记。
 * 		2. 工程菜单下二级菜单必须为“文件夹”,不可以为链接，即链接至少为三级菜单
 * 		3. 有子级菜单即表示当前菜单为文件夹不为链接、链接不能有子菜单。
 * 		4. 没有子级菜单并且菜单事件链接不为null，即表示链接菜单
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
public class TreeTag extends RequestContextAwareTag {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 树形列表数据
	 */
	private List<LeftMenuTree> trees;
	
	/**
	 * 默认打开连接的导航tabId
	 */
	private String defaultPage;
	
	/**
	 * 构造函数
	 */
	public TreeTag() {
		trees = null;
		defaultPage = null;
	}

	@Override
	protected int doStartTagInternal() throws Exception {
		try {
			StringBuilder str = new StringBuilder();

			JspWriter out = pageContext.getOut();

			if (trees != null) {
				for (LeftMenuTree top_tree : trees) {
					str.append("<div class=\"accordionHeader\"><h2><span>Folder</span>").append(top_tree.getName()).append("</h2></div>");
					str.append("<div class=\"accordionContent\"><ul class=\"tree treeFolder\">");
					str.append(getChildItems(top_tree));
					str.append("</ul></div>");
				}
			}
			
			out.write(str.toString());
		} catch (IOException ex) {
		}

		return 0;
	}
	
	/**
	 * 递归输出子菜单
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:39:58
	 *
	 * @param itemTree		当前菜单对象
	 * @return
	 */
	private StringBuffer getChildItems(LeftMenuTree itemTree) {
		StringBuffer sb = new StringBuffer();
		List<LeftMenuTree> children = itemTree.getChildren();
		
		if(children == null) return sb;
		
		for (LeftMenuTree child : children) {
			String name = child.getName();
			String pageid = child.getPageid();
			String eventUrl = child.getEventurl();
			
			if(child.getChildren() != null || eventUrl == null) {
				sb.append("<li><a href=\"javascript:void(0)\">").append(name).append("</a><ul>");
				sb.append(getChildItems(child));
				sb.append("</ul></li>");
			} else {
				String page = StringUtils.isNotEmpty(eventUrl) ? pageid : defaultPage;

				sb.append("<li><a href=\"").append(child.getEventurl());
				sb.append("\" target=\"navTab\" rel=\"").append(page).append("\" title=\"").append(name).append("\">");
				sb.append(name).append("</a></li>");
			}
		}

		return sb;
	}

	/**
	 * 获取树形列表数据
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:40:36
	 *
	 * @return
	 */
	public List<LeftMenuTree> getTrees() {
		return trees;
	}

	/**
	 * 设置树形列表数据
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:40:54
	 *
	 * @param trees				树形列表数据
	 */
	public void setTrees(List<LeftMenuTree> trees) {
		this.trees = trees;
	}

	/**
	 * 获取连接的导航tabId
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:41:05
	 *
	 * @return
	 */
	public String getDefaultPage() {
		return defaultPage;
	}

	/**
	 * 设置连接的导航tabId
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:41:08
	 *
	 * @param defaultPage		导航tabId
	 */
	public void setDefaultPage(String defaultPage) {
		this.defaultPage = defaultPage;
	}
}