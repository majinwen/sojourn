/**
 * @FileName: TreeNodeSimpleVo.java
 * @Package com.ziroom.minsu.services.basedata.entity
 * 
 * @author zl
 * @created 2017年6月15日 下午2:38:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.entity;

import java.util.List;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public class TreeNodeSimpleVo extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1896907343557013564L;

	/**
	 * 节点名称
	 */
	private String text;  

	/**
	 * 节点编码
	 */
	private String code;
	
	/**
	 * 节点层级
	 */
	private Integer level; 

	/**
	 * 子节点集合
	 */
	private List<TreeNodeSimpleVo> nodes;

	public String getText() {
		return text;
	}

	public String getCode() {
		return code;
	}

	public Integer getLevel() {
		return level;
	}

	public List<TreeNodeSimpleVo> getNodes() {
		return nodes;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public void setNodes(List<TreeNodeSimpleVo> nodes) {
		this.nodes = nodes;
	}
	
}
