/**
 * @FileName: HouseTagDao.java
 * @Package com.ziroom.minsu.services.house.dao
 * 
 * @author bushujie
 * @created 2017年3月17日 上午9:41:40
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HouseTagEntity;

/**
 * <p>房源标签关系DAO</p>
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
@Repository("house.houseTagDao")
public class HouseTagDao {
	
	private String SQLID="house.houseTagDao.";
	
    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
	
	
	/**
	 * 
	 * 插入房源标签关系
	 *
	 * @author bushujie
	 * @created 2017年3月17日 上午9:46:18
	 *
	 * @param houseTagEntity
	 */
	public void insertHouseTag(HouseTagEntity houseTagEntity){
		mybatisDaoContext.save(SQLID+"insertHouseTag", houseTagEntity);
	}
	
	/**
	 * 
	 * 条件查询房源标签 返回标签fids 集合
	 *
	 * @author yd
	 * @created 2017年3月17日 下午7:35:34
	 *
	 * @param params
	 * @return
	 */
	public List<String> findHouseTagByParams(Map<String, Object> params){
		return mybatisDaoContext.findAll(SQLID+"findHouseTagByParams", String.class, params);
	}
	
	
	/**
	 * 
	 * 删除top房源标签
	 *
	 * @author bushujie
	 * @created 2017年3月22日 下午7:21:01
	 *
	 * @param paramMap
	 */
	public void delHouseTagByParams(Map<String, Object> paramMap){
		mybatisDaoContext.delete(SQLID+"delHouseTagByParams", paramMap);
	}
}
