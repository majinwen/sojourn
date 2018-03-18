/**
 * @FileName: LandlordStaticController.java
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
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.CityRegionEntity;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.basedata.service.ConfCityService;
import com.ziroom.minsu.report.common.util.DealExcelUtil;
import com.ziroom.minsu.report.customer.dto.LandlordRequest;
import com.ziroom.minsu.report.customer.service.LandlordStaticService;
import com.ziroom.minsu.report.customer.vo.LandlordStaticVo;
import com.ziroom.minsu.services.basedata.api.inner.CityRegionService;

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
@RequestMapping("/landlordStatic")
public class LandlordStaticController {
	
		private static final Logger LOGGER = LoggerFactory.getLogger(LandlordStaticController.class);
		
		
		@Resource(name="report.landlordStaticService")
		private LandlordStaticService landlordStaticService;
		
	    @Resource(name = "report.confCityService")
	    ConfCityService confCityService;
	    
	    @Resource(name="basedata.cityRegionService")
	    private CityRegionService cityRegionService;
		
	    
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
	    @RequestMapping("/toList")
	    public String toList(HttpServletRequest request){

	         List<Map<String,String>> cityMapList = confCityService.getCityListInfo();
	         
	         request.setAttribute("cityList", cityMapList);
	         DataTransferObject resultJson = JsonEntityTransform.json2DataTransferObject(cityRegionService.fillAllRegion());
	         List<CityRegionEntity> regionList = resultJson.parseData("list", new TypeReference<List<CityRegionEntity>>() {});
	         request.setAttribute("regionList",regionList);
	         List<ConfCityEntity> nationList = confCityService.getNations();
	         request.setAttribute("nationList", nationList);
	    	
	    	return  "/customerInfo/landlordStaticList";
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
	   @RequestMapping("/listData")
	   @ResponseBody
	   public PagingResult<LandlordStaticVo> listData(LandlordRequest paramRequest) {
	       LogUtil.info(LOGGER, "landlordStaticList param:{}", JsonEntityTransform.Object2Json(paramRequest));
	       PagingResult<LandlordStaticVo> result = new PagingResult<LandlordStaticVo>();
	       try{
	    	   result = landlordStaticService.getPageInfo(paramRequest);
	       }catch(Exception ex){
	       	   LogUtil.error(LOGGER, "landlordStaticList param:{},error:{}", JsonEntityTransform.Object2Json(paramRequest),ex);
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
	   @RequestMapping("/exportList")
	   public void exportList(LandlordRequest paramRequest,HttpServletResponse response) {
		   
	       try{
	    	   
	    	   DealExcelUtil dealExcelUtil = new DealExcelUtil(landlordStaticService,paramRequest, null, "LandlordStatic_data_list");
	   		   dealExcelUtil.exportZipFile(response,"getPageInfo");
	    	   
	       }catch(Exception ex){
	       	   LogUtil.error(LOGGER, "exportLandlordStaticList param:{},error:{}", JsonEntityTransform.Object2Json(paramRequest),ex);
	       }
		    
	    }
	    
	    
	    
}
