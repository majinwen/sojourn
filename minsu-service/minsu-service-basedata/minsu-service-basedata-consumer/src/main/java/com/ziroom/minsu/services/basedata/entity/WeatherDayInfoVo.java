package com.ziroom.minsu.services.basedata.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 天气信息详情
 * @author jixd on 2016年4月13日
 * @version 1.0
 * @since 1.0
 */
public class WeatherDayInfoVo extends BaseEntity{
	
	private static final long serialVersionUID = -569939261038971957L;
	/*城市名称*/
	private String cityName;
	/*当天日期*/
	private Date date;
	/*当天周几*/
	private String week;
	/*农历日期*/
	private String nongli;
	/*早上天气*/
	private IntervalDay dawn;
	/*白天天气*/
	private IntervalDay day;
	/*晚上天气*/
	private IntervalDay night;
	
	
	
	public String getCityName() {
		return cityName;
	}



	public void setCityName(String cityName) {
		this.cityName = cityName;
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
		this.week = week;
	}



	public String getNongli() {
		return nongli;
	}



	public void setNongli(String nongli) {
		this.nongli = nongli;
	}



	public IntervalDay getDawn() {
		return dawn;
	}



	public void setDawn(IntervalDay dawn) {
		this.dawn = dawn;
	}



	public IntervalDay getDay() {
		return day;
	}



	public void setDay(IntervalDay day) {
		this.day = day;
	}



	public IntervalDay getNight() {
		return night;
	}



	public void setNight(IntervalDay night) {
		this.night = night;
	}



	/**
	 * 时段天气
	 * @author homelink
	 */
	public static final class IntervalDay{
		/*天气情况*/
		private String weather;
		/*最低温度*/
		private String lowTemperature;
		/*最高温度*/
		private String highTemperature;
		/*风向*/
		private String wind;
		/*风向强弱*/
		private String windStrong;
		public String getWeather() {
			return weather;
		}
		public void setWeather(String weather) {
			this.weather = weather;
		}
		public String getLowTemperature() {
			return lowTemperature;
		}
		public void setLowTemperature(String lowTemperature) {
			this.lowTemperature = lowTemperature;
		}
		public String getHighTemperature() {
			return highTemperature;
		}
		public void setHighTemperature(String highTemperature) {
			this.highTemperature = highTemperature;
		}
		public String getWind() {
			return wind;
		}
		public void setWind(String wind) {
			this.wind = wind;
		}
		public String getWindStrong() {
			return windStrong;
		}
		public void setWindStrong(String windStrong) {
			this.windStrong = windStrong;
		}
		
	}
}
