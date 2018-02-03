/**
 * @Description:
 * @Author:chengxiang.huang
 * @Date:2016年3月23日 下午2:58:34
 * @Version: V1.0
 */
package com.ziroom.minsu.services.solr.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.solr.client.solrj.beans.Field;

/**
 * 
 * @Description:
 * @Author:chengxiang.huang 2016年3月23日
 * @CreateTime: 2016年3月23日 下午2:58:34
 * @Version 1.0
 */
public class ZiRoom implements Serializable{

	/**@Fileds serialVersionUID
	 */
	private static final long serialVersionUID = -2146500387249180232L;
	
	@Field
	private String id;
	
	@Field("ziroom_name")
	private String name;
	
	@Field("ziroom_desc")
	private String desc;
	
	@Field("ziroom_address")
	private String address;
	
	@Field("ziroom_price")
    private float price;
	
	@Field("ziroom_lat")
    private float lat;
	
	@Field("ziroom_lgt")
    private float lgt;
	
	@Field("ziroom_picUrl")
    private String picUrl;
	
	@Field("ziroom_owner")
	private String owner;
	
	@Field("ziroom_keywords")
	private String keywords;
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLgt() {
		return lgt;
	}

	public void setLgt(float lgt) {
		this.lgt = lgt;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
}
