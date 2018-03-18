/**
 * @FileName: HouseDayOrderAcceptNumDao.java
 * @Package com.ziroom.minsu.report.afi.dao
 * 
 * @author bushujie
 * @created 2016年9月27日 上午10:36:29
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.house.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.report.house.entity.HouseDayOrderAcceptNumEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * <p>房源日订单接受量统计Dao</p>
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
@Repository("report.houseDayOrderAcceptNumDao")
public class HouseDayOrderAcceptNumDao {
	
    private String SQLID="report.houseDayOrderAcceptNumDao.";

    @Autowired
    @Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     * 房源日订单接受量统计插入
     *
     * @author bushujie
     * @created 2016年9月27日 上午10:46:48
     *
     * @param houseDayOrderAcceptNumEntity
     */
    public void insertDayOrderAcceptNum(HouseDayOrderAcceptNumEntity houseDayOrderAcceptNumEntity){
    	mybatisDaoContext.save(SQLID+"insertDayOrderAcceptNum", houseDayOrderAcceptNumEntity);
    }

}
