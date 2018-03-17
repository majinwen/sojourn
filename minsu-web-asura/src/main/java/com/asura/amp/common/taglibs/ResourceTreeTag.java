package com.asura.amp.common.taglibs;

import java.util.List;

import javax.servlet.jsp.JspWriter;

import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.asura.amp.authority.entity.Resource;
import com.asura.framework.base.util.JsonEntityTransform;
/**
 * <p>
 *     资源树标签, 主要用于资源管理和资源分配
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
public class ResourceTreeTag extends RequestContextAwareTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6364966903805594379L;
	/*
	 * 资源集合 
	 */
	private List<Resource> resources;
	
	/*
	 * 带有复选框的树
	 */
	private Boolean treeCheck; 
	
	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}
	
	public boolean isTreeCheck() {
		return treeCheck;
	}
	
	public void setTreeCheck(boolean treeCheck) {
		this.treeCheck = treeCheck;
	}

	/**
	 * 构造方法
	 */
	public ResourceTreeTag() {
		resources = null;
		treeCheck = null;
	}

	@Override
	protected int doStartTagInternal() throws Exception {
		String trees = "";
		if(resources != null && resources.size() != 0){
			if(treeCheck){// 复选框树
				trees = getTreeCheck(resources, 0);
			}else{
				trees = getTree(resources, 0);
			}
		}
		JspWriter out = pageContext.getOut();
		out.write(trees.replaceAll("<ul></ul>", ""));
		return 0;
	}
	
	/**
	 * 递归遍历资源信息，生成资源树
	 * @param resources 资源集合
	 * @param id 
	 * @return
	 */
	private String getTree(List<Resource> resources, int id){
		StringBuilder str = new StringBuilder();
        if(id == 0){
        	str.append("<ul class=\"tree treeFolder\">");
        	str.append("<li><a href='javascript:void(0);' id='tree0' tname='"+id+"' onclick='nodeClick(this);'>"+"资源信息");
            str.append("</a>");
            str.append("<ul>");
        }else{
        	str.append("<ul>");
        }
        for(Resource rs : resources){
        	Integer parentId = rs.getParentId();
        	if(parentId == id){
        		if(isLeaf(resources, rs.getResId())){
        			rs.setLeaf(true);
        		}else{
        			rs.setLeaf(false);
        		}
        		String json = JsonEntityTransform.Object2Json(rs);
                str.append("<li><a href='javascript:void(0);' id='"+json+"' onclick='nodeClick(this);'>"+rs.getResName());
                str.append("<input type='hidden' id='tree"+rs.getResId()+"' value='"+json.toString()+"'/>");
                str.append("</a>");
                str.append(getTree(resources, rs.getResId()));
                str.append("</li>");
        	}
        }
        str.append("</ul>");
        return str.toString();
	}
	
	/**
	 * 
	 * 生成带有复选框的树
	 *
	 * @author zhangshaobin
	 * @created 2013-1-27 上午11:47:38
	 *
	 * @param resources
	 * @param id
	 * @return
	 */
	private String getTreeCheck(List<Resource> resources, int id){
		StringBuilder str = new StringBuilder();
        if(id == 0){
        	str.append("<ul id='treecheckId' class=\"tree treeFolder treeCheck expand\">");
        }else{
        	str.append("<ul>");
        }
        for(Resource rs : resources){
        	Integer parentId = rs.getParentId();
        	if(parentId == id){
        		if(isLeaf(resources, rs.getResId())){
        			rs.setLeaf(true);
        		}else{
        			rs.setLeaf(false);
        		}
                str.append("<li><a href='javascript:void(0);' tname='"+rs.getResFunctionType()+"' tvalue='"+rs.getResId()+"'>"+rs.getResName());
                str.append("</a>");
                str.append(getTreeCheck(resources, rs.getResId()));
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