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
import com.zra.kanban.entity.dto.KanbanRateDto;
import com.zra.report.entity.ReportStockEntity;
import com.zra.report.entity.dto.BaseReportDto;
import com.zra.report.entity.dto.ReportStockDto;
import com.zra.report.service.ReportStockService;

/**
 * 报表库存数据逻辑层
 * @author huangy168@ziroom.com
 * @Date 2016年10月28日
 * @Time 下午6:24:45
 */
@Component
public class ReportStockLogic {

	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(ReportStockLogic.class);
	
	@Autowired
	private ReportStockService reportStockService;
	
	@Autowired
	private ProjectLogic projectLogic;
	
	@Autowired
	private HouseTypeLogic houseTypeLogic;
	
	/**
	 * 获取报表库存数据总数
	 * @param projectId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public int getReportStockCount(String projectId, String beginTime, String endTime) {
		LOGGER.info("Begin to get project:{}'s report stock count.", projectId);
		
		if (StringUtils.isBlank(projectId)) {
			LOGGER.error("The project id is empty.");
			throw new ResultException(ErrorEnum.MSG_PARAM_NULL);
		}
		ReportStockDto reportStockDto = new ReportStockDto();
		reportStockDto.setProjectId(projectId);
		reportStockDto.setBeginTime(beginTime);
		reportStockDto.setEndTime(endTime);
		
		return reportStockService.getReportStockCount(reportStockDto);
	}
	
	/**
	 * 获取报表库存数据,明细中的所有户型，户型1，户型2....的数据
	 * @param projectId
	 * @param beginTime
	 * @param endTime
	 * @param page
	 * @return
	 */
	public Map<String, Map<String, Object>> getReportStockMapByPage(String projectId, String beginTime, String endTime, PageDto page) {
		LOGGER.info("Begin to get project:{}'s report bo list.", projectId);
		Map<String, Map<String, Object>> resultMap = new LinkedHashMap<String, Map<String,Object>>();
		
		if (StringUtils.isBlank(projectId)) {
			LOGGER.error("The project id is empty.");
			throw new ResultException(ErrorEnum.MSG_PARAM_NULL);
		}
		ReportStockDto reportStockDto = new ReportStockDto();
		reportStockDto.setProjectId(projectId);
		reportStockDto.setBeginTime(beginTime);
		reportStockDto.setEndTime(endTime);
		
		// 1、获取查询时间列表
		List<String> dateList = reportStockService.getReportStockTimeSpanByPage(reportStockDto, page);
		if (dateList != null && dateList.size() > 0) {
			// 2、查询某时间的统计数据
			for (String date : dateList) {
				reportStockDto.setRecordDate(date);
				List<ReportStockEntity> reportStockList = reportStockService.getReportStockList(reportStockDto);
				// 3、映射日期、户型对应数据
				Map<String, Object> reportStockMap = new LinkedHashMap<>();
				ReportStockDto allHouseTypeStock = new ReportStockDto();	// 统计所有
				if (reportStockList != null && reportStockList.size() > 0) {
					reportStockMap.put("ALL", allHouseTypeStock);
					for (ReportStockEntity reportStock : reportStockList) {
						ReportStockDto stockDto = convertEntity2Dto(reportStock);
						//一天中每一个熟悉的数据
						reportStockMap.put(reportStock == null ? null : reportStock.getHouseTypeId(), stockDto);
					
						//累计一天中所有户型的数据数据
						//库存数据
						allHouseTypeStock.setStockCount(MathUtils.add(allHouseTypeStock.getStockCount(), stockDto.getStockCount()));
						allHouseTypeStock.setRentableCount(MathUtils.add(allHouseTypeStock.getRentableCount(), stockDto.getRentableCount()));
						allHouseTypeStock.setLeasedCount(MathUtils.add(allHouseTypeStock.getLeasedCount(), stockDto.getLeasedCount()));
						allHouseTypeStock.setShortLeasedCount(MathUtils.add(allHouseTypeStock.getShortLeasedCount(), stockDto.getShortLeasedCount()));
						
						// 剩余库存 
						allHouseTypeStock.setLeaveStockTotal(MathUtils.add(allHouseTypeStock.getLeaveStockTotal(), stockDto.getLeaveStockTotal()));
						allHouseTypeStock.setLeaveStockConfig(MathUtils.add(allHouseTypeStock.getLeaveStockConfig(), stockDto.getLeaveStockConfig()));
						allHouseTypeStock.setLeaveStockWait(MathUtils.add(allHouseTypeStock.getLeaveStockWait(), stockDto.getLeaveStockWait()));
						allHouseTypeStock.setLeaveStockOther(MathUtils.add(allHouseTypeStock.getLeaveStockOther(), stockDto.getLeaveStockOther()));
						
						// 退租量
						allHouseTypeStock.setQuitTotal(MathUtils.add(allHouseTypeStock.getQuitTotal(), stockDto.getQuitTotal()));
						allHouseTypeStock.setQuitNormal(MathUtils.add(allHouseTypeStock.getQuitNormal(), stockDto.getQuitNormal()));
						allHouseTypeStock.setQuitUnnormal(MathUtils.add(allHouseTypeStock.getQuitUnnormal(), stockDto.getQuitUnnormal()));
						allHouseTypeStock.setQuitCustomer(MathUtils.add(allHouseTypeStock.getQuitCustomer(), stockDto.getQuitCustomer()));
						allHouseTypeStock.setQuitUnsatisfied(MathUtils.add(allHouseTypeStock.getQuitUnsatisfied(), stockDto.getQuitUnsatisfied()));
						allHouseTypeStock.setQuitChange(MathUtils.add(allHouseTypeStock.getQuitChange(), stockDto.getQuitChange()));
						allHouseTypeStock.setQuitExchange(MathUtils.add(allHouseTypeStock.getQuitExchange(), stockDto.getQuitExchange()));
					}
					allHouseTypeStock.setOccupancyRate(MathUtils.mul(MathUtils.div(allHouseTypeStock.getLeasedCount().toString(), allHouseTypeStock.getRentableCount().toString(), 4).toString(), "100").setScale(2).toString());
					allHouseTypeStock.setShortLeasedRate(MathUtils.mul(MathUtils.div(allHouseTypeStock.getShortLeasedCount().toString(), allHouseTypeStock.getLeasedCount().toString(), 4).toString(), "100").setScale(2).toString());
				}
				
				resultMap.put(date, reportStockMap);
			}
		}
		
		return resultMap;
	}
	
	/**
	 * 根据时间范围获取报表库存数据总计
	 * @param projectId
	 * @param beginTime
	 * @param endTime
	 * @param page
	 * @return
	 */
	public Map<String, Map<String, Object>> getReportStockMap(String projectId, String beginTime, String endTime) {
		LOGGER.info("Begin to get project:{}'s report bo list.", projectId);
		Map<String, Map<String, Object>> resultMap = new LinkedHashMap<String, Map<String,Object>>();
		
		if (StringUtils.isBlank(projectId)) {
			LOGGER.error("The project id is empty.");
			throw new ResultException(ErrorEnum.MSG_PARAM_NULL);
		}
		ReportStockDto reportStockDto = new ReportStockDto();
		reportStockDto.setProjectId(projectId);
		reportStockDto.setBeginTime(beginTime);
		reportStockDto.setEndTime(endTime);
		
		Map<String, Object> reportStockMap = new LinkedHashMap<>();
		ReportStockDto allHouseTypeStock = new ReportStockDto();	// 统计所有户型
		
		// 1、获取报表库存数据列表
		List<ReportStockEntity> reportStockList = reportStockService.getReportStockListByTimeSpan(reportStockDto);
		if (reportStockList != null && reportStockList.size() > 0) {
			reportStockMap.put("ALL", allHouseTypeStock);
			
			//时间段内累加每天同一户型可出租总数之和 用于计算总计的出租率
			Map<String,Integer> rentableCountSumMap = new HashMap<String,Integer>();
			//时间段内累加每天同一户型出租数之和 用于计算总计的出租率
			Map<String,Integer> leasedCountSumMap = new HashMap<String,Integer>();
			
			for (ReportStockEntity reportStock : reportStockList) {
				ReportStockDto stockDto = convertEntity2Dto(reportStock);
				// 1.1、统计各户型总计
				if (reportStockMap.containsKey(stockDto.getHouseTypeId())) {
					ReportStockDto tempDto = (ReportStockDto) reportStockMap.get(stockDto.getHouseTypeId());
					
					// 库存总数、出租数取最后天即可
					tempDto.setStockCount(stockDto.getStockCount());
					tempDto.setLeasedCount(stockDto.getLeasedCount());
					tempDto.setShortLeasedCount(stockDto.getShortLeasedCount());
					
					if(rentableCountSumMap.get(stockDto.getHouseTypeId())==null) {
						rentableCountSumMap.put(stockDto.getHouseTypeId(), stockDto.getRentableCount());
					}else {
						Integer sum1 = MathUtils.add(rentableCountSumMap.get(stockDto.getHouseTypeId()), stockDto.getRentableCount());
						rentableCountSumMap.put(stockDto.getHouseTypeId(),sum1);
					}
					
					if(leasedCountSumMap.get(stockDto.getHouseTypeId())==null) {
						leasedCountSumMap.put(stockDto.getHouseTypeId(), stockDto.getLeasedCount());
					}else {
						Integer sum2 = MathUtils.add(leasedCountSumMap.get(stockDto.getHouseTypeId()), stockDto.getLeasedCount());
						leasedCountSumMap.put(stockDto.getHouseTypeId(), sum2);
					
					}
					
					// 剩余库存---取最后天
					tempDto.setLeaveStockTotal(stockDto.getLeaveStockTotal());
					tempDto.setLeaveStockConfig(stockDto.getLeaveStockConfig());
					tempDto.setLeaveStockWait(stockDto.getLeaveStockWait());
					tempDto.setLeaveStockOther(stockDto.getLeaveStockOther());
					
					// 退租量
					tempDto.setQuitTotal(MathUtils.add(tempDto.getQuitTotal(), stockDto.getQuitTotal()));
					tempDto.setQuitNormal(MathUtils.add(tempDto.getQuitNormal(), stockDto.getQuitNormal()));
					tempDto.setQuitUnnormal(MathUtils.add(tempDto.getQuitUnnormal(), stockDto.getQuitUnnormal()));
					tempDto.setQuitCustomer(MathUtils.add(tempDto.getQuitCustomer(), stockDto.getQuitCustomer()));
					tempDto.setQuitUnsatisfied(MathUtils.add(tempDto.getQuitUnsatisfied(), stockDto.getQuitUnsatisfied()));
					tempDto.setQuitChange(MathUtils.add(tempDto.getQuitChange(), stockDto.getQuitChange()));
					tempDto.setQuitExchange(MathUtils.add(tempDto.getQuitExchange(), stockDto.getQuitExchange()));
					
					reportStockMap.put(stockDto.getHouseTypeId(), tempDto);
				} else {
					reportStockMap.put(stockDto.getHouseTypeId(), stockDto);
					rentableCountSumMap.put(stockDto.getHouseTypeId(), stockDto.getRentableCount());
					leasedCountSumMap.put(stockDto.getHouseTypeId(), stockDto.getLeasedCount());
				}
			}
			
			// 2、计算所有户型数据、出租率等
			for (Entry<String, Object> entry : reportStockMap.entrySet()) {
				ReportStockDto tempDto = (ReportStockDto) entry.getValue();
				if (!"ALL".equals(entry.getKey())) {
					if(leasedCountSumMap.get(entry.getKey())!=null&&rentableCountSumMap.get(entry.getKey())!=null&&rentableCountSumMap.get(entry.getKey()).intValue()!=0) {
						tempDto.setOccupancyRate(MathUtils.mul(MathUtils.div(leasedCountSumMap.get(entry.getKey()).toString(), rentableCountSumMap.get(entry.getKey()).toString(), 4).toString(), "100").setScale(2).toString());
					}else {
						tempDto.setOccupancyRate("0.00");
					}
					
					tempDto.setShortLeasedRate(MathUtils.mul(MathUtils.div(tempDto.getShortLeasedCount().toString(), tempDto.getLeasedCount().toString(), 4).toString(), "100").setScale(2).toString());
				}

				allHouseTypeStock.setStockCount(MathUtils.add(allHouseTypeStock.getStockCount(), tempDto.getStockCount()));
				allHouseTypeStock.setRentableCount(MathUtils.add(allHouseTypeStock.getRentableCount(), tempDto.getRentableCount()));
				allHouseTypeStock.setLeasedCount(MathUtils.add(allHouseTypeStock.getLeasedCount(), tempDto.getLeasedCount()));
				allHouseTypeStock.setShortLeasedCount(MathUtils.add(allHouseTypeStock.getShortLeasedCount(), tempDto.getShortLeasedCount()));
				
				// 剩余库存
				allHouseTypeStock.setLeaveStockTotal(MathUtils.add(allHouseTypeStock.getLeaveStockTotal(), tempDto.getLeaveStockTotal()));
				allHouseTypeStock.setLeaveStockConfig(MathUtils.add(allHouseTypeStock.getLeaveStockConfig(), tempDto.getLeaveStockConfig()));
				allHouseTypeStock.setLeaveStockWait(MathUtils.add(allHouseTypeStock.getLeaveStockWait(), tempDto.getLeaveStockWait()));
				allHouseTypeStock.setLeaveStockOther(MathUtils.add(allHouseTypeStock.getLeaveStockOther(), tempDto.getLeaveStockOther()));
				
				// 退租量
				allHouseTypeStock.setQuitTotal(MathUtils.add(allHouseTypeStock.getQuitTotal(), tempDto.getQuitTotal()));
				allHouseTypeStock.setQuitNormal(MathUtils.add(allHouseTypeStock.getQuitNormal(), tempDto.getQuitNormal()));
				allHouseTypeStock.setQuitUnnormal(MathUtils.add(allHouseTypeStock.getQuitUnnormal(), tempDto.getQuitUnnormal()));
				allHouseTypeStock.setQuitCustomer(MathUtils.add(allHouseTypeStock.getQuitCustomer(), tempDto.getQuitCustomer()));
				allHouseTypeStock.setQuitUnsatisfied(MathUtils.add(allHouseTypeStock.getQuitUnsatisfied(), tempDto.getQuitUnsatisfied()));
				allHouseTypeStock.setQuitChange(MathUtils.add(allHouseTypeStock.getQuitChange(), tempDto.getQuitChange()));
				allHouseTypeStock.setQuitExchange(MathUtils.add(allHouseTypeStock.getQuitExchange(), tempDto.getQuitExchange()));
			}
			
			//计算总计中的所有户型的出租率
			int totalRentableCount = 0;
			int totalLeasedCount = 0;
			for(String key:rentableCountSumMap.keySet()) {
				totalRentableCount = totalRentableCount+rentableCountSumMap.get(key).intValue();
			}
			
			for(String key:leasedCountSumMap.keySet()) {
				totalLeasedCount = totalLeasedCount+leasedCountSumMap.get(key).intValue();
			}
			
			
			
			allHouseTypeStock.setOccupancyRate(MathUtils.mul(MathUtils.div(String.valueOf(totalLeasedCount), String.valueOf(totalRentableCount), 4).toString(), "100").setScale(2).toString());
			allHouseTypeStock.setShortLeasedRate(MathUtils.mul(MathUtils.div(allHouseTypeStock.getShortLeasedCount().toString(), allHouseTypeStock.getLeasedCount().toString(), 4).toString(), "100").setScale(2).toString());
			
			resultMap.put("total", reportStockMap);
		}
		
		return resultMap;
	}
	
	/**
	 * 转化实例为dto
	 * @param entity
	 * @return
	 */
	private ReportStockDto convertEntity2Dto(ReportStockEntity entity) {
		ReportStockDto stockDto = new ReportStockDto();
		if (entity != null) {
			stockDto.setReportStockId(entity.getReportStockId());
			stockDto.setProjectId(entity.getProjectId());
			stockDto.setRecordDate(DateUtil.DateToStr(entity.getRecordDate(), DateUtil.TIME_FORMAT));
			stockDto.setHouseTypeId(entity.getHouseTypeId());
			stockDto.setStockCount(entity.getStockCount());
			stockDto.setRentableCount(entity.getRentableCount());
			stockDto.setLeasedCount(entity.getLeasedCount());
			stockDto.setOccupancyRate(entity.getOccupancyRate().toString());
			stockDto.setShortLeasedCount(entity.getShortLeasedCount());
			stockDto.setShortLeasedRate(entity.getShortLeasedRate().toString());
			stockDto.setLeaveStockConfig(entity.getLeaveStockConfig());
			stockDto.setLeaveStockWait(entity.getLeaveStockWait());
			stockDto.setLeaveStockOther(entity.getLeaveStockOther());
			stockDto.setLeaveStockOther(entity.getLeaveStockOther());
			stockDto.setLeaveStockTotal(entity.getLeaveStockTotal());
			stockDto.setQuitNormal(entity.getQuitNormal());
			stockDto.setQuitUnnormal(entity.getQuitUnnormal());
			stockDto.setQuitCustomer(entity.getQuitCustomer());
			stockDto.setQuitUnsatisfied(entity.getQuitUnsatisfied());
			stockDto.setQuitChange(entity.getQuitChange());
			stockDto.setQuitExchange(entity.getQuitExchange());
			stockDto.setQuitTotal(entity.getQuitTotal());
			stockDto.setIsDel(entity.getIsDel());
			stockDto.setCreateTime(DateUtil.DateToStr(entity.getCreateTime(), DateUtil.TIME_FORMAT));
			stockDto.setDeleteTime(DateUtil.DateToStr(entity.getDeleteTime(), DateUtil.TIME_FORMAT));
			stockDto.setUpdateTime(DateUtil.DateToStr(entity.getUpdateTime(), DateUtil.TIME_FORMAT));
			stockDto.setCreateId(entity.getCreateId());
			stockDto.setUpdateId(entity.getUpdateId());
			stockDto.setDeleteId(entity.getDeleteId());
		}
		
		return stockDto;
	}
	
	/**
	 * 保存运营报表库存数据
	 * @author tianxf9
	 * @param recordDate
	 * @return
	 */
	public int saveReportStockCount(String recordDate) {
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
		List<ReportStockEntity> insertEntityList = new ArrayList<ReportStockEntity>();
		
		for(ProjectListReturnDto project:projectList) {
			Map<String,ReportStockEntity> entitysMap = new HashMap<String,ReportStockEntity>();
			String projectId = project.getProjId();
			LOGGER.info("====保存库存数据params===recordDate="+recordDate+";projectId="+projectId);
			List<HouseTypeEntity> houseTypeEntitys = houseTypeMap.get(projectId);
			
			for(HouseTypeEntity houseTypeEntity:houseTypeEntitys) {
				ReportStockEntity entity = new ReportStockEntity();
				entity.setReportStockId(KeyGenUtils.genKey());
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
			//设置库存总数,可出租数
			setStockCount(entitysMap, projectId);
			//设置已出租数,出租率
			setLeasedCount(entitysMap, projectId, recordDate);
			//设置短租出租数,短租占比
			setShortLeasedCount(entitysMap, projectId, recordDate);
			//设置剩余库存
			setLeaveStockCount(entitysMap, projectId,recordDate);
			//设置退租量
		    setQuitCount(entitysMap, projectId, recordDate);
			
			//遍历map
			for(String key:entitysMap.keySet()) {
				insertEntityList.add(entitysMap.get(key));
			}
		}
		
		return this.reportStockService.saveReportStockEntitys(insertEntityList);
	}
	

	/**
	 * 设置库存数，可出租数
	 * @author tianxf9
	 * @param entitysMap
	 * @param projectId
	 */
	public void setStockCount(Map<String,ReportStockEntity> entitysMap,String projectId) {
		
		List<BaseReportDto> baseReportDtos = this.reportStockService.getStockCount(projectId);
		//获取可出租数
		List<BaseReportDto> rentableCount = this.reportStockService.getRentableCount(projectId);
		
		for(BaseReportDto dto:baseReportDtos) {
			if(entitysMap.get(dto.getHouseTypeId())!=null) {
				entitysMap.get(dto.getHouseTypeId()).setStockCount(dto.getCount());
			}
		}
		
		//设置可出租数
		for(BaseReportDto dto:rentableCount) {
			if(entitysMap.get(dto.getHouseTypeId())!=null) {
			    entitysMap.get(dto.getHouseTypeId()).setRentableCount(dto.getCount());
			}
		}
	}
	
	/**
	 * 设置已出租数,出租率
	 * @author tianxf9
	 * @param entitysMap
	 * @param projectId
	 * @param startDate
	 * @param endDate
	 */
	public void setLeasedCount(Map<String,ReportStockEntity> entitysMap,String projectId,String recordDate) {
		List<BaseReportDto> baseReportDtos = this.reportStockService.getLeasedCount(recordDate, projectId);
		for(BaseReportDto dto:baseReportDtos) {
			ReportStockEntity entity = entitysMap.get(dto.getHouseTypeId());
			if(entity!=null) {
				entity.setLeasedCount(dto.getCount());
				if(entity.getRentableCount().intValue()!=0) {
					BigDecimal newRate = new BigDecimal(entity.getLeasedCount()).divide(new BigDecimal(entity.getRentableCount()),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
					entity.setOccupancyRate(newRate);
				}
				entitysMap.put(dto.getHouseTypeId(), entity);
			}
		}
	}
	
	/**
	 * 设置短租出租数，以及短租占比
	 * @author tianxf9
	 * @param entitysMap
	 * @param projectId
	 * @param startDate
	 * @param endDate
	 */
	public void setShortLeasedCount(Map<String,ReportStockEntity> entitysMap,String projectId,String recordDate) {
		List<BaseReportDto> baseReportDtos = this.reportStockService.getShortLeasedCount(recordDate, projectId);
		for(BaseReportDto dto:baseReportDtos) {
			ReportStockEntity entity = entitysMap.get(dto.getHouseTypeId());
			if(entity!=null) {
				entity.setShortLeasedCount(dto.getCount());
				if(entity.getLeasedCount().intValue()!=0) {
					BigDecimal newRate = new BigDecimal(dto.getCount()).divide(new BigDecimal(entity.getLeasedCount()),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
					entity.setShortLeasedRate(newRate);
				}
				entitysMap.put(dto.getHouseTypeId(), entity);
			}

		}
	}
	
	/**
	 * 设置剩余库存
	 * @author tianxf9
	 * @param entitysMap
	 * @param projectId
	 */
	public void setLeaveStockCount(Map<String,ReportStockEntity> entitysMap,String projectId,String recordDate) {
		List<BaseReportDto> baseReportDtos = this.reportStockService.getLeaveStockCount(recordDate,projectId);
		
		for(BaseReportDto dto:baseReportDtos) {
			ReportStockEntity entity = entitysMap.get(dto.getHouseTypeId());
			if(entity!=null) {
				
				if(dto.getCurrentState().equals("2")) {
					entity.setLeaveStockConfig(dto.getCount());
				}else {
					entity.setLeaveStockWait(dto.getCount());
				}
				
				entitysMap.put(dto.getHouseTypeId(), entity);
			}
		}
		
		for(String key:entitysMap.keySet()) {
			ReportStockEntity entity = entitysMap.get(key);
			entity.setLeaveStockTotal(entity.getStockCount().intValue()-entity.getLeasedCount().intValue());
			entity.setLeaveStockOther(entity.getLeaveStockTotal().intValue()-entity.getLeaveStockConfig().intValue()-entity.getLeaveStockWait().intValue());
			
			entitysMap.put(key, entity);
		}
	}
	
	/**
	 * 设置退租量
	 * @author tianxf9
	 * @param entitysMap
	 * @param projectId
	 * @param startDate
	 * @param endDate
	 */
	public void setQuitCount(Map<String,ReportStockEntity> entitysMap,String projectId,String recordDate) {
		List<BaseReportDto> baseReportDtos = this.reportStockService.getQuitCount(recordDate, projectId);
		//'退租类型  [0 正常退租,1 非正常退租,2 客户单方面解约, 3 三天不满意退租,4 换租,5 转租,6 短租退租]'
		for(BaseReportDto dto:baseReportDtos) {
			ReportStockEntity entity = entitysMap.get(dto.getHouseTypeId());
			if(entity!=null) {
				entity.setQuitTotal(entity.getQuitTotal().intValue() + dto.getCount());
				if("0".equals(dto.getSurType())) {
					entity.setQuitNormal(dto.getCount());
				}else if("1".equals(dto.getSurType())) {
					entity.setQuitUnnormal(dto.getCount());
				}else if("2".equals(dto.getSurType())) {
					entity.setQuitCustomer(dto.getCount());
				}else if("3".equals(dto.getSurType())) {
					entity.setQuitUnsatisfied(dto.getCount());
				}else if("4".equals(dto.getSurType())) {
					entity.setQuitChange(dto.getCount());
				}else if("5".equals(dto.getSurType())) {
					entity.setQuitExchange(dto.getCount());
				}
				
				entitysMap.put(dto.getHouseTypeId(), entity);
			}

		}
	}
	
	/**
	 * 删除记录根据记录时间
	 * @author tianxf9
	 * @param recordDate
	 * @return
	 */
	public int delEntitysByRecordDate(String recordDate) {
		recordDate = recordDate + "%";
		return this.reportStockService.delEntitysByRecordDate(recordDate, new Date(), SysConstant.ADMINID);
	}
	
	
	/**
	 * 获取所有项目出租率（目标看板）
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<KanbanRateDto> getLeasedRateForKanBan(String startDate,String endDate) {
		return this.reportStockService.getLeasedRateForKB(startDate, endDate);
	}
	
	/**
	 * 获取所有项目的日均入住率(目标看板)
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param count
	 * @return
	 */
	public List<KanbanRateDto> getOccupancyRateAvge(String startDate,String endDate,int count) {
		return this.reportStockService.getOccupancyRateAvge(startDate, endDate, count);
	}
	
}
