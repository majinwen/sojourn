/**
 * @FileName: BaseDataService.java
 * @Package com.ziroom.minsu.report.board.service
 * 
 * @author bushujie
 * @created 2017年1月12日 下午5:21:41
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.board.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.ziroom.minsu.entity.conf.CityRegionEntity;
import com.ziroom.minsu.entity.conf.ConfCityEntity;
import com.ziroom.minsu.report.basedata.dao.ConfCityDao;
import com.ziroom.minsu.report.board.dao.BaseDataDao;
import com.ziroom.minsu.report.board.dto.EmpTargetItemRequest;
import com.ziroom.minsu.report.board.vo.EmpTargetItem;
import com.ziroom.minsu.report.board.vo.EmpTargetItemData;
import com.ziroom.minsu.report.board.vo.RegionHouseChannelData;
import com.ziroom.minsu.services.common.utils.DecimalCalculate;

/**
 * <p>TODO</p>
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
@Service("report.BaseDataService")
public class BaseDataService {
	
	@Resource(name="report.baseDataDao")
	private BaseDataDao baseDataDao;
	
	@Resource(name="report.confCityDao")
	private ConfCityDao confCityDao;
	
	/**
	 * 
	 * 分页查询员工目标列表
	 *
	 * @author bushujie
	 * @created 2017年1月12日 下午5:40:03
	 *
	 * @param empTargetItemRequest
	 * @return
	 */
	public PagingResult<EmpTargetItem> findGaurdAreaByPage(EmpTargetItemRequest empTargetItemRequest){
		return baseDataDao.findGaurdAreaByPage(empTargetItemRequest);
	}
	
	
	/**
	 * 
	 * 月份查询房源渠道数据报表list
	 *
	 * @author bushujie
	 * @created 2017年1月19日 下午12:01:50
	 *
	 * @param targetMonth
	 * @return
	 * @throws ParseException 
	 */
	@SuppressWarnings("rawtypes")
	public List<RegionHouseChannelData> dataList(String targetMonth,String nationCode) throws ParseException{
		//查询国家名称
		com.ziroom.minsu.report.basedata.entity.ConfCityEntity confCityEntity=confCityDao.getNationByCode(nationCode);
		//上个月
		String lastTargetMonth=DateUtil.dateFormat(DateUtils.addMonths(DateUtils.parseDate(targetMonth, "yyyy-MM"), -1),"yyyy-MM");
		List<RegionHouseChannelData> dataList=new ArrayList<RegionHouseChannelData>();
		RegionHouseChannelData regionHouseChannelData1=new RegionHouseChannelData();
		regionHouseChannelData1.setRegionName(confCityEntity.getShowName());
		List<CityRegionEntity> regionList=baseDataDao.findRegionByCountryCode(nationCode);
		List<RegionHouseChannelData> dataList1=new ArrayList<RegionHouseChannelData>();
		Integer countryPushNum=0;
		Integer countryAutoNum=0;
		Integer countryPushUpNum=0;
		Integer countryAutoUpNum=0;
		Integer countryAllUpNum=0;
		//上个月上架数量
		Integer lastCountryPushUpNum=0;
		Integer lastCountryAutoUpNum=0;
		//国家目标
		Integer countryTargetPushNum=0;
		Integer countryTargetSelfNum=0;
		//上个月国家目标
		Integer lastCountTargetPushNum=0;
		Integer lastCountTargetSelfNum=0;
		for(CityRegionEntity region:regionList){
			RegionHouseChannelData regionHouseChannelData2=new RegionHouseChannelData();
			regionHouseChannelData2.setRegionName(region.getRegionName());
			//大区目标
			Integer regionTargetPushNum=0;
			Integer regionTargetSelfNum=0;
			//上个月大区目标
			Integer lastRegionTargetPushNum=0;
			Integer lastRegionTargetSelfNum=0;
			
			List<ConfCityEntity> cityList=baseDataDao.findConfCityEntity(region.getFid());
			Integer regionPushNum=0;
			Integer regionAutoNum=0;
			Integer regionPushUpNum=0;
			Integer regionAutoUpNum=0;
			//所有上架房源数量
			Integer regionAllUpNum=0;
			//上个月上架数量
			Integer lastRegionPushUpNum=0;
			Integer lastRegionAutoUpNum=0;
			List<RegionHouseChannelData> dataList2=new ArrayList<RegionHouseChannelData>();
			for(ConfCityEntity city:cityList){
				Map<String, Object> paramMap=new HashMap<String, Object>();
				paramMap.put("targetMonth", targetMonth);
				paramMap.put("countryCode", nationCode);
				paramMap.put("regionFid", region.getFid());
				paramMap.put("cityCode", city.getCode());
				Map targetMap=baseDataDao.findTargetByCondition(paramMap);
				//计算上个月达成率
				paramMap.put("targetMonth", lastTargetMonth);
				Map lastCityTargetMap=baseDataDao.findTargetByCondition(paramMap);
				RegionHouseChannelData regionHouseChannelData3=new RegionHouseChannelData();
				regionHouseChannelData3.setRegionName(region.getRegionName());
				regionHouseChannelData3.setCityName(city.getShowName());
				if(targetMap==null){
					regionHouseChannelData3.setToPushTarget(0);
					regionHouseChannelData3.setAutoTarget(0);
				}else{
					if(Check.NuNObj(targetMap.get("targetPush"))){
						regionHouseChannelData3.setToPushTarget(0);
					} else {
						regionHouseChannelData3.setToPushTarget(Integer.valueOf(targetMap.get("targetPush").toString()));
					}
					if(Check.NuNObj(targetMap.get("targetSelf"))){
						regionHouseChannelData3.setAutoTarget(0);
					} else {
						regionHouseChannelData3.setAutoTarget(Integer.valueOf(targetMap.get("targetSelf").toString()));
					}
				}
				//地推发布量
				Map<String, Object> pMap=new HashMap<>();
				pMap.put("targetMonth", targetMonth);
				pMap.put("channel", 3);
				pMap.put("cityCode", city.getCode());
				pMap.put("toStatus", 11);
				regionHouseChannelData3.setToPushNum(baseDataDao.getIssueHouseNum(pMap));
				//自主发布量
				pMap.put("channel", 2);
				regionHouseChannelData3.setAutoNum(baseDataDao.getIssueHouseNum(pMap));
				//自主上架量
				pMap.put("toStatus", 40);
				regionHouseChannelData3.setAutoUpNum(baseDataDao.getIssueHouseNum(pMap));
				//地推上架量
				pMap.put("channel", 3);
				regionHouseChannelData3.setToPushUpNum(baseDataDao.getIssueHouseNum(pMap));
				//地推达成率
				if(regionHouseChannelData3.getToPushTarget()==0){
					regionHouseChannelData3.setToPushRate(0d);
				}else{
					regionHouseChannelData3.setToPushRate(DecimalCalculate.div(regionHouseChannelData3.getToPushUpNum().toString(), regionHouseChannelData3.getToPushTarget().toString(), 4).doubleValue());
				}
				regionHouseChannelData3.setToPushRateS(DecimalCalculate.mul(regionHouseChannelData3.getToPushRate().toString(), "100").setScale(2, BigDecimal.ROUND_HALF_UP)+"%");
				//自主达成率
				if(regionHouseChannelData3.getAutoTarget()==0){
					regionHouseChannelData3.setAutoRate(0d);
				}else{
					regionHouseChannelData3.setAutoRate(DecimalCalculate.div(regionHouseChannelData3.getAutoUpNum().toString(), regionHouseChannelData3.getAutoTarget().toString(), 4).doubleValue());
				}
				regionHouseChannelData3.setAutoRateS(DecimalCalculate.mul(regionHouseChannelData3.getAutoRate().toString(), "100").setScale(2, BigDecimal.ROUND_HALF_UP)+"%");
				//地推自主占比
				pMap.remove("channel");
				Integer cityAllUpNum=baseDataDao.getIssueHouseNum(pMap);
				if(cityAllUpNum==0){
					regionHouseChannelData3.setToPushDuty(0d);
					regionHouseChannelData3.setAutoDuty(0d);
				} else {
					regionHouseChannelData3.setToPushDuty(DecimalCalculate.div(regionHouseChannelData3.getToPushUpNum().toString(), cityAllUpNum.toString(), 4).doubleValue());
					regionHouseChannelData3.setAutoDuty(DecimalCalculate.div(regionHouseChannelData3.getAutoUpNum().toString(), cityAllUpNum.toString(), 4).doubleValue());
				}
				regionHouseChannelData3.setToPushDutyS(DecimalCalculate.mul(regionHouseChannelData3.getToPushDuty().toString(), "100").setScale(2, BigDecimal.ROUND_HALF_UP)+"%");
				regionHouseChannelData3.setAutoDutyS(DecimalCalculate.mul(regionHouseChannelData3.getAutoDuty().toString(), "100").setScale(2, BigDecimal.ROUND_HALF_UP)+"%");
				//计算上个月达成率开始（城市）
				if(lastCityTargetMap==null){
					regionHouseChannelData3.setAutoQoQ(0d);
					regionHouseChannelData3.setToPushQoQ(0d);
				} else {
					pMap.put("targetMonth", lastTargetMonth);
					pMap.put("channel", 3);
					Integer lastCityPushUpNum=baseDataDao.getIssueHouseNum(pMap);
					pMap.put("channel", 2);
					Integer lastCityAutoUpNum=baseDataDao.getIssueHouseNum(pMap);
					Double lastAutoRate = 0.0;
					Double lastToPushRate = 0.0;
					if (!"0".equals(lastCityTargetMap.get("targetSelf").toString())){
						lastAutoRate=DecimalCalculate.div(lastCityAutoUpNum.toString(), lastCityTargetMap.get("targetSelf").toString(), 4).doubleValue();
					}
					if (!"0".equals(lastCityTargetMap.get("targetPush").toString())){
						lastToPushRate=DecimalCalculate.div(lastCityPushUpNum.toString(),lastCityTargetMap.get("targetPush").toString() , 4).doubleValue();
					}
					if(lastAutoRate > -0.000001 && lastAutoRate < +0.000001){
						regionHouseChannelData3.setAutoQoQ(0d);
					}else{
						regionHouseChannelData3.setAutoQoQ(DecimalCalculate.div(DecimalCalculate.sub(regionHouseChannelData3.getAutoRate().toString(), lastAutoRate.toString()).toString(),lastAutoRate.toString(),4).doubleValue());
					}
					if(lastToPushRate > -0.000001 && lastToPushRate < +0.000001){
						regionHouseChannelData3.setToPushQoQ(0d);
					}else{
						regionHouseChannelData3.setToPushQoQ(DecimalCalculate.div(DecimalCalculate.sub(regionHouseChannelData3.getToPushRate().toString(), lastToPushRate.toString()).toString(),lastToPushRate.toString(),4).doubleValue());
					}
					lastRegionAutoUpNum+=lastCityAutoUpNum;
					lastRegionPushUpNum+=lastCityPushUpNum;
					//计算上个月大区目标
					lastRegionTargetPushNum+=Integer.valueOf(lastCityTargetMap.get("targetPush").toString());
					lastRegionTargetSelfNum+=Integer.valueOf(lastCityTargetMap.get("targetSelf").toString());
				}
				regionHouseChannelData3.setAutoQoQS(DecimalCalculate.mul(regionHouseChannelData3.getAutoQoQ().toString(), "100").setScale(2, BigDecimal.ROUND_HALF_UP)+"%");
				regionHouseChannelData3.setToPushQoQS(DecimalCalculate.mul(regionHouseChannelData3.getToPushQoQ().toString(), "100").setScale(2, BigDecimal.ROUND_HALF_UP)+"%");
				//计算上个月达成率结束
				regionPushNum+=regionHouseChannelData3.getToPushNum();
				regionAutoNum+=regionHouseChannelData3.getAutoNum();
				regionAutoUpNum+=regionHouseChannelData3.getAutoUpNum();
				regionPushUpNum+=regionHouseChannelData3.getToPushUpNum();
				regionAllUpNum+=cityAllUpNum;
				//计算大区目标
				regionTargetPushNum+=regionHouseChannelData3.getToPushTarget();
				regionTargetSelfNum+=regionHouseChannelData3.getAutoTarget();
				dataList2.add(regionHouseChannelData3);
			}
			regionHouseChannelData2.setToPushNum(regionPushNum);
			regionHouseChannelData2.setAutoNum(regionAutoNum);
			regionHouseChannelData2.setToPushUpNum(regionPushUpNum);
			regionHouseChannelData2.setAutoUpNum(regionAutoUpNum);
			regionHouseChannelData2.setToPushTarget(regionTargetPushNum);
			regionHouseChannelData2.setAutoTarget(regionTargetSelfNum);
			//地推达成率
			if(regionHouseChannelData2.getToPushTarget()==0){
				regionHouseChannelData2.setToPushRate(0d);
			}else{
				regionHouseChannelData2.setToPushRate(DecimalCalculate.div(regionHouseChannelData2.getToPushUpNum().toString(), regionHouseChannelData2.getToPushTarget().toString(), 4).doubleValue());
			}
			regionHouseChannelData2.setToPushRateS(DecimalCalculate.mul(regionHouseChannelData2.getToPushRate().toString(), "100").setScale(2, BigDecimal.ROUND_HALF_UP)+"%");
			//自主达成率
			if(regionHouseChannelData2.getAutoTarget()==0){
				regionHouseChannelData2.setAutoRate(0d);
			}else{
				regionHouseChannelData2.setAutoRate(DecimalCalculate.div(regionHouseChannelData2.getAutoUpNum().toString(), regionHouseChannelData2.getAutoTarget().toString(), 4).doubleValue());
			}
			regionHouseChannelData2.setAutoRateS(DecimalCalculate.mul(regionHouseChannelData2.getAutoRate().toString(), "100").setScale(2, BigDecimal.ROUND_HALF_UP)+"%");
			//地推自主占比
			if(regionAllUpNum==0){
				regionHouseChannelData2.setToPushDuty(0d);
				regionHouseChannelData2.setAutoDuty(0d);
			} else {
				regionHouseChannelData2.setToPushDuty(DecimalCalculate.div(regionHouseChannelData2.getToPushUpNum().toString(), regionAllUpNum.toString(), 4).doubleValue());
				regionHouseChannelData2.setAutoDuty(DecimalCalculate.div(regionHouseChannelData2.getAutoUpNum().toString(), regionAllUpNum.toString(), 4).doubleValue());
			}
			regionHouseChannelData2.setToPushDutyS(DecimalCalculate.mul(regionHouseChannelData2.getToPushDuty().toString(), "100").setScale(2, BigDecimal.ROUND_HALF_UP)+"%");
			regionHouseChannelData2.setAutoDutyS(DecimalCalculate.mul(regionHouseChannelData2.getAutoDuty().toString(), "100").setScale(2, BigDecimal.ROUND_HALF_UP)+"%");
			//计算上个月达成率开始（大区）
			if(regionHouseChannelData2.getToPushTarget()==0){
				regionHouseChannelData2.setToPushQoQ(0d);
			} else {
				Double lastToPushRate=DecimalCalculate.div(lastRegionPushUpNum.toString(),regionHouseChannelData2.getToPushTarget().toString(), 4).doubleValue();
				if(lastToPushRate > -0.000001 && lastToPushRate < +0.000001){
					regionHouseChannelData2.setToPushQoQ(0d);
				}else{
					regionHouseChannelData2.setToPushQoQ(DecimalCalculate.div(DecimalCalculate.sub(regionHouseChannelData2.getToPushRate().toString(), lastToPushRate.toString()).toString(),lastToPushRate.toString(),4).doubleValue());
				}
			}
			if(regionHouseChannelData2.getAutoTarget()==0){
				regionHouseChannelData2.setAutoQoQ(0d);
			} else {
				Double lastAutoRate=DecimalCalculate.div(lastRegionAutoUpNum.toString(),regionHouseChannelData2.getAutoTarget().toString(), 4).doubleValue();
				if(lastAutoRate > -0.000001 && lastAutoRate < +0.000001){
					regionHouseChannelData2.setAutoQoQ(0d);
				}else{
					regionHouseChannelData2.setAutoQoQ(DecimalCalculate.div(DecimalCalculate.sub(regionHouseChannelData2.getAutoRate().toString(), lastAutoRate.toString()).toString(),lastAutoRate.toString(),4).doubleValue());
				}
			}
			lastCountryAutoUpNum+=lastRegionAutoUpNum;
			lastCountryPushUpNum+=lastRegionPushUpNum;
			regionHouseChannelData2.setAutoQoQS(DecimalCalculate.mul(regionHouseChannelData2.getAutoQoQ().toString(), "100").setScale(2, BigDecimal.ROUND_HALF_UP)+"%");
			regionHouseChannelData2.setToPushQoQS(DecimalCalculate.mul(regionHouseChannelData2.getToPushQoQ().toString(), "100").setScale(2, BigDecimal.ROUND_HALF_UP)+"%");
			//计算上个月达成率结束
			dataList1.add(regionHouseChannelData2);
			dataList1.addAll(dataList2);
			countryPushNum+=regionPushNum;
			countryAutoNum+=regionAutoNum;
			countryPushUpNum+=regionPushUpNum;
			countryAutoUpNum+=regionAutoUpNum;
			countryAllUpNum+=regionAllUpNum;
			//国家目标计算
			countryTargetPushNum+=regionHouseChannelData2.getToPushTarget();
			countryTargetSelfNum+=regionHouseChannelData2.getAutoTarget();
			//国家上个月目标计算
			lastCountTargetPushNum+=lastRegionTargetPushNum;
			lastCountTargetSelfNum+=lastRegionTargetSelfNum;
		}
		regionHouseChannelData1.setToPushNum(countryPushNum);
		regionHouseChannelData1.setAutoNum(countryAutoNum);
		regionHouseChannelData1.setToPushUpNum(countryPushUpNum);
		regionHouseChannelData1.setAutoUpNum(countryAutoUpNum);
		regionHouseChannelData1.setToPushTarget(countryTargetPushNum);
		regionHouseChannelData1.setAutoTarget(countryTargetSelfNum);
		//地推达成率
		if(regionHouseChannelData1.getToPushTarget()==0){
			regionHouseChannelData1.setToPushRate(0d);
		}else{
			regionHouseChannelData1.setToPushRate(DecimalCalculate.div(regionHouseChannelData1.getToPushUpNum().toString(), regionHouseChannelData1.getToPushTarget().toString(), 4).doubleValue());
		}
		regionHouseChannelData1.setToPushRateS(DecimalCalculate.mul(regionHouseChannelData1.getToPushRate().toString(), "100").setScale(2, BigDecimal.ROUND_HALF_UP)+"%");
		//自主达成率
		if(regionHouseChannelData1.getAutoTarget()==0){
			regionHouseChannelData1.setAutoRate(0d);
		}else{
			regionHouseChannelData1.setAutoRate(DecimalCalculate.div(regionHouseChannelData1.getAutoUpNum().toString(), regionHouseChannelData1.getAutoTarget().toString(), 4).doubleValue());
		}
		regionHouseChannelData1.setAutoRateS(DecimalCalculate.mul(regionHouseChannelData1.getAutoRate().toString(), "100").setScale(2, BigDecimal.ROUND_HALF_UP)+"%");
		//地推自主占比
		if(countryAllUpNum==0){
			regionHouseChannelData1.setToPushDuty(0d);
			regionHouseChannelData1.setAutoDuty(0d);
		} else {
			regionHouseChannelData1.setToPushDuty(DecimalCalculate.div(regionHouseChannelData1.getToPushUpNum().toString(), countryAllUpNum.toString(), 4).doubleValue());
			regionHouseChannelData1.setAutoDuty(DecimalCalculate.div(regionHouseChannelData1.getAutoUpNum().toString(), countryAllUpNum.toString(), 4).doubleValue());
		}
		regionHouseChannelData1.setToPushDutyS(DecimalCalculate.mul(regionHouseChannelData1.getToPushDuty().toString(), "100").setScale(2, BigDecimal.ROUND_HALF_UP)+"%");
		regionHouseChannelData1.setAutoDutyS(DecimalCalculate.mul(regionHouseChannelData1.getAutoDuty().toString(), "100").setScale(2, BigDecimal.ROUND_HALF_UP)+"%");
		//计算上月达成率（国家）
		if(regionHouseChannelData1.getToPushTarget()==0){
			regionHouseChannelData1.setToPushQoQ(0d);
		} else {
			Double lastToPushRate=DecimalCalculate.div(lastCountryPushUpNum.toString(),regionHouseChannelData1.getToPushTarget().toString() , 4).doubleValue();
			if(lastToPushRate > -0.000001 && lastToPushRate < +0.000001){
				regionHouseChannelData1.setToPushQoQ(0d);
			}else{
				regionHouseChannelData1.setToPushQoQ(DecimalCalculate.div(DecimalCalculate.sub(regionHouseChannelData1.getToPushRate().toString(), lastToPushRate.toString()).toString(),lastToPushRate.toString(),4).doubleValue());
			}
		}
		if(regionHouseChannelData1.getAutoTarget()==0){
			regionHouseChannelData1.setAutoQoQ(0d);
		} else {
			Double lastAutoRate=DecimalCalculate.div(lastCountryAutoUpNum.toString(), regionHouseChannelData1.getAutoTarget().toString(), 4).doubleValue();
			if(lastAutoRate > -0.000001 && lastAutoRate < +0.000001){
				regionHouseChannelData1.setAutoQoQ(0d);
			}else{
				regionHouseChannelData1.setAutoQoQ(DecimalCalculate.div(DecimalCalculate.sub(regionHouseChannelData1.getAutoRate().toString(), lastAutoRate.toString()).toString(),lastAutoRate.toString(),4).doubleValue());
			}
		}
		regionHouseChannelData1.setAutoQoQS(DecimalCalculate.mul(regionHouseChannelData1.getAutoQoQ().toString(), "100").setScale(2, BigDecimal.ROUND_HALF_UP)+"%");
		regionHouseChannelData1.setToPushQoQS(DecimalCalculate.mul(regionHouseChannelData1.getToPushQoQ().toString(), "100").setScale(2, BigDecimal.ROUND_HALF_UP)+"%");
		//计算上个月达成率结束
		dataList.add(regionHouseChannelData1);
		dataList.addAll(dataList1);
		return dataList;
	}
	
	/**
	 * 
	 * 专员目标数据分页查询
	 *
	 * @author bushujie
	 * @created 2017年2月7日 下午3:40:37
	 *
	 * @param empTargetItemRequest
	 * @return
	 */
	public List<EmpTargetItemData> empTargetList(EmpTargetItemRequest empTargetItemRequest,List<EmpTargetItem> itemList){
		List<EmpTargetItemData> dataList=new ArrayList<EmpTargetItemData>();
		for(EmpTargetItem item:itemList){
			EmpTargetItemData itemData=new EmpTargetItemData();
			itemData.setEmpCode(item.getEmpCode());
			itemData.setEmpName(item.getEmpName());
			itemData.setIsSet(1);
			StringBuilder sBuilder=new StringBuilder();
			sBuilder.append("员工编号：").append(item.getEmpCode()).append(",员工姓名：").append(item.getEmpName());
			itemData.setEmpCodeName(sBuilder.toString());
			Integer targetHouseNumInteger=0;
			List<EmpTargetItemData> subdataList=new ArrayList<EmpTargetItemData>();
			//月份目标
			Map<String, Object> paramMap=new HashMap<>();
			paramMap.put("empCode", itemData.getEmpCode());
			if(!Check.NuNStr(empTargetItemRequest.getTargetMonth())){
				paramMap.put("targetMonth", empTargetItemRequest.getTargetMonth());
			}
			List<EmpTargetItem> monthTargetList=baseDataDao.findEmpTarget(paramMap);
			for(EmpTargetItem target:monthTargetList){
				EmpTargetItemData subItemData=new EmpTargetItemData();
				subItemData.setEmpCode(item.getEmpCode());
				subItemData.setEmpName(item.getEmpName());
				sBuilder=new StringBuilder();
				sBuilder.append("员工编号：").append(item.getEmpCode()).append(",员工姓名：").append(item.getEmpName());
				subItemData.setEmpCodeName(sBuilder.toString());
				subItemData.setTargetHouseNum(target.getTargetHouseNum());
				subItemData.setTargetMonth(target.getTargetMonth());
				subdataList.add(subItemData);
				targetHouseNumInteger+=target.getTargetHouseNum();
			}
			itemData.setTargetHouseNum(targetHouseNumInteger);
			dataList.add(itemData);
			dataList.addAll(subdataList);
		}
		return dataList;
	}
	
	/**
	 * 
	 * 分页查询专员
	 *
	 * @author bushujie
	 * @created 2017年2月9日 下午5:16:59
	 *
	 * @param empTargetItemRequest
	 * @return
	 */
	public PagingResult<EmpTargetItem> empPageList(EmpTargetItemRequest empTargetItemRequest){
		return baseDataDao.findPushEmpPage(empTargetItemRequest);
	}
}
