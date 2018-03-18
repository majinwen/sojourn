package com.zra.common.dto.vacancyreport;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by homelink on 2016/12/9.
 */
public class VacancyReportResultDto<T> {

    // 项目名称
    private String projectName;

    // 库存总量
    private Integer totalRoomCount;

    // 空置率
    private BigDecimal vacantRate;

    // 空置总数
    private Integer totalVacantCount;

    // 待租中总数
    private Integer toRentCount;

    // 配置中总数
    private Integer configuringCount;

    // 已出租总数
    private Integer rentedCount;

    // 不可用/锁定总数
    private Integer unavailableCount;

    // 空置总览
    private List<T> vacancySummaryList;

    // 库存详情
    private  List<VacancyReportDto> vacancyReportDtos;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getTotalRoomCount() {
        return totalRoomCount;
    }

    public void setTotalRoomCount(Integer totalRoomCount) {
        this.totalRoomCount = totalRoomCount;
    }

    public BigDecimal getVacantRate() {
        return vacantRate;
    }

    public void setVacantRate(BigDecimal vacantRate) {
        this.vacantRate = vacantRate;
    }

    public Integer getTotalVacantCount() {
        return totalVacantCount;
    }

    public void setTotalVacantCount(Integer totalVacantCount) {
        this.totalVacantCount = totalVacantCount;
    }

    public Integer getToRentCount() {
        return toRentCount;
    }

    public void setToRentCount(Integer toRentCount) {
        this.toRentCount = toRentCount;
    }

    public Integer getConfiguringCount() {
        return configuringCount;
    }

    public void setConfiguringCount(Integer configuringCount) {
        this.configuringCount = configuringCount;
    }

    public Integer getRentedCount() {
        return rentedCount;
    }

    public void setRentedCount(Integer rentedCount) {
        this.rentedCount = rentedCount;
    }

    public Integer getUnavailableCount() {
        return unavailableCount;
    }

    public void setUnavailableCount(Integer unavailableCount) {
        this.unavailableCount = unavailableCount;
    }

    public List<T> getVacancySummaryList() {
        return vacancySummaryList;
    }

    public void setVacancySummaryList(List<T> vacancySummaryList) {
        this.vacancySummaryList = vacancySummaryList;
    }

    public List<VacancyReportDto> getVacancyReportDtos() {
        return vacancyReportDtos;
    }

    public void setVacancyReportDtos(List<VacancyReportDto> vacancyReportDtos) {
        this.vacancyReportDtos = vacancyReportDtos;
    }
}
