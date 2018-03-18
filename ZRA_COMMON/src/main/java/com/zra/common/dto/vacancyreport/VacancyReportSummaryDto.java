package com.zra.common.dto.vacancyreport;

import java.math.BigDecimal;
import java.util.List;

/**
 * 空置报表表头页面信息总览
 *
 * @author dongl50@ziroom.com
 * @date 2016/11/30 16:30
 */
public class VacancyReportSummaryDto {

    // 天数类型，所有、0-5天、6-10天、11-15天、15天以上
    private String daysType;
    // 总览->空置房间总数
    private int totalVacantAmount;
    // 天数区间内空置房源总数占比
    private BigDecimal daysTotalVacantRate;
    // 总览->待租中房源总数
    private int totalToRentAmount;
    // 天数区间内待租中房源占比
    private BigDecimal daysToRentVacantRate;
    // 总览->配置中房源总数
    private int totalConfiguringAmount;
    // 天数区间内配置中房源占比
    private BigDecimal daysConfiguringVacantRate;

    public int getTotalVacantAmount() {
        return totalVacantAmount;
    }

    public String getDaysType() {
        return daysType;
    }

    public void setDaysType(String daysType) {
        this.daysType = daysType;
    }

    public void setTotalVacantAmount(int totalVacantAmount) {
        this.totalVacantAmount = totalVacantAmount;
    }

    public int getTotalToRentAmount() {
        return totalToRentAmount;
    }

    public void setTotalToRentAmount(int totalToRentAmount) {
        this.totalToRentAmount = totalToRentAmount;
    }

    public int getTotalConfiguringAmount() {
        return totalConfiguringAmount;
    }

    public void setTotalConfiguringAmount(int totalConfiguringAmount) {
        this.totalConfiguringAmount = totalConfiguringAmount;
    }

    public BigDecimal getDaysTotalVacantRate() {
        return daysTotalVacantRate;
    }

    public void setDaysTotalVacantRate(BigDecimal daysTotalVacantRate) {
        this.daysTotalVacantRate = daysTotalVacantRate;
    }

    public BigDecimal getDaysToRentVacantRate() {
        return daysToRentVacantRate;
    }

    public void setDaysToRentVacantRate(BigDecimal daysToRentVacantRate) {
        this.daysToRentVacantRate = daysToRentVacantRate;
    }

    public BigDecimal getDaysConfiguringVacantRate() {
        return daysConfiguringVacantRate;
    }

    public void setDaysConfiguringVacantRate(BigDecimal daysConfiguringVacantRate) {
        this.daysConfiguringVacantRate = daysConfiguringVacantRate;
    }
}
