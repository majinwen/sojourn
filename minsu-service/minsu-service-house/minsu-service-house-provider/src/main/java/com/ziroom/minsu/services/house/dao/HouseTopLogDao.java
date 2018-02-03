package com.ziroom.minsu.services.house.dao;


import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HouseTopLogEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * 
 * <p>TopColumn dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改时间	修改内容
 * </PRE>
 * 
 * @author lunan
 * @since 1.0
 * @version 1.0
 */
@Repository("house.houseTopLogDao")
public class HouseTopLogDao {


    private String SQLID="house.houseTopLogDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     * 插入top房源操作日志
     *
     * @author bushujie
     * @created 2017年3月21日 下午5:15:52
     *
     * @param houseTopLogEntity
     */
    public void insertHouseTopLog(HouseTopLogEntity houseTopLogEntity){
    	mybatisDaoContext.save(SQLID+"insertHouseTopLog", houseTopLogEntity);
    }
}
