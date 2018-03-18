package com.zra.report.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zra.common.utils.SysConstant;
import com.zra.house.entity.dto.PageDto;
import com.zra.report.dao.ReportRenewDao;
import com.zra.report.entity.ReportRenewEntity;
import com.zra.report.entity.dto.BaseReportDto;
import com.zra.report.entity.dto.ReportRenewDto;

/**
 * 报表续约数据服务
 * @author huangy168@ziroom.com
 * @Date 2016年10月28日
 * @Time 下午5:11:19
 */
@Service
public class ReportRenewService {

	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(ReportRenewService.class);
	
	@Autowired
	private ReportRenewDao reportRenewDao;
	
	/**
	 * 获取报表续约数据总量
	 * @param reportRenewDto
	 * @return
	 */
	public int getReportRenewCount(ReportRenewDto reportRenewDto) {
		LOGGER.info("Begin to get renew report count.");
		return reportRenewDao.selectReportRenewCount(reportRenewDto.getProjectId(), reportRenewDto.getBeginTime(), reportRenewDto.getEndTime());
	}
	
	/**
	 * 获取报表续约记录时间列表--支持分页
	 * @param projectId
	 * @param page
	 * @return
	 */
	public List<String> getReportRenewTimeSpanByPage(ReportRenewDto reportRenewDto, PageDto page) {
		LOGGER.info("Begin to get renew report time span.");
		return reportRenewDao.selectReportRenewTimeSpanByPage(reportRenewDto.getProjectId(), reportRenewDto.getBeginTime(), reportRenewDto.getEndTime(), page);
	}
	
	/**
	 * 获取报表续约数据
	 * @param reportRenewDto
	 * @return 
	 */
	public List<ReportRenewEntity> getReportRenewList(ReportRenewDto reportRenewDto) {
		LOGGER.info("Begin to get renew report list.");
		return reportRenewDao.selectReportRenewList(reportRenewDto.getProjectId(), reportRenewDto.getRecordDate());
	}
	
	/**
	 * 根据时间范围获取报表续约数据
	 * @param reportRenewDto
	 * @return 
	 */
	public List<ReportRenewEntity> getReportRenewListByTimeSpan(ReportRenewDto reportRenewDto) {
		LOGGER.info("Begin to get renew report list.");
		return reportRenewDao.selectReportRenewListByTimeSpan(reportRenewDto.getProjectId(), reportRenewDto.getBeginTime(), reportRenewDto.getEndTime());
	}
	
	/**
	 * 获得到期房源数量
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getExpireRoomCount(String startDate,String endDate,String projectId) {
		return this.reportRenewDao.getExpireRoomCount(startDate, endDate, projectId);
	}
	
	/**
	 * 获取长租续约量
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getRenewLongCount(String startDate,String endDate,String projectId) {
		return this.reportRenewDao.getRenewLongCount(startDate, endDate, projectId);
	}
	
	/**
	 * 获取短租（1-3个月）续约量
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getRenewShort1Count(String startDate,String endDate,String projectId) {
		return this.reportRenewDao.getRenewShort1Count(startDate, endDate, projectId);
	}
	
	/**
	 * 获取短租（4-6个月）续约量
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getRenewShort2Count(String startDate,String endDate,String projectId) {
		return this.reportRenewDao.getRenewShort2Count(startDate, endDate, projectId);
	}
	
	/**
	 * 保存运营报表中的续约数据
	 * @author tianxf9
	 * @param entitys
	 * @return
	 */
	public int insertRenewEntitys(List<ReportRenewEntity> entitys) {
		int i = 0;
		for(ReportRenewEntity entity:entitys) {
			i = i + this.reportRenewDao.insertReportRenew(entity);
		}
		return i;
	}
	
	/**
	 * 删除续约量记录
	 * @author tianxf9
	 * @param recordDate
	 * @return
	 */
	public int delRenewEntitysByRecordDate(String recordDate) {
		recordDate = recordDate + "%";
		return this.reportRenewDao.delRenewEntityByRecordDate(recordDate, SysConstant.ADMINID, new Date());
	}

}
