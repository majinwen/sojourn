/**
 * @FileName: TonightDiscountDao.java
 * @Package com.ziroom.minsu.services.house.dao
 * 
 * @author bushujie
 * @created 2017年5月10日 下午3:45:03
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.TonightDiscountEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p></p>
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
@Repository("house.tonightDiscountDao")
public class TonightDiscountDao {
	
    private String SQLID="house.tonightDiscountDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     * 插入今夜特价
     *
     * @author bushujie
     * @created 2017年5月10日 下午3:53:59
     *
     * @param tonightDiscountEntity
     */
    public void insertTonightDiscount(TonightDiscountEntity tonightDiscountEntity){
    	mybatisDaoContext.save(SQLID+"insertTonightDiscount", tonightDiscountEntity);
    }

    /**
      * 按条件查询今夜特价
      * @author wangwentao
      * @created 2017/5/11 11:45
      *
      * @param
      * @return
      */
    public List<TonightDiscountEntity> findTonightDiscountByCondition(TonightDiscountEntity tonightDiscountEntity) {
        return mybatisDaoContext.findAll(SQLID + "findTonightDiscountByCondition", TonightDiscountEntity.class, tonightDiscountEntity);
    }

    /**
      * 今夜特价整租查询
      * @author wangwentao
      * @created 2017/5/11 11:45
      *
      * @param
      * @return
      */
    public TonightDiscountEntity findTonightDiscountEntire(TonightDiscountEntity tonightDiscountEntity) {
        return mybatisDaoContext.findOne(SQLID + "findTonightDiscountEntire", TonightDiscountEntity.class, tonightDiscountEntity);
    }

    /**
      * 今夜特价分组查询
      * @author wangwentao
      * @created 2017/5/11 11:45
      *
      * @param
      * @return
      */
    public TonightDiscountEntity findTonightDiscountSub(TonightDiscountEntity tonightDiscountEntity) {
        return mybatisDaoContext.findOne(SQLID + "findTonightDiscountSub", TonightDiscountEntity.class, tonightDiscountEntity);
    }
    
    /**
     * 
     * 查询今日要提醒房东设置今日特价的房东uid列表
     *
     * @author bushujie
     * @created 2017年5月16日 下午5:50:00
     *
     * @param paramMap
     * @return
     */
    public PagingResult<HouseBaseMsgEntity> findRemindLandlordUid(Map<String, Object> paramMap){
    	PageBounds pageBounds=new PageBounds();
    	pageBounds.setPage(Integer.valueOf(paramMap.get("page").toString()));
    	pageBounds.setLimit(Integer.valueOf(paramMap.get("limit").toString()));
    	return mybatisDaoContext.findForPage(SQLID+"findRemindLandlordUid", HouseBaseMsgEntity.class, paramMap, pageBounds);
    }
}
