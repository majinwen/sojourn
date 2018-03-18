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
import com.ziroom.minsu.report.basedata.valenum.HouseOrderTypeEnum;
import com.ziroom.minsu.report.basedata.valenum.HouseQualityGradeEnum;
import com.ziroom.minsu.report.basedata.valenum.HouseRentWayEnum;
import com.ziroom.minsu.report.basedata.valenum.HouseStatusEnum;
import com.ziroom.minsu.report.basedata.valenum.NationEnum;
import com.ziroom.minsu.report.basedata.valenum.RegionEnum;
import com.ziroom.minsu.report.common.util.DealExcelUtil;
import com.ziroom.minsu.report.house.dto.HouseInfoReportDto;
import com.ziroom.minsu.report.house.service.HouseInfoReportService;
import com.ziroom.minsu.report.house.vo.HouseInfoReportVo;
import com.ziroom.minsu.report.order.vo.OrderInformationVo;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;


/**
 * 
 * <p>房源信息报表</p>
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
@RequestMapping("/houseInfoReport")
public class HouseInfoReportController {

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseInfoReportController.class);
	
	@Resource(name="report.confCityService")
	ConfCityService confCityService;
	                       
	@Resource(name="report.houseInfoReportService")
	HouseInfoReportService houseInfoReportService;
	
	/**
	 * @Title: toHouseInfoExcel  
	 * @Description: 跳转到“查询和导出房源信息”的页面
	 * @param houseInfoDto
	 * @param response
	 * @return ModelAndView   
	 * @throws
	 */
	@RequestMapping("/toHouseInfoExcel")
	public ModelAndView toHouseInfoExcel(HttpServletRequest request){
		ModelAndView maView  = new ModelAndView("/house/houseInfo/houseInfoListExcel");
		List<Map<String, String>> cityList = getCityList();
		
		List<NationCodeEntity> allNationList = confCityService.getAllNationList();
		maView.addObject("allNationList", allNationList);
		
		List<CityRegionEntity> allRegionList = confCityService.getAllRegionList();
		maView.addObject("allRegionList", allRegionList);
		
		maView.addObject("cityList", cityList);
		maView.addObject("houseStatusMap", HouseStatusEnum.getValidEnumMap());
		maView.addObject("houseQualityGradeMap", HouseQualityGradeEnum.getValidEnumMap());
		return maView;
	}
	
	@RequestMapping("/getHouseInfoReportVoList")
	@ResponseBody
	public PagingResult<HouseInfoReportVo> getHouseInfoReportVoList(HouseInfoReportDto paramDto){
		
		PagingResult<HouseInfoReportVo> houseInfoRVoList = null;
		try {
			houseInfoRVoList = houseInfoReportService.getHouseInfoReportVoList(paramDto);
			if(Check.NuNCollection(houseInfoRVoList.getRows()) || houseInfoRVoList.getTotal() == 0){
				houseInfoRVoList.setRows(new ArrayList<HouseInfoReportVo>());
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "房源信息查询失败，param={}，e={}", JsonEntityTransform.Object2Json(paramDto),e);
		}
		return houseInfoRVoList;
	}
	
	/**
	 * 
	 * 导出zip（内含一张或多长excel表格，800/张）
	 *
	 * @author ls
	 * @created 2017年5月4日 下午5:44:20
	 *
	 * @param paramDto
	 * @param response
	 */
	@RequestMapping("/getHouseInfoReportVoExcel")
	public void getHouseInfoReportVoExcel(HouseInfoReportDto paramDto, HttpServletResponse response){
		  DealExcelUtil dealExcelUtil = new DealExcelUtil(houseInfoReportService,paramDto, null, "HouseInfoReport");
	      dealExcelUtil.exportZipFile(response,"getHouseInfoReportVoList");//指定调用的方法名
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
