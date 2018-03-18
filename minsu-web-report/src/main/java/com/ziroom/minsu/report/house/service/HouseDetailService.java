package com.ziroom.minsu.report.house.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.common.service.ReportService;
import com.ziroom.minsu.report.house.dto.HouseEvaluateRequest;
import com.ziroom.minsu.report.house.dto.HouseRequest;
import com.ziroom.minsu.report.house.dao.HouseDetailDao;
import com.ziroom.minsu.report.house.vo.*;
import com.ziroom.minsu.report.order.vo.HouseOrderFilterVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>房源 HouseDetailService</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/6.
 * @version 1.0
 * @since 1.0
 */
@Service("report.houseDetailService")
public class HouseDetailService implements ReportService <HouseEvaluateVo,HouseEvaluateRequest>{

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseDetailService.class);
 
    @Resource(name="report.houseDetailDao")
    private HouseDetailDao houseDetailDao;

    /**
     * 房源周期信息
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public PagingResult<HouseLifeCycleVo> getHouseLifeCycle(HouseRequest houseRequest) {
    	if(Check.NuNObj(houseRequest)){
			LogUtil.info(LOGGER, " HouseDetailService getPageInfo param:{}", JsonEntityTransform.Object2Json(houseRequest));
    		return null;
    	}
    	return houseDetailDao.getHouseLifeCycle(houseRequest);
    }

	
    /**
     * 房源渠道信息
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public PagingResult<HouseCommonVo> getHouseChannelInfo(HouseRequest houseRequest){
    	if(Check.NuNObj(houseRequest)){
			LogUtil.info(LOGGER, "HouseDetailService getHouseChannelInfo param:{}", JsonEntityTransform.Object2Json(houseRequest));
    		return null;
    	}
    	
    	return houseDetailDao.getHouseChannelInfo(houseRequest);
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
			LogUtil.info(LOGGER, "HouseDetailService getHouseEvaInfo param:{}", JsonEntityTransform.Object2Json(houseRequest));
    		return null;
    	}
    	return houseDetailDao.getHouseEvaInfo(houseRequest);
    }
    
    
    /**
     * 房源订单漏斗
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public PagingResult<HouseOrderFilterVo> getHouseFilterInfo(HouseRequest houseRequest){
    	if(Check.NuNObj(houseRequest)){
			LogUtil.info(LOGGER, "HouseDetailService getHouseFilterInfo param:{}", JsonEntityTransform.Object2Json(houseRequest));
    		return null;
    	}
    	return houseDetailDao.getHouseFilterInfo(houseRequest);
    }
	
    
    /**
     * 房源拍摄需求
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public PagingResult<HousePhotoVo> getHousePhotoInfo(HouseRequest houseRequest){
    	if(Check.NuNObj(houseRequest)){
			LogUtil.info(LOGGER, "HouseDetailService getHousePhotoInfo param:{}", JsonEntityTransform.Object2Json(houseRequest));
    		return null;
    	}
    	return houseDetailDao.getHousePhotoInfo(houseRequest);
    }
    

    /**
     * 房源审核
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public PagingResult<HouseAuditVo> getHouseAuditInfo(HouseRequest houseRequest){
    	if(Check.NuNObj(houseRequest)){
			LogUtil.info(LOGGER, "HouseDetailService getHouseAuditInfo param:{}", JsonEntityTransform.Object2Json(houseRequest));
    		return null;
    	}
    	return houseDetailDao.getHouseAuditInfo(houseRequest);
    }
    
	@Override
	public Long countDataInfo(HouseEvaluateRequest par) {
		return null;
	}


	@Override
	public PagingResult<HouseEvaluateVo> getPageInfo(HouseEvaluateRequest houseRequest) {
		if(Check.NuNObj(houseRequest)){
			LogUtil.info(LOGGER, "HouseOrderLifeCycleService param :{}", JsonEntityTransform.Object2Json(houseRequest));
			return null;
		}
		if(Check.NuNObj(houseRequest.getRentWay())){
			LogUtil.info(LOGGER, "HouseOrderLifeCycleService param rentWay:{}", houseRequest.getRentWay());
			return null;
		}
		return houseDetailDao.getHouseEvaInfo(houseRequest);
	}

	
}
