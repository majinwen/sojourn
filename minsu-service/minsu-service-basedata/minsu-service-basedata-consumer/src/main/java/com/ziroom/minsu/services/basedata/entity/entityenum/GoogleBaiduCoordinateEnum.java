/**
 * @FileName: GoogleBaiduCoordinateUtils.java
 * @Package com.ziroom.minsu.services.common.conf
 * 
 * @author yd
 * @created 2017年2月23日 下午9:59:10
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.entity.entityenum;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.entity.conf.HotRegionEntity;
import com.ziroom.minsu.entity.conf.SubwayOutEntity;
import com.ziroom.minsu.entity.conf.SubwayStationEntity;
import com.ziroom.minsu.entity.house.HousePhyMsgEntity;
import com.ziroom.minsu.services.common.utils.CoordinateTransforUtils;
import com.ziroom.minsu.services.common.utils.Gps;
import com.ziroom.minsu.valenum.base.MapTypeEnum;


/**
 * <p>百度经纬度 和 谷歌经纬度 相互转化</p>
 * 
 * <PRE>
 * 说明：
 *  当前涉及 经纬度的类 直接列在这里 分类进行处理
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public enum GoogleBaiduCoordinateEnum {

	HousePhyMsgEntity(1,"HousePhyMsgEntity"){
		/* (non-Javadoc)
		 * @see com.ziroom.minsu.services.common.conf.GoogleBaiduCoordinateEnum#googleBaiduCoordinateTranfor(java.lang.Object)
		 */
		@Override
		public void googleBaiduCoordinateTranfor(HousePhyMsgEntity obj,double lat,double lon,String mapType) {
			if(obj!=null&&!Check.NuNStr(mapType)){
				/*try {
				
				} catch (Exception e) {
					LogUtil.error(logger, "GoogleBaiduCoordinateEnum:HousePhyMsgEntity: 地图经纬度转换异常lat={},lon={},mapType={},e={}", lat,lon,mapType,e);
				}*/
				if(MapTypeEnum.BAIDU.getCode().equals(mapType)
						&&!Check.NuNObj(lat)
						&&!Check.NuNObj(lon)){
					Gps gps  = CoordinateTransforUtils.bd09_To_Gps84(lat,lon);
					obj.setLatitude(lat);
					obj.setLongitude(lon);
					if(gps != null){
						obj.setGoogleLatitude(gps.getWgLat());
						obj.setGoogleLongitude(gps.getWgLon());
					}
				}
				
				if(MapTypeEnum.GOOGLE.getCode().equals(mapType)){
					Gps gps  =  CoordinateTransforUtils.wgs84_To_bd09(lat,lon);
					obj.setGoogleLatitude(lat);
					obj.setGoogleLongitude(lon);
					if(gps != null){
						obj.setLatitude(gps.getWgLat());
						obj.setLongitude(gps.getWgLon());
					}
				}
			}
		}
	},

	HotRegionEntity(2,"HotRegionEntity"){
		
		/* (non-Javadoc)
		 * @see com.ziroom.minsu.services.basedata.entity.entityenum.GoogleBaiduCoordinateEnum#googleBaiduCoordinateTranfor(com.ziroom.minsu.entity.house.HousePhyMsgEntity, double, double, java.lang.String)
		 */
		@Override
		public void googleBaiduCoordinateTranfor(HotRegionEntity obj,
				double lat, double lon, String mapType) {
			if(obj!=null&&!Check.NuNStr(mapType)){
				/*try {
					
				} catch (Exception e) {
					LogUtil.error(logger, "GoogleBaiduCoordinateEnum:HotRegionEntity: 地图经纬度转换异常lat={},lon={},mapType={},e={}", lat,lon,mapType,e);
				}*/
				if(MapTypeEnum.BAIDU.getCode().equals(mapType)
						&&!Check.NuNObj(lat)
						&&!Check.NuNObj(lon)){
					Gps gps  = CoordinateTransforUtils.bd09_To_Gps84(lat,lon);
					obj.setLatitude(lat);
					obj.setLongitude(lon);
					if(gps != null){
						obj.setGoogleLatitude(gps.getWgLat());
						obj.setGoogleLongitude(gps.getWgLon());
					}
				}
				
				if(MapTypeEnum.GOOGLE.getCode().equals(mapType)){
					Gps gps  =  CoordinateTransforUtils.wgs84_To_bd09(lat,lon);
					obj.setGoogleLatitude(lat);
					obj.setGoogleLongitude(lon);
					if(gps != null){
						obj.setLatitude(gps.getWgLat());
						obj.setLongitude(gps.getWgLon());
					}
				}
			}
		}
	},
	SubwayStationEntity(3,"SubwayStationEntity"){
		/* (non-Javadoc)
		 * @see com.ziroom.minsu.services.basedata.entity.entityenum.GoogleBaiduCoordinateEnum#googleBaiduCoordinateTranfor(com.ziroom.minsu.entity.conf.HotRegionEntity, double, double, java.lang.String)
		 */
		@Override
		public void googleBaiduCoordinateTranfor(SubwayStationEntity obj, double lat,
				double lon, String mapType) {
			if(obj!=null&&!Check.NuNStr(mapType)){
				/*try {
					
				} catch (Exception e) {
					LogUtil.error(logger, "GoogleBaiduCoordinateEnum:SubwayStationEntity: 地图经纬度转换异常lat={},lon={},mapType={},e={}", lat,lon,mapType,e);
				}*/
				if(MapTypeEnum.BAIDU.getCode().equals(mapType)
						&&!Check.NuNObj(lat)
						&&!Check.NuNObj(lon)){
					Gps gps  = CoordinateTransforUtils.bd09_To_Gps84(lat,lon);
					obj.setLatitude(lat);
					obj.setLongitude(lon);
					if(gps != null){
						obj.setGoogleLatitude(gps.getWgLat());
						obj.setGoogleLongitude(gps.getWgLon());
					}
				}
				
				if(MapTypeEnum.GOOGLE.getCode().equals(mapType)){
					Gps gps  =  CoordinateTransforUtils.wgs84_To_bd09(lat,lon);
					obj.setGoogleLatitude(lat);
					obj.setGoogleLongitude(lon);
					if(gps != null){
						obj.setLatitude(gps.getWgLat());
						obj.setLongitude(gps.getWgLon());
					}
				}
			}
		}
	},
	SubwayOutEntity(4,"SubwayOutEntity"){
		
		/* (non-Javadoc)
		 * @see com.ziroom.minsu.services.basedata.entity.entityenum.GoogleBaiduCoordinateEnum#googleBaiduCoordinateTranfor(com.ziroom.minsu.entity.conf.SubwayOutEntity, double, double, java.lang.String)
		 */
		@Override
		public void googleBaiduCoordinateTranfor(SubwayOutEntity obj, double lat,
				double lon, String mapType) {
			if(obj!=null&&!Check.NuNStr(mapType)){
				/*try {
					
				} catch (Exception e) {
					LogUtil.error(logger, "GoogleBaiduCoordinateEnum:SubwayOutEntity: 地图经纬度转换异常lat={},lon={},mapType={},e={}", lat,lon,mapType,e);
				}*/
				if(MapTypeEnum.BAIDU.getCode().equals(mapType)
						&&!Check.NuNObj(lat)
						&&!Check.NuNObj(lon)){
					Gps gps  = CoordinateTransforUtils.bd09_To_Gps84(lat,lon);
					obj.setLatitude(lat);
					obj.setLongitude(lon);
					if(gps != null){
						obj.setGoogleLatitude(gps.getWgLat());
						obj.setGoogleLongitude(gps.getWgLon());
					}
				}
				
				if(MapTypeEnum.GOOGLE.getCode().equals(mapType)){
					Gps gps  =  CoordinateTransforUtils.wgs84_To_bd09(lat,lon);
					obj.setGoogleLatitude(lat);
					obj.setGoogleLongitude(lon);
					if(gps != null){
						obj.setLatitude(gps.getWgLat());
						obj.setLongitude(gps.getWgLon());
					}
				}
			}
		}
	};
	/**
	 * 枚举code
	 */
	private int code;

	/**
	 * 枚举值
	 */
	private String value;


	GoogleBaiduCoordinateEnum(int code,String value ){

		this.code = code;
		this.value = value;
	}

	/**
	 * 赋值经纬度
	 *
	 * @author yd
	 * @created 2017年2月24日 上午10:13:51
	 *
	 * @param obj
	 * @param mapType
	 */
	public void googleBaiduCoordinateTranfor(HotRegionEntity obj,double lat,double lon,
			String mapType) {
	}


	/**
	 * 赋值经纬度
	 *
	 * @author yd
	 * @created 2017年2月24日 上午10:13:51
	 *
	 * @param obj
	 * @param mapType
	 */
	public void googleBaiduCoordinateTranfor(HousePhyMsgEntity obj,double lat,double lon,
			String mapType) {
	}
	

	/**
	 * 赋值经纬度
	 *
	 * @author yd
	 * @created 2017年2月24日 上午10:13:51
	 *
	 * @param obj
	 * @param mapType
	 */
	public void googleBaiduCoordinateTranfor(SubwayOutEntity obj,double lat,double lon,
			String mapType) {
	}
	
	/**
	 * 赋值经纬度
	 *
	 * @author yd
	 * @created 2017年2月24日 上午10:13:51
	 *
	 * @param obj
	 * @param mapType
	 */
	public void googleBaiduCoordinateTranfor(SubwayStationEntity obj,double lat,double lon,
			String mapType) {
	}
	/**
	 * 获取当前的默认值
	 * @param type
	 * @param code
	 * @return
	 */
	public static GoogleBaiduCoordinateEnum getConfig(String code){

		for (GoogleBaiduCoordinateEnum googleBaiduCoordinateEnum : GoogleBaiduCoordinateEnum.values()) {
			if(code.equals(googleBaiduCoordinateEnum.getCode())){
				return googleBaiduCoordinateEnum;
			}
		}
		return null;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
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


}
