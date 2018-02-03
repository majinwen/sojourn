/**
 * @FileName: HouseBusinessMsgDao.java
 * @Package com.ziroom.minsu.services.house.dao
 * 
 * @author bushujie
 * @created 2016年7月5日 下午4:42:01
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.house.HouseBusinessMsgEntity;
import com.ziroom.minsu.services.house.dto.HouseBusinessDto;
import com.ziroom.minsu.services.house.entity.HouseBusinessListVo;

/**
 * <p>房源商机表数据操作</p>
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
@Repository("house.houseBusinessMsgDao")
public class HouseBusinessMsgDao {
	
	private String SQLID="house.houseBusinessMsgDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    
    /**
     * 
     * 插入房源商机主表信息
     *
     * @author bushujie
     * @created 2016年7月5日 下午4:49:32
     *
     * @param houseBusinessMsgEntity
     */
    public void insertHouseBusinessMsg(HouseBusinessMsgEntity houseBusinessMsgEntity){
    	 mybatisDaoContext.save(SQLID+"insertHouseBusinessMsgEntity", houseBusinessMsgEntity);
    }
    
    /**
     * 更新房源商机来源表
     *  
     *
     * @author bushujie
     * @created 2016年7月6日 上午10:54:00
     *
     * @param houseBusinessSourceEntity
     * @return
     */
    public int updateHouseBusinessMsg(HouseBusinessMsgEntity houseBusinessMsgEntity){
    	return mybatisDaoContext.update(SQLID+"updateHouseBusinessMsgEntity", houseBusinessMsgEntity);
    }
    
    /**
     * 
     * 分页查询房源商机列表
     *
     * @author bushujie
     * @created 2016年7月6日 下午5:35:58
     *
     * @param houseBusinessDto
     * @return
     */
    public PagingResult<HouseBusinessListVo> findBusinessList(HouseBusinessDto houseBusinessDto){
    	PageBounds pageBounds=new PageBounds();
    	pageBounds.setLimit(houseBusinessDto.getLimit());
    	pageBounds.setPage(houseBusinessDto.getPage());
    	return mybatisDaoContext.findForPage(SQLID+"findHouseBusinessList", HouseBusinessListVo.class, houseBusinessDto, pageBounds);
    }
    
    /**
     * 
     * 查询房源商机信息
     *
     * @author bushujie
     * @created 2016年7月9日 下午12:27:57
     *
     * @param fid
     * @return
     */
    public HouseBusinessMsgEntity findBusinessMsgEntityByFid(String fid){
    	return mybatisDaoContext.findOne(SQLID+"getHouseBusinessMsgByFid", HouseBusinessMsgEntity.class, fid);
    }
    
    /**
     * 
     * 房源fid查询商机
     *
     * @author bushujie
     * @created 2016年7月12日 上午10:18:17
     *
     * @param houseBaseFid
     * @return
     */
    public HouseBusinessMsgEntity findBusinessMsgByHouseFid(String houseBaseFid){
    	return mybatisDaoContext.findOne(SQLID+"getHouseBusinessByHouseFid", HouseBusinessMsgEntity.class,houseBaseFid);
    }
}
