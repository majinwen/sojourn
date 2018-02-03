/**
 * @FileName: BaiDuIPUtil.java
 * @Package com.ziroom.minsu.services.common.utils
 * 
 * @author zl
 * @created 2016年12月9日 下午7:28:30
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.common.utils;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;

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
public class BaiDuIPUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(BaiDuIPUtil.class);
//	private static Properties property = PropertiesUtils.getProperties("classpath:sms.properties");
//	private static String ipApiUrl = property.getProperty("ip.baidu.api.url");
//	private static String ipApiAk = property.getProperty("ip.baidu.api.ak");
	private static String ipApiUrl =BaiDuIPUtil.Constant.ipApiUrl;
	private static String ipApiAk =BaiDuIPUtil.Constant.ipApiAk;
	
	private static String coor="bd09ll";//百度经纬度坐标
	
	public static BaiduIPResult getBaiduIPInfo(String ip){
		BaiduIPResult result = new BaiDuIPUtil().new BaiduIPResult();
		if (!isIP(ip) || Check.NuNStr(ipApiUrl) || Check.NuNStr(ipApiAk)) {
			result.setMessage("参数为空");
			result.setStatus(1);
			return result;			
		} 
		
		String url = ipApiUrl + "?ip="+ip+ "&ak=" + ipApiAk +"&coor="+coor;
		String resp = CloseableHttpUtil.sendGet(url, null);
		Map<?, ?> respMap = null;
		try {
			respMap = JsonEntityTransform.json2Map(resp);
		} catch (Exception e) {
			LogUtil.error(logger, "百度ip查询结果转化异常e={}，url={},result={}", e,url, resp);
			result.setMessage("百度ip查询结果转化异常");
			result.setStatus(1);
			return result;	
		}

		if (!Check.NuNMap(respMap)) {
			Map<?, ?> addressMap = null;

			try {
				Integer status = (Integer) respMap.get("status");
				addressMap = (Map<?, ?>) respMap.get("content");
				result.setStatus(status);
			} catch (Exception e) {
				LogUtil.error(logger, "百度ip查询结果转化异常，url={},result={}", url, resp);
				result.setStatus(1);
				return result;
			}

			if (result.getStatus() == 0 && !Check.NuNMap(addressMap)) {
				Map<?, ?> addressInfoMap = null;
				Map<?, ?> pointMap = null;
				try {
					addressInfoMap = (Map<?, ?>) addressMap.get("address_detail");
					pointMap = (Map<?, ?>) addressMap.get("point");
				} catch (Exception e) {
					LogUtil.error(logger, "百度ip查询结果转化异常，url={},result={}", url, resp);
				}
				
				if (!Check.NuNMap(addressInfoMap)) {
					result.setAddressInfo(String.valueOf(respMap.get("address")));
					result.setAddress(String.valueOf(addressMap.get("address")));
					result.setCity(String.valueOf(addressInfoMap.get("city")));
					result.setCityCode(String.valueOf(addressInfoMap.get("city_code")));
					result.setProvince(String.valueOf(addressInfoMap.get("province")));
					result.setDistrict(String.valueOf(addressInfoMap.get("district")));
					result.setStreet(String.valueOf(addressInfoMap.get("street")));
					result.setStreetNumber(String.valueOf(addressInfoMap.get("street_number")));
				}
				
				if (!Check.NuNMap(pointMap)) {
					try {
						result.setLat(Double.valueOf(String.valueOf(pointMap.get("y"))));
						result.setLng(Double.valueOf(String.valueOf(pointMap.get("x"))));			
						
					} catch (Exception e) {
						LogUtil.error(logger, "百度ip查询经纬度转化异常，url={},result={}", url, resp);
					}
				}
				
			}
		}
		
		
		return result;	
	}
	
	/**
	 * 校验ip
	 * TODO
	 *
	 * @author zl
	 * @created 2016年12月9日 下午9:02:39
	 *
	 * @param addr
	 * @return
	 */
	public static boolean isIP(String addr) {
		if (addr==null ||addr.length() < 7 || addr.length() > 15) {
			return false;
		}
		/**
		 * 判断IP格式和范围
		 */
		String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

		Pattern pat = Pattern.compile(rexp);
		Matcher mat = pat.matcher(addr);
		boolean ipAddress = mat.find();
		return ipAddress;
	}
	
	
	/**
	 * unicode 转字符串	
	 * TODO
	 *
	 * @author zl
	 * @created 2016年12月9日 下午9:02:32
	 *
	 * @param theString
	 * @return
	 */
	public static String decodeUnicode(String theString) {

		if (theString == null) {
			return null;
		}

		char aChar;
		int len = theString.length();

		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
						}

					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
					outBuffer.append(aChar);
				}

			} else {
				outBuffer.append(aChar);
			}
		}

		return outBuffer.toString();
	}
	

	public static void main(String[] args) {
		getBaiduIPInfo("106.120.220.6");
//		System.out.println(decodeUnicode("\u5317\u4eac\u5e02"));
	}
	
	public class Constant{
		private static final String ipApiUrl = "http://api.map.baidu.com/location/ip";
		private static final String ipApiAk = "yZ7wc2RjbFWGYMIWyTDEHqbQ";
	}	
	
	public class BaiduIPResult implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -1315123340744884419L;

		/**
		 * 返回结果状态值， 成功返回0
		 */
		private int status;

		/**
		 * 错误提示信息
		 */
		private String message;

		/**
		 * 地址，如：CN|北京|北京|None|CHINANET|0|0
		 */
		private String addressInfo;

		/**
		 * 简要地址  
		 */
		private String address;

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
		 * 街道名
		 */
		private String street;
		/**
		 * 街道门牌号
		 */
		private String streetNumber;
		/**
		 * 维度
		 */
		private Double lat;
		/**
		 * 经度
		 */
		private Double lng;
		public int getStatus() {
			return status;
		}
		public String getMessage() {
			return message;
		}
		public String getAddressInfo() {
			return addressInfo;
		}
		public String getAddress() {
			return address;
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
		public String getStreet() {
			return street;
		}
		public String getStreetNumber() {
			return streetNumber;
		}
		public Double getLat() {
			return lat;
		}
		public Double getLng() {
			return lng;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public void setAddressInfo(String addressInfo) {
			this.addressInfo = addressInfo;
		}
		public void setAddress(String address) {
			this.address = address;
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
		public void setStreet(String street) {
			this.street = street;
		}
		public void setStreetNumber(String streetNumber) {
			this.streetNumber = streetNumber;
		}
		public void setLat(Double lat) {
			this.lat = lat;
		}
		public void setLng(Double lng) {
			this.lng = lng;
		}

	}
	
	

}
