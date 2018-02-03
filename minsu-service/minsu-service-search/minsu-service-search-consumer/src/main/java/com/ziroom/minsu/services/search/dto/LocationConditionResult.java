package com.ziroom.minsu.services.search.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;

/**
 * <p>位置条件搜索出参</p>
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
public class LocationConditionResult extends BaseEntity{
	
	
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -4690990545804143827L;

	/**
	 * 行政区
	 */
	List<LocationConditionDistrictsResult> districts = new ArrayList<LocationConditionDistrictsResult>();
	
	/**
	 * 商圈
	 */
	List<String> commercialRegions = new ArrayList<String>();

	/**
	 * 景点
	 */
	List<String> sceneryRegions = new ArrayList<String>();
	
	
	/**
	 * 地铁
	 */
	List<LocationConditionSubwaysResult> subways = new ArrayList<LocationConditionSubwaysResult>();
	


	public List<LocationConditionDistrictsResult> getDistricts() {
		return districts;
	}


	public void setDistricts(List<LocationConditionDistrictsResult> districts) {
		this.districts = districts;
	}


	public List<String> getCommercialRegions() {
		return commercialRegions;
	}


	public void setCommercialRegions(List<String> commercialRegions) {
		this.commercialRegions = commercialRegions;
	}


	public List<String> getSceneryRegions() {
		return sceneryRegions;
	}


	public void setSceneryRegions(List<String> sceneryRegions) {
		this.sceneryRegions = sceneryRegions;
	}

	
	public List<LocationConditionSubwaysResult> getSubways() {
		return subways;
	}


	public void setSubways(List<LocationConditionSubwaysResult> subways) {
		this.subways = subways;
	}



	/**
	 *
	 * @return
     */
	public LocationCondition transToLocationPar(){
		LocationCondition locationCondition = new LocationCondition();
		//商圈
		Map<String,Object>  commercialRegionsMap = new HashMap<>();
		if (!Check.NuNCollection(commercialRegions)){
			commercialRegionsMap.put("list",commercialRegions);
			commercialRegionsMap.put("index",1);
			commercialRegionsMap.put("name","商圈");
		}
		locationCondition.setCommercialRegion(commercialRegionsMap);

		//景点
		Map<String,Object>  sceneryRegionsMap = new HashMap<>();
		if (!Check.NuNCollection(sceneryRegions)){
			sceneryRegionsMap.put("list",sceneryRegions);
			sceneryRegionsMap.put("index",2);
			sceneryRegionsMap.put("name","景点");
		}
		locationCondition.setSceneryRegion(sceneryRegionsMap);


		//地铁
		Map<String,Object>  subwaysMap = new HashMap<>();
		if (!Check.NuNCollection(subways)){
			subwaysMap.put("list",subways);
			subwaysMap.put("index",3);
			subwaysMap.put("name","地铁");
		}
		locationCondition.setSubway(subwaysMap);

		//行政区
		Map<String,Object>  districtsMap = new HashMap<>();
		if (!Check.NuNCollection(districts)){
			districtsMap.put("districts",districts);
			districtsMap.put("index",4);
			districtsMap.put("name","行政区");
		}
		locationCondition.setDistrict(districtsMap);
		return locationCondition;
	}


}
