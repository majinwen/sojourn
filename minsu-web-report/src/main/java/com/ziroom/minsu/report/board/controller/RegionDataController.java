package com.ziroom.minsu.report.board.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.ziroom.minsu.report.basedata.service.ConfCityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.board.dto.RegionRequest;
import com.ziroom.minsu.report.board.service.CityDailyMsgService;
import com.ziroom.minsu.report.board.service.RegionDataService;
import com.ziroom.minsu.report.board.vo.RegionDataItem;
import com.ziroom.minsu.report.board.vo.RegionItem;
import com.ziroom.minsu.report.common.util.DealExcelUtil;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;

/**
 * 
 * <p>大区数据cotroller</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("/regionData")
public class RegionDataController {

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RegionDataController.class);
    
    @Resource(name="report.confCityService")
    private ConfCityService confCityService;

    @Resource(name="report.regionDataService")
    private RegionDataService regionDataService;
    
    @Resource(name="report.cityDailyMsgService")
    private CityDailyMsgService cityDailyMsgService;

    /**
     * 
     * 目标看板-跳转大区数据列表页面
     *
     * @author liujun
     * @created 2017年1月12日
     *
     * @param model
     * @return
     */
    @RequestMapping("/showList")
    public String toTargetList(Model model){
		List<ConfCityEntity> list = confCityService.getNations();
		model.addAttribute("nationList", list);
		return "board/regionDataList";
    }
    
    /**
     * 
     * 目标看板-显示大区数据列表
     *
     * @author liujun
     * @created 2017年1月13日
     *
     * @param cityTargetRequest
     * @return
     */
    @RequestMapping("/dataList")
    @ResponseBody
    public JSONObject dataList(RegionRequest regionRequest){
    	JSONObject result = new JSONObject();
    	try {
    		Map<String, Map<String, RegionDataItem>> holder = new HashMap<String, Map<String,RegionDataItem>>();
    		List<RegionDataItem> dataList = new ArrayList<RegionDataItem>();
    		if (Check.NuNStr(regionRequest.getNationCode())) {
    			List<ConfCityEntity> list = confCityService.getNations();
				for (ConfCityEntity confCityEntity : list) {
					regionRequest.setNationCode(confCityEntity.getCode());
					this.getLastMonthDataList(regionRequest, holder);
					List<RegionDataItem> nationList = this.getDataList(regionRequest, holder);
					dataList.addAll(nationList);
				}
			} else {
				this.getLastMonthDataList(regionRequest, holder);
				dataList = this.getDataList(regionRequest, holder);
			}
	        result.put("totalpages", 1);
	        result.put("currPage", 1);
	        result.put("totalCount", 0);
	        result.put("dataList", dataList);
	        return result;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "dataList error:{} param:{}", e, JsonEntityTransform.Object2Json(regionRequest));
		}
    	return result;
    }
    
    /**
     * 
     * 目标看板-显示大区数据列表
     *
     * @author liujun
     * @created 2017年1月13日
     *
     * @param cityTargetRequest
     * @return
     */
    @RequestMapping("/dataListToExcel")
    @ResponseBody
    public void dataListToExcel(String targetMonthStart, String targetMonthEnd, HttpServletResponse response){
    	try {
    		Map<String, Map<String, RegionDataItem>> holder = new HashMap<String, Map<String,RegionDataItem>>();
    		Map<String, List<? extends BaseEntity>> dataMap = new LinkedHashMap<String, List<? extends BaseEntity>>();
    		List<String> targetMonthList = getMonthBetween(targetMonthStart, targetMonthEnd);
    		int temp = 0;
    		for (String targetMonth : targetMonthList) {
    			RegionRequest regionRequest = new RegionRequest();
    			regionRequest.setTargetMonth(targetMonth);
    			List<RegionDataItem> dataList = new ArrayList<RegionDataItem>();
    			List<ConfCityEntity> list = confCityService.getNations();
    			for (ConfCityEntity confCityEntity : list) {
    				regionRequest.setNationCode(confCityEntity.getCode());
    				if (temp == 0) {
    					this.getLastMonthDataList(regionRequest, holder);
					}
    				List<RegionDataItem> nationList = this.getDataList(regionRequest, holder);
    				dataList.addAll(nationList);
    			}
    			dataMap.put(targetMonth, dataList);
    			temp ++;
			}
    		DealExcelUtil excelUtil = new DealExcelUtil(null, null, null, "region-data");
    		excelUtil.exportMultiSheetExcelFile(response, dataMap);
    	} catch (Exception e) {
    		LogUtil.error(LOGGER, "dataListToExcel error:{} targetMonthStart:{} targetMonthEnd:{}", e, targetMonthStart, targetMonthEnd);
    	}
    }
    
    /**
     * 目标看板-获取上月数据
     *
     * @author liujun
     * @created 2017年1月19日
     *
     * @param lastRequest
     * @param holder
     * @throws ParseException 
     */
    private List<RegionDataItem> getLastMonthDataList(RegionRequest regionRequest, Map<String, Map<String, RegionDataItem>> holder) 
    		throws ParseException {
    	RegionRequest lastRequest = new RegionRequest();
    	BeanUtils.copyProperties(regionRequest, lastRequest);
    	lastRequest.setTargetMonth(getLastMothStr(lastRequest.getTargetMonth()));
    	return getDataList(lastRequest, holder);
    }

	/**
	 * 目标看板-获取当月数据
	 *
	 * @author liujun
	 * @created 2017年1月18日
	 *
	 * @param regionRequest
	 * @throws ParseException 
	 * @param holder 
	 * @return
	 */
	private List<RegionDataItem> getDataList(RegionRequest regionRequest, Map<String, Map<String, RegionDataItem>> holder)
			throws ParseException {
		Map<String, RegionDataItem> lastMonthMap = holder.get(getLastMothStr(regionRequest.getTargetMonth()));
		Map<String, RegionDataItem> currMonthMap = holder.get(regionRequest.getTargetMonth());
		if (Check.NuNMap(currMonthMap)) {
			currMonthMap = new HashMap<String, RegionDataItem>();
			holder.put(regionRequest.getTargetMonth(), currMonthMap);		
		}
		List<RegionItem> list = regionDataService.findRegionListByNationCode(regionRequest.getNationCode());
		List<RegionDataItem> totalList = new ArrayList<>();
		List<RegionDataItem> regionItemList = new ArrayList<>();
		for (RegionItem item : list) {
			regionRequest.setRegionFid(item.getRegionFid());
			List<RegionDataItem> cityItemList = regionDataService.findRegionDataItemListFromTask(regionRequest);
			if (!Check.NuNCollection(cityItemList)) {
				RegionDataItem regionItem = this.calcTotalNum(cityItemList, item.getRegionName(), 
						item.getRegionFid(), lastMonthMap, currMonthMap, true);
				cityItemList.add(0, regionItem);
				regionItemList.add(regionItem);
				totalList.addAll(cityItemList);
			}
		}
		
		if (!Check.NuNCollection(regionItemList)) {
			RegionDataItem nationItem = this.calcTotalNum(regionItemList, list.get(0).getNationName(), 
					list.get(0).getNationCode(), lastMonthMap, currMonthMap, false);
			totalList.add(0, nationItem);
		}
		return totalList;
	}

	/**
	 * 获取上月字符串(eg:2016-12 yyyy-MM)
	 *
	 * @author liujun
	 * @created 2017年1月18日
	 *
	 * @param monthStr
	 * @return
	 * @throws ParseException 
	 */
	private static String getLastMothStr(String monthStr) throws ParseException {
		String lastMonthStr = null;
		if (!Check.NuNStr(monthStr)) {
			Date month = DateUtil.parseDate(monthStr, RegionRequest.MONTH_FORMAT_PATTERN);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(month);
			calendar.add(Calendar.MONTH, -1);
			lastMonthStr = DateUtil.dateFormat(calendar.getTime(), RegionRequest.MONTH_FORMAT_PATTERN);
		}
		return lastMonthStr;
	}

	/**
	 * 
	 * 计算大区数据
	 *
	 * @author liujun
	 * @created 2017年1月18日
	 *
	 * @param itemList
	 * @param areaName
	 * @param uniqueCode 
	 * @param currMonthMap 
	 * @param lastMonthMap 
	 * @param flag 
	 * @return
	 */
	private RegionDataItem calcTotalNum(List<RegionDataItem> itemList, String areaName, String uniqueCode,
			Map<String, RegionDataItem> lastMonthMap, Map<String, RegionDataItem> currMonthMap, boolean flag) {
    	RegionDataItem totalItem = new RegionDataItem();
    	totalItem.setRegionName(areaName);
    	for (RegionDataItem item : itemList) {
    		totalItem.setTotalHouseNum(add(totalItem.getTotalHouseNum(), item.getTotalHouseNum()));
    		totalItem.setTargetHouseNum(add(totalItem.getTargetHouseNum(), item.getTargetHouseNum()));
    		totalItem.setIssueHouseNum(add(totalItem.getIssueHouseNum(), item.getIssueHouseNum()));
    		totalItem.setOnlineHouseNum(add(totalItem.getOnlineHouseNum(), item.getOnlineHouseNum()));
    		totalItem.setTargetOrderNum(add(totalItem.getTargetOrderNum(), item.getTargetOrderNum()));
    		totalItem.setActualOrderNum(add(totalItem.getActualOrderNum(), item.getActualOrderNum()));
    		totalItem.setTargetRentNum(add(totalItem.getTargetRentNum(), item.getTargetRentNum()));
    		totalItem.setActualRentNum(add(totalItem.getActualRentNum(), item.getActualRentNum()));
    		if (flag) {
    			item.setRegionName(areaName);
				RegionDataItem lastItem = null;
				if (!Check.NuNMap(lastMonthMap)) {
					lastItem = lastMonthMap.get(item.getCityCode());
				}
    			this.calcPct(item, lastItem);
    			currMonthMap.put(item.getCityCode(), item);
			}
    	}
    	
    	RegionDataItem lastItem = null;
    	if (!Check.NuNMap(lastMonthMap)) {
    		lastItem = lastMonthMap.get(uniqueCode);
		}
    	this.calcPct(totalItem, lastItem);
    	currMonthMap.put(uniqueCode, totalItem);
    	return totalItem;
    }

	/**
	 * 计算百分比
	 *
	 * @author liujun
	 * @created 2017年1月17日
	 *
	 * @param curritem
	 * @param lastItem 
	 */
	private void calcPct(RegionDataItem curritem, RegionDataItem lastItem) {
		curritem.setHouseRate(div(curritem.getOnlineHouseNum(), curritem.getTargetHouseNum()));
		curritem.setOrderRate(div(curritem.getActualOrderNum(), curritem.getTargetOrderNum()));
		curritem.setNightRate(div(curritem.getActualRentNum(), curritem.getTargetRentNum()));
		curritem.setRentRate(div(curritem.getActualRentNum(), curritem.getRentableNum()));
		if (Check.NuNObj(lastItem)) {
			lastItem = new RegionDataItem();
		}
		curritem.setHouseQoQ(div(sub(curritem.getHouseRate(), lastItem.getHouseRate()), lastItem.getHouseRate()));
		curritem.setOrderQoQ(div(sub(curritem.getOrderRate(), lastItem.getOrderRate()), lastItem.getOrderRate()));
		curritem.setRentQoQ(div(sub(curritem.getRentRate(), lastItem.getRentRate()), lastItem.getRentRate()));
	}
	
	/**
	 * 加法运算
	 *
	 * @author liujun
	 * @created 2017年1月17日
	 *
	 * @param totalHouseNum
	 * @param totalHouseNum2
	 * @return
	 */
	private static Integer add(Integer n1, Integer n2) {
		n1 = Check.NuNObj(n1) ? 0 : n1;
		n2 = Check.NuNObj(n2) ? 0 : n2;
		return (int) BigDecimalUtil.add(n1.doubleValue(), n2.doubleValue());
	}
	
	/**
	 * 除法运算
	 *
	 * @author liujun
	 * @created 2017年1月17日
	 *
	 * @param onlineHouseNum
	 * @param targetHouseNum
	 * @return
	 */
	private static Double div(Number n1, Number n2) {
		if (Check.NuNObj(n2) || n2.doubleValue() == 0d) {
			return 0d;
		}
		n1 = Check.NuNObj(n1) ? 0d : n1;
		return BigDecimalUtil.div(n1.doubleValue(), n2.doubleValue(), 4);
	}

	/**
	 * 减法运算
	 *
	 * @author liujun
	 * @created 2017年1月18日
	 *
	 * @param n1
	 * @param n2
	 * @return
	 */
	private static Double sub(Double n1, Double n2) {
		n1 = Check.NuNObj(n1) ? 0d : n1;
		n2 = Check.NuNObj(n2) ? 0d : n2;
		return BigDecimalUtil.sub(n1.doubleValue(), n2.doubleValue());
	}
	
	/**
	 * 获取两个月份之间的所有月份(包括给定的两个月份)
	 * @param minDate eg:2017-01
	 * @param maxDate eg:2017-05
	 * @return
	 * @throws ParseException
	 */
	private static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
		List<String> result = new ArrayList<String>();
		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();
		min.setTime(DateUtil.parseDate(minDate, RegionRequest.MONTH_FORMAT_PATTERN));
		max.setTime(DateUtil.parseDate(maxDate, RegionRequest.MONTH_FORMAT_PATTERN));
		if (min.after(max)) {
			Calendar temp = min;
			min = max;
			max = temp;
		}
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
		Calendar curr = min;
		while (curr.before(max)) {
			result.add(DateUtil.dateFormat(curr.getTime(), RegionRequest.MONTH_FORMAT_PATTERN));
			curr.add(Calendar.MONTH, 1);
		}
		return result;
	}
	
	/**
     * 
     * 目标看板-启动统计城市日可出租天数定时任务
     *
     * @author liujun
     * @created 2017年1月13日
     *
     * @return
     */
    @RequestMapping("/runTask")
    @ResponseBody
    public void runTask(){
    	try {
    		 List<ConfCityEntity> cityList = confCityService.getOpenCity(null);
    		 cityDailyMsgService.startTask(cityList);
    	} catch (Exception e) {
    		LogUtil.error(LOGGER, "runTask error:{}", e);
    	}
    }
    
}
