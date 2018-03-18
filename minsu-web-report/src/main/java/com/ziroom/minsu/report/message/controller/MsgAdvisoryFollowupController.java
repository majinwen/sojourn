/**
 * @FileName: MsgAdvisoryFollowupController.java
 * @Package com.ziroom.minsu.report.message.controller
 * 
 * @author ls
 * @created 2017年5月31日 上午11:11:44
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.message.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.basedata.dto.CityRequest;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.basedata.service.ConfCityService;
import com.ziroom.minsu.report.common.util.DealExcelUtil;
import com.ziroom.minsu.report.house.dto.HouseInfoReportDto;
import com.ziroom.minsu.report.house.service.HouseInfoReportService;
import com.ziroom.minsu.report.house.vo.HouseInfoReportVo;
import com.ziroom.minsu.report.message.dto.MsgAdvisoryFollowRequest;
import com.ziroom.minsu.report.message.service.MsgAdvisoryFollowupService;
import com.ziroom.minsu.report.message.vo.MsgAdvisoryFollowVo;
import com.ziroom.minsu.report.order.dto.OrderFollowQueryRequest;

/**
 * <p>TODO</p>
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
@RequestMapping("messageFollowup")
public class MsgAdvisoryFollowupController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(MsgAdvisoryFollowupController.class);
	
	@Resource(name="report.msgAdvisoryFollowupService")
	private MsgAdvisoryFollowupService msgAdvisoryFollowupService;
	
	@Resource(name="report.confCityService")
	ConfCityService confCityService;
	
	/**
	 * 
	 *  跳转订单跟进列表
	 *
	 * @author loushuai
	 * @created 2017年5月26日 上午10:47:19
	 *
	 * @param request
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping("/listMsgAdvisoryFollow")
	public ModelAndView listMsgAdvisoryFollow(HttpServletRequest request) throws SOAParseException{
		List<Map<String, String>> cityList = getCityList();
		ModelAndView mv = new ModelAndView("message/msgAdvisoryFollowup");
		mv.addObject("cityList", cityList);
		return mv;
	}
	
	/**
	 * 
	 * TODO
	 *
	 * @author ls
	 * @created 2017年5月31日 下午1:54:41
	 *
	 * @param paramRequest
	 * @return
	 */
	@RequestMapping("/queryAllNeedFollowList")
	@ResponseBody
	public String queryAllNeedFollowList(MsgAdvisoryFollowRequest paramRequest){
		fillCreateTime(paramRequest);
		String allFollowListJson = null;
		LogUtil.info(LOGGER, "queryAllNeedFollowList方法参数  paramRequest={}", paramRequest);
		try {
			allFollowListJson = msgAdvisoryFollowupService.queryAllNeedFollowList(paramRequest);
			DataTransferObject  allFollowListDto = JsonEntityTransform.json2DataTransferObject(allFollowListJson);
			Long size =  SOAResParseUtil.getLongFromDataByKey(allFollowListJson,"size");
			List<MsgAdvisoryFollowVo> imFollowVoList = null;
	    	if(allFollowListDto.getCode()==DataTransferObject.SUCCESS){
		    	imFollowVoList = SOAResParseUtil.getListValueFromDataByKey(allFollowListJson, "imFollowVoList", MsgAdvisoryFollowVo.class);
		    	JSONObject result = new JSONObject();
				Long totalpages = 0L;
				if(!Check.NuNObj(size)){
					if(size % paramRequest.getLimit() == 0){
						totalpages = size/paramRequest.getLimit();
					}else {
						totalpages = size/paramRequest.getLimit() + 1;
					}
				}
				result.put("totalpages", totalpages);
				result.put("currPage", paramRequest.getPage());
				result.put("totalCount", 0);
				result.put("dataList", imFollowVoList);
				return  result.toString();
		   }
		} catch (Exception e) {
			LogUtil.error(LOGGER, "房源信息查询失败，param={}，e={}", JsonEntityTransform.Object2Json(paramRequest),e);
		}
		return null;
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
	@RequestMapping("/queryAllNeedFollowListExcel")
	public void queryAllNeedFollowListExcel(MsgAdvisoryFollowRequest paramRequest, HttpServletResponse response){
		  try {
			  DealExcelUtil dealExcelUtil = new DealExcelUtil(msgAdvisoryFollowupService,paramRequest, null, "ImFollowReport");
			  //dealExcelUtil.exportZipFile(response,"exportAllNeedFollowByPage");//指定调用的方法名
			  dealExcelUtil.exportExcelOrZip(response, "exportAllNeedFollowByPage", "ImFollowReport");
		} catch (Exception e) {
			LogUtil.error(LOGGER, "房源信息查询失败，param={}，e={}", JsonEntityTransform.Object2Json(paramRequest),e);
		}
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
	     * 
	     * 订单创建时间如果为空，默认是当前2个月前
	     *
	     * @author ls
	     * @created 2017年12月18日 下午7:27:30
	     *
	     * @param orderFollowQueryRequest
	     */
	    public  void fillCreateTime(MsgAdvisoryFollowRequest request){
	    	if(Check.NuNStr(request.getMsgSendEndTime())){
	    		Calendar calendar  = Calendar.getInstance();
	    		calendar.setTime(new Date());
	    		calendar.add(Calendar.MONTH, -1);
	    		String dateFormat = DateUtil.timestampFormat(calendar.getTime());
	    		request.setMsgSendEndTime(dateFormat);
	    	}
	    }
	
}
