package com.ziroom.minsu.services.search.vo;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>
 * 地铁站点vo
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年8月23日
 * @since 1.0
 * @version 1.0
 */
public class SubwayStationVo extends BaseEntity {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -6606023801006776518L;

	/**
	 * 地铁线路fid
	 */
	String lineFid;

	/**
	 * 地铁线路名称
	 */
	String lineName;

	/**
	 * 地铁线路排序
	 */
	Integer lineSort;

	/**
	 * 站点fid
	 */
	String stationFid;

	/**
	 * 站点名称
	 */
	String stationName;

	/**
	 * 站点排序
	 */
	Integer stationSort;

	public String getLineFid() {
		return lineFid;
	}

	public void setLineFid(String lineFid) {
		this.lineFid = lineFid;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public Integer getLineSort() {
		return lineSort;
	}

	public void setLineSort(Integer lineSort) {
		this.lineSort = lineSort;
	}

	public String getStationFid() {
		return stationFid;
	}

	public void setStationFid(String stationFid) {
		this.stationFid = stationFid;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public Integer getStationSort() {
		return stationSort;
	}

	public void setStationSort(Integer stationSort) {
		this.stationSort = stationSort;
	}

}
