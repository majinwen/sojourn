/**
 * @FileName: HouseBookRateDao.java
 * @Package com.ziroom.minsu.services.house.dao
 * 
 * @author bushujie
 * @created 2016年4月11日 下午4:08:24
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HouseBookRateEntity;

/**
 * <p>房源未来30天预订率Dao</p>
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
@Repository("house.houseBookRateDao")
public class HouseBookRateDao {
	
    private String SQLID="house.houseBookRateDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     * 插入房源未来30天预订率
     *
     * @author bushujie
     * @created 2016年4月11日 下午4:12:16
     *
     * @param houseBookRateEntity
     */
    public void insertHouseBookRate(HouseBookRateEntity houseBookRateEntity){
    	mybatisDaoContext.save(SQLID+"insertHouseBookRate", houseBookRateEntity);
    }
    
    /**
     * 
     * 更新房源未来30天预订率
     *
     * @author bushujie
     * @created 2016年4月11日 下午4:25:20
     *
     * @param houseBookRateEntity
     * @return
     */
    public int updateHouseBookRate(HouseBookRateEntity houseBookRateEntity){
    	return mybatisDaoContext.update(SQLID+"updateHouseBookRate", houseBookRateEntity);
    }

}
