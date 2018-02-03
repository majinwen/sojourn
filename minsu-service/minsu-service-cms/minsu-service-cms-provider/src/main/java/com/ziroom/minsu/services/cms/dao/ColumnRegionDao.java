/**
 * @FileName: ColumnRegionDao.java
 * @Package com.ziroom.minsu.services.cms.dao
 * 
 * @author bushujie
 * @created 2016年11月10日 下午4:57:22
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.cms.ColumnRegionEntity;
import com.ziroom.minsu.services.cms.dto.ColumnRegionRequest;
import com.ziroom.minsu.services.cms.entity.ColumnRegionUpVo;
import com.ziroom.minsu.services.cms.entity.ColumnRegionVo;

/**
 * <p>专栏景点商圈DAO</p>
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
@Repository("cms.columnRegionDao")
public class ColumnRegionDao {
	
	private String SQLID = "cms.columnRegionDao.";

	@Autowired
	@Qualifier("cms.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 
	 * 分页查询专栏景点商圈
	 *
	 * @author bushujie
	 * @created 2016年11月10日 下午6:08:11
	 *
	 * @param columnRegionRequest
	 * @return
	 */
	public PagingResult<ColumnRegionVo> findColumnRegionList(ColumnRegionRequest columnRegionRequest){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(columnRegionRequest.getLimit());
        pageBounds.setPage(columnRegionRequest.getPage());
        return mybatisDaoContext.findForPage(SQLID+"findColumnRegionList", ColumnRegionVo.class, columnRegionRequest, pageBounds);
    }
	
	/**
	 * 
	 * 插入专栏商圈景点
	 *
	 * @author bushujie
	 * @created 2016年11月10日 下午6:10:26
	 *
	 * @param columnRegionEntity
	 */
	public void insertColumnRegion(ColumnRegionEntity columnRegionEntity){
		mybatisDaoContext.save(SQLID+"insertColumnRegion", columnRegionEntity);
	}
	
	/**
	 * 
	 * 初始化专栏景点商圈更新数据
	 *
	 * @author bushujie
	 * @created 2016年11月14日 下午5:23:23
	 *
	 * @param fid
	 * @return
	 */
	public ColumnRegionUpVo findColumnRegionUpVo(String fid){
		return mybatisDaoContext.findOneSlave(SQLID+"findColumnRegionUpVo", ColumnRegionUpVo.class,fid);
	}
	
	/**
	 * 
	 * 更新专栏景点商圈
	 *
	 * @author bushujie
	 * @created 2016年11月15日 下午5:06:19
	 *
	 * @param columnRegionEntity
	 * @return
	 */
	public int updateColumnRegion(ColumnRegionEntity columnRegionEntity){
		return mybatisDaoContext.update(SQLID+"updateColumnRegion", columnRegionEntity);
	}
	
	/**
	 * 
	 * 查询城市专栏包含商圈景点列表
	 *
	 * @author bushujie
	 * @created 2016年11月16日 下午6:03:38
	 *
	 * @param columnCityFid
	 * @return
	 */
	public List<ColumnRegionUpVo> findColumnRegionUpVoListByCityFid(String columnCityFid){
		return mybatisDaoContext.findAll(SQLID+"findColumnRegionUpVoListByCityFid", ColumnRegionUpVo.class, columnCityFid);
	}
	
	/**
	 * 
	 * 查询下一个城市专栏排序号
	 *
	 * @author bushujie
	 * @created 2017年1月5日 下午6:05:45
	 *
	 * @param columnCityFid
	 * @return
	 */
	public int findNextOrderSortByColumnCityFid(String columnCityFid){
		return mybatisDaoContext.findOne(SQLID+"findNextOrderSortByColumnCityFid", Integer.class, columnCityFid);
	}
}
