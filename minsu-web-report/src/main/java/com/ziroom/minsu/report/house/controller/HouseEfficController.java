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
import com.ziroom.minsu.report.common.util.DealExcelUtil;
import com.ziroom.minsu.report.house.dto.HouseEfficDto;
import com.ziroom.minsu.report.house.service.HouseEfficService;
import com.ziroom.minsu.report.house.vo.HouseEfficVo;


/**
 * 
 * <p>房源效率报表</p>
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
@RequestMapping("/houseEffic")
public class HouseEfficController {

	private static Logger LOGGER = LoggerFactory.getLogger(HouseEfficController.class);
	
	@Resource(name="report.houseEfficService")
	HouseEfficService houseEfficService;
	
	@Resource(name="report.confCityService")
	ConfCityService confCityService;
	
	/**
	 * 
	 * 跳转到房源房源效率报表页面
	 *
	 * @author ls
	 * @created 2017年5月8日 上午9:18:34
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/toHouseEfficExcel")
	public ModelAndView toHouseEfficExcel(HttpServletRequest request){
		ModelAndView maView  = new ModelAndView("/house/houseEffic/houseEfficListExcel");
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
	 * 分页查询房源效率信息
	 *
	 * @author ls
	 * @created 2017年5月8日 上午9:18:54
	 *
	 * @param paramDto
	 * @return
	 */
	@RequestMapping("/getHouseEfficVoList")
	@ResponseBody
	public PagingResult<HouseEfficVo> getHouseEfficVoList(HouseEfficDto paramDto){
		LogUtil.info(LOGGER, "HouseEfficController, getHouseEfficVoList, param={}", JsonEntityTransform.Object2Json(paramDto));
		PagingResult<HouseEfficVo> houseEfficRVoList = null;
		try {
			houseEfficRVoList = houseEfficService.getHouseEfficExcelList(paramDto);
			if(Check.NuNCollection(houseEfficRVoList.getRows()) || houseEfficRVoList.getTotal() == 0){
				houseEfficRVoList.setRows(new ArrayList<HouseEfficVo>());
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "房源效率信息查询失败，param={}，e={}", JsonEntityTransform.Object2Json(paramDto),e);
		}
		return houseEfficRVoList;
	}
	
	/**
	 * 
	 * 导出到一个压缩包（包含一个或者多个Excel，800数据/Excel）
	 *
	 * @author ls
	 * @created 2017年5月8日 上午9:19:27
	 *
	 * @param paramDto
	 * @param response
	 */
	@RequestMapping("/getHouseEfficVoExcel")
	public void getHouseInfoReportVoExcel(HouseEfficDto paramDto, HttpServletResponse response){
        		  DealExcelUtil dealExcelUtil = new DealExcelUtil(houseEfficService,paramDto, null, "HouseInfoReport");
	      dealExcelUtil.exportZipFile(response,"getHouseEfficExcelList");//指定调用的方法名
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
