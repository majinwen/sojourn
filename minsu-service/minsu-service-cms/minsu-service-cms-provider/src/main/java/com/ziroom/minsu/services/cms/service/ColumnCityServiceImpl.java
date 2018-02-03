/**
 * @FileName: ColumnCityServiceImpl.java
 * @Package com.ziroom.minsu.services.cms.service
 * 
 * @author bushujie
 * @created 2016年11月9日 下午4:28:08
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.service;

import javax.annotation.Resource;

import com.ziroom.minsu.services.cms.entity.FileRegionsVo;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.cms.ColumnCityEntity;
import com.ziroom.minsu.services.cms.dao.ColumnCityDao;
import com.ziroom.minsu.services.cms.dto.ColumnCityRequest;
import com.ziroom.minsu.services.cms.entity.ColumnCityVo;

/**
 * <p>城市专栏相关业务</p>
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
@Service("cms.columnCityServiceImpl")
public class ColumnCityServiceImpl {
	
	@Resource(name="cms.columnCityDao")
	private ColumnCityDao columnCityDao;
	
	/**
	 * 
	 * 城市专栏列表
	 *
	 * @author bushujie
	 * @created 2016年11月9日 下午4:31:36
	 *
	 * @param columnCityRequest
	 * @return
	 */
	public PagingResult<ColumnCityVo> findColumnCityList(ColumnCityRequest columnCityRequest){
		return columnCityDao.findColumnCityList(columnCityRequest);
	}
	
	/**
	 * 
	 * 插入城市专栏
	 *
	 * @author bushujie
	 * @created 2016年11月9日 下午4:35:09
	 *
	 * @param columnCityEntity
	 */
	public void insertColumnCity(ColumnCityEntity columnCityEntity){
		columnCityDao.insertColumnCity(columnCityEntity);
	}
	
	/**
	 * 
	 * fid查询城市专栏
	 *
	 * @author bushujie
	 * @created 2016年11月9日 下午4:35:59
	 *
	 * @param fid
	 * @return
	 */
	public ColumnCityEntity getColumnCityByFid(String fid){
		return columnCityDao.getColumnCityByFid(fid);
	}
	
	/**
	 * 
	 * 更新城市专栏
	 *
	 * @author bushujie
	 * @created 2016年11月9日 下午4:36:49
	 *
	 * @param columnCityEntity
	 * @return
	 */
	public int updateColumnCity(ColumnCityEntity columnCityEntity){
		return columnCityDao.updateColumnCity(columnCityEntity);
	}

	/**
	 * 根据城市code查询城市以及其下辖专栏
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/17 16:05
	 */
	public FileRegionsVo getCityRegionsByCityCode(String cityCode){
		return columnCityDao.findFileRegionsByCityCode(cityCode);
	}
}
