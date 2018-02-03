/**
 * @FileName: SmsTemplateService.java
 * @Package com.ziroom.minsu.services.basedata.service
 * 
 * @author yd
 * @created 2016年4月1日 下午2:51:53
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.conf.SmsTemplateEntity;
import com.ziroom.minsu.services.basedata.dao.SmsTemplateDao;
import com.ziroom.minsu.services.basedata.dto.SmsTemplateRequest;

/**
 * <p>短信模板服务层</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
@Service("basedata.smsTemplateServiceImpl")
public class SmsTemplateServiceImpl {


	@Resource(name = "basedata.smsTemplateDao")
	private SmsTemplateDao smsTemplateDao;
	/**
	 * 
	 * 分页查询
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:53:45
	 *
	 * @param pageRequest
	 * @return
	 */
	public PagingResult<SmsTemplateEntity> findEntityByCondition(SmsTemplateRequest smsTemplateRequest){
		
		//为null时候，默认分页参数
		if(smsTemplateRequest == null ) smsTemplateRequest = new SmsTemplateRequest();
		return smsTemplateDao.findEntityByCondition(smsTemplateRequest);
	}
	
	/**
	 * 
	 * 按id查询
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:29:55
	 *
	 * @param id
	 * @return
	 */
	public SmsTemplateEntity findEntityById(int id){
		return smsTemplateDao.findEntityById(id);
	}
	/**
	 * 
	 * 按id查询
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:29:55
	 *
	 * @param fid
	 * @return
	 */
	public SmsTemplateEntity findEntityByFid(String fid){
		return smsTemplateDao.findEntityByFid(fid);
	}
	/**
	 * 
	 * 按id删除
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:34:18
	 *
	 * @param id
	 */
	public int deleteEntityById(int id){
		
		return this.smsTemplateDao.deleteEntityById(id);
	}
	
	/**
	 * 
	 * 按fid删除
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:34:18
	 *
	 * @param fid
	 */
	public int deleteEntityByFid(String fid){
		return this.smsTemplateDao.deleteEntityByFid(fid);
	}
	/**
	 * 
	 * 按id更新
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:45:17
	 *
	 * @param id
	 */
	public int updateEntityById(SmsTemplateEntity smsTemplateEntity){
	   return  smsTemplateDao.updateEntityById(smsTemplateEntity);
		
	}
	/**
	 * 
	 * 按fid更新
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:45:17
	 *
	 * @param fid
	 */
	public int updateEntityByFid(SmsTemplateEntity smsTemplateEntity){
	   return  smsTemplateDao.updateEntityByFid(smsTemplateEntity);
		
	}
	/**
	 * 
	 * 保存实体
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:47:47
	 *
	 * @param smsTemplateEntity
	 */
	public int saveEntity(SmsTemplateEntity smsTemplateEntity){
		return this.smsTemplateDao.saveEntity(smsTemplateEntity);
	}	
	/**
	 * 
	 * 按fid查询
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:29:55
	 *
	 * @param id
	 * @returnfindEntityBySmsCode
	 */
	public SmsTemplateEntity findEntityBySmsCode(String smsCode){
		return smsTemplateDao.findEntityBySmsCode(smsCode);
	}
}
