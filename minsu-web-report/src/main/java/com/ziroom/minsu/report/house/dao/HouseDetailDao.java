package com.ziroom.minsu.report.house.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.house.dto.HouseEvaluateRequest;
import com.ziroom.minsu.report.house.dto.HouseRequest;
import com.ziroom.minsu.report.house.vo.*;
import com.ziroom.minsu.report.order.vo.HouseOrderFilterVo;
import com.ziroom.minsu.report.order.vo.HouseOrderLifeCycleVo;
import com.ziroom.minsu.report.house.valenum.HouseRequestTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * <p>HouseDetailDao</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/5.
 * @version 1.0
 * @since 1.0
 */
@Repository("report.houseDetailDao")
public class HouseDetailDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(HouseDetailDao.class);

	
    private String SQLID="report.houseDetailDao.";

    @Autowired
    @Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    
    
    /**
     * 房源周期
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public PagingResult<HouseLifeCycleVo> getHouseLifeCycle(HouseRequest houseRequest){
        
    	if(Check.NuNObj(houseRequest)){
    		LogUtil.info(LOGGER, "getEntireHouseLifeCycle param :{}", JsonEntityTransform.Object2Json(houseRequest));
    		return null;
    	}
    	if(Check.NuNObj(houseRequest.getRentWay())){
    		LogUtil.info(LOGGER, "getEntireHouseLifeCycle param rentWay:{}", houseRequest.getRentWay());
    		return null;
    	}
    	
    	PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(houseRequest.getLimit());
        pageBounds.setPage(houseRequest.getPage());
        if(houseRequest.getRentWay() == HouseRequestTypeEnum.ENTIRE_RENT.getCode()){
        	return mybatisDaoContext.findForPage(SQLID + "entireHouseLifeCycle", HouseLifeCycleVo.class, houseRequest, pageBounds);
        }else if(houseRequest.getRentWay() == HouseRequestTypeEnum.SUB_RENT.getCode()){
        	return mybatisDaoContext.findForPage(SQLID + "subHouseLifeCycle", HouseLifeCycleVo.class, houseRequest, pageBounds);
        }
        return null;
    }
    
    
    /**
     * 房源订单周期
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public PagingResult<HouseOrderLifeCycleVo> getHouseOrderLifeCycle(HouseRequest houseRequest){
    	if(Check.NuNObj(houseRequest)){
    		LogUtil.info(LOGGER, "getEntireHouseOrderLifeCycle param :{}", JsonEntityTransform.Object2Json(houseRequest));
    		return null;
    	}
    	if(Check.NuNObj(houseRequest.getRentWay())){
    		LogUtil.info(LOGGER, "getEntireHouseOrderLifeCycle param rentWay:{}", houseRequest.getRentWay());
    		return null;
    	}
    	PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(houseRequest.getLimit());
        pageBounds.setPage(houseRequest.getPage());
        if(houseRequest.getRentWay() == HouseRequestTypeEnum.ENTIRE_RENT.getCode()){
        	return mybatisDaoContext.findForPage(SQLID + "entireHouseOrderLifeCycle", HouseOrderLifeCycleVo.class, houseRequest, pageBounds);
        }else if(houseRequest.getRentWay() == HouseRequestTypeEnum.SUB_RENT.getCode()){
        	return mybatisDaoContext.findForPage(SQLID + "subHouseOrderLifeCycle", HouseOrderLifeCycleVo.class, houseRequest, pageBounds);
        }
        return null;
    }
    
    
    /**
     * 房源渠道信息
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public PagingResult<HouseCommonVo> getHouseChannelInfo(HouseRequest houseRequest){
    	if(Check.NuNObj(houseRequest)){
    		LogUtil.info(LOGGER, "getHouseChannelInfo param :{}", JsonEntityTransform.Object2Json(houseRequest));
    		return null;
    	}
    	if(Check.NuNObj(houseRequest.getRentWay())){
    		LogUtil.info(LOGGER, "getHouseChannelInfo param rentWay:{}", houseRequest.getRentWay());
    		return null;
    	}
    	PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(houseRequest.getLimit());
        pageBounds.setPage(houseRequest.getPage());
        if(houseRequest.getRentWay() == HouseRequestTypeEnum.ENTIRE_RENT.getCode()){
        	return mybatisDaoContext.findForPage(SQLID + "entireHouseChannelInfo", HouseCommonVo.class, houseRequest, pageBounds);
        }else if(houseRequest.getRentWay() == HouseRequestTypeEnum.SUB_RENT.getCode()){
        	return mybatisDaoContext.findForPage(SQLID + "subHouseChannelInfo", HouseCommonVo.class, houseRequest, pageBounds);
        }
        return null;
    }
    
    
    /**
     * 房源评价信息
     * @author liyingjie
     * @param afiRequest
     * @param houseRequest
	 * @return
     */
    public PagingResult<HouseEvaluateVo> getHouseEvaInfo(HouseEvaluateRequest houseRequest){
    	if(Check.NuNObj(houseRequest)){
    		LogUtil.info(LOGGER, "getHouseEvaInfo param :{}", JsonEntityTransform.Object2Json(houseRequest));
    		return null;
    	}
    	if(Check.NuNObj(houseRequest.getRentWay())){
    		LogUtil.info(LOGGER, "getHouseEvaInfo param rentWay:{}", houseRequest.getRentWay());
    		return null;
    	}
    	PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(houseRequest.getLimit());
        pageBounds.setPage(houseRequest.getPage());
        if(houseRequest.getRentWay() == HouseRequestTypeEnum.ENTIRE_RENT.getCode()){
        	return mybatisDaoContext.findForPage(SQLID + "entireHouseEvaInfo", HouseEvaluateVo.class, houseRequest, pageBounds);
        }else if(houseRequest.getRentWay() == HouseRequestTypeEnum.SUB_RENT.getCode()){
        	return mybatisDaoContext.findForPage(SQLID + "subHouseEvaInfo", HouseEvaluateVo.class, houseRequest, pageBounds);
        }
        return null;
    }
    
    /**
     * 房源订单漏斗
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public PagingResult<HouseOrderFilterVo> getHouseFilterInfo(HouseRequest houseRequest){
    	if(Check.NuNObj(houseRequest)){
    		LogUtil.info(LOGGER, "getHouseFilterInfo param :{}", JsonEntityTransform.Object2Json(houseRequest));
    		return null;
    	}
    	if(Check.NuNObj(houseRequest.getRentWay())){
    		LogUtil.info(LOGGER, "getHouseFilterInfo param rentWay:{}", houseRequest.getRentWay());
    		return null;
    	}
    	PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(houseRequest.getLimit());
        pageBounds.setPage(houseRequest.getPage());
        if(houseRequest.getRentWay() == HouseRequestTypeEnum.ENTIRE_RENT.getCode()){
        	return mybatisDaoContext.findForPage(SQLID + "entireHouseFilterInfo", HouseOrderFilterVo.class, houseRequest, pageBounds);
        }else if(houseRequest.getRentWay() == HouseRequestTypeEnum.SUB_RENT.getCode()){
        	return mybatisDaoContext.findForPage(SQLID + "subHouseFilterInfo", HouseOrderFilterVo.class, houseRequest, pageBounds);
        }
        return null;
    }
    
    /**
     * 房源拍摄需求
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public PagingResult<HousePhotoVo> getHousePhotoInfo(HouseRequest houseRequest){
    	if(Check.NuNObj(houseRequest)){
    		LogUtil.info(LOGGER, "getHousePhotoInfo param :{}", JsonEntityTransform.Object2Json(houseRequest));
    		return null;
    	}
    	if(Check.NuNObj(houseRequest.getRentWay())){
    		LogUtil.info(LOGGER, "getHousePhotoInfo param rentWay:{}", houseRequest.getRentWay());
    		return null;
    	}
    	PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(houseRequest.getLimit());
        pageBounds.setPage(houseRequest.getPage());
        if(houseRequest.getRentWay() == HouseRequestTypeEnum.ENTIRE_RENT.getCode()){
        	return mybatisDaoContext.findForPage(SQLID + "entireHousePhotoInfo", HousePhotoVo.class, houseRequest, pageBounds);
        }else if(houseRequest.getRentWay() == HouseRequestTypeEnum.SUB_RENT.getCode()){
        	return mybatisDaoContext.findForPage(SQLID + "subHousePhotoInfo", HousePhotoVo.class, houseRequest, pageBounds);
        }
        return null;
    }
    
    
    /**
     * 房源审核
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public PagingResult<HouseAuditVo> getHouseAuditInfo(HouseRequest houseRequest){
    	if(Check.NuNObj(houseRequest)){
    		LogUtil.info(LOGGER, "getHouseAuditInfo param :{}", JsonEntityTransform.Object2Json(houseRequest));
    		return null;
    	}
    	if(Check.NuNObj(houseRequest.getRentWay())){
    		LogUtil.info(LOGGER, "getHouseAuditInfo param rentWay:{}", houseRequest.getRentWay());
    		return null;
    	}
    	PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(houseRequest.getLimit());
        pageBounds.setPage(houseRequest.getPage());
        if(houseRequest.getRentWay() == HouseRequestTypeEnum.ENTIRE_RENT.getCode()){
        	return mybatisDaoContext.findForPage(SQLID + "entireHouseAuditInfo", HouseAuditVo.class, houseRequest, pageBounds);
        }else if(houseRequest.getRentWay() == HouseRequestTypeEnum.SUB_RENT.getCode()){
        	return mybatisDaoContext.findForPage(SQLID + "subHouseAuditInfo", HouseAuditVo.class, houseRequest, pageBounds);
        }
        return null;
    }
    
}
