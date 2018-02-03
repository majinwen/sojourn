/**
 * @FileName: LockVo.java
 * @Package com.ziroom.minsu.services.house.smartlock.vo
 * 
 * @author yd
 * @created 2016年6月24日 下午12:00:48
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.smartlock.vo;

/**
 * <p>一把锁的基本信息</p>
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
public class LockVo {

	/**
	 * 门锁在线状态：
     * 1：在线
     * 2：离线
	 */
	private int onoff_line;
	
	private Long onoff_time;
}
