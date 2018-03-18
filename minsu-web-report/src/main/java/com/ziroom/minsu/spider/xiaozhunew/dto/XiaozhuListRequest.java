/**
 * @FileName: XiaozhuListRequest.java
 * @Package com.ziroom.minsu.spider.xiaozhunew.dto
 * 
 * @author zl
 * @created 2016年10月14日 下午5:09:53
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.xiaozhunew.dto;

import java.util.List;


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
public class XiaozhuListRequest {
	
	/**
	 * 页码
	 */
	private Integer page;
	
	/**
	 * 城市缩写
	 */
	private String cityAbbreviation;
	
	/**
	 * 出租方式
	 */
	private String[] rentTypes;
	
	/**
	 * 宜居人数  1-10 |〉10
	 */
	private Integer guestnum;
	
	
	/**
	 * 区域
	 */
	private List<String> areas;
	
	
	
	/**
	 * 关键词
	 */
	private String putkey ;
	
	/**
	 * 开始时间
	 * yyyy-MM-dd
	 */
	private String startDate;
	
	/**
	 * 结束时间
	 * yyyy-MM-dd
	 */
	private String endDate;
	
	/**
	 * 最低价格
	 */
	private Integer minPrice;
	
	/**
	 * 最高价格
	 */
	private Integer maxPrice;
	
	
	
	
}
