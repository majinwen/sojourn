package com.asura.amp.common.taglibs;

import java.util.List;

import javax.servlet.jsp.JspWriter;

import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.asura.amp.authority.entity.Resource;
/**
 * <p>
 *     菜单树标签，主要左边菜单使用
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
public class MenuTreeTag extends RequestContextAwareTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6364966903805594379L;
	/*
	 * 资源集合 
	 */
	private List<Resource> resources;
	
	/*
	 * 一级菜单的id
	 */
	private String pId;
	
	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}
	
	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	/**
	 * 构造方法
	 */
	public MenuTreeTag() {
		resources = null;
		pId = null;
	}

	@Override
	protected int doStartTagInternal() throws Exception {
		String trees = "";
		if(resources != null && resources.size() != 0){
			trees = getFirstTree(resources, Integer.parseInt(pId));
			if(!trees.isEmpty()){
				JspWriter out = pageContext.getOut();
				out.write(trees);
			}else{
				trees = getTree(resources, Integer.parseInt(pId));
				JspWriter out = pageContext.getOut();
				out.write(trees.replaceAll("<ul></ul>", ""));
			}
		}
		return 0;
	}
	
	/**
	 * 
	 * 特殊情况
	 *
	 * @author zhangshaobin
	 * @created 2013-1-29 下午10:36:59
	 *
	 * @return
	 */
	private String getFirstTree(List<Resource> resources, int id){
		String name = "";
		StringBuilder str = new StringBuilder();
		boolean flag = true;
		for(Resource rs : resources){
			if(rs.getParentId() == id){
				flag = false;
				break;
			}
			if(rs.getResId() == id){
				name = rs.getResName();
			}
		}
		if(flag){
			str.append("<ul class=\"tree treeFolder\">");
        	str.append("<li><a href=\"javascript:void(0);\" tname=\""+id+"\" tvalue=\""+id+"\">"+name);
            str.append("</a>");
			str.append("</ul>");
		}
		return str.toString();
	}
	
	/**
	 * 递归遍历资源信息，生成资源树
	 * @param resources 资源集合
	 * @param id 
	 * @return
	 */
	private String getTree(List<Resource> resources, int id){
		StringBuilder str = new StringBuilder();
        if(id == Integer.parseInt(pId)){
        	str.append("<ul class=\"tree treeFolder\">");
        }else{
        	str.append("<ul>");
        }
        for(Resource rs : resources){
        	Integer parentId = rs.getParentId();
        	String url = rs.getResUrl();
        	if(parentId == id){
        		if(isLeaf(resources, rs.getResId())){
        			if(url.contains("?")){
        				url = url+"&resId="+rs.getResId();
        			}else{
        				url = url+"?resId="+rs.getResId();
        			}
        			str.append("<li><a href=\"").append(url).append("\" target=\"navTab\" rel=\"page").append(rs.getResId()).append("\" tilte=\""+rs.getResName()+"\">"+rs.getResName());
        			
        			rs.setLeaf(true);
        		}else{
        			str.append("<li><a href=\"javascript:void(0);\" tilte=\""+rs.getResName()+"\">"+rs.getResName());
        			rs.setLeaf(false);
        		}
                str.append("</a>");
                str.append(getTree(resources, rs.getResId()));
                str.append("</li>");
        	}
        }
        str.append("</ul>");
        return str.toString();
	}
	
	/**
	 * 判断该节点是不是叶子
	 * @param resources 资源集合
	 * @param id 
	 * @return
	 */
	private boolean isLeaf(List<Resource> resources, int id){
		boolean leaf = true;
		for(Resource re : resources){
			if(id == re.getParentId()){
				leaf = false;
				break;
			}
		}
		return leaf;
	}
}