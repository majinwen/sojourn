/**
 * @FileName: HouseBusinessSource.java
 * @Package com.ziroom.minsu.services.house.dao
 * 
 * @author bushujie
 * @created 2016年7月5日 下午7:00:54
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HouseBusinessSourceEntity;

/**
 * <p>房源商机来源表数据操作</p>
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
@Repository("house.houseBusinessSourceDao")
public class HouseBusinessSourceDao {
	
	private String SQLID="house.houseBusinessSourceDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     * 插入房源商机来源表信息
     *
     * @author bushujie
     * @created 2016年7月5日 下午7:14:17
     *
     * @param houseBusinessSourceEntity
     */
    public void insertHouseBusinessSourceEntity(HouseBusinessSourceEntity houseBusinessSourceEntity){
    	mybatisDaoContext.save(SQLID+"insertHouseBusinessSourceEntity", houseBusinessSourceEntity);
    }
    
    /**
     * 
     * 更新房源商机来源表信息
     *
     * @author bushujie
     * @created 2016年7月6日 上午11:23:11
     *
     * @param houseBusinessSourceEntity
     */
    public int updateHouseBusinessSource(HouseBusinessSourceEntity houseBusinessSourceEntity){
    	return mybatisDaoContext.update(SQLID+"updateHouseBusinessSourceEntity", houseBusinessSourceEntity);
    }
    
    /**
     * 
     *查询商机来源信息
     *
     * @author bushujie
     * @created 2016年7月9日 下午2:19:31
     *
     * @param businessFid
     * @return
     */
    public HouseBusinessSourceEntity findBusinessSourceByBusinessFid(String businessFid){
    	return mybatisDaoContext.findOne(SQLID+"findBusinessSourceByBusinessFid", HouseBusinessSourceEntity.class, businessFid);
    }
    
    /**
     * 
     * 删除房源商机来源信息
     *
     * @author bushujie
     * @created 2016年7月9日 下午5:58:34
     *
     * @param businessFid
     */
    public void delHouseBusinessSource(String businessFid ){
    	Map<String, Object> params=new HashMap<String, Object>();
    	params.put("businessFid", businessFid);
    	mybatisDaoContext.update(SQLID+"delHouseBusinessSource", params);
    }
}
