package com.zra.report.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zra.house.entity.dto.PageDto;
import com.zra.kanban.entity.dto.KanbanRateDto;
import com.zra.report.dao.ReportStockDao;
import com.zra.report.entity.ReportStockEntity;
import com.zra.report.entity.dto.BaseReportDto;
import com.zra.report.entity.dto.ReportStockDto;

/**
 * 报表库存数据服务
 * @author huangy168@ziroom.com
 * @Date 2016年10月28日
 * @Time 下午5:11:19
 */
@Service
public class ReportStockService {

	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(ReportStockService.class);
	
	@Autowired
	private ReportStockDao reportStockDao;
	
	/**
	 * 获取报表库存数据总量
	 * @param reportStockDto
	 * @return
	 */
	public int getReportStockCount(ReportStockDto reportStockDto) {
		LOGGER.info("Begin to get stock report count.");
		return reportStockDao.selectReportStockCount(reportStockDto.getProjectId(), reportStockDto.getBeginTime(), reportStockDto.getEndTime());
	}
	
	/**
	 * 获取报表库存记录时间列表--支持分页
	 * @param projectId
	 * @param page
	 * @return
	 */
	public List<String> getReportStockTimeSpanByPage(ReportStockDto reportStockDto, PageDto page) {
		LOGGER.info("Begin to get stock report time span.");
		return reportStockDao.selectReportStockTimeSpanByPage(reportStockDto.getProjectId(), reportStockDto.getBeginTime(), reportStockDto.getEndTime(), page);
	}
	
	/**
	 * 获取报表库存数据
	 * @param reportBoDto
	 * @return 
	 */
	public List<ReportStockEntity> getReportStockList(ReportStockDto reportStockDto) {
		LOGGER.info("Begin to get stock report list.");
		return reportStockDao.selectReportStockList(reportStockDto.getProjectId(), reportStockDto.getRecordDate());
	}
	
	/**
	 * 根据时间范围查询报表库存数据
	 * @param reportBoDto
	 * @return 
	 */
	public List<ReportStockEntity> getReportStockListByTimeSpan(ReportStockDto reportStockDto) {
		LOGGER.info("Begin to get stock report list.");
		return reportStockDao.selectReportStockListByTimeSpan(reportStockDto.getProjectId(), reportStockDto.getBeginTime(), reportStockDto.getEndTime());
	}
	
	/**
	 * 获取库存数
	 * @author tianxf9
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getStockCount(String projectId) {
		return this.reportStockDao.getStockCount(projectId);
	}
	
	
	/**
	 * 获取可出租库存数
	 * @author tianxf9
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getRentableCount(String projectId) {
		return this.reportStockDao.getRentableCount(projectId);
	}
	
	/**
	 * 获取已出租数
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getLeasedCount(String recordDate,String projectId) {
		return this.reportStockDao.getLeasedCount(recordDate,projectId);
	}
	
	/**
	 * 获取短租出租数
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getShortLeasedCount(String recordDate,String projectId) {
		return this.reportStockDao.getShortLeasedCount(recordDate, projectId);
	}
	
	/**
	 * 获取剩余库存(配置中,待租中)
	 * @author tianxf9
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getLeaveStockCount(String recordDate,String projectId) {
		return this.reportStockDao.getLeaveStockCount(recordDate,projectId);
	}
	
	/**
	 * 获得退租量
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getQuitCount(String recordDate,String projectId) {
		return this.reportStockDao.getQuitCount(recordDate, projectId);
	}
	
	/**
	 * 保存运营报表库存数据
	 * @author tianxf9
	 * @param entitys
	 * @return
	 */
	public int saveReportStockEntitys(List<ReportStockEntity> entitys) {
		
		int i = 0;
		for(ReportStockEntity entity:entitys) {
			i = i + this.reportStockDao.insertReportStock(entity);
		}
		return i;
	}
	
	/**
	 * 删除记录依据记录时间
	 * @author tianxf9
	 * @param recordDate
	 * @return
	 */
	public int delEntitysByRecordDate(String recordDate,Date delTime,String delId) {
		return this.reportStockDao.delEntitysByRecordDate(recordDate,delId,delTime);
	}
	
	
	/**
	 * 获取所有项目出租率（目标看板）
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<KanbanRateDto> getLeasedRateForKB(String startDate,String endDate) {
		return this.reportStockDao.getLeasedRate(startDate, endDate);
	}
	
	/**
	 * 获取所有项目的日均入住率
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param count
	 * @return
	 */
	public List<KanbanRateDto> getOccupancyRateAvge(String startDate,String endDate,int count) {
		return this.reportStockDao.getOccupancyRateAvge(startDate, endDate, count);
	}
}
