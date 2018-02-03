/**
 * @FileName: HouseStatisticsMsgDao.java
 * @Package com.ziroom.minsu.services.house.dao
 * 
 * @author bushujie
 * @created 2016年5月15日 上午12:45:38
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HouseStatsDayMsgEntity;

/**
 * <p>房源统计信息Dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
@Repository("house.houseStatsMsgDao")
public class HouseStatsDayMsgDao {
	
	private String SQLID="house.houseStatsMsgDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     * 新增房源统计信息
     *
     * @author liujun
     * @created 2016年12月2日
     *
     * @param houseStatsMsgEntity
     */
    public int insertHouseStatisticsMsg(HouseStatsDayMsgEntity houseStatsMsgEntity){
    	return mybatisDaoContext.save(SQLID+"insertHouseStatisticsMsg", houseStatsMsgEntity);
    }
    
    /**
     * 
     * 条件删除统计信息
     *
     * @author liujun
     * @created 2016年12月5日
     *
     * @param houseStatsMsgEntity
     */
    public int deleteHouseStatsMsgByStatsDate(Date statsDate){
    	HouseStatsDayMsgEntity entity = new HouseStatsDayMsgEntity();
		entity.setStatsDate(statsDate);
    	return mybatisDaoContext.delete(SQLID+"deleteHouseStatsMsgByStatsDate", entity.toMap());
    }
}
