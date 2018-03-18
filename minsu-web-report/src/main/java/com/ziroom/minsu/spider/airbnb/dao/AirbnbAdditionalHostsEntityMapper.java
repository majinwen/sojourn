/**
 * @FileName: AirbnbAdditionalHostsEntityMapper.java
 * @Package com.ziroom.minsu.spider.airbnb.dao
 * 
 * @author zl
 * @created 2016年10月12日 下午2:46:59
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.airbnb.dao;

import java.util.List;

import com.ziroom.minsu.spider.airbnb.entity.AirbnbAdditionalHostsEntity;

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
public interface AirbnbAdditionalHostsEntityMapper {
	
	public  int  insert(AirbnbAdditionalHostsEntity additionalHosts);
	
	public  int  updateByPrimaryKeySelective(AirbnbAdditionalHostsEntity additionalHosts);
	
	public  AirbnbAdditionalHostsEntity  findByAdditionalHostSn(String additionalHostSn);
	
	public  List<AirbnbAdditionalHostsEntity>  findByHouseSn(String houseSn);
	
	public  List<AirbnbAdditionalHostsEntity>  findByHostSn(String hostSn);
	
	public  List<AirbnbAdditionalHostsEntity>  findAll();

}
