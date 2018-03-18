/**
 * @FileName: TestDao.java
 * @Package com.ziroom.minsu.report.dao
 * 
 * @author bushujie
 * @created 2016年12月30日 下午2:46:54
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.house.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;

/**
 * <p>TODO</p>
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
@Repository("report.testDao")
public class TestDao {
	
    private String SQLID="report.testDao.";

    @Autowired
    @Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     * 查时间段注册用户量
     *
     * @author bushujie
     * @created 2016年12月30日 下午3:00:35
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public int getAllRegNum(Date startDate,Date endDate){
    	Map<String, Object> paramMap=new HashMap<String,Object>();
    	paramMap.put("startDate", startDate);
    	paramMap.put("endDate", endDate);
    	return mybatisDaoContext.findOne(SQLID+"getAllRegNum", Integer.class, paramMap);
    }
    
    /**
     * 
     * 查询时间段转换房东量
     *
     * @author bushujie
     * @created 2016年12月30日 下午3:07:25
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public int getAllLandlordNum(Date startDate,Date endDate){
    	Map<String, Object> paramMap=new HashMap<String,Object>();
    	paramMap.put("startDate", startDate);
    	paramMap.put("endDate", endDate);
    	return mybatisDaoContext.findOne(SQLID+"getAllLandlordNum", Integer.class, paramMap);
    }
    
    /**
     * 
     * 注册用户有发布房源的量
     *
     * @author bushujie
     * @created 2016年12月30日 下午3:09:49
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public int getAllHouseNum(Date startDate,Date endDate){
    	Map<String, Object> paramMap=new HashMap<String,Object>();
    	paramMap.put("startDate", startDate);
    	paramMap.put("endDate", endDate);
    	return mybatisDaoContext.findOne(SQLID+"getAllHouseNum", Integer.class, paramMap);
    }
    
    /**
     * 
     *  
     *注册用户发布成功量
     * @author bushujie
     * @created 2016年12月30日 下午3:13:15
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public int getHouseSucceedNum(Date startDate,Date endDate){
    	Map<String, Object> paramMap=new HashMap<String,Object>();
    	paramMap.put("startDate", startDate);
    	paramMap.put("endDate", endDate);
    	return mybatisDaoContext.findOne(SQLID+"getHouseSucceedNum", Integer.class, paramMap);
    }
    
    /**
     * 
     * 注册用户房源审核通过量（用户）
     *
     * @author bushujie
     * @created 2016年12月30日 下午3:16:14
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public int getHouseAuthNum(Date startDate,Date endDate){
    	Map<String, Object> paramMap=new HashMap<String,Object>();
    	paramMap.put("startDate", startDate);
    	paramMap.put("endDate", endDate);
    	return mybatisDaoContext.findOne(SQLID+"getHouseAuthNum", Integer.class, paramMap);
    }
}
