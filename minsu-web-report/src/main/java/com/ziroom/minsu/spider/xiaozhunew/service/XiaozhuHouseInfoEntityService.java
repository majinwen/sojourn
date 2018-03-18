/**
 * @FileName: XiaozhuHouseInfoEntityService.java
 * @Package com.ziroom.minsu.spider.xiaozhunew.service
 * 
 * @author zl
 * @created 2016年10月21日 下午9:14:51
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.xiaozhunew.service;

import java.util.List;

import com.ziroom.minsu.spider.xiaozhunew.entity.XiaozhuNewHouseInfoEntityWithBLOBs;
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
public interface XiaozhuHouseInfoEntityService {
	
	public  int  saveAirbnbHouseInfo(XiaozhuNewHouseInfoEntityWithBLOBs houseInfoEntity);
	
	public XiaozhuNewHouseInfoEntityWithBLOBs selectByHouseSn(String houseSn);
	
	public  List<XiaozhuNewHousePriceEntity>  findByHouseSn(String houseSn);

}
