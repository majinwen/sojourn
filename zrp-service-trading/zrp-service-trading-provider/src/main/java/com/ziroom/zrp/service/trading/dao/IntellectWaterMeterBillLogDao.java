/**
 * @FileName: IntellectWaterBillLogDao.java
 * @Package com.ziroom.zrp.service.trading.dao
 * 
 * @author bushujie
 * @created 2018年1月30日 下午5:38:12
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.zrp.service.trading.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.trading.entity.IntellectWaterMeterBillLogEntity;

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
@Repository("trading.IntellectWaterMeterBillLogDao")
public class IntellectWaterMeterBillLogDao {
	
    private String SQLID = "trading.IntellectWaterMeterBillLogDao.";

    @Autowired
    @Qualifier("trading.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     * 查询水费应收账单的生成明细记录根据应收账单fid
     *
     * @author bushujie
     * @created 2018年1月30日 下午5:48:37
     *
     * @param billFid
     * @return
     */
    public IntellectWaterMeterBillLogEntity getIntellectWaterMeterBillLog(String billFid){
    	return mybatisDaoContext.findOneSlave(SQLID+"getIntellectWaterMeterBillLogByBillFid", IntellectWaterMeterBillLogEntity.class, billFid);
    }
    
    /**
     * 
     * 插入
     *
     * @author bushujie
     * @created 2018年1月31日 上午10:20:43
     *
     * @param intellectWaterMeterBillLogEntity
     */
    public void insertIntellectWaterMeterBillLog(IntellectWaterMeterBillLogEntity intellectWaterMeterBillLogEntity){
    	mybatisDaoContext.save(SQLID+"insert", intellectWaterMeterBillLogEntity);
    }
}
