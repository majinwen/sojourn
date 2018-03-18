package com.ziroom.minsu.report.house.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ziroom.minsu.report.basedata.service.ConfCityService;
import com.ziroom.minsu.report.customer.service.UserCusInfoService;
import com.ziroom.minsu.report.house.service.HouseDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
@RequestMapping("/houseDetail")
public class HouseDetailController {
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseDetailController.class);
	
    @Resource(name = "report.houseDetailService")
    HouseDetailService houseDetailService;
    
    
    @Resource(name = "report.confCityService")
    ConfCityService confCityService;
    
    @Resource(name="report.userCusInfoService")
    UserCusInfoService userCusInfoService;
    
    /**
     * 房源下载页面
     * @param request
     * @return
     */
    @RequestMapping("/toHouseDetailExcel")
    public  ModelAndView toHouseDetailExcel(HttpServletRequest request){
    	ModelAndView maView  = new ModelAndView("/house/houseDetailExcel/houseDetailExcel");
    	List<Map<String,String>> cityMapList = this.getCityList(); 
        maView.addObject("cityList", cityMapList);
    	return  maView;
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
    * @param request
    * @return
    */
  @RequestMapping("/houseDetailExcel")
  public void houseDetailExcel(HouseRequest paramRequest,HttpServletResponse response) {
       DealExcelUtil dealExcelUtil = new DealExcelUtil(houseDetailService,paramRequest, null, "houseDetailExcel-oper");
       dealExcelUtil.exportZipFile(response,paramRequest.getMethodName());//指定调用的方法名
   }
  
  /**
   * 
   * 客户信息统计excel导出
   *
   * @author bushujie
   * @created 2016年9月20日 下午5:07:37
   *
   * @param paramRequest
   * @param response
   */
  @RequestMapping("/userCusInfoExcel")
  public void userCusInfoExcel(HouseRequest paramRequest,HttpServletResponse response) {
	  DealExcelUtil dealExcelUtil = new DealExcelUtil(userCusInfoService,paramRequest, null, "userCusInfoList-oper");
      dealExcelUtil.exportZipFile(response,paramRequest.getMethodName());//指定调用的方法名
  }
}
