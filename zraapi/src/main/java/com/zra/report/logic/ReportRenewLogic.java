package com.zra.report.logic;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ziroom.platform.harmonia.utils.StringUtils;
import com.zra.common.enums.ErrorEnum;
import com.zra.common.error.ResultException;
import com.zra.common.utils.DateUtil;
import com.zra.common.utils.KeyGenUtils;
import com.zra.common.utils.MathUtils;
import com.zra.common.utils.SysConstant;
import com.zra.house.entity.HouseTypeEntity;
import com.zra.house.entity.dto.PageDto;
import com.zra.house.entity.dto.ProjectListReturnDto;
import com.zra.house.logic.HouseTypeLogic;
import com.zra.house.logic.ProjectLogic;
import com.zra.report.entity.ReportRenewEntity;
import com.zra.report.entity.dto.BaseReportDto;
import com.zra.report.entity.dto.ReportRenewDto;
import com.zra.report.service.ReportRenewService;

/**
 * 报表续约数据逻辑层
 * @author huangy168@ziroom.com
 * @Date 2016年10月28日
 * @Time 下午6:24:45
 */
@Component
public class ReportRenewLogic {

	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(ReportRenewLogic.class);
	
	@Autowired
	private ReportRenewService reportRenewService;
	
	@Autowired
	private ProjectLogic projectLogic;
	
	@Autowired
	private HouseTypeLogic houseTypeLogic;
	
	/**
	 * 获取报表续约数据总数
	 * @param projectId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public int getReportRenewCount(String projectId, String beginTime, String endTime) {
		LOGGER.info("Begin to get project:{}'s report renew count.", projectId);
		
		if (StringUtils.isBlank(projectId)) {
			LOGGER.error("The project id is empty.");
			throw new ResultException(ErrorEnum.MSG_PARAM_NULL);
		}
		ReportRenewDto reportRenewDto = new ReportRenewDto();
		reportRenewDto.setProjectId(projectId);
		reportRenewDto.setBeginTime(beginTime);
		reportRenewDto.setEndTime(endTime);
		
		return reportRenewService.getReportRenewCount(reportRenewDto);
	}
	
	/**
	 * 获取报表续约数据
	 * @param projectId
	 * @param beginTime
	 * @param endTime
	 * @param page
	 * @return
	 */
	public Map<String, Map<String, Object>> getReportRenewMapByPage(String projectId, String beginTime, String endTime, PageDto page) {
		LOGGER.info("Begin to get project:{}'s report renew list.", projectId);
		Map<String, Map<String, Object>> resultMap = new LinkedHashMap<String, Map<String,Object>>();
		
		if (StringUtils.isBlank(projectId)) {
			LOGGER.error("The project id is empty.");
			throw new ResultException(ErrorEnum.MSG_PARAM_NULL);
		}
		ReportRenewDto reportRenewDto = new ReportRenewDto();
		reportRenewDto.setProjectId(projectId);
		reportRenewDto.setBeginTime(beginTime);
		reportRenewDto.setEndTime(endTime);
		
		// 1、获取查询时间列表
		List<String> dateList = reportRenewService.getReportRenewTimeSpanByPage(reportRenewDto, page);
		if (dateList != null && dateList.size() > 0) {
			// 2、查询某时间的统计数据
			for (String date : dateList) {
				reportRenewDto.setRecordDate(date);
				List<ReportRenewEntity> reportRenewList = reportRenewService.getReportRenewList(reportRenewDto);
				// 3、映射日期、户型对应数据
				Map<String, Object> reportRenewMap = new LinkedHashMap<>();
				ReportRenewDto allHouseTypeRenew = new ReportRenewDto();	// 统计所有户型
				if (reportRenewList != null && reportRenewList.size() > 0) {
					reportRenewMap.put("ALL", allHouseTypeRenew);
					for (ReportRenewEntity reportRenew : reportRenewList) {
						ReportRenewDto renewDto = convertEntity2Dto(reportRenew);
						reportRenewMap.put(reportRenew == null ? null : reportRenew.getHouseTypeId(), renewDto);
						
						// 到期房源数
						allHouseTypeRenew.setExpireRoomTotal(MathUtils.add(allHouseTypeRenew.getExpireRoomTotal(), renewDto.getExpireRoomTotal()));
						allHouseTypeRenew.setExpireRoomLong(MathUtils.add(allHouseTypeRenew.getExpireRoomLong(), renewDto.getExpireRoomLong()));
						allHouseTypeRenew.setExpireRoomShort1(MathUtils.add(allHouseTypeRenew.getExpireRoomShort1(), renewDto.getExpireRoomShort1()));
						allHouseTypeRenew.setExpireRoomShort2(MathUtils.add(allHouseTypeRenew.getExpireRoomShort2(), renewDto.getExpireRoomShort2()));
						
						// 续约量
						allHouseTypeRenew.setRenewTotal(MathUtils.add(allHouseTypeRenew.getRenewTotal(), renewDto.getRenewTotal()));
						allHouseTypeRenew.setRenewLong(MathUtils.add(allHouseTypeRenew.getRenewLong(), renewDto.getRenewLong()) );
						allHouseTypeRenew.setRenewShort1(MathUtils.add(allHouseTypeRenew.getRenewShort1(), renewDto.getRenewShort1()));
						allHouseTypeRenew.setRenewShort2(MathUtils.add(allHouseTypeRenew.getRenewShort2(), renewDto.getRenewShort2()));
					}
					// 续约率
					allHouseTypeRenew.setRenewRateTotal(MathUtils.mul(MathUtils.div(allHouseTypeRenew.getRenewTotal().toString(), allHouseTypeRenew.getExpireRoomTotal().toString(), 4).toString(), "100").setScale(2).toString());
					allHouseTypeRenew.setRenewRateLong(MathUtils.mul(MathUtils.div(allHouseTypeRenew.getRenewLong().toString(), allHouseTypeRenew.getExpireRoomLong().toString(), 4).toString(), "100").setScale(2).toString());
					allHouseTypeRenew.setRenewRateShort1(MathUtils.mul(MathUtils.div(allHouseTypeRenew.getRenewShort1().toString(), allHouseTypeRenew.getExpireRoomShort1().toString(), 4).toString(), "100").setScale(2).toString());
					allHouseTypeRenew.setRenewRateShort2(MathUtils.mul(MathUtils.div(allHouseTypeRenew.getRenewShort2().toString(), allHouseTypeRenew.getExpireRoomShort2().toString(), 4).toString(), "100").setScale(2).toString());
				}
				
				resultMap.put(date, reportRenewMap);
			}
		}
		
		return resultMap;
	}
	
	/**
	 * 根据时间范围获取报表续约数据
	 * @param projectId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public Map<String, Map<String, Object>> getReportRenewMap(String projectId, String beginTime, String endTime) {
		LOGGER.info("Begin to get project:{}'s report renew list.", projectId);
		Map<String, Map<String, Object>> resultMap = new LinkedHashMap<String, Map<String,Object>>();
		
		if (StringUtils.isBlank(projectId)) {
			LOGGER.error("The project id is empty.");
			throw new ResultException(ErrorEnum.MSG_PARAM_NULL);
		}
		ReportRenewDto reportRenewDto = new ReportRenewDto();
		reportRenewDto.setProjectId(projectId);
		reportRenewDto.setBeginTime(beginTime);
		reportRenewDto.setEndTime(endTime);
		
		Map<String, Object> reportRenewMap = new LinkedHashMap<>();
		ReportRenewDto allHouseTypeRenew = new ReportRenewDto();	// 统计所有户型
		
		// 1、获取报表续约数据
		List<ReportRenewEntity> reportRenewList = reportRenewService.getReportRenewListByTimeSpan(reportRenewDto);
		if (reportRenewList != null && reportRenewList.size() > 0) {
			reportRenewMap.put("ALL", allHouseTypeRenew);
			
			for (ReportRenewEntity reportRenew : reportRenewList) {
				ReportRenewDto renewDto = convertEntity2Dto(reportRenew);
				// 1.1、统计各户型总计
				if (reportRenewMap.containsKey(renewDto.getHouseTypeId())) {
					ReportRenewDto tempDto = (ReportRenewDto) reportRenewMap.get(renewDto.getHouseTypeId());
					
					// 到期房源数				
					tempDto.setExpireRoomTotal(MathUtils.add(tempDto.getExpireRoomTotal(), renewDto.getExpireRoomTotal()));
					tempDto.setExpireRoomLong(MathUtils.add(tempDto.getExpireRoomLong(), renewDto.getExpireRoomLong()));
					tempDto.setExpireRoomShort1(MathUtils.add(tempDto.getExpireRoomShort1(), renewDto.getExpireRoomShort1()));
					tempDto.setExpireRoomShort2(MathUtils.add(tempDto.getExpireRoomShort2(), renewDto.getExpireRoomShort2()));
					
					// 续约量
					tempDto.setRenewTotal(MathUtils.add(tempDto.getRenewTotal(), renewDto.getRenewTotal()));
					tempDto.setRenewLong(MathUtils.add(tempDto.getRenewLong(), renewDto.getRenewLong()) );
					tempDto.setRenewShort1(MathUtils.add(tempDto.getRenewShort1(), renewDto.getRenewShort1()));
					tempDto.setRenewShort2(MathUtils.add(tempDto.getRenewShort2(), renewDto.getRenewShort2()));
				
					reportRenewMap.put(renewDto.getHouseTypeId(), tempDto);
				} else {
					reportRenewMap.put(renewDto.getHouseTypeId(), renewDto);
				}
			}
			
			// 2、计算所有户型数据、续约率
			for (Entry<String, Object> entry : reportRenewMap.entrySet()) {
				ReportRenewDto tempDto = (ReportRenewDto) entry.getValue();
				// 2.1、各户型续约率
				if (!"ALL".equals(entry.getKey())) {
					tempDto.setRenewRateTotal(MathUtils.mul(MathUtils.div(tempDto.getRenewTotal().toString(), tempDto.getExpireRoomTotal().toString(), 4).toString(), "100").setScale(2).toString());
					tempDto.setRenewRateLong(MathUtils.mul(MathUtils.div(tempDto.getRenewLong().toString(), tempDto.getExpireRoomLong().toString(), 4).toString(), "100").setScale(2).toString());
					tempDto.setRenewRateShort1(MathUtils.mul(MathUtils.div(tempDto.getRenewShort1().toString(), tempDto.getExpireRoomShort1().toString(), 4).toString(), "100").setScale(2).toString());
					tempDto.setRenewRateShort2(MathUtils.mul(MathUtils.div(tempDto.getRenewShort2().toString(), tempDto.getExpireRoomShort2().toString(), 4).toString(), "100").setScale(2).toString());
				}
			
				// 2.2、所有户型
				// 到期房源数
				allHouseTypeRenew.setExpireRoomTotal(MathUtils.add(allHouseTypeRenew.getExpireRoomTotal(), tempDto.getExpireRoomTotal()));
				allHouseTypeRenew.setExpireRoomLong(MathUtils.add(allHouseTypeRenew.getExpireRoomLong(), tempDto.getExpireRoomLong()));
				allHouseTypeRenew.setExpireRoomShort1(MathUtils.add(allHouseTypeRenew.getExpireRoomShort1(), tempDto.getExpireRoomShort1()));
				allHouseTypeRenew.setExpireRoomShort2(MathUtils.add(allHouseTypeRenew.getExpireRoomShort2(), tempDto.getExpireRoomShort2()));
				
				// 续约量
				allHouseTypeRenew.setRenewTotal(MathUtils.add(allHouseTypeRenew.getRenewTotal(), tempDto.getRenewTotal()));
				allHouseTypeRenew.setRenewLong(MathUtils.add(allHouseTypeRenew.getRenewLong(), tempDto.getRenewLong()) );
				allHouseTypeRenew.setRenewShort1(MathUtils.add(allHouseTypeRenew.getRenewShort1(), tempDto.getRenewShort1()));
				allHouseTypeRenew.setRenewShort2(MathUtils.add(allHouseTypeRenew.getRenewShort2(), tempDto.getRenewShort2()));
			}
			// 续约率
			allHouseTypeRenew.setRenewRateTotal(MathUtils.mul(MathUtils.div(allHouseTypeRenew.getRenewTotal().toString(), allHouseTypeRenew.getExpireRoomTotal().toString(), 4).toString(), "100").setScale(2).toString());
			allHouseTypeRenew.setRenewRateLong(MathUtils.mul(MathUtils.div(allHouseTypeRenew.getRenewLong().toString(), allHouseTypeRenew.getExpireRoomLong().toString(), 4).toString(), "100").setScale(2).toString());
			allHouseTypeRenew.setRenewRateShort1(MathUtils.mul(MathUtils.div(allHouseTypeRenew.getRenewShort1().toString(), allHouseTypeRenew.getExpireRoomShort1().toString(), 4).toString(), "100").setScale(2).toString());
			allHouseTypeRenew.setRenewRateShort2(MathUtils.mul(MathUtils.div(allHouseTypeRenew.getRenewShort2().toString(), allHouseTypeRenew.getExpireRoomShort2().toString(), 4).toString(), "100").setScale(2).toString());
		
			resultMap.put("total", reportRenewMap);
		}
		
		return resultMap;
	}
	
	/**
	 * 转化实例为dto
	 * @param entity
	 * @return
	 */
	private ReportRenewDto convertEntity2Dto(ReportRenewEntity entity) {
		ReportRenewDto renewDto = new ReportRenewDto();
		if (entity != null) {
			renewDto.setReportRenewId(entity.getReportRenewId());
			renewDto.setProjectId(entity.getProjectId());
			renewDto.setRecordDate(DateUtil.DateToStr(entity.getRecordDate(), DateUtil.TIME_FORMAT));
			renewDto.setHouseTypeId(entity.getHouseTypeId());
			renewDto.setExpireRoomTotal(entity.getExpireRoomTotal());
			renewDto.setExpireRoomLong(entity.getExpireRoomLong());
			renewDto.setExpireRoomShort1(entity.getExpireRoomShort1());
			renewDto.setExpireRoomShort2(entity.getExpireRoomShort2());
			renewDto.setRenewTotal(entity.getRenewTotal());
			renewDto.setRenewLong(entity.getRenewLong());
			renewDto.setRenewShort1(entity.getRenewShort1());
			renewDto.setRenewShort2(entity.getRenewShort2());
			renewDto.setRenewRateTotal(entity.getRenewRateTotal().toString());
			renewDto.setRenewRateLong(entity.getRenewRateLong().toString());
			renewDto.setRenewRateShort1(entity.getRenewRateShort1().toString());
			renewDto.setRenewRateShort2(entity.getRenewRateShort2().toString());
			renewDto.setIsDel(entity.getIsDel());
			renewDto.setCreateTime(DateUtil.DateToStr(entity.getCreateTime(), DateUtil.TIME_FORMAT));
			renewDto.setDeleteTime(DateUtil.DateToStr(entity.getDeleteTime(), DateUtil.TIME_FORMAT));
			renewDto.setUpdateTime(DateUtil.DateToStr(entity.getUpdateTime(), DateUtil.TIME_FORMAT));
			renewDto.setCreateId(entity.getCreateId());
			renewDto.setUpdateId(entity.getUpdateId());
			renewDto.setDeleteId(entity.getDeleteId());
		}
		
		return renewDto;
	}
	
	
	/**
	 * 保存运营报表的新签数据
	 * @author tianxf9
	 * @param recordDate
	 * @return
	 */
	public int saveReportRenewDate(String recordDate) {
		String startDate = recordDate + " 00:00:00";
		String endDate = recordDate +" 23:59:59";
		//获取所有项目
		List<ProjectListReturnDto> projectList = projectLogic.getProjectList();
		//获取所有户型
		List<HouseTypeEntity> houseTypeList = houseTypeLogic.getAllHouseType();
		//项目-户型map
		Map<String,List<HouseTypeEntity>> houseTypeMap = new HashMap<String,List<HouseTypeEntity>>();
		for(HouseTypeEntity houseType:houseTypeList) {
				
				if(houseTypeMap.get(houseType.getProjectId())==null) {
					List<HouseTypeEntity> houseTypes = new ArrayList<HouseTypeEntity>();
					houseTypes.add(houseType);
					houseTypeMap.put(houseType.getProjectId(), houseTypes);
				}else {
					houseTypeMap.get(houseType.getProjectId()).add(houseType);
				}
			}
		//新增实体
		List<ReportRenewEntity> insertEntityList = new ArrayList<ReportRenewEntity>();
		
		for(ProjectListReturnDto project:projectList) {
			
			Map<String,ReportRenewEntity> entitysMap = new HashMap<String,ReportRenewEntity>();
			String projectId = project.getProjId();
			LOGGER.info("====保存续约数据params===startDate="+startDate+";endDate="+endDate+""+";projectId="+projectId);
			List<HouseTypeEntity> houseTypeEntitys = houseTypeMap.get(projectId);
			
			for(HouseTypeEntity houseTypeEntity:houseTypeEntitys) {
				ReportRenewEntity entity = new ReportRenewEntity();
				entity.setReportRenewId(KeyGenUtils.genKey());
				entity.setProjectId(projectId);
				entity.setHouseTypeId(houseTypeEntity.getId());
				try {
					entity.setRecordDate(DateUtil.castString2Date(recordDate, DateUtil.DATE_FORMAT));
				} catch (ParseException e) {
					LOGGER.error("日期处理出错", e);
				}
				entity.setCreateId(SysConstant.ADMINID);
				entity.setIsDel(0);
				entity.setCreateTime(new Date());
				
				entitysMap.put(houseTypeEntity.getId(), entity);
			}
			
			//设置到期房源数量
			setExpireRoomCount(entitysMap, startDate, endDate, projectId);
			//设置续约量
			setRenewTotal(entitysMap, startDate, endDate, projectId);
			//设置续约率
			setRenewRate(entitysMap);
			
			//遍历map
			for(String key:entitysMap.keySet()) {
				insertEntityList.add(entitysMap.get(key));
			}
		}
		
		return this.reportRenewService.insertRenewEntitys(insertEntityList);
	}
	
	/**
	 * 设置到期房源数量
	 * @author tianxf9
	 * @param entitysMap
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 */
	public void setExpireRoomCount(Map<String,ReportRenewEntity> entitysMap,String startDate,String endDate,String projectId) {
		
		//获取到期房源数量
		List<BaseReportDto> baseReportDtos = this.reportRenewService.getExpireRoomCount(startDate, endDate, projectId);
		//处理到期房源数量
		for(BaseReportDto baseReportDto:baseReportDtos) {
			ReportRenewEntity entity = entitysMap.get(baseReportDto.getHouseTypeId());
			Integer totalExireRoomCount = entity.getExpireRoomTotal().intValue() + baseReportDto.getCount();
			if(baseReportDto.getConRentYear()==1||baseReportDto.getConRentYear()==2||baseReportDto.getConRentYear()==3) {
				
				Integer short1ExireRoomCount = entity.getExpireRoomShort1().intValue() + baseReportDto.getCount();
				entity.setExpireRoomShort1(short1ExireRoomCount);
			}else if(baseReportDto.getConRentYear()==4||baseReportDto.getConRentYear()==5||baseReportDto.getConRentYear()==6) {
				
				Integer short2ExireRoomCount = entity.getExpireRoomShort2().intValue() + baseReportDto.getCount();
				entity.setExpireRoomShort2(short2ExireRoomCount);
			}else {
				
				Integer longExireRoomCount = entity.getExpireRoomLong().intValue() + baseReportDto.getCount();
				entity.setExpireRoomLong(longExireRoomCount);
			}
			
			entity.setExpireRoomTotal(totalExireRoomCount);
			
			entitysMap.put(baseReportDto.getHouseTypeId(), entity);
			
		}
		
	}
	
	/**
	 * 设置续约量
	 * @author tianxf9
	 * @param entitysMap
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 */
	public void setRenewTotal(Map<String,ReportRenewEntity> entitysMap,String startDate,String endDate,String projectId) {
		
		List<BaseReportDto> baseShort1Count = this.reportRenewService.getRenewShort1Count(startDate, endDate, projectId);
		
		//设置短租（1——3个月续约量）
		for(BaseReportDto baseReportCount:baseShort1Count) {
			ReportRenewEntity entity = entitysMap.get(baseReportCount.getHouseTypeId());
			Integer totalCount = entity.getRenewTotal().intValue() + baseReportCount.getCount();
			entity.setRenewTotal(totalCount);
			Integer short1Count = entity.getRenewShort1().intValue() + baseReportCount.getCount();
			entity.setRenewShort1(short1Count);	
			
			entitysMap.put(baseReportCount.getHouseTypeId(), entity);
		}
		
		List<BaseReportDto> baseShort2Count = this.reportRenewService.getRenewShort2Count(startDate, endDate, projectId);
		//设置短租（4——6个月续约量）
		for(BaseReportDto baseReportCount:baseShort2Count) {
			ReportRenewEntity entity = entitysMap.get(baseReportCount.getHouseTypeId());
			Integer totalCount = entity.getRenewTotal().intValue() + baseReportCount.getCount();
			entity.setRenewTotal(totalCount);
			Integer short2Count = entity.getRenewShort2().intValue() + baseReportCount.getCount();
			entity.setRenewShort2(short2Count);
			
			entitysMap.put(baseReportCount.getHouseTypeId(), entity);
		}
		
		List<BaseReportDto> baseLongCount = this.reportRenewService.getRenewLongCount(startDate, endDate, projectId);
		
		//设置长租续约量
		for(BaseReportDto baseReportCount:baseLongCount) {
			ReportRenewEntity entity = entitysMap.get(baseReportCount.getHouseTypeId());
			Integer totalCount = entity.getRenewTotal().intValue() + baseReportCount.getCount();
			entity.setRenewTotal(totalCount);
			Integer longCount = entity.getRenewLong().intValue() + baseReportCount.getCount();
			entity.setRenewLong(longCount);
			
			entitysMap.put(baseReportCount.getHouseTypeId(), entity);
		}
	}
	
	/**
	 * 设置续约率
	 * @author tianxf9
	 * @param entitysMap
	 */
	public void setRenewRate(Map<String,ReportRenewEntity> entitysMap) {
		
		for(String key:entitysMap.keySet()) {
			ReportRenewEntity entity = entitysMap.get(key);
			if(entity.getExpireRoomLong().intValue()!=0) {
			    BigDecimal rate = new BigDecimal(entity.getRenewLong()).divide(new BigDecimal(entity.getExpireRoomLong()),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
			    entity.setRenewRateLong(rate);
			}
			
			if(entity.getExpireRoomShort1().intValue()!=0) {
			    BigDecimal rate = new BigDecimal(entity.getRenewShort1()).divide(new BigDecimal(entity.getExpireRoomShort1()), 4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
			    entity.setRenewRateShort1(rate);
			}
			
			if(entity.getExpireRoomShort2().intValue()!=0) {
			    BigDecimal rate = new BigDecimal(entity.getRenewShort2()).divide(new BigDecimal(entity.getExpireRoomShort2()),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
			    entity.setRenewRateShort2(rate);
			}
			
			if(entity.getRenewTotal().intValue()!=0) { 
			    BigDecimal rate = new BigDecimal(entity.getRenewTotal()).divide(new BigDecimal(entity.getExpireRoomTotal()), 4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
			    entity.setRenewRateTotal(rate);
			}
			
			entitysMap.put(key, entity);
		}
	}
	
	/**
	 * 根据记录日期删除数据
	 * @author tianxf9
	 * @param recordDate
	 * @return
	 */
	public int delReportRenewDate(String recordDate) {
		return this.reportRenewService.delRenewEntitysByRecordDate(recordDate);
	}
}
