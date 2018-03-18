package com.ziroom.minsu.report.customer.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.common.service.ReportService;
import com.ziroom.minsu.report.customer.dao.CusInfoDao;
import com.ziroom.minsu.report.house.dto.HouseRequest;
import com.ziroom.minsu.report.customer.vo.UserCusInfoVo;
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
@Service("report.userCusInfoService")
public class UserCusInfoService implements ReportService <UserCusInfoVo,HouseRequest>{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCusInfoService.class);
 
    @Resource(name="report.cusInfoDao")
    private CusInfoDao cusInfoDao;

    /**
     * 订单明细信息
     * @author bushujie
     * @param afiRequest
     * @return
     */
    public PagingResult<UserCusInfoVo> getUserCusInfo(HouseRequest houseRequest) {
    	if(Check.NuNObj(houseRequest)){
			LogUtil.info(LOGGER, " UserCusInfoService getUserCusInfo param:{}", JsonEntityTransform.Object2Json(houseRequest));
    		return null;
    	}
    	return cusInfoDao.getUserCusInfo(houseRequest);
    }

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#getPageInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public PagingResult<UserCusInfoVo> getPageInfo(HouseRequest par) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#countDataInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public Long countDataInfo(HouseRequest par) {
		// TODO Auto-generated method stub
		return null;
	}
}
