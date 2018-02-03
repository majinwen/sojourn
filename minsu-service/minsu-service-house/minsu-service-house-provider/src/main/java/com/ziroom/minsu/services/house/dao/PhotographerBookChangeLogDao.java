/**
 * @FileName: PhotographerBookOrderDao.java
 * @Package com.ziroom.minsu.services.house.dao
 * 
 * @author yd
 * @created 2016年11月4日 下午3:22:20
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.photographer.PhotographerBookChangeLogEntity;
import com.ziroom.minsu.entity.photographer.PhotographerBookLogEntity;
import com.ziroom.minsu.entity.photographer.PhotographerBookOrderEntity;
import com.ziroom.minsu.services.common.utils.SnUtil;
import com.ziroom.minsu.services.house.dto.BookHousePhotogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>摄影师改变</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lunan
 * @since 1.0
 * @version 1.0
 */
@Component("photographer.photographerBookChangeLogDao")
public class PhotographerBookChangeLogDao {

	private String SQLID="photographer.photographerBookChangeLogDao.";

	@Autowired
	@Qualifier("house.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * 保存实体
	 * @author lunan
	 * @created 2017年2月28日 下午3:43:34
	 *
	 * @param bookChangeLogEntity
	 * @return
	 */
	public int saveBookChangeLog(PhotographerBookChangeLogEntity bookChangeLogEntity){
		if(!Check.NuNObj(bookChangeLogEntity)&&!Check.NuNStrStrict(bookChangeLogEntity.getBookOrderSn())){

			if(Check.NuNStrStrict(bookChangeLogEntity.getFid())) bookChangeLogEntity.setFid(UUIDGenerator.hexUUID());

			return this.mybatisDaoContext.save(SQLID+"saveBookChangeLog", bookChangeLogEntity);
		}
		return 0;
	}



}
