/**
 * @FileName: XiaozhuNewHouseInfoEntityMapper.java
 * @Package com.ziroom.minsu.spider.xiaozhunew.dao
 * 
 * @author zl
 * @created 2016年10月21日 下午9:03:16
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.xiaozhunew.dao;

import java.util.List;

import com.ziroom.minsu.spider.xiaozhunew.entity.XiaozhuNewHouseInfoEntityWithBLOBs;


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
public interface XiaozhuNewHouseInfoEntityMapper {
	
	public  int  insert(XiaozhuNewHouseInfoEntityWithBLOBs houseInfoEntity);
	
	public  int  updateByPrimaryKeySelective(XiaozhuNewHouseInfoEntityWithBLOBs houseInfoEntity);
	
	public XiaozhuNewHouseInfoEntityWithBLOBs selectByHouseSn(String houseSn);
	
	public List<XiaozhuNewHouseInfoEntityWithBLOBs> selectByHostSn(String hostSn);

}
