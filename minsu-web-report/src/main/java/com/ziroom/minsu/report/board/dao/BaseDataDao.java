/**
 * @FileName: BaseDataDao.java
 * @Package com.ziroom.minsu.report.board.dao
 * 
 * @author bushujie
 * @created 2017年1月12日 下午2:42:42
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.board.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ziroom.minsu.entity.conf.CityRegionEntity;
import com.ziroom.minsu.entity.conf.ConfCityEntity;
import com.ziroom.minsu.report.board.vo.EmpTargetItemVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.report.board.dto.EmpTargetItemRequest;
import com.ziroom.minsu.report.board.vo.EmpTargetItem;

/**
 * <p></p>
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
@Repository("report.baseDataDao")
public class BaseDataDao {
	
	private String SQLID = "report.baseDataDao.";

	@Autowired
	@Qualifier("minsuReport.all.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 
	 * 分页查询区域管家列表
	 *
	 * @author bushujie
	 * @created 2017年1月12日 下午3:00:16
	 *
	 * @param guardAreaR
	 * @return
	 */
	public PagingResult<EmpTargetItem> findGaurdAreaByPage(EmpTargetItemRequest empTargetItemRequest ) {
		if(Check.NuNObj(empTargetItemRequest)) empTargetItemRequest = new EmpTargetItemRequest();
		PageBounds pageBounds=new PageBounds();
		pageBounds.setLimit(empTargetItemRequest.getLimit());
		pageBounds.setPage(empTargetItemRequest.getPage());
	
		return mybatisDaoContext.findForPage(SQLID+"findGaurdAreaByPage", EmpTargetItem.class, empTargetItemRequest, pageBounds);
	}

	/**
	 * 分页查询区域管家列表(导出数据专用)
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2017/1/13 18:11
	 */
	public PagingResult<EmpTargetItemVo> findGaurdAreaForExcel(EmpTargetItemRequest empTargetItemRequest ) {
		if(Check.NuNObj(empTargetItemRequest)) empTargetItemRequest = new EmpTargetItemRequest();
		PageBounds pageBounds=new PageBounds();
		pageBounds.setLimit(empTargetItemRequest.getLimit());
		pageBounds.setPage(empTargetItemRequest.getPage());

		return mybatisDaoContext.findForPage(SQLID+"findGaurdAreaForExcel", EmpTargetItemVo.class, empTargetItemRequest, pageBounds);
	}
	
	/**
	 * 
	 * 条件查询目标
	 *
	 * @author bushujie
	 * @created 2017年1月18日 上午11:42:05
	 *
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public HashMap findTargetByCondition(Map<String, Object> paramMap){
		return mybatisDaoContext.findOne(SQLID+"findTargetByCondition", HashMap.class, paramMap);
	}
	
	/**
	 * 
	 * 国家查询大区
	 *
	 * @author bushujie
	 * @created 2017年1月18日 下午6:34:58
	 *
	 * @param countryCode
	 * @return
	 */
	public List<CityRegionEntity> findRegionByCountryCode(String countryCode){
		return mybatisDaoContext.findAll(SQLID+"findRegionByCountryCode", countryCode);
	}
	
	/**
	 * 
	 * 大区查询开通城市
	 *
	 * @author bushujie
	 * @created 2017年1月19日 上午11:55:20
	 *
	 * @param regionFid
	 * @return
	 */
	public List<ConfCityEntity> findConfCityEntity(String regionFid){
		return mybatisDaoContext.findAll(SQLID+"findCityByRegion", ConfCityEntity.class, regionFid);
	}
	
	/**
	 * 
	 * 条件查询房源的一些数量
	 *
	 * @author bushujie
	 * @created 2017年1月19日 下午3:45:42
	 *
	 * @param paramMap
	 * @return
	 */
	public int getIssueHouseNum(Map<String, Object> paramMap){
		return mybatisDaoContext.findOne(SQLID+"getIssueHouseNum", Integer.class, paramMap);
	}
	
	/**
	 * 
	 * 分页查询专员列表
	 *
	 * @author bushujie
	 * @created 2017年1月22日 上午11:40:51
	 *
	 * @param empTargetItemRequest
	 * @return
	 */
	public PagingResult<EmpTargetItem> findPushEmpPage(EmpTargetItemRequest empTargetItemRequest){
		if(Check.NuNObj(empTargetItemRequest)) empTargetItemRequest = new EmpTargetItemRequest();
		PageBounds pageBounds=new PageBounds();
		pageBounds.setLimit(empTargetItemRequest.getLimit());
		pageBounds.setPage(empTargetItemRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID+"findPushEmpPage", EmpTargetItem.class, empTargetItemRequest, pageBounds);
	}
	
	/**
	 * 
	 * 查询专员月份目标列表
	 *
	 * @author bushujie
	 * @created 2017年1月22日 下午4:00:23
	 *
	 * @param paramMap
	 * @return
	 */
	public List<EmpTargetItem> findEmpTarget(Map<String, Object> paramMap){
		return mybatisDaoContext.findAll(SQLID+"findEmpTarget", EmpTargetItem.class, paramMap);
	}
}
