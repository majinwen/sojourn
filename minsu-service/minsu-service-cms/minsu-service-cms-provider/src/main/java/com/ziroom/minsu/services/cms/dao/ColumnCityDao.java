/**
 * @FileName: ColumnCityDao.java
 * @Package com.ziroom.minsu.services.cms.dao
 * 
 * @author bushujie
 * @created 2016年11月9日 上午11:23:36
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.dao;

import com.ziroom.minsu.services.cms.entity.FileRegionsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.cms.ColumnCityEntity;
import com.ziroom.minsu.services.cms.dto.ColumnCityRequest;
import com.ziroom.minsu.services.cms.entity.ColumnCityVo;

/**
 * <p>城市专栏DAO</p>
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
@Repository("cms.columnCityDao")
public class ColumnCityDao {
	
	private String SQLID = "cms.columnCityDao.";

	@Autowired
	@Qualifier("cms.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * 根据cityCode查询城市以及下辖的所有专栏
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/17 15:17
	 */
	public FileRegionsVo findFileRegionsByCityCode(String cityCode){
		return mybatisDaoContext.findOneSlave(SQLID+"findFileRegionsByCityCode",FileRegionsVo.class,cityCode);
	}

	/**
	 * 
	 * 分页查询城市专栏
	 *
	 * @author bushujie
	 * @created 2016年11月9日 下午2:44:22
	 *
	 * @param columnCityRequest
	 * @return
	 */
	public PagingResult<ColumnCityVo> findColumnCityList(ColumnCityRequest columnCityRequest){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(columnCityRequest.getLimit());
        pageBounds.setPage(columnCityRequest.getPage());
        return mybatisDaoContext.findForPage(SQLID+"findColumnCityList", ColumnCityVo.class, columnCityRequest, pageBounds);
	}
	
	/**
	 * 
	 * 插入城市专栏
	 *
	 * @author bushujie
	 * @created 2016年11月9日 下午3:11:22
	 *
	 * @param columnCityEntity
	 */
	public void insertColumnCity(ColumnCityEntity columnCityEntity){
		mybatisDaoContext.save(SQLID+"insertColumnCity", columnCityEntity);
	}
	
	/**
	 * 
	 * fid查询城市专栏
	 *
	 * @author bushujie
	 * @created 2016年11月9日 下午3:46:07
	 *
	 * @param fid
	 * @return
	 */
	public ColumnCityEntity getColumnCityByFid(String fid){
		return mybatisDaoContext.findOneSlave(SQLID+"getColumnCityByFid", ColumnCityEntity.class, fid);
	}
	
	/**
	 * 
	 * 更新城市专栏
	 *
	 * @author bushujie
	 * @created 2016年11月9日 下午4:06:41
	 *
	 * @param columnCityEntity
	 * @return
	 */
	public int updateColumnCity(ColumnCityEntity columnCityEntity){
		return mybatisDaoContext.update(SQLID+"updateColumnCity", columnCityEntity);
	}
}
