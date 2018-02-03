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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HouseDayRevenueEntity;
import com.ziroom.minsu.entity.house.HouseMonthRevenueEntity;
import com.ziroom.minsu.entity.house.RoomMonthRevenueEntity;
import com.ziroom.minsu.services.house.dto.LandlordRevenueDto;
import com.ziroom.minsu.services.house.entity.LandlordRevenueVo;

/**
 * <p>房源日收益Dao</p>
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
@Repository("house.houseDayRevenueDao")
public class HouseDayRevenueDao {
	
    private String SQLID="house.houseDayRevenueDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     * 插入房源日收益
     *
     * @author bushujie
     * @created 2016年4月11日 下午5:08:07
     *
     * @param houseDayRevenueEntity
     */
    public void insertHouseDayRevenue(HouseDayRevenueEntity houseDayRevenueEntity){
    	mybatisDaoContext.save(SQLID+"insertHouseDayRevenue", houseDayRevenueEntity);
    }
    
    /**
     * 
     * 查询房东昨日收益
     *
     * @author bushujie
     * @created 2016年4月11日 下午10:30:56
     *
     */
    public Integer getYesterdayRevenueByLandlordUid(String landlordUid,String yesterDate ){
    	Map<String, Object> paramMap=new HashMap<String, Object>();
    	paramMap.put("landlordUid", landlordUid);
    	paramMap.put("statisticsDateDay", yesterDate);
    	return mybatisDaoContext.findOneSlave(SQLID+"getSumRevenueByLandlordUid", Integer.class, paramMap);
    }
    
    /**
     * 
     * 查询房东月收益
     *
     * @author bushujie
     * @created 2016年4月11日 下午10:46:07
     *
     * @param landlordUid
     * @param statisticsDateMonth
     * @return
     */
    public Integer getMonthRevenueByLandlordUid(String landlordUid,Integer statisticsDateMonth){
    	Map<String, Object> paramMap=new HashMap<String, Object>();
    	paramMap.put("landlordUid", landlordUid);
    	paramMap.put("statisticsDateMonth", statisticsDateMonth);
    	return mybatisDaoContext.findOneSlave(SQLID+"getSumRevenueByLandlordUid", Integer.class, paramMap);
    }
    
    /**
     * 
     * 查询房东累计收益
     *
     * @author bushujie
     * @created 2016年4月11日 下午10:48:36
     *
     * @param landlordUid
     * @return
     */
    public Integer getAddUpRevenueByLandlordUid(String landlordUid){
    	Map<String, Object> paramMap=new HashMap<String, Object>();
    	paramMap.put("landlordUid", landlordUid);
    	return mybatisDaoContext.findOneSlave(SQLID+"getSumRevenueByLandlordUid", Integer.class, paramMap);
    }

	/**
	 * 查询房东周收益
	 *
	 * @author liujun
	 * @created 2016年4月26日 下午3:52:36
	 *
	 * @param landlordUid
	 * @param firstDayOfWeek
	 * @param lastDayOfWeek
	 * @return
	 */
	public Integer getWeekRevenueByLandlordUid(String landlordUid, String firstDayOfWeek, String lastDayOfWeek) {
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("landlordUid", landlordUid);
		paramMap.put("firstDayOfWeek", firstDayOfWeek);
		paramMap.put("lastDayOfWeek", lastDayOfWeek);
		return mybatisDaoContext.findOneSlave(SQLID+"getSumRevenueByLandlordUid", Integer.class, paramMap);
	}

	/**
	 * 查询房东月收益列表
	 *
	 * @author liujun
	 * @created 2016年4月26日 下午4:07:13
	 *
	 * @param landlordUid
	 * @param statisticsDateYear
	 * @return
	 */
	public List<LandlordRevenueVo> getMonthRevenueListByLandlordUid(String landlordUid, Integer statisticsDateYear) {
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("landlordUid", landlordUid);
		paramMap.put("statisticsDateYear", statisticsDateYear);
		return mybatisDaoContext.findAll(SQLID + "getMonthRevenueListByLandlordUid", LandlordRevenueVo.class, paramMap);
	}

	/**
	 * 查询房东房源月收益列表
	 *
	 * @author liujun
	 * @created 2016年4月26日 下午10:18:01
	 *
	 * @param landlordRevenueDto
	 * @return
	 */
	public List<LandlordRevenueVo> findHouseRevenueListByLandlordUid(LandlordRevenueDto landlordRevenueDto) {
		return mybatisDaoContext.findAll(SQLID + "findHouseRevenueListByLandlordUid", LandlordRevenueVo.class, landlordRevenueDto);
	}
	
	/**
	 * 
	 * 查询房源月收益
	 *
	 * @author liujun
	 * @created 2016年4月27日 下午1:50:25
	 *
	 * @param houseBaseFid
	 * @param statisticsDateYear
	 * @return
	 */
	public HouseMonthRevenueEntity findHouseMonthRevenueByHouseBaseFid(LandlordRevenueDto landlordRevenueDto) {
		return mybatisDaoContext.findOneSlave(SQLID + "findHouseMonthRevenueByHouseBaseFid", HouseMonthRevenueEntity.class, landlordRevenueDto);
	}
	
	/**
	 * 
	 * 查询房间月收益列表
	 *
	 * @author liujun
	 * @created 2016年4月27日 下午1:50:25
	 *
	 * @param houseBaseFid
	 * @param statisticsDateYear
	 * @return
	 */
	public List<RoomMonthRevenueEntity> findRoomMonthRevenueListByHouseBaseFid(LandlordRevenueDto landlordRevenueDto) {
		return mybatisDaoContext.findAll(SQLID + "findRoomMonthRevenueListByHouseBaseFid", RoomMonthRevenueEntity.class, landlordRevenueDto);
	}

	/**
	 * 查询房源逻辑id集合
	 *
	 * @author liujun
	 * @created 2016年4月28日 下午10:36:04
	 *
	 * @param statisticsDateYear
	 * @param statisticsDateMonth
	 * @return
	 */
	public List<String> findHouseBaseFidListFromHouseDayRevenue(Integer statisticsDateYear, Integer statisticsDateMonth) {
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("statisticsDateYear", statisticsDateYear);
		paramMap.put("statisticsDateMonth", statisticsDateMonth);
		return mybatisDaoContext.findAll(SQLID + "findHouseBaseFidListFromHouseDayRevenue", String.class, paramMap);
	}
}
