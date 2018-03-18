/**
 * @FileName: TenantOrderController.java
 * @Package com.ziroom.minsu.report.customer.controller
 * 
 * @author zl
 * @created 2017年5月16日 下午8:43:57
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.report.customer.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.common.util.DealExcelUtil;
import com.ziroom.minsu.report.customer.dto.TenantRequest;
import com.ziroom.minsu.report.customer.service.TenantOrderService;
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
@Controller
@RequestMapping("/tenantOrder")
public class TenantOrderController {
	
		private static final Logger LOGGER = LoggerFactory.getLogger(TenantOrderController.class);
		
		
	    @Resource
	    private TenantOrderService tenantOrderService;
		
	    
	    /**
	     * 
	     * 跳转到列表页面
	     *
	     * @author zl
	     * @created 2017年5月16日 下午8:46:34
	     *
	     * @param request
	     * @return
	     */
	    @RequestMapping("/toTenantOrderList")
	    public String toTenantOrderList(HttpServletRequest request){

	    	return  "/customerInfo/tenantOrderList";
	    }
	    
	    
	  
	    /**
	     * 
	     * 列表数据
	     *
	     * @author zl
	     * @created 2017年5月16日 下午8:50:16
	     *
	     * @param paramRequest
	     * @return
	     */
	   @RequestMapping("/tenantOrderList")
	   @ResponseBody
	   public PagingResult<TenantOrderVo> tenantOrderList(TenantRequest paramRequest) {
	       LogUtil.info(LOGGER, "tenantOrderList param:{}", JsonEntityTransform.Object2Json(paramRequest));
	       PagingResult<TenantOrderVo> result = new PagingResult<TenantOrderVo>();
	       try{
	    	   result = tenantOrderService.getPageInfo(paramRequest);
	       }catch(Exception ex){
	       	   LogUtil.error(LOGGER, "tenantOrderList param:{},error:{}", JsonEntityTransform.Object2Json(paramRequest),ex);
	       }
	   	
	   	   return result;
	   }
		
	    
	    /**
	     * 
	     * 数据导出
	     *
	     * @author zl
	     * @created 2017年5月16日 下午8:55:47
	     *
	     * @param paramRequest
	     * @param response
	     */
	   @RequestMapping("/exportTenantOrderList")
	   public void exportTenantOrderList(TenantRequest paramRequest,HttpServletResponse response) {
		   
	       try{
	    	   DealExcelUtil dealExcelUtil = new DealExcelUtil(tenantOrderService,paramRequest, null, "TenantOrder_data_list");
	   		   dealExcelUtil.exportZipFile(response,"getPageInfo");
	       }catch(Exception ex){
	       	   LogUtil.error(LOGGER, "exportTenantOrderList param:{},error:{}", JsonEntityTransform.Object2Json(paramRequest),ex);
	       }
		    
	    }
	    
	    
	    
}
