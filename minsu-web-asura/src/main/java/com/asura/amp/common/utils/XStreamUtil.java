/**
 * @FileName: XstreamUtil.java
 * @Package com.asura.lscm.common.util
 * 
 * @author zhangshaobin
 * @created 2013-4-16 上午10:27:38
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.common.utils;

import com.asura.framework.base.entity.BaseEntity;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * <p>
 * xml字符串转换成java对象, java对象转换成xml字符串
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public class XStreamUtil {

	/**
	 * 
	 * java实体对象转换成xml字符串
	 * 
	 * @author zhangshaobin
	 * @created 2013-4-16 上午10:33:07
	 * 
	 * @param entity
	 * @param rootAlias
	 * @return
	 */
	public static <T extends BaseEntity> String entityToXmlStr(T entity) {
		XStream xStream = new XStream(new DomDriver("UTF-8"));
		xStream.processAnnotations(entity.getClass());
		return xStream.toXML(entity);
	}

	/**
	 * java实体对象转换成xml字符串(并处理多下划线的BUG)
	 *
	 * @author zhangshaobin
	 * @created 2013-5-2 上午9:46:18
	 *
	 * @param entity
	 * @return
	 */
	public static <T extends BaseEntity> String entityToXmlStrAndDelUnderscore(T entity) {
		String xmlStr = entityToXmlStr(entity);
		return xmlStr.replaceAll("__", "_");
	}

	/**
	 * 
	 * xml字符串转换成java对象
	 * 
	 * @author zhangshaobin
	 * @created 2013-4-17 下午3:49:17
	 * 
	 * @param xml
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends BaseEntity> T xmlStrToEntity(String xml, Class<T> clazz) {
		XStream xStream = new XStream(new DomDriver("UTF-8"));
		xStream.processAnnotations(clazz);
		return (T) xStream.fromXML(xml);
	}

}
