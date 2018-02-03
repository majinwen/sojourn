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

import java.util.HashMap;
import java.util.Map;

import com.asura.framework.base.util.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HouseStatisticsMsgEntity;

/**
 * <p>房源统计信息Dao</p>
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
@Repository("house.houseStatisticsMsgDao")
public class HouseStatisticsMsgDao {
	
	private String SQLID="house.houseStatisticsMsgDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     * 插入房源统计相关信息
     *
     * @author bushujie
     * @created 2016年5月15日 上午12:52:33
     *
     * @param houseStatisticsMsgEntity
     */
    public void insertHouseStatisticsMsg(HouseStatisticsMsgEntity houseStatisticsMsgEntity){
    	mybatisDaoContext.save(SQLID+"insertHouseStatisticsMsg", houseStatisticsMsgEntity);
    }
    
    /**
     * 
     * 条件查询房源或房间统计信息
     *
     * @author bushujie
     * @created 2016年5月15日 上午12:58:53
     *
     * @param paramMap
     * @return
     */
    public HouseStatisticsMsgEntity getHouseStatisticsMsgByParam(HouseStatisticsMsgEntity houseStatisticsMsgEntity){
    	return mybatisDaoContext.findOne(SQLID+"getHouseStatisticsMsgByParam", HouseStatisticsMsgEntity.class, houseStatisticsMsgEntity);
    }
    
    /**
     * 
     * 更新房源或者房间统计信息
     *
     * @author bushujie
     * @created 2016年5月15日 上午1:14:35
     *
     * @param paramMap
     * @return
     */
    public int updateHouseStatisticsMsgPvByParam(HouseStatisticsMsgEntity houseStatisticsMsgEntity){
    	return mybatisDaoContext.update(SQLID+"updateHouseStatisticsMsgPv", houseStatisticsMsgEntity);
    }
    //tt



    /**
     * 获取当前房东的房源数量
     * 按照房源的维度去统计
     * @author afi
     * @param landUid
     * @return
     */
    public Long countLandHouseNum(String landUid){
        Map<String,Object> par = new HashMap<>();
        par.put("landUid",landUid);
        return mybatisDaoContext.count(SQLID + "countLandHouseNum", par);

    }


    /**
     * 获取当前房东的房源数量
     * 按照房源的sku
     * @author afi
     * @param landUid
     * @return
     */
    public Long countLandHouseSkuNum(String landUid) {
        Map<String,Object> par = new HashMap<>();
        par.put("landUid",landUid);
        return mybatisDaoContext.count(SQLID + "countLandHouseSkuNum", par);
    }

}
