/**
 * @FileName: HouseBaseExtDao.java
 * @Package com.ziroom.minsu.services.house.dao
 * 
 * @author bushujie
 * @created 2016年4月8日 下午4:08:47
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.services.house.dto.HouseBaseExtRequest;
import com.ziroom.minsu.services.house.entity.HouseBaseExtVo;


/**
 * <p>房源dao</p>
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
@Repository("house.houseBaseExtDao")
public class HouseBaseExtDao {
	
	private String SQLID="house.houseBaseExtDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    
    /**
     * 
     * 新增房源基础信息扩展
     *
     * @author bushujie
     * @created 2016年4月8日 下午4:13:39
     *
     * @param houseBaseExt
     */
    public int insertHouseBaseExt(HouseBaseExtEntity houseBaseExt){
    	if(!Check.NuNObj(houseBaseExt)&&Check.NuNStr(houseBaseExt.getCheckOutRulesCode())){
    		houseBaseExt.setCheckOutRulesCode("TradeRulesEnum005002");
    	}
    	return mybatisDaoContext.save(SQLID+"insertHouseBaseExt", houseBaseExt);
    }
    
    /**
     * 
     * 更新房源基础信息扩展
     *
     * @author liujun
     * @created 2016年4月9日 上午11:24:05
     *
     * @param houseBaseExt
     * @return
     */
    public int updateHouseBaseExt(HouseBaseExtEntity houseBaseExt) {
    	return mybatisDaoContext.update(SQLID+"updateHouseBaseExtByFid", houseBaseExt);
    }

	/**
	 * 根据房源基本信息逻辑id查询房源基本信息扩展vo
	 *
	 * @author liujun
	 * @created 2016年4月10日 上午12:12:03
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HouseBaseExtVo findBaseExtVoByHouseBaseFid(String houseBaseFid) {
		return mybatisDaoContext.findOne(SQLID+"findBaseExtVoByHouseBaseFid", HouseBaseExtVo.class, houseBaseFid);
	}
	
	/**
	 * 
	 *获取扩展信息实体
	 * @author jixd
	 * @created 2016年6月15日 下午2:21:05
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HouseBaseExtEntity getHouseBaseExtByHouseBaseFid(String houseBaseFid){
		return mybatisDaoContext.findOne(SQLID+"getHouseBaseExtByHouseBaseFid", HouseBaseExtEntity.class, houseBaseFid);
	}

	/**
	 * 根据房源基本信息逻辑id更新房源基本信息扩展
	 *
	 * @author liujun
	 * @created 2016年4月11日 上午9:50:03
	 *
	 * @param houseBaseExt
	 */
	public int updateHouseBaseExtByHouseBaseFid(HouseBaseExtEntity houseBaseExt) {
		return mybatisDaoContext.update(SQLID+"updateHouseBaseExtByHouseBaseFid", houseBaseExt);
	}
	
	/**
	 * 分页查询房源基本信息扩展信息
	 *
	 * @author liujun
	 * @created 2016年7月25日
	 *
	 * @param request
	 * @return
	 */
	public PagingResult<HouseBaseExtEntity> findHouseBaseExtListByCondition(HouseBaseExtRequest request) {
		PageBounds pageBounds=new PageBounds();
    	pageBounds.setPage(request.getPage());
    	pageBounds.setLimit(request.getLimit());
		return mybatisDaoContext.findForPage(SQLID + "findHouseBaseExtListByCondition", 
				HouseBaseExtEntity.class, request, pageBounds);
	}

	/**
	 * 分页查询分租房源的 ext
	 * @author jixd
	 * @created 2017年06月29日 17:22:11
	 * @param
	 * @return
	 */
	public PagingResult<HouseBaseExtEntity> findHouseBaseExtByPageF(HouseBaseExtRequest request) {
		PageBounds pageBounds=new PageBounds();
		pageBounds.setPage(request.getPage());
		pageBounds.setLimit(request.getLimit());
        return mybatisDaoContext.findForPage(SQLID + "findHouseBaseExtByPageF", HouseBaseExtEntity.class, request, pageBounds);
    }
	
	/**
	 * 
	 * 特殊更新，楼号-门牌号可以更新成空
	 *
	 * @author bushujie
	 * @created 2017年7月17日 上午11:29:12
	 *
	 * @param houseBaseExt
	 * @return
	 */
	public int specialUpdateHouseBaseExtByHouseBaseFid(HouseBaseExtEntity houseBaseExt) {
		return mybatisDaoContext.update(SQLID+"specialUpdateHouseBaseExtByHouseBaseFid", houseBaseExt);
	}

}
