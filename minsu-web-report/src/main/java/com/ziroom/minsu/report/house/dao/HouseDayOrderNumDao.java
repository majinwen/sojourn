/**
 * @FileName: HouseDayOrderNumDao.java
 * @Package com.ziroom.minsu.report.afi.dao
 * 
 * @author bushujie
 * @created 2016年9月26日 上午11:25:28
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.house.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.report.house.entity.HouseDayOrderNumEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * <p>房源日增订单量Dao</p>
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
@Repository("report.houseDayOrderNumDao")
public class HouseDayOrderNumDao {
	
    private String SQLID="report.houseDayOrderNumDao.";

    @Autowired
    @Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     * 插入房源日订单量统计表
     *
     * @author bushujie
     * @created 2016年9月26日 上午11:44:51
     *
     * @param houseDayOrderNumEntity
     */
    public void insertHouseDayOrderNum(HouseDayOrderNumEntity houseDayOrderNumEntity){
    	mybatisDaoContext.save(SQLID+"insertHouseDayOrderNum", houseDayOrderNumEntity);
    }
}
