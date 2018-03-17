package com.asura.amp.common.taglibs;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspWriter;

import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.asura.framework.monitor.entity.ApplicationNode;
/**
 * 
 * <p>服务信息标签</p>
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
public class ApplicationNodesTag extends RequestContextAwareTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6364966903805594379L;
	
	/**
	 * 服务信息
	 */
	private Map<String,List<ApplicationNode>> applicationNodes;
	
	/**
	 * 点击节点跳转的url
	 */
	private String url;
	
	/**
	 * 引用
	 */
	private String rel;
	
	

	/**
	 * @return the rel
	 */
	public String getRel() {
		return rel;
	}

	/**
	 * @param rel the rel to set
	 */
	public void setRel(String rel) {
		this.rel = rel;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the applicationNodes
	 */
	public Map<String, List<ApplicationNode>> getApplicationNodes() {
		return applicationNodes;
	}

	/**
	 * @param applicationNodes the applicationNodes to set
	 */
	public void setApplicationNodes(
			Map<String, List<ApplicationNode>> applicationNodes) {
		this.applicationNodes = applicationNodes;
	}

	/**
	 * 构造方法
	 */
	public ApplicationNodesTag() {
		applicationNodes = null;
	}

	@Override
	protected int doStartTagInternal() throws Exception {
		String trees = "";
		if(applicationNodes != null && applicationNodes.size() != 0){
			trees = getTree(applicationNodes, 0);
		}
		JspWriter out = pageContext.getOut();
		out.write(trees);
		return 0;
	}
	
	
	/**
	 * 
	 * 根据服务信息生成树
	 *
	 * @author zhangshaobin
	 * @created 2013-2-19 下午4:05:58
	 *
	 * @param applicationNodes
	 * @param id
	 * @return
	 */
	private String getTree(Map<String,List<ApplicationNode>> applicationNodes, int id){
		StringBuilder str = new StringBuilder();
        if(id == 0){
        	str.append("<ul id='roleTreeCheckId' class=\"tree treeFolder expand\">");
        	str.append("<li><a href='javascript:void(0);' tname='"+id+"' tvalue='"+id+"'>"+"服务信息");
            str.append("</a>");
            str.append("<ul>");
        }
        for(Entry<String,List<ApplicationNode>> item : applicationNodes.entrySet()){
        	str.append("<li><a href='javascript:void(0);' tname='"+id+"' tvalue='"+id+"'>"+item.getKey());
            str.append("</a>");
            str.append("<ul>");
            for(ApplicationNode node : item.getValue()){
            	str.append("<li><a href='"+url+"?ip="+node.getIp()+"' target='ajax' rel='"+rel+"'>"+node.getIp());
                str.append("</a>");
                str.append("</li>");
            }
            str.append("</ul>");
            str.append("</li>");
        }
        str.append("</ul>");
        str.append("</li>");
        str.append("</ul>");
        return str.toString();
	}
}