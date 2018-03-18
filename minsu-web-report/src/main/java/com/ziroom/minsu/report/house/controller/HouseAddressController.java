package com.ziroom.minsu.report.house.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.CityRegionEntity;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.basedata.service.ConfCityService;
import com.ziroom.minsu.report.common.util.DealExcelUtil;
import com.ziroom.minsu.report.house.dto.HouseAddressRequest;
import com.ziroom.minsu.report.house.dto.HouseRequest;
import com.ziroom.minsu.report.house.service.HouseAddressService;
import com.ziroom.minsu.report.house.service.HouseService;
import com.ziroom.minsu.report.house.vo.HouseAddressVo;
import com.ziroom.minsu.services.basedata.api.inner.CityRegionService;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;

/**
 * 
 * <p>房源地址报表</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author baiwei on 2017/4/28.
 * @since 1.0
 * @version 1.0
 */

@Controller
@RequestMapping("/houseAddress")
public class HouseAddressController {
	/**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HouseAddressController.class);
    
    @Resource(name="basedata.cityRegionService")
    private CityRegionService cityRegionService;
    
    @Resource(name = "report.confCityService")
    ConfCityService confCityService;
    
    @Resource(name = "report.houseService")
    HouseService houseService;
    
    @Resource(name = "report.houseAddressService")
    HouseAddressService houseaddressService;
    
    /**
     * 
     * 整租--房源地址报表页面
     *
     * @author baiwei
     * @created 2017年4月28日 下午5:43:36
     *
     * @param model
     * @return
     */
    @RequestMapping("/toEntireHouseAddress")
    public String toEntireHouseAddress(Model model){
    	this.setModelRegionCity(model);
    	this.setHouseStatus(model);
    	return "/house/houseAddress/entireHouseAddress";
    }
    
	/**
     * 
     * 分租--房源地址报表页面
     *
     * @author baiwei
     * @created 2017年5月2日 上午9:39:09
     *
     * @param model
     * @return
     */
    @RequestMapping("/toSubHouseAddress")
    public String toSubHouseAddress(Model model){
    	this.setModelRegionCity(model);
    	this.setHouseStatus(model);
    	return "/house/houseAddress/subHouseAddress";
    }
    
    /**
     * 
     * 查询房源地址列表
     *
     * @author baiwei
     * @created 2017年5月2日 下午8:14:16
     *
     * @param paramRequest
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping("/dataList")
    @ResponseBody
    public PagingResult houseAddress(HouseAddressRequest paramRequest) {
        LogUtil.info(LOGGER, "houseAddress param:{}", JsonEntityTransform.Object2Json(paramRequest));
    	PagingResult result = new PagingResult();
        try{
        	PagingResult<HouseAddressVo> houseAddressVoList = houseaddressService.getHouseAddressByPage(paramRequest);
        	result.setRows(houseAddressVoList.getRows());
        	result.setTotal(houseAddressVoList.getTotal());
        }catch(Exception e){
        	LogUtil.error(LOGGER, "houseAddress error:{}",e);
        }
    	return result;
    }
    
	/**
	 * 为model设置国家 /大区 /城市
	 *
	 * @author baiwei
	 * @created 2017年4月28日 下午5:43:16
	 *
	 * @param model
	 */
	private void setModelRegionCity(Model model) {
		List<ConfCityEntity> listNation = confCityService.getNations();
		model.addAttribute("nationList", listNation);
		DataTransferObject resultJson = JsonEntityTransform.json2DataTransferObject(cityRegionService.fillAllRegion());
		List<CityRegionEntity> list = resultJson.parseData("list", new TypeReference<List<CityRegionEntity>>() {});
        model.addAttribute("regionList",list);
        List<Map<String,String>> cityMapList = confCityService.getCityListInfo();
        model.addAttribute("cityList", cityMapList);
	}
    
	/**
	 * 为model设置房源状态
	 *
	 * @author baiwei
	 * @created 2017年5月2日 下午3:01:37
	 *
	 * @param model
	 */
	private void setHouseStatus(Model model) {
		model.addAttribute("houseStatusMap", HouseStatusEnum.getValidEnumMap());
	}
	
	/**
	 * 
	 * 房源地址报表导出
	 *
	 * @author baiwei
	 * @created 2017年5月2日 上午11:56:33
	 *
	 * @param paramRequest
	 * @param response
	 */
	@RequestMapping("/houseAddressExcel")
	public void houseAddressExcel(HouseAddressRequest paramRequest,HttpServletResponse response){
		DealExcelUtil dealExcelUtil = new DealExcelUtil(houseaddressService,paramRequest, null, "houseAddressExcel");
	    dealExcelUtil.exportZipFile(response,"getHouseAddressByPage");//指定调用的方法名
	}

}
