/**
 * @FileName: DateTag.java
 * @Package com.asura.management.common.taglibs
 * 
 * @author zhangshaobin
 * @created 2013-1-16 下午5:20:41
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.common.taglibs;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspWriter;

import org.springframework.web.servlet.tags.RequestContextAwareTag;

/**
 * <p>日期处理标签</p>
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
public class DateTag extends RequestContextAwareTag {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 默认输入日期的格式化类型
	 */
	private static final String defaultFormat = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 输入日期类型
	 */
	private Date date;
	
	/**
	 * 输入字符串日期类型
	 */
	private String dateString;
	
	/**
	 * 输入Long型日期类型
	 */
	private Long dateLong;
	
	/**
	 * dateString输入值时，输入格式
	 */
	private String fromFormat;
	
	/**
	 * 输出格式化类型
	 */
	private String toFormat;
	
	/**
	 * 默认构造函数
	 */
	public DateTag() {
		date = null;
		dateString = null;
		dateLong = null;
		fromFormat = defaultFormat;
		toFormat = null;
	}
	
	@Override
	protected int doStartTagInternal() throws Exception {
		try {
			StringBuilder str = new StringBuilder();

			// 输出Writer
			JspWriter out = pageContext.getOut();
			
			// 处理输入值，转换成Date。不输入默认为当前时间
			Date d = null;
			if(date != null) {
				d = date;
			} else if(dateString != null) {
				d = new SimpleDateFormat(fromFormat).parse(dateString);
			} else if(dateLong != null) {
				d = new Date(dateLong);
			} else {
				d = new Date();
			}
			
			// 格式化处理
			str.append(new SimpleDateFormat(toFormat).format(d));
			
			// 输出
			out.write(str.toString());
		} catch (IOException ex) {
		}

		return 0;
	}

	/**
	 * 获取输入日期类型值
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:29:24
	 *
	 * @return
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * 设置输入日期类型值
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:29:50
	 *
	 * @param date		输入日期类型值
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * 获取输入字符串类型日期值
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:30:05
	 *
	 * @return
	 */
	public String getDateString() {
		return dateString;
	}

	/**
	 * 设置输入字符串类型日期值
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:30:22
	 *
	 * @param dateString	输入字符串类型日期值
	 */
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	/**
	 * 获取输入Long类型日期值
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:30:35
	 *
	 * @return
	 */
	public Long getDateLong() {
		return dateLong;
	}

	/**
	 * 设置输入Long类型日期值
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:30:48
	 *
	 * @param dateLong		输入Long类型日期值
	 */
	public void setDateLong(Long dateLong) {
		this.dateLong = dateLong;
	}

	/**
	 * 获取输入格式化
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:30:58
	 *
	 * @return
	 */
	public String getFromFormat() {
		return fromFormat;
	}

	/**
	 * 设置输入格式化
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:31:16
	 *
	 * @param fromFormat	输入格式化
	 */
	public void setFromFormat(String fromFormat) {
		this.fromFormat = fromFormat;
	}

	/**
	 * 获取输出格式化
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:31:49
	 *
	 * @return
	 */
	public String getToFormat() {
		return toFormat;
	}

	/**
	 * 设置输出格式化
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:32:03
	 *
	 * @param toFormat		输出格式化
	 */
	public void setToFormat(String toFormat) {
		this.toFormat = toFormat;
	}
}