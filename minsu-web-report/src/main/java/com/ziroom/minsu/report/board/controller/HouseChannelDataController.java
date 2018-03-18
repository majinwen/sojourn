/**
 * @FileName: RegionHouseChannelDataController.java
 * @Package com.ziroom.minsu.report.board.controller
 *
 * @author bushujie
 * @created 2017年1月18日 下午4:13:31
 *
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.board.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ziroom.minsu.report.basedata.service.ConfCityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.ziroom.minsu.report.board.service.BaseDataService;
import com.ziroom.minsu.report.board.vo.RegionHouseChannelData;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;

/**
 * <p>房源渠道数据统计</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("houseChannel")
public class HouseChannelDataController {

//	@Resource(name="basedata.confCityService")
//	private ConfCityService confCityService;

	@Resource(name="report.BaseDataService")
	private BaseDataService baseDataService;

	@Resource(name="report.confCityService")
	private ConfCityService confReportCityService;


	/**
	 *
	 * 大区房源渠道数据报表页
	 *
	 * @author bushujie
	 * @created 2017年1月18日 下午4:24:09
	 *
	 * @return
	 */
	@RequestMapping("showList")
	public String showList(HttpServletRequest request){
		List<ConfCityEntity> list = confReportCityService.getNations();
		request.setAttribute("nationList", list);
		return "/board/houseChannelTarget";
	}

	/**
	 *
	 * 大区房源渠道数据报表数据
	 *
	 * @author bushujie
	 * @created 2017年1月18日 下午4:34:56
	 *
	 * @param targetMonth
	 * @return
	 * @throws SOAParseException
	 * @throws ParseException
	 */
	@RequestMapping("showData")
	@ResponseBody
	public JSONObject showData(String targetMonth,String nationCode ) throws SOAParseException, ParseException{
		if(Check.NuNStr(targetMonth)){
			targetMonth=DateUtil.dateFormat(new Date(), "yyyy-MM");
		}
		if(Check.NuNStr(nationCode)){
			nationCode="100000";
		}
		List<RegionHouseChannelData> dataList=baseDataService.dataList(targetMonth,nationCode);
		JSONObject result = new JSONObject();
		result.put("dataList", dataList);
		return result;
	}
}
