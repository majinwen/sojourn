package com.ziroom.minsu.report.house.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ziroom.minsu.report.basedata.service.ConfCityService;
import com.ziroom.minsu.report.house.service.HouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.common.util.DealExcelUtil;
import com.ziroom.minsu.report.basedata.dto.CityRequest;
import com.ziroom.minsu.report.house.dto.HouseRequest;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;

/**
 * <p>注释的模板</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/30.
 * @version 1.0
 * @since 1.0
 */

@Controller
@RequestMapping("/houseLifeCycle")
public class HouseLifeCycleController {
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseLifeCycleController.class);
	
    @Resource(name = "report.houseService")
    HouseService houseService;
    
    
    @Resource(name = "report.confCityService")
    ConfCityService confCityService;
    
    /**
     * 整租 当前房源状态统计页面
     * @param request
     * @return
     */
    @RequestMapping("/toEntireHouseLifeCycle")
    public  ModelAndView toEntireHouseLifeCycle(HttpServletRequest request){
    	ModelAndView maView  = new ModelAndView("/house/houseLifeCycle/entireHouseLifeCycle");
    	List<Map<String,String>> cityMapList = this.getCityList(); 
        maView.addObject("cityList", cityMapList);
    	return  maView;
    }

    /**
     * 分租 当前房源状态统计页面
     * @param request
     * @return
     */
    @RequestMapping("/toSubHouseLifeCycle")
    public  ModelAndView toSubHouseLifeCycle(HttpServletRequest request){
    	ModelAndView maView  = new ModelAndView("/house/houseLifeCycle/subHouseLifeCycle");
    	List<Map<String,String>> cityMapList = this.getCityList(); 
        maView.addObject("cityList", cityMapList);
    	
    	return  maView;
    }
    
  
    /**
     * 当前房源状态统计页面
     * @param paramRequest
     * @param request
     * @return
     */
    
	@SuppressWarnings("rawtypes")
	@RequestMapping("/dataList")
    @ResponseBody
    public PagingResult houseLifeCycle(HouseRequest paramRequest, HttpServletRequest request) {
        LogUtil.info(LOGGER, "houseLifeCycle param:{}", JsonEntityTransform.Object2Json(paramRequest));
    	PagingResult result = new PagingResult();
        try{
        	result = houseService.getPageInfo(paramRequest);
        }catch(Exception ex){
        	LogUtil.error(LOGGER, "houseLifeCycle param:{},error:{}", JsonEntityTransform.Object2Json(paramRequest),ex);
        }
    	
    	return result;
    }
    

   /**
    * 获取城市列表
    * @author liyingjie
    * @return
    */
   private List<Map<String,String>> getCityList(){
	   CityRequest cityRequest = new CityRequest();
       List<ConfCityEntity> cityList = confCityService.getOpenCity(cityRequest);
       List<Map<String,String>> cityMapList = new ArrayList<Map<String,String>>(cityList.size()); 
       Map<String,String> paramMap = new HashMap<String, String>(2);
       for(ConfCityEntity cEntity : cityList){
       	paramMap = new HashMap<String, String>(2);
       	paramMap.put("name", cEntity.getShowName());
       	paramMap.put("code", cEntity.getCode());
       	cityMapList.add(paramMap);
       }
       return cityMapList;
   }
   
   /**
    * 房源统计excel导出
    * @param paramRequest
    * @param response
    * @return
    */
  @RequestMapping("/houseLifeCycleExcelList")
  public void houseOrderExcelList(HouseRequest paramRequest,HttpServletResponse response) {
       DealExcelUtil test = new DealExcelUtil(houseService,paramRequest, null, "houseLifeCycleList-oper");
       test.exportZipFile(response);
   }
   
}
