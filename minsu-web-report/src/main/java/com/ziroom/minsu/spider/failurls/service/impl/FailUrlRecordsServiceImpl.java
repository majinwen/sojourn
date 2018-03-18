/**
 * @FileName: FailUrlRecordsServiceImpl.java
 * @Package com.ziroom.minsu.spider.failurls.service.impl
 * 
 * @author zl
 * @created 2016年10月08日 下午4:48:11
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.failurls.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.spider.failurls.dao.FailUrlRecordsMapper;
import com.ziroom.minsu.spider.failurls.dto.FailUrlRequestDto;
import com.ziroom.minsu.spider.failurls.entity.FailUrlRecordsEntity;
import com.ziroom.minsu.spider.failurls.service.FailUrlRecordsService;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
@Service("failUrlService")
public class FailUrlRecordsServiceImpl implements FailUrlRecordsService {
 
	@Autowired
	private FailUrlRecordsMapper failUrlMapper;
 
	@Override
	public int saveFailUrlRecords(FailUrlRecordsEntity failUrl) {
		if (failUrl==null || failUrl.getUrl()==null) {
			return 0;
		}
		FailUrlRecordsEntity oldFailUrl = failUrlMapper.selectByUrl(failUrl.getUrl());
		if (oldFailUrl==null) {
			failUrl.setCreateDate(new Date());
			failUrl.setLastModifyDate(new Date());
			failUrl.setTryCount(1);
			return failUrlMapper.insert(failUrl);
		}else {
			failUrl.setId(oldFailUrl.getId());
			failUrl.setCreateDate(new Date());
			failUrl.setTryCount(oldFailUrl.getTryCount()+1);
			failUrl.setLastModifyDate(new Date());
			return failUrlMapper.updateByPrimaryKeyWithBLOBs(failUrl);
		}
	}
 
	@Override
	public FailUrlRecordsEntity selectByPrimaryKey(int id) {
		return failUrlMapper.selectByPrimaryKey(id);
	}
 
	@Override
	public FailUrlRecordsEntity selectByUrl(String url) {
		return failUrlMapper.selectByUrl(url);
	}

 
	@Override
	public int deleteByPrimaryKey(int id) {
		return failUrlMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteByUrl(String url) {
		return failUrlMapper.deleteByUrl(url);
	}
 
	@Override
	public PageResult selectRecordsByPage(FailUrlRequestDto requestDto) {
		
		if (requestDto==null) {
			return null;
		} 
		
		PageHelper.startPage(requestDto.getPage(), requestDto.getLimit());
		
		List<FailUrlRecordsEntity> list =null;
		if (requestDto.getIds()!=null && requestDto.getIds().size()>0) {//有id就按照id查
			
			list = failUrlMapper.selectByIDs(requestDto.getIds());
			 
		}else if(requestDto.getUrlTypes()!=null && requestDto.getUrlTypes().length>0){
			
			list = failUrlMapper.selectByUrlTypes(requestDto.getUrlTypes());
		}else {
			list = failUrlMapper.selectAll();
		}

		PageInfo<FailUrlRecordsEntity> pageInfo = new PageInfo<>(list);
		
		PageResult result = new PageResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		
		return result;
		 
	}
 
	@Override
	public List<FailUrlRecordsEntity> selectByIDs(List<Integer> ids) { 
		return failUrlMapper.selectByIDs(ids);
	}
 
	@Override
	public List<FailUrlRecordsEntity> selectByUrlTypes(Integer[] urlTypes) {
		return failUrlMapper.selectByUrlTypes(urlTypes);
	}

}
