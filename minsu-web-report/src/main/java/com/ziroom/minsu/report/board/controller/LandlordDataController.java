package com.ziroom.minsu.report.board.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.ziroom.minsu.report.basedata.service.ConfCityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.ziroom.minsu.report.board.dto.LandlordRequest;
import com.ziroom.minsu.report.board.service.LandlordDataService;
import com.ziroom.minsu.report.board.service.RegionDataService;
import com.ziroom.minsu.report.board.vo.LandlordDataItem;
import com.ziroom.minsu.report.board.vo.RegionItem;
import com.ziroom.minsu.report.common.util.DealExcelUtil;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;

/**
 * 
 * <p>房东数据cotroller</p>
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
@RequestMapping("/landlordData")
public class LandlordDataController {

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LandlordDataController.class);
    
    @Resource(name="report.confCityService")
    private ConfCityService confCityService;

    @Resource(name="report.regionDataService")
    private RegionDataService regionDataService;
    
    @Resource(name="report.landlordDataService")
    private LandlordDataService landlordDataService;

    /**
     * 
     * 经营数据-跳转房东汇总数据页面
     *
     * @author liujun
     * @created 2017年2月14日
     *
     * @param model
     * @return
     */
    @RequestMapping("/showList")
    public String toTargetList(Model model){
		List<ConfCityEntity> list = confCityService.getNations();
		model.addAttribute("nationList", list);
		return "board/landlordDataList";
    }
    
    /**
     * 
     * 经营数据-显示房东汇总数据列表
     *
     * @author liujun
     * @created 2017年2月15日
     *
     * @param regionRequest
     * @return
     */
    @RequestMapping("/dataList")
    @ResponseBody
    public JSONObject dataList(LandlordRequest landlordRequest){
    	JSONObject result = new JSONObject();
    	try {
    		List<LandlordDataItem> dataList = new ArrayList<LandlordDataItem>();
    		if (Check.NuNStr(landlordRequest.getNationCode())) {
    			List<ConfCityEntity> list = confCityService.getNations();
				for (ConfCityEntity confCityEntity : list) {
					landlordRequest.setNationCode(confCityEntity.getCode());
					List<LandlordDataItem> nationList = this.getDataList(landlordRequest);
					dataList.addAll(nationList);
				}
			} else {
				dataList = this.getDataList(landlordRequest);
			}
	        result.put("totalpages", 1);
	        result.put("currPage", 1);
	        result.put("totalCount", 0);
	        result.put("dataList", dataList);
	        return result;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "dataList error:{} param:{}", e, JsonEntityTransform.Object2Json(landlordRequest));
		}
    	return result;
    }
    
    /**
     * 
     * 经营数据-下载房东汇总数据列表
     *
     * @author liujun
     * @created 2017年2月15日
     *
     * @param cityTargetRequest
     * @return
     */
    @RequestMapping("/dataListToExcel")
    @ResponseBody
    public void dataListToExcel(String queryDate, HttpServletResponse response){
    	try {
    		Map<String, List<? extends BaseEntity>> dataMap = new LinkedHashMap<String, List<? extends BaseEntity>>();
    		LandlordRequest landlordRequest = new LandlordRequest();
    		if (Check.NuNStrStrict(queryDate)) {
    			queryDate = DateUtil.dateFormat(new Date());
			}
    		landlordRequest.setQueryDate(queryDate);
    		List<LandlordDataItem> dataList = new ArrayList<LandlordDataItem>();
    		List<ConfCityEntity> list = confCityService.getNations();
    		for (ConfCityEntity confCityEntity : list) {
    			landlordRequest.setNationCode(confCityEntity.getCode());
    			List<LandlordDataItem> nationList = this.getDataList(landlordRequest);
    			dataList.addAll(nationList);
    		}
    		dataMap.put(queryDate, dataList);
    		DealExcelUtil excelUtil = new DealExcelUtil(null, null, null, "landlord-data-" + queryDate);
    		excelUtil.exportMultiSheetExcelFile(response, dataMap);
    	} catch (Exception e) {
    		LogUtil.error(LOGGER, "dataListToExcel error:{} queryDate:{}", e, queryDate);
    	}
    }

	/**
	 * 经营数据-获取当前数据
	 *
	 * @author liujun
	 * @created 2017年1月18日
	 *
	 * @param landlordRequest
	 * @throws ParseException
	 * @return
	 */
	private List<LandlordDataItem> getDataList(LandlordRequest landlordRequest) throws ParseException {
		List<RegionItem> list = regionDataService.findRegionListByNationCode(landlordRequest.getNationCode());
		List<LandlordDataItem> totalList = new ArrayList<>();
		List<LandlordDataItem> regionItemList = new ArrayList<>();
		for (RegionItem item : list) {
			landlordRequest.setRegionFid(item.getRegionFid());
			List<LandlordDataItem> cityItemList = landlordDataService.findLandlordDataItemList(landlordRequest);
			if (!Check.NuNCollection(cityItemList)) {
				LandlordDataItem regionItem = this.calcTotalNum(cityItemList, item.getRegionName(), true);
				cityItemList.add(0, regionItem);
				regionItemList.add(regionItem);
				totalList.addAll(cityItemList);
			}
		}
		
		if (!Check.NuNCollection(regionItemList)) {
			LandlordDataItem nationItem = this.calcTotalNum(regionItemList, list.get(0).getNationName(), false);
			totalList.add(0, nationItem);
		}
		return totalList;
	}

	/**
	 * 
	 * 计算大区数据
	 *
	 * @author liujun
	 * @created 2017年2月15日
	 *
	 * @param itemList
	 * @param areaName
	 * @param flag
	 * @return
	 */
	private LandlordDataItem calcTotalNum(List<LandlordDataItem> itemList, String areaName, boolean flag) {
		LandlordDataItem totalItem = new LandlordDataItem();
    	totalItem.setRegionName(areaName);
    	for (LandlordDataItem item : itemList) {
    		if (flag) {
    			item.setRegionName(areaName);
    			item.setTotalLandNum(add(item.getExpLandNum(), item.getNonProLandNum(), item.getProLandNum()));
    			this.calcPct(item);
    		}
    		totalItem.setTotalHouseNum(add(totalItem.getTotalHouseNum(), item.getTotalHouseNum()));
    		totalItem.setTotalLandNum(add(totalItem.getTotalLandNum(), item.getTotalLandNum()));
    		totalItem.setExpLandNum(add(totalItem.getExpLandNum(), item.getExpLandNum()));
    		totalItem.setNonProLandNum(add(totalItem.getNonProLandNum(), item.getNonProLandNum()));
    		totalItem.setProLandNum(add(totalItem.getProLandNum(), item.getProLandNum()));
    		totalItem.setImmediateBookTypeNum(add(totalItem.getImmediateBookTypeNum(), item.getImmediateBookTypeNum()));
    	}
    	
    	this.calcPct(totalItem);
    	return totalItem;
    }

	/**
	 * 计算百分比
	 *
	 * @author liujun
	 * @created 2017年2月15日
	 *
	 * @param item
	 */
	private void calcPct(LandlordDataItem item) {
		item.setPerHouseNum(div(item.getTotalHouseNum(), item.getTotalLandNum()));
		item.setExpRate(div(item.getExpLandNum(), item.getTotalLandNum()));
		item.setNonProRate(div(item.getNonProLandNum(), item.getTotalLandNum()));
		item.setProRate(div(item.getProLandNum(), item.getTotalLandNum()));
	}
	
	/**
	 * 加法运算
	 *
	 * @author liujun
	 * @created 2017年2月15日
	 *
	 * @param totalHouseNum
	 * @param totalHouseNum2
	 * @return
	 */
	private static Integer add(Integer... integerArray) {
		double sum = 0d;
		for (Integer n : integerArray) {
			n = Check.NuNObj(n) ? 0 : n;
			sum = BigDecimalUtil.add(sum, n.doubleValue());
		}
		return (int) sum;
	}
	
	public static void main(String[] args) {
		System.err.println(add(1,2));
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
    
}
