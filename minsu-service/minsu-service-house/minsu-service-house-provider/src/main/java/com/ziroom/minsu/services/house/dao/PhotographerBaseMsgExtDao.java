/**
 * @FileName: PhotographerBaseMsgExtDao.java
 * @Package com.ziroom.minsu.services.house.dao
 * 
 * @author yd
 * @created 2016年11月4日 下午2:53:45
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.photographer.PhotographerBaseMsgExtEntity;

/**
 * <p>摄影师 基本信息扩展信息</p>
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
@Component("photographer.photographerBaseMsgExtDao")
public class PhotographerBaseMsgExtDao{

	private String SQLID="photographer.photographerBaseMsgExtDao.";

	@Autowired
	@Qualifier("house.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	
	/**
	 * 
	 * 保存实体
	 *
	 * @author yd
	 * @created 2016年11月4日 下午2:07:57
	 *
	 * @param photographerBaseMsg
	 * @return
	 */
	public int savePhotographerBaseMsgExt(PhotographerBaseMsgExtEntity photographerBaseMsgExt){
		
		if(!Check.NuNObj(photographerBaseMsgExt)&&!Check.NuNStrStrict(photographerBaseMsgExt.getPhotographerUid())){
			if(Check.NuNStrStrict(photographerBaseMsgExt.getFid())) photographerBaseMsgExt.setFid(UUIDGenerator.hexUUID());
		    return this.mybatisDaoContext.save(SQLID+"savePhotographerBaseMsgExt", photographerBaseMsgExt);
		}
		return 0;
	}
	
	/**
	 * 
	 * 根据摄影师uid 修改摄影师扩展信息
	 *
	 * @author yd
	 * @created 2016年11月4日 下午2:11:42
	 *
	 * @param photographerBaseMsg
	 * @return
	 */
	public int updatePhotographerBaseMsgExtByUid(PhotographerBaseMsgExtEntity photographerBaseMsgExt){
		
		if(Check.NuNObj(photographerBaseMsgExt)||Check.NuNStrStrict(photographerBaseMsgExt.getPhotographerUid())){
			throw new BusinessException("修改对象异常");
		}
		return this.mybatisDaoContext.update(SQLID+"updatePhotographerBaseMsgExtByUid", photographerBaseMsgExt);
		
	}
}
