/**
 * @FileName: PhotographerBaseMsgDao.java
 * @Package com.ziroom.minsu.services.house
 * 
 * @author yd
 * @created 2016年11月4日 上午11:43:53
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.photographer.PhotographerBaseMsgEntity;
import com.ziroom.minsu.services.house.photog.dto.PhotogDetailDto;
import com.ziroom.minsu.services.house.photog.dto.PhotogRequestDto;

/**
 * <p>摄影师基本信息持久化</p>
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
@Repository("photographer.photographerBaseMsgDao")
public class PhotographerBaseMsgDao {

	private String SQLID="photographer.photographerBaseMsgDao.";

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
	public int savePhotographerBaseMsg(PhotographerBaseMsgEntity photographerBaseMsg){
		
		if(!Check.NuNObj(photographerBaseMsg)){
			if(Check.NuNStrStrict(photographerBaseMsg.getPhotographerUid())) photographerBaseMsg.setPhotographerUid(UUIDGenerator.hexUUID());
		    return this.mybatisDaoContext.save(SQLID+"savePhotographerBaseMsg", photographerBaseMsg);
		}
		return 0;
	}
	
	/**
	 * 
	 * 根据摄影师uid 修改摄影师基本信息
	 *
	 * @author yd
	 * @created 2016年11月4日 下午2:11:42
	 *
	 * @param photographerBaseMsg
	 * @return
	 */
	public int updatePhotographerBaseMsgByUid(PhotographerBaseMsgEntity photographerBaseMsg){
		
		if(Check.NuNObj(photographerBaseMsg)||Check.NuNStrStrict(photographerBaseMsg.getPhotographerUid())){
			throw new BusinessException("修改对象异常");
		}
		return this.mybatisDaoContext.update(SQLID+"updatePhotographerBaseMsgByUid", photographerBaseMsg);
		
	}

	/**
	 * 分页查询摄影师基本信息
	 *
	 * @author liujun
	 * @created 2016年11月7日
	 *
	 * @param photogDto
	 * @return
	 */
	public PagingResult<PhotographerBaseMsgEntity> findPhotographerListByPage(PhotogRequestDto photogDto) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(photogDto.getPage());
		pageBounds.setLimit(photogDto.getLimit());
		return mybatisDaoContext.findForPage(SQLID+"findPhotogList", PhotographerBaseMsgEntity.class, photogDto, pageBounds);
	}

	/**
	 * 根据摄影师uid查询信息
	 *
	 * @author liujun
	 * @created 2016年11月8日
	 *
	 * @param photographerUid
	 * @return
	 */
	public PhotogDetailDto findPhotogDetailByUid(String photographerUid) {
		return mybatisDaoContext.findOneSlave(SQLID+"findPhotogDetailByUid", PhotogDetailDto.class, photographerUid);
	}
	
}
