/**
 * @FileName: AirbnbHousePriceEntityMapper.java
 * @Package com.ziroom.minsu.spider.airbnb.dao
 * 
 * @author zl
 * @created 2016年10月12日 下午2:45:57
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.airbnb.dao;

import java.util.Date;
import java.util.List;

import com.ziroom.minsu.spider.airbnb.entity.AirbnbHousePriceEntity;

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
public interface AirbnbHousePriceEntityMapper {
	
	public  int  insert(AirbnbHousePriceEntity housePrice);
	
	public  int  updateByPrimaryKeySelective(AirbnbHousePriceEntity housePrice);
	
	public  List<AirbnbHousePriceEntity>  findByHouseSn(String houseSn);
	
	public  AirbnbHousePriceEntity  findByHouseSnDate(String houseSn,Date date);
	
	public  int delByHouseSn(String houseSn);

}
