/**
 * @FileName: HuanxinImReVo.java
 * @Package com.ziroom.minsu.services.message.entity
 * 
 * @author yd
 * @created 2016年9月12日 上午10:29:13
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>环信 聊天记录 请求返回公用信息 实体</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class HuanxinImReVo extends BaseEntity {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 486802682526941752L;

	
	/**
	 * 请求方式
	 */
	private String action;
	
	/**
	 * 请求参数 json串格式
	 */
	private  String params;
	
	/**
	 * 请求路径
	 */
	private String path;
	
	/**
	 * 请求url
	 */
	private String uri;
	
	/**
	 * 请求当前时间戳
	 */
	private Long timestamp;
	
	/**
	 * 分页光标位置
	 */
	private int duration;
	
	/**
	 * 下一次 分页标识
	 */
	private String cursor;
	
	/**
	 * 返回记录条数
	 */
	private int count;

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the params
	 */
	public String getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(String params) {
		this.params = params;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @return the timestamp
	 */
	public Long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @return the cursor
	 */
	public String getCursor() {
		return cursor;
	}

	/**
	 * @param cursor the cursor to set
	 */
	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	
	
	
}
