/**
 * @FileName: HouseStatusDataDao.java
 * @Package com.ziroom.minsu.report.dao
 * 
 * @author bushujie
 * @created 2016年9月28日 下午3:18:34
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.house.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.report.house.entity.HouseStatusDataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * <p>房源状态变化周期Dao</p>
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
@Repository("report.houseStatusDataDao")
public class HouseStatusDataDao {
	
	private String SQLID="report.houseStatusDataDao.";
	
    @Autowired
    @Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext; 
    
    /**
     * 
     * 插入房源周期变化记录
     *
     * @author bushujie
     * @created 2016年9月28日 下午3:40:05
     *
     * @param houseStatusDataEntity
     */
    public void insertHouseStatusData(HouseStatusDataEntity houseStatusDataEntity){
    	mybatisDaoContext.save(SQLID+"insertHouseStatusData", houseStatusDataEntity);
    }
    
    /**
     * 
     * 查询旧状态开始时间
     *
     * @author bushujie
     * @created 2016年9月30日 下午12:01:37
     *
     * @param paramMap
     * @return
     */
    public HouseStatusDataEntity getOldStatusStart(Map<String, Object> paramMap){
    	return mybatisDaoContext.findOne(SQLID+"getOldStatusStart", HouseStatusDataEntity.class, paramMap);
    }
}
