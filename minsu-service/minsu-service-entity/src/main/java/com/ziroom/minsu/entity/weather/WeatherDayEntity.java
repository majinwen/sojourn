package com.ziroom.minsu.entity.weather;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
/**
 * 
 * @author jixd on 2016/01/12
 * 
 */
public class WeatherDayEntity extends BaseEntity{
    
	private static final long serialVersionUID = -3778503773000161181L;
	
	private Integer id;
	
    private String fid;
    /*城市名称*/
    private String cityName;
    /*日期*/
    private Date date;
    /*星期*/
    private String week;
    /*农历日期*/
    private String nongli;
    /*天气详情  json串*/
    private String weatherInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week == null ? null : week.trim();
    }

    public String getNongli() {
        return nongli;
    }

    public void setNongli(String nongli) {
        this.nongli = nongli == null ? null : nongli.trim();
    }

    public String getWeatherInfo() {
        return weatherInfo;
    }

    public void setWeatherInfo(String weatherInfo) {
        this.weatherInfo = weatherInfo == null ? null : weatherInfo.trim();
    }
}