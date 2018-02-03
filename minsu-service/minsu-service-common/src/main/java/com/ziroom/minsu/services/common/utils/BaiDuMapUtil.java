/**
 * @FileName: BaiDuMapUtil.java
 * @Package com.ziroom.minsu.services.common.utils
 * 
 * @author zl
 * @created 2016年11月30日 下午8:30:26
 * 
 * Copyright 2011-2015 ziroom
 */
package com.ziroom.minsu.services.common.utils;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;

/**
 * <p>
 * TODO
 * </p>
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
public class BaiDuMapUtil {

	private final static Logger logger = LoggerFactory.getLogger(BaiDuMapUtil.class);
//	private static Properties property = PropertiesUtils.getProperties("classpath:sms.properties");
//	private static String geocodingUrl = property.getProperty("map.baidu.api.geocoding.url");
//	private static String geocodingAk = property.getProperty("map.baidu.api.geocoding.ak");
	private static String geocodingUrl =BaiDuMapUtil.Constant.geocodingUrl;
	private static String geocodingAk =BaiDuMapUtil.Constant.geocodingAk;
	
	// 输出格式
	private static final String defaultOutput = "json";

	private static final int defaultPois = 0;

	public static BaiduGeocodingResult getBaiduAreaCodeByLocation(double lat, double lng) {

		BaiduGeocodingResult baiduResult = new BaiDuMapUtil().new BaiduGeocodingResult();

		if (Check.NuNStr(geocodingUrl) || Check.NuNStr(geocodingAk)) {
			return null;
		}

		String url = geocodingUrl + "?output=" + defaultOutput + "&pois=" + defaultPois + "&ak=" + geocodingAk
				+ "&location=" + lat + "," + lng;
		String resp = CloseableHttpUtil.sendGet(url, null);
		Map<?, ?> respMap = null;
		try {
			respMap = JsonEntityTransform.json2Map(resp);
		} catch (Exception e) {
			LogUtil.error(logger, "百度地图反坐标查询结果转化异常，url={},result={}", url, resp);
			baiduResult.setStatus(1);
			return baiduResult;
		}

		if (!Check.NuNMap(respMap)) {

			Map<?, ?> resultMap = null;

			try {
				Integer status = (Integer) respMap.get("status");
				resultMap = (Map<?, ?>) respMap.get("result");
				baiduResult.setStatus(status);
			} catch (Exception e) {
				LogUtil.error(logger, "百度地图反坐标查询结果转化异常，url={},result={}", url, resp);
				baiduResult.setStatus(1);
				return baiduResult;
			}

			if (baiduResult.getStatus() == 0 && !Check.NuNMap(resultMap)) {

				baiduResult.setFormattedAddress(ValueUtil.getStrValue(resultMap.get("formatted_address")));
				baiduResult.setBusiness(ValueUtil.getStrValue(resultMap.get("business")));
				baiduResult.setSematicDescription(ValueUtil.getStrValue(resultMap.get("sematic_description")));
				baiduResult.setCityCode(ValueUtil.getStrValue(resultMap.get("cityCode")));

				Map<?, ?> addressComponentMap = null;

				try {
					addressComponentMap = (Map<?, ?>) resultMap.get("addressComponent");
				} catch (Exception e) {
					LogUtil.error(logger, "百度地图反坐标查询结果转化异常，url={},result={}", url, resp);
				}

				if (!Check.NuNMap(addressComponentMap)) {
					baiduResult.setCountry(ValueUtil.getStrValue(addressComponentMap.get("country")));
					baiduResult.setCountryCode(ValueUtil.getintValue(addressComponentMap.get("country_code")));
					baiduResult.setProvince(ValueUtil.getStrValue(addressComponentMap.get("province")));
					baiduResult.setCity(ValueUtil.getStrValue(addressComponentMap.get("city")));
					baiduResult.setDistrict(ValueUtil.getStrValue(addressComponentMap.get("district")));
					baiduResult.setAdcode(ValueUtil.getStrValue(addressComponentMap.get("adcode")));
					baiduResult.setStreet(ValueUtil.getStrValue(addressComponentMap.get("street")));
					baiduResult.setStreetNumber(ValueUtil.getStrValue(addressComponentMap.get("street_number")));
					baiduResult.setDirection(ValueUtil.getStrValue(addressComponentMap.get("direction")));
					baiduResult.setDistance(ValueUtil.getintValue(addressComponentMap.get("distance")));
				}

			}

		}

		return baiduResult;
	}

	public static void main(String[] args) {
		BaiduGeocodingResult result = getBaiduAreaCodeByLocation(28.082001, 117.788929);
		System.out.println(JsonEntityTransform.Object2Json(result));
	}
	
	public class Constant{
		private static final String geocodingUrl = "http://api.map.baidu.com/geocoder/v2/";
		private static final String geocodingAk = "yZ7wc2RjbFWGYMIWyTDEHqbQ";
	}

	public class BaiduGeocodingResult implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6828230552429654265L;

		/**
		 * 返回结果状态值， 成功返回0
		 */
		private int status;

		/**
		 * 结构化地址信息
		 */
		private String formattedAddress;

		/**
		 * 所在商圈信息，如 "人民大学,中关村,苏州街"
		 */
		private String business;

		/**
		 * 当前位置结合POI的语义化结果描述
		 */
		private String sematicDescription;

		/**
		 * 国家名
		 */
		private String country;

		/**
		 * 国家代码(百度)
		 */
		private int countryCode;

		/**
		 * 省名
		 */
		private String province;
		/**
		 * 城市名
		 */
		private String city;

		/**
		 * 城市编码
		 */
		private String cityCode;

		/**
		 * 区县名
		 */
		private String district;

		/**
		 * 行政区划代码
		 */
		private String adcode;

		/**
		 * 街道名
		 */
		private String street;
		/**
		 * 街道门牌号
		 */
		private String streetNumber;

		/**
		 * 和当前坐标点的方向，当有门牌号的时候返回数据
		 */
		private String direction;
		/**
		 * 和当前坐标点的距离，当有门牌号的时候返回数据
		 */
		private int distance;

		public int getStatus() {
			return status;
		}

		public String getFormattedAddress() {
			return formattedAddress;
		}

		public String getBusiness() {
			return business;
		}

		public String getSematicDescription() {
			return sematicDescription;
		}

		public String getCountry() {
			return country;
		}

		public int getCountryCode() {
			return countryCode;
		}

		public String getProvince() {
			return province;
		}

		public String getCity() {
			return city;
		}

		public String getCityCode() {
			return cityCode;
		}

		public String getDistrict() {
			return district;
		}

		public String getAdcode() {
			return adcode;
		}

		public String getStreet() {
			return street;
		}

		public String getStreetNumber() {
			return streetNumber;
		}

		public String getDirection() {
			return direction;
		}

		public int getDistance() {
			return distance;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public void setFormattedAddress(String formattedAddress) {
			this.formattedAddress = formattedAddress;
		}

		public void setBusiness(String business) {
			this.business = business;
		}

		public void setSematicDescription(String sematicDescription) {
			this.sematicDescription = sematicDescription;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public void setCountryCode(int countryCode) {
			this.countryCode = countryCode;
		}

		public void setProvince(String province) {
			this.province = province;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public void setCityCode(String cityCode) {
			this.cityCode = cityCode;
		}

		public void setDistrict(String district) {
			this.district = district;
		}

		public void setAdcode(String adcode) {
			this.adcode = adcode;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		public void setStreetNumber(String streetNumber) {
			this.streetNumber = streetNumber;
		}

		public void setDirection(String direction) {
			this.direction = direction;
		}

		public void setDistance(int distance) {
			this.distance = distance;
		}

	}

}
