/**
 * @FileName: FailUrlRecordsMapper.java
 * @Package com.ziroom.minsu.spider.failurls.dao
 * 
 * @author zl
 * @created 2016年10月08日 下午3:39:46
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.failurls.dao;

import java.util.List;

import com.ziroom.minsu.spider.failurls.entity.FailUrlRecordsEntity;

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
public interface FailUrlRecordsMapper {

	public  int  insert(FailUrlRecordsEntity failUrl); 
	
	public int updateByPrimaryKeyWithBLOBs(FailUrlRecordsEntity failUrl); 
	
	public FailUrlRecordsEntity selectByPrimaryKey(int id);
	
	public FailUrlRecordsEntity selectByUrl(String url);
	
	public  int  deleteByPrimaryKey(int id);
	
	public  int  deleteByUrl(String url);
	
	public List<FailUrlRecordsEntity> selectByIDs(List<Integer> ids);
	
	public List<FailUrlRecordsEntity> selectByUrlTypes(Integer[] urlTypes);
	
	public List<FailUrlRecordsEntity> selectAll();
	
}
