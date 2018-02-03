/**
 * @FileName: HouseUpdateHistoryExtLogDao.java
 * @Package com.ziroom.minsu.services.house.dao
 * 
 * @author yd
 * @created 2017年6月30日 下午4:30:39
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HouseUpdateHistoryExtLogEntity;

/**
 * <p>修改房源记录大字段信息 数据持久</p>
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
@Repository("house.houseUpdateHistoryExtLogDao")
public class HouseUpdateHistoryExtLogDao {

	private String SQLID="house.houseUpdateHistoryExtLogDao.";

	@Autowired
	@Qualifier("house.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	
	/**
	 * 
	 * 保存 
	 *
	 * @author yd
	 * @created 2017年6月30日 下午4:34:42
	 *
	 * @param houseUpdateHistoryExtLog
	 */
	public int saveHouseUpdateHistoryExtLog(HouseUpdateHistoryExtLogEntity houseUpdateHistoryExtLog){
		return mybatisDaoContext.save(SQLID+"saveHouseUpdateHistoryExtLog", houseUpdateHistoryExtLog);
	}
}
