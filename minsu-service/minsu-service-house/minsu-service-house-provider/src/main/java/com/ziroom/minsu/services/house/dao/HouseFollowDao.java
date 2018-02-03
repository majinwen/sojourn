/**
 * @FileName: HouseFollowDao.java
 * @Package com.ziroom.minsu.services.house.dao
 * 
 * @author bushujie
 * @created 2017年2月22日 下午4:23:02
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dao;


import java.util.List;
import java.util.Map;

import com.ziroom.minsu.services.house.dto.HouseFollowListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.house.HouseFollowEntity;
import com.ziroom.minsu.services.house.dto.HouseFollowDto;
import com.ziroom.minsu.services.house.entity.HouseFollowVo;

/**
 * <p>房源跟进主表Dao</p>
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
@Repository("house.houseFollowDao")
public class HouseFollowDao {
	
    private String SQLID="house.houseFollowDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     * 插入房源跟进主表
     *
     * @author bushujie
     * @created 2017年2月22日 下午4:31:14
     *
     * @param houseFollowEntity
     */
    public void insertHouseFollow(HouseFollowEntity houseFollowEntity){
    	mybatisDaoContext.save(SQLID+"insertHouseFollow", houseFollowEntity);
    }
    
    /**
     * 
     * 查询满足客服跟进条件，未生成客服跟进未审核通过房源的记录（条件：未通过原因非房源品质原因且审核未通过超过12小时）
     *
     * @author bushujie
     * @created 2017年2月22日 下午7:58:51
     *
     * @param paramMap
     * @return
     */
    public List<HouseFollowVo> findServicerFollowHouseList(Map<String, Object> paramMap){
    	return mybatisDaoContext.findAll(SQLID+"findServicerFollowHouseList", HouseFollowVo.class, paramMap);
    }
    
    /**
     * 
     * 分页查询客服跟进未审核通过房源房东列表
     *
     * @author bushujie
     * @created 2017年2月23日 下午5:31:16
     *
     * @param houseFollowDto
     * @return
     */
    public PagingResult<HouseFollowVo> findServicerFollowLandlordList(HouseFollowDto houseFollowDto){
    	PageBounds pageBounds=new PageBounds();
    	pageBounds.setPage(houseFollowDto.getPage());
    	pageBounds.setLimit(houseFollowDto.getLimit());
    	return mybatisDaoContext.findForPage(SQLID+"findServicerFollowLandlordList", HouseFollowVo.class, houseFollowDto, pageBounds);
    }
    
    /**
     * 
     *  房东下客服跟进房源列表
     *
     * @author bushujie
     * @created 2017年2月23日 下午8:57:57
     *
     * @param houseFollowDto
     * @return
     */
    public List<HouseFollowVo> findServicerFollowHouseListByLandlord(HouseFollowDto houseFollowDto){
    	return mybatisDaoContext.findAll(SQLID+"findServicerFollowHouseListByLandlord", HouseFollowVo.class, houseFollowDto);
    }
    
    /**
     * 
     * fid查询房源跟进信息
     *
     * @author bushujie
     * @created 2017年2月25日 下午2:35:55
     *
     * @param fid
     * @return
     */
    public HouseFollowEntity getHouseFollowByKey(String fid){
    	return mybatisDaoContext.findOne(SQLID+"getHouseFollowByKey", HouseFollowEntity.class, fid);
    }
    
    /**
     * 
     * 查询整租房源跟进信息
     *
     * @author bushujie
     * @created 2017年2月25日 下午2:38:24
     *
     * @param houseSn
     * @return
     */
    public HouseFollowEntity findHouseFollowMsgZ(String houseSn){
    	return mybatisDaoContext.findOne(SQLID+"findHouseFollowMsgZ", HouseFollowEntity.class, houseSn);
    }
    
    /**
     * 
     * 查询整分组房源跟进信息
     *
     * @author bushujie
     * @created 2017年2月25日 下午2:41:12
     *
     * @param roomSn
     * @return
     */
    public HouseFollowEntity findHouseFollowMsgF(String roomSn){
    	return mybatisDaoContext.findOne(SQLID+"findHouseFollowMsgF", HouseFollowEntity.class, roomSn);
    }
    
    /**
     * 
     * 查询整租房源跟进详细信息
     *
     * @author bushujie
     * @created 2017年2月27日 上午11:13:23
     *
     * @param followFid
     * @return
     */
    public HouseFollowVo findHouseFollowDetailZ(String followFid){
    	return mybatisDaoContext.findOne(SQLID+"findHouseFollowDetailZ", HouseFollowVo.class, followFid);
    }
    
    /**
     * 
     *查询分组房源跟进详细信息
     *
     * @author bushujie
     * @created 2017年2月27日 上午11:20:36
     *
     * @param followFid
     * @return
     */
    public HouseFollowVo findHouseFollowDetailF(String followFid){
    	return mybatisDaoContext.findOne(SQLID+"findHouseFollowDetailF", HouseFollowVo.class, followFid);
    }
    
    /**
     * 
     * 更新房源跟进主表
     *
     * @author bushujie
     * @created 2017年2月27日 下午8:48:38
     *
     * @param houseFollowEntity
     */
    public int updateHouseFollow(HouseFollowEntity houseFollowEntity){
    	return mybatisDaoContext.update(SQLID+"updateHouseFollow", houseFollowEntity);
    }

    /**
     * 查询列表
     * @author jixd
     * @created 2017年03月03日 18:56:32
     * @param
     * @return
     */
    public List<HouseFollowEntity> listHouseFollowAll(HouseFollowListDto houseFollowListDto){
        return mybatisDaoContext.findAll(SQLID + "listHouseFollowAll",HouseFollowEntity.class,houseFollowListDto);
    }

    /**
     * 
     * 分页查询专员房源跟进房东列表
     *
     * @author bushujie
     * @created 2017年2月28日 上午11:57:14
     *
     * @param houseFollowDto
     * @return
     */
    public PagingResult<HouseFollowVo> findAttacheFollowLandlordList(HouseFollowDto houseFollowDto){
    	PageBounds pageBounds=new PageBounds();
    	pageBounds.setPage(houseFollowDto.getPage());
    	pageBounds.setLimit(houseFollowDto.getLimit());
    	return mybatisDaoContext.findForPage(SQLID+"findAttacheFollowLandlordList", HouseFollowVo.class, houseFollowDto, pageBounds);
    }
    
    /**
     * 
     * 以房东为条件查询查询专员房源跟进列表
     *
     * @author bushujie
     * @created 2017年2月28日 上午11:59:16
     *
     * @param houseFollowDto
     * @return
     */
    public List<HouseFollowVo> findAttacheFollowHouseListByLandlord(HouseFollowDto houseFollowDto){
    	return mybatisDaoContext.findAll(SQLID+"findAttacheFollowListByLandlord", HouseFollowVo.class, houseFollowDto);
    }
}
