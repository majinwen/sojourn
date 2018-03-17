/**
 * @FileName: MappMessageConst.java
 * @Package com.ziroom.minsu.mapp.common.constant
 * 
 * @author liujun
 * @created 2016年5月3日
 * 
 * Copyright 2016 ziroom
 */
package com.ziroom.minsu.mapp.common.constant;


/**
 * <p>M站常量</p>
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
public class MappConst{
	
	/**
	 * 操作成功
	 */
	public static final int OPERATION_SUCCESS = 0;
	
	/**
	 * 操作失败
	 */
	public static final int OPERATION_FAILURE = 1;

	/**
	 * 房源分享展示icon数量
	 */
	public static final int DISPLAY_ICON_NUM = 5;


	/**
	 * 满减
	 */
	public static final String ACT_COUPON_LITTLE_MSG= "满%d减%d";
	/**
	 * 描述信息
	 */
	public static final String ACT_COUPON_DETAIL_MSG= "满%d元可用，有效期%d天";
	
	/**
	 * 私有化构造方法
	 */
	private MappConst () {
		
	}
}
