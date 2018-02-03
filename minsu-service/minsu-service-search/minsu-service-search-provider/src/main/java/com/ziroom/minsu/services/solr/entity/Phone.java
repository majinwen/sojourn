/**
 * @Description:
 * @Author:chengxiang.huang
 * @Date:2016年3月25日 下午2:05:58
 * @Version: V1.0
 */
package com.ziroom.minsu.services.solr.entity;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

/**
 * 
 * @Description:
 * @Author:chengxiang.huang 2016年3月25日
 * @CreateTime: 2016年3月25日 下午2:05:58
 * @Version 1.0
 */
public class Phone implements Serializable{
	
	/**@Fileds serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	@Field("id")
	public String id;
	@Field("phone_name")
	public String name;
	@Field("phone_producer")
	public String producer;
	@Field("phone_color")
	public String color;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	/**
	 * @param id
	 * @param name
	 * @param producer
	 * @param color
	 */
	public Phone(String id, String name, String producer, String color) {
		super();
		this.id = id;
		this.name = name;
		this.producer = producer;
		this.color = color;
	}
	
}
