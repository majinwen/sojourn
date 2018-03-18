/**
 * @FileName: TenantOrderServiceTest.java
 * @Package com.ziroom.minsu.report.test.customer
 * 
 * @author zl
 * @created 2017年5月8日 下午1:59:30
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.report.test.customer;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.report.customer.dto.TenantRequest;
import com.ziroom.minsu.report.customer.service.TenantOrderService;
import com.ziroom.minsu.report.customer.vo.TenantOrderVo;

import base.BaseTest;

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
public class TenantOrderServiceTest extends BaseTest {
	
	@Resource(name="report.tenantOrderService")
	private TenantOrderService tenantOrderService;
	
	@Test
	public void  tenantOrder(){
		
		TenantRequest par = new TenantRequest();
		par.setBeginTime("2016-05-16 00:00:00");
		par.setEndTime("2017-05-16 13:59:59");
		
		PagingResult<TenantOrderVo>  result = tenantOrderService.getPageInfo(par);
		
		System.err.println(JsonEntityTransform.Object2Json(result));
		
	}

}
