/**
 * @FileName: HouseViscosityController.java
 * @Package com.ziroom.minsu.report.house.controller
 * 
 * @author ls
 * @created 2017年5月3日 下午2:30:29
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.house.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.NationCodeEntity;
import com.ziroom.minsu.entity.conf.CityRegionEntity;
import com.ziroom.minsu.report.basedata.dto.CityRequest;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.basedata.service.ConfCityService;
import com.ziroom.minsu.report.basedata.valenum.HouseStatusEnum;
import com.ziroom.minsu.report.basedata.valenum.NationEnum;
import com.ziroom.minsu.report.basedata.valenum.RegionEnum;
import com.ziroom.minsu.report.common.util.DealExcelUtil;
import com.ziroom.minsu.report.house.dto.HouseViscosityDto;
import com.ziroom.minsu.report.house.service.HouseViscosityService;
import com.ziroom.minsu.report.house.vo.HouseViscosityVo;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;

/**
 * <p>房源粘性报表</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author ls
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("/houseViscosity")
public class HouseViscosityController {

	public static final Logger LOGGER = LoggerFactory.getLogger(HouseViscosityController.class);
	
	@Resource(name="report.houseViscosityService")
	HouseViscosityService houseViscosityService;
	
	@Resource(name="report.confCityService")
	ConfCityService confCityService;
	
	/**
	 * 
	 * 跳转到房源粘性报表页面
	 *
	 * @author ls
	 * @created 2017年5月8日 上午10:04:41
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/toHouseViscosity")
	public ModelAndView toHouseViscosity(HttpServletRequest request){
		ModelAndView maView  = new ModelAndView("/house/houseViscosity/houseViscosityListExcel");
        List<Map<String, String>> cityList = getCityList();
		
		List<NationCodeEntity> allNationList = confCityService.getAllNationList();
		maView.addObject("allNationList", allNationList);
		
		List<CityRegionEntity> allRegionList = confCityService.getAllRegionList();
		maView.addObject("allRegionList", allRegionList);
		maView.addObject("cityList", cityList);
		maView.addObject("houseStatusMap", HouseStatusEnum.getValidEnumMap());
		return maView;
	}
	
	/**
	 * 
	 * 房源粘性报表——分页查询
	 *
	 * @author ls
	 * @created 2017年5月8日 上午10:05:11
	 *
	 * @param paramDto
	 * @return
	 */
	@RequestMapping("/getHouseViscosityVoList")
	@ResponseBody
	public PagingResult<HouseViscosityVo> getHouseEfficVoList(HouseViscosityDto paramDto){
		PagingResult<HouseViscosityVo> houseViscosityRVoList = null;
		try {
			houseViscosityRVoList = houseViscosityService.getHouseViscosityExcelList(paramDto);
			if(Check.NuNCollection(houseViscosityRVoList.getRows()) || houseViscosityRVoList.getTotal() == 0){
				houseViscosityRVoList.setRows(new ArrayList<HouseViscosityVo>());
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "查询房源粘性数据异常，param={},e={}", JsonEntityTransform.Object2Json(paramDto),e);
		}
		return houseViscosityRVoList;
	}
	
	/**
	 * 
	 * 房源粘性报表——导出到一个压缩包（包含一个或者多个Excel，800数据/Excel） 
	 *
	 * @author ls
	 * @created 2017年5月8日 上午10:05:42
	 *
	 * @param paramDto
	 * @param response
	 */
	@RequestMapping("/getHouseViscosityExcel")
	public void getHouseViscosityExcel(HouseViscosityDto paramDto, HttpServletResponse response){
	    DealExcelUtil dealExcelUtil = new DealExcelUtil(houseViscosityService,paramDto, null, "HouseViscosity");
	    dealExcelUtil.exportZipFile(response,"getHouseViscosityExcelList");//指定调用的方法名
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
}
