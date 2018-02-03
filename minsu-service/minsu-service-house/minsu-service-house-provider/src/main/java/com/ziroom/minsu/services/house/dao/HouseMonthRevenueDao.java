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
import com.ziroom.minsu.entity.house.HouseMonthRevenueEntity;
import com.ziroom.minsu.services.house.dto.LandlordRevenueDto;
import com.ziroom.minsu.services.house.entity.HouseMonthRevenueVo;

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
@Repository("house.houseMonthRevenueDao")
public class HouseMonthRevenueDao {
	
    private String SQLID="house.houseMonthRevenueDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     * 插入房源月收益
     *
     * @author bushujie
     * @created 2016年4月11日 下午5:08:07
     *
     * @param houseDayRevenueEntity
     */
    public void insertHouseMonthRevenue(HouseMonthRevenueEntity houseMonthRevenue){
    	mybatisDaoContext.save(SQLID+"insertHouseMonthRevenue", houseMonthRevenue);
    }

	/**
	 * 查询房源月收益收益列表
	 *
	 * @author liujun
	 * @created 2016年4月28日 下午2:18:05
	 *
	 * @param landlordRevenueDto
	 * @return
	 */
	public List<HouseMonthRevenueVo> findMonthRevenueListByHouseBaseFid(LandlordRevenueDto landlordRevenueDto) {
		return mybatisDaoContext.findAll(SQLID + "findMonthRevenueListByHouseBaseFid", HouseMonthRevenueVo.class,
				landlordRevenueDto);
	}

	/**
	 * 查询房源逻辑id集合
	 *
	 * @author liujun
	 * @created 2016年4月28日 下午11:11:53
	 *
	 * @param statisticsDateYear
	 * @param statisticsDateMonth
	 * @return
	 */
	public List<String> findHouseBaseFidListFromHouseMonthRevenue(Integer statisticsDateYear,
			Integer statisticsDateMonth) {
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("statisticsDateYear", statisticsDateYear);
		paramMap.put("statisticsDateMonth", statisticsDateMonth);
		return mybatisDaoContext.findAll(SQLID + "findHouseBaseFidListFromHouseMonthRevenue", String.class, paramMap);
	}

	/**
	 * 更新房源月收益
	 *
	 * @author liujun
	 * @created 2016年4月29日 上午12:14:12
	 *
	 * @param houseMonthRevenue
	 */
	public int updateHouseMonthRevenue(HouseMonthRevenueEntity houseMonthRevenue) {
		return mybatisDaoContext.update(SQLID + "updateHouseMonthRevenue", houseMonthRevenue);
	}

	/**
	 * 根据房源月收益逻辑id查询房源月收益信息
	 *
	 * @author liujun
	 * @created 2016年4月29日 上午12:18:16
	 *
	 * @param landlordRevenueDto
	 * @return
	 */
	public HouseMonthRevenueEntity findOneHouseMonthRevenue(LandlordRevenueDto landlordRevenueDto) {
		return mybatisDaoContext.findOneSlave(SQLID + "findOneHouseMonthRevenue", HouseMonthRevenueEntity.class,
				landlordRevenueDto);
	}
}
