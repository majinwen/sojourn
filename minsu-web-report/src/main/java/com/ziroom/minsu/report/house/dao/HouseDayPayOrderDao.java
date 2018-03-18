/**
 * @FileName: HouseDayPayOrderDao.java
 * @Package com.ziroom.minsu.report.dao
 * 
 * @author bushujie
 * @created 2016年9月26日 上午11:25:28
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.house.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.report.house.entity.HouseDayPayOrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * <p>房源日增支付订单量Dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */
@Repository("report.houseDayPayOrderDao")
public class HouseDayPayOrderDao {
	
    private String SQLID="report.houseDayPayOrderDao.";

    @Autowired
    @Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 插入房源日支付订单量统计表
     * @author liyingjie
     * @param hpay
     */
    public int insertHouseDayPayOrderNum(HouseDayPayOrderEntity hpay){
    	if(Check.NuNObj(hpay)){
    		return -1;
    	}
    	return mybatisDaoContext.save(SQLID+"insertHouseDayPayOrder", hpay);
    }
}
