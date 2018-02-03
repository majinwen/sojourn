package com.ziroom.minsu.services.search.dto;

import com.ziroom.minsu.services.search.vo.SubwayVo;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>位置条件搜索-地铁出参</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年8月23日
 * @since 1.0
 * @version 1.0
 */
public class LocationConditionSubwaysResult {

	/**
	 * 地铁线路名
	 */
	String name;

	String lineFid;

	/**
	 * 地铁站
	 */
	List<SubwayVo> stations = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SubwayVo> getStations() {
		return stations;
	}

	public void setStations(List<SubwayVo> stations) {
		this.stations = stations;
	}


	public String getLineFid() {
		return lineFid;
	}

	public void setLineFid(String lineFid) {
		this.lineFid = lineFid;
	}
}
