package com.ziroom.minsu.report.house.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.common.service.ReportService;
import com.ziroom.minsu.report.house.dto.HouseRequest;
import com.ziroom.minsu.report.house.dao.HouseDetailDao;
import com.ziroom.minsu.report.order.vo.HouseOrderLifeCycleVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>房源 HouseService</p>
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
@Service("report.houseOrderLifeCycleService")
public class HouseOrderLifeCycleService implements ReportService <HouseOrderLifeCycleVo,HouseRequest>{

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseOrderLifeCycleService.class);

    @Resource(name="report.houseDetailDao")
    private HouseDetailDao houseDetailDao;

    @Override
    public PagingResult<HouseOrderLifeCycleVo> getPageInfo(HouseRequest houseRequest) {
    	if(Check.NuNObj(houseRequest)){
    		LogUtil.info(LOGGER, "HouseOrderLifeCycleService param :{}", JsonEntityTransform.Object2Json(houseRequest));
    		return null;
    	}
    	if(Check.NuNObj(houseRequest.getRentWay())){
    		LogUtil.info(LOGGER, "HouseOrderLifeCycleService param rentWay:{}", houseRequest.getRentWay());
    		return null;
    	}
    	return houseDetailDao.getHouseOrderLifeCycle(houseRequest);
    }

	


	@Override
	public Long countDataInfo(HouseRequest par) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
