package com.ziroom.zrp.service.trading.dto;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年11月01日 18:33
 * @since 1.0
 */
public class LeaseCycleLimitDto {

    private Integer dayMin;

    private Integer dayMax;

    private Integer monthMin;

    private Integer monthMax;

    private Integer yearMin;

    private Integer yearMax;

    public Integer getDayMin() {
        return dayMin;
    }

    public void setDayMin(Integer dayMin) {
        this.dayMin = dayMin;
    }

    public Integer getDayMax() {
        return dayMax;
    }

    public void setDayMax(Integer dayMax) {
        this.dayMax = dayMax;
    }

    public Integer getMonthMin() {
        return monthMin;
    }

    public void setMonthMin(Integer monthMin) {
        this.monthMin = monthMin;
    }

    public Integer getMonthMax() {
        return monthMax;
    }

    public void setMonthMax(Integer monthMax) {
        this.monthMax = monthMax;
    }

    public Integer getYearMin() {
        return yearMin;
    }

    public void setYearMin(Integer yearMin) {
        this.yearMin = yearMin;
    }

    public Integer getYearMax() {
        return yearMax;
    }

    public void setYearMax(Integer yearMax) {
        this.yearMax = yearMax;
    }
}
