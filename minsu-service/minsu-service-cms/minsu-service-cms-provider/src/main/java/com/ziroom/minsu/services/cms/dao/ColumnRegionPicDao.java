/**
 * @FileName: ColumnRegionPicDao.java
 * @Package com.ziroom.minsu.services.cms.dao
 * 
 * @author bushujie
 * @created 2016年11月14日 上午11:24:32
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.cms.ColumnRegionPicEntity;

/**
 * <p>专栏景点商圈图片Dao</p>
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
@Repository("cms.columnRegionPicDao")
public class ColumnRegionPicDao {
	
	private String SQLID = "cms.columnRegionPicDao.";

	@Autowired
	@Qualifier("cms.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 
	 * 插入专栏景点商圈图片
	 *
	 * @author bushujie
	 * @created 2016年11月14日 上午11:28:51
	 *
	 * @param columnRegionPicEntity
	 */
	public void insertColumnRegionPic(ColumnRegionPicEntity columnRegionPicEntity){
		mybatisDaoContext.save(SQLID+"insertColumnRegionPic", columnRegionPicEntity);
	}
	
	/**
	 * 
	 * 更新专栏景点商圈图片
	 *
	 * @author bushujie
	 * @created 2016年11月15日 下午5:12:08
	 *
	 * @param columnRegionPicEntity
	 */
	public void updateColumnRegionPic(ColumnRegionPicEntity columnRegionPicEntity){
		mybatisDaoContext.update(SQLID+"updateColumnRegionPic", columnRegionPicEntity);
	}
	
	/**
	 * 
	 * 更新专栏景点商圈主图
	 *
	 * @author bushujie
	 * @created 2016年11月15日 下午6:06:21
	 *
	 * @param columnRegionPicEntity
	 */
	public int updateColumnRegionPicByType(ColumnRegionPicEntity columnRegionPicEntity){
		return mybatisDaoContext.update(SQLID+"updateColumnRegionPicByType", columnRegionPicEntity);
	}
}
