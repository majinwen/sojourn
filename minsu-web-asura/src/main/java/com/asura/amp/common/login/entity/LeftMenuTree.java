/**
 * @FileName: LeftMenuTree.java
 * @Package com.asura.amp.common.login.entity
 * 
 * @author zhangshaobin
 * @created 2013-1-16 下午5:20:41
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.common.login.entity;

import java.util.List;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>菜单树对象</p>
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
public class LeftMenuTree extends BaseEntity {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 菜单节点ID
	 */
	private Integer id;
	
	/**
	 * 工程ID
	 */
	private Integer projectid;
	
	/**
	 * 菜单显示名称
	 */
	private String name;
	
	/**
	 * 事件Action链接
	 */
	private String eventurl;
	
	/**
	 * 打开页面导航tabId
	 */
	private String pageid;
	
	/**
	 * 父节点ID
	 */
	private Integer parentid;
	
	/**
	 * 菜单样式
	 */
	private String style;
	
	/**
	 * 子菜单，null表示没有子菜单为叶子节点
	 */
	private List<LeftMenuTree> children;

	/**
	 * 获取菜单ID
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:15:33
	 *
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置菜单ID
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:15:45
	 *
	 * @param id	菜单ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取工程ID
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:15:55
	 *
	 * @return
	 */
	public Integer getProjectid() {
		return projectid;
	}

	/**
	 * 设置工程ID
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:16:26
	 *
	 * @param projectid		工程ID
	 */
	public void setProjectid(Integer projectid) {
		this.projectid = projectid;
	}

	/**
	 * 获取名称
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:16:43
	 *
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:17:01
	 *
	 * @param name		名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取菜单事件URL
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:17:15
	 *
	 * @return
	 */
	public String getEventurl() {
		return eventurl;
	}

	/**
	 * 获取菜单事件URL
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:17:37
	 *
	 * @param eventurl		事件URL
	 */
	public void setEventurl(String eventurl) {
		this.eventurl = eventurl;
	}

	/**
	 * 获取页面导航tabId
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:17:50
	 *
	 * @return
	 */
	public String getPageid() {
		return pageid;
	}

	/**
	 * 设置页面导航tabId
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:18:13
	 *
	 * @param pageid	页面导航tabId
	 */
	public void setPageid(String pageid) {
		this.pageid = pageid;
	}

	/**
	 * 获取父节点ID
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:18:35
	 *
	 * @return
	 */
	public Integer getParentid() {
		return parentid;
	}

	/**
	 * 设置父节点ID
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:18:44
	 *
	 * @param parentid		父节点ID
	 */
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	/**
	 * 获取菜单样式
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:18:55
	 *
	 * @return
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * 设置菜单样式
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:19:11
	 *
	 * @param style		菜单样式
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * 获取子菜单（null表示没有子菜单为叶子节点）
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:19:36
	 *
	 * @return
	 */
	public List<LeftMenuTree> getChildren() {
		return children;
	}

	/**
	 * 设置子菜单（null表示没有子菜单为叶子节点）
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:19:57
	 *
	 * @param children		子菜单
	 */
	public void setChildren(List<LeftMenuTree> children) {
		this.children = children;
	}
}
