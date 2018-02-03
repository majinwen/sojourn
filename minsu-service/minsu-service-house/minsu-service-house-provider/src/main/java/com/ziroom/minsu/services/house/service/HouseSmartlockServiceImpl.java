/**
 * @FileName: HouseSmartlockServiceImpl.java
 * @Package com.ziroom.minsu.services.house.service
 * 
 * @author jixd
 * @created 2016年6月24日 上午10:26:13
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.service;


import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HouseSmartlockEntity;
import com.ziroom.minsu.services.house.dao.HouseBaseMsgDao;
import com.ziroom.minsu.services.house.dao.HouseSmartlockDao;


/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
@Service("house.houseSmartlockServiceImpl")
public class HouseSmartlockServiceImpl {
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseSmartlockServiceImpl.class);
	
	@Resource(name="house.houseSmartlockDao")
	private HouseSmartlockDao houseSmartlockDao;
	
	/**
	 * 
	 * 插入记录
	 *
	 * @author jixd
	 * @created 2016年6月24日 上午10:31:04
	 *
	 * @return
	 */
	public int saveHouseSmartLock(HouseSmartlockEntity houseSmartlockEntity){
		houseSmartlockEntity.setFid(UUIDGenerator.hexUUID());
		return houseSmartlockDao.saveHouseSmartlock(houseSmartlockEntity);
	}
	/**
	 * 
	 * 更新记录
	 *
	 * @author jixd
	 * @created 2016年6月24日 上午10:31:23
	 *
	 * @param houseSmartlockEntity
	 * @return
	 */
	public int updateHouseSmartlock(HouseSmartlockEntity houseSmartlockEntity){
		return houseSmartlockDao.updateHouseSmartlock(houseSmartlockEntity);
	}
	
	/**
	 * 
	 * 查找记录
	 *
	 * @author jixd
	 * @created 2016年6月24日 上午10:31:33
	 *
	 * @param houseSmartlockEntity
	 * @return
	 */
	public List<HouseSmartlockEntity> findHouseSmartlock(HouseSmartlockEntity houseSmartlockEntity){
		return houseSmartlockDao.findHouseSmartlock(houseSmartlockEntity);
	}
	
}
