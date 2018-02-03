/**
 * @FileName: ObjectReflectUtils.java
 * @Package com.ziroom.minsu.services.common.utils
 * 
 * @author yd
 * @created 2017年6月30日 下午4:54:00
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.common.utils;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.services.common.entity.CalendarDataVo;

/**
 * <p>类 反射工具</p>
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
public class ClassReflectUtils {

	/**
	 * 
	 *  获取 类属性的路径  路径=类包路径+类别+属性的getXXX方法名称
	 *
	 * @author yd
	 * @created 2017年6月30日 下午5:11:37
	 *
	 * @param cla
	 * @return
	 */
	public static String getFieldNamePath(Class<?> cla,String fieldName){


		StringBuilder fieldPath = new StringBuilder("");
		if(!Check.NuNObj(cla)&&
				!Check.NuNStr(fieldName)){
			String packagePath = cla.getName();
			if(!Check.NuNStr(packagePath)){
				fieldPath.append(packagePath+"."+fieldName);
			}
		}
		return fieldPath.toString();
	}
	public static void main(String[] args) {

		System.out.println(getFieldNamePath(CalendarDataVo.class,"houseSn"));
	}
}
