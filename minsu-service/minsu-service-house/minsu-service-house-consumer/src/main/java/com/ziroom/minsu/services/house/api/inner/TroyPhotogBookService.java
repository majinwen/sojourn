/**
 * @FileName: TroyPhotogBookService.java
 * @Package com.ziroom.minsu.services.house.api.inner
 * 
 * @author yd
 * @created 2016年11月8日 上午9:06:57
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.api.inner;

import com.asura.framework.base.exception.SOAParseException;


/**
 * <p>troy后台摄影预约管理</p>
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
public interface TroyPhotogBookService {

	/**
	 * 
	 *  查询需要拍照的房源信息
	 *  
	 *  说明： 
	 *   1.摄影师针对房源
	 *   2.分租，只要有有一间房间被预约摄影，整个房源就算已预约摄影（特殊情况 线下处理）
	 *   3.分租： 多个房间编号以逗号隔开  房间名称 以分号隔开
	 *   
	 *   问题：（此处查询用的 是联合查询  数据量大了之后，会有性能影响，但平台房源达不到大数据量的级别）
	 *
	 * @author yd
	 * @created 2016年11月8日 上午9:07:57
	 *
	 * @param dtoVoJson
	 * @return
	 */
	public String findNeedPhotographerHouse(String dtoVoJson);
	
	/**
	 * 
	 * 预约房源摄影师
	 * 
	 * 说明：
	 * 参数： 房源 fid  预约摄影开始时间  预约摄影结束时间
	 *
	 * @author yd
	 * @created 2016年11月8日 上午9:44:37
	 *
	 * @param dtoVoJson
	 * @return
	 */
	public String bookHousePhotographer(String dtoVoJson);
	
	/**
	 * 
	 *  1.指定摄影师 或 2. 摄影师摄影完成
	 *
	 * @author yd
	 * @created 2016年11月8日 下午9:40:37
	 *
	 * @return
	 */
	public String updatePhotographerBookOrderBySn(String dtoVoJson);

	/**
	 * 
     * 查询摄影师预约订单
	 * @author zl
	 * @param paramJson
	 * @return
	 */
	public String findPhotographerBookOrder(String paramJson);

	/**
	 *
	 * 查询预约单详情
	 * @author lunan
	 * @param paramJson
	 * @return
	 */
	public String findPhotogOrderDetail(String paramJson);

	/**
	 *
	 * 查询品质审核未通过原因
	 * @author lunan
	 * @param param
	 * @return
	 */
	public String findHouseBizReason(String param);

	/**
	 * 查询预约摄影单的日志记录
	 * @param paramJson
	 * @return
     */
	public String findLogs(String paramJson);
	
	/**
	 * 
	 * 通过房源fid查询摄影师预约订单
	 *
	 * @author baiwei
	 * @created 2017年4月12日 下午9:05:37
	 *
	 * @param houseFid
	 * @return
	 */
	public String findBookOrderByHouseFid(String houseFid);
}
