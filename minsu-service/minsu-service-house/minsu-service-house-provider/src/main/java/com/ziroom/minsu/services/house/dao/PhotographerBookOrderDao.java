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

import java.util.List;

import com.ziroom.minsu.services.house.photog.vo.PhotographerBookOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.photographer.PhotographerBookOrderEntity;
import com.ziroom.minsu.services.common.utils.SnUtil;
import com.ziroom.minsu.services.house.dto.BookHousePhotogDto;

/**
 * <p>摄影师预定订单</p>
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
@Component("photographer.photographerBookOrderDao")
public class PhotographerBookOrderDao {

	private String SQLID="photographer.photographerBookOrderDao.";

	@Autowired
	@Qualifier("house.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 
	 * 保存实体
	 *
	 * @author yd
	 * @created 2016年11月4日 下午3:54:24
	 *
	 * @param photographerBookOrder
	 * @return
	 */
	public int savePhotographerBookOrder(PhotographerBookOrderEntity photographerBookOrder){
		
		if(!Check.NuNObj(photographerBookOrder)){
			if(Check.NuNStrStrict(photographerBookOrder.getFid())) photographerBookOrder.setFid(UUIDGenerator.hexUUID());
			if(Check.NuNStrStrict(photographerBookOrder.getBookOrderSn())) photographerBookOrder.setBookOrderSn(SnUtil.getBookOrderSn());
			
			return this.mybatisDaoContext.save(SQLID+"savePhotographerBookOrder", photographerBookOrder);
		}
		
		return 0;
	}
	
	/**
	 * 
	 * 根据摄影师订单编号 修改信息
	 *
	 * @author yd
	 * @created 2016年11月4日 下午3:57:28
	 *
	 * @param photographerBookOrder
	 * @return
	 */
	public int updatePhotographerBookOrderBySn(PhotographerBookOrderEntity photographerBookOrder){
		
		if(Check.NuNObj(photographerBookOrder)||Check.NuNStrStrict(photographerBookOrder.getBookOrderSn())){
			throw new BusinessException("修改摄影师订单：参数错误");
		}
		
		return this.mybatisDaoContext.update(SQLID+"updatePhotographerBookOrderBySn", photographerBookOrder);
	}
	
	/**
	 * 
	 * 查询摄影师预约订单
	 *
	 * @author yd
	 * @created 2016年11月8日 下午8:07:35
	 *
	 * @param houseFid
	 * @return
	 */
	public List<PhotographerBookOrderEntity> queryPhotographerBookOrderByHouseFid(String houseFid){
		return mybatisDaoContext.findAll(SQLID+"queryPhotographerBookOrderByHouseFid", PhotographerBookOrderEntity.class, houseFid);
	}
	
	
	/**
	 * 
	 * 查询摄影师预约订单
	 * @author yd findBookOrderByHouseFid
	 * @created 2016年11月8日 下午8:07:35
	 * @param bookOrderSn
	 * @return
	 */
	public PhotographerBookOrderEntity queryPhotographerBookOrderBySn(String bookOrderSn){
		return mybatisDaoContext.findOne(SQLID+"queryPhotographerBookOrderBySn", PhotographerBookOrderEntity.class, bookOrderSn);
	}

	/**
	 * 通过房源fid查询摄影师预约订单
	 * @author lunan
	 * @created 2016年11月8日 下午8:07:35
	 *
	 * @param houseFid
	 * @return
	 */
	public PhotographerBookOrderEntity findBookOrderByHouseFid(String houseFid){
		return mybatisDaoContext.findOne(SQLID+"findBookOrderByHouseFid", PhotographerBookOrderEntity.class, houseFid);
	}

	/**
	 * 查询摄影师预约订单
	 * @author zl
	 * @param bookOrderDto
	 * @return
	 */
	public PagingResult<PhotographerBookOrderEntity> findPhotographerBookOrder(BookHousePhotogDto bookOrderDto){
		
		PageBounds pageBounds=new PageBounds();
		pageBounds.setLimit(bookOrderDto.getLimit());
		pageBounds.setPage(bookOrderDto.getPage()); 
	
		PagingResult<PhotographerBookOrderEntity> list =mybatisDaoContext.findForPage(SQLID+"findPhotographerBookOrder", PhotographerBookOrderEntity.class, bookOrderDto,pageBounds);
		return list;
	}

	/**
	 * 查询摄影师预约订单
	 * @author zl
	 * @param bookOrderDto
	 * @return
	 */
	public PagingResult<PhotographerBookOrderVo> listPhotographerBookOrderByCondition(BookHousePhotogDto bookOrderDto){

		PageBounds pageBounds=new PageBounds();
		pageBounds.setLimit(bookOrderDto.getLimit());
		pageBounds.setPage(bookOrderDto.getPage());

		PagingResult<PhotographerBookOrderVo> list =mybatisDaoContext.findForPage(SQLID+"listPhotographerBookOrderByCondition", PhotographerBookOrderVo.class, bookOrderDto,pageBounds);
		return list;
	}

}
