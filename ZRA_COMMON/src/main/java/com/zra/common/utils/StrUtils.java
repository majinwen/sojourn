package com.zra.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * 字符串工具类
 * 
 * @author thinkpad
 * 
 */
public class StrUtils {
	/**
	 * 判断字符串是否不为空或者不为空字符
	 * 
	 * @param str
	 *            允许为NULL
	 * @return
	 */
	public static boolean isNotNullOrBlank(Object o) {
		if (null == o) {
			return false;
		} else {
			String str = o.toString();
			if ("".equals(str.trim())) {
				return false;
			} else {
				return true;
			}
		}
	}

	/**
	 * 判断字符串是否为空或者不为空字符
	 * 
	 * @param str
	 *            允许为NULL
	 * @return
	 */
	public static boolean isNullOrBlank(Object o) {
		return !isNotNullOrBlank(o);
	}

	/**
	 * 将sourceObject转换为字符串后与compareStr对象比较，如果相等就返回afterIsEqualToObject对象，
	 * 不相等就返回sourceObject
	 * 
	 * @param sourceObject
	 *            被比较的对象
	 * @param compareStr
	 *            比较的对象
	 * @param object
	 *            如果相等，则返回afterIsEqualToObject对象
	 */
	public static Object toStringForCompare(Object sourceObject,
			String compareStr, Object afterIsEqualToObject) {
		if (null == sourceObject) {
			return sourceObject;
		} else if (sourceObject.toString().equals(compareStr)) {
			return compareStr;
		} else {
			return null;
		}

	}

	/**
	 * 如果对象为空，则返回空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String isNullToBank(Object str) {
		if (null == str) {
			return "";
		} else {
			return str.toString();
		}
	}

	/**
	 * 将对象转换为String类型
	 * 
	 * @param str
	 * @return
	 */
	public static String ObjectToStr(Object o, String defaultValue) {
		if (null == o) {
			return defaultValue;
		} else {
			return o.toString().trim();
		}
	}

	public static String ObjectToStr(Object o) {
		if (null == o) {
			return null;
		} else {
			return o.toString().trim();
		}
	}

	/**
	 * 将对象转换为String类型
	 * 
	 * @param str
	 * @return
	 */
	public static String ObjectToString(Object o) {
		if (null == o) {
			return null;
		} else {
			return o.toString();
		}
	}

	/**
	 * 将对象转换为Long类型
	 * 
	 * @param str
	 * @return
	 */
	public static Long ObjectToLong(Object o, Long defaultValue) {
		if (null == o) {
			return defaultValue;
		} else {
			return (Long) o;
		}
	}

	/**
	 * 将对象转换为Long类型
	 * 
	 * @param str
	 * @return
	 */
	public static Long ObjectToLong(Object o) {
		if (null == o) {
			return null;
		} else {
			return new Long(o.toString());
		}
	}

	/**
	 * 将对象转换为Integer类型
	 * 
	 * @param str
	 * @return
	 */
	public static Integer ObjectToInteger(Object o) {
		if (null == o) {
			return null;
		} else {
			return ((java.math.BigDecimal) o).intValue();
		}
	}

	/**
	 * 集合转换成字符串
	 * 
	 * @param collection
	 *            集合
	 * @param separator
	 *            分隔符
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String CollectionToStr(Collection collection,
			String separator, boolean all) {
		Iterator it = collection.iterator();
		Object object = null;
		String str = "";
		while (it.hasNext()) {
			object = it.next();
			str = str + object.toString() + separator;
		}
		if (collection.size() > 0) {
			if (all) {
				str = "," + str;
			} else {
				str = str.substring(0, str.length() - (separator.length()));
			}
		}
		return str;
	}
	
	/**
	 * 将list 转换为a,b,c,d,e形式
	 * @param list
	 * @return
	 */
	public static String listToString(List<String> list){
		if(list == null || list.size() <= 0) {
			return"";
		}
		
		StringBuffer sbuffer = new StringBuffer();
		
		for(String id : list){
			
			sbuffer.append("'"+id+"',");
		}
		StringBuffer resBuffer = new StringBuffer(sbuffer.substring(0, sbuffer.length()-1));
		
	    return resBuffer.toString();
	}
	
	/**
	 * 将list 转换为（a,b,c,d,e）形式
	 * @param list
	 * @return
	 */
	public static String listToStringAddBrackets(List<String> list){
		if(list == null || list.size() <= 0) {
			return"()";
		}
		
		StringBuffer sbuffer = new StringBuffer();
		sbuffer.append("(");
		for(String id : list){
			sbuffer.append(id+",");
		}
		StringBuffer resBuffer = new StringBuffer(sbuffer.substring(0, sbuffer.length()-1));
		resBuffer.append(")");
	    return resBuffer.toString();
	}
	
	/**
	 * @param map
	 * @return
	 */
	public static String getKeysByCom(Map<String,String> map){
		String keys = "";
		if(map!=null && map.size()>0){
			Set<Entry<String,String>> set = map.entrySet();
			int i = 0;
			for(Entry<String,String> en: set){
				if(i == 0){
					keys ="'" +en.getKey()+"'";
				}else{
					keys +=",'" +en.getKey()+"'";
				}
				i++;
			}
		}
		return keys;
	}
	
	public static List<String> getListSting(Map<String,String> map){
		if(map == null || map.size() <= 0) { 
			return new ArrayList<String>();
		}
		List<String> resList = new ArrayList<String>(map.size());
		Set<Entry<String,String>> set = map.entrySet();
		for(Entry<String,String> en: set){
			resList.add(en.getKey());
		}
		return resList;
	}
}
