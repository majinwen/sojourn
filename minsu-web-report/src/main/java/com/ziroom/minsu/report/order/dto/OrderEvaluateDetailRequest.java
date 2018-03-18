package com.ziroom.minsu.report.order.dto;

import com.ziroom.minsu.report.basedata.dto.CityRegionRequest;

import java.util.Date;
import java.util.List;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/3/3.
 * @version 1.0
 * @since 1.0
 */
public class OrderEvaluateDetailRequest extends CityRegionRequest {

    private static final long serialVersionUID = 323422341446703L;

    /**
     * 开始时间
     */
    private String starTimeStr;

    /**
     * 结束时间
     */
    private String endTimeStr;


    /**
     * 开始时间
     */
    private Date starTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 城市code
     */
    private List<String> cityList;

    public String getStarTimeStr() {
        return starTimeStr;
    }

    public void setStarTimeStr(String starTimeStr) {
        this.starTimeStr = starTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public Date getStarTime() {
        return starTime;
    }

    public void setStarTime(Date starTime) {
        this.starTime = starTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<String> getCityList() {
        return cityList;
    }

    public void setCityList(List<String> cityList) {
        this.cityList = cityList;
    }
}
