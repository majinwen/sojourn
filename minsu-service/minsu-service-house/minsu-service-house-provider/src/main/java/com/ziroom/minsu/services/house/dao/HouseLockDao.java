/**
 * @FileName: HouseLockDao.java
 * @Package com.ziroom.minsu.services.house.dao
 * 
 * @author bushujie
 * @created 2017年3月2日 下午8:36:06
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HouseLockEntity;

/**
 * <p>房源锁业务</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@Repository("house.houseLockDao")
public class HouseLockDao {
	
    private String SQLID="house.houseLockDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     *插入房源业务锁
     *
     * @author bushujie
     * @created 2017年3月2日 下午8:40:25
     *
     * @param houseLockEntity
     */
    public void insertHouseLock(HouseLockEntity houseLockEntity){
    	mybatisDaoContext.save(SQLID+"insertHouseLock", houseLockEntity);
    }
    
    /**
     * 
     * 条件查询房源锁记录
     *
     * @author bushujie
     * @created 2017年3月2日 下午9:03:01
     *
     * @param paramMap
     * @return
     */
    public List<HouseLockEntity> findHouseLock(Map<String, Object> paramMap){
    	return mybatisDaoContext.findAll(SQLID+"findHouseLock", HouseLockEntity.class, paramMap);
    }
    
    /**
     * 
     * 删除房源锁业务
     *
     * @author bushujie
     * @created 2017年3月3日 上午10:46:15
     *
     * @param paramMap
     */
    public void deleteHouseLock(Map<String, Object> paramMap){
    	mybatisDaoContext.delete(SQLID+"deleteHouseLock",paramMap);
    }
}
