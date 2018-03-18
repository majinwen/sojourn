package com.ziroom.minsu.report.customer.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.common.service.ReportService;
import com.ziroom.minsu.report.customer.dao.TenantInfoDao;
import com.ziroom.minsu.report.customer.dto.TenantRequest;
import com.ziroom.minsu.report.customer.vo.UserTenantInfoVo;



/**
 * <p>用户 TenantService</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp on 2017/5/2.
 * @version 1.0
 * @since 1.0
 */
@Service("report.tenantService")
public class TenantService implements ReportService <UserTenantInfoVo,TenantRequest>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TenantService.class);
	
	@Resource(name="report.tenantInfoDao")
	private TenantInfoDao tenantInfoDao;
	/**
     * 房客信息详细
     * @author lusp
     * @param landlordRequest
     * @return UserTenantInfoVo
     */
	public PagingResult<UserTenantInfoVo> getUserTenantInfoVo(TenantRequest request){
		if(Check.NuNObj(request)){
			LogUtil.info(LOGGER, " TenantService getUserTenantInfoVo param:{}", JsonEntityTransform.Object2Json(request));
    		return null;
    	}
		PagingResult<UserTenantInfoVo> result = new PagingResult<UserTenantInfoVo>();
		result = tenantInfoDao.getTenantInfo(request);
		return  result;
	}
	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#getPageInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public PagingResult<UserTenantInfoVo> getPageInfo(TenantRequest request) {
		if(Check.NuNObj(request)){
			LogUtil.info(LOGGER, " TenantService getPageInfo param:{}", JsonEntityTransform.Object2Json(request));
    		return null;
    	}
		PagingResult<UserTenantInfoVo> result = new PagingResult<UserTenantInfoVo>();
		result = tenantInfoDao.getTenantInfo(request);
		return  result;
	}
	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#countDataInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public Long countDataInfo(TenantRequest par) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
