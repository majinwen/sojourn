/**
 * @FileName: Gps.java
 * @Package com.ziroom.minsu.services.common.utils.test
 * 
 * @author yd
 * @created 2017年2月14日 下午3:30:46
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.common.utils;

/**
 * <p>坐标实体</p>
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
public class Gps {

	/**
	 * 经度
	 */
	private double wgLon;
	
	/**
	 * 纬度
	 */
	private double wgLat;
	
	Gps(double lat,double lon){
		this.wgLat = lat;
		this.wgLon =  lon;
	}

	Gps(){};
	/**
	 * @return the wgLon
	 */
	public double getWgLon() {
		return wgLon;
	}

	/**
	 * @param wgLon the wgLon to set
	 */
	public void setWgLon(double wgLon) {
		this.wgLon = wgLon;
	}

	/**
	 * @return the wgLat
	 */
	public double getWgLat() {
		return wgLat;
	}

	/**
	 * @param wgLat the wgLat to set
	 */
	public void setWgLat(double wgLat) {
		this.wgLat = wgLat;
	}
	
	
}
