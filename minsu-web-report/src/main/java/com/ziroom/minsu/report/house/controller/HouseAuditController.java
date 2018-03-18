
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.CityRegionEntity;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.basedata.service.ConfCityService;
import com.ziroom.minsu.report.common.util.DealExcelUtil;
import com.ziroom.minsu.report.house.dto.HouseAuditRequest;
import com.ziroom.minsu.report.house.service.HouseAuditService;
import com.ziroom.minsu.report.house.vo.HouseAddressVo;
import com.ziroom.minsu.report.house.vo.HouseAuditVoNew;
import com.ziroom.minsu.services.basedata.api.inner.CityRegionService;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;


/**
 * <p>房源审核报表</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author baiwei
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("/houseAudit")
public class HouseAuditController {
	/**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HouseAuditController.class);
    
    @Resource(name = "report.confCityService")
    ConfCityService confCityService;
    
    @Resource(name="basedata.cityRegionService")
    private CityRegionService cityRegionService;
    
    @Resource(name="report.houseAuditService")
    HouseAuditService houseAuditService;
    /**
     * 
     * 整租--房源审核报表页面
     *
     * @author baiwei
     * @created 2017年5月4日 下午2:41:07
     *
     * @param model
     * @return
     */
    @RequestMapping("/toEntireHouseAudit")
    public String toEntireHouseAddress(Model model){
    	this.setModelRegionCity(model);
    	this.setHouseStatus(model);
    	return "/house/houseAudit/entireHouseAudit";
    } 
    
    /**
     * 
     * 分租--房源审核报表页面
     *
     * @author baiwei
     * @created 2017年5月4日 下午2:43:49
     *
     * @param model
     * @return
     */
    @RequestMapping("/toSubHouseAudit")
    public String toSubHouseAddress(Model model){
    	this.setModelRegionCity(model);
    	this.setHouseStatus(model);
    	return "/house/houseAudit/subHouseAudit";
    }
    
    /**
     * 
     * 查询房源审核列表
     *
     * @author baiwei
     * @created 2017年5月4日 下午5:45:36
     *
     * @param paramRequest
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping("/dataList")
    @ResponseBody
    public PagingResult houseAudit(HouseAuditRequest paramRequest){
    	LogUtil.info(LOGGER, "houseAddress param:{}", JsonEntityTransform.Object2Json(paramRequest));
    	PagingResult result = new PagingResult();
        try{
        	PagingResult<HouseAuditVoNew> houseAuditVoNewList = houseAuditService.getHouseAuditByPage(paramRequest);
        	result.setRows(houseAuditVoNewList.getRows());
        	result.setTotal(houseAuditVoNewList.getTotal());
        }catch(Exception e){
        	LogUtil.error(LOGGER, "houseAddress error:{}",e);
        }
    	return result;
    }
    
    /**
     * 
     * 为model设置国家 /大区 /城市
     *
     * @author baiwei
     * @created 2017年5月4日 下午2:41:41
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
     * 
     * 为model设置房源状态
     *
     * @author baiwei
     * @created 2017年5月4日 下午2:43:15
     *
     * @param model
     */
    private void setHouseStatus(Model model) {
		model.addAttribute("houseStatusMap", HouseStatusEnum.getValidEnumMap());
	}
    
    /**
     * 
     * 房源审核报表导出
     *
     * @author baiwei
     * @created 2017年5月4日 下午5:43:37
     *
     * @param request
     * @param response
     */
    @RequestMapping("/houseAuditExcel")
    public void houseAuditExcel(HouseAuditRequest request,HttpServletResponse response){
    	DealExcelUtil dealExcelUtil = new DealExcelUtil(houseAuditService,request, null, "houseAuditExcel");
	    dealExcelUtil.exportZipFile(response,"getHouseAuditByPage");//指定调用的方法名
    }
}
