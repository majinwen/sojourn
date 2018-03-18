/**
 * @FileName: HttpUtil.java
 * @Package com.ziroom.minsu.spider.commons
 * 
 * @author zl
 * @created 2016年10月7日 下午3:26:52
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.commons;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

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
public class HttpUtil {
	
	private static Set<String> userAgentSet=null;
	private static Set<String> ipSet=null;
	private static Set<String> xiaozhuIpSet=null;
	
	private static Map<String, String> cookies = null;
	
	private static Map<String, String> xiaozhuCookies = null;
	
			
	static{
		userAgentSet=new HashSet<String>();		
		
		userAgentSet.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/22.0.1207.1 Safari/537.1");
		userAgentSet.add("Mozilla/5.0 (X11; CrOS i686 2268.111.0) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.57 Safari/536.11");
		userAgentSet.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.6 (KHTML, like Gecko) Chrome/20.0.1092.0 Safari/536.6");
		userAgentSet.add("Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.6 (KHTML, like Gecko) Chrome/20.0.1090.0 Safari/536.6");
		userAgentSet.add("Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.1  (KHTML, like Gecko) Chrome/19.77.34.5 Safari/537.1");
		userAgentSet.add("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.9 Safari/536.5");
		userAgentSet.add("Mozilla/5.0 (Windows NT 6.0) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.36 Safari/536.5");
		userAgentSet.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1063.0 Safari/536.3");
		userAgentSet.add("Mozilla/5.0 (Windows NT 5.1) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1063.0 Safari/536.3");
		userAgentSet.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_0) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1063.0 Safari/536.3");
		userAgentSet.add("Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1062.0 Safari/536.3");
		userAgentSet.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1062.0 Safari/536.3");
		userAgentSet.add("Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3");
		userAgentSet.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3");
		userAgentSet.add("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3");
		userAgentSet.add("Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.0 Safari/536.3");
		userAgentSet.add("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.24 (KHTML, like Gecko) Chrome/19.0.1055.1 Safari/535.24");
		userAgentSet.add("Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/535.24 (KHTML, like Gecko) Chrome/19.0.1055.1 Safari/535.24");
		
		ipSet=new HashSet<String>();
//		ipSet.add("218.32.94.77:8080");
		ipSet.add("121.199.28.155:80"); 
		ipSet.add("222.173.56.27:8080");
		ipSet.add("139.196.108.68:80");
//		ipSet.add("70.88.182.181:80");
		ipSet.add("221.226.82.130:8998");
		
		xiaozhuIpSet=new HashSet<String>();
		xiaozhuIpSet.add("13.92.84.64:6666");
		xiaozhuIpSet.add("121.204.165.76:8118");
		xiaozhuIpSet.add("52.198.84.159:8090");
		xiaozhuIpSet.add("61.184.185.68:3128");
		xiaozhuIpSet.add("119.6.136.122:80");
		xiaozhuIpSet.add("202.171.253.72:80");
		xiaozhuIpSet.add("139.196.108.68:80");
		xiaozhuIpSet.add("60.194.100.51:80");
		xiaozhuIpSet.add("123.56.74.13:8080");
		xiaozhuIpSet.add("139.196.140.9:80");
		xiaozhuIpSet.add("202.108.2.42:80");
		xiaozhuIpSet.add("119.254.84.90:80");
		xiaozhuIpSet.add("112.81.100.102:8888");
		xiaozhuIpSet.add("122.96.59.106:80");
		xiaozhuIpSet.add("122.0.74.166:3389");
		xiaozhuIpSet.add("182.90.252.10:2226");
		xiaozhuIpSet.add("120.25.105.45:81");
		xiaozhuIpSet.add("112.243.184.242:8888");
		xiaozhuIpSet.add("61.55.135.192:82");
		xiaozhuIpSet.add("122.96.59.104:80");
		xiaozhuIpSet.add("221.226.82.130:8998");
		xiaozhuIpSet.add("121.40.108.76:80");
		xiaozhuIpSet.add("122.228.179.178:80");
		xiaozhuIpSet.add("120.76.243.40:80");
		
	}
	
	public static String getUserAgent(){
		if (userAgentSet.size()>0) {
			int n = new Random().nextInt(userAgentSet.size()-1);
			return (String) userAgentSet.toArray()[n];
		}
		
		return null; 
	}
	
	public static String getIpAndPort(){
		return getIpAndPort(ipSet,"http://zh.airbnb.com");
	}
	
	public static String getIpAndPort(Set<String> ipsSet,String tryUrl){
		if (ipsSet==null||ipsSet.size()==0) {
			return null;
		}
		boolean got = false;
		int num=0;
		String ipPort =null;
		if (ipsSet.size()>0) {
			
			for (; ;) {
				if ( got || num>10) {
					break;
				}
				int n =0;
				if (ipsSet.size()>1) {
					n = new Random().nextInt(ipsSet.size()-1);
				}
				try {
					ipPort =(String) ipsSet.toArray()[n];
					if (tryUrl==null||tryUrl.length()==0) {
						return ipPort;
					}
					URL url = new URL(tryUrl);
					
					String ip = ipPort.substring(0, ipPort.indexOf(":"));
					String port= ipPort.substring(ipPort.indexOf(":")+1);
					
					System.setProperty("http.maxRedirects", "50");
					System.getProperties().setProperty("proxySet", "true");
					System.getProperties().setProperty("http.proxyHost", ip);
					System.getProperties().setProperty("http.proxyPort", port);					
//					HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); 
					
//					Proxy proxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress(ip, Integer.valueOf(port))); 
//					HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(proxy); 
					
//		            urlConnection.setRequestMethod("GET");  
//		            urlConnection.setConnectTimeout(500); 
//		            urlConnection.connect();  
//		            if(urlConnection.getResponseCode()==200){
//		            	got = true;  
//		            }
		            
					Response res =null;
					
					if (tryUrl.contains("airbnb") && cookies!=null && cookies.size()>0) {
						res = Jsoup.connect(tryUrl).cookies(cookies).timeout(3000).execute();
					}else if (tryUrl.contains("xiaozhu")&& xiaozhuCookies!=null && xiaozhuCookies.size()>0) {
						res = Jsoup.connect(tryUrl).cookies(xiaozhuCookies).timeout(3000).execute();
					}else {
						res = Jsoup.connect(tryUrl).timeout(3000).execute();
					}
					
		            if(res.statusCode()==200){
		            	got = true; 
		            	if (tryUrl.contains("airbnb")) {
		            		updateCookies(res.cookies());
		            	}else if (tryUrl.contains("xiaozhu")) {
		            		updateXiaozhuCookies(res.cookies());
		            	}
		            }
		            
				} catch (Exception e) {
					e.printStackTrace();
				} 
				num+=1;
			}
			
		}
		
		if (got) {
			return ipPort;
		}
		
		return null; 
	}
	
	
	public static Map<String, String> getCookies(){
		return cookies;
	} 
	
	public static Map<String, String> getXiaozhuCookies(){
		return xiaozhuCookies;
	} 
	
	public static Map<String, String> updateCookies(Map<String, String> newCookies){
		
		if (newCookies!=null && newCookies.size()>0) {
			if (cookies==null) {
				cookies = new HashMap<String, String>();
			}
			Iterator<Entry<String, String>> iterator = newCookies.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				cookies.put(entry.getKey(), entry.getValue());
			}
		}
		
		return cookies;
	}
	
	public static Map<String, String> updateXiaozhuCookies(Map<String, String> newCookies){
		
		if (newCookies!=null && newCookies.size()>0) {
			if (xiaozhuCookies==null) {
				xiaozhuCookies = new HashMap<String, String>();
			}
			Iterator<Entry<String, String>> iterator = newCookies.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				xiaozhuCookies.put(entry.getKey(), entry.getValue());
			}
		}
		
		return xiaozhuCookies;
	}
	
	
	
	public static String getXiaozhuIpAndPort(){
		return getIpAndPort(xiaozhuIpSet,"http://www.xiaozhu.com");
	}
	
	
	public static Integer getRandTime(){
		return getRandTime(5000);
	}
	
	public static Integer getRandTime(int max){
		return getRandTime(2000, max);
	}
	
	public static Integer getRandTime(int min,int max){
		int n= 0;
		while(n<min){
			n= new Random().nextInt(max);
		}
		return n;
	}

	public static <T> String toAirbnbUrlParamString(T obj) {
		StringBuilder str=new StringBuilder();
		if (obj==null) {
			return null;
		}
		
		Class<?> clas = obj.getClass();
		 try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clas);
			PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
	        for (int i = 0; i< propertyDescriptors.length; i++) {
	        	 PropertyDescriptor descriptor = propertyDescriptors[i];
	             String propertyName = descriptor.getName();
	             Object result =null;
	             if (!propertyName.equals("class")) {
	                 Method readMethod = descriptor.getReadMethod();
	                 result = readMethod.invoke(obj, new Object[0]);
	             }
	             if (result!=null) {
	            	 Class<?>  propertyType = descriptor.getPropertyType(); 
	            	 if (propertyType.isArray()) {
	            		 Object[] vals=(Object[]) result;
	            		 if (vals!=null && vals.length>0) {
							for (int j = 0; j < vals.length; j++) {
								str.append(propertyName).append("[]=").append(vals[j]).append("&");
							}
						}
	            		 
	            	 }else {
	            		 str.append(propertyName).append("=").append(result).append("&");
	            	 }
				 }
	             
	             
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if (str.length()>1) {
			str.deleteCharAt(str.length()-1);
		}
		 
		return str.toString();
	}
	
	
	public static String getHost(String url) {
		if (url==null) {
			return null;
		}
		
		Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)",Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(url);
		matcher.find();
		return matcher.group();
	}
	
	
	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, IntrospectionException {
//		System.out.println(getUserAgent());
//		System.out.println(getIpAndPort());
		System.out.println(getXiaozhuIpAndPort());

	}
	

}
