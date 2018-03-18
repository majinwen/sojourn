/**
 * @FileName: TenantOrderService.java
 * @Package com.ziroom.minsu.report.customer.service
 * 
 * @author zl
 * @created 2017年5月8日 上午10:26:20
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.report.customer.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.common.service.ReportService;
import com.ziroom.minsu.report.customer.dao.TenantOrderDao;
import com.ziroom.minsu.report.customer.dto.TenantRequest;
import com.ziroom.minsu.report.customer.vo.TenantOrderVo;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
@Service("report.tenantOrderService")
public class TenantOrderService  implements ReportService<TenantOrderVo, TenantRequest> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TenantOrderService.class);
 
	@Resource(name="report.tenantOrderDao")
	private TenantOrderDao tenantOrderDao;
	
	@Override
	public PagingResult<TenantOrderVo> getPageInfo(TenantRequest par) {
		LogUtil.info(LOGGER, "房客订单信息报表参数，parameter={}", JsonEntityTransform.Object2Json(par));
		try {
			return tenantOrderDao.getTenantOrderList(par);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "查询房客订单信息异常，e={}", e);
		}
		return new PagingResult<TenantOrderVo>();
	}
 
	@Override
	public Long countDataInfo(TenantRequest par) {
		// TODO Auto-generated method stub
		return null;
	}

}
