package com.zra.report.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zra.report.entity.ReportOverviewEntity;
import com.zra.report.entity.dto.ReportOverviewDto;
/**
 * 自如寓业务总览实体dao.
 * @author wangws21
 * @date 2017年5月22日
 */
public interface ReportOverviewDao {

    /**
     * 获取时间段内合同签约的数量.
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return List<ReportOverviewDto>
     */
    List<ReportOverviewDto> getSignCount(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 获取时间段内合同签约的数量.
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return List<ReportOverviewDto>
     */
    List<ReportOverviewDto> getSurrenderCount(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 获取时间段内合同签约的数量.
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return List<ReportOverviewDto>
     */
    List<ReportOverviewDto> getTotalPayment(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 获取时间段内合同签约的数量.
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return List<ReportOverviewDto>
     */
    List<ReportOverviewDto> getTotalPay(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 保存/添加 总览数据实体.
     */
    int insert(ReportOverviewEntity record);


    /**
     * 按时间查询总览数据list.
     */
    List<ReportOverviewEntity> getReportOverviewListByDateStr(String dataStr);

}