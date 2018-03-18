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
import org.springframework.web.servlet.ModelAndView;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.common.util.DealExcelUtil;
import com.ziroom.minsu.report.customer.dto.TenantRequest;
import com.ziroom.minsu.report.customer.service.TenantService;
import com.ziroom.minsu.report.customer.vo.UserTenantInfoVo;

/**
 * <p>房客的统计信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp on on 2017/5/2.
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/tenant")
public class TenantController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TenantController.class);

    
    
    @Resource
    private TenantService tenantService;
    
    /**
     * 到房东信息详情页面
     * @author lusp
     * @param request
     * @return
     */
    @RequestMapping("/toTenantList")
    public ModelAndView toTenantList(HttpServletRequest request){
        ModelAndView maView  = new ModelAndView("/customerInfo/tenantList");
        return  maView;
    }
    
    
    /**
     * @author lusp
     * 房客信息统计页面
     * @param paramRequest
     * @param request
     * @return
     */
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/tenantList")
   @ResponseBody
   public PagingResult<UserTenantInfoVo> tenantList(TenantRequest paramRequest, HttpServletRequest request) {
       LogUtil.info(LOGGER, "tenantList param:{}", JsonEntityTransform.Object2Json(paramRequest));
       PagingResult<UserTenantInfoVo> result = new PagingResult<UserTenantInfoVo>();
       try{
    	   result = tenantService.getUserTenantInfoVo(paramRequest);
	   		
       }catch(Exception ex){
       	   LogUtil.error(LOGGER, "tenantList param:{},error:{}", JsonEntityTransform.Object2Json(paramRequest),ex);
       }
   	
   	   return result;
   }
	
    
    /**
     * 
     * @param paramRequest
     * @param request
     * @return
     */
   @RequestMapping("/tenantListExcelList")
   public void tenantListExcelList(TenantRequest paramRequest,HttpServletResponse response) {
        DealExcelUtil dealExcelUtil = new DealExcelUtil(tenantService,paramRequest, null, "tenant_data_list");
		dealExcelUtil.exportZipFile(response,"getUserTenantInfoVo");
        
    }


}
