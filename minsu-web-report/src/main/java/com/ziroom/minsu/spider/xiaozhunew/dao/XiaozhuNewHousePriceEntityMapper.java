/**
 * @FileName: XiaozhuNewHousePriceEntityMapper.java
 * @Package com.ziroom.minsu.spider.xiaozhunew.dao
 * 
 * @author zl
 * @created 2016年10月21日 下午9:23:15
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.xiaozhunew.dao;

import java.util.Date;
import java.util.List;

import com.ziroom.minsu.spider.xiaozhunew.entity.XiaozhuNewHousePriceEntity;


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
public interface XiaozhuNewHousePriceEntityMapper {
	
	public  int  insert(XiaozhuNewHousePriceEntity housePrice);
	
	public  int  updateByPrimaryKeySelective(XiaozhuNewHousePriceEntity housePrice);
	
	public  List<XiaozhuNewHousePriceEntity>  findByHouseSn(String houseSn);
	
	public  XiaozhuNewHousePriceEntity  findByHouseSnDate(String houseSn,Date date);
	
	public  int delByHouseSn(String houseSn);

}
