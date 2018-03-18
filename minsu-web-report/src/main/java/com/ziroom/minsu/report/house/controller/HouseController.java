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

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.common.constant.Constant;
import com.ziroom.minsu.report.common.util.DealExcelUtil;
import com.ziroom.minsu.report.basedata.dto.CityRequest;
import com.ziroom.minsu.report.house.dto.HouseRequest;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.house.vo.HouseCountResultVo;

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
@RequestMapping("/house")
public class HouseController {
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseController.class);
	
    @Resource(name = "report.houseService")
    HouseService houseService;
    
    
    @Resource(name = "report.confCityService")
    ConfCityService confCityService;
    
    /**
     * 整租 当前房源状态统计页面
     * @param request
     * @return
     */
    @RequestMapping("/toHouseList")
    public  ModelAndView toOrderList(HttpServletRequest request){
    	ModelAndView maView  = new ModelAndView("/house/houseList");
    	List<Map<String,String>> cityMapList = this.getCityList(); 
        maView.addObject("cityList", cityMapList);
    	return  maView;
    }

    /**
     * 分租 当前房源状态统计页面
     * @param request
     * @return
     */
    @RequestMapping("/toSharedRentList")
    public  ModelAndView toSharedRentList(HttpServletRequest request){
    	ModelAndView maView  = new ModelAndView("/house/sharedRentList");
    	List<Map<String,String>> cityMapList = this.getCityList(); 
        maView.addObject("cityList", cityMapList);
    	
    	return  maView;
    }
    
    /**
     * 分租按套 当前房源状态统计页面
     * @param request
     * @return
     */
    @RequestMapping("/toSharedEntireRentList")
    public  ModelAndView toSharedEntireRentList(HttpServletRequest request){
    	ModelAndView maView  = new ModelAndView("/house/sharedEntireRentList");
    	List<Map<String,String>> cityMapList = this.getCityList(); 
        maView.addObject("cityList", cityMapList);
    	
    	return  maView;
    }
    
    /**
     * 整租 房源审核过程状态统计
     * @param request
     * @return
     */
    @RequestMapping("/toLimitHouseList")
    public  ModelAndView toLimitHouseList(HttpServletRequest request){
    	ModelAndView maView  = new ModelAndView("/house/houseLimitList");
    	List<Map<String,String>> cityMapList = this.getCityList(); 
        maView.addObject("cityList", cityMapList);
    	return  maView;
    }

    /**
     * 分租 房源审核过程状态统计
     * @param request
     * @return
     */
    @RequestMapping("/toLimitSharedRentList")
    public  ModelAndView toLimitSharedRentList(HttpServletRequest request){
    	ModelAndView maView  = new ModelAndView("/house/sharedLimitRentList");
    	List<Map<String,String>> cityMapList = this.getCityList(); 
        maView.addObject("cityList", cityMapList);
    	return  maView;
    }
    
    /**
     * 分租按套 房源审核过程状态统计
     * @param request
     * @return
     */
    @RequestMapping("/toSharedEntireLimitRentList")
    public  ModelAndView toSharedEntireLimitRentList(HttpServletRequest request){
    	ModelAndView maView  = new ModelAndView("/house/sharedEntireLimitRentList");
    	List<Map<String,String>> cityMapList = this.getCityList(); 
        maView.addObject("cityList", cityMapList);
    	
    	return  maView;
    }
    
    
    
    /**
     * 处理返回数据
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void dealDataList(HouseRequest paramRequest,PagingResult result){
    	CityRequest cityRequest = new CityRequest();
    	cityRequest.setLimit(paramRequest.getLimit());
    	cityRequest.setPage(paramRequest.getPage());
        PagingResult pagingResult =  confCityService.getPageInfo(cityRequest);
        List<ConfCityEntity> cityList = pagingResult.getRows();
        List<HouseCountResultVo> resultList = new ArrayList<HouseCountResultVo>(cityList.size());
        for(ConfCityEntity city : cityList){
        	if(Check.NuNStr(city.getCode())){
        		continue;
        	}
        	paramRequest.setCityCode(city.getCode());
        	HouseCountResultVo resultVo = houseService.getEntireCurrentStatusInfo(paramRequest);
        	resultVo.setCityCode(city.getCode());
        	resultVo.setCityName(city.getShowName());
        	resultList.add(resultVo);
        }
        result.setRows(resultList);
        result.setTotal(pagingResult.getTotal());
    }
    
    /**
     * 处理返回数据
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void dealLimitDataList(HouseRequest paramRequest,PagingResult result){
    	CityRequest cityRequest = new CityRequest();
    	cityRequest.setLimit(paramRequest.getLimit());
    	cityRequest.setPage(paramRequest.getPage());
        PagingResult pagingResult =  confCityService.getPageInfo(cityRequest);
        List<ConfCityEntity> cityList = pagingResult.getRows();
        List<HouseCountResultVo> resultList = new ArrayList<HouseCountResultVo>(cityList.size());
        for(ConfCityEntity city : cityList){
        	if(Check.NuNStr(city.getCode())){
        		continue;
        	}
        	paramRequest.setCityCode(city.getCode());
        	HouseCountResultVo resultVo = houseService.getLimitEntireCurrentStatusInfo(paramRequest);
        	resultVo.setCityCode(city.getCode());
        	resultVo.setCityName(city.getShowName());
        	resultList.add(resultVo);
        }
        result.setRows(resultList);
        result.setTotal(pagingResult.getTotal());
    }
    
    /**
     * 当前房源状态统计页面
     * @param paramRequest
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/dataList")
    @ResponseBody
    public PagingResult houseCountDataList(HouseRequest paramRequest, HttpServletRequest request) {
        LogUtil.info(LOGGER, "houseCountDataList param:{}", JsonEntityTransform.Object2Json(paramRequest));
    	PagingResult result = new PagingResult();
        try{
        	if(Check.NuNObj(paramRequest) || Check.NuNStr(paramRequest.getCityCode())){
        		this.dealDataList(paramRequest,result);
        	}else {
        		HouseCountResultVo resultVo = houseService.getEntireCurrentStatusInfo(paramRequest);
        		resultVo.setCityCode(paramRequest.getCityCode());
        		/*ConfCityEntity cityEntity = confCityService.getCityByCode(paramRequest.getCityCode());
        		resultVo.setCityName(cityEntity.getShowName());*/
        		List<HouseCountResultVo> resultList = new ArrayList<HouseCountResultVo>(1);
        		resultList.add(resultVo);
            	result.setRows(resultList);
                result.setTotal(1);
    		}
        }catch(Exception ex){
        	LogUtil.error(LOGGER, "houseCountDataList param:{},error:{}", JsonEntityTransform.Object2Json(paramRequest),ex);
        }
    	
    	return result;
    }
    
   
    /**
     * 房源审核过程状态统计
     * @param paramRequest
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/limitCountDataList")
    @ResponseBody
    public PagingResult houseLimitCountDataList(HouseRequest paramRequest, HttpServletRequest request) {
    	LogUtil.info(LOGGER, "houseLimitCountDataList param:{}", JsonEntityTransform.Object2Json(paramRequest));
    	PagingResult result = new PagingResult();
        try{
        	List<HouseCountResultVo> resultList = new ArrayList<HouseCountResultVo>();
        	if(Check.NuNObj(paramRequest) || Check.NuNStr(paramRequest.getCityCode())){
        		this.dealLimitDataList(paramRequest,result);
        	}else {
        		HouseCountResultVo resultVo = houseService.getLimitEntireCurrentStatusInfo(paramRequest);
        		resultVo.setCityCode(paramRequest.getCityCode());
        		/*ConfCityEntity cityEntity = confCityService.getCityByCode(paramRequest.getCityCode());
        		resultVo.setCityName(cityEntity.getShowName());*/
            	resultList.add(resultVo);
            	result.setRows(resultList);
                result.setTotal(1);
    		}
        }catch(Exception ex){
        	LogUtil.error(LOGGER, "houseLimitCountDataList param:{},error:{}", JsonEntityTransform.Object2Json(paramRequest),ex);
        }
    	return result;
    }
    
   
    
   
    
    /**
     * @param paramRequest
     * @param request
     * @return
     */
   @RequestMapping("/houseDataListExcel")
   public void houseDataExcelList(HouseRequest paramRequest,HttpServletResponse response) {
        DealExcelUtil test = new DealExcelUtil(houseService,paramRequest, null, "house-current-status");
        List<BaseEntity> resultList = this.getEntireCurrentStatusDataList(paramRequest);
        test.exportExcelFile(response,resultList);
    }

   
   /**
    * @param paramRequest
    * @param request
    * @return
    */
  @RequestMapping("/houseLimitListExcel")
  public void houseLimitDataExcelList(HouseRequest paramRequest,HttpServletResponse response) {
       DealExcelUtil test = new DealExcelUtil(houseService,paramRequest, null, "house-oper");
       List<BaseEntity> resultList = this.getLimitEntireCurrentDataList(paramRequest);
       test.exportExcelFile(response,resultList);
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
    * 获取返回data数据
    * @param request
    * @return
    */
   @SuppressWarnings({ "rawtypes", "unchecked" })
   private List<BaseEntity> getLimitEntireCurrentDataList(HouseRequest paramRequest){
   	   CityRequest cityRequest = new CityRequest();
   	   cityRequest.setLimit(Constant.PAGE_LIMIT);
       cityRequest.setPage(paramRequest.getPage());
       PagingResult pagingResult =  confCityService.getPageInfo(cityRequest);
       List<ConfCityEntity> cityList = pagingResult.getRows();
       List<BaseEntity> resultList = new ArrayList<BaseEntity>(cityList.size());
       for(ConfCityEntity city : cityList){
       	if(Check.NuNStr(city.getCode())){
       		continue;
       	}
       	paramRequest.setCityCode(city.getCode());
       	HouseCountResultVo resultVo = houseService.getLimitEntireCurrentStatusInfo(paramRequest);
       	resultVo.setCityCode(city.getCode());
       	resultVo.setCityName(city.getShowName());
       	resultList.add(resultVo);
       }
       return resultList;
   }
   
   /**
    * 获取返回data数据
    * @param request
    * @return
    */
   @SuppressWarnings({ "rawtypes", "unchecked" })
   private List<BaseEntity> getEntireCurrentStatusDataList(HouseRequest paramRequest){
   	   CityRequest cityRequest = new CityRequest();
   	   cityRequest.setLimit(Constant.PAGE_LIMIT);
   	   cityRequest.setPage(paramRequest.getPage());
       PagingResult pagingResult =  confCityService.getPageInfo(cityRequest);
       List<ConfCityEntity> cityList = pagingResult.getRows();
       List<BaseEntity> resultList = new ArrayList<BaseEntity>(cityList.size());
       for(ConfCityEntity city : cityList){
       	if(Check.NuNStr(city.getCode())){
       		continue;
       	}
       	paramRequest.setCityCode(city.getCode());
       	HouseCountResultVo resultVo = houseService.getEntireCurrentStatusInfo(paramRequest);
       	resultVo.setCityCode(city.getCode());
       	resultVo.setCityName(city.getShowName());
       	resultList.add(resultVo);
       }
       return resultList;
   }
   
}
