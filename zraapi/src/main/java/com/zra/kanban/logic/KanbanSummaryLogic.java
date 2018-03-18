package com.zra.kanban.logic;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.business.logic.BusinessReportLogic;
import com.zra.common.dto.business.BoReportQueryParamDto;
import com.zra.common.dto.house.ProjectDto;
import com.zra.common.dto.kanban.KanbanGoalDto;
import com.zra.common.dto.kanban.KanbanQueryDto;
import com.zra.common.dto.kanban.SummaryShowDto;
import com.zra.common.enums.CycleTypeEnum;
import com.zra.common.utils.DateTool;
import com.zra.common.utils.DateUtil;
import com.zra.common.utils.KeyGenUtils;
import com.zra.common.utils.SysConstant;
import com.zra.house.logic.ProjectLogic;
import com.zra.kanban.entity.KanbanSummary;
import com.zra.kanban.entity.dto.KanbanRateDto;
import com.zra.kanban.service.KanbanSummaryService;
import com.zra.report.logic.ReportPaymentLogic;
import com.zra.report.logic.ReportStockLogic;

/**
 * 目标看板-核心数据逻辑层
 * 
 * @author tianxf9
 *
 */
@Component
public class KanbanSummaryLogic {

	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(KanbanSummaryLogic.class);

	@Autowired
	private KanbanSummaryService summaryService;

	@Autowired
	private BusinessReportLogic boLogic;

	@Autowired
	private ReportStockLogic reportStockLogic;

	@Autowired
	private ReportPaymentLogic paymentLogic;

	@Autowired
	private ProjectLogic projectLogic;
	
	@Autowired
	private KanbanGoalLogic kanbanGoalLogic;

	/**
	 * 获取所有项目的商机约看及时跟进率
	 * 
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<KanbanRateDto> getYkDealRate(String startDate, String endDate) {
		BoReportQueryParamDto paramDto = new BoReportQueryParamDto();
		startDate = startDate + " 00:00:00";
		endDate = endDate + " 23:59:59";
		paramDto.setStartDate(startDate);
		paramDto.setEndDate(endDate);

		return this.boLogic.getBoGJRate(paramDto);
	}

	/**
	 * 获取所有项目的出租率
	 * 
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<KanbanRateDto> getLeasedRate(String startDate, String endDate) {
		startDate = startDate + " 00:00:00";
		endDate = endDate + " 00:00:00";
		return this.reportStockLogic.getLeasedRateForKanBan(startDate, endDate);
	}

	/**
	 * 获取所有项目的日均入住率
	 * 
	 * @author tianxf9
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public List<KanbanRateDto> occupancyRateAvge(String startDateStr, String endDateStr) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		Date endDate = null;
		int count = 0;
		try {
			startDate = sdf.parse(startDateStr);
			endDate = sdf.parse(endDateStr);
			count = DateUtil.daysBetween(endDate, startDate);
		} catch (ParseException e) {
			LOGGER.error("计算日均入住率日期处理出现问题", e);
		}
		count = count + 1;
		startDateStr = startDateStr + " 00:00:00";
		endDateStr = endDateStr + " 00:00:00";
		return this.reportStockLogic.getOccupancyRateAvge(startDateStr, endDateStr, count);
	}

	/**
	 * 获取所有项目实际回款率
	 * 
	 * @author tianxf9
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public List<KanbanRateDto> getVoucherRate(String startDateStr, String endDateStr) {
		return this.paymentLogic.getVoucherCountRateForKB(startDateStr, endDateStr);
	}

	/**
	 * 获取出租周期
	 * 
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<KanbanRateDto> getLeaseCycleLogic(String startDate, String endDate) {
		return this.summaryService.getLeaseCycleService(startDate, endDate);
	}

	/**
	 * 新增保存目标看板核心数据
	 * 
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int saveOrUpdateSummaryList(String startDateStr, String endDateStr,int type) {
        
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 获取商机约看及时跟进率
		List<KanbanRateDto> ykDealRateList = this.getYkDealRate(startDateStr, endDateStr);
		// 获取出租率
		List<KanbanRateDto> leaseRateList = this.getLeasedRate(startDateStr, endDateStr);
		// 获取日均入住率
		List<KanbanRateDto> occupancyRateList = this.occupancyRateAvge(startDateStr, endDateStr);
		// 获取实际回款率
		List<KanbanRateDto> voucherRateList = this.getVoucherRate(startDateStr, endDateStr);
		// 获取出租周期
		List<KanbanRateDto> leaseCycleList = this.getLeaseCycleLogic(startDateStr, endDateStr);

		// map key = projectId,value=相应的值
		Map<String, BigDecimal> ykDealRateMap = new HashMap<String, BigDecimal>();
		for (KanbanRateDto ykDealRate : ykDealRateList) {
			ykDealRateMap.put(ykDealRate.getProjectId(), ykDealRate.getRate());
		}

		Map<String, BigDecimal> leaseRateMap = new HashMap<String, BigDecimal>();
		for (KanbanRateDto leaseRate : leaseRateList) {
			leaseRateMap.put(leaseRate.getProjectId(), leaseRate.getRate());
		}

		Map<String, BigDecimal> occupancyRateMap = new HashMap<String, BigDecimal>();
		for (KanbanRateDto occupancyRate : occupancyRateList) {
			occupancyRateMap.put(occupancyRate.getProjectId(), occupancyRate.getRate());
		}

		Map<String, BigDecimal> voucherRateMap = new HashMap<String, BigDecimal>();
		for (KanbanRateDto voucherRate : voucherRateList) {
			voucherRateMap.put(voucherRate.getProjectId(), voucherRate.getRate());
		}

		Map<String, BigDecimal> leaseCycleMap = new HashMap<String, BigDecimal>();
		for (KanbanRateDto leaseCycle : leaseCycleList) {
			leaseCycleMap.put(leaseCycle.getProjectId(), leaseCycle.getRate());
		}
		
		List<KanbanSummary> newSummaryEntitys = new ArrayList<KanbanSummary>();
		// 获取所有项目列表
		List<ProjectDto> projectList = projectLogic.getAllProjectList();
		for (ProjectDto projectDto : projectList) {
			try {
				KanbanSummary summaryEntity = new KanbanSummary();
				summaryEntity.setKanbanSummaryBid(KeyGenUtils.genKey());
				summaryEntity.setCreaterId(SysConstant.ADMINID);
				summaryEntity.setCreateTime(new Date());
				summaryEntity.setStartDate(sdf.parse(startDateStr));
				summaryEntity.setEndDate(sdf.parse(endDateStr));
				summaryEntity.setIsDel((byte) 0);
				summaryEntity.setProjectId(projectDto.getId());
				summaryEntity.setCycleType((byte)type);
				
				if (ykDealRateMap.get(summaryEntity.getProjectId()) != null) {
					summaryEntity.setYkDealRate(ykDealRateMap.get(summaryEntity.getProjectId()));
				}

				if (leaseRateMap.get(summaryEntity.getProjectId()) != null) {
					summaryEntity.setLeaseRate(leaseRateMap.get(summaryEntity.getProjectId()));
				}

				if (occupancyRateMap.get(summaryEntity.getProjectId()) != null) {
					summaryEntity.setOccupancyRate(occupancyRateMap.get(summaryEntity.getProjectId()));
				}

				if (voucherRateMap.get(summaryEntity.getProjectId()) != null) {
					summaryEntity.setVoucherRate(voucherRateMap.get(summaryEntity.getProjectId()));
				}

				if (leaseCycleMap.get(summaryEntity.getProjectId()) != null) {
					summaryEntity.setLeaseCycle(leaseCycleMap.get(summaryEntity.getProjectId()));
				} else {
					summaryEntity.setLeaseCycle(new BigDecimal(-1));
				}

				
				summaryEntity.setEndDate(sdf.parse(endDateStr));
				newSummaryEntitys.add(summaryEntity);
			} catch (ParseException e) {
				LOGGER.error("======生成startDate = "+startDateStr+";endDate="+endDateStr+"的"+CycleTypeEnum.getByCode(type)+";失败！！！！！",e);
			}
		}
			
		return this.summaryService.saveEntitys(newSummaryEntitys);

	}

	
	/**
	 * 保存或更新目标看板核心数据
	 * @author tianxf9
	 * @param currentTime
	 */
	public int taskKanbanSummary(Date currentTime) {
		
		int rows = 0;

		try {
			
			for(int type=1;type<=4;type++) {
				
				Date startDate = DateTool.getStartDate(currentTime, type);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String startDateStr = sdf.format(startDate);
				String endDateStr = sdf.format(currentTime);
				LOGGER.info("======删除startDate = "+startDateStr+"的"+CycleTypeEnum.getByCode(type)+"(核心数据)=======================");
				rows = this.summaryService.updateEntitysByConditions(startDateStr, type);
				LOGGER.info("======生成startDate = "+startDateStr+";endDate="+endDateStr+"的"+CycleTypeEnum.getByCode(type)+"(核心数据)=======================");
				this.saveOrUpdateSummaryList(startDateStr,endDateStr,type);
			}
			
		} catch (ParseException e) {
			LOGGER.error("保存或更新失败",e);
		}
		
		return rows;
	}
	
	/**
	 * 查询目标看板和核心数据
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param cycleType
	 * @return
	 */
	public List<SummaryShowDto> getSummaryShowDtos(KanbanQueryDto queryDto) {
		
		try {
			
			//根据当前登录人和登录城市查询项目
			Map<String,String> projectNameMap = new HashMap<String,String>();
			if(queryDto.getProjectId()==null) {
				List<ProjectDto> projectList = this.projectLogic.getProjectListByUser(queryDto.getUserId(), queryDto.getCityId());
				List<String> projectIdList = new ArrayList<String>();
			    
			    for(ProjectDto projectDto:projectList) {
			    	projectNameMap.put(projectDto.getId(), projectDto.getName());
			    	projectIdList.add(projectDto.getId());
			    }
			    queryDto.setProjectId(projectIdList);
			}else {
				//queryDto projectIds要么为空，要么就有一个值
				String projectId = queryDto.getProjectId().get(0);
				ProjectDto projectDto = this.projectLogic.getProjectDtoById(projectId);
				projectNameMap.put(projectId, projectDto.getName());
			}
		    
			List<KanbanSummary> summaryEntitys = this.summaryService.getEntitysByConditions(queryDto);
			List<KanbanGoalDto> kanbanGoals = new ArrayList<KanbanGoalDto>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date sDate = sdf.parse(queryDto.getStartDate());
			//周报或月报都去月目标值
			if(queryDto.getCycleType()==CycleTypeEnum.WEEK.getCode()) {
				Date mfDate = DateTool.getStartDate(sDate, 2);
				kanbanGoals = kanbanGoalLogic.listGoals(queryDto.getCityId(), mfDate, CycleTypeEnum.MONTH.getCode());
			}else {
				kanbanGoals = kanbanGoalLogic.listGoals(queryDto.getCityId(), sDate, queryDto.getCycleType().intValue());
			}
			
			Map<String,KanbanGoalDto> kanbanGoalMap = new HashMap<String,KanbanGoalDto>();
			for(KanbanGoalDto kanbanGoal:kanbanGoals) {
				kanbanGoalMap.put(kanbanGoal.getProjectId(), kanbanGoal);
			}
			
			List<SummaryShowDto> summaryShowDtos = new ArrayList<SummaryShowDto>();
			for(KanbanSummary summaryEntity:summaryEntitys) {
				SummaryShowDto summaryShowDto = new SummaryShowDto();
				if(summaryEntity.getLeaseCycle()!=null) {
					summaryShowDto.setLeaseCycle(summaryEntity.getLeaseCycle().toString());
				}
				
				if(summaryEntity.getLeaseRate()!=null) {
					summaryShowDto.setLeaseRate(summaryEntity.getLeaseRate().toString());
				}
				
				if(summaryEntity.getOccupancyRate()!=null) {
					summaryShowDto.setOccupancyRate(summaryEntity.getOccupancyRate().toString());
				}
				
				summaryShowDto.setProjectId(summaryEntity.getProjectId());
				summaryShowDto.setProjectName(projectNameMap.get(summaryEntity.getProjectId()));
				if(summaryEntity.getVoucherRate()!=null) {
					summaryShowDto.setVoucherRateActual(summaryEntity.getVoucherRate().toString());
				}

				if(summaryEntity.getYkDealRate()!=null) {
					summaryShowDto.setYkDealRate(summaryEntity.getYkDealRate().toString());
				}
				
				//目标
				KanbanGoalDto kanbanGoal = kanbanGoalMap.get(summaryEntity.getProjectId());
				if(kanbanGoal!=null) {
					summaryShowDto.setRentalCycle(kanbanGoal.getRentalCycle());
					summaryShowDto.setRentalRate(kanbanGoal.getRentalRate());
					summaryShowDto.setVoucherRateGoal(kanbanGoal.getVoucherRate());
					summaryShowDto.setYuekanGjRate(kanbanGoal.getYuekanGjRate());
				}
				
				summaryShowDtos.add(summaryShowDto);
			}
			
			return summaryShowDtos;
			
		} catch (ParseException e) {
			LOGGER.error("查询目标看板失败",e);
		}
		return null;
	}

}
