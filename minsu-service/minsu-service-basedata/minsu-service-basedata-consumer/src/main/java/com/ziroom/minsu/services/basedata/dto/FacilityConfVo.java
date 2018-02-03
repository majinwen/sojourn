/**
 * @FileName: FacilityConfVo.java
 * @Package com.ziroom.minsu.services.basedata.dto
 * 
 * @author bushujie
 * @created 2016年8月10日 下午5:19:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>设施</p>
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
public class FacilityConfVo {
	
	/**
	 * code
	 */
	private String code;
	/**
	 * 对应值
	 */
	private String value;
	
	/**
	 * 是否选中状态
	 */
	private Integer selected;

	/**
	 * 对应样式
	 */
	private String styleCss;
	
	/**
	 * 对应名称
	 */
	private String name;
	
	/**
	 * 子配置列表
	 */
	private List<FacilityConfVo> confList=new ArrayList<FacilityConfVo>();
	
	/**
	 * @return the confList
	 */
	public List<FacilityConfVo> getConfList() {
		return confList;
	}

	/**
	 * @param confList the confList to set
	 */
	public void setConfList(List<FacilityConfVo> confList) {
		this.confList = confList;
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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the selected
	 */
	public Integer getSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(Integer selected) {
		this.selected = selected;
	}

	/**
	 * @return the styleCss
	 */
	public String getStyleCss() {
		return styleCss;
	}

	/**
	 * @param styleCss the styleCss to set
	 */
	public void setStyleCss(String styleCss) {
		this.styleCss = styleCss;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
