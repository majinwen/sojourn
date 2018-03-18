/**
 * @FileName: HouseDayPayOrderDao.java
 * @Package com.ziroom.minsu.report.dao
 * 
 * @author bushujie
 * @created 2016年9月26日 上午11:25:28
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.report.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.report.report.entity.ReportLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * <p>失败记录日志Dao</p>
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
@Repository("report.reportLogDao")
public class ReportLogDao {
	
    private String SQLID="report.reportLogDao.";

    @Autowired
    @Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 插入日志记录
     * @author liyingjie
     * @param hpay
     */
    public int insertReportLog(ReportLogEntity hpay){
    	if(Check.NuNObj(hpay)){
    		return -1;
    	}
    	return mybatisDaoContext.save(SQLID+"insertReportLog", hpay);
    }
}
