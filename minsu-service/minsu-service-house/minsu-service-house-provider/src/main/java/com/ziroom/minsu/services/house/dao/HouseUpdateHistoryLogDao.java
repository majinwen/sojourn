/**
 * @FileName: HouseUpdateHistoryLogDao.java
 * @Package com.ziroom.minsu.services.house.dao
 * 
 * @author yd
 * @created 2017年6月30日 下午4:32:16
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HouseUpdateHistoryLogEntity;

/**
 * <p>房源基本信息修改记录 数据持久</p>
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
@Repository("house.houseUpdateHistoryLogDao")
public class HouseUpdateHistoryLogDao {

	private String SQLID="house.houseUpdateHistoryLogDao.";

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
	 * @param houseUpdateHistoryLog
	 */
	public int saveHouseUpdateHistoryLog(HouseUpdateHistoryLogEntity houseUpdateHistoryLog){
		
		if(Check.NuNObj(houseUpdateHistoryLog)){
			return 0;
		}
		
		if(Check.NuNStr(houseUpdateHistoryLog.getFid())) 
			houseUpdateHistoryLog.setFid(UUIDGenerator.hexUUID());
		return mybatisDaoContext.save(SQLID+"saveHouseUpdateHistoryLog", houseUpdateHistoryLog);
	}
	
	/**
	 * 
	 * 通过 fieldPathKey 查询 当前房源的 修改记录 默认排序
	 *
	 * @author yd
	 * @created 2017年7月3日 下午5:25:29
	 *
	 * @param fieldPathKey
	 * @return
	 */
	public List<HouseUpdateHistoryLogEntity> findListHouseUpdateHistoryLogByKey(String fieldPathKey){
		return mybatisDaoContext.findAllByMaster(SQLID+"findListHouseUpdateHistoryLogByKey", HouseUpdateHistoryLogEntity.class, fieldPathKey);
	}
	
	/**
	 * 
	 * 通过 fieldPathKey 查询 当前房源的  最新一条修改记录
	 *
	 * @author yd
	 * @created 2017年7月3日 下午5:34:12
	 *
	 * @param fieldPathKey
	 * @return
	 */
	public HouseUpdateHistoryLogEntity findLastOneByKey(String fieldPathKey){
		
		return mybatisDaoContext.findOne(SQLID+"findLastOneByKey", HouseUpdateHistoryLogEntity.class, fieldPathKey);
	}
}
