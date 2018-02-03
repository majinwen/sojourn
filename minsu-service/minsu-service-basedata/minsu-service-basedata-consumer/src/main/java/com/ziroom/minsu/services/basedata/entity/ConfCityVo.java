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

import java.util.List;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>国家、省份、城市、区域 树结构vo</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */
public class ConfCityVo extends BaseEntity {
	
	
	/**
	 * 序列化 id
	 */
	private static final long serialVersionUID = 3037228726236368865L;
	
	
	/**
	 * 节点id
	 */
	private String id;
	
	/**
	 * 节点名称
	 */
	private String showName;
	
	/**
	 * 节点层级
	 */
	private Integer level;
	
	/**
	 * 节点编码
	 */
	private String code;
	
	/**
	 * 子节点集合
	 */
	private List<ConfCityVo> nodes;
	
	
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
	 * @return the showName
	 */
	public String getShowName() {
		return showName;
	}

	/**
	 * @param showName the showName to set
	 */
	public void setShowName(String showName) {
		this.showName = showName;
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

	/**
	 * @return the nodes
	 */
	public List<ConfCityVo> getNodes() {
		return nodes;
	}

	/**
	 * @param nodes the nodes to set
	 */
	public void setNodes(List<ConfCityVo> nodes) {
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
	
	
	
}
