package com.ziroom.minsu.services.common.utils;


/**
 * @FileName: MobileUtil.java
 * @Package com.ziroom.minsu.services.common.utils
 * 
 * @author zl
 * @created 2016年12月19日 下午2:30:42
 * 
 * Copyright 2011-2015 asura
 */

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
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
public class MobileUtil {


	private final static Logger logger = LoggerFactory.getLogger(MobileUtil.class);
	
	private static String apiUrl =MobileUtil.Constant.apiUrl;
	private static String apiAk =MobileUtil.Constant.apiAk;
	
	/**
	 * 查询手机号码信息
	 * TODO
	 *
	 * @author zl
	 * @created 2016年12月19日 下午4:59:30
	 *
	 * @param mobile
	 */
	public static MobileResult getMobileInfo(String mobile){
		
		MobileResult result = new MobileUtil().new MobileResult();
		
		if (!isMobile(mobile) || Check.NuNStr(apiUrl) || Check.NuNStr(apiAk)) {
			result.setMessage("参数不正确");
			result.setStatus(1);
			return result;			
		} 
		
		String url = apiUrl + "?phone="+mobile;
		Map<String,String> head = new HashMap<String, String>();
		head.put("apikey", apiAk);
		String resp = CloseableHttpUtil.sendGet(url, head);
		Map<?, ?> respMap = null;
		try {
			respMap = JsonEntityTransform.json2Map(resp);
		} catch (Exception e) {
			LogUtil.error(logger, "手机号码查询结果转化异常e={}，url={},result={}", e,url, resp);
			result.setMessage("手机号码查询结果转化异常");
			result.setStatus(1);
			return result;	
		}
		
		if (!Check.NuNMap(respMap)) {
			Map<?, ?> bodyMap = null;

			try {
				Integer status = (Integer) respMap.get("errNum");
				bodyMap = (Map<?, ?>) respMap.get("retData");
				result.setStatus(status);
			} catch (Exception e) {
				LogUtil.error(logger, "手机号码查询结果转化异常，url={},result={}", url, resp);
				result.setMessage("手机号码查询结果转化异常");
				result.setStatus(1);
				return result;
			}
			
			if (result.getStatus() == 0 && !Check.NuNMap(bodyMap)) {
				
				try {					
					result.setCity(decodeUnicode(String.valueOf(bodyMap.get("city"))));
					result.setProvince(decodeUnicode(String.valueOf(bodyMap.get("province"))));
					result.setSuit(decodeUnicode(String.valueOf(bodyMap.get("suit"))));
					result.setSupplier(decodeUnicode(String.valueOf(bodyMap.get("supplier"))));
				} catch (Exception e) {
					LogUtil.error(logger, "手机号码查询结果转化异常，url={},result={}", url, resp);
					result.setMessage("手机号码查询结果转化异常");
					result.setStatus(1);
					return result;
				}
				
				
			}else{
				result.setMessage(decodeUnicode(String.valueOf(respMap.get("retMsg"))));
			}
			
		}
		
		return result;
	}
	
	
	/**
	 * 手机号码校验
	 * TODO
	 *
	 * @author zl
	 * @created 2016年12月19日 下午4:58:23
	 *
	 * @param mobile
	 * @return
	 */
	public static boolean isMobile(String mobile) {
		if (mobile==null) {
			return false;
		} 		
		String rexp = "^1[3|4|5|7|8][0-9]\\d{8}$";
		Pattern pat = Pattern.compile(rexp);
		Matcher mat = pat.matcher(mobile);
		boolean isMobile = mat.find();
		return isMobile;
	}
	
	
	/**
	 * unicode 转字符串	
	 * TODO
	 *
	 * @author zl
	 * @created 2016年12月19日 下午4:02:32
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
	
	
	public class Constant{
		private static final String apiUrl = "http://apis.baidu.com/apistore/mobilenumber/mobilenumber";//"http://apis.baidu.com/showapi_open_bus/mobile/find";
		private static final String apiAk = "378251d134a39b8192acaad47865c858";
	}
	
	public class MobileResult implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1844960051598766492L;

		/**
		 * 返回结果状态值， 成功返回0
		 */
		private int status;
		
		/**
		 * 提示信息
		 */
		private String message;
		
		/**
		 * 号段
		 */
		private String suit; 
		/**
		 * 运营商
		 */
		private String supplier;
		/**
		 * 省份
		 */
		private String province; 
		/**
		 * 城市
		 */
		private String city;
		
		public int getStatus() {
			return status;
		}
		public String getMessage() {
			return message;
		}
		public String getSuit() {
			return suit;
		}
		public String getSupplier() {
			return supplier;
		}
		public String getProvince() {
			return province;
		}
		public String getCity() {
			return city;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public void setSuit(String suit) {
			this.suit = suit;
		}
		public void setSupplier(String supplier) {
			this.supplier = supplier;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		public void setCity(String city) {
			this.city = city;
		}

	}
	
   
    public static void main(String[] args) throws Exception {  
        getMobileInfo("18817324792");  
    } 

 
}
