/**
 * @FileName: HouseTopDao.java
 * @Package com.ziroom.minsu.services.house.dao
 * 
 * @author bushujie
 * @created 2017年3月17日 上午9:59:35
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;



import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.MD5Util;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HouseTopEntity;
import com.ziroom.minsu.services.house.entity.HouseTopDetail;
import com.ziroom.minsu.services.house.entity.HouseTopVo;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.services.house.dto.HouseTopDto;
import com.ziroom.minsu.services.house.entity.HouseTopListVo;

/**
 * <p>top房源Dao</p>
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
@Repository("house.houseTopDao")
public class HouseTopDao {

	private String SQLID="house.houseTopDao.";
	
    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    
    /**
     * 
     * 查询top房源信息
     *
     * @author yd
     * @created 2017年3月17日 下午4:12:39
     *
     * @param paramMap
     * @return
     */
    public HouseTopVo findHouseTopVoByHouse(Map<String, Object> paramMap){
    	return mybatisDaoContext.findOneSlave(SQLID+"findHouseTopVoByHouse", HouseTopVo.class, paramMap);
    }
    
    /**
     * 
     * 新增房源基础信息
     *
     * @author liujun
     * @created 2016年4月9日 上午11:51:23
     *
     * @param houseTop
     */
    public int insertHouseTop(HouseTopEntity houseTop) {
    	
    	if(!Check.NuNObj(houseTop)
    			&&!Check.NuNStr(houseTop.getHouseBaseFid())
    			&&!Check.NuNObj(houseTop.getRentWay())){
    		String fid = houseTop.getHouseBaseFid()+houseTop.getRentWay()+houseTop.getRoomFid();
    		fid = MD5Util.MD5Encode(fid);
    		houseTop.setFid(fid);
    	}
		return mybatisDaoContext.save(SQLID+"insertSelective", houseTop);
	}
    
    /**
     * 
     * 分页查询塔尖民宿房源列表
     *
     * @author bushujie
     * @created 2017年3月17日 下午3:55:31
     *
     * @param houseTopDto
     * @return
     */
    public PagingResult<HouseTopListVo> findTopHouseListPage(HouseTopDto houseTopDto){
    	PageBounds pageBounds=new PageBounds();
    	pageBounds.setPage(houseTopDto.getPage());
    	pageBounds.setLimit(houseTopDto.getLimit());
    	return mybatisDaoContext.findForPage(SQLID+"findTopHouseListPage", HouseTopListVo.class, houseTopDto, pageBounds);
    }
    
    /**
     * 
     * 查询top房源详情
     *
     * @author bushujie
     * @created 2017年3月22日 上午10:22:32
     *
     * @param fid
     * @return
     */
    public HouseTopDetail findHouseTopDetail(String fid){
    	return mybatisDaoContext.findOne(SQLID+"findHouseTopDetail", HouseTopDetail.class, fid);
    }

	/**
	 * 根据排序编号更新排序编号
	 * @author lunan
	 * @param newSortTop,oldSortTop  (将oldSortTop更新成newSortTop)
	 * @return
	 */
	public int updateHouseTopByTopSort(Integer newSortTop,Integer oldSortTop){
		if(Check.NuNObjs(newSortTop,oldSortTop)){
			return 0;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("newTopSort",newSortTop);
		map.put("oldSortTop",oldSortTop);
		return mybatisDaoContext.update(SQLID+"updateHouseTopByTopSort",map);
	}

	/**
	 * 根据fid更新HouseTop
	 * @author lunan
	 * @param houseTop
	 * @return
	 */
	public int updateHouseTopByfid(HouseTopEntity houseTop){
		if(!Check.NuNObj(houseTop) && !Check.NuNStr(houseTop.getFid())){
			return mybatisDaoContext.update(SQLID+"updateHouseTopByfid",houseTop);
		}else {
			return 0;
		}
	}

	/**
	 * 根据排序编号进行上浮操作
	 * @author lunan
	 * @param newSortTop,oldSortTop  (将oldSortTop更新成newSortTop)
	 *                    根据排序编号进行上浮操作(排在前面的顺序要换到后面)
	 * @return
	 */
	public int updateHouseTopSortFloat(Integer newSortTop,Integer oldSortTop){
		if(Check.NuNObjs(newSortTop,oldSortTop)){
			return 0;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("newTopSort",newSortTop);
		map.put("oldSortTop",oldSortTop);
		return mybatisDaoContext.update(SQLID+"updateHouseTopSortFloat",map);
	}

	/**
	 * 根据排序编号进行下沉操作
	 * @author lunan
	 * @param newSortTop,oldSortTop 根据排序编号进行下沉操作(排在后面的顺序要换到前面)
	 * @return
	 */
	public int updateHouseTopSortSink(Integer newSortTop,Integer oldSortTop){
		if(Check.NuNObjs(newSortTop,oldSortTop)){
			return 0;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("newTopSort",newSortTop);
		map.put("oldSortTop",oldSortTop);
		return mybatisDaoContext.update(SQLID+"updateHouseTopSortSink",map);
	}

	/**
	 * 根据fid查询
	 * @author lunan
	 * @param  houseTopFid
	 * @return
	 */
	public HouseTopEntity findHouseTopEntityByfid(String houseTopFid){
		if(Check.NuNStr(houseTopFid)){
			return null;
		}
		return mybatisDaoContext.findOne(SQLID+"findHouseTopEntityByfid",HouseTopEntity.class,houseTopFid);
	}
}
