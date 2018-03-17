/**
 * @FileName: PagerTag.java
 * @Package com.asura.management.common.taglibs
 * 
 * @author zhangshaobin
 * @created 2013-1-16 下午5:20:41
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.common.taglibs;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import org.springframework.web.servlet.tags.RequestContextAwareTag;

/**
 * <p>分页处理标签</p>
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
public class PagerTag extends RequestContextAwareTag {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 关联divId
	 */
	private String rel;
	
	/**
	 * 查询结果总件数
	 */
	private Integer totalCount;
	
	/**
	 * 每页显示件数
	 */
	private Integer numPerPage;
	
	/**
	 * 分页显示，每次显示个数
	 */
	private Integer pageNumShown = 5;
	
	/**
	 * 默认当前页数
	 */
	private Integer currentPage = 1;
	
	/**
	 * 默认翻页形式
	 */
	private String targetType = "navTab";
	/**
	 * 默认翻页显示条数组件函数名
	 */
	private String funName = "navTabPageBreak";
	
	/**
	 * 初始化构造函数
	 */
	public PagerTag() {
		rel = null;
		totalCount = 0;
		numPerPage = 0;
		pageNumShown = 5;
		currentPage = 1;
	}

	@Override
	protected int doStartTagInternal() throws Exception {
		try {
			StringBuilder str = new StringBuilder("<div class=\"panelBar\">");

			// 输出Writer
			JspWriter out = pageContext.getOut();
			if(targetType.equals("dialog")){
				funName="dialogPageBreak";
			}
			str.append("<div class=\"pages\"><span>显示</span>");
			str.append("<select class=\"combox\" name=\"numPerPage\" onchange=\""+funName+"({numPerPage:this.value}, '").append(rel).append("')\">");
			str.append("<option value=\"20\" " + (numPerPage == 20? "selected=\"selected\"" : "") + ">20</option>");
			str.append("<option value=\"50\" " + (numPerPage == 50? "selected=\"selected\"" : "") + ">50</option>");
			str.append("<option value=\"100\" " + (numPerPage == 100? "selected=\"selected\"" : "") + ">100</option>");
			str.append("<option value=\"200\" " + (numPerPage == 200? "selected=\"selected\"" : "") + ">200</option>");
			str.append("</select>");
			str.append("<span>条，共").append(totalCount).append("条</span>");
			str.append("</div>");
			
			str.append("<div class=\"pagination\" rel=\"").append(rel)
				.append("\" targettype=\"").append(targetType)
				.append("\" totalCount=\"").append(totalCount)
				.append("\" numPerPage=\"").append(numPerPage)
				.append("\" pageNumShown=\"").append(pageNumShown)
				.append("\" currentPage=\"").append(currentPage)
				.append("\"></div>");
			str.append("</div>");
			
			// 输出
			out.write(str.toString());
		} catch (IOException ex) {
		}
		
		return 0;
	}

	/**
	 * 获取关联divId
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:35:32
	 *
	 * @return
	 */
	public String getRel() {
		return rel;
	}

	/**
	 * 设置关联divId
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:35:39
	 *
	 * @param rel				关联divId
	 */
	public void setRel(String rel) {
		this.rel = rel;
	}

	/**
	 * 获取查询结果总件数
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:35:57
	 *
	 * @return
	 */
	public Integer getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置查询结果总件数
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:36:06
	 *
	 * @param totalCount		结果总件数
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 获取每页显示件数
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:36:19
	 *
	 * @return
	 */
	public Integer getNumPerPage() {
		return numPerPage;
	}

	/**
	 * 设置每页显示件数
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:36:28
	 *
	 * @param numPerPage		每页显示件数
	 */
	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}

	/**
	 * 获取分页显示个数
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:36:54
	 *
	 * @return
	 */
	public Integer getPageNumShown() {
		return pageNumShown;
	}

	/**
	 * 设置分页显示个数
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:37:10
	 *
	 * @param pageNumShown		分页显示个数
	 */
	public void setPageNumShown(Integer pageNumShown) {
		this.pageNumShown = pageNumShown;
	}

	/**
	 * 获取当前页号
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:37:27
	 *
	 * @return
	 */
	public Integer getCurrentPage() {
		return currentPage;
	}

	/**
	 * 设置当前页号
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:37:40
	 *
	 * @param currentPage		当前页号
	 */
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	/**
	 * 获取分页目标对象类型
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:35:32
	 *
	 * @return
	 */
	public String getTargetType() {
		return targetType;
	}

	/**
	 * 设置分页目标对象类型
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:35:39
	 *
	 * @param rel				关联divId
	 */
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
}