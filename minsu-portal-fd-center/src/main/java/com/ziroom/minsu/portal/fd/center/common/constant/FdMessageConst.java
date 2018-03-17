/**
 * @FileName: MappMessageConst.java
 * @Package com.ziroom.minsu.mapp.common.constant
 * 
 * @author liujun
 * @created 2016年5月3日
 * 
 * Copyright 2016 ziroom
 */
package com.ziroom.minsu.portal.fd.center.common.constant;

import com.ziroom.minsu.services.common.constant.MessageConst;

/**
 * <p>M站消息常量</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class FdMessageConst extends MessageConst {
	
	/**
	 * 房源出租方式错误
	 */
	public static final String HOUSE_RENTWAY_ERROR = "house.rentway.error";
	
	/**
	 * 房东信息不存在
	 */
	public static final String LANDLORD_INFO_NULL= "landlord.info.null";
	
	/**
	 * 房东uid不存在
	 */
	public static final String LANDLORDUID_NULL= "landlordUid.null";
	
	/**
	 * 私有化构造方法
	 */
	private FdMessageConst () {
		
	}
}
