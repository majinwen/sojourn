/**
 * @FileName: SmsAuthLogDao.java
 * @Package com.ziroom.minsu.services.customer.dao
 * 
 * @author bushujie
 * @created 2016年4月21日 下午10:24:53
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.customer.SmsAuthLogEntity;
import com.ziroom.minsu.services.customer.dto.SmsAuthLogDto;

/**
 * <p>手机验证短信记录</p>
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
@Repository("customer.smsAuthLogDao")
public class SmsAuthLogDao {
	
    private String SQLID="customer.smsAuthLogDao.";

    @Autowired
    @Qualifier("customer.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    
    /**
     * 
     * 插入认证短信发送记录
     *
     * @author bushujie
     * @created 2016年4月21日 下午10:50:08
     *
     * @param smsAuthLogEntity
     */
    public void insertSmsAuthLog(SmsAuthLogEntity smsAuthLogEntity){
    	mybatisDaoContext.save(SQLID+"insertSmsAuthLog", smsAuthLogEntity);
    }
    
    /**
     * 
     * 手机号和验证码查询数量
     *
     * @author bushujie
     * @created 2016年4月22日 下午2:46:02
     *
     * @param paramMap
     * @return
     */
    public int getSmsAuthLogCountByMobile(Map<String, Object> paramMap){
    	return mybatisDaoContext.findOne(SQLID+"getSmsAuthLogCountByMobile", Integer.class, paramMap);
    }
    /**
     * 
     * 条件查询 返回数量
     *
     * @author yd
     * @created 2016年5月10日 下午3:55:36
     *
     * @param smsAuthLogDto
     * @return
     */
    public int getSmsAuthLogCountByCondition(SmsAuthLogDto smsAuthLogDto){
    	return mybatisDaoContext.findOne(SQLID+"getSmsAuthLogCountByCondition", Integer.class, smsAuthLogDto);
    }
}
