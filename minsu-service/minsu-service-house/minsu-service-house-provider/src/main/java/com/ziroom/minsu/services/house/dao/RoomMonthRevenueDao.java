/**
 * @FileName: HouseDayRevenueDao.java
 * @Package com.ziroom.minsu.services.house.dao
 * 
 * @author bushujie
 * @created 2016年4月11日 下午4:58:32
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.RoomMonthRevenueEntity;

/**
 * <p>房源月收益Dao</p>
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
@Repository("house.roomMonthRevenueDao")
public class RoomMonthRevenueDao {
	
    private String SQLID="house.roomMonthRevenueDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     * 插入房间月收益
     *
     * @author bushujie
     * @created 2016年4月11日 下午5:08:07
     *
     * @param houseDayRevenueEntity
     */
    public void insertRoomMonthRevenue(RoomMonthRevenueEntity roomMonthRevenue){
    	mybatisDaoContext.save(SQLID+"insertRoomMonthRevenue", roomMonthRevenue);
    }
    
    /**
     * 
     * 根据房源月收益逻辑id查询房间月收益
     *
     * @author liujun
     * @created 2016年4月29日 上午10:39:15
     *
     * @param houseMonthRevenueFid
     * @return
     */
	public List<RoomMonthRevenueEntity> findRoomMonthRevenueListByHouseMonthRevenueFid(String houseMonthRevenueFid) {
		return mybatisDaoContext.findAll(SQLID + "findRoomMonthRevenueListByHouseMonthRevenueFid",
				RoomMonthRevenueEntity.class, houseMonthRevenueFid);
	}

	/**
	 * 更新房间月收益
	 *
	 * @author liujun
	 * @created 2016年4月29日 上午10:39:06
	 *
	 * @param roomMonthRevenue
	 */
	public int updateRoomMonthRevenue(RoomMonthRevenueEntity roomMonthRevenue) {
		return mybatisDaoContext.update(SQLID+"updateRoomMonthRevenue", roomMonthRevenue);
	}
}
