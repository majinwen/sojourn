package com.zra.report.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zra.report.dao.ReportOverviewDao;
import com.zra.report.entity.ReportOverviewEntity;
import com.zra.report.entity.dto.ReportOverviewDto;

/**
 * 自如寓业务总览实体服务.
 * @author wangws21 
 * @date 2017年5月22日
 */
@Service
public class ReportOverviewService {

	@Autowired
	private ReportOverviewDao reportOverviewDao;
	
	/**
     * 获取时间段内合同签约的数量.
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return List<ReportOverviewDto>
     */
    public List<ReportOverviewDto> getSignCount(String startDate, String endDate){
        return this.reportOverviewDao.getSignCount(startDate, endDate);
    }

    /**
     * 获取时间段内合同签约的数量.
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return List<ReportOverviewDto>
     */
    public List<ReportOverviewDto> getSurrenderCount(String startDate,  String endDate){
        return this.reportOverviewDao.getSurrenderCount(startDate, endDate);
    }

    /**
     * 获取时间段内合同签约的数量.
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return List<ReportOverviewDto>
     */
    public List<ReportOverviewDto> getTotalPayment(String startDate, String endDate){
        return this.reportOverviewDao.getTotalPayment(startDate, endDate);
    }

    /**
     * 获取时间段内合同签约的数量.
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return List<ReportOverviewDto>
     */
    public List<ReportOverviewDto> getTotalPay(String startDate, String endDate){
        return this.reportOverviewDao.getTotalPay(startDate, endDate);
    }
	
	/**
     * 保存/添加 总览数据实体.
     */
    public int insert(ReportOverviewEntity record){
        return reportOverviewDao.insert(record);
    }

    /**
     * 按时间查询总览数据list.
     */
    public List<ReportOverviewEntity> getReportOverviewListByDateStr(String dataStr){
        return this.reportOverviewDao.getReportOverviewListByDateStr(dataStr);
    }
}
