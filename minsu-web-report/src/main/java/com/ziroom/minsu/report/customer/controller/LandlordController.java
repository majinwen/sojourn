package com.ziroom.minsu.report.customer.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.CityRegionEntity;
import com.ziroom.minsu.report.basedata.dto.CityRequest;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.basedata.service.ConfCityService;
import com.ziroom.minsu.report.common.util.DealExcelUtil;
import com.ziroom.minsu.report.customer.dto.LandRequest;
import com.ziroom.minsu.report.customer.dto.LandlordRequest;
import com.ziroom.minsu.report.customer.service.LandlordService;
import com.ziroom.minsu.report.customer.vo.UserLandlordInfoVo;
import com.ziroom.minsu.report.order.service.OrderEvaluateService;
import com.ziroom.minsu.services.basedata.api.inner.CityRegionService;
import com.ziroom.minsu.services.common.utils.ValueUtil;

/**
 * <p>房东的统计信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp on on 2017/4/28.
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/landlord")
public class LandlordController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LandlordController.class);

    @Resource(name = "report.orderEvaluateService")
    OrderEvaluateService orderEvaluateService;
    
    @Resource(name = "report.confCityService")
    ConfCityService confCityService;
    
    @Resource(name="basedata.cityRegionService")
    private CityRegionService cityRegionService;
    
    @Resource
    private LandlordService landlordService;
    
    /**
     * 到房东信息详情页面
     * @author lusp
     * @param request
     * @return
     */
    @RequestMapping("/toLandlordList")
    public ModelAndView toLandList(HttpServletRequest request){
        ModelAndView maView  = new ModelAndView("/customerInfo/landlordList");
        List<Map<String,String>> cityMapList = confCityService.getCityListInfo();
        maView.addObject("cityList", cityMapList);
        DataTransferObject resultJson = JsonEntityTransform.json2DataTransferObject(cityRegionService.fillAllRegion());
        List<CityRegionEntity> regionList = resultJson.parseData("list", new TypeReference<List<CityRegionEntity>>() {});
        maView.addObject("regionList",regionList);
        List<ConfCityEntity> nationList = confCityService.getNations();
        maView.addObject("nationList", nationList);
        return  maView;
    }
    
    
    /**
     * @author lusp
     * 房东信息统计页面
     * @param paramRequest
     * @param request
     * @return
     */
	@RequestMapping("/landlordList")
    @ResponseBody
    public PagingResult<UserLandlordInfoVo> landlordList(LandlordRequest landlordRequest) {
		long start = System.currentTimeMillis();
		LogUtil.info(LOGGER, "LandlordController landlordList param:{}", JsonEntityTransform.Object2Json(landlordRequest));
        PagingResult<UserLandlordInfoVo> result = new PagingResult<UserLandlordInfoVo>();
        try{
    	    result = landlordService.getPageInfo(landlordRequest);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "LandlordController landlordList param:{}", e, JsonEntityTransform.Object2Json(landlordRequest));
		}
        long end = System.currentTimeMillis();
        LogUtil.info(LOGGER, "LandlordController landlordList方法耗时={}", end-start);
    	return result;
    }
	
	/**
     * 
     * 房东信息详情-下载房东信息详情数据列表
     *
     * @author lusp
     * @created 2017年5月2日
     *
     * @param landlordRequest,response
     * @return
     */
    @RequestMapping("/landlordExcelList")
    public void landlordExcelList(LandlordRequest landlordRequest, HttpServletResponse response){
        DealExcelUtil dealExcelUtil = new DealExcelUtil(landlordService,landlordRequest, null, "landlord_data_list");
		dealExcelUtil.exportZipFile(response,"getPageInfo");
    }

    /**
     * 转化当前的时间格式
     * @author lusp
     * @param paramRequest
     * @throws ParseException
     */
    private String  getTimeTitle(String org,LandRequest paramRequest) throws ParseException {
        StringBuilder sb = new StringBuilder();
        sb.append(ValueUtil.getStrValue(org));

        if (!Check.NuNStr(paramRequest.getStarTimeStr())){
            sb.append(paramRequest.getStarTimeStr());
        }
        sb.append("-");
        if (!Check.NuNStr(paramRequest.getEndTimeStr())){
            sb.append(paramRequest.getEndTimeStr());
        }
        //
        return sb.toString();
    }



    /**
     * 转化当前的时间格式
     * @author lusp
     * @param paramRequest
     * @throws ParseException
     */
    private void transTime(LandRequest paramRequest) throws ParseException {
        if (!Check.NuNStr(paramRequest.getStarTimeStr())){
            paramRequest.setStarTime(DateUtil.parseDate(paramRequest.getStarTimeStr(),"yyyy-MM-dd"));
        }

        if (!Check.NuNStr(paramRequest.getEndTimeStr())){
            paramRequest.setEndTime(DateUtil.parseDate(paramRequest.getEndTimeStr(),"yyyy-MM-dd"));
        }
    }



}
