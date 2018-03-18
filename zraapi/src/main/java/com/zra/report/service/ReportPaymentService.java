package com.zra.report.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zra.report.dao.ReportPaymentDao;
import com.zra.report.entity.ReportPaymenTaskEntity;
import com.zra.report.entity.ReportPaymentEntity;
import com.zra.report.entity.dto.ReportPaymentDto;
import com.zra.report.entity.dto.ReportPaymentForKBDto;

/**
 * 报表回款数据服务
 * @author huangy168@ziroom.com
 * @Date 2016年10月28日
 * @Time 下午5:11:19
 */
@Service
public class ReportPaymentService {

	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(ReportPaymentService.class);
	
	@Autowired
	private ReportPaymentDao reportPaymentDao;
	
	/**
	 * 获取报表回款数据
	 * @param reportPaymentDto
	 * @return 
	 */
	public List<ReportPaymentEntity> getReportPaymentByRecordDate(ReportPaymentDto reportPaymentDto) {
		LOGGER.info("Begin to get payment report.");
		return reportPaymentDao.selectReportPaymentByRecordDate(reportPaymentDto.getProjectId(), reportPaymentDto.getRecordDate());
	}
	
	/**
	 * 获取应回款单量（本月）
	 * @param projectID
	 * @param recordDate
	 * @return
	 */
	public List<ReportPaymenTaskEntity> getPaymentCount(String projectId, String recordDate) {
		LOGGER.info("Begin to get payment count.");
		return reportPaymentDao.selectPaymentCount(projectId, recordDate);
	}
	
	/**
	 * 时间段内应收回款单量
	 * @author tianxf9
	 * @param projectId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<ReportPaymenTaskEntity> getPaymentCountRange(String projectId, String startDate,String endDate) {
		LOGGER.info("Begin to get payment count.");
		return reportPaymentDao.selectPaymentCountRange(projectId, startDate,endDate);
	}
	
	/**
	 * 获取实际回款单量
	 * @param projectId
	 * @param recordDate
	 * @return
	 */
	public List<ReportPaymenTaskEntity> getVoucherCount(String projectId, String recordDate) {
		LOGGER.info("Begin to get voucher count.");
		return reportPaymentDao.selectVoucherCount(projectId, recordDate);
	}
	
	/**
	 * 获取及时回款单量
	 * @param projectId
	 * @param recordDate
	 * @return
	 */
	public List<ReportPaymenTaskEntity> getIntimeVoucherCount(String projectId, String startDate,String endDate) {
		LOGGER.info("Begin to get intime voucher count.");
		return reportPaymentDao.selectIntimeVoucherCount(projectId, startDate,endDate);
	}
	
	/**
	 * 获取应收回款金额（本月）
	 * @param projectId
	 * @param recordDate
	 * @return
	 */
	public List<ReportPaymenTaskEntity> getPaymentAmount(String projectId, String recordDate) {
		LOGGER.info("Begin to get payment amount");
		return reportPaymentDao.selectPaymentAmount(projectId, recordDate);
	}
	
	/**
	 * 获取应收回款金额（时间段内）
	 * @author tianxf9
	 * @param projectId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<ReportPaymenTaskEntity> getPaymentAmountRange(String projectId, String startDate,String endDate) {
		LOGGER.info("Begin to get payment amount");
		return reportPaymentDao.selectPaymentAmountRange(projectId, startDate,endDate);
	} 
	
	/**
	 * 获取实际回款金额
	 * @param projectId
	 * @param recordDate
	 * @return
	 */
	public List<ReportPaymenTaskEntity> getVoucherAmount(String projectId, String recordDate) {
		LOGGER.info("Begin to get voucher amount");
		return reportPaymentDao.selectVoucherAmount(projectId, recordDate);
	}
	
	/**
	 * 获取及时回款金额
	 * @param projectId
	 * @param recordDate
	 * @return
	 */
	public List<ReportPaymenTaskEntity> getIntimeVoucherAmount(String projectId, String startDate,String endDate) {
		LOGGER.info("Begin to get intime voucher amount");
		return reportPaymentDao.selectIntimeVoucherAmount(projectId, startDate,endDate);
	}
	
	/**
	 * 新增回款报表数据
	 * @param entity
	 * @return
	 */
	public int insertReportPayment(ReportPaymentEntity entity) {
		LOGGER.info("Begin to add new payment report.");
		return reportPaymentDao.insertReportPayment(entity);
	}
	
	/**
	 * 更新回款报表数据
	 * @param entity
	 * @return
	 */
	public int updateReportPayment(ReportPaymentEntity entity) {
		LOGGER.info("Begin to update payment report.");
		return reportPaymentDao.updateReportPayment(entity);
	}
	
	/**
	 * 删除记录依据记录时间
	 * @author tianxf9
	 * @param recordDate
	 * @return
	 */
	public int delEntitysByRecordDate(String recordDate,Date delTime,String delId) {
		return reportPaymentDao.delEntitysByRecordDate(recordDate,delId,delTime);
	}
	
	
	/**
	 * 查询时间范围内应收款单量(目标看板)
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<ReportPaymentForKBDto> getPaymentCountForKB(String startDate,String endDate) {
		return reportPaymentDao.getPaymentCountForKB(startDate, endDate);
	}
	
	/**
	 * 查询时间范围内时间回款(目标看板)
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<ReportPaymentForKBDto> getVoucherCountForKB(String startDate,String endDate) {
		return reportPaymentDao.getVoucherCountForKB(startDate, endDate);
	}
}
