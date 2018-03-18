/**
 * @FileName: AirbnbHostInfoEntityMapper.java
 * @Package com.ziroom.minsu.spider.airbnb.dao
 * 
 * @author zl
 * @created 2016年9月30日 下午4:39:46
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.airbnb.dao;

import java.util.List;

import com.ziroom.minsu.spider.airbnb.entity.AirbnbHostInfoEntity;

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
public interface AirbnbHostInfoEntityMapper {

	public  int  insert(AirbnbHostInfoEntity hostInfoEntity);
	
	public  int  updateByPrimaryKeySelective(AirbnbHostInfoEntity hostInfoEntity);
	
	public AirbnbHostInfoEntity selectByHostSn(String hostSn);
	
	public List<AirbnbHostInfoEntity> selectAll();
}
