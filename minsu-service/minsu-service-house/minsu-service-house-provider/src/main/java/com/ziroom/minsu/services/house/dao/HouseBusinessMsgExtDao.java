/**
 * @FileName: HouseBusinessMsgExtDao.java
 * @Package com.ziroom.minsu.services.house.dao
 * 
 * @author bushujie
 * @created 2016年7月5日 下午6:37:36
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HouseBusinessMsgExtEntity;
import com.ziroom.minsu.services.house.dto.HouseBusinessMsgExtDto;

/**
 * <p>房源商机扩展表数据操作</p>
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
@Repository("house.houseBusinessMsgExtDao")
public class HouseBusinessMsgExtDao {
	
	private String SQLID="house.houseBusinessMsgExtDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     * 插入房源商机扩展表信息
     *
     * @author bushujie
     * @created 2016年7月5日 下午6:45:24
     *
     * @param houseBusinessMsgExtEntity
     */
    public void insertBusinessMsgExtEntity(HouseBusinessMsgExtEntity houseBusinessMsgExtEntity){
    	 mybatisDaoContext.save(SQLID+"insertBusinessMsgExtEntity", houseBusinessMsgExtEntity);
    }
    
    /**
     * 
     * 更新房源商机扩展表信息
     *
     * @author bushujie
     * @created 2016年7月6日 上午11:02:22
     *
     * @param houseBusinessMsgExtEntity
     * @return
     */
    public int updateBusinessMsgExt(HouseBusinessMsgExtEntity houseBusinessMsgExtEntity){
    	return mybatisDaoContext.update(SQLID+"updateHouseBusinessMsgExtEntity", houseBusinessMsgExtEntity);
    }
    
    /**
     * 
     * 根据房东查询地推管家员工编号
     *
     * @author bushujie
     * @created 2016年7月8日 上午11:25:23
     *
     * @param paramMap
     * @return
     */
    public String findDtGuardCodeByLandlord(Map<String, Object> paramMap){
    	return mybatisDaoContext.findOne(SQLID+"findDtGuardCodeByLandlord", String.class, paramMap);
    }
    
    /**
     * 
     * 商机fid查询商机扩展信息
     *
     * @author bushujie
     * @created 2016年7月9日 下午2:05:15
     *
     * @param businessFid
     * @return
     */
    public HouseBusinessMsgExtEntity findHouseBusinessMsgExtByBusinessFid(String businessFid){
    	return mybatisDaoContext.findOne(SQLID+"findHouseBusinessMsgExtByBusinessFid", HouseBusinessMsgExtEntity.class, businessFid);
    }
    
    /**
     * 
     * 条件查询 商机扩展信息
     * 
     * 查询对象不能为null
     *
     * @author yd
     * @created 2016年7月9日 下午2:27:25
     *
     * @param houseBusinessMsgExtDto
     * @return
     */
    public List<HouseBusinessMsgExtEntity>  findHouseBusExtByCondition(HouseBusinessMsgExtDto houseBusinessMsgExtDto){
    	
    	if(Check.NuNObj(houseBusinessMsgExtDto)){
    		return null;
    	}
    	return this.mybatisDaoContext.findAll(SQLID+"findHouseBusExtByCondition", HouseBusinessMsgExtEntity.class, houseBusinessMsgExtDto);
    }
    
    /**
     * 
     * 更新商机的地推管家
     *
     * @author bushujie
     * @created 2016年7月19日 下午4:06:22
     *
     * @param paramMap
     */
    public int updateDtGuard(Map<String, Object> paramMap ){
    	return mybatisDaoContext.update(SQLID+"updateDtGuard", paramMap);
    }
}
