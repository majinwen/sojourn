package com.ziroom.minsu.services.basedata.api.inner;

public interface SubwayLineService {

	/**
	 * 按条件分页查询地铁线路
	 * 
	 * @param paramJson
	 * @return
	 */
	public String findSubwayLinePage(String paramJson);
	
	
	/**
	 * 查询地铁线路、站点、出口
	 * @param paramJson
	 * @return
	 */
	public String findSubwayInfo(String paramJson);
	
	
	/**
	 * 保存地铁线路
	 * @param paramJson
	 * @return
	 */
	public String saveSubwayLine(String paramJson);
	
	
	/**
	 * 修改地铁线路
	 * @param paramJson
	 * @return
	 */
	public String updateSubwayLine(String paramJson);

}
