/**
 * @FileName: AuthIdentifyServiceImpl.java
 * @Package com.ziroom.minsu.services.basedata.service
 * 
 * @author lunan
 * @created 2017年8月31日 下午2:46:32
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ziroom.minsu.entity.base.AuthIdentifyEntity;
import com.ziroom.minsu.services.basedata.dao.AuthIdentifyDao;

/**
 * <p>自如网授权标示实现类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
@Service("basedata.authIdentifyServiceImpl")
public class AuthIdentifyServiceImpl {
	
	@Resource(name="basedata.authIdentifyDao")
	private AuthIdentifyDao authIdentifyDao;

	/**
	 * 获取对象
	 *
	 * @author loushuai
	 * @created 2017年8月31日 下午2:59:11
	 *
	 * @param authIdentify
	 * @return
	 */
	public AuthIdentifyEntity getByCode(AuthIdentifyEntity authIdentify) {
		return authIdentifyDao.getByParam(authIdentify);
	}
	
	

}
