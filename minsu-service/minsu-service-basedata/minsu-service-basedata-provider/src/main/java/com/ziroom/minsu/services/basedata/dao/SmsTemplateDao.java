/**
 * @FileName: SmsTemplateDao.java
 * @Package com.ziroom.minsu.services.basedata
 * 
 * @author yd
 * @created 2016年4月1日 下午1:56:47
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.dao;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.SmsTemplateEntity;
import com.ziroom.minsu.services.basedata.dto.SmsTemplateRequest;

/**
 * <p>短信模板持久化层</p>
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
@Repository("basedata.smsTemplateDao")
public class SmsTemplateDao {

	/**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(SmsTemplateDao.class);
	private String SQLID = "basedata.smsTemplateDao.";

	@Autowired
	@Qualifier("basedata.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 
	 * 条件分页查询
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:22:05
	 *
	 * @param pageRequest
	 * @return
	 */
	public PagingResult<SmsTemplateEntity> findEntityByCondition(SmsTemplateRequest smsTemplateRequest){
		
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(smsTemplateRequest.getLimit());
		pageBounds.setPage(smsTemplateRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "findEntityByCondition", SmsTemplateEntity.class, smsTemplateRequest, pageBounds) ;//mybatisDaoContext.findForPage(SQLID + "findEntityByCondition", SmsTemplateEntity.class, smsTemplateRequest,pageBounds);
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
		return mybatisDaoContext.findOneSlave(SQLID+"selectByPrimaryKey", SmsTemplateEntity.class, id);
	}
	/**
	 * 
	 * 按fid查询
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:29:55
	 *
	 * @param id
	 * @return
	 */
	public SmsTemplateEntity findEntityByFid(String fid){
		return mybatisDaoContext.findOneSlave(SQLID+"findEntityByFid", SmsTemplateEntity.class, fid);
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
		return mybatisDaoContext.findOneSlave(SQLID+"findEntityBySmsCode", SmsTemplateEntity.class, smsCode);
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
		
		Map<String, Object> paramsMap = new HashMap<String, Object>();		
		paramsMap.put("id", id);
		return this.mybatisDaoContext.delete(SQLID+"deleteByPrimaryKey",paramsMap);
	}
	/**
	 * 
	 * 按fid删除
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:34:18
	 *
	 * @param id
	 */
	public int deleteEntityByFid(String fid){
		if(Check.NuNStr(fid)){
			LogUtil.info(logger, "fid is null");
			return 0;
		}
		Map<String, Object> paramsMap = new HashMap<String, Object>();		
		paramsMap.put("fid", fid);
		return this.mybatisDaoContext.delete(SQLID+"deleteEntityByFid",paramsMap);
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
		if(Check.NuNObj(smsTemplateEntity)||Check.NuNObj(smsTemplateEntity.getId())){
			LogUtil.info(logger, "待更新的实体smsTemplateEntity={}", smsTemplateEntity);
			return 0;
		}
	   return  this.mybatisDaoContext.update(SQLID+"updateByPrimaryKeySelective", smsTemplateEntity);
		
	}
	/**
	 * 
	 * 按fid更新
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:45:17
	 *
	 * @param id
	 */
	public int updateEntityByFid(SmsTemplateEntity smsTemplateEntity){
		if(Check.NuNObj(smsTemplateEntity)||Check.NuNStr(smsTemplateEntity.getFid())){
			LogUtil.info(logger, "待更新的实体smsTemplateEntity={}", smsTemplateEntity);
			return 0;
		}
	   return  this.mybatisDaoContext.update(SQLID+"updateEntityByFid", smsTemplateEntity);
		
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
		
		if(Check.NuNObj(smsTemplateEntity)){
			LogUtil.info(logger ,"实体对象为null");
			return -1;
		}
		if(Check.NuNStr(smsTemplateEntity.getFid())) smsTemplateEntity.setFid(UUIDGenerator.hexUUID());
		return this.mybatisDaoContext.update(SQLID+"insertSelective", smsTemplateEntity);
	}	
}
