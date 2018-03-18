package com.zra.report.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zra.common.utils.SysConstant;
import com.zra.house.entity.dto.PageDto;
import com.zra.report.dao.ReportBoDao;
import com.zra.report.entity.ReportBoEntity;
import com.zra.report.entity.dto.BaseReportDto;
import com.zra.report.entity.dto.ReportBoDto;

/**
 * 报表商机数据服务
 * @author huangy168@ziroom.com
 * @Date 2016年10月28日
 * @Time 下午5:11:19
 */
@Service
public class ReportBoService {

	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(ReportBoService.class);
	
	@Autowired
	private ReportBoDao reportBoDao;
	
	/**
	 * 获取报表商机数据总量
	 * @param reportBoDto
	 * @return
	 */
	public int getReportBoCount(ReportBoDto reportBoDto) {
		LOGGER.info("Begin to get bo report count.");
		return reportBoDao.selectReportBoCount(reportBoDto.getProjectId(), reportBoDto.getBeginTime(), reportBoDto.getEndTime());
	}
	
	/**
	 * 获取报表商机记录时间列表--支持分页
	 * @param projectId
	 * @param page
	 * @return
	 */
	public List<String> getReportBoTimeSpanByPage(ReportBoDto reportBoDto, PageDto page) {
		LOGGER.info("Begin to get bo report time span by page.");
		return reportBoDao.selectReportBoTimeSpanByPage(reportBoDto.getProjectId(), reportBoDto.getBeginTime(), reportBoDto.getEndTime(), page);
	}
	
	/**
	 * 获取报表商机数据
	 * @param reportBoDto
	 * @return 
	 */
	public List<ReportBoEntity> getReportBoList(ReportBoDto reportBoDto) {
		LOGGER.info("Begin to get bo report list.");
		return reportBoDao.selectReportBoList(reportBoDto.getProjectId(), reportBoDto.getRecordDate());
	}
	
	/**
	 * 根据时间范围查询表报商机数据
	 * @param reportBoDto
	 * @return
	 */
	public List<ReportBoEntity> getReportBoListByTimeSpan(ReportBoDto reportBoDto) {
		LOGGER.info("Begin to get bo report list.");
		return reportBoDao.selectReportBoListByTimeSpan(reportBoDto.getProjectId(), reportBoDto.getBeginTime(), reportBoDto.getEndTime());
	}
	
	/**
	 * 根据条件得到新增商机数量
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getNewBusinessCount(String startDate,String endDate,String projectId) {
		return reportBoDao.getNewBusinessCount(startDate, endDate, projectId);
	}
	
	public List<BaseReportDto> getDealBusinessCount(String startDate,String endDate,String projectId) {
		return reportBoDao.getDealBusinessCount(startDate, endDate, projectId);
	}
	
	/**
	 * 根据条件获得带看量
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getSeeBusinessCount(String startDate,String endDate,String projectId) {
		return reportBoDao.getSeeBusinessCount(startDate, endDate, projectId);
	}
	
	/**
	 * 根据条件获取新增客源量
	 * @author wangws21 2017-1-18
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getBusinessKYLCount(String startDate,String endDate,String projectId) {
	    return reportBoDao.getBusinessKYLCount(startDate, endDate, projectId);
	}
	
	/**
	 * 根据条件获得新签量
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getSignCount(String startDate,String endDate,String projectId) {
		return reportBoDao.getSignCount(startDate, endDate, projectId);
	}
	
	/**
	 * 根据条件获取实际出房价
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getActualPrice(String startDate,String endDate,String projectId) {
		return reportBoDao.getActualPrice(startDate, endDate, projectId);
	}
	
	/**
	 * 保存运营报表商机数据
	 * @author tianxf9
	 * @param entitys
	 * @return
	 */
	public int saveReportBoEntitys(List<ReportBoEntity> entitys) {
		
		int i=0;
		for(ReportBoEntity entity:entitys) {
			i = i + this.reportBoDao.insertReportBo(entity);
		}
		
		return i;
	}
	
	/**
	 * 根据记录时间删除商机数据
	 * @author tianxf9
	 * @param recordDate
	 * @return
	 */
	public int delByRecordDate(String recordDate) {
		recordDate = recordDate + "%";
		return this.reportBoDao.delByRecordDate(recordDate, new Date(), SysConstant.ADMINID);
	}
}
