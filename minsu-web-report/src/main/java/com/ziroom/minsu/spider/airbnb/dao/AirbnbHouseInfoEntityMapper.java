/**
 * @FileName: AirbnbHouseInfoEntityMapper.java
 * @Package com.ziroom.minsu.spider.airbnb.dao
 * 
 * @author zl
 * @created 2016年9月30日 下午4:40:48
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.airbnb.dao;

import java.util.List;

import com.ziroom.minsu.spider.airbnb.entity.AirbnbHouseInfoEntity;
import com.ziroom.minsu.spider.airbnb.entity.AirbnbHouseInfoEntityWithBLOBs;

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
public interface AirbnbHouseInfoEntityMapper {
	
	public  int  insert(AirbnbHouseInfoEntityWithBLOBs houseInfoEntity);
	
	public  int  updateByPrimaryKeySelective(AirbnbHouseInfoEntityWithBLOBs houseInfoEntity);
	
	public AirbnbHouseInfoEntityWithBLOBs selectByHouseSn(String houseSn);
	
	public List<AirbnbHouseInfoEntityWithBLOBs> selectByHostSn(String hostSn);
	
	public List<AirbnbHouseInfoEntity> selectByAvailableNull();
	
	public List<AirbnbHouseInfoEntity> selectByHouseNull();
}
