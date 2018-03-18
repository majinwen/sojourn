/**
 * @FileName: HouseAddressDao.java
 * @Package com.ziroom.minsu.report.house.dao
 * 
 * @author baiwei
 * @created 2017年5月2日 下午9:19:57
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.house.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.house.dto.HouseAddressRequest;
import com.ziroom.minsu.report.house.valenum.HouseRequestTypeEnum;
import com.ziroom.minsu.report.house.vo.HouseAddressVo;

/**
 * <p>
 * TODO
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author baiwei
 * @since 1.0
 * @version 1.0
 */
@Repository("report.houseAddressDao")
public class HouseAddressDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseAddressDao.class);
	
	private String SQLID = "report.houseAddressDao.";

	@Autowired
	@Qualifier("minsuReport.all.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 
	 * 分页查询
	 *
	 * @author baiwei
	 * @created 2017年5月4日 下午1:49:12
	 *
	 * @param houseAddressRequest
	 * @return
	 */
	public PagingResult<HouseAddressVo> getHouseAddressByPage(HouseAddressRequest houseAddressRequest){
		if(Check.NuNObj(houseAddressRequest)){
    		LogUtil.info(LOGGER, "param :{}", JsonEntityTransform.Object2Json(houseAddressRequest));
    		return null;
    	}
    	if(Check.NuNObj(houseAddressRequest.getRentWay())){
    		LogUtil.info(LOGGER, "param rentWay:{}", houseAddressRequest.getRentWay());
    		return null;
    	}
    	PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(houseAddressRequest.getLimit());
        pageBounds.setPage(houseAddressRequest.getPage());
        if(houseAddressRequest.getRentWay() == HouseRequestTypeEnum.ENTIRE_RENT.getCode()){
        	return mybatisDaoContext.findForPage(SQLID + "entirehouseAddress", HouseAddressVo.class, houseAddressRequest.toMap(), pageBounds);
        }else if(houseAddressRequest.getRentWay() == HouseRequestTypeEnum.SUB_RENT.getCode()){
        	return mybatisDaoContext.findForPage(SQLID + "subHouseAddress", HouseAddressVo.class, houseAddressRequest.toMap(), pageBounds);
        }
        return null;
	}

	/**
	 * 不分页查询
	 *
	 * @author baiwei
	 * @created 2017年5月4日 上午10:42:19
	 *
	 * @param houseAddressRequest
	 * @return
	 */
	public List<HouseAddressVo> getHouseAddressList(HouseAddressRequest houseAddressRequest) {
		if(Check.NuNObj(houseAddressRequest)){
    		LogUtil.info(LOGGER, "param :{}", JsonEntityTransform.Object2Json(houseAddressRequest));
    		return null;
    	}
    	if(Check.NuNObj(houseAddressRequest.getRentWay())){
    		LogUtil.info(LOGGER, "param rentWay:{}", houseAddressRequest.getRentWay());
    		return null;
    	}
        if(houseAddressRequest.getRentWay() == HouseRequestTypeEnum.ENTIRE_RENT.getCode()){
        	return mybatisDaoContext.findAll(SQLID + "entirehouseAddress", HouseAddressVo.class, houseAddressRequest.toMap());
        }else if(houseAddressRequest.getRentWay() == HouseRequestTypeEnum.SUB_RENT.getCode()){
        	return mybatisDaoContext.findAll(SQLID + "subHouseAddress", HouseAddressVo.class, houseAddressRequest.toMap());
        }
        return null;
	}
}

