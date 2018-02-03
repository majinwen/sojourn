/**
 * @FileName: OrderSortUpRequest.java
 * @Package com.ziroom.minsu.services.cms.dto
 * 
 * @author bushujie
 * @created 2017年1月6日 上午11:39:41
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.dto;

/**
 * <p>排序调整参数</p>
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
public class OrderSortUpRequest {
	
	/**
	 * 上移fid
	 */
	private String upFid;
	
	/**
	 * 下移fid
	 */
	private String downFid;
	
	/**
	 * 上移的排序号
	 */
	private Integer upSort;
	
	/**
	 * 下移的排序号
	 */
	private Integer downSort;

	/**
	 * @return the downSort
	 */
	public Integer getDownSort() {
		return downSort;
	}

	/**
	 * @param downSort the downSort to set
	 */
	public void setDownSort(Integer downSort) {
		this.downSort = downSort;
	}

	/**
	 * @return the upFid
	 */
	public String getUpFid() {
		return upFid;
	}

	/**
	 * @param upFid the upFid to set
	 */
	public void setUpFid(String upFid) {
		this.upFid = upFid;
	}

	/**
	 * @return the downFid
	 */
	public String getDownFid() {
		return downFid;
	}

	/**
	 * @param downFid the downFid to set
	 */
	public void setDownFid(String downFid) {
		this.downFid = downFid;
	}

	/**
	 * @return the upSort
	 */
	public Integer getUpSort() {
		return upSort;
	}

	/**
	 * @param upSort the upSort to set
	 */
	public void setUpSort(Integer upSort) {
		this.upSort = upSort;
	}
}
