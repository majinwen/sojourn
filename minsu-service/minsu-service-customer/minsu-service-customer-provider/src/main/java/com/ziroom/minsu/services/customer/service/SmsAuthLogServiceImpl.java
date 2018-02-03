/**
 * @FileName: SmsAuthLogServiceImpl.java
 * @Package com.ziroom.minsu.services.customer.service
 * 
 * @author bushujie
 * @created 2016年4月22日 上午10:22:51
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ziroom.minsu.entity.customer.SmsAuthLogEntity;
import com.ziroom.minsu.services.customer.dao.SmsAuthLogDao;
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
@Service("customer.smsAuthLogServiceImpl")
public class SmsAuthLogServiceImpl {
	
	@Resource(name="customer.smsAuthLogDao")
	private SmsAuthLogDao smsAuthLogDao;
	
	
	/**
	 * 
	 * 插入认证短信发送记录
	 *
	 * @author bushujie
	 * @created 2016年4月22日 上午10:31:57
	 *
	 * @param smsAuthLogEntity
	 */
	public void insertSmsAuthLog(SmsAuthLogEntity smsAuthLogEntity){
		smsAuthLogDao.insertSmsAuthLog(smsAuthLogEntity);
	}
	
	/**
	 * 
	 * 手机验证是否通过
	 *
	 * @author bushujie
	 * @created 2016年4月22日 下午3:57:42
	 *
	 * @param mobileNo
	 * @param authCode
	 * @return
	 */
	public boolean findMobileVerifyResult(String mobileNo,String authCode ){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("mobileNo", mobileNo);
		paramMap.put("authCode", authCode);
		paramMap.put("nowDate", new Date());
		int count=smsAuthLogDao.getSmsAuthLogCountByMobile(paramMap);
		if(count>0){
			return true;
		} else {
			return false;
		}
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
    	return smsAuthLogDao.getSmsAuthLogCountByCondition(smsAuthLogDto);
    }
    
}
