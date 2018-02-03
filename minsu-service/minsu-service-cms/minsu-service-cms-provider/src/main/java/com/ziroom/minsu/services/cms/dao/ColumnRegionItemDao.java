/**
 * @FileName: ColumnRegionItemDao.java
 * @Package com.ziroom.minsu.services.cms.dao
 * 
 * @author bushujie
 * @created 2016年11月14日 下午2:35:44
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.cms.ColumnRegionItemEntity;

/**
 * <p>专栏商圈景点推荐项目DAO</p>
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
@Repository("cms.columnRegionItemDao")
public class ColumnRegionItemDao {
	
	private String SQLID = "cms.columnRegionItemDao.";

	@Autowired
	@Qualifier("cms.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 
	 * 插入专栏商圈景点推荐项目
	 *
	 * @author bushujie
	 * @created 2016年11月14日 下午2:42:55
	 *
	 * @param columnRegionItemEntity
	 */
	public void insertColumnRegionItem(ColumnRegionItemEntity columnRegionItemEntity){
		mybatisDaoContext.save(SQLID+"insertColumnRegionItem", columnRegionItemEntity);
	}
	
	/**
	 * 
	 * 更新专栏景点商圈推荐项目
	 *
	 * @author bushujie
	 * @created 2016年11月15日 下午5:36:39
	 *
	 * @param columnRegionItemEntity
	 */
	public void updateColumnRegionItem(ColumnRegionItemEntity columnRegionItemEntity){
		mybatisDaoContext.update(SQLID+"updateColumnRegionItem", columnRegionItemEntity);
	}
	
	/**
	 * 
	 * 根据景点商圈fid更新专栏景点商圈推荐项目
	 *
	 * @author bushujie
	 * @created 2016年11月15日 下午6:15:44
	 *
	 * @param columnRegionItemEntity
	 * @return
	 */
	public int updateColumnRegionItemByRegionItemsFid(ColumnRegionItemEntity columnRegionItemEntity){
		return mybatisDaoContext.update(SQLID+"updateColumnRegionItemByRegionItemsFid", columnRegionItemEntity);
	}
	
	/**
	 * 
	 * 查询专栏景点商圈项目下一个排序号
	 *
	 * @author bushujie
	 * @created 2017年1月9日 上午11:46:00
	 *
	 * @param columnRegionFid
	 */
	public int findNextOrderSortByColumnRegionFid(String columnRegionFid){
		return mybatisDaoContext.findOne(SQLID+"findNextOrderSortByColumnRegionFid", Integer.class, columnRegionFid);
	}
}
