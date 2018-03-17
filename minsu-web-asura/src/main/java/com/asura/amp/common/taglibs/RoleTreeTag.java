package com.asura.amp.common.taglibs;

import java.util.List;

import javax.servlet.jsp.JspWriter;

import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.asura.amp.authority.entity.Role;
/**
 * <p>
 *      角色树标签, 主要用于分配角色信息
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
public class RoleTreeTag extends RequestContextAwareTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6364966903805594379L;
	/*
	 * 角色信息集合
	 */
	private List<Role> roles;
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	/**
	 * 构造方法
	 */
	public RoleTreeTag() {
		roles = null;
	}

	@Override
	protected int doStartTagInternal() throws Exception {
		String trees = "";
		if(roles != null && roles.size() != 0){
			trees = getTreeCheck(roles, 0);
		}
		JspWriter out = pageContext.getOut();
		out.write(trees);
		return 0;
	}
	
	
	/**
	 * 
	 * 生成带有复选框的树(角色树只有一个父节点)
	 *
	 * @author zhangshaobin
	 * @created 2013-1-27 上午11:47:38
	 *
	 * @param roles 角色信息集合
	 * @param id 父节点
	 * @return
	 */
	private String getTreeCheck(List<Role> roles, int id){
		StringBuilder str = new StringBuilder();
        if(id == 0){
        	str.append("<ul id='roleTreeCheckId' class=\"tree treeFolder treeCheck expand\">");
        	str.append("<li><a href='javascript:void(0);' tname='"+id+"' tvalue='"+id+"'>"+"角色信息");
            str.append("</a>");
            str.append("<ul>");
        }
        for(Role role : roles){
            str.append("<li><a href='javascript:void(0);' tname='"+role.getRoleName()+"' tvalue='"+role.getRoleId()+"'>"+role.getRoleName());
            str.append("</a>");
            str.append("</li>");
        }
        str.append("</ul>");
        str.append("</li>");
        str.append("</ul>");
        return str.toString();
	}
}