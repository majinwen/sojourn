/**
 * @FileName: SearchResult.java
 * @Package com.asura.management.web.search.bean
 * 
 * @author zhangshaobin
 * @created 2012-12-25 下午3:43:48
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.dubbo.search.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>查询结果数据(搜索、机器IP、应用查询)</p>
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
public class SearchResult extends BaseEntity {

	/**
	 * 序列化UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述（角色/状态类型）
	 */
	private String description;

	/**
	 * 降级
	 */
	private String mock;

	/**
	 * 无参数构造函数
	 */
	public SearchResult() {
	}

	/**
	 * 有参构造函数
	 */
	public SearchResult(String name, String description) {
		this.name = name;
		this.description = description;
	}

	/**
	 * 获取名称
	 *
	 * @author zhangshaobin
	 * @created 2012-12-25 下午3:52:13
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
	 * @created 2012-12-25 下午3:52:43
	 *
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取描述（角色/状态类型）
	 *
	 * @author zhangshaobin
	 * @created 2012-12-25 下午3:52:50
	 *
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置描述（角色/状态类型）
	 *
	 * @author zhangshaobin
	 * @created 2012-12-25 下午3:53:08
	 *
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public String getMock() {
		return mock;
	}

	public void setMock(String mock) {
		this.mock = mock;
	}
}
