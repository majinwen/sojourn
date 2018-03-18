/**
 * @FileName: HouseAuditDao.java
 * @Package com.ziroom.minsu.report.house.dao
 * 
 * @author baiwei
 * @created 2017年5月4日 下午7:14:16
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.house.dao;

import java.util.List;
import java.util.Map;

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
import com.ziroom.minsu.report.house.dto.HouseAuditRequest;
import com.ziroom.minsu.report.house.valenum.HouseRequestTypeEnum;
import com.ziroom.minsu.report.house.vo.HouseAuditVoNew;

/**
 * <p>TODO</p>
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
@Repository("report.houseAuditDao")
public class HouseAuditDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseAuditDao.class);
	
	private String SQLID = "report.houseAuditDao.";

	@Autowired
	@Qualifier("minsuReport.all.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * 分页查询
	 *
	 * @author baiwei
	 * @created 2017年5月4日 下午7:18:02
	 *
	 * @param houseAuditRequest
	 * @return
	 */
	public PagingResult<HouseAuditVoNew> getHouseAuditByPage(HouseAuditRequest houseAuditRequest) {
		if(Check.NuNObj(houseAuditRequest)){
    		LogUtil.info(LOGGER, "param :{}", JsonEntityTransform.Object2Json(houseAuditRequest));
    		return null;
    	}
    	if(Check.NuNObj(houseAuditRequest.getRentWay())){
    		LogUtil.info(LOGGER, "param rentWay:{}", houseAuditRequest.getRentWay());
    		return null;
    	}
    	PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(houseAuditRequest.getLimit());
        pageBounds.setPage(houseAuditRequest.getPage());
        if(houseAuditRequest.getRentWay() == HouseRequestTypeEnum.ENTIRE_RENT.getCode()){
        	return mybatisDaoContext.findForPage(SQLID + "entirehouseAudit", HouseAuditVoNew.class, houseAuditRequest.toMap(), pageBounds);
        }else if(houseAuditRequest.getRentWay() == HouseRequestTypeEnum.SUB_RENT.getCode()){
        	return mybatisDaoContext.findForPage(SQLID + "subHouseAudit", HouseAuditVoNew.class, houseAuditRequest.toMap(), pageBounds);
        }
        return null;
	}

	/**
	 * 不分页查询
	 *
	 * @author baiwei
	 * @created 2017年5月4日 下午7:19:41
	 *
	 * @param houseAuditRequest
	 * @return
	 */
	public List<HouseAuditVoNew> getHouseAuditList(HouseAuditRequest houseAuditRequest) {
		if(Check.NuNObj(houseAuditRequest)){
    		LogUtil.info(LOGGER, "param :{}", JsonEntityTransform.Object2Json(houseAuditRequest));
    		return null;
    	}
    	if(Check.NuNObj(houseAuditRequest.getRentWay())){
    		LogUtil.info(LOGGER, "param rentWay:{}", houseAuditRequest.getRentWay());
    		return null;
    	}
        if(houseAuditRequest.getRentWay() == HouseRequestTypeEnum.ENTIRE_RENT.getCode()){
        	return mybatisDaoContext.findAll(SQLID + "entirehouseAudit", HouseAuditVoNew.class, houseAuditRequest.toMap());
        }else if(houseAuditRequest.getRentWay() == HouseRequestTypeEnum.SUB_RENT.getCode()){
        	return mybatisDaoContext.findAll(SQLID + "subHouseAudit", HouseAuditVoNew.class, houseAuditRequest.toMap());
        }
        return null;
	}

	/**
	 * 查询品质审核未通过次数
	 *
	 * @author baiwei
	 * @created 2017年5月6日 下午3:09:11
	 *
	 * @param mapTimes
	 * @return
	 */
	public int findHouseAuditNoLogTime(Map<String, Object> mapTimes) {
		if((Integer)mapTimes.get("rentWay") == HouseRequestTypeEnum.ENTIRE_RENT.getCode()){
			if(!Check.NuNStr((String)mapTimes.get("fid"))){
				return mybatisDaoContext.findOne(SQLID + "findEntireHouseAuditNoLogTime", Integer.class, mapTimes);
			}
		}else if((Integer)mapTimes.get("rentWay") == HouseRequestTypeEnum.SUB_RENT.getCode()){
			if(!Check.NuNStr((String)mapTimes.get("fid"))){
				return mybatisDaoContext.findOne(SQLID + "findSubHouseAuditNoLogTime", Integer.class, mapTimes);
			}
		}
		return 0;
	}
	
	
	
}
