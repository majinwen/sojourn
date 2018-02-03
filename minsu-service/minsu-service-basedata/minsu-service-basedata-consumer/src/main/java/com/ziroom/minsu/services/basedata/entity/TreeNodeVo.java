/**
 * @FileName: TreeNodeVo.java
 * @Package com.ziroom.minsu.services.basedata.entity
 * 
 * @author bushujie
 * @created 2016年3月13日 下午6:40:42
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>树结构vo</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class TreeNodeVo extends BaseEntity {
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -6706136972949070599L;
	/**
	 * 节点名称
	 */
	private String text;
	/**
	 * 节点链接
	 */
	private String href;
	/**
	 * 节点id
	 */
	private String id;

	/**
	 * 节点类型
	 */
	private Integer nodeType;

	/**
	 * 节点编码
	 */
	private String code;
	
	/**
	 * 节点层级
	 */
	private Integer level;
	
	/**
	 * 节点状态集合
	 */
	private Map<String, Object> state = new HashMap<String, Object>();

	/**
	 * 子节点集合
	 */
	private List<TreeNodeVo> nodes;

	public Integer getNodeType() {
		return nodeType;
	}

	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the href
	 */
	public String getHref() {
		return href;
	}
	/**
	 * @param href the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the state
	 */
	public Map<String, Object> getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(Map<String, Object> state) {
		this.state = state;
	}
	/**
	 * @return the nodes
	 */
	public List<TreeNodeVo> getNodes() {
		return nodes;
	}
	/**
	 * @param nodes the nodes to set
	 */
	public void setNodes(List<TreeNodeVo> nodes) {
		this.nodes = nodes;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the level
	 */
	public Integer getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	
}
