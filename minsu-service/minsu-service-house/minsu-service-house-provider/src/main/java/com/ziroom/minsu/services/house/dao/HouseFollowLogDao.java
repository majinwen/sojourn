/**
 * @FileName: HouseFollowLogDao.java
 * @Package com.ziroom.minsu.services.house.dao
 * 
 * @author bushujie
 * @created 2017年2月27日 下午8:04:02
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HouseFollowLogEntity;

/**
 * <p>房源跟进明细DAO</p>
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
@Repository("house.houseFollowLogDao")
public class HouseFollowLogDao {
	
    private String SQLID="house.houseFollowLogDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     * 插入房源跟进明细
     *
     * @author bushujie
     * @created 2017年2月27日 下午8:09:45
     *
     * @param houseFollowLogEntity
     */
    public void insertHouseFollowLog(HouseFollowLogEntity houseFollowLogEntity){
    	mybatisDaoContext.save(SQLID+"insertHouseFollowLog", houseFollowLogEntity);
    }
}
