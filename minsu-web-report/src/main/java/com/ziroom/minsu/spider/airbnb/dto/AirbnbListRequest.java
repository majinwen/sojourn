/**
 * @FileName: AirbnbListRequest.java
 * @Package com.ziroom.minsu.spider.airbnb.dto
 * 
 * @author zl
 * @created 2016年10月10日 上午9:26:28
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.airbnb.dto;

import com.ziroom.minsu.spider.airbnb.entity.enums.AmenitiesEnum;
import com.ziroom.minsu.spider.airbnb.entity.enums.LanguageEnum;
import com.ziroom.minsu.spider.airbnb.entity.enums.RoomTypeEnum;

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
public class AirbnbListRequest {
	
	/**
	 * 页码
	 */
	private Integer page;
	
	/**
	 * 房间类型
	 * @see RoomTypeEnum
	 */
	private String room_types[];
	
	/**
	 * 街区
	 */
	private String neighborhoods[];
	
	/**
	 * 房客数
	 * <=16
	 */
	private Integer guests;
	
	/**
	 * 最小价格
	 * >=67
	 */
	private Integer price_min;
	
	/**
	 * 最大价格
	 * <=5000
	 */
	private Integer price_max;
	
	/**
	 * 最少卫生间
	 * <=8
	 */
	private Float min_bathrooms;
	
	/**
	 * 最少卧室
	 * <=10
	 */
	private Integer min_bedrooms;
	
	/**
	 * 最少床位数
	 * <=16
	 */
	private Integer min_beds;
	
	/**
	 * 是否可以立即预定
	 */
	private Boolean ib;
	
	/**
	 * 是否超级房东
	 */
	private Boolean superhost;
	
	/**
	 * 便利设施
	 * @see AmenitiesEnum
	 */
	private Integer hosting_amenities[];
	
	/**
	 * 房东语言
	 * @see LanguageEnum
	 */
	private Integer languages[];
	
	/**
	 * 位置
	 * Beijing, China
	 */
	private String location;
	
	/**
	 * 入住日期
	 * yyyy-MM-dd
	 */
	private String checkin;
	
	/**
	 * 退房日期
	 * yyyy-MM-dd
	 */
	private String checkout;
	
	/**
	 * 最小纬度
	 */
	private Double sw_lat;
	/**
	 * 最小经度
	 */
	private Double sw_lng;
	/**
	 * 最大纬度
	 */
	private Double ne_lat;
	/**
	 * 最大经度
	 */
	private Double ne_lng;
	/**
	 * 是否根据地图搜索
	 */
	private Boolean search_by_map;
	/**
	 * 放缩比例
	 */
	private Integer zoom;
	
	private String source;
	private Boolean airbnb_plus_only;
	private String ss_id;
	private String allow_override[];
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public String[] getRoom_types() {
		return room_types;
	}
	public void setRoom_types(String[] room_types) {
		this.room_types = room_types;
	}
	public String[] getNeighborhoods() {
		return neighborhoods;
	}
	public void setNeighborhoods(String[] neighborhoods) {
		this.neighborhoods = neighborhoods;
	}
	public Integer getGuests() {
		return guests;
	}
	public void setGuests(Integer guests) {
		this.guests = guests;
	}
	public Integer getPrice_min() {
		return price_min;
	}
	public void setPrice_min(Integer price_min) {
		this.price_min = price_min;
	}
	public Integer getPrice_max() {
		return price_max;
	}
	public void setPrice_max(Integer price_max) {
		this.price_max = price_max;
	}
	public Float getMin_bathrooms() {
		return min_bathrooms;
	}
	public void setMin_bathrooms(Float min_bathrooms) {
		this.min_bathrooms = min_bathrooms;
	}
	public Integer getMin_bedrooms() {
		return min_bedrooms;
	}
	public void setMin_bedrooms(Integer min_bedrooms) {
		this.min_bedrooms = min_bedrooms;
	}
	public Integer getMin_beds() {
		return min_beds;
	}
	public void setMin_beds(Integer min_beds) {
		this.min_beds = min_beds;
	}
	public Boolean getIb() {
		return ib;
	}
	public void setIb(Boolean ib) {
		this.ib = ib;
	}
	public Boolean getSuperhost() {
		return superhost;
	}
	public void setSuperhost(Boolean superhost) {
		this.superhost = superhost;
	}
	public Integer[] getHosting_amenities() {
		return hosting_amenities;
	}
	public void setHosting_amenities(Integer[] hosting_amenities) {
		this.hosting_amenities = hosting_amenities;
	}
	public Integer[] getLanguages() {
		return languages;
	}
	public void setLanguages(Integer[] languages) {
		this.languages = languages;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCheckin() {
		return checkin;
	}
	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}
	public String getCheckout() {
		return checkout;
	}
	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}
	public Double getSw_lat() {
		return sw_lat;
	}
	public void setSw_lat(Double sw_lat) {
		this.sw_lat = sw_lat;
	}
	public Double getSw_lng() {
		return sw_lng;
	}
	public void setSw_lng(Double sw_lng) {
		this.sw_lng = sw_lng;
	}
	public Double getNe_lat() {
		return ne_lat;
	}
	public void setNe_lat(Double ne_lat) {
		this.ne_lat = ne_lat;
	}
	public Double getNe_lng() {
		return ne_lng;
	}
	public void setNe_lng(Double ne_lng) {
		this.ne_lng = ne_lng;
	}
	public Boolean getSearch_by_map() {
		return search_by_map;
	}
	public void setSearch_by_map(Boolean search_by_map) {
		this.search_by_map = search_by_map;
	}
	public Integer getZoom() {
		return zoom;
	}
	public void setZoom(Integer zoom) {
		this.zoom = zoom;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Boolean getAirbnb_plus_only() {
		return airbnb_plus_only;
	}
	public void setAirbnb_plus_only(Boolean airbnb_plus_only) {
		this.airbnb_plus_only = airbnb_plus_only;
	}
	public String getSs_id() {
		return ss_id;
	}
	public void setSs_id(String ss_id) {
		this.ss_id = ss_id;
	}
	public String[] getAllow_override() {
		return allow_override;
	}
	public void setAllow_override(String[] allow_override) {
		this.allow_override = allow_override;
	} 
	
}
