/**
 * @FileName: EvaluateConsant.java
 * @Package com.ziroom.minsu.services.evaluate.constant
 * 
 * @author yd
 * @created 2016年4月7日 下午9:04:04
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.constant;

/**
 * <p>评价相关常量</p>
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
public class EvaluateConsant {
	
	/**
	 * 评价状态(1=待审核 2=已下线 3=已发布 4=已举报)
	 */
	public final static Integer EVA_STATU_AUDIT = 1;
	public final static Integer EVA_STATU_OFFLINE = 2;
	public final static Integer EVA_STATU_ONLINE = 3;
	public final static Integer EVA_STATU_REPORTED = 4;
	/**
	 * 评论数初始化参数
	 */
	public final  static int INIT_EVA_NUM = 1;
	
	/**
	 * 评价星级 5
	 */
	public final static float EVA_START_FIVE = 5;

	public final static String TENANT_DEFAULT_NAME = "房客";

}
