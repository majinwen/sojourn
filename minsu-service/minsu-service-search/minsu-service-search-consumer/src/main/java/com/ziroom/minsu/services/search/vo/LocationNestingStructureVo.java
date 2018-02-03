package com.ziroom.minsu.services.search.vo;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>
 * 20170810统一搜索位置嵌套数据结构VO
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangyl
 * @since 1.0
 * @version 1.0
 */
@JsonSerialize(include=Inclusion.NON_NULL) 
public class LocationNestingStructureVo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1286245324007854569L;

	/**
	 * 层级展示名称
	 */
	private String name;

	/**
	 * 当前元素的值
	 */
	private String value;

	/**
	 * 子元素参数名，子元素的值被获取到时以该名称提交
	 */
	private String key;

	/**
	 * 是否无需条件（不限），无条件时则去获取父元素 例如地铁线路下的所有地铁站列表中的“不限”,则获取地铁线路（父元素）的id即可 0-默认
	 * 1-无需条件
	 */
	private Integer unlimited;

	/**
	 * 子元素数据集合
	 */
	private List<LocationNestingStructureVo> data;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the unlimited
	 */
	public Integer getUnlimited() {
		return unlimited;
	}

	/**
	 * @param unlimited
	 *            the unlimited to set
	 */
	public void setUnlimited(Integer unlimited) {
		this.unlimited = unlimited;
	}

	/**
	 * @return the data
	 */
	public List<LocationNestingStructureVo> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(List<LocationNestingStructureVo> data) {
		this.data = data;
	}

}
